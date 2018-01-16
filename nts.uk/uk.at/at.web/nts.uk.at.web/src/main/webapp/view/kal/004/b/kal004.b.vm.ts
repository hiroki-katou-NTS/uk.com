module nts.uk.com.view.kal004.b.viewmodel {


    export class ScreenModel {
        enable: boolean;
        txtDay: KnockoutObservable<string>;
        txtStrMonth: KnockoutObservable<string>;
        txtEndMonth: KnockoutObservable<string>;
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;
        
        //start date
        strSelected: KnockoutObservable<number>;
        strDay: KnockoutObservable<number>;
        strMonth: KnockoutObservable<number>;
        
        //End date
        endSelected: KnockoutObservable<number>;
        endDay: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        
        getParam: ExtractionDailyDto;
        
        constructor() {
            var self = this;
            self.enable = true;
            self.txtDay = ko.observable(resource.getText('KAL004_32'));
            self.txtStrMonth = ko.observable(resource.getText('KAL004_37'));
            self.txtEndMonth = ko.observable(resource.getText('KAL004_43'));
            self.strComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            self.endComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            
            self.getParam = nts.uk.ui.windows.getShared("extractionDailyDto");
            //start date
            self.strSelected = ko.observable(self.getParam.strSpecify);
            self.strDay = ko.observable(self.getParam.strDay);
            self.strMonth = ko.observable(self.getParam.strMonth);
            //End Date
            self.endSelected = ko.observable(self.getParam.endSpecify);
            self.endDay = ko.observable(self.getParam.endDay);
            self.endMonth = ko.observable(self.getParam.endMonth);
            
            //console.log(self.getParam);
        }
        Decide(): any {
            var self = this;
            if(self.strSelected()==0){
                $(".input-str").trigger("validate");
            }
            if(self.endSelected()==0){
                $(".input-end").trigger("validate");
            }
            if($(".nts-input").ntsError("hasError")){
                return;    
            }else if(self.checkPeriod()){
                let dataSetShare = self.getData();
                nts.uk.ui.windows.setShared("extractionDaily", dataSetShare);
                self.cancel_Dialog();
            }
        }
        
        getData(): any{
            var self = this;
            var extractionId = self.getParam.extractionId;
            var extractionRange = self.getParam.extractionRange;
            var strSpecify = self.strSelected(); 
            var strPreviousDay = null;
            var strMakeToDay = null;
            var strDay = null;
            var strPreviousMonth = null ;
            var strCurrentMonth = null;
            var strMonth = null;
            var endSpecify = self.endSelected();
            var endPreviousDay = null;
            var endMakeToDay = null;
            var endDay = null;
            var endPreviousMonth = null;
            var endCurrentMonth = null;
            var endMonth = null;
            
            //start
            if(self.strSelected()==0){
                strPreviousDay = 0;
                strDay = self.strDay();
                if(strDay==0){
                    strMakeToDay = 1;
                }else{
                    strMakeToDay = 0;
                }  
            }else{
                strPreviousMonth = 0 ;
                strMonth = self.strMonth();
                if(strMonth == 0){
                    strCurrentMonth = 1;
                }else{
                    strCurrentMonth = 0;
                } 
            }
            //end
            if(self.endSelected()==0){
                endPreviousDay = 0;
                endDay = self.endDay();
                if(endDay==0){
                    endMakeToDay = 1;
                }else{
                    endMakeToDay = 0;
                }  
            }else{
                endPreviousMonth = 0 ;
                endMonth = self.endMonth();
                if(endMonth == 0){
                    endCurrentMonth = 1;
                }else{
                    endCurrentMonth = 0;
                } 
            }
            
            return {extractionId: extractionId,
                extractionRange: extractionRange,
                strSpecify: strSpecify,
                strPreviousDay: strPreviousDay,
                strMakeToDay: strMakeToDay,
                strDay: strDay,
                strPreviousMonth: strPreviousMonth,
                strCurrentMonth: strCurrentMonth,
                strMonth: strMonth,
                endSpecify: endSpecify,
                endPreviousDay: endPreviousDay,
                endMakeToDay: endMakeToDay,
                endDay: endDay,
                endPreviousMonth: endPreviousMonth,
                endCurrentMonth: endCurrentMonth,
                endMonth: endMonth
            };
        }
        checkPeriod(): boolean {
            var self = this;
            if(self.strSelected()==0 && self.endSelected()==1){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_815"});
                return false;    
            }else if(self.strSelected() == 0 && (self.strDay() < self.endDay())){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_812"});
                return false;
            }else if(self.strSelected() == 1 && self.endSelected()==1 && (self.strMonth() < self.endMonth())){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_812"});
                return false;    
            }else{
                return true;    
            }
        }
       
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
    }
   export interface ExtractionDailyDto {
        extractionId: string;
        extractionRange: number;
        strSpecify: number;
        strPreviousDay?: number;
        strMakeToDay?: number;
        strDay?: number;
        strPreviousMonth?: number;
        strCurrentMonth?: number;
        strMonth?: number;
        endSpecify: number;
        endPreviousDay?: number;
        endMakeToDay?: number;
        endDay?: number;
        endPreviousMonth?: number;
        endCurrentMonth?: number;
        endMonth?: number;
    }
}


class items {
    value: string;
    name: string;
    description: string;

    constructor(value: string, name: string, description: string) {
        var self = this;
        self.value = value;
        self.name = name;
        self.description = description;
    }
}
    

