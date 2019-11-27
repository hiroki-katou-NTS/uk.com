module nts.uk.pr.view.qmm019.g.viewmodel {
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import windows = nts.uk.ui.windows;
    import modal = nts.uk.ui.windows.sub.modal;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;

    export class ScreenModel {
        screenControl: KnockoutObservable<ScreenControl>;

        selectedSearchIitemName: KnockoutObservable<any>;
        itemNames: KnockoutObservableArray<StatementItem>;
        codeSelected: KnockoutObservable<any>;

        categoryAtr: number;
        params: IParams;
        categoryAtrText: KnockoutObservable<string>;

        params: IParams;
        dataScreen: KnockoutObservable<Params>;

        constructor() {
            let self = this;
            self.screenControl = ko.observable(new ScreenControl());

            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);

            self.categoryAtr = shareModel.CategoryAtr.REPORT_ITEM;
            self.categoryAtrText = ko.observable(null);
            self.dataScreen = ko.observable(new Params());

            // G10_1
            $("[data-toggle='userguide-register']").ntsUserGuide();
            // G10_2
            $("[data-toggle='userguide-exist']").ntsUserGuide();
            // G10_3
            $("[data-toggle='userguide-not-register']").ntsUserGuide();

            self.codeSelected.subscribe(value => {
                if (isNullOrUndefined(value)) return;
                let itemName = _.find(self.itemNames(), (item: IStatementItem) => {
                    return item.itemNameCd == value;
                });
                if (isNullOrUndefined(itemName)) return;
                self.dataScreen().itemNameCode(itemName.itemNameCd);
                self.dataScreen().shortName(itemName.shortName);
                // 選択モードへ移行する
                self.selectedMode();
                self.categoryAtrText(shareModel.getCategoryAtrText(self.categoryAtr));
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            let params: IParams = windows.getShared("QMM019_A_TO_G_PARAMS");
            self.params = params;
            let dto = {
                categoryAtr: self.categoryAtr,
                itemNameCdSelected: self.params.itemNameCode,
                itemNameCdExcludeList: self.params.listItemSetting
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
            modal("/view/qmm/012/b/index.xhtml").onClosed(() => {
                block.invisible();
                let dto = {
                    categoryAtr: self.categoryAtr,
                    itemNameCdSelected: self.params.itemNameCode,
                    itemNameCdExcludeList: self.params.listItemSetting
                };
                service.getStatementItem(dto).done((data: Array<IStatementItem>) => {
                    self.itemNames(StatementItem.fromApp(data));
                    self.codeSelected.valueHasMutated();
                }).fail(err => {
                    alertError(err);
                }).always(() => {
                    block.clear();
                });
            });
        }

        decide() {
            let self = this;
            let result = {
                itemNameCode: self.dataScreen().itemNameCode(),
                shortName: self.dataScreen().shortName()
            };
            windows.setShared("QMM019G_RESULTS", result);
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
        shortName: string;
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
         * 略名
         */
        shortName: string;
        /**
         * 既定区分
         */
        defaultAtr: number;

        constructor(data: IStatementItem) {
            if (isNullOrUndefined(data)) {
                this.categoryAtr = null;
                this.itemNameCd = null;
                this.shortName = null;
                this.defaultAtr = null;
                return;
            }
            this.categoryAtr = data.categoryAtr;
            this.itemNameCd = data.itemNameCd;
            this.shortName = data.shortName;
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
        listItemSetting: Array<string>;
    }

    class Params {
        /**
         * 項目名コード
         */
        itemNameCode: KnockoutObservable<string> = ko.observable(null);
        /**
         * 略名
         */
        shortName: KnockoutObservable<string> = ko.observable(null);
    }
}