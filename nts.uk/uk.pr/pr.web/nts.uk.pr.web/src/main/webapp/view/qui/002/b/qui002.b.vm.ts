module nts.uk.pr.view.qui002.b.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import errors = nts.uk.ui.errors;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;


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

        loadGird(){
            let self = this;
            let template = "<div id =\"${employeeId}\" data-bind='ntsDatePicker: {name: \"#[QUI002_B222_5]\", value: ko.observable(${changeDate}), dateFormat: \"YYYY/MM/DD\",valueFormat: \"YYYYMMDD\"} '</div>";
            $("#B_2").ntsGrid({
                height: '319px',
                dataSource: self.listEmp(),
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'id', key: 'id', dataType: 'number', width: '20' , hidden: true},
                    { headerText: 'employeeId', key: 'employeeId', dataType: 'string', width: '100' , hidden: true},
                    { headerText: getText('QUI002_B222_2'), key: 'employeeCode', dataType: 'string', width: '160' },
                    { headerText: getText('QUI002_B222_3'), key: 'employeeName', dataType: 'string', width: '170' },
                    { headerText: getText('QUI002_B222_4'), key: 'employeeNameBefore', dataType: 'string', width: '170' },
                    { headerText: getText('QUI002_B222_5'), key: 'changeDate', dataType: 'string',width: '113'}

                ],
                features: [
                    { name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'employeeCode', allowResizing: false, minimumWidth: 150,
                            columnKey: 'employeeName', allowResizing: false, minimumWidth: 150,
                            columnKey: 'employeeNameBefore', allowResizing: false, minimumWidth: 150,
                            columnKey: 'changeDate', allowResizing: false, minimumWidth: 150,
                        }]
                    },
                    {name: 'Selection', mode: 'row', multipleSelection: false}]

            });
            $("#B_2").setupSearchScroll("igGrid", true);

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
            let listEmp = [];
                _.each(self.listEmp(), (item) => {
                    let employee = new Employee();
                    employee.employeeId = item.employeeId;
                    employee.employeeCode = item.employeeCode;
                    employee.changeDate = item.changeDate();
                listEmp.push(employee);
                });
            setShared("QUI002_PARAMS_A", listEmp);
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
            this.employeeNameBefore = employeeNameBefore;
            this.changeDate = changeDate;
        }
        constructor() {

        }
    }
}