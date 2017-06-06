module cmm044.b.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        agentData: KnockoutObservable<AgentData>;
        personCom: KnockoutObservable<PersonCom>;

        constructor() {
            var self = this;
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(200001);
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
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
}