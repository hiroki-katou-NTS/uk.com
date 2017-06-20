module cmm044.b.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        agentData: KnockoutObservable<AgentData>;
        startDate: KnockoutObservable<any>;
        endDate: KnockoutObservable<any>;
        inp_startDate: KnockoutObservable<string>;
        inp_endDate: KnockoutObservable<string>;
        personList: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(200001);
            self.startDate = ko.observable(null);
            self.endDate = ko.observable(null);
        }
        start() {
            var self = this;
            var dfd = $.Deferred();

            self.startDate(nts.uk.ui.windows.getShared('cmm044StartDate'));
            self.endDate(nts.uk.ui.windows.getShared('cmm044EndDate'));
            self.personList = ko.observableArray([
                { firstName: 'Bert', lastName: 'Bertington' },
                { firstName: 'Charles', lastName: 'Charlesforth' },
                { firstName: 'Denise', lastName: 'Dentiste' }
            ]);

            dfd.resolve();
            return dfd.promise();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }

    class AgentData {
        requestCode: KnockoutObservable<string>;
        requestName: KnockoutObservable<string>;
        workPlaceCode: KnockoutObservable<string>;
        workPlaceName: KnockoutObservable<string>;
        position: KnockoutObservable<string>;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        agentTarget: KnockoutObservable<string>;
        agentCode: KnockoutObservable<string>;
        agentName: KnockoutObservable<string>;
        constructor(requestCode: string, requestName: string, workPlaceCode: string, workPlaceName: string, position: string, startDate: string, endDate: string, agentTarget: string, agentCode: string, agentName: string) {
            this.requestCode = ko.observable(requestCode);
            this.requestName = ko.observable(requestName);
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