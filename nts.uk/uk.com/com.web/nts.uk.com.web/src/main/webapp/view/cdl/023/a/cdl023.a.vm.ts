module nts.uk.com.view.cdl023.a.viewmodel {

    export class ScreenModel {

        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        totalSelectionDisp: KnockoutObservable<string>;
        isOverride: KnockoutObservable<boolean>;
        
        lstSelected: KnockoutObservableArray<string>;
        
        targetType: number;
        
        baseDate: Date;
        
        constructor() {
            let self = this;
            
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
            let object: any = nts.uk.ui.windows.getShared("ObjectDuplication");
            self.code(object.code);
            self.name(object.name);
            self.targetType = object.targetType;
            self.baseDate = object.baseDate;
            
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * closeDialog
         */
        public closeDialog() {
            nts.uk.ui.windows.close();
        }

        /**
         * execution
         */
        public execution() {
            let self = this;
            nts.uk.ui.windows.setShared("CDL023Output", self.lstSelected());
            nts.uk.ui.windows.close();
        }
        
        /**
         * openDialog
         */
        public openDialog() {
            let self = this;
            let screenUrl: string = null;
            
            // set parameters
            let shareData: any = {};
            shareData.isMultiple = true;
            shareData.showNoSelection = false;
            shareData.selectedCodes = self.lstSelected();
            
            let keyInput: string = null;
            let keyOutput: string = null;
            switch (self.targetType) {
                case TargetType.EMPLOYMENT:
                    screenUrl = '/view/cdl/002/a/index.xhtml';
                    keyInput = 'CDL002Params';
                    keyOutput = 'CDL002Output';
                    break;
                case TargetType.CLASSIFICATION:
                    screenUrl = '/view/cdl/003/a/index.xhtml';
                    keyInput = 'inputCDL003';
                    keyOutput = 'outputCDL003';
                    break;
                case TargetType.JOB_TITLE:
                    screenUrl = '/view/cdl/004/a/index.xhtml';
                    keyInput = 'inputCDL004';
                    keyOutput = 'outputCDL004';
                    
                    // set data share
                    shareData.baseDate = self.baseDate;
                    break;
                case TargetType.WORKPLACE:
                    screenUrl = '/view/cdl/008/a/index.xhtml';
                    keyInput = 'inputCDL008';
                    keyOutput = 'outputCDL008';
                    
                    // set data share
                    shareData.baseDate = self.baseDate;
                    break;
                case TargetType.DEPARMENT:
                    screenUrl = '/view/cdl/007/a/index.xhtml';
                    nts.uk.ui.dialog.alert("Not cover.");
                    return;
//                    break;
                case TargetType.WORKPLACE_PERSONAL:
                    screenUrl = '/view/cdl/009/a/index.xhtml';
                    keyInput = 'CDL009Params';
                    keyOutput = 'outputCDL003';
                    
                    // set data share
                    shareData.target = 1;
                    shareData.baseDate = self.baseDate;
                    shareData.selectedIds = self.lstSelected();
                    break;
                case TargetType.DEPARMENT_PERSONAL:
                    screenUrl = '/view/cdl/009/a/index.xhtml';
                    keyInput = 'CDL009Params';
                    keyOutput = 'CDL009Output';
                    
                    // set data share
                    shareData.target = 2;
                    shareData.baseDate = self.baseDate;
                    shareData.selectedIds = self.lstSelected();
                    break;
                default:
                    nts.uk.ui.dialog.alert("Target type not found.");
                    return;
            }
            
            // share data
            nts.uk.ui.windows.setShared(keyInput, shareData);
            
            // open dialog
            nts.uk.ui.windows.sub.modal(screenUrl).onClosed(() => {
                // get value respond
                let selectedCode: any = nts.uk.ui.windows.getShared(keyOutput);
                
                // set list selection
                if (Array.isArray(selectedCode)) {
                    self.lstSelected(selectedCode);
                } else {
                    self.lstSelected([selectedCode]);
                }
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
        static DEPARMENT = 5;
        
        // 職場個人
        static WORKPLACE_PERSONAL = 6;
        
        // 部門個人
        static DEPARMENT_PERSONAL = 7;
    }
}