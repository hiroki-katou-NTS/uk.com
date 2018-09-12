module nts.uk.pr.view.qmm012.share.model {
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
    
    export class ItemModel {
        code: string;
        name: string;
    
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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