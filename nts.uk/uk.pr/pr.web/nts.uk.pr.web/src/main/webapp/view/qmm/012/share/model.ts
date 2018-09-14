module nts.uk.pr.view.qmm012.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    
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
        SHOW =1
    }
    
    export enum BreakdownItemUseAtr {
        NOT_USE = 0,
        USE =1
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
    
    export function getCategoryAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(CategoryAtr.PAYMENT_ITEM.toString(), getText('PAYMENT_ITEM')),
            new model.ItemModel(CategoryAtr.DEDUCTION_ITEM.toString(), getText('DEDUCTION_ITEM')),
            new model.ItemModel(CategoryAtr.ATTEND_ITEM.toString(), getText('ATTEND_ITEM'))
        ];
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
    
    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
    }
}
}