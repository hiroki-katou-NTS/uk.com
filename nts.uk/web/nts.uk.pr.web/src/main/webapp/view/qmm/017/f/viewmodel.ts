module nts.qmm017 {
    
    export class ListBoxF {
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<number>;
        selectedCodes: KnockoutObservableArray<number>;
        isEnable: KnockoutObservable<boolean>;

        constructor(data) {
            var self = this;
            self.itemList = ko.observableArray(data);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(null)
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);

            $('#list-box-f').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#list-box-f').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
        }
    }

    export class FScreen {
        fList001: KnockoutObservable<ListBoxF>;
        fList002: KnockoutObservable<ListBoxF>;

        constructor() {
            var fList001 = [
            ];
            var fList002 = [
            ];
            self.fList001 = ko.observable(new ListBoxF(fList001));
            self.fList002 = ko.observable(new ListBoxF(fList002));

        }
    }
}