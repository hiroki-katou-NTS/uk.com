module nts.uk.at.view.kdw001.e.viewmodel {
     import share001 = nts.uk.at.view.kdw001.share.share001;
    export class ScreenModel {
        //combo box 
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
       
        //gridlist
        items: KnockoutObservableArray<Gridlist>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        //
        empCalAndSumExecLogID: KnockoutObservable<string>;
        /** 実行開始日時  Start date and time of execution*/
        executionDate: KnockoutObservable<string>;
        
        
        taskId: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.executionDate = ko.observable('');
            self.itemList = ko.observableArray([
                new ItemModel('1'),
                new ItemModel('2'),
                new ItemModel('3')
            ]);

            self.selectedCode = ko.observable('1');
            self.items = ko.observableArray([]);

            for (let i = 1; i < 100; i++) {
                this.items.push(new Gridlist('00' + i, '基本給', "description " + i, i % 3 === 0, "2010/1/1"));
            }
            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KDW001_33'), key: 'empCD', width: 100 },
                { headerText: nts.uk.resource.getText('KDW001_35'), key: 'code', width: 150 },
                { headerText: nts.uk.resource.getText('KDW001_36'), key: 'disposalDay', width: 150 },
                { headerText: nts.uk.resource.getText('KDW001_37'), key: 'errContents', width: 150 },
            ]);
            this.currentCode = ko.observable();

            self.empCalAndSumExecLogID = ko.observable('1001');
            self.taskId = ko.observable('');
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let parameter = nts.uk.ui.windows.getShared("KDWL001D");
            let dfd = $.Deferred();
           
            service.getImplementationResult(self.empCalAndSumExecLogID()).done(function(data) {
                console.log(data);
                self.executionDate(data.empCalAndSumExeLogDto.executionDate);
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
    class ItemModel {
        code: string;
       

        constructor(code: string) {
            this.code = code;
            
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
            this.errContents = errContents ;
          
        }

    }


}
