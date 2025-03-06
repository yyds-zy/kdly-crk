package com.crk.kdly.http;

import java.util.List;

/**
 * @author 孙贝贝
 * @packagename com.example.kdly.http
 * @date on 2020/8/28 12:31
 * @wechat 18813158027
 */
public class Comfim_Result {

    /**
     * torf : t
     * list : [{"position_code":"8418A01-02","base_qtyn":200}]
     */

    private String torf;
    private List<ListBean> list;

    public String getTorf() {
        return torf;
    }

    public void setTorf(String torf) {
        this.torf = torf;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * position_code : 8418A01-02
         * base_qtyn : 200.0
         */

        private String position_code;
        private double base_qtyn;

        public String getPosition_code() {
            return position_code;
        }

        public void setPosition_code(String position_code) {
            this.position_code = position_code;
        }

        public double getBase_qtyn() {
            return base_qtyn;
        }

        public void setBase_qtyn(double base_qtyn) {
            this.base_qtyn = base_qtyn;
        }
    }
}
