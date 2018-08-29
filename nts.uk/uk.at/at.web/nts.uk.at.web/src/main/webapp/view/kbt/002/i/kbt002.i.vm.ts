module nts.uk.at.view.kbt002.i {
    export module viewmodel {
        import modal = nts.uk.ui.windows.sub.modal;
        import setShared = nts.uk.ui.windows.setShared;
        import getShared = nts.uk.ui.windows.getShared;
        import windows = nts.uk.ui.windows;
        import block = nts.uk.ui.block;
        import dialog = nts.uk.ui.dialog;
        import getText = nts.uk.resource.getText;
        import modelkbt002b = nts.uk.at.view.kbt002.b.viewmodel;
        export class ScreenModel {
            executionId: KnockoutObservable<string>;
            execItemCd: KnockoutObservable<string>;
            isDaily: KnockoutObservable<boolean>;
            execution : KnockoutObservable<modelkbt002b.ExecutionItem>;
            dateString: KnockoutObservable<string>;
            dataSource : KnockoutObservableArray<any>;
            nameObj : KnockoutObservable<string>;
            numberPerson : KnockoutObservable<number>;
            
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode : KnockoutObservable<any>;
            
            constructor() {
                let self = this;
                let sharedData = nts.uk.ui.windows.getShared('inputDialogI').sharedObj;
                self.executionId = ko.observable(sharedData.executionId);
                self.execItemCd = ko.observable(sharedData.execItemCd)
                self.nameObj = ko.observable(sharedData.nameObj);
                self.isDaily= ko.observable(sharedData.isDaily);
                self.execution = ko.observable(null);
                self.dateString = ko.observable("2018/08/28");
                self.dataSource  = ko.observableArray([]);
                self.numberPerson = ko.observable(0);
                self.currentCode  = ko.observable(null);
                
                this.columns = ko.observableArray([
                    { headerText: 'id', key: 'employeeId', width: 100, hidden: true },
                    { headerText: getText('KBT002_184'), key: 'employeeCode', width: 100 },
                    { headerText: getText('KBT002_185'), key: 'pname', width: 130 },
                    { headerText: getText('KBT002_186'), key: 'date', width: 170 }, 
                    { headerText: getText('KBT002_187'), key: 'errorMessage', width: 300 }
                ]);
            }
            
            // Start page
            start() : JQueryPromise<any> { 
                let self = this;
                var dfd = $.Deferred();
                let dfdGetDataByExecution = self.getDataByExecution(self.executionId());
//                self.getDataByExecution(self.executionId()).done(function(){
//                    dfd.resolve();
//                });
                let dfdGetLogHis = self.getLogHistory(self.execItemCd(),self.executionId());
                $.when(dfdGetDataByExecution,dfdGetLogHis).done((dfdGetDataByExecutionData,dfdGetLogHisData) => {
                    for(let i = 0;i<self.dataSource().length;i++){
                        self.dataSource()[i].date =self.dateString();
                    }
                    console.log(self.dataSource());
                    dfd.resolve();
                });
                return dfd.promise();
            }  
            
            getDataByExecution(executionId : string ){ 
                let self = this;
                let dfd = $.Deferred();
                if(self.isDaily()){
                    service.findAppDataInfoDailyByExeID(executionId).done(function(data){
                        self.dataSource(data);
                        self.numberPerson(data.length);
                        dfd.resolve();
                    }).fail(function(res: any) { 
                        dfd.reject();
                        nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                    });
                }else{
                    service.findAppDataInfoMonthlyByExeID(executionId).done(function(data){
                        self.dataSource(data);  
                        self.numberPerson(data.length);
                        dfd.resolve();
                    }).fail(function(res: any) {
                        dfd.reject();
                        nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                    });
                }
                return dfd.promise();  
            }
            
            getLogHistory(execItemCd :string, execId :string){
                let self = this;
                let dfd = $.Deferred();
                service.getLogHistory(execItemCd,execId).done(function(data){
                    self.dateString(data.lastExecDateTime);
                    dfd.resolve();
                }).fail(function(res: any) { 
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
               
                return dfd.promise();
            }
            
            // 閉じる button
            closeDialog() {
                windows.close();
            }
        }
    }
}
