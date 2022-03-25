/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.test {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import util = nts.uk.util;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;

    @bean()
    export class ViewModel extends ko.ViewModel {
        currentScreen: any = null;
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        enable: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;

        //Declare kcp005 list properties
        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel>;
        // startDate for validate
        startDateValidate: KnockoutObservable<string>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;

        //Declare employee filter component
        ccg001ComponentOption: any;
        showinfoSelectedEmployee: KnockoutObservable<boolean> = ko.observable(false);
        // Options
        baseDate: KnockoutObservable<Date>;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);

        orgUnit: KnockoutObservable<number> = ko.observable(0);
        orgId: KnockoutObservable<string> = ko.observable("");
        orgCode: KnockoutObservable<string> = ko.observable("");
        targetOrganizationName: KnockoutObservable<string> = ko.observable("");
        selectedWkpId: KnockoutObservable<string> = ko.observable(null);
        selectedWkpGroupId: KnockoutObservable<string> = ko.observable(null);

        enableOpenKdl016: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            super();
            var self = this;
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable(null);
            self.multiSelectedCode = ko.observableArray([]);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                {code: '1', isAlreadySetting: true},
                {code: '2', isAlreadySetting: true}
            ]);
            self.isDialog = ko.observable(true);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(true);
            self.isShowWorkPlaceName = ko.observable(true);
            self.isShowSelectAllButton = ko.observable(true);

            this.employeeList = ko.observableArray<UnitModel>([]);

            self.enable = ko.observable(true);
            self.required = ko.observable(true);
            self.startDateString = ko.observable("2020/10/01");
            self.endDateString = ko.observable("2020/10/31");
            self.dateValue = ko.observable({startDate: new Date(), endDate: new Date()});
            self.startDateString.subscribe(function (value) {
                self.dateValue().startDate = value;
                self.dateValue.valueHasMutated();
            });

            self.endDateString.subscribe(function (value) {
                self.dateValue().endDate = value;
                self.dateValue.valueHasMutated();
            });

            let ccg001ComponentOption: any = {

                /** Common properties */
                systemType: 2, // システム区分
                showEmployeeSelection: false, // 検索タイプ
                showQuickSearchTab: true, // クイック検索
                showAdvancedSearchTab: true, // 詳細検索
                showBaseDate: false, // 基準日利用
                showClosure: true, // 就業締め日利用
                showAllClosure: false, // 全締め表示
                showPeriod: true, // 対象期間利用
                periodFormatYM: false, // 対象期間精度

                /** Required parameter */
                baseDate: moment().toISOString(), // 基準日
                //periodEndDate: self.dateValue().endDate,
                dateRangePickerValue: self.dateValue,
                inService: true, // 在職区分
                leaveOfAbsence: true, // 休職区分
                closed: true, // 休業区分
                retirement: false, // 退職区分

                /** Quick search tab options */
                showAllReferableEmployee: true, // 参照可能な社員すべて
                showOnlyMe: true, // 自分だけ
                showSameWorkplace: true, // 同じ職場の社員
                showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment: false, // 雇用条件
                showWorkplace: true, // 職場条件
                showClassification: true, // 分類条件
                showJobTitle: true, // 職位条件
                showWorktype: true, // 勤種条件
                isMutipleCheck: true, // 選択モード

                /** Return data */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    self.showinfoSelectedEmployee(true);
                    self.selectedEmployee(data.listEmployee);

                    let items = _.map(data.listEmployee, item => {
                        return {
                            id: item.employeeId,
                            code: item.employeeCode,
                            name: item.employeeName,
                            affiliationName: item.affiliationName,
                            isAlreadySetting: true
                        }
                    });
                    self.employeeList(items);


                    let selectList = _.map(data.listEmployee, item => {
                        return item.employeeId;
                    });
                    self.multiSelectedCode(selectList);
                }
            };

            let listComponentOption: any = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_ALL,
                selectedCode: self.selectedCode,
                isDialog: false,
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: false,
                maxRows: 10,
                isSelectAllAfterReload: true
            };

            $('#ccgcomponent').ntsGroupComponent(ccg001ComponentOption);
            $('#component-items-list').ntsListComponent(listComponentOption);

            self.employeeList.subscribe((value) => {
                if(!util.isNullOrEmpty(self.targetOrganizationName())) {
                    self.enableOpenKdl016(true);
                }
            })
            self.targetOrganizationName.subscribe((value) => {
                if(!util.isNullOrEmpty(self.employeeList())) {
                    self.enableOpenKdl016(true);
                }
            })

        }

        openDialog(): void {
            let vm = this;

            let empIds: Array<string> = [];
            _.forEach(vm.selectedCode(), code => {
                _.map(vm.employeeList(), item => {
                    if (item.code === code) {
                        empIds.push(item.id);
                    }
                });
            });

            let request: IScreenParameter = {
                targetOrg: {
                    id: vm.orgId(),
                    code: vm.orgCode(),
                    unit: vm.orgUnit(),
                    name: vm.targetOrganizationName()
                },
                startDate: moment(vm.dateValue().startDate).format('YYYY/MM/DD'),
                endDate: moment(vm.dateValue().endDate).format('YYYY/MM/DD'),
                employeeIds: empIds
            };

            vm.$window.modal("/view/kdl/016/a/index.xhtml", request).then((rs: any) => {
                let result = getShared('status-result');
                if (!_.isNil(result) && result == true) {

                } else {
                    nts.uk.characteristics.restore('kdl016Status').done((data: any) => {
                        if (!_.isNil(data)) {
                            if (data.reloadable) {

                            }
                            nts.uk.characteristics.remove("kdl016Status");
                        }
                    });
                }
            });
        }

        openKDL046(): void {
            let vm = this;
            let param = {
                unit: vm.orgUnit(),
                date: moment(vm.dateValue().endDate),
                workplaceId: vm.selectedWkpId(),
                workplaceGroupId: vm.selectedWkpGroupId(),
                showBaseDate: false
            };
            setShared('dataShareDialog046', param);
            vm.$window.modal('/view/kdl/046/a/index.xhtml').then(() => {
                let dataFrom046 = getShared('dataShareKDL046');
                if (!_.isNil(dataFrom046)) {
                    vm.orgUnit(dataFrom046.unit);
                    vm.orgId(dataFrom046.unit === 0 ? dataFrom046.workplaceId : dataFrom046.workplaceGroupID);
                    vm.orgCode(dataFrom046.unit === 0 ? dataFrom046.workplaceCode : dataFrom046.workplaceGroupCode);
                    vm.targetOrganizationName(dataFrom046.unit === 0 ? dataFrom046.workplaceName : dataFrom046.workplaceGroupName);
                }
            });
        }

        created(params: any) {
            const vm = this;

        }

        mounted() {
            const vm = this;
            $('#ccgcomponent').focus();
        }
    }

    interface IScreenParameter {
        targetOrg: ITargetOrganization;
        startDate: string;
        endDate: string;
        employeeIds: string[]
    }

    interface ITargetOrganization {
        id: string;
        code: string;
        unit: number;
        name: string;
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export class UnitModel {
        id: string;
        code: string;
        name: string;
        affiliationName: string;
        isAlreadySetting: boolean;

        constructor(x: EmployeeSearchDto) {
            let self = this;
            if (x) {
                self.code = x.employeeCode;
                self.name = x.employeeName;
                self.affiliationName = x.workplaceName;
                self.isAlreadySetting = false;
            } else {
                self.code = "";
                self.name = "";
                self.affiliationName = "";
                self.isAlreadySetting = false;
            }
        }
    }
}
