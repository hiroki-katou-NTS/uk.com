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
            //chua dinh nghia
            executeCopy(): void {
                let self = this;
            }
            closeDialog(): void {
                nts.uk.ui.windows.setShared('KWR006_D', null);
                nts.uk.ui.windows.close();
            }
            //chua dinh nghia
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                let data = nts.uk.ui.windows.getShared('KWR001_D');
                
                dfd.resolve();      
                return dfd.promise();

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