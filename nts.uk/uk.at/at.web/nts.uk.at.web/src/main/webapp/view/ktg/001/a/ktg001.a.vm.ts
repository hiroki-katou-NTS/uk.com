module nts.uk.at.view.ktg001.a.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    export class ScreenModel {


        constructor() {
            let self = this;

        }

        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getData().done((data) => {
                console.log(data);
            });
            dfd.resolve();
            return dfd.promise();
        }
        dailyPerformanceConfirm() {
            block.invisible();
            windows.sub.modeless("/view/kdw/004/a/index.xhtml").onClosed(() => {
                block.clear();
            });
        }
    }
}