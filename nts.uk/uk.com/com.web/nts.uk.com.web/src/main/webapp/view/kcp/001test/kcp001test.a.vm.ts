module kcp001test.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    export class ScreenModel {
        codeEditorOption: KnockoutObservable<any>;
        nameEditorOption: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<string>;
        bySelectedCode: KnockoutObservable<string>;
        isAlreadySetting: KnockoutObservable<boolean>;
        isShowMultiSelect: KnockoutObservable<boolean>;
        isShowAsDialog: KnockoutObservable<boolean>;
        isShowNoSelectionItem: KnockoutObservable<boolean>;
        
        multiSelectedCode: KnockoutObservableArray<any>;
        multiSelectAllItems: KnockoutObservableArray<any>;
        
        listComponentOption: ComponentOption;
        alreadySettingList: KnockoutObservableArray<any>;
        
        hasSelectedEmp: KnockoutObservable<boolean>;
        employmentList: KnockoutObservableArray<UnitModel>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        
        selectionTypeList: KnockoutObservableArray<any>;
        selectedType: KnockoutObservable<number>;
        
        constructor() {
            var self = this;
            self.codeEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                width: "50px",
                textmode: "text",
                textalign: "left"
            }));
            self.nameEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                width: "150px",
                textmode: "text",
                textalign: "left"
            }));
            self.selectedCode = ko.observable('02');
            self.bySelectedCode = ko.observable('02');
            // Selected Item subscribe
            self.selectedCode.subscribe(function(data: string) {
                self.bindEmploymentSettingData(self.employmentList().filter((item) => {
                    return item.code == self.selectedCode();
                })[0]);
            });
            self.isAlreadySetting = ko.observable(false);
            self.isAlreadySetting.subscribe(function() {
                self.showAlreadySet();
            });
            
            self.isShowMultiSelect = ko.observable(false);
            self.isShowMultiSelect.subscribe(function() {
                self.showMultiSelect();
            });
            
            self.isShowAsDialog = ko.observable(false);
            self.isShowAsDialog.subscribe(function() {
                self.showAsDialog();
            });
            
            self.isShowNoSelectionItem = ko.observable(false);
            self.isShowNoSelectionItem.subscribe(function() {
                self.showNoSelectionItem();
            });
            
            
            self.multiSelectAllItems = ko.observableArray([]);
            self.multiSelectedCode = ko.observableArray(['02', '04']);
            self.alreadySettingList = ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}]);

            self.code = ko.observable(null);
            self.name = ko.observable(null);
            self.employmentList = ko.observableArray<UnitModel>([]);
            
            self.hasSelectedEmp = ko.computed(function() {
                return (self.selectedCode != undefined);
            });
            
            self.listComponentOption = {
                    isShowAlreadySet: false,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingList
                };
//            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
            // employmentList...
            $('#empt-list-setting').ntsListComponent(self.listComponentOption).done(function() {
                
                
                
                // Selected Item
//                self.selectedCode(self.employmentList()[0].code);
                if (($('#empt-list-setting').getDataList() == undefined) || ($('#empt-list-setting').getDataList().length <= 0)) {
                    self.hasSelectedEmp(false);
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_146" });
                }
                else {
                    // Employment List
                    self.employmentList($('#empt-list-setting').getDataList());
                    
                    // Bind Employment Setting Data
                    self.bindEmploymentSettingData(self.employmentList().filter((item) => {
                        return item.code == self.selectedCode();
                    })[0]);
                    //                    self.hasSelectedEmp(true);
                }
            });
            
            self.selectionTypeList = ko.observableArray([
                {code : 0, name: 'By Selected Code'},
                {code : 1, name: 'Select All Items'},
                {code : 2, name: 'Select First Item'},
                {code : 3, name: 'Select None'}
            ]);
            self.selectedType = ko.observable(0);
            self.selectedType.subscribe(function(data: number) {
                switch(data) {
                    case 0:
                        self.selectBySelectedCode();
                        break;
                    case 1:
                        self.selectAllItems();
                        break;
                    case 2:
                        self.selectFirstItems();
                        break;
                }
            });
        }
        
        private setAlreadyCheck(): void {
            var self = this;
            self.alreadySettingList.push({"code": self.selectedCode(), "isAlreadySetting": true});
        }
        
        private settingSavedItem(): void {
            var self = this;
            // Clear errors
            self.clearErrors();

            // Validate. 
            $('#code').ntsEditor('validate');
            $('#name').ntsEditor('validate');
            if ($('.nts-input').ntsError('hasError')) {
                return;
            }
            var item = self.alreadySettingList().filter((item) => {
                return item.code == self.selectedCode();
            })[0];
            if (!item) {
                self.alreadySettingList.push({ "code": self.selectedCode(), "isAlreadySetting": true });
            }
            
            self.listComponentOption.isShowAlreadySet = true;
        }
        
        private settingDeletedItem() {
            let self = this;
            self.alreadySettingList.remove(self.alreadySettingList().filter((item) => {
                return item.code == self.selectedCode();
            })[0]);
            self.listComponentOption.isShowAlreadySet = true;
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        
        private clearErrors(): void {
            // Clear errors
            $('#code').ntsError('clear');
            $('#name').ntsError('clear');
        }
        
        
        // Show Already Setting
        private showAlreadySet(): void {
            var self = this;
            if (!self.listComponentOption.isShowAlreadySet) {
                self.listComponentOption.isShowAlreadySet = true;
            }
            else {
                self.listComponentOption.isShowAlreadySet = false;
            }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        // Show MultiSelection
        private showMultiSelect(): void {
            var self = this;
            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.isMultiSelect = false;
                self.listComponentOption.selectedCode = self.selectedCode;
            }
            else {
                self.listComponentOption.isMultiSelect = true;
                self.listComponentOption.selectedCode = self.multiSelectedCode;
            }

            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        // Show KCP as Dialog
        private showAsDialog(): void {
            var self = this;
            if (!self.listComponentOption.isDialog) {
                self.listComponentOption.isDialog = true;
            }
            else {
                self.listComponentOption.isDialog = false;
            }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        
        // show No Selection Item
        private showNoSelectionItem(): void {
            var self = this;
            if (!self.listComponentOption.isShowNoSelectRow) {
                self.listComponentOption.isShowNoSelectRow = true;
            } else {
                self.listComponentOption.isShowNoSelectRow = false;
            }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        
        // Selection Type: By Selected code
        private selectBySelectedCode(): void {
            var self = this;
            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectType = SelectType.SELECT_BY_SELECTED_CODE;
                self.listComponentOption.selectedCode = self.multiSelectedCode;
            }
            else {
                self.listComponentOption.selectType = SelectType.SELECT_BY_SELECTED_CODE;
                self.listComponentOption.selectedCode = self.selectedCode;
            }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        // Selection Type: Select All Items
        private selectAllItems(): void {
            var self = this;
            self.listComponentOption.isMultiSelect = true;
            self.listComponentOption.selectType = SelectType.SELECT_ALL;
            self.listComponentOption.selectedCode = self.multiSelectAllItems;
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        // Selection Type: Select First Item
        private selectFirstItems(): void {
            var self = this;
            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectType = SelectType.SELECT_FIRST_ITEM;
                self.listComponentOption.selectedCode = self.multiSelectedCode;
            }
            else {
                self.listComponentOption.selectType = SelectType.SELECT_FIRST_ITEM;
                self.listComponentOption.selectedCode = self.bySelectedCode;
            }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        private bindEmploymentSettingData(data: UnitModel): void {
            var self = this;
            self.clearErrors();
            if (data == undefined) {
                self.code(null);
                self.name(null);
            } else {
                self.code(data.code);
                self.name(data.name);
            }

        }
        private resetComponent() {
            var self = this;
            
        }
        
        private reloadComponent() {
            let self = this;
            self.resetComponent();
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

    }
    

}