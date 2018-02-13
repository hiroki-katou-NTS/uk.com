module nts.uk.com.view.cmf001.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    export class StandardAcceptanceConditionSetting {
        conditionSettingCode: KnockoutObservable<string>;
        dispConditionSettingCode: string;
        conditionSettingName: KnockoutObservable<string>;
        dispConditionSettingName: string;
        deleteExistingData: KnockoutObservable<number> = ko.observable(0);
        deleteDetermination: KnockoutObservable<number> = ko.observable(-1);
        acceptMode: KnockoutObservable<number>;
        
        constructor(code: string, name: string, deleteExistingData: number, acceptMode: number) {
            this.conditionSettingCode = ko.observable(code);
            this.dispConditionSettingCode = code;
            this.conditionSettingName = ko.observable(name);
            this.dispConditionSettingName = name;
            if (deleteExistingData) {
                this.deleteExistingData(deleteExistingData);
                this.deleteDetermination(1);
            }
            this.acceptMode = ko.observable(acceptMode);
        }
    }
    
    export class UserAcceptanceConditionSetting {
        conditionSettingCode: KnockoutObservable<string>;
        dispConditionSettingCode: string;
        conditionSettingName: KnockoutObservable<string>;
        dispConditionSettingName: string;
        
        constructor(code: string, name: string) {
            this.conditionSettingCode = ko.observable(code);
            this.dispConditionSettingCode = code;
            this.conditionSettingName = ko.observable(name);
            this.dispConditionSettingName = name;
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
    
    export class StandardAcceptItem {
        
    }
    
    export class ExternalAcceptanceCategory {
        categoryCode: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        dispCategoryCode: string;
        dispCategoryName: string;
        
        constructor(code: string, name: string) {
            this.categoryCode = ko.observable(code);
            this.categoryName = ko.observable(name);
            this.dispCategoryCode = code;
            this.dispCategoryName = name;
        }
    }
    
    export class ExternalAcceptanceCategoryItemData {
        itemCode: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        dispItemCode: string;
        dispItemName: string;
        
        constructor(code: string, name: string) {
            this.itemCode = ko.observable(code);
            this.itemName = ko.observable(name);
            this.dispItemCode = code;
            this.dispItemName = name;
        }
    }
}