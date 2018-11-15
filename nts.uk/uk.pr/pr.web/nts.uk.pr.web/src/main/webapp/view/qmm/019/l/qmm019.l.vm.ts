module nts.uk.pr.view.qmm019.l.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {

        itemList: KnockoutObservableArray<shareModel.BoxModel>;
        selectedId: KnockoutObservable<number>;

        constructor() {
            let self = this;
            self.itemList = ko.observableArray([
                new shareModel.BoxModel(1, getText("QMM019_194")),
                new shareModel.BoxModel(2, getText("QMM019_195")),
                new shareModel.BoxModel(3, getText("QMM019_196"))
            ]);
            self.selectedId = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        decide(){
            nts.uk.ui.windows.close();
        }

        cancel(){
            nts.uk.ui.windows.close();
        }
    }
}