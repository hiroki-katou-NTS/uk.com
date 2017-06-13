module kcp.share.tree {
    export interface UnitModel {
        code: string;
        name: string;
        nodeText?: string;
        settingType: SettingType;
        childs: Array<UnitModel>;
    }
    
    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
        isUseParentSetting: boolean;
    }
    
    export interface TreeComponentOption {
        /**
         * is Show Already setting.
         */
        isShowAlreadySet: boolean;
        
        /**
         * is Multi select.
         */
        isMultiSelect: boolean;
        
        /**
         * Tree type, if not set, default is work place.
         */
        treeType?: TreeType;
        
        /**
         * selected value.
         * May be string or Array<string>
         */
        selectedCode: KnockoutObservable<any>;
        
        /**
         * Base date.
         */
        baseDate: KnockoutObservable<Date>;
        
        /**
         * is dialog, if is main screen, set false,
         */
        isDialog: boolean;
        
        /**
         * Already setting list code. structure: {code: string, isAlreadySetting: boolean}
         * ignore when isShowAlreadySet = false.
         */
        alreadySettingList?: KnockoutObservableArray<UnitAlreadySettingModel>;
    }
    
    export class SettingType {
        static NO_SETTING = 0;
        static ALREADY_SETTING = 1;
        static USE_PARRENT_SETTING =2;
    }
    
    export class TreeType {
        static WORK_PLACE = 1;
    }
    
    export class TreeComponentScreenModel {
        itemList: KnockoutObservableArray<UnitModel>;
        selectedCodes: KnockoutObservable<any>;
        treeComponentColumn: Array<any>;
        isMultiple: boolean;
        hasBaseDate: boolean;
        baseDate: KnockoutObservable<Date>;
        
        constructor() {
            this.itemList = ko.observableArray([]);
            this.isMultiple = false;
            this.baseDate = ko.observable(new Date());
            this.treeComponentColumn = [{ headerText: nts.uk.resource.getText("KCP004_5"), key: 'code', dataType: "string", hidden: true },
            { headerText: nts.uk.resource.getText("KCP004_5"), key: 'nodeText', width: "200px", dataType: "string" }];
        }
        
        public init($input: JQuery, data: TreeComponentOption) :JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            self.isMultiple = data.isMultiSelect;
            self.selectedCodes = data.selectedCode;
            
            // If show Already setting.
            if (data.isShowAlreadySet) {
                // Add row already setting.
                self.treeComponentColumn.push({
                    headerText: nts.uk.resource.getText('KCP004_6'), prop: 'settingType', width: 70,
                    formatter: function(settingType: number) {
                        if (settingType == SettingType.USE_PARRENT_SETTING) {
                            return '<div style="text-align: center;"><i class="icon icon-close"></i></div>';
                        }
                        if (settingType == SettingType.ALREADY_SETTING) {
                            return '<div style="text-align: center;"><i class="icon icon-dot"></i></div>';
                        }
                        return '';
                    }
                });
            }
            
            return dfd.promise();
        }
        
        
    }
     export module service {
        
        // Service paths.
        var servicePath = {
            findWorkplaceTree: "basic/company/organization/employment/findAll/",
        }
        
        /**
         * Find Employment list.
         */
        export function findWorkplaceTree(screenModel: TreeComponentScreenModel): JQueryPromise<Array<UnitModel>> {
            return nts.uk.request.ajax(servicePath.findWorkplaceTree);
        }
        
    }
}