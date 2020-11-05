/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.b {
    import common = nts.uk.at.kaf021.common;
    import formatYearMonth = nts.uk.time.formatYearMonth;
    import validation = nts.uk.ui.validation;

    const API = {
        REGISTER_MONTH: 'at/record/monthly/agreement/monthly-result/special-provision/register-month',
        REGISTER_YEAR: 'at/record/monthly/agreement/monthly-result/special-provision/register-year'
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        datas: Array<EmployeeAgreementTimeNew> = [];
        appType: common.AppTypeEnum = null;
        processingMonth: number;

        yearTimeValidation = new validation.TimeValidator(this.$i18n("KAF021_18"), "AgreementOneYearTime", { required: true, valueType: "Clock", inputFormat: "hh:mm", outputFormat: "time", mode: "time" });
        reasonsValidation = new validation.StringValidator(this.$i18n("KAF021_19"), "ReasonsForAgreement", { required: true });
        constructor() {
            super();
            const vm = this;
        }

        created(params: any) {
            const vm = this;
            if (params != null) {
                vm.convertParams(params);
                let cacheJson = localStorage.getItem(vm.getCacheKey());
                let cache: Array<Kaf021B_Cache> = JSON.parse(cacheJson);

                // UI処理3参照
                // 登録画面から選択画面に戻り再度同じ従業員を選択した場合、同じ入力値を自動入力する
                if (cache) {
                    _.each(vm.datas, (item: EmployeeAgreementTimeNew) => {
                        let empCache = _.find(cache, (c: Kaf021B_Cache) => {
                            return c.employeeId == item.employeeId;
                        });

                        if (empCache) {
                            item.newMax = empCache.newMax;
                            item.reason = empCache.reason;
                        }
                    })
                }
            }
            vm.loadMGrid();

            _.extend(window, { vm });
        }

        mounted() {
            const vm = this;


        }

        loadMGrid() {
            const vm = this;
            let height = $(window).height() - 90 - 289;
            let width = $(window).width() + 20 - 1250;

            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1170px",
                height: "200px",
                subWidth: width + "px",
                subHeight: height + "px",
                headerHeight: '60px',
                rowHeight: '40px',
                dataSource: vm.datas,
                primaryKey: 'employeeId',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                columns: vm.getColumns(),
                ntsControls: [
                    {
                        name: 'CheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId: any, columnKey: any, value: any, rowData: any) {
                            //vm.checkDelete(rowId, value);
                        }
                    }
                ],
                features: [
                    {
                        name: 'CellStyles',
                        states: vm.getCellStyles()
                    }
                ]
            }).create();
        }

        getColumns(): Array<any> {
            const vm = this;
            let month = vm.getMonthStr();

            var columns = [];
            columns.push({ headerText: "key", key: 'employeeId', dataType: 'string', hidden: true });
            // B3_1
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '100px', ntsControl: "Label" });
            // B3_2
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '160px', ntsControl: "Label" });
            // B3_3
            columns.push({
                headerText: vm.$i18n("KAF021_25"),
                group: [
                    // B3_4
                    { headerText: vm.$i18n("KAF021_64", [month.toString()]), key: 'monthStr', dataType: 'string', width: '75px', ntsControl: "Label" },
                    // B3_5
                    { headerText: vm.$i18n("KAF021_26"), key: 'yearStr', dataType: 'string', width: '85px', ntsControl: "Label" }
                ]
            });
            // B3_6
            columns.push({ headerText: vm.$i18n("KAF021_10"), key: 'monthAverage2Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_7
            columns.push({ headerText: vm.$i18n("KAF021_11"), key: 'monthAverage3Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_8
            columns.push({ headerText: vm.$i18n("KAF021_12"), key: 'monthAverage4Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_9
            columns.push({ headerText: vm.$i18n("KAF021_13"), key: 'monthAverage5Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_10
            columns.push({ headerText: vm.$i18n("KAF021_14"), key: 'monthAverage6Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // B3_11
            columns.push({ headerText: vm.$i18n("KAF021_15"), key: 'exceededNumber', dataType: 'string', width: '50px', ntsControl: "Label" });

            // B3_12
            columns.push({
                headerText: vm.getCurrentMaxHeader(),
                group: [
                    // B3_13
                    { headerText: vm.$i18n("KAF021_27"), key: vm.getCurrentMaxKey(), dataType: 'string', width: '75px', ntsControl: "Label" },
                    // B3_14
                    {
                        headerText: vm.$i18n("KAF021_28"), key: 'newMax', dataType: 'string', width: '75px',
                        constraint: {
                            primitiveValue: 'AgreementOneYearTime',
                            required: true
                        }
                    }
                ]
            });

            // B3_15
            columns.push({
                headerText: vm.$i18n("KAF021_29"), key: 'reason', dataType: 'string', width: '310px',
                constraint: {
                    primitiveValue: 'ReasonsForAgreement',
                    required: true
                }
            });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<common.CellState> = [];
            _.forEach(vm.datas, (data: EmployeeAgreementTimeNew) => {
                cellStates.push(new common.CellState(data.employeeId, 'currentTime', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'yearStr', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthStr', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage2Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage3Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage4Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage5Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage6Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'exceededNumber', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, vm.getCurrentMaxKey(), ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'newMax', ["center-align", "cell-edit"]));
                cellStates.push(new common.CellState(data.employeeId, 'reason', ["cell-edit"]));
            })
            return cellStates;
        }

        convertParams(params: any) {
            const vm = this;
            vm.appType = params.appType;
            vm.processingMonth = params.processingMonth;

            let month = vm.getMonthStr();

            let datas: Array<EmployeeAgreementTimeNew> = [];
            _.each(params.datas, (item: any) => {
                let data: EmployeeAgreementTimeNew = {
                    employeeId: item.employeeId,
                    wkpName: item.wkpName,
                    employeeName: item.employeeName,

                    monthStr: item["month" + month + "Str"],
                    monthTime: item["month" + month + "Time"],
                    monthMaxTime: item["month" + month + "MaxTime"],
                    monthUpperLimit: item["month" + month + "UpperLimit"],
                    monthTimeStr: item["month" + month + "TimeStr"],
                    monthMaxTimeStr: item["month" + month + "MaxTimeStr"],
                    monthUpperLimitStr: item["month" + month + "UpperLimitStr"],
                    monthError: item["month" + month + "Error"],
                    monthAlarm: item["month" + month + "Alarm"],

                    year: item.year,
                    yearStr: item.yearStr,
                    yearTime: item.yearTime,
                    yearMaxTime: item.yearMaxTime,
                    yearUpperLimit: item.yearUpperLimit,
                    yearTimeStr: item.yearTimeStr,
                    yearMaxTimeStr: item.yearMaxTimeStr,
                    yearUpperLimitStr: item.yearUpperLimitStr,
                    yearError: item.yearError,
                    yearAlarm: item.yearAlarm,

                    monthAverage2: item.monthAverage2,
                    monthAverage2Str: item.monthAverage2Str,

                    monthAverage3: item.monthAverage3,
                    monthAverage3Str: item.monthAverage3Str,

                    monthAverage4: item.monthAverage4,
                    monthAverage4Str: item.monthAverage4Str,

                    monthAverage5: item.monthAverage5,
                    monthAverage5Str: item.monthAverage5Str,

                    monthAverage6: item.monthAverage6,
                    monthAverage6Str: item.monthAverage6Str,

                    exceededNumber: item.exceededNumber,
                    newMax: null,
                    reason: null
                };
                datas.push(data);
            });
            vm.datas = datas;
        }

        register() {
            const vm = this;

            let data: Array<EmployeeAgreementTimeNew> = $("#grid").mGrid("dataSource", true);
            if (!vm.isValid(data)) return;
            vm.$blockui("invisible");
            if (vm.isYearMode()) {
                let commandYears: Array<RegisterAppSpecialProvisionYearCommand> = [];
                _.each(data, (item: EmployeeAgreementTimeNew) => {
                    let content: AnnualAppContentCommand = {
                        employeeId: item.employeeId,
                        errorTime: moment.duration(item.newMax).asMinutes(),
                        alarmTime: moment.duration(item.newMax).asMinutes(),
                        year: item.year,
                        reason: item.reason
                    };
                    let screenInfo: ScreenDisplayInfoCommand = {
                        /** B4_3 */
                        monthTime: item.monthTime,
                        /** B4_3 */
                        monthTargetTime: item.monthMaxTime,
                        /** B4_4 */
                        yearTime: item.yearTime,
                        /** B4_5 */
                        monthAverage2: item.monthAverage2,
                        /** B4_6 */
                        monthAverage3: item.monthAverage3,
                        /** B4_7 */
                        monthAverage4: item.monthAverage4,
                        /** B4_8 */
                        monthAverage5: item.monthAverage5,
                        /** B4_9 */
                        monthAverage6: item.monthAverage6,
                        /** B4_10 */
                        exceededNumber: item.exceededNumber,
                        /** B4_11 */
                        monthUpperLimit: 0,
                        /** B4_11 */
                        yearUpperLimit: item.yearUpperLimit
                    };
                    let commandYear: RegisterAppSpecialProvisionYearCommand = {
                        content: content,
                        screenInfo: screenInfo
                    };
                    commandYears.push(commandYear);
                })
                vm.$ajax(API.REGISTER_YEAR, commandYears).done((empErrors: Array<common.ErrorResultDto>) => {
                    if (empErrors && !_.isEmpty(empErrors)) {
                        let errorItems = common.generateErrors(empErrors);
                        nts.uk.ui.dialog.bundledErrors({ errors: errorItems });
                    } else {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                            localStorage.setItem(vm.getCacheKey(), null);
                            vm.$jump('at', '/view/kaf/021/a/index.xhtml', true);
                        });
                    }
                }).fail((error: any) => {
                    vm.$dialog.error(error);
                }).always(() => vm.$blockui("clear"));
            } else {
                let commandMonths: Array<RegisterAppSpecialProvisionMonthCommand> = [];
                _.each(data, (item: EmployeeAgreementTimeNew) => {
                    let content: MonthlyAppContentCommand = {
                        employeeId: item.employeeId,
                        errorTime: moment.duration(item.newMax).asMinutes(),
                        alarmTime: moment.duration(item.newMax).asMinutes(),
                        yearMonth: vm.getYearMonth(),
                        reason: item.reason
                    };
                    let screenInfo: ScreenDisplayInfoCommand = {
                        /** B4_3 */
                        monthTime: item.monthTime,
                        /** B4_3 */
                        monthTargetTime: item.monthMaxTime,
                        /** B4_4 */
                        yearTime: item.yearTime,
                        /** B4_5 */
                        monthAverage2: item.monthAverage2,
                        /** B4_6 */
                        monthAverage3: item.monthAverage3,
                        /** B4_7 */
                        monthAverage4: item.monthAverage4,
                        /** B4_8 */
                        monthAverage5: item.monthAverage5,
                        /** B4_9 */
                        monthAverage6: item.monthAverage6,
                        /** B4_10 */
                        exceededNumber: item.exceededNumber,
                        /** B4_11 */
                        monthUpperLimit: item.monthUpperLimit,
                        /** B4_11 */
                        yearUpperLimit: 0
                    };
                    let commandYear: RegisterAppSpecialProvisionMonthCommand = {
                        content: content,
                        screenInfo: screenInfo
                    };
                    commandMonths.push(commandYear);
                })
                vm.$ajax(API.REGISTER_MONTH, commandMonths).done((empErrors: Array<common.ErrorResultDto>) => {
                    if (empErrors && !_.isEmpty(empErrors)) {
                        let errorItems = common.generateErrors(empErrors);
                        nts.uk.ui.dialog.bundledErrors({ errors: errorItems });
                    } else {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                            localStorage.setItem(vm.getCacheKey(), null);
                            vm.$jump('at', '/view/kaf/021/a/index.xhtml', true);
                        });
                    }
                }).fail((error: any) => {
                    vm.$dialog.error(error);
                }).always(() => vm.$blockui("clear"));
            }
        }

        preScreen() {
            const vm = this;
            let data: Array<EmployeeAgreementTimeNew> = $("#grid").mGrid("dataSource", true);
            let cache: Array<Kaf021B_Cache> = _.map(data, (item: EmployeeAgreementTimeNew) => {
                return new Kaf021B_Cache(item.employeeId, item.newMax, item.reason);
            })
            localStorage.setItem(vm.getCacheKey(), JSON.stringify(cache));
            vm.$jump('at', '/view/kaf/021/a/index.xhtml', false);
        }

        isValid(data: Array<EmployeeAgreementTimeNew>) {
            const vm = this;

            let errorItems: Array<any> = [];
            _.forEach(data, (item: EmployeeAgreementTimeNew) => {
                let checkTime: any = vm.yearTimeValidation.validate(item.newMax == null ? null : item.newMax);
                if (!checkTime.isValid) {
                    errorItems.push({
                        message: checkTime.errorMessage,
                        messageId: checkTime.errorCode,
                        supplements: {}
                    })
                }


                let checkReason: any = vm.reasonsValidation.validate(item.reason == null ? null : item.reason);
                if (!checkReason.isValid) {
                    errorItems.push({
                        message: checkReason.errorMessage,
                        messageId: checkReason.errorCode,
                        supplements: {}
                    })
                }
            })

            if (!_.isEmpty(errorItems)) {
                nts.uk.ui.dialog.bundledErrors({ errors: errorItems });
                return false;
            }

            return true;
        }

        getMonthStr() {
            const vm = this;
            return moment(vm.getDate()).format("M");
        }

        getDate() {
            const vm = this;
            let date = new Date(formatYearMonth(vm.processingMonth));
            let month = date.getMonth();
            if (vm.appType == common.AppTypeEnum.NEXT_MONTH) {
                month = date.getMonth() + 1;
            }
            return new Date(date.setMonth(month));
        }

        getYearMonth() {
            const vm = this;
            return Number(moment(vm.getDate()).format("YYYYMM"));
        }

        getYear() {
            const vm = this;
            return Number(moment(vm.getDate()).format("YYYY"));
        }

        getCurrentMaxKey() {
            const vm = this;
            if (vm.isYearMode()) {
                return "yearUpperLimitStr";
            } else {
                return "monthUpperLimitStr";
            }
        }

        getCurrentMaxHeader() {
            const vm = this;
            if (vm.isYearMode()) {
                return vm.$i18n("KAF021_67", [vm.getYear().toString()]);
            } else {
                let month = vm.getMonthStr();
                return vm.$i18n("KAF021_64", [month.toString()])
            }
        }

        isYearMode() {
            const vm = this;
            return vm.appType == common.AppTypeEnum.YEARLY;
        }

        getCacheKey() {
            const vm = this;
            return __viewContext.user.companyCode + "_" + __viewContext.user.employeeId + "_" + vm.appType + "_" + vm.processingMonth + "_kaf021b_cache";
        }
    }

    interface EmployeeAgreementTimeNew {
        employeeId: string;
        wkpName: string;
        employeeName: string;

        monthStr: any;
        monthTime: any;
        monthMaxTime: any;
        monthUpperLimit: any;
        monthTimeStr: any;
        monthMaxTimeStr: any;
        monthUpperLimitStr: any;
        monthError: any;
        monthAlarm: any;

        year: any;
        yearStr: any;
        yearTime: any;
        yearMaxTime: any;
        yearUpperLimit: any;
        yearTimeStr: any;
        yearMaxTimeStr: any;
        yearUpperLimitStr: any;
        yearError: any;
        yearAlarm: any;

        monthAverage2: any;
        monthAverage2Str: any;

        monthAverage3: any;
        monthAverage3Str: any;

        monthAverage4: any;
        monthAverage4Str: any;

        monthAverage5: any;
        monthAverage5Str: any;

        monthAverage6: any;
        monthAverage6Str: any;

        exceededNumber: number;
        newMax: string;
        reason: string;
    }

    class Kaf021B_Cache {
        employeeId: string;
        newMax: string;
        reason: string;

        constructor(employeeId: string, newMax: string, reason: string) {
            this.employeeId = employeeId;
            this.newMax = newMax;
            this.reason = reason;
        }
    }

    interface AnnualAppContentCommand {
        employeeId: string;
        errorTime: number;
        alarmTime: number;
        year: number;
        reason: string;
    }

    interface MonthlyAppContentCommand {
        employeeId: string;
        errorTime: number;
        alarmTime: number;
        yearMonth: number;
        reason: string;
    }

    interface ScreenDisplayInfoCommand {
        /** B4_3 */
        monthTime: number;
        /** B4_3 */
        monthTargetTime: number;
        /** B4_4 */
        yearTime: number;
        /** B4_5 */
        monthAverage2: number;
        /** B4_6 */
        monthAverage3: number;
        /** B4_7 */
        monthAverage4: number;
        /** B4_8 */
        monthAverage5: number;
        /** B4_9 */
        monthAverage6: number;
        /** B4_10 */
        exceededNumber: number;
        /** B4_11 */
        monthUpperLimit: number;
        /** B4_11 */
        yearUpperLimit: number;
    }

    interface RegisterAppSpecialProvisionMonthCommand {
        content: MonthlyAppContentCommand;
        screenInfo: ScreenDisplayInfoCommand;
    }

    interface RegisterAppSpecialProvisionYearCommand {
        content: AnnualAppContentCommand;
        screenInfo: ScreenDisplayInfoCommand;
    }
}
