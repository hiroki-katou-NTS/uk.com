module nts.uk.at.view.kaf000.test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel {
        appInfor: KnockoutObservableArray<ApproverDtoK> = ko.observableArray([]);
        
        constructor() {
            var self = this;
            self.appInfor.push(new ApproverDtoK('e3ee58d6-4ed3-4b88-a6e9-e91e2545ea7d', 4));
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
        openKaf009b() {
            var self = this;
            nts.uk.request.jump("/view/kaf/000/b/index.xhtml", self.appInfor()[0]);
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