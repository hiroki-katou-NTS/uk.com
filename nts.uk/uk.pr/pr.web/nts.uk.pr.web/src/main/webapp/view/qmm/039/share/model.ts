module nts.uk.pr.view.qmm039.share.model {
    export interface IPerProcessClsSet {
        processCateNo: string;
        uid: string;
        cid: string;
    }
    export class PerProcessClsSet {
        processCateNo: KnockoutObservable<string> = ko.observable(null);
        uid: KnockoutObservable<string> = ko.observable(null);
        cid: KnockoutObservable<string> = ko.observable(null);

        constructor(params: IPerProcessClsSet) {
            this.processCateNo(params ? params.processCateNo : null);
            this.uid(params ? params.uid : null);
            this.cid(params ? params.cid : null);
        }
    }

    export interface ICurrentProcessDate {
        processCategNo: string;
        cID: string;
        giveCurrTreatYear: number;
    }
    export class CurrentProcessDate {
        processCategNo: KnockoutObservable<string> = ko.observable(null);
        cID: KnockoutObservable<string> = ko.observable(null);
        giveCurrTreatYear: KnockoutObservable<number> = ko.observable(null);

        constructor(params: ICurrentProcessDate) {
            this.processCategNo(params ? params.processCategNo : null);
            this.cID(params ? params.cID : null);
            this.giveCurrTreatYear(params ? params.giveCurrTreatYear : null);
        }
    }

    export interface ISalIndAmount {
        historyId: string;
        amountOfMoney: number;
    }
    export class SalIndAmount {
        historyId: KnockoutObservable<string> = ko.observable(null);
        amountOfMoney: KnockoutObservable<number> = ko.observable(null);

        constructor(params: ISalIndAmount) {
            this.historyId(params ? params.historyId : null);
            this.amountOfMoney(params ? params.amountOfMoney : null);
        }
    }

    export interface ISalIndAmountHis {
        perValCode: string;
        cateIndicator: number;
        historyID: string;
        periodStartYm: number;
        periodEndYm: number;
        empId: string;
        salBonusCate: number;
    }
    export class SalIndAmountHis {
        perValCode: KnockoutObservable<string> = ko.observable(null);
        cateIndicator: KnockoutObservable<number> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);
        periodStartYm: KnockoutObservable<number> = ko.observable(null);
        periodEndYm: KnockoutObservable<number> = ko.observable(null);
        empId: KnockoutObservable<string> = ko.observable(null);
        salBonusCate: KnockoutObservable<number> = ko.observable(null);

        constructor(params: ISalIndAmountHis) {
            this.perValCode(params ? params.perValCode : null);
            this.cateIndicator(params ? params.cateIndicator : null);
            this.historyID(params ? params.historyID : null);
            this.periodStartYm(params ? params.periodStartYm : null);
            this.periodEndYm(params ? params.periodEndYm : null);
            this.empId(params ? params.empId : null);
            this.salBonusCate(params ? params.salBonusCate : null);
        }
    }

    export interface ISetDaySupport {
        cid: string;
        processDate: number;
        processCategoryNo: number;
        closeDateTime: string;
        paymentDate: string;
        empExtraRefeDate: string;
        socialInsurCollecMonth: number;
        socialInsurStanDate: string;
        empInsurStandDate: string;
        incomeTaxDate: string;
        accountingClosureDate: string;
        numberWorkDay: number;
    }
    export class SetDaySupport {
        cid: KnockoutObservable<string> = ko.observable(null);
        processDate: KnockoutObservable<number> = ko.observable(null);
        processCategoryNo: KnockoutObservable<number> = ko.observable(null);
        closeDateTime: KnockoutObservable<string> = ko.observable(null);
        paymentDate: KnockoutObservable<string> = ko.observable(null);
        empExtraRefeDate: KnockoutObservable<string> = ko.observable(null);
        socialInsurCollecMonth: KnockoutObservable<number> = ko.observable(null);
        socialInsurStanDate: KnockoutObservable<string> = ko.observable(null);
        empInsurStandDate: KnockoutObservable<string> = ko.observable(null);
        incomeTaxDate: KnockoutObservable<string> = ko.observable(null);
        accountingClosureDate: KnockoutObservable<string> = ko.observable(null);
        numberWorkDay: KnockoutObservable<number> = ko.observable(null);

        constructor(params: ISetDaySupport) {
            this.cid(params ? params.cid : null);
            this.processDate(params ? params.processDate : null);
            this.processCategoryNo(params ? params.processCategoryNo : null);
            this.closeDateTime(params ? params.closeDateTime : null);
            this.paymentDate(params ? params.paymentDate : null);
            this.empExtraRefeDate(params ? params.empExtraRefeDate : null);
            this.socialInsurCollecMonth(params ? params.socialInsurCollecMonth : null);
            this.socialInsurStanDate(params ? params.socialInsurStanDate : null);
            this.empInsurStandDate(params ? params.empInsurStandDate : null);
            this.incomeTaxDate(params ? params.incomeTaxDate : null);
            this.accountingClosureDate(params ? params.accountingClosureDate : null);
            this.numberWorkDay(params ? params.numberWorkDay : null);
        }
    }

    export interface IGenericHistYMPeriod {
        historyID: string;
        periodStartYm: string;
        periodEndYm: string;
    }

    export class GenericHistYMPeriod {
        historyID: KnockoutObservable<string> = ko.observable(null);
        periodStartYm: KnockoutObservable<string> = ko.observable(null);
        periodEndYm: KnockoutObservable<string> = ko.observable(null);

        constructor(params: IGenericHistYMPeriod) {
            this.historyID(params ? params.historyID : null);
            this.periodStartYm(params ? params.periodStartYm : null);
            this.periodEndYm(params ? params.periodEndYm : null);
        }
    }
    export class EnumModel {
        value: number;
        name: string;

        constructor(value, name) {
            this.value = value;
            this.name = name;
        }
    }

    export enum INHERITANCE_CLS {
        WITH_HISTORY = 0,
        NO_HISTORY = 1
    }

    export enum MOFIDY_METHOD {
        DELETE = 0,
        UPDATE = 1
    }
    export enum ITEM_CLASS {
        SALARY_SUPLY = 0,
        SALARY_DEDUCTION = 1,
        BONUS_SUPLY = 2,
        BONUS_DEDUCTION = 3,
    }
    export enum PERVALUECATECLS    {
        SUPPLY = 0,
        DEDUCTION = 1
    }

    export enum SALBONUSCATE {
        SALARY = 0,
        BONUSES = 1
    }

    export enum MODE {
        NORMAL = 0,
        HISTORY_UNREGISTERED = 1,
        ADD_HISTORY = 2
    }
}