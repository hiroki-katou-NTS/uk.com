module nts.uk.pr.view.qmm003.d {
    export class ScreenModel {
        items: any;
        item1s: any;
        singleSelectedCode: KnockoutObservable<any>;
        selectedCode: any;
        index: number;
        curentNode: any;
        arrayCode: any;
        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
        filteredData: any;
        filteredData1: any;
        filteredData2: any;
        filteredData3: any;
        editMode1: boolean; //true al
        constructor() {
            let self = this;
            self.init();
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.selectedCode.subscribe(function(newValue) {
                self.arrayCode(newValue);
            });

        }
        findByName(items: Array<Node>): Node {
            let self = this;
            let node: Node;
            _.find(items, function(obj: Node) {
                if (!node) {
                    if (obj.name == self.curentNode().name) {
                        node = obj;
                    }
                }
            });
            return node;
        };
        findByCode(items: Array<Node>, newValue: string): Node {
            let self = this;
            let _node: Node;
            _.find(items, function(_obj: Node) {
                if (!_node) {
                    if (_obj.code == newValue) {
                        _node = _obj;
                    }
                }
            });
            return _node;
        };
        clickButton(): any {
            let self = this;
            for (let i = 0; i < _.size(self.arrayCode()); i++) {
                self.removeNodeByCode(self.items(), self.arrayCode()[i]);
                self.item1s(self.items());
                self.items([]);
                self.items(self.item1s());
            }
            nts.uk.ui.windows.setShared("items", self.items(), true);
            nts.uk.ui.windows.close();

        }
        cancelButton(): void {
            nts.uk.ui.windows.close();
        }
        removeNodeByCode(items: Array<Node>, newValue: string): any {
            let self = this;
            _.remove(items, function(obj: Node) {
                if (obj.code == newValue) {
                    return obj.code == newValue;
                } else {
                    return self.removeNodeByCode(obj.childs, newValue);

                }
            })
        };
        removeData1(items: Array<Node>): any {
            _.remove(items, function(value: string) {
                return _.size(value) == 1;
            });
        }
        removeData2(items: Array<Node>): any {
            _.remove(items, function(value: string) {
                return _.size(value) == 2;
            });
        }
        removeData3(items: Array<Node>): any {
            _.remove(items, function(value: string) {
                return _.size(value) == 3;
            });
        }
        delete(items: Array<Node>): any {
            let self = this;
            _.each(self.filteredData(), function(obj: Node) {
                if (_.size(obj.code) == 1) {
                    self.removeData1(self.filteredData());
                }
            });



        }
        init(): void {
            let self = this;
            self.items = ko.observableArray(
                [
                    new Node('1', '東北', [
                        new Node('11', '青森県', [
                            new Node('022012', '青森市', []),
                            new Node('052019', '秋田市', [])
                        ]),
                        new Node('12', '東北', [
                            new Node('062014', '山形市', [])
                        ]),
                        new Node('13', '福島県', [
                            new Node('062015', '福島市', [])
                        ])
                    ]),
                    new Node('2', '北海道', []),
                    new Node('3', '東海', []),
                    new Node('4', '関東', [
                        new Node('41', '茨城県', [
                            new Node('062016', '水戸市', []),
                        ]),
                        new Node('42', '栃木県', [
                            new Node('062017', '宇都宮市', [])
                        ]),
                        new Node('43', '埼玉県', [
                            new Node('062019', '川越市', []),
                            new Node('062020', '熊谷市', []),
                            new Node('062022', '浦和市', []),
                        ])
                    ]),
                    new Node('5', '東海', [])
                ]);
            self.item1s = ko.observableArray([]);
            self.singleSelectedCode = ko.observable("11");
            self.curentNode = ko.observable(new Node("", "", []));
            self.index = 0;
            self.selectedCode = ko.observableArray([]);
            self.arrayCode = ko.observableArray([]);
        };
        register(): void {
            // if()
            //                         $("#A_INP_002").attr('disabled', 'true');

            let inputSearch = $("#D_SCH_001").find("input.ntsSearchBox").val();
            if (inputSearch == "") {
                $('#D_SCH_001').ntsError('set', 'inputSearch が入力されていません。');
            } else {
                $('#D_SCH_001').ntsError('clear');
            }

            // errror search
            let error: boolean;
            _.find(this.filteredData(), function(obj: Node) {
                if (obj.code !== inputSearch) {
                    error = true;
                }
            });
            if (error = true) {
                $('#D_SCH_001').ntsError('set', '対象データがありません。');
            } else {
                $('#D_SCH_001').ntsError('clear');
            }


        }
    }
    export class Node {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;

        }
    }

}