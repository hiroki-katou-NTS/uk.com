module nts.uk.pr.view.qmm019.d.viewmodel {
    import windows = nts.uk.ui.windows;
    import modal = nts.uk.ui.windows.sub.modal;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;

    export class ScreenModel {
        screenMode: KnockoutObservable<number>;
        unselectMode: number = 0;
        selectMode: number = 1

        option: any;

        selectedSearchIitemName: KnockoutObservable<string>;
        itemNames: KnockoutObservableArray<StatementItem>;
        codeSelected: KnockoutObservable<string>;
        itemNameSelected: KnockoutObservable<StatementItem>;

        value: KnockoutObservable<number>;
        totalObjAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        calcMethods: KnockoutObservableArray<shareModel.ItemModel>;
        workingAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        proportionalDivisionSetAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        proportionalDivisionSetAtrSelected: KnockoutObservable<shareModel.ItemModel>;
        proportionalDivisionRatioSetAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        proportionalDivisionRatioSetAtrSelected: KnockoutObservable<shareModel.ItemModel>;

        dataScreen: KnockoutObservable<Params>;
        categoryAtrText: KnockoutObservable<string>;
        paymentItemSet: KnockoutObservable<PaymentItemSet>;
        breakdownItemSets: KnockoutObservableArray<BreakdownItemSet>

        constructor() {
            let self = this;
            self.screenMode = ko.observable(0);
            self.option = {
                grouplength: 3,
                textalign: "right",
                currencyformat: "JPY"
            }

            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);
            self.itemNameSelected = ko.observable(new StatementItem(null));

            self.value = ko.observable(1000);
            self.totalObjAtrs = ko.observableArray(shareModel.getPaymentTotalObjAtr());
            self.calcMethods = ko.observableArray(shareModel.getPaymentCaclMethodAtr());
            self.workingAtrs = ko.observableArray(shareModel.getWorkingAtr());
            self.proportionalDivisionSetAtrs = ko.observableArray(shareModel.getProportionalDivisionSetAtr())
            self.proportionalDivisionSetAtrSelected = ko.observable(null);
            self.proportionalDivisionRatioSetAtrs = ko.observableArray(shareModel.getProportionalDivisionRatioSetAtr())
            self.proportionalDivisionRatioSetAtrSelected = ko.observable(null);

            self.dataScreen = ko.observable(new Params(null));
            self.categoryAtrText = ko.observable(null);
            self.paymentItemSet = ko.observable(new PaymentItemSet(null));
            self.breakdownItemSets = ko.observableArray([]);

            self.codeSelected.subscribe(value => {
                let itemName = _.find(self.itemNames(), (item: IStatementItem) => {
                    return item.itemNameCd == value;
                })
                self.itemNameSelected(itemName);
                self.getDataAccordion().done(() => {

                })
            })

            $("[data-toggle='userguide-register']").ntsUserGuide();
            $("[data-toggle='userguide-exist']").ntsUserGuide();
            $("[data-toggle='userguide-not-register']").ntsUserGuide();

        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            $("#fixed-table").ntsFixedTable({height: 139});
            // let params: IParams = windows.getShared("QMM019D_PARAMS");
            let params: IParams = new Params(null);
            params.workingAtr = 1;
            params.totalObject = 1;
            params.calcMethod = 1;
            params.proportionalAtr = 1;
            params.proportionalMethod = 1;
            params.rangeValAttribute = 1;
            params.errorRangeSetting.upperLimitSetting.valueSettingAtr = 1;
            params.errorRangeSetting.upperLimitSetting.rangeValue = 111;
            params.errorRangeSetting.lowerLimitSetting.valueSettingAtr = 1;
            params.errorRangeSetting.lowerLimitSetting.rangeValue = 10;
            params.alarmRangeSetting.upperLimitSetting.valueSettingAtr = 1;
            params.alarmRangeSetting.upperLimitSetting.rangeValue = 30;
            params.alarmRangeSetting.lowerLimitSetting.valueSettingAtr = 1;
            params.alarmRangeSetting.lowerLimitSetting.rangeValue = 50;
            params.perValCode = "08";
            params.formulaCode = "0002";
            params.wageTableCode = "0003";
            params.commonAmount = 10;
            service.getStatementItem(shareModel.CategoryAtr.PAYMENT_ITEM).done((data: Array<IStatementItem>) => {
                self.itemNames(StatementItem.fromApp(data));
                self.initScreen(params);
            })
            dfd.resolve();
            return dfd.promise();
        }

        initScreen(params: IParams) {
            let self = this;
            // 取得できたデータ件数を確認する
            if (_.isEmpty(self.itemNames())) {
                // 未選択モードへ移行する
                self.unselectedMode();
                return;
            }
            // TODO #125441

            // 選択モードへ移行する
            self.selectedMode();
            let item: IStatementItem = _.head(self.itemNames());
            self.codeSelected(item.itemNameCd);
            // パラメータを受け取り取得した情報と合わせて画面上に表示する
            self.dataScreen(new Params(params));
            // TODO
        }

        getDataAccordion(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                ctgAtr = self.itemNameSelected().categoryAtr;
            // ドメインモデル「支給項目設定」を取得する
            let sv1 = service.getPaymentItemStById(ctgAtr, self.codeSelected());
            // ドメインモデル「内訳項目設定」を取得する
            let sv2 = service.getAllBreakdownItemSetById(ctgAtr, self.codeSelected());
            // ドメインモデル「給与個人別金額名称」を取得する
            let sv3 = service.getSalIndAmountNameById(self.dataScreen().perValCode(), ctgAtr);
            // ドメインモデル「計算式」を取得する
            let sv4 = service.getFormulaById(self.dataScreen().formulaCode());
            // ドメインモデル「賃金テーブル」を取得する
            let sv5 = service.getWageTableById(self.dataScreen().wageTableCode())
            $.when(sv1, sv2, sv3, sv4, sv5).done((pay: IPaymentItemSet,
                                                  breakItems: Array<IBreakdownItemSet>,
                                                  perVal: any,
                                                  formula: any,
                                                  wageTable: any) => {
                self.categoryAtrText(shareModel.getCategoryAtrText(ctgAtr));
                self.paymentItemSet(isNullOrUndefined(pay) ? new PaymentItemSet(null) : new PaymentItemSet(pay));
                self.breakdownItemSets(_.isEmpty(breakItems) ? [] : BreakdownItemSet.fromApp(breakItems));
                self.dataScreen().perValName(isNullOrUndefined(perVal) ? null : perVal.individualPriceName);
                self.dataScreen().formulaName(isNullOrUndefined(formula) ? null : formula.formulaName);
                self.dataScreen().wageTableName(isNullOrUndefined(wageTable) ? null : wageTable.wageTableName);
            })
            dfd.resolve();
            return dfd.promise();
        }

        unselectedMode() {
            let self = this;
            self.screenMode(self.unselectMode);
            self.paymentItemSet = ko.observable(new PaymentItemSet(null));
        }

        selectedMode() {
            let self = this;
            self.screenMode(self.selectMode);
        }


        register() {
            // $(".userguide-register").ntsUserGuide("show");
            // $(".userguide-exist").ntsUserGuide("show");
            $(".userguide-not-register").ntsUserGuide("show");
        }

        openI() {
            windows.setShared("QMM019I_PARAMS", {cateIndicator: 0, individualPriceCode: '02'});
            modal("/view/qmm/019/i/index.xhtml").onClosed(() => {
                let results = windows.getShared("QMM019I_RESULTS");
                if (isNullOrUndefined(results)) {

                } else {

                }
            });
        }

        openM() {
            windows.setShared("QMM019M_PARAMS", {yearMonth: 201802, formulaCode: '0003'});
            modal("/view/qmm/019/m/index.xhtml").onClosed(() => {
                let results = windows.getShared("QMM019M_RESULTS");
                if (isNullOrUndefined(results)) {

                } else {

                }
            });
        }

        openN() {
            windows.setShared("QMM019N_PARAMS", {yearMonth: 201802, wageTableCode: '0003'});
            modal("/view/qmm/019/n/index.xhtml").onClosed(() => {
                let results = windows.getShared("QMM019N_RESULTS");
                if (isNullOrUndefined(results)) {

                } else {

                }
            });
        }

        referenced() {

        }

        decide() {
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    interface IStatementItem {
        categoryAtr: number;
        itemNameCd: string;
        name: string;
        deprecatedAtr: number;
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
         * 廃止区分
         */
        deprecatedAtr: number;

        constructor(data: IStatementItem) {
            if (isNullOrUndefined(data)) {
                this.categoryAtr = null;
                this.itemNameCd = null;
                this.name = null;
                this.deprecatedAtr = null;
                return;
            }
            this.categoryAtr = data.categoryAtr;
            this.itemNameCd = data.itemNameCd;
            this.name = data.name;
            this.deprecatedAtr = data.deprecatedAtr;
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
        workingAtr: number;
        totalObject: number;
        calcMethod: number;
        proportionalAtr: number;
        proportionalMethod: number;
        rangeValAttribute: number;
        errorRangeSetting: IErrorAlarmRangeSetting;
        alarmRangeSetting: IErrorAlarmRangeSetting;
        perValCode: string;
        formulaCode: string;
        wageTableCode: string;
        commonAmount: number;
    }

    class Params {
        /**
         * 項目名コード
         */
        itemNameCode: string;
        /**
         * 通勤区分
         */
        workingAtr: KnockoutObservable<number>;
        /**
         * 合計対象
         */
        totalObject: KnockoutObservable<number>;
        /**
         * 計算方法
         */
        calcMethod: KnockoutObservable<number>;
        /**
         * 按分区分
         */
        proportionalAtr: KnockoutObservable<number>;
        /**
         * 按分方法
         */
        proportionalMethod: KnockoutObservable<number>;
        /**
         * 範囲値の属性
         */
        rangeValAttribute: KnockoutObservable<number>;
        /**
         * エラー範囲設定
         */
        errorRangeSetting: ErrorAlarmRangeSetting;
        /**
         * アラーム範囲設定
         */
        alarmRangeSetting: ErrorAlarmRangeSetting;
        /**
         * 個人金額コード
         */
        perValCode: KnockoutObservable<string>;
        /**
         * 個人金額名称
         */
        perValName: KnockoutObservable<string>;
        /**
         * 計算式コード
         */
        formulaCode: KnockoutObservable<string>;
        /**
         * 計算式名
         */
        formulaName: KnockoutObservable<string>;
        /**
         * 賃金テーブルコード
         */
        wageTableCode: KnockoutObservable<string>;
        /**
         * 賃金テーブル名
         */
        wageTableName: KnockoutObservable<string>;
        /**
         * 共通金額
         */
        commonAmount: KnockoutObservable<number>;

        constructor(data: IParams) {
            this.perValName = ko.observable(null);
            this.formulaName = ko.observable(null);
            this.wageTableName = ko.observable(null);
            if (isNullOrUndefined(data)) {
                this.itemNameCode = null;
                this.workingAtr = ko.observable(null);
                this.totalObject = ko.observable(null);
                this.calcMethod = ko.observable(null);
                this.proportionalAtr = ko.observable(null);
                this.proportionalMethod = ko.observable(null);
                this.rangeValAttribute = ko.observable(null);
                this.errorRangeSetting = new ErrorAlarmRangeSetting(null);
                this.alarmRangeSetting = new ErrorAlarmRangeSetting(null);
                this.perValCode = ko.observable(null);
                this.formulaCode = ko.observable(null);
                this.wageTableCode = ko.observable(null);
                this.commonAmount = ko.observable(null);
                return;
            }
            this.itemNameCode = data.itemNameCode;
            this.workingAtr = ko.observable(data.workingAtr);
            this.totalObject = ko.observable(data.totalObject);
            this.calcMethod = ko.observable(data.calcMethod);
            this.proportionalAtr = ko.observable(data.proportionalAtr);
            this.proportionalMethod = ko.observable(data.proportionalMethod);
            this.rangeValAttribute = ko.observable(data.rangeValAttribute);
            this.errorRangeSetting = new ErrorAlarmRangeSetting(data.errorRangeSetting);
            this.alarmRangeSetting = new ErrorAlarmRangeSetting(data.alarmRangeSetting);
            this.perValCode = ko.observable(data.perValCode);
            this.formulaCode = ko.observable(data.formulaCode);
            this.wageTableCode = ko.observable(data.wageTableCode);
            this.commonAmount = ko.observable(data.commonAmount);

            this.commonAmount.subscribe(value => {
                console.log(value)
            })

            this.proportionalAtr.subscribe(value => {
                console.log(value)
            })
        }
    }

    interface IErrorAlarmRangeSetting {
        upperLimitSetting: IErrorAlarmValueSetting;
        lowerLimitSetting: IErrorAlarmValueSetting;
    }

    class ErrorAlarmRangeSetting {
        /**
         * 上限設定
         */
        upperLimitSetting: ErrorAlarmValueSetting;
        /**
         * 下限設定
         */
        lowerLimitSetting: ErrorAlarmValueSetting;

        constructor(data: IErrorAlarmRangeSetting) {
            if (isNullOrUndefined(data)) {
                this.upperLimitSetting = new ErrorAlarmValueSetting(null);
                this.lowerLimitSetting = new ErrorAlarmValueSetting(null);
                return;
            }
            this.upperLimitSetting = new ErrorAlarmValueSetting(data.upperLimitSetting);
            this.lowerLimitSetting = new ErrorAlarmValueSetting(data.lowerLimitSetting);
        }
    }

    interface IErrorAlarmValueSetting {
        valueSettingAtr: number;
        rangeValue: number;
    }

    class ErrorAlarmValueSetting {
        /**
         * 値設定区分
         */
        valueSettingAtr: KnockoutObservable<number>;
        /**
         * 範囲値
         */
        rangeValue: KnockoutObservable<number>;

        constructor(data: IErrorAlarmValueSetting) {
            if (isNullOrUndefined(data)) {
                this.valueSettingAtr = ko.observable(null);
                this.rangeValue = ko.observable(null);
                return;
            }
            this.valueSettingAtr = ko.observable(data.valueSettingAtr);
            this.rangeValue = ko.observable(data.rangeValue);
            this.rangeValue.subscribe(value => {
                console.log(value)
            })
        }
    }

    interface IPaymentItemSet {
        taxAtr: number;
        limitAmount: number;
        socialInsuranceCategory: number;
        laborInsuranceCategory: number;
        everyoneEqualSet: number;
        averageWageAtr: number;
        errorRangeSetting: IErrorAlarmRangeSetting;
        alarmRangeSetting: IErrorAlarmRangeSetting;
    }

    class PaymentItemSet {
        /**
         * 課税区分
         */
        taxAtr: KnockoutObservable<number>;
        taxAtrText: KnockoutObservable<string>;
        /**
         * 限度金額設定.限度金額
         */
        limitAmount: KnockoutObservable<number>;
        /**
         * 社会保険区分
         */
        socialInsuranceCategory: KnockoutObservable<number>;
        socialInsuranceCategoryText: KnockoutObservable<string>;
        /**
         * 労働保険区分
         */
        laborInsuranceCategory: KnockoutObservable<number>;
        laborInsuranceCategoryText: KnockoutObservable<string>;
        /**
         * 固定的賃金
         */
        everyoneEqualSet: KnockoutObservable<number>;
        everyoneEqualSetText: KnockoutObservable<number>;
        /**
         * 平均賃金区分
         */
        averageWageAtr: KnockoutObservable<number>;
        averageWageAtrText: KnockoutObservable<string>;

        errorRangeSetting: ErrorAlarmRangeSetting;
        alarmRangeSetting: ErrorAlarmRangeSetting;

        constructor(data: IPaymentItemSet) {
            if (isNullOrUndefined(data)) {
                this.taxAtr = ko.observable(null);
                this.taxAtrText = ko.observable(null);
                this.limitAmount = ko.observable(null);
                this.socialInsuranceCategory = ko.observable(null);
                this.socialInsuranceCategoryText = ko.observable(null);
                this.laborInsuranceCategory = ko.observable(null);
                this.laborInsuranceCategoryText = ko.observable(null);
                this.everyoneEqualSet = ko.observable(null);
                this.everyoneEqualSetText = ko.observable(null);
                this.averageWageAtr = ko.observable(null);
                this.averageWageAtrText = ko.observable(null);
                this.errorRangeSetting = new ErrorAlarmRangeSetting(null);
                this.alarmRangeSetting = new ErrorAlarmRangeSetting(null);
                return;
            }
            this.taxAtr = ko.observable(data.taxAtr);
            this.taxAtrText = ko.observable(shareModel.getTaxAtrText(data.taxAtr));
            this.limitAmount = ko.observable(data.limitAmount);
            this.socialInsuranceCategory = ko.observable(data.socialInsuranceCategory);
            this.socialInsuranceCategoryText = ko.observable(shareModel.getSocialInsuranceCategoryText(data.socialInsuranceCategory));
            this.laborInsuranceCategory = ko.observable(data.laborInsuranceCategory);
            this.laborInsuranceCategoryText = ko.observable(shareModel.getLaborInsuranceCategoryText(data.laborInsuranceCategory));
            this.everyoneEqualSet = ko.observable(data.everyoneEqualSet);
            this.everyoneEqualSetText = ko.observable(shareModel.getCategoryFixedWageText(data.averageWageAtr));
            this.averageWageAtr = ko.observable(data.averageWageAtr);
            this.averageWageAtrText = ko.observable(shareModel.getAverageWageAtrText(data.averageWageAtr));
            this.errorRangeSetting = new ErrorAlarmRangeSetting(data.errorRangeSetting);
            this.alarmRangeSetting = new ErrorAlarmRangeSetting(data.alarmRangeSetting);
        }
    }

    class IBreakdownItemSet {
        breakdownItemCode: string;
        breakdownItemName: string;
    }

    class BreakdownItemSet {
        /**
         * 内訳項目コード
         */
        breakdownItemCode: string;
        /**
         * 内訳項目名称
         */
        breakdownItemName: string;

        constructor(data: IBreakdownItemSet) {
            if (isNullOrUndefined(data)) {
                this.breakdownItemCode = null;
                this.breakdownItemName = null;
                return;
            }
            this.breakdownItemCode = data.breakdownItemCode;
            this.breakdownItemName = data.breakdownItemName;
        }

        static fromApp(data: Array<IBreakdownItemSet>): Array<BreakdownItemSet> {
            let result = _.map(data, (item: IBreakdownItemSet) => {
                return new BreakdownItemSet(item);
            });
            return result;
        }
    }
}