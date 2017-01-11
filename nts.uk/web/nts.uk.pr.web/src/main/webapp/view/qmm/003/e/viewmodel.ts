module nts.uk.pr.view.qmm003.e {
    export class ScreenModel {
        index: number;
        items: any;
        singleSelectedCode: KnockoutObservable<any>;
        headers: any;
        test: any;
        curentNode: any;
        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
        filteredData: any;
        selectedCodes: any;
        constructor(){
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

            self.singleSelectedCode = ko.observable("11");
            self.test = ko.observable(null);
            self.curentNode = ko.observable(new Node("", "", []));
            self.index = 0;
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(),"childs"));
            self.selectedCodes = ko.observableArray([]);
            //Init();
            self.singleSelectedCode.subscribe(function(newValue){
                self.curentNode(findObjByCode(self.items,newValue));
                });
            
            function findObjByCode(items: Array<Node>, newValue: string): Node {
                        let _node: Node;
                        _.find(items, function(_obj: Node) {
                            if (!_node) {
                                if (_obj.code == newValue) {
                                    _node = _obj;
                                              
                                } else {
                                    
                                    _node = findObjByCode(_obj.childs, newValue);
                                }
                            }
                        });
                        
                        return _node;
                    };
                 function Init(): void{
                 self.singleSelectedCode("11");
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