module nts.uk.at.view.ksu006.a {
    export module viewmodel {
        
        import ExternalBudgetModel = service.model.ExternalBudgetModel; 
        import ExternalBudgetValueModel = service.model.ExternalBudgetValueModel;
        
        export class ScreenModel {
            
            externalBudgetList: KnockoutObservableArray<ExternalBudgetModel>;
            selectedExtBudgetCode: KnockoutObservable<string>;
            
            fileName: KnockoutObservable<string>;
            extensionFileList: KnockoutObservableArray<string>;
            
            encodingList: KnockoutObservableArray<any>;
            selectedEncoding: KnockoutObservable<string>;
            
            startLine: KnockoutObservable<number>;
            isOverride: KnockoutObservable<boolean>;
            
            totalRecord: KnockoutObservable<string>;
            
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
                
                self.externalBudgetList = ko.observableArray([]);
                self.selectedExtBudgetCode = ko.observable('');
                
                self.fileName = ko.observable("");
                self.extensionFileList = ko.observableArray([".txt",'.csv']);
                
                self.encodingList = ko.observableArray([{code: 'Shift JIS', name: 'Shift JIS'}]);
                self.selectedEncoding = ko.observable("Shift JIS");
                
                self.startLine = ko.observable(null);
                self.isOverride = ko.observable(false);
                
                self.totalRecord = ko.observable('');
                
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
                        self.selectedExtBudgetCode(self.externalBudgetList()[0].code);
                    }
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            public openDialog() {
                nts.uk.ui.windows.sub.modal('/view/kdl/024/a/index.xhtml', { title: '外部予算実績の設定'});
            }
            
            private showDataPreview() {
                let self = this;
                // reset value
                self.dataPreview([]);
                self.firstRecord(null);
                self.remainData([]);
                
                self.enableDataPreview(true);
                // fake data
                let listValue: Array<any> = [];
                for (let i=1; i<49; i++) {
                    listValue.push(i * 1000);
                }
                self.dataPreview.push(new ExternalBudgetValueModel('1000000001', '20170721', listValue));
                self.dataPreview.push(new ExternalBudgetValueModel('1000000002', '20170721', listValue));
                self.dataPreview.push(new ExternalBudgetValueModel('1000000003', '20170721', listValue));
                
                self.firstRecord(self.dataPreview()[0]);
                self.remainData(self.dataPreview().slice(1, self.dataPreview().length));
                
                self.totalRecord(nts.uk.resource.getText("KSU006_123", [self.dataPreview().length]));
                $('#data-preview').css('visibility', 'visible');
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
                });
                return dfd.promise();
            }
        }
    }
}