module nts.uk.pr.view.qmm019.g.viewmodel {
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import windows = nts.uk.ui.windows;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;

    export class ScreenModel {
        screenControl: KnockoutObservable<ScreenControl>;

        selectedSearchIitemName: KnockoutObservable<any>;
        itemNames: KnockoutObservableArray<StatementItem>;
        codeSelected: KnockoutObservable<any>;
        itemNameSelected: KnockoutObservable<StatementItem>;

        categoryAtr: number;
        params: IParams;
        categoryAtrText: KnockoutObservable<string>;

        params: IParams;

        constructor() {
            let self = this;
            self.screenControl = ko.observable(new ScreenControl());

            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);
            self.itemNameSelected = ko.observable(new StatementItem(null));

            self.categoryAtr = shareModel.CategoryAtr.REPORT_ITEM;
            self.categoryAtrText = ko.observable(null);

            // G10_1
            $("[data-toggle='userguide-register']").ntsUserGuide();
            // G10_2
            $("[data-toggle='userguide-exist']").ntsUserGuide();
            // G10_3
            $("[data-toggle='userguide-not-register']").ntsUserGuide();

            self.codeSelected.subscribe(value => {
                let itemName = _.find(self.itemNames(), (item: IStatementItem) => {
                    return item.itemNameCd == value;
                })
                self.itemNameSelected(itemName);
                // 選択モードへ移行する
                self.selectedMode();
                self.categoryAtrText(shareModel.getCategoryAtrText(self.categoryAtr));
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            let params: IParams = <IParams>{};
            params.itemNameCode = "0d001";
            params.itemNameCdExcludeList = [];
            self.params = params;
            let dto = {
                categoryAtr: self.categoryAtr,
                itemNameCdSelected: self.params.itemNameCode,
                itemNameCdExcludeList: self.params.itemNameCdExcludeList
            };
            service.getStatementItem(dto).done((data: Array<IStatementItem>) => {
                self.itemNames(StatementItem.fromApp(data));
                self.initScreen();

            }).always(() => {
                block.clear();
            });
            dfd.resolve();
            return dfd.promise();
        }

        initScreen() {
            let self = this;
            // 取得できたデータ件数を確認する
            if (_.isEmpty(self.itemNames())) {
                // 未選択モードへ移行する
                self.unselectedMode();
                return;
            }
            if (isNullOrUndefined(self.params.itemNameCode)) {
                // 未選択モードへ移行する
                self.unselectedMode();
                return;
            }
            let item: IStatementItem = _.find(self.itemNames(), (item: IStatementItem) => {
                return item.itemNameCd == self.params.itemNameCode;
            });
            if (isNullOrUndefined(item)) {
                // 未選択モードへ移行する
                self.unselectedMode();
                return;
            }
            self.codeSelected(item.itemNameCd);
        }

        unselectedMode() {
            let self = this;
            self.codeSelected(null);
            self.condition6(self.itemNames(), self.codeSelected());
            self.condition7(self.itemNames());
            self.screenControl().visibleG2_2(false);
            self.screenControl().visibleG2_3(false);
            self.screenControl().enableG2_4(false);
            self.screenControl().enableG3_1(false);

            nts.uk.ui.errors.clearAll();
            $("#btn-register").focus();
        }

        selectedMode() {
            let self = this;
            self.screenControl().visibleG2_2(true);
            self.screenControl().visibleG2_3(true);
            self.screenControl().enableG2_4(true);
            self.screenControl().enableG3_1(true);

            nts.uk.ui.errors.clearAll();
            $("#G1_6_container").focus();
        }

        /**
         * ※6
         */
        condition6(list: Array, code: string) {
            if (!_.isEmpty(list) && isNullOrEmpty(code)) {
                // G10_1
                $(".userguide-register").ntsUserGuide("show");
                // G10_2
                $(".userguide-exist").ntsUserGuide("show");
            }
        }

        /**
         * ※7
         */
        condition7(list: Array) {
            if (_.isEmpty(list)) {
                // G10_3
                $(".userguide-not-register").ntsUserGuide("show");
            }
        }

        register() {
            let self = this;
            block.invisible();
            let dto = {
                categoryAtr: self.categoryAtr,
                itemNameCdSelected: self.params.itemNameCode,
                itemNameCdExcludeList: self.params.itemNameCdExcludeList
            };
            service.getStatementItem(dto).done((data: Array<IStatementItem>) => {
                self.itemNames(StatementItem.fromApp(data));
            }).fail(err => {
                alertError(err);
            }).always(() => {
                block.clear();
            });
        }

        decide() {
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    class ScreenControl {
        visibleG2_2: KnockoutObservable<boolean> = ko.observable(false);
        visibleG2_3: KnockoutObservable<boolean> = ko.observable(false);
        enableG2_4: KnockoutObservable<boolean> = ko.observable(false);
        enableG3_1: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
        }
    }

    interface IStatementItem {
        categoryAtr: number;
        itemNameCd: string;
        name: string;
        defaultAtr: number;
    }

    class StatementItem {
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
         * 既定区分
         */
        defaultAtr: number;

        constructor(data: IStatementItem) {
            if (isNullOrUndefined(data)) {
                this.categoryAtr = null;
                this.itemNameCd = null;
                this.name = null;
                this.defaultAtr = null;
                return;
            }
            this.categoryAtr = data.categoryAtr;
            this.itemNameCd = data.itemNameCd;
            this.name = data.name;
            this.defaultAtr = data.defaultAtr;
        }

        static fromApp(data: Array<IStatementItem>): Array<StatementItem> {
            let result = _.map(data, (item: IStatementItem) => {
                return new StatementItem(item);
            });
            return result;
        }
    }

    interface IParams {
        itemNameCode: string;
        itemNameCdExcludeList: Array<string>;
    }
}