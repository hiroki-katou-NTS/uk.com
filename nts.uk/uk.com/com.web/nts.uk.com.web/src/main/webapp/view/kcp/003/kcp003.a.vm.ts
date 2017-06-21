module kcp003.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    export class ScreenModel {
        selectedCode: KnockoutObservable<string>;
        selectedCodeNoSetting: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservable<any>;
        multiSelectedCodeNoSetting: KnockoutObservable<any>;
        selectedCodeUnSelect: KnockoutObservable<string>;
        
        listComponentOption: ComponentOption;
        listComponentOptionMulti: ComponentOption;
        listComponentNoneSetting: ComponentOption;
        listComponentMultiNoneSetting: ComponentOption;
        baseDate: KnockoutObservable<Date>;
        listComponentUnSelect: ComponentOption;
        alreadySettingList: KnockoutObservableArray<any>;
        
        constructor() {
            this.selectedCode = ko.observable('2');
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray([]);
            this.multiSelectedCode = ko.observableArray([]);
            this.baseDate = ko.observable(new Date());
            this.selectedCodeUnSelect = ko.observable('3');
            this.alreadySettingList = ko.observableArray([{code: '1', isAlreadySetting: true}, , {code: '2', isAlreadySetting: true}]);
            this.listComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    listType: ListType.JOB_TITLE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: this.selectedCode,
                    isDialog: true,
                    baseDate: this.baseDate,
                    alreadySettingList: this.alreadySettingList
                }
            $('#jobTitle-setting').ntsListComponent(this.listComponentOption);
            
            
            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.JOB_TITLE,
                selectType: SelectType.SELECT_ALL,
                selectedCode: this.multiSelectedCode,
                isDialog: false,
                baseDate: this.baseDate,
                alreadySettingList: this.alreadySettingList
            }
            $('#jobTitle-multi-setting').ntsListComponent(this.listComponentOptionMulti);
            
            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.JOB_TITLE,
                selectType: SelectType.SELECT_FIRST_ITEM,
                selectedCode: this.selectedCodeNoSetting,
                isDialog: false,
                baseDate: this.baseDate,
            }
            $('#jobTitle-noSetting').ntsListComponent(this.listComponentNoneSetting);
            
            
            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.JOB_TITLE,
                selectType: SelectType.NO_SELECT,
                selectedCode: this.multiSelectedCodeNoSetting,
                isDialog: true,
                baseDate: this.baseDate,
            }
            $('#jobTitle-multiSelect-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);
            
            this.listComponentUnSelect = {
                isShowAlreadySet: true,
                isMultiSelect: false, 
                listType: ListType.JOB_TITLE,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: this.selectedCodeUnSelect,
                isDialog: false,
                isShowNoSelectRow: true,
                alreadySettingList: this.alreadySettingList,
                baseDate: this.baseDate,
            }
            $('#jobTitle-list-unSelect').ntsListComponent(this.listComponentUnSelect);
            
        }
        
        private settingRegistedItem() {
            var self = this;
            self.alreadySettingList.push({"code": self.selectedCode().toString(), "isAlreadySetting": true});
        }
        
        private settingDeletedItem() {
            let self = this;
            self.alreadySettingList.remove(self.alreadySettingList().filter((item) => {
                return item.code == self.selectedCode();
            })[0]);
        }
        
        
    }
}