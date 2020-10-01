/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.b {
    import textFormat = nts.uk.text.format;
    import parseTime = nts.uk.time.parseTime;
    import common = nts.uk.at.kaf021.common;
    import formatYearMonth = nts.uk.time.formatYearMonth;

    const API = {
        REGISTER_MONTH: 'at/record/monthly/agreement/monthly-result/special-provision/register-month',
        REGISTER_YEAR: 'at/record/monthly/agreement/monthly-result/special-provision/register-year'
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        datas: Array<EmployeeAgreementTimeNew> = [];
        appType: common.AppTypeEnum = null;
        processingMonth: number;
        constructor() {
            super();
            const vm = this;
        }

        created(params: any) {
            const vm = this;
            if (params != null) {
                vm.convertParams(params);
                let cacheJson = localStorage.getItem('kaf021b_cache');
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
                        onChange: function (rowId, columnKey, value, rowData) {
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
            let month = vm.getMonth()

            var columns = [];
            columns.push({ headerText: "key", key: 'employeeId', dataType: 'string', hidden: true });
            // B3_1
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '120px', ntsControl: "Label" });
            // B3_2
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '140px', ntsControl: "Label" });
            // B3_3
            columns.push({
                headerText: vm.$i18n("KAF021_25"),
                group: [
                    // B3_4
                    { headerText: vm.$i18n("KAF021_64", [month.toString()]), key: 'monthStr', dataType: 'string', width: '75px', ntsControl: "Label" },
                    // B3_5
                    { headerText: vm.$i18n("KAF021_26"), key: 'yearStr', dataType: 'string', width: '75px', ntsControl: "Label" }
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
                    { headerText: vm.$i18n("KAF021_28"), key: 'newMax', dataType: 'string', width: '75px',
                        constraint: {
                            primitiveValue: vm.isYearMode() ? 'AgreementOneYearTime' : 'AgreementOneMonthTime',
                            required: true
                        }
                    }
                ]
            });

            // B3_15
            columns.push({ 
                headerText: vm.$i18n("KAF021_29"), key: 'reason', dataType: 'string', width: '320px',
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

        convertParams(params: any){
            const vm = this;
            vm.appType = params.appType;
            vm.processingMonth = params.processingMonth;

            let month = vm.getMonth()

            let datas = [];
            _.each(params.datas, (item: common.EmployeeAgreementTime) => {
                let data: EmployeeAgreementTimeNew = {
                    employeeId: item.employeeId,
                    wkpName: item.wkpName,
                    employeeName: item.employeeName,

                    monthStr: item["month" + month +"Str"],
                    monthTime: item["month" + month +"Time"],
                    monthMaxTime: item["month" + month +"MaxTime"],
                    monthTimeStr: item["month" + month +"TimeStr"],
                    monthMaxTimeStr: item["month" + month +"MaxTimeStr"],
                    monthStatus: item["month" + month +"Status"],
                    monthError: item["month" + month +"Error"],
                    monthAlarm: item["month" + month +"Alarm"],

                    yearStr: item.yearStr,
                    yearTime: item.yearTime,
                    yearMaxTime: item.yearMaxTime,
                    yearTimeStr: item.yearTimeStr,
                    yearMaxTimeStr: item.yearMaxTimeStr,
                    yearStatus: item.yearStatus,
                    yearError: item.yearError,
                    yearAlarm: item.yearAlarm,

                    monthAverage2: item.monthAverage2,
                    monthAverage2Str: item.monthAverage2Str,
                    monthAverage2Status: item.monthAverage2Status,

                    monthAverage3: item.monthAverage3,
                    monthAverage3Str: item.monthAverage3Str,
                    monthAverage3Status: item.monthAverage3Status,

                    monthAverage4: item.monthAverage4,
                    monthAverage4Str: item.monthAverage4Str,
                    monthAverage4Status: item.monthAverage4Status,

                    monthAverage5: item.monthAverage5,
                    monthAverage5Str: item.monthAverage5Str,
                    monthAverage5Status: item.monthAverage5Status,

                    monthAverage6: item.monthAverage6,
                    monthAverage6Str: item.monthAverage6Str,
                    monthAverage6Status: item.monthAverage6Status,

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
            if (vm.isYearMode()) {
                let commandYears: Array<RegisterAppSpecialProvisionYearCommand> = [];
                _.each(data, (item: EmployeeAgreementTimeNew) => {
                    let content: AnnualAppContentCommand = {
                        applicantId: null,
                        employeeId: item.employeeId,
                        errorTime: null,
                        alarmTime: null,
                        year: null,
                        reason: item.reason
                    };
                    let screenInfo: ScreenDisplayInfoCommand = {
                        /** B4_3 */
                        monthTime: null,
                        /** B4_3 */
                        monthTargetTime: null,
                        /** B4_4 */
                        yearTime: null,
                        /** B4_5 */
                        monthAverage2: null,
                        /** B4_6 */
                        monthAverage3: null,
                        /** B4_7 */
                        monthAverage4: null,
                        /** B4_8 */
                        monthAverage5: null,
                        /** B4_9 */
                        monthAverage6: null,
                        /** B4_10 */
                        exceededNumber: null,
                        /** B4_11 */
                        monthError: null,
                        /** B4_11 */
                        monthAlarm: null,
                        /** B4_11 */
                        yearError: null,
                        /** B4_11 */
                        yearAlarm: null,
                    };
                    let commandYear: RegisterAppSpecialProvisionYearCommand = {
                        content: content,
                        screenInfo: screenInfo
                    };
                    commandYears.push(commandYear);
                })

            }

            localStorage.setItem('kaf021b_cache', null);
            vm.$jump('at', '/view/kaf/021/a/index.xhtml', true);
        }

        preScreen() {
            const vm = this;
            let data: Array<EmployeeAgreementTimeNew> = $("#grid").mGrid("dataSource", true);
            let cache: Array<Kaf021B_Cache> = _.map(data, (item: EmployeeAgreementTimeNew) => {
                return new Kaf021B_Cache(item.employeeId, item.newMax, item.reason);
            })
            localStorage.setItem('kaf021b_cache', JSON.stringify(cache));
            vm.$jump('at', '/view/kaf/021/a/index.xhtml', true);
        }

        getMonth() {
            const vm = this;
            let date = new Date(formatYearMonth(vm.processingMonth));
            let month = 0;

            if (vm.appType == common.AppTypeEnum.NEXT_MONTH) {
                month = date.getMonth() + 2;
            } else {
                month = date.getMonth() + 1;
            }
            return month;
        }

        getCurrentMaxKey() {
            const vm = this;
            if (vm.isYearMode()) {
                return "yearMaxTimeStr";
            } else {
                return "monthMaxTimeStr";
            }
        }

        getCurrentMaxHeader() {
            const vm = this;
            if (vm.isYearMode()) {
                return vm.$i18n("KAF021_4");
            } else {
                let month = vm.getMonth();
                return vm.$i18n("KAF021_64", [month.toString()])
            }
        }

        isYearMode(){
            const vm = this;
            return vm.appType == common.AppTypeEnum.YEARLY;
        }
    }

    interface EmployeeAgreementTimeNew {
        employeeId: string;
        wkpName: string;
        employeeName: string;

        monthStr: any;
        monthTime: any;
        monthMaxTime: any;
        monthTimeStr: any;
        monthMaxTimeStr: any;
        monthStatus: common.AgreementTimeStatusOfMonthly;
        monthError: any;
        monthAlarm: any;

        yearStr: any;
        yearTime: any;
        yearMaxTime: any;
        yearTimeStr: any;
        yearMaxTimeStr: any;
        yearStatus: common.AgreTimeYearStatusOfMonthly;
        yearError: any;
        yearAlarm: any;

        monthAverage2: any;
        monthAverage2Str: any;
        monthAverage2Status: common.AgreMaxTimeStatusOfMonthly;

        monthAverage3: any;
        monthAverage3Str: any;
        monthAverage3Status: common.AgreMaxTimeStatusOfMonthly;

        monthAverage4: any;
        monthAverage4Str: any;
        monthAverage4Status: common.AgreMaxTimeStatusOfMonthly;

        monthAverage5: any;
        monthAverage5Str: any;
        monthAverage5Status: common.AgreMaxTimeStatusOfMonthly;

        monthAverage6: any;
        monthAverage6Str: any;
        monthAverage6Status: common.AgreMaxTimeStatusOfMonthly;

        exceededNumber: number;
        newMax: number;
        reason: string;
    }

    class Kaf021B_Cache {
        employeeId: string;
        newMax: number;
        reason: string;

        constructor(employeeId: string, newMax: number, reason: string) {
            this.employeeId = employeeId;
            this.newMax = newMax;
            this.reason = reason;
        }
    }

    interface AnnualAppContentCommand {
        applicantId: string;
        employeeId: string;
        errorTime: number;
        alarmTime: number;
        year: number;
        reason: string;
    }

    interface MonthlyAppContentCommand {
        applicantId: string;
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
        monthError: number;
        /** B4_11 */
        monthAlarm: number;
        /** B4_11 */
        yearError: number;
        /** B4_11 */
        yearAlarm: number;
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
