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

        //spr-cmm045
        dateValue: KnockoutObservable<any> = ko.observable({ startDate: '2018/05/02', endDate: '2018/05/02' });
        itemList:  KnockoutObservableArray<any> = ko.observableArray([{ id: 0, name: '全て'},
                                                               {id: 1, name: '早出・普通残業のみ'}]);
        selectedIds: KnockoutObservable<any> = ko.observable(0);
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
        openCMM045(){
            let self = this;
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let param = {
                mode: 1,//1=承認一覧
                startDate: self.dateValue().startDate,//yyyy-mm-dd //期間（開始日）
                endDate: self.dateValue().endDate,//yyyy-mm-dd //期間（終了日）
                extractCondition: self.selectedIds(),//０＝全て、１＝早出・普通残業のみ
                agreementTime36: 0//０＝表示しない、1＝表示する
            }
//            setShared('PARAM_SPR_CMM045', param);
            nts.uk.request.jump("/view/cmm/045/a/index.xhtml",{'PARAM_SPR_CMM045': param});
        }
    }
    export class Application {
        applicationType: number;
        constructor(applicationType: number) {
            this.applicationType = applicationType;
        }
    }
}