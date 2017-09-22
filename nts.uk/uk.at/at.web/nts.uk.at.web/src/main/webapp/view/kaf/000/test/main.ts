module nts.uk.at.view.kaf000.test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel {
        appInfor: KnockoutObservableArray<ApproverDtoK> = ko.observableArray([]);
        
        constructor() {
            var self = this;
            self.appInfor.push(new ApproverDtoK('18a9bf80-4a30-4e3b-9b63-6aee4e0bac1b', 4));
            self.appInfor.push(new ApproverDtoK('e3ee58d6-4ed3-4b88-a6e9-e91e2545ea7d', 7));
            self.appInfor.push(new ApproverDtoK('1445DA47-3CF9-4B0A-B819-96D20721881C', 9));
        }
        openKaf002b() {
            var self = this;
            nts.uk.request.jump("/view/kaf/000/b/index.xhtml", self.appInfor()[1]);
        }
        openKaf004e() {
            var self = this;
            nts.uk.request.jump("/view/kaf/000/b/index.xhtml", self.appInfor()[2]);
        }
    }
    export class ApproverDtoK {
        appID: string;
        appType: number;
        constructor(appID: string, appType: number) {
            this.appID = appID;
            this.appType = appType;
        }
    }
}