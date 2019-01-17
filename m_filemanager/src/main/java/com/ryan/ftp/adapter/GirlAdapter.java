package com.ryan.ftp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ryan.ftp.BaseApplication;
import com.ryan.ftp.model.FileItemBean;
import com.ryan.ftp.utils.ImageLoader;
import com.ryan.ftp.view.ScaleImageView;
import com.ryan.m_filemanager.R;


/**
 * 第一个泛型Status是数据实体类型,第二个BaseViewHolder是ViewHolder其目的是为了支持扩展ViewHolde
 */
public class GirlAdapter extends BaseQuickAdapter<FileItemBean, BaseViewHolder> {

    public GirlAdapter(){
        super(R.layout.item_girl_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileItemBean item) {
        ScaleImageView imageView = helper.getView(R.id.girl_item_iv);
        imageView.setInitSize(item.getWidth(), item.getHeight());
        ImageLoader.load(BaseApplication.getContext(),
                item.getPath(), imageView);
    }

    public void deleteItem(int position){
        remove(position);
        notifyDataSetChanged();
    }
}
