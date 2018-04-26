module nts.uk.at.view.kal004.g.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import share = nts.uk.at.view.kal004.share.model;
    import service = nts.uk.at.view.kal004.a.service;
    import errors = nts.uk.ui.errors;
    
    export class ScreenModel {
        textlabel: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;              
        
        //start date tab1
        strSelected: KnockoutObservable<number>;
        strDay: KnockoutObservable<number>;
        strMonth: KnockoutObservable<number>;     
        strComboMonth: KnockoutObservableArray<any>;        
        
        //End date tab1
        endSelected: KnockoutObservable<number>;
        endDay: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        endComboMonth: KnockoutObservableArray<any>;
        
        getParam: ExtractionDailyDto;
        categoryId: KnockoutObservable<number>;
        categoryName: KnockoutObservable<string>;
        
        dateSpecify: KnockoutObservableArray<any>;
        
        
        
        // tab2 
        strMonth2: KnockoutObservable<number>;     
        strComboMonth2: KnockoutObservableArray<any>;           
        endMonth2: KnockoutObservable<number>;
        endComboMonth2: KnockoutObservableArray<any>;
            
        // tab3
        yearSpecify: KnockoutObservableArray<any>;
        strSelected3: KnockoutObservable<number>;
        strMonthy3: KnockoutObservable<number>;   
        strMonth3: KnockoutObservable<number>;     
        strComboMonth3: KnockoutObservableArray<any>;           
        endMonth3: KnockoutObservable<number>;
        endComboMonth3: KnockoutObservableArray<any>;
        
        // tab4        
        strSelected4: KnockoutObservable<number>;
        strMonthy4: KnockoutObservable<number>;   
        strMonth4: KnockoutObservable<number>;     
        strComboMonth4: KnockoutObservableArray<any>;           
        endMonth4: KnockoutObservable<number>;
        endComboMonth4: KnockoutObservableArray<any>;          
        
        //tab5
        strSelected5: KnockoutObservable<number>;
        strYear5: KnockoutObservable<number>;        
        constructor() {
            var self = this;
            self.textlabel = ko.observable(nts.uk.ui.windows.getShared("categoryName"));
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL004_69'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('KAL004_70'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: getText('KAL004_71'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: getText('KAL004_72'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: getText('KAL004_73'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) },
                
            ]);
            self.selectedTab = ko.observable('tab-1'); 
            self.selectedTab.subscribe(()=>{
                    nts.uk.ui.errors.clearAll();
            });
            
            
            self.getParam = nts.uk.ui.windows.getShared("extractionDailyDto");
            self.categoryName = nts.uk.ui.windows.getShared("categoryName");
            self.categoryId = ko.observable(nts.uk.ui.windows.getShared("categoryId"));
            
            
            //start date
            self.strSelected = ko.observable(self.getParam.strSpecify);
            self.strDay = ko.observable(self.getParam.strDay);
            self.strMonth = ko.observable(self.getParam.strMonth);
            self.strComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);  
            
            //End Date
            self.endSelected = ko.observable(self.getParam.endSpecify);
            self.endDay = ko.observable(self.getParam.endDay);
            self.endMonth = ko.observable(self.getParam.endMonth);    
            self.endComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            
            
            self.dateSpecify = ko.observableArray([
                {value: 0, name: getText('KAL004_77')},
                {value: 1, name: ''}
                ]);
            
            //tab2
            self.strMonth2 = ko.observable(self.getParam.strMonth);
            self.strComboMonth2 = ko.observableArray(__viewContext.enums.SpecifiedMonth);              
            self.endMonth2 = ko.observable(self.getParam.endMonth);    
            self.endComboMonth2 = ko.observableArray(__viewContext.enums.SpecifiedMonth);             
                        
            
            //tab3:
            self.yearSpecify = ko.observableArray([
                {value: 0, name: ''},
                {value: 1, name: getText('KAL004_96')}
                ]);
            self.strSelected3 = ko.observable(self.getParam.strSpecify);
            self.strMonthy3 = ko.observable(self.getParam.strDay);
            self.strMonth3 = ko.observable(self.getParam.strMonth);
            self.strComboMonth3 = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            self.endMonth3 = ko.observable(self.getParam.endMonth);    
            self.endComboMonth3 = ko.observableArray(__viewContext.enums.SpecifiedMonth);  

            //tab4:
            self.strSelected4 = ko.observable(self.getParam.strSpecify);
            self.strMonthy4 = ko.observable(self.getParam.strDay);
            self.strMonth4 = ko.observable(self.getParam.strMonth);
            self.strComboMonth4 = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            self.endMonth4 = ko.observable(self.getParam.endMonth);    
            self.endComboMonth4 = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            
            //tab5
            self.strSelected5 = ko.observable(self.getParam.strSpecify);
            self.strYear5 = ko.observable(self.getParam.strDay);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
         

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
        submit() {
            let self = this;
            nts.uk.ui.windows.close();
        }
        closeDialog(): void {
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