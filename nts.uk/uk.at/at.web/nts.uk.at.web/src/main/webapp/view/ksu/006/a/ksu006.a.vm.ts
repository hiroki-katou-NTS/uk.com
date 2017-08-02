module nts.uk.at.view.ksu006.a {
    export module viewmodel {
        
        import ExternalBudgetModel = service.model.ExternalBudgetModel; 
        import ExternalBudgetValueModel = service.model.ExternalBudgetValueModel;
        import DataPreviewModel = service.model.DataPreviewModel;
        
        export class ScreenModel {
            
            isEnableExecute: KnockoutObservable<boolean>;
            
            externalBudgetList: KnockoutObservableArray<ExternalBudgetModel>;
            selectedExtBudgetCode: KnockoutObservable<string>;
            
            fileName: KnockoutObservable<string>;
            extensionFileList: KnockoutObservableArray<string>;
            
            encodingList: KnockoutObservableArray<any>;
            selectedEncoding: KnockoutObservable<number>;
            
            startLine: KnockoutObservable<number>;
            isOverride: KnockoutObservable<boolean>;
            
            totalRecord: KnockoutObservable<number>;
            totalRecordDisplay: KnockoutObservable<string>;
            
            isDataDailyUnit: KnockoutObservable<boolean>;
            enableDataPreview: KnockoutObservable<boolean>;
            dataPreview: KnockoutObservableArray<ExternalBudgetValueModel>;
            firstRecord: KnockoutObservable<ExternalBudgetValueModel>;
            remainData: KnockoutObservableArray<ExternalBudgetValueModel>;
            
            //  name id
            nameIdTitleList: KnockoutObservableArray<string>;
            nameIdValueList: KnockoutObservableArray<string>;
            
            constructor() {
                let self = this;
                
                self.isEnableExecute = ko.observable(false);
                
                self.externalBudgetList = ko.observableArray([]);
                self.selectedExtBudgetCode = ko.observable('');
                
                self.fileName = ko.observable("");
                self.extensionFileList = ko.observableArray([".txt",'.csv']);
                
                self.encodingList = ko.observableArray([{code: 1, name: 'Shift JIS'}]);
                self.selectedEncoding = ko.observable(1);
                
                self.startLine = ko.observable(1);
                self.isOverride = ko.observable(true);
                
                self.totalRecord = ko.observable(0);
                self.totalRecordDisplay = ko.computed(() => {
                    return nts.uk.resource.getText("KSU006_123", [self.totalRecord()]);
                });
                
                self.enableDataPreview = ko.observable(false);;
                self.isDataDailyUnit = ko.observable(false);
                self.dataPreview = ko.observableArray([]);
                self.firstRecord = ko.observable(null);
                self.remainData = ko.observableArray([]);
                // name id
                self.nameIdTitleList = ko.observableArray([]);
                self.nameIdValueList = ko.observableArray([]);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                self.initNameId();
                $.when(self.loadAllExternalBudget()).done(() => {
                    if (self.externalBudgetList().length > 0) {
                        self.isEnableExecute(true);
                        self.selectedExtBudgetCode(self.externalBudgetList()[0].code);
                    }
                    $('#showDialogExternalBudget').focus();
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            public execute() {
                let self = this;
                $('#comboExternalBudget').focus();
                nts.uk.ui.windows.setShared("totalRecord", 10);//self.totalRecord());
                nts.uk.ui.windows.sub.modal('/view/ksu/006/b/index.xhtml', { title: '外部予算実績データ受入実行', dialogClass: 'no-close' });
            }
            
            public openDialogExternBudget() {
                nts.uk.ui.windows.sub.modal('/view/kdl/024/a/index.xhtml', { title: '外部予算実績の設定'});
            }
            
            public openDialogLog() {
                nts.uk.ui.windows.sub.modal('/view/ksu/006/c/index.xhtml', { title: '外部予算実績データ受入実行ログ', dialogClass: 'no-close' });
            }
            
            private showDataPreview() {
                let self = this;
                // reset value
                self.dataPreview([]);
                self.firstRecord(null);
                self.remainData([]);
                
                if (!self.fileName()) {
                    nts.uk.ui.dialog.alertError(nts.uk.resource.getMessage("Msg_158" ));
                    return;
                }
                
                $("#file-upload").ntsFileUpload({stereoType:"any"}).done(function(inforFileUpload) {
                    let extractCondition: any = {
                        externalBudgetCode: null,
                        fileId: inforFileUpload[0].id,
                        encoding: self.selectedEncoding(),
                        startLine: self.startLine(),
                        isOverride: null
                    };
                    service.findDataPreview(extractCondition).done((res: DataPreviewModel) => {
                        self.enableDataPreview(true);
                         // fake data
                        let listValue: Array<any> = [];
                        for (let i=1; i<49; i++) {
                            listValue.push(i * 1000000);
                        }
                        self.dataPreview.push(new ExternalBudgetValueModel('1000000001', '20170721', listValue));
                        self.dataPreview.push(new ExternalBudgetValueModel('1000000002', '20170721', listValue));
                        self.dataPreview.push(new ExternalBudgetValueModel('1000000003', '20170721', listValue));
                        
                        self.firstRecord(self.dataPreview()[0]);
                        self.remainData(self.dataPreview().slice(1, self.dataPreview().length));
                        
                        self.totalRecord(res.totalRecord);
    //                    self.totalRecord(nts.uk.resource.getText("KSU006_123", [res.totalRecord]));
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });  
            }
            
            private initNameId() {
                let self = this;
                if (self.isDataDailyUnit()) {
                    return;
                }
                // find name id for case time zone unit
                for (let i=0; i<48; i++) {
                    self.nameIdTitleList.push(nts.uk.resource.getText("KSU006_" + (i + 23)));
                    self.nameIdValueList.push(nts.uk.resource.getText("KSU006_" + (i + 73)));
                }
            }
            
            private loadAllExternalBudget(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                service.findExternalBudgetList().done(function(res: Array<ExternalBudgetModel>) {
                    self.externalBudgetList(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
        }
    }
}