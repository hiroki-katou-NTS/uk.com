module qpp018.c.viewmodel {
    export class ScreenModel {
        
        required: KnockoutObservable<boolean>;
        showHealthInsuranceType: KnockoutObservableArray<HealthInsuranceType>;
        enable: KnockoutObservable<boolean>; 
        selectedCode: KnockoutObservable<string>;
        printSettingValue: KnockoutObservable<string>;
        printSettingList: KnockoutObservable<PrintSettingList>; 
        
        constructor() {
            this.required = ko.observable(true);
            this.showHealthInsuranceType = ko.observableArray<HealthInsuranceType>([
                new HealthInsuranceType('1','表示する'),
                new HealthInsuranceType('2','表示しない'),
             ]);
            
            this.selectedCode = ko.observable('1');
            this.enable = ko.observable(true);
            this.printSettingValue = ko.observable('PrintSetting Value');
            this.printSettingList = ko.observable<PrintSettingList>();
        }
        closePrintSetting(){
         // Set child value
            nts.uk.ui.windows.setShared("printSettingValue",this.printSettingValue(),true);
            nts.uk.ui.windows.close();    
        }
        setupPrintSetting(){
            if (!(this.printSettingList().showDetail) && !(this.printSettingList().showCategoryInsuranceItem) && !(this.printSettingList().showOffice) 
                    && !(this.printSettingList().showDeliveryNoticeAmount)) {
                alert("Something is not right");
            } else {
                nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                alert("Yep");
                nts.uk.ui.windows.close();                
            }              
        }
        
        public start(): JQueryPromise<any>{
            var dfd = $.Deferred<any>();
            var self=this;
            $.when(self.loadAllCheckListPrintSetting()).done(function() {
                dfd.resolve();
            })
            return dfd.promise();
        }
        
        public loadAllCheckListPrintSetting(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var self = this;
            service.getallCheckListPrintSetting().done(function(data: service.model.CheckListPrintSetting) {
                self.printSettingList(data);
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
                dfd.reject();
            })
            return dfd.promise();
        }
        
    }
    /**
     * Class HealthInsuranceType
     */
    export class HealthInsuranceType{
        code: string;
        name: string;
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }       
    }
    
    /**
     * Class PrintSettingList
     */
    export class PrintSettingList{
        showCategoryInsuranceItem: boolean;
        showDeliveryNoticeAmount: boolean;
        showDetail: boolean;
        showOffice: boolean;
        constructor(showCategoryInsuranceItem: boolean, showDeliveryNoticeAmount: boolean, showDetail: boolean, showOffice: boolean){
            this.showCategoryInsuranceItem =  showCategoryInsuranceItem;
            this.showDeliveryNoticeAmount =  showDeliveryNoticeAmount;
            this.showDetail = showDetail;
            this.showOffice = showOffice;
        }    
    }


}