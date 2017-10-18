package com.lgs.imageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.lgs.imageloader.cache.DoubleCache;
import com.lgs.imageloader.cache.MemoryCache;
import com.lgs.imageloader.cache.NoCache;
import com.lgs.imageloader.config.ImageLoaderConfig;
import com.lgs.imageloader.core.SimpleImageLoader;
import com.lgs.imageloader.policy.SerialPolicy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private SimpleImageLoader simpleImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ImageLoaderConfig imageLoaderConfig = new ImageLoaderConfig.Builder()
                                    .setLoadingImg(R.drawable.loading)
                                    .setFailLoadImg(R.drawable.not_found)
                                    .setPolicyLoad(new SerialPolicy())
                                    .setCachePolicy(new DoubleCache(getApplicationContext()))
                                    .build();
        simpleImageLoader = SimpleImageLoader.getInstance(imageLoaderConfig);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return imageThumbUrls.length;
        }

        @Override
        public String getItem(int position) {
            return imageThumbUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext() , R.layout.item , null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            simpleImageLoader.displayImage(getItem(position) , imageView);
            return imageView;
        }
    }

    public final static String[] imageThumbUrls = new String[] {
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
            // 以下三张为本地图片,本地图片支持uri格式，形如file:// + 图片绝对路径
            "file:///storage/emulated/0/Camera/P41115-140216.jpg",
            "file:///storage/emulated/0/Camera/P41115-142950.jpg",
            "file:///storage/emulated/0/Camera/P50102-133614.jpg",
            "http://alipiba.com/image/not_found_haha.jpg" // 不存在的图片
    };
}
