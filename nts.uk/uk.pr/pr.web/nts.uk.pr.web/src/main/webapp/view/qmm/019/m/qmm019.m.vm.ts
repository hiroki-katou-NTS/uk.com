module nts.uk.pr.view.qmm019.m.viewmodel {
    import getText = nts.uk.resource.getText;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import windows = nts.uk.ui.windows;

    export class ScreenModel {

        columns: KnockoutObservableArray<any>;
        formulas: KnockoutObservableArray<IFormula>;
        formulaSelected: KnockoutObservable<IFormula>;
        formulaCodeSelected: KnockoutObservable<string>;

        constructor() {
            let self = this;

            self.formulas = ko.observableArray([]);
            self.formulaSelected = ko.observable(null);
            self.formulaCodeSelected = ko.observable(null);

            this.columns = ko.observableArray([
                {headerText: getText("QMM019_108"), key: 'formulaCode', width: 60, formatter: _.escape},
                {headerText: getText("QMM019_109"), key: 'formulaName', width: 240, formatter: _.escape}
            ]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            // パラメータを取得する
            let params = windows.getShared("QMM019M_PARAMS");
            if (isNullOrUndefined(params)) {
                dfd.resolve();
                return dfd.promise();
            }
            // ドメインモデル「計算式」を取得する
            service.getFormulaByYearMonth(params.yearMonth).done((data: Array<IFormula>) => {
                self.formulas(data);
                self.focusFormula(params.formulaCode);
                dfd.resolve();
            })
            return dfd.promise();
        }

        focusFormula(formulaCode: string) {
            let self = this;
            // パラメータ.計算式コードを確認する
            if (!isNullOrUndefined(formulaCode)) {
                // 計算式一覧にパラメータ.計算式コードがあるか確認する
                let formula = _.find(self.formulas(), (item: IFormula) => {
                    return item.formulaCode == formulaCode;
                })
                if (!isNullOrUndefined(formula)) {
                    // パラメータ.計算式コードの項目を選択状態にする
                    self.formulaCodeSelected(formulaCode);
                    self.formulaSelected(formula);
                    return;
                }
            }

            // 一覧の先頭を選択状態に宇する
            let firstItem: IFormula = _.head(self.formulas());
            self.formulaSelected(firstItem);
            if (isNullOrUndefined(firstItem)) {
                self.formulaCodeSelected(null);
            } else {
                self.formulaCodeSelected(firstItem.formulaCode);
            }
        }

        decide() {
            let self = this;
            windows.setShared("QMM019M_RESULTS", self.formulaSelected());
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    interface IFormula {
        /**
         * 計算式コード
         */
        formulaCode: string;
        /**
         * 計算式名
         */
        formulaName: string;
        /**
         * 計算式の設定方法
         */
        settingMethod: number;
        /**
         * 入れ子利用区分
         */
        nestedAtr: number;
    }
}