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
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'name', width: 150 },
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
                service.findAllCreator(parentData.executionId).done(function(data: Array<any>) {
                    self.targetRange(nts.uk.resource.getText("KSC001_46", [parentData.startDate, parentData.endDate]));
                    self.executionNumber(nts.uk.resource.getText("KSC001_47", [data.length]));
                    data.forEach(function(item, index) {
                        self.items.push(new ItemModel(item.employeeCode, item.employeeName, item.executionStatus));
                    });
                    dfd.resolve();
                });
                return dfd.promise();
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