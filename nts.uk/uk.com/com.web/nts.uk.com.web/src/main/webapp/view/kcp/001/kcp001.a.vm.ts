module kcp001.a.viewmodel {
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
        
        constructor() {
            this.selectedCode = ko.observable(null);
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray([]);
            this.multiSelectedCode = ko.observableArray([]);
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
                    /**
                     * Selected value:
                     * Return type is Array<string> while multiselect.
                     * Return type is String while select.
                     */
                    selectedCode: this.selectedCode,
                    alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}])
                }
            $('#empt-list-setting').ntsListComponent(this.listComponentOption);
            
            
            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.EMPLOYMENT,
                selectedCode: this.multiSelectedCode,
                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#empt-list-multi-setting').ntsListComponent(this.listComponentOptionMulti);
            
            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYMENT,
                selectedCode: this.selectedCodeNoSetting,
//                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#empt-list-noSetting').ntsListComponent(this.listComponentNoneSetting);
            
            
            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYMENT,
                selectedCode: this.multiSelectedCodeNoSetting,
//                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#empt-list-multiSelect-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);
            
        } 
    }
}