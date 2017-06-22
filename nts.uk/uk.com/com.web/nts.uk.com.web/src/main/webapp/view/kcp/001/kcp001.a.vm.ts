module kcp001.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    export class ScreenModel {
        selectedCode: KnockoutObservable<string>;
        selectedCodeNoSetting: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<any>;
        multiSelectedCodeNoSetting: KnockoutObservableArray<any>;
        selectedCodeUnSelect: KnockoutObservable<string>;
        
        listComponentOption: ComponentOption;
        listComponentOptionMulti: ComponentOption;
        listComponentNoneSetting: ComponentOption;
        listComponentMultiNoneSetting: ComponentOption;
        listComponentUnSelect: ComponentOption;
        alreadySettingList: KnockoutObservableArray<any>;
        
        constructor() {
            this.selectedCode = ko.observable('02');
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray([]);
            this.multiSelectedCode = ko.observableArray([]);
            this.selectedCodeUnSelect = ko.observable('03');
            this.alreadySettingList = ko.observableArray([{code: '01', isAlreadySetting: true}, , {code: '02', isAlreadySetting: true}]);
            this.listComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    /**
                     * 1- Employment list.
                     * 2- ???.
                     * 3- Job_title list.
                     * 4- Employee list.
                     */
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    /**
                     * Selected value:
                     * Return type is Array<string> while multiselect.
                     * Return type is String while select.
                     */
                    selectedCode: this.selectedCode,
                    isDialog: true,
                    isShowNoSelectRow: false,
                    alreadySettingList: this.alreadySettingList
                };
            $('#empt-list-setting').ntsListComponent(this.listComponentOption);
            
            
            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.EMPLOYMENT,
                selectType: SelectType.SELECT_ALL,
                selectedCode: this.multiSelectedCode,
                isDialog: true,
                isShowNoSelectRow: false,
                alreadySettingList: this.alreadySettingList
            };
            $('#empt-list-multi-setting').ntsListComponent(this.listComponentOptionMulti);
            
            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYMENT,
                selectType: SelectType.SELECT_FIRST_ITEM,
                selectedCode: this.selectedCodeNoSetting,
                isDialog: true,
                isShowNoSelectRow: false
            };
            $('#empt-list-noSetting').ntsListComponent(this.listComponentNoneSetting);
            
            
            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYMENT,
                selectType: SelectType.NO_SELECT,
                selectedCode: this.multiSelectedCodeNoSetting,
                isDialog: true,
                isShowNoSelectRow: false
            };
            $('#empt-list-multiSelect-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);
            
            this.listComponentUnSelect = {
                isShowAlreadySet: true,
                isMultiSelect: false, 
                listType: ListType.EMPLOYMENT,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: this.selectedCodeUnSelect,
                isDialog: true,
                isShowNoSelectRow: true,
                alreadySettingList: this.alreadySettingList
            }
            $('#empt-list-unSelect').ntsListComponent(this.listComponentUnSelect);
            
        }
        
        private setAlreadyCheck(): void {
            var self = this;
            self.alreadySettingList.push({"code": self.selectedCode(), "isAlreadySetting": true});
        }
        
        private settingRegistedItem(): void {
            var self = this;
            self.alreadySettingList.push({"code": self.selectedCode(), "isAlreadySetting": true});
        }
        
        private settingDeletedItem() {
            let self = this;
            self.alreadySettingList.remove(self.alreadySettingList().filter((item) => {
                return item.code == self.selectedCode();
            })[0]);
        }
        
        
    }
}