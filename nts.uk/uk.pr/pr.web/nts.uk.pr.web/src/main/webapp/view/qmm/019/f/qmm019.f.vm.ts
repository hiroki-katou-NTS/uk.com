module nts.uk.pr.view.qmm019.f.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {
        selectedSearchIitemName: KnockoutObservable<any>;
        itemNames: KnockoutObservableArray<shareModel.ItemModel>;
        codeSelected: KnockoutObservable<any>;
        itemNameSelected: KnockoutObservable<shareModel.ItemModel>;

        value: KnockoutObservable<number>;

        constructor() {
            let self = this;
            self.selectedSearchIitemName = ko.observable(null);
            self.itemNames = ko.observableArray([]);
            self.codeSelected = ko.observable(null);
            self.itemNameSelected = ko.observable(new shareModel.ItemModel(null, null));

            self.value = ko.observable(1000);

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
            dfd.resolve();
            return dfd.promise();
        }

        register() {

        }

        decide() {
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }
}