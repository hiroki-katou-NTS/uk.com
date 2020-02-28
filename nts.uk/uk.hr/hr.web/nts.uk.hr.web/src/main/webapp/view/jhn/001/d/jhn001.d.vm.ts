module jhn001.d.viewmodel {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import info = nts.uk.ui.dialog.info;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import hasError = nts.uk.ui.errors.hasError;
    import permision = service.getCurrentEmpPermision;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        listReturn: KnockoutObservableArray<IReturnModel> = ko.observableArray([]);
        selectedReturn: KnockoutObservable<number> = ko.observable(1);

        listSendBackCls: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedSendBackCls: KnockoutObservable<any> = ko.observable(null);

        sendBackComment: KnockoutObservable<any> = ko.observable(null);

        constructor() {
            let self = this;

            self.listSendBackCls = ko.observableArray([
                new ItemModel(2, ''),
                new ItemModel(0, '記載不備'),
                new ItemModel(1, '添付書類不')
            ]);
            self.selectedSendBackCls = ko.observable(0);
            self.start();
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
                listData = [],
                dfd = $.Deferred();

            var dfdGetData = service.getDetailRp(reportId);

            block();
            $.when(dfdGetData).done((result: any) => {
                debugger;
                if (result.length > 0) {
                    for (var i = 0; i < result.length; i++) {
                        let _data = {
                            id: result[i].id,
                            cid: result[i].cid,
                            reportID: result[i].reportID,
                            phaseNum: result[i].phaseNum,
                            aprNum: result[i].aprNum,
                            comment: result[i].comment,
                            inputSid: result[i].inputSid, // sid của người login
                            appSid: result[i].appSid, // sid của người làm đơn
                            aprSid: result[i].aprSid,  // sid người approw
                            sendBackClass: result[i].sendBackClass,
                            arpAgency: result[i].arpAgency,
                            infoToDisplay: result[i].infoToDisplay
                        }
                        listData.push(_data);
                    }
                    self.listReturn(listData);
                    unblock();
                }
                unblock();
                dfd.resolve();
            });
            return dfd.promise();
        }

        sendBack() {
            let self = this;
            
            if (hasError()) {
                return;
            }

            nts.uk.ui.dialog.confirm({ messageId: "MsgJ_8" }).ifYes(() => {
                let selectedReturn = self.selectedReturn();
                let selectedSendBackCls = self.selectedSendBackCls();
                let  obj = _.find(self.listReturn(), function(o) { return o.id == selectedReturn; })

                if(selectedReturn == null || obj == undefined || selectedSendBackCls == 2){
                    return;    
                }
                
                let command = {
                    reportID: obj.reportID,
                    phaseNum: selectedReturn == 1 ? 0 : obj.phaseNum,
                    aprNum:   selectedReturn == 1 ? 0 : obj.aprNum,
                    comment:  self.sendBackComment(),
                    inputSid: selectedReturn == 1 ? null : obj.inputSid,
                    appSid:   obj.appSid,
                    aprSid:   selectedReturn == 1 ? null : obj.aprSid,
                    sendBackClass: selectedSendBackCls,
                    sendBackSID: selectedReturn == 1 ? obj.appSid : obj.aprSid,
                    selectedReturn : selectedReturn
                };
                
                block();
                service.saveData(command).done(() => {
                    info({ messageId: "MsgJ_10" }).then(function() {
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