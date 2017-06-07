module kcp001.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    export class ScreenModel {
        selectedCode: KnockoutObservable<string>;
        listComponentOption: ComponentOption
        constructor() {
            this.selectedCode = ko.observable(null);
            this.selectedCode.subscribe(function(newVal) {
                alert(newVal);
            });
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
                    alreadySettingList: ko.observableArray([{code: 'EMC1', isAlreadySetting: true}])
                }
            $('#employment-list').ntsListComponent(this.listComponentOption);
        } 
    }
}