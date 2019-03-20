module nts.uk.pr.view.qmm019.d.viewmodel {
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import windows = nts.uk.ui.windows;
    import modal = nts.uk.ui.windows.sub.modal;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;
    import formatNumber = nts.uk.ntsNumber.formatNumber;

    export class ScreenModel {
        screenControl: KnockoutObservable<ScreenControl>;
        option: any;

        selectedSearchIitemName: KnockoutObservable<string>;
        itemNames: KnockoutObservableArray<StatementItem>;
        codeSelected: KnockoutObservable<string>;

        categoryAtr: number;
        totalObjAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        calcMethods: KnockoutObservableArray<shareModel.ItemModel>;
        workingAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        paymentProportionalAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        proportionalMethodAtrs: KnockoutObservableArray<shareModel.ItemModel>;

        params: IParams;
        yearMonth: number;
        dataScreen: KnockoutObservable<Params>;
        categoryAtrText: KnockoutObservable<string>;
        paymentItemSet: KnockoutObservable<PaymentItemSet>;
        breakdownItemSets: KnockoutObservableArray<BreakdownItemSet>;

        constructor() {
            let self = this;
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
            self.totalObjAtrs = ko.observableArray(shareModel.getPaymentTotalObjAtr(null));
            self.calcMethods = ko.observableArray(shareModel.getPaymentCaclMethodAtr(null));
            self.workingAtrs = ko.observableArray(shareModel.getWorkingAtr());
            self.paymentProportionalAtrs = ko.observableArray(shareModel.getPaymentProportionalAtr());
            self.proportionalMethodAtrs = ko.observableArray(shareModel.getProportionalMethodAtr());

            self.dataScreen = ko.observable(new Params());
            self.categoryAtrText = ko.observable(null);
            self.paymentItemSet = ko.observable(new PaymentItemSet());
            self.breakdownItemSets = ko.observableArray([]);

            // D10_1
            $("[data-toggle='userguide-register']").ntsUserGuide();
            // D10_2
            $("[data-toggle='userguide-exist']").ntsUserGuide();
            // D10_3
            $("[data-toggle='userguide-not-register']").ntsUserGuide();

            self.codeSelected.subscribe(value => {
                if (isNullOrUndefined(value)) return;
                block.invisible();
                let itemName: StatementItem = _.find(self.itemNames(), (item: IStatementItem) => {
                    return item.itemNameCd == value;
                });
                if (isNullOrUndefined(itemName)) {
                    block.clear();
                    return;
                };
                self.dataScreen().itemNameCode(itemName.itemNameCd);
                self.dataScreen().shortName(itemName.shortName);
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
            block.invisible();
            $("#fixed-table").ntsFixedTable({height: 139});
            let params: IParams = windows.getShared("QMM019_A_TO_D_PARAMS");
            if (isNullOrUndefined(params.detail)) {
                params.detail = <shareModel.IPaymentItemDetail>{};
            }
            if (isNullOrUndefined(params.itemRangeSet)) {
                params.itemRangeSet = <shareModel.IItemRangeSet> {};
            }
            self.params = params;
            self.yearMonth = params.yearMonth;
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

        initSubscribe() {
            let self = this;
            self.dataScreen().initSubscribe();
            self.dataScreen().calcMethod.subscribe(value => {
                self.condition12(self.paymentItemSet().taxAtr(), value);
                self.condition13(self.paymentItemSet().taxAtr(), value);
                self.condition14(value);
                self.condition15(value);
                self.condition16(value);
            });

            self.dataScreen().itemRangeSet.errorUpperLimitSetAtr.subscribe(value => {
                self.condition8(value);
            });

            self.dataScreen().itemRangeSet.errorLowerLimitSetAtr.subscribe(value => {
                self.condition9(value);
            });

            self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr.subscribe(value => {
                self.condition10(value);
            });

            self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr.subscribe(value => {
                self.condition11(value);
            });
        }

        initScreen() {
            let self = this;
            self.initSubscribe();
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
            // パラメータを受け取り取得した情報と合わせて画面上に表示する
            self.dataScreen().setData(self.params);
            self.codeSelected(item.itemNameCd);
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

                self.paymentItemSet().setData(pay);
                self.loadControlD2_9();
                self.assignItemRangeSet();

                self.breakdownItemSets(_.isEmpty(breakItems) ? [] : BreakdownItemSet.fromApp(breakItems));
                self.dataScreen().perValName(isNullOrUndefined(perVal) ? null : perVal.individualPriceName);
                self.dataScreen().formulaName(isNullOrUndefined(formula) ? null : formula.formulaName);
                self.dataScreen().wageTableName(isNullOrUndefined(wageTable) ? null : wageTable.wageTableName);
                dfd.resolve();
            });

            return dfd.promise();
        }

        assignItemRangeSet() {
            let self = this;
            if (self.params.itemNameCode == self.codeSelected()) {
                self.dataScreen().itemRangeSet.setData(self.params.itemRangeSet);
                return;
            }
            self.dataScreen().itemRangeSet.errorUpperLimitSetAtr(self.paymentItemSet().errorRangeSetting.upperLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.errorUpRangeValAmount(self.paymentItemSet().errorRangeSetting.upperLimitSetting.rangeValue());
            self.dataScreen().itemRangeSet.errorLowerLimitSetAtr(self.paymentItemSet().errorRangeSetting.lowerLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.errorLoRangeValAmount(self.paymentItemSet().errorRangeSetting.lowerLimitSetting.rangeValue());
            self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr(self.paymentItemSet().alarmRangeSetting.upperLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.alarmUpRangeValAmount(self.paymentItemSet().alarmRangeSetting.upperLimitSetting.rangeValue());
            self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr(self.paymentItemSet().alarmRangeSetting.lowerLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.alarmLoRangeValAmount(self.paymentItemSet().alarmRangeSetting.lowerLimitSetting.rangeValue());
        }

        loadControlD2_9() {
            let self = this;
            // ※補足資料8参照
            self.calcMethods(shareModel.getPaymentCaclMethodAtr(self.paymentItemSet().breakdownItemUseAtr()));
            if (self.paymentItemSet().breakdownItemUseAtr() == shareModel.BreakdownItemUseAtr.USE) {
                self.dataScreen().calcMethod(shareModel.PaymentCaclMethodAtr.BREAKDOWN_ITEM.toString());
            } else if (isNullOrUndefined(self.dataScreen().calcMethod())) {
                self.dataScreen().calcMethod(self.params.detail.calcMethod);
            }
        }

        unselectedMode() {
            let self = this;
            self.codeSelected(null);
            self.condition6(self.itemNames(), self.codeSelected());
            self.condition7(self.itemNames());
            self.screenControl().visibleD2_2(false);
            self.screenControl().visibleD2_3(false);
            self.screenControl().enableD2_5(false);
            self.screenControl().enableD2_8(false);
            self.screenControl().enableD2_10(false);
            self.screenControl().enableD3_2(false);
            self.dataScreen().proportionalAtr(shareModel.PaymentProportionalAtr.NOT_PROPORTIONAL.toString());
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
            nts.uk.ui.errors.clearAll();
            $("#btn-register").focus();
        }

        selectedMode(defaultAtr: shareModel.DefaultAtr) {
            let self = this;
            self.condition42(defaultAtr);
            if (defaultAtr != shareModel.DefaultAtr.SYSTEM_DEFAULT) {
                self.screenControl().visibleD2_2(true);
                self.screenControl().visibleD2_3(true);
                self.screenControl().enableD2_5(true);
                self.condition43(self.dataScreen().calcMethod(), self.paymentItemSet().taxAtr());
                self.screenControl().enableD2_10(true);
                self.screenControl().enableD3_2(true);
                self.screenControl().enableD3_6(true);
                self.screenControl().enableD3_9(true);
                self.screenControl().visibleD3_10(true);
                self.condition8(self.dataScreen().itemRangeSet.errorUpperLimitSetAtr());
                self.screenControl().visibleD3_11(true);
                self.screenControl().enableD3_12(true);
                self.screenControl().visibleD3_13(true);
                self.condition9(self.dataScreen().itemRangeSet.errorLowerLimitSetAtr());
                self.screenControl().visibleD3_14(true);
                self.screenControl().enableD3_16(true);
                self.screenControl().visibleD3_17(true);
                self.condition10(self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr());
                self.screenControl().visibleD3_18(true);
                self.screenControl().enableD3_19(true);
                self.screenControl().visibleD3_20(true);
                self.condition11(self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr());
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
                self.condition8(self.dataScreen().itemRangeSet.errorUpperLimitSetAtr());
                self.screenControl().visibleD3_11(true);
                self.screenControl().enableD3_12(true);
                self.screenControl().visibleD3_13(true);
                self.condition9(self.dataScreen().itemRangeSet.errorLowerLimitSetAtr());
                self.screenControl().visibleD3_14(true);
                self.screenControl().enableD3_16(true);
                self.screenControl().visibleD3_17(true);
                self.condition10(self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr());
                self.screenControl().visibleD3_18(true);
                self.screenControl().enableD3_19(true);
                self.screenControl().visibleD3_20(true);
                self.condition11(self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr());
                self.screenControl().visibleD3_21(true);
                self.screenControl().visibleD3_22(true);
                self.screenControl().visibleD3_23(true);
                self.screenControl().enableD4_1(true);
                self.screenControl().visibleD5(false);
                self.screenControl().visibleD6(false);
                self.screenControl().visibleD7(false);
                self.screenControl().visibleD8(false);
                self.screenControl().visibleD9(false);
                // D3_6
                self.dataScreen().proportionalMethod(null);
            }
            nts.uk.ui.errors.clearAll();
            $("#D1_6_container").focus();
        }

        /**
         * ※6
         */
        condition6(list: Array, code: string) {
            if (!_.isEmpty(list) && isNullOrEmpty(code)) {
                // D10_1
                $(".userguide-register").ntsUserGuide("show");
                // D10_2
                $(".userguide-exist").ntsUserGuide("show");
            }
        }

        /**
         * ※7
         */
        condition7(list: Array) {
            if (_.isEmpty(list)) {
                // D10_3
                $(".userguide-not-register").ntsUserGuide("show");
            }
        }

        /**
         * ※8
         */
        condition8(check: boolean) {
            let self = this;
            self.screenControl().enableD3_10(check);
        }

        /**
         * ※9
         */
        condition9(check: boolean) {
            let self = this;
            self.screenControl().enableD3_13(check);
        }

        /**
         * ※10
         */
        condition10(check: boolean) {
            let self = this;
            self.screenControl().enableD3_17(check);
        }

        /**
         * ※11
         */
        condition11(check: boolean) {
            let self = this;
            self.screenControl().enableD3_20(check);
        }

        /**
         * ※12
         */
        condition12(taxAtr: number, calcMethod: any) {
            let self = this;
            if (taxAtr == shareModel.TaxAtr.COMMUTING_EXPENSES_MANUAL && calcMethod != shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF) {
                self.screenControl().visibleD5(true);
            } else {
                self.screenControl().visibleD5(false);
            }
        }

        /**
         * ※13
         */
        condition13(taxAtr: number, calcMethod: any) {
            let self = this;
            if (taxAtr != shareModel.TaxAtr.COMMUTING_EXPENSES_MANUAL && calcMethod == shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF) {
                self.screenControl().visibleD6(true);
            } else {
                self.screenControl().visibleD6(false);
            }
        }

        /**
         * ※14
         */
        condition14(calcMethod: any) {
            let self = this;
            if (calcMethod == shareModel.PaymentCaclMethodAtr.CACL_FOMULA) {
                self.screenControl().visibleD7(true);
            } else {
                self.screenControl().visibleD7(false);
            }
        }

        /**
         * ※15
         */
        condition15(calcMethod: any) {
            let self = this;
            if (calcMethod == shareModel.PaymentCaclMethodAtr.WAGE_TABLE) {
                self.screenControl().visibleD8(true);
            } else {
                self.screenControl().visibleD8(false);
            }
        }

        /**
         * ※16
         */
        condition16(calcMethod: any) {
            let self = this;
            if (calcMethod == shareModel.PaymentCaclMethodAtr.COMMON_AMOUNT) {
                self.screenControl().visibleD9(true);
            } else {
                self.screenControl().visibleD9(false);
            }
        }

        /**
         * ※42：補足資料7を参照
         */
        condition42(defaultAtr: shareModel.DefaultAtr) {
            let self = this;
            if (defaultAtr == shareModel.DefaultAtr.SYSTEM_DEFAULT) {
                self.totalObjAtrs(shareModel.getPaymentTotalObjAtr(null));
            } else {
                self.totalObjAtrs(shareModel.getPaymentTotalObjAtr(self.params.printSet));
            }
        }

        /**
         * ※43
         */
        condition43(calcMethod: any, taxAtr: any) {
            let self = this;
            if (taxAtr == shareModel.TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER) {
                self.dataScreen().calcMethod(shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF.toString());
            }
            self.screenControl().enableD2_8(calcMethod != shareModel.PaymentCaclMethodAtr.BREAKDOWN_ITEM && taxAtr != shareModel.TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER);
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

        decide() {
            let self = this;
            // アルゴリズム「決定時チェック処理」を実施
            self.dataScreen().checkDecide(self.paymentItemSet().taxAtr());
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            windows.setShared("QMM019D_RESULTS", self.createResult());
            windows.close();
        }

        createResult() {
            let self = this;
            let detail: shareModel.IPaymentItemDetail = <shareModel.IPaymentItemDetail>{};
            detail.salaryItemId = self.dataScreen().itemNameCode();
            detail.totalObj = self.dataScreen().totalObject();
            detail.proportionalAtr = self.dataScreen().proportionalAtr();
            detail.proportionalMethod = self.dataScreen().proportionalMethod();
            detail.calcMethod = self.dataScreen().calcMethod();
            detail.calcFomulaCd = self.dataScreen().formulaCode();
            detail.personAmountCd = self.dataScreen().perValCode();
            detail.commonAmount = self.dataScreen().commonAmount();
            detail.wageTblCode = self.dataScreen().wageTableCode();
            detail.workingAtr = self.dataScreen().workingAtr();

            let itemRangeSet = <shareModel.IItemRangeSet>{};
            itemRangeSet.alarmLowerLimitSetAtr = self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr() ? share.model.UseRangeAtr.USE : share.model.UseRangeAtr.NOT_USE;
            if (self.dataScreen().itemRangeSet.errorUpperLimitSetAtr()) {
                itemRangeSet.errorUpperLimitSetAtr = share.model.UseRangeAtr.USE;
                itemRangeSet.errorUpRangeValAmount = self.dataScreen().itemRangeSet.errorUpRangeValAmount();
            } else {
                itemRangeSet.errorUpperLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
            }
            if (self.dataScreen().itemRangeSet.errorLowerLimitSetAtr()) {
                itemRangeSet.errorLowerLimitSetAtr = share.model.UseRangeAtr.USE;
                itemRangeSet.errorLoRangeValAmount = self.dataScreen().itemRangeSet.errorLoRangeValAmount();
            } else {
                itemRangeSet.errorLowerLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
            }
            if (self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr()) {
                itemRangeSet.alarmUpperLimitSetAtr = share.model.UseRangeAtr.USE;
                itemRangeSet.alarmUpRangeValAmount = self.dataScreen().itemRangeSet.alarmUpRangeValAmount();
            } else {
                itemRangeSet.alarmUpperLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
            }
            if (self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr()) {
                itemRangeSet.alarmLowerLimitSetAtr = share.model.UseRangeAtr.USE;
                itemRangeSet.alarmLoRangeValAmount = self.dataScreen().itemRangeSet.alarmLoRangeValAmount();
            } else {
                itemRangeSet.alarmLowerLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
            }

            let result = {
                itemNameCode: self.dataScreen().itemNameCode(),
                shortName: self.dataScreen().shortName(),
                detail: detail,
                itemRangeSet: itemRangeSet
            };
            return result;
        }

        cancel() {
            windows.setShared("QMM019D_RESULTS", null);
            windows.close();
        }

        formatNumber(number) {
            return formatNumber(number, new nts.uk.ui.option.NumberEditorOption({grouplength: 3}))
        }
    }

    class ScreenControl {
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
        printSet: number;
        yearMonth: number;
        itemNameCode: string;
        listItemSetting: Array<string>;
        detail: shareModel.IPaymentItemDetail;
        itemRangeSet: shareModel.IItemRangeSet;
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

        itemRangeSet: ItemRangeSet = new ItemRangeSet();

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
        /**
         * 共通金額
         */
        commonAmount: KnockoutObservable<number> = ko.observable(null);

        constructor() {
        }

        setData(data: IParams) {
            let self = this;
            self.itemNameCode(data.itemNameCode);
            self.workingAtr(isNullOrUndefined(data.detail.workingAtr) ? null : data.detail.workingAtr.toString());
            self.totalObject(isNullOrUndefined(data.detail.totalObj) ? null : data.detail.totalObj.toString());
            self.calcMethod(isNullOrUndefined(data.detail.calcMethod) ? null : data.detail.calcMethod.toString());
            self.proportionalAtr(isNullOrUndefined(data.detail.proportionalAtr) ? null : data.detail.proportionalAtr.toString());
            self.proportionalMethod(isNullOrUndefined(data.detail.proportionalMethod) ? null : data.detail.proportionalMethod.toString());
            self.itemRangeSet.setData(data.itemRangeSet);
            self.perValCode(data.detail.personAmountCd);
            self.formulaCode(data.detail.calcFomulaCd);
            self.wageTableCode(data.detail.wageTblCode);
            self.commonAmount(data.detail.commonAmount);
        }

        initSubscribe() {
            let self = this;
            self.calcMethod.subscribe((value) => {
                if (isNullOrUndefined(value)) return;
                if (value != shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF) {
                    self.perValCode(null);
                    self.perValName(null);
                    self.clearError("#D6_2");
                }
                if (value != shareModel.PaymentCaclMethodAtr.CACL_FOMULA) {
                    self.formulaCode(null);
                    self.formulaName(null);
                    self.clearError("#D7_2");
                }
                if (value != shareModel.PaymentCaclMethodAtr.WAGE_TABLE) {
                    self.wageTableCode(null);
                    self.wageTableName(null);
                    self.clearError("#D8_2");
                }
                if (value != shareModel.PaymentCaclMethodAtr.COMMON_AMOUNT) {
                    self.commonAmount(null);
                    self.clearError("#D9_2");
                }
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

            self.itemRangeSet.errorUpperLimitSetAtr.subscribe(() => {
                self.itemRangeSet.errorUpRangeValAmount(null);
                self.clearError("#D3_10");
            });
            self.itemRangeSet.errorLowerLimitSetAtr.subscribe(() => {
                self.itemRangeSet.errorLoRangeValAmount(null);
                self.clearError("#D3_13");
            });
            self.itemRangeSet.errorUpperLimitSetAtr.subscribe(() => {
                self.clearError("#D3_13");
                self.checkError("#D3_13");
            });
            self.itemRangeSet.errorUpRangeValAmount.subscribe(() => {
                self.clearError("#D3_13");
                self.checkError("#D3_13");
            });

            self.itemRangeSet.alarmUpperLimitSetAtr.subscribe(() => {
                self.itemRangeSet.alarmUpRangeValAmount(null);
                self.clearError("#D3_17");
            });
            self.itemRangeSet.alarmLowerLimitSetAtr.subscribe(() => {
                self.itemRangeSet.alarmLoRangeValAmount(null);
                self.clearError("#D3_20");
            });
            self.itemRangeSet.alarmLowerLimitSetAtr.subscribe(() => {
                self.clearError("#D3_20");
                self.checkError("#D3_20");
            });
            self.itemRangeSet.alarmUpRangeValAmount.subscribe(() => {
                self.clearError("#D3_20");
                self.checkError("#D3_20");
            });
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
            self.checkError("#D2_5");
            self.checkError("#D2_8");
            self.checkError("#D9_2");
        }

        checkCalcMethod(taxAtr: number) {
            let self = this;
            // ※43
            // ※補足資料9を参照
            if (taxAtr == shareModel.TaxAtr.COMMUTING_EXPENSES_MANUAL
                && self.calcMethod() == shareModel.PaymentCaclMethodAtr.PERSON_INFO_REF.toString()) {
                // alertError({messageId: "MsgQ_181"});
                self.setError("#D2_8", "MsgQ_181");
            }
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
            if (self.itemRangeSet.errorUpperLimitSetAtr()) {
                // エラー範囲上限値の入力を確認する
                if (isNullOrEmpty(self.itemRangeSet.errorUpRangeValAmount())) {
                    self.setError("#D3_10", "MsgQ_14");
                } else {
                    // エラー範囲下限値チェック状況を確認する
                    if (self.itemRangeSet.errorLowerLimitSetAtr()) {
                        // エラー範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.itemRangeSet.errorLoRangeValAmount())) {
                            self.setError("#D3_13", "MsgQ_15");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.itemRangeSet.errorLoRangeValAmount()) > parseInt(self.itemRangeSet.errorUpRangeValAmount())) {
                                self.setError("#D3_13", "MsgQ_1");
                            }
                        }
                    }
                }
            } else {
                // エラー範囲下限値チェック状況を確認する
                if (self.itemRangeSet.errorLowerLimitSetAtr()) {
                    // エラー範囲下限値の入力を確認する
                    if (isNullOrEmpty(self.itemRangeSet.errorLoRangeValAmount())) {
                        self.setError("#D3_13", "MsgQ_15");
                    }
                }
            }
        }

        checkAlarmRange() {
            let self = this;
            // アラーム範囲上限値チェック状況を確認する
            if (self.itemRangeSet.alarmUpperLimitSetAtr()) {
                // アラーム範囲上限値の入力を確認する
                if (isNullOrEmpty(self.itemRangeSet.alarmUpRangeValAmount())) {
                    self.setError("#D3_17", "MsgQ_16");
                } else {
                    // アラーム範囲下限値チェック状況を確認する
                    if (self.itemRangeSet.alarmLowerLimitSetAtr()) {
                        // アラーム範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.itemRangeSet.alarmLoRangeValAmount())) {
                            self.setError("#D3_20", "MsgQ_17");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.itemRangeSet.alarmLoRangeValAmount()) > parseInt(self.itemRangeSet.alarmUpRangeValAmount())) {
                                self.setError("#D3_20", "MsgQ_2");
                            }
                        }
                    }
                }
            } else {
                // アラーム範囲下限値チェック状況を確認する
                if (self.itemRangeSet.alarmLowerLimitSetAtr()) {
                    // アラーム範囲下限値の入力を確認する
                    if (isNullOrEmpty(self.itemRangeSet.alarmLoRangeValAmount())) {
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

    class ItemRangeSet {
        errorUpperLimitSetAtr: KnockoutObservable<boolean> = ko.observable(null);
        errorUpRangeValAmount: KnockoutObservable<string> = ko.observable(null);
        errorLowerLimitSetAtr: KnockoutObservable<boolean> = ko.observable(null);
        errorLoRangeValAmount: KnockoutObservable<string> = ko.observable(null);
        alarmUpperLimitSetAtr: KnockoutObservable<boolean> = ko.observable(null);
        alarmUpRangeValAmount: KnockoutObservable<string> = ko.observable(null);
        alarmLowerLimitSetAtr: KnockoutObservable<boolean> = ko.observable(null);
        alarmLoRangeValAmount: KnockoutObservable<string> = ko.observable(null);

        constructor() {
        }

        setData(data: shareModel.IItemRangeSet) {
            this.errorUpperLimitSetAtr(data.errorUpperLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.errorUpRangeValAmount(isNullOrUndefined(data.errorUpRangeValAmount) ? null : data.errorUpRangeValAmount);

            this.errorLowerLimitSetAtr(data.errorLowerLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.errorLoRangeValAmount(isNullOrUndefined(data.errorLoRangeValAmount) ? null : data.errorLoRangeValAmount);

            this.alarmUpperLimitSetAtr(data.alarmUpperLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.alarmUpRangeValAmount(isNullOrUndefined(data.alarmUpRangeValAmount) ? null : data.alarmUpRangeValAmount);

            this.alarmLowerLimitSetAtr(data.alarmLowerLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.alarmLoRangeValAmount(isNullOrUndefined(data.alarmLoRangeValAmount) ? null : data.alarmLoRangeValAmount);
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
            if (isNullOrUndefined(data)) {
                this.upperLimitSetting.setData(null);
                this.lowerLimitSetting.setData(null);
            } else {
                this.upperLimitSetting.setData(data.upperLimitSetting);
                this.lowerLimitSetting.setData(data.lowerLimitSetting);
            }
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
            if (isNullOrUndefined(data)) {
                this.valueSettingAtr(false);
                this.rangeValue(null);
            } else {
                this.valueSettingAtr(data.valueSettingAtr == shareModel.UseRangeAtr.USE);
                this.rangeValue(isNullOrUndefined(data.rangeValue) ? null : data.rangeValue.toString());
            }
        }
    }

    interface IPaymentItemSet {
        taxAtr: number;
        limitAmount: number;
        socialInsuranceCategory: number;
        laborInsuranceCategory: number;
        everyoneEqualSet: number;
        averageWageAtr: number;
        breakdownItemUseAtr: number;
        errorRangeSetting: IErrorAlarmRangeSetting;
        alarmRangeSetting: IErrorAlarmRangeSetting;
    }

    class PaymentItemSet {
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
        /**
         * 内訳項目利用区分
         */
        breakdownItemUseAtr: KnockoutObservable<number> = ko.observable(null);
        errorRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();
        alarmRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();

        constructor() {
        }

        setData(data: IPaymentItemSet) {
            if (isNullOrUndefined(data)) {
                this.taxAtr(null);
                this.taxAtrText(null);
                this.limitAmount(null);
                this.socialInsuranceCategory(null);
                this.socialInsuranceCategoryText(null);
                this.laborInsuranceCategory(null);
                this.laborInsuranceCategoryText(null);
                this.everyoneEqualSet(null);
                this.everyoneEqualSetText(null);
                this.averageWageAtr(null);
                this.averageWageAtrText(null);
                this.breakdownItemUseAtr(null);
                this.errorRangeSetting.setData(null);
                this.alarmRangeSetting.setData(null);
            } else {
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
                this.breakdownItemUseAtr(data.breakdownItemUseAtr);
                this.errorRangeSetting.setData(data.errorRangeSetting);
                this.alarmRangeSetting.setData(data.alarmRangeSetting);
            }
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
            return _.sortBy(result, ['breakdownItemCode']);
        }
    }
}