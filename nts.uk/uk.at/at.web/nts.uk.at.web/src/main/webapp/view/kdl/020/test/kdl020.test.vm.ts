module nts.uk.at.view.kdl020.test.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import alError = nts.uk.ui.dialog.alertError;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ViewModel {
        constructor() {
            let self = this;

        }

        openKDL020Dialog() {
            modal('/view/kdl/020/a/index.xhtml').onClosed(function(): any {

            });

        }
        start(): JQueryPromise<any> {
        
            var self = this,
                dfd = $.Deferred();


            dfd.resolve();

            return dfd.promise();
        }
    }
}