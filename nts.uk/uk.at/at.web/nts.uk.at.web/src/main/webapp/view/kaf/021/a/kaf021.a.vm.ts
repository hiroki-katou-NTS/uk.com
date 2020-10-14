/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.a {
    import textFormat = nts.uk.text.format;
    import parseTime = nts.uk.time.parseTime;
    import formatYearMonth = nts.uk.time.formatYearMonth;
    import common = nts.uk.at.kaf021.common;

    const API = {
        INIT: 'screen/at/kaf021/init',
        CURRENT_MONTH: 'screen/at/kaf021/get-current-month',
        NEXT_MONTH: 'screen/at/kaf021/get-next-month',
        YEAR: 'screen/at/kaf021/get-year',
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        ccg001ComponentOption: GroupOption = null;
        empSearchItems: Array<EmployeeSearchDto> = [];

        processingMonth: number = 0;
        startingMonth: number = 0;

        appTypes: Array<common.AppType> = [];
        appTypeSelected: KnockoutObservable<common.AppTypeEnum> = ko.observable(null);

        datas: Array<common.EmployeeAgreementTime> = [];

        canNextScreen: KnockoutObservable<boolean> = ko.observable(false);
        empItems: Array<string> = [];
        constructor(isBackFromScreenB: boolean) {
            super();
            const vm = this;

            if (isBackFromScreenB) {
                let cacheJson: string = localStorage.getItem('kaf021a_cache');
                let cache: CacheData = JSON.parse(cacheJson);
                if (cache) {
                    vm.datas = cache.datas;
                    vm.empItems = cache.empItems;
                    vm.canNextScreen(vm.empItems.length > 0);
                    vm.appTypeSelected(cache.appType);
                } else {
                    vm.getMockData();
                    vm.appTypeSelected(common.AppTypeEnum.NEXT_MONTH);
                }
            } else {
                vm.getMockData();
                vm.appTypeSelected(common.AppTypeEnum.NEXT_MONTH);
            }

            vm.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: true,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: true,
                maxPeriodRange: 'oneMonth',

                /** Required parameter */
                baseDate: moment.utc().toISOString(),
                periodStartDate: null,
                periodEndDate: null,
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: false,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: false,
                showSameWorkplaceAndChild: false,

                /** Advanced search properties */
                showEmployment: true,
                showDepartment: false,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: false,
                isMutipleCheck: true,
                //tabindex: 6,
                showOnStart: false,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    vm.empSearchItems = data.listEmployee;
                    vm.fetchData().done(() => {
                        $("#grid").mGrid("destroy");
                        vm.loadMGrid();
                    });
                }
            }
        }

        created() {
            const vm = this;
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);
            vm.initData().done(() => {
                vm.loadMGrid();
                $('#A1_5').focus();
                vm.appTypeSelected.subscribe((value: common.AppTypeEnum) => {
                    vm.fetchData().done(() => {
                        $("#grid").mGrid("destroy");
                        vm.loadMGrid();
                    });
                })
            });

            _.extend(window, { vm });
        }

        mounted() {
            const vm = this;


        }

        initData(): JQueryPromise<any> {
            const vm = this,
                dfd = $.Deferred();
            vm.$blockui("invisible");
            vm.$ajax(API.INIT).done((data: any) => {
                vm.processingMonth = data.processingMonth;
                vm.startingMonth = data.startingMonth;

                let date = new Date(formatYearMonth(vm.processingMonth));
                let currentMonth = date.getMonth() + 1;
                date.setMonth(currentMonth);
                let nextMonth = date.getMonth() + 1;

                vm.appTypes.push(new common.AppType(common.AppTypeEnum.CURRENT_MONTH, textFormat(vm.$i18n("KAF021_64"), currentMonth)));
                vm.appTypes.push(new common.AppType(common.AppTypeEnum.NEXT_MONTH, textFormat(vm.$i18n("KAF021_64"), nextMonth)));
                vm.appTypes.push(new common.AppType(common.AppTypeEnum.YEARLY, vm.$i18n("KAF021_4")));
                vm.appTypeSelected.valueHasMutated();

                dfd.resolve();
            }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));

            return dfd.promise();
        }

        fetchData(): JQueryPromise<any> {
            const vm = this,
                dfd = $.Deferred();
            vm.canNextScreen(false);
            vm.empItems = [];
            if (_.isEmpty(vm.empSearchItems)) {
                vm.datas = [];
                dfd.resolve();
                return dfd.promise();
            }
            vm.$blockui("invisible");
            vm.datas = [];
            switch (vm.appTypeSelected()) {
                case common.AppTypeEnum.CURRENT_MONTH:
                    vm.$ajax(API.CURRENT_MONTH, { employees: vm.empSearchItems }).done((data: any) => {
                        vm.datas = common.EmployeeAgreementTime.fromApp(data);
                        dfd.resolve();
                    }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
                    break;
                case common.AppTypeEnum.NEXT_MONTH:
                    vm.$ajax(API.NEXT_MONTH, { employees: vm.empSearchItems }).done((data: any) => {
                        vm.datas = common.EmployeeAgreementTime.fromApp(data);
                        dfd.resolve();
                    }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
                    break;
                case common.AppTypeEnum.YEARLY:
                    vm.$ajax(API.YEAR, { employees: vm.empSearchItems }).done((data: any) => {
                        vm.datas = common.EmployeeAgreementTime.fromApp(data);
                        dfd.resolve();
                    }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
                    break;
                default:
                    dfd.resolve();
                    vm.$blockui("clear");
            }
            return dfd.promise();
        }

        loadMGrid() {
            const vm = this;
            let height = $(window).height() - 90 - 339;
            let width = $(window).width() + 20 - 1170;

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
                            vm.checkNextScreen(rowId, value);
                        }
                    }
                ],
                features: [
                    {
                        name: "ColumnFixing",
                        showFixButtons: false,
                        fixingDirection: 'left',
                        columnSettings: [
                            {
                                columnKey: "employeeId",
                                isFixed: true
                            },
                            {
                                columnKey: "checked",
                                isFixed: true
                            },
                            {
                                columnKey: "status",
                                isFixed: true
                            },
                            {
                                columnKey: "wkpName",
                                isFixed: true
                            },
                            {
                                columnKey: "employeeName",
                                isFixed: true
                            }
                        ]
                    },
                    {
                        name: 'HeaderStyles',
                        columns: vm.getHeaderStyles()
                    },
                    {
                        name: 'CellStyles',
                        states: vm.getCellStyles()
                    }
                ]
            }).create();
        }

        getColumns(): Array<any> {
            const vm = this;

            var columns = [];
            columns.push({ headerText: "key", key: 'employeeId', dataType: 'string', hidden: true });
            // A3_1
            columns.push({ headerText: vm.$i18n("KAF021_6"), key: 'checked', dataType: 'boolean', width: '35px', checkbox: false, ntsControl: "CheckBox" });
            // A3_2
            columns.push({ headerText: vm.$i18n("KAF021_7"), key: 'status', dataType: 'string', width: '60px', ntsControl: "Label" });
            // A3_3
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '120px', ntsControl: "Label" });
            // A3_4
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '140px', ntsControl: "Label" });
            // A3_5 ~ A3_16
            let date: Date = vm.$date.today();
            date.setMonth(vm.startingMonth);
            for (let i = 0; i < 12; i++) {
                date.setMonth(vm.startingMonth + i);
                let month = date.getMonth() + 1;
                columns.push({
                    headerText: vm.getMonthHeader(month), key: vm.getMonthKey(month), dataType: 'string', width: '75px', ntsControl: "Label"
                });
            }

            // A3_17
            columns.push({ headerText: vm.$i18n("KAF021_26"), key: 'yearStr', dataType: 'string', width: '85px', ntsControl: "Label" });
            // A3_18
            columns.push({ headerText: vm.$i18n("KAF021_10"), key: 'monthAverage2Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // A3_19
            columns.push({ headerText: vm.$i18n("KAF021_11"), key: 'monthAverage3Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // A3_20
            columns.push({ headerText: vm.$i18n("KAF021_12"), key: 'monthAverage4Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // A3_21
            columns.push({ headerText: vm.$i18n("KAF021_13"), key: 'monthAverage5Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // A3_22
            columns.push({ headerText: vm.$i18n("KAF021_14"), key: 'monthAverage6Str', dataType: 'string', width: '60px', ntsControl: "Label" });
            // A3_23
            columns.push({ headerText: vm.$i18n("KAF021_15"), key: 'exceededNumber', dataType: 'string', width: '50px', ntsControl: "Label" });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<common.CellState> = [];
            _.forEach(vm.datas, (data: common.EmployeeAgreementTime) => {

                cellStates.push(new common.CellState(data.employeeId, 'checked', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'month1Str', ["center-align"].concat(vm.getMonthStatusColor(data.month1Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month2Str', ["center-align"].concat(vm.getMonthStatusColor(data.month2Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month3Str', ["center-align"].concat(vm.getMonthStatusColor(data.month3Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month4Str', ["center-align"].concat(vm.getMonthStatusColor(data.month4Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month5Str', ["center-align"].concat(vm.getMonthStatusColor(data.month5Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month6Str', ["center-align"].concat(vm.getMonthStatusColor(data.month6Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month7Str', ["center-align"].concat(vm.getMonthStatusColor(data.month7Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month8Str', ["center-align"].concat(vm.getMonthStatusColor(data.month8Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month9Str', ["center-align"].concat(vm.getMonthStatusColor(data.month9Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month10Str', ["center-align"].concat(vm.getMonthStatusColor(data.month10Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month11Str', ["center-align"].concat(vm.getMonthStatusColor(data.month11Status))));
                cellStates.push(new common.CellState(data.employeeId, 'month12Str', ["center-align"].concat(vm.getMonthStatusColor(data.month12Status))));
                cellStates.push(new common.CellState(data.employeeId, 'yearStr', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage2Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage3Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage4Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage5Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'monthAverage6Str', ["center-align"]));
                cellStates.push(new common.CellState(data.employeeId, 'exceededNumber', ["center-align"]));
            })
            return cellStates;
        }

        getHeaderStyles(): Array<any> {
            const vm = this;
            let date = vm.$date.today();
            let currentMonth = vm.getMonthKey(date.getMonth() + 1);
            return [
                { key: "checked", colors: ['padding-5'] },
                { key: "status", colors: ['padding-12'] },
                { key: "monthAverage2Str", colors: ['padding-12'] },
                { key: "monthAverage3Str", colors: ['padding-12'] },
                { key: "monthAverage4Str", colors: ['padding-12'] },
                { key: "monthAverage5Str", colors: ['padding-12'] },
                { key: "monthAverage6Str", colors: ['padding-12'] },
                { key: currentMonth, colors: ['#ffffff'] }
            ]
        }

        getMonthHeader(month: number) {
            const vm = this;
            return vm.$i18n("KAF021_65", [month.toString()]);// month + '月';
        }

        getMonthKey(month: number) {
            return 'month' + month + 'Str';
        }

        getMonthStatusColor(status: common.AgreementTimeStatusOfMonthly) {
            switch (status) {
                case common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM:
                case common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM:
                    return ["bg-alarm", "red-text"];
                case common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR:
                case common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR:
                    return ["bg-error", "black-text"];
                case common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP:
                case common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP:
                    return ["bg-special"];
                default: return [];
            }
        }

        checkNextScreen(rowId: any, check: any) {
            const vm = this;
            if (check) {
                vm.empItems.push(rowId);
            } else {
                const index = vm.empItems.indexOf(rowId);
                if (index > -1) {
                    vm.empItems.splice(index, 1);
                }
            }

            vm.canNextScreen(vm.empItems.length > 0);
        }

        nextScreen() {
            const vm = this;
            let dataAll: Array<common.EmployeeAgreementTime> = $("#grid").mGrid("dataSource", true);
            let dataSelected = _.filter(dataAll, (item: common.EmployeeAgreementTime) => { return item.checked });
            let cache = new CacheData(vm.appTypeSelected(), dataAll, vm.empItems);
            localStorage.setItem('kaf021a_cache', JSON.stringify(cache));
            vm.$jump('at', '/view/kaf/021/b/index.xhtml', {
                datas: dataSelected,
                appType: vm.appTypeSelected(),
                processingMonth: vm.processingMonth
            });
        }

        getMockData() {
            const vm = this;
            let datas: Array<common.IEmployeeAgreementTime> = []
            for (let i = 0; i < 100; i++) {
                let dto: common.IEmployeeAgreementTime = {
                    employeeId: "employeeId" + i,
                    employeeCode: "employeeCode " + i,
                    employeeName: "employeeName " + i,
                    affiliationCode: "affiliationCode " + i,
                    affiliationId: "affiliationId " + i,
                    affiliationName: "affiliationName " + i,
                    month1: {
                        yearMonth: 202001,
                        time: 100 * i,
                        maxTime: 100 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.NORMAL,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month2: {
                        yearMonth: 202002,
                        time: 200 * i,
                        maxTime: 200 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month3: {
                        yearMonth: 202003,

                        time: 3 * i,
                        maxTime: 3 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month4: {
                        yearMonth: 202004,
                        time: 4 * i,
                        maxTime: 4 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month5: {
                        yearMonth: 202005,
                        time: 5 * i,
                        maxTime: 5 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month6: {
                        yearMonth: 202006,
                        time: 6 * i,
                        maxTime: 6 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.NORMAL_SPECIAL,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month7: {
                        yearMonth: 202007,
                        time: 7 * i,
                        maxTime: 7 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month8: {
                        yearMonth: 202008,
                        time: 8 * i,
                        maxTime: 8 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month9: {
                        yearMonth: 202009,
                        time: 9 * i,
                        maxTime: 9 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month10: {
                        yearMonth: 202010,
                        time: 10 * i,
                        maxTime: 10 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month11: {
                        yearMonth: 202011,
                        time: 12 * i,
                        maxTime: 12 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    month12: {
                        yearMonth: 202012,
                        time: 1300 * i,
                        maxTime: 1300 * i + 10,
                        status: common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM,
                        error: 100 * i,
                        alarm: 50 * i
                    },
                    year: {
                        limitTime: 1000 * i,
                        time: 10000 * i,
                        status: common.AgreTimeYearStatusOfMonthly.EXCESS_LIMIT,
                        error: 1000 * i,
                        alarm: 500 * i
                    },
                    monthAverage2: {
                        totalTime: 120 * i,
                        time: 140 * i,
                        status: common.AgreMaxTimeStatusOfMonthly.NORMAL
                    },
                    monthAverage3: {
                        totalTime: 1300 * i,
                        time: 1500 * i,
                        status: common.AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME
                    },
                    monthAverage4: {
                        totalTime: 1400 * i,
                        time: 1600 * i,
                        status: common.AgreMaxTimeStatusOfMonthly.NORMAL
                    },
                    monthAverage5: {
                        totalTime: 150 * i,
                        time: 170 * i,
                        status: common.AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME
                    },
                    monthAverage6: {
                        totalTime: 16 * i,
                        time: 18 * i,
                        status: common.AgreMaxTimeStatusOfMonthly.NORMAL
                    },
                    exceededNumber: i + 1
                };

                datas.push(dto);
            }

            vm.datas = common.EmployeeAgreementTime.fromApp(datas);
        }
    }

    interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab?: boolean; // クイック検索
        showAdvancedSearchTab?: boolean; // 詳細検索
        showBaseDate?: boolean; // 基準日利用
        showClosure?: boolean; // 就業締め日利用
        showAllClosure?: boolean; // 全締め表示
        showPeriod?: boolean; // 対象期間利用
        periodFormatYM?: boolean; // 対象期間精度
        isInDialog?: boolean;

        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // 参照可能な社員すべて
        showOnlyMe?: boolean; // 自分だけ
        showSameWorkplace?: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment?: boolean; // 雇用条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード
        isTab2Lazy?: boolean;

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }

    interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        affiliationCode: string;
        affiliationId: string;
        affiliationName: string;
    }


    class CacheData {
        appType: common.AppTypeEnum;
        datas: Array<common.EmployeeAgreementTime>;
        empItems: Array<string> = [];

        constructor(appType: common.AppTypeEnum, datas: Array<common.EmployeeAgreementTime>, empItems: Array<string> = []) {
            this.appType = appType;
            this.datas = datas;
            this.empItems = empItems;
        }
    }
}
