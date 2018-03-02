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
        // current line
        currentCode: KnockoutObservable<IImExErrorLog>;
        // result log
        imExExecuteResultLog: IImExExecuteResultLogR;
        // array list error log
        imExErrorLog: KnockoutObservableArray<IImExErrorLog>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        
        // get share name from Q, S
        nameSetting: KnockoutObservable<string>;
        imexProcessID: any ;
        
        constructor() {
            let self = this;
            // dump data
            let imExExecuteResultLogDump = {
                cid: 'cid',
                conditionSetCd: 'conditionSetCd',
                externalProcessId: '001',
                executorId: 'executorId',
                userId: 'userId',
                processStartDatetime: '2018/02/28 14:00:00',
                standardAtr: 100,
                executeForm: 1000,
                targetCount: 900,
                errorCount: 11,
                fileName: 'fileName',
                systemType: 10,
                resultStatus: 10,
                processEndDatetime: '2018/02/28 14:00:00',
                processAtr: 14
            };
            let imExErrorLogDump = {
                logSeqNumber: 0,
                cid: '1',
                externalProcessId: '001',
                csvErrorItemName: 'csvErrorItemName',
                csvAcceptedValue: 'csvAcceptedValue',
                errorContents: 'errorContents',
                recordNumber: 20,
                logRegDateTime: 'logRegDateTime',
                itemName: 'itemName',
                errorAtr: 8  
            };
            self.imExExecuteResultLog = imExExecuteResultLogDump;
            self.imExErrorLog = ko.observableArray( [ imExErrorLogDump, imExErrorLogDump ]);
            self.currentCode = ko.observable(imExErrorLogDump, imExErrorLogDump, imExErrorLogDump, imExErrorLogDump);
            self.columns = ko.observableArray([
                { headerText: 'レコード番号', key: 'recordNumber', width: 100 },
                { headerText: 'CSV項目名', key: 'csvErrorItemName', width: 150 }, 
                { headerText: '項目名', key: 'itemName', width: 150 }, 
                { headerText: '値', key: 'csvAcceptedValue', width: 150},
                { headerText: 'エラーメッセージ', key: 'errorContents', width: 150} 
            ]); 
            // param receive from Q, S
            self.imexProcessID = '001';
            self.nameSetting = ko.observable('A社人事管理情報');
        }
        
        //開始
        start(): JQueryPromise<any>{
            let self = this,
                dfd = $.Deferred();
            nts.uk.ui.errors.clearAll();
            service.getLogResults('001').done((itemList: Array<IImExExecuteResultLogR>) => {
                if (itemList && itemList.length > 0) {
                    // do something set to imExExecuteResultLog
                    self.imExExecuteResultLog =  itemList[0];
                }
                dfd.resolve();
            });
            return dfd.promise();
        }
        // エラー出力
        errorExport(){
            confirm({ messageId: "Msg_912" }).ifYes(() => {
                nts.uk.request.exportFile('exio/exi/execlog/generateCSV', { value: 'abc' }).done(() => {
                    console.log('DONE!!');
                });
                }).ifNo(() => {
                    return;
                })
        }
        //　閉じる
        close(){
             nts.uk.ui.windows.close();
        }
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