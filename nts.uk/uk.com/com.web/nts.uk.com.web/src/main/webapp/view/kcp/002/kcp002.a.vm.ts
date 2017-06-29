module kcp002.a.viewmodel {
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
        
        hasSelectedClass: KnockoutObservable<boolean>;
        classificationList: KnockoutObservableArray<UnitModel>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        
        selectionTypeList: KnockoutObservableArray<any>;
        selectedType: KnockoutObservable<number>;
        selectionOption: KnockoutObservableArray<any>;
        selectedOption: KnockoutObservable<number>;
        
        constructor() {
            var self = this;
            self.codeEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                width: "90px",
                textmode: "text",
                textalign: "left"
            }));
            self.nameEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                width: "150px",
                textmode: "text",
                textalign: "left"
            }));
            self.selectedCode = ko.observable('0000000001');
            self.bySelectedCode = ko.observable('0000000001');

            self.selectedCode.subscribe(function(data: string) {
                self.bindClassificationSettingData(self.classificationList().filter((item) => {
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
            
            self.multiSelectedCode = ko.observableArray([ '0000000001', '0000000004']);
            self.multiSelectionCode = ko.observableArray([]);
            
            self.alreadySettingList = ko.observableArray([{code: '0000000001', isAlreadySetting: true}, {code: '0000000002', isAlreadySetting: true}]);

            self.code = ko.observable(null);
            self.name = ko.observable(null);
            self.classificationList = ko.observableArray<UnitModel>([]);
            
            self.hasSelectedClass = ko.computed(function() {
                return (self.selectedCode != undefined);
            });

            self.listComponentOption = {
                isShowAlreadySet: self.isAlreadySetting(),
                isMultiSelect: false,
                listType: ListType.Classification,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectionItem(),
                alreadySettingList: self.alreadySettingList
            };
            
            self.selectionTypeList = ko.observableArray([
                {code : 0, name: 'By Selected Code'},
                {code : 1, name: 'Select All Items'},
                {code : 2, name: 'Select First Item'},
                {code : 3, name: 'Select None'}
            ]);
            
            $('#classification-list-setting').ntsListComponent(self.listComponentOption).done(function() {
                if (($('#classification-list-setting').getDataList() == undefined) || ($('#classification-list-setting').getDataList().length <= 0)) {
                    self.hasSelectedClass(false);
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_146" });
                }
                else {
                    self.classificationList($('#classification-list-setting').getDataList());
                    self.bindClassificationSettingData(self.classificationList().filter((item) => {
                        return item.code == self.selectedCode();
                    })[0]);
                }
            });
            
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
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
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
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
        }
        
        private clearErrors(): void {
            $('#code').ntsError('clear');
            $('#name').ntsError('clear');
        }

        private showMultiSelect(): void {
            var self = this;
            self.listComponentOption.isMultiSelect = true;
            if (self.listComponentOption.selectType == SelectType.SELECT_BY_SELECTED_CODE) {
                self.listComponentOption.selectedCode = self.multiSelectedCode;
            } else {
                self.listComponentOption.selectedCode = self.multiSelectionCode;
            }
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
        }
        private showSingleSelect(): void {
            var self = this;
            self.listComponentOption.isMultiSelect = false;
            self.listComponentOption.selectedCode = self.selectedCode;
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
        }

        private selectBySelectedCode(): void {
            var self = this;
            if (self.listComponentOption.isMultiSelect) {
                self.listComponentOption.selectedCode = self.multiSelectedCode;
            }
            else {
                self.listComponentOption.selectedCode = self.bySelectedCode;
            }
            self.listComponentOption.selectType = SelectType.SELECT_BY_SELECTED_CODE;
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
        }

        private selectAllItems(): void {
            var self = this;
            self.listComponentOption.isMultiSelect = true;
            self.listComponentOption.selectType = SelectType.SELECT_ALL;
            self.listComponentOption.selectedCode = self.multiSelectionCode;
            self.selectedOption(1);
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
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
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
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
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
        }
        
        private bindClassificationSettingData(data: UnitModel): void {
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
                self.listComponentOption.listType = ListType.Classification;
                self.listComponentOption.isDialog = self.isDialog();
                self.listComponentOption.isShowNoSelectRow = self.isShowNoSelectionItem();
                self.listComponentOption.alreadySettingList = self.alreadySettingList;
            
            $('#classification-list-setting').ntsListComponent(self.listComponentOption);
        }
    }
}