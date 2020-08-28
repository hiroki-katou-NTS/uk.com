module nts.uk.at.view.kaf022.k.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelK {
        itemListD15: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_391')},
            {code: 1, name: text('KAF022_392')}
        ]);
        itemListD13: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_389')},
            {code: 0, name: text('KAF022_390')}
        ]);
        selectedIdD15: KnockoutObservable<number>;
        selectedIdD13: KnockoutObservable<number>;

        texteditorD9: KnockoutObservable<string>;
        valueD10: KnockoutObservable<string>;
        enableD11: KnockoutObservable<boolean>;

        texteditorD12: KnockoutObservable<string>;
        valueD10_1: KnockoutObservable<string>;
        enableD11_1: KnockoutObservable<boolean>;

        constructor() {
            const self = this;
            self.selectedIdD15 = ko.observable(0);
            self.selectedIdD13 = ko.observable(0);

            self.texteditorD9 = ko.observable(null);
            self.valueD10 = ko.observable(null);
            self.enableD11 = ko.observable(false);

            self.texteditorD12 = ko.observable(null);
            self.valueD10_1 = ko.observable(null);
            self.enableD11_1 = ko.observable(false);

            $("#fixed-table-j1").ntsFixedTable({});
            $("#fixed-table-j2").ntsFixedTable({});
            $("#fixed-table-j3").ntsFixedTable({});
            $("#fixed-table-j4").ntsFixedTable({});
            $("#fixed-table-j5").ntsFixedTable({});
            $("#fixed-table-j6").ntsFixedTable({});
            $("#fixed-table-j7").ntsFixedTable({});
            $("#fixed-table-j8").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            let data = allData.appChange;
            if (data) {
                self.selectedIdD15(data.initDisplayWorktime);
                self.selectedIdD13(data.workChangeTimeAtr);
                self.texteditorD9(data.commentContent1);
                self.valueD10(data.commentFontColor1);
                self.enableD11(data.commentFontWeight1);
                self.texteditorD12(data.commentContent2);
                self.valueD10_1(data.commentFontColor2);
                self.enableD11_1(data.commentFontWeight2);
            }
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