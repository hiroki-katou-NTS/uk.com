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
            self.getListReportSaveDraft().done(() => { 
                console.log('get list done');
            });
        }

        start(code?: string) {
            let self = this,
                listReportDraft = self.listReportDraft;
            listReportDraft.removeAll();

            self.getListReportSaveDraft().done(() => { });
        }
        
        getListReportSaveDraft(): JQueryPromise<any> {
            let self = this,
                listReportDraft = self.listReportDraft,
                dfd = $.Deferred();

            listReportDraft.removeAll();

            var dfdGetData = service.getListReportSaveDraft();

            block();
            $.when(dfdGetData).done((listReportDarft: any) => {
                if (listReportDarft) {
                    for (var i = 0; i < listReportDarft.length; i++) {
                        let _data = {
                            id: listReportDarft[i].reportID,
                            draftSaveDate: listReportDarft[i].draftSaveDate,
                            reportName: listReportDarft[i].reportName,
                            missingDocName: listReportDarft[i].missingDocName,
                            reportCode: listReportDarft[i].reportCode,
                            reportLayoutID: listReportDarft[i].reportCode
                        }
                        listReportDraft.push(_data);
                    }
                    self.reportId(listReportDarft[0].reportID);
                }
                unblock();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
     
        continueProcess() {
            let self = this;
            let reportId = self.reportId();

            if (reportId == null || reportId == '' || reportId == undefined)
                return;

            setShared('JHN001B_PARAMS', {
                obj: _.find(self.listReportDraft(), function(o) { return o.id == self.reportId(); })
            });
            close();
        }
        
        deleteSaveDraft() {
            let self = this;
            let reportId = self.reportId();

            if(reportId == null || reportId == '' || reportId == undefined)
                return;
            
            let objRemove = {
                reportId: string = reportId
            };

            block();
            service.removeData(objRemove).done(() => {
                info({ messageId: "Msg_40" }).then(function() {
                    self.start();
                });
            }).fail((mes: any) => {
                unblock();
            });
        }

        close() {
             setShared('JHN001B_PARAMS', {
                    reportId: null
                });
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