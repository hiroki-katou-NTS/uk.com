module cps001.c.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import clearError = nts.uk.ui.errors.clearAll;
    import showDialog = nts.uk.ui.dialog;
    import close = nts.uk.ui.windows.close;

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
                            self.currentEmployee(new Employee(emp));
                            self.detail(new EmployeeInfo(data));
                        }
                    });
                }
            });
        }

        start(sid?: string): JQueryPromise<any> {
            let self = this,
                currentEmployee = self.currentEmployee(),
                dfd = $.Deferred();
            self.listEmpDelete.removeAll();
            service.getData().done((data: Array<IEmployees>) => {
                if (data && data.length) {
                    self.listEmpDelete(data);

                    if (!sid) {
                        self.currentEmployee(new Employee(data[0]));
                        currentEmployee.code(data[0].code);
                    } else {
                        debugger;
                        let _item: IEmployees = _.find(ko.toJS(self.listEmpDelete()), function(item: IEmployees) { return item.id == sid; });
                        if (_item) {
                            self.currentEmployee(new Employee(_item));
                            currentEmployee.code(_item.code);
                        } else {
                            self.currentEmployee(new Employee(data[0]));
                            currentEmployee.code(data[0].code);
                        }
                    }
                } else {
                    // list null
                    self.newMode();
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        reStoreData() {
            let self = this,
                currentItem: IEmployees = ko.toJS(self.currentEmployee()),
                listItem: Array<IEmployees> = ko.toJS(self.listEmpDelete()),
                detail: IEmployeeInfo = ko.toJS(self.detail());

            nts.uk.ui.dialog.confirm({ messageId: "Msg_528" }).ifYes(() => {
                let itemListLength = self.listEmpDelete().length;
                let indexItemDelete = _.findIndex(ko.toJS(self.listEmpDelete), function(item: any) { return item.id == currentItem.id; });
                let objToRestore = { id: currentItem.id, code: currentItem.code, newCode: detail.newCode, newName: detail.newName };
                service.restoreData(objToRestore).done(() => {
                    if (itemListLength === 1) {
                        self.start();
                    } else if (itemListLength - 1 === indexItemDelete) {
                        self.start(listItem[indexItemDelete - 1].id).done(() => {
                        });
                    } else if (itemListLength - 1 > indexItemDelete) {
                        self.start(listItem[indexItemDelete + 1].id).done(() => {
                        });
                    }
                });

            }).ifCancel(() => {

            });
        }

        deleteData() {
            let self = this,
                currentItem: IEmployees = ko.toJS(self.currentEmployee()),
                listItem: Array<IEmployees> = ko.toJS(self.listEmpDelete()),
                detail: IEmployeeInfo = ko.toJS(self.detail());
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let sid = currentItem.id;
                service.removedata(sid).done(() => {
                    showDialog.info({ messageId: "Msg_464" }).then(function() {
                        self.start(sid).done(() => {

                        });
                    });


                });
            }).ifCancel(() => {

            });

        }

        closeUp() {
            close();
        }

        newMode() {
            let self = this,
                listItem: Array<IEmployees> = self.listEmpDelete(),
                detail: EmployeeInfo = self.detail();
            debugger;
            detail.newCode('');
            detail.newName('');
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