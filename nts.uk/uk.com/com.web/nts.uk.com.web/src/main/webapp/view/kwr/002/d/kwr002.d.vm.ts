module nts.uk.at.view.kwr002.d {
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    export module viewmodel {
        export class ScreenModel {

            constructor() {
                var self = this;
            }
            public start_page(): JQueryPromise<any> {
                var self = this;
                blockUI.invisible();
                var dfd = $.Deferred();

                return dfd.promise();
            }
        }
    }
}