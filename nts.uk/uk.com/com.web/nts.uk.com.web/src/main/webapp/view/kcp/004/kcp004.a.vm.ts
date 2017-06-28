module kcp004.a.viewmodel {
    import UnitModel = kcp.share.tree.UnitModel;
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
        selectedWorkplaceId: KnockoutObservable<string>;
        baseDate: KnockoutObservable<Date>;
        multiSelectedWorkplaceId: KnockoutObservableArray<string>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        treeGrid: TreeComponentOption;
        
        jsonData: KnockoutObservable<string>;
        
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
            self.selectedWorkplaceId = ko.observable('wpl1');
            self.multiSelectedWorkplaceId = ko.observableArray(['wpl1', 'wpl3']);
            self.alreadySettingList = ko.observableArray([
                    {workplaceId: 'wpl1', settingType: SettingType.NO_SETTING},
                    {workplaceId: 'wpl3', settingType: SettingType.ALREADY_SETTING},
            ]);
            self.treeGrid = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultipleTreeGrid(),
                treeType: TreeType.WORK_PLACE,
                selectedWorkplaceId: self.getSelectedWorkplaceId(),
                baseDate: self.baseDate,
                isShowSelectButton: self.isShowSelectButton(),
                isDialog: self.isDialog(),
                alreadySettingList: self.alreadySettingList
            };
            
            self.jsonData = ko.observable('');
            
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
            });
        }
        
        private copy() {
            // issue #84074
            nts.uk.ui.dialog.info('複写時の処理については現状対象外となります。');
        }
        
        private register() {
            let self = this;
            if (self.isMultipleTreeGrid()) {
                for (let workplaceId of self.multiSelectedWorkplaceId()) {
                    self.alreadySettingList.push({workplaceId: workplaceId, settingType: SettingType.USE_PARRENT_SETTING});
                }
            } else {
                self.alreadySettingList.push({workplaceId: self.selectedWorkplaceId(), settingType: SettingType.USE_PARRENT_SETTING});
            }
        }
        
        private remove() {
            let self = this;
            let selecetdWorkplaceId = self.getSelectedData();
            self.alreadySettingList(self.alreadySettingList().filter((item) => {
                return selecetdWorkplaceId.indexOf(item.workplaceId) < 0;
            }));
        }
        
        private getSelectedWorkplaceId() : any {
            let self = this;
            if (self.isMultipleTreeGrid()) {
                return self.multiSelectedWorkplaceId;
            }
            return self.selectedWorkplaceId;
        }
        
        private getSelectedData() : string {
            let self = this;
            if (self.isMultipleTreeGrid()) {
                return self.multiSelectedWorkplaceId().join(", ");
            }
            return self.selectedWorkplaceId();
        }
        
        private setTreeData() {
            let self = this;
            self.treeGrid = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultipleTreeGrid(),
                treeType: TreeType.WORK_PLACE,
                selectedWorkplaceId: self.getSelectedWorkplaceId(),
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