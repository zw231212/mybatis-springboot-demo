package com.zzq.api.interceptor.page;

import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
public class PageHelper {

    private Integer number;
    private Integer size;
    private Integer totalElements;
    private Integer totalPages;
    private boolean hasBefore;
    private boolean hasNext;

    //前一页
    private int prePage;
    //下一页
    private int nextPage;

    //导航页码数
    private int navigatePages = 6;
    //所有导航页号
    private int[] navigatepageNums;

    //导航条上的第一页
    private int navigateFirstPage;
    //导航条上的最后一页
    private int navigateLastPage;

    private static Map<Integer, PageHelper> helperMap = new HashMap<>();


    public static PageHelper of(int num, int size) {
        Thread thread = Thread.currentThread();
        int i = thread.hashCode();
        PageHelper helper = new PageHelper();
        helper.setNumber(num);
        helper.setSize(size);
        helperMap.put(i,helper);
        return helper;
    }

    protected static PageHelper getInstance(){
        return helperMap.get(Thread.currentThread().hashCode());
    }


    /**
     * 计算一些导航页面和最后最前页面信息
     */
    public void doLastCal(){
        calcNavigatepageNums();
        calcPage();
    }

    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (totalPages <= navigatePages) {
            navigatepageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = number - navigatePages / 2;
            int endNum = number + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > totalPages) {
                endNum = totalPages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }


    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            navigateFirstPage = navigatepageNums[0];
            navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
            if (number > 1) {
                prePage = number - 1;
            }
            if (number < totalPages) {
                nextPage = number + 1;
            }
        }
        this.hasBefore = number != 0;
        this.hasNext = number != totalPages-1;
    }

    @Override
    public String toString() {
        doLastCal();
        return "PageHelper{" +
                "number=" + number +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", hasBefore=" + hasBefore +
                ", hasNext=" + hasNext +
                ", prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", navigatePages=" + navigatePages +
                ", navigatepageNums=" + Arrays.toString(navigatepageNums) +
                ", navigateFirstPage=" + navigateFirstPage +
                ", navigateLastPage=" + navigateLastPage +
                '}';
    }
}
