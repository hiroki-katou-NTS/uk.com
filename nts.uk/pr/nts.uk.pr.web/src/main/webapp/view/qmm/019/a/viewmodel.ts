// TreeGrid Node
module qmm019.a {

    export class ScreenModel {
        //Khai bao bien
        itemList: KnockoutObservableArray<NodeTest>;
        singleSelectedCode: any;
        selectedCode: KnockoutObservableArray<NodeTest>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        layoutsMax: KnockoutObservableArray<service.model.LayoutMasterDto>;

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
            self.layouts = ko.observableArray([]);
            self.layoutsMax = ko.observableArray([]);
        }

        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            service.getAllLayout().done(function(layouts: Array<service.model.LayoutMasterDto>) {
                self.layouts(layouts);
                service.getLayoutsWithMaxStartYm().done(function(layoutsMax: Array<service.model.LayoutMasterDto>) {
                    self.layoutsMax(layoutsMax);
                    self.buildTreeDataSource();
                    dfd.resolve();
                });
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            // Return.
            return dfd.promise();
        }

        buildTreeDataSource(): any {
            var self = this;
            self.itemList.removeAll();
            _.forEach(self.layoutsMax(), function(layoutMax) {
                //self.itemList.push(new NodeTest())
                //let childLayouts = ko.observableArray([]);

                let childLayouts = _.filter(self.layouts, function(layout) {
                    return layout.stmtCode() === layoutMax.stmtCode();
                })
            });
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