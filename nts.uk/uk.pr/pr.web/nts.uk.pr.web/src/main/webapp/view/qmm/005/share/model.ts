module nts.uk.pr.view.qmm005.share.model {
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






    export interface ISetDaySupport{
        cid:string,
        processCategoryNO:string,
        socialInsurCollecMonth:number,
        processDate:number,
        incomeTaxDate:string,
        closeDateTime:string,
        empExtraRefeDate:string,
        closureAccountingDate:string,
        socialInsurStanDate:string,
        empInsurdStanDate:string,
        payMentDate:string,
        numberWorkDay:number
    }



    export class SetDaySupport{
        cid:string;
        processCategoryNO:string;
        socialInsurCollecMonth:KnockoutObservable<number>;
        processDate:KnockoutObservable<number>;
        incomeTaxDate:KnockoutObservable<string>;
        closeDateTime:KnockoutObservable<string>;
        empExtraRefeDate:KnockoutObservable<string>;
        closureAccountingDate:KnockoutObservable<string>;
        socialInsurStanDate:KnockoutObservable<string>;
        empInsurdStanDate:KnockoutObservable<string>;
        payMentDate:KnockoutObservable<string>;
        numberWorkDay:KnockoutObservable<number>;
        constructor(params:ISetDaySupport){
            this.cid=params.cid;
            this.processCategoryNO=params.processCategoryNO;
            this.socialInsurCollecMonth=ko.observable(params.socialInsurCollecMonth);
            this.processDate=ko.observable(params.processDate);
            this.incomeTaxDate=ko.observable(params.incomeTaxDate);
            this.closeDateTime=ko.observable(params.closeDateTime);
            this.empExtraRefeDate=ko.observable(params.empExtraRefeDate);
            this.closureAccountingDate=ko.observable(params.closureAccountingDate);
            this.socialInsurStanDate=ko.observable(params.socialInsurStanDate);
            this.empInsurdStanDate=ko.observable(params.empInsurdStanDate);
            this.payMentDate=ko.observable(params.payMentDate);
            this.numberWorkDay=ko.observable(params.numberWorkDay);

        }

    }

    export enum Abolition{
        Abolition=0,
        Not_Abolition=1
    }

    export interface IProcessInfomation {
        cid: string,
        processCategoryNO: string,
        processingName: string,
        deprecatCategory: Abolition
    }


    export class ProcessInfomation{
        cid:string;
        processCategoryNO:string;
        processName:KnockoutObservable<string>;
        deprecatCategory:KnockoutObservable<Abolition>;

        constructor(params:IProcessInfomation){
            this.cid=params.cid;
            this.processCategoryNO=params.processCategoryNO;
            this.processName=ko.observable(params.processingName);
            this.deprecatCategory=ko.observable(params.deprecatCategory);
        }
    }



    export class SpecPrintYmSet{
        cid:string;
        processCategoryNO:string;
        processDate:KnockoutObservable<string>;
        printDate:KnockoutObservable<string>;
        constructor(spec:ISpecPrintYmSet){
            this.cid=spec.cid;
            this.processCategoryNO=spec.processCategoryNO;
            this.processDate=ko.observable(spec.printDate);
            this.printDate=ko.observable(spec.printDate);
        }
    }

    export interface ISpecPrintYmSet{
        cid:string
        processCategoryNO:string;
        processDate:string;
        printDate:string;
    };


    export class MonthlyPaymentDate{
        datePayment:DateSelectClassification;
        constructor(datePayment:DateSelectClassification){
            this.datePayment=datePayment;
        }
    }

    export class EmployeeExtractionReferenceDate{
        refeDate:DateSelectClassification;
        referMonth:PreviousMonthClassification;
        constructor(refeDate:DateSelectClassification, referMonth:PreviousMonthClassification){
            this.refeDate=refeDate;
            this.referMonth=referMonth
        }
    }

    export class AccountingClosureDate {
        disposalDay: DateSelectClassification;
        processMonth: PreviousMonthClassification;

        constructor(disposalDay: DateSelectClassification,
                    processMonth: PreviousMonthClassification) {
            this.disposalDay = disposalDay;
            this.processMonth = processMonth;
        }
    }


    export class DetailPrintingMonth{
        printingMonth:PreviousMonthClassification;
        constructor(printingMonth:PreviousMonthClassification){
            this.printingMonth=printingMonth;
        }
    }


    export class SalaryInsuranceCollecMonth{
        monthsCollected:SocialInsuColleMonth;
        constructor(monthsCollected:SocialInsuColleMonth){
            this.monthsCollected=monthsCollected;
        }
    }

    export class SocialInsuranceStanDate {
        baseYear: YearSelectClassification;
        baseMonth: InsuranceStanMonthClassSification;
        baseDate: DateSelectClassification;

        constructor(baseYear: YearSelectClassification,
                    baseMonth: InsuranceStanMonthClassSification,
                    baseDate: DateSelectClassification) {
            this.baseDate = baseDate;
            this.baseMonth = baseMonth;
            this.baseYear = baseYear
        }
    }

    export class CloseDate {
        timeCloseDate: TimeCloseDateClassification;
        baseYear: YearSelectClassification;
        RefeDate: DateSelectClassification;
        baseMonth: SocialInsuColleMonth;

        constructor(timeCloseDate: TimeCloseDateClassification,
                    baseYear: YearSelectClassification,
                    RefeDate: DateSelectClassification,
                    baseMonth: SocialInsuColleMonth) {
            this.timeCloseDate = timeCloseDate;
            this.baseYear = baseYear;
            this.RefeDate = RefeDate;
            this.baseMonth = baseMonth;
        }
    }

    export class IncomeTaxBaseYear {
        baseYear: YearSelectClassification;
        refeDate: DateSelectClassification;
        baseMonth: MonthSelectionSegment;

        constructor(baseYear: YearSelectClassification,
                    baseMonth: MonthSelectionSegment,
                    refeDate: DateSelectClassification) {
            this.baseYear = baseYear;
            this.baseMonth = baseMonth;
            this.refeDate = refeDate;
        }
    }


    export interface IBasicSetting{
        monthlyPaymentDate: MonthlyPaymentDate;
        employeeExtractionreferenceDate: EmployeeExtractionReferenceDate;
        accountingClosureDate: AccountingClosureDate;
        numberOfWorkingDays: number;
    }

    //基本的な設定
    export class BasicSetting {
        monthlyPaymentDate: MonthlyPaymentDate;
        employeeExtractionreferenceDate: EmployeeExtractionReferenceDate;
        accountingClosureDate: AccountingClosureDate;
        numberOfWorkingDays: number;

        constructor(param:IBasicSetting) {
            this.monthlyPaymentDate = param.monthlyPaymentDate;
            this.employeeExtractionreferenceDate = param.employeeExtractionreferenceDate;
            this.accountingClosureDate = param.accountingClosureDate;
            this.numberOfWorkingDays = param.numberOfWorkingDays;
        }
    }

    export class EmploymentInsuranceStanDate {
        refeDate: DateSelectClassification;
        baseMonth: MonthSelectionSegment;

        constructor(refeDate: DateSelectClassification,
                    baseMonth: MonthSelectionSegment) {
            this.refeDate = refeDate;
            this.baseMonth = baseMonth;
        }
    }

    export class CurrentProcessDate {
        cid: string;
        processCategoryNo: string;
        salaryCurrentProcessingDate: number;

        constructor(cid: string,
                    processCategoryNo: string,
                    salaryCurrentProcessingDate: number) {
            this.cid = cid;
            this.processCategoryNo = processCategoryNo;
            this.salaryCurrentProcessingDate = this.salaryCurrentProcessingDate;
        }

    }

    export class EmploymentTiedProcessYear {
        cid: string;
        processCategoryNo: string;
        employeeCode: Array<string>;

        constructor(cid: string,
                    processCategoryNo: string,
                    employeeCode: Array<string>) {
            this.cid = cid;
            this.processCategoryNo = processCategoryNo;
            this.employeeCode = employeeCode;
        }
    }

    export class Employment {
        cid: string;
        employmentCode: string;
        employmentName: string;

        constructor(cid: string,
                    employmentCode: string,
                    employmentName: string) {
            this.cid=cid;
            this.employmentCode=employmentCode;
            this.employmentName=employmentName;

        }
    }


    export interface IAdvancedSetting{
        closeDate:CloseDate;
        incomeTaxBaseYear:IncomeTaxBaseYear;
        detailPrintingMonth:DetailPrintingMonth;
        socialInsuranceStanDate:SocialInsuranceStanDate;
        salaryInsuranceCollecMonth:SalaryInsuranceCollecMonth;
        employmentInsuranceStanDate:EmploymentInsuranceStanDate;
    }

    //高度な設定
    export class AdvancedSetting{
        closeDate:CloseDate;
        incomeTaxBaseYear:IncomeTaxBaseYear;
        detailPrintingMonth:DetailPrintingMonth;
        socialInsuranceStanDate:SocialInsuranceStanDate;
        salaryInsuranceCollecMonth:SalaryInsuranceCollecMonth;
        employmentInsuranceStanDate:EmploymentInsuranceStanDate;
        constructor(param:IAdvancedSetting){
            this.closeDate=param.closeDate;
            this.incomeTaxBaseYear=param.incomeTaxBaseYear;
            this.detailPrintingMonth=param.detailPrintingMonth;
            this.socialInsuranceStanDate=param.socialInsuranceStanDate;
            this.salaryInsuranceCollecMonth=param.salaryInsuranceCollecMonth;
            this.employmentInsuranceStanDate=param.employmentInsuranceStanDate;
        }
    }


    export interface IValPayDateSet{
        cid: string,
        processCategoryNo: string,
        basicSetting: BasicSetting,
        advancedSetting: AdvancedSetting
    }

    //支払日の設定の規定値
    export class ValPayDateSet {
        cid: string;
        processCategoryNo: string;
        basicSetting: BasicSetting;
        advancedSetting: AdvancedSetting;

        constructor(param: IValPayDateSet) {
            this.cid = param.cid;
            this.processCategoryNo = param.processCategoryNo;
            this.advancedSetting = param.advancedSetting;
            this.basicSetting = param.basicSetting;
        }
    }

    interface IReferenceDateInformation {
        dailyPaymentDateCheck: boolean,
        empExtractionRefDateCheck: boolean,
        socialInsuranceMonthCheck: boolean,
        specPrintDateCheck: boolean,
        numWorkingDaysCheck: boolean,
        socialInsuranceDateCheck: boolean,
        empInsuranceStandardDateCheck: boolean,
        timeClosingCheck: boolean,
        incomeTaxReferenceCheck: boolean,
        accountingClosureDateCheck: boolean,
    }



    export class ReferenceDateInformation {
        dailyPaymentDateCheck: KnockoutObservable<boolean>;
        empExtractionRefDateCheck: KnockoutObservable<boolean>;
        socialInsuranceMonthCheck: KnockoutObservable<boolean>;
        specPrintDateCheck: KnockoutObservable<boolean>;
        numWorkingDaysCheck: KnockoutObservable<boolean>;
        socialInsuranceDateCheck: KnockoutObservable<boolean>;
        empInsuranceStandardDateCheck: KnockoutObservable<boolean>;
        timeClosingCheck: KnockoutObservable<boolean>;
        incomeTaxReferenceCheck: KnockoutObservable<boolean>;
        accountingClosureDateCheck: KnockoutObservable<boolean>;

        constructor(params: IReferenceDateInformation) {
            this.dailyPaymentDateCheck = ko.observable(params.dailyPaymentDateCheck);
            this.empExtractionRefDateCheck = ko.observable(params.empExtractionRefDateCheck);
            this.socialInsuranceMonthCheck = ko.observable(params.socialInsuranceMonthCheck);
            this.specPrintDateCheck = ko.observable(params.specPrintDateCheck);
            this.numWorkingDaysCheck = ko.observable(params.numWorkingDaysCheck);
            this.socialInsuranceDateCheck = ko.observable(params.socialInsuranceDateCheck);
            this.empInsuranceStandardDateCheck = ko.observable(params.empInsuranceStandardDateCheck);
            this.timeClosingCheck = ko.observable(params.timeClosingCheck);
            this.incomeTaxReferenceCheck = ko.observable(params.incomeTaxReferenceCheck);
            this.accountingClosureDateCheck = ko.observable(params.accountingClosureDateCheck);
        }
    }


    export class SetPaymentDateTransfer {
        processingYear: KnockoutObservable<number>;
        listSettingPayment: KnockoutObservableArray<PaymentDateItem>;

        constructor(processingYear: KnockoutObservable<number>, listSettingPayment: KnockoutObservableArray<PaymentDateItem>) {
            this.processingYear = ko.observable(processingYear);
            this.listSettingPayment = listSettingPayment;
        }
    }

    interface IPaymentDateItem {
        paymentDate: string;
        employeeExtractionReferenceDate: string;
        socialInsuranceCollectionMonth: string;
        specificationPrintDate: string;
        numberOfWorkingDays: number;
        targetMonth: string;
        incomeTaxReferenceDate: string;
        accountingClosureDate: string;
        socialInsuranceStandardDate: string;
        employmentInsuranceStandardDate: string;
        timeClosingDate: string;
    }

    export class PaymentDateItem {
        paymentDate: KnockoutObservable<Date>;
        employeeExtractionReferenceDate: KnockoutObservable<string>;
        socialInsuranceCollectionMonth: KnockoutObservable<string>;
        specificationPrintDate: KnockoutObservable<string>;
        numberOfWorkingDays: KnockoutObservable<number>;
        targetMonth: KnockoutObservable<string>;
        incomeTaxReferenceDate: KnockoutObservable<string>;
        accountingClosureDate: KnockoutObservable<string>;
        socialInsuranceStandardDate: KnockoutObservable<string>;
        employmentInsuranceStandardDate: KnockoutObservable<string>;
        timeClosingDate: KnockoutObservable<string>;

        constructor(params: IPaymentDateItem) {
            this.paymentDate = ko.observable(params.paymentDate);
            this.employeeExtractionReferenceDate = ko.observable(params.employeeExtractionReferenceDate);
            this.socialInsuranceCollectionMonth = ko.observable(params.socialInsuranceCollectionMonth);
            this.specificationPrintDate = ko.observable(params.specificationPrintDate);
            this.numberOfWorkingDays = ko.observable(params.numberOfWorkingDays);
            this.targetMonth = ko.observable(params.targetMonth);
            this.incomeTaxReferenceDate = ko.observable(params.incomeTaxReferenceDate);
            this.accountingClosureDate = ko.observable(params.accountingClosureDate);
            this.socialInsuranceStandardDate = ko.observable(params.socialInsuranceStandardDate);
            this.employmentInsuranceStandardDate = ko.observable(params.employmentInsuranceStandardDate);
            this.timeClosingDate = ko.observable(params.timeClosingDate);
        }
    }

}