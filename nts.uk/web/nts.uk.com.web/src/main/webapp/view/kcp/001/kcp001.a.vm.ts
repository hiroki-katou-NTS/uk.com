module kcp001.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    export class ScreenModel {
        selectedCode: KnockoutObservable<string>;
        listComponentOption: ComponentOption
        constructor() {
            this.selectedCode = ko.observable(null);
            this.selectedCode.subscribe(function(newVal) {
                alert(newVal);
            });
            this.listComponentOption = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: 1,
                    selectedCode: this.selectedCode,
                    alreadySettingList: ko.observableArray([{code: 'EMC1', isAlreadySetting: true}])
                }
            $('#employment-list').ntsListComponent(this.listComponentOption);
        } 
    }
}