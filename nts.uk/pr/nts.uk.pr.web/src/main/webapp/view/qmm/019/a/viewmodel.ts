// TreeGrid Node
module qmm019.a {

    export class ScreenModel {
        //Khai bao bien
        itemList: KnockoutObservableArray<NodeTest>;
        singleSelectedCode: any;
        selectedCode: KnockoutObservableArray<NodeTest>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new NodeTest('0001', 'サービス部', [
                    new NodeTest('0001-1', 'サービス部1', []),
                    new NodeTest('0001-2', 'サービス部2', []),
                    new NodeTest('0001-3', 'サービス部3', []),
                    new NodeTest('0001-4', 'サービス部4', [])]),
                new NodeTest('0002', '開発部', [])
            ]);
            self.singleSelectedCode = ko.observable(null);
        }
        
        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

//            service.getAllLayout("1").done(function(layouts: Array<service.model.LayoutMasterDto>) {
//                self.layouts(layouts);
                dfd.resolve(null);
//            }).fail(function(res) {
//                // Alert message
//                alert(res);
//            });
            // Return.
            return dfd.promise();
        }
    }
    export class NodeTest {
        code: string;
        name: string;
        childs: Array<NodeTest>;
        nodeText: any;
        constructor(code: string, name: string, children: Array<NodeTest>) {
            this.code = code;
            this.name = name;
            this.childs = children;
            this.nodeText = this.code + ' ' + this.name;
        }

    }
}