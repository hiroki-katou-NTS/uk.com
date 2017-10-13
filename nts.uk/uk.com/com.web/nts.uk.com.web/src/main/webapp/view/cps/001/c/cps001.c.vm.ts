module cps001.c.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {

        listEmpDelete: KnockoutObservableArray<IEmployees> = ko.observableArray([]);
        currentEmployee: KnockoutObservable<Employee> = ko.observable(new Employee());
        detail: KnockoutObservable<EmployeeInfo> = ko.observable(new EmployeeInfo({ datedelete: '', reason: '', newCode: '', newName:'' }));

        constructor() {
            let self = this,
            currentEmployee = self.currentEmployee(),
            detail = self.detail();

            
            self.start();
        }

        start() {
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