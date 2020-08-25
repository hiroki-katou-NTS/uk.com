module nts.uk.at.view.kaf022.c.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelC {
        itemListC27: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_100')},
            {code: 0, name: text('KAF022_101')},
            {code: 2, name: text('KAF022_171')}
        ]);
        itemListC51: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_36')},
            {code: 0, name: text('KAF022_37')}
        ]);
        itemListC48: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_420')},
            {code: 0, name: text('KAF022_421')}
        ]);
        itemListC29: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_173')},
            {code: 1, name: text('KAF022_174')},
            {code: 2, name: text('KAF022_175')},
        ]);
        itemListC30: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_173')},
            {code: 1, name: text('KAF022_175')},
        ]);

        itemListCC1: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_75')},
            {code: 1, name: text('KAF022_82')},
        ]);

        itemListC28: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_75')},
            {code: 1, name: text('KAF022_82')},
        ]);

        selectedIdC51: KnockoutObservable<number>;
        selectedIdC48: KnockoutObservable<number>;
        selectedIdC38: KnockoutObservable<number>;
        selectedIdC39: KnockoutObservable<number>;
        selectedIdC40: KnockoutObservable<number>;
        selectedIdC27: KnockoutObservable<number>;
        selectedIdC28: KnockoutObservable<number>;
        selectedIdC182: KnockoutObservable<number>;

        selectedIdC29: KnockoutObservable<number>;
        selectedIdC30: KnockoutObservable<number>;
        selectedIdC31: KnockoutObservable<number>;
        selectedIdC32: KnockoutObservable<number>;
        selectedIdC33: KnockoutObservable<number>;
        selectedIdC34: KnockoutObservable<number>;
        selectedIdC35: KnockoutObservable<number>;
        selectedIdC36: KnockoutObservable<number>;
        selectedIdC37: KnockoutObservable<number>;
        selectedIdC49: KnockoutObservable<number>;

        texteditorC41: KnockoutObservable<string>;
        texteditorC42: KnockoutObservable<string>;
        texteditorC43: KnockoutObservable<string>;
        texteditorC44: KnockoutObservable<string>;
        texteditorC45: KnockoutObservable<string>;
        texteditorC46: KnockoutObservable<string>;
        texteditorC47: KnockoutObservable<string>;
        texteditorC51: KnockoutObservable<string>;

        selectedIdCC1: KnockoutObservable<number>;

        constructor() {
            const self = this;
            self.selectedIdC51 = ko.observable(0);
            self.selectedIdC48 = ko.observable(0);
            self.selectedIdC38 = ko.observable(0);
            self.selectedIdC39 = ko.observable(0);
            self.selectedIdC40 = ko.observable(0);
            self.selectedIdC27 = ko.observable(0);
            self.selectedIdC28 = ko.observable(0);
            self.selectedIdC182 = ko.observable(0);

            self.selectedIdC29 = ko.observable(0);
            self.selectedIdC30 = ko.observable(0);
            self.selectedIdC31 = ko.observable(0);
            self.selectedIdC32 = ko.observable(0);
            self.selectedIdC33 = ko.observable(0);
            self.selectedIdC34 = ko.observable(0);
            self.selectedIdC35 = ko.observable(0);
            self.selectedIdC36 = ko.observable(0);
            self.selectedIdC37 = ko.observable(0);
            self.selectedIdC49 = ko.observable(0);

            self.texteditorC41 = ko.observable(null);
            self.texteditorC42 = ko.observable(null);
            self.texteditorC43 = ko.observable(null);
            self.texteditorC44 = ko.observable(null);
            self.texteditorC45 = ko.observable(null);
            self.texteditorC46 = ko.observable(null);
            self.texteditorC47 = ko.observable(null);
            self.texteditorC51 = ko.observable(null);

            self.selectedIdCC1 = ko.observable(0);

            $("#fixed-table-c1").ntsFixedTable({});
            $("#fixed-table-c2").ntsFixedTable({});
            $("#fixed-table-c3").ntsFixedTable({});
            $("#fixed-table-c4").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;

        }

    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}