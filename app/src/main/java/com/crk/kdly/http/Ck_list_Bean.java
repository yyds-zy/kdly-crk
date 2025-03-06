package com.crk.kdly.http;

import java.util.List;

/**
 * @author 孙贝贝
 * @packagename com.example.kdly.http
 * @date on 2020/8/27 17:10
 * @wechat 18813158027
 */
public class Ck_list_Bean {


    private List<OutlistBean> outlist;

    public List<OutlistBean> getOutlist() {
        return outlist;
    }

    public void setOutlist(List<OutlistBean> outlist) {
        this.outlist = outlist;
    }

    public static class OutlistBean {
        /**
         * list2 : [{"position_code":"8414A03-43","base_qtyn":80,"subid":"1"}]
         * main_id : 722020062610769
         * cinv_std : 5428300(Y)
         * cinv_code : 222775
         */

        private String main_id;
        private String cinv_std;
        private String cinv_code;
        private List<List2Bean> list2;

        public String getMain_id() {
            return main_id;
        }

        public void setMain_id(String main_id) {
            this.main_id = main_id;
        }

        public String getCinv_std() {
            return cinv_std;
        }

        public void setCinv_std(String cinv_std) {
            this.cinv_std = cinv_std;
        }

        public String getCinv_code() {
            return cinv_code;
        }

        public void setCinv_code(String cinv_code) {
            this.cinv_code = cinv_code;
        }

        public List<List2Bean> getList2() {
            return list2;
        }

        public void setList2(List<List2Bean> list2) {
            this.list2 = list2;
        }

        public static class List2Bean {
            /**
             * position_code : 8414A03-43
             * base_qtyn : 80.0
             * subid : 1
             */

            private String position_code;
            private double base_qtyn;
            private String subid;

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

            public String getSubid() {
                return subid;
            }

            public void setSubid(String subid) {
                this.subid = subid;
            }
        }
    }
}
