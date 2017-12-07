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
                dataShare: IDataShare = getShared('CPS001B_PARAM') || null;

            //if (dataShare) {

            // Gọi service tải dữ liệu employee
            service.getEmployeeInfo("90000000-0000-0000-0000-000000000001").done((data: IModelDto) => {
                if (data) {
                    empDelete.code(data.code); // scd
                    empDelete.reason(data.reason); // reason delete
                }
            });

            // Gọi service tải dữ liệu name of person
            service.getPersonInfo("00001").done((data: IModelDto) => {
                if (data) {
                    empDelete.name(data.name);
                }
            });

            // }
        }

        pushData() {
            let self = this,
                empDelete: IModelDto = ko.toJS(self.empDelete);
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this,
                    dataShare: IDataShare = getShared('CPS001B_PARAM') || null,
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

    // Object truyen tu man A sang
    interface IDataShare {
        sid: string;
        pid: string;
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