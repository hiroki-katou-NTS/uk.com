module cps001.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {

        empDelete: KnockoutObservable<ModelDelete> = ko.observable(new ModelDelete({ code: '', name: '', reason: '' }));

        constructor() {
            let self = this,
                empDelete: ModelDelete = self.empDelete(),
                employeeId: IModelDto = getShared('CPS001B_PARAM') || null;
            var employeeIdq = "90000000-0000-0000-0000-000000000001";
            if (employeeIdq) {

                // Gọi service tải dữ liệu employee
                service.getEmployee(employeeIdq).done((data: IModelDto) => {
                    if (data) {
                        empDelete.code(data.code);
                        empDelete.name(data.name);
                    }
                });
            }
        }

        pushData() {
            let self = this,
                empDelete: IModelDto = ko.toJS(self.empDelete);
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this,
                    //employeeId: any = getShared('CPS001B_PARAM') || null;
                    employeeId = "90000000-0000-0000-0000-000000000001";
                if (employeeId) {
                    let command = { sId: employeeId, reason: empDelete.reason };
                    service.deleteEmp(command).done(() => {
                        showDialog.info({ messageId: "Msg_16" }).then(function() {
                            setShared('CPS001B_VALUE', {});
                            close();
                        });

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
        code: string;
        name: string;
        reason: string;
    }

    class ModelDelete {
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        reason: KnockoutObservable<string> = ko.observable('');

        constructor(param: IModelDto) {
            let self = this;

            if (param) {
                self.code(param.code || '');
                self.name(param.name || '');
                self.reason(param.reason || '');
            }
        }
    }
}