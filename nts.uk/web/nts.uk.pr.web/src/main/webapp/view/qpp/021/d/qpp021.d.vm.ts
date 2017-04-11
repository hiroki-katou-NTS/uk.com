module qpp021.d.viewmodel {
    export class ScreenModel {
        zeroItemSetting: KnockoutObservableArray<ItemModel>;
        zeroItemSettingCode: KnockoutObservable<number>;

        zeroAmountOutput: KnockoutObservableArray<ItemModel>;
        zeroAmountOutputCode: KnockoutObservable<number>;

        zeroTimeClassification: KnockoutObservableArray<ItemModel>;
        zeroTimeClassificationCode: KnockoutObservable<number>;

        selectPrintYearMonth: KnockoutObservableArray<ItemModel>;
        selectPrintYearMonthCode: KnockoutObservable<number>;
        constructor() {
            let self = this;
            self.zeroItemSetting = ko.observableArray([
                new ItemModel(1, "項目名の登録の設定を優先する"),
                new ItemModel(2, "個別にッ設定する")
            ]);
            self.zeroItemSettingCode = ko.observable(2);

            self.zeroAmountOutput = ko.observableArray([
                new ItemModel(1, "する"),
                new ItemModel(2, "しない")
            ]);
            self.zeroAmountOutputCode = ko.observable(2);

            self.zeroTimeClassification = ko.observableArray([
                new ItemModel(1, "する"),
                new ItemModel(2, "しない")
            ]);
            self.zeroTimeClassificationCode = ko.observable(1);
            
             self.selectPrintYearMonth = ko.observableArray([
                new ItemModel(1, "現在処理年月の2ヶ月前"),
                new ItemModel(2, "現在処理年月の1か月前"),
                new ItemModel(3, "現在処理年月"),
                new ItemModel(4, "現在処理年月の翌月"),
                new ItemModel(5, "現在処理年月の2ヶ月後")
            ]);
            self.selectPrintYearMonthCode = ko.observable(3);
            
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }

    class RadioBoxGroupModel {
        rbCode: number;
        rbName: string;
        constructor(rbCode: number, rbName: string) {
            this.rbCode = rbCode;
            this.rbName = rbName;
        }
    }

    class SwitchButtonModel {
        sbCode: number;
        sbName: string;
        constructor(sbCode: number, sbName: string) {
            this.sbCode = sbCode;
            this.sbName = sbName;
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