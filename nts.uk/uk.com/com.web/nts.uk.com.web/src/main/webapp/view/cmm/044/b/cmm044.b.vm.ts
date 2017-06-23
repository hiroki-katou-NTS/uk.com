module cmm044.b.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        //        agentData: KnockoutObservable<AgentData>;
        inp_startDate: KnockoutObservable<string>;
        inp_endDate: KnockoutObservable<string>;
        personList: KnockoutObservableArray<AgentData>;
        dateValue: KnockoutObservable<any>;
        currentItem: KnockoutObservable<model.AgentAppDto>;
        dataHist: any;
        dataPerson: any;
        empItems: KnockoutObservableArray<PersonModel>;


        constructor() {
            var self = this;
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(200001);
            self.dateValue = ko.observable({});
            self.dateValue = ko.observable({ startDate: '', endDate: '' });
            self.inp_endDate = ko.observable(null);
            self.inp_startDate = ko.observable(null);
            self.currentItem = ko.observable(null);
            self.dataHist = nts.uk.ui.windows.getShared('cmm044Data');
            self.dataPerson = nts.uk.ui.windows.getShared('cmm044DataPerson');
            self.empItems = ko.observableArray([]);
            self.personList = ko.observableArray([]);



        }
        start() {
            var self = this;
            var dfd = $.Deferred();

            self.inp_startDate(nts.uk.ui.windows.getShared('cmm044StartDate'));
            self.inp_endDate(nts.uk.ui.windows.getShared('cmm044EndDate'));
            var obj;
            for (var i = 0; i < self.dataPerson.length; i++) {
                obj = _.find(self.dataHist, function(o) { return o.employeeId == self.dataPerson[i].personId; });
                self.personList().push(new AgentData(self.dataPerson[i].code, self.dataPerson[i].name, '', '', '',
                obj != undefined? obj.startDate : '', obj != undefined? obj.endDate : '','','',''));
            }
     

            _.range(3).map(i => {
                i++;
                if (i < 10) {
                    self.empItems.push(new PersonModel({
                        personId: '99900000-0000-0000-0000-00000000000' + i,
                        code: 'A00000000' + i,
                        name: '日通　社員' + i,
                    }));
                } else {
                    self.empItems.push(new PersonModel({
                        personId: '99900000-0000-0000-0000-00000000000' + i,
                        code: 'A0000000' + i,
                        name: '日通　社員' + i,
                    }));

                }
            });

            dfd.resolve();
            return dfd.promise();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        findAgentByDate(employeeId, startDate, endDate): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findAgentByDate(employeeId, startDate, endDate).done(function(agent_arr: Array<model.AgentDto>) {
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });

            return dfd.promise();
        }

        getAgen(employeeId: string, requestId: string): JQueryPromise<any> {

            var self = this;
            var dfd = $.Deferred();
            if (!requestId) {
                return;
            }
            var param = {
                employeeId: employeeId,
                requestId: requestId
            };
            service.findAgent(param).done(function(agent: model.AgentDto) {
                self.currentItem(new model.AgentAppDto(employeeId, requestId, agent.startDate, agent.endDate));
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });


            return dfd.promise();
        }
    }

    interface IPersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate?: number;
    }


    class PersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.personId = param.personId;
            this.code = param.code;
            this.name = param.name;
            this.baseDate = param.baseDate || 20170104;
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
        export class AgentAppDto {
            employeeId: KnockoutObservable<string>;
            requestId: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            constructor(employeeId: string, requestId: string, startDate: string, endDate: string) {
                this.employeeId = ko.observable(employeeId);
                this.requestId = ko.observable(requestId);
                this.startDate = ko.observable(startDate);
                this.endDate = ko.observable(endDate);
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