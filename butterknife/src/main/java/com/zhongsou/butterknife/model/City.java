package com.zhongsou.butterknife.model;

import java.util.List;

/**
 * Created by yelong on 2015/10/12.
 */
public class City {

    /**
     * body : [{"id":166,"name":"朝阳区","children":[{"id":178,"name":"国贸"},{"id":186,"name":"双井"},{"id":173,"name":"三里屯"},{"id":184,"name":"对外经贸"},{"id":185,"name":"酒仙桥"},{"id":190,"name":"管庄"},{"id":188,"name":"首都机场"},{"id":191,"name":"十八里店"},{"id":187,"name":"北苑家园"},{"id":192,"name":"十里堡"},{"id":196,"name":"东坝"},{"id":245,"name":"孙河"},{"id":246,"name":"马泉营"},{"id":247,"name":"定福庄"},{"id":179,"name":"建外大街"},{"id":180,"name":"大望路"},{"id":182,"name":"朝外大街"},{"id":175,"name":"朝阳公园/团结湖"},{"id":189,"name":"左家庄"},{"id":176,"name":"亮马桥/三元桥"},{"id":177,"name":"亚运村"},{"id":183,"name":"望京"},{"id":181,"name":"劲松/潘家园"},{"id":194,"name":"安贞"},{"id":193,"name":"朝阳其它"}]},{"id":75,"name":"东城区","children":[{"id":73,"name":"地安门"},{"id":195,"name":"和平里"},{"id":88,"name":"王府井/东单"},{"id":201,"name":"建国门/北京站"},{"id":89,"name":"东四"},{"id":91,"name":"安定门"},{"id":200,"name":"朝阳门"},{"id":90,"name":"东直门"},{"id":199,"name":"广渠门"},{"id":202,"name":"左安门"},{"id":155,"name":"沙子口"},{"id":76,"name":"前门"},{"id":197,"name":"崇文门"},{"id":198,"name":"天坛"}]},{"id":50,"name":"西城区","children":[{"id":81,"name":"西四"},{"id":85,"name":"月坛"},{"id":72,"name":"什刹海"},{"id":74,"name":"德外大街"},{"id":61,"name":"西单"},{"id":71,"name":"复兴门"},{"id":80,"name":"阜成门"},{"id":67,"name":"西直门/动物园"},{"id":92,"name":"新街口"},{"id":73,"name":"地安门"},{"id":76,"name":"前门"},{"id":83,"name":"牛街"},{"id":84,"name":"虎坊桥"},{"id":77,"name":"菜市口"},{"id":82,"name":"广内大街"},{"id":78,"name":"广外大街"},{"id":79,"name":"宣武门"},{"id":86,"name":"右安门"}]},{"id":1,"name":"海淀区","children":[{"id":44,"name":"双榆树"},{"id":41,"name":"五棵松"},{"id":40,"name":"清河"},{"id":47,"name":"远大路"},{"id":45,"name":"香山"},{"id":8,"name":"中关村"},{"id":10,"name":"五道口"},{"id":52,"name":"魏公村"},{"id":26,"name":"北太平庄"},{"id":39,"name":"苏州桥"},{"id":37,"name":"北下关"},{"id":38,"name":"公主坟/万寿路"},{"id":30,"name":"紫竹桥"},{"id":27,"name":"航天桥"},{"id":43,"name":"上地"},{"id":25,"name":"颐和园"},{"id":42,"name":"海淀其它"}]},{"id":87,"name":"丰台区","children":[{"id":138,"name":"北大地"},{"id":140,"name":"刘家窑"},{"id":128,"name":"青塔"},{"id":144,"name":"开阳里"},{"id":110,"name":"草桥"},{"id":111,"name":"看丹桥"},{"id":112,"name":"花乡"},{"id":113,"name":"大红门"},{"id":160,"name":"公益西桥"},{"id":162,"name":"云岗"},{"id":145,"name":"卢沟桥"},{"id":137,"name":"方庄"},{"id":134,"name":"六里桥/丽泽桥"},{"id":139,"name":"洋桥/木樨园"},{"id":127,"name":"丰台其它"}]},{"id":168,"name":"石景山区","children":[{"id":169,"name":"模式口"},{"id":174,"name":"苹果园"},{"id":172,"name":"古城/八角"},{"id":171,"name":"鲁谷"},{"id":170,"name":"石景山其它"}]},{"id":163,"name":"大兴区","children":[{"id":167,"name":"亦庄"},{"id":165,"name":"旧宫"},{"id":164,"name":"黄村"},{"id":131,"name":"西红门"}]},{"id":98,"name":"通州区","children":[{"id":102,"name":"果园"},{"id":101,"name":"梨园"},{"id":100,"name":"新华大街"},{"id":99,"name":"九棵树"}]},{"id":93,"name":"昌平区","children":[{"id":96,"name":"回龙观"},{"id":95,"name":"天通苑"},{"id":94,"name":"昌平镇"},{"id":97,"name":"小汤山"}]},{"id":204,"name":"房山区","children":[{"id":248,"name":"良乡"}]},{"id":203,"name":"顺义区","children":[{"id":249,"name":"国展"}]},{"id":103,"name":"近郊","children":[{"id":104,"name":"平谷"},{"id":105,"name":"密云县"},{"id":106,"name":"延庆县"},{"id":109,"name":"门头沟"},{"id":107,"name":"怀柔"},{"id":108,"name":"近郊其它"}]}]
     * head : {"status":200}
     */

    private HeadEntity head;
    private List<BodyEntity> body;

    public void setHead(HeadEntity head) {
        this.head = head;
    }

    public void setBody(List<BodyEntity> body) {
        this.body = body;
    }

    public HeadEntity getHead() {
        return head;
    }

    public List<BodyEntity> getBody() {
        return body;
    }

    public static class HeadEntity {
        /**
         * status : 200
         */

        private int status;

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    public static class BodyEntity {
        /**
         * id : 166
         * name : 朝阳区
         * children : [{"id":178,"name":"国贸"},{"id":186,"name":"双井"},{"id":173,"name":"三里屯"},{"id":184,"name":"对外经贸"},{"id":185,"name":"酒仙桥"},{"id":190,"name":"管庄"},{"id":188,"name":"首都机场"},{"id":191,"name":"十八里店"},{"id":187,"name":"北苑家园"},{"id":192,"name":"十里堡"},{"id":196,"name":"东坝"},{"id":245,"name":"孙河"},{"id":246,"name":"马泉营"},{"id":247,"name":"定福庄"},{"id":179,"name":"建外大街"},{"id":180,"name":"大望路"},{"id":182,"name":"朝外大街"},{"id":175,"name":"朝阳公园/团结湖"},{"id":189,"name":"左家庄"},{"id":176,"name":"亮马桥/三元桥"},{"id":177,"name":"亚运村"},{"id":183,"name":"望京"},{"id":181,"name":"劲松/潘家园"},{"id":194,"name":"安贞"},{"id":193,"name":"朝阳其它"}]
         */

        private int id;
        private String name;
        private List<ChildrenEntity> children;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setChildren(List<ChildrenEntity> children) {
            this.children = children;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<ChildrenEntity> getChildren() {
            return children;
        }

        public static class ChildrenEntity {
            /**
             * id : 178
             * name : 国贸
             */

            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
