module nts.uk.pr.view.qmm041.share.model {


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

    export enum MODIFY_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum MODE {
        NORMAL = 0,
        HISTORY_UNREGISTERED = 1,
        ADD_HISTORY = 2
    }
}