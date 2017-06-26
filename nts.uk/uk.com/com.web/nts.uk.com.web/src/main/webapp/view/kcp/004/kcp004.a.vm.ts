module kcp004.a.viewmodel {
    
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;
    import SettingType = kcp.share.tree.SettingType;
    import UnitAlreadySettingModel = kcp.share.tree.UnitAlreadySettingModel; 
    
    export class ScreenModel {
        
        // Control common
        listTreeType: KnockoutObservableArray<any>;
        selectedTreeType: KnockoutObservable<number>;
        
        isMultipleTreeGrid: KnockoutObservable<boolean>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        isDialog: KnockoutObservable<boolean>;
        isShowSelectButton: KnockoutObservable<boolean>;
        
        // Control component
        selectedCode: KnockoutObservable<string>;
        baseDate: KnockoutObservable<Date>;
        multiSelectedCode: KnockoutObservable<any>;
        multiSelectedCodeNoDisp: KnockoutObservable<any>;
        
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        
        treeGrid: TreeComponentOption;
        
        constructor() {
            let self = this;
            
            // Control common
            self.listTreeType = ko.observableArray([
                {code : 0, name: 'Single tree grid'},
                {code : 1, name: 'Multiple tree grid'}
            ]);
            self.selectedTreeType = ko.observable(1);
            self.isMultipleTreeGrid = ko.computed(function () {
                return self.selectedTreeType() == 1;
            });
            self.isDialog = ko.observable(false);
            self.isShowAlreadySet = ko.observable(true);
            self.isShowSelectButton = ko.observable(true);
            
            // Control component
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('A001');
            self.multiSelectedCode = ko.observableArray(['A001', 'B002']);
            self.alreadySettingList = ko.observableArray([
                    {code: 'A001', settingType: SettingType.NO_SETTING},
                    {code: 'A005', settingType: SettingType.ALREADY_SETTING},
            ]);
            self.treeGrid = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultipleTreeGrid(),
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.getSelectedCode(),
                baseDate: self.baseDate,
                isShowSelectButton: self.isShowSelectButton(),
                isDialog: self.isDialog(),
                alreadySettingList: self.alreadySettingList
            };
            
            // Subscribe
            self.selectedTreeType.subscribe(function(code) {
                self.reloadTreeGrid();
            });
            self.isDialog.subscribe(function(value) {
                self.reloadTreeGrid();
            });
            self.isShowAlreadySet.subscribe(function() {
                self.reloadTreeGrid();
            });
            self.alreadySettingList.subscribe(function() {
                self.reloadTreeGrid();
            });
            self.isShowSelectButton.subscribe(function() {
                self.reloadTreeGrid();
            })
        }
        
        private copy() {
            // issue #84074
            nts.uk.ui.dialog.info('複写時の処理については現状対象外となります。');
        }
        
        private register() {
            let self = this;
            if (self.isMultipleTreeGrid()) {
                for (let code of self.multiSelectedCode()) {
                    self.alreadySettingList.push({code: code, settingType: SettingType.USE_PARRENT_SETTING});
                }
            } else {
                self.alreadySettingList.push({code: self.selectedCode(), settingType: SettingType.USE_PARRENT_SETTING});
            }
        }
        
        private remove() {
            let self = this;
            let selecetdCode = self.getSelectedData();
            self.alreadySettingList(self.alreadySettingList().filter((item) => {
                return selecetdCode.indexOf(item.code) < 0;
            }));
        }
        
        private getSelectedCode() : any {
            let self = this;
            if (self.isMultipleTreeGrid()) {
                return self.multiSelectedCode;
            }
            return self.selectedCode;
        }
        
        private getSelectedData() : string {
            let self = this;
            if (self.isMultipleTreeGrid()) {
                return self.multiSelectedCode().join(", ");
            }
            return self.selectedCode();
        }
        
        private setTreeData() {
            let self = this;
            self.treeGrid = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultipleTreeGrid(),
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.getSelectedCode(),
                baseDate: self.baseDate,
                isShowSelectButton: self.isShowSelectButton(),
                isDialog: self.isDialog(),
                alreadySettingList: self.alreadySettingList
            };
        }
        
        private reloadTreeGrid() {
            let self = this;
            self.setTreeData();
            $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                $('#tree-grid').focusComponent();
            });
        }
    }
}