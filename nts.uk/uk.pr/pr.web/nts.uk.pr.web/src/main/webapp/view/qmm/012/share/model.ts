module nts.uk.pr.view.qmm012.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    import time = nts.uk.time;

    /**
     * カテゴリ区分
     */
    export enum CategoryAtr {

        PAYMENT_ITEM = 0,

        DEDUCTION_ITEM = 1,

        ATTEND_ITEM = 2,

        REPORT_ITEM = 3,

        OTHER_ITEM = 4
    }

    export enum TaxAtr {
        TAXATION = 0,
        LIMIT_TAX_EXEMPTION = 1,
        NO_LIMIT_TAX_EXEMPTION = 2,
        COMMUTING_EXPENSES_MANUAL = 3,
        COMMUTING_EXPENSES_USING_COMMUTER = 4
    }

    export enum CoveredAtr {
        NOT_COVERED = 0,
        COVERED = 1
    }

    export enum SettingClassification {
        DESIGNATE_FOR_EACH_SALARY_CONTRACT_TYPE = 0,
        DESIGNATE_BY_ALL_MEMBERS = 1
    }

    export enum Display {
        NOT_SHOW = 0,
        SHOW = 1
    }

    export enum BreakdownItemUseAtr {
        NOT_USE = 0,
        USE = 1
    }

    export enum LimitAmountClassification {
        TAX_EXEMPTION_LIMIT_MASTER = 0,
        FIXED_AMOUNT = 1,
        REFER_TO_PERSONAL_TRANSPORTATION_TOOL_LIMIT = 2,
        REFER_TO_PERSONAL_TRANSPORTATION_LIMIT = 3
    }

    export enum TaxableAmountClassification {
        OVERDRAFT_TAXATION = 0,
        FULL_TAXATION = 1
    }

    export enum CycleSettingAtr {
        NOT_USE = 0,
        USE = 1
    }

    export enum ValidityPeriodAtr {
        NOT_SETUP = 0,
        SETUP = 1
    }

    export enum DeductionItemAtr {
        OPTIONAL_DEDUCTION_ITEM = 0,
        SOCIAL_INSURANCE_ITEM = 1,
        INCOME_TAX_ITEM = 2,
        INHABITANT_TAX_ITEM = 3
    }
    
    export enum TimeCountAtr {
        TIME = 0,
        TIMES = 1
    }
    
    export function getCategoryAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(CategoryAtr.PAYMENT_ITEM, getText('QMM012_5')),
            new model.ItemModel(CategoryAtr.DEDUCTION_ITEM, getText('QMM012_6')),
            new model.ItemModel(CategoryAtr.ATTEND_ITEM, getText('QMM012_7'))
        ];
    }

    export function getCycleSettingAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(CycleSettingAtr.USE, getText('QMM012_113')),
            new model.ItemModel(CycleSettingAtr.NOT_USE, getText('QMM012_114'))
        ];
    }

    export function getValidityPeriodAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(ValidityPeriodAtr.SETUP, getText('QMM012_121')),
            new model.ItemModel(ValidityPeriodAtr.NOT_SETUP, getText('QMM012_122'))
        ];
    }

    export function getItemAtrText(itemAtr: number): string {
        switch (itemAtr) {
            case CategoryAtr.PAYMENT_ITEM:
                return getText('QMM012_5');
            case CategoryAtr.DEDUCTION_ITEM:
                return getText('QMM012_6')
            case CategoryAtr.ATTEND_ITEM:
                return getText('QMM012_7')
            default:
                return "";
        }
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    export interface IValidityPeriodAndCycleSet {
        cycleSettingAtr: number;
        january: boolean;
        february: boolean;
        march: boolean;
        april: boolean;
        may: boolean;
        june: boolean;
        july: boolean;
        august: boolean;
        september: boolean;
        october: boolean;
        november: boolean;
        december: boolean;
        periodAtr: number;
        yearPeriodStart: number;
        yearPeriodEnd: number;
    }

    export class ValidityPeriodAndCycleSet {
        cycleSettingAtr: KnockoutObservable<number> = ko.observable(null);
        january: KnockoutObservable<boolean> = ko.observable(false);
        february: KnockoutObservable<boolean> = ko.observable(false);
        march: KnockoutObservable<boolean> = ko.observable(false);
        april: KnockoutObservable<boolean> = ko.observable(false);
        may: KnockoutObservable<boolean> = ko.observable(false);
        june: KnockoutObservable<boolean> = ko.observable(false);
        july: KnockoutObservable<boolean> = ko.observable(false);
        august: KnockoutObservable<boolean> = ko.observable(false);
        september: KnockoutObservable<boolean> = ko.observable(false);
        october: KnockoutObservable<boolean> = ko.observable(false);
        november: KnockoutObservable<boolean> = ko.observable(false);
        december: KnockoutObservable<boolean> = ko.observable(false);
        periodAtr: KnockoutObservable<number> = ko.observable(null);
        yearPeriodStart: KnockoutObservable<number> = ko.observable(null);
        yearPeriodEnd: KnockoutObservable<number> = ko.observable(null);
        startYearJpn: KnockoutObservable<string> = ko.observable(null);;
        endYearJpn: KnockoutObservable<string> = ko.observable(null);;

        constructor(data: IValidityPeriodAndCycleSet) {
            this.yearPeriodStart.subscribe(data => {
                if (data) {
                    this.startYearJpn("（" + time.yearInJapanEmpire(data).toString().split(' ').join('') + "）");
                }
                else {
                    this.startYearJpn("");
                }
            });
            this.yearPeriodEnd.subscribe(data => {
                if (data) {
                    this.endYearJpn("（" + time.yearInJapanEmpire(data).toString().split(' ').join('') + "）");
                }
                else {
                    this.endYearJpn("");
                }
            });
            
            this.cycleSettingAtr = ko.observable(data ? data.cycleSettingAtr : null);
            this.january(data ? data.january : false);
            this.february(data ? data.february : false);
            this.march(data ? data.march : false);
            this.april(data ? data.april : false);
            this.may(data ? data.may : false);
            this.june(data ? data.june : false);
            this.july(data ? data.july : false);
            this.august(data ? data.august : false);
            this.september(data ? data.september : false);
            this.october(data ? data.october : false);
            this.november(data ? data.november : false);
            this.december(data ? data.december : false);
            this.periodAtr(data ? data.periodAtr : null);
            this.yearPeriodStart(data ? data.yearPeriodStart : null);
            this.yearPeriodEnd(data ? data.yearPeriodEnd : null);
        }
    }
}