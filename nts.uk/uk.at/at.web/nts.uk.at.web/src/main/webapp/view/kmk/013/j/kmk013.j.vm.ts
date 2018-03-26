module nts.uk.at.view.kmk013.j {
    export module viewmodel {
        export class ScreenModel {
            transAttendMethod: KnockoutObservableArray<ItemModel>;
            selectedItem: KnockoutObservable<number>; // J2_4
            givenDays: KnockoutObservable<string>; // J4_5
            attendanceDays: KnockoutObservable<string>; // J4_9
            
            constructor() {
                var self = this;
                self.transAttendMethod = ko.observableArray<ItemModel> ([
                    new ItemModel(0, nts.uk.resource.getText("KMK013_311")),
                    new ItemModel(1, nts.uk.resource.getText("KMK013_312"))
                ]);
                self.selectedItem = ko.observable(0);
                self.givenDays = ko.observable('');
                self.attendanceDays = ko.observable('');
            }
            
            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.loadAllSetting().done(function(data) {
                    self.selectedItem(data.attendanceItemCountingMethod);
                    // TODO: fill the rest of other fields
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