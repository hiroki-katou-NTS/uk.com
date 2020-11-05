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

        setting: common.AgreementOperationSettingDto;
        processingMonth: number = 0;

        appTypes: Array<AppType> = [];
        appTypeSelected: KnockoutObservable<common.AppTypeEnum> = ko.observable(null);

        datas: Array<EmployeeAgreementTime> = [];

        canNextScreen: KnockoutObservable<boolean> = ko.observable(false);
        empItems: Array<string> = [];
        isReload?: boolean;
        constructor(isReload?: boolean) {
            super();
            const vm = this;

            this.isReload = isReload;
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

            if (vm.isReload != null) {
                let cacheJson: string = localStorage.getItem(vm.getCacheKey());
                let cache: CacheData = JSON.parse(cacheJson);
                if (cache) {
                    vm.datas = cache.datas;
                    vm.empItems = cache.empItems;
                    vm.empSearchItems = cache.empSearchItems;
                    vm.canNextScreen(vm.empItems.length > 0);
                    vm.appTypeSelected(cache.appType);
                } else {
                    vm.appTypeSelected(common.AppTypeEnum.NEXT_MONTH);
                }
            } else {
                vm.appTypeSelected(common.AppTypeEnum.NEXT_MONTH);
            }

            vm.initData().done(() => {
                vm.loadMGrid();
                $('#A1_5').focus();
                vm.appTypeSelected.subscribe((value: common.AppTypeEnum) => {
                    vm.fetchData().done(() => {
                        $("#grid").mGrid("destroy");
                        vm.loadMGrid();
                    });
                })

                // Reload when back from screen B (Register success)
                if (vm.isReload) {
                    vm.appTypeSelected.valueHasMutated();
                }
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
            vm.$ajax(API.INIT).done((data: StartupInfo) => {
                vm.processingMonth = data.processingMonth;
                vm.setting = data.setting;

                if (!vm.setting.useSpecical) {
                    vm.$dialog.error({ messageId: "Msg_1843" }).done(() => {
                        vm.$jump('com', '/view/ccg/008/a/index.xhtml');
                    });
                    vm.$blockui("clear");
                    return;
                }

                let date = new Date(formatYearMonth(vm.processingMonth));
                let currentMonth = date.getMonth() + 1;
                date.setMonth(currentMonth);
                let nextMonth = date.getMonth() + 1;

                vm.appTypes.push(new AppType(common.AppTypeEnum.CURRENT_MONTH, textFormat(vm.$i18n("KAF021_64"), currentMonth)));
                vm.appTypes.push(new AppType(common.AppTypeEnum.NEXT_MONTH, textFormat(vm.$i18n("KAF021_64"), nextMonth)));
                if (vm.setting.useYear) {
                    vm.appTypes.push(new AppType(common.AppTypeEnum.YEARLY, vm.$i18n("KAF021_4")));
                }
                vm.appTypeSelected.valueHasMutated();

                vm.$blockui("clear");
                dfd.resolve();
            }).fail((error: any) => {
                vm.$blockui("clear");
                vm.$dialog.error(error);
            });

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
                        vm.datas = vm.convertData(data);
                        dfd.resolve();
                    }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
                    break;
                case common.AppTypeEnum.NEXT_MONTH:
                    vm.$ajax(API.NEXT_MONTH, { employees: vm.empSearchItems }).done((data: any) => {
                        vm.datas = vm.convertData(data);
                        dfd.resolve();
                    }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
                    break;
                case common.AppTypeEnum.YEARLY:
                    vm.$ajax(API.YEAR, { employees: vm.empSearchItems }).done((data: any) => {
                        vm.datas = vm.convertData(data);
                        dfd.resolve();
                    }).fail((error: any) => vm.$dialog.error(error)).always(() => vm.$blockui("clear"));
                    break;
                default:
                    dfd.resolve();
                    vm.$blockui("clear");
            }
            return dfd.promise();
        }

        convertData(data: Array<IEmployeeAgreementTime>): Array<EmployeeAgreementTime> {
            const vm = this;
            let results: Array<EmployeeAgreementTime> = [];
            _.each(data, (item: IEmployeeAgreementTime) => {
                let result = new EmployeeAgreementTime(item)
                result.statusStr = result.isApplying ? vm.$i18n("KAF021_73") : vm.$i18n("KAF021_72");
                results.push(result);
            })
            return results
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
                        onChange: function (rowId: any, columnKey: any, value: any, rowData: any) {
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
                                columnKey: "statusStr",
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
            columns.push({ headerText: vm.$i18n("KAF021_7"), key: 'statusStr', dataType: 'string', width: '60px', ntsControl: "Label" });
            // A3_3
            columns.push({ headerText: vm.$i18n("KAF021_8"), key: 'wkpName', dataType: 'string', width: '100px', ntsControl: "Label" });
            // A3_4
            columns.push({ headerText: vm.$i18n("KAF021_9"), key: 'employeeName', dataType: 'string', width: '160px', ntsControl: "Label" });
            // A3_5 ~ A3_16
            let date: Date = vm.$date.today();
            date.setDate(1);
            date.setMonth(vm.setting.startingMonth - 1);
            for (let i = 0; i < 12; i++) {
                date.setMonth(vm.setting.startingMonth - 1 + i);
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
            _.forEach(vm.datas, (data: EmployeeAgreementTime) => {
                if (data.isApplying) {
                    cellStates.push(new common.CellState(data.employeeId, 'checked', ["center-align", nts.uk.ui.mgrid.color.Disable]));
                } else {
                    cellStates.push(new common.CellState(data.employeeId, 'checked', ["center-align"]));
                }
                cellStates.push(new common.CellState(data.employeeId, 'statusStr', ["center-align"]));
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
            let date = new Date(formatYearMonth(vm.processingMonth));
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
                    return ["bg-alarm", "cl-alarm"];
                case common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR:
                case common.AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR:
                    return ["bg-error", "cl-error"];
                case common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP:
                case common.AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP:
                    return ["bg-special", "cl-special"];
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
            let dataAll: Array<EmployeeAgreementTime> = $("#grid").mGrid("dataSource", true);
            let dataSelected = _.filter(dataAll, (item: EmployeeAgreementTime) => { return item.checked });
            let cache = new CacheData(vm.appTypeSelected(), dataAll, vm.empItems, vm.empSearchItems);
            localStorage.setItem(vm.getCacheKey(), JSON.stringify(cache));
            vm.$jump('at', '/view/kaf/021/b/index.xhtml', {
                datas: dataSelected,
                appType: vm.appTypeSelected(),
                processingMonth: vm.processingMonth
            });
        }

        getCacheKey() {
            return __viewContext.user.companyCode + "_" + __viewContext.user.employeeId + "_kaf021a_cache";
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
        datas: Array<EmployeeAgreementTime>;
        empItems: Array<string> = [];
        empSearchItems: Array<EmployeeSearchDto> = [];

        constructor(appType: common.AppTypeEnum, datas: Array<EmployeeAgreementTime>, empItems: Array<string> = [], empSearchItems: Array<EmployeeSearchDto>) {
            this.appType = appType;
            this.datas = datas;
            this.empItems = empItems;
            this.empSearchItems = empSearchItems;
        }
    }

    interface StartupInfo {
        setting: common.AgreementOperationSettingDto;
        processingMonth: number;
    }

    class AppType {
        value: common.AppTypeEnum;
        name: string;

        constructor(value: common.AppTypeEnum, name: string) {
            this.value = value;
            this.name = name;
        }
    }

    class EmployeeAgreementTime {
        employeeId: string;
        checked: boolean;
        status: common.ApprovalStatusEnum;
        statusStr: string;
        isApplying: boolean;
        wkpName: string;
        employeeName: string;

        month1Str: any;
        month1Time: any;
        month1MaxTime: any;
        month1UpperLimit: any;
        month1TimeStr: any;
        month1MaxTimeStr: any;
        month1UpperLimitStr: any;
        month1Status: common.AgreementTimeStatusOfMonthly;
        month1Error: any;
        month1Alarm: any;

        month2Str: any;
        month2Time: any;
        month2MaxTime: any;
        month2UpperLimit: any;
        month2TimeStr: any;
        month2MaxTimeStr: any;
        month2UpperLimitStr: any;
        month2Status: common.AgreementTimeStatusOfMonthly;
        month2Error: any;
        month2Alarm: any;

        month3Str: any;
        month3Time: any;
        month3MaxTime: any;
        month3UpperLimit: any;
        month3TimeStr: any;
        month3MaxTimeStr: any;
        month3UpperLimitStr: any;
        month3Status: common.AgreementTimeStatusOfMonthly;
        month3Error: any;
        month3Alarm: any;

        month4Str: any;
        month4Time: any;
        month4MaxTime: any;
        month4UpperLimit: any;
        month4TimeStr: any;
        month4MaxTimeStr: any;
        month4UpperLimitStr: any;
        month4Status: common.AgreementTimeStatusOfMonthly;
        month4Error: any;
        month4Alarm: any;

        month5Str: any;
        month5Time: any;
        month5MaxTime: any;
        month5UpperLimit: any;
        month5TimeStr: any;
        month5MaxTimeStr: any;
        month5UpperLimitStr: any;
        month5Status: common.AgreementTimeStatusOfMonthly;
        month5Error: any;
        month5Alarm: any;

        month6Str: any;
        month6Time: any;
        month6MaxTime: any;
        month6UpperLimit: any;
        month6TimeStr: any;
        month6MaxTimeStr: any;
        month6UpperLimitStr: any;
        month6Status: common.AgreementTimeStatusOfMonthly;
        month6Error: any;
        month6Alarm: any;

        month7Str: any;
        month7Time: any;
        month7MaxTime: any;
        month7UpperLimit: any;
        month7TimeStr: any;
        month7MaxTimeStr: any;
        month7UpperLimitStr: any;
        month7Status: common.AgreementTimeStatusOfMonthly;
        month7Error: any;
        month7Alarm: any;

        month8Str: any;
        month8Time: any;
        month8MaxTime: any;
        month8UpperLimit: any;
        month8TimeStr: any;
        month8MaxTimeStr: any;
        month8UpperLimitStr: any;
        month8Status: common.AgreementTimeStatusOfMonthly;
        month8Error: any;
        month8Alarm: any;

        month9Str: any;
        month9Time: any;
        month9MaxTime: any;
        month9UpperLimit: any;
        month9TimeStr: any;
        month9MaxTimeStr: any;
        month9UpperLimitStr: any;
        month9Status: common.AgreementTimeStatusOfMonthly;
        month9Error: any;
        month9Alarm: any;

        month10Str: any;
        month10Time: any;
        month10MaxTime: any;
        month10UpperLimit: any;
        month10TimeStr: any;
        month10MaxTimeStr: any;
        month10UpperLimitStr: any;
        month10Status: common.AgreementTimeStatusOfMonthly;
        month10Error: any;
        month10Alarm: any;

        month11Str: any;
        month11Time: any;
        month11MaxTime: any;
        month11UpperLimit: any;
        month11TimeStr: any;
        month11MaxTimeStr: any;
        month11UpperLimitStr: any;
        month11Status: common.AgreementTimeStatusOfMonthly;
        month11Error: any;
        month11Alarm: any;

        month12Str: any;
        month12Time: any;
        month12MaxTime: any;
        month12UpperLimit: any;
        month12TimeStr: any;
        month12MaxTimeStr: any;
        month12UpperLimitStr: any;
        month12Status: common.AgreementTimeStatusOfMonthly;
        month12Error: any;
        month12Alarm: any;

        year: any;
        yearStr: any;
        yearTime: any;
        yearMaxTime: any;
        yearUpperLimit: any;
        yearTimeStr: any;
        yearMaxTimeStr: any;
        yearUpperLimitStr: any;
        yearStatus: common.AgreTimeYearStatusOfMonthly
        yearError: any;
        yearAlarm: any;

        monthAverage2: any;
        monthAverage2Str: any;
        monthAverage2Status: common.AgreMaxTimeStatusOfMonthly

        monthAverage3: any;
        monthAverage3Str: any;
        monthAverage3Status: common.AgreMaxTimeStatusOfMonthly

        monthAverage4: any;
        monthAverage4Str: any;
        monthAverage4Status: common.AgreMaxTimeStatusOfMonthly

        monthAverage5: any;
        monthAverage5Str: any;
        monthAverage5Status: common.AgreMaxTimeStatusOfMonthly

        monthAverage6: any;
        monthAverage6Str: any;
        monthAverage6Status: common.AgreMaxTimeStatusOfMonthly

        exceededNumber: number;

        constructor(data: IEmployeeAgreementTime) {
            this.employeeId = data.employeeId;
            this.checked = false;
            this.status = data.status;
            this.isApplying = this.status == common.ApprovalStatusEnum.UNAPPROVED || this.status == common.ApprovalStatusEnum.DENY;
            this.wkpName = data.affiliationName;
            this.employeeName = data.employeeCode + "　" + data.employeeName;

            this.month1Time = data.month1?.time?.time;
            this.month1MaxTime = data.month1?.maxTime?.time;
            this.month1UpperLimit = data.month1?.time.maxTime;
            this.month1TimeStr = parseTime(this.month1Time, true).format();
            this.month1MaxTimeStr = parseTime(this.month1MaxTime, true).format();
            this.month1UpperLimitStr = parseTime(this.month1UpperLimit, true).format();
            this.month1Str = EmployeeAgreementTime.getCellTime(this.month1Time, this.month1MaxTime);
            this.month1Status = data.month1?.status;
            this.month1Error = data.month1?.time?.error;
            this.month1Alarm = data.month1?.time?.alarm;

            this.month2Time = data.month2?.time?.time;
            this.month2MaxTime = data.month2?.maxTime?.time;
            this.month2UpperLimit = data.month2?.time?.maxTime;
            this.month2TimeStr = parseTime(this.month2Time, true).format();
            this.month2MaxTimeStr = parseTime(this.month2MaxTime, true).format();
            this.month2UpperLimitStr = parseTime(this.month2UpperLimit, true).format();
            this.month2Str = EmployeeAgreementTime.getCellTime(this.month2Time, this.month2MaxTime);
            this.month2Status = data.month2?.status;
            this.month2Error = data.month2?.time?.error;
            this.month2Alarm = data.month2?.time?.alarm;

            this.month3Time = data.month3?.time?.time;
            this.month3MaxTime = data.month3?.maxTime?.time;
            this.month3UpperLimit = data.month3?.time?.maxTime;
            this.month3TimeStr = parseTime(this.month3Time, true).format();
            this.month3MaxTimeStr = parseTime(this.month3MaxTime, true).format();
            this.month3UpperLimitStr = parseTime(this.month3UpperLimit, true).format();
            this.month3Str = EmployeeAgreementTime.getCellTime(this.month3Time, this.month3MaxTime);
            this.month3Status = data.month3?.status;
            this.month3Error = data.month3?.time?.error;
            this.month3Alarm = data.month3?.time?.alarm;

            this.month4Time = data.month4?.time?.time;
            this.month4MaxTime = data.month4?.maxTime?.time;
            this.month4UpperLimit = data.month4?.time?.maxTime;
            this.month4TimeStr = parseTime(this.month4Time, true).format();
            this.month4MaxTimeStr = parseTime(this.month4MaxTime, true).format();
            this.month4UpperLimitStr = parseTime(this.month4UpperLimit, true).format();
            this.month4Str = EmployeeAgreementTime.getCellTime(this.month4Time, this.month4MaxTime);
            this.month4Status = data.month4?.status;
            this.month4Error = data.month4?.time?.error;
            this.month4Alarm = data.month4?.time?.alarm;

            this.month5Time = data.month5?.time?.time;
            this.month5MaxTime = data.month5?.maxTime?.time;
            this.month5UpperLimit = data.month5?.time?.maxTime;
            this.month5TimeStr = parseTime(this.month5Time, true).format();
            this.month5MaxTimeStr = parseTime(this.month5MaxTime, true).format();
            this.month5UpperLimitStr = parseTime(this.month5UpperLimit, true).format();
            this.month5Str = EmployeeAgreementTime.getCellTime(this.month5Time, this.month5MaxTime);
            this.month5Status = data.month5?.status;
            this.month5Error = data.month5?.time?.error;
            this.month5Alarm = data.month5?.time?.alarm;

            this.month6Time = data.month6?.time?.time;
            this.month6MaxTime = data.month6?.maxTime?.time;
            this.month6UpperLimit = data.month6?.time?.maxTime;
            this.month6TimeStr = parseTime(this.month6Time, true).format();
            this.month6MaxTimeStr = parseTime(this.month6MaxTime, true).format();
            this.month6UpperLimitStr = parseTime(this.month6UpperLimit, true).format();
            this.month6Str = EmployeeAgreementTime.getCellTime(this.month6Time, this.month6MaxTime);
            this.month6Status = data.month6?.status;
            this.month6Error = data.month6?.time?.error;
            this.month6Alarm = data.month6?.time?.alarm;

            this.month7Time = data.month7?.time?.time;
            this.month7MaxTime = data.month7?.maxTime?.time;
            this.month7UpperLimit = data.month7?.time?.maxTime;
            this.month7TimeStr = parseTime(this.month7Time, true).format();
            this.month7MaxTimeStr = parseTime(this.month7MaxTime, true).format();
            this.month7UpperLimitStr = parseTime(this.month7UpperLimit, true).format();
            this.month7Str = EmployeeAgreementTime.getCellTime(this.month7Time, this.month7MaxTime);
            this.month7Status = data.month7?.status;
            this.month7Error = data.month7?.time?.error;
            this.month7Alarm = data.month7?.time?.alarm;

            this.month8Time = data.month8?.time?.time;
            this.month8MaxTime = data.month8?.maxTime?.time;
            this.month8UpperLimit = data.month8?.time?.maxTime;
            this.month8TimeStr = parseTime(this.month8Time, true).format();
            this.month8MaxTimeStr = parseTime(this.month8MaxTime, true).format();
            this.month8UpperLimitStr = parseTime(this.month8UpperLimit, true).format();
            this.month8Str = EmployeeAgreementTime.getCellTime(this.month8Time, this.month8MaxTime);
            this.month8Status = data.month8?.status;
            this.month8Error = data.month8?.time?.error;
            this.month8Alarm = data.month8?.time?.alarm;

            this.month9Time = data.month9?.time?.time;
            this.month9MaxTime = data.month9?.maxTime?.time;
            this.month9UpperLimit = data.month9?.time?.maxTime;
            this.month9TimeStr = parseTime(this.month9Time, true).format();
            this.month9MaxTimeStr = parseTime(this.month9MaxTime, true).format();
            this.month9UpperLimitStr = parseTime(this.month9UpperLimit, true).format();
            this.month9Str = EmployeeAgreementTime.getCellTime(this.month9Time, this.month9MaxTime);
            this.month9Status = data.month9?.status;
            this.month9Error = data.month9?.time?.error;
            this.month9Alarm = data.month9?.time?.alarm;

            this.month10Time = data.month10?.time?.time;
            this.month10MaxTime = data.month10?.maxTime?.time;
            this.month10UpperLimit = data.month10?.time?.maxTime;
            this.month10TimeStr = parseTime(this.month10Time, true).format();
            this.month10MaxTimeStr = parseTime(this.month10MaxTime, true).format();
            this.month10UpperLimitStr = parseTime(this.month10UpperLimit, true).format();
            this.month10Str = EmployeeAgreementTime.getCellTime(this.month10Time, this.month10MaxTime);
            this.month10Status = data.month10?.status;
            this.month10Error = data.month10?.time?.error;
            this.month10Alarm = data.month10?.time?.alarm;

            this.month11Time = data.month11?.time?.time;
            this.month11MaxTime = data.month11?.maxTime?.time;
            this.month11UpperLimit = data.month11?.time?.maxTime;
            this.month11TimeStr = parseTime(this.month11Time, true).format();
            this.month11MaxTimeStr = parseTime(this.month11MaxTime, true).format();
            this.month11UpperLimitStr = parseTime(this.month11UpperLimit, true).format();
            this.month11Str = EmployeeAgreementTime.getCellTime(this.month11Time, this.month11MaxTime);
            this.month11Status = data.month11?.status;
            this.month11Error = data.month11?.time?.error;
            this.month11Alarm = data.month11?.time?.alarm;

            this.month12Time = data.month12?.time?.time;
            this.month12MaxTime = data.month12?.maxTime?.time;
            this.month12UpperLimit = data.month12?.time?.maxTime;
            this.month12TimeStr = parseTime(this.month12Time, true).format();
            this.month12MaxTimeStr = parseTime(this.month12MaxTime, true).format();
            this.month12UpperLimitStr = parseTime(this.month12UpperLimit, true).format();
            this.month12Str = EmployeeAgreementTime.getCellTime(this.month12Time, this.month12MaxTime);
            this.month12Status = data.month12?.status;
            this.month12Error = data.month12?.time?.error;
            this.month12Alarm = data.month12?.time?.alarm;

            this.year = data.year?.year;
            this.yearTime = data.year?.time?.time;
            this.yearMaxTime = data.year?.maxTime?.time;
            this.yearUpperLimit = data.year?.time?.maxTime;
            this.yearTimeStr = parseTime(this.yearTime, true).format();
            this.yearMaxTimeStr = parseTime(this.yearMaxTime, true).format();
            this.yearUpperLimitStr = parseTime(this.yearUpperLimit, true).format();
            this.yearStr = EmployeeAgreementTime.getCellTime(this.yearTime, this.yearMaxTime);
            this.yearStatus = data.year?.status;
            this.yearError = data.year?.time?.error;
            this.yearAlarm = data.year?.time?.alarm;

            this.monthAverage2 = data.monthAverage2?.time;
            this.monthAverage2Str = parseTime(this.monthAverage2, true).format();
            this.monthAverage3Status = data.monthAverage3?.status;

            this.monthAverage3 = data.monthAverage3?.time;
            this.monthAverage3Str = parseTime(this.monthAverage3, true).format();
            this.monthAverage3Status = data.monthAverage3?.status;

            this.monthAverage4 = data.monthAverage4?.time;
            this.monthAverage4Str = parseTime(this.monthAverage4, true).format();
            this.monthAverage4Status = data.monthAverage4?.status;

            this.monthAverage5 = data.monthAverage5?.time;
            this.monthAverage5Str = parseTime(this.monthAverage5, true).format();
            this.monthAverage5Status = data.monthAverage5?.status;

            this.monthAverage6 = data.monthAverage6?.time;
            this.monthAverage6Str = parseTime(this.monthAverage6, true).format();
            this.monthAverage6Status = data.monthAverage6?.status;

            this.exceededNumber = data.exceededNumber;
        }

        static getCellTime(time?: number, maxTime?: number) {
            let result = parseTime(time, true).format();
            if (maxTime != null) {
                result += "<br>(" + parseTime(maxTime, true).format() + ")";
            }
            return result;
        }
    }

    interface IEmployeeAgreementTime {
        /**
         * 社員ID
         */
        employeeId: string;
        /**
         * 申請状況
         */
        status: common.ApprovalStatusEnum;
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
         * 法定上限対象時間
         */
        maxTime: IAggreementTime;
        /**
         * 36協定対象時間
         */
        time: IAggreementTime;
        /**
         * 状態
         */
        status: common.AgreementTimeStatusOfMonthly;
    }

    interface IAgreementTimeYear {
        /**
         * 年間
         */
        year: number;
        /**
         * 法定上限対象時間
         */
        maxTime: IAggreementTime;
        /**
         * 36協定対象時間
         */
        time: IAggreementTime;
        /**
         * 状態
         */
        status: common.AgreTimeYearStatusOfMonthly;
    }

    interface IAggreementTime {
        /**
         * 上限時間
         */
        maxTime: number;
        /**
         * 36協定時間
         */
        time: number;
        /**
         * エラー時間
         */
        error: number;
        /**
         * アラーム時間
         */
        alarm: number;
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
        status: common.AgreMaxTimeStatusOfMonthly;
    }
}
