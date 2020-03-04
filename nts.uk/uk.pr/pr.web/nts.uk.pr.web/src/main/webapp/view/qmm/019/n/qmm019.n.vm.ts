module nts.uk.pr.view.qmm019.n.viewmodel {
    import getText = nts.uk.resource.getText;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import windows = nts.uk.ui.windows;

    export class ScreenModel {

        columns: KnockoutObservableArray<any>;
        wageTables: KnockoutObservableArray<IWageTable>;
        wageTableCodeSelected: KnockoutObservable<string>;

        constructor() {
            let self = this;

            self.wageTables = ko.observableArray([]);
            self.wageTableCodeSelected = ko.observable(null);

            this.columns = ko.observableArray([
                {headerText: getText("QMM019_108"), key: 'wageTableCode', width: 60, formatter: _.escape},
                {headerText: getText("QMM019_109"), key: 'wageTableName', width: 240, formatter: _.escape}
            ]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            // パラメータを受け取る
            let params = windows.getShared("QMM019N_PARAMS");
            if (isNullOrUndefined(params)) {
                dfd.resolve();
                return dfd.promise();
            }
            // ドメインモデル「賃金テーブル」を取得する
            service.getWageTableByYearMonth(params.yearMonth).done((data: Array<IWageTable>) => {
                data = _.sortBy(data, [(item: IWageTable) => {
                    return item.wageTableCode;
                }]);
                self.wageTables(data);
                self.focusWageTable(params.wageTableCode);
                dfd.resolve();
            })
            return dfd.promise();
        }

        focusWageTable(wageTableCode: string) {
            let self = this;
            // パラメータ.賃金テーブルコードを確認する
            if (!isNullOrUndefined(wageTableCode)) {
                // 賃金テーブル一覧にパラメータ.賃金テーブルコードがあるか確認する
                let wageTable = _.find(self.wageTables(), (item: IWageTable) => {
                    return item.wageTableCode == wageTableCode;
                })
                if (!isNullOrUndefined(wageTable)) {
                    // パラメータ.賃金テーブルコードの項目を選択状態にする
                    self.wageTableCodeSelected(wageTableCode);
                    return;
                }
            }

            // 一覧の先頭を選択状態に宇する
            let firstItem: IWageTable = _.head(self.wageTables());
            if (isNullOrUndefined(firstItem)) {
                self.wageTableCodeSelected(null);
            } else {
                self.wageTableCodeSelected(firstItem.wageTableCode);
            }
        }

        decide() {
            let self = this;
            let wageTable = _.find(self.wageTables(), (item: IWageTable) => {
                return item.wageTableCode == self.wageTableCodeSelected();
            })
            windows.setShared("QMM019N_RESULTS", wageTable);
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    interface IWageTable {
        /**
         * 賃金テーブルコード
         */
        wageTableCode: string;
        /**
         * 賃金テーブル名
         */
        wageTableName: string;
    }
}