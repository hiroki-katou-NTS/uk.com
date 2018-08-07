module nts.uk.com.view.cmf001.q {
    
    export module viewmodel {
        export class ScreenModel {
            // テストモード ＝ チェック中モード
            isCheckMode: KnockoutObservable<boolean> = ko.observable(true);
            isCheckFinish: KnockoutObservable<boolean> = ko.observable(false);
            // 中断 モード
            isStop: KnockoutObservable<boolean> = ko.observable(false);
            
            // received object from P
            params =  nts.uk.ui.windows.getShared("CMF001qParams");
            
            // 外部受入処理ＩＤを新規採番する. index at server
            processId: KnockoutObservable<string> = ko.observable('');
            
            // 外部受入実行結果ログ
            exacExeResultLog : IExacExeResultLog;
            
            // Q2_3
            codCode: KnockoutObservable<string>;
            // Q2_4
            codName: KnockoutObservable<string>;
            // Q3_3
            timeOver: KnockoutObservable<string>;
            
            // 項目移送表
            totalRecord: KnockoutObservable<number> =  ko.observable(0);
            currentRecord: KnockoutObservable<number> =  ko.observable(0);
            stopMode: KnockoutObservable<number> = ko.observable(0);
            stateBehavior: KnockoutObservable<number> = ko.observable(0);
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
            timeStart: any;
            timeNow: any;
            
            constructor() {
                let self = this;
                self.timeStart = new Date();
                let systemDate = Date.now();
                let convertdLocalTime = new Date(systemDate);
                let hourOffset = convertdLocalTime.getTimezoneOffset() / 60;
                convertdLocalTime.setHours( convertdLocalTime.getHours() - hourOffset ); 
        
                self.exacExeResultLog = {
                    cid: '', /* 会社ID set at server*/
                    conditionSetCd: self.params.conditionCd,  /* 条件設定コード*/
                    externalProcessId: self.processId(), // 外部受入処理ID＝取得した新規採番のＩＤ set at server
                    executorId: '',  /* 実行者ID ＝ログイン者  set at server*/
                    userId: '',  /* ユーザID ＝ログイン者 set at server*/
                    processStartDatetime: moment.utc(convertdLocalTime).toISOString(),  /* 処理開始日時  = システム日付時刻 set at server*/
                    standardAtr: '0',  /* 定型区分*/
                    executeForm: 1, /* 実行形態  ＝手動 */
                    targetCount: self.params.totalRecord, /*対象件数  ＝受入ファイル件数*/
                    errorCount: 0,  /* エラー件数 ＝0 */
                    fileName: self.params.fileName, /* ファイル名 ＝受入ファイル名 */
                    systemType: self.params.systemType, /* システム種類 */
                    resultStatus: null,/* 結果状態  ＝空白 */
                    processEndDatetime: null,/*処理終了日時＝空白 */
                    processAtr: 0, /* 処理区分 ＝受入チェック処理*/
                }

                self.codCode = ko.observable(self.params.conditionCd);
                self.codName = ko.observable(self.params.conditionName);
                self.timeOver = ko.observable('00:00:00');

                //init
                //self.totalRecord(self.params.totalRecord);
                self.totalRecord(self.params.totalRecord);
                self.currentRecord(0);
                self.numberFail(0);
                self.executionState('準備中');

            }
            //開始
            start(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();

                // ドメインモデル「外部受入実行結果ログ」に登録する
                let command: IExacExeResultLog = self.exacExeResultLog;
                service.addErrorLog(command).done(function(data){
                    self.processId(data);
                    self.executionCheck();
                });
                
                dfd.resolve();
                return dfd.promise();
            }
            /*
            * エラーボタン
            */
            gotoErrorList(){
                let self = this;
                nts.uk.ui.windows.setShared('CMF001-R', {
                    // add after test
                    imexProcessId: self.processId(),
                    code: self.codCode(),
                    nameSetting: self.codName()
                }, true);
                
                nts.uk.ui.windows.sub.modal("/view/cmf/001/r/index.xhtml");
            }
            /**
            * 閉じる
            */
            close(){
                 nts.uk.ui.windows.close();
            }
            
            /**
            * execution check mode
            */
            public executionCheck(): void {
                var self = this;
                let command: CSVManager = new CSVManager(
                    self.processId(),
                    self.totalRecord(),
                    self.currentRecord(),
                    self.numberFail(),
                    self.stopMode(),
                    self.stateBehavior());
                // find task id
                service.check(command).done(function(res: any) {
                    self.taskId(res.taskInfor.id);
                    // update state
                    self.updateState();
                }).fail(function(res: any) {
                    console.log(res);
                });
            }
            
            /**
            * execution import mode
            */
            public executionImport(): void {
                var self = this;
                // restart time
                self.timeStart = new Date();
                self.timeOver('00:00:00');
                // update mode
                self.isCheckMode(false);
                self.isStop(false);
                $('#BTN_STOP').focus();
                let command: CSVManager = new CSVManager(
                    self.processId(),
                    self.totalRecord(),
                    self.currentRecord(),
                    self.numberFail(),
                    self.stopMode(),
                    self.stateBehavior());

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
                if (self.isCheckMode() == true) {
                    self.executionState(getListProcessing()[0].value);
                }else {
                    self.executionState(getListProcessing()[2].value);
                }

                // 1秒おきに下記を実行
                nts.uk.deferred.repeat(conf => conf
                    .task(() => {
                        return nts.uk.request.asyncTask.getInfo(self.taskId()).done(function(res: any) {
                            // update state on screen
                            if (res.running || res.succeeded || res.cancelled) {
                                _.forEach(res.taskDatas, item => {
                                    // 「非同期タスク情報」を取得する
                                    let serverData = JSON.parse(item.valueAsString);
                                    console.log(serverData);
                                    if (self.isCheckMode() == true) {
                                        self.executionState(getListProcessing()[0].value);
                                    }else {
                                        self.executionState(getListProcessing()[2].value);
                                    }
                                    // "処理トータルカウント
                                    if (item.key == '処理トータルカウント') {
                                        self.totalRecord(item.valueAsNumber);
                                    }
                                    // 処理カウント
                                    if (item.key == '処理カウント') {
                                        self.currentRecord(item.valueAsNumber);
                                    }
                                    //エラー件数
                                    if (item.key == 'エラー件数') {
                                        self.numberFail(item.valueAsNumber);
                                    }
                                    // 動作状態
                                    if (item.key == '動作状態') {
                                        self.stateBehavior(item.valueAsNumber);
                                    }
                                });
                                if (res.running) {
                                    // 経過時間＝現在時刻－開始時刻
                                    let timeNow = new Date();
                                    let result = moment.utc(moment(timeNow, "HH:mm:ss").diff(moment(self.timeStart, "HH:mm:ss"))).format("HH:mm:ss");
                                    self.timeOver(result);
                                }
                            }

                            if (res.succeeded || res.failed || res.cancelled) {
                                self.isStop(true);
                                if (self.isCheckMode() == true) {
                                    if (res.succeeded) {
                                        // チェック終了モード
                                        self.isCheckFinish(true);
                                        $('#BTN_ERROR').focus();
                                    }
                                    self.executionState(getListProcessing()[1].value);
                                }else {
                                    self.executionState(getListProcessing()[3].value);
                                    $('#BTN_ERROR').focus();
                                }

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
                nts.uk.ui.dialog.confirm({ messageId: "Msg_911" })
                .ifYes(() => {
                    
                    if (nts.uk.text.isNullOrEmpty(self.taskId())) {
                        return;
                    }
                    nts.uk.request.asyncTask.requestToCancel(self.taskId());
                 })
                 .ifNo(() => {
                     return;
                 });
            }
        }
    }
    // 対象アルゴリズム
    export class CSVManager {
        processId: string;
        csvLine: number;
        currentLine: number;
        errorCount: number;
        stopMode: number;
        stateBehavior: number;
        constructor(processId: string, csvLine: number, currentLine: number, errorCount: number, stopMode: number, stateBehavior: number) {
            let self = this;
            self.processId = processId;
            self.csvLine = csvLine;
            self.currentLine = currentLine;
            self.errorCount = errorCount;
            self.stopMode = stopMode;
            self.stateBehavior = stateBehavior;
        }
    }

    export interface IExacExeResultLog {
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

    export function getListProcessing(): Array<ItemModel> {
        return [
            new ItemModel(0, 'チェック中...'),
            new ItemModel(1, 'チェック完了'),
            new ItemModel(2, '受入中...'),
            new ItemModel(3, '完了'),
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
    
    interface IExeResultLogCommandResult {
        processID: string;
    }
}