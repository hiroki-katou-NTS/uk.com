module nts.uk.com.view.cmf001.q.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
  
    export function getListProcessing(): Array<ItemModel> {
        return [
//            new ItemModel(0, getText('Enum_OperatingCondition_PREPRARING')),
//            new ItemModel(1, getText('Enum_OperatingCondition_EXPORTING')),
//            new ItemModel(2, getText('Enum_OperatingCondition_IMPORTING')),
//            new ItemModel(3, getText('Enum_OperatingCondition_TEST_FINISH')),
//            new ItemModel(4, getText('Enum_OperatingCondition_STOP_FINISH')),
//            new ItemModel(5, getText('Enum_OperatingCondition_ERROR_FINISH')),
//            new ItemModel(6, getText('Enum_OperatingCondition_CHECKING')),
//            new ItemModel(7, getText('Enum_OperatingCondition_EXPORT_FINISH')),
//            new ItemModel(8, getText('Enum_OperatingCondition_IMPORT_FINISH'))
            // delete after have resource
            new ItemModel(0, getText('準備中')),
            new ItemModel(1, getText('出力中')),
            new ItemModel(2, getText('受入中')),
            new ItemModel(3, getText('テスト完了')),
            new ItemModel(4, getText('中断終了')),
            new ItemModel(5, getText('異常終了')),
            new ItemModel(6, getText('チェック中')),
            new ItemModel(7, getText('出力完了')),
            new ItemModel(8, getText('受入完了'))
        ];
    }
    
    export class ScreenModel {
        // Check mode
        isCheckMode: KnockoutObservable<boolean>;
        // stop view error mode
        isStopMode: KnockoutObservable<boolean>;
        
        // dto from server
        exAcOpManage: ExAcOpManage;
        processId: any;
        
        // object param
        params = getShared("CMF001qParams");
        codCode: string;
        codeName: string;
        timeOver: string;
        operatingCondition: string;
        mode: number;
        systemType: number;
        conditionCd: string;
        fileName: string;
        fileId: string;
        totalRecord: number;
        
        //・対象アルゴリズム
        iExacExeResultLog: IExacExeResultLog;
        
        constructor() {
            let self = this;
            // mode
            self.isCheckMode = ko.observable(true);
            self.isStopMode = ko.observable(false);
            
            // 外部受入処理ＩＤを新規採番する
            self.processId = "1";
            
            // dump data. Delete after phase 2 
            self.exAcOpManage = new ExAcOpManage('1', '001', 8, 0, 92, 100, 6);
            self.codCode = "001";
            self.codeName = "A社人事管理情報";
            self.timeOver = "00:01:27";
            self.operatingCondition = getListProcessing()[ self.exAcOpManage.stateBehavior].value;
            
            // params from P
            self.mode = self.params.mode;
            self.systemType= self.params.systemType;
            self.conditionCd= self.params.conditionCd;
            self.fileName= self.params.fileName;
            self.fileId= self.params.fileId;
            self.totalRecord= self.params.totalRecord;
            
            self.iExacExeResultLog = {
                cid: '',
                conditionSetCd: self.codCode,
                externalProcessId: self.processId,
                executorId: '',
                userId: '',
                processStartDatetime: self.timeOver,
                standardAtr: self.mode,
                executeForm: 1,
                targetCount: self.totalRecord,
                errorCount: 0,
                fileName: self.fileName,
                systemType: self.systemType,
                resultStatus: null,
                processEndDatetime: '',
                processAtr: 0
            }
        }
        
        //開始
        start(): JQueryPromise<any>{
            let self = this,
                dfd = $.Deferred();
            nts.uk.ui.errors.clearAll();
            
            // ドメインモデル「外部受入実行結果ログ」に登録する
            let command = self.iExacExeResultLog;
            service.addDataErrorLog(command).done(function() {
            }).fail(error => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
            
            return dfd.promise();
        }
        
        // 中断ボタン
        stop() {
            let self = this;
             confirm({ messageId: "Msg_911" }).ifYes(() => {
                
                 self.isStopMode(true);
                 if (self.isCheckMode()) {
                     self.exAcOpManage.stateBehavior = 3;
                     self.operatingCondition = getListProcessing()[ self.exAcOpManage.stateBehavior].value;
                 }
                 if (!self.isCheckMode()) {
                     self.exAcOpManage.stateBehavior = 8;
                     self.operatingCondition = getListProcessing()[ self.exAcOpManage.stateBehavior].value;

                 }
                 
                 // do stop 
             }).ifNo(() => {
                 return;
             })
            
        }
        
        // 受け入れボタン
        importFile() {
            let self = this;
            self.isCheckMode(false);
            self.isStopMode(false);
            self.exAcOpManage.stateBehavior = 2;
            self.operatingCondition = getListProcessing()[ self.exAcOpManage.stateBehavior].value;
            
            // do something
        }
        
        //エラーボタン
        gotoErrorList(){
            let self = this;
            setShared('CMF001-R', {
                // add after test
                imexProcessId: '001',
                nameSetting: 'A社人事管理情報'
            }, true);
            
            nts.uk.ui.windows.sub.modal("/view/cmf/001/r/index.xhtml");
        }
        
        // 閉じる
        close(){
             nts.uk.ui.windows.close();
        }
     }
    
    class ItemModel {
        key: number;
        value: string;   
        
        constructor(key: number,value: string) {
            let self = this;
            self.key = key;
            self.value = value;
        }
    }
    
    class IExAcOpManage{
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 外部受入処理ID
        */
        processId: string;
        
        /**
        * エラー件数
        */
        errorCount: number;
        
        /**
        * 中断するしない
        */
        interruption: number;
        
        /**
        * 処理カウント
        */
        processCount: number;
        
        /**
        * 処理トータルカウント
        */
        processTotalCount: number;
        
        /**
        * 動作状態
        */
        stateBehavior: number;
    }
    
    class ExAcOpManage{
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 外部受入処理ID
        */
        processId: string;
        
        /**
        * エラー件数
        */
        errorCount: number;
        
        /**
        * 中断するしない
        */
        interruption: number;
        
        /**
        * 処理カウント
        */
        processCount: number;
        
        /**
        * 処理トータルカウント
        */
        processTotalCount: number;
        
        /**
        * 動作状態
        */
        stateBehavior: number;
        
        constructor(cid: string,processId: string, errorCount: number, interruption: number, processCount: number, processTotalCount: number, stateBehavior: number ) {
            let self = this;
            self.cid = cid;
            self.processId =  processId;
            self.errorCount = errorCount;
            self.interruption = interruption;
            self.processCount = processCount;
            self.processTotalCount = processTotalCount;
            self.stateBehavior = stateBehavior;
        }
    }
    
    interface  IExacExeResultLog{
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
        standardAtr: string;
        
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
}