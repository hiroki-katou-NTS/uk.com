module nts.uk.pr.view.qmm019.f.viewmodel {
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

        categoryAtr: number;

        params: IParams;
        dataType: number;
        dataScreen: KnockoutObservable<Params>;
        categoryAtrText: KnockoutObservable<string>;
        attendanceItemSet: KnockoutObservable<AttendanceItemSet>;

        constructor() {
            let self = this;
            self.screenControl = ko.observable(new ScreenControl());

            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);

            self.categoryAtr = shareModel.CategoryAtr.ATTEND_ITEM;

            self.dataType = DataType.TIME60;
            self.dataScreen = ko.observable(new Params());
            self.categoryAtrText = ko.observable(null);
            self.attendanceItemSet = ko.observable(new AttendanceItemSet());

            // F10_1
            $("[data-toggle='userguide-register']").ntsUserGuide();
            // F10_2
            $("[data-toggle='userguide-exist']").ntsUserGuide();
            // F10_3
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
                    self.selectedMode();
                    block.clear();
                })
            })

            self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition28(value);
            });

            self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition29(value);
            });

            self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition30(value);
            });

            self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(value => {
                self.condition31(value);
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            let params: IParams = <IParams>{};
            params.itemNameCode = "0003d";
            params.itemNameCdExcludeList = [];
            params.rangeValAttribute = null;
            params.errorRangeSetting = <IErrorAlarmRangeSetting>{};
            params.errorRangeSetting.upperLimitSetting = <IErrorAlarmValueSetting>{};
            params.errorRangeSetting.upperLimitSetting.valueSettingAtr = 1;
            params.errorRangeSetting.upperLimitSetting.time10Value = 1;
            params.errorRangeSetting.upperLimitSetting.time60Value = 2;
            params.errorRangeSetting.upperLimitSetting.timesValue = 3;
            params.errorRangeSetting.lowerLimitSetting = <IErrorAlarmValueSetting>{};
            params.errorRangeSetting.lowerLimitSetting.valueSettingAtr = 1;
            params.errorRangeSetting.lowerLimitSetting.time10Value = 4;
            params.errorRangeSetting.lowerLimitSetting.time60Value = 5;
            params.errorRangeSetting.lowerLimitSetting.timesValue = 6;
            params.alarmRangeSetting = <IErrorAlarmRangeSetting>{};
            params.alarmRangeSetting.upperLimitSetting = <IErrorAlarmValueSetting>{};
            params.alarmRangeSetting.upperLimitSetting.valueSettingAtr = 1;
            params.alarmRangeSetting.upperLimitSetting.time10Value = 7;
            params.alarmRangeSetting.upperLimitSetting.time60Value = 8;
            params.alarmRangeSetting.upperLimitSetting.timesValue = 9;
            params.alarmRangeSetting.lowerLimitSetting = <IErrorAlarmValueSetting>{};
            params.alarmRangeSetting.lowerLimitSetting.valueSettingAtr = 1;
            params.alarmRangeSetting.lowerLimitSetting.time10Value = 10;
            params.alarmRangeSetting.lowerLimitSetting.time60Value = 11;
            params.alarmRangeSetting.lowerLimitSetting.timesValue = 12;
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
            self.dataScreen().initSubscribe();
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
            let sv1 = service.getAttendanceItemStById(self.categoryAtr, self.dataScreen().itemNameCode());
            $.when(sv1).done((att: IAttendanceItemSet) => {
                self.categoryAtrText(shareModel.getCategoryAtrText(self.categoryAtr));
                if (!isNullOrUndefined(att)) {
                    self.attendanceItemSet().setData(att);
                }
                dfd.resolve();
            });

            return dfd.promise();
        }

        unselectedMode() {
            let self = this;
            self.codeSelected(null);
            self.condition6(self.itemNames(), self.codeSelected());
            self.condition7(self.itemNames());
            self.screenControl().visibleF2_2(false);
            self.screenControl().visibleF2_3(false);
            self.screenControl().enableF2_4(false);
            self.screenControl().enableF3_2(false);
            self.screenControl().visibleF3_3(true);
            self.screenControl().enableF3_3(false);
            self.screenControl().enableF3_5(false);
            self.screenControl().visibleF3_6(true);
            self.screenControl().enableF3_6(false);
            self.screenControl().enableF3_9(false);
            self.screenControl().visibleF3_10(true);
            self.screenControl().enableF3_10(false);
            self.screenControl().enableF3_12(false);
            self.screenControl().enableF3_13(false);
            self.screenControl().visibleF3_13(true);
            self.screenControl().visibleF3_15(false);
            self.screenControl().visibleF3_16(false);
            self.screenControl().visibleF3_17(false);
            self.screenControl().visibleF3_18(false);
            self.screenControl().visibleF3_19(false);
            self.screenControl().visibleF3_20(false);
            self.screenControl().enableF3_20(false);
            self.screenControl().visibleF3_21(false);
            self.screenControl().enableF3_21(false);
            self.screenControl().visibleF3_22(false);
            self.screenControl().enableF3_22(false);
            self.screenControl().visibleF3_23(false);
            self.screenControl().enableF3_23(false);
            self.screenControl().visibleF3_24(false);
            self.screenControl().enableF3_24(false);
            self.screenControl().visibleF3_25(false);
            self.screenControl().enableF3_25(false);
            self.screenControl().visibleF3_26(false);
            self.screenControl().enableF3_26(false);
            self.screenControl().visibleF3_27(false);
            self.screenControl().enableF3_27(false);
            self.screenControl().enableF4_1(false);
            nts.uk.ui.errors.clearAll();
            $("#btn-register").focus();
        }

        selectedMode() {
            let self = this;
            self.screenControl().visibleF2_2(true);
            self.screenControl().visibleF2_3(true);
            self.screenControl().enableF2_4(true);
            self.screenControl().enableF3_2(true);
            self.screenControl().enableF3_5(true);
            self.screenControl().enableF3_9(true);
            self.screenControl().enableF3_12(true);
            self.screenControl().visibleF3_15(true);
            self.screenControl().visibleF3_16(true);
            self.screenControl().enableF4_1(true);
            self.condition28(self.dataScreen().errorRangeSetting.upperLimitSetting.valueSettingAtr());
            self.condition29(self.dataScreen().errorRangeSetting.lowerLimitSetting.valueSettingAtr());
            self.condition30(self.dataScreen().alarmRangeSetting.upperLimitSetting.valueSettingAtr());
            self.condition31(self.dataScreen().alarmRangeSetting.lowerLimitSetting.valueSettingAtr());
            self.condition45(self.dataType);
            self.condition46(self.dataType);
            self.condition47(self.dataType);
            nts.uk.ui.errors.clearAll();
            $("#F1_6_container").focus();
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
         * ※28
         */
        condition28(check: boolean) {
            let self = this;
            self.screenControl().enableF3_3(check);
            self.screenControl().enableF3_20(check);
            self.screenControl().enableF3_24(check);
        }

        /**
         * ※29
         */
        condition29(check: boolean) {
            let self = this;
            self.screenControl().enableF3_6(check);
            self.screenControl().enableF3_21(check);
            self.screenControl().enableF3_25(check);
        }

        /**
         * ※30
         */
        condition30(check: boolean) {
            let self = this;
            self.screenControl().enableF3_10(check);
            self.screenControl().enableF3_22(check);
            self.screenControl().enableF3_26(check);
        }

        /**
         * ※31
         */
        condition31(check: boolean) {
            let self = this;
            self.screenControl().enableF3_13(check);
            self.screenControl().enableF3_23(check);
            self.screenControl().enableF3_27(check);
        }

        /**
         * ※45
         */
        condition45(type: DataType) {
            let self = this;
            if (type == DataType.TIME10) {
                self.screenControl().visibleF3_3(true);
                self.screenControl().visibleF3_6(true);
                self.screenControl().visibleF3_10(true);
                self.screenControl().visibleF3_13(true);
                self.screenControl().visibleF3_17(true);
            } else {
                self.screenControl().visibleF3_3(false);
                self.screenControl().visibleF3_6(false);
                self.screenControl().visibleF3_10(false);
                self.screenControl().visibleF3_13(false);
                self.screenControl().visibleF3_17(false);
            }
        }

        /**
         * ※46
         */
        condition46(type: DataType) {
            let self = this;
            if (type == DataType.TIME60) {
                self.screenControl().visibleF3_18(true);
                self.screenControl().visibleF3_20(true);
                self.screenControl().visibleF3_21(true);
                self.screenControl().visibleF3_22(true);
                self.screenControl().visibleF3_23(true);
            } else {
                self.screenControl().visibleF3_18(false);
                self.screenControl().visibleF3_20(false);
                self.screenControl().visibleF3_21(false);
                self.screenControl().visibleF3_22(false);
                self.screenControl().visibleF3_23(false);
            }
        }

        /**
         * ※47
         */
        condition47(type: DataType) {
            let self = this;
            if (type == DataType.TIMES) {
                self.screenControl().visibleF3_19(true);
                self.screenControl().visibleF3_24(true);
                self.screenControl().visibleF3_25(true);
                self.screenControl().visibleF3_26(true);
                self.screenControl().visibleF3_27(true);
            } else {
                self.screenControl().visibleF3_19(false);
                self.screenControl().visibleF3_24(false);
                self.screenControl().visibleF3_25(false);
                self.screenControl().visibleF3_26(false);
                self.screenControl().visibleF3_27(false);
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
            let self = this;
            // アルゴリズム「決定時チェック処理」を実施
            self.dataScreen().checkDecide();
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let result = {
                itemNameCode: self.dataScreen().itemNameCode(),
                name: self.dataScreen().name()
            };
            windows.setShared("QMM019F_RESULTS", result);
            windows.close();
        }

        cancel() {
            windows.close();
        }
    }

    class ScreenControl {
        visibleF2_2: KnockoutObservable<boolean> = ko.observable(false);
        visibleF2_3: KnockoutObservable<boolean> = ko.observable(false);
        enableF2_4: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_2: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_3: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_3: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_5: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_6: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_6: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_9: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_10: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_10: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_12: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_13: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_13: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_15: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_16: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_17: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_18: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_19: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_20: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_20: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_21: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_21: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_22: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_22: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_23: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_23: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_24: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_24: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_25: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_25: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_26: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_26: KnockoutObservable<boolean> = ko.observable(false);
        visibleF3_27: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_27: KnockoutObservable<boolean> = ko.observable(false);
        enableF4_1: KnockoutObservable<boolean> = ko.observable(false);

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
        rangeValAttribute: number;
        errorRangeSetting: IErrorAlarmRangeSetting;
        alarmRangeSetting: IErrorAlarmRangeSetting;
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

        constructor() {
        }

        setData(data: IParams) {
            let self = this;
            self.itemNameCode(data.itemNameCode);
            self.rangeValAttribute(isNullOrUndefined(data.rangeValAttribute) ? null : data.rangeValAttribute.toString());
            self.errorRangeSetting.setData(data.errorRangeSetting);
            self.alarmRangeSetting.setData(data.alarmRangeSetting);
        }

        initSubscribe() {
            let self = this;

            self.errorRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.errorRangeSetting.upperLimitSetting.time10Value(null);
                self.clearError("#F3_3");
                self.errorRangeSetting.upperLimitSetting.time60Value(null);
                self.clearError("#F3_20");
                self.errorRangeSetting.upperLimitSetting.timesValue(null);
                self.clearError("#F3_24");
            });
            self.errorRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
                self.errorRangeSetting.lowerLimitSetting.time10Value(null);
                self.clearError("#F3_6");
                self.errorRangeSetting.lowerLimitSetting.time60Value(null);
                self.clearError("#F3_21");
                self.errorRangeSetting.lowerLimitSetting.timesValue(null);
                self.clearError("#F3_25");
            });
            self.errorRangeSetting.upperLimitSetting.time10Value.subscribe(() => {
                self.clearError("#F3_6");
                self.checkError("#F3_6");
            });
            self.errorRangeSetting.upperLimitSetting.time60Value.subscribe(() => {
                self.clearError("#F3_21");
                self.checkError("#F3_21");
            });
            self.errorRangeSetting.upperLimitSetting.timesValue.subscribe(() => {
                self.clearError("#F3_25");
                self.checkError("#F3_25");
            });

            self.alarmRangeSetting.upperLimitSetting.valueSettingAtr.subscribe(() => {
                self.alarmRangeSetting.upperLimitSetting.time10Value(null);
                self.clearError("#F3_10");
                self.alarmRangeSetting.upperLimitSetting.time60Value(null);
                self.clearError("#F3_22");
                self.alarmRangeSetting.upperLimitSetting.timesValue(null);
                self.clearError("#F3_26");
            });
            self.alarmRangeSetting.lowerLimitSetting.valueSettingAtr.subscribe(() => {
                self.alarmRangeSetting.lowerLimitSetting.time10Value(null);
                self.clearError("#F3_13");
                self.alarmRangeSetting.lowerLimitSetting.time60Value(null);
                self.clearError("#F3_23");
                self.alarmRangeSetting.lowerLimitSetting.timesValue(null);
                self.clearError("#F3_27");
            });
            self.alarmRangeSetting.upperLimitSetting.time10Value.subscribe(() => {
                self.clearError("#F3_13");
                self.checkError("#F3_13");
            });
            self.alarmRangeSetting.upperLimitSetting.time60Value.subscribe(() => {
                self.clearError("#F3_23");
                self.checkError("#F3_23");
            });
            self.alarmRangeSetting.upperLimitSetting.timesValue.subscribe(() => {
                self.clearError("#F3_27");
                self.checkError("#F3_27");
            });
        }

        /**
         * 決定時チェック処理
         */
        checkDecide() {
            let self = this;
            // self.checkErrorRange();
            // self.checkAlarmRange();
        }

        /*
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
        */

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
        time10Value: number;
        time60Value: number;
        timesValue: number;
    }

    class ErrorAlarmValueSetting {
        /**
         * 値設定区分
         */
        valueSettingAtr: KnockoutObservable<boolean>;
        /**
         * 時間 & 10進表記の場合
         */
        time10Value: KnockoutObservable<string>;
        /**
         * 時間 & 60進表記の場合
         */
        time60Value: KnockoutObservable<string>;
        /**
         * 明細範囲値（回数）
         */
        timesValue: KnockoutObservable<string>;

        constructor() {
            this.valueSettingAtr = ko.observable(false);
            this.time10Value = ko.observable(null);
            this.time60Value = ko.observable(null);
            this.timesValue = ko.observable(null);
        }

        setData(data: IErrorAlarmValueSetting) {
            this.valueSettingAtr(data.valueSettingAtr == shareModel.UseRangeAtr.USE);
            this.time10Value(isNullOrUndefined(data.time10Value) ? null : data.time10Value.toString());
            this.time60Value(isNullOrUndefined(data.time60Value) ? null : data.time60Value.toString());
            this.timesValue(isNullOrUndefined(data.timesValue) ? null : data.timesValue.toString());
        }
    }

    interface IAttendanceItemSet {
        timeCountAtr: number;
        errorRangeSetting: IDetailTimeErrorAlarmRangeSetting;
        alarmRangeSetting: IDetailTimeErrorAlarmRangeSetting;
    }

    class AttendanceItemSet {
        /**
         * 時間回数区分
         */
        timeCountAtr: KnockoutObservable<number> = ko.observable(null);
        timeCountAtrText: KnockoutObservable<string> = ko.observable(null);
        /**
         * 勤怠エラー範囲設定
         */
        errorRangeSetting: DetailTimeErrorAlarmRangeSetting = new DetailTimeErrorAlarmRangeSetting();
        /**
         * 勤怠アラーム範囲設定
         */
        alarmRangeSetting: DetailTimeErrorAlarmRangeSetting = new DetailTimeErrorAlarmRangeSetting();

        constructor() {
        }

        setData(data: IAttendanceItemSet) {
            this.timeCountAtr(data.timeCountAtr);
            this.timeCountAtrText(shareModel.getTimeCountAtrText(data.timeCountAtr));
            this.errorRangeSetting.setData(data.errorRangeSetting);
            this.alarmRangeSetting.setData(data.alarmRangeSetting);
        }
    }

    interface IDetailTimeErrorAlarmRangeSetting {
        upperLimitSetting: IDetailTimeErrorAlarmValueSetting;
        lowerLimitSetting: IDetailTimeErrorAlarmValueSetting;
    }

    class DetailTimeErrorAlarmRangeSetting {
        /**
         * 上限設定
         */
        upperLimitSetting: DetailTimeErrorAlarmValueSetting;
        /**
         * 下限設定
         */
        lowerLimitSetting: DetailTimeErrorAlarmValueSetting;

        constructor() {
            this.upperLimitSetting = new DetailTimeErrorAlarmValueSetting();
            this.lowerLimitSetting = new DetailTimeErrorAlarmValueSetting();
        }

        setData(data: IDetailTimeErrorAlarmRangeSetting) {
            this.upperLimitSetting.setData(data.upperLimitSetting);
            this.lowerLimitSetting.setData(data.lowerLimitSetting);
        }
    }

    interface IDetailTimeErrorAlarmValueSetting {
        valueSettingAtr: number;
        timesValue: number;
        timeValue: number;
    }

    class DetailTimeErrorAlarmValueSetting {
        /**
         * 値設定区分
         */
        valueSettingAtr: KnockoutObservable<number>;
        /**
         * 明細範囲値（回数）
         */
        timesValue: KnockoutObservable<number>;
        /**
         * 明細範囲値（時間）
         */
        timeValue: KnockoutObservable<number>;

        constructor() {
            this.valueSettingAtr = ko.observable(null);
            this.timesValue = ko.observable(null);
            this.timeValue = ko.observable(null);
        }

        setData(data: IDetailTimeErrorAlarmValueSetting) {
            this.valueSettingAtr(data.valueSettingAtr);
            if (isNullOrUndefined(data.timesValue)) {
                this.timesValue(null);
            } else {
                this.timesValue(data.timesValue.toFixed(2));
            }
            if (isNullOrUndefined(data.timeValue)) {
                this.timeValue(null);
            } else {
                this.timeValue(nts.uk.time.format.byId("Clock_Short_HM", data.timeValue));
            }
        }
    }

    enum DataType {
        // 時間 & 10進表記
        TIME10 = 0,
        // 時間 & 60進表記
        TIME60 = 1,
        // 回数
        TIMES = 2
    }
}