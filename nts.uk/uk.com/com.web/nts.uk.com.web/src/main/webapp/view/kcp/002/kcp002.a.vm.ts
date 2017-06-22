module kcp002.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    export class ScreenModel {
        selectedCode: KnockoutObservable<string>;
        selectedCodeNoSetting: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservable<any>;
        multiSelectedCodeNoSetting: KnockoutObservable<any>;
        listComponentOption: ComponentOption;
        listComponentOptionMulti: ComponentOption;
        listComponentNoneSetting: ComponentOption;
        listComponentMultiNoneSetting: ComponentOption;
        listComponentUnSelect: ComponentOption;
        alreadySettingList: KnockoutObservableArray<any>;
        constructor() {
            this.selectedCode = ko.observable('0000000002');
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray(['0000000002', '0000000004']);
            this.multiSelectedCode = ko.observableArray([]);
            this.selectedCodeUnSelect = ko.observable('0000000003');
            this.alreadySettingList = ko.observableArray([{code: '0000000001', isAlreadySetting: true}, {code: '00000000002', isAlreadySetting: true}]);
            this.listComponentOption = {
                isShowAlreadySet: true, // is show already setting column.
                isMultiSelect: false, // is multiselect.
                /**
                 * 1- Employment list.
                 * 2- ???.
                 * 3- Job_title list.
                 * 4- Employee list.
                 */
                listType: ListType.Classification,
                /**
                 * Selected value:
                 * Return type is Array<string> while multiselect.
                 * Return type is String while select.
                 */
                selectType: SelectType.SELECT_BY_SELECTED_CODE;
                isShowNoSelectRow: true,
                selectedCode: this.selectedCode,
                isDialog: true,
                isShowNoSelectRow: false,
                alreadySettingList: this.alreadySettingList
            }
            $('#empt-list-setting').ntsListComponent(this.listComponentOption);


            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.Classification,
                selectType: SelectType.SELECT_ALL;
                selectedCode: this.multiSelectedCode,
                isDialog: true,
                isShowNoSelectRow: false,
                alreadySettingList: this.alreadySettingList
            }
            $('#empt-list-multi-setting').ntsListComponent(this.listComponentOptionMulti);

            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.Classification,
                selectType: SelectType.SELECT_FIRST_ITEM;
                selectedCode: this.selectedCodeNoSetting,
                isDialog: true,
                isShowNoSelectRow: false,

                //                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#empt-list-noSetting').ntsListComponent(this.listComponentNoneSetting);


            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.Classification,
                selectType: SelectType.NO_SELECT;
                selectedCode: this.multiSelectedCodeNoSetting,
                isDialog: true
                isShowNoSelectRow: false,
                //                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#empt-list-multiSelect-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);

            this.listComponentUnSelect = {
                isShowAlreadySet: true,
                isMultiSelect: false,
                listType: ListType.Classification,
                selectType: SelectType.SELECT_FIRST_ITEM;
                selectedCode: this.selectedCodeNoSetting,
                isDialog: true,
                isShowNoSelectRow: true,
                alreadySettingList: this.alreadySettingList
            }
            $('#empt-list-unSelect').ntsListComponent(this.listComponentUnSelect);

        }
        private settingCopiedItem() {
            var self = this;
            
            self.listComponentOption = {
                    isShowAlreadySet: false, // is show already setting column.
                    isMultiSelect: true, // is multiselect.
                    listType: ListType.Classification,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,                 
                    selectedCode: self.selectedCode,
                    isDialog: false,
                    isShowSelectAllButton: false,
                    alreadySettingList: self.alreadySettingList
                }
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
        }

        private settingRegistedItem(): void {
            var self = this;
            self.alreadySettingList.push({ "code": self.selectedCode(), "isAlreadySetting": true });
        }

        private settingDeletedItem() {
            let self = this;
            self.alreadySettingList.remove(self.alreadySettingList().filter((item) => {
                return item.code == self.selectedCode();
            })[0]);
        }
    }