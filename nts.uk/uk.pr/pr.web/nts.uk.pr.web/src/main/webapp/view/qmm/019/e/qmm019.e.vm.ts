module nts.uk.pr.view.qmm019.e.viewmodel {
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import windows = nts.uk.ui.windows;
    import modal = nts.uk.ui.windows.sub.modal;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;

    export class ScreenModel {
        screenControl: KnockoutObservable<ScreenControl>;
        option: any;

        selectedSearchIitemName: KnockoutObservable<any>;
        itemNames: KnockoutObservableArray<shareModel.ItemModel>;
        codeSelected: KnockoutObservable<any>;

        categoryAtr: number;
        totalObjAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        calcMethods: KnockoutObservableArray<shareModel.ItemModel>;
        workingAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        deductionProportionalAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        proportionalMethodAtrs: KnockoutObservableArray<shareModel.ItemModel>;

        params: IParams;
        yearMonth: number;
        dataScreen: KnockoutObservable<Params>;
        categoryAtrText: KnockoutObservable<string>;
        deductionItemSet: KnockoutObservable<DeductionItemSet>;
        breakdownItemSets: KnockoutObservableArray<BreakdownItemSet>;

        constructor() {
            let self = this;
            self.screenControl = ko.observable(new ScreenControl());
            self.option = {
                grouplength: 3,
                textalign: "right",
                currencyformat: "JPY"
            }

            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);

            self.categoryAtr = shareModel.CategoryAtr.DEDUCTION_ITEM;
            self.totalObjAtrs = ko.observableArray([]);
            self.calcMethods = ko.observableArray(shareModel.getDeductionCaclMethodAtr());
            self.workingAtrs = ko.observableArray(shareModel.getWorkingAtr());
            self.deductionProportionalAtrs = ko.observableArray(shareModel.getDeductionProportionalAtr())
            self.proportionalMethodAtrs = ko.observableArray(shareModel.getProportionalMethodAtr())

            self.dataScreen = ko.observable(new Params());
            self.categoryAtrText = ko.observable(null);
            self.deductionItemSet = ko.observable(new DeductionItemSet());
            self.breakdownItemSets = ko.observableArray([]);

            // E10_1
            $("[data-toggle='userguide-register']").ntsUserGuide();
            // E10_2
            $("[data-toggle='userguide-exist']").ntsUserGuide();
            // E10_3
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
            })

            self.dataScreen().calcMethod.subscribe(value => {
                self.condition22(value);
                self.condition23(value);
                self.condition24(value);
                self.condition25(value);
                self.condition26(value);
                self.condition44(value);
            });

            self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition18(value);
            });

            self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition19(value);
            });

            self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition20(value);
            });

            self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition21(value);
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            $("#fixed-table").ntsFixedTable({height: 139});
            let params: IParams = <IParams>{};
            params.itemNameCode = "0001";
            params.itemNameCdExcludeList = ["0003", "0031"];
            params.printSet = shareModel.StatementPrintAtr.DO_NOT_PRINT;
            params.yearMonth = 201802;
            params.workingAtr = null;
            params.totalObject = null;
            params.calcMethod = null;
            params.proportionalAtr = null;
            params.proportionalMethod = null;
            params.rangeValAttribute = null;
            params.errorRangeSetting = <IErrorAlarmRangeSetting>{};
            params.errorRangeSetting.upperLimitSetting = <IErrorAlarmValueSetting>{};
            params.errorRangeSetting.upperLimitSetting.valueSettingAtr = 1;
            params.errorRangeSetting.upperLimitSetting.rangeValue = 100;
            params.errorRangeSetting.lowerLimitSetting = <IErrorAlarmValueSetting>{};
            params.errorRangeSetting.lowerLimitSetting.valueSettingAtr = 0;
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
            self.params = params;
            self.yearMonth = params.yearMonth;
            self.condition42();
            let dto = {
                categoryAtr: self.categoryAtr,
                itemNameCdSelected: self.params.itemNameCode,
                itemNameCdExcludeList: self.params.itemNameCdExcludeList
            };
            service.getStatementItem(dto).done((data: Array<IStatementItem>) => {
                self.itemNames(StatementItem.fromApp(data));
                //self.initScreen();
                dfd.resolve();
            });
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
            // TODO #125441
            // パラメータを受け取り取得した情報と合わせて画面上に表示する
            self.dataScreen().setData(self.params);
        }

        getDataAccordion(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            // ドメインモデル「支給項目設定」を取得する
            let sv1 = service.getDeductionItemStById(self.categoryAtr, self.dataScreen().itemNameCode());
            // ドメインモデル「内訳項目設定」を取得する
            let sv2 = service.getAllBreakdownItemSetById(self.categoryAtr, self.dataScreen().itemNameCode());
            // ドメインモデル「給与個人別金額名称」を取得する
            let sv3 = service.getSalIndAmountNameById(self.dataScreen().perValCode(), self.categoryAtr);
            // ドメインモデル「計算式」を取得する
            let sv4 = service.getFormulaById(self.dataScreen().formulaCode());
            // ドメインモデル「賃金テーブル」を取得する
            let sv5 = service.getWageTableById(self.dataScreen().wageTableCode());
            $.when(sv1, sv2, sv3, sv4, sv5).done((dedu: IDeductionItemSet,
                                                  breakItems: Array<IBreakdownItemSet>,
                                                  perVal: any,
                                                  formula: any,
                                                  wageTable: any) => {
                self.categoryAtrText(shareModel.getCategoryAtrText(self.categoryAtr));
                if (!isNullOrUndefined(dedu)) {
                    self.deductionItemSet().setData(dedu);
                }
                self.breakdownItemSets(_.isEmpty(breakItems) ? [] : BreakdownItemSet.fromApp(breakItems));
                self.dataScreen().perValName(isNullOrUndefined(perVal) ? null : perVal.individualPriceName);
                self.dataScreen().formulaName(isNullOrUndefined(formula) ? null : formula.formulaName);
                self.dataScreen().wageTableName(isNullOrUndefined(wageTable) ? null : wageTable.wageTableName);
                dfd.resolve();
            });

            return dfd.promise();
        }

        unselectedMode() {
            let self = this;
            self.codeSelected(null);
            self.condition6(self.itemNames(), self.codeSelected());
            self.condition7(self.itemNames());
            self.screenControl().visibleE2_2(false);
            self.screenControl().visibleE2_3(false);
            self.screenControl().enableE2_5(false);
            self.screenControl().enableE2_8(false);
            self.screenControl().enableE2_10(false);
            self.screenControl().enableE3_2(false);
            self.screenControl().enableE3_6(false);
            self.screenControl().enableE3_9(false);
            self.screenControl().visibleE3_10(false);
            self.screenControl().enableE3_10(false);
            self.screenControl().visibleE3_11(false);
            self.screenControl().enableE3_12(false);
            self.screenControl().visibleE3_13(false);
            self.screenControl().enableE3_13(false);
            self.screenControl().visibleE3_14(false);
            self.screenControl().enableE3_16(false);
            self.screenControl().visibleE3_17(false);
            self.screenControl().enableE3_17(false);
            self.screenControl().visibleE3_18(false);
            self.screenControl().enableE3_19(false);
            self.screenControl().visibleE3_20(false);
            self.screenControl().enableE3_20(false);
            self.screenControl().visibleE3_21(false);
            self.screenControl().visibleE3_22(false);
            self.screenControl().visibleE3_23(false);
            self.screenControl().enableE4_1(false);
            self.screenControl().visibleE5(false);
            self.screenControl().visibleE6(false);
            self.screenControl().visibleE7(false);
            self.screenControl().visibleE8(false);
            self.screenControl().visibleE9(false);
            nts.uk.ui.errors.clearAll();
            $("#btn-register").focus();
        }

        selectedMode(defaultAtr: shareModel.DefaultAtr) {
            let self = this;
            if (defaultAtr != share.model.DefaultAtr.SYSTEM_DEFAULT) {
                self.screenControl().visibleE2_2(true);
                self.screenControl().visibleE2_3(true);
                self.screenControl().enableE2_5(true);
                self.condition44(self.dataScreen().calcMethod());
                self.screenControl().enableE2_10(true);
                self.screenControl().enableE3_2(true);
                self.screenControl().enableE3_6(true);
                self.screenControl().enableE3_9(true);
                self.screenControl().visibleE3_10(true);
                self.condition18(self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_11(true);
                self.screenControl().enableE3_12(true);
                self.screenControl().visibleE3_13(true);
                self.condition19(self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_14(true);
                self.screenControl().enableE3_16(true);
                self.screenControl().visibleE3_17(true);
                self.condition20(self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_18(true);
                self.screenControl().enableE3_19(true);
                self.screenControl().visibleE3_20(true);
                self.condition21(self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_21(true);
                self.screenControl().visibleE3_22(true);
                self.screenControl().visibleE3_23(true);
                self.screenControl().enableE4_1(true);
                self.condition22(self.dataScreen().calcMethod());
                self.condition23(self.dataScreen().calcMethod());
                self.condition24(self.dataScreen().calcMethod());
                self.condition25(self.dataScreen().calcMethod());
                self.condition26(self.dataScreen().calcMethod());
            } else {
                self.screenControl().visibleE2_2(true);
                self.screenControl().visibleE2_3(true);
                self.screenControl().enableE2_5(false);
                self.screenControl().enableE2_8(false);
                self.screenControl().enableE2_10(true);
                self.screenControl().enableE3_2(false);
                self.screenControl().enableE3_6(false);
                self.screenControl().enableE3_9(true);
                self.screenControl().visibleE3_10(true);
                self.condition18(self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_11(true);
                self.screenControl().enableE3_12(true);
                self.screenControl().visibleE3_13(true);
                self.condition19(self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_14(true);
                self.screenControl().enableE3_16(true);
                self.screenControl().visibleE3_17(true);
                self.condition20(self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_18(true);
                self.screenControl().enableE3_19(true);
                self.screenControl().visibleE3_20(true);
                self.condition21(self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr());
                self.screenControl().visibleE3_21(true);
                self.screenControl().visibleE3_22(true);
                self.screenControl().visibleE3_23(true);
                self.screenControl().enableE4_1(true);
                self.screenControl().visibleE5(false);
                self.screenControl().visibleE6(false);
                self.screenControl().visibleE7(false);
                self.screenControl().visibleE8(false);
                self.screenControl().visibleE9(false);
            }
            nts.uk.ui.errors.clearAll();
            $("#E1_6_container").focus();
        }

        /**
         * ※6
         */
        condition6(list: Array, code: string) {
            if (!_.isEmpty(list) && isNullOrEmpty(code)) {
                // E10_1
                $(".userguide-register").ntsUserGuide("show");
                // E10_2
                $(".userguide-exist").ntsUserGuide("show");
            }
        }

        /**
         * ※7
         */
        condition7(list: Array) {
            if (_.isEmpty(list)) {
                // E10_3
                $(".userguide-not-register").ntsUserGuide("show");
            }
        }

        /**
         * ※18
         */
        condition18(check: boolean) {
            let self = this;
            self.screenControl().enableE3_10(check);
        }

        /**
         * ※19
         */
        condition19(check: boolean) {
            let self = this;
            self.screenControl().enableE3_13(check);
        }

        /**
         * ※20
         */
        condition20(check: boolean) {
            let self = this;
            self.screenControl().enableE3_17(check);
        }

        /**
         * ※21
         */
        condition21(check: boolean) {
            let self = this;
            self.screenControl().enableE3_20(check);
        }

        /**
         * ※22
         */
        condition22(calcMethod: any) {
            let self = this;
            self.screenControl().visibleE5(calcMethod == shareModel.DeductionCaclMethodAtr.PERSON_INFO_REF);
        }

        /**
         * ※23
         */
        condition23(calcMethod: any) {
            let self = this;
            self.screenControl().visibleE6(calcMethod == shareModel.DeductionCaclMethodAtr.CACL_FOMULA);
        }

        /**
         * ※24
         */
        condition24(calcMethod: any) {
            let self = this;
            self.screenControl().visibleE7(calcMethod == shareModel.DeductionCaclMethodAtr.WAGE_TABLE);
        }

        /**
         * ※25
         */
        condition25(calcMethod: any) {
            let self = this;
            self.screenControl().visibleE8(calcMethod == shareModel.DeductionCaclMethodAtr.COMMON_AMOUNT);
        }

        /**
         * ※26
         */
        condition26(calcMethod: any) {
            let self = this;
            self.screenControl().visibleE9(calcMethod == shareModel.DeductionCaclMethodAtr.SUPPLY_OFFSET);
        }

        /**
         * ※42：補足資料7を参照
         */
        condition42() {
            let self = this;
            self.totalObjAtrs(shareModel.getDeductionTotalObjAtr(self.params.printSet));
            if (self.params.totalObject == shareModel.DeductionTotalObjAtr.INSIDE) {
                self.params.totalObject = shareModel.DeductionTotalObjAtr.OUTSIDE;
            }
        }

        /**
         * ※44
         */
        condition44(calcMethod: any) {
            let self = this;
            self.screenControl().enableE2_8(calcMethod != shareModel.DeductionCaclMethodAtr.BREAKDOWN_ITEM);
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
                yearMonth: self.yearMonth,
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
                yearMonth: self.yearMonth,
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

        openO() {
            let self = this;
            windows.setShared("QMM019O_PARAMS", {
                yearMonth: self.yearMonth,
                itemNameCd: self.dataScreen().statementItemCode()
            });
            modal("/view/qmm/019/o/index.xhtml").onClosed(() => {
                let results = windows.getShared("QMM019O_RESULTS");
                if (!isNullOrUndefined(results)) {
                    self.dataScreen().statementItemCode(results.itemNameCd);
                    self.dataScreen().statementItemName(results.name);
                }
            });
        }

        decide() {
            let self = this;
            // アルゴリズム「決定時チェック処理」を実施
            self.dataScreen().checkDecide(self.deductionItemSet().taxAtr());
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }

    class ScreenControl {
        visibleE2_2: KnockoutObservable<boolean> = ko.observable(false);
        visibleE2_3: KnockoutObservable<boolean> = ko.observable(false);
        enableE2_5: KnockoutObservable<boolean> = ko.observable(false);
        enableE2_8: KnockoutObservable<boolean> = ko.observable(false);
        enableE2_10: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_2: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_6: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_9: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_10: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_10: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_11: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_12: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_13: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_13: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_14: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_16: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_17: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_17: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_18: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_19: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_20: KnockoutObservable<boolean> = ko.observable(false);
        enableE3_20: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_21: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_22: KnockoutObservable<boolean> = ko.observable(false);
        visibleE3_23: KnockoutObservable<boolean> = ko.observable(false);
        enableE4_1: KnockoutObservable<boolean> = ko.observable(false);
        visibleE5: KnockoutObservable<boolean> = ko.observable(false);
        visibleE6: KnockoutObservable<boolean> = ko.observable(false);
        visibleE7: KnockoutObservable<boolean> = ko.observable(false);
        visibleE8: KnockoutObservable<boolean> = ko.observable(false);
        visibleE9: KnockoutObservable<boolean> = ko.observable(false);

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
        printSet: number;
        yearMonth: number;
        itemNameCode: string;
        itemNameCdExcludeList: Array<string>;
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
        statementItemCode: string;
        commonAmount: number;
    }

    class Params {
        /**
         * 項目名コード
         */
        itemNameCode: KnockoutObservable<string> = ko.observable(null);
        /**
         * 名称
         */
        name: KnockoutObservable<string> = ko.observable(null);
        /**
         * 通勤区分
         */
        workingAtr: KnockoutObservable<string> = ko.observable(null);
        /**
         * 合計対象
         */
        totalObject: KnockoutObservable<string> = ko.observable(null);
        /**
         * 計算方法
         */
        calcMethod: KnockoutObservable<string> = ko.observable(null);
        /**
         * 按分区分
         */
        proportionalAtr: KnockoutObservable<string> = ko.observable(null);
        /**
         * 按分方法
         */
        proportionalMethod: KnockoutObservable<string> = ko.observable(null);
        /**
         * 範囲値の属性
         */
        rangeValAttribute: KnockoutObservable<string> = ko.observable(null);
        /**
         * エラー範囲設定
         */
        errorRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();
        /**
         * アラーム範囲設定
         */
        alarmRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();
        /**
         * 個人金額コード
         */
        perValCode: KnockoutObservable<string> = ko.observable(null);
        /**
         * 個人金額名称
         */
        perValName: KnockoutObservable<string> = ko.observable(null);
        /**
         * 計算式コード
         */
        formulaCode: KnockoutObservable<string> = ko.observable(null);
        /**
         * 計算式名
         */
        formulaName: KnockoutObservable<string> = ko.observable(null);
        /**
         * 賃金テーブルコード
         */
        wageTableCode: KnockoutObservable<string> = ko.observable(null);
        /**
         * 賃金テーブル名
         */
        wageTableName: KnockoutObservable<string> = ko.observable(null);
        statementItemCode: KnockoutObservable<string> = ko.observable(null);
        statementItemName: KnockoutObservable<string> = ko.observable(null);
        /**
         * 共通金額
         */
        commonAmount: KnockoutObservable<number> = ko.observable(null);

        constructor() {
            let self = this;
            self.calcMethod.subscribe(() => {
                self.perValCode(null);
                self.perValName(null);
                self.clearError("#E6_2");
                self.formulaCode(null);
                self.formulaName(null);
                self.clearError("#E7_2");
                self.wageTableCode(null);
                self.wageTableName(null);
                self.clearError("#E8_2");
                self.commonAmount(null);
                self.clearError("#E9_2");
            });
            self.perValCode.subscribe(() => {
                self.clearError("#E6_2");
            });
            self.formulaCode.subscribe(() => {
                self.clearError("#E7_2");
            });
            self.wageTableCode.subscribe(() => {
                self.clearError("#E8_2");
            });

            self.errorRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.errorRangeSetting.upperLimitSetting.rangeValue(null);
                self.clearError("#E3_10");
            });
            self.errorRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
                self.errorRangeSetting.lowerLimitSetting.rangeValue(null);
                self.clearError("#E3_13");
            });
            self.errorRangeSetting.upperLimitSetting.rangeValue.subscribe(() => {
                self.clearError("#E3_13");
                self.checkError("#E3_13");
            });

            self.alarmRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.alarmRangeSetting.upperLimitSetting.rangeValue(null);
                self.clearError("#E3_17");
            });
            self.alarmRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
                self.alarmRangeSetting.lowerLimitSetting.rangeValue(null);
                self.clearError("#E3_20");
            });
            self.alarmRangeSetting.upperLimitSetting.rangeValue.subscribe(() => {
                self.clearError("#E3_20");
                self.checkError("#E3_20");
            });
        }

        setData(data: IParams) {
            let self = this;
            self.itemNameCode(data.itemNameCode);
            self.workingAtr(isNullOrUndefined(data.workingAtr) ? null : data.workingAtr.toString());
            self.totalObject(isNullOrUndefined(data.totalObject) ? null : data.totalObject.toString());
            self.calcMethod(isNullOrUndefined(data.calcMethod) ? null : data.calcMethod.toString());
            self.proportionalAtr(isNullOrUndefined(data.proportionalAtr) ? null : data.proportionalAtr.toString());
            self.proportionalMethod(isNullOrUndefined(data.proportionalMethod) ? null : data.proportionalMethod.toString());
            self.rangeValAttribute(isNullOrUndefined(data.rangeValAttribute) ? null : data.rangeValAttribute.toString());
            self.errorRangeSetting.setData(data.errorRangeSetting);
            self.alarmRangeSetting.setData(data.alarmRangeSetting);
            self.perValCode(data.perValCode);
            self.formulaCode(data.formulaCode);
            self.wageTableCode(data.wageTableCode);
            self.statementItemCode(data.statementItemCode);
            self.commonAmount(data.commonAmount);
        }

        /**
         * 決定時チェック処理
         */
        checkDecide(taxAtr: number) {
            let self = this;
            self.validate();
            self.checkCalcMethod(taxAtr);
            self.checkErrorRange();
            self.checkAlarmRange();
        }

        validate() {
            let self = this;
            self.checkError("#E2_5");
            self.checkError("#E2_8");
            self.checkError("#E9_2");
        }

        checkCalcMethod(taxAtr: number) {
            let self = this;
            // ※43
            // ※補足資料9を参照
            if (taxAtr == shareModel.TaxAtr.COMMUTING_EXPENSES_MANUAL
                && self.calcMethod() == shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF.toString()) {
                // alertError({messageId: "MsgQ_181"});
                self.setError("#E2_8", "MsgQ_181");
            }
            // 設定された計算方法を確認する
            switch (parseInt(self.calcMethod())) {
                case shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF:
                    // 個人金額コードが設定されているか確認する
                    if (isNullOrEmpty(self.perValCode())) {
                        // alertError({messageId: "MsgQ_11"});
                        self.setError("#E6_2", "MsgQ_11");
                    }
                    break;
                case shareModel.PaymentCaclMethodAtr.CACL_FOMULA:
                    // 計算式コードが設定されているか確認する
                    if (isNullOrEmpty(self.formulaCode())) {
                        // alertError({messageId: "MsgQ_12"});
                        self.setError("#E7_2", "MsgQ_12");
                    }
                    break;
                case shareModel.PaymentCaclMethodAtr.WAGE_TABLE:
                    // 賃金テーブルコードが設定されているか確認する
                    if (isNullOrEmpty(self.wageTableCode())) {
                        // alertError({messageId: "MsgQ_13"});
                        self.setError("#E8_2", "MsgQ_13");
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
                    self.setError("#E3_10", "MsgQ_14");
                } else {
                    // エラー範囲下限値チェック状況を確認する
                    if (self.errorRangeSetting.lowerLimitSetting.valueSettingAtr()) {
                        // エラー範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.errorRangeSetting.lowerLimitSetting.rangeValue())) {
                            // alertError({messageId: "MsgQ_15"});
                            self.setError("#E3_13", "MsgQ_15");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.errorRangeSetting.lowerLimitSetting.rangeValue()) > parseInt(self.errorRangeSetting.upperLimitSetting.rangeValue())) {
                                // alertError({messageId: "MsgQ_1"});
                                self.setError("#E3_13", "MsgQ_1");
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
                        self.setError("#E3_13", "MsgQ_15");
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
                    self.setError("#E3_17", "MsgQ_16");
                } else {
                    // アラーム範囲下限値チェック状況を確認する
                    if (self.alarmRangeSetting.lowerLimitSetting.valueSettingAtr()) {
                        // アラーム範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.alarmRangeSetting.lowerLimitSetting.rangeValue())) {
                            // alertError({messageId: "MsgQ_17"});
                            self.setError("#E3_20", "MsgQ_17");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.alarmRangeSetting.lowerLimitSetting.rangeValue()) > parseInt(self.alarmRangeSetting.upperLimitSetting.rangeValue())) {
                                // alertError({messageId: "MsgQ_2"});
                                self.setError("#E3_20", "MsgQ_2");
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
                        self.setError("#E3_20", "MsgQ_17");
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

        constructor() {
            this.upperLimitSetting = new ErrorAlarmValueSetting();
            this.lowerLimitSetting = new ErrorAlarmValueSetting();
        }

        setData(data: IErrorAlarmRangeSetting) {
            this.upperLimitSetting.setData(data.upperLimitSetting);
            this.lowerLimitSetting.setData(data.lowerLimitSetting);
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

        constructor() {
            this.valueSettingAtr = ko.observable(false);
            this.rangeValue = ko.observable(null);
        }

        setData(data: IErrorAlarmValueSetting) {
            this.valueSettingAtr(data.valueSettingAtr == shareModel.UseRangeAtr.USE);
            this.rangeValue(isNullOrUndefined(data.rangeValue) ? null : data.rangeValue.toString());
        }
    }

    interface IDeductionItemSet {
        taxAtr: number;
        limitAmount: number;
        socialInsuranceCategory: number;
        laborInsuranceCategory: number;
        everyoneEqualSet: number;
        averageWageAtr: number;
        errorRangeSetting: IErrorAlarmRangeSetting;
        alarmRangeSetting: IErrorAlarmRangeSetting;
    }

    class DeductionItemSet {
        /**
         * 課税区分
         */
        taxAtr: KnockoutObservable<number> = ko.observable(null);
        taxAtrText: KnockoutObservable<string> = ko.observable(null);
        /**
         * 限度金額設定.限度金額
         */
        limitAmount: KnockoutObservable<number> = ko.observable(null);
        /**
         * 社会保険区分
         */
        socialInsuranceCategory: KnockoutObservable<number> = ko.observable(null);
        socialInsuranceCategoryText: KnockoutObservable<string> = ko.observable(null);
        /**
         * 労働保険区分
         */
        laborInsuranceCategory: KnockoutObservable<number> = ko.observable(null);
        laborInsuranceCategoryText: KnockoutObservable<string> = ko.observable(null);
        /**
         * 固定的賃金
         */
        everyoneEqualSet: KnockoutObservable<number> = ko.observable(null);
        everyoneEqualSetText: KnockoutObservable<string> = ko.observable(null);
        /**
         * 平均賃金区分
         */
        averageWageAtr: KnockoutObservable<number> = ko.observable(null);
        averageWageAtrText: KnockoutObservable<string> = ko.observable(null);

        errorRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();
        alarmRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();

        constructor() {
        }

        setData(data: IDeductionItemSet) {
            this.taxAtr(data.taxAtr);
            this.taxAtrText(shareModel.getTaxAtrText(data.taxAtr));
            this.limitAmount(data.limitAmount);
            this.socialInsuranceCategory(data.socialInsuranceCategory);
            this.socialInsuranceCategoryText(shareModel.getSocialInsuranceCategoryText(data.socialInsuranceCategory));
            this.laborInsuranceCategory(data.laborInsuranceCategory);
            this.laborInsuranceCategoryText(shareModel.getLaborInsuranceCategoryText(data.laborInsuranceCategory));
            this.everyoneEqualSet(data.everyoneEqualSet);
            this.everyoneEqualSetText(shareModel.getCategoryFixedWageText(data.everyoneEqualSet));
            this.averageWageAtr(data.averageWageAtr);
            this.averageWageAtrText(shareModel.getAverageWageAtrText(data.averageWageAtr));
            this.errorRangeSetting.setData(data.errorRangeSetting);
            this.alarmRangeSetting.setData(data.alarmRangeSetting);
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