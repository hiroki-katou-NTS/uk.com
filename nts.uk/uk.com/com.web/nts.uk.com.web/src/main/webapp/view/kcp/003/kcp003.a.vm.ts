module kcp003.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
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
//        baseDate: KnockoutObservable<string>;
        
        constructor() {
            this.selectedCode = ko.observable('2');
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray(['2', '4']);
            this.multiSelectedCode = ko.observableArray([]);
            this.baseDate = ko.observable(new Date());
            this.listComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    /**
                     * 1- Employment list.
                     * 2- ???.
                     * 3- Job_title list.
                     * 4- Employee list.
                     */
                    listType: ListType.JOB_TITLE,
                    /**
                     * Selected value:
                     * Return type is Array<string> while multiselect.
                     * Return type is String while select.
                     */
                    selectedCode: this.selectedCode,
                    isDialog: false,
                    baseDate: this.baseDate,
                    alreadySettingList: ko.observableArray([{code: '1', isAlreadySetting: true}])
                }
            $('#jobTitle-setting').ntsListComponent(this.listComponentOption);
            
            
            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.JOB_TITLE,
                selectedCode: this.multiSelectedCode,
                isDialog: false,
                baseDate: this.baseDate,
                alreadySettingList: ko.observableArray([{code: '1', isAlreadySetting: true}, {code: '2', isAlreadySetting: true}])
            }
            $('#jobTitle-multi-setting').ntsListComponent(this.listComponentOptionMulti);
            
            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.JOB_TITLE,
                selectedCode: this.selectedCodeNoSetting,
                isDialog: false,
                baseDate: this.baseDate,
            }
            $('#jobTitle-noSetting').ntsListComponent(this.listComponentNoneSetting);
            
            
            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.JOB_TITLE,
                selectedCode: this.multiSelectedCodeNoSetting,
                isDialog: false,
                baseDate: this.baseDate,
            }
            $('#jobTitle-multiSelect-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);
            
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
}