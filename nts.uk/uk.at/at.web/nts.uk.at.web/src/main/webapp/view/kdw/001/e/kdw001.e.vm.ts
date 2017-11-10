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
            let parameter = nts.uk.ui.windows.getShared("KDWL001D");
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


        btnAsyncTask() {
            var self = this;
            service.asyncTask().done((info) => {
                self.taskId(info.id);
                nts.uk.deferred.repeat(function(conf) {
                    return conf
                        .task(function() {
                            return nts.uk.request.asyncTask.getInfo(this.taskId).done(function(res) {
                                console.log(res.status);
                            });
                        })
                        .while(function(info) { return info.pending || info.running; })
                        .pause(1000);
                });
            });

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
