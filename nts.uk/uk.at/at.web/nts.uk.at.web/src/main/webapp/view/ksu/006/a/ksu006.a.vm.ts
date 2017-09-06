module nts.uk.at.view.ksu006.a {
    export module viewmodel {
        
        import EnumerationModel = service.model.EnumerationModel;
        import ExternalBudgetModel = service.model.ExternalBudgetModel; 
        import ExternalBudgetValueModel = service.model.ExternalBudgetValueModel;
        import DataPreviewModel = service.model.DataPreviewModel;
        
        export class ScreenModel {
            
            isModeExecute: KnockoutObservable<boolean>;
            
            externalBudgetList: KnockoutObservableArray<ExternalBudgetModel>;
            selectedExtBudgetCode: KnockoutObservable<string>;
            
            fileName: KnockoutObservable<string>;
            extensionFileList: KnockoutObservableArray<string>;
            fileId: KnockoutObservable<string>;
            
            encodingList: KnockoutObservableArray<EnumerationModel>;
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
                
                self.isModeExecute = ko.observable(false);
                
                self.externalBudgetList = ko.observableArray([]);
                self.selectedExtBudgetCode = ko.observable('');
                
                self.fileName = ko.observable("");
                self.extensionFileList = ko.observableArray(['txt','csv', 'TXT', 'CSV']);
                self.fileId = ko.observable(null);
                
                self.encodingList = ko.observableArray([]);
                self.selectedEncoding = ko.observable(null);
                
                self.startLine = ko.observable(1);
                self.isOverride = ko.observable(true);
                
                self.totalRecord = ko.observable(0);
                self.totalRecordDisplay = ko.computed(() => {
                    return nts.uk.resource.getText("KSU006_123", [self.totalRecord()]);
                });
                
                self.enableDataPreview = ko.observable(true);
                self.isDataDailyUnit = ko.observable(true);
                self.dataPreview = ko.observableArray([]);
                self.firstRecord = ko.observable(null);
                self.remainData = ko.observableArray([]);
                // name id
                self.nameIdTitleList = ko.observableArray([]);
                self.nameIdValueList = ko.observableArray([]);
                
                // subscribe
                self.selectedExtBudgetCode.subscribe(function(value) {
                    if(!value) {
                        return;
                    }
                    self.enableDataPreview(false);
                    nts.uk.ui.block.grayout();
                    self.checkUnitAtr().done(() => {
                        // reset value
                        self.resetDataPreview();
                        
                        self.enableDataPreview(true);
                        nts.uk.ui.block.clear();
                    });
                });
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                nts.uk.ui.block.grayout();
                
                // initial name id of time zone unit
                self.initNameIdTimeZoneUnit();
                
                $.when(self.loadCharsetEnum(), self.loadAllExternalBudget()).done(() => {
                    if (self.encodingList().length > 0) {
                        self.selectedEncoding(self.encodingList()[0].value);
                    }
                    
                    if (self.externalBudgetList().length > 0) {
                        self.isModeExecute(true);
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
                
                // valid input
                if (!self.validInput()) {
                    nts.uk.ui.dialog.alert("Line start not valid.");
                    return;
                }
                
                $('#comboExternalBudget').focus();
                self.uploadFile().done(function() {
                    self.validateFile().done(() => {
                        self.openDialogProgress();
                    }).fail((isContinueProgress: boolean) => {
                        if (!isContinueProgress) {
                            return;
                        }
                        self.openDialogProgress();
                    });
                    dfd.resolve();
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
            }
            
            private openDialogProgress() {
                let self = this;
                nts.uk.ui.block.grayout();
                nts.uk.ui.windows.setShared("ExtractCondition", self.toJSObject());
                nts.uk.ui.windows.sub.modal('/view/ksu/006/b/index.xhtml').onClosed(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            public openDialogExternalBudget() {
                let self = this;
                nts.uk.ui.block.grayout();
                nts.uk.ui.windows.sub.modal('/view/kdl/024/a/index.xhtml').onClosed(() => {
                    nts.uk.ui.block.clear();
                    self.loadAllExternalBudget().done(() => {
                        let isEmptyExtBudgetSet: boolean = self.externalBudgetList().length <= 0;
                        self.isModeExecute(!isEmptyExtBudgetSet);
                        
                        // set selected external budget code
                        if (isEmptyExtBudgetSet) {
                            self.selectedExtBudgetCode(null);
                        }
                        
                        // update header data preview
                        self.checkUnitAtr().done(() => {
                            // reset value
                            self.resetDataPreview();

                            self.enableDataPreview(true);
                        });
                    });
                });
            }
            
            public openDialogLog() {
                nts.uk.ui.block.grayout();
                nts.uk.ui.windows.sub.modal('/view/ksu/006/c/index.xhtml').onClosed(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            private checkUnitAtr(): JQueryPromise<boolean> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                // list external budget is empty ==> show mode Daily.
                if (nts.uk.text.isNullOrEmpty(self.selectedExtBudgetCode())) {
                    self.isDataDailyUnit(true);
                    return dfd.promise();
                }
                service.checkUnitAtr(self.selectedExtBudgetCode()).done((state: boolean) => {
                    self.isDataDailyUnit(state);
                    dfd.resolve();
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            private showDataPreview() {
                let self = this;
                // reset value
                self.resetDataPreview();
                
                // valid input
                if (!self.validInput()) {
                    nts.uk.ui.dialog.alert("Line start not valid.");
                    return;
                }
                
                self.uploadFile().done(function() {
                    self.validateFile().done(() => {
                        self.loadDataPreview();
                    }).fail((isContinueProgress: boolean) => {
                        if (!isContinueProgress) {
                            return;
                        }
                        self.loadDataPreview();
                    });
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });  
            }
            
            private loadDataPreview() {
                let self = this;
                service.findDataPreview(self.toJSObject()).done((res: DataPreviewModel) => {
                    self.isDataDailyUnit(res.isDailyUnit);

                    self.dataPreview(res.data);
                    self.firstRecord(self.dataPreview()[0]);
                    self.remainData(self.dataPreview().slice(1, self.dataPreview().length));

                    self.totalRecord(res.totalRecord);
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
            }
            
            private resetDataPreview() {
                let self = this;
                self.totalRecord(0);
                self.dataPreview([]);
                self.firstRecord(null);
                self.remainData([]);
            }
            
            private initNameIdTimeZoneUnit() {
                let self = this;
                // find name id for case time zone unit
                for (let i=0; i<48; i++) {
                    self.nameIdTitleList.push(nts.uk.resource.getText("KSU006_" + (i + 23)));
                    self.nameIdValueList.push(nts.uk.resource.getText("KSU006_" + (i + 73)));
                }
            }
            
            private validInput(): boolean {
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                return true;
            }
            
            private loadAllExternalBudget(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                service.findExternalBudgetList().done(function(res: Array<ExternalBudgetModel>) {
                    self.externalBudgetList(res);
                    dfd.resolve();
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            private uploadFile(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                if (!self.fileName()) {
                    nts.uk.ui.dialog.alertError({messageId: "Msg_157"});
                    return dfd.promise();
                }
                $("#file-upload").ntsFileUpload({stereoType: 'ExternalBudgetFile'}).done(function(inforFileUpload) {
                    self.fileId(inforFileUpload[0].id);
                    dfd.resolve();
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            private validateFile(): JQueryPromise<boolean> {
                let self = this;
                let dfd = $.Deferred<boolean>();
                
                service.validateFile(self.toJSObject()).done(function() {
                    dfd.resolve();
                }).fail(function(res: any) {
                    // TODO: pending check charset of file.
//                    if (res.businessException && res.messageId == 'Msg_161') {
//                        nts.uk.ui.dialog.confirm({ messageId: 'Msg_161' }).ifYes(function() {
//                            dfd.reject(true);
//                        }).ifNo(function() {
//                            dfd.reject(false);
//                        });
//                    } else {
//                        self.showMessageError(res);
//                    }
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            private toJSObject(): any {
                let self = this;
                return {
                        externalBudgetCode: self.selectedExtBudgetCode(),
                        fileId: self.fileId(),
                        fileName: self.fileName(),
                        encoding: self.selectedEncoding(),
                        startLine: parseInt(self.startLine()),
                        isOverride: self.isOverride()
                };
            }
            
            private loadCharsetEnum(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findCharsetList().done((res: Array<EnumerationModel>) => {
                    self.encodingList(res);
                    dfd.resolve();
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            private showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                } else {
                    nts.uk.ui.dialog.alertError(res.message);
                }
            }
        }
    }
}