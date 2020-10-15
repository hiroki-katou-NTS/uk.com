module nts.uk.at.view.kaf022.d.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelD {
        itemListD15: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_391')},
            {code: 1, name: text('KAF022_392')}
        ]);
        itemListD13: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_389')},
            {code: 0, name: text('KAF022_390')}
        ]);

        // 勤務時間の初期表示
        selectedIdD15: KnockoutObservable<number>;
        // 出退勤を反映するか
        selectedIdD13: KnockoutObservable<number>;

        // コメント１．コメント
        texteditorD9: KnockoutObservable<string>;
        //コメント１．文字色
        valueD10: KnockoutObservable<string>;
        // コメント１．太字
        enableD11: KnockoutObservable<boolean>;

        // コメント2．コメント
        texteditorD12: KnockoutObservable<string>;
        // コメント2．文字色
        valueD10_1: KnockoutObservable<string>;
        // コメント2．太字
        enableD11_1: KnockoutObservable<boolean>;

        constructor() {
            const self = this;
            self.selectedIdD15 = ko.observable(0);
            self.selectedIdD13 = ko.observable(1);

            self.texteditorD9 = ko.observable(null);
            self.valueD10 = ko.observable("#000000");
            self.enableD11 = ko.observable(false);

            self.texteditorD12 = ko.observable(null);
            self.valueD10_1 = ko.observable("#000000");
            self.enableD11_1 = ko.observable(false);

            $("#fixed-table-d1").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            let data = allData.appChange;
            let workTimeReflectAtr = allData.workTimeReflectAtr;
            if (data) {
                self.selectedIdD15(data.initDisplayWorktimeAtr);
                self.selectedIdD13(workTimeReflectAtr);
                self.texteditorD9(data.comment1.comment);
                self.valueD10(data.comment1.colorCode);
                self.enableD11(data.comment1.bold);
                self.texteditorD12(data.comment2.comment);
                self.valueD10_1(data.comment2.colorCode);
                self.enableD11_1(data.comment2.bold);
            }
        }

        collectData(): any {
            const self = this;
            return {
                initDisplayWorktime: self.selectedIdD15(),
                workTimeReflectAtr: self.selectedIdD13(),

                commentContent1: self.texteditorD9(),
                commentFontWeight1: self.enableD11() ? 1 : 0,
                commentFontColor1: self.valueD10(),

                commentContent2: self.texteditorD12(),
                commentFontWeight2: self.enableD11_1() ? 1 : 0,
                commentFontColor2: self.valueD10_1()
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