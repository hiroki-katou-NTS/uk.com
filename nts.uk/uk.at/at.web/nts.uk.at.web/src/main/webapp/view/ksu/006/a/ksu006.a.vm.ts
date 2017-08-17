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
            fileId: KnockoutObservable<string>;
            
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
                self.fileId = ko.observable(null);
                
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
                nts.uk.ui.block.grayout();
                self.initNameId();
                $.when(self.loadAllExternalBudget()).done(() => {
                    if (self.externalBudgetList().length > 0) {
                        self.isEnableExecute(true);
                        self.selectedExtBudgetCode(self.externalBudgetList()[0].code);
                    }
                    $('#showDialogExternalBudget').focus();
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            public execute() {
                let self = this;
                let dfd = $.Deferred<any>();
                $('#comboExternalBudget').focus();
                self.uploadFile().done(function() {
                    self.validateFile();
                    dfd.resolve();
                }).fail(function() {
                    nts.uk.ui.dialog.alertError(res.message);
                });
            }
            
            public openDialogExternalBudget() {
                let self = this;
                nts.uk.ui.block.grayout();
                nts.uk.ui.windows.sub.modal('/view/kdl/024/a/index.xhtml').onClosed(() => {
                    nts.uk.ui.block.clear();
                    self.loadAllExternalBudget();
                });
            }
            
            public openDialogLog() {
                nts.uk.ui.block.grayout();
                nts.uk.ui.windows.sub.modal('/view/ksu/006/c/index.xhtml').onClosed(() => {
                        nts.uk.ui.block.clear();
                    });
            }
            
            private showDataPreview() {
                let self = this;
                // reset value
                self.dataPreview([]);
                self.firstRecord(null);
                self.remainData([]);
                
                self.uploadFile().done(function() {
                    service.findDataPreview(self.toJSObject()).done((res: DataPreviewModel) => {
                        self.isDataDailyUnit(res.isDailyUnit);
                        
                        self.dataPreview(res.data);
                        self.firstRecord(self.dataPreview()[0]);
                        self.remainData(self.dataPreview().slice(1, self.dataPreview().length));
                        
                        self.totalRecord(res.totalRecord);
                        self.enableDataPreview(true);
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
            
            private uploadFile(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                if (!self.fileName()) {
                    nts.uk.ui.dialog.alertError(nts.uk.resource.getMessage("Msg_157" ));
                    return dfd.promise();
                }
                $("#file-upload").ntsFileUpload({stereoType: self.extensionFileList()}).done(function(inforFileUpload) {
                    self.fileId(inforFileUpload[0].id);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
            
            private validateFile() {
                let self = this;
                let extractCondition: any = self.toJSObject();
                service.validateFile(extractCondition).done(function() {
                    nts.uk.ui.block.grayout();
                    nts.uk.ui.windows.setShared("ExtractCondition", extractCondition);
                    nts.uk.ui.windows.sub.modal('/view/ksu/006/b/index.xhtml').onClosed(() => {
                        nts.uk.ui.block.clear();
                    });
                }).fail(function(res: any) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
            }
            
            private toJSObject(): any {
                let self = this;
                return {
                        externalBudgetCode: self.selectedExtBudgetCode(),
                        fileId: self.fileId(),
                        fileName: self.fileName(),
                        encoding: self.selectedEncoding(),
                        startLine: self.startLine(),
                        isOverride: self.isOverride()
                };
            }
        }
    }
}