module kcp001.a.viewmodel {
    export class ScreenModel {
        selectedCode: KnockoutObservableArray<string>;
        constructor() {
            this.selectedCode = ko.observableArray([]);
            this.selectedCode.subscribe(function(newVal) {
                alert(newVal.length);
            })
        } 
    }
}