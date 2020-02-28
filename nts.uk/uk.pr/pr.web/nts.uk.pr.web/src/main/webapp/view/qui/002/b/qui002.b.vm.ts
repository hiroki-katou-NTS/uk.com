module nts.uk.pr.view.qui002.b.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import errors = nts.uk.ui.errors;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;


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
                    { headerText: getText('QUI002_B222_5'), key: 'changeDate', dataType: 'string',width: '113', ntsControl: 'TextEditor'}

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
                    { name: 'TextEditor', controlType: 'TextEditor', constraint: { valueType: 'String', required: false } }
                ],

            });
            $("#B_2").setupSearchScroll("igGrid", true);

        }

        checkDate(yearMonthDate: any) {
            if (yearMonthDate === undefined || yearMonthDate === null || yearMonthDate == "") {
                return true;
            }
            if (!(yearMonthDate instanceof String)) {
                yearMonthDate = yearMonthDate.toString();
            }
            if(yearMonthDate.length != 10) {
                return false;
            }
            yearMonthDate = yearMonthDate.replace("/", "");
            yearMonthDate = yearMonthDate.replace("/", "");
            var checkNum = yearMonthDate.replace(/[0-9]/g, "");
            if (checkNum) {
                return false;
            }
            var year = parseInt(yearMonthDate.substring(0, 4));
            if (year < 1900 || year > 9999) {
                return false;
            }
            var month = parseInt(yearMonthDate.substring(4, 6));
            if (month < 1 || month > 12) {
               return false;
            }

            var date = parseInt(yearMonthDate.substring(6));
            var maxDate = 30;
            switch (month) {
                case 2:
                    if (year % 400 == 0) {
                        maxDate = 29;
                    } else if (year % 4 == 0 && year % 25 != 0) {
                        maxDate = 29;
                    } else {
                        maxDate = 28;
                    }
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    maxDate = 31;
                    break;
                default:
                    maxDate = 30;
                    break;
            }
            if (date < 1 || date > maxDate) {
                return false;
            }
            return true;

        }

        //set cancel method
        cancel() {
            nts.uk.ui.windows.close();
        }

        register(){
            let self = this;
            let validate = true;
            _.each(self.listEmp(), (item) =>{
                if(!self.checkDate(item.changeDate)) {
                    validate = false;
                }
            });
            if(!validate) {
                dialog.alertError("変更年月日は 1900/01/01 ～ 9999/12/31 の日付を入力してください");
                return;
            }
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