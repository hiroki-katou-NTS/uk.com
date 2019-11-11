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
            //let params = getShared("QUI002_PARAMS_B");

            self.listEmp(self.createData());
            /*if(nts.uk.util.isNullOrEmpty(params) || nts.uk.util.isNullOrEmpty(params.employeeList)) {
                close();
            }*/
            self.loadGird();
        }

        createData(){
            let employeeList = [];
            for (var i = 0; i < 20; i++) {
                let employee = new Employee("000000001" + i,"000000001" + i,"000000001" + i , "000000001" + i, "2019/12/12");
                employeeList.push(employee);
            }
            return employeeList;
        }

        loadGird(){
            let self = this;
            let template = "<div id =\"${employeeId}\" data-bind='ntsDatePicker: {name: \"jjj\", value: ko.observable(${changeDate}), dateFormat: \"YYYY/MM/DD\",valueFormat: \"YYYYMMDD\"} '</div>";
            $("#B_2").ntsGrid({
                height: '340px',
                dataSource: self.listEmp(),
                primaryKey: 'employeeId',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: '', key: 'employeeId', dataType: 'string', width: '100' , hidden: true},
                    { headerText: getText('QUI002_17'), key: 'employeeCode', dataType: 'string', width: '150' },
                    { headerText: getText('QUI002_18'), key: 'employeeName', dataType: 'string', width: '140' },
                    { headerText: getText('QUI002_19'), key: 'employeeNameBefore', dataType: 'string', width: '140' },
                    { headerText: getText('QUI002_20'), key: 'changeDate', dataType: 'string',width: '120',template: template}

                ],
                features: [
                    { name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'employeeCode', allowResizing: false, minimumWidth: 30,
                            columnKey: 'employeeName', allowResizing: false, minimumWidth: 30,
                            columnKey: 'employeeNameBefore', allowResizing: false, minimumWidth: 30,
                            columnKey: 'changeDate', allowResizing: false, minimumWidth: 30,
                        }]
                    }],
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
                item.changeDate  = $("'#" + item.employeeId + "'" ).val();
                listEmployee.push(item);
            });
            return listEmployee;
        }

        convertListEmployee(array :Array<Employee>){
            return _.orderBy(array, ['employeeCode'], ['asc'])
        }


    }
    export class Employee {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        employeeNameBefore: string;
        changeDate: KnockoutObservable<string>;
        constructor(employeeId, employeeCode,employeeName, employeeNameBefore, changeDate){
            this.employeeId = employeeId;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.employeeNameBefore = employeeNameBefore;
            this.changeDate = changeDate;
        }
    }
}