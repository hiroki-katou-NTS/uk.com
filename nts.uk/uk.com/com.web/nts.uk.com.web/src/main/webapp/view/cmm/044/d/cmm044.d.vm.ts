module cmm044.d.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        inp_startDate: KnockoutObservable<string>;
        inp_endDate: KnockoutObservable<string>;
        dateValue: KnockoutObservable<any>;
        histItems: KnockoutObservableArray<model.AgentDto>;
        personList: KnockoutObservableArray<AgentData>;
        dataPerson: any;
        

        constructor() {
            var self = this;
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(200001);

            self.dateValue = ko.observable({ startDate: '', endDate: '' });
            self.histItems = ko.observableArray([]);

            self.personList = ko.observableArray([]);
            self.dataPerson = nts.uk.ui.windows.getShared('cmm044_DataPerson');
            
        }
        start() {
            var self = this,
                dfd = $.Deferred();

            self.personList.removeAll();
            $.when(self.findAgent()).done(function() {
                dfd.resolve();
            });

            return dfd.promise();
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }


        findAgent(): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred();
            service.findAgent().done(function(agent_arr: Array<model.AgentDto>) {
           
                for (var i = 0; i < agent_arr.length; i++) {
                    self.histItems.push(new model.AgentDto(agent_arr[i].companyId, agent_arr[i].employeeId, agent_arr[i].requestId, agent_arr[i].startDate, agent_arr[i].endDate));
                }
                dfd.resolve();
                    
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        findAgentByDate(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.personList.removeAll();
            service.findAgentByDate(self.dateValue().startDate, self.dateValue().endDate).done(function(agent_arr: Array<model.AgentDto>) {
                if(agent_arr.length == 0){
                    nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_7"));
                }else{
                _.each(agent_arr, x => {
                    let obj = _.find(self.dataPerson, (p: any) => p.personId == x.employeeId);
                    if (obj) {
                        self.personList.push(new AgentData(obj.code, obj.name, '', '', '', x.startDate, x.endDate, '', '', ''));
                    }
                });
                                
                dfd.resolve();
                    }
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

    }

    export module model {
        export class AgentDto {
            companyId: string;
            employeeId: string;
            requestId: string;
            startDate: string;
            endDate: string;

            constructor(companyId: string, employeeId: string, requestId: string, startDate: string, endDate: string) {
                this.companyId = companyId;
                this.employeeId = employeeId;
                this.requestId = requestId;
                this.startDate = startDate;
                this.endDate = endDate;
            }
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