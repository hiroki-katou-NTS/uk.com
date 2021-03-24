module nts.uk.at.view.kmk013.e {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            itemListUnit: KnockoutObservableArray<ItemModel>;
            listData: KnockoutObservableArray<UnitRouding>;
            
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

                $("#fixed-table").ntsFixedTable({ width: 560 });
            }

            startPage(): JQueryPromise<any> {
                const self = this;
                const dfd = $.Deferred();
                blockUI.invisible();
                service.findByCompanyId().done(data => {
                    if (!_.isEmpty(data)) {
                        service.getPossibleItem(_.map(data.itemRoundingSet, (i: any) => i.timeItemId)).done(attendanceItems => {
                            _.forEach(attendanceItems, (element) => {
                                let obj: any = _.find(data.itemRoundingSet, ['timeItemId', element.attendanceItemId]);
                                let ur = new UnitRouding(element.attendanceItemId, element.attendanceItemName, obj ? obj.unit : 0, obj ? obj.rounding : 0);
                                self.listData.push(ur);
                            });
                            dfd.resolve();
                        }).fail(error => {
                            dfd.reject();
                            nts.uk.ui.dialog.alert(error);
                        });
                    } else {
                        dfd.resolve();
                    }
                }).fail(error => {
                    dfd.reject();
                    nts.uk.ui.dialog.alert(error);
                }).always(() => {
                    blockUI.clear();
                });
                return dfd.promise();
            }

            saveData(): void {
                let self = this;
                blockUI.invisible();
                service.save(ko.toJS(self.listData)).done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                        $('.unit-combo-box')[0].focus();
                    });
                }).fail((error) => {
                    nts.uk.ui.dialog.alert(error);
                }).always(() => {
                    blockUI.clear();
                });
            }
        }

        class ItemModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        class UnitRouding {
            timeItemId: number;
            attendanceItemName: string;
            unit: KnockoutObservable<number>;
            rounding: KnockoutObservable<number>;
            list_round: KnockoutObservableArray<any>;
            constructor(timeItemId: number, attendanceItemName: string, unit: number, rounding: number) {
                const self = this;
                self.timeItemId = timeItemId;
                self.attendanceItemName = attendanceItemName;
                self.unit = ko.observable(unit);
                self.rounding = ko.observable(rounding);
                self.list_round = ko.observableArray([
                    new ItemModel(0, nts.uk.resource.getText("Enum_Rounding_Down")),
                    new ItemModel(1, nts.uk.resource.getText("Enum_Rounding_Up")),
                    new ItemModel(2, nts.uk.resource.getText("Enum_Rounding_Down_Over"))
                ]);
                self.unit.subscribe(function(v) {
                    if (v == 4 || v == 6) {
                        if (self.list_round().length == 2)
                            self.list_round.push(new ItemModel(2, nts.uk.resource.getText("Enum_Rounding_Down_Over")));
                    } else {
                        if (self.list_round().length == 3) self.list_round.pop();
                        if (self.rounding() == 2)
                            self.rounding(0);
                    }
                });
                this.unit.valueHasMutated();
            }
        }
    }
}