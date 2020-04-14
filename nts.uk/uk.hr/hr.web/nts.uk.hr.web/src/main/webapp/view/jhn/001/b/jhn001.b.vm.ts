module jhn001.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
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
        hasRemove = false;
        
        reportColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', key: 'id', width: 0, hidden: true },
            { headerText: text('JHN001_B2_3_1'), key: 'draftSaveDate', width: 120,  hidden: false },
            { headerText: text('JHN001_B2_3_2'), key: 'reportName', width: 260, hidden: false },
            { headerText: text('JHN001_B2_3_3'), key: 'missingDocName', width: 390, hidden: false }
        ]);

        constructor() {
            let self = this,
                listReportDraft = self.listReportDraft;
            self.hasRemove = false;
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
                if (listReportDarft.length > 0) {
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
                    unblock();
                }
                unblock();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        continueProcess() {
            let self = this;
            let reportId = self.reportId();
            let listReportDraft = self.listReportDraft();

            if (reportId == null || reportId == '' || reportId == undefined){
                alertError({ messageId: "MsgJ_24" });
                return;
            }
            
            if(listReportDraft.length == 0){
                reportId = null;
            }
            
            setShared('JHN001B_PARAMS', { reportId : reportId , isClose : false , isContinue : true, hasRemove : self.hasRemove});
            close();
        }
        
        deleteSaveDraft() {
            let self = this;
            let reportId = self.reportId();

            if (reportId == null || reportId == '' || reportId == undefined){
                alertError({ messageId: "MsgJ_24" });
                return;
            }

            let objRemove = {
                reportId: reportId
            };

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                block();
                
                let objRemove = {
                    reportId: string = reportId
                };

                service.removeData(objRemove).done(() => {
                    info({ messageId: "MsgJ_40" }).then(function() {
                        self.hasRemove = true;
                        self.start();
                    });
                }).fail((mes: any) => {
                    unblock();
                });
            }).ifNo(() => { });
        }

        closeDialog() {
            let self = this;
            let reportId = self.reportId();
            setShared('JHN001B_PARAMS', { reportId: reportId , isClose : true , isContinue : false, hasRemove : self.hasRemove });
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