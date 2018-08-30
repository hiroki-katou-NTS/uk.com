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
            executionId: string;
            execItemCd: string;
            isDaily: boolean;
            execution : KnockoutObservable<modelkbt002b.ExecutionItem>;
            dateString: string;
            dataSource : KnockoutObservableArray<any>;
            
            sharedObj = {};
            
            
            constructor() {
                let self = this;
                self.executionId = "472006f6-eb49-4caa-b607-1b837fc56df5";
                self.execItemCd = "00"
                self.isDaily= true;
                self.execution = ko.observable(null);
                self.dateString = "2018/08/28";
                self.dataSource  = ko.observableArray([]);
            }
            
            // Start page
            start() : JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
//                let dfdGetDataByExecution = self.getDataByExecution(self.executionId());
////                self.getDataByExecution(self.executionId()).done(function(){
////                    dfd.resolve();
////                });
//                let dfdGetLogHis = self.getLogHistory(self.execItemCd(),self.executionId());
//                $.when(dfdGetDataByExecution,dfdGetLogHis).done((dfdGetDataByExecutionData,dfdGetLogHisData) => {
//                    for(let i = 0;i<self.dataSource().length;i++){
//                        self.dataSource()[i].date =self.dateString();
//                    }
//                    console.log(self.dataSource());
                    dfd.resolve();
//                });
                return dfd.promise();
            }  
            
            openDialog(){
                let self = this;
                self.sharedObj = {
                    executionId : self.executionId,
                    execItemCd : self.execItemCd,
                    isDaily : self.isDaily,
                    nameObj : self.isDaily ==true ? "承認ルート更新（日次）":"承認ルート更新（月次）"  
                }; 
                nts.uk.ui.windows.setShared('inputDialogI', { sharedObj:self.sharedObj});
                nts.uk.ui.windows.sub.modal("/view/kbt/002/i/index.xhtml").onClosed(function() {
                });
            }
        }
    }
}
