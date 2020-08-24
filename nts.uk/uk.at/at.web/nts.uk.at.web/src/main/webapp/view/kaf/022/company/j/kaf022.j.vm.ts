module nts.uk.at.view.kaf022.j.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelJ {
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

        cancelAtr: KnockoutObservable<number>;
        stampTypeSettings: KnockoutObservableArray<StampSetting>;

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

            self.cancelAtr = ko.observable(0);
            self.stampTypeSettings = ko.observableArray([
                new StampSetting(0, text("KAF022_246"), text("KAF022_703"), text("KAF022_256"), text("KAF022_257"), 0, {comment: "", colorCode: "", bold: false}, {comment: "", colorCode: "", bold: false}),
                new StampSetting(1, text("KAF022_246"), text("KAF022_703"), text("KAF022_256"), text("KAF022_257"), 0, {comment: "", colorCode: "", bold: false}, {comment: "", colorCode: "", bold: false}),
                new StampSetting(2, text("KAF022_246"), text("KAF022_703"), text("KAF022_256"), text("KAF022_257"), 0, {comment: "", colorCode: "", bold: false}, {comment: "", colorCode: "", bold: false}),
                new StampSetting(3, text("KAF022_246"), text("KAF022_703"), text("KAF022_256"), text("KAF022_257"), 0, {comment: "", colorCode: "", bold: false}, {comment: "", colorCode: "", bold: false}),
                new StampSetting(4, text("KAF022_246"), text("KAF022_703"), text("KAF022_256"), text("KAF022_257"), 0, {comment: "", colorCode: "", bold: false}, {comment: "", colorCode: "", bold: false}),
                new StampSetting(5, text("KAF022_246"), text("KAF022_703"), text("KAF022_256"), text("KAF022_257"), 0, {comment: "", colorCode: "", bold: false}, {comment: "", colorCode: "", bold: false}),
                new StampSetting(6, text("KAF022_246"), text("KAF022_703"), text("KAF022_256"), text("KAF022_257"), 0, {comment: "", colorCode: "", bold: false}, {comment: "", colorCode: "", bold: false})
            ]);

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
            let data = allData.appStampSetting;
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

    class StampSetting {
        stampAtr: number;
        headerText: string;
        reflectText: string;
        comment1Text: string;
        comment2Text: string;
        reflectAtr: KnockoutObservable<number>;
        comment1Content: KnockoutObservable<string>;
        comment1Color: KnockoutObservable<string>;
        comment1FontWeight: KnockoutObservable<boolean>;
        comment2Content: KnockoutObservable<string>;
        comment2Color: KnockoutObservable<string>;
        comment2FontWeight: KnockoutObservable<boolean>;
        constructor(stampAtr: number, headerText: string, reflectText: string, comment1Text: string, comment2Text: string, reflectAtr: number, comment1: any, comment2: any) {
            this.stampAtr = stampAtr;
            this.headerText = headerText;
            this.reflectText = reflectText;
            this.comment1Text = comment1Text;
            this.comment2Text = comment2Text;
            this.reflectAtr = ko.observable(reflectAtr || 0);
            this.comment1Content = ko.observable(comment1 ? comment1.comment || "" : "");
            this.comment1Color = ko.observable(comment1 ? comment1.colorCode || "" : "");
            this.comment1FontWeight = ko.observable(comment1 ? comment1.bold || false : false);
            this.comment2Content = ko.observable(comment2 ? comment2.comment || "" : "");
            this.comment2Color = ko.observable(comment2 ? comment2.colorCode || "" : "");
            this.comment2FontWeight = ko.observable(comment2 ? comment2.bold || false : false);
        }
    }

}