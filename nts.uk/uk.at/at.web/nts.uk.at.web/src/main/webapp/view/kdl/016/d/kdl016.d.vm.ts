/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.d {
    const API = {
        update: "screen/at/kdl016/update"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        detail: KnockoutObservable<ISupportInformation> = ko.observable(null);
        data: ISupportInformation;
        dateValue: KnockoutObservable<any>;
        d11Text: KnockoutObservable<string> = ko.observable("");

        constructor(params: any) {
            super();
            const vm = this;
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
                    timeSpanDisplay: dataFromA.timeSpanDisplay,
                    displayMode: dataFromA.displayMode
                };
                vm.detail(transfer);

                vm.dateValue = ko.observable({
                    startDate: vm.detail().periodStart,
                    endDate: vm.detail().periodEnd,
                });
                vm.d11Text = ko.observable(vm.detail().displayMode === 1 ? vm.$i18n('KDL016_15') : vm.$i18n('KDL016_20'));
            }
        }

        mounted() {
            const vm = this;
            $('#daterangepicker').find('input')[0].focus();
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
                    periodStart: moment.utc(vm.dateValue().startDate).format("YYYY/MM/DD"),
                    periodEnd: moment.utc(vm.dateValue().endDate).format("YYYY/MM/DD"),
                    supportTimeSpan: {
                        start: null,
                        end: null
                    }
                };

                if (moment.utc(vm.dateValue().startDate).isBefore(moment.utc().format('YYYY/MM/DD'))) {
                    vm.$dialog.confirm({messageId: 'Msg_3280'}).then((result: 'no' | 'yes') => {
                        vm.$blockui("invisible");
                        if (result === 'yes') {
                            vm.executeUpdate(command);
                        }

                        if (result === 'no') {
                            vm.$blockui("hide");
                        }
                    });
                } else {
                    vm.$blockui("invisible");
                    vm.executeUpdate(command);
                }
            });
        }

        executeUpdate(command: any) {
            const vm = this;
            vm.$ajax(API.update, command).then((data: any) => {
                if (!data.error) {
                    vm.$dialog.info({messageId: 'Msg_15'}).then(function () {
                        vm.closeDialog(true);
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
                        // vm.closeDialog();
                    });
                }
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    vm.closeDialog(false);
                });
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        closeDialog(reloadable: boolean): void {
            const vm = this;
            let dataShare = {reloadable: reloadable};
            nts.uk.ui.windows.setShared("shareKdl016D", dataShare);
            vm.$window.close();
        }

        cancel() {
            const vm = this;
            vm.closeDialog(false);
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
        displayMode: number;
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