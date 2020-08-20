module nts.uk.at.view.kdl017.test.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import alError = nts.uk.ui.dialog.alertError;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;

    export class ViewModel {

        selectedCodeList = ko.observable();
        baseDate = ko.observable(new Date());
        constructor() {
            let self = this;


        }

        openKDL020Dialog() {
            let self = this,
                employeeList = _.split(self.selectedCodeList(), ', ');
            setShared('KDL020A_PARAM', { baseDate: self.baseDate(), employeeIds: employeeList } );
            modal('/view/kdl/017/a/index.xhtml').onClosed(function(): any {

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