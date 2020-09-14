/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.a {
    import textFormat = nts.uk.text.format;
    import parseTime = nts.uk.time.parseTime;
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

        appTypes: Array<AppType> = [];
        appTypeSelected: KnockoutObservable<AppTypeEnum> = ko.observable(null);

        items: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable("");

        datas: Array<EmployeeAgreementTime> = [];

        API = {
            getListWorkCycleDto: 'screen/at/ksm003/a/get',
        };

        constructor() {
            super();
            const vm = this;
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
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

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
                    // vm.fetchData();
                }
            }

            vm.appTypes.push(new AppType(AppTypeEnum.CURRENT_MONTH, textFormat("{0}月度", 1)));
            vm.appTypes.push(new AppType(AppTypeEnum.NEXT_MONTH, textFormat("{0}月度", 2)));
            vm.appTypes.push(new AppType(AppTypeEnum.YEARLY, this.$i18n("KAF021_4")));
        }

        created() {
            const vm = this;
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);
            vm.getMockData();
            vm.initData();

            vm.appTypeSelected.subscribe((value: AppTypeEnum) => {
                // vm.fetchData();
            })
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
                vm.loadMGrid();
                dfd.resolve();
            }).fail((error: any) => vm.$errors(error)).always(() => vm.$blockui("clear"));

            return dfd.promise();
        }

        fetchData(): JQueryPromise<any> {
            const vm = this,
                dfd = $.Deferred();
            vm.$blockui("invisible");
            switch (vm.appTypeSelected()) {
                case AppTypeEnum.CURRENT_MONTH:
                    vm.$ajax(API.CURRENT_MONTH, { employees: vm.empSearchItems }).done((data: any) => {
                        dfd.resolve();
                    }).fail((error: any) => vm.$errors(error)).always(() => vm.$blockui("clear"));
                    break;
                case AppTypeEnum.NEXT_MONTH:
                    vm.$ajax(API.NEXT_MONTH, { employees: vm.empSearchItems }).done((data: any) => {
                        dfd.resolve();
                    }).fail((error: any) => vm.$errors(error)).always(() => vm.$blockui("clear"));
                    break;
                case AppTypeEnum.YEARLY:
                    vm.$ajax(API.YEAR, { employees: vm.empSearchItems }).done((data: any) => {
                        dfd.resolve();
                    }).fail((error: any) => vm.$errors(error)).always(() => vm.$blockui("clear"));
                    break;
                default:
                    dfd.resolve();
                    vm.$blockui("clear");
            }
            return dfd.promise();
        }

        loadMGrid() {
            const vm = this;
            let height = $(window).height() - 90 - 290;
            let width = $(window).width() + 20 - 1170;

            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1170px",
                height: "200px",
                subWidth: width + "px",
                subHeight: height + "px",
                headerHeight: '60px',
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
            //self.setPageStatus();
        }

        getColumns(): Array<any> {
            const vm = this;

            var columns = [];
            columns.push({ headerText: "key", key: 'employeeId', dataType: 'string', hidden: true });
            // A3_1
            columns.push({ headerText: vm.$i18n("KAF021_6"), key: 'checked', dataType: 'boolean', width: '60px', checkbox: false, ntsControl: "CheckBox" });
            // A3_2
            columns.push({ headerText: vm.$i18n("KAF021_7"), key: 'status', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_3
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_4
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '130px', ntsControl: "Label" });
            // A3_5 ~ A3_16
            let date: Date = vm.$date.today();
            date.setMonth(vm.startingMonth);
            for (let i = 0; i < 12; i++) {
                date.setMonth(vm.startingMonth + i);
                let month = date.getMonth() + 1;
                columns.push({
                    headerText: vm.getMonthHeader(month), key: vm.getMonthKey(month), dataType: 'string', width: '80px', ntsControl: "Label"
                });
            }

            // A3_17
            columns.push({ headerText: vm.$i18n("KAF021_26"), key: 'year', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_18
            columns.push({ headerText: vm.$i18n("KAF021_10"), key: 'monthAverage2', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_19
            columns.push({ headerText: vm.$i18n("KAF021_11"), key: 'monthAverage3', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_20
            columns.push({ headerText: vm.$i18n("KAF021_12"), key: 'monthAverage4', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_21
            columns.push({ headerText: vm.$i18n("KAF021_13"), key: 'monthAverage5', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_22
            columns.push({ headerText: vm.$i18n("KAF021_14"), key: 'monthAverage6', dataType: 'string', width: '80px', ntsControl: "Label" });
            // A3_23
            columns.push({ headerText: vm.$i18n("KAF021_15"), key: 'exceededNumber', dataType: 'string', width: '80px', ntsControl: "Label" });
            return columns;
        }

        getCellStyles(): Array<any> {
            const vm = this;
            let cellStates: Array<CellState> = [];
            _.forEach(vm.datas, (data: EmployeeAgreementTime) => {

                cellStates.push(new CellState(data.employeeId, 'checked', ["center-align"]));
                cellStates.push(new CellState(data.employeeId, 'month1', ["center-align"].concat(vm.getMonthStatusColor(data.month1Status))));
                cellStates.push(new CellState(data.employeeId, 'month2', ["center-align"].concat(vm.getMonthStatusColor(data.month2Status))));
                cellStates.push(new CellState(data.employeeId, 'month3', ["center-align"].concat(vm.getMonthStatusColor(data.month3Status))));
                cellStates.push(new CellState(data.employeeId, 'month4', ["center-align"].concat(vm.getMonthStatusColor(data.month4Status))));
                cellStates.push(new CellState(data.employeeId, 'month5', ["center-align"].concat(vm.getMonthStatusColor(data.month5Status))));
                cellStates.push(new CellState(data.employeeId, 'month6', ["center-align"].concat(vm.getMonthStatusColor(data.month6Status))));
                cellStates.push(new CellState(data.employeeId, 'month7', ["center-align"].concat(vm.getMonthStatusColor(data.month7Status))));
                cellStates.push(new CellState(data.employeeId, 'month8', ["center-align"].concat(vm.getMonthStatusColor(data.month8Status))));
                cellStates.push(new CellState(data.employeeId, 'month9', ["center-align"].concat(vm.getMonthStatusColor(data.month9Status))));
                cellStates.push(new CellState(data.employeeId, 'month10', ["center-align"].concat(vm.getMonthStatusColor(data.month10Status))));
                cellStates.push(new CellState(data.employeeId, 'month11', ["center-align"].concat(vm.getMonthStatusColor(data.month11Status))));
                cellStates.push(new CellState(data.employeeId, 'month12', ["center-align"].concat(vm.getMonthStatusColor(data.month12Status))));
                cellStates.push(new CellState(data.employeeId, 'year', ["center-align"]));
                cellStates.push(new CellState(data.employeeId, 'monthAverage2', ["center-align"]));
                cellStates.push(new CellState(data.employeeId, 'monthAverage3', ["center-align"]));
                cellStates.push(new CellState(data.employeeId, 'monthAverage4', ["center-align"]));
                cellStates.push(new CellState(data.employeeId, 'monthAverage5', ["center-align"]));
                cellStates.push(new CellState(data.employeeId, 'monthAverage6', ["center-align"]));
                cellStates.push(new CellState(data.employeeId, 'exceededNumber', ["center-align"]));
            })
            return cellStates;
        }

        getHeaderStyles(): Array<any> {
            const vm = this;
            let date = vm.$date.today();
            let currentMonth = vm.getMonthKey(date.getMonth() + 1);
            return [{ key: currentMonth, colors: ['#ffffff'] }]
        }

        getMonthHeader(month: number) {
            return month + '月';
        }

        getMonthKey(month: number) {
            return 'month' + month;
        }

        getMonthStatusColor(status: AgreementTimeStatusOfMonthly) {
            switch (status) {
                case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM:
                case AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM:
                    return ["bg-alarm", "red-text"];
                case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR:
                case AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR:
                    return ["bg-error", "black-text"];
                case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP:
                case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP:
                    return ["bg-special"];
                default: return [];
            }
        }

        nextScreen() {

        }

        getMockData() {
            const vm = this;
            let datas: Array<IEmployeeAgreementTime> = []
            for (let i = 0; i < 100; i++) {
                let dto: IEmployeeAgreementTime = {
                    employeeId: "employeeId" + i,
                    employeeCode: "employeeCode " + i,
                    employeeName: "employeeName " + i,
                    affiliationCode: "affiliationCode " + i,
                    affiliationId: "affiliationId " + i,
                    affiliationName: "affiliationName " + i,
                    month1: {
                        yearMonth: 202001,
                        time: {
                            time: 1 * i,
                            maxTime: 1 * i + 10,
                            status: AgreementTimeStatusOfMonthly.NORMAL
                        },
                        maxTime: {
                            time: 2 * i,
                            maxTime: 2 * i + 10,
                            status: AgreementTimeStatusOfMonthly.NORMAL
                        },
                    },
                    month2: {
                        yearMonth: 202002,
                        time: {
                            time: 2 * i,
                            maxTime: 2 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR
                        },
                        maxTime: {
                            time: 3 * i,
                            maxTime: 3 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR
                        },
                    },
                    month3: {
                        yearMonth: 202003,
                        time: {
                            time: 3 * i,
                            maxTime: 3 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM
                        },
                        maxTime: {
                            time: 4 * i,
                            maxTime: 4 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM
                        },
                    },
                    month4: {
                        yearMonth: 202004,
                        time: {
                            time: 4 * i,
                            maxTime: 4 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR
                        },
                        maxTime: {
                            time: 5 * i,
                            maxTime: 5 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR
                        },
                    },
                    month5: {
                        yearMonth: 202005,
                        time: {
                            time: 5 * i,
                            maxTime: 5 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                        maxTime: {
                            time: 6 * i,
                            maxTime: 6 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                    },
                    month6: {
                        yearMonth: 202006,
                        time: {
                            time: 6 * i,
                            maxTime: 6 * i + 10,
                            status: AgreementTimeStatusOfMonthly.NORMAL_SPECIAL
                        },
                        maxTime: {
                            time: 7 * i,
                            maxTime: 7 * i + 10,
                            status: AgreementTimeStatusOfMonthly.NORMAL_SPECIAL
                        },
                    },
                    month7: {
                        yearMonth: 202007,
                        time: {
                            time: 7 * i,
                            maxTime: 7 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP
                        },
                        maxTime: {
                            time: 8 * i,
                            maxTime: 8 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP
                        },
                    },
                    month8: {
                        yearMonth: 202008,
                        time: {
                            time: 8 * i,
                            maxTime: 8 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                        maxTime: {
                            time: 9 * i,
                            maxTime: 9 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                    },
                    month9: {
                        yearMonth: 202009,
                        time: {
                            time: 9 * i,
                            maxTime: 9 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP
                        },
                        maxTime: {
                            time: 10 * i,
                            maxTime: 10 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP
                        },
                    },
                    month10: {
                        yearMonth: 202010,
                        time: {
                            time: 10 * i,
                            maxTime: 10 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY
                        },
                        maxTime: {
                            time: 11 * i,
                            maxTime: 11 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY
                        },
                    },
                    month11: {
                        yearMonth: 202011,
                        time: {
                            time: 12 * i,
                            maxTime: 12 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                        maxTime: {
                            time: 12 * i,
                            maxTime: 12 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                    },
                    month12: {
                        yearMonth: 202012,
                        time: {
                            time: 13 * i,
                            maxTime: 13 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                        maxTime: {
                            time: 14 * i,
                            maxTime: 14 * i + 10,
                            status: AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
                        },
                    },
                    year: {
                        limitTime: 10 + i,
                        time: 8 + i,
                        status: AgreTimeYearStatusOfMonthly.EXCESS_LIMIT
                    },
                    monthAverage2: {
                        totalTime: 12 + i,
                        time: 14 + i,
                        status: AgreMaxTimeStatusOfMonthly.NORMAL
                    },
                    monthAverage3: {
                        totalTime: 13 + i,
                        time: 15 + i,
                        status: AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME
                    },
                    monthAverage4: {
                        totalTime: 14 + i,
                        time: 16 + i,
                        status: AgreMaxTimeStatusOfMonthly.NORMAL
                    },
                    monthAverage5: {
                        totalTime: 15 + i,
                        time: 17 + i,
                        status: AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME
                    },
                    monthAverage6: {
                        totalTime: 16 + i,
                        time: 18 + i,
                        status: AgreMaxTimeStatusOfMonthly.NORMAL
                    },
                    exceededNumber: i + 1
                };

                datas.push(dto);
            }

            vm.datas = EmployeeAgreementTime.fromApp(datas);
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

    class CellState {
        rowId: string;
        columnKey: string;
        state: Array<any>
        constructor(rowId: string, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }

    class AppType {
        value: AppTypeEnum;
        name: string;

        constructor(value: AppTypeEnum, name: string) {
            this.value = value;
            this.name = name;
        }
    }

    enum AppTypeEnum {
        CURRENT_MONTH = 1,
        NEXT_MONTH = 2,
        YEARLY = 3
    }

    class EmployeeAgreementTime {
        employeeId: string;
        checked: boolean;
        status: string;
        wkpName: string;
        employeeName: string;
        month1: any;
        month1Status: AgreementTimeStatusOfMonthly;
        month2: any;
        month2Status: AgreementTimeStatusOfMonthly;
        month3: any;
        month3Status: AgreementTimeStatusOfMonthly;
        month4: any;
        month4Status: AgreementTimeStatusOfMonthly;
        month5: any;
        month5Status: AgreementTimeStatusOfMonthly;
        month6: any;
        month6Status: AgreementTimeStatusOfMonthly;
        month7: any;
        month7Status: AgreementTimeStatusOfMonthly;
        month8: any;
        month8Status: AgreementTimeStatusOfMonthly;
        month9: any;
        month9Status: AgreementTimeStatusOfMonthly;
        month10: any;
        month10Status: AgreementTimeStatusOfMonthly;
        month11: any;
        month11Status: AgreementTimeStatusOfMonthly;
        month12: any;
        month12Status: AgreementTimeStatusOfMonthly;
        year: any;
        yearStatus: AgreTimeYearStatusOfMonthly
        monthAverage2: any;
        monthAverage2Status: AgreMaxTimeStatusOfMonthly
        monthAverage3: any;
        monthAverage3Status: AgreMaxTimeStatusOfMonthly
        monthAverage4: any;
        monthAverage4Status: AgreMaxTimeStatusOfMonthly
        monthAverage5: any;
        monthAverage5Status: AgreMaxTimeStatusOfMonthly
        monthAverage6: any;
        monthAverage6Status: AgreMaxTimeStatusOfMonthly
        exceededNumber: number;

        constructor(data: IEmployeeAgreementTime) {
            this.employeeId = data.employeeId;
            this.checked = false;
            this.status = "status";
            this.wkpName = "wkpName";
            this.employeeName = data.employeeCode + "　" + data.employeeName;
            this.month1 = parseTime(data.month1.time.time, true).format();
            this.month1Status = data.month1.time.status;
            this.month2 = parseTime(data.month2.time.time, true).format();
            this.month2Status = data.month2.time.status;
            this.month3 = parseTime(data.month3.time.time, true).format();
            this.month3Status = data.month3.time.status;
            this.month4 = parseTime(data.month4.time.time, true).format();
            this.month4Status = data.month4.time.status;
            this.month5 = parseTime(data.month5.time.time, true).format();
            this.month5Status = data.month5.time.status;
            this.month6 = parseTime(data.month6.time.time, true).format();
            this.month6Status = data.month6.time.status;
            this.month7 = parseTime(data.month7.time.time, true).format();
            this.month7Status = data.month7.time.status;
            this.month8 = parseTime(data.month8.time.time, true).format();
            this.month8Status = data.month8.time.status;
            this.month9 = parseTime(data.month9.time.time, true).format();
            this.month9Status = data.month9.time.status;
            this.month10 = parseTime(data.month10.time.time, true).format();
            this.month10Status = data.month10.time.status;
            this.month11 = parseTime(data.month11.time.time, true).format();
            this.month11Status = data.month11.time.status;
            this.month12 = parseTime(data.month12.time.time, true).format();
            this.month12Status = data.month12.time.status;
            this.year = parseTime(data.year.time, true).format();
            this.yearStatus = data.year.status;
            this.monthAverage2 = parseTime(data.monthAverage2.time, true).format();
            this.monthAverage2Status = data.monthAverage2.status;
            this.monthAverage3 = parseTime(data.monthAverage3.time, true).format();
            this.monthAverage3Status = data.monthAverage3.status;
            this.monthAverage4 = parseTime(data.monthAverage4.time, true).format();
            this.monthAverage4Status = data.monthAverage4.status;
            this.monthAverage5 = parseTime(data.monthAverage5.time, true).format();
            this.monthAverage5Status = data.monthAverage5.status;
            this.monthAverage6 = parseTime(data.monthAverage6.time, true).format();
            this.monthAverage6Status = data.monthAverage6.status;
            this.exceededNumber = data.exceededNumber;
        }

        static fromApp(data: Array<IEmployeeAgreementTime>): Array<EmployeeAgreementTime> {
            return _.map(data, (item: IEmployeeAgreementTime) => { return new EmployeeAgreementTime(item) })
        }
    }

    interface IEmployeeAgreementTime {
        /**
         * 社員ID
         */
        employeeId: string;
        /**
         * 社員コード
         */
        employeeCode: string;
        /**
         * 社員名
         */
        employeeName: string;
        /**
         * 所属CD
         */
        affiliationCode: string;
        /**
         * 所属ID
         */
        affiliationId: string;
        /**
         * 所属名称
         */
        affiliationName: string;
        /**
         * 1月度
         */
        month1: IAgreementTimeMonth;
        /**
         * 2月度
         */
        month2: IAgreementTimeMonth;
        /**
         * 3月度
         */
        month3: IAgreementTimeMonth;
        /**
         * 4月度
         */
        month4: IAgreementTimeMonth;
        /**
         * 5月度
         */
        month5: IAgreementTimeMonth;
        /**
         * 6月度
         */
        month6: IAgreementTimeMonth;
        /**
         * 7月度
         */
        month7: IAgreementTimeMonth;
        /**
         * 8月度
         */
        month8: IAgreementTimeMonth;
        /**
         * 9月度
         */
        month9: IAgreementTimeMonth;
        /**
         * 10月度
         */
        month10: IAgreementTimeMonth;
        /**
         * 11月度
         */
        month11: IAgreementTimeMonth;
        /**
         * 12月度
         */
        month12: IAgreementTimeMonth;
        /**
         * 年間
         */
        year: IAgreementTimeYear;
        /**
         * 直近2ヵ月平均
         */
        monthAverage2: IAgreementMaxAverageTime;
        /**
         * 直近3ヵ月平均
         */
        monthAverage3: IAgreementMaxAverageTime;
        /**
         * 直近4ヵ月平均
         */
        monthAverage4: IAgreementMaxAverageTime;
        /**
         * 直近5ヵ月平均
         */
        monthAverage5: IAgreementMaxAverageTime;
        /**
         * 直近6ヵ月平均
         */
        monthAverage6: IAgreementMaxAverageTime;
        /**
         * 36協定超過情報.超過回数
         */
        exceededNumber: number;
    }

    interface IAgreementTimeMonth {
        /**
         * 月度
         */
        yearMonth: number;

        /**
         * 管理期間の36協定時間
         */
        time: IAgreementTime;

        /**
         * 36協定時間一覧.月別実績の36協定上限時間
         */
        maxTime: IAgreementTime;
    }

    interface IAgreementTime {
        /**
         * 36協定時間
         */
        time: number;

        /**
         * 上限時間
         */
        maxTime: number;

        /**
         * 状態
         */
        status: AgreementTimeStatusOfMonthly;
    }

    /**
     * 月別実績の36協定時間状態
     */
    enum AgreementTimeStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 限度エラー時間超過 */
        EXCESS_LIMIT_ERROR = 1,
        /** 限度アラーム時間超過 */
        EXCESS_LIMIT_ALARM = 2,
        /** 特例限度エラー時間超過 */
        EXCESS_EXCEPTION_LIMIT_ERROR = 3,
        /** 特例限度アラーム時間超過 */
        EXCESS_EXCEPTION_LIMIT_ALARM = 4,
        /** 正常（特例あり） */
        NORMAL_SPECIAL = 5,
        /** 限度エラー時間超過（特例あり） */
        EXCESS_LIMIT_ERROR_SP = 6,
        /** 限度アラーム時間超過（特例あり） */
        EXCESS_LIMIT_ALARM_SP = 7,
        /** tính Tổng hiệp định 36） */
        EXCESS_BG_GRAY = 8
    }

    interface IAgreementTimeYear {
        /**
         * 限度時間
         */
        limitTime: number;
        /**
         * 実績時間
         */
        time: number;
        /**
         * 状態
         */
        status: AgreTimeYearStatusOfMonthly;
    }

    enum AgreTimeYearStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 限度超過 */
        EXCESS_LIMIT = 1
    }

    interface IAgreementMaxAverageTime {
        /**
        * 合計時間
        */
        totalTime: number;
        /**
         * 平均時間
         */
        time: number;
        /**
         * 状態
         */
        status: AgreMaxTimeStatusOfMonthly;
    }

    /**
     * 月別実績の36協定上限時間状態
     */
    enum AgreMaxTimeStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 上限時間超過 */
        EXCESS_MAXTIME = 1
    }
}
