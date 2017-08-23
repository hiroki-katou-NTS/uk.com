module nts.uk.com.view.cps006.b.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    export class ScreenModel {

        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

        currentCode: KnockoutObservable<string> = ko.observable('');

        columns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', prop: 'itemCode', width: 100, hidden: true },
            { headerText: getText('CPS006_15'), prop: 'itemName', width: 150 },
            { headerText: getText('CPS006_16'), prop: 'itemIsSetting', width: 50, formatter: makeIcon },
        ]);

        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: '1', name: '四捨五入' },
            { code: '2', name: '切り上げ' }
        ]);

        simpleValue: KnockoutObservable<string> = ko.observable('');

        categoryType: KnockoutObservable<number> = ko.observable(1);

        selectedRuleCode: KnockoutObservable<number> = ko.observable(1);

        currentItem: KnockoutObservable<ItemModel> = ko.observable(new ItemModel(null));

        constructor() {
            let self = this;

            self.currentCode.subscribe(function(newValue) {

                let newItem = _.find(self.items(), function(o) { return o.itemCode == newValue; });

                self.currentItem(newItem);

            });
        }

        start(): JQueryPromise<any> {

            let self = this,
                dfd = $.Deferred();

            for (let i = 0; i < 100; i++) {
                let iItemModel = {
                    itemName: 'item' + i,
                    itemCode: 'code' + i,
                    itemIsSetting: i % 2 == 0 ? true : false
                }
                self.items().push(new ItemModel(iItemModel));
            }
            dfd.resolve();

            return dfd.promise();
        }

    }
    function makeIcon(value, row) {
        if (value == 'true')
            return "×";
        return '';
    }
    export interface IItemModel {
        itemName: string;
        itemCode: string;
        itemIsSetting: boolean;
    }

    export class ItemModel {
        itemName: string;
        itemCode: string;
        itemIsSetting: boolean;

        constructor(data: IItemModel) {

            this.itemName = data ? data.itemName : '';
            this.itemCode = data ? data.itemCode : '';
            this.itemIsSetting = data ? data.itemIsSetting : false;

        }

    }

}


