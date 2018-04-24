module nts.uk.com.view.cmf003.c {
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            
            // swapList
           itemsSwap: KnockoutObservableArray<ItemModel>;
           itemsSwapLeft: KnockoutObservableArray<ItemModel>;
           columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
           currentCodeListSwap: KnockoutObservableArray<any>;
           
           // comboBox
           itemList: KnockoutObservableArray<ItemModelCombo>;
           selectedCode: KnockoutObservable<string>;
           currentItem: KnockoutObservable<ItemModelCombo>;
           isEnable: KnockoutObservable<boolean>;
           isEditable: KnockoutObservable<boolean>;
           selected_comobox: string = getText("CMF003_59");
           constructor() {
               var self = this;
                
               self.itemList = ko.observableArray([
                    new ItemModelCombo('0',self.selected_comobox)
                    
                ]);
               service.getSysTypes().done(function(data: Array<any>) {
                    if (data && data.length) {
                        _.forOwn(data, function(index) {
                            self.itemList.push(new ItemModelCombo(index.type,index.name));
                          });
                         
                    } else {
                       
                    }
                   
                }).fail(function(error) {
                    alertError(error);
                   
                }).always(() => {
                    
                });
                
                
                    self.selectedCode = ko.observable('0');
                    self.isEnable = ko.observable(true);
                    self.isEditable = ko.observable(true);
                   
                    self.itemsSwap = ko.observableArray([]);
                    self.selectedCode.subscribe(value=>{
                    self.currentItem = _.find(self.itemList(), a => a.code === value);
                    
                   console.log(self.selectedCode());
                   service.getConditionList(self.selectedCode()).done(function(data: Array<any>) {
                    
                       console.log(data);
                       
                   }).fail(function(error) {
                        alertError(error);
                   }).always(() => {
                       
                        _.defer(() => {
                            $("#grd_Condition tr:first-child").focus();
                        });
                    });
                   
                   
                   
               })
               var array = [];
               for (var i = 0; i < 10000; i++) {
                   array.push(new ItemModel(i, '基本給', "description","period","range"));
               }
               self.itemsSwap(array);
                
               self.columns = ko.observableArray([
                   { headerText: 'コード', key: 'code', width: 70 },
                   { headerText: '名称', key: 'name', width: 250 }
               ]);
    
               self.currentCodeListSwap = ko.observableArray([]);
               self.itemsSwapLeft = self.currentCodeListSwap;
           }
       
           remove(){
               self.itemsSwap.shift();            
           }
            
            closeUp() {
                close();
            }
            
            submit() {
                let self = this;
                setShared("result",self.currentCodeListSwap());
                setShared("currentItem",self.currentItem);
                close();
            }
            
        }
    }

        class ItemModel {
           code: number;
           name: string;
           period: string;
           range: string;
           constructor(code: number, name: string, period: string, range: string) {
               this.code = code;
               this.name = name;
               this.period = period;
               this.range = range;
           }
       }

        class ItemModelCombo {
            code: string;
            name: string;
        
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    
       
    
}