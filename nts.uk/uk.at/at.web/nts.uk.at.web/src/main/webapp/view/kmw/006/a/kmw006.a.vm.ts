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
        selectedClosureId: KnockoutObservable<number>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel(1, '基本給'),
                new ItemModel(2, '役職手当'),
                new ItemModel(3, '役職手当'),
                new ItemModel(4, '役職手当'),
                new ItemModel(3, '基本給ながい文')
            ]);
    
            self.selectedClosureId = ko.observable(1);
            $("#A1_1").ntsFixedTable({});
            $("#A1_10").ntsFixedTable({});
            $("#A1_14").ntsFixedTable({});
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        private executeClick(){
            let self = this;
            block.invisible();
            service.checkStatus(self.selectedClosureId()).done((result) => {
                if (result) {
                    result.periodStart = moment.utc("result.periodStart", "YYYY/MM/DD").toISOString();
                    result.periodEnd = moment.utc("result.periodEnd", "YYYY/MM/DD").toISOString();
                    self.openKMW006fDialog(result);
                }
            }).fail((error) => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        private openKMW006fDialog(params: any) {
            let self = this;
            nts.uk.ui.errors.clearAll();
            setShared("kmw006fParams", params);
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