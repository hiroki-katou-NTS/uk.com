module nts.uk.at.view.kaf011.b.viewmodel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;

    export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {
        screenModeNew: KnockoutObservable<boolean> = ko.observable(false);
        update() {

        }

        constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata) {
            super(listAppMetadata, currentApp);
            let self = this;

            self.startPage(self.appID());
        }

        startPage(appID: string): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred(),
                appParam = { appID: appID };
            service.findById(appParam).done((data) => {
                self.setDataFromStart(data);

            }).fail((error) => {
                dialog({ messageId: error.messageId });
            }).always(() => {
                dfd.resolve();

            });



            return dfd.promise();

        }
        setDataFromStart(data) {

        }
    }


}