module nts.uk.pr.view.qmm003.a {
    export class ScreenModel {
        // data of items list - tree grid
        items: any;
        item1s: any;
        singleSelectedCode: KnockoutObservable<any>;
        headers: any;
        curentNode: any;
        currentNode: any;
        nameBySelectedCode: any;
        arrayAfterFilter: any;
        labelSubsub: any; // show label sub sub of root
        inter: any;
        
        // data of itemList - combox
        itemList: KnockoutObservableArray<Node>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
        filteredData: any;
        filteredData1: any;
        filteredData2: any;
        selectedCodes: any;
        Value: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.currentNode = ko.observable(new Node("", "", []));
            self.init();
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.filteredData1 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.removeData(self.filteredData());
            self.selectedCodes = ko.observableArray([]);
            self.singleSelectedCode.subscribe(function(newValue) {
                self.Value(newValue);

                if (self.editMode) {
                    let count = 0;  
                    self.curentNode(self.findByCode(self.filteredData2(), newValue, count));
                    self.nameBySelectedCode(self.findByName(self.itemList()));             
                    if (count == 1) {
                        self.selectedCode(self.nameBySelectedCode().code);
                        
                    }
                    let co = 0, co1 = 0;
                    _.each(self.filteredData(), function(obj: Node) {

                        if (obj.code != self.curentNode().code) {
                            co++;
                        } else {
                            if (co < ((_.size(self.filteredData())) - 1)) {
                                co1 = co + 1;

                            } else {
                                co1 = co;
                            }
                        }
                    });

                    self.labelSubsub(self.filteredData()[co1]);
                    if (self.labelSubsub() == null) {
                        self.labelSubsub(new Node("11", "22", []));
                    }
                } else {
                    self.editMode = true;
                }
                

            });
            


        }
        
        findByCode(items: Array<Node>, newValue: string, count: number): Node{
            let self = this;
            let node : Node;
            _.find(items, function(obj: Node){
                if(!node){
                    if(obj.code == newValue){
                        node = obj;
                        count = count + 1;
                    }
                    }
                });
            return node;
        };
        
        findByName(items: Array<Node>): Node{
            let self = this;
            let node : Node;
            _.find(items, function(obj: Node){
                if(!node){
                    if(obj.name == self.curentNode().name){
                        node = obj;
                        }
                    }
                });
            return node;
        };
        removeNodeByCode(items: Array<Node>): any{
            let self = this;
            _.remove(items,function(obj: Node){
                if(obj.code == self.Value()){
                    console.log(self.Value());
                    return obj.code == self.Value();
                }else{
                    return self.removeNodeByCode(obj.childs);
                   
                }
                })
            
            };
        // remove data: return array of subsub tree
        removeData(items: Array<Node>): any {
             _.remove(items, function(obj: Node) {
                  return _.size(obj.code) < 3;
                });
            }
        deleteData():any{
            let self = this;
            self.removeNodeByCode(self.items());
            self.item1s(self.items());
            self.items([]);
            self.items(self.item1s());
            }
        alerDelete():void{
            let self= this;
            if(confirm("do you wanna delete")=== true){
                self.deleteData();
            }else{
                    alert("you didnt delete!");
            }
            }           
        resetData(): void {
            let self = this;
            self.editMode = false;
            self.curentNode(new Node("", "", []));
            self.singleSelectedCode("");
            self.selectedCode("");
            self.labelSubsub("");
            self.items([]);
            self.items(self.item1s());
        }
        init(): void {
            let self = this;
            //青森市  itemList == RemoveData()
            self.itemList = ko.observableArray([
                new Node('1', '青森市', []),
                new Node('2', '秋田市', []),
                new Node('3', '山形市', []),
                new Node('4', '福島市', []),
                new Node('5', '水戸市', []),
                new Node('6', '宇都宮市', []),
                new Node('7', '川越市', []),
                new Node('8', '熊谷市', []),
                new Node('9', '浦和市', [])
            ]);
            // data of treegrid
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
            self.currentCode = ko.observable(null);
            self.item1s = ko.observable(new Node('','',[]));
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.nameBySelectedCode = ko.observable(null);
            self.Value =  ko.observable(null);
            
            self.singleSelectedCode = ko.observable("022012");
            self.curentNode = ko.observable(new Node('022012', '青森市', []));
            self.labelSubsub = ko.observable(new Node('052019', '秋田市', []));
            self.selectedCode = ko.observable("1");

            }  
        openBDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/qmm/003/b/index.xhtml").onClosed(function() {
                //self.singleSelectedCode(nts.uk.ui.windows.setShared("singleSelectedCode"));
                nts.uk.ui.windows.setShared("singleSelectedCode", self.singleSelectedCode());
            });
        }
        openCDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/qmm/003/c/index.xhtml");
        }  
        openDDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/qmm/003/d/index.xhtml");
        }   
        openEDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/qmm/003/e/index.xhtml");
        }   
    }
    export class Node {
        code: string;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;

        }
    }
//    function OpenModalSubWindow(option?: any) {
//        let self = this;
//        nts.uk.ui.windows.sub.modal("/view/qmm/003/b/index.xhtml", option);
//        //           nts.uk.ui.windows.sub.modal("/view/qmm/003/b/test.xhtml", option).onClosed(() => {
//        //              
//        //           } );
//    }
//    function OpenModalSubWindowc(option?: any) {
//        let self = this;
//        nts.uk.ui.windows.sub.modal("/view/qmm/003/b/index.xhtml", option);
//                   nts.uk.ui.windows.sub.modal("/view/qmm/003/c/test.xhtml", option).onClosed(() => {
//        
//                   } );
//    }
//    function OpenModalSubWindowd(option?: any) {
//        let self = this;
//        nts.uk.ui.windows.sub.modal("/view/qmm/003/b/index.xhtml", option);
//                   nts.uk.ui.windows.sub.modal("/view/qmm/003/d/test.xhtml", option).onClosed(() => {
//                        
//                       
//                   } );
//    };
//    function OpenModalSubWindowe(option?: any) {
//        let self = this;
//        nts.uk.ui.windows.sub.modal("/view/qmm/003/b/index.xhtml", option);
//                   nts.uk.ui.windows.sub.modal("/view/qmm/003/e/test.xhtml", option).onClosed(() => {
//                     
//                   } );
//    };
};
