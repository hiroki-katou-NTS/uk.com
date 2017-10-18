module cps001.c.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import clearError = nts.uk.ui.errors.clearAll;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {

        listEmpDelete: KnockoutObservableArray<IEmployees> = ko.observableArray([]);
        currentEmployee: KnockoutObservable<Employee> = ko.observable(new Employee());
        detail: KnockoutObservable<IEmployeeInfo> = ko.observable(null);

        constructor() {
            let self = this,
                currentEmployee = self.currentEmployee(),
                listEmpDelete = self.listEmpDelete(),
                detail = self.detail();

            currentEmployee.code.subscribe(x => {
                if (x) {
                    // clear all error message
                    clearError();
                    service.getDetail(x).done((data: IEmployeeInfo) => {
                        if (data) {
                            self.detail(data);
                        }
                    });
                }
            });



            self.start();
        }

        start() {
            let self = this,
                currentEmployee = self.currentEmployee();
            self.listEmpDelete.removeAll();
            service.getData().done((data: Array<IEmployees>) => {
                if (data && data.length) {
                    self.listEmpDelete(data);
                    currentEmployee.code(data[0].code);
                }
            });

        }

        saveData() {
            let self = this;
        }


    }

    interface IEmployees {
        code: string;
        name: string;
    }

    class Employee {
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');

        constructor(param?: IEmployees) {
            let self = this;
            if (param) {
                self.code(param.code || '');
                self.name(param.name || '');
            }
        }
    }

    interface IEmployeeInfo {
        datedelete: string;
        reason: string;
        newCode: string;
        newName: string;
    }

    class EmployeeInfo {
        datedelete: KnockoutObservable<string> = ko.observable('');
        reason: KnockoutObservable<string> = ko.observable('');
        newCode: KnockoutObservable<string> = ko.observable('');
        newName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IEmployeeInfo) {
            let self = this;

            self.datedelete(param.datedelete || '');
            self.reason(param.reason || '');
            self.newCode(param.newCode || '');
            self.newName(param.newName || '');
        }
    }

}