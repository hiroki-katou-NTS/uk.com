module jhn001.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import permision = service.getCurrentEmpPermision;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {

        fileId: KnockoutObservable<string>;
        filename: KnockoutObservable<string>;
        fileInfo: KnockoutObservable<any>;
        textId: KnockoutObservable<string>;
        accept: KnockoutObservableArray<string>;
        asLink: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        fileSize: KnockoutObservable<string>;
        uploadFinished: (fileInfo) => void;
        onfilenameclick: (fileId) => void;
        stereoType: KnockoutObservable<string>;
        allowDowloadFile: KnockoutObservable<boolean>;


        items: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        
        comboColumns    = [{ prop: 'name', length: 12 }];
        reportId        = null;
        layoutReportId  = null;
        dataShare       = null;
        totalFileSize   = 0;
        missingDocName  = '';


        constructor() {
            let self = this,
                dto: any = getShared('CPS002F_PARAMS') || {};
            self.fileId = ko.observable("");
            self.filename = ko.observable("");
            self.fileInfo = ko.observable(null);
            self.accept = ko.observableArray([]);
            self.textId = ko.observable("CPS001_71");
            self.asLink = ko.observable(true);
            self.enable = ko.observable(true);
            self.allowDowloadFile = ko.observable(true);
            self.fileSize = ko.observable("");
            self.stereoType = ko.observable("documentfile");

            self.uploadFinished = (fileInfo, id) => {
                console.log("upload File Finished");
                console.log(fileInfo);
                self.pushData(fileInfo, id);
            };

            self.onfilenameclick = (fileId) => {
                alert(fileId);
            };

        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            let listItem   = [];
            let dataShare  = getShared('JHN002F_PARAMS');
            self.dataShare = dataShare;
            self.reportId  = dataShare.reportId;
            self.layoutReportId  = dataShare.layoutReportId;
            let param = { reportId: dataShare.reportId, layoutReportId: dataShare.layoutReportId };

            block();
            service.getData(param).done((datafile: Array<IReportFileManagement>) => {
                console.log('get Data Start done');
                var totalSize = 0;
                _.forEach(datafile, function(item) {
                    totalSize = totalSize + item.fileSize;
                    item.urlFile = item.fileId == null || item.fileId == '' ? '#' : nts.uk.request.file.liveViewUrl(item.fileId);
                    listItem.push(new GridItem(item));
                });
                self.items(listItem);
                let sum = (totalSize / 1024).toFixed(2);
                self.totalFileSize = totalSize;
                self.fileSize(nts.uk.resource.getText("CPS001_85", [sum]));
                unblock();
                dfd.resolve();
            }).fail((mes) => {
                console.log('get Data Start fail');
                dfd.reject();
                unblock();
            });
            return dfd.promise();
        }

        pushData(fileInfo, id) {
            let self = this,
                listItem = self.items();
            
            if (fileInfo.id == '' || fileInfo.id == null || fileInfo.id == undefined) {
                return;
            }

            // check xem đã có file hay chưa, có rồi thì không có upload nua.
            let row: IReportFileManagement = _.filter(self.items(), function(o) { return o.id == id; });
            if (_.size(row) == 0) {
                return;
            }

            if(row[0].fileId){
                console.log("Đã có file rồi, không upload được nữa, xóa file để upload nhé.");
                return;
            }

            // check file size.
            var maxSize = 10;
            if (maxSize && fileInfo.originalSize > (maxSize * 1048576)) {
                nts.uk.ui.dialog.alertError({ messageId: 'MsgJ_70', messageParams: [maxSize] });
                return;
            }

            // check tổng size
            let totalSize = self.totalFileSize + fileInfo.originalSize;
            if (totalSize > (maxSize * 1048576)) {
                nts.uk.ui.dialog.alertError({ messageId: 'MsgJ_28', messageParams: [maxSize] });
                return;
            }
            
            let missingDocName = '';
            for (var i = 0; i < listItem.length; i++) {
                let fileData = listItem[i];
                if (fileData.fileId == null && id != fileData.id) {
                    missingDocName = missingDocName + fileData.docName + text('JHN001_B2_3_7_1');
                }
            }
            
            let objAdd = {
                docID: row[0].docID, //書類ID int
                docName: row[0].docName, //書類名 String
                fileId: fileInfo.id, //ファイルID String
                fileName: fileInfo.originalName, //ファイル名 String
                fileAttached: 1, //ファイル添付済     0:未添付、1:添付済み  int  
                fileStorageDate: moment.utc().toDate(), //ファイル格納日時 GeneralDate
                mimeType: fileInfo.mimeType, //MIMEタイプ String
                fileTypeName: fileInfo.fileType, //ファイル種別名 String
                fileSize: fileInfo.originalSize, //ファイルサイズ    đơn vị byte int
                delFlg: 0, //削除済     0:未削除、1:削除済 int 
                sampleFileID: row[0].sampleFileId, //サンプルファイルID String
                sampleFileName: row[0].sampleFileName,
                reportID: self.reportId, //届出ID int
                layoutReportId: self.layoutReportId,
                missingDocName : missingDocName = '' ? missingDocName : missingDocName.substring(0, missingDocName.length - 1),
                dataLayout: self.dataShare.command
            }

            // save file to domain AttachmentPersonReportFile
            var dfd = $.Deferred();
            service.addDocument(objAdd).done((reportId) => {
                console.log('reportId: '  + reportId);
                console.log('Add file Done');
                self.reportId = reportId;
                __viewContext['viewModel'].getDataAfterPushOrRemoveFile(reportId).done(() => {
                    unblock();
                    dfd.resolve();
                });
            }).fail((err) => {
                console.log('Add file fail');
                error({ messageId: err.messageId });
                dfd.reject();
                unblock();
            });
            return dfd.promise();
        }
        
        getDataAfterPushOrRemoveFile(reportId) {
            let self = this,
                dfd = $.Deferred();
            let listItem = [];
            let param = { reportId: reportId, layoutReportId: self.dataShare.layoutReportId };

            block();
            service.getData(param).done((datafile: Array<IReportFileManagement>) => {
                console.log('get Data After Push Or Remove File Done');
                var totalSize = 0;
                _.forEach(datafile, function(item) {
                    totalSize = totalSize + item.fileSize;
                    item.urlFile = item.fileId == null || item.fileId == '' ? '#' : nts.uk.request.file.liveViewUrl(item.fileId);
                    listItem.push(new GridItem(item));
                });
                self.items(listItem);
                let sum = (totalSize / 1024).toFixed(2);
                self.totalFileSize = totalSize;
                self.fileSize(nts.uk.resource.getText("CPS001_85", [sum]));
                unblock();
                dfd.resolve();
            }).fail((mes) => {
                console.log('get Data After Push Or Remove File fail');
                dfd.reject();
                unblock();
            });
            return dfd.promise();
        }

        checkSize() {
            var self = this;
            nts.uk.request.ajax("/shr/infra/file/storage/infor/" + self.fileId()).done(function(res) {
                self.fileInfo(res);
            });
        }


        deleteItem(id) {
            let self = this,
                listItem = self.items(),
                dfd = $.Deferred();
            // check xem đã có file hay chưa, có rồi thì không có upload nua.
            let row: IReportFileManagement = _.filter(self.items(), function(o) { return o.id == id; });
            if (_.size(row) == 0) {
                return;
            }
            
            if(row[0].fileId == null || row[0].fileId == '')
                return;
            
            let missingDocName = '';
            for (var i = 0; i < listItem.length; i++) {
                let fileData = listItem[i];
                if (fileData.fileId == null || id != fileData.id) {
                    missingDocName = missingDocName + fileData.docName + text('JHN001_B2_3_7_1');
                }
            }
            
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                nts.uk.request.ajax("/shr/infra/file/storage/infor/" + row[0].fileId)
                    .done(function(res) {
                        self.fileInfo(res);
                        block();
                        let command = {
                            cid: '',
                            fileId: row[0].fileId,
                            reportId: self.reportId,
                            layoutReportId: self.layoutReportId,
                            missingDocName: missingDocName = '' ? missingDocName : missingDocName.substring(0, missingDocName.length - 1)
                        };
                        
                        service.deleteDocument(command).done(() => {
                            info({ messageId: "MsgJ_40" }).then(function() {
                                __viewContext['viewModel'].getDataAfterPushOrRemoveFile(self.reportId).done(() => {
                                    console.log('Xoa file done');
                                    unblock();
                                    dfd.resolve();
                                });
                            });
                        }).fail((mes) => {
                            console.log('Xoa file fail');
                            dfd.reject();
                            unblock();
                        });
                    }).fail(function(res) {
                        console.log(res);
                    });


            }).ifNo(() => {
                unblock();
            });
            return dfd.promise();
        }

        restart() {
            let self = this;
            __viewContext['viewModel'].start().done(() => {
                self.filename("");
            });
        }

        close() {
            let self = this;
            setShared("JHN002F_DATA", self.reportId);
            close();
        }
    }

    // Object truyen tu man A sang
    interface IDataShare {
        layoutReportId: string;
        reportId: string;
    }

    class GridItem {
        id: string = '';
        cid: string = '';
        reportLayoutID: number = 0;
        docID: number = 0;
        docName: string = '';
        dispOrder: number = 0;
        requiredDoc: number = 0;
        docRemarks: string = '';
        sampleFileId: number = -1;
        sampleFileName: string = '';
        reportID: number = -1;
        fileName: string = '';
        fileId: string = '';
        fileSize: number = -1;
        urlFile: string = '';

        constructor(param: IReportFileManagement) {
            this.id = nts.uk.util.randomId();

            _.extend(this, param);
        }
    }

    interface IReportFileManagement {
        id: string;
        cid: string;
        reportLayoutID: number;
        docID: number;
        docName: string;
        dispOrder: number;
        requiredDoc: number;
        docRemarks: string;
        sampleFileId: number;
        sampleFileName: string;
        reportID: number;
        fileId: string;
        fileName: string;
        fileSize: number;
    }

    class ReportFileMana {
        id: string;
        cid: string;
        reportLayoutID: number;
        docID: number;
        docName: string;
        dispOrder: number;
        requiredDoc: number;
        docRemarks: string;
        sampleFileId: number;
        sampleFileName: string;
        reportID: number;
        fileId: string;
        fileName: string;
        fileSize: number;
        employeeId: string;
        constructor(param: IReportFileManagement) {
            this.id = param.id;
            this.cid = param.cid;
            this.reportLayoutID = param.reportLayoutID;
            this.docID = param.docID;
            this.docName = param.docName;
            this.dispOrder = param.dispOrder;
            this.requiredDoc = param.requiredDoc;
            this.docRemarks = param.docRemarks;
            this.sampleFileId = param.sampleFileId;
            this.sampleFileName = param.sampleFileName;
            this.reportID = param.reportID;
            this.fileId = param.fileId;
            this.fileName = param.fileName;
            this.fileSize = param.fileSize;
        }
    }
}