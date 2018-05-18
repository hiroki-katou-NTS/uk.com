module nts.uk.at.view.kwr001.d {
    
    import service = nts.uk.at.view.kwr001.d.service;
    
    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            D1_6_value: KnockoutObservable<string>;
            D1_7_value: KnockoutObservable<string>;
        
            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.itemList = ko.observableArray([]);
        
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                self.D1_6_value = ko.observable('');
                self.D1_7_value = ko.observable('');
            }
            
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                var data = nts.uk.ui.windows.getShared('KWR001_D');
                
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
            
            initData() : JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        
            closeSaveDialog(): void {
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