module cps001.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {

        empDelete: KnockoutObservable<ModelDelete> = ko.observable(new ModelDelete({ employeeCode: '', personName: '', reason: '' }));

        constructor() {
            let self = this,
                empDelete: ModelDelete = self.empDelete(),
                employeeId: IModelDto = getShared('CPS001B_PARAM') || null;
            var employeeIdq = "90000000-0000-0000-0000-000000000001";
            if (employeeIdq) {

                // Gọi service tải dữ liệu employee
                service.getEmployee(employeeIdq).done((data: IModelDto) => {
                    if (data) {
                        empDelete.employeeCode(data.employeeCode);
                        empDelete.personName(data.personName);
                    }
                });
            }



        }

        pushData() {
            let self = this,
                empDelete: ModelDelete = self.empDelete();
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this,
                    //employeeId: any = getShared('CPS001B_PARAM') || null;
                    employeeId = "90000000-0000-0000-0000-000000000001";
                if (employeeId) {
                    service.deleteEmp(new EmpDelete(employeeId, empDelete.reason + "")).done((result) => {
                        setShared('CPS001B_VALUE', {});
                        console.log(result);
                    });
                }
            }).ifCancel(() => {
            });
        }

        close() {
            close();
        }
    }

    interface IModelDto {
        employeeCode: string;
        personName: string;
        reason: string;
    }

    class ModelDelete {
        employeeCode: KnockoutObservable<string> = ko.observable('');
        personName: KnockoutObservable<string> = ko.observable('');
        reason: KnockoutObservable<string> = ko.observable('');

        constructor(param: IModelDto) {
            let self = this;

            if (param) {
                self.employeeCode(param.employeeCode || '');
                self.personName(param.personName || '');
                self.reason(param.reason || '');

            }
        }
    }

    class EmpDelete {
        employeeId: string;
        reasonDelete: string;
        constructor(employeeId: string, reasonDelete: string) {
            this.employeeId = employeeId;
            this.reasonDelete = reasonDelete;
        }
    }




}