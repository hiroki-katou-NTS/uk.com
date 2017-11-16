import shareModel = nts.uk.at.view.kdw001.share.model;

module nts.uk.at.view.kdw001.e.viewmodel {

    export class ScreenModel {
        //combo box 
        itemList: KnockoutObservableArray<ComboBox>;
        selectedCode: KnockoutObservable<string>;

        //gridlist
        items: KnockoutObservableArray<Gridlist>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        //
        empCalAndSumExecLogID: KnockoutObservable<string>;
        /** 実行開始日時  Start date and time of execution*/
        executionDate: KnockoutObservable<string>;
        //Date 
        disposalDay: KnockoutObservable<string>;

        taskId: KnockoutObservable<string>;
        constructor() {
            var self = this;

            self.executionDate = ko.observable('');
            self.itemList = ko.observableArray([]);

            self.selectedCode = ko.observable('1');
            self.items = ko.observableArray([]);

            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KDW001_33'), key: 'empCD', width: 100 },
                { headerText: nts.uk.resource.getText('KDW001_35'), key: 'code', width: 150 },
                { headerText: nts.uk.resource.getText('KDW001_36'), key: 'disposalDay', width: 150 },
                { headerText: nts.uk.resource.getText('KDW001_37'), key: 'errContents', width: 150 },
            ]);
            this.currentCode = ko.observable();

            self.empCalAndSumExecLogID = ko.observable('emp001');
            self.taskId = ko.observable('');
            self.disposalDay = ko.observable('');

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            service.getImplementationResult(self.empCalAndSumExecLogID()).done(function(data) {
                console.log(data);
                self.executionDate(data.empCalAndSumExeLogDto.executionDate);
                self.itemList(data.enumComboBox);
                self.disposalDay(data.errMessageInfoDto.disposalDay);
                dfd.resolve(data);
            });

            return dfd.promise();
        }

        executeTask() {
            var self = this;
            let params: shareModel.executionProcessingCommand = nts.uk.ui.windows.getShared("KDWL001E");

            service.executeTask(params).done((info) => {
                console.log(info);
                self.taskId(info.id);
                nts.uk.deferred.repeat((conf) => {
                    return conf.task(() => {
                        return nts.uk.request.asyncTask.getInfo(self.taskId()).done((res) => {
                            console.log(res);
                        });
                    })
                    .while((info) => {
                        return info.pending || info.running;
                    }).pause(1000);
                });
            });
        }

        cancelTask() {
            
        }
        
        closeDialog() {
            
        }

    }
    class ComboBox {
        value: number;
        fieldName: string;
        localizedName: string;

        constructor(value: number, fieldName: string, localizedName: string) {
            this.value = value;
            this.fieldName = fieldName;
            this.localizedName = localizedName;
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
