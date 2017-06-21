module cmm044.b.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        agentData: KnockoutObservable<AgentData>;
        inp_startDate: KnockoutObservable<string>;
        inp_endDate: KnockoutObservable<string>;
        personList: KnockoutObservableArray<any>;
        dateValue: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(200001);
            self.dateValue = ko.observable({});
            self.dateValue = ko.observable({startDate: '', endDate: ''});
            self.inp_endDate = ko.observable(null);
            self.inp_startDate = ko.observable(null);
        }
        start() {
            var self = this;
            var dfd = $.Deferred();

            self.inp_startDate(nts.uk.ui.windows.getShared('cmm044StartDate'));
            self.inp_endDate(nts.uk.ui.windows.getShared('cmm044EndDate'));
            self.personList = ko.observableArray([
                { employeeCode: 'A000000001', employeeName: '日通　社員1',workplaceCode:'',workplaceName:'',position:'',startDate: self.inp_endDate(),endDate: self.inp_startDate(),substitutionTarget:'',agentCode:'',agentName:''}
                ,{ employeeCode: 'A000000002', lastName: 'Charlesforth' },
                { firstName: 'Denise', lastName: 'Dentiste'} 
            ]);

            dfd.resolve();
            return dfd.promise();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        findAgent(employeeId, startDate, endDate): JQueryPromise<any>{
             var self = this;
            var dfd = $.Deferred();     
            service.findAllAgent(employeeId,startDate,endDate).done(function(agent_arr: Array<AgentData>) {        
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });

            return dfd.promise();
        }
    }

    export class AgentData {
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        workPlaceCode: KnockoutObservable<string>;
        workPlaceName: KnockoutObservable<string>;
        position: KnockoutObservable<string>;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        agentTarget: KnockoutObservable<string>;
        agentCode: KnockoutObservable<string>;
        agentName: KnockoutObservable<string>;
        constructor(employeeCode: string, employeeName: string, workPlaceCode: string, workPlaceName: string, position: string, startDate: string, endDate: string, agentTarget: string, agentCode: string, agentName: string) {
            this.employeeCode = ko.observable(employeeCode);
            this.employeeName = ko.observable(employeeName);
            this.workPlaceCode = ko.observable(workPlaceCode);
            this.workPlaceName = ko.observable(workPlaceName);
            this.position = ko.observable(position);
            this.startDate = ko.observable(startDate);
            this.endDate = ko.observable(endDate);
            this.agentTarget = ko.observable(agentTarget);
            this.agentCode = ko.observable(agentCode);
            this.agentName = ko.observable(agentName);
        }
    }




}