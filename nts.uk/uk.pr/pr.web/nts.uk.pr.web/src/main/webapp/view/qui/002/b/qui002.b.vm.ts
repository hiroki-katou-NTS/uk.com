module nts.uk.pr.view.qui002.b.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import errors = nts.uk.ui.errors;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import hasError = nts.uk.ui.errors.hasError;


    export class ScreenModel {
        listEmp: KnockoutObservableArray<Employee> =  ko.observableArray([]);


        constructor() {
            $('#B_2_container table tr th').attr( 'tabIndex', -1 );
            $('#B_2').attr( 'tabIndex', -1 );
            $('#B_2 tr').attr( 'tabIndex', -1 );
            $('#B_2 tr td').attr( 'tabIndex', -1 );
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
            $("#B_2").ntsGrid({
                height: '315px',
                dataSource: self.listEmp(),
                primaryKey: 'id',
                virtualization: true,
                showErrorsOnPage: true,
                virtualizationMode: 'continuous',
                errorColumns: [ 'changeDate' ],
                columns: [
                    { headerText: 'id', key: 'id', dataType: 'number', width: '20' , hidden: true},
                    { headerText: 'employeeId', key: 'employeeId', dataType: 'string', width: '100' , hidden: true},
                    { headerText: getText('QUI002_B222_2'), key: 'employeeCode', dataType: 'string', width: '160' },
                    { headerText: getText('QUI002_B222_3'), key: 'employeeName', dataType: 'string', width: '170' },
                    { headerText: getText('QUI002_B222_4'), key: 'employeeNameBefore', dataType: 'string', width: '170' },
                    { headerText: getText('QUI002_B222_5'), key: 'changeDate', dataType: 'Date',width: '113', ntsControl: 'DatePicker',
                        constraint: {
                            cDisplayType: "Date",
                            required: false
                        } }

                ],
                features: [
                    { name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'employeeCode', allowResizing: false, minimumWidth: 140,
                            columnKey: 'employeeName', allowResizing: false, minimumWidth: 150,
                            columnKey: 'employeeNameBefore', allowResizing: false, minimumWidth: 150,
                            columnKey: 'changeDate', allowResizing: false, minimumWidth: 130,
                        }]
                    },
                    {name: 'Selection', mode: 'row', multipleSelection: false}],
                ntsControls: [
                    { name: 'DatePicker', controlType: 'DatePicker', format: 'ymd', constraint: { required: false } }
                ],
                ntsFeatures: [{ name: "Sheet",
                    initialDisplay: "sheet1",
                    sheets: [
                        { name: "sheet1", text: "Sheet 1", columns: ["id", "employeeId", "employeeCode", "employeeName", "employeeNameBefore", "changeDate"] },
                    ]
                }]

            });
            $("#B_2").setupSearchScroll("igGrid", true);

        }

        //set cancel method
        cancel() {
            nts.uk.ui.windows.close();
        }

        register(){
            let self = this;
            let listEmp = [];
                _.each(self.listEmp(), (item) => {
                    let employee = new Employee();
                    employee.employeeId = item.employeeId;
                    employee.employeeCode = item.employeeCode;
                    employee.changeDate = item.changeDate;
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