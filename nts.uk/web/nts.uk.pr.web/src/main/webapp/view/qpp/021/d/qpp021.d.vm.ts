module qpp021.d.viewmodel {
    export class ScreenModel {
        isEnaleFromParent: KnockoutObservable<boolean>;

        zeroItemSetting: KnockoutObservableArray<ItemModel>;
        zeroItemSettingCode: KnockoutObservable<number>;

        switchItemList: KnockoutObservableArray<ItemModel>;
        //zeroAmountOutput: KnockoutObservableArray<ItemModel>;
        zeroAmountOutputCode: KnockoutObservable<number>;
        zeroTimeClassificationCode: KnockoutObservable<number>;
        totalTaxableOutputCode: KnockoutObservable<number>;
        yearlyHolidaysClassificationCode: KnockoutObservable<number>;
        kindPaymentOutputCode: KnockoutObservable<number>;

        selectPrintYearMonth: KnockoutObservableArray<ItemModel>;
        selectPrintYearMonthCode: KnockoutObservable<number>;

        outputNameDesignation: KnockoutObservableArray<ItemModel>;
        outputNameDesignationCode: KnockoutObservable<number>;

        outputDepartment: KnockoutObservableArray<ItemModel>;
        outputDepartmentCode: KnockoutObservable<number>;

        borderLineWidth: KnockoutObservableArray<ItemModel>;
        borderLineWidthCode: KnockoutObservable<number>;

        outputCompanyNameCode: KnockoutObservable<number>;
        shadedSectionCode: KnockoutObservable<number>;
        
        

        constructor() {
            let self = this;
            self.zeroItemSetting = ko.observableArray([
                new ItemModel(1, "項目名の登録の設定を優先する"),
                new ItemModel(2, "個別にッ設定する")
            ]);
            self.zeroItemSettingCode = ko.observable(2);

            self.switchItemList = ko.observableArray([
                new ItemModel(1, "する"),
                new ItemModel(2, "しない")
            ]);
            self.zeroAmountOutputCode = ko.observable(2);
            self.zeroTimeClassificationCode = ko.observable(1);
            self.totalTaxableOutputCode = ko.observable(1);
            self.yearlyHolidaysClassificationCode = ko.observable(1);
            self.kindPaymentOutputCode = ko.observable(1);

            self.selectPrintYearMonth = ko.observableArray([
                new ItemModel(1, "現在処理年月の2ヶ月前"),
                new ItemModel(2, "現在処理年月の1か月前"),
                new ItemModel(3, "現在処理年月"),
                new ItemModel(4, "現在処理年月の翌月"),
                new ItemModel(5, "現在処理年月の2ヶ月後")
            ]);
            self.selectPrintYearMonthCode = ko.observable(3);

            self.outputNameDesignation = ko.observableArray([
                new ItemModel(1, "個人情報より取得する"),
                new ItemModel(2, "項目名より取得する"),
            ]);
            self.outputNameDesignationCode = ko.observable(1);

            self.outputDepartment = ko.observableArray([
                new ItemModel(1, "部門コードを出力する"),
                new ItemModel(2, "部門名を出力する"),
                new ItemModel(3, "出力しない"),
            ]);
            self.outputDepartmentCode = ko.observable(2);

            self.borderLineWidth = ko.observableArray([
                new ItemModel(1, "太い"),
                new ItemModel(2, "標準"),
                new ItemModel(3, "細い    "),
            ]);
            self.borderLineWidthCode = ko.observable(2);

            self.outputCompanyNameCode = ko.observable(1);
            self.shadedSectionCode = ko.observable(1);
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