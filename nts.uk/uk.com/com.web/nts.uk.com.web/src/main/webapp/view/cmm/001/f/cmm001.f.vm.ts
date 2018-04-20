module nts.uk.com.view.cmm001.f {

    export module viewmodel {

        export class ScreenModel {

            errorLogs: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            executionStartDate: string;
            executionState: KnockoutObservable<string>;
            executionError: KnockoutObservable<string>;
            periodInfo: string;
            taskId: KnockoutObservable<string>;
            totalRecord: KnockoutObservable<number>;
            numberSuccess: KnockoutObservable<number>;
            numberFail: KnockoutObservable<number>;
            //            dataError: KnockoutObservableArray<ErrorContentDto>;
            //            inputData: ScheduleBatchCorrectSettingSave;
            isError: KnockoutObservable<boolean>;
            isFinish: KnockoutObservable<boolean>;
            readIndex: KnockoutObservableArray<number>;

            constructor() {
                let self = this;

                self.taskId = ko.observable(null);
            }
            
            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                 var dfd = $.Deferred();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * execution
             */
            public execution(): void {
                var self = this;
                // find task id
                service.executionMasterCopyData().done(function(res: any) {
                    self.taskId(res.taskInfor.id);
                    // update state
                    self.updateState();
                }).fail(function(res: any) {
                    console.log(res);
                });
            }
            
            
            private updateState(): void {
                
            }

            /**
            * function cancel execution
            */
            private stopExecution(): void {
                let self = this;

                if (nts.uk.text.isNullOrEmpty(self.taskId())) {
                    return;
                }
                // interrupt process import then close dialog
                nts.uk.request.asyncTask.requestToCancel(self.taskId());

                service.pause();
            }

        }

    }

}