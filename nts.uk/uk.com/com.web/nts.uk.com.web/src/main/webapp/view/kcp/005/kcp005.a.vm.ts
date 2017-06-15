module kcp005.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import UnitModel = kcp.share.list.UnitModel;
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
        employeeList: Array<UnitModel>
        
        constructor() {
            this.selectedCode = ko.observable('02');
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray(['02', '04']);
            this.multiSelectedCode = ko.observableArray([]);
            this.baseDate = ko.observable(new Date());
            this.employeeList = ([{code: '01', name: 'Angela Baby', workplaceName: 'HN'},
                    {code: '02', name: 'Angela Phuong Trinh', workplaceName: 'HN'},
                    {code: '03', name: 'Angela Linh Tinh', workplaceName: 'HCM'},
                    {code: '04', name: 'Min', workplaceName: 'HN'}
                ]);
            this.listComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    listType: ListType.EMPLOYEE,
                    employeeInputList: this.employeeList,
                    selectedCode: this.selectedCode,
                    isDialog: true,
                    baseDate: this.baseDate,
                    alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}])
                }
            $('#employee-multi-setting').ntsListComponent(this.listComponentOption);
            
            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: this.employeeList,
                selectedCode: this.multiSelectedCode,
                isDialog: true,
                baseDate: this.baseDate,
                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#employee-setting').ntsListComponent(this.listComponentOptionMulti);
            
            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                employeeInputList: this.employeeList,
                selectedCode: this.selectedCodeNoSetting,
                isDialog: true,
                baseDate: this.baseDate,
            }
            $('#employee-multiSelect-noSetting').ntsListComponent(this.listComponentNoneSetting);
            
            
            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: this.employeeList,
                selectedCode: this.multiSelectedCodeNoSetting,
                isDialog: true,
                baseDate: this.baseDate,
            }
            $('#employee-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);
            
        }
        
        private settingRegistedItem() {
            var self = this;
            self.listComponentOption.alreadySettingList.push({"code": this.selectedCode().toString(), "isAlreadySetting": true});
        }
        
        private settingDeletedItem() {
            let self = this;
            self.listComponentOption.alreadySettingList.remove(function(item) {
                return item.code == self.selectedCode();
            });
        }
        
        
    }
//    
//    export class EmployeeModel {
//        code: string;
//        name: string;
//        workplace: string;
//        
//        constructor(code: string, name: string, workplace: string) {
//            this.code = code;
//            this.name = name;
//            this.workplace = workplace;
//        }
//    }
}