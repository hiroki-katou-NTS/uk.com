module nts.uk.com.view.cmf003.c {
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;
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
                    new ItemModelCombo('-1',self.selected_comobox)
                    
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
                
                
                    self.selectedCode = ko.observable('-1');
                    self.isEnable = ko.observable(true);
                    self.isEditable = ko.observable(true);
                   
                    self.itemsSwap = ko.observableArray([]);
                    self.selectedCode.subscribe(value=>{
                    self.currentItem = _.find(self.itemList(), a => a.code === value);
                    
                   
                   service.getConditionList(self.selectedCode()).done(function(data: Array<any>) {
                       
                      console.log("1111111");
                       self.itemsSwap(data);
                       
                   }).fail(function(error) {
                        alertError(error);
                   }).always(() => {
                       
                        _.defer(() => {
                            $("#grd_Condition tr:first-child").focus();
                        });
                    });
                   
                   
                   
               })
                
               self.columns = ko.observableArray([
                   { headerText: 'コード', key: 'categoryId', width: 70 },
                   { headerText: '名称', key: 'categoryName', width: 250 }
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
                if(self.currentCodeListSwap().length == 0){
                     alertError({ messageId: "Msg_577" });
                } else {
                    setShared("result",self.currentCodeListSwap());
                    setShared("CMF003cOutputCategorys",self.currentItem);
                    close();
                }
            }
            
        }
    }

        class ItemModel {
           schelperSystem: number;
           categoryId: string;
           categoryName: string;
           possibilitySystem: number;
           storedProcedureSpecified: number;
           timeStore: number;
           otherCompanyCls: number;
           attendanceSystem: number;
           recoveryStorageRange: number;
           paymentAvailability: number;
           storageRangeSaved: number;
           constructor(schelperSystem: number,categoryId: string, categoryName: string,possibilitySystem: number,
           storedProcedureSpecified: number, timeStore: number,otherCompanyCls: number,attendanceSystem: number,
           recoveryStorageRange: number,paymentAvailability: number ,storageRangeSaved: number) {
               this.schelperSystem = schelperSystem;
               this.categoryId = categoryId;
               this.categoryName = categoryName;
               this.possibilitySystem = possibilitySystem;
               this.storedProcedureSpecified = storedProcedureSpecified;
               this.timeStore = timeStore;
               this.otherCompanyCls = otherCompanyCls;
               this.attendanceSystem = attendanceSystem;
               this.recoveryStorageRange = recoveryStorageRange;
               this.paymentAvailability = paymentAvailability;
               this.storageRangeSaved = storageRangeSaved;
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