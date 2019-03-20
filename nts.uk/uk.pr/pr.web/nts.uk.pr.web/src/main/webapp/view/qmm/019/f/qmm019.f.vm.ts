module nts.uk.pr.view.qmm019.f.viewmodel {
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import windows = nts.uk.ui.windows;
    import modal = nts.uk.ui.windows.sub.modal;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;

    export class ScreenModel {
        option: any = {
            decimallength: 2
        };
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
                    self.selectedMode();
                    block.clear();
                })
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            let params: IParams = windows.getShared("QMM019_A_TO_F_PARAMS");
            if (isNullOrUndefined(params.itemRangeSet)) {
                params.itemRangeSet = <shareModel.IItemRangeSet> {};
            }
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

        initSubscribe() {
            let self = this;
            self.dataScreen().initSubscribe();
            self.dataScreen().itemRangeSet.errorUpperLimitSetAtr.subscribe(value => {
                self.condition28(value);
            });

            self.dataScreen().itemRangeSet.errorLowerLimitSetAtr.subscribe(value => {
                self.condition29(value);
            });

            self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr.subscribe(value => {
                self.condition30(value);
            });

            self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr.subscribe(value => {
                self.condition31(value);
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
            let sv1 = service.getAttendanceItemStById(self.categoryAtr, self.dataScreen().itemNameCode());
            $.when(sv1).done((att: IAttendanceItemSet) => {
                self.categoryAtrText(shareModel.getCategoryAtrText(self.categoryAtr));

                self.attendanceItemSet().setData(att);
                self.assignItemRangeSet();

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
            self.dataScreen().itemRangeSet.errorUpperLimitSetAtr(self.attendanceItemSet().errorRangeSetting.upperLimitSetting.valueSettingAtr() == shareModel.UseRangeAtr.USE);
            self.dataScreen().itemRangeSet.errorLowerLimitSetAtr(self.attendanceItemSet().errorRangeSetting.lowerLimitSetting.valueSettingAtr() == shareModel.UseRangeAtr.USE);
            self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr(self.attendanceItemSet().alarmRangeSetting.upperLimitSetting.valueSettingAtr() == shareModel.UseRangeAtr.USE);
            self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr(self.attendanceItemSet().alarmRangeSetting.lowerLimitSetting.valueSettingAtr() == shareModel.UseRangeAtr.USE);
            if (self.attendanceItemSet().timeCountAtr() == shareModel.TimeCountAtr.TIME) {
                self.dataScreen().itemRangeSet.errorUpRangeValTime(self.attendanceItemSet().errorRangeSetting.upperLimitSetting.timeValue());
                self.dataScreen().itemRangeSet.errorLoRangeValTime(self.attendanceItemSet().errorRangeSetting.lowerLimitSetting.timeValue());
                self.dataScreen().itemRangeSet.alarmUpRangeValTime(self.attendanceItemSet().alarmRangeSetting.upperLimitSetting.timeValue());
                self.dataScreen().itemRangeSet.alarmLoRangeValTime(self.attendanceItemSet().alarmRangeSetting.lowerLimitSetting.timeValue());
            } else if (self.attendanceItemSet().timeCountAtr() == shareModel.TimeCountAtr.TIMES) {
                self.dataScreen().itemRangeSet.errorUpRangeValNum(self.attendanceItemSet().errorRangeSetting.upperLimitSetting.timesValue());
                self.dataScreen().itemRangeSet.errorLoRangeValNum(self.attendanceItemSet().errorRangeSetting.lowerLimitSetting.timesValue());
                self.dataScreen().itemRangeSet.alarmUpRangeValNum(self.attendanceItemSet().alarmRangeSetting.upperLimitSetting.timesValue());
                self.dataScreen().itemRangeSet.alarmLoRangeValNum(self.attendanceItemSet().alarmRangeSetting.lowerLimitSetting.timesValue());
            }
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
            self.screenControl().enableF3_5(false);
            self.screenControl().enableF3_9(false);
            self.screenControl().enableF3_12(false);
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
            self.condition28(self.dataScreen().itemRangeSet.errorUpperLimitSetAtr());
            self.condition29(self.dataScreen().itemRangeSet.errorLowerLimitSetAtr());
            self.condition30(self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr());
            self.condition31(self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr());
            self.condition46(self.attendanceItemSet().timeCountAtr());
            self.condition47(self.attendanceItemSet().timeCountAtr());
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
         * ※46
         */
        condition46(type: shareModel.TimeCountAtr) {
            let self = this;
            if (type == shareModel.TimeCountAtr.TIME) {
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
        condition47(type: shareModel.TimeCountAtr) {
            let self = this;
            if (type == shareModel.TimeCountAtr.TIMES) {
                self.screenControl().visibleF3_17(true);
                self.screenControl().visibleF3_24(true);
                self.screenControl().visibleF3_25(true);
                self.screenControl().visibleF3_26(true);
                self.screenControl().visibleF3_27(true);
            } else {
                self.screenControl().visibleF3_17(false);
                self.screenControl().visibleF3_24(false);
                self.screenControl().visibleF3_25(false);
                self.screenControl().visibleF3_26(false);
                self.screenControl().visibleF3_27(false);
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
            // アルゴリズム「決定時チェック処理」を実施
            self.dataScreen().checkDecide(self.attendanceItemSet().timeCountAtr());
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            windows.setShared("QMM019F_RESULTS", self.createResult());
            windows.close();
        }

        createResult(){
            let self = this;
            let itemRangeSet = <shareModel.IItemRangeSet>{};
            itemRangeSet.alarmLowerLimitSetAtr = self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr() ? share.model.UseRangeAtr.USE : share.model.UseRangeAtr.NOT_USE;
            if (self.attendanceItemSet().timeCountAtr() == shareModel.TimeCountAtr.TIME) {
                itemRangeSet.rangeValAttribute = share.model.RangeValueEnum.TIME;
                if (self.dataScreen().itemRangeSet.errorUpperLimitSetAtr()) {
                    itemRangeSet.errorUpperLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.errorUpRangeValTime = self.dataScreen().itemRangeSet.errorUpRangeValTime();
                } else {
                    itemRangeSet.errorUpperLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
                if (self.dataScreen().itemRangeSet.errorLowerLimitSetAtr()) {
                    itemRangeSet.errorLowerLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.errorLoRangeValTime = self.dataScreen().itemRangeSet.errorLoRangeValTime();
                } else {
                    itemRangeSet.errorLowerLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
                if (self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr()) {
                    itemRangeSet.alarmUpperLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.alarmUpRangeValTime = self.dataScreen().itemRangeSet.alarmUpRangeValTime();
                } else {
                    itemRangeSet.alarmUpperLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
                if (self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr()) {
                    itemRangeSet.alarmLowerLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.alarmLoRangeValTime = self.dataScreen().itemRangeSet.alarmLoRangeValTime();
                } else {
                    itemRangeSet.alarmLowerLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
            } else if (self.attendanceItemSet().timeCountAtr() == shareModel.TimeCountAtr.TIMES) {
                itemRangeSet.rangeValAttribute = share.model.RangeValueEnum.TIME;
                if (self.dataScreen().itemRangeSet.errorUpperLimitSetAtr()) {
                    itemRangeSet.errorUpperLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.errorUpRangeValNum = self.dataScreen().itemRangeSet.errorUpRangeValNum();
                } else {
                    itemRangeSet.errorUpperLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
                if (self.dataScreen().itemRangeSet.errorLowerLimitSetAtr()) {
                    itemRangeSet.errorLowerLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.errorLoRangeValNum = self.dataScreen().itemRangeSet.errorLoRangeValNum();
                } else {
                    itemRangeSet.errorLowerLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
                if (self.dataScreen().itemRangeSet.alarmUpperLimitSetAtr()) {
                    itemRangeSet.alarmUpperLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.alarmUpRangeValNum = self.dataScreen().itemRangeSet.alarmUpRangeValNum();
                } else {
                    itemRangeSet.alarmUpperLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
                if (self.dataScreen().itemRangeSet.alarmLowerLimitSetAtr()) {
                    itemRangeSet.alarmLowerLimitSetAtr = share.model.UseRangeAtr.USE;
                    itemRangeSet.alarmLoRangeValNum = self.dataScreen().itemRangeSet.alarmLoRangeValNum();
                } else {
                    itemRangeSet.alarmLowerLimitSetAtr = share.model.UseRangeAtr.NOT_USE;
                }
            }

            let result = {
                itemNameCode: self.dataScreen().itemNameCode(),
                shortName: self.dataScreen().shortName(),
                itemRangeSet: itemRangeSet
            };
            return result;
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
        // visibleF3_3: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_3: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_5: KnockoutObservable<boolean> = ko.observable(false);
        // visibleF3_6: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_6: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_9: KnockoutObservable<boolean> = ko.observable(false);
        // visibleF3_10: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_10: KnockoutObservable<boolean> = ko.observable(false);
        enableF3_12: KnockoutObservable<boolean> = ko.observable(false);
        // visibleF3_13: KnockoutObservable<boolean> = ko.observable(false);
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

        itemRangeSet: ItemRangeSet = new ItemRangeSet();

        constructor() {
        }

        setData(data: IParams) {
            let self = this;
            self.itemNameCode(data.itemNameCode);
            self.itemRangeSet.setData(data.itemRangeSet);
        }

        initSubscribe() {
            let self = this;
            self.itemRangeSet.errorUpperLimitSetAtr.subscribe(() => {
                self.itemRangeSet.errorUpRangeValTime(null);
                self.clearError("#F3_20");
                self.itemRangeSet.errorUpRangeValNum(null);
                self.clearError("#F3_24");
            });
            self.itemRangeSet.errorLowerLimitSetAtr.subscribe(() => {
                self.itemRangeSet.errorLoRangeValTime(null);
                self.clearError("#F3_21");
                self.itemRangeSet.errorLoRangeValNum(null);
                self.clearError("#F3_25");
            });
            self.itemRangeSet.errorUpRangeValTime.subscribe(() => {
                self.clearError("#F3_21");
                self.checkError("#F3_21");
            });
            self.itemRangeSet.errorUpRangeValNum.subscribe(() => {
                self.clearError("#F3_25");
                self.checkError("#F3_25");
            });
            self.itemRangeSet.errorLoRangeValTime.subscribe(() => {
                self.clearError("#F3_21");
                self.checkError("#F3_21");
            });
            self.itemRangeSet.errorLoRangeValNum.subscribe(() => {
                self.clearError("#F3_25");
                self.checkError("#F3_25");
            });

            self.itemRangeSet.alarmUpperLimitSetAtr.subscribe(() => {
                self.itemRangeSet.alarmUpRangeValTime(null);
                self.clearError("#F3_22");
                self.itemRangeSet.alarmUpRangeValNum(null);
                self.clearError("#F3_26");
            });
            self.itemRangeSet.alarmLowerLimitSetAtr.subscribe(() => {
                self.itemRangeSet.alarmLoRangeValTime(null);
                self.clearError("#F3_23");
                self.itemRangeSet.alarmLoRangeValNum(null);
                self.clearError("#F3_27");
            });
            self.itemRangeSet.alarmUpRangeValTime.subscribe(() => {
                self.clearError("#F3_23");
                self.checkError("#F3_23");
            });
            self.itemRangeSet.alarmUpRangeValNum.subscribe(() => {
                self.clearError("#F3_27");
                self.checkError("#F3_27");
            });
            self.itemRangeSet.alarmLoRangeValTime.subscribe(() => {
                self.clearError("#F3_23");
                self.checkError("#F3_23");
            });
            self.itemRangeSet.alarmLoRangeValNum.subscribe(() => {
                self.clearError("#F3_27");
                self.checkError("#F3_27");
            });
        }

        /**
         * 決定時チェック処理
         */
        checkDecide(type: shareModel.TimeCountAtr) {
            let self = this;
            self.checkErrorRange(type);
            self.checkAlarmRange(type);
        }

        checkErrorRange(type: shareModel.TimeCountAtr) {
            let self = this;

            let error = {
                upper: {
                    atr: null,
                    value: null,
                    control: null
                },
                lower: {
                    atr: null,
                    value: null,
                    control: null
                }
            };
            error.upper.atr = self.itemRangeSet.errorUpperLimitSetAtr();
            error.lower.atr = self.itemRangeSet.errorLowerLimitSetAtr();
            switch (type) {
                case shareModel.TimeCountAtr.TIME:
                    error.upper.value = self.itemRangeSet.errorUpRangeValTime();
                    error.upper.control = "#F3_20";
                    error.lower.value = self.itemRangeSet.errorLoRangeValTime();
                    error.lower.control = "#F3_21";
                    break;
                case shareModel.TimeCountAtr.TIMES:
                    error.upper.value = self.itemRangeSet.errorUpRangeValNum();
                    error.upper.control = "#F3_24";
                    error.lower.value = self.itemRangeSet.errorLoRangeValNum();
                    error.lower.control = "#F3_25";
                    break;
                default:
                    return;
            }

            // エラー範囲上限値チェック状況を確認する
            if (error.upper.atr) {
                // エラー範囲上限値の入力を確認する
                if (isNullOrEmpty(error.upper.value)) {
                    self.setError(error.upper.control, "MsgQ_14");
                } else {
                    // エラー範囲下限値チェック状況を確認する
                    if (error.lower.atr) {
                        // エラー範囲下限値の入力を確認する
                        if (isNullOrEmpty(error.lower.value)) {
                            self.setError(error.lower.control, "MsgQ_15");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(error.lower.value) > parseInt(error.upper.value)) {
                                self.setError(error.lower.control, "MsgQ_1");
                            }
                        }
                    }
                }
            } else {
                // エラー範囲下限値チェック状況を確認する
                if (error.lower.atr) {
                    // エラー範囲下限値の入力を確認する
                    if (isNullOrEmpty(error.lower.value)) {
                        self.setError(error.lower.control, "MsgQ_15");
                    }
                }
            }
        }

        checkAlarmRange(type: shareModel.TimeCountAtr) {
            let self = this;
            let alarm = {
                upper: {
                    atr: null,
                    value: null,
                    control: null
                },
                lower: {
                    atr: null,
                    value: null,
                    control: null
                }
            };
            alarm.upper.atr = self.itemRangeSet.alarmUpperLimitSetAtr();
            alarm.lower.atr = self.itemRangeSet.alarmLowerLimitSetAtr();
            switch (type) {
                case shareModel.TimeCountAtr.TIME:
                    alarm.upper.value = self.itemRangeSet.alarmUpRangeValTime();
                    alarm.upper.control = "#F3_22";
                    alarm.lower.value = self.itemRangeSet.alarmLoRangeValTime();
                    alarm.lower.control = "#F3_23";
                    break;
                case shareModel.TimeCountAtr.TIMES:
                    alarm.upper.value = self.itemRangeSet.alarmUpRangeValNum();
                    alarm.upper.control = "#F3_26";
                    alarm.lower.value = self.itemRangeSet.alarmLoRangeValNum();
                    alarm.lower.control = "#F3_27";
                    break;
            }
            // アラーム範囲上限値チェック状況を確認する
            if (alarm.upper.atr) {
                // アラーム範囲上限値の入力を確認する
                if (isNullOrEmpty(alarm.upper.value)) {
                    self.setError(alarm.upper.control, "MsgQ_16");
                } else {
                    // アラーム範囲下限値チェック状況を確認する
                    if (alarm.lower.atr) {
                        // アラーム範囲下限値の入力を確認する
                        if (isNullOrEmpty(alarm.lower.value)) {
                            self.setError(alarm.lower.control, "MsgQ_17");
                        } else {
                            // エラー範囲の入力確認をする
                            if (parseInt(alarm.lower.value) > parseInt(alarm.upper.value)) {
                                self.setError(alarm.lower.control, "MsgQ_2");
                            }
                        }
                    }
                }
            } else {
                // アラーム範囲下限値チェック状況を確認する
                if (alarm.lower.atr) {
                    // アラーム範囲下限値の入力を確認する
                    if (isNullOrEmpty(alarm.lower.value)) {
                        self.setError(alarm.lower.control, "MsgQ_17");
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
        errorUpRangeValTime: KnockoutObservable<string> = ko.observable(null);
        errorUpRangeValNum: KnockoutObservable<string> = ko.observable(null);
        errorLowerLimitSetAtr: KnockoutObservable<boolean> = ko.observable(null);
        errorLoRangeValTime: KnockoutObservable<string> = ko.observable(null);
        errorLoRangeValNum: KnockoutObservable<string> = ko.observable(null);
        alarmUpperLimitSetAtr: KnockoutObservable<boolean> = ko.observable(null);
        alarmUpRangeValTime: KnockoutObservable<string> = ko.observable(null);
        alarmUpRangeValNum: KnockoutObservable<string> = ko.observable(null);
        alarmLowerLimitSetAtr: KnockoutObservable<boolean> = ko.observable(null);
        alarmLoRangeValTime: KnockoutObservable<string> = ko.observable(null);
        alarmLoRangeValNum: KnockoutObservable<string> = ko.observable(null);

        constructor() {
        }

        setData(data: shareModel.IItemRangeSet) {
            this.errorUpperLimitSetAtr(data.errorUpperLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.errorUpRangeValTime(isNullOrUndefined(data.errorUpRangeValTime) ? null : data.errorUpRangeValTime);
            this.errorUpRangeValNum(isNullOrUndefined(data.errorUpRangeValNum) ? null : data.errorUpRangeValNum);

            this.errorLowerLimitSetAtr(data.errorLowerLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.errorLoRangeValTime(isNullOrUndefined(data.errorLoRangeValTime) ? null : data.errorLoRangeValTime);
            this.errorLoRangeValNum(isNullOrUndefined(data.errorLoRangeValNum) ? null : data.errorLoRangeValNum);

            this.alarmUpperLimitSetAtr(data.alarmUpperLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.alarmUpRangeValTime(isNullOrUndefined(data.alarmUpRangeValTime) ? null : data.alarmUpRangeValTime);
            this.alarmUpRangeValNum(isNullOrUndefined(data.alarmUpRangeValNum) ? null : data.alarmUpRangeValNum);

            this.alarmLowerLimitSetAtr(data.alarmLowerLimitSetAtr == shareModel.UseRangeAtr.USE);
            this.alarmLoRangeValTime(isNullOrUndefined(data.alarmLoRangeValTime) ? null : data.alarmLoRangeValTime);
            this.alarmLoRangeValNum(isNullOrUndefined(data.alarmLoRangeValNum) ? null : data.alarmLoRangeValNum);
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
        timeCountAtr: KnockoutObservable<shareModel.TimeCountAtr> = ko.observable(null);
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
            if (isNullOrUndefined(data)) {
                this.timeCountAtr(null);
                this.timeCountAtrText(null);
                this.errorRangeSetting.setData(null);
                this.alarmRangeSetting.setData(null);
            } else {
                this.timeCountAtr(data.timeCountAtr);
                this.timeCountAtrText(shareModel.getTimeCountAtrText(data.timeCountAtr));
                this.errorRangeSetting.setData(data.errorRangeSetting);
                this.alarmRangeSetting.setData(data.alarmRangeSetting);
            }
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
            if (isNullOrUndefined(data)) {
                this.upperLimitSetting.setData(null);
                this.lowerLimitSetting.setData(null);
            } else {
                this.upperLimitSetting.setData(data.upperLimitSetting);
                this.lowerLimitSetting.setData(data.lowerLimitSetting);
            }
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
            let valueSettingAtr, timesValue, timeValue;
            if (isNullOrUndefined(data)) {
                valueSettingAtr = null;
                timesValue = null;
                timeValue = null;
            } else {
                valueSettingAtr = data.valueSettingAtr;
                timesValue = data.timesValue;
                timeValue = data.timeValue;
            }
            this.valueSettingAtr(valueSettingAtr);
            if (isNullOrUndefined(timesValue)) {
                this.timesValue(null);
            } else {
                this.timesValue(timesValue.toFixed(2));
            }
            if (isNullOrUndefined(timeValue)) {
                this.timeValue(null);
            } else {
                this.timeValue(timeValue);
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