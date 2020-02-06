module nts.uk.hr.view.jhn001.d.viewmodel {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import permision = service.getCurrentEmpPermision;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ScreenModel {
        listReturn: KnockoutObservableArray<IReturnModel> = ko.observableArray([]);
        selectedReturn: KnockoutObservable<number> = ko.observable(1);

        listSendBackCls: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedSendBackCls: KnockoutObservable<any> = ko.observable(null);

        sendBackComment: KnockoutObservable<any> = ko.observable(null);

        constructor() {
            let self = this;

            self.listSendBackCls = ko.observableArray([
                new ItemModel(1, 'gg'),
                new ItemModel(2, '記載不備'),
                new ItemModel(3, '添付書類不')
            ]);
            self.selectedSendBackCls = ko.observable(2);
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dataShare = getShared('JHN001D_PARAMS');
            if(dataShare == null || dataShare == undefined )
                return;
            let reportId = dataShare.reportId;
            self.getDetail(reportId).done(() => {
                console.log('get data done');
            });
        }

        getDetail(reportId): JQueryPromise<any> {
            let self = this,
                listReturn = self.listReturn,
                dfd = $.Deferred();

            var dfdGetData = service.getDetailRp(reportId);

            block();
            $.when(dfdGetData).done((listReturn: any) => {
                debugger;
                if (listReturn.length > 0) {
                    for (var i = 0; i < listReturn.length; i++) {
                        let _data = {
                            id: listReturn[i].id,
                            cid: listReturn[i].cid,
                            reportID: listReturn[i].reportID,
                            phaseNum: listReturn[i].phaseNum,
                            aprNum: listReturn[i].aprNum,
                            comment: listReturn[i].comment,
                            inputSid: listReturn[i].inputSid, // sid của người login
                            appSid: listReturn[i].appSid, // sid của người làm đơn
                            aprSid: listReturn[i].aprSid,  // sid người approw
                            sendBackClass: listReturn[i].sendBackClass,
                            arpAgency: listReturn[i].arpAgency,
                            infoToDisplay: listReturn[i].infoToDisplay
                        }
                        listReturn.push(_data);
                    }
                    unblock();
                }
                unblock();
                dfd.resolve();
            });
            return dfd.promise();
        }

        save() {
            let self = this;

            nts.uk.ui.dialog.confirm({ messageId: "Msgj_8" }).ifYes(() => {
                block();
                let selectedReturn = self.selectedReturn();
                let selectedSendBackCls = self.selectedSendBackCls();
                let  obj = _.find(self.listReturn(), function(o) { return o.id == selectedReturn; })

                let command = {
                    reportID: obj.reportID,
                    phaseNum: selectedReturn == 1 ? 0 : obj.phaseNum,
                    aprNum:   selectedReturn == 1 ? 0obj.aprNum,
                    comment:  self.sendBackComment(),
                    inputSid: selectedReturn == 1 ? null : obj.inputSid,
                    appSid:   obj.appSid,
                    aprSid:   selectedReturn == 1 ? null : obj.aprSid,
                    sendBackClass: selectedSendBackCls == 1 ? null : selectedSendBackCls,
                    sendBackSID: selectedReturn == 1 ? obj.appSid : obj.aprSid,
                    selectedReturn : selectedReturn
                };

                service.saveData(command).done(() => {
                    info({ messageId: "Msg_15" }).then(function() {
                        unblock();
                        close();
                    });
                }).fail((mes: any) => {
                    unblock();
                });
            }).ifNo(() => { });
        }

        close() {
            close();
        }
    }

    interface IReturnModel {
        id: number;
        cid: string;
        reportID: number;
        phaseNum: number;
        aprNum: number;
        comment: string;
        inputSid: string;
        appSid: string;
        aprSid:string;
        sendBackClass: string;
        arpAgency: boolean;
        infoToDisplay; string;
    }

    class ReturnModel {
        id: number;
        cid: string;
        reportID: number;
        phaseNum: number;
        aprNum: number;
        comment: string;
        inputSid: string;
        appSid: string;
        aprSid:string;
        sendBackClass: string;
        arpAgency: boolean;
        infoToDisplay; string;

        constructor(param: IReturnModel) {
            this.id = param.id
            this.cid = param.cid;
            this.reportID = param.reportID;
            this.phaseNum = param.phaseNum;
            this.aprNum = param.aprNum;
            this.comment = param.comment;
            this.inputSid = param.inputSid;
            this.appSid = param.appSid;
            this.aprSid = param.aprSid;
            this.sendBackClass = param.sendBackClass;
            this.arpAgency = param.arpAgency;
            this.infoToDisplay = param.infoToDisplay;
        }
    }

    class ItemModel {
        id: number;
        name: string;

        constructor(id: number, name: string) {
            this.id = id;
            this.name = name;
        }
    }
}