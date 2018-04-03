module nts.uk.at.view.kmw006.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<number>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel(1, '基本給'),
                new ItemModel(2, '役職手当'),
                new ItemModel(3, '基本給ながい文')
            ]);
    
            self.selectedCode = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }



        openKMW006fDialog() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            modal("/view/kmw/006/f/index.xhtml").onClosed(() => {
                var output = getShared("outputKAL003d");
                if (!nts.uk.util.isNullOrUndefined(output)) {
                    
                }
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

}