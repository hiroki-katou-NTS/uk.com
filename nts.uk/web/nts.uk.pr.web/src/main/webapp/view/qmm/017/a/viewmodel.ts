module nts.uk.pr.view.qmm017.a {
    export module viewmodel {
        export class Node {
            code: string;
            name: string;
            nodeText: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
            }
        }
        export class TreeGrid {
            index: number;
            items: any;
            selectedCode: any;
            singleSelectedCode: any;
            constructor() {
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
        }

        export class ScreenModel {
            lst_001: KnockoutObservable<TreeGrid>;
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.lst_001 = ko.observable(new TreeGrid());
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');
            }
        }
    }
}