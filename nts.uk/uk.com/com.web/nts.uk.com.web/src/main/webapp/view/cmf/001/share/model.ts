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
        
        constructor(code: string, name: string) {
            this.conditionSettingCode = ko.observable(code);
            this.dispConditionSettingCode = code;
            this.conditionSettingName = ko.observable(name);
            this.dispConditionSettingName = name;
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
        code: string;
        name: string;
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    
}