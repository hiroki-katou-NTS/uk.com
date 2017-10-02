module nts.uk.at.view.ksc001.i {
    export module viewmodel {
        export class ScreenModel {
            targetRange: KnockoutObservable<string>;
            executionNumber: KnockoutObservable<string>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.targetRange = ko.observable(nts.uk.resource.getText("KSC001_46", ['2016/11/11', '2016/11/11']));
                self.executionNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [33]));
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'name', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_86"), key: 'status', width: 150 }
                ]);
                self.items = ko.observableArray([]);
                for(var i=0;i<33;i++)
                {
                    self.items.push(new ItemModel("A0000000"+i,"日通システム"+i,"未作成"));    
                }
                self.currentCode = ko.observable('');
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                var data: any = nts.uk.ui.windows.getShared('dataFromDetailDialog');
                $("#fixed-table").ntsFixedTable({ height: 230 });
                dfd.resolve();
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