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
        detail: KnockoutObservable<EmployeeInfo> = ko.observable(null);

        constructor() {
            let self = this,
                currentEmployee = self.currentEmployee(),
                listEmpDelete = self.listEmpDelete(),
                detail = self.detail();

            self.start();

            currentEmployee.code.subscribe(x => {
                if (x) {
                    let self = this;
                    let emp: IEmployees = self.findByCode(x, self.listEmpDelete());
                    service.getDetail(emp.id).done((data: IEmployeeInfo) => {
                        if (data) {
                            self.detail(new EmployeeInfo(data));
                        }
                    });
                }
            });
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

        reStoreData() {
            let self = this,
            currentItem : IEmployees = ko.toJS(self.currentEmployee()),
            detail : IEmployeeInfo =  ko.toJS(self.detail());
            
            nts.uk.ui.dialog.confirm({ messageId: "Msg_528" }).ifYes(() => { 
            let itemListLength = self.listEmpDelete().length;
                let objToRestore ={sid: currentItem.id ,code : currentItem.code ,newCode: detail.newCode , newName : detail.newName};
                service.restoreData(objToRestore).done(() => {
                    
                
                });
            
            }).ifCancel(() => {

            });
        }

        private findByCode(code: string, sources: any) {
            let self = this;
            if (!sources || !sources.length) {
                return undefined;
            }
            let listEmp = ko.toJS(sources);
            return _.find(listEmp, function(item: IEmployees) { return item.code == code; });
        }

        private findByIndex(code: string, sources: any) {
            let self = this;
            if (!sources || !sources.length) {
                return undefined;
            }

            let indexOfItemSelected = _.findIndex(ko.toJS(sources), function(item: IEmployees) { return item.code == code; });

        }
    }

    interface IEmployees {
        code: string;
        name: string;
        id: string;
    }

    class Employee {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');

        constructor(param?: IEmployees) {
            let self = this;
            if (param) {
                self.code(param.code || '');
                self.name(param.name || '');
                self.id(param.id || '');
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