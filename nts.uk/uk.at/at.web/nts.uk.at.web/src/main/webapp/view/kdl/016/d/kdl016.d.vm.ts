/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.d {
    const API = {
        update: "screen/at/kdl016/update"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        detail: KnockoutObservable<ISupportInformation> = ko.observable(null);
        data : ISupportInformation;

        startDateStr: KnockoutObservable<string> = ko.observable("");
        endDateStr: KnockoutObservable<string> = ko.observable("");
        dateValue: KnockoutObservable<any>

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
                    timeSpanDisplay: dataFromA.timeSpanDisplay
                };
                vm.detail(transfer);
                vm.dateValue = ko.observable({
                    startDate: vm.detail().periodStart,
                    endDate: vm.detail().periodEnd,
                });
            }
        }

        mounted() {
            const vm = this;
            $('#daterangepicker').focus();
        }

        update() {

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