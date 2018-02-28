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
        currentCode: KnockoutObservable<model.IImExErrorLog>;
        imExExecuteResultLog: KnockoutObservable<model.IImExExecuteResultLogR>;
        imExErrorLog: KnockoutObservableArray<model.IImExErrorLog>
        columns: KnockoutObservableArray<NtsGridListColumn>;
        
        nameSetting: KnockoutObservable<string>;
        imexProcessID: string;
        
        constructor() {
            let self = this;
            self.imExExecuteResultLog =  ko.observable(null);
            self.imExErrorLog = ko.observableArray([]);
            self.currentCode = ko.observable(null);
            self.columns = ko.observableArray([
                { headerText: 'レコード番号', key: 'recordNumber', width: 100 },
                { headerText: 'CSV項目名', key: 'csvErrorItemName', width: 150 }, 
                { headerText: '項目名', key: 'itemName', width: 150 }, 
                { headerText: '値', key: 'csvAcceptedValue', width: 150},
                { headerText: 'エラーメッセージ', key: 'errorContents', width: 150} 
            ]); 
            
            //let imexProcessID = getShared ("CMD001-R");
            // param receive from Q, S
            self.nameSetting = ko.observable('  A社人事管理情報');
        }
        
         //開始
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            nts.uk.ui.errors.clearAll();
            service.getLogResults(self.imexProcessID).done(itemList: Array<IImExErrorLog>) => {
               
                dfd.resolve();
            });
            
            service.getErrorLogs(self.imexProcessID).done((itemList: Array<IImExErrorLog>) => {
               
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
}