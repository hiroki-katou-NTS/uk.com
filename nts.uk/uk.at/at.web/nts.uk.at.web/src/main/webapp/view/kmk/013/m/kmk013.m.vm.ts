module nts.uk.at.view.kmk013.m {
    
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            // Item M2_2
            upperLimitOption: KnockoutObservableArray<ItemModel>;
            upperLimitChoice: KnockoutObservable<number>;
            
            // Item M3_2
            calcMethodOption: KnockoutObservableArray<ItemModel>;
            calcMethodChoice: KnockoutObservable<number>;
            
            vacationOrder: KnockoutObservable<VacationOrderDto>;
            
            constructor() {
                var self = this;
                
                self.upperLimitOption = ko.observableArray<ItemModel> ([
                    new ItemModel(0, nts.uk.resource.getText("KMK013_353")),
                    new ItemModel(1, nts.uk.resource.getText("KMK013_354")),
                    new ItemModel(2, nts.uk.resource.getText("KMK013_355"))
                ]);
                self.upperLimitChoice = ko.observable(0);
                
                self.calcMethodOption = ko.observableArray<ItemModel> ([
                    new ItemModel(1, nts.uk.resource.getText("KMK013_358")),
                    new ItemModel(2, nts.uk.resource.getText("KMK013_359")),
                    new ItemModel(0, nts.uk.resource.getText("KMK013_360"))
                ]);
                self.calcMethodChoice = ko.observable(0);
                
                self.vacationOrder = ko.observable(new VacationOrderDto(0,1,3,2));
            }
            
            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                var self = this;
                
                service.findSetting().done(function(data) {
                    if (data) {
                        self.upperLimitChoice(data.upperLimitSet);
                        self.calcMethodChoice(data.constraintCalcMethod);
                        if (data.substituteHoliday == data.sixtyHourVacation == data.specialHoliday == data.annualHoliday)
                            self.vacationOrder(new VacationOrderDto(0,1,3,2));
                        else
                            self.vacationOrder(new VacationOrderDto(data.substituteHoliday, data.sixtyHourVacation, data.specialHoliday, data.annualHoliday));
                    }
                    dfd.resolve();
                })
                .fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                
                return dfd.promise();
            }
            openScreenO () {
                var self = this;
                nts.uk.ui.windows.setShared('KMK013_0_Order', self.vacationOrder(), true);
                nts.uk.ui.windows.sub.modal('/view/kmk/013/o/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.block.clear();
                    var data = nts.uk.ui.windows.getShared('KMK013_O_NewOrder');
                    if (data) {
                        self.vacationOrder().substitute = data.substitute;
                        self.vacationOrder().sixtyHour = data.sixtyHour;
                        self.vacationOrder().annual = data.annual;
                        self.vacationOrder().special = data.special;
                    }
                });
            }
            
            saveData(): void {
                var self = this;
                let data: any = {};
                blockUI.grayout();
                data.constraintCalcMethod = self.calcMethodChoice();
                data.upperLimitSet = self.upperLimitChoice();
                data.substituteHoliday = self.vacationOrder().substitute;
                data.sixtyHourVacation = self.vacationOrder().sixtyHour;
                data.specialHoliday = self.vacationOrder().special;
                data.annualHoliday = self.vacationOrder().annual;
                service.register(data).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                        $('#upper-limit').focus();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(() => {
                    blockUI.clear();
                });
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
    // Class vacation order dto (for communicating with screen O)
    export class VacationOrderDto {
        substitute: number;
        sixtyHour: number;
        special: number;
        annual: number;
        constructor(substitute: number, sixtyHour: number, special: number, annual: number) {
            this.substitute = substitute;
            this.sixtyHour = sixtyHour;
            this.special = special;
            this.annual = annual;
        }
    }
}