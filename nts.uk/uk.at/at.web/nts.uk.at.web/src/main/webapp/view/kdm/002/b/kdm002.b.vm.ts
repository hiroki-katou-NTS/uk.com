module nts.uk.at.view.kdm002.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {

        export class ScreenModel {
            // param from resources
            CODE_EMP: string = getText("KDM002_25");
            NAME_EMP: string = getText("KDM002_26");
            ERROR_MESS: string = getText("KDM002_27")
            
            // params
            parrams = nts.uk.ui.windows.getShared('KDM002Params');
            empployeeList: any = parrams.empployeeList;
            periodDate: any =  parrams.periodDate;
            date: any = parrams.date;
            maxday: any = parrams.maxDaysCumulationByEmp;   
            
            // table result
            timeStart: KnockoutObservable<string> = ko.observable('2018/01/01 13:25:16');
            timeOver: KnockoutObservable<string> = ko.observable('00:03:07.123');
            status: KnockoutObservable<string> = ko.observable('処理中');
            result: KnockoutObservable<string> = ko.observable('2 / 25人');
            total: KnockoutObservable<number> = ko.observable(empployeeList.length);
            pass: KnockoutObservable<number> = ko.observable(0);
            error: KnockoutObservable<number> = ko.observable(0);
            // gridList
            imErrorLog: KnockoutObservableArray<IErrorLog>;
            currentCode: KnockoutObservable<IErrorLog>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            
            // flag
            isStop: KnockoutObservable<boolean> = ko.observable(false);
            isComplete: KnockoutObservable<boolean> = ko.observable(false);
            
            constructor() {
                let dataDump = {
                    employeeCode: '',
                    employeeName: '',
                    errorMessage: ''
                };
                
                self.currentCode = ko.observable(dataDump);
                self.imErrorLog =  ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: '会社コード', key: 'employeeCode', width: 100 },
                    { headerText: '会社名', key: 'employeeName', width: 150 },
                    { headerText: 'イラー内容', key: 'errorMessage', width: 300 }
                ]);
            }
            
            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var self = this;
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
            * execution 
            */
            public execution(): void {
                var self = this;
                let command: CheckFuncDto = new CheckFuncDto(
                    self.total(),
                    0,
                    0,
                    self.imErrorLog());
                // find task id
                service.execution(command).done(function(res: any) {
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
                                    // "処理トータルカウント
                                    if (item.key == 'NUMBER_OF_ERROR') {
                                    }
                                    // 処理カウント
                                    if (item.key == 'NUMBER_OF_SUCCESS') {
                                    }
                                    //エラー件数
                                    if (item.key == 'NUMBER_OF_TOTAL') {
                                    }
                                    // 動作状態
                                    if (item.key == 'ERROR_LIST') {
                                    }
                                });
                            }

                            if (res.succeeded || res.failed || res.cancelled) {
                                self.isStop(true);
                            }
                        });
                    }).while(infor => {
                        return infor.pending || infor.running;
                    }).pause(1000));
            }
            
            // 中断ボタンをクリックする
            stop(){
                let self = this;
                self.isStop(true);
                self.isComplete(true);
                ('#BTN_CLOSE').focus();
            }
            
            //閉じるボタンをクリックする
            close(){
                let self = this;
                nts.uk.ui.windows.close();
            }
            
            //エラー出力ボタンをクリックする
            errorExport(){
                let self = this;
                
            }
        }
        
        interface IErrorLog{
            employeeCode: string;
            employeeName: string;
            errorMessage: string;
        }
        
        class ErrorLog {
            employeeCode: string;
            employeeName: string;
            errorMessage: string
            constructor(param: IErrorLog) {
                let self = this;
                self.employeeCode = param.employeeCode;
                self.employeeName = param.employeeName;
                self.errorMessage = param.errorMessage;
            }
        }
        
        interface ICheckFuncDto {
            total: number;
            error: number;
            pass: number;
            outputErrorList: IErrorLog[];
        }
        
        class CheckFuncDto {
            total: number;
            error: number;
            pass: number;
            outputErrorList: IErrorLog[];
             constructor(param: ICheckFuncDto) {
                let self = this;
                self.total = param.total;
                self.error = param.error;
                self.pass = param.pass;
                self.outputErrorList = param.outputErrorList;
             }
        }
    }
}