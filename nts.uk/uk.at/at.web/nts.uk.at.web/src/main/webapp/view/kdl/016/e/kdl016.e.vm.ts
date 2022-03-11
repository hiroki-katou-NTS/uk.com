/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.e {
    const API = {
        update: "screen/at/kdl016/update"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        detail: KnockoutObservable<ISupportInformation> = ko.observable(null);
        data: ISupportInformation;
        timespanMin: KnockoutObservable<number> = ko.observable(undefined);
        timespanMax: KnockoutObservable<number> = ko.observable(undefined);

        constructor(params: any) {
            super();
            const vm = this;

            vm.timespanMax.subscribe((value) => {
                vm.validateTimeSpanMax(value);
            });
            vm.timespanMin.subscribe((value) => {
                vm.validateTimeSpanMin(value);
            });
        }

        created(params: any) {
            const vm = this;
            let dataFromA = nts.uk.ui.windows.getShared("shareFromKdl016a");
            if (!_.isNil(dataFromA)) {
                let transfer: ISupportInformation = {
                    id: dataFromA.id,
                    employeeId: dataFromA.employeeId,
                    periodStart: dataFromA.periodStart,
                    periodEnd: dataFromA.periodEnd,
                    employeeCode: dataFromA.employeeCode,
                    employeeName: dataFromA.employeeName,
                    supportOrgName: dataFromA.supportOrgName,
                    supportOrgId: dataFromA.supportOrgId,
                    supportOrgUnit: dataFromA.supportOrgUnit,
                    supportType: dataFromA.supportType,
                    timeSpan: dataFromA.timeSpan,
                    supportTypeName: dataFromA.supportTypeName,
                    periodDisplay: dataFromA.periodDisplay,
                    employeeDisplay: dataFromA.employeeDisplay,
                    timeSpanDisplay: dataFromA.timeSpanDisplay
                };
                vm.detail(transfer);
                vm.timespanMin = ko.observable(dataFromA.timeSpan.start);
                vm.timespanMax = ko.observable(dataFromA.timeSpan.end);
            }
        }

        mounted() {
            const vm = this;
            $('#timespanMin').focus();
        }

        validateTimeSpanMin(value: any) {
            const vm = this;
            $('#timespanMin').ntsError('clear');
            let start = value;
            let end = vm.timespanMax();
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

        update() {
            const vm = this;
            vm.$blockui("invisible");
            vm.$validate(".nts-input:not(:disabled)").then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                let command: any = {
                    employeeId: vm.detail().employeeId,
                    supportType: vm.detail().supportType,
                    // periodStart: moment.utc(vm.dateValue().startDate).format("YYYY/MM/DD"),
                    // periodEnd: moment.utc(vm.dateValue().endDate).format("YYYY/MM/DD"),
                    supportTimeSpan: {
                        start: vm.detail().supportType === 0 ? null : vm.timespanMin(),
                        end: vm.detail().supportType === 0 ? null : vm.timespanMax()
                    }
                };

                vm.$ajax(API.update, command).then((data: any) => {
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

    interface ITargetOrganization {
        id: string;
        code: string;
        unit: number
    }
}