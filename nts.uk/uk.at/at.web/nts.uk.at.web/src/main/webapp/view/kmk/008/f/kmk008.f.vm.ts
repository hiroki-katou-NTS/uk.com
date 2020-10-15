/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.f {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;

    const PATH_API = {
        getMonth: "screen/at/kmk008/f/getMonthSetting",
        getYear: "screen/at/kmk008/f/getYearSetting",
    };

    @bean()
    export class KMK008FViewModel extends ko.ViewModel {
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string> = ko.observable('tab-1');

        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        items2: KnockoutObservableArray<ItemModel2> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        columns2: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any> = ko.observable();

        isShowButton: KnockoutObservable<boolean> = ko.observable(true);

        employeeName: KnockoutObservable<string> = ko.observable("");
        selectedId: KnockoutObservable<string> = ko.observable("");

        //list
        listComponentOption: any;
        selectedEmpCode: KnockoutObservable<string> = ko.observable('');
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray<UnitAlreadySettingModel>([]);

        //search
        ccg001ComponentOption: any;
        showInfoSelectedEmployee: KnockoutObservable<boolean> = ko.observable(false);

        // Options
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);

        constructor() {
            super();
            const vm = this;
            //list
            vm.listComponentOption = {
                isShowAlreadySet: true,
                alreadySettingList: vm.alreadySettingList,
                isMultiSelect: false,
                listType: 4,
                employeeInputList: vm.employeeList,
                selectType: 1,
                selectedCode: vm.selectedEmpCode,
                isDialog: false,
                isShowNoSelectRow: false,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: false,
                disableSelection: false,
                maxRows: 12,
                tabindex: 4
            };

            vm.reloadCcg001();

            vm.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: vm.$i18n("KMK008_29"),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: vm.$i18n("KMK008_30"),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                }
            ]);
            vm.selectedTab.subscribe(x => {
                if (vm.selectedId()) {
                    return vm.getDetail(vm.selectedId());
                } else {
                    vm.items([]);
                    vm.items2([]);
                }
            });

            vm.columns = ko.observableArray([
                {headerText: vm.$i18n("KMK008_29"), key: 'year', width: 100},
                {headerText: vm.$i18n("KMK008_19"), key: 'error', width: 150},
                {headerText: vm.$i18n("KMK008_20"), key: 'alarm', width: 150}
            ]);
            vm.columns2 = ko.observableArray([

                {headerText: vm.$i18n("KMK008_30"), key: 'year', width: 100},
                {headerText: vm.$i18n("KMK008_19"), key: 'error', width: 150},
                {headerText: vm.$i18n("KMK008_20"), key: 'alarm', width: 150}
            ]);

            vm.selectedEmpCode.subscribe(newValue => {
                if (!nts.uk.text.isNullOrEmpty(newValue)) {
                    let data = vm.selectedEmployee();
                    let employee = _.find(data, function (o) {
                        return o.employeeCode == vm.selectedEmpCode();
                    });
                    vm.getDetail(employee.employeeId);
                    vm.selectedId(employee.employeeId);
                    vm.employeeName(employee.employeeName);
                } else {
                    vm.selectedId("");
                    vm.employeeName("");
                    vm.items([]);
                    vm.items2([]);
                }
            });
        }

        reloadCcg001() {
            const vm = this;

            vm.ccg001ComponentOption = {
                /** Common properties */
                systemType: 2, // システム区分
                showEmployeeSelection: false, // 検索タイプ
                showQuickSearchTab: false, // クイック検索
                showAdvancedSearchTab: true, // 詳細検索
                showBaseDate: true, // 基準日利用
                showClosure: false, // 就業締め日利用
                showAllClosure: false, // 全締め表示
                showPeriod: false, // 対象期間利用
                periodFormatYM: true, // 対象期間精度

                /** Required parameter */
                baseDate: moment().toISOString(), // 基準日
                inService: true, // 在職区分
                leaveOfAbsence: false, // 休職区分
                closed: false, // 休業区分
                retirement: false, // 退職区分

                /** Quick search tab options */
                showAllReferableEmployee: false, // 参照可能な社員すべて
                showOnlyMe: false, // 自分だけ
                showSameWorkplace: false, // 同じ職場の社員
                showSameWorkplaceAndChild: false, // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment: true, // 雇用条件
                showWorkplace: true, // 職場条件
                showClassification: true, // 分類条件
                showJobTitle: true, // 職位条件
                showWorktype: true, // 勤種条件
                isMutipleCheck: true, // 選択モード

                /** Return data */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    vm.selectedEmployee(data.listEmployee);
                    vm.showInfoSelectedEmployee(true);
                    vm.employeeList([]);
                    vm.employeeList(_.map(data.listEmployee, item => {
                        return new UnitModel(item.employeeCode, item.employeeName, item.affiliationName, item.employeeId);
                    }));
                    if (vm.employeeList() && vm.employeeList().length > 0) {
                        vm.selectedEmpCode(vm.employeeList()[0].code);
                    }
                }
            }
        }

        created() {
            const vm = this;

            if (!vm.selectedEmpCode()) {
                vm.isShowButton(false);
            }

            $('#ccgcomponent').ntsGroupComponent(vm.ccg001ComponentOption);
            $('#component-items-list').ntsListComponent(vm.listComponentOption);

            _.extend(window, {vm});
        }

        openDiaglog() {
            const vm = this;

            setShared("KMK_008_PARAMS", {
                employeeCode: vm.selectedEmpCode(),
                employeeId: vm.selectedId(),
                employeeName: vm.employeeName(),
                isYearMonth: vm.selectedTab() == "tab-2"
            });

            setShared("KMK_008_DATA", vm.items2);

            modal('../../../kmk/008/k/index.xhtml').onClosed(() => {
                if (vm.selectedId()) {
                    vm.getDetail(vm.selectedId());
                }
            });
        }

        getDetail(employmentCategoryCode: string) {
            const vm = this;
            vm.isShowButton(true);

            vm.$blockui("invisible");

            if (vm.selectedTab() == "tab-2") {
                vm.$ajax(PATH_API.getMonth, {employeeId: employmentCategoryCode})
                    .done(function (monthData: Array<MonthDto>) {
                        vm.items([]);
                        vm.items2([]);
                        if (monthData && monthData.length) {
                            _.forEach(monthData, function (value) {
                                vm.items.push(new ItemModel(nts.uk.time.parseYearMonth(value.yearMonthValue).format(), nts.uk.time.parseTime(value.errorOneMonth, true).format(), nts.uk.time.parseTime(value.alarmOneMonth, true).format()));
                                vm.items2.push(new ItemModel2(value.yearMonthValue, value.errorOneMonth, value.alarmOneMonth));
                            });
                        }
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            } else {
                vm.$ajax(PATH_API.getYear, {employeeId: employmentCategoryCode})
                    .done(function (yearData: Array<YearDto>) {
                        vm.items([]);
                        vm.items2([]);
                        if (yearData && yearData.length) {
                            _.forEach(yearData, function (value) {
                                vm.items.push(new ItemModel(value.yearValue.toString(), nts.uk.time.parseTime(value.errorOneYear, true).format(), nts.uk.time.parseTime(value.alarmOneYear, true).format()));
                                vm.items2.push(new ItemModel2(value.yearValue, value.errorOneYear, value.alarmOneYear));
                            });
                        }
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            }
        }
    }

    export class ItemModel {
        year: string;
        error: string;
        alarm: string;

        constructor(year: string, error: string, alarm: string) {
            this.year = year;
            this.error = error;
            this.alarm = alarm;
        }
    }

    export class ItemModel2 {
        year: number;
        error: number;
        alarm: number;

        constructor(year: number, error: number, alarm: number) {
            this.year = year;
            this.error = error;
            this.alarm = alarm;
        }
    }

    export class UnitModel {
        code: string;
        name: string;
        affiliationName: string;
        employeeId: string;

        constructor(code: string, name: string, affiliationName: string, employeeId: string) {
            this.code = code;
            this.name = name;
            this.affiliationName = affiliationName;
            this.employeeId = employeeId;
        }
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }

    export class MonthDto {
        yearMonthValue: number; //３６協定年月設定.年月
        errorOneMonth: number; //３６協定年月設定.1ヶ月のエラーアラーム時間.エラー時間
        alarmOneMonth: number; //３６協定年月設定.1ヶ月のエラーアラーム時間.アラーム時間
    }

    export class YearDto {
        yearValue: number; //３６協定年度設定.年度
        errorOneYear: number; //３６協定年度設定.1年間のエラーアラーム時間.エラー時間
        alarmOneYear: number; //３６協定年度設定.1年間のエラーアラーム時間.アラーム時間
    }
}
