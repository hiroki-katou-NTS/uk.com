module nts.uk.pr.view.qmm019.o.viewmodel {
    import getText = nts.uk.resource.getText;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import windows = nts.uk.ui.windows;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {

        columns: KnockoutObservableArray<any>;
        satementItems: KnockoutObservableArray<IStatementItem>;
        statementItemNameCdSelected: KnockoutObservable<any>;

        constructor() {
            let self = this;

            self.satementItems = ko.observableArray([]);
            self.statementItemNameCdSelected = ko.observable(null);

            this.columns = ko.observableArray([
                {headerText: getText("QMM019_108"), key: 'itemNameCd', width: 60, formatter: _.escape},
                {headerText: getText("QMM019_109"), key: 'name', width: 240, formatter: _.escape}
            ]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            // パラメータを受け取る
            let params = windows.getShared("QMM019O_PARAMS");
            if (isNullOrUndefined(params)) {
                dfd.resolve();
                return dfd.promise();
            }
            // ドメインモデル「明細書項目」を取得する
            service.getAllStatementItemData(shareModel.CategoryAtr.PAYMENT_ITEM, false).done((data: Array<IStatementItem>) => {
                data = _.sortBy(data, [(item: IStatementItem) => {
                    return item.itemNameCd;
                }]);
                self.satementItems(data);
                self.focusSatementItem(params.itemNameCd);
                dfd.resolve();
            })
            return dfd.promise();
        }

        focusSatementItem(itemNameCd: string) {
            let self = this;
            // パラメータ.項目名コードを確認する
            if (!isNullOrUndefined(itemNameCd)) {
                // 相殺項目一覧にパラメータ.項目名コードがあるか確認する
                let sttItem = _.find(self.satementItems(), (item: IStatementItem) => {
                    return item.itemNameCd == itemNameCd;
                })
                if (!isNullOrUndefined(sttItem)) {
                    // パラメータ.項目名コードの項目を選択状態にする
                    self.statementItemNameCdSelected(itemNameCd);
                    return;
                }
            }

            // 一覧の先頭を選択状態に宇する
            let firstItem: IStatementItem = _.head(self.satementItems());
            if (isNullOrUndefined(firstItem)) {
                self.statementItemNameCdSelected(null);
            } else {
                self.statementItemNameCdSelected(firstItem.itemNameCd);
            }
        }

        decide() {
            let self = this;
            let sttItem = _.find(self.satementItems(), (item: IStatementItem) => {
                return item.itemNameCd == self.statementItemNameCdSelected();
            })
            windows.setShared("QMM019O_RESULTS", sttItem);
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    interface IStatementItem {
        /**
         * カテゴリ区分
         */
        categoryAtr: number;
        /**
         * 項目名コード
         */
        itemNameCd: string;
        /**
         * 名称
         */
        name: string;
        /**
         * 廃止区分
         */
        deprecatedAtr: number;
    }
}