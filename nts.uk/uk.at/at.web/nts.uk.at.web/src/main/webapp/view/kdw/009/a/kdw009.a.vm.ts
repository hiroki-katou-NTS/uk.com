module nts.uk.at.view.kdw009.a.viewmodel {
    
    export class ScreenModel {
        lstBusinessType: KnockoutObservableArray<BusinessType>;
        gridListColumns: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;
        selectedOption: KnockoutObservable<BusinessType>;
        selectedName: KnockoutObservable<string>;
        codeObject: KnockoutObservable<string>;
        check: KnockoutObservable<boolean>;
        checkUpdate: KnockoutObservable<boolean>;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW009_6"), key: 'businessTypeCode', width: 100 },
                { headerText: nts.uk.resource.getText("KDW009_7"), key: 'businessTypeName', width: 200 }
            ]);
            self.lstBusinessType = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.selectedName = ko.observable("");
            self.selectedOption = ko.observable(null);
            self.check = ko.observable(false);
            self.codeObject = ko.observable("");
            self.checkUpdate = ko.observable(true);
            self.selectedCode.subscribe((businessTypeCode) => {
                if (businessTypeCode) {
                    let foundItem = _.find(self.lstBusinessType(), (item: BusinessType) => {
                        return item.businessTypeCode == businessTypeCode;
                    });
                    self.selectedOption(foundItem);
                    self.selectedName(self.selectedOption().businessTypeName);
                    self.codeObject(self.selectedOption().businessTypeCode)
                    self.check(false);
                }
            });
            self.startPage();
        }

        /** get data number "value" in list **/
        getData(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done((lstData: Array<viewmodel.BusinessType>) => {
                let sortedData = _.orderBy(lstData, ['businessTypeCode'], ['asc']);
                self.lstBusinessType(sortedData);
                dfd.resolve();
            }).fail(function(error){
                    dfd.reject();
                    alert(error.message);
                }) 
              return dfd.promise();      
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let array=[];
            let list=[];
            self.getData().done(function(){
                if(self.lstBusinessType().length == 0){
                self.newMode();
                return;
                }
                else{
                    self.selectedCode(self.lstBusinessType()[0].businessTypeCode.toString());
                }
            });
            
//            service.getAll().done((lstData: Array<viewmodel.BusinessType>) => {
//                if(lstData.length==0){
//                    self.newMode();
//                    self.lstBusinessType(lstData);
//                    dfd.resolve();
//                    return;
//                }
//                let sortedData = _.orderBy(lstData, ['businessTypeCode'], ['asc']);
//                self.lstBusinessType(sortedData);
//                self.selectedCode(self.lstBusinessType()[0].businessTypeCode.toString());
//                
//                dfd.resolve();
//            }).fail(function(error){
//                    dfd.reject();
//                    alert(error.message);
//                })        
            return dfd.promise();
        }  
        
        /** update or insert data when click button register **/
        register() {
            let self = this;
            let code = "";
            let foundItem = _.find(self.lstBusinessType(), (item: BusinessType) => {
                        return item.businessTypeCode == self.codeObject();
                    });
            let updateOption = new BusinessType(self.selectedCode(), self.selectedName());  
            code = self.codeObject();
            if(self.checkUpdate() == true){
                service.update(updateOption).done(function(){
                    self.getData().done(function(){
                        self.selectedCode(code);
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }); 
                    });
                });
            }
            else{
                if(self.codeObject().length<10){
                    do{
                        self.codeObject("0" + self.codeObject());
                    }while(self.codeObject().length<10);
                }
                code = self.codeObject();
                self.selectedOption(null);
                let obj = new BusinessType(self.codeObject(), self.selectedName())
                service.insert(obj).done(function(){
                    self.getData().done(function(){
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        self.selectedCode(code);
                    });
                }).fail(function(res){
                    $('#inpCode').ntsError('set', res.messageId);
                });
            }            
        } 
        
        newMode(){
            let self = this;
            self.check(true);
            self.checkUpdate(false);
            self.selectedCode("");
            self.codeObject("");
            self.selectedName("");
            $("#inpCode").focus();   
        }
        
        remove(){
            let self = this;
            let count = 0;
            for (let i = 0; i <= self.lstBusinessType().length; i++){
                if(self.lstBusinessType()[i].businessTypeCode == self.selectedCode()){
                    count = i;
                    break;
                }
            }
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => { 
                service.remove(self.selectedOption()).done(function(){
                    self.getData().done(function(){
                        if(self.lstBusinessType().length==0){
                            self.newMode();
                            return;
                        }
                        // delete the last item
                        if(count == ((self.lstBusinessType().length))){
                            self.selectedCode(self.lstBusinessType()[count-1].businessTypeCode);
                            return;
                        }
                        if(count == 0 ){
                            self.selectedCode(self.lstBusinessType()[0].businessTypeCode);
                            return;
                        }
                        else if(count > 0 && count < self.lstBusinessType().length){
                            self.selectedCode(self.lstBusinessType()[count].businessTypeCode);    
                            return;
                        }
                    });
                    
                });
            }).ifCancel(() => { 
            }); 
            
            
        }
    }
    export class BusinessType{
        businessTypeCode: string;
        businessTypeName: string;  
        constructor(businessTypeCode: string, businessTypeName: string){
            this.businessTypeCode = businessTypeCode;
            this.businessTypeName =businessTypeName;
        }
    }
}




