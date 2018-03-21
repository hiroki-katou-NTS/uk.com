module nts.uk.at.view.kaf011.b.viewmodel {

    import dialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    export class ViewModel extends kaf000.b.viewmodel.ScreenModel {
        screenModeNew: KnockoutObservable<boolean> = ko.observable(false);
        update() {

        }
        start(appID: string): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();

        }
    }


}