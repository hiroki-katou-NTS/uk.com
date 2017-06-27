module kcp001test.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import UnitAlreadySettingModel = kcp.share.list.UnitAlreadySettingModel;
    export class ScreenModel {
        codeEditorOption: KnockoutObservable<any>;
        nameEditorOption: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<string>;
        bySelectedCode: KnockoutObservable<string>;
        isAlreadySetting: KnockoutObservable<boolean>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectionItem: KnockoutObservable<boolean>;
        
        multiSelectionCode: KnockoutObservableArray<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        
        listComponentOption: ComponentOption;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        
        hasSelectedEmp: KnockoutObservable<boolean>;
        employmentList: KnockoutObservableArray<UnitModel>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        
        selectionTypeList: KnockoutObservableArray<any>;
        selectedType: KnockoutObservable<number>;
        selectionOption: KnockoutObservableArray<any>;
        selectedOption: KnockoutObservable<number>;
        
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
            self.selectedCode = ko.observable('1');
            self.bySelectedCode = ko.observable('1');
            // Selected Item subscribe
            self.selectedCode.subscribe(function(data: string) {
                self.bindEmploymentSettingData(self.employmentList().filter((item) => {
                    return item.code == self.selectedCode();
                })[0]);
            });
            self.isAlreadySetting = ko.observable(false);
            self.isAlreadySetting.subscribe(function() {
                self.reloadComponent();
            });
            
            self.isDialog = ko.observable(false);
            self.isDialog.subscribe(function(value: boolean) {
                self.reloadComponent();
            });
            
            self.isShowNoSelectionItem = ko.observable(false);
            self.isShowNoSelectionItem.subscribe(function() {
                self.reloadComponent();
            });
            
            self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
            self.multiSelectionCode = ko.observableArray([]);
            
            self.alreadySettingList = ko.observableArray([{code: '1', isAlreadySetting: true}, {code: '2', isAlreadySetting: true}]);

            self.code = ko.observable(null);
            self.name = ko.observable(null);
            self.employmentList = ko.observableArray<UnitModel>([]);
            
            self.hasSelectedEmp = ko.computed(function() {
                return (self.selectedCode != undefined);
            });

            self.listComponentOption = {
                isShowAlreadySet: self.isAlreadySetting(),
                isMultiSelect: false,
                listType: ListType.EMPLOYMENT,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectionItem(),
                alreadySettingList: self.alreadySettingList
            };
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
                    case 3:
                        self.selectNone();
                        break;
                }
            });
            self.selectionOption = ko.observableArray([
                {code : 0, name: 'Single Selection'},
                {code : 1, name: 'Multiple Selection'},
            ]);
            self.selectedOption = ko.observable(0);
            self.selectedOption.subscribe(function(data: number) {
                if (data == 0) {
                    self.showSingleSelect();
                }
                else {
                    self.showMultiSelect();
                    
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

            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectedCode().forEach((selected) => {
                    var existItem = self.alreadySettingList().filter((item) => {
                        return item.code == selected;
                    })[0];
                    if (!existItem) {
                        self.alreadySettingList.push({"code": selected, "isAlreadySetting": true});
                    }
                });
            } else {
                var existItem = self.alreadySettingList().filter((item) => {
                    return item.code == self.listComponentOption.selectedCode();
                })[0];
                if (!existItem) {
                    self.alreadySettingList.push({ "code": self.selectedCode(), "isAlreadySetting": true });
                }
            }
            
            self.isAlreadySetting(true);
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        
        private settingDeletedItem() {
            let self = this;
            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectedCode().forEach((selected) => {
                    self.alreadySettingList.remove(self.alreadySettingList().filter((item) => {
                        return item.code == selected;
                    })[0]);
                });
            } else {
                self.alreadySettingList.remove(self.alreadySettingList().filter((item) => {
                    return item.code == self.listComponentOption.selectedCode();
                })[0]);
            }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        
        private clearErrors(): void {
            // Clear errors
            $('#code').ntsError('clear');
            $('#name').ntsError('clear');
        }

        // Show MultiSelection
        private showMultiSelect(): void {
            var self = this;
            self.listComponentOption.isMultiSelect = true;
            //                self.code(null);
            //                self.name(null);
            if (self.listComponentOption.selectType == SelectType.SELECT_BY_SELECTED_CODE) {
                self.listComponentOption.selectedCode = self.multiSelectedCode;
            } else {
                self.listComponentOption.selectedCode = self.multiSelectionCode;
            }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
        private showSingleSelect(): void {
            var self = this;
            self.listComponentOption.isMultiSelect = false;
            self.listComponentOption.selectedCode = self.selectedCode;
            // Binding Data to right content
            //                self.bindEmploymentSettingData(self.employmentList().filter((item) => {
            //                    return item.code == self.selectedCode();
            //                })[0]);
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        // Selection Type: By Selected code
        private selectBySelectedCode(): void {
            var self = this;
            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectedCode = self.multiSelectedCode;
            }
            else {
                self.listComponentOption.selectedCode = self.bySelectedCode;
            }
            self.listComponentOption.selectType = SelectType.SELECT_BY_SELECTED_CODE;
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        // Selection Type: Select All Items
        private selectAllItems(): void {
            var self = this;
            self.listComponentOption.isMultiSelect = true;
            self.listComponentOption.selectType = SelectType.SELECT_ALL;
            self.listComponentOption.selectedCode = self.multiSelectionCode;
            self.selectedOption(1);
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        // Selection Type: Select First Item
        private selectFirstItems(): void {
            var self = this;
            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectedCode = self.multiSelectionCode;
            }
            else {
                self.listComponentOption.selectedCode = self.selectedCode;
            }
            self.listComponentOption.selectType = SelectType.SELECT_FIRST_ITEM;
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        // Selection Type: Select None
        private selectNone(): void {
            var self = this;
            if (!self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectedCode = self.selectedCode;
            } else {
                self.listComponentOption.selectedCode = self.multiSelectionCode;
            }
            self.listComponentOption.selectType = SelectType.NO_SELECT;
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
        
        private reloadComponent() {
            var self = this;
                self.listComponentOption.isShowAlreadySet = self.isAlreadySetting();
                self.listComponentOption.listType = ListType.EMPLOYMENT;
                self.listComponentOption.isDialog = self.isDialog();
                self.listComponentOption.isShowNoSelectRow = self.isShowNoSelectionItem();
                self.listComponentOption.alreadySettingList = self.alreadySettingList;
            
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }
    }
}