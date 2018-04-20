module nts.uk.at.view.kaf000.test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import model = nts.uk.at.view.kaf000.b.viewmodel.model;
    export class ScreenModel {
        startDate: KnockoutObservable<any> = ko.observable('2018/01/01');
        endDate: KnockoutObservable<any> = ko.observable(new Date());
        listAppMeta: Array<model.ApplicationMetadata>;
        currentApp: model.ApplicationMetadata;

        constructor() {
            var self = this;
            self.listAppMeta = [];
            self.currentApp = null;
        }
        
        openDetailScreen() {
            var self = this;
            var data = new service.dateInfor;
            data.startDate = self.startDate();
            data.endDate = self.endDate();
            service.getAppId(data).done(function(values: any) {
                if (values.length != 0) {
                    self.listAppMeta = _.map(values, x => { return x.appID;});
                    self.currentApp = self.listAppMeta[0];
                    nts.uk.request.jump("/view/kaf/000/b/index.xhtml", { 'listAppMeta': self.listAppMeta, 'currentApp': self.currentApp });
                }
            });
        }
        
        openSingle() {
            var self = this;
            let data = $("#appID-input").val();
            nts.uk.request.jump("/view/kaf/000/b/index.xhtml", { 'listAppMeta': [data], 'currentApp': data });
        }
    }
    export class Application {
        applicationType: number;
        constructor(applicationType: number) {
            this.applicationType = applicationType;
        }
    }
}