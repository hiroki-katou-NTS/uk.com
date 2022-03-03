/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.c {
    const API = {
        get: "screen/at/kdl016/c/init",
        register: "screen/at/kdl016/register",
        reloadEmployee: "screen/at/kdl016/c/load-employee"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        requiredParams: IParameter;
        organizationInfoList: any[] = [];

        // kcp005
        listComponentOption: any;
        date: KnockoutObservable<string> = ko.observable(new Date().toISOString());
        startDate: KnockoutObservable<string> = ko.observable(null);
        endDate: KnockoutObservable<string> = ko.observable(null);
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

        startDateStr: KnockoutObservable<string> = ko.observable("");
        endDateStr: KnockoutObservable<string> = ko.observable("");
        dateValue: KnockoutObservable<any> = ko.observable({
            startDate: new Date(),
            endDate: new Date(),
        });

        constructor(params: any) {
            super();
            const vm = this;

            vm.supportTypes = ko.observableArray([
                new BoxModel(0, '終日応援'),
                new BoxModel(1, '時間帯応援')
            ]);
            vm.selectedSupportType = ko.observable(0);

            vm.startDateStr.subscribe(function (value) {
                vm.dateValue().startDate = value;
                vm.dateValue.valueHasMutated();
            });
            vm.endDateStr.subscribe(function (value) {
                vm.dateValue().endDate = value;
                vm.dateValue.valueHasMutated();
            });

            vm.selectedSupportType.subscribe(value => {
                if (value == 1) {
                    vm.enableEditTimespan(true);
                }
                else {
                    vm.enableEditTimespan(false);
                    vm.$errors("clear");
                }
            });

            vm.selectedOrgCode.subscribe((newValue: any) => {
                vm.reloadEmployeeInfo(newValue);
            });
        }

        created(params: IParameter) {
            const vm = this;
            if (!_.isNil(params)) {
                vm.requiredParams = params;
            }

            vm.initialData();

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
            $('#combo-box').focus();
        }

        initialData() {
            const vm = this, dfd = $.Deferred();
            vm.$blockui("show");
            const request = {
                orgId: vm.requiredParams.targetOrg.orgId,
                orgUnit: vm.requiredParams.targetOrg.orgUnit,
                periodStart: vm.requiredParams.startDate,
                periodEnd: vm.requiredParams.endDate
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
                vm.$dialog.error(error);
                dfd.reject();
                vm.closeDialog();
            }).always(() => {
                vm.$blockui("hide");
            });

            return dfd.promise();
        }

        reloadEmployeeInfo(orgCode: string) {
            const vm = this, dfd = $.Deferred();
            vm.$blockui("show");
            let orgId = _.find(vm.organizationInfoList, (i: any) => {
                return i.orgCode == orgCode
            });
            const request = {
                orgId: orgId,
                orgUnit: vm.requiredParams.targetOrg.orgUnit,
                periodStart: vm.requiredParams.startDate,
                periodEnd: vm.requiredParams.endDate
            };

            vm.$ajax(API.reloadEmployee, request).done(data => {
                vm.employeeList(data.employeeInforList.map((e: any) => ({
                    id: e.employeeId,
                    code: e.employeeCode,
                    name: e.businessName
                })));
                dfd.resolve();
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });

            return dfd.promise();
        }

        register() {
            const vm = this;
            // if (_.isEmpty(self.employeeList())) {
            //     $('#A6_2').ntsError('set', {messageId:'MsgB_2',messageParams:[nts.uk.resource.getText('KDL014_21')]});
            //     return;
            // }

            let orgSelected = _.find(vm.organizationInfoList, (i: any) => {
                return i.orgCode == vm.selectedOrgCode()
            });
            let empIdSelected: string[] = vm.employeeList().filter((i) => {
                return _.includes(vm.selectedEmployees(), i.code)
            }).map(i => i.id);
            let command: any = {
                employeeIds: empIdSelected,
                supportDestinationId: vm.selectedOrgCode(),
                orgUnit: orgSelected.orgUnit,
                supportType: vm.selectedSupportType(),
                supportPeriodStart: moment.utc(vm.dateValue().startDate).format("YYYY/MM/DD"),
                supportPeriodEnd: moment.utc(vm.dateValue().endDate).format("YYYY/MM/DD"),
                supportTimeSpan: {
                    start: vm.selectedSupportType() === 0 ? null : vm.timespanMin(),
                    end: vm.selectedSupportType() === 0 ? null : vm.timespanMax()
                }
            };

            if (moment.utc(vm.dateValue().startDate).isBefore(moment.utc().format('YYYY/MM/DD'))) {
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

        execute(command: any) {
            const vm = this;
            vm.$ajax(API.register, command).then((data: any) => {
                if (!data.error) {
                    vm.$dialog.info({messageId: 'Msg_15'}).then(function () {
                        vm.closeDialog();
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

                    vm.$window.modal("/view/kdl/016/f/index.xhtml", dataError).then((result: any) => {
                        vm.closeDialog();
                    });
                }
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    vm.closeDialog();
                });
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
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
        targetOrg: ITargetOrganization;
        startDate: string,
        endDate: string
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

        constructor(id: number, name: string) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    enum SUPPORT_TYPE {
        /** 終日応援 **/
        ALLDAY = 0,
        /** 時間帯応援 **/
        TIMEZONE = 1
    }
}