module kcp005.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import UnitModel = kcp.share.list.UnitModel;
    import SelectType = kcp.share.list.SelectType;
    export class ScreenModel {
        selectedCode: KnockoutObservable<string>;
        selectedCodeNoSetting: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        multiSelectedCodeNoSetting: KnockoutObservableArray<string>;
        selectedCodeUnSelect: KnockoutObservable<string>;
        copySelectedCode: KnockoutObservableArray<string>;
        
        listComponentOption: ComponentOption;
        listComponentOptionMulti: ComponentOption;
        listComponentNoneSetting: ComponentOption;
        listComponentMultiNoneSetting: ComponentOption;
        listComponentUnSelect: ComponentOption;
        
        employeeList: KnockoutObservableArray<UnitModel>;
        alreadySettingList: KnockoutObservableArray<any>;
        
        constructor() {
            this.selectedCode = ko.observable('02');
            this.selectedCodeNoSetting = ko.observable(null);
            this.selectedCodeUnSelect = ko.observable('03');
            this.multiSelectedCodeNoSetting = ko.observableArray([]);
            this.multiSelectedCode = ko.observableArray(['02', '04']);
            this.employeeList = ko.observableArray<UnitModel>([{code: '01', name: 'Angela Baby', workplaceName: 'HN'},
                    {code: '02', name: 'Angela Phuong Trinh', workplaceName: 'HN'},
                    {code: '03', name: 'Angela Linh Tinh', workplaceName: 'HCM'},
                    {code: '04', name: 'Min', workplaceName: 'HN'}
                ]);
            this.alreadySettingList = ko.observableArray([{code: '01', isAlreadySetting: true}, , {code: '02', isAlreadySetting: true}]);
            this.copySelectedCode = ko.observableArray(['02', '04']);
            this.listComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    listType: ListType.EMPLOYEE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    employeeInputList: this.employeeList,
                    isShowWorkPlaceName: false,
                    selectedCode: this.selectedCode,
                    isDialog: false,
                    isShowSelectAllButton: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: this.alreadySettingList
                    
                }
            $('#employee-setting').ntsListComponent(this.listComponentOption);
            
            
            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                selectType: SelectType.SELECT_ALL,
                isShowWorkPlaceName: true,
                employeeInputList: this.employeeList,
                selectedCode: this.multiSelectedCode,
                isDialog: true,
                isShowSelectAllButton: false,
                isShowNoSelectRow: false,
                alreadySettingList: this.alreadySettingList
            }
            $('#employee-multi-setting').ntsListComponent(this.listComponentOptionMulti);
            
            
            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                selectType: SelectType.NO_SELECT,
                isShowWorkPlaceName: true,
                employeeInputList: this.employeeList,
                selectedCode: this.multiSelectedCodeNoSetting,
                isDialog: true,
                isShowSelectAllButton: true,
                isShowNoSelectRow: false,
            }
            $('#employee-multiSelect-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);
            
            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                selectType: SelectType.SELECT_FIRST_ITEM,
                isShowWorkPlaceName: true,
                employeeInputList: this.employeeList,
                selectedCode: this.selectedCodeNoSetting,
                isDialog: true,
                isShowSelectAllButton: false,
                isShowNoSelectRow: false,
            }
            $('#employee-noSetting').ntsListComponent(this.listComponentNoneSetting);
            
            this.listComponentUnSelect = {
                isShowAlreadySet: true,
                isMultiSelect: false, 
                listType: ListType.EMPLOYEE,
                employeeInputList: this.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: this.selectedCodeUnSelect,
                isDialog: true,
                isShowNoSelectRow: true,
                alreadySettingList: this.alreadySettingList
            }
            $('#employee-list-unSelect').ntsListComponent(this.listComponentUnSelect);
            
        }
        
        private settingRegistedItem() {
            var self = this;
            self.alreadySettingList.push({"code": self.selectedCode(), "isAlreadySetting": true});
        }
        
        private settingDeletedItem() {
            let self = this;
            self.alreadySettingList.remove(self.alreadySettingList().filter((item) => {
                return item.code == self.selectedCode();
            })[0]);
        }
        
        private settingCopiedItem() {
            var self = this;
            self.listComponentOption.isMultiSelect = true;
            self.listComponentOption.selectedCode = self.copySelectedCode;
            $('#employee-setting').ntsListComponent(self.listComponentOption);
        }
    }

}