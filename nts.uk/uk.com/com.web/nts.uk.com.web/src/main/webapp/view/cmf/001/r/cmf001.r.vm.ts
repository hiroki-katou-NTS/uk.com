module nts.uk.com.view.cmf001.r.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.com.view.cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        // param from resources
        CODE_BANGO: string = getText("CMF001_518");
        CSV_FIELD_NAME: string = getText("CMF001_519");
        FIELD_NAME: string = getText("CMF001_520");
        VALUE: string = getText("CMF001_521");
        MESSENGE: string = getText("CMF001_522");
        
        // result log
        imExExecuteResultLog: KnockoutObservable<IImExExecuteResultLogR>;
        datetime : KnockoutObservable<string>;
        
        // gridList
        imExErrorLog: KnockoutObservableArray<IImExErrorLog>;
        currentCode: KnockoutObservable<IImExErrorLog>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        
        //params from Q, S
        code: KnockoutObservable<string>;
        nameSetting: KnockoutObservable<string>;
        imexProcessID: any ;
        
        // csv 
        dataError: KnockoutObservableArray<IErrorContentCSV>;
        itemDataError: KnockoutObservable<IErrorContentCSV>;
        
        constructor() {
            let self = this;
            // param received from Q, S
            let paramReceived = getShared('CMF001-R');
            self.code =  ko.observable(paramReceived.code);
            self.imexProcessID = paramReceived.imexProcessId;
            self.nameSetting = ko.observable(paramReceived.nameSetting);
            
            self.datetime = ko.observable('');
            self.imExExecuteResultLog = ko.observable({
                cid: '',
                conditionSetCd: '',
                externalProcessId: '',
                executorId: '',
                userId: '',
                processStartDatetime: '',
                standardAtr: null,
                executeForm: null,
                targetCount: 0,
                errorCount: 0,
                fileName: '',
                systemType: null,
                resultStatus: null,
                processEndDatetime: '',
                processAtr: null,
            });
            
            // grid list constructor
            self.imExErrorLog =  ko.observableArray([]);
            let dataNull = {
                logSeqNumber: null,
                cid: '',
                externalProcessId: '',
                csvErrorItemName: '',
                csvAcceptedValue: '',
                errorContents: '',
                recordNumber: null,
                logRegDateTime: '',
                itemName: '',
                errorAtr: null,
            }
            self.currentCode = ko.observable(dataNull);
            self.columns = ko.observableArray([
                { headerText: self.CODE_BANGO, key: 'recordNumber', width: 100 },
                { headerText: self.CSV_FIELD_NAME, key: 'csvErrorItemName', width: 150 },
                { headerText: self.FIELD_NAME, key: 'itemName', width: 150 },
                { headerText: self.VALUE, key: 'csvAcceptedValue', width: 150 },
                { headerText: self.MESSENGE, key: 'errorContents', width: 150 }
            ]);

            // csv template
            self.itemDataError = ko.observable({
                nameSetting: self.nameSetting(),
                resultLog: null,
                errorLog: null,
            });
            self.dataError = ko.observableArray([]);
        }
        
        //開始
        start(): JQueryPromise<any>{
            let self = this,
                dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            // ドメインモデル「外部受入実行結果ログ」を取得する
            service.getLogResults(self.imexProcessID).done((itemList: Array<IImExExecuteResultLogR>) => {
                console.log("KET QUA" + itemList.length);
                if (itemList && itemList.length > 0) {
                    self.imExExecuteResultLog(itemList[0]);
                    self.datetime(moment.utc(self.imExExecuteResultLog.processStartDatetime).format("YYYY/MM/DD H:mm:ss")); 
                    console.log("DATE TIME" +  self.datetime);
                    self.itemDataError().resultLog = itemList[0];
                }
                else {
                     self.nameSetting('');   
                }

                // set to list csv data
                self.dataError.push(self.itemDataError());
                dfd.resolve();
                nts.uk.ui.block.clear();
            }).fail(function(res: any) {
                alertError({ messageId: res.messageId });
                dfd.reject();
                nts.uk.ui.block.clear();
            });

            // ドメインモデル「外部受入エラーログ」を取得する
            service.getErrorLogs(self.imexProcessID).done((itemListError: Array<IImExErrorLog>) => {
                if (itemListError && itemListError.length > 0) {
                    itemListError.forEach(x => self.imExErrorLog.push(x));
                    self.currentCode = ko.observable(itemListError[0]);
                    self.itemDataError().errorLog = self.imExErrorLog();
                }
                dfd.resolve();
                 nts.uk.ui.block.clear();
            }).fail(function(res: any) {
                alertError({ messageId: res.messageId });
                dfd.reject();
                 nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }
        
        // エラー出力
        errorExport(){
            let self = this;
            confirm({ messageId: "Msg_912" }).ifYes(() => {
                nts.uk.ui.block.invisible();
                service.exportDatatoCsv(self.dataError()[0]).fail(function(res: any) {
                    alertError({ messageId: res.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }).ifNo(() => {
                return;
            });
        }
        
        //　閉じる
        close(){
            nts.uk.ui.windows.close();
        }
     }
    
     interface IErrorContentCSV {
         nameSetting: string;
         resultLog: IImExExecuteResultLogR;
         errorLog: IImExErrorLog[];
     }
     interface IImExErrorLog{
        /**
        * ログ連番
        */
       logSeqNumber: number;
        
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * CSVエラー項目名
        */
        csvErrorItemName: string;
        
        /**
        * CSV受入値
        */
        csvAcceptedValue: string;
        
        /**
        * エラー内容
        */
        errorContents: string;
        
        /**
        * レコード番号
        */
        recordNumber: number;
        
        /**
        * ログ登録日時
        */
        logRegDateTime: string;
        
        /**
        * 項目名
        */
        itemName: string;
        
        /**
        * エラー発生区分
        */
        errorAtr: number;
    }
    
     class ImExErrorLog {
        /**
        * ログ連番
        */
       logSeqNumber: number;
        
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * CSVエラー項目名
        */
        csvErrorItemName: string;
        
        /**
        * CSV受入値
        */
        csvAcceptedValue: string;
        
        /**
        * エラー内容
        */
        errorContents: string;
        
        /**
        * レコード番号
        */
        recordNumber: number;
        
        /**
        * ログ登録日時
        */
        logRegDateTime: string;
        
        /**
        * 項目名
        */
        itemName: string;
        
        /**
        * エラー発生区分
        */
        errorAtr: number;
        
        constructor(param: IImExErrorLog) {
            let self = this;
            if (param)
            {
                self.logSeqNumber = param.logSeqNumber;
                self.cid = param.cid;
                self.externalProcessId = param.externalProcessId;
                self.csvErrorItemName = param.csvErrorItemName;
                self.csvAcceptedValue = param.csvAcceptedValue;
                self.errorContents = param.errorContents;
                self.recordNumber = param.recordNumber;
                self.logRegDateTime = param.logRegDateTime;
                self.errorAtr = param.errorAtr;
            }
        }
    }

     interface IImExExecuteResultLogR{
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 条件設定コード
        */
        conditionSetCd: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * 実行者ID
        */
        executorId: string;
        
        /**
        * ユーザID
        */
        userId: string;
        
        /**
        * 処理開始日時
        */
        processStartDatetime: string;
        
        /**
        * 定型区分
        */
        standardAtr: number;
        
        /**
        * 実行形態
        */
        executeForm: number;
        
        /**
        * 対象件数
        */
        targetCount: number;
        
        /**
        * エラー件数
        */
        errorCount: number;
        
        /**
        * ファイル名
        */
        fileName: string;
        
        /**
        * システム種類
        */
        systemType: number;
        
        /**
        * 結果状態
        */
        resultStatus: number;
        
        /**
        * 処理終了日時
        */
        processEndDatetime: string;
        
        /**
        * 処理区分
        */
        processAtr: number;
    }
    
     class ImExExecuteResultLogR {
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 条件設定コード
        */
        conditionSetCd: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * 実行者ID
        */
        executorId: string;
        
        /**
        * ユーザID
        */
        userId: string;
        
        /**
        * 処理開始日時
        */
        processStartDatetime: string;
        
        /**
        * 定型区分
        */
        standardAtr: number;
        
        /**
        * 実行形態
        */
        executeForm: number;
        
        /**
        * 対象件数
        */
        targetCount: number;
        
        /**
        * エラー件数
        */
        errorCount: number;
        
        /**
        * ファイル名
        */
        fileName: string;
        
        /**
        * システム種類
        */
        systemType: number;
        
        /**
        * 結果状態
        */
        resultStatus: number;
        
        /**
        * 処理終了日時
        */
        processEndDatetime: string;
        
        /**
        * 処理区分
        */
        processAtr: number;
        
        constructor(param: IImExExecuteResultLogR) {
            let self = this;
            if (param){
                self.cid = param.cid;
                self.conditionSetCd = param.conditionSetCd;
                self.externalProcessId = param.externalProcessId;
                self.executorId = param.executorId;
                self.userId = param.userId;
                self.processStartDatetime = param.processStartDatetime;
                self.standardAtr = param.standardAtr;
                self.executeForm = param.executeForm;
                self.targetCount = param.targetCount;
                self.errorCount = param.errorCount;
                self.fileName = param.fileName;
                self.systemType = param.systemType;
                self.resultStatus = param.resultStatus;
                self.processEndDatetime = param.processEndDatetime;
                self.processAtr = param.processAtr;                                
            }
        }
    }
}