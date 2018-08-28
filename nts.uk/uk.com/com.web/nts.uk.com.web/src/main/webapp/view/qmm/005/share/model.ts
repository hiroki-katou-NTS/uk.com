module nts.uk.com.view.qmm005.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    
    export enum MonthSelectionSegment{
        JANUARY=1,
        FEBRUARY=2,
        MARCH=3,
        APRIL=4,
        MAY=5,
        JUNE=6,
        JULY=7,
        AUGUST=8,
        SEPTEMBER=9,
        OCTOBER=10,
        NOVEMBER=11,
        DECEMBER=12
    }
    
    
    export enum InsuranceStanMonthClassSification{
        LAST_MONTH=0,
        MONTH=1,
        JANUARY=2,
        FEBRUARY=3,
        MARCH=4,
        APRIL=5,
        MAY=6,
        JUNE=7,
        JULY=8,
        AUGUST=9,
        SEPTEMBER=10,
        OCTOBER=11,
        NOVEMBER=12,
        DECEMBER=13
    }
    
    
    export enum YearSelectClassification{
        THIS_YEAR=1,
        AFTER_YEAR=2,
        LEAP_YEAR=3,
        LAST_YEAR=0
    }
    
    export enum TimeCloseDateClassification{
        SAME_DATE=0,
        APART_FROM_DATE=1    
    }
    
    export enum PreviousMonthClassification{
         THIS_MONTH=0,
         LAST_MONTH=1  
    }
    
    export enum SocialInsuColleMonth{
        LAST_MONTH=1,
        MONTH=2,
        NEXT_MONTH=3,
        SECOND_FOLLOWING_MONTH=4,
        BEFORE_MONTH=0
    }
    
    
    export enum DateSelectClassification{
        FIRST = 1,
        SECOND = 2,
        THIRD = 3,
        FOURTH = 4,
        FIFTH = 5,
        SIXTH = 6,
        SEVENTH = 7,
        EIGHTH = 8,
        NINETH = 9,
        TENTH = 10,
        ELEVENTH = 11,
        TWELFTH = 12,
        THIRTEENTH = 13,
        FOURTEENTH = 14,
        FIFTEENTH = 15,
        SIXTEENTH = 16,
        SEVENTEENTH = 17,
        EIGHTEENTH = 18,
        NINETEENTH = 19,
        TWENTIETH = 20,
        TWENTY_FIRST = 21,
        TWENTY_SECOND = 22,
        TWENTY_THIRD = 23,
        TWENTY_FOURTH = 24,
        TWENTY_FIFTH = 25,
        TWENTY_SIXTH = 26,
        TWENTY_SEVENTH = 27,
        TWENTY_EIGHTH = 28,
        TWENTY_NINTH = 29,
        THIRTIETH = 30,
        LASTDAY = 31
    }
    
    
    export class ItemModel{
        code:number;
        name:string;
        
        constructor(code:number,name:string){
            this.code=code;
            this.name=name;
        }
    }


    export interface IPaymentDateSettingList {
        targetMonth:string,
        paymentDate:string,
        payDay:string,
        employeeExtractionReferenceDate:string,
        socialInsuranceCollectionMonth:number,
        collectMinutes:string,
        specificationPrintDate:number,
        numberOfWorkingDays:number,
        day:string,
        socialInsuranceStandardDate: string,
        employmentInsuranceStandardDate: string,
        timeClosingDate: string,
        incomeTaxReferenceDate: string,
        accountingClosureDate: string
    }
    
    
    

    
    
    export class PaymentDateSettingList{
        targetMonth:string;
        paymentDate:KnockoutObservable<string>;
        payDay:string;        
        employeeExtractionReferenceDate:KnockoutObservable<string>;
        socialInsuranceCollectionMonth:KnockoutObservable<number>;
        specificationPrintDate:KnockoutObservable<number>;
        numberOfWorkingDays:KnockoutObservable<number>;
        day:string;
        collectMinutes:string;
        
        socialInsuranceStandardDate: KnockoutObservable<string>;
        employmentInsuranceStandardDate: KnockoutObservable<string>;
        timeClosingDate: KnockoutObservable<string>;
        incomeTaxReferenceDate: KnockoutObservable<string>;
        accountingClosureDate: KnockoutObservable<string>;
        
        constructor(
            targetMonth:string,
            paymentDate:string,
            payDay:string,
            employeeExtractionReferenceDate:string,
            socialInsuranceCollectionMonth:number,
            collectMinutes:string,
            specificationPrintDate:number,
            numberOfWorkingDays:number,
            day:string,
            socialInsuranceStandardDate: string,
            employmentInsuranceStandardDate: string,
            timeClosingDate: string,
            incomeTaxReferenceDate: string,
            accountingClosureDate: string

        ){
            this.targetMonth= targetMonth;
            this.paymentDate=ko.observable(paymentDate);
            this.payDay=payDay;
            this.socialInsuranceCollectionMonth=ko.observable(socialInsuranceCollectionMonth);
            this.specificationPrintDate=ko.observable(specificationPrintDate);
            this.numberOfWorkingDays=ko.observable(numberOfWorkingDays);
            this.day=day;
            this.employeeExtractionReferenceDate=ko.observable(employeeExtractionReferenceDate);
            this.collectMinutes=collectMinutes;
            this.socialInsuranceStandardDate=ko.observable(socialInsuranceStandardDate);
            this.employmentInsuranceStandardDate=ko.observable(employmentInsuranceStandardDate);
            this.timeClosingDate=ko.observable(timeClosingDate);
            this.incomeTaxReferenceDate=ko.observable(incomeTaxReferenceDate);
            this.accountingClosureDate=ko.observable(accountingClosureDate);
        }
        
        
        
    }
    

    
}