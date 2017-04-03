module nts.qmm017 {
    export class RScreen {
        listBoxItemType: KnockoutObservable<ListBox>;
        listBoxItems: KnockoutObservable<ListBox>;

        constructor() {
            var self = this;
            var rList001 = [
                { code: '1', name: '会社単価項目（会社単価＠）' },
                { code: '2', name: '個人単価項目（個人単価＠）' }
            ];
            self.listBoxItemType = ko.observable(new ListBox(rList001));
            self.listBoxItems = ko.observable(new ListBox([]));
            self.listBoxItemType().selectedCode.subscribe(function(codeChange){

            });
        }
    }
}