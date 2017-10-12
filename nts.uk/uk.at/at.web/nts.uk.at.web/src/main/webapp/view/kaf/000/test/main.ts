module nts.uk.at.view.kaf000.test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel {
        appInfor: KnockoutObservableArray<ApproverDtoK> = ko.observableArray([]);
        startDate: KnockoutObservable<any> = ko.observable('2017/01/01');
        endDate: KnockoutObservable<any> = ko.observable(new Date());
        listAppID :Array<String> ;
        constructor() {
            var self = this;
            self.listAppID  =[]; 
            self.appInfor.push(new ApproverDtoK('e3ee58d6-4ed3-4b88-a6e9-e91e2545ea7d', 4));
            self.appInfor.push(new ApproverDtoK('e3ee58d6-4ed3-4b88-a6e9-e91e2545ea7d', 7));
            self.appInfor.push(new ApproverDtoK('1445DA47-3CF9-4B0A-B819-96D20721881C', 9));
        }
        /**
         * open　直行直帰申請
         */
        openKaf009b() {
            var self = this;
            nts.uk.request.jump("/view/kaf/000/b/index.xhtml", self.appInfor()[0]);
        }
        /**
         * 
         */
        openKaf002b() {
            var self = this;
            nts.uk.request.jump("/view/kaf/000/b/index.xhtml", self.appInfor()[1]);
        }
        /**
         * 
         */
        openKaf004e() {
            var self = this;
            nts.uk.request.jump("/view/kaf/000/b/index.xhtml", self.appInfor()[2]);
        }
        openDetailScreen(){
            var self = this;
            var data = new service.dateInfor;
            data.startDate = self.startDate();
            data.endDate = self.endDate();
            debugger;
            service.getAppId(data).done(function(values: any){
                if(values.length != 0){
                    debugger;
                    self.listAppID =values;
                    nts.uk.request.jump("/view/kaf/000/b/index.xhtml", {'listAppId': self.listAppID, 'appType': 4});
                }else{
                    
                }
            });
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