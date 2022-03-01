/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.e {
    const API = {
        update: "screen/at/kdl016/update"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        detail: KnockoutObservable<ISupportInformation> = ko.observable(null);
        data: ISupportInformation;

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
            }
        }

        mounted() {
            const vm = this;
            $('#timespanMin').focus();
        }

        update(): void {
            const vm = this;
            let dataError = [
                {
                    id: 1,
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Anthony Elanga',
                    errorMessage: "登録できません 登録できません 登録できません",
                },
                {
                    id: 2,
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000002 Volodymyr Zelensky',
                    errorMessage: "登録できません 登録できません 登録できません",
                }
            ];
            vm.$window.modal("/view/kdl/016/f/index.xhtml", dataError).then((result: any) => {
                vm.closeDialog();
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