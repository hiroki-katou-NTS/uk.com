module nts.uk.at.view.kwr006.d {
    import service = nts.uk.at.view.kwr006.d.service;

    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;

            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

            D1_6_value: KnockoutObservable<string>;
            D1_7_value: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.itemList = ko.observableArray([]);
                self.selectedCode = ko.observable('');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                self.D1_6_value = ko.observable('');
                self.D1_7_value = ko.observable('');
            }

            /*
                Start Page
            */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                let data = nts.uk.ui.windows.getShared('KWR006_D');

                service.getDataStartPage().done(function(data: any) {
                    let arr: ItemModel[] = [];
                    _.forEach(data, function(value, index) {
                        arr.push(new ItemModel(value.code, value.name));
                    })
                    self.itemList(arr);
                    dfd.resolve();
                })
                return dfd.promise();
            }

            /*
                Execute Copy
            */
            executeCopy(): void {
                let self = this,
                    dataReturnScrC: any = {};
                $('.save-error').ntsError('check');
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                service.executeCopy(self.D1_6_value(), self.selectedCode(), nts.uk.ui.windows.getShared('KWR006_D')).done(function(data: any) {
                    console.log("Hoang" + data);
                    dataReturnScrC.lstAtdChoose = data;
                    dataReturnScrC.codeCopy = self.D1_6_value();
                    dataReturnScrC.nameCopy = self.D1_7_value();
                    nts.uk.ui.windows.setShared('KWR006_D', dataReturnScrC);
                    nts.uk.ui.windows.close();
                }).fail(function(err) {
                    nts.uk.ui.dialog.error(err);
                })
            }

            /*
                Close Dialog D
            */
            closeDialog(): void {
                nts.uk.ui.windows.setShared('KWR006_D', null);
                nts.uk.ui.windows.close();
            }
        };

        class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}