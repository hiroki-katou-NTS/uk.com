module nts.uk.pr.view.qmm019.i.viewmodel {
    import getText = nts.uk.resource.getText;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import windows = nts.uk.ui.windows;

    export class ScreenModel {

        columns: KnockoutObservableArray<any>;
        salIndAmounts: KnockoutObservableArray<ISalIndAmountName>;
        individualPriceCodeSelected: KnockoutObservable<string>;
        salIndAmountSelected: KnockoutObservable<ISalIndAmountName>;

        constructor() {
            let self = this;

            self.salIndAmounts = ko.observableArray([]);
            self.individualPriceCodeSelected = ko.observable(null);
            self.salIndAmountSelected = ko.observable(null);

            this.columns = ko.observableArray([
                {headerText: getText("QMM019_108"), key: 'individualPriceCode', width: 60, formatter: _.escape},
                {headerText: getText("QMM019_109"), key: 'individualPriceName', width: 240, formatter: _.escape}
            ]);

        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            // パラメータを受け取る
            let params = windows.getShared("QMM019I_PARAMS");
            if (isNullOrUndefined(params)) {
                dfd.resolve();
                return dfd.promise();
            }
            // ドメインモデル「給与個人別金額名称」を取得する
            service.getSalIndAmountName(params.cateIndicator).done((data: Array<ISalIndAmountName>) => {
                self.salIndAmounts(data);
                self.focusSalIndAmount(params.individualPriceCode);
                dfd.resolve();
            })
            return dfd.promise();
        }

        focusSalIndAmount(individualPriceCode: string) {
            let self = this;
            // パラメータ.個人金額コードを確認する
            if (!isNullOrUndefined(individualPriceCode)) {
                // 個人金額一覧にパラメータ.個人金額コードがあるか確認する
                let salIndAmount = _.find(self.salIndAmounts(), (item: ISalIndAmountName) => {
                    return item.individualPriceCode == individualPriceCode;
                })
                if (!isNullOrUndefined(salIndAmount)) {
                    // パラメータ.個人金額コードの項目を選択状態にする
                    self.individualPriceCodeSelected(individualPriceCode);
                    self.salIndAmountSelected(salIndAmount);
                    return;
                }
            }

            // 一覧の先頭を選択状態に宇する
            let firstItem: ISalIndAmountName = _.head(self.salIndAmounts());
            self.salIndAmountSelected(firstItem);
            if (isNullOrUndefined(firstItem)) {
                self.individualPriceCodeSelected(null);
            } else {
                self.individualPriceCodeSelected(firstItem.individualPriceCode);
            }
        }

        decide() {
            let self = this;
            windows.setShared("QMM019I_RESULTS", self.salIndAmountSelected());
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    interface ISalIndAmountName {
        /**
         * 個人金額コード
         */
        individualPriceCode: string;
        /**
         * カテゴリ区分
         */
        cateIndicator: number;
        /**
         * 個人金額名称
         */
        individualPriceName: string;
    }
}