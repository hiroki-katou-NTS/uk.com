module kcp003.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import UnitAlreadySettingModel = kcp.share.list.UnitAlreadySettingModel;
    export class ScreenModel {
        baseDate: KnockoutObservable<Date>;
        codeEditorOption: KnockoutObservable<any>;
        nameEditorOption: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<string>;
        bySelectedCode: KnockoutObservable<string>;
        isAlreadySetting: KnockoutObservable<boolean>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectionItem: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;

        multiSelectedCode: KnockoutObservableArray<string>;
        multiBySelectedCode: KnockoutObservableArray<string>;

        listComponentOption: ComponentOption;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;

        hasSelectedJobTitle: KnockoutObservable<boolean>;
        jobTitleList: KnockoutObservableArray<UnitModel>;

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
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable(null);
            self.bySelectedCode = ko.observable('1');
            self.isAlreadySetting = ko.observable(false);
            self.isAlreadySetting.subscribe(function() {
                self.reloadComponent();
            });

            self.isDialog = ko.observable(false);
            self.isDialog.subscribe(function(value: boolean) {
                self.reloadComponent();
            });

            self.isShowNoSelectionItem = ko.observable(false);
            self.isShowNoSelectionItem.subscribe(function(data: boolean) {
                self.reloadComponent();
            });

            self.multiBySelectedCode = ko.observableArray(['1', '2']);
            self.multiSelectedCode = ko.observableArray([]);

            self.isMultiSelect = ko.observable(false);
            self.isMultiSelect.subscribe(function(data: boolean) {
                if (data) {
                    if (self.selectedType() == SelectType.SELECT_BY_SELECTED_CODE) {
                        self.listComponentOption.selectedCode = self.multiBySelectedCode;
                    } else {
                        self.listComponentOption.selectedCode = self.multiSelectedCode;
                    }
                } else {
                    if (self.selectedType() == SelectType.SELECT_BY_SELECTED_CODE) {
                        self.listComponentOption.selectedCode = self.bySelectedCode;
                    } else {
                        self.listComponentOption.selectedCode = self.selectedCode;
                    }
                }
                self.reloadComponent();
            });

            self.alreadySettingList = ko.observableArray([{ code: '1', isAlreadySetting: true }, { code: '2', isAlreadySetting: true }]);
            self.jobTitleList = ko.observableArray<UnitModel>([]);
            self.hasSelectedJobTitle = ko.computed(function() {
                return (self.selectedCode != undefined);
            });
            self.selectedType = ko.observable(1);
            self.listComponentOption = {
                baseDate: self.baseDate,
                isShowAlreadySet: self.isAlreadySetting(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.JOB_TITLE,
                selectType: self.selectedType(),
                selectedCode: self.bySelectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectionItem(),
                alreadySettingList: self.alreadySettingList
            };
            // Job Title List...
            $('#component-items-list').ntsListComponent(self.listComponentOption).done(function() {
                if (($('#component-items-list').getDataList() == undefined) || ($('#component-items-list').getDataList().length <= 0)) {
                    self.hasSelectedJobTitle(false);
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_146" });
                }
                else {
                    // Job Title List
                    self.jobTitleList($('#component-items-list').getDataList());
                }
            });

            self.selectionTypeList = ko.observableArray([
                { code: 1, name: 'By Selected Code' },
                { code: 2, name: 'Select All Items' },
                { code: 3, name: 'Select First Item' },
                { code: 4, name: 'Select None' }
            ]);

            self.selectedType.subscribe(function(data: number) {
                switch (data) {
                    case 1:
                        if (self.isMultiSelect()) {
                            self.listComponentOption.selectedCode = self.multiBySelectedCode;
                        }
                        else {
                            self.listComponentOption.selectedCode = self.bySelectedCode;
                        }
                        break;
                    case 2:
                        if (self.isMultiSelect()) {
                            self.listComponentOption.selectedCode = self.multiSelectedCode;
                        }
                        else {
                            self.selectedType(1);
                            nts.uk.ui.dialog.alert("SelectAll is not available for Single selection ! ");
                        }
                        break;
                    case 3:
                        if (self.isMultiSelect()) {
                            self.listComponentOption.selectedCode = self.multiSelectedCode;
                        }
                        else {
                            self.listComponentOption.selectedCode = self.selectedCode;
                        }
                        break;
                    case 4:
                        if (!self.isMultiSelect()) {
                            self.listComponentOption.selectedCode = self.selectedCode;
                        } else {
                            self.listComponentOption.selectedCode = self.multiSelectedCode;
                        }
                        break;
                }
                self.reloadComponent();
            });
            self.selectionOption = ko.observableArray([
                { code: 0, name: 'Single Selection' },
                { code: 1, name: 'Multiple Selection' },
            ]);
            self.selectedOption = ko.observable(0);
            self.selectedOption.subscribe(function(data: number) {
                if (data == 0) {
                    self.isMultiSelect(false);
                }
                else {
                    self.isMultiSelect(true);
                }
            });
        }

        private settingSavedItem(): void {
            var self = this;
            if (self.listComponentOption.selectedCode() != undefined) {
                if (self.listComponentOption.isMultiSelect) {
                    self.listComponentOption.selectedCode().forEach((selected) => {
                        var existItem = self.alreadySettingList().filter((item) => {
                            return item.code == selected;
                        })[0];
                        if (!existItem) {
                            self.alreadySettingList.push({ "code": selected, "isAlreadySetting": true });
                        }
                    });
                } else {
                    var existItem = self.alreadySettingList().filter((item) => {
                        return item.code == self.listComponentOption.selectedCode();
                    })[0];
                    if (!existItem) {
                        self.alreadySettingList.push({ "code": self.listComponentOption.selectedCode(), "isAlreadySetting": true });
                    }
                }
                self.isAlreadySetting(true);
                nts.uk.ui.dialog.alert("Saved Successfully ! ");
            } else {
                nts.uk.ui.dialog.alert("Select Item to Save ! ");
            }
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }

        private settingDeletedItem() {
            let self = this;
            if (self.listComponentOption.selectedCode() != undefined) {
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
                self.isAlreadySetting(true);
                nts.uk.ui.dialog.alert("Deleted Successfully ! ");
            } else {
                nts.uk.ui.dialog.alert("Select Item to Delete ! ");
            }
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }

        private getSelectedItemCode(): string {
            var self = this;
            if (self.isMultiSelect()) {
                if (self.selectedType() == SelectType.SELECT_BY_SELECTED_CODE) {
                    return self.multiBySelectedCode().join(', ');
                } else {
                    return self.multiSelectedCode().join(', ');
                }
            } else {
                if (self.selectedType() == SelectType.SELECT_BY_SELECTED_CODE) {
                    return self.bySelectedCode();
                } else {
                    return self.selectedCode();
                }
            }
        }

        private reloadComponent() {
            var self = this;
            self.listComponentOption.isShowAlreadySet = self.isAlreadySetting();
            self.listComponentOption.listType = ListType.JOB_TITLE;
            self.listComponentOption.isDialog = self.isDialog();
            self.listComponentOption.isShowNoSelectRow = self.isShowNoSelectionItem();
            self.listComponentOption.alreadySettingList = self.alreadySettingList;
            self.listComponentOption.isMultiSelect = self.isMultiSelect();
            self.listComponentOption.selectType = self.selectedType();

            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }
    }
}