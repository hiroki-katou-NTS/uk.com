module jhn001.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import subModal = nts.uk.ui.windows.sub.modal;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {

        listReportDraft: KnockoutObservableArray<IReportDraft> = ko.observableArray([]);
        reportId : KnockoutObservable<string> = ko.observable('');
        
        reportColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', key: 'id', width: 0, hidden: true },
            { headerText: text('JHN001_B2_3_1'), key: 'draftSaveDate', width: 160,  hidden: false },
            { headerText: text('JHN001_B2_3_2'), key: 'reportName', width: 260, hidden: false },
            { headerText: text('JHN001_B2_3_3'), key: 'missingDocName', width: 260, hidden: false }
        ]);

        constructor() {
            let self = this,
                listReportDraft = self.listReportDraft;
            self.start();
        }

        start(code?: string){
            let self = this,
                listReportDraft = self.listReportDraft;
            listReportDraft.removeAll();
            
            for (var i = 0; i < 30; i++) {
                let _data = {id: i,
                             draftSaveDate: '2019/12/'+ i,
                             reportName: 'reportName ' + i,
                             missingDocName : 'missingDocName ' + i,
                             reportCode : 'reportCode ' + i }
                 listReportDraft.push(_data);
           }
        }

        continueProcess() {
            let self = this;
            
            if(self.reportId()){
                setShared('JHN001B_PARAMS', {
                    reportId: self.reportId()
                });
                self.close();
            }
        }
        
        deleteSaveDraft() {
            let self = this;
            let reportId = self.reportId();
        }

        close() {
            close();
        }
    }

    
    interface IReportDraft {
        reportId       : string;
        reportCode     : string;
        reportName     : string;
        draftSaveDate  : string;
        missingDocName : string;
    }

    class ModelDelete {
        reportId: KnockoutObservable<string> = ko.observable('');
        reportCode: KnockoutObservable<string> = ko.observable('');
        reportName: KnockoutObservable<string> = ko.observable('');
        draftSaveDate: KnockoutObservable<string> = ko.observable('');
        missingDocName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IReportDraft) {
            let self = this;

            if (param) {
                self.reportId(param.reportId || '');
                self.reportCode(param.reportCode || '');
                self.reportName(param.reportName || '');
                self.draftSaveDate(param.draftSaveDate || '');
                self.missingDocName(param.missingDocName || '');
            }
        }
    }
}