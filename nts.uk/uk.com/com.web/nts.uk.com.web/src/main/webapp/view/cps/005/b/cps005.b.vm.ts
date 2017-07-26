module nts.uk.com.view.cps005.b {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import service = nts.uk.com.view.cps005.a.service;
    
    export module viewmodel {
        export class ScreenModel {

            constructor() {
                let self = this;

            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            register() {

            }

            updateData() {

            }
        }
    }

    export class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    interface IData {
        employeeId?: string;
        employeeCode: string;
        employeeName: string;
        isYearMonth: boolean;
    }
}

