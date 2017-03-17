var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.treeGridHistory = ko.observable(new TreeGrid());
                self.a_sel_001 = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTabASel001 = ko.observable('tab-1');
                self.isNewMode = ko.observable(true);
                self.startYearMonth = ko.observable('');
                self.treeGridHistory().singleSelectedCode.subscribe(function (codeChange) {
                    var currentNode = null;
                    var currentParentNode = null;
                    for (var order = 0; order < self.treeGridHistory().items().length; order++) {
                        var foundNode = findNode(self.treeGridHistory().items()[order], self.treeGridHistory().singleSelectedCode());
                        if (foundNode) {
                            currentNode = foundNode;
                            currentParentNode = self.treeGridHistory().items()[order];
                        }
                    }
                    var rangeYearMonth = currentNode.name.split('~');
                    self.startYearMonth(rangeYearMonth[0].trim());
                    self.viewModel017b().startYearMonth(rangeYearMonth[0].trim());
                    self.viewModel017b().formulaCode(currentParentNode.code);
                    self.viewModel017b().formulaName(currentParentNode.name);
                    self.isNewMode(false);
                });
                self.viewModel017b = ko.observable(new qmm017.BScreen(self));
                self.viewModel017c = ko.observable(new qmm017.CScreen(self.viewModel017b));
                self.viewModel017d = ko.observable(new qmm017.DScreen());
                self.viewModel017e = ko.observable(new qmm017.EScreen());
                self.viewModel017f = ko.observable(new qmm017.FScreen());
                self.viewModel017g = ko.observable(new qmm017.GScreen());
                self.viewModel017h = ko.observable(new qmm017.HScreen());
                self.viewModel017i = ko.observable(new qmm017.IScreen());
            }
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                var itemsTreeGridHistory = [];
                var itemsTreeGridFormula = [];
                var nodesTreeGrid = [];
                qmm017.service.getAllFormula().done(function (lstFormulaDto) {
                    if (lstFormulaDto.length > 0) {
                        var _loop_1 = function(orderFormulaDto) {
                            itemsTreeGridFormula.push(lstFormulaDto[orderFormulaDto]);
                            var itemTreeGridHistory = [];
                            qmm017.service.findFormulaHistoryByCode(lstFormulaDto[orderFormulaDto].formulaCode)
                                .done(function (lstFormulaHistoryDto) {
                                if (lstFormulaHistoryDto.length > 0) {
                                    for (var orderFormulaHistoryDto = 0; orderFormulaHistoryDto < lstFormulaHistoryDto.length; orderFormulaHistoryDto++) {
                                        itemTreeGridHistory.push(lstFormulaHistoryDto[orderFormulaHistoryDto]);
                                    }
                                    itemsTreeGridHistory.push(itemTreeGridHistory);
                                    var nodeHistory = [];
                                    for (var orderHistory = 0; orderHistory < itemsTreeGridHistory[orderFormulaDto].length; orderHistory++) {
                                        nodeHistory.push(new Node(itemsTreeGridHistory[orderFormulaDto][orderHistory].historyId, nts.uk.time.formatYearMonth(itemsTreeGridHistory[orderFormulaDto][orderHistory].startDate)
                                            + ' ~ '
                                            + nts.uk.time.formatYearMonth(itemsTreeGridHistory[orderFormulaDto][orderHistory].endDate), []));
                                    }
                                    var nodeFormula = new Node(itemsTreeGridFormula[orderFormulaDto].formulaCode, itemsTreeGridFormula[orderFormulaDto].formulaName, nodeHistory);
                                    nodesTreeGrid.push(nodeFormula);
                                    self.treeGridHistory().items(nodesTreeGrid);
                                }
                            })
                                .fail(function (res) {
                                // Alert message
                                alert(res);
                            });
                        };
                        for (var orderFormulaDto = 0; orderFormulaDto < lstFormulaDto.length; orderFormulaDto++) {
                            _loop_1(orderFormulaDto);
                        }
                    }
                    dfd.resolve();
                    //                for (let orderTreeGrid = 0; orderTreeGrid < itemsTreeGridFormula.length; orderTreeGrid++) {
                    //                    let nodeHistory = []
                    //                    for(let orderHistory = 0; orderHistory< itemsTreeGridHistory[orderTreeGrid].length; orderHistory++) {
                    //                        nodeHistory.push(
                    //                            new Node(
                    //                                itemsTreeGridHistory[orderTreeGrid][orderHistory].historyId,
                    //                                itemsTreeGridHistory[orderTreeGrid][orderHistory].startDate + ' ~ ' + itemsTreeGridHistory[orderTreeGrid][orderHistory].endDate,
                    //                                []
                    //                            )
                    //                        );
                    //                    }
                    //                    let nodeFormula = new Node(itemsTreeGridFormula[orderTreeGrid].formulaCode,itemsTreeGridFormula[orderTreeGrid].formulaName,nodeHistory);
                    //                }
                    //                console.log(nodesTreeGrid);
                }).fail(function (res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            };
            return ScreenModel;
        }());
        qmm017.ScreenModel = ScreenModel;
        function findNode(targetNode, searchString) {
            if (targetNode.code === searchString) {
                if (targetNode.childs.length > 0) {
                    return targetNode.childs[0];
                }
                else if (targetNode.childs.length === 0) {
                    return targetNode;
                }
            }
            for (var count = 0; count < targetNode.childs.length; count++) {
                var foundNode = findNode(targetNode.childs[count], searchString);
                if (foundNode) {
                    return foundNode;
                }
            }
        }
        var TreeGrid = (function () {
            function TreeGrid() {
                var self = this;
                self.items = ko.observableArray([new Node('001', 'Formula 1', [
                        new Node('0001-2', '2017/03 ~ 9999/12', []),
                        new Node('0001-1', '2016/06 ~ 2017/03', [])
                    ]), new Node('002', 'Formula 2', [
                        new Node('0002-2', '2017/03 ~ 9999/12', []),
                        new Node('0002-1', '2016/06 ~ 2017/03', [])
                    ])
                ]);
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
                if (childs.length > 0) {
                    self.nodeText = self.code + ' ' + self.name;
                }
                else if (childs.length === 0) {
                    self.nodeText = self.name;
                }
                self.childs = childs;
            }
            return Node;
        }());
        qmm017.Node = Node;
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
