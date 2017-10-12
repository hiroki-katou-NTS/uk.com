module cps001.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {

        constructor() {
            let self = this,
                employeeIdq: IModelDto = getShared('CPS001B_PARAM') || {};
            if (employeeIdq){
                var employeeId = "90000000-0000-0000-0000-000000000001";
                    // Gọi service tải dữ liệu employee
                    service.getEmployee(employeeId).done((data: any) => {
                        if (data) {
                            console.log(data);
                            debugger;
                        }
                    });
            }



        }

        pushData() {
            let self = this;

            setShared('CPS001B_VALUE', {});
            self.close();
        }

        close() {
            close();
        }
    }

    interface IModelDto {
        id: string;
    }


}