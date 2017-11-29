module nts.uk.at.view.kdw001.e.viewmodel {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.at.view.kdw001.share.model;
    
    export class ScreenModel {
        // Combo box 
        executionContents: KnockoutObservableArray<any>;
        selectedExeContent: KnockoutObservable<string>;

        // GridList
        errorMessageInfo: KnockoutObservableArray<Gridlist>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        
        /** 実行開始日時  Start date and time of execution*/
        executionDate: KnockoutObservable<string>;
        disposalDay: KnockoutObservable<string>;

        constructor() {
            var self = this;

            self.executionContents = ko.observableArray([]);
            self.selectedExeContent = ko.observable('1');
            
            self.errorMessageInfo = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: getText('KDW001_33'), key: 'empCD', width: 110 },
                { headerText: getText('KDW001_35'), key: 'code', width: 150 },
                { headerText: getText('KDW001_36'), key: 'disposalDay', width: 150 },
                { headerText: getText('KDW001_37'), key: 'errContents', width: 290 },
            ]);
            self.currentCode = ko.observable();

            self.executionDate = ko.observable('');
            self.disposalDay = ko.observable('');

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            var params: shareModel.executionProcessingCommand = nts.uk.ui.windows.getShared("KDWL001E");
            
            service.insertData(params).done((data: shareModel.executionResult) => {
                console.log(data);
                //self.executionDate(data.periodStartDate);
                //self.executionContents(data.enumComboBox);
                // Start checking proceess
                //self.executeTask(data);
                dfd.resolve();
            });
            
            return dfd.promise();
        }

        cancelTask() {
            
            nts.uk.ui.windows.close();
        }
        
        closeDialog() {
            nts.uk.ui.windows.close();
        }
        
        private executeTask(data: shareModel.executionResult) {
            var self = this;            
            nts.uk.deferred.repeat(conf => conf.task(() => {
                    return service.executeTask(data).done((info) => {
                        console.log(info);
                    });
                })
                .while(info => !info.isComplete)
                .pause(1000)
            );
        }

    }

    class Gridlist {
        empCD: string;
        code: string;
        disposalDay: string;
        errContents: string;


        constructor(empCD: string, code: string, disposalDay: string, errContents: string) {
            this.empCD = empCD;
            this.code = code;
            this.disposalDay = disposalDay;
            this.errContents = errContents;

        }

    }


}
