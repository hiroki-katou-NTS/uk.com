module nts.uk.pr.view.qmm019.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {

        itemList: KnockoutObservableArray<shareModel.BoxModel>;
        selectedId: KnockoutObservable<number>;

        constructor() {
            let self = this;
            let params = getShared("QMM019_A_TO_J_PARAMS");

            self.itemList = ko.observableArray([
                new shareModel.BoxModel(0, getText("QMM019_188")),
                new shareModel.BoxModel(1, getText("QMM019_189")),
            ]);

            self.selectedId = ko.observable(0);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        decide(){
            let self = this;
            // validate

            if(!nts.uk.ui.errors.hasError()) {
                setShared("QMM019_J_TO_A_PARAMS", {isRegistered: true, printSet: self.selectedId()});
                nts.uk.ui.windows.close();
            }
        }

        cancel(){
            setShared("QMM019_J_TO_A_PARAMS", {isRegistered: false});
            nts.uk.ui.windows.close();
        }
    }
}