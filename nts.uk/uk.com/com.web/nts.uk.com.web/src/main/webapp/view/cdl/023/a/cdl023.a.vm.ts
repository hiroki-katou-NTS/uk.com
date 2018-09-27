module nts.uk.com.view.cdl023.a.viewmodel {

    export class ScreenModel {
        
        isFirtTime : KnockoutObservable<boolean>;

        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        totalSelectionDisp: KnockoutObservable<string>;
        isOverride: KnockoutObservable<boolean>;
        
        lstSelected: KnockoutObservableArray<string>;
        
        targetType: number;
        itemListSetting: Array<string>;
        baseDate: Date;
        
        roleType: number;
        
        constructor() {
            let self = this;
            
            self.isFirtTime = ko.observable(true);
            
            self.code = ko.observable(null);
            self.name = ko.observable(null);
            
            self.lstSelected = ko.observableArray([]);
            
            // display number of destinations
            self.totalSelectionDisp = ko.computed(() => {
                return nts.uk.resource.getText("CDL023_5", [self.lstSelected().length]);
            });
            self.isOverride = ko.observable(false);
            
            self.baseDate = null;
        }

        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            // get data shared
            let object: any = nts.uk.ui.windows.getShared("CDL023Input");
            self.code(object.code);
            self.name(object.name);
            self.targetType = object.targetType;
            self.itemListSetting = object.itemListSetting;
            self.baseDate = object.baseDate;
            self.roleType = object.roleType;
            
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * closeDialog
         */
        public closeDialog() {
            // respond data
            nts.uk.ui.windows.setShared("CDL023Cancel", true);
            
            // close dialog
            nts.uk.ui.windows.close();
        }

        /**
         * execution
         */
        public execution() {
            let self = this;
            
            // check empty selection
            if (self.lstSelected().length <= 0) {
               nts.uk.ui.dialog.alertError({ messageId: "Msg_646" });
               return;
            }
            
            // respond data
            nts.uk.ui.windows.setShared("CDL023Output", self.getSelectedItems());
            
            // close dialog
            nts.uk.ui.windows.close();
        }
        
        /**
         * getSelectedItems
         */
        private getSelectedItems(): Array<string> {
            let self = this;
            
            // if is override, return list selected
            if (self.isOverride()) {
                return self.lstSelected();
            }
            
            // if not override, remove items is saved setting.
            _.remove(self.lstSelected(), function(obj) {               
                return _.find(self.itemListSetting, (item) => { return item == obj; } ) != undefined;
            });
            return self.lstSelected();
        }
        
        /**
         * openDialog
         */
        public openDialog() {
            let self = this;
            
            let listToDialog = self.isFirtTime() ? self.itemListSetting : self.lstSelected();
            
            let screenUrl: string = null;
            
            // set parameters
            let shareData: any = {};
            shareData.isMultiple = true;
            
            // define key share
            let keyInput: string = null;
            let keyOutput: string = null;
            let keyCancel: string = null;
            
            switch (self.targetType) {
                case TargetType.EMPLOYMENT:
                    screenUrl = '/view/cdl/002/a/index.xhtml';
                    keyInput = 'CDL002Params';
                    keyOutput = 'CDL002Output';
                    keyCancel = 'CDL002Cancel';
                    
                    // set parameter
                    shareData.showNoSelection = false;
                    shareData.selectedCodes = self.lstSelected();
                    break;
                case TargetType.CLASSIFICATION:
                    screenUrl = '/view/cdl/003/a/index.xhtml';
                    keyInput = 'inputCDL003';
                    keyOutput = 'outputCDL003';
                    keyCancel = 'CDL003Cancel';
                    
                    // set parameter
                    shareData.showNoSelection = false;
                    shareData.selectedCodes = self.lstSelected();
                    break;
                case TargetType.JOB_TITLE:
                    screenUrl = '/view/cdl/004/a/index.xhtml';
                    keyInput = 'inputCDL004';
                    keyOutput = 'outputCDL004';
                    keyCancel = 'CDL004Cancel';
                    
                    // set data share
                    shareData.baseDate = self.baseDate;
                    shareData.showNoSelection = false;
                    shareData.selectedCodes = self.lstSelected();
                    break;
                case TargetType.WORKPLACE:
                    screenUrl = '/view/cdl/008/a/index.xhtml';
                    keyInput = 'inputCDL008';
                    keyOutput = 'outputCDL008';
                    keyCancel = 'CDL008Cancel';
                    
                    // set data share
                    shareData.selectedSystemType=2;
                    shareData.baseDate = self.baseDate;
                    shareData.selectedCodes = self.lstSelected();
                    break;
                case TargetType.DEPARTMENT:
                    screenUrl = '/view/cdl/007/a/index.xhtml';
                    nts.uk.ui.dialog.alert("Not cover.");
                    return;
//                    break;
                case TargetType.WORKPLACE_PERSONAL:
                    screenUrl = '/view/cdl/009/a/index.xhtml';
                    keyInput = 'CDL009Params';
                    keyOutput = 'CDL009Output';
                    keyCancel = 'CDL009Cancel';
                    
                    // set data share
                    shareData.target = TargetPersonalType.WORKPLACE;
                    shareData.baseDate = self.baseDate;
                    shareData.selectedIds = self.lstSelected();
                    break;
                case TargetType.DEPARTMENT_PERSONAL:
                    screenUrl = '/view/cdl/009/a/index.xhtml';
                    keyInput = 'CDL009Params';
                    keyOutput = 'CDL009Output';
                    keyCancel = 'CDL009Cancel';
                    
                    // set data share
                    shareData.target = TargetPersonalType.DEPARTMENT;
                    shareData.baseDate = self.baseDate;
                    shareData.selectedIds = self.lstSelected();
                    break;
                case TargetType.ROLE:
                    screenUrl = '/view/cdl/025/index.xhtml';
                    keyInput = 'paramCdl025';
                    keyOutput = 'dataCdl025';
                    keyCancel = 'CDL009Cancel';
                    
                    // set data share
                    shareData.roleType = self.roleType;
                    shareData.multiple = true;
                    shareData.currentCode = listToDialog;
                    break;
                    
                case TargetType.WORK_TYPE:
                    screenUrl = '/view/cdl/024/index.xhtml';
                    keyInput = 'CDL024';
                    keyOutput = 'currentCodeList';
                    keyCancel = 'CDL009Cancel';
                    
                    // set data share
                    shareData.codeList = listToDialog;
                    break;
                default:
                    nts.uk.ui.dialog.alert("Target type not found.");
                    return;
            }
            
            // share data
            nts.uk.ui.windows.setShared(keyInput, shareData);
            
            // open dialog
            nts.uk.ui.windows.sub.modal(screenUrl).onClosed(() => {
                
                // check close dialog
                if (nts.uk.ui.windows.getShared(keyCancel)) {
                    return;
                }
                
                // get value respond
                let selectedCode: any = nts.uk.ui.windows.getShared(keyOutput);
                if (!selectedCode || selectedCode.length <= 0) {
                    self.lstSelected([]);
                    return;
                }
                
                // set list selection
                if (Array.isArray(selectedCode)) {
                    self.lstSelected(selectedCode);
                } else {
                    self.lstSelected([selectedCode]);
                }
                
                // update flag
                self.isFirtTime(false);
            });
        }
    }
    
    /**
     * TargetType
     */
    class TargetType {
        
        // 雇用
        static EMPLOYMENT = 1;
        
        // 分類
        static CLASSIFICATION = 2;
        
        // 職位
        static JOB_TITLE = 3;
        
        // 職場
        static WORKPLACE = 4;
        
        // 部門
        static DEPARTMENT = 5;
        
        // 職場個人
        static WORKPLACE_PERSONAL = 6;
        
        // 部門個人
        static DEPARTMENT_PERSONAL = 7;
        
        // ロール
        static ROLE = 8;
        
        // 勤務種別
        static WORK_TYPE = 9;
        
    }
    
    /**
     * TargetPersonalType
     */
    class TargetPersonalType {
        
        // 職場個人
        static WORKPLACE = 1;
        
        //  部門個人
        static DEPARTMENT = 2;
    }
}