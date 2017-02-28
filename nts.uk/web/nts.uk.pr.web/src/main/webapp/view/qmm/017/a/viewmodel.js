var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var TreeGrid = (function () {
            function TreeGrid() {
                var self = this;
                self.items = ko.observableArray([new Node('0001', 'サービス部', [
                        new Node('0001-1', 'サービス部1', []),
                        new Node('0001-2', 'サービス部2', []),
                        new Node('0001-3', 'サービス部3', [])
                    ]), new Node('0002', '開発部', [])]);
                self.selectedCode = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.index = 0;
            }
            return TreeGrid;
        }());
        qmm017.TreeGrid = TreeGrid;
        var Node = (function () {
            function Node(code, name, childs) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
            }
            return Node;
        }());
        qmm017.Node = Node;
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.viewModel017b = ko.observable(new qmm017.BScreen());
                self.viewModel017c = ko.observable(new qmm017.CScreen(self.viewModel017b));
                self.viewModel017d = ko.observable(new qmm017.DScreen());
                self.viewModel017e = ko.observable(new qmm017.EScreen());
                self.viewModel017f = ko.observable(new qmm017.FScreen());
                self.viewModel017g = ko.observable(new qmm017.GScreen());
                self.viewModel017h = ko.observable(new qmm017.HScreen());
                self.viewModel017i = ko.observable(new qmm017.IScreen());
                self.a_lst_001 = ko.observable(new TreeGrid());
                self.a_sel_001 = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTabASel001 = ko.observable('tab-1');
            }
            return ScreenModel;
        }());
        qmm017.ScreenModel = ScreenModel;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
function OpenModalSubWindowJ(option) {
    nts.uk.ui.windows.sub.modal("../../../../view/qmm/017/j/index.xhtml", $.extend(option, { title: "計算式の登録>履歴の追加" }));
}
function OpenModalSubWindowK(option) {
    var test = nts.uk.ui.windows.sub.modal("../../../../view/qmm/017/k/index.xhtml", $.extend(option, { title: "計算式の登録>履歴の編集" }));
}
//export module nts.uk.pr.view.qmm017.a {
//    export module viewmodel {
//        export class Node {
//            code: string;
//            name: string;
//            nodeText: string;
//            childs: any;
//            constructor(code: string, name: string, childs: Array<Node>) {
//                var self = this;
//                self.code = code;
//                self.name = name;
//                self.nodeText = self.code + ' ' + self.name;
//                self.childs = childs;
//            }
//        }
//        export class SwitchButton {
//            roundingRules: KnockoutObservableArray<any>;
//            selectedRuleCode: any;
//
//            constructor(data) {
//                var self = this;
//                self.roundingRules = ko.observableArray(data);
//                self.selectedRuleCode = ko.observable(1);
//            }
//        }
//        export class ItemModelComboBox {
//            code: any;
//            name: string;
//            label: string;
//
//            constructor(code: any, name: string) {
//                this.code = code;
//                this.name = name;
//            }
//        }
