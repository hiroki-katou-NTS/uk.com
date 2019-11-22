module nts.uk.pr.view.qui002.b.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;
    import errors = nts.uk.ui.errors;
    import setShared = nts.uk.ui.windows.setShared;


    export class ScreenModel {
        listEmp: KnockoutObservableArray<Employee> =  ko.observableArray([]);


        constructor() {

        }

        initScreen(emplist: Array) :JQueryPromise<any>{
            let dfd = $.Deferred();
            let self = this;
            let employeeList = [];
            let employeeIdList = [];
            _.each(emplist, (item,key) =>{
                let employee = new Employee(key,item.id,item.code,item.name , item.nameBefore, item.changeDate);
                employeeList.push(employee);
                employeeIdList.push(item.id);
            });
            let data = {
                employeeIds: employeeIdList
            };
            self.listEmp(_.orderBy(employeeList, ['employeeCode'], ['asc']));
            service.getPersonInfo(data).done((listEmp: any)=>{
                if(listEmp && listEmp.length > 0) {
                    _.each(self.listEmp(), (employee) =>{
                        _.each(listEmp, (person) => {
                            if(employee.employeeId == person.employeeId) {
                                employee.employeeNameBefore = person.oldName;
                            }
                        });
                    });
                }
                dfd.resolve();
            }).fail(function (err) {
                dfd.reject();
                dialog.alertError(err);
            });
            return dfd.promise();
        }

        //set cancel method
        cancel() {
            nts.uk.ui.windows.close();
        }

        register(){
            let self = this;
            $("#B222_9").trigger("validate");
            if(errors.hasError()) {
                return;
            }
            setShared("QUI002_PARAMS_A", self.listEmp());
            nts.uk.ui.windows.close();
        }

    }
    export class Employee {
        id: number;
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        employeeNameBefore: string;
        changeDate: KnockoutObservable<string>;
        constructor(key, employeeId, employeeCode,employeeName, employeeNameBefore, changeDate){
            this.id = key;
            this.employeeId = employeeId;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.employeeNameBefore = ko.observable(employeeNameBefore);
            this.changeDate = ko.observable(changeDate);
        }
    }
}