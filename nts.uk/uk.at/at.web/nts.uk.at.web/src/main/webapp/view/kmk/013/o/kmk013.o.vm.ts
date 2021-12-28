module nts.uk.at.view.kmk013.o {
    export module viewmodel {
        import VacationOrderDto = nts.uk.at.view.kmk013.m.VacationOrderDto;
        
        export class ScreenModel {
            
            vacationSortingOrderDisp: KnockoutObservableArray<ItemModel>;
            
            vacationSortingTemp: KnockoutObservableArray<ItemModel>;
            
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentVacation: KnockoutObservable<any>;
            
            constructor() {
                var self = this;
                self.vacationSortingOrderDisp = ko.observableArray([]);
                self.vacationSortingTemp = ko.observableArray([]);
                self.currentVacation = ko.observable("");
                
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMK013_370"), key: 'position', width: 80},
                    { headerText: nts.uk.resource.getText("KMK013_371"), key: 'name', width: 130 },
                ]);
                self.vacationSortingOrderDisp.subscribe(function (data) {
                    self.reIndexing();
                });
            }
            
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                
                var data = nts.uk.ui.windows.getShared('KMK013_0_Order');
                if (data) {
                    self.vacationSortingOrderDisp.removeAll();
                    let model;
                    for (var i = 0; i <= 5; i++) {
                        var positionText = (i + 1).toString();
                        if (data.substitute == i) {
                            model = new ItemModel(0, positionText, nts.uk.resource.getText("KMK013_378"));
                        } else if (data.sixtyHour == i) {
                            model = new ItemModel(1, positionText, nts.uk.resource.getText("KMK013_377"));
                        } else if (data.annual == i) {
                            model = new ItemModel(2, positionText, nts.uk.resource.getText("KMK013_376"));
                        } else if (data.special == i) {
                            model = new ItemModel(3, positionText, nts.uk.resource.getText("KMK013_379"));
                        } else if (data.childCare == i) {
                            model = new ItemModel(4, positionText, nts.uk.resource.getText("KMK013_476"));
                        } else if (data.care == i) {
                            model = new ItemModel(5, positionText, nts.uk.resource.getText("KMK013_477"));
                        }
                        if (i == 0) {
                            self.currentVacation(model.name);
                        }
                        self.vacationSortingOrderDisp.push(model);
                    }
                    
                }
                
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            reIndexing(): void {
                var self = this;
                var i = 0;
                self.vacationSortingOrderDisp().every(function(item, index) {
                    if (self.vacationSortingOrderDisp()[i]) {
                        self.vacationSortingOrderDisp()[i].position = (++i).toString();
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            }
            
            register() {
                var self = this;
                let data = {};
                var i = 0;
                _.forEach(self.vacationSortingOrderDisp(), items => {
                    if (items.id == 0)
                        //data.substitute = parseInt(items.position) - 1;
                        data["substitute"] = i++;
                    else if (items.id == 1)
                        //data.sixtyHour = parseInt(items.position) - 1;
                        data["sixtyHour"] = i++;
                    else if (items.id == 2)
                        //data.annual = parseInt(items.position) - 1;
                        data["annual"] = i++;
                    else if (items.id == 3)
                        //data.special = parseInt(items.position) - 1;
                        data["special"] = i++;
                    else if (items.id == 4)
                        //data.special = parseInt(items.position) - 1;
                        data["childCare"] = i++;
                    else if (items.id == 5)
                        //data.special = parseInt(items.position) - 1;
                        data["care"] = i++;
                });
                nts.uk.ui.windows.setShared('KMK013_O_NewOrder', data);
                self.close();
            }
            
            close() {
                nts.uk.ui.windows.close();
            }
        }
        
        class ItemModel {
            id: number;
            position: string; // 1st col
            name: string; // 2nd col
            constructor(id: number, position: string, name: string) {
                this.id = id;
                this.position = position;
                this.name = name;
            }
        }
    }
}