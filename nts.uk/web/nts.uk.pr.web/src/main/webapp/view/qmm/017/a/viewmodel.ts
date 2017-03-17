module nts.qmm017 {

    export class ScreenModel {
        viewModel017b: KnockoutObservable<any>;
        viewModel017c: KnockoutObservable<any>;
        viewModel017d: KnockoutObservable<any>;
        viewModel017e: KnockoutObservable<any>;
        viewModel017f: KnockoutObservable<any>;
        viewModel017g: KnockoutObservable<any>;
        viewModel017h: KnockoutObservable<any>;
        viewModel017i: KnockoutObservable<any>;

        treeGridHistory: KnockoutObservable<TreeGrid>;
        a_sel_001: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTabASel001: KnockoutObservable<string>;
        isNewMode: KnockoutObservable<boolean>;
        startYearMonth: KnockoutObservable<string>;
        selectedFormula: KnockoutObservable<model.FormulaDto>;
        
        constructor() {
            var self = this;
            self.treeGridHistory = ko.observable(new TreeGrid());
            self.a_sel_001 = ko.observableArray([
                { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTabASel001 = ko.observable('tab-1');
            self.isNewMode = ko.observable(true);
            self.startYearMonth = ko.observable('');
            self.treeGridHistory().singleSelectedCode.subscribe(function(codeChange) {
                let currentNode = null;
                let currentParentNode = null;
                for (let order = 0; order < self.treeGridHistory().items().length; order++) {
                    let foundNode = findNode(self.treeGridHistory().items()[order], self.treeGridHistory().singleSelectedCode());
                    if (foundNode) {
                        currentNode = foundNode;
                        currentParentNode = self.treeGridHistory().items()[order];
                    }
                }
                let rangeYearMonth = currentNode.name.split('~');
                self.startYearMonth(rangeYearMonth[0].trim());
                self.viewModel017b().startYearMonth(rangeYearMonth[0].trim());
                self.viewModel017b().formulaCode(currentParentNode.code);
                self.viewModel017b().formulaName(currentParentNode.name);
                self.isNewMode(false);
            });
            self.viewModel017b = ko.observable(new BScreen(self));
            self.viewModel017c = ko.observable(new CScreen(self.viewModel017b));
            self.viewModel017d = ko.observable(new DScreen());
            self.viewModel017e = ko.observable(new EScreen());
            self.viewModel017f = ko.observable(new FScreen());
            self.viewModel017g = ko.observable(new GScreen());
            self.viewModel017h = ko.observable(new HScreen());
            self.viewModel017i = ko.observable(new IScreen());
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            var itemsTreeGridHistory = [];
            var itemsTreeGridFormula = [];
            var nodesTreeGrid = [];

            service.getAllFormula().done(function(lstFormulaDto: Array<model.FormulaDto>) {
                if (lstFormulaDto.length > 0) {
                    for (let orderFormulaDto = 0; orderFormulaDto < lstFormulaDto.length; orderFormulaDto++) {
                        itemsTreeGridFormula.push(lstFormulaDto[orderFormulaDto]);
                        let itemTreeGridHistory = [];
                        service.findFormulaHistoryByCode(lstFormulaDto[orderFormulaDto].formulaCode)
                            .done(function(lstFormulaHistoryDto: Array<model.FormulaHistoryDto>) {
                                if (lstFormulaHistoryDto.length > 0) {
                                    for (let orderFormulaHistoryDto = 0; orderFormulaHistoryDto < lstFormulaHistoryDto.length; orderFormulaHistoryDto++) {
                                        itemTreeGridHistory.push(lstFormulaHistoryDto[orderFormulaHistoryDto]);
                                    }
                                    itemsTreeGridHistory.push(itemTreeGridHistory);
                                    let nodeHistory = [];
                                    for(let orderHistory = 0; orderHistory< itemsTreeGridHistory[orderFormulaDto].length; orderHistory++) {
                                        nodeHistory.push(
                                            new Node(
                                                itemsTreeGridHistory[orderFormulaDto][orderHistory].historyId,
                                                nts.uk.time.formatYearMonth(itemsTreeGridHistory[orderFormulaDto][orderHistory].startDate) 
                                                + ' ~ ' 
                                                + nts.uk.time.formatYearMonth(itemsTreeGridHistory[orderFormulaDto][orderHistory].endDate),
                                                []
                                            )
                                        );
                                    }
                                    let nodeFormula = new Node(itemsTreeGridFormula[orderFormulaDto].formulaCode,itemsTreeGridFormula[orderFormulaDto].formulaName,nodeHistory);
                                    nodesTreeGrid.push(nodeFormula);
                                    self.treeGridHistory().items(nodesTreeGrid);
                                }
                            })
                            .fail(function(res) {
                                // Alert message
                                alert(res);
                            });
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
            }).fail(function(res) {
                // Alert message
                alert(res);
            });

            // Return.
            return dfd.promise();
        }
    }

    function findNode(targetNode: Node, searchString: string) {
        if (targetNode.code === searchString) {
            if (targetNode.childs.length > 0) {
                return targetNode.childs[0];
            } else if (targetNode.childs.length === 0) {
                return targetNode;
            }
        }
        for (let count = 0; count < targetNode.childs.length; count++) {
            let foundNode = findNode(targetNode.childs[count], searchString);
            if (foundNode) {
                return foundNode;
            }
        }
    }

    export class TreeGrid {
        index: number;
        items: any;
        selectedCode: any;
        singleSelectedCode: KnockoutObservable<string>;
        constructor() {
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
    }

    export class Node {
        code: string;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            if (childs.length > 0) {
                self.nodeText = self.code + ' ' + self.name;
            } else if (childs.length === 0) {
                self.nodeText = self.name;
            }
            self.childs = childs;
        }
    }


}

function OpenModalSubWindowJ(option?: any) {
    nts.uk.ui.windows.sub.modal("../../../../view/qmm/017/j/index.xhtml", $.extend(option, { title: "計算式の登録>履歴の追加" }));
}

function OpenModalSubWindowK(option?: any) {
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



