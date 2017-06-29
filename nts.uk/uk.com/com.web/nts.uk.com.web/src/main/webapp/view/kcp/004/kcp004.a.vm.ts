module kcp004.a.viewmodel {
    import UnitModel = kcp.share.tree.UnitModel;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;
    import SettingType = kcp.share.tree.SettingType;
    import SelectType = kcp.share.tree.SelectionType;
    import UnitAlreadySettingModel = kcp.share.tree.UnitAlreadySettingModel; 
    
    export class ScreenModel {
        
        // Control common
        listTreeType: KnockoutObservableArray<any>;
        selectedTreeType: KnockoutObservable<number>;
        
        listSelectionType: KnockoutObservableArray<any>;
        selectedSelectionType: KnockoutObservable<number>;
        
        isMultipleTreeGrid: KnockoutObservable<boolean>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        isDialog: KnockoutObservable<boolean>;
        isShowSelectButton: KnockoutObservable<boolean>;
        enableShowSelectButton: KnockoutObservable<boolean>;
        
        // Control component
        selectedWorkplaceId: KnockoutObservable<string>;
        baseDate: KnockoutObservable<Date>;
        multiSelectedWorkplaceId: KnockoutObservable<any>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        treeGrid: TreeComponentOption;
        
        jsonData: KnockoutObservable<string>;
        rowSelected: KnockoutObservable<RowSelection>;
        isBindingTreeGrid: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        
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
            self.enableShowSelectButton = ko.computed(function() {
                return self.isMultipleTreeGrid();
            });
            
            // Control component
            self.baseDate = ko.observable(new Date());
            self.selectedWorkplaceId = ko.observable('wpl2');
            self.multiSelectedWorkplaceId = ko.observableArray(['wpl111111111111111111111111111111111', 'wpl311111111111111111111111111111111']);
            self.enable = ko.observable(true); 
            self.listSelectionType = ko.observableArray([
                {code : 1, name: 'Select by selected code', enable: self.enable},
                {code : 2, name: 'Select all', enable: self.isMultipleTreeGrid},
                {code : 3, name: 'Select first item', enable: self.enable},
                {code : 4, name: 'No select', enable: self.enable}
            ]);
            self.selectedSelectionType = ko.observable(1);
            
            self.alreadySettingList = ko.observableArray([
                    {workplaceId: 'wpl111111111111111111111111111111111', settingType: SettingType.NO_SETTING},
                    {workplaceId: 'wpl311111111111111111111111111111111', settingType: SettingType.ALREADY_SETTING},
            ]);
            self.treeGrid = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultipleTreeGrid(),
                treeType: TreeType.WORK_PLACE,
                selectedWorkplaceId: self.getSelectedWorkplaceId(),
                baseDate: self.baseDate,
                selectType: self.selectedSelectionType(), 
                isShowSelectButton: self.isShowSelectButton(),
                isDialog: self.isDialog(),
                alreadySettingList: self.alreadySettingList
            };
            
            self.jsonData = ko.observable('');
            self.rowSelected = ko.observable(new RowSelection('', ''));
            self.isBindingTreeGrid = ko.observable(false);
            
            // Subscribe
            self.selectedTreeType.subscribe(function(code) {
                self.resetSelectedWorkplace();
                self.reloadTreeGrid().done(() => {
                    self.getSelectedData();
                    self.isShowSelectButton(code == 1);
                });
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
            self.selectedSelectionType.subscribe((code) => {
                if (code == 1) {
                    self.resetSelectedWorkplace();
                }
                self.reloadTreeGrid().done(function() {
                    self.getSelectedData();
                });
            });
            self.selectedWorkplaceId.subscribe(() => {
                self.getSelectedData();
            });
            self.multiSelectedWorkplaceId.subscribe(() => {
                self.getSelectedData();
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
            let data = $('#tree-grid').getRowSelected();
            let selecetdWorkplaceId = data.map(item => item.workplaceId).join(", ");
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
        
        public getSelectedData() {
            let self = this;
            if (!self.isBindingTreeGrid()) {
                return;
            }
            let data = $('#tree-grid').getRowSelected();
            self.rowSelected().workplaceId(data.length > 0 ? data.map(item => item.workplaceId).join(", ") : '');
            self.rowSelected().workplaceCode(data.length > 0 ? data.map(item => item.workplaceCode).join(", ") : '');
        }
        
        private setTreeData() {
            let self = this;
            self.treeGrid = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultipleTreeGrid(),
                treeType: TreeType.WORK_PLACE,
                selectedWorkplaceId: self.getSelectedWorkplaceId(),
                baseDate: self.baseDate,
                selectType: self.selectedSelectionType(),
                isShowSelectButton: self.isShowSelectButton(),
                isDialog: self.isDialog(),
                alreadySettingList: self.alreadySettingList
            };
        }
        
        private reloadTreeGrid() : JQueryPromise<void>  {
            let self = this;
            let dfd = $.Deferred<void>();
            self.setTreeData();
            $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                $('#tree-grid').focusComponent();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        private resetSelectedWorkplace() {
            let self = this;
            self.selectedWorkplaceId('wpl211111111111111111111111111111111');
            self.multiSelectedWorkplaceId(['wpl111111111111111111111111111111111', 'wpl311111111111111111111111111111111']);
        }
    }
    
    export class RowSelection {
        workplaceId: KnockoutObservable<string>;
        workplaceCode: KnockoutObservable<string>;
        
        constructor(workplaceId: string, workplaceCode: string) {
            let self = this;
            self.workplaceId = ko.observable(workplaceId);
            self.workplaceCode = ko.observable(workplaceCode);
        }
    }
}