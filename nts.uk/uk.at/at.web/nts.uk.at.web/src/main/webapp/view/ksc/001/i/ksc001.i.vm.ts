module nts.uk.at.view.ksc001.i {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        export class ScreenModel {
            targetRange: KnockoutObservable<string>;
            executionNumber: KnockoutObservable<string>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.targetRange = ko.observable('');
                self.executionNumber = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'code', width: 120 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'name', width: 180 },
                    { headerText: nts.uk.resource.getText("KSC001_86"), key: 'status', width: 150 }
                ]);
                self.items = ko.observableArray([]);
                self.currentCode = ko.observable('');
            }

            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let data: any = nts.uk.ui.windows.getShared('dataFromDetailDialog');
                blockUI.invisible();
                self.getDataBindScreen(data).done(function() {
                    blockUI.clear();
                    dfd.resolve();
                });
                $("#fixed-table").ntsFixedTable({ height: 230 });
                return dfd.promise();
            }

            /**
             * function to get data and bind to screen
             */
            private getDataBindScreen(parentData: any): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.items([]);
                //get all creater
                service.findAllCreator(parentData.executionId).done(function(data: Array<any>) {
                    self.targetRange(nts.uk.resource.getText("KSC001_46", [parentData.startDate, parentData.endDate]));
                    self.executionNumber(nts.uk.resource.getText("KSC001_47", [data.length]));
                    //push data to list
                    if (data && data.length > 0) {
                        data.forEach(function(item, index) {
                            self.items.push(new ItemModel(item.employeeCode, item.employeeName, item.executionStatus));
                        });
                    }
                    //sort by employ code
                    self.items().sort(self.compare);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            //define sort function
            private compare(a: any, b: any) {
                let genreA: string = a.code.toUpperCase();
                let genreB: string = b.code.toUpperCase();

                let comparison = 0;
                if (genreA > genreB) {
                    comparison = 1;
                } else if (genreA < genreB) {
                    comparison = -1;
                }
                return comparison;
            }

            /**
            * close dialog 
            */
            closeDialog(): void {
                let self = this;
                nts.uk.ui.windows.close();
            }

        }
        
        //item data
        class ItemModel {
            code: string;
            name: string;
            status: string;
            constructor(code: string, name: string, status: string) {
                this.code = code;
                this.name = name;
                this.status = status;
            }
        }
    }
}