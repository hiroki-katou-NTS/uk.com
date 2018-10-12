module nts.uk.pr.view.qmm018.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    export interface IAverageWageCalculationSet {
        cId: string;
        exceptionFormula: number;
        obtainAttendanceDays: number;
        daysFractionProcessing: number;
        decimalPointCutoffSegment: number;
    }
    export class AverageWageCalculationSet {
        cId: KnockoutObservable<string> = ko.observable(null);
        exceptionFormula: KnockoutObservable<number> = ko.observable(null);
        obtainAttendanceDays: KnockoutObservable<number> = ko.observable(null);
        daysFractionProcessing: KnockoutObservable<number> = ko.observable(null);
        decimalPointCutoffSegment: KnockoutObservable<number> = ko.observable(null);
        constructor(params: IAverageWageCalculationSet) {
            this.cId(params ? params.cId : null);
            this.exceptionFormula(params ? params.exceptionFormula : null);
            this.obtainAttendanceDays(params ? params.obtainAttendanceDays : null);
            this.daysFractionProcessing(params ? params.daysFractionProcessing : null);
            this.decimalPointCutoffSegment(params ? params.decimalPointCutoffSegment : null);
        }
    }

    export interface IStatement{
        salaryItemId: string;
        categoryAtr: number;
        itemNameCd: string;
        name: string;
    }
    export class Statement{
        salaryItemId: KnockoutObservable<string> = ko.observable(null);
        categoryAtr: KnockoutObservable<number> = ko.observable(null);
        itemNameCd: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IStatement) {
            this.salaryItemId(params ? params.salaryItemId : null);
            this.categoryAtr(params ? params.categoryAtr : null);
            this.itemNameCd(params ? params.itemNameCd : null);
            this.name(params ? params.name : null);
        }
    }

    export interface IDisplayData{
        averageWageCalculationSet: IAverageWageCalculationSet;
        lstStatemetPaymentItem: IStatement;
        lstStatemetAttendanceItem: IStatement;
    }

    export class DisplayData{
        averageWageCalculationSet: KnockoutObservable<AverageWageCalculationSet>;
        lstStatemetPaymentItem: KnockoutObservableArray<Statement>;
        lstStatemetAttendanceItem: KnockoutObservableArray<Statement>;
        constructor(params: IDisplayData){
            let self = this;
            self.averageWageCalculationSet = ko.observable(new AverageWageCalculationSet(params.averageWageCalculationSet));
            self.lstStatemetPaymentItem = ko.observableArray(new Statement(params.lstStatemetPaymentItem));
            self.lstStatemetAttendanceItem = ko.observableArray(new Statement(params.lstStatemetAttendanceItem));
        }
    }
}