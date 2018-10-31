module nts.uk.pr.view.qmm019.e.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {
        option: any;

        selectedSearchIitemName: KnockoutObservable<any>;
        itemNames: KnockoutObservableArray<shareModel.ItemModel>;
        codeSelected: KnockoutObservable<any>;
        itemNameSelected: KnockoutObservable<shareModel.ItemModel>;
        value: KnockoutObservable<number>;
        breakdownAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        breakdownAtrSelected: KnockoutObservable<shareModel.ItemModel>;
        calcMethods: KnockoutObservableArray<shareModel.ItemModel>;
        calcMethodSelected: KnockoutObservable<shareModel.ItemModel>;
        proportionalDivisionSetAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        proportionalDivisionSetAtrSelected: KnockoutObservable<shareModel.ItemModel>;
        proportionalDivisionRatioSetAtrs: KnockoutObservableArray<shareModel.ItemModel>;
        proportionalDivisionRatioSetAtrSelected: KnockoutObservable<shareModel.ItemModel>;


        constructor() {
            let self = this;
            self.option = {
                grouplength: 3,
                textalign: "right",
                currencyformat: "JPY"
            }

            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);
            self.itemNameSelected = ko.observable(new shareModel.ItemModel(null, null));

            self.value = ko.observable(1000);
            self.breakdownAtrs = ko.observableArray(shareModel.getDeductionTotalObjAtr());
            self.breakdownAtrSelected = ko.observable(null);
            self.calcMethods = ko.observableArray(shareModel.getDeductionCaclMethodAtr());
            self.calcMethodSelected = ko.observable(null);
            self.proportionalDivisionSetAtrs = ko.observableArray(shareModel.getProportionalDivisionSetAtr())
            self.proportionalDivisionSetAtrSelected = ko.observable(null);
            self.proportionalDivisionRatioSetAtrs = ko.observableArray(shareModel.getProportionalDivisionRatioSetAtr())
            self.proportionalDivisionRatioSetAtrSelected = ko.observable(null);

            for (let i = 1; i < 100; i++) {
                this.itemNames.push(new shareModel.ItemModel(i.toString(), "item name " + (i + 1)));
            }

            self.codeSelected.subscribe(value => {
                let itemName = _.find(self.itemNames(), (item: shareModel.ItemModel) => {
                    return item.code == value;
                })
                self.itemNameSelected(itemName);
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            $("#fixed-table").ntsFixedTable({ height: 139});
            dfd.resolve();
            return dfd.promise();
        }

        register() {

        }

        referenced(){

        }

        decide() {
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }
}