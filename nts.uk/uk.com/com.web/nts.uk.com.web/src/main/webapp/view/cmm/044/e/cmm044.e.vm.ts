module cmm044.e.viewmodel {
    export class ScreenModel {
        employeeList: KnockoutObservable<any>;
        personId: KnockoutObservable<any>;
        personList: KnockoutObservableArray<AgentData>;
        personSelectedItem: KnockoutObservable<any>;
        dataPerson: any;
        tab: any;

        constructor() {
            var self = this;
            self.personList = ko.observableArray([]);
            self.dataPerson = nts.uk.ui.windows.getShared('cmm044_DataPerson');
            self.personSelectedItem = ko.observable("");
            self.employeeList = ko.observable("");
            self.personId = ko.observable("");
            self.personSelectedItem.subscribe(function(personId) {
                var currentPerson = self.findEmployee(personId);
                self.employeeList(currentPerson);
            });
            self.tab = ko.observable(nts.uk.ui.windows.getShared('cmm044_Tab'));
        }
        start() {
            var self = this,
                dfd = $.Deferred();
            
            if (!!nts.uk.ui.windows.getShared('cmm044_Name')) {
                self.personSelectedItem( nts.uk.ui.windows.getShared('cmm044_Name'))
            }
            
            _.forEach(self.dataPerson, function(x) {
                self.personList.push(new AgentData(x.personId, x.code, x.name, null, null));
                if (!self.personSelectedItem()) {
                    self.personSelectedItem(self.personList()[0].personId);
                }
            });


            dfd.resolve();
            return dfd.promise();
        }
        closeDialog(): void {

            nts.uk.ui.windows.close();

        }
        getEmployeeName() {
            var self = this;
            nts.uk.ui.windows.setShared('cmm044_TabNumber', self.tab());
            var currentEmployee = self.employeeList()
            var data = {
                employeId: self.personSelectedItem(),
                employeeName: currentEmployee.employeeName   
            }
            nts.uk.ui.windows.setShared('cmm044_Employee', data);
           

            nts.uk.ui.windows.close();

        }

        findEmployee(value: string): any {
            let self = this;
            return _.find(self.personList(), function(obj: AgentData) {
                return obj.personId === value;
            })
        }

    }

    export class AgentData {
        personId: string;
        employeeCode: string;
        employeeName: string;
        workPlaceCode: string;
        workPlaceName: string;

        constructor(personId: string, employeeCode: string, employeeName: string, workPlaceCode: string, workPlaceName: string) {
            this.personId = personId;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.workPlaceCode = workPlaceCode;
            this.workPlaceName = workPlaceName;

        }
    }



}