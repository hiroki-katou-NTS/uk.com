module nts.uk.com.view.cmf003.b.viewmodel {
    import model = cmf003.share.model;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);
        
        //gridlist
        items: KnockoutObservableArray<CategoryModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        
        //Date Ranger Picker : type fullDay
        dayEnable: KnockoutObservable<boolean>;
        dayRequired: KnockoutObservable<boolean>;
        dayValue: KnockoutObservable<any>;
        dayStartDateString: KnockoutObservable<string>;
        dayEndDateString: KnockoutObservable<string>;
        
        //Date Ranger Picker : type monthyear
        monthEnable: KnockoutObservable<boolean>;
        monthRequired: KnockoutObservable<boolean>;
        monthValue: KnockoutObservable<any>;
        monthStartDateString: KnockoutObservable<string>;
        monthEndDateString: KnockoutObservable<string>;
        
        //Date Ranger Picker : type year
        yearEnable: KnockoutObservable<boolean>;
        yearRequired: KnockoutObservable<boolean>;
        yearValue: KnockoutObservable<any>;
        yearStartDateString: KnockoutObservable<string>;
        yearEndDateString: KnockoutObservable<string>;
        
        constructor() {
            var self = this;

            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }
            ];
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
            
            //gridlist
            this.items = ko.observableArray([]);
            
            for(let i = 1; i < 100; i++) {
                this.items.push(new CategoryModel('00' + i, '基本給', "基本給 ", "基本給"));
            }
 
            this.columns = ko.observableArray([
                { headerText: '', key: 'code', width: 100, hidden: true },
                { headerText: getText('CMF003_30'), key: 'name', width: 350 },
                { headerText: getText('CMF003_31'), key: 'period', width: 80 },
                { headerText: getText('CMF003_32'), key: 'range', width: 80 }
            ]);
            
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            
            //Date Ranger Picker : type full day
            self.dayEnable = ko.observable(true);
            self.dayRequired = ko.observable(true);
            self.dayStartDateString = ko.observable("");
            self.dayEndDateString = ko.observable("");
            self.dayValue = ko.observable({});
            
            self.dayStartDateString.subscribe(function(value){
                self.dayValue().startDate = value;
                self.dayValue.valueHasMutated();        
            });
            
            self.dayEndDateString.subscribe(function(value){
                self.dayValue().endDate = value;
                self.dayValue.valueHasMutated();      
            });
            
            //Date Ranger Picker : type month
            self.monthEnable = ko.observable(true);
            self.monthRequired = ko.observable(true);
            self.monthStartDateString = ko.observable("");
            self.monthEndDateString = ko.observable("");
            self.monthValue = ko.observable({});
            
            self.monthStartDateString.subscribe(function(value){
                self.monthValue().startDate = value;
                self.monthValue.valueHasMutated();        
            });
            
            self.monthEndDateString.subscribe(function(value){
                self.monthValue().endDate = value;
                self.monthValue.valueHasMutated();      
            });
            
            //Date Ranger Picker : type year
            self.yearEnable = ko.observable(true);
            self.yearRequired = ko.observable(true);
            self.yearStartDateString = ko.observable("");
            self.yearEndDateString = ko.observable("");
            self.yearValue = ko.observable({});
            
            self.yearStartDateString.subscribe(function(value){
                self.yearValue().startDate = value;
                self.yearValue.valueHasMutated();        
            });
            
            self.yearEndDateString.subscribe(function(value){
                self.yearValue().endDate = value;
                self.yearValue.valueHasMutated();      
            });
        }
    }   
}

class CategoryModel {
    code: string;
    name: string;
    period: string;
    range: string;
    
    constructor(code: string, name: string, period: string, range: string) {
        this.code  = code;
        this.name = name;
        this.period = period;
        this.range   = range;
    }
}