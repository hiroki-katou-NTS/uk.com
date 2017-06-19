module kcp005.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import UnitModel = kcp.share.list.UnitModel;
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
        baseDate: KnockoutObservable<Date>;
        employeeList: KnockoutObservableArray<UnitModel>;
        alreadySettingList: KnockoutObservableArray<any>;
        
        constructor() {
            this.selectedCode = ko.observable('02');
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray([]);
            this.multiSelectedCode = ko.observableArray([]);
            this.baseDate = ko.observable(new Date());
            this.employeeList = ko.observableArray<UnitModel>([{code: '01', name: 'Angela Baby', workplaceName: 'HN'},
                    {code: '02', name: 'Angela Phuong Trinh', workplaceName: 'HN'},
                    {code: '03', name: 'Angela Linh Tinh', workplaceName: 'HCM'},
                    {code: '04', name: 'Min', workplaceName: 'HN'}
                ]);
            this.alreadySettingList = ko.observableArray([{code: '01', isAlreadySetting: true}, , {code: '02', isAlreadySetting: true}]);
            this.listComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    listType: ListType.EMPLOYEE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    employeeInputList: this.employeeList,
                    isShowWorkPlaceName: false,
                    selectedCode: this.selectedCode,
                    isDialog: false,
                    baseDate: this.baseDate,
                    isShowSelectAllButton: false,
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
                baseDate: this.baseDate,
                isShowSelectAllButton: false,
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
                baseDate: this.baseDate,
                isShowSelectAllButton: true,
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
                baseDate: this.baseDate,
                isShowSelectAllButton: false,
            }
            $('#employee-noSetting').ntsListComponent(this.listComponentNoneSetting);
            
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
            
            self.listComponentOption = {
                    isShowAlreadySet: false, // is show already setting column.
                    isMultiSelect: true, // is multiselect.
                    listType: ListType.EMPLOYEE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    employeeInputList: self.employeeList,
                    isShowWorkPlaceName: false,
                    selectedCode: self.selectedCode,
                    isDialog: false,
                    baseDate: self.baseDate,
                    isShowSelectAllButton: false,
                    alreadySettingList: self.alreadySettingList
                }
            $('#employee-setting').ntsListComponent(self.listComponentOption);
        }
    }

}