module nts.uk.at.view.kmk013.e {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            itemListUnit: KnockoutObservableArray<ItemModel>;
            itemListRounding: KnockoutObservableArray<ItemModel>;
            itemListRoundingFull: KnockoutObservableArray<ItemModel>;
            itemListExcOutRounding: KnockoutObservableArray<ItemModel>;
            itemListExcOutRoundingFull: KnockoutObservableArray<ItemModel>;
            currentRounding: KnockoutObservableArray<ItemModel>;
            listData: KnockoutObservableArray<UnitRouding>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            
            excRoundingUnit: KnockoutObservable<number>;
            excRoundingProc: KnockoutObservable<number>;
            isVisibleE22: KnockoutObservable<boolean>;
            
            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.listData = ko.observableArray([]);
                
                self.itemListUnit = ko.observableArray([
                    new ItemModel(0, nts.uk.resource.getText("Enum_RoundingTime_1Min")),
                    new ItemModel(1, nts.uk.resource.getText("Enum_RoundingTime_5Min")),
                    new ItemModel(2, nts.uk.resource.getText("Enum_RoundingTime_6Min")),
                    new ItemModel(3, nts.uk.resource.getText("Enum_RoundingTime_10Min")),
                    new ItemModel(4, nts.uk.resource.getText("Enum_RoundingTime_15Min")),
                    new ItemModel(5, nts.uk.resource.getText("Enum_RoundingTime_20Min")),
                    new ItemModel(6, nts.uk.resource.getText("Enum_RoundingTime_30Min")),
                    new ItemModel(7, nts.uk.resource.getText("Enum_RoundingTime_60Min"))
                ]);

                self.itemListRounding = ko.observableArray([
                    new ItemModel(0, nts.uk.resource.getText("Enum_Rounding_Down")),
                    new ItemModel(1, nts.uk.resource.getText("Enum_Rounding_Up")),
                    //new ItemModel(2, nts.uk.resource.getText("Enum_Rounding_Down_Over"))
                ]);
                self.itemListRoundingFull = ko.observableArray([
                    new ItemModel(0, nts.uk.resource.getText("Enum_Rounding_Down")),
                    new ItemModel(1, nts.uk.resource.getText("Enum_Rounding_Up")),
                    new ItemModel(2, nts.uk.resource.getText("Enum_Rounding_Down_Over"))
                ]);
                self.itemListExcOutRounding = ko.observableArray([
                    new ItemModel(0, nts.uk.resource.getText("Enum_Exc_Rounding_Down")),
                    new ItemModel(1, nts.uk.resource.getText("Enum_Exc_Rounding_Up"))
                ]);
                self.itemListExcOutRoundingFull = ko.observableArray([
                    new ItemModel(0, nts.uk.resource.getText("Enum_Exc_Rounding_Down")),
                    new ItemModel(1, nts.uk.resource.getText("Enum_Exc_Rounding_Up")),
                    new ItemModel(2, nts.uk.resource.getText("Enum_Exc_Follow_Element"))
                ]);
                self.currentRounding = ko.observableArray(self.itemListExcOutRounding());
                
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(false);
                
                self.excRoundingUnit = ko.observable(0);
                self.excRoundingProc = ko.observable(0);
                self.isVisibleE22 = ko.observable(false);
                
                self.excRoundingUnit.subscribe(function(v) {
                    if (v == 4 || v == 6) {
                        self.currentRounding(self.itemListExcOutRoundingFull());
                    }
                    else {
                        self.currentRounding(self.itemListExcOutRounding());
                        if (self.excRoundingProc() == 2)
                            self.excRoundingProc(0);
                    }
                });
                
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                $("#fixed-table").ntsFixedTable({ height: 300, width: 560 });
                self.initData();
                dfd.resolve();
                return dfd.promise();
            }
            initData(): void {
                let self = this;
                service.getOTCalc().done(data => {
                    if (data.calculationMethod == 0) {
                        self.isVisibleE22(false);
                    }
                    else {
                        self.isVisibleE22(true);
                    }
                });
                service.getIdMonth().done(arr => {
                    service.getPossibleItem(arr).done(listName => {
                        service.findByCompanyId().done(listData => {
                            _.forEach(listName, (element) => {
                                let obj: any = _.find(listData, ['timeItemId', element.attendanceItemId.toString()]);
                                let ur;
                                if (obj) {
                                    ur = new UnitRouding(element.attendanceItemId, element.attendanceItemName, obj.unit, obj.rounding, self.itemListRounding());
                                } else {
                                    ur = new UnitRouding(element.attendanceItemId, element.attendanceItemName, 0, 0, self.itemListRounding() );
                                }
                                ur.initRoundingOption(ur.unit(), self);
                                
                                self.listData.push(ur);
                            });
                            $('#unit-combo-box').find("input").focus();
                        });
                    });
                });
                service.findExcByCompanyId().done(data => {
                    if (data) {
                        self.excRoundingUnit(data.roundingUnit);
                        self.excRoundingProc(data.roundingProcess);
                    }
                });
            }
            saveData(): void {
                let self = this;
                blockUI.invisible();
                service.save(ko.toJS(self.gatherData())).done(() => {
                    let data:any = {};
                    data.roundingUnit = self.excRoundingUnit();
                    data.roundingProcess = self.excRoundingProc();
                    service.saveExcOut(data).done(() => {
                        blockUI.clear();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                            $("#unit-combo-box").find("input").focus();
                        });
                    });
                }).fail((error) => {
                    console.log(error);
                    blockUI.clear();
                });
            }
            
            private gatherData(): Array<UnitRoudingClientData>{
                let self = this;
                let data = [];
                for (let i = 0; i < self.listData().length; i++){
                    data.push(new UnitRoudingClientData(self.listData()[i]));
                }
                return data;
            }
        };

        class ItemModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        class UnitRoudingClientData {
            timeItemId: string;
            attendanceItemName: string;
            unit: number;
            rounding: number;
            
            constructor(unitRouding: UnitRouding){
                this.timeItemId = unitRouding.timeItemId;
                this.attendanceItemName = unitRouding.attendanceItemName;
                this.unit = unitRouding.unit;
                this.rounding = unitRouding.rounding;
            }
        }
        
        class UnitRouding {
            timeItemId: string;
            attendanceItemName: string;
            unit: KnockoutObservable<number>;
            rounding: KnockoutObservable<number>;
            list_round: KnockoutObservableArray<any>;
            constructor(timeItemId: string, attendanceItemName: string, unit: number, rounding: number, list: any) {
                this.timeItemId = timeItemId;
                this.attendanceItemName = attendanceItemName;
                this.unit = ko.observable(unit);
                this.rounding = ko.observable(rounding);
                this.list_round = ko.observableArray(list);
            }
            
            initRoundingOption(v: number, screenModel: any): void {
                let self = this;
                if (v == 4 || v == 6) {
                    self.list_round(screenModel.itemListRoundingFull());
                } else {
                    self.list_round(screenModel.itemListRounding());
                    if (self.rounding() == 2)
                        self.rounding(0);
                }
                
                this.unit.subscribe(function(v) {
                    if (v == 4 || v == 6) {
                        self.list_round(screenModel.itemListRoundingFull());
                    } else {
                        self.list_round(screenModel.itemListRounding());
                        if (self.rounding() == 2)
                            self.rounding(0);
                    }
                });
            }
        }
    }
}