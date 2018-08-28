module nts.uk.com.view.qmm005.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.com.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        
        //D4_19
        guaranteedBaseDate:KnockoutObservableArray<model.ItemModel>; 
        guaranteedBaseDateSelectedCode:KnockoutObservable<number>;
        //D3_8
        refeDate:KnockoutObservableArray<model.ItemModel>;       
        refeDateSelectedCode:KnockoutObservable<number>;
        
        //D3_11
        refeMonth:KnockoutObservableArray<model.ItemModel>;
        refeMonthSelectedCode:KnockoutObservable<number>;
        //D3_17
        processMonth:KnockoutObservableArray<model.ItemModel>;
        processMonthSelectedCode:KnockoutObservable<number>;
        
        //D3_20
        disposalDay:KnockoutObservableArray<model.ItemModel>;
        disposalDaySelectedCode:KnockoutObservable<number>;
        
       
        
        simpleValue: KnockoutObservable<string>;
        //D3_3
        datePayment:KnockoutObservableArray<model.ItemModel>;
        datePaymentSelectedCode: KnockoutObservable<string>;
        
        isEnable: KnockoutObservable<boolean>;
        
        
        
        numberOfWorkingDays:KnockoutObservable<number>;
        selectedId:KnockoutObservable<number>;
        
        
       
        enableChecckBox:KnockoutObservable<boolean>;
        
        timeCloseDate:KnockoutObservableArray<model.ItemModel>;
        
        labelRequired:KnockoutObservable<boolean>;
        
        //D4_3
        printingMonth:KnockoutObservableArray<model.ItemModel>;
        printingMonthSelectedCode: KnockoutObservable<string>;
        
        //D4_9
        monthsCollected:KnockoutObservableArray<model.ItemModel>;
        monthsCollectedSelectedCode: KnockoutObservable<string>;
        
        //D4_15
        guaranteedBaseYear:KnockoutObservableArray<model.ItemModel>;
        guaranteedBaseYearSelectedCode: KnockoutObservable<string>;
        
        //D4_17
        guaranteedBaseMonth:KnockoutObservableArray<model.ItemModel>;
        guaranteedBaseMonthSelectedCode: KnockoutObservable<string>;
        
        
        //D4_23
        employmentInsuranceStandardMonth:KnockoutObservableArray<model.ItemModel>;
        employmentInsuranceStandardMonthSelectedCode: KnockoutObservable<string>;
        
        
        //D4_25
        employmentInsuranceStandardDate:KnockoutObservableArray<model.ItemModel>;
        employmentInsuranceStandardDateSelectedCode: KnockoutObservable<string>;
        
         //D4_34
        timeBaseYear:KnockoutObservableArray<model.ItemModel>;
        timeBaseYearSelectedCode: KnockoutObservable<string>;
        
         //D4_36
        timeClosingStandardMonth:KnockoutObservableArray<model.ItemModel>;
        timeClosingStandardMonthSelectedCode: KnockoutObservable<string>;
        
         //D4_38
        timeReferenceStandardDay:KnockoutObservableArray<model.ItemModel>;
        timeReferenceStandardDaySelectedCode: KnockoutObservable<string>;
        
        //D4_42
        incomeTaxBaseYear:KnockoutObservableArray<model.ItemModel>;
        incomeTaxBaseYearSelectedCode: KnockoutObservable<string>;
        
        //D4_44
        incomeTaxBaseMonth:KnockoutObservableArray<model.ItemModel>;
        incomeTaxBaseMonthSelectedCode: KnockoutObservable<string>;
        
        //D4_46
        incomeTaxBaseDate:KnockoutObservableArray<model.ItemModel>;
        incomeTaxBaseDateSelectedCode: KnockoutObservable<string>;
        
        constructor(){
            var self=this;
            self.numberOfWorkingDays=ko.observable(20);
            self.simpleValue=ko.observable(getText('QMM005_48'));


            
            //D4_42
            self.incomeTaxBaseYear=ko.observableArray([
                new model.ItemModel(model.YearSelectClassification.LAST_YEAR,'LAST_YEAR'),  
                new model.ItemModel(model.YearSelectClassification.THIS_YEAR,'THIS_YEAR'),
                new model.ItemModel(model.YearSelectClassification.AFTER_YEAR,'AFTER_YEAR'),
                new model.ItemModel(model.YearSelectClassification.LEAP_YEAR,'LEAP_YEAR')
            ]);
            self.incomeTaxBaseYearSelectedCode=ko.observable(0);
            
            //D4_44
            self.incomeTaxBaseMonth=ko.observableArray([
                new model.ItemModel(model.MonthSelectionSegment.JANUARY,'JANUARY'),
                new model.ItemModel(model.MonthSelectionSegment.FEBRUARY,'FEBRUARY'),
                new model.ItemModel(model.MonthSelectionSegment.MARCH,'MARCH'),
                new model.ItemModel(model.MonthSelectionSegment.APRIL,'APRIL'),
                new model.ItemModel(model.MonthSelectionSegment.MAY,'MAY'),
                new model.ItemModel(model.MonthSelectionSegment.JUNE,'JUNE'),
                new model.ItemModel(model.MonthSelectionSegment.JULY,'JULY'),
                new model.ItemModel(model.MonthSelectionSegment.AUGUST,'AUGUST'),
                new model.ItemModel(model.MonthSelectionSegment.SEPTEMBER,'SEPTEMBER'),
                new model.ItemModel(model.MonthSelectionSegment.OCTOBER,'OCTOBER'),
                new model.ItemModel(model.MonthSelectionSegment.NOVEMBER,'NOVEMBER'),
                new model.ItemModel(model.MonthSelectionSegment.DECEMBER,'DECEMBER')
            ]);
            self.incomeTaxBaseMonthSelectedCode=ko.observable(0);
            
            //D4_46
            self.incomeTaxBaseDate=ko.observableArray([]);
            self.incomeTaxBaseDateSelectedCode=ko.observable(0);
            
            
            
            //D4_34
            self.timeBaseYear=ko.observableArray([
                new model.ItemModel(model.YearSelectClassification.LAST_YEAR,'LAST_YEAR'),  
                new model.ItemModel(model.YearSelectClassification.THIS_YEAR,'THIS_YEAR'),
                new model.ItemModel(model.YearSelectClassification.AFTER_YEAR,'AFTER_YEAR'),
                new model.ItemModel(model.YearSelectClassification.LEAP_YEAR,'LEAP_YEAR')
            ]);
            self.timeBaseYearSelectedCode=ko.observable(0);
            
            
            //D4_36
            self.timeClosingStandardMonth=ko.observableArray([
                new model.ItemModel(model.SocialInsuColleMonth.BEFORE_MONTH,'BEFORE_MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.LAST_MONTH,'LAST_MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.MONTH,'MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.NEXT_MONTH,'NEXT_MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.SECOND_FOLLOWING_MONTH,'SECOND_FOLLOWING_MONTH'),
            ]);
            self.timeClosingStandardMonthSelectedCode=ko.observable(0);
            
            //D4_38
            self.timeReferenceStandardDay=ko.observableArray([]);
            self.timeReferenceStandardDaySelectedCode=ko.observable(0);
            
            //D4_19
            self.guaranteedBaseDate=ko.observableArray([]);
             self.guaranteedBaseDateSelectedCode=ko.observable(0);
            
            
            //D3_3
            self.disposalDay=ko.observableArray([]);
            self.disposalDaySelectedCode=ko.observable(0);
            
            //D4_23
            self.employmentInsuranceStandardDate=ko.observableArray([]);
            self.employmentInsuranceStandardDateSelectedCode=ko.observable(0);
            
            
            //D4_25
            self.employmentInsuranceStandardMonth=ko.observableArray([
                new model.ItemModel(model.MonthSelectionSegment.JANUARY,'JANUARY'),
                new model.ItemModel(model.MonthSelectionSegment.FEBRUARY,'FEBRUARY'),
                new model.ItemModel(model.MonthSelectionSegment.MARCH,'MARCH'),
                new model.ItemModel(model.MonthSelectionSegment.APRIL,'APRIL'),
                new model.ItemModel(model.MonthSelectionSegment.MAY,'MAY'),
                new model.ItemModel(model.MonthSelectionSegment.JUNE,'JUNE'),
                new model.ItemModel(model.MonthSelectionSegment.JULY,'JULY'),
                new model.ItemModel(model.MonthSelectionSegment.AUGUST,'AUGUST'),
                new model.ItemModel(model.MonthSelectionSegment.SEPTEMBER,'SEPTEMBER'),
                new model.ItemModel(model.MonthSelectionSegment.OCTOBER,'OCTOBER'),
                new model.ItemModel(model.MonthSelectionSegment.NOVEMBER,'NOVEMBER'),
                new model.ItemModel(model.MonthSelectionSegment.DECEMBER,'DECEMBER')
            ]);
            self.employmentInsuranceStandardMonthSelectedCode=ko.observable(0);
            
            //D3_20
            self.datePayment=ko.observableArray([]);
            self.datePaymentSelectedCode=ko.observable(0);
            
            //D3_17
            self.timeCloseDate=ko.observableArray([
                new model.ItemModel(model.PreviousMonthClassification.THIS_MONTH,'THIS_MONTH'),
                new model.ItemModel(model.PreviousMonthClassification.LAST_MONTH,'LAST_MONTH')
            ]);
            self.processMonthSelectedCode=ko.observable(0);
            
            //D4_3
            self.printingMonth=ko.observableArray([
                new model.ItemModel(model.PreviousMonthClassification.THIS_MONTH,'THIS_MONTH'),
                new model.ItemModel(model.PreviousMonthClassification.LAST_MONTH,'LAST_MONTH')
            ]);
            self.printingMonthSelectedCode=ko.observable(0);
            
            //D3_20
            self.disposalDay=ko.observableArray([]);
            self.disposalDaySelectedCode=ko.observable(0);
            
            
            //D4_30
            self.processMonth=ko.observableArray([
                new model.ItemModel(model.TimeCloseDateClassification.SAME_DATE,'SAME_DATE'),
                new model.ItemModel(model.TimeCloseDateClassification.APART_FROM_DATE,'APART_FROM_DATE')
            ]);
            self.processMonthSelectedCode=ko.observable(0);
            
            //D4_9
            self.monthsCollected=ko.observableArray([
               new model.ItemModel(model.SocialInsuColleMonth.BEFORE_MONTH,'BEFORE_MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.LAST_MONTH,'LAST_MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.MONTH,'MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.NEXT_MONTH,'NEXT_MONTH'),
                new model.ItemModel(model.SocialInsuColleMonth.SECOND_FOLLOWING_MONTH,'SECOND_FOLLOWING_MONTH'),
                                 
            ]);
            self.monthsCollectedSelectedCode=ko.observable(0);
            
            //D4_15
            self.guaranteedBaseYear=ko.observableArray([
                new model.ItemModel(model.YearSelectClassification.LAST_YEAR,'LAST_YEAR'),  
                new model.ItemModel(model.YearSelectClassification.THIS_YEAR,'THIS_YEAR'),
                new model.ItemModel(model.YearSelectClassification.AFTER_YEAR,'AFTER_YEAR'),
                new model.ItemModel(model.YearSelectClassification.LEAP_YEAR,'LEAP_YEAR')
                            
                
            ]);
            self.guaranteedBaseYearSelectedCode=ko.observable(0);
            
            
            //D3_11
            self.refeDate=ko.observableArray([]);
            self.refeDateSelectedCode=ko.observable(0);
            
            //D3_8
            
            self.refeMonth=ko.observableArray([
                new model.ItemModel(model.PreviousMonthClassification.THIS_MONTH,'THIS_MONTH'),
                new model.ItemModel(model.PreviousMonthClassification.LAST_MONTH,'LAST_MONTH')
            ]);
            self.refeMonthSelectedCode=ko.observable(0);
            
            //D3_17
            self.guaranteedBaseMonth=ko.observableArray([
                new model.ItemModel(model.InsuranceStanMonthClassSification.LAST_MONTH,'LAST_MONTH'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.MONTH,'MONTH'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.JANUARY,'JANUARY'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.FEBRUARY,'FEBRUARY'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.MARCH,'MARCH'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.APRIL,'APRIL'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.MAY,'MAY'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.JUNE,'JUNE'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.JULY,'JULY'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.AUGUST,'AUGUST'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.SEPTEMBER,'SEPTEMBER'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.OCTOBER,'OCTOBER'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.NOVEMBER,'NOVEMBER'),
                new model.ItemModel(model.InsuranceStanMonthClassSification.DECEMBER,'DECEMBER')
                
            ]);
            self.guaranteedBaseMonthSelectedCode=ko.observable(0);
            
//            let i = 1;
//            for (let data in model.DateSelectClassification) {
//                if (isNaN(data)) continue;
//                self.itemList.push(new model.ItemModel(data, i + '日'))
//                i++;
//            }
            
            self.pushDaytoList(self.disposalDay,model.DateSelectClassification);
            self.pushDaytoList(self.datePayment,model.DateSelectClassification);
            self.pushDaytoList(self.refeDate,model.DateSelectClassification);
             self.pushDaytoList(self.employmentInsuranceStandardDate,model.DateSelectClassification);
            self.pushDaytoList(self.timeReferenceStandardDay,model.DateSelectClassification);
            self.pushDaytoList(self.guaranteedBaseDate,model.DateSelectClassification);
            self.pushDaytoList(self.incomeTaxBaseDate,model.DateSelectClassification);
            
            
            
            
            self.selectedId=ko.observable(0);

            self.labelRequired=ko.observable(true);
            self.isEnable=ko.observable(true);
            self.enableChecckBox=ko.observable(true);

            
        
        }
        
        
        pushDaytoList(itemList:Array<model.ItemModel>,codeEnum:enum):void{
            let items=itemList;
            let code=codeEnum;
            let 
            let i = 1;
            for (let data in code) {
                if (isNaN(data)) continue;
                items.push(new model.ItemModel(data, i + '日'));
                i++;
            }
        }
    
        saveCharacterSetting():void{}
    
        cancelCharacterSetting():void{}
        
        



        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }

    }
    
   
    
}
