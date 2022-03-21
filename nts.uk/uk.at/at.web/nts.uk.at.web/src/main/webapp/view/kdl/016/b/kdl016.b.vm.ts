/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.b {
    const API = {
        get: "screen/at/kdl016/b/init",
        register: "screen/at/kdl016/register"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        requiredParam: IParameter;
        organizationInfoList: any[] = [];

        // kcp005
        listComponentOption: any;
        startDate: KnockoutObservable<string> = ko.observable(undefined);
        endDate: KnockoutObservable<string> = ko.observable(undefined);
        enableEndDate: KnockoutObservable<boolean> = ko.observable(true);
        employeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedEmployees: KnockoutObservableArray<string> = ko.observableArray([]);

        // combobox
        orgList: KnockoutObservableArray<OrgItemModel> = ko.observableArray([]);
        selectedOrgCode: KnockoutObservable<string> = ko.observable(undefined);

        // RadioBtn
        supportTypes: KnockoutObservableArray<any>;
        selectedSupportType: KnockoutObservable<number>;

        enableEditTimespan: KnockoutObservable<boolean> = ko.observable(false);
        timespanMin: KnockoutObservable<number> = ko.observable(undefined);
        timespanMax: KnockoutObservable<number> = ko.observable(undefined);

        constructor(params: any) {
            super();
            const vm = this;

            vm.selectedSupportType = ko.observable(0);
            vm.selectedSupportType.subscribe(value => {
                if (value == 1) {
                    vm.enableEditTimespan(true);
                    vm.enableEndDate(false);
                    vm.endDate(undefined);
                    $('#timespanMin').focus();
                }
                else {
                    vm.enableEditTimespan(false);
                    vm.enableEndDate(true);
                    vm.$errors("clear");
                }
            });

            vm.timespanMax.subscribe((value) => {
                vm.validateTimeSpanMax(value);
            });
            vm.timespanMin.subscribe((value) => {
                vm.validateTimeSpanMin(value);
            });

            vm.endDate.subscribe((value: any) => {
                vm.validateEndDate(value);
            });

            vm.startDate.subscribe((value: any) => {
                vm.validateStartDate(value);
            });

            vm.selectedEmployees.subscribe((value) => {
                if(!_.isEmpty(value))
                    vm.$errors("clear", "#employee-list")
            });
        }

        created(params: IParameter) {
            const vm = this;
            if (!_.isNil(params)) {
                vm.requiredParam = params;
                vm.supportTypes = ko.observableArray([
                    new BoxModel(0, vm.$i18n('KDL016_37'), true),
                    new BoxModel(1, vm.$i18n('KDL016_38'), params.enableSupportTimezone)
                ]);
            }

            vm.loadData();

            vm.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: 4, // Employee
                employeeInputList: vm.employeeList,
                selectedCode: vm.selectedEmployees,
                maxRows: 8,
                isDialog: true,
                hasPadding: false
            };
        }

        mounted() {
            const vm = this;
            $('#employee-list').ntsListComponent(vm.listComponentOption);
            $('#employee-list').focus();
        }

        validateEndDate(value: any) {
            const vm = this;
            $('#endDate').ntsError('clear');
            let start = moment.utc(vm.startDate(), "YYYY/MM/DD");
            let end = moment.utc(value, "YYYY/MM/DD");
            if (end.isBefore(start)) {
                $('#endDate').ntsError('set', {messageId: 'MsgB_21', messageParams: [vm.$i18n('KDL016_27')]});
            }

            // Validate maxRange oneYear
            let maxDate = _.cloneDeep(start);
            let currentDate = start.date();
            let isEndMonth = currentDate === start.endOf("months").date();
            let isStartMonth = currentDate === 1;
            maxDate = maxDate.date(1).add(1, 'year');
            if (isStartMonth) {
                maxDate = maxDate.month(maxDate.month() - 1).endOf("months");
            }
            else if (isEndMonth) {
                maxDate = maxDate.endOf("months").add(-1, "days");
            }
            else {
                maxDate = maxDate.date(currentDate - 1);
            }

            if (end.isAfter(maxDate)) {
                $('#endDate').ntsError('set', {messageId: 'MsgB_23', messageParams: [vm.$i18n('KDL016_27')]});
            }

            if($('#startDate').ntsError("hasError")) {
                vm.validateStartDate(vm.startDate());
            }
        }

        validateStartDate(value: any) {
            const vm = this;
            $('#startDate').ntsError('clear');
            let start = moment.utc(value, "YYYY/MM/DD");
            let end = moment.utc(vm.endDate(), "YYYY/MM/DD");
            if (end.isBefore(start)) {
                $('#startDate').ntsError('set', {messageId: 'MsgB_21', messageParams: [vm.$i18n('KDL016_27')]});
            }

            if($('#endDate').ntsError("hasError")) {
                vm.validateEndDate(vm.startDate());
            }
        }

        validateTimeSpanMin(value: any) {
            const vm = this;
            $('#timespanMin').ntsError('clear');
            let start = moment.utc(value, "YYYY/MM/DD");
            let end = moment.utc(vm.timespanMax(), "YYYY/MM/DD");
            if (start >= end) {
                $('#timespanMin').ntsError('set', {messageId: 'Msg_770', messageParams: [vm.$i18n('KDL016_24')]});
            }

            if($('#timespanMax').ntsError("hasError")) {
                vm.validateTimeSpanMax(vm.timespanMax());
            }
        }

        validateTimeSpanMax(value: any) {
            const vm = this;
            $('#timespanMax').ntsError('clear');
            let start = vm.timespanMin();
            let end = value;
            if (start >= end) {
                $('#timespanMax').ntsError('set', {messageId: 'Msg_770', messageParams: [vm.$i18n('KDL016_24')]});
            }

            if($('#timespanMin').ntsError("hasError")) {
                vm.validateTimeSpanMin(vm.timespanMin());
            }
        }

        loadData() {
            const vm = this, dfd = $.Deferred();
            vm.$blockui("show");
            const request = {
                employeeIds: vm.requiredParam.employeeIds,
                orgId: vm.requiredParam.targetOrg.orgId,
                orgUnit: vm.requiredParam.targetOrg.orgUnit
            };

            vm.$ajax(API.get, request).done(data => {
                vm.employeeList(data.employeeInforList.map((e: any) => ({
                    id: e.employeeId,
                    code: e.employeeCode,
                    name: e.businessName
                })));

                vm.organizationInfoList = data.orgInfoList;
                vm.orgList(data.orgInfoList.map((i: any) => new OrgItemModel(i.orgCode, i.displayName)));

                dfd.resolve();
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    vm.$window.close({closeable: true});
                });
                dfd.reject();
            }).always(() => {
                vm.$blockui("hide");
            });

            return dfd.promise();
        }

        register() {
            const vm = this;
            if (_.isEmpty(vm.selectedEmployees())) {
                $('#employee-list').ntsError('set', {messageId:'MsgB_2',messageParams:[nts.uk.resource.getText('KDL016_21')]});
                return;
            }
            vm.$validate(".nts-input:not(:disabled)").then((valid: boolean) => {
                if (valid) {
                    let orgSelected = _.find(vm.organizationInfoList, (i: any) => {
                        return i.orgCode == vm.selectedOrgCode()
                    });
                    let empIdSelected: string[] = vm.employeeList().filter((i) => {
                        return _.includes(vm.selectedEmployees(), i.code)
                    }).map(i => i.id);
                    let command: any = {
                        employeeIds: empIdSelected,
                        supportDestinationId: orgSelected.orgId,
                        orgUnit: orgSelected.orgUnit,
                        supportType: vm.selectedSupportType(),
                        supportPeriodStart: moment.utc(vm.startDate()).format("YYYY/MM/DD"),
                        supportPeriodEnd: moment.utc(vm.endDate()).format("YYYY/MM/DD"),
                        supportTimeSpan: {
                            start: vm.selectedSupportType() === 0 ? null : vm.timespanMin(),
                            end: vm.selectedSupportType() === 0 ? null : vm.timespanMax()
                        }
                    };

                    if (moment.utc(vm.startDate()).isBefore(moment.utc().format('YYYY/MM/DD'))) {
                        vm.$dialog.confirm({messageId: 'Msg_3280'}).then((result: 'no' | 'yes') => {
                            vm.$blockui("invisible");
                            if (result === 'yes') {
                                vm.execute(command);
                            }

                            if (result === 'no') {
                                vm.$blockui("hide");
                            }
                        });
                    } else {
                        vm.$blockui("invisible");
                        vm.execute(command);
                    }
                }
            });
        }

        execute(command: any) {
            const vm = this;
            vm.$ajax(API.register, command).then((data: any) => {
                if (!data.error) {
                    vm.$dialog.info({messageId: 'Msg_15'}).then(function () {
                        vm.$window.close({reloadable: true});
                    });
                } else {
                    let errorResults = data.errorResults;
                    let dataError: any = [];
                    for (let i = 0; i < errorResults.length; i++) {
                        dataError.push(
                            {
                                id: i + 1,
                                periodDisplay: errorResults[i].periodDisplay,
                                employeeDisplay: errorResults[i].employeeDisplay,
                                errorMessage: errorResults[i].errorMessage,
                            }
                        );
                    }
                    let resultObj = {
                        action: 1,
                        gridItems: dataError
                    };

                    vm.$window.modal("/view/kdl/016/f/index.xhtml", resultObj).then((result: any) => {
                        // vm.$window.close({closeable: true});
                    });
                }
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    vm.$window.close({reloadable: false});
                });
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close({reloadable: false});
        }
    }

    interface ISupportInformation {
        id: number;
        employeeId: string;
        periodStart: string;
        periodEnd: string;
        employeeCode: string;
        employeeName: string;
        supportOrgName: string;
        supportOrgId: string;
        supportOrgUnit: number;
        supportType: number;
        timeSpan: ITimeSpan;
        supportTypeName: string;
        periodDisplay: string;
        employeeDisplay: string;
        timeSpanDisplay: string;
    }

    interface ITimeSpan {
        start: number;
        end: number;
    }

    interface IParameter {
        employeeIds: string[]
        targetOrg: ITargetOrganization;
        enableSupportTimezone: boolean
    }

    interface ITargetOrganization {
        orgId: string;
        orgUnit: number
    }

    class OrgItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class BoxModel {
        id: number;
        name: string;
        enable: boolean;

        constructor(id: number, name: string, enable: boolean) {
            var self = this;
            self.id = id;
            self.name = name;
            self.enable = enable;
        }
    }

    enum SUPPORT_TYPE {
        /** 終日応援 **/
        ALLDAY = 0,
        /** 時間帯応援 **/
        TIMEZONE = 1
    }
}