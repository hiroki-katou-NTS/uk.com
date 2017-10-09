module nts.uk.at.view.ksc001.k {
    export module viewmodel {
        import blockUI = nts.uk.ui.block;
        export class ScreenModel {
            targetRange: KnockoutObservable<string>;
            errorNumber: KnockoutObservable<string>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<string>;
            
            constructor() {
                var self = this;
                self.targetRange = ko.observable('');
                self.errorNumber = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: '', key: 'executionId', width: 100, hidden: true },
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'code', width: 100 },
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
                    if (data) {
                        self.targetRange(nts.uk.resource.getText("KSC001_46", [parentData.startDate, parentData.endDate]));
                        self.errorNumber(nts.uk.resource.getText("KSC001_47", [data.length]));
                        data.forEach(function(item, index) {
                            self.items.push(new ItemModel(item.executionId, item.employeeCode, item.employeeName, item.date, item.errorContent));
                        });
                    }
                });
                dfd.resolve();
                return dfd.promise();
            }

            /**
             * export error to csv
             */
            private exportError(): void {
                var self = this;
                let executionId = self.currentCode();
                service.exportError(executionId).done(function() {

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
            executionId: string;
            code: string;
            name: string;
            date: string;
            errorContent: string;
            constructor(executionId: string, code: string, name: string, date: string, errorContent: string) {
                this.executionId = executionId;
                this.code = code;
                this.name = name;
                this.date = date;
                this.errorContent = errorContent;
            }
        }
    }
}