module nts.uk.pr.view.qmm019.d.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import modal = nts.uk.ui.windows.sub.modal;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;

    export class ScreenModel {
        isInitSubscribe: boolean;
        screenControl: KnockoutObservable<ScreenControl>;

        option: any;

        selectedSearchIitemName: KnockoutObservable<string>;
        itemNames: KnockoutObservableArray<StatementItem>;
        codeSelected: KnockoutObservable<string>;

        categoryAtr: number;
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
        breakdownItemSets: KnockoutObservableArray<BreakdownItemSet>;

        constructor() {
            let self = this;
            self.isInitSubscribe = false;
            self.screenControl = ko.observable(new ScreenControl());
            self.option = {
                grouplength: 3,
                textalign: "right",
                currencyformat: "JPY"
            };

            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);

            self.categoryAtr = shareModel.CategoryAtr.PAYMENT_ITEM;
            self.totalObjAtrs = ko.observableArray(shareModel.getPaymentTotalObjAtr());
            self.calcMethods = ko.observableArray(shareModel.getPaymentCaclMethodAtr());
            self.workingAtrs = ko.observableArray(shareModel.getWorkingAtr());
            self.proportionalDivisionSetAtrs = ko.observableArray(shareModel.getProportionalDivisionSetAtr());
            self.proportionalDivisionSetAtrSelected = ko.observable(null);
            self.proportionalDivisionRatioSetAtrs = ko.observableArray(shareModel.getProportionalDivisionRatioSetAtr());
            self.proportionalDivisionRatioSetAtrSelected = ko.observable(null);

            self.dataScreen = ko.observable(new Params(null));
            self.categoryAtrText = ko.observable(null);
            self.paymentItemSet = ko.observable(new PaymentItemSet(null));
            self.breakdownItemSets = ko.observableArray([]);

            // D10_1
            $("[data-toggle='userguide-register']").ntsUserGuide();
            // D10_2
            $("[data-toggle='userguide-exist']").ntsUserGuide();
            // D10_3
            $("[data-toggle='userguide-not-register']").ntsUserGuide();

            self.codeSelected.subscribe(value => {
                block.invisible();
                let itemName: StatementItem = _.find(self.itemNames(), (item: IStatementItem) => {
                    return item.itemNameCd == value;
                });
                self.dataScreen().itemNameCode(itemName.itemNameCd);
                self.dataScreen().name(itemName.name);
                self.getDataAccordion().done(() => {
                    // 選択モードへ移行する
                    self.selectedMode(itemName.defaultAtr);
                    block.clear();
                })
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            $("#fixed-table").ntsFixedTable({height: 139, width: 272});
            // let params: IParams = windows.getShared("QMM019D_PARAMS");
            let params: IParams = <IParams>{};
            params.itemNameCode = "0002";
            params.yearMonth = 201802;
            params.workingAtr = null;
            params.totalObject = shareModel.PaymentTotalObjAtr.INSIDE;
            params.calcMethod = shareModel.PaymentCaclMethodAtr.COMMON_AMOUNT;
            params.proportionalAtr = null;
            params.proportionalMethod = null;
            params.rangeValAttribute = null;
            params.errorRangeSetting = <IErrorAlarmRangeSetting>{};
            params.errorRangeSetting.upperLimitSetting = <IErrorAlarmValueSetting>{};
            params.errorRangeSetting.upperLimitSetting.valueSettingAtr = 1;
            params.errorRangeSetting.upperLimitSetting.rangeValue = null;
            params.errorRangeSetting.lowerLimitSetting = <IErrorAlarmValueSetting>{};
            params.errorRangeSetting.lowerLimitSetting.valueSettingAtr = 1;
            params.errorRangeSetting.lowerLimitSetting.rangeValue = null;
            params.alarmRangeSetting = <IErrorAlarmRangeSetting>{};
            params.alarmRangeSetting.upperLimitSetting = <IErrorAlarmValueSetting>{};
            params.alarmRangeSetting.upperLimitSetting.valueSettingAtr = 1;
            params.alarmRangeSetting.upperLimitSetting.rangeValue = null;
            params.alarmRangeSetting.lowerLimitSetting = <IErrorAlarmValueSetting>{};
            params.alarmRangeSetting.lowerLimitSetting.valueSettingAtr = 1;
            params.alarmRangeSetting.lowerLimitSetting.rangeValue = null;
            params.perValCode = null;
            params.formulaCode = null;
            params.wageTableCode = null;
            params.commonAmount = null;
            service.getStatementItem(self.categoryAtr).done((data: Array<IStatementItem>) => {
                self.itemNames(StatementItem.fromApp(data));
                self.initScreen(params);
            });
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
            if (isNullOrUndefined(params.itemNameCode)) {
                // 未選択モードへ移行する
                self.unselectedMode();
                return;
            }
            let item: IStatementItem = _.find(self.itemNames(), (item: IStatementItem) => {
                return item.itemNameCd == params.itemNameCode;
            });
            if (isNullOrUndefined(item)) {
                // 未選択モードへ移行する
                self.unselectedMode();
                return;
            }
            self.codeSelected(item.itemNameCd);
            // TODO #125441
            // パラメータを受け取り取得した情報と合わせて画面上に表示する
            self.dataScreen(new Params(params));
            self.initSubscribeDataScreen();
        }

        getDataAccordion(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            // ドメインモデル「支給項目設定」を取得する
            let sv1 = service.getPaymentItemStById(self.categoryAtr, self.dataScreen().itemNameCode());
            // ドメインモデル「内訳項目設定」を取得する
            let sv2 = service.getAllBreakdownItemSetById(self.categoryAtr, self.dataScreen().itemNameCode());
            // ドメインモデル「給与個人別金額名称」を取得する
            let sv3 = service.getSalIndAmountNameById(self.dataScreen().perValCode(), self.categoryAtr);
            // ドメインモデル「計算式」を取得する
            let sv4 = service.getFormulaById(self.dataScreen().formulaCode());
            // ドメインモデル「賃金テーブル」を取得する
            let sv5 = service.getWageTableById(self.dataScreen().wageTableCode());
            $.when(sv1, sv2, sv3, sv4, sv5).done((pay: IPaymentItemSet,
                                                  breakItems: Array<IBreakdownItemSet>,
                                                  perVal: any,
                                                  formula: any,
                                                  wageTable: any) => {
                self.categoryAtrText(shareModel.getCategoryAtrText(self.categoryAtr));
                self.paymentItemSet(isNullOrUndefined(pay) ? new PaymentItemSet(null) : new PaymentItemSet(pay));
                self.breakdownItemSets(_.isEmpty(breakItems) ? [] : BreakdownItemSet.fromApp(breakItems));
                self.dataScreen().perValName(isNullOrUndefined(perVal) ? null : perVal.individualPriceName);
                self.dataScreen().formulaName(isNullOrUndefined(formula) ? null : formula.formulaName);
                self.dataScreen().wageTableName(isNullOrUndefined(wageTable) ? null : wageTable.wageTableName);
                dfd.resolve();
            });

            return dfd.promise();
        }

        initSubscribeDataScreen() {
            let self = this;
            if (self.isInitSubscribe) return;
            self.isInitSubscribe = true;

            self.dataScreen().calcMethod.subscribe(value => {
                self.condition43(value, self.paymentItemSet().taxAtr());
            });

            self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition8(value);
            });

            self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition9(value);
            });

            self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition10(value);
            });

            self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition11(value);
            });

            self.dataScreen().calcMethod.subscribe(value => {
                self.condition12(self.paymentItemSet().taxAtr(), value);
                self.condition13(self.paymentItemSet().taxAtr(), value);
                self.condition14(value);
                self.condition15(value);
                self.condition16(value);
            })
        }

        unselectedMode() {
            let self = this;
            self.codeSelected(null);
            self.paymentItemSet = ko.observable(new PaymentItemSet(null));
            self.condition6(self.itemNames(), self.codeSelected());
            self.condition7(self.itemNames());
            self.screenControl().visibleD2_2(false);
            self.screenControl().visibleD2_3(false);
            self.screenControl().enableD2_5(false);
            self.screenControl().enableD2_8(false);
            self.screenControl().enableD2_10(false);
            self.screenControl().enableD3_2(false);
            self.screenControl().enableD3_6(false);
            self.screenControl().enableD3_9(false);
            self.screenControl().visibleD3_10(false);
            self.screenControl().enableD3_10(false);
            self.screenControl().visibleD3_11(false);
            self.screenControl().enableD3_12(false);
            self.screenControl().visibleD3_13(false);
            self.screenControl().enableD3_13(false);
            self.screenControl().visibleD3_14(false);
            self.screenControl().enableD3_16(false);
            self.screenControl().visibleD3_17(false);
            self.screenControl().enableD3_17(false);
            self.screenControl().visibleD3_18(false);
            self.screenControl().enableD3_19(false);
            self.screenControl().visibleD3_20(false);
            self.screenControl().enableD3_20(false);
            self.screenControl().visibleD3_21(false);
            self.screenControl().visibleD3_22(false);
            self.screenControl().visibleD3_23(false);
            self.screenControl().enableD4_1(false);
            self.screenControl().visibleD5(false);
            self.screenControl().visibleD6(false);
            self.screenControl().visibleD7(false);
            self.screenControl().visibleD8(false);
            self.screenControl().visibleD9(false);
        }

        selectedMode(defaultAtr: shareModel.DefaultAtr) {
            let self = this;
            self.initSubscribeDataScreen();
            if (defaultAtr != share.model.DefaultAtr.SYSTEM_DEFAULT) {
                self.screenControl().visibleD2_2(true);
                self.screenControl().visibleD2_3(true);
                self.screenControl().enableD2_5(true);
                self.condition43(self.dataScreen().calcMethod(), self.paymentItemSet().taxAtr());
                self.screenControl().enableD2_10(true);
                self.screenControl().enableD3_2(true);
                self.screenControl().enableD3_6(true);
                self.screenControl().enableD3_9(true);
                self.screenControl().visibleD3_10(true);
                self.condition8(self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_11(true);
                self.screenControl().enableD3_12(true);
                self.screenControl().visibleD3_13(true);
                self.condition9(self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_14(true);
                self.screenControl().enableD3_16(true);
                self.screenControl().visibleD3_17(true);
                self.condition10(self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_18(true);
                self.screenControl().enableD3_19(true);
                self.screenControl().visibleD3_20(true);
                self.condition11(self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_21(true);
                self.screenControl().visibleD3_22(true);
                self.screenControl().visibleD3_23(true);
                self.screenControl().enableD4_1(true);
                self.condition12(self.paymentItemSet().taxAtr(), self.dataScreen().calcMethod());
                self.condition13(self.paymentItemSet().taxAtr(), self.dataScreen().calcMethod());
                self.condition14(self.dataScreen().calcMethod());
                self.condition15(self.dataScreen().calcMethod());
                self.condition16(self.dataScreen().calcMethod());
            } else {
                self.screenControl().visibleD2_2(true);
                self.screenControl().visibleD2_3(true);
                self.screenControl().enableD2_5(false);
                self.screenControl().enableD2_8(false);
                self.screenControl().enableD2_10(true);
                self.screenControl().enableD3_2(false);
                self.screenControl().enableD3_6(false);
                self.screenControl().enableD3_9(true);
                self.screenControl().visibleD3_10(true);
                self.condition8(self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_11(true);
                self.screenControl().enableD3_12(true);
                self.screenControl().visibleD3_13(true);
                self.condition9(self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_14(true);
                self.screenControl().enableD3_16(true);
                self.screenControl().visibleD3_17(true);
                self.condition10(self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_18(true);
                self.screenControl().enableD3_19(true);
                self.screenControl().visibleD3_20(true);
                self.condition11(self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleD3_21(true);
                self.screenControl().visibleD3_22(true);
                self.screenControl().visibleD3_23(true);
                self.screenControl().enableD4_1(true);
                self.condition12(self.paymentItemSet().taxAtr(), self.dataScreen().calcMethod());
                self.condition13(self.paymentItemSet().taxAtr(), self.dataScreen().calcMethod());
                self.condition14(self.dataScreen().calcMethod());
                self.condition15(self.dataScreen().calcMethod());
                self.condition16(self.dataScreen().calcMethod());
            }
        }

        condition6(list: Array, code: string) {
            if (!_.isEmpty(list) && isNullOrEmpty(code)) {
                // D10_1
                $(".userguide-register").ntsUserGuide("show");
                // D10_2
                $(".userguide-exist").ntsUserGuide("show");
            }
        }

        condition7(list: Array) {
            if (_.isEmpty(list)) {
                // D10_3
                $(".userguide-not-register").ntsUserGuide("show");
            }
        }

        condition8(check: boolean) {
            let self = this;
            self.screenControl().enableD3_10(check);
        }

        condition9(check: boolean) {
            let self = this;
            self.screenControl().enableD3_13(check);
        }

        condition10(check: boolean) {
            let self = this;
            self.screenControl().enableD3_17(check);
        }

        condition11(check: boolean) {
            let self = this;
            self.screenControl().enableD3_20(check);
        }

        condition12(taxAtr: number, calcMethod: any) {
            let self = this;
            if (taxAtr == shareModel.TaxAtr.COMMUTING_EXPENSES_MANUAL && calcMethod != shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF) {
                self.screenControl().visibleD5(true);
            } else {
                self.screenControl().visibleD5(false);
            }
        }

        condition13(taxAtr: number, calcMethod: any) {
            let self = this;
            if (taxAtr != shareModel.TaxAtr.COMMUTING_EXPENSES_MANUAL && calcMethod == shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF) {
                self.screenControl().visibleD6(true);
            } else {
                self.screenControl().visibleD6(false);
            }
        }

        condition14(calcMethod: any) {
            let self = this;
            if (calcMethod == shareModel.PaymentCaclMethodAtr.CACL_FOMULA) {
                self.screenControl().visibleD7(true);
            } else {
                self.screenControl().visibleD7(false);
            }
        }

        condition15(calcMethod: any) {
            let self = this;
            if (calcMethod == shareModel.PaymentCaclMethodAtr.WAGE_TABLE) {
                self.screenControl().visibleD8(true);
            } else {
                self.screenControl().visibleD8(false);
            }
        }

        condition16(calcMethod: any) {
            let self = this;
            if (calcMethod == shareModel.PaymentCaclMethodAtr.COMMON_AMOUNT) {
                self.screenControl().visibleD9(true);
            } else {
                self.screenControl().visibleD9(false);
            }
        }

        condition43(calcMethod: any, taxAtr: any) {
            let self = this;
            self.screenControl().enableD2_8(calcMethod != shareModel.PaymentCaclMethodAtr.BREAKDOWN_ITEM && taxAtr != shareModel.TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER);
        }

        register() {

        }

        openI() {
            let self = this;
            windows.setShared("QMM019I_PARAMS", {
                cateIndicator: self.categoryAtr,
                individualPriceCode: self.dataScreen().perValCode()
            });
            modal("/view/qmm/019/i/index.xhtml").onClosed(() => {
                let results = windows.getShared("QMM019I_RESULTS");
                if (!isNullOrUndefined(results)) {
                    self.dataScreen().perValCode(results.individualPriceCode);
                    self.dataScreen().perValName(results.individualPriceName);
                }
            });
        }

        openM() {
            let self = this;
            windows.setShared("QMM019M_PARAMS", {
                yearMonth: self.dataScreen().yearMonth,
                formulaCode: self.dataScreen().formulaCode()
            });
            modal("/view/qmm/019/m/index.xhtml").onClosed(() => {
                let results = windows.getShared("QMM019M_RESULTS");
                if (!isNullOrUndefined(results)) {
                    self.dataScreen().formulaCode(results.formulaCode);
                    self.dataScreen().formulaName(results.formulaName);
                }
            });
        }

        openN() {
            let self = this;
            windows.setShared("QMM019N_PARAMS", {
                yearMonth: self.dataScreen().yearMonth,
                wageTableCode: self.dataScreen().wageTableCode()
            });
            modal("/view/qmm/019/n/index.xhtml").onClosed(() => {
                let results = windows.getShared("QMM019N_RESULTS");
                if (!isNullOrUndefined(results)) {
                    self.dataScreen().wageTableCode(results.wageTableCode);
                    self.dataScreen().wageTableName(results.wageTableName);
                }
            });
        }

        decide() {
            let self = this;
            // アルゴリズム「決定時チェック処理」を実施
            self.dataScreen().checkDecide();

            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    class ScreenControl {
        // screenMode: ScreenMode = ScreenMode.UNSELECTED;
        visibleD2_2: KnockoutObservable<boolean> = ko.observable(false);
        visibleD2_3: KnockoutObservable<boolean> = ko.observable(false);
        enableD2_5: KnockoutObservable<boolean> = ko.observable(false);
        enableD2_8: KnockoutObservable<boolean> = ko.observable(false);
        enableD2_10: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_2: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_6: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_9: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_10: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_10: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_11: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_12: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_13: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_13: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_14: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_16: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_17: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_17: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_18: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_19: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_20: KnockoutObservable<boolean> = ko.observable(false);
        enableD3_20: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_21: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_22: KnockoutObservable<boolean> = ko.observable(false);
        visibleD3_23: KnockoutObservable<boolean> = ko.observable(false);
        enableD4_1: KnockoutObservable<boolean> = ko.observable(false);
        visibleD5: KnockoutObservable<boolean> = ko.observable(false);
        visibleD6: KnockoutObservable<boolean> = ko.observable(false);
        visibleD7: KnockoutObservable<boolean> = ko.observable(false);
        visibleD8: KnockoutObservable<boolean> = ko.observable(false);
        visibleD9: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
        }
    }

    /*enum ScreenMode {
        UNSELECTED = 0,
        SELECTED = 1,
        SYSTEM = 2
    }*/

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
        yearMonth: number;
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
        yearMonth: number;
        /**
         * 項目名コード
         */
        itemNameCode: KnockoutObservable<string>;
        /**
         * 名称
         */
        name: KnockoutObservable<string>;
        /**
         * 通勤区分
         */
        workingAtr: KnockoutObservable<string>;
        /**
         * 合計対象
         */
        totalObject: KnockoutObservable<string>;
        /**
         * 計算方法
         */
        calcMethod: KnockoutObservable<string>;
        /**
         * 按分区分
         */
        proportionalAtr: KnockoutObservable<string>;
        /**
         * 按分方法
         */
        proportionalMethod: KnockoutObservable<string>;
        /**
         * 範囲値の属性
         */
        rangeValAttribute: KnockoutObservable<string>;
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
            let self = this;
            self.name = ko.observable(null);
            self.perValName = ko.observable(null);
            self.formulaName = ko.observable(null);
            self.wageTableName = ko.observable(null);
            if (isNullOrUndefined(data)) {
                self.yearMonth = null;
                self.itemNameCode = ko.observable(null);
                self.workingAtr = ko.observable(null);
                self.totalObject = ko.observable(null);
                self.calcMethod = ko.observable(null);
                self.proportionalAtr = ko.observable(null);
                self.proportionalMethod = ko.observable(null);
                self.rangeValAttribute = ko.observable(null);
                self.errorRangeSetting = new ErrorAlarmRangeSetting(null);
                self.alarmRangeSetting = new ErrorAlarmRangeSetting(null);
                self.perValCode = ko.observable(null);
                self.formulaCode = ko.observable(null);
                self.wageTableCode = ko.observable(null);
                self.commonAmount = ko.observable(null);
            } else {
                self.yearMonth = data.yearMonth;
                self.itemNameCode = ko.observable(data.itemNameCode);
                self.workingAtr = ko.observable(isNullOrUndefined(data.workingAtr) ? null : data.workingAtr.toString());
                self.totalObject = ko.observable(isNullOrUndefined(data.totalObject) ? null : data.totalObject.toString());
                self.calcMethod = ko.observable(isNullOrUndefined(data.calcMethod) ? null : data.calcMethod.toString());
                self.proportionalAtr = ko.observable(isNullOrUndefined(data.proportionalAtr) ? null : data.proportionalAtr.toString());
                self.proportionalMethod = ko.observable(isNullOrUndefined(data.proportionalMethod) ? null : data.proportionalMethod.toString());
                self.rangeValAttribute = ko.observable(isNullOrUndefined(data.rangeValAttribute) ? null : data.rangeValAttribute.toString());
                self.errorRangeSetting = new ErrorAlarmRangeSetting(data.errorRangeSetting);
                self.alarmRangeSetting = new ErrorAlarmRangeSetting(data.alarmRangeSetting);
                self.perValCode = ko.observable(data.perValCode);
                self.formulaCode = ko.observable(data.formulaCode);
                self.wageTableCode = ko.observable(data.wageTableCode);
                self.commonAmount = ko.observable(data.commonAmount);
            }

            self.calcMethod.subscribe(() => {
                self.perValCode(null);
                self.perValName(null);
                self.clearError("#D6_2");
                self.formulaCode(null);
                self.formulaName(null);
                self.clearError("#D7_2");
                self.wageTableCode(null);
                self.wageTableName(null);
                self.clearError("#D8_2");
                self.commonAmount(null);
                self.clearError("#D9_2");
            });
            self.perValCode.subscribe(() => {
                self.clearError("#D6_2");
            });
            self.formulaCode.subscribe(() => {
                self.clearError("#D7_2");
            });
            self.wageTableCode.subscribe(() => {
                self.clearError("#D8_2");
            });

            self.errorRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.errorRangeSetting.upperLimitSetting.rangeValue(null);
                self.clearError("#D3_10");
            });
            self.errorRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
                self.errorRangeSetting.lowerLimitSetting.rangeValue(null);
                self.clearError("#D3_13");
            });
            self.errorRangeSetting.upperLimitSetting.rangeValue.subscribe(() => {
                self.clearError("#D3_13");
                self.checkError("#D3_13");
            });

            self.alarmRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.alarmRangeSetting.upperLimitSetting.rangeValue(null);
                self.clearError("#D3_17");
            });
            self.alarmRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
                self.alarmRangeSetting.lowerLimitSetting.rangeValue(null);
                self.clearError("#D3_20");
            });
            self.alarmRangeSetting.upperLimitSetting.rangeValue.subscribe(() => {
                self.clearError("#D3_20");
                self.checkError("#D3_20");
            });
        }

        /**
         * 決定時チェック処理
         */
        checkDecide() {
            let self = this;
            // self.validate();
            self.checkCalcMethod();
            self.checkErrorRange();
            self.checkAlarmRange();
        }

        validate() {
            let self = this;
            self.checkError("#D2_5");
            self.checkError("#D2_8");
            self.checkError("#D5_2");
            self.checkError("#D9_2");
        }

        checkCalcMethod() {
            let self = this;
            // 設定された計算方法を確認する
            switch (parseInt(self.calcMethod())) {
                case shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF:
                    // 個人金額コードが設定されているか確認する
                    if (isNullOrEmpty(self.perValCode())) {
                        // alertError({messageId: "MsgQ_11"});
                        self.setError("#D6_2", "MsgQ_11");
                    }
                    break;
                case shareModel.PaymentCaclMethodAtr.CACL_FOMULA:
                    // 計算式コードが設定されているか確認する
                    if (isNullOrEmpty(self.formulaCode())) {
                        // alertError({messageId: "MsgQ_12"});
                        self.setError("#D7_2", "MsgQ_12");
                    }
                    break;
                case shareModel.PaymentCaclMethodAtr.WAGE_TABLE:
                    // 賃金テーブルコードが設定されているか確認する
                    if (isNullOrEmpty(self.wageTableCode())) {
                        // alertError({messageId: "MsgQ_13"});
                        self.setError("#D8_2", "MsgQ_13");
                    }
                    break;
                default:
                    break;
            }
        }

        checkErrorRange() {
            let self = this;
            // エラー範囲上限値チェック状況を確認する
            if (self.errorRangeSetting.upperLimitSetting.valueSettingAtr()) {
                // エラー範囲上限値の入力を確認する
                if (isNullOrEmpty(self.errorRangeSetting.upperLimitSetting.rangeValue())) {
                    // alertError({messageId: "MsgQ_14"});
                    self.setError("#D3_10", "MsgQ_14");
                } else {
                    // エラー範囲下限値チェック状況を確認する
                    if (self.errorRangeSetting.lowerLimitSetting.valueSettingAtr()) {
                        // エラー範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.errorRangeSetting.lowerLimitSetting.rangeValue())) {
                            // alertError({messageId: "MsgQ_15"});
                            self.setError("#D3_13", "MsgQ_15");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.errorRangeSetting.lowerLimitSetting.rangeValue()) > parseInt(self.errorRangeSetting.upperLimitSetting.rangeValue())) {
                                // alertError({messageId: "MsgQ_1"});
                                self.setError("#D3_13", "MsgQ_1");
                            }
                        }
                    }
                }
            } else {
                // エラー範囲下限値チェック状況を確認する
                if (self.errorRangeSetting.upperLimitSetting.valueSettingAtr()) {
                    // エラー範囲下限値の入力を確認する
                    if (isNullOrEmpty(self.errorRangeSetting.lowerLimitSetting.rangeValue())) {
                        // alertError({messageId: "MsgQ_15"});
                        self.setError("#D3_13", "MsgQ_15");
                    }
                }
            }
        }

        checkAlarmRange() {
            let self = this;
            // アラーム範囲上限値チェック状況を確認する
            if (self.alarmRangeSetting.upperLimitSetting.valueSettingAtr()) {
                // アラーム範囲上限値の入力を確認する
                if (isNullOrEmpty(self.alarmRangeSetting.upperLimitSetting.rangeValue())) {
                    // alertError({messageId: "MsgQ_16"});
                    self.setError("#D3_17", "MsgQ_16");
                } else {
                    // アラーム範囲下限値チェック状況を確認する
                    if (self.alarmRangeSetting.lowerLimitSetting.valueSettingAtr()) {
                        // アラーム範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.alarmRangeSetting.lowerLimitSetting.rangeValue())) {
                            // alertError({messageId: "MsgQ_17"});
                            self.setError("#D3_20", "MsgQ_17");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.alarmRangeSetting.lowerLimitSetting.rangeValue()) > parseInt(self.alarmRangeSetting.upperLimitSetting.rangeValue())) {
                                // alertError({messageId: "MsgQ_2"});
                                self.setError("#D3_20", "MsgQ_2");
                            }
                        }
                    }
                }
            } else {
                // アラーム範囲下限値チェック状況を確認する
                if (self.alarmRangeSetting.lowerLimitSetting.valueSettingAtr()) {
                    // アラーム範囲下限値の入力を確認する
                    if (isNullOrEmpty(self.alarmRangeSetting.lowerLimitSetting.rangeValue())) {
                        // alertError({messageId: "MsgQ_17"});
                        self.setError("#D3_20", "MsgQ_17");
                    }
                }
            }
        }

        setError(control, messageId) {
            $(control).ntsError('set', {messageId: messageId});
        }

        clearError(control) {
            $(control).ntsError('clear');
        }

        checkError(control) {
            $(control).ntsError('check');
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
        valueSettingAtr: KnockoutObservable<boolean>;
        /**
         * 範囲値
         */
        rangeValue: KnockoutObservable<string>;

        constructor(data: IErrorAlarmValueSetting) {
            if (isNullOrUndefined(data)) {
                this.valueSettingAtr = ko.observable(false);
                this.rangeValue = ko.observable(null);
                return;
            }
            this.valueSettingAtr = ko.observable(data.valueSettingAtr == shareModel.UseRangeAtr.USE);
            this.rangeValue = ko.observable(isNullOrUndefined(data.rangeValue) ? null : data.rangeValue.toString());
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