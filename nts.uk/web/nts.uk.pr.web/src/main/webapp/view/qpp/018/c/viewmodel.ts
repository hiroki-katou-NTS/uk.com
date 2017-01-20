module qpp018.c.viewmodel {
    export class ScreenModel {
        
        
        required: KnockoutObservable<boolean>;
        isDetailed: KnockoutObservable<boolean>;
        isTotalMonthlyTotal: KnockoutObservable<boolean>;
        isOfficeMonthlyTotal: KnockoutObservable<boolean>; 
        isDeliveryNoticeAmount: KnockoutObservable<boolean>; 
        showHealthInsuranceType: KnockoutObservableArray<HealthInsuranceType>;
        enable: KnockoutObservable<boolean>; 
        selectedCode: KnockoutObservable<string>;
        printSettingValue: KnockoutObservable<string>; 
        
        
        constructor() {
            this.required = ko.observable(true);
            this.isDetailed = ko.observable(true);
            this.isTotalMonthlyTotal= ko.observable(true);
            this.isOfficeMonthlyTotal= ko.observable(true);
            this.isDeliveryNoticeAmount= ko.observable(true);
            this.showHealthInsuranceType= ko.observableArray<HealthInsuranceType>([
                new HealthInsuranceType('1','表示する'),
                new HealthInsuranceType('2','表示しない'),
             ]);
            
            this.selectedCode=ko.observable('1');
            this.enable=ko.observable(true);
            this.printSettingValue=ko.observable('PrintSetting Value');
            
        }
        closePrintSetting(){
         // Set child value
         //   nts.uk.ui.windows.setShared("printSettingValue",this.printSettingValue,true);
            nts.uk.ui.windows.close();    
        }
        setupPrintSetting(){
            if (!(this.isDetailed()) && !(this.isTotalMonthlyTotal()) && !(this.isOfficeMonthlyTotal()) 
                    && !(this.isDeliveryNoticeAmount())) {
                alert("Something is not right");
            } else {
                nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                nts.uk.ui.windows.close();
                alert("Yep");
            }
              
        }
        
        public start(): JQueryPromise<any>{
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        
    }
    /**
     * class HealthInsuranceType
     */
    export class HealthInsuranceType{
        code: string;
        name: string;
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }       
    }

}