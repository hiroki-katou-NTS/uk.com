module nts.uk.at.view.kmk013.e {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            itemListUnit: KnockoutObservableArray<ItemModel>;
            itemListRounding: KnockoutObservableArray<ItemModel>;
            itemListRoundingFull: KnockoutObservableArray<ItemModel>;
            listData: KnockoutObservableArray<UnitRouding>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.listData = ko.observableArray([]);
                self.itemListUnit = ko.observableArray([
                    new ItemModel(1, nts.uk.resource.getText("Enum_RoundingTime_1Min")),
                    new ItemModel(5, nts.uk.resource.getText("Enum_RoundingTime_5Min")),
                    new ItemModel(6, nts.uk.resource.getText("Enum_RoundingTime_6Min")),
                    new ItemModel(10, nts.uk.resource.getText("Enum_RoundingTime_10Min")),
                    new ItemModel(15, nts.uk.resource.getText("Enum_RoundingTime_15Min")),
                    new ItemModel(20, nts.uk.resource.getText("Enum_RoundingTime_20Min")),
                    new ItemModel(30, nts.uk.resource.getText("Enum_RoundingTime_30Min")),
                    new ItemModel(60, nts.uk.resource.getText("Enum_RoundingTime_60Min"))
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
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(false);

            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.initData();
                dfd.resolve();
                return dfd.promise();
            }
            initData(): void {
                let self = this;
                service.findByCompanyId().done(arr => {
                    let arrSort = _.orderBy(arr, [(obj) => {
                        return parseInt(obj.timeItemId);
                    }], ['asc']);
                    _.forEach(arrSort, (element) => {
                        self.listData.push(new UnitRouding(element.timeItemId, element.unit, element.rounding));
                    });
                });

            }
            saveData(): void {
                let self = this;
                blockUI.invisible();
                service.save(ko.toJS(self.listData())).done(() => {
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                    blockUI.clear();
                }).fail((error)=>{
                    console.log(error);
                    blockUI.clear(); 
                });
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
        class UnitRouding {
            timeItemId: KnockoutObservableArray<string>;
            unit: KnockoutObservableArray<number>;
            rounding: KnockoutObservableArray<number>;
            constructor(timeItemId: string, unit: number, rounding: number) {
                this.timeItemId = ko.observable(timeItemId);
                this.unit = ko.observable(unit);
                this.rounding = ko.observable(rounding);
            }
        }
    }
}