module nts.uk.com.view.kal004.b.viewmodel {


    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        selectMonth: KnockoutObservable<boolean>;
        strSelected: KnockoutObservable<any>;
        endSelected: KnockoutObservable<any>;
        txtDay: KnockoutObservable<string>;
        txtMonth: KnockoutObservable<string>;
        
        
        //Combo Box
        strListSpecifiedMonth: KnockoutObservableArray<any>;
        strMonthSelected: KnockoutObservable<any>;
        endListSpecifiedMonth: KnockoutObservableArray<any>;
        endMonthSelected: KnockoutObservable<any>;
        
        constructor() {
            var self = this;
            self.enable = ko.observable(true);
            self.selectMonth = ko.observable(true);
            self.strSelected = ko.observable(1);
            self.endSelected = ko.observable(0);
            self.txtDay = ko.observable(resource.getText('KAL004_32'));
            self.txtMonth = ko.observable(resource.getText('KAL004_32'));
            
            // combo box
            self.strMonthSelected = ko.observable(0);
            self.endMonthSelected = ko.observable(1);
            self.strListSpecifiedMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            self.endListSpecifiedMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            console.log(self.endMonthSelected());
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
        test(): void {
            nts.uk.ui.windows.sub.modal("../004/b/index.xhtml").onClosed(() => {
                console.log("success!");
            });  
        }

    }
    export interface ExtractionRangeDto{
        extractionId: string;
        extractionRange: number;
        strSpecify: number;
        strPreviousDay: number;
        strMakeToDay: number;
        strDay: number;
        strPreviousMonth: number;
        strCurrentMonth: number;
        strMonth: number;
        endSpecify: number;
        endPreviousDay: number;
        endMakeToDay: number;
        endDay: number;
        endPreviousMonth: number;
        endCurrentMonth: number;
        endMonth: number;  
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
    

