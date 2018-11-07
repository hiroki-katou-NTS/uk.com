module nts.uk.pr.view.qmm019.d.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import modal = nts.uk.ui.windows.sub.modal;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;

    export class ScreenModel {
        screenMode: KnockoutObservable<number>;
        unselectMode: number = 0;
        selectMode: number = 1

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

            self.categoryAtr = shareModel.CategoryAtr.PAYMENT_ITEM;
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
                block.invisible();
                let itemName: StatementItem = _.find(self.itemNames(), (item: IStatementItem) => {
                    return item.itemNameCd == value;
                })
                self.dataScreen().itemNameCode(itemName.itemNameCd);
                self.dataScreen().name(itemName.name);
                self.getDataAccordion().done(() => {
                    block.clear();
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
            let params: IParams = <IParams>{};
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
            // パラメータを受け取り取得した情報と合わせて画面上に表示する
            self.dataScreen(new Params(params));
            let item: IStatementItem = _.head(self.itemNames());
            self.codeSelected(item.itemNameCd);
            // TODO
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
            let sv5 = service.getWageTableById(self.dataScreen().wageTableCode())
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
            })

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
                return;
            }
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

            self.calcMethod.subscribe(() => {
                self.clearError("#D6-2");
                self.clearError("#D7-2");
                self.clearError("#D8-2");
            });
            self.perValCode.subscribe(() => {
                self.clearError("#D6-2");
            });
            self.formulaCode.subscribe(() => {
                self.clearError("#D7-2");
            });
            self.wageTableCode.subscribe(() => {
                self.clearError("#D8-2");
            });


            self.errorRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.clearError("#D3_10");
            });
            self.errorRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
                self.clearError("#D3_13");
            });
            self.errorRangeSetting.upperLimitSetting.rangeValue.subscribe(() => {
                self.clearError("#D3_13");
                self.checkError("#D3_13");
            });

            self.alarmRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.clearError("#D3_17");
            });
            self.alarmRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
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
            self.validate();
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
                        self.setError("#D6-2", "MsgQ_11");
                    }
                    break;
                case shareModel.PaymentCaclMethodAtr.CACL_FOMULA:
                    // 計算式コードが設定されているか確認する
                    if (isNullOrEmpty(self.formulaCode())) {
                        // alertError({messageId: "MsgQ_12"});
                        self.setError("#D7-2", "MsgQ_11");
                    }
                    break;
                case shareModel.PaymentCaclMethodAtr.WAGE_TABLE:
                    // 賃金テーブルコードが設定されているか確認する
                    if (isNullOrEmpty(self.wageTableCode())) {
                        // alertError({messageId: "MsgQ_13"});
                        self.setError("#D8-2", "MsgQ_11");
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