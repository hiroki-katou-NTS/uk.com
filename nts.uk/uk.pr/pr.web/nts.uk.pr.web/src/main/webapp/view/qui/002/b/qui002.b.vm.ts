module nts.uk.pr.view.qui002.b.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;
    import errors = nts.uk.ui.errors;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;


    export class ScreenModel {
        listEmp: KnockoutObservableArray<Employee> =  ko.observableArray([]);


        constructor() {
            var self = this;
            let params = getShared("QUI002_PARAMS_B");

            self.listEmp(self.convertListEmployee(self.createData(params.employeeList)));
            if(nts.uk.util.isNullOrEmpty(params) || nts.uk.util.isNullOrEmpty(params.employeeList)) {
                close();
            }
            self.loadGird();

        }

        createData(emplist: Array){
            let employeeList = [];
            _.each(emplist, (item,key) =>{
                let employee = new Employee(key,item.id,item.code,item.name , item.nameBefore, "");
                employeeList.push(employee);
            });

            return employeeList;
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
                    { headerText: getText('QUI002_B222_5'), key: 'changeDate', dataType: 'string',width: '113',template: template}

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
                    {name: 'Selection', mode: 'row', multipleSelection: false}],


            });
            $("#B_2").setupSearchScroll("igGrid", true);

        }

        //set cancel method
        cancel() {
            nts.uk.ui.windows.close();
        }

        register(){
            let self = this;
            setShared("QUI002_PARAMS_A", self.getListEmployee(self.listEmp()));
            nts.uk.ui.windows.close();
        }

        getListEmployee(emplist: Array){
            let listEmployee: any = [];
            _.each(emplist, (item) =>{
                item.changeDate  = $("#" + item.employeeId).val();
                listEmployee.push(item);
            });
            return listEmployee;
        }

        convertListEmployee(array :Array<Employee>){
            return _.orderBy(array, ['employeeCode'], ['asc'])
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
    }
}