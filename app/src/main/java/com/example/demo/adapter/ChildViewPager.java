package com.example.demo.adapter;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class ChildViewPager extends ViewPager {
    /** 触摸时按下坐标 **/
    PointF downP = new PointF();
    /** 触摸时当前坐标 **/
    PointF curP = new PointF();
    OnSingleTouchListener onSingleTouchListener;
    /** 当前pager的总页数 **/
    int listCount;
    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ChildViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        //在此位置返回true，将事件拦截在子pager中
        return true;
    }
    //添加总页数记录，在给pager配置adapter时要调用此方法把总页数添加进来
    public void setListSize(int size){
        listCount = size;
    }
    //获取总页数
    public int getFragmentCount(){
        return listCount;
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        //每次onTouch事件触发时都记录当前按下的坐标
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if(arg0.getAction() == MotionEvent.ACTION_DOWN){
            //记录按下时候的坐标
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            //此句代码是避免父pager对操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(arg0.getAction() == MotionEvent.ACTION_MOVE){
            //判断是否是在第一个页面继续右划或者是否在最后一个页面时继续左划
            //如果是，把事件给到父级pager
            if(getCurrentItem()==getFragmentCount()-1&&downP.x-curP.x>0||getCurrentItem()==0&&downP.x-curP.x<0){
                getParent().requestDisallowInterceptTouchEvent(false);
            }else{
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }

        if(arg0.getAction() == MotionEvent.ACTION_UP){
            //在此时判断按下和松手是否在同一位置
            //如果是，执行自己写的点击事件
            if(downP.x==curP.x && downP.y==curP.y){
                onSingleTouch();
                return true;
            }
        }
        return super.onTouchEvent(arg0);
    }

    //点击事件
    public void onSingleTouch() {
        if (onSingleTouchListener!= null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    //创建点击事件接口
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListener(OnSingleTouchListener
                                                 onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }
}
