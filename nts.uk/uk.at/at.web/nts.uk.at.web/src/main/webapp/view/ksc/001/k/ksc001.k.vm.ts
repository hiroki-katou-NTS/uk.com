module nts.uk.at.view.ksc001.k {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        export class ScreenModel {
            executionId: KnockoutObservable<string>;
            targetRange: KnockoutObservable<string>;
            errorNumber: KnockoutObservable<string>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.executionId = ko.observable('');
                self.targetRange = ko.observable('');
                self.errorNumber = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: '', key: 'id', width: 100, hidden: true },
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'code', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'name', width: 200 },
                    { headerText: nts.uk.resource.getText("KSC001_58"), key: 'date', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_59"), key: 'errorContent', width: 300 }
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
                self.executionId(data.executionId);
                blockUI.invisible();
                self.getDataBindScreen(data).done(function() {
                    blockUI.clear();
                    dfd.resolve();
                });
                $("#fixed-table").ntsFixedTable({ height: 230 });
                return dfd.promise();
            }

            /**
             * Bind data to screen
             */
            private getDataBindScreen(parentData: any): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findAllError(parentData.executionId).done(function(data: Array<any>) {
                    self.targetRange(nts.uk.resource.getText("KSC001_46", [parentData.startDate, parentData.endDate]));
                    self.errorNumber(nts.uk.resource.getText("KSC001_47", [parentData.countError]));
                    if (data) {
                        data.forEach(function(item, index) {
                            self.items.push(new ItemModel(index, item.executionId, item.employeeCode, item.employeeName, item.date, item.errorContent));
                        });
                    }
                    //sort by employee code
                    self.items().sort(self.compare);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * define function to sort
             */
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
             * export error to csv
             */
            private exportError(): void {
                var self = this;
                let executionId = self.executionId();
                service.exportError(executionId).done(function() {
                }).fail(function(res:any){
                    if (res.businessException) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                    }
                });
            }

            /**
            * close dialog 
            */
            closeDialog(): void {
                let self = this;
                nts.uk.ui.windows.close();
            }

        }

        class ItemModel {
            id: string;
            code: string;
            name: string;
            date: string;
            errorContent: string;
            constructor(id: number, executionId: string, code: string, name: string, date: string, errorContent: string) {
                this.id = id.toString();
                this.code = code;
                this.name = name;
                this.date = date;
                this.errorContent = errorContent;
            }
        }
    }
}