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

    export enum ExitStatus {
        CANCEL = 0,
        EXECUTION = 1
    }
    
    export enum Abolition {
        NOT_ABOLISH = 0,
        ABOLISH = 1
    }

    export function getCategoryAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(CategoryAtr.PAYMENT_ITEM.toString(), getText('QMM012_3')),
            new model.ItemModel(CategoryAtr.DEDUCTION_ITEM.toString(), getText('QMM012_4')),
            new model.ItemModel(CategoryAtr.ATTEND_ITEM.toString(), getText('QMM012_5'))
        ];
    }

    export function getCycleSettingAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(CycleSettingAtr.USE.toString(), getText('QMM012_99')),
            new model.ItemModel(CycleSettingAtr.NOT_USE.toString(), getText('QMM012_100'))
        ];
    }

    export function getValidityPeriodAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(ValidityPeriodAtr.SETUP.toString(), getText('QMM012_94')),
            new model.ItemModel(ValidityPeriodAtr.NOT_SETUP.toString(), getText('QMM012_95'))
        ];
    }

    export function getCategoryAtrText(itemAtr: number): string {
        switch (itemAtr) {
            case CategoryAtr.PAYMENT_ITEM:
                return getText('QMM012_3');
            case CategoryAtr.DEDUCTION_ITEM:
                return getText('QMM012_4');
            case CategoryAtr.ATTEND_ITEM:
                return getText('QMM012_5');
            case CategoryAtr.REPORT_ITEM:
                return getText('QMM012_6');
            case CategoryAtr.OTHER_ITEM:
                return getText('QMM012_7');
            default:
                return "";
        }
    }
    
    export function getCategoryAtrText2(itemAtr: number): string {
        switch (itemAtr) {
            case CategoryAtr.PAYMENT_ITEM:
                return getText('QMM012_15');
            case CategoryAtr.DEDUCTION_ITEM:
                return getText('QMM012_16')
            case CategoryAtr.ATTEND_ITEM:
                return getText('QMM012_17')
            default:
                return "";
        }
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    /**
 * 内訳項目設定
 */
    export interface IBreakdownItemSet {
        salaryItemId: string;
        breakdownItemCode: string;
        breakdownItemName: string;
    }
    export class BreakdownItemSet {
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        breakdownItemCode: KnockoutObservable<string> = ko.observable('');
        breakdownItemName: KnockoutObservable<string> = ko.observable('');
        constructor(param: IBreakdownItemSet) {
            let self = this;
            self.salaryItemId(param ? param.salaryItemId : '');
            self.breakdownItemCode(param ? param.breakdownItemCode : '');
            self.breakdownItemName(param ? param.breakdownItemName : '');
        }
    }

    export interface IStatementItem {
        cId: string;
        categoryAtr: number;
        itemNameCd: string;
        salaryItemId: string;
        defaultAtr: number;
        valueAtr: number;
        deprecatedAtr: number;
        socialInsuaEditableAtr: number;
        intergrateCd: number;
    }

    export class StatementItem {
        cId: KnockoutObservable<string> = ko.observable('');
        categoryAtr: KnockoutObservable<number> = ko.observable(0);
        itemNameCd: KnockoutObservable<string> = ko.observable('');
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        defaultAtr: KnockoutObservable<number> = ko.observable(0);
        valueAtr: KnockoutObservable<number> = ko.observable(0);
        deprecatedAtr: KnockoutObservable<number> = ko.observable(0);
        socialInsuaEditableAtr: KnockoutObservable<number> = ko.observable(0);
        intergrateCd: KnockoutObservable<number> = ko.observable(0);
        constructor(param: IStatementItem) {
            let self = this;
            self.cId(param ? param.cId : '');
            self.categoryAtr(param ? param.categoryAtr : 0);
            self.itemNameCd(param ? param.itemNameCd : '');
            self.salaryItemId(param ? param.salaryItemId : '');
            self.defaultAtr(param ? param.defaultAtr : 0);
            self.valueAtr(param ? param.valueAtr : 0);
            self.deprecatedAtr(param ? param.deprecatedAtr : 0);
            self.socialInsuaEditableAtr(param ? param.socialInsuaEditableAtr : 0);
            self.intergrateCd(param ? param.intergrateCd : 0);
        }
    }

    export interface IStatementItemName {
        cId: string;
        salaryItemId: string;
        name: string;
        shortName: string;
        otherLanguageName: string;
        englishName: string
    }

    export class StatementItemName {
        cId: KnockoutObservable<string> = ko.observable('');
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        shortName: KnockoutObservable<string> = ko.observable('');
        otherLanguageName: KnockoutObservable<string> = ko.observable('');
        englishName: KnockoutObservable<string> = ko.observable('');
        constructor(param: IStatementItemName) {
            let self = this;
            self.cId(param ? param.cId : '');
            self.salaryItemId(param ? param.salaryItemId : '');
            self.name(param ? param.name : '');
            self.shortName(param ? param.shortName : '');
            self.otherLanguageName(param ? param.otherLanguageName : '');
            self.englishName(param ? param.englishName : '');
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
        salaryItemId: string;
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
        salaryItemId: KnockoutObservable<string> = ko.observable(null);
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

        constructor(data: IValidityPeriodAndCycleSet) {
            let self = this;
            self.cycleSettingAtr = ko.observable(data ? data.cycleSettingAtr : CycleSettingAtr.NOT_USE);
            self.january(data ? data.january : false);
            self.february(data ? data.february : false);
            self.march(data ? data.march : false);
            self.april(data ? data.april : false);
            self.may(data ? data.may : false);
            self.june(data ? data.june : false);
            self.july(data ? data.july : false);
            self.august(data ? data.august : false);
            self.september(data ? data.september : false);
            self.october(data ? data.october : false);
            self.november(data ? data.november : false);
            self.december(data ? data.december : false);
            self.periodAtr(data ? data.periodAtr : ValidityPeriodAtr.NOT_SETUP);
            self.yearPeriodStart(data ? data.yearPeriodStart : null);
            self.yearPeriodEnd(data ? data.yearPeriodEnd : null);

            self.periodAtr.subscribe(x => {
                if ((x == "1") || (x == 1) ) {
                    if (self.yearPeriodStart() && self.yearPeriodEnd()
                        && self.yearPeriodStart() > self.yearPeriodEnd()) {
                        $('#validityPeriod').ntsError('set', { messageId: "MsgQ_3" });
                        isValid = false;
                    } else {
                        $('#validityPeriod').ntsError('clear');
                    }
                } else {
                    $('#validityPeriod').ntsError('clear');
                    $('#validityPeriod .ntsDatepicker').ntsError('clear');
                }
            });
        }
    }
}