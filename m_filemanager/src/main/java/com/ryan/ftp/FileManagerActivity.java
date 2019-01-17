package com.ryan.ftp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ryan.ftp.adapter.GirlAdapter;
import com.ryan.ftp.ftp.FTPUtils;
import com.ryan.ftp.model.FileItemBean;
import com.ryan.ftp.ftp.DownloadService;
import com.ryan.ftp.presenter.FileItemPresenter;
import com.ryan.ftp.presenter.FilesInfoPresenter;
import com.ryan.ftp.view.IFileItemView;
import com.ryan.m_filemanager.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManagerActivity extends AppCompatActivity implements IFileItemView,SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 1;
    private int mTempPageCount = 2;
    private int PAGE_SIZE = 10;

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private GirlAdapter mAdapter;

    private boolean isItemDeleted = false;

    private ArrayList<FileItemBean> mAllFtpFilesList;  // 全部的文件信息
    private ArrayList<FileItemBean> mAllList = new ArrayList<>(); // 当前显示的信息

    private boolean isLoadMore;//是否是底部加载更多

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        this.setTitle("连接到"+Constants.FTP_IP+":"+Constants.FTP_PORT);

        initAllFiles();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);

        new Thread("closeFtP"){
            @Override
            public void run() {
                super.run();
                try {
                    FTPUtils.getInstance().closeConnect();
                    Log.d("zhf123", "退出，断开");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        EventBus.getDefault().register(this);

        mSwipeRefreshLayout = findViewById(R.id.type_item_swipfreshlayout);
        mRecyclerView = findViewById(R.id.type_item_recyclerview);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        // 监听刷新
        mSwipeRefreshLayout.setOnRefreshListener(FileManagerActivity.this);
        //实现首次自动显示加载提示
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true); // 这里只是显示一个进度条
            }
        });

        mAdapter = new GirlAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setPreLoadNumber(3); //设置当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested()方法

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (PAGE_COUNT == mTempPageCount) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                initData();
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                delete(position);
                return false;
            }
        });

        mAdapter.setOnItemClickListener(new  BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(FileManagerActivity.this, "setOnItemClickListener position="+position, Toast.LENGTH_SHORT).show();
            }
        });

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRecyclerView.setLayoutManager(layoutManager);

        //曾经删除过Item，则滑到顶部的时候刷新布局，避免错乱。
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isItemDeleted){
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    int[] firstVisibleItem = null;
                    firstVisibleItem = layoutManager.findFirstVisibleItemPositions(firstVisibleItem);
                    if (firstVisibleItem != null && firstVisibleItem[0] == 0) {
                        if (mAdapter!=null) {
                            isItemDeleted = false;
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获取FTP上所有的文件信息
     */
    private void initAllFiles(){
        FilesInfoPresenter presenter = new FilesInfoPresenter(this);
        presenter.getFilesInfo();
    }

    /**
     * 获取到所有相册信息
     * @param data
     */
    @Override
    public void onCompleted(ArrayList<FileItemBean> data) {
        mAllFtpFilesList = data;
        initData();
    }

    private void initData() {
        FileItemPresenter presenter = new FileItemPresenter(this);
        Log.d("zhf123", "initData====>PAGE_COUNT="+PAGE_COUNT+", mAllFtpFilesList="+mAllFtpFilesList.size());
        presenter.getFileItemData(mAllFtpFilesList, PAGE_COUNT, PAGE_SIZE ); // 下载当前第几页的图片到本地
    }

    /**
     * 每次在首位置下拉刷新
     */
    @Override
    public void onRefresh() {
        Log.d("zhf123", "onRefresh="+PAGE_COUNT);
        isLoadMore = false;
        PAGE_COUNT = 1;
        mTempPageCount = 2;
        mAllList.clear();
        initData();
    }

    /**
     * 下载完成后，开始调用DataService去加载bitmap放到data中去
     * @param data
     */
    @Override
    public void onDownload(ArrayList<FileItemBean> data) {
        DownloadService.startService(FileManagerActivity.this, data /*, mSubtype*/);
    }

    @Override
    public void onError() {
        if (isLoadMore) {
            mAdapter.loadMoreFail();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    // 声明订阅方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(List<FileItemBean> data) {
        Log.d("zhf123", "dataEvent: dataEvent data="+data);
        mAllList.addAll(data);
        setData(data);
    }

    private void setData(List<FileItemBean> data){
        int size = data.size();
//        if (!data.get(0).getSubtype().equals(mSubtype)) {
//            return;
//        }

        if (isLoadMore) {
            if (data.size() == 0) {
                mAdapter.loadMoreFail();
            } else {
                mTempPageCount++;
                mAdapter.addData(data);
            }
        } else {
            mAdapter.setNewData(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (size < 10) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(!isLoadMore);
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    private void delete(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want delete this photo?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isItemDeleted = true;
                        mAllList.remove(position);
                        mAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
