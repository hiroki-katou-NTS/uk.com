module nts.uk.pr.view.qmm003.d {
    export class ScreenModel {
        items: any;
        singleSelectedCode: KnockoutObservable<any>;
        selectedCode: any;
        index: number;
        curentNode: any;
        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
        filteredData: any;
        constructor(){
            let self = this;
            self.init();
            self.singleSelectedCode.subscribe(function(newValue){
               
                });
             self.selectedCode.subscribe(function(newValue){
                console.log(self.selectedCode());
                });
           // console.log(self.selectedCode());

            }
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
        clickButton():any{
            let self = this;
            nts.uk.ui.windows.setShared('items', self.items(),true);
            nts.uk.ui.windows.close();
            
        }
        cancelButton():void{
            nts.uk.ui.windows.close();
            }
//        removeNodeByCode(items: Array<Node>): any{
//            let self = this;
//            _.remove(items,function(obj: Node){
//                if(obj.code == self.Value()){
//                    return obj.code == self.Value();
//                }else{
//                    return self.removeNodeByCode(obj.childs);
//                   
//                }
//                })
//            
//            };
        removeData1(items: Array<Node>): any {
          _.remove(items, function(obj: Node){
                return _.size(obj.code) == 1; 
            });
          }
        removeData2(items: Array<Node>): any {
          _.remove(items, function(obj: Node){
                return _.size(obj.code) == 2; 
            });
          }  
         removeData3(items: Array<Node>): any {
          _.remove(items, function(obj: Node){
                return _.size(obj.code) > 3; 
            });
          } 
        delete(items: Array<Node>): any{
            let self = this;
           _.each(self.filteredData(),function(obj: Node){
               if(_.size(obj.code)==1){
                 self.removeData1(self.filteredData()); 
               }
           });    
        
        }              
        init():void{
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
            self.curentNode = ko.observable(new Node("", "", []));
            self.index = 0;
            self.selectedCode = ko.observableArray([]);
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(),"childs"));  
            
        };
//       findByCode(items: Array<Node>, newValue: string, count: number): Node{
//            let self = this;
//            let node : Node;
//            _.find(items, function(obj: Node){
//                if(!node){
//                    if(obj.code == newValue){
//                        node = obj;
//                        count = count + 1;
//                    }
//                    }
//                });
//            return node;
//        };
//        
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
//        removeNodeByCode(items: Array<Node>): any{
//            let self = this;
//            _.remove(items,function(obj: Node){
//                if(obj.code == self.Value()){
//                    return obj.code == self.Value();
//                }else{
//                    return self.removeNodeByCode(obj.childs);
//                   
//                }
//                })
//            
//            };
//        // remove data: return array of subsub tree

//        deleteData():any{
//            let self = this;
//            self.removeNodeByCode(self.items());
//            console.log(self.items());
//            self.item1s(self.items());
//            console.log(self.item1s());
//            self.items([]);
//            self.items(self.item1s());
//            console.log(self.items());
//            }
//        alerDelete():void{
//            let self= this;
//            if(confirm("do you wanna delete")=== true){
//                self.deleteData();
//            }else{
//                    alert("you didnt delete!");
//            }
//            }           
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