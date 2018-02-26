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
        currentCode: KnockoutObservable<model.ImExErrorLog>;
        imExExecuteResultLog: KnockoutObservable<model.ImExExecuteResultLog>;
        imExErrorLog: KnockoutObservableArray<model.ImExErrorLog>
        columns: KnockoutObservableArray<NtsGridListColumn>;
        constructor() {
            let self = this;
            self.imExExecuteResultLog =  ko.observable(new model.ImExExecuteResultLog('001', 'A社人事管理情報', '2018/3/15 14:52:00', '    100件', '    92件', '     8件'));
            self.imExErrorLog = ko.observableArray([
                new model.ImExErrorLog(3, '' ,'' , '','' ),
                new model.ImExErrorLog(5, '' ,'' , '','' ),
                new model.ImExErrorLog(12, '' ,'' , '','' ),
                new model.ImExErrorLog(16, '' ,'' , '','' ),
                new model.ImExErrorLog(35, '' ,'' , '','' ),
                new model.ImExErrorLog(37, '' ,'' , '','' ),
                new model.ImExErrorLog(56, '' ,'' , '','' ),
                new model.ImExErrorLog(83, '' ,'' , '','' ),
                
                ]);
            self.currentCode = ko.observable(new model.ImExErrorLog(3, '' ,'' , '','' ));
            self.columns = ko.observableArray([
                { headerText: 'レコード番号', key: 'recordNumber', width: 100 },
                { headerText: 'CSV項目名', key: 'csvFieldName', width: 150 }, 
                { headerText: '項目名', key: 'fieldName', width: 150 }, 
                { headerText: '値', key: 'fieldValue', width: 150},
                { headerText: 'エラーメッセージ', key: 'errorDesciption', width: 150} 
            ]); 
        }
        
        errorExport(){
            confirm({ messageId: "Msg_912" }).ifYes(() => {
                        return;
                    }).ifNo(() => {
                        return;
                    })
        }
        close(){
             nts.uk.ui.windows.close();
        }
     }
}