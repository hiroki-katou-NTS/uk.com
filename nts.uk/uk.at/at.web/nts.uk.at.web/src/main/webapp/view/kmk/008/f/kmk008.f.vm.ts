/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.f {
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;

    const PATH_API = {
        getAllMonth: "screen/at/kmk008/f/getAllMonthSetting",
        getAllYear: "screen/at/kmk008/f/getAllYearSetting",
    };

    @bean()
    export class KMK008FViewModel extends ko.ViewModel {
        selectedTab: KnockoutObservable<string> = ko.observable('tab-1');

        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        items2: KnockoutObservableArray<ItemModel2> = ko.observableArray([]);
        currentCode: KnockoutObservable<any> = ko.observable();

        isShowButton: KnockoutObservable<boolean> = ko.observable(true);

        employeeName: KnockoutObservable<string> = ko.observable("");
        selectedId: KnockoutObservable<string> = ko.observable("");

        //list
        listComponentOption: any;
        selectedEmpCode: KnockoutObservable<string> = ko.observable('');
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);

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
                maxRows: 12
            };

            vm.reloadCcg001();
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
                showDepartment: false, // 部門条件
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
                        vm.initData().done(() => {
                            vm.selectedEmpCode(vm.employeeList()[0].code);
                        });
                    }
                }
            }
        }

        initData() {
            const vm = this;
            let dfd = $.Deferred();

            let employeeIds = _.map(vm.employeeList(), item => {
                return item.employeeId;
            });

            vm.alreadySettingList([]);

            let oldUnitModel: UnitModel;
            let newUnitModel: UnitModel;
            let monthData: Array<ItemModel> = [];
            let yearData: Array<ItemModel> = [];
            let monthData2: Array<ItemModel2> = [];
            let yearData2: Array<ItemModel2> = [];
            let settingDtos: Array<any> = [];

            let monthQuery = vm.$ajax(PATH_API.getAllMonth, {employeeIds: employeeIds});
            let yearQuery = vm.$ajax(PATH_API.getAllYear, {employeeIds: employeeIds});

            vm.$blockui("invisible");
            $.when(monthQuery, yearQuery)
                .done((monthDataList: any, yearDataList: any) => {
                    _.forEach(employeeIds, (value) => {
                        monthData = [];
                        yearData = [];
                        monthData2 = [];
                        yearData2 = [];

                        oldUnitModel = _.find(vm.employeeList(), item => {
                            return item.employeeId === value;
                        });

                        settingDtos = _.find(monthDataList, item => {
                            return item.employeeId === value;
                        }).settingDtos;
                        settingDtos = _.orderBy(settingDtos, ['yearMonthValue'], ['desc']);
                        if (settingDtos && settingDtos.length) {
                            _.forEach(settingDtos, (value) => {
                                monthData.push(new ItemModel(
                                    nts.uk.time.parseYearMonth(value.yearMonthValue).format(),
                                    nts.uk.time.parseTime(value.errorOneMonth, true).format(),
                                    nts.uk.time.parseTime(value.alarmOneMonth, true).format()));

                                monthData2.push(new ItemModel2(value.yearMonthValue, value.errorOneMonth, value.alarmOneMonth));
                            });
                        }

                        settingDtos = _.find(yearDataList, item => {
                            return item.employeeId === value;
                        }).settingDtos;
                        settingDtos = _.orderBy(settingDtos, ['yearValue'], ['desc']);
                        if (settingDtos && settingDtos.length) {
                            _.forEach(settingDtos, (value) => {
                                yearData.push(new ItemModel(
                                    value.yearValue.toString(),
                                    nts.uk.time.parseTime(value.errorOneYear, true).format(),
                                    nts.uk.time.parseTime(value.alarmOneYear, true).format()));

                                yearData2.push(new ItemModel2(value.yearValue, value.errorOneYear, value.alarmOneYear));
                            });
                        }

                        newUnitModel = new UnitModel(
                            oldUnitModel.code,
                            oldUnitModel.name,
                            oldUnitModel.affiliationName,
                            oldUnitModel.employeeId,
                            monthData,
                            yearData,
                            monthData2,
                            yearData2
                        );

                        vm.employeeList.replace(oldUnitModel, newUnitModel);
                        vm.alreadySettingList.push(new UnitAlreadySettingModel(oldUnitModel.code, monthData2.length > 0 || yearData2.length > 0));
                    });

                    dfd.resolve();
                })
                .fail((err1: any, err2: any) => {
                    if (err1) {
                        vm.$dialog.error(err1.message).then(() => {
                        });
                    }
                    if (err2) {
                        vm.$dialog.error(err1.message).then(() => {
                        });
                    }
                    dfd.reject();
                })
                .always(() => vm.$blockui("clear"));

            return dfd.promise();
        }

        created() {
            const vm = this;
            vm.selectedTab.subscribe(x => {
                if (vm.selectedId()) {
                    return vm.getDetail(vm.selectedEmpCode());
                } else {
                    vm.items([]);
                    vm.items2([]);
                }
            });

            vm.selectedEmpCode.subscribe(newValue => {
                if (!nts.uk.text.isNullOrEmpty(newValue)) {
                    vm.getDetail(vm.selectedEmpCode());
                } else {
                    vm.selectedId("");
                    vm.employeeName("");
                    vm.items([]);
                    vm.items2([]);
                }
            });

            if (!vm.selectedEmpCode()) {
                vm.isShowButton(false);
            }

            $('#ccgcomponent').ntsGroupComponent(vm.ccg001ComponentOption);
            $('#component-items-list').ntsListComponent(vm.listComponentOption);

            _.extend(window, {vm});
        }

        openDiaglog() {
            const vm = this;
            const data = {
                employeeCode: vm.selectedEmpCode(),
                employeeId: vm.selectedId(),
                employeeName: vm.employeeName(),
                isYearMonth: vm.selectedTab() == "tab-2",
                itemData: vm.items2()
            };

            vm.$window.modal('../../../kmk/008/k/index.xhtml', data).then(() => {
                vm.initData().done(() => {
                    if (vm.selectedEmpCode()) {
                    vm.getDetail(vm.selectedEmpCode());
                    }
                });
            });
        }

        getDetail(employmentCategoryCode: string) {
            const vm = this;
            vm.isShowButton(true);

            let employeeModel: UnitModel = _.find(vm.employeeList(), item => {
                return item.code === employmentCategoryCode;
            });

            vm.selectedId(employeeModel.employeeId);
            vm.employeeName(employeeModel.name);

            vm.items([]);
            vm.items2([]);
            if (vm.selectedTab() == "tab-2") {
                vm.items(employeeModel.monthSettingList);
                vm.items2(employeeModel.monthSettingList2);
            } else {
                vm.items(employeeModel.yearSettingList);
                vm.items2(employeeModel.yearSettingList2);
            }
        }

        close() {
            const vm = this;
            vm.$window.close();
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

        monthSettingList: Array<ItemModel>;
        yearSettingList: Array<ItemModel>;
        monthSettingList2: Array<ItemModel2>;
        yearSettingList2: Array<ItemModel2>;

        constructor(code: string, name: string,
                    affiliationName: string,
                    employeeId: string,
                    monthSettingList: Array<ItemModel> = [],
                    yearSettingList: Array<ItemModel> = [],
                    monthSettingList2: Array<ItemModel2> = [],
                    yearSettingList2: Array<ItemModel2> = []) {
            this.code = code;
            this.name = name;
            this.affiliationName = affiliationName;
            this.employeeId = employeeId;

            this.monthSettingList = monthSettingList;
            this.yearSettingList = yearSettingList;
            this.monthSettingList2 = monthSettingList2;
            this.yearSettingList2 = yearSettingList2;
        }
    }

    export class UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;

        constructor(code: string, isAlreadySetting: boolean) {
            this.code = code;
            this.isAlreadySetting = isAlreadySetting;
        }
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
