module nts.uk.com.view.cmf001.q {
    
    //import service = nts.uk.com.view.cmf001.q.service;
    export module viewmodel {
        export class ScreenModel {
            // テストモード ＝ チェック中モード
            isCheckMode: KnockoutObservable<boolean> = ko.observable(true);
            
            // received object from P
            params =  nts.uk.ui.windows.getShared("CMF001qParams");
            
            // 外部受入処理ＩＤを新規採番する. index at server
            processId: KnockoutObservable<string> = ko.observable('');
            
            // 外部受入動作管理
            exAcOpManager : KnockoutObservable<IExAcOpManager>;
            
            // 外部受入実行結果ログ
            exacExeResultLog : IExacExeResultLog;
            
            // Q2_3
            codCode: KnockoutObservable<string>;
            // Q2_4
            codName: KnockoutObservable<string>;
            // Q3_3
            timeOver: KnockoutObservable<string>;
            
            // stop view error mode
            totalRecord: KnockoutObservable<number> =  ko.observable(0);
            currentRecord: KnockoutObservable<number> =  ko.observable(0);
            count: number = 100;
            executionStartDate: string;
            executionState: KnockoutObservable<string> =  ko.observable('');
            executionError: KnockoutObservable<string>;
            periodInfo: string;
            taskId: KnockoutObservable<string> =  ko.observable('');            
            numberSuccess: KnockoutObservable<number>;
            numberFail: KnockoutObservable<number> =  ko.observable(0);
            //dataError: KnockoutObservableArray<ErrorContentDto>;
            //inputData: ScheduleBatchCorrectSettingSave;
            isError: KnockoutObservable<boolean>;
            isFinish: KnockoutObservable<boolean>;
            constructor() {
                let self = this;
                let startDate = new Date().toLocaleDateString();
                self.exacExeResultLog = {
                    cid: '', /* 会社ID set at server*/
                    //conditionSetCd: self.params.conditionCd,  /* 条件設定コード*/
                    conditionSetCd: '001',  /* 条件設定コード*/
                    externalProcessId: self.processId(), // 外部受入処理ID＝取得した新規採番のＩＤ set at server
                    executorId: '',  /* 実行者ID ＝ログイン者  set at server*/
                    userId: '',  /* ユーザID ＝ログイン者 set at server*/
                    processStartDatetime: "2000-02-13T00:00:00.000Z",  /* 処理開始日時  = システム日付時刻 set at server*/
                    standardAtr: '0',  /* 定型区分*/
                    executeForm: 1, /* 実行形態  ＝手動 */
                    //targetCount: self.params.totalRecord, /*対象件数  ＝受入ファイル件数*/
                    targetCount: 100, /*対象件数  ＝受入ファイル件数*/
                    errorCount: 0,  /* エラー件数 ＝0 */
                    //fileName: self.params.fileName, /* ファイル名 ＝受入ファイル名 */
                    fileName: 'A社人事管理情報', /* ファイル名 ＝受入ファイル名 */
                    //systemType: self.params.systemType, /* システム種類 */
                    systemType: 0, /* システム種類 */
                    resultStatus: null,/* 結果状態  ＝空白 */
                    processEndDatetime: null,/*処理終了日時＝空白 */
                    processAtr: 0, /* 処理区分 ＝受入チェック処理*/
                }
                
                // dump data. Delete after phase 2 
                self.exAcOpManager = ko.observable({ 
                    cid: '1', /* 会社ID */
                    processId: '001', /* 外部受入処理ID */
                    errorCount: 0, /*  エラー件数 */
                    interruption: 0, /* 中断するしない  */
                    processCount: 0, /* 処理カウント */
                    processTotalCount: 100, /* 処理トータルカウント  */
                    stateBehavior: 6, /* 動作状態  */
                 });
                self.codCode = ko.observable('001');
                self.codName = ko.observable('A社人事管理情報');
                self.timeOver = ko.observable('00:01:27');
                
                //init
                self.totalRecord(self.exAcOpManager().processTotalCount);
                self.currentRecord(self.exAcOpManager().processCount);
                self.numberFail(self.exAcOpManager().errorCount);
                self.executionState(getListProcessing()[self.exAcOpManager().stateBehavior].value);
                
            }
            //開始
            start(): JQueryPromise<any>{
                let self = this,
                    dfd = $.Deferred();
                
                // ドメインモデル「外部受入実行結果ログ」に登録する
                let command: IExacExeResultLog = self.exacExeResultLog;
                service.addErrorLog(command).done(function(res:any){
                    self.execution();
                    dfd.resolve();
                }).fail(function(res:any){
                    console.log(res);
                });
                
                return dfd.promise();
            }
            
            //エラーボタン
            gotoErrorList(){
                let self = this;
                nts.uk.ui.windows.setShared('CMF001-R', {
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
            /**
             * execution
             */
            public execution(): void {
                var self = this;
                let command: CSVManager = new CSVManager(
                    getListProcessing()[self.exAcOpManager().stateBehavior].value, 
                    self.exAcOpManager().processTotalCount, 
                    self.exAcOpManager().processCount, 
                    self.exAcOpManager().errorCount, 
                    self.exAcOpManager().stateBehavior);
                
                // find task id
                service.executionImportCsvData(command).done(function(res: any) {
                    self.taskId(res.taskInfor.id);
                    // update state
                    self.updateState();
                }).fail(function(res: any) {
                    console.log(res);
                });
            }
            /**
             * updateState
             */
            private updateState() {
                let self = this;                
                // Set execution state to processing
                self.executionState('処理中… ');
                
                nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(function(res: any) {
                        // update state on screen
                        if (res.running || res.succeeded || res.cancelled) {
                             _.forEach(res.taskDatas, item => {
                                let serverData = JSON.parse(item.valueAsString);
                                //self.testData(serverData);
                                 console.log(serverData);
                                 self.executionState('処理中… ');
                                 //totalRecord
                                 if (item.key == 'TOTAL_RECORD') {
                                     self.totalRecord(item.valueAsNumber);
                                }
                                 if (item.key == 'NUMBER_OF_SUCCESS') {
                                     self.currentRecord(item.valueAsNumber);
                                }
                                if (item.key == 'NUMBER_OF_ERROR') {
                                     self.numberFail(item.valueAsNumber);
                                }
                            });
                            
                        }
                        
                        if (res.succeeded || res.failed || res.cancelled) {
//                          
                            self.executionState('完了');
                            
                            if (res.succeeded) {
                                $('#closeDialog').focus();
                            }                      
                        }
                    });
                }).while(infor => {
                    return infor.pending || infor.running;
                }).pause(1000));
            }
             /**
             * function cancel execution
             */
            private stopExecution(): void {
                let self = this;
                
                if (nts.uk.text.isNullOrEmpty(self.taskId())) {
                    return;
                }
                // interrupt process import then close dialog
                nts.uk.request.asyncTask.requestToCancel(self.taskId());
                //nts.uk.ui.windows.close();
            }
        }
     }
    
    // 対象アルゴリズム
    export class CSVManager {
        test: string;
        csvLine: number;
        currentLine: number;
        errorCount: number;
        stateBehavior: number;
        constructor(test: string, csvLine: number, currentLine: number, errorCount: number, stateBehavior: number) {
            let self = this;
            self.test = test;
            self.csvLine = csvLine;
            self.currentLine = currentLine;
            self.errorCount = errorCount;
            self.stateBehavior = stateBehavior;
        }
    }
    
    export interface IExacExeResultLog{
        /* 会社ID */
        cid: string;
        /* 条件設定コード*/
        conditionSetCd: string;
        /* 外部受入処理ID*/
        externalProcessId: string;
        /* 実行者ID*/
        executorId: string;
        /* ユーザID*/
        userId: string;
        /* 処理開始日時*/
        processStartDatetime: any;
        /* 定型区分*/
        standardAtr: string;
        /* 実行形態 */
        executeForm: number;
        /*対象件数 */
        targetCount: number;
        /* エラー件数*/
        errorCount: number;
        /* ファイル名 */
        fileName: string;
        /* システム種類 */
        systemType: number;
        /* 結果状態 */
        resultStatus: number;
        /*処理終了日時 */
        processEndDatetime: string;
        /* 処理区分 */
        processAtr: number;    
    }
    
    export interface IExAcOpManager {
        /* 会社ID */
        cid: string;
        /* 外部受入処理ID */
        processId: string;
        /*  エラー件数 */
        errorCount: number;
        /* 中断するしない */
        interruption: number;
        /* 処理カウント*/
        processCount: number;
        /*  処理トータルカウント */
        processTotalCount: number;
        /* 動作状態  */
        stateBehavior: number;
    }
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
            new ItemModel(0, '準備中...'),
            new ItemModel(1, '出力中...'),
            new ItemModel(2, '受入中...'),
            new ItemModel(3, 'テスト完了'),
            new ItemModel(4, '中断終了'),
            new ItemModel(5, '異常終了'),
            new ItemModel(6, 'チェック中...'),
            new ItemModel(7, '出力完了'),
            new ItemModel(8, '受入完了')
        ];
    }
    export class ItemModel {
        key: number;
        value: string;
        
        constructor(key: number, value: string) {
            let self = this;
            self.key = key;
            self.value = value;    
        }
    }
}