/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kal013.b {
    import errors = nts.uk.ui.errors;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import sharemodel = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    const PATH_API = {
        getAttendComparison: "at/record/attendanceitem/daily/getattendcomparison/{0}",
        getAttendCoutinousTime: "at/record/attendanceitem/daily/getattendcoutinoustime",
        getAttendCoutinousWork: "at/share/worktype/findNotDeprecated",
        getAttendCoutinousTimeZone: "at/record/attendanceitem/daily/getattendcoutinoustimezone",
        getAttendCompound: "at/record/attendanceitem/daily/getattendcompound/{0}",
        getAttendNameByIds: "at/record/attendanceitem/daily/getattendnamebyids",
        getErrorAlarmCondition: "at/record/attendanceitem/daily/geterroralarmcondition/{0}",
        getAttendanceItemByCodes: "at/record/divergencetime/setting/AttendanceDivergenceName",
        findWorkTypeByCodes: "at/share/worktype/findNotDeprecatedByListCode",
        //update #100050 daily
        getAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListByAttendanceAtr/",
        getMonthlyAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListMonthlyByAttendanceAtr/",
        getOptItemByAtr: "at/record/attendanceitem/daily/getattendcomparison/",
        // start MinhVV Edit
        getEnumTypeCheckWorkRecordMultipleMonth: "/at/function/alarm/checkcondition/kal003b/get-enum-type-check-work-record-multiple-month",
        // End MinhVV
        getEnumSingleValueCompareType: "/at/function/alarm/checkcondition/kal003b/getEnumSingleValueCompareTypse",
        getEnumRangeCompareType: "/at/function/alarm/checkcondition/kal003b/getEnumRangeCompareType",
        getEnumTypeCheckWorkRecord: "/at/function/alarm/checkcondition/kal003b/getEnumTypeCheckWorkRecord",
        getEnumTargetSelectionRange: "/at/function/alarm/checkcondition/kal003b/getEnumTargetSelectionRange",
        getEnumTargetServiceType: "/at/function/alarm/checkcondition/kal003b/getEnumTargetServiceType",
        getEnumLogicalOperator: "/at/function/alarm/checkcondition/kal003b/getEnumLogicalOperator",
        //monthly
        getAttdItemMonByAtr: "at/record/attendanceitem/monthly/findbyatr/{0}",

        //Update ticket #101187
//            getSpecialholidayframe : "at/share/worktype/specialholidayframe/findspecbyabolish",
        getSpecialHoliday: "shared/specialholiday/findByCid",
        //End Update ticket #101187

        getMonthlyAttendanceItemByCodes: "at/record/divergencetime/setting/getMonthlyAttendanceDivergenceName",
        getListMonthlyByAtrPrimitive: "at/record/businesstype/attendanceItem/getListMonthlyByAtrPrimitive/",
        getMonthlyOptItemByAtr: "at/record/attendanceitem/monthly/getattendcomparison/",

        //getname monthly
        getNameMonthly: "screen/at/correctionofdailyperformance/getNameMonthlyAttItem"
    };

    @bean()
    export class KAL013BViewModel extends ko.ViewModel {
        workRecordExtractingCondition: KnockoutObservable<sharemodel.WorkRecordExtractingCondition>;
        // list item check
        listTypeCheckWorkRecords: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        listSingleValueCompareTypes: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        listRangeCompareTypes: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        listCompareTypes: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        itemListTargetServiceType_BA1_2: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        itemListTargetSelectionRange_BA1_5: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        listAllWorkType: Array<string> = ([]);
        listAllAttdItem: Array<string> = ([]);
        listAllWorkingTime: Array<string> = ([]);

        displayWorkTypeSelections_BA1_4: KnockoutObservable<string> = ko.observable('');
        displayAttendanceItemSelections_BA2_3: KnockoutObservable<string> = ko.observable('');
        displayWorkingTimeSelections_BA5_3: KnockoutObservable<string> = ko.observable('');
        required_BA1_4: KnockoutObservable<boolean>;

        private setting: sharemodel.WorkRecordExtractingCondition;

        swANDOR_B5_3: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        swANDOR_B6_3: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        swANDOR_B7_2: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        enableComparisonMaxValue: KnockoutObservable<boolean> = ko.observable(false);
        comparisonRange: KnockoutObservable<ComparisonValueRange>;
        checkItemTemp: KnockoutObservable<number> = ko.observable(null);

        category: KnockoutObservable<number> = ko.observable(0);
        //monthly
        listEnumRoleType: KnockoutObservableArray<any>;
        listTypeCheckVacation: KnockoutObservableArray<any>;
        listSpecialholidayframe: Array<any> = ([]);
        private settingExtraMon: sharemodel.ExtraResultMonthly;
        extraResultMonthly: KnockoutObservable<sharemodel.ExtraResultMonthly>;

        // list item check Multiple Months MinhVV
        listTypeCheckWorkRecordMultipleMonths: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        modeScreen: KnockoutObservable<number> = ko.observable(0);
        mulMonCondExtra: KnockoutObservable<sharemodel.MulMonCheckCondExtra>;
        //MinhVV
        mulMonCheckCondSet: KnockoutObservable<sharemodel.MulMonCheckCondSet>;
        private setting: sharemodel.MulMonCheckCondSet;

        constructor(params: any) {
            super();
            const vm = this;

            let option = params;

            vm.category(option.category);
            switch (vm.category()) {
                case sharemodel.CATEGORY.DAILY: {
                    vm.setting = $.extend({}, shareutils.getDefaultWorkRecordExtractingCondition(0), option.data);

                    let workRecordExtractingCond = shareutils.convertTransferDataToWorkRecordExtractingCondition(vm.setting);
                    vm.workRecordExtractingCondition = ko.observable(workRecordExtractingCond);
                    // setting comparison value range

                    vm.comparisonRange = ko.observable(vm.initComparisonValueRange());

                    vm.checkItemTemp = ko.observable(vm.workRecordExtractingCondition().checkItem());

                    // change select item check
                    vm.workRecordExtractingCondition().checkItem.subscribe((itemCheck) => {
                        errors.clearAll();
                        //fix bug 100145
                        vm.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition().planLstWorkType([]);
                        vm.comparisonRange().minAmountOfMoneyValue(null);
                        vm.comparisonRange().maxAmountOfMoneyValue(null);
                        vm.comparisonRange().minTimeValue(null);
                        vm.comparisonRange().maxTimeValue(null);
                        vm.comparisonRange().minTimesValue(null);
                        vm.comparisonRange().maxTimesValue(null);
                        vm.comparisonRange().maxTimeWithinDayValue(null);
                        vm.comparisonRange().minTimeWithinDayValue(null);
                        if ((itemCheck && itemCheck != undefined) || itemCheck === 0) {
                            vm.initialScreen().then(function () {
                                vm.settingEnableComparisonMaxValueField(false);
                                if ((vm.checkItemTemp() || vm.checkItemTemp() == 0) && vm.checkItemTemp() != itemCheck) {
                                    setTimeout(function () {
                                        vm.displayAttendanceItemSelections_BA2_3("");
                                    }, 200);

                                }
                            });
                        }
                        $(".nts-input").ntsError("clear");
                    });
                    vm.comparisonRange().comparisonOperator.subscribe((operN) => {
                        vm.settingEnableComparisonMaxValueField(false);
                        if (vm.comparisonRange().comparisonOperator() > 5) {
                            $(".nts-input").ntsError("clear");
                            if (vm.comparisonRange().comparisonOperator() == 7 || vm.comparisonRange().comparisonOperator() == 9) {
                                setTimeout(() => {
                                    if (parseInt(vm.comparisonRange().minValue()) > parseInt(vm.comparisonRange().maxValue())) {
                                        $('#endValue').ntsError('set', {messageId: "Msg_927"});
                                    }
                                }, 25);
                            }
                            if (vm.comparisonRange().comparisonOperator() == 6 || vm.comparisonRange().comparisonOperator() == 8) {
                                setTimeout(() => {
                                    if (parseInt(vm.comparisonRange().minValue()) >= parseInt(vm.comparisonRange().maxValue())) {
                                        $('#endValue').ntsError('set', {messageId: "Msg_927"});
                                    }
                                }, 25);
                            }
                        } else {
                            $(".nts-input").ntsError("clear");
                        }
                    });
                    vm.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition().comparePlanAndActual = ko.observable(vm.setting.errorAlarmCondition.workTypeCondition.comparePlanAndActual);
                    vm.required_BA1_4 = ko.observable(vm.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition().comparePlanAndActual() > 0);
                    vm.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition().comparePlanAndActual.subscribe((newV) => {
                        vm.required_BA1_4(newV > 0);
                        $(".nts-input").ntsError("clear");
                    });
                    break;
                }
                case sharemodel.CATEGORY.MONTHLY: {
                    vm.modeScreen(1);
                    //monthly
                    vm.listEnumRoleType = ko.observableArray(__viewContext.enums.TypeMonCheckItem);
//                    vm.listTypeCheckVacation = ko.observableArray(__viewContext.enums.TypeCheckVacation);
                    vm.listTypeCheckVacation = ko.observableArray([
                        new sharemodel.ItemModel(0, vm.$i18n('KAL003_112')),
                        new sharemodel.ItemModel(1, vm.$i18n('KAL003_113')),
                        new sharemodel.ItemModel(2, vm.$i18n('KAL003_114')),
                        new sharemodel.ItemModel(3, vm.$i18n('KAL003_115')),
                        new sharemodel.ItemModel(6, vm.$i18n('KAL003_118'))
                    ]);

                    vm.extraResultMonthly = ko.observable(sharemodel.ExtraResultMonthly.clone(option.data));
                    break;
                }
                //MinhVV add
                case sharemodel.CATEGORY.MULTIPLE_MONTHS: {
                    vm.setting = $.extend({}, shareutils.getDefaultMulMonCheckCondSet(0), option.data);
                    let mulMonCheckCondSet = shareutils.convertTransferDataToMulMonCheckCondSet(vm.setting);
                    vm.mulMonCheckCondSet = ko.observable(mulMonCheckCondSet);
                    // setting comparison value range

                    vm.comparisonRange = ko.observable(vm.initComparisonValueRangeMulMon());
                    vm.checkItemTemp = ko.observable(vm.mulMonCheckCondSet().typeCheckItem());

                    // change select item check
                    vm.mulMonCheckCondSet().typeCheckItem.subscribe((itemCheck) => {
                        errors.clearAll();
                        // fix bug 100050 save data
                        vm.mulMonCheckCondSet().erAlAtdItem().countableAddAtdItems([]);
                        vm.mulMonCheckCondSet().erAlAtdItem().countableSubAtdItems([]);

                        // fix khoi tao khi typecheck thay doi
                        vm.comparisonRange().minAmountOfMoneyValue(null);
                        vm.comparisonRange().maxAmountOfMoneyValue(null);
                        vm.comparisonRange().minTimeValue(null);
                        vm.comparisonRange().maxTimeValue(null);
                        vm.comparisonRange().minTimesValue(null);
                        vm.comparisonRange().maxTimesValue(null);
                        //日数
                        vm.comparisonRange().minTimesValueDay(null);
                        vm.comparisonRange().maxTimesValueDay(null);
                        vm.mulMonCheckCondSet().times(0);
                        if ((itemCheck && itemCheck != undefined) || itemCheck === TYPECHECKWORKRECORDMULTIPLEMONTH.TIME) {
                            vm.initialMultiMonthScreen().then(function () {
                                if ((vm.checkItemTemp() || vm.checkItemTemp() == TYPECHECKWORKRECORDMULTIPLEMONTH.TIME) && vm.checkItemTemp() != itemCheck) {
                                    setTimeout(function () {
                                        vm.displayAttendanceItemSelections_BA2_3("");
                                    }, 200);
                                }
                            });
                        }
                        $(".nts-input").ntsError("clear");
                    });

                    vm.comparisonRange().comparisonOperator.subscribe((operN) => {
                        $(".nts-input").ntsError("clear");
                        vm.settingEnableComparisonMaxValueFieldExtra();

                    });
                    break;
                }
                default:
                    break;
            }
        }

        created() {
            const vm = this;
            errors.clearAll();

            switch (vm.category()) {
                case sharemodel.CATEGORY.DAILY: {
                    vm.getAllEnums();
                    vm.initialScreen();
                    break;
                }

                case sharemodel.CATEGORY.MONTHLY: {
                    vm.getAllEnums();
                    vm.getSpecialHoliday();
                    break;
                }

                case sharemodel.CATEGORY.MULTIPLE_MONTHS: {
                    vm.getAllEnums();
                    vm.initialMultiMonthScreen();
                    break;
                }
                default:
                    break;
            }

            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;

            $("#table-group1condition").ntsFixedTable();
            $("#table-group2condition").ntsFixedTable();
            $('#cbxTypeCheckWorkRecordcategory5').focus();
            $('#cbxTypeCheckWorkRecordcategory7').focus();
            $('#cbxTypeCheckWorkRecordcategory9').focus();
        }

        submitAndCloseDialog() {
            const vm = this;

            $('.nts-input').filter(":enabled").trigger("validate");
            if (errors.hasError() === true) {
                return;
            }

            let retData: any;
            switch (vm.category()) {
                case sharemodel.CATEGORY.DAILY: {
                    let workRecordExtractingCondition = vm.workRecordExtractingCondition();
                    let isOk: boolean = true;
                    if (workRecordExtractingCondition.checkItem() == enItemCheck.Time
                        || workRecordExtractingCondition.checkItem() == enItemCheck.Times
                        || workRecordExtractingCondition.checkItem() == enItemCheck.AmountOfMoney
                        || workRecordExtractingCondition.checkItem() == enItemCheck.TimeOfDate
                        || workRecordExtractingCondition.checkItem() == enItemCheck.CountinuousTime
                    ) {
                        vm.initialDataOfErAlAtdItemCon();
                        // validate comparison range
                        let group1 = workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1();
                        let listErAlAtdItemCondition = group1.lstErAlAtdItemCon();
                        let erAlAtdItemCondition = listErAlAtdItemCondition[0];
                        if (vm.comparisonRange().checkValidOfRange(
                                workRecordExtractingCondition.checkItem()
                                , 1)) {
                            erAlAtdItemCondition.compareOperator(vm.comparisonRange().comparisonOperator());
                            erAlAtdItemCondition.compareStartValue(vm.comparisonRange().minValue());
                            erAlAtdItemCondition.compareEndValue(vm.comparisonRange().maxValue());
                            erAlAtdItemCondition.singleAtdItem(vm.comparisonRange().minValue());
                            //clear
                            //listErAlAtdItemCondition = listErAlAtdItemCondition.splice(0, 1);
                            // workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon(listErAlAtdItemCondition);
                            //workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group2().lstErAlAtdItemCon([]);

                        } else {
                            isOk = false;
                        }
                    } else if (workRecordExtractingCondition.checkItem() == enItemCheck.CountinuousWork
                        || workRecordExtractingCondition.checkItem() == enItemCheck.CountinuousTimeZone) {
                        // workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon([]);
                        //workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group2().lstErAlAtdItemCon([]);
                    }
                    if (isOk) {
                        retData = ko.toJS(workRecordExtractingCondition);
                        retData = shareutils.convertArrayOfWorkRecordExtractingConditionToJS(retData, workRecordExtractingCondition);
                    }
                    break;
                }
                case sharemodel.CATEGORY.MONTHLY: {
                    retData = ko.toJS(vm.extraResultMonthly());
                    break;
                }
                //MinhVV add
                case sharemodel.CATEGORY.MULTIPLE_MONTHS: {
                    let isOk: boolean = true;

                    let mulMonCheckItem = vm.mulMonCheckCondSet().typeCheckItem();
                    if (mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIME
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIMES
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AMOUNT
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIME
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIMES
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_AMOUNT
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIME
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIMES
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_AMOUNT
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIME
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIMES
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_AMOUNT
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.DAYS
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_DAYS
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_DAYS
                        || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_DAYS
                    ) {
                        // khoi tao du lieu mac dinh ban dau
                        vm.initialDataOfErAlAtdItemConMultipleMonth();
                        if (vm.comparisonRange().checkValidOfRangeCategory9(mulMonCheckItem, 1)) {
                            vm.mulMonCheckCondSet().erAlAtdItem().compareOperator(vm.comparisonRange().comparisonOperator());
                            vm.mulMonCheckCondSet().erAlAtdItem().compareStartValue(vm.comparisonRange().minValue());
                            vm.mulMonCheckCondSet().erAlAtdItem().compareEndValue(vm.comparisonRange().maxValue());
                            vm.mulMonCheckCondSet().erAlAtdItem().singleAtdItem(vm.comparisonRange().minValue());
                        } else {
                            isOk = false;
                        }
                    }
                    if (isOk) {
                        retData = ko.toJS(vm.mulMonCheckCondSet());
                        retData = shareutils.convertArrayOfMulMonCheckCondSetToJS(retData, vm.mulMonCheckCondSet());
                    }
                    break;
                }
                default:
                    break;
            }

            vm.$window.close({
                retData
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }

        /**
         * setting Enable/Disable Comparison of Max Value Field
         */
        private settingEnableComparisonMaxValueField(isStart: boolean) {
            const vm = this;
            if (isStart) {
                vm.enableComparisonMaxValue(
                    vm.workRecordExtractingCondition().errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon()[0].compareOperator() > 5);
            } else {
                vm.enableComparisonMaxValue(
                    vm.comparisonRange().comparisonOperator() > 5);
            }
        }

        private settingEnableComparisonMaxValueFieldExtra() {
            const vm = this;
            vm.enableComparisonMaxValue(vm.mulMonCheckCondSet().erAlAtdItem().compareOperator() > 5);
            //>5 thi tra ve  ban dau 30/07
            if (!vm.enableComparisonMaxValue()) {
                let mulMonCheckType = vm.mulMonCheckCondSet().typeCheckItem();
            }
            //日数
//            vm.comparisonRange().minTimesValueDay.subscribe((value) => {
//                if (vm.comparisonRange().comparisonOperator() == 7 || vm.comparisonRange().comparisonOperator() == 9) {
//                    if (vm.comparisonRange().minTimesValueDay() > vm.comparisonRange().maxTimesValueDay()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//                if (vm.comparisonRange().comparisonOperator() == 6 || vm.comparisonRange().comparisonOperator() == 8) {
//                    if (vm.comparisonRange().minTimesValueDay() >= vm.comparisonRange().maxTimesValueDay()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//
//           });
//           // 金額
//            vm.comparisonRange().minTimeValue.subscribe((value) => {
//                if (vm.comparisonRange().comparisonOperator() == 7 || vm.comparisonRange().comparisonOperator() == 9) {
//                    if (vm.comparisonRange().minTimeValue() > vm.comparisonRange().maxTimeValue()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//                if (vm.comparisonRange().comparisonOperator() == 6 || vm.comparisonRange().comparisonOperator() == 8) {
//                    if (vm.comparisonRange().minTimeValue() >= vm.comparisonRange().maxTimeValue()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//
//           });
//            //時間
//            vm.comparisonRange().minTimesValue.subscribe((value) => {
//                if (vm.comparisonRange().comparisonOperator() == 7 || vm.comparisonRange().comparisonOperator() == 9) {
//                    if (vm.comparisonRange().minTimesValue() > vm.comparisonRange().maxTimesValue()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//                if (vm.comparisonRange().comparisonOperator() == 6 || vm.comparisonRange().comparisonOperator() == 8) {
//                    if (vm.comparisonRange().minTimesValue() >= vm.comparisonRange().maxTimesValue()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//           });
//            vm.comparisonRange().minAmountOfMoneyValue.subscribe((value) => {
//                if (vm.comparisonRange().comparisonOperator() == 7 || vm.comparisonRange().comparisonOperator() == 9) {
//                    if (vm.comparisonRange().minAmountOfMoneyValue() > vm.comparisonRange().minAmountOfMoneyValue()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//                if (vm.comparisonRange().comparisonOperator() == 6 || vm.comparisonRange().comparisonOperator() == 8) {
//                    if (vm.comparisonRange().minAmountOfMoneyValue() >= vm.comparisonRange().minAmountOfMoneyValue()) {
//                        setTimeout(() => {
//                            nts.uk.ui.errors.removeByCode($('#endValue'), 'Msg_927');
//                            $('#endValue').ntsError('set', { messageId: "Msg_927" });
//                        }, 25);
//                    }
//                }
//           });

        }

        private initComparisonValueRange(): ComparisonValueRange {
            const vm = this;
            let erAlAtdItemCondition = vm.workRecordExtractingCondition().errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon()[0];

            let comparisonValueRange;

            if (vm.workRecordExtractingCondition().checkItem() == enItemCheck.Time          //時間
                || vm.workRecordExtractingCondition().checkItem() == enItemCheck.Times      //回数
                || vm.workRecordExtractingCondition().checkItem() == enItemCheck.AmountOfMoney //金額
                || vm.workRecordExtractingCondition().checkItem() == enItemCheck.TimeOfDate    //時刻の場合
                || vm.workRecordExtractingCondition().checkItem() == enItemCheck.CountinuousTime   //連続時間
            ) {
                if (erAlAtdItemCondition.compareOperator() > 5
                    || erAlAtdItemCondition.conditionType() == ConditionType.FIXED_VALUE
                ) {
                    comparisonValueRange = new ComparisonValueRange(
                        vm.workRecordExtractingCondition().checkItem,
                        erAlAtdItemCondition.compareOperator,
                        erAlAtdItemCondition.compareStartValue(),
                        erAlAtdItemCondition.compareEndValue());
                } else {
                    comparisonValueRange = new ComparisonValueRange(
                        vm.workRecordExtractingCondition().checkItem,
                        erAlAtdItemCondition.compareOperator,
                        erAlAtdItemCondition.singleAtdItem(),
                        erAlAtdItemCondition.singleAtdItem());
                }
            } else {
                comparisonValueRange = new ComparisonValueRange(
                    vm.workRecordExtractingCondition().checkItem,
                    erAlAtdItemCondition.compareOperator,
                    0,
                    0);
            }
            return comparisonValueRange;
        }

        /**
         * initial screen
         */
        private initialScreen(): JQueryPromise<any> {
            let vm = this,
                dfd = $.Deferred();
            vm.initialDaily().done(() => {
                vm.settingEnableComparisonMaxValueField(true);
                dfd.resolve();
            });
            return dfd.promise();
        }

        // ===========common begin ===================
        private getAllEnums(): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();

            $.when(vm.$ajax(PATH_API.getEnumSingleValueCompareType), vm.$ajax(PATH_API.getEnumTypeCheckWorkRecordMultipleMonth),
                vm.$ajax(PATH_API.getEnumRangeCompareType), vm.$ajax(PATH_API.getEnumTypeCheckWorkRecord),
                vm.$ajax(PATH_API.getEnumTargetSelectionRange), vm.$ajax(PATH_API.getEnumTargetServiceType),
                vm.$ajax(PATH_API.getEnumLogicalOperator))
                .done((listSingleValueCompareTypse: Array<EnumModel>, listTypeCheckWorkRecordMultipleMonth: Array<EnumModel>,
                       lstRangeCompareType: Array<EnumModel>, listTypeCheckWorkRecord: Array<EnumModel>,
                       listTargetSelectionRange: Array<EnumModel>, listTargetServiceType: Array<EnumModel>,
                       listLogicalOperator: Array<EnumModel>) => {
                    vm.listSingleValueCompareTypes(vm.getLocalizedNameForEnum(listSingleValueCompareTypse));
                    //MinhVV add
                    vm.listTypeCheckWorkRecordMultipleMonths(vm.getLocalizedNameForEnum(listTypeCheckWorkRecordMultipleMonth));
                    vm.listRangeCompareTypes(vm.getLocalizedNameForEnum(lstRangeCompareType));
                    vm.listTypeCheckWorkRecords(vm.getLocalizedNameForEnum(listTypeCheckWorkRecord));
                    //remove 3 enum : 4 5 6 as required ( ohashi)
                    _.remove(vm.listTypeCheckWorkRecords(), n => {
                        return (n.value() == 5 || n.value() == 6 || n.value() == 4);
                    });
                    let listTargetRangeWithName = vm.getLocalizedNameForEnum(listTargetSelectionRange);
                    vm.itemListTargetSelectionRange_BA1_5(listTargetRangeWithName);
                    vm.itemListTargetServiceType_BA1_2(vm.getLocalizedNameForEnum(listTargetServiceType));
                    vm.buildListCompareTypes();
                    let listANDOR = vm.getLocalizedNameForEnum(listLogicalOperator)
                    //ENUM 論理演算子
                    vm.swANDOR_B5_3 = ko.observableArray(listANDOR);
                    //ENUM 論理演算子
                    vm.swANDOR_B6_3 = ko.observableArray(listANDOR);
                    //ENUM 論理演算子
                    vm.swANDOR_B7_2 = ko.observableArray(listANDOR);
                    dfd.resolve();

                })
                .always(() => {
                    dfd.resolve();
                });
            return dfd.promise();
        }

        /**
         * build List of Compare Types
         */
        private buildListCompareTypes() {
            const vm = this;
            let listCompareTypes = vm.listSingleValueCompareTypes().concat(vm.listRangeCompareTypes());
            vm.listCompareTypes(listCompareTypes);
        }

        /**
         * get Localize name by Enum
         * @param listEnum
         */
        private getLocalizedNameForEnum(listEnum: Array<EnumModel>): Array<EnumModel> {
            const vm = this;

            if (listEnum) {
                for (let i = 0; i < listEnum.length; i++) {
                    if (listEnum[i].localizedName) {
                        listEnum[i].localizedName = vm.$i18n(listEnum[i].localizedName);
                    }
                }
                return listEnum;
            }
            return [];
        }

        /**
         * Initial Group Condition
         * @param listGroupCondition
         */
        private initGroupCondition(listGroupCondition: Array<sharemodel.ErAlAtdItemCondition>): Array<sharemodel.ErAlAtdItemCondition> {
            let listCondition: Array<sharemodel.ErAlAtdItemCondition> = [];
            let maxRow = 3;
            if (listGroupCondition && listGroupCondition != undefined) {
                for (let i = 0; i < listGroupCondition.length && i < maxRow; i++) {
                    listGroupCondition[i].targetNO(i);
                    listCondition.push(listGroupCondition[i]);
                }
            }
            if (listCondition.length < maxRow) {
                for (let i = listCondition.length; i < maxRow; i++) {
                    listCondition.push(shareutils.getDefaultCondition(i - 1));
                }
            }
            return listCondition;
        }

        /**
         * Initial Compound Group Condition
         */
        private initCompoundGroupCondition(dfd) {
            let vm = this,
                errorAlarmCondition = vm.workRecordExtractingCondition().errorAlarmCondition();
            let compoundCondition = errorAlarmCondition.atdItemCondition();
            if (!compoundCondition || compoundCondition == undefined) {
                compoundCondition = shareutils.getDefaultAttendanceItemCondition();
                errorAlarmCondition.atdItemCondition(compoundCondition);
            }
            let listGr1 = vm.initGroupCondition(compoundCondition.group1().lstErAlAtdItemCon());
            compoundCondition.group1().lstErAlAtdItemCon(listGr1);
            let listGr2 = vm.initGroupCondition(compoundCondition.group2().lstErAlAtdItemCon());
            compoundCondition.group2().lstErAlAtdItemCon(listGr2);
        }

        // ============build enum for combobox BA2-5: end ==============
        // ===========common end =====================
        //==========Daily section Begin====================
        /**
         * Initial Daily
         */
        private initialDaily(): JQueryPromise<any> {
            let vm = this,
                dfd = $.Deferred();
            switch (vm.workRecordExtractingCondition().checkItem()) {
                case enItemCheck.Time:          //時間
                case enItemCheck.Times:         //回数
                case enItemCheck.AmountOfMoney: //金額
                case enItemCheck.TimeOfDate:    //時刻の場合
                    vm.initialDailyItemChkItemComparison(dfd);
                    break;
                case enItemCheck.CountinuousTime:   //連続時間
                    vm.initialDailyItemChkCountinuousTime(dfd);
                    break;
                case enItemCheck.CountinuousWork:   //連続時間帯
                    vm.initialDailyItemChkCountinuousWork(dfd);
                    break;
                case enItemCheck.CountinuousTimeZone: //連続勤務
                    vm.initialDailyItemChkCountinuousTimeZone(dfd);
                    break;
                case enItemCheck.CompoundCondition: //複合条件
                    vm.initCompoundGroupCondition(dfd);
                    break;
                default:
                    break;
            }
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * initial in case check item : Time, Times, Amount of money, Time of day
         */
        private initialDailyItemChkItemComparison(defered) {
            let vm = this,
                workRecordExtractingCondition = vm.workRecordExtractingCondition();
            //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
            service.getDailyItemChkItemComparison(workRecordExtractingCondition.checkItem()).then((itemAttendances: Array<any>) => {
                vm.listAllAttdItem = vm.getListAttendanceIdFromDtos(itemAttendances);

                // build name of Attendance Item
                let currentAtdItemCondition = workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon()[0];
                vm.fillTextDisplayTarget(defered, currentAtdItemCondition);
                /*
               let listAttendanceItemSelectedCode = vm.getListAttendanceItemCode();//勤怠項目の加算減算式
                vm.generateNameCorrespondingToAttendanceItem(listAttendanceItemSelectedCode).done((names) => {
                            vm.displayAttendanceItemSelections_BA2_3(names);
                });
                */
                // initial default data of ErAlAtdItemCon
                //vm.initialDataOfErAlAtdItemCon();
                //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
                vm.initialWorkTypes(defered);
            }, function (rejected) {
                defered.resolve();
            });
        }

        /**
         * Initial in case Daily Item Check Continuous Time
         */
        private initialDailyItemChkCountinuousTime(defered) {
            const vm = this;
            //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
            service.getAttendCoutinousTime().then((itemAttendances) => {
                vm.listAllAttdItem = vm.getListAttendanceIdFromDtos(itemAttendances);

                // build name of Attendance Item
                let currentAtdItemCondition = vm.workRecordExtractingCondition().errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon()[0];
                vm.fillTextDisplayTarget(defered, currentAtdItemCondition);

                /*let listWorkTimeItemSelectedCode = vm.getListAttendanceItemCode();//勤怠項目の加算減算式
                vm.generateNameCorrespondingToAttendanceItem(listWorkTimeItemSelectedCode).done((names) => {
                    vm.displayAttendanceItemSelections_BA2_3(names);
                });
                */
                // initial default data of ErAlAtdItemCon
                //vm.initialDataOfErAlAtdItemCon();
                //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
                vm.initialWorkTypes(defered);
            }, function (rejected) {
                defered.resolve();
            });
        }

        /**
         * Initial in case Daily Item Check Continuous Work
         */
        private initialDailyItemChkCountinuousWork(defered) {
            const vm = this;
            //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
            vm.initialWorkTypes(defered);
        }

        /**
         * Initial in case Daily Item Check Continuous Time zone
         */
        private initialDailyItemChkCountinuousTimeZone(defered) {
            const vm = this;
            //ドメインモデル「就業時間帯の設定」を取得する - Acquire domain model "WorkTimeSetting"
            service.getAttendCoutinousTimeZone().done((settingTimeZones) => {
                vm.listAllWorkingTime = vm.getListWorkTimeCdFromDtos(settingTimeZones);
                //get name
                let listItems = vm.workRecordExtractingCondition().errorAlarmCondition().workTimeCondition().planLstWorkTime();

                vm.generateNameCorrespondingToAttendanceItem(listItems).done((data) => {
                    vm.displayWorkingTimeSelections_BA5_3(data);
                });
                //vm.initialWorkTimeCodesFromDtos(settingTimeZones);
                //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
                vm.initialWorkTypes(defered);
            }, function (rejected) {
                defered.resolve();
            });
        }

        private getListAttendanceItemCode(): Array<any> {
            let vm = this,
                workRecordExtractingCondition = vm.workRecordExtractingCondition();
            let lstErAlAtdItemCon = workRecordExtractingCondition.errorAlarmCondition()
                .atdItemCondition().group1().lstErAlAtdItemCon();
            let listAttendanceItemSelectedCode = lstErAlAtdItemCon[0].countableAddAtdItems() || [];//勤怠項目の加算減算式
            return listAttendanceItemSelectedCode;
        }

        private setListAttendanceItemCode(listWorkTimeItemSelectedCode: Array<any>) {
            let vm = this,
                workRecordExtractingCondition = vm.workRecordExtractingCondition();
            workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1()
                .lstErAlAtdItemCon()[0].countableAddAtdItems(listWorkTimeItemSelectedCode || []);//勤怠項目の加算減算式
        }

        /**
         * アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
         * @param List<itemAttendanceId>
         */
        private generateNameCorrespondingToAttendanceItem(listAttendanceItemCode: Array<any>): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();
            if (listAttendanceItemCode && listAttendanceItemCode.length > 0) {
                service.getAttendNameByIds(listAttendanceItemCode).done((dailyAttendanceItemNames) => {
                    if (dailyAttendanceItemNames && dailyAttendanceItemNames.length > 0) {
                        let attendanceName: string = '';
                        for (let i = 0; i < dailyAttendanceItemNames.length; i++) {
                            if (attendanceName) {
                                attendanceName = attendanceName + "," + dailyAttendanceItemNames[i].attendanceItemName;
                            } else {
                                attendanceName = dailyAttendanceItemNames[i].attendanceItemName;
                            }
                        }
                        dfd.resolve(attendanceName);
                    } else {
                        dfd.resolve('');
                    }
                }).always(() => {
                    dfd.resolve('');
                });
            } else {
                dfd.resolve('');
            }
            return dfd.promise();
        }

        /**
         * Build list of Attendance Item Name from List<AttdItemDto>
         * @param listAttendanceItemCode
         */
        private buildItemName(listItem: Array<any>): string {
            let vm = this, retNames: string = '';
            if (listItem) {
                for (let i = 0; i < listItem.length; i++) {
                    if (retNames) {
                        retNames = retNames + "," + listItem[i].name;
                    } else {
                        retNames = listItem[i].name;
                    }
                }
            }
            return retNames;
        }

        /**
         * //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
         *
         */
        private initialWorkTypes(defered): void {
            let vm = this,
                workTypeCondition = vm.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition();
            vm.listAllWorkType = [];
            // get all Work type
            service.getAttendCoutinousWork().then((workTypes) => {
                if (workTypes && workTypes != undefined) {
                    for (let i = 0; i < workTypes.length; i++) {
                        vm.listAllWorkType.push(workTypes[i].workTypeCode);
                    }
                }

                // get Name of selected work type.
                let wkTypeSelected = workTypeCondition.planLstWorkType();
                if (wkTypeSelected && wkTypeSelected.length > 0) {
                    service.findWorkTypeByCodes(wkTypeSelected).then((listWrkTypes) => {
                        let names: string = vm.buildItemName(listWrkTypes);
                        vm.displayWorkTypeSelections_BA1_4(names);
                    });
                } else {
                    vm.displayWorkTypeSelections_BA1_4("");
                }
            }, function (rejected) {
                defered.resolve();
            });
        }

        //initial default data of ErAlAtdItemCon
        private initialDataOfErAlAtdItemCon() {
            let vm = this, workRecordExtractingCondition = vm.workRecordExtractingCondition();
            let checkItem = workRecordExtractingCondition.checkItem();
            if (!(checkItem == enItemCheck.Time
                    || checkItem == enItemCheck.CountinuousTime
                    || checkItem == enItemCheck.Times
                    || checkItem == enItemCheck.AmountOfMoney
                    || checkItem == enItemCheck.TimeOfDate)) {
                return;
            }
            let conditionAtr = 0;
            let lstErAlAtdItemCon1 = workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon();
            switch (checkItem) {
                case enItemCheck.Time:          //時間
                case enItemCheck.CountinuousTime:   //連続時間
                    conditionAtr = 1;
                    break;
                case enItemCheck.Times:         //回数
                    conditionAtr = 0;
                    break;
                case enItemCheck.AmountOfMoney: //金額
                    conditionAtr = 3;
                    break;
                case enItemCheck.TimeOfDate:    //時刻の場合
                    conditionAtr = 2;
                    break;
                default:
                    return;
            }

            for (let i = 0; i < lstErAlAtdItemCon1.length; i++) {
                lstErAlAtdItemCon1[i].conditionAtr(conditionAtr);
                lstErAlAtdItemCon1[i].conditionType(ConditionType.FIXED_VALUE); //1: 勤怠項目 - AttendanceItem, 0: fix
            }
            let lstErAlAtdItemCon2 = workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group2().lstErAlAtdItemCon();
            for (let i = 0; i < lstErAlAtdItemCon2.length; i++) {
                lstErAlAtdItemCon2[i].conditionAtr(conditionAtr);
                lstErAlAtdItemCon2[i].conditionType(ConditionType.FIXED_VALUE); //1: 勤怠項目 - AttendanceItem, 0: fix
            }
        }

        /**
         * Get list of attendance id from list Dtos
         * @param itemAttendances
         */
        private getListAttendanceIdFromDtos(itemAttendances: Array<any>): Array<string> {
            let listAllAttdItemCode: Array<string> = [];
            if (itemAttendances && itemAttendances != undefined) {
                for (let i = 0; i < itemAttendances.length; i++) {
                    listAllAttdItemCode.push(itemAttendances[i].attendanceItemId);
                }
            }
            return listAllAttdItemCode;
        }

        /**
         * Get list of work time id from list Dtos
         * @param itemAttendances
         */
        private getListWorkTimeCdFromDtos(workTimes: Array<any>): Array<string> {
            let listWorkTimesCode: Array<string> = [];
            if (workTimes && workTimes != undefined) {
                for (let i = 0; i < workTimes.length; i++) {
                    listWorkTimesCode.push(workTimes[i].worktimeCode);
                }
            }
            return listWorkTimesCode;
        }

        //==========Daily session End====================

        /**
         * Get list code from list codes that return from selected dialog
         * @param listKdl002Model
         */
        private getListCode(listKdl002Model: Array<ItemModelKdl002>): Array<string> {
            let retListCode: Array<string> = [];
            if (listKdl002Model == null || listKdl002Model == undefined) {
                return retListCode;
            }
            for (let i = 0; i < listKdl002Model.length; i++) {
                retListCode.push(listKdl002Model[i].code);
            }
            return retListCode;
        }

        /**
         * open dialog for select working type
         */
        btnSettingBA1_3_click() {
            let vm = this;
            let workTypeCondition = vm.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition();

            vm.$blockui("invisible");
            let lstSelectedCode = workTypeCondition.planLstWorkType();
            setShared("KDL002_Multiple", true);
            //all possible items
            setShared("KDL002_AllItemObj", vm.listAllWorkType);
            //selected items
            setShared("KDL002_SelectedItemId", lstSelectedCode);
            vm.$window.modal("/view/kdl/002/a/index.xhtml", {title: "乖離時間の登録＞対象項目", dialogClass: "no-close"})
                .then(() => {
                    $(".nts-input").ntsError("clear");
                    //get data from share window
                    let listItems: Array<any> = getShared("KDL002_SelectedNewItem");
                    if (listItems != null && listItems != undefined) {
                        let listCodes: Array<string> = vm.getListCode(listItems);
                        workTypeCondition.planLstWorkType(listCodes);
                        // get name
                        let names: string = vm.buildItemName(listItems);
                        vm.displayWorkTypeSelections_BA1_4(names);

                    }
                    vm.$blockui("clear");
                });
        }

        /**
         * open dialog for select working time zone (KDL002)
         */
        btnSettingBA5_2_click() {
            let vm = this,
                workTimeCondition = vm.workRecordExtractingCondition().errorAlarmCondition().workTimeCondition();

            vm.$blockui("invisible");
            let lstSelectedCode = workTimeCondition.planLstWorkTime();
            setShared("kml001multiSelectMode", true);
            //all possible items
            setShared("kml001selectAbleCodeList", vm.listAllWorkingTime);
            //selected items
            setShared("kml001selectedCodeList", lstSelectedCode);
            vm.$window.modal("/view/kdl/001/a/index.xhtml", {title: "割増項目の設定", dialogClass: "no-close"}).then(() => {
                $(".nts-input").ntsError("clear");
                //get data from share window
                let listItems: Array<any> = getShared("kml001selectedCodeList");
                if (listItems != null && listItems != undefined) {
                    workTimeCondition.planLstWorkTime(listItems);
                    //get name
                    vm.generateNameCorrespondingToAttendanceItem(listItems).done((data) => {
                        vm.displayWorkingTimeSelections_BA5_3(data);
                    });
                }
                vm.$blockui("clear");
            });
        }


        //openSelectAtdItemDialogTarget() {
        btnSettingBA2_2_click() {
            const vm = this;
            let dfd = $.Deferred();
            switch (vm.category()) {
                case sharemodel.CATEGORY.DAILY: {
                    let currentAtdItemCondition = vm.workRecordExtractingCondition().errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon()[0];
                    if (vm.workRecordExtractingCondition().checkItem() == 3) {
                        vm.getListItemByAtr(6).done((lstItem) => {
                            let lstItemCode = lstItem.map((item) => {
                                return item.attendanceItemId;
                            });
                            //Open dialog KDL021
                            setShared('Multiple', false);
                            setShared('AllAttendanceObj', lstItemCode);
                            setShared('SelectedAttendanceId', [currentAtdItemCondition.uncountableAtdItem()]);
                            vm.$window.modal("at", "/view/kdl/021/a/index.xhtml").then(() => {
                                $(".nts-input").ntsError("clear");
                                let output = getShared("selectedChildAttendace");
                                if (output) {
                                    currentAtdItemCondition.uncountableAtdItem(parseInt(output));
                                    vm.fillTextDisplayTarget(dfd, currentAtdItemCondition);
                                } else if (output === "") {
                                    currentAtdItemCondition.uncountableAtdItem(0);
                                    vm.displayAttendanceItemSelections_BA2_3("");
                                }
                            });

                        });
                    } else {
                        vm.getListItemByAtrDaily(vm.workRecordExtractingCondition().checkItem(), 0).done((lstItem) => {
                            let lstItemCode = lstItem.map((item) => {
                                return item.attendanceItemId;
                            });
                            //Open dialog KDW007C
                            let param = {
                                //                                attr: 1,
                                lstAllItems: lstItemCode,
                                lstAddItems: currentAtdItemCondition.countableAddAtdItems(),
                                lstSubItems: currentAtdItemCondition.countableSubAtdItems()
                            };
                            setShared("KDW007Params", param);
                            vm.$window.modal("at", "/view/kdw/007/c/index.xhtml").then(() => {
                                $(".nts-input").ntsError("clear");
                                let output = getShared("KDW007CResults");
                                if (output) {
                                    currentAtdItemCondition.countableAddAtdItems(output.lstAddItems.map((item) => {
                                        return parseInt(item);
                                    }));
                                    currentAtdItemCondition.countableSubAtdItems(output.lstSubItems.map((item) => {
                                        return parseInt(item);
                                    }));
                                    vm.fillTextDisplayTarget(dfd, currentAtdItemCondition);
                                }
                            });
                        });

                    }
                    break;
                }
                case sharemodel.CATEGORY.MONTHLY: {

                    let currentAtdItemConMon = vm.extraResultMonthly().currentConditions()[0].group1().lstErAlAtdItemCon()[0];
                    vm.getListItemByAtrDailyAndMonthly(vm.extraResultMonthly().typeCheckItem(), 1).done((lstItem) => {
                        let lstItemCode = lstItem.map((item) => {
                            return item.attendanceItemId;
                        });
                        //Open dialog KDW007C
                        let param = {
                            attr: 1,
                            lstAllItems: lstItemCode,
                            lstAddItems: currentAtdItemConMon.countableAddAtdItems(),
                            lstSubItems: currentAtdItemConMon.countableSubAtdItems()
                        };

                        //                        if ((vm.checkItemTemp() || vm.checkItemTemp() == 0) && vm.checkItemTemp() != vm.workRecordExtractingCondition().checkItem()) {
                        //                            param.lstAddItems = [];
                        //              ems = [];
                        //                        }

                        setShared("KDW007Params", param);
                        vm.$window.modal("at", "/view/kdw/007/c/index.xhtml").then(() => {
                            $(".nts-input").ntsError("clear");
                            let output = getShared("KDW007CResults");
                            if (output) {
                                currentAtdItemConMon.countableAddAtdItems(output.lstAddItems.map((item) => {
                                    return parseInt(item);
                                }));
                                currentAtdItemConMon.countableSubAtdItems(output.lstSubItems.map((item) => {
                                    return parseInt(item);
                                }));
                                //vm.fillTextDisplayTarget(dfd, currentAtdItemCondition);
                            }
                        });

                    });
                    break;
                }
                case sharemodel.CATEGORY.MULTIPLE_MONTHS: {
                    let attdAtr = CONDITIONATR.TIMES;
                    let mulMonCheckItem = vm.mulMonCheckCondSet().typeCheckItem();
                    vm.getListItemByAtrMultipleMonth(mulMonCheckItem, 1).done((lstItem) => {
                        let lstItemCode = lstItem.map((item) => {
                            return item.attendanceItemId;
                        });
                        //Open dialog KDW007C
                        let param = {
                            attr: 1,
                            lstAllItems: lstItemCode,
                            lstAddItems: vm.mulMonCheckCondSet().erAlAtdItem().countableAddAtdItems(),
                            lstSubItems: vm.mulMonCheckCondSet().erAlAtdItem().countableSubAtdItems()
                        };
                        setShared("KDW007Params", param);
                        vm.$window.modal("at", "/view/kdw/007/c/index.xhtml").then(() => {
                            $(".nts-input").ntsError("clear");
                            let output = getShared("KDW007CResults");
                            if (output) {
                                vm.mulMonCheckCondSet().erAlAtdItem().countableAddAtdItems(output.lstAddItems.map((item) => {
                                    return parseInt(item);
                                }));
                                vm.mulMonCheckCondSet().erAlAtdItem().countableSubAtdItems(output.lstSubItems.map((item) => {
                                    return parseInt(item);
                                }));
                                vm.fillTextDisplayTargetMulMon(dfd, vm.mulMonCheckCondSet().erAlAtdItem());
                            }
                        });

                    });
                    break;
                }
                default:
                    break;
            }

        }

        getListItemByAtr(conditionAtr) {
            const vm = this;
            return service.getAttendanceItemByAtr(conditionAtr);
        }

        //GET ALL DAILY
        getListItemByAtrDaily(typeCheck: number, mode: number) {
            const vm = this;
            let dfd = $.Deferred();
            if (typeCheck == 1) {
                //With type 回数 - Times , Number  = 2
                service.getAttendanceItemByAtrNew(DAILYATTENDANCEITEMATR.NumberOfTime, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (typeCheck == 0) {
                //With type 時間 - Time
                service.getAttendanceItemByAtrNew(DAILYATTENDANCEITEMATR.Time, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (typeCheck == 2) {
                //With type 金額 - AmountMoney
                service.getAttendanceItemByAtrNew(DAILYATTENDANCEITEMATR.AmountOfMoney, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else {
                dfd.resolve([]);
            }
            return dfd.promise();
        }


        getListItemByAtrMultipleMonth(mulMonCheckItem: number, mode: number) {
            const vm = this;
            let dfd = $.Deferred();
            if ((mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIME
                    || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIME
                    || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIME
                    || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIME)) {
                //時間
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.TIME, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIMES
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIMES
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIMES
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIMES) {
                //回数
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.NUMBER, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AMOUNT
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_AMOUNT
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_AMOUNT
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_AMOUNT) {
                //金額
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.AMOUNT, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.DAYS
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_DAYS
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_DAYS
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_DAYS) {
                //回数
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.DAYS, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else {
                dfd.resolve([]);
            }
            return dfd.promise();
        }

        //GET ALL MONTHLY
        getListItemByAtrDailyAndMonthly(typeCheck: number, mode: number) {
            const vm = this;
            let dfd = $.Deferred();
            if (typeCheck == 6) { //combobox select
                //With type 回数 - Times , Number  = 2
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.NUMBER, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (typeCheck == 4) {
                //With type 時間 - Time
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.TIME, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (typeCheck == 7) {
                //With type 金額 - AmountMoney
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.AMOUNT, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (typeCheck == 5) { // 日数
                service.getAttendanceItemByAtrNew(MONTHLYATTENDANCEITEMATR.DAYS, mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else {
                dfd.resolve([]);
            }
            return dfd.promise();
        }

        //monthly
        getAttdItemMonByAtr(atr) {
            const vm = this;
            return service.getAttdItemMonByAtr(atr);
        }

        //Update ticket #100187
        //        getSpecialholidayframe(): JQueryPromise<any> {
        //            let vm = this,
        //                dfd = $.Deferred();
        //            service.getSpecialholidayframe().done(function(data) {
        //                vm.listSpecialholidayframe = data;
        //                dfd.resolve();
        //                 //            return dfd.promise();
        //        }

        getSpecialHoliday(): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();
            service.getSpecialHoliday().done(function (data) {
                let holidayCode;
                _.map(vm.extraResultMonthly().conditions(), (d) => {
                    if (d.haveComboboxFrame()) {
                        if (!nts.uk.util.isNullOrUndefined(d.listItemID()) && d.listItemID() > 0) {
                            holidayCode = d.listItemID()[0];
                        }
                    }
                });
                if (!nts.uk.util.isNullOrUndefined(holidayCode)) {
                    let newdata = [];
                    let haveInList = false;
                    let index = 0;
                    if (!nts.uk.util.isNullOrUndefined(data) && data.length > 0) {
                        let length = data.length;
                        _.map(data, (d) => {
                            if (index == 0 && holidayCode < d.specialHolidayCode) {
                                newdata.push({
                                    specialHolidayCode: holidayCode,
                                    specialHolidayName: vm.$i18n('KAL003_120')
                                });
                                newdata.push(d);
                            } else if ((holidayCode > d.specialHolidayCode && index == length - 1)
                                || (holidayCode > d.specialHolidayCode && index < length - 1 && holidayCode < data[index + 1].specialHolidayCode)) {
                                newdata.push(d);
                                newdata.push({
                                    specialHolidayCode: holidayCode,
                                    specialHolidayName: vm.$i18n('KAL003_120')
                                });
                            } else {
                                newdata.push(d);
                            }

                            if (d.specialHolidayCode === holidayCode) {
                                haveInList = true;
                            }
                            index++;
                        });
                    } else {
                        newdata.push({
                            specialHolidayCode: holidayCode,
                            specialHolidayName: vm.$i18n('KAL003_120')
                        });
                    }

                    if (!haveInList) {
                        vm.listSpecialholidayframe = newdata;
                    } else {
                        vm.listSpecialholidayframe = data;
                    }
                } else {
                    vm.listSpecialholidayframe = data;
                }

                dfd.resolve();
            });
            return dfd.promise();
        }

        //End update ticket #100187

        fillTextDisplayTarget(defered, currentAtdItemCondition) {

            const vm = this;
            vm.displayAttendanceItemSelections_BA2_3("");
            if (vm.workRecordExtractingCondition().checkItem() === 3) {
                if (currentAtdItemCondition.uncountableAtdItem()) {
                    service.getAttendanceItemByCodes([currentAtdItemCondition.uncountableAtdItem()]).then((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            vm.displayAttendanceItemSelections_BA2_3(lstItems[0].attendanceItemName);
                            $("#display-target-item").trigger("validate");
                        }
                    }, function (rejected) {
                        defered.resolve();
                    });
                }
            } else {
                if (currentAtdItemCondition.countableAddAtdItems().length > 0) {
                    service.getAttendanceItemByCodes(currentAtdItemCondition.countableAddAtdItems()).then((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            for (let i = 0; i < lstItems.length; i++) {
                                let operator = (i === (lstItems.length - 1)) ? "" : " + ";
                                vm.displayAttendanceItemSelections_BA2_3(vm.displayAttendanceItemSelections_BA2_3() + lstItems[i].attendanceItemName + operator);
                            }
                            $("#display-target-item").trigger("validate");
                        }

                        if (currentAtdItemCondition.countableSubAtdItems().length > 0) {
                            service.getAttendanceItemByCodes(currentAtdItemCondition.countableSubAtdItems()).then((lstItems) => {
                                if (lstItems && lstItems.length > 0) {
                                    for (let i = 0; i < lstItems.length; i++) {
                                        let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                                        let beforeOperator = (i === 0) ? " - " : "";
                                        vm.displayAttendanceItemSelections_BA2_3(vm.displayAttendanceItemSelections_BA2_3() + beforeOperator + lstItems[i].attendanceItemName + operator);
                                    }
                                    $("#display-target-item").trigger("validate");
                                }
                            });
                        }
                    }, function (rejected) {
                        defered.resolve();
                    });
                } else if (currentAtdItemCondition.countableSubAtdItems().length > 0) {
                    service.getAttendanceItemByCodes(currentAtdItemCondition.countableSubAtdItems()).then((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            for (let i = 0; i < lstItems.length; i++) {
                                let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                                let beforeOperator = (i === 0) ? " - " : "";
                                vm.displayAttendanceItemSelections_BA2_3(vm.displayAttendanceItemSelections_BA2_3() + beforeOperator + lstItems[i].attendanceItemName + operator);
                            }
                            $("#display-target-item").trigger("validate");
                        }
                    }, function (rejected) {
                        defered.resolve();
                    });
                }

            }

            return defered.promise();
        }

        //MinhVV start
        fillTextDisplayTargetMulMon(defered, currentAtdItemCondition) {
            const vm = this;
            vm.displayAttendanceItemSelections_BA2_3("");
            if (currentAtdItemCondition.countableAddAtdItems().length > 0) {
                service.getMonthlyAttendanceItemByCodes(currentAtdItemCondition.countableAddAtdItems()).then((lstItems) => {
                    if (lstItems && lstItems.length > 0) {
                        for (let i = 0; i < lstItems.length; i++) {
                            let operator = (i === (lstItems.length - 1)) ? "" : " + ";
                            vm.displayAttendanceItemSelections_BA2_3(vm.displayAttendanceItemSelections_BA2_3() + lstItems[i].attendanceItemName + operator);
                        }
                        $("#display-target-item-category9").trigger("validate");
                    }
                    if (currentAtdItemCondition.countableSubAtdItems().length > 0) {
                        service.getMonthlyAttendanceItemByCodes(currentAtdItemCondition.countableSubAtdItems()).then((lstItems) => {
                            if (lstItems && lstItems.length > 0) {
                                for (let i = 0; i < lstItems.length; i++) {
                                    let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                                    let beforeOperator = (i === 0) ? " - " : "";
                                    vm.displayAttendanceItemSelections_BA2_3(vm.displayAttendanceItemSelections_BA2_3() + beforeOperator + lstItems[i].attendanceItemName + operator);
                                }
                                $("#display-target-item-category9").trigger("validate");
                            }
                        });
                    }
                }, function (rejected) {
                    defered.resolve();
                });
            } else if (currentAtdItemCondition.countableSubAtdItems().length > 0) {
                service.getMonthlyAttendanceItemByCodes(currentAtdItemCondition.countableSubAtdItems()).then((lstItems) => {
                    if (lstItems && lstItems.length > 0) {
                        for (let i = 0; i < lstItems.length; i++) {
                            let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                            let beforeOperator = (i === 0) ? " - " : "";
                            vm.displayAttendanceItemSelections_BA2_3(vm.displayAttendanceItemSelections_BA2_3() + beforeOperator + lstItems[i].attendanceItemName + operator);
                        }
                        $("#display-target-item-category9").trigger("validate");
                    }
                }, function (rejected) {
                    defered.resolve();
                });
            }

            //            }
            return defered.promise();
        }

        private initialMultiMonthScreen(): JQueryPromise<any> {
            let vm = this,
                dfd = $.Deferred();
            vm.initialMulMon().done(() => {
                vm.settingEnableComparisonMaxValueFieldExtra();
                dfd.resolve();
            });

            dfd.resolve();
            return dfd.promise();
        }

        private initialMulMonItemChkItemComparison(defered) {
            const vm = this;
            vm.fillTextDisplayTargetMulMon(defered, vm.mulMonCheckCondSet().erAlAtdItem());
        }

        //MinhVV
        private initialMulMon(): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();
            vm.initialMulMonItemChkItemComparison(dfd);
            dfd.resolve();
            return dfd.promise();
        }

        //MinhVV initial default data of ErAlAtdItemConMultipleMonth
        private initialDataOfErAlAtdItemConMultipleMonth() {
            let vm = this, mulMonCheckCond = vm.mulMonCheckCondSet();
            let checkItem = mulMonCheckCond.typeCheckItem();
            if (!(checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIME
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIMES
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AMOUNT
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIME
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIMES
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_AMOUNT
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIME
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIMES
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_AMOUNT
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIME
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIMES
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_AMOUNT
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.DAYS
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_DAYS
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_DAYS
                    || checkItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_DAYS)) {
                return;
            }
            let conditionAtr = CONDITIONATR.TIMES;
            switch (checkItem) {
                case TYPECHECKWORKRECORDMULTIPLEMONTH.TIME:          //時間
                case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIME:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIME:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIME: {
                    conditionAtr = CONDITIONATR.TIME;
                    break;
                }
                case TYPECHECKWORKRECORDMULTIPLEMONTH.TIMES:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIMES:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIMES:      //回数
                case TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIMES: {
                    conditionAtr = CONDITIONATR.TIMES;
                    break;
                }
                case TYPECHECKWORKRECORDMULTIPLEMONTH.AMOUNT: //金額
                case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_AMOUNT:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_AMOUNT:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_AMOUNT: {
                    conditionAtr = CONDITIONATR.AMOUNT;
                    break;
                }
                case TYPECHECKWORKRECORDMULTIPLEMONTH.DAYS:                 //日数
                case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_DAYS:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_DAYS:
                case TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_DAYS: {
                    conditionAtr = CONDITIONATR.DAYS;
                    break;
                }
                default:
                    return;
            }

            vm.mulMonCheckCondSet().erAlAtdItem().conditionAtr(conditionAtr);
            vm.mulMonCheckCondSet().erAlAtdItem().conditionType(ConditionType.FIXED_VALUE);

        }

        private initComparisonValueRangeMulMon(): ComparisonValueRange {
            const vm = this;
            let erAlAtdItemCondition = vm.mulMonCheckCondSet().erAlAtdItem();

            let comparisonValueRange;
            let mulMonCheckItem = vm.mulMonCheckCondSet().typeCheckItem();
            if (mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIME
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.TIMES
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AMOUNT
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIME
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIMES
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_AMOUNT
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIME
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_TIMES
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_AMOUNT
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIME
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_TIMES
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_AMOUNT
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.DAYS
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.CONTINUOUS_DAYS
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_DAYS
                || mulMonCheckItem == TYPECHECKWORKRECORDMULTIPLEMONTH.NUMBER_DAYS) {
                if (erAlAtdItemCondition.compareOperator() > COMPARETYPE.LESS_OR_EQUAL
                    || erAlAtdItemCondition.conditionType() == ConditionType.FIXED_VALUE) {
                    comparisonValueRange = new ComparisonValueRange(
                        vm.mulMonCheckCondSet().typeCheckItem,
                        erAlAtdItemCondition.compareOperator,
                        erAlAtdItemCondition.compareStartValue(),
                        erAlAtdItemCondition.compareEndValue());
                } else {
                    comparisonValueRange = new ComparisonValueRange(
                        vm.mulMonCheckCondSet().typeCheckItem,
                        erAlAtdItemCondition.compareOperator,
                        erAlAtdItemCondition.singleAtdItem(),
                        erAlAtdItemCondition.singleAtdItem());
                }
            } else {
                comparisonValueRange = new ComparisonValueRange(
                    vm.mulMonCheckCondSet().typeCheckItem,
                    erAlAtdItemCondition.compareOperator,
                    0,
                    0);
            }
            return comparisonValueRange;
        }
    }

    export enum CONDITIONATR {
        TIMES = 0,
        TIME = 1,
        TIME_WITH_DAY = 2,
        AMOUNT = 3,
        DAYS = 4
    }

    export enum MONTHLYATTENDANCEITEMATR {
        TIME = 1,
        /* 回数 */
        NUMBER = 2,
        /* 日数 */
        DAYS = 3,
        /* 金額 */
        AMOUNT = 4,
        /* マスタを参照する */
        REFER_TO_MASTER = 5
    }

    enum DAILYATTENDANCEITEMATR {
        /* コード */
        Code = 0,
        /* マスタを参照する */
        ReferToMaster = 1,
        /* 回数*/
        NumberOfTime = 2,
        /* 金額*/
        AmountOfMoney = 3,
        /* 区分 */
        Classification = 4,
        /* 時間 */
        Time = 5,
        /* 時刻*/
        TimeOfDay = 6,
        /* 文字 */
        Character = 7
    }

    export enum COMPARETYPE {
        LESS_OR_EQUAL = 5 //(5, "Enum_SingleValueCompareType_LessOrEqual");
    }

    export enum TYPECHECKWORKRECORDMULTIPLEMONTH {
        TIME = 0,
        TIMES = 1,
        AMOUNT = 2,
        AVERAGE_TIME = 3,
        AVERAGE_TIMES = 4,
        AVERAGE_AMOUNT = 5,
        CONTINUOUS_TIME = 6,
        CONTINUOUS_TIMES = 7,
        CONTINUOUS_AMOUNT = 8,
        NUMBER_TIME = 9,
        NUMBER_TIMES = 10,
        NUMBER_AMOUNT = 11,
        DAYS = 12,
        AVERAGE_DAYS = 13,
        CONTINUOUS_DAYS = 14,
        NUMBER_DAYS = 15
    }

    /**
     * The enum of ROLE TYPE
     */
    export enum enCategory {
        Daily = 0,
        Weekly = 1,
        Monthly = 2
    }

    export enum enItemCheck {
        Time = 0, // 時間
        Times = 1, // 回数
        AmountOfMoney = 2, // 金額
        TimeOfDate = 3, // 時刻
        CountinuousTime = 4, // 連続時間
        CountinuousWork = 5, // 連続勤務
        CountinuousTimeZone = 6, // 連続時間帯
        CompoundCondition = 7// 複合条件
    }

    export enum ConditionType {
        /* 固定値 */
        FIXED_VALUE = 0, //, "Enum_ConditionType_FixedValue"),
        /* 勤怠項目 */
        ATTENDANCE_ITEM = 1 //, "Enum_ConditionType_AttendanceItem");
    }

    export class ItemModelKdl002 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export interface IEnumModel {
        value: number;
        fieldName: string;
        localizedName: string;
    }

    export class EnumModel {
        value: KnockoutObservable<number>;
        fieldName: string;
        localizedName: string;

        constructor(param: IEnumModel) {
            const vm = this;
            vm.value = ko.observable(param.value || -1);
            vm.fieldName = param.fieldName || '';
            vm.localizedName = param.localizedName || '';
        }
    }

    /**
     * ComparisonValueRange
     */
    export class ComparisonValueRange extends ko.ViewModel {
        minTimeValue: KnockoutObservable<number> = ko.observable(0);
        maxTimeValue: KnockoutObservable<number> = ko.observable(0);

        minTimesValue: KnockoutObservable<number> = ko.observable(0);
        maxTimesValue: KnockoutObservable<number> = ko.observable(0);

        minAmountOfMoneyValue: KnockoutObservable<number> = ko.observable(null);
        maxAmountOfMoneyValue: KnockoutObservable<number> = ko.observable(null);

        minTimeWithinDayValue: KnockoutObservable<number> = ko.observable(0);
        maxTimeWithinDayValue: KnockoutObservable<number> = ko.observable(0);
        minValue: KnockoutObservable<number> = ko.observable(0);
        maxValue: KnockoutObservable<number> = ko.observable(0);

        checkItem: KnockoutObservable<number> = ko.observable(0);
        comparisonOperator: KnockoutObservable<number> = ko.observable(0);

        isChecking: boolean = false;
        minTimesValueDay: KnockoutObservable<number> = ko.observable(0);
        maxTimesValueDay: KnockoutObservable<number> = ko.observable(0);

        constructor(checkItem: KnockoutObservable<number>, comOper: KnockoutObservable<number>, minVal: number, maxVal: number) {
            super();
            const vm = this;

            minVal = (typeof minVal === "string") ? parseInt(minVal) : minVal;
            maxVal = (typeof maxVal === "string") ? parseInt(maxVal) : maxVal;
            vm.minValue(minVal || 0);
            vm.maxValue(maxVal || 0);
            vm.checkItem = checkItem;
            vm.comparisonOperator = comOper;
            //時間 - 0: check time
            //連続時間 - 4:  check time
            vm.minTimeValue(minVal);
            vm.maxTimeValue(maxVal);
            //回数 - 1: check times
            vm.minTimesValue(minVal);
            vm.maxTimesValue(maxVal);
            //金額 - 2: check amount of money
            vm.minAmountOfMoneyValue(minVal);
            vm.maxAmountOfMoneyValue(maxVal);
            //時刻の場合 - 3: time within day
            vm.minTimeWithinDayValue(minVal);
            vm.maxTimeWithinDayValue(maxVal);

            //時間 - 0: check time
            //連続時間 - 4:  check time
            vm.minTimeValue.subscribe((value) => {
                vm.settingMinValue(value);
            });
            vm.maxTimeValue.subscribe((value) => {
                vm.settingMaxValue(value);
            });

            //回数 - 1: check times
            vm.minTimesValue.subscribe((value) => {
                vm.settingMinValue(value);
            });
            vm.maxTimesValue.subscribe((value) => {
                vm.settingMaxValue(value);
            });

            //金額 - 2: check amount of money
            vm.minAmountOfMoneyValue.subscribe((value) => {
                vm.settingMinValue(value);
            });
            vm.maxAmountOfMoneyValue.subscribe((value) => {
                vm.settingMaxValue(value);
            });

            //時刻の場合 - 3: time within day
            vm.minTimeWithinDayValue.subscribe((value) => {
                vm.settingMinValue(value);
            });
            vm.maxTimeWithinDayValue.subscribe((value) => {
                vm.settingMaxValue(value);
            });
            //日数 Times Value Day
            vm.minTimesValueDay(minVal);
            vm.maxTimesValueDay(maxVal);
            vm.minTimesValueDay.subscribe((value) => {
                vm.settingMinValue(value);
            });
            vm.maxTimesValueDay.subscribe((value) => {
                vm.settingMaxValue(value);
            });
        }

        private settingMinValue(val: number) {
            const vm = this;
            if (vm.minValue() == val) {
                return;
            }

            $("#startValue").ntsError("clear", 'Msg_927');
            $("#endValue").ntsError("clear", 'Msg_927');
            vm.minValue(val);

            if (vm.category == 9) {
                vm.checkValidOfRangeCategory9(vm.checkItem(), 0); //min
            } else {
                vm.checkValidOfRange(vm.checkItem(), 0); //min
            }
        }

        private settingMaxValue(val: number) {
            const vm = this;
            if (vm.maxValue() == val) {
                return;
            }

            $("#startValue").ntsError("clear", 'Msg_927');
            $("#endValue").ntsError("clear", 'Msg_927');
            vm.maxValue(val);

            if (vm.category == 9) {
                vm.checkValidOfRangeCategory9(vm.checkItem(), 1); //min
            } else {
                vm.checkValidOfRange(vm.checkItem(), 1);//max
            }

        }

        /**
         * valid range of comparison
         */
        checkValidOfRange(checkItem: number, textBoxFocus: number): boolean {
            const vm = this;
            let isValid: boolean = true;


            if (vm.comparisonOperator() > 5) {
                let mnValue: number = undefined;
                let mxValue: number = undefined;
                switch (checkItem) {
                    case enItemCheck.Time:          //時間 - 0: check time
                    case enItemCheck.CountinuousTime:   //連続時間 - 4:  check time
                        mnValue = vm.minTimeValue();
                        mxValue = vm.maxTimeValue();
                        break;
                    case enItemCheck.Times:       //回数 - 1: check times
                        mnValue = vm.minTimesValue();
                        mxValue = vm.maxTimesValue();
                        break;
                    case enItemCheck.AmountOfMoney: //金額 - 2: check amount of money
                        mnValue = vm.minAmountOfMoneyValue();
                        mxValue = vm.maxAmountOfMoneyValue();
                        break;
                    case enItemCheck.TimeOfDate:   //時刻の場合 - 3: time within day
                        mnValue = vm.minTimeWithinDayValue();
                        mxValue = vm.maxTimeWithinDayValue();
                        break;
                    default:
                        break;
                }

                if (mnValue != undefined && mxValue != undefined) {
                    isValid = vm.compareValid(vm.comparisonOperator(), mnValue, mxValue);
                }
            }
            if (!isValid) {
                if (textBoxFocus === 1) {
                    vm.$errors({"#endValue": {messageId: "Msg_927"}});
                } else {
                    vm.$errors({"#startValue": {messageId: "Msg_927"}});
                }
            }

            return isValid;
        }

        checkValidOfRangeCategory9(checkItem: number, textBoxFocus: number): boolean {
            const vm = this;
            let isValid: boolean = true;

            if (vm.comparisonOperator() > 5) {
                let mnValue: number = undefined;
                let mxValue: number = undefined;
                switch (checkItem) {
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.TIME:
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIME:          //時間 - 0: check time
                        mnValue = vm.minTimeValue();
                        mxValue = vm.maxTimeValue();
                        break;
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.TIMES:       //回数 - 1: check times
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_TIMES:
                        mnValue = vm.minTimesValue();
                        mxValue = vm.maxTimesValue();
                        break;
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.AMOUNT: //金額 - 2: check amount of money
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_AMOUNT:
                        mnValue = vm.minAmountOfMoneyValue();
                        mxValue = vm.maxAmountOfMoneyValue();
                        break;
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.DAYS:        //日数
                    case TYPECHECKWORKRECORDMULTIPLEMONTH.AVERAGE_DAYS:
                        mnValue = vm.minTimesValueDay();
                        mxValue = vm.maxTimesValueDay();
                        break;
                    default:
                        break;
                }

                if (mnValue != undefined && mxValue != undefined) {
                    isValid = vm.compareValid(vm.comparisonOperator(), mnValue, mxValue);
                }
            }
            if (!isValid) {
                if (textBoxFocus === 1) {
                    vm.$errors({"#endValue": {messageId: "Msg_927"}});
                } else {
                    vm.$errors({"#startValue": {messageId: "Msg_927"}});
                }
            }

            return isValid;
        }

        /**
         * execute check valid of range
         */
        private compareValid(comOper: number, minValue: number, maxValue: number): boolean {
            let rs = true;
            switch (comOper) {
                case 6: // 範囲の間（境界値を含まない）（＜＞）
                case 8: {// 範囲の外（境界値を含まない）（＞＜）
                    rs = Number(minValue) < Number(maxValue);
                    break;
                }
                case 7: // 範囲の間（境界値を含む）（≦≧）
                case 9: {// 範囲の外（境界値を含む）（≧≦）
                    rs = Number(minValue) <= Number(maxValue);
                    break;
                }
                default:
                    break;
            }
            return rs;
        }
    }
}