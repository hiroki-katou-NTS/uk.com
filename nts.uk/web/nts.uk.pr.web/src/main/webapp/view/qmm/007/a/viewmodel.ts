module qmm007.a {
    export module viewmodel {
        export class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
                self.custom = 'Random' + new Date().getTime();
            }
        }

        export class ScreenModel {
            index: number;
            dataSource: any;
            filteredData: any;
            singleSelectedCode: any;
            selectedCodes: any;
            headers: any;

            sel_002: KnockoutObservableArray<any>;
            selected: KnockoutObservable<string>;
            enabled: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                    new Node('0003', 'Bangkok Thailand', []),
                    new Node('0004', 'Tokyo Japan', []),
                    new Node('0005', 'Jakarta Indonesia', []),
                    new Node('0002', 'Seoul Korea', []),
                    new Node('0006', 'Paris France', []),
                    new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                    new Node('0010', 'Beijing China', []),
                    new Node('0011', 'London United Kingdom', []),
                    new Node('0012', '', [])]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "childs"));
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([]);
                self.index = 0;
                self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);

                self.enabled = ko.observable(false);
                self.selected = ko.observable('');
                self.sel_002 = ko.observableArray([
                    { code: '1', name: '対象' },
                    { code: '2', name: '対象外' }
                ]);
            }

            goToB() {
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { dialogClass: 'no-close', height: 380, width: 400 }).setTitle('履歴の追加');
            }

        }
    }
}