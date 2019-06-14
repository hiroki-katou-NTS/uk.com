module nts.uk.pr.view.qmm019.e.viewmodel {
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

        selectedSearchIitemName: KnockoutObservable<any>;
        itemNames: KnockoutObservableArray<StatementItem>;
        codeSelected: KnockoutObservable<any>;

        categoryAtr: number;
        totalObjAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        calcMethods: KnockoutObservableArray<shareModel.ItemModel>;
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
            self.totalObjAtrs = ko.observableArray(shareModel.getDeductionTotalObjAtr(null));
            self.calcMethods = ko.observableArray(shareModel.getDeductionCaclMethodAtr(null));
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
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            $("#fixed-table").ntsFixedTable({height: 139});
            let params: IParams = windows.getShared("QMM019_A_TO_E_PARAMS");
            if (isNullOrUndefined(params.detail)) {
                params.detail = <shareModel.IDeductionItemDetail>{};
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
                self.condition22(value);
                self.condition23(value);
                self.condition24(value);
                self.condition25(value);
                self.condition26(value);
            });

            self.dataScreen().itemRangeSet.errorUpperLimitSetAtr.subscribe(value => {
                self.condition18(value);
            });

            self.dataScreen().itemRangeSet.errorLowerLimitSetAtr.subscribe(value => {
                self.condition19(value);
            });

            self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr.subscribe(value => {
                self.condition20(value);
            });

            self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr.subscribe(value => {
                self.condition21(value);
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
            let sv1 = service.getDeductionItemStById(self.categoryAtr, self.dataScreen().itemNameCode());
            // ドメインモデル「内訳項目設定」を取得する
            let sv2 = service.getAllBreakdownItemSetById(self.categoryAtr, self.dataScreen().itemNameCode());
            // ドメインモデル「給与個人別金額名称」を取得する
            let sv3 = service.getSalIndAmountNameById(self.dataScreen().perValCode(), self.categoryAtr);
            // ドメインモデル「計算式」を取得する
            let sv4 = service.getFormulaById(self.dataScreen().formulaCode());
            // ドメインモデル「賃金テーブル」を取得する
            let sv5 = service.getWageTableById(self.dataScreen().wageTableCode());
            let sv6 = service.getStatementItemName(shareModel.CategoryAtr.PAYMENT_ITEM, self.dataScreen().statementItemCode())
            $.when(sv1, sv2, sv3, sv4, sv5, sv6).done((dedu: IDeductionItemSet,
                                                       breakItems: Array<IBreakdownItemSet>,
                                                       perVal: any,
                                                       formula: any,
                                                       wageTable: any,
                                                       statementItemName: any) => {
                self.categoryAtrText(shareModel.getCategoryAtrText(self.categoryAtr));

                self.deductionItemSet().setData(dedu);
                self.loadControlE2_9();
                self.assignItemRangeSet();

                self.breakdownItemSets(_.isEmpty(breakItems) ? [] : BreakdownItemSet.fromApp(breakItems));
                self.dataScreen().perValName(isNullOrUndefined(perVal) ? null : perVal.individualPriceName);
                self.dataScreen().formulaName(isNullOrUndefined(formula) ? null : formula.formulaName);
                self.dataScreen().wageTableName(isNullOrUndefined(wageTable) ? null : wageTable.wageTableName);
                self.dataScreen().statementItemName(isNullOrUndefined(statementItemName) ? null : statementItemName.name);
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
            self.dataScreen().itemRangeSet.errorUpperLimitSetAtr(self.deductionItemSet().errorRangeSetting.upperLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.errorUpRangeValAmount(self.deductionItemSet().errorRangeSetting.upperLimitSetting.rangeValue());
            self.dataScreen().itemRangeSet.errorLowerLimitSetAtr(self.deductionItemSet().errorRangeSetting.lowerLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.errorLoRangeValAmount(self.deductionItemSet().errorRangeSetting.lowerLimitSetting.rangeValue());
            self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr(self.deductionItemSet().alarmRangeSetting.upperLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.alarmUpRangeValAmount(self.deductionItemSet().alarmRangeSetting.upperLimitSetting.rangeValue());
            self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr(self.deductionItemSet().alarmRangeSetting.lowerLimitSetting.valueSettingAtr());
            self.dataScreen().itemRangeSet.alarmLoRangeValAmount(self.deductionItemSet().alarmRangeSetting.lowerLimitSetting.rangeValue());
        }

        loadControlE2_9() {
            let self = this;
            // ※補足資料8参照
            self.calcMethods(shareModel.getDeductionCaclMethodAtr(self.deductionItemSet().breakdownItemUseAtr()));
            if (self.deductionItemSet().breakdownItemUseAtr() == shareModel.BreakdownItemUseAtr.USE) {
                self.dataScreen().calcMethod(shareModel.DeductionCaclMethodAtr.BREAKDOWN_ITEM.toString());
            } else if (isNullOrUndefined(self.dataScreen().calcMethod())) {
                self.dataScreen().calcMethod(self.params.detail.calcMethod);
            }
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
            self.dataScreen().proportionalAtr(shareModel.DeductionProportionalAtr.NOT_PROPORTIONAL.toString());
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
            self.condition42(defaultAtr);
            if (defaultAtr != shareModel.DefaultAtr.SYSTEM_DEFAULT) {
                self.screenControl().visibleE2_2(true);
                self.screenControl().visibleE2_3(true);
                self.screenControl().enableE2_5(true);
                self.condition44(self.dataScreen().calcMethod());
                self.screenControl().enableE2_10(true);
                self.screenControl().enableE3_2(true);
                self.screenControl().enableE3_6(true);
                self.screenControl().enableE3_9(true);
                self.screenControl().visibleE3_10(true);
                self.condition18(self.dataScreen().itemRangeSet.errorUpperLimitSetAtr());
                self.screenControl().visibleE3_11(true);
                self.screenControl().enableE3_12(true);
                self.screenControl().visibleE3_13(true);
                self.condition19(self.dataScreen().itemRangeSet.errorLowerLimitSetAtr());
                self.screenControl().visibleE3_14(true);
                self.screenControl().enableE3_16(true);
                self.screenControl().visibleE3_17(true);
                self.condition20(self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr());
                self.screenControl().visibleE3_18(true);
                self.screenControl().enableE3_19(true);
                self.screenControl().visibleE3_20(true);
                self.condition21(self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr());
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
                self.screenControl().enableE2_5(true);
                self.screenControl().enableE2_8(false);
                self.screenControl().enableE2_10(true);
                self.screenControl().enableE3_2(false);
                self.screenControl().enableE3_6(false);
                self.screenControl().enableE3_9(true);
                self.screenControl().visibleE3_10(true);
                self.condition18(self.dataScreen().itemRangeSet.errorUpperLimitSetAtr());
                self.screenControl().visibleE3_11(true);
                self.screenControl().enableE3_12(true);
                self.screenControl().visibleE3_13(true);
                self.condition19(self.dataScreen().itemRangeSet.errorLowerLimitSetAtr());
                self.screenControl().visibleE3_14(true);
                self.screenControl().enableE3_16(true);
                self.screenControl().visibleE3_17(true);
                self.condition20(self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr());
                self.screenControl().visibleE3_18(true);
                self.screenControl().enableE3_19(true);
                self.screenControl().visibleE3_20(true);
                self.condition21(self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr());
                self.screenControl().visibleE3_21(true);
                self.screenControl().visibleE3_22(true);
                self.screenControl().visibleE3_23(true);
                self.screenControl().enableE4_1(true);
                self.screenControl().visibleE5(false);
                self.screenControl().visibleE6(false);
                self.screenControl().visibleE7(false);
                self.screenControl().visibleE8(false);
                self.screenControl().visibleE9(false);
                // E3_6
                self.dataScreen().proportionalMethod(null);
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
        condition42(defaultAtr: shareModel.DefaultAtr) {
            let self = this;
            if (defaultAtr == shareModel.DefaultAtr.SYSTEM_DEFAULT) {
                self.totalObjAtrs(shareModel.getDeductionTotalObjAtr(null));
            } else {
                self.totalObjAtrs(shareModel.getDeductionTotalObjAtr(self.params.printSet));
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
            self.dataScreen().checkDecide();
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            windows.setShared("QMM019E_RESULTS", self.createResult());
            windows.close();
        }

        createResult() {
            let self = this;
            let detail: shareModel.IDeductionItemDetail = <shareModel.IDeductionItemDetail>{};
            detail.salaryItemId = self.dataScreen().itemNameCode();
            detail.totalObj = self.dataScreen().totalObject();
            detail.proportionalAtr = self.dataScreen().proportionalAtr();
            detail.proportionalMethod = self.dataScreen().proportionalMethod();
            detail.calcMethod = self.dataScreen().calcMethod();
            detail.calcFormulaCd = self.dataScreen().formulaCode();
            detail.personAmountCd = self.dataScreen().perValCode();
            detail.commonAmount = self.dataScreen().commonAmount();
            detail.wageTblCd = self.dataScreen().wageTableCode();
            detail.supplyOffset = self.dataScreen().statementItemCode();

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
            windows.close();
        }

        formatNumber(number) {
            return formatNumber(number, new nts.uk.ui.option.NumberEditorOption({grouplength: 3}))
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
        detail: shareModel.IDeductionItemDetail;
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
        statementItemCode: KnockoutObservable<string> = ko.observable(null);
        statementItemName: KnockoutObservable<string> = ko.observable(null);
        /**
         * 共通金額
         */
        commonAmount: KnockoutObservable<number> = ko.observable(null);

        constructor() {
        }

        setData(data: IParams) {
            let self = this;
            self.itemNameCode(data.itemNameCode);
            self.totalObject(isNullOrUndefined(data.detail.totalObj) ? null : data.detail.totalObj.toString());
            self.calcMethod(isNullOrUndefined(data.detail.calcMethod) ? null : data.detail.calcMethod.toString());
            self.proportionalAtr(isNullOrUndefined(data.detail.proportionalAtr) ? null : data.detail.proportionalAtr.toString());
            self.proportionalMethod(isNullOrUndefined(data.detail.proportionalMethod) ? null : data.detail.proportionalMethod.toString());
            self.itemRangeSet.setData(data.itemRangeSet);
            self.perValCode(data.detail.personAmountCd);
            self.formulaCode(data.detail.calcFormulaCd);
            self.wageTableCode(data.detail.wageTblCd);
            self.statementItemCode(data.detail.supplyOffset);
            self.commonAmount(data.detail.commonAmount);
        }

        initSubscribe() {
            let self = this;
            self.calcMethod.subscribe((value) => {
                if (isNullOrUndefined(value)) return;
                if (value != shareModel.DeductionCaclMethodAtr.PERSON_INFO_REF) {
                    self.perValCode(null);
                    self.perValName(null);
                    self.clearError("#E5_2");
                }
                if (value != shareModel.DeductionCaclMethodAtr.CACL_FOMULA) {
                    self.formulaCode(null);
                    self.formulaName(null);
                    self.clearError("#E6_2");
                }
                if (value != shareModel.DeductionCaclMethodAtr.WAGE_TABLE) {
                    self.wageTableCode(null);
                    self.wageTableName(null);
                    self.clearError("#E7_2");
                }
                if (value != shareModel.DeductionCaclMethodAtr.COMMON_AMOUNT) {
                    self.commonAmount(null);
                    self.clearError("#E8_2");
                }
                if (value != shareModel.DeductionCaclMethodAtr.SUPPLY_OFFSET) {
                    self.statementItemCode(null);
                    self.statementItemName(null);
                    self.clearError("#E9_2");
                }
            });
            self.perValCode.subscribe(() => {
                self.clearError("#E5_2");
            });
            self.formulaCode.subscribe(() => {
                self.clearError("#E6_2");
            });
            self.wageTableCode.subscribe(() => {
                self.clearError("#E7_2");
            });
            self.statementItemCode.subscribe(() => {
                self.clearError("#E9_2");
            });

            self.itemRangeSet.errorUpperLimitSetAtr.subscribe(() => {
                self.itemRangeSet.errorUpRangeValAmount(null);
                self.clearError("#E3_10");
            });
            self.itemRangeSet.errorLowerLimitSetAtr.subscribe(() => {
                self.itemRangeSet.errorLoRangeValAmount(null);
                self.clearError("#E3_13");
            });
            self.itemRangeSet.errorUpperLimitSetAtr.subscribe(() => {
                self.clearError("#E3_13");
                self.checkError("#E3_13");
            });
            self.itemRangeSet.errorUpRangeValAmount.subscribe(() => {
                self.clearError("#E3_13");
                self.checkError("#E3_13");
            });

            self.itemRangeSet.alarmUpperLimitSetAtr.subscribe(() => {
                self.itemRangeSet.alarmUpRangeValAmount(null);
                self.clearError("#E3_17");
            });
            self.itemRangeSet.alarmLowerLimitSetAtr.subscribe(() => {
                self.itemRangeSet.alarmLoRangeValAmount(null);
                self.clearError("#E3_20");
            });
            self.itemRangeSet.alarmUpperLimitSetAtr.subscribe(() => {
                self.clearError("#E3_20");
                self.checkError("#E3_20");
            });
            self.itemRangeSet.alarmUpRangeValAmount.subscribe(() => {
                self.clearError("#E3_20");
                self.checkError("#E3_20");
            });
        }

        /**
         * 決定時チェック処理
         */
        checkDecide() {
            let self = this;
            self.validate();
            self.checkCalcMethod();
            self.checkErrorRange();
            self.checkAlarmRange();
        }

        validate() {
            let self = this;
            self.checkError("#E2_5");
            self.checkError("#E2_8");
            self.checkError("#E8_2");
        }

        checkCalcMethod() {
            let self = this;
            // 設定された計算方法を確認する
            switch (parseInt(self.calcMethod())) {
                case shareModel.DeductionCaclMethodAtr.PERSON_INFO_REF:
                    // 個人金額コードが設定されているか確認する
                    if (isNullOrEmpty(self.perValCode())) {
                        self.setError("#E5_2", "MsgQ_11");
                    }
                    break;
                case shareModel.DeductionCaclMethodAtr.CACL_FOMULA:
                    // 計算式コードが設定されているか確認する
                    if (isNullOrEmpty(self.formulaCode())) {
                        self.setError("#E6_2", "MsgQ_12");
                    }
                    break;
                case shareModel.DeductionCaclMethodAtr.WAGE_TABLE:
                    // 賃金テーブルコードが設定されているか確認する
                    if (isNullOrEmpty(self.wageTableCode())) {
                        self.setError("#E7_2", "MsgQ_13");
                    }
                    break;
                case shareModel.DeductionCaclMethodAtr.SUPPLY_OFFSET:
                    // 相殺対象項目コードが設定されているか確認する
                    if (isNullOrEmpty(self.statementItemCode())) {
                        self.setError("#E9_2", "MsgQ_32");
                    }
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
                    self.setError("#E3_10", "MsgQ_14");
                } else {
                    // エラー範囲下限値チェック状況を確認する
                    if (self.itemRangeSet.errorLowerLimitSetAtr()) {
                        // エラー範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.itemRangeSet.errorLoRangeValAmount())) {
                            self.setError("#E3_13", "MsgQ_15");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.itemRangeSet.errorLoRangeValAmount()) > parseInt(self.itemRangeSet.errorUpRangeValAmount())) {
                                self.setError("#E3_13", "MsgQ_1");
                            }
                        }
                    }
                }
            } else {
                // エラー範囲下限値チェック状況を確認する
                if (self.itemRangeSet.errorLowerLimitSetAtr()) {
                    // エラー範囲下限値の入力を確認する
                    if (isNullOrEmpty(self.itemRangeSet.errorLoRangeValAmount())) {
                        self.setError("#E3_13", "MsgQ_15");
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
                    // alertError({messageId: "MsgQ_16"});
                    self.setError("#E3_17", "MsgQ_16");
                } else {
                    // アラーム範囲下限値チェック状況を確認する
                    if (self.itemRangeSet.alarmLowerLimitSetAtr()) {
                        // アラーム範囲下限値の入力を確認する
                        if (isNullOrEmpty(self.itemRangeSet.alarmLoRangeValAmount())) {
                            // alertError({messageId: "MsgQ_17"});
                            self.setError("#E3_20", "MsgQ_17");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(self.itemRangeSet.alarmLoRangeValAmount()) > parseInt(self.itemRangeSet.alarmUpRangeValAmount())) {
                                // alertError({messageId: "MsgQ_2"});
                                self.setError("#E3_20", "MsgQ_2");
                            }
                        }
                    }
                }
            } else {
                // アラーム範囲下限値チェック状況を確認する
                if (self.itemRangeSet.alarmLowerLimitSetAtr()) {
                    // アラーム範囲下限値の入力を確認する
                    if (isNullOrEmpty(self.itemRangeSet.alarmLoRangeValAmount())) {
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

    interface IDeductionItemSet {
        deductionItemAtr: number;
        breakdownItemUseAtr: number;
        errorRangeSetting: IErrorAlarmRangeSetting;
        alarmRangeSetting: IErrorAlarmRangeSetting;
    }

    class DeductionItemSet {
        /**
         * 控除項目区分
         */
        deductionItemAtr: KnockoutObservable<number> = ko.observable(null);
        deductionItemAtrText: KnockoutObservable<string> = ko.observable(null);
        /**
         * 内訳項目利用区分
         */
        breakdownItemUseAtr: KnockoutObservable<number> = ko.observable(null);

        errorRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();
        alarmRangeSetting: ErrorAlarmRangeSetting = new ErrorAlarmRangeSetting();

        constructor() {
        }

        setData(data: IDeductionItemSet) {
            if (isNullOrUndefined(data)){
                this.deductionItemAtr(null);
                this.deductionItemAtrText(null);
                this.breakdownItemUseAtr(null);
                this.errorRangeSetting.setData(null);
                this.alarmRangeSetting.setData(null);
            } else{
                this.deductionItemAtr(data.deductionItemAtr);
                this.deductionItemAtrText(shareModel.getDeductionItemAtrText(data.deductionItemAtr));
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