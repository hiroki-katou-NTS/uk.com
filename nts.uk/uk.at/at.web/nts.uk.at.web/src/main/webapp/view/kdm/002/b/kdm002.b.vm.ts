module nts.uk.at.view.kdm002.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {

        export class ScreenModel {
            // param from resources
            CODE_EMP: string = getText("KDM002_25");
            NAME_EMP: string = getText("KDM002_26");
            ERROR_MESS: string = getText("KDM002_27")
            
            // table result
            timeStart: KnockoutObservable<string> = ko.observable('2018/01/01 13:25:16');
            timeOver: KnockoutObservable<string> = ko.observable('00:03:07.123');
            status: KnockoutObservable<string> = ko.observable('処理中');
            result: KnockoutObservable<string> = ko.observable('2 / 25人');
            
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
                // find task id
                service.execution().done(function(res: any) {
                    self.taskId(res.taskInfor.id);
                    // update state
                    self.updateState();
                }).fail(function(res: any) {
                    console.log(res);
                });
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
    }
}