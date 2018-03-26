module nts.uk.at.view.kmk013.j {
    export module viewmodel {
        export class ScreenModel {
            transAttendMethod: KnockoutObservableArray<ItemModel>;
            selectedItem: KnockoutObservable<number>;
            
            constructor() {
                var self = this;
                self.transAttendMethod = ko.observableArray<ItemModel> ([
                    new ItemModel(0, nts.uk.resource.getText("KMK013_311")),
                    new ItemModel(1, nts.uk.resource.getText("KMK013_312"))
                ]);
                self.selectedItem = ko.observable(0);
            }
            
            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.loadAllSetting().done(function(data) {
                    self.selectedItem(data.flexWorkManagement);
                    dfd.resolve();
                })
                .fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            // Save data
            saveData(): void {
                
            }
        }
        
        
        // Class ItemModel
        class ItemModel {
            id: number;
            name: string;
            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
            }
        }
    }
}