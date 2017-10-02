module nts.uk.at.view.ksc001.h {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            exeDetailModel: KnockoutObservable<ExecutionDetailModel>;
            
            constructor() {
                var self = this;
                self.exeDetailModel = ko.observable(new ExecutionDetailModel());
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                $("#fixed-table").ntsFixedTable();
                dfd.resolve();
                return dfd.promise();
            }
            /**
             * Load excution detail data
             */
            private loadDetailData(executionId: string) {
                service.findExecutionDetail(executionId).done(function(data: any){
                    //TODO bind data to screen
                });
            }
            /**
             * 
             */
            openDialogI(): void {
                let self = this;
                //TODO
                var data: any = {
                    executionId: '',
                    strD: '',
                    endD: ''    
                }
                blockUI.invisible();
                nts.uk.ui.windows.setShared('dataFromDetailDialog', data);
                nts.uk.ui.windows.sub.modal("/view/ksc/001/i/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }
            /**
             * 
             */
            openDialogK(): void {
                let self = this;
                blockUI.invisible();
                // the default value of categorySet = undefined
                //nts.uk.ui.windows.setShared('', );
                nts.uk.ui.windows.sub.modal("/view/ksc/001/k/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }
             /**
             * close dialog 
             */
            closeDialog(): void {
                let self = this;
                nts.uk.ui.windows.close();   
            }

        }
        
        export class ExecutionDetailModel {
            a:KnockoutObservable<string>;
            b:KnockoutObservable<string>;
            targetRange: KnockoutObservable<string>;
            completionStatus: KnockoutObservable<string>;
            executionId: KnockoutObservable<string>;
            executionContent: KnockoutObservable<string>;
            executionDateTime: KnockoutObservable<string>;
            executionEmployeeId: KnockoutObservable<string>;
            period: KnockoutObservable<Period>;
            executionNumber: KnockoutObservable<string>;
            errorNumber: KnockoutObservable<string>;
            constructor(){
                var self = this;
                self.a = ko.observable('2016/11/11');
                self.b = ko.observable('2016/11/11');
                self.targetRange = ko.observable(nts.uk.resource.getText("KSC001_46", [self.a(), self.b()]));
                self.completionStatus = ko.observable('1');
                self.executionId = ko.observable('1');
                self.executionContent = ko.observable('就就業承認就業承就業承認就業承認就');
                self.executionDateTime = ko.observable('1');
                self.executionEmployeeId = ko.observable('1');
                self.period = ko.observable(new Period());
                self.executionNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [33]));
                self.errorNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [2]));
            }
        }
        export class Period{
            startDateTime: string;
            endDateTime: string;
            constructor() {
                this.startDateTime = "2016/11/30 12:19:10";
                this.endDateTime = "2016/11/30 12:19:10";
            }
        }
    }
}