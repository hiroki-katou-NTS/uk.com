module nts.uk.at.view.ksc001.k {
    export module viewmodel {
        export class ScreenModel {
            targetRange: KnockoutObservable<string>;
            errorNumber: KnockoutObservable<string>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.targetRange = ko.observable(nts.uk.resource.getText("KSC001_46", ['2016/11/11', '2016/11/11']));
                self.errorNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [2]));
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'name', width: 200 },
                    { headerText: nts.uk.resource.getText("KSC001_58"), key: 'date', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_59"), key: 'errorContent', width: 300 }
                ]);
                self.items = ko.observableArray([]);
                for (var i = 0; i < 2; i++) {
                    self.items.push(new ItemModel("A0000000" + i, "日通システム" + i,"2016/11/11", "コピー元のスケジュールが存在しません。"));
                }
                self.currentCode = ko.observable('');
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                $("#fixed-table").ntsFixedTable({ height: 230 });
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * export error 
             */
            private exportError():void {
                var self = this;
                let executionId = "";
                service.exportError("07138ec6-57a2-49d7-a158-1e149c4296c9").done(function() {

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
            code: string;
            name: string;
            date: string;
            errorContent: string;
            constructor(code: string, name: string, date: string, errorContent: string) {
                this.code = code;
                this.name = name;
                this.date = date;
                this.errorContent = errorContent;
            }
        }
    }
}