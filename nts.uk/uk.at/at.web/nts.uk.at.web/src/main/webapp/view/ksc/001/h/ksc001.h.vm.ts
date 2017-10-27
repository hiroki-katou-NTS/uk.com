module nts.uk.at.view.ksc001.h {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                $("#fixed-table").ntsFixedTable({});
                dfd.resolve();
                return dfd.promise();
            }
            /**
             * 
             */
            openDialogI(): void {
                let self = this;
                blockUI.invisible();
                // the default value of categorySet = undefined
                //nts.uk.ui.windows.setShared('', );
                nts.uk.ui.windows.sub.modal("/view/ksc/001/i/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }
            /**
             * 
             */
            openDialogK(): void {
                let self = this;
                blockUI.invisible();
                // the default value of categorySet = undefined
                //nts.uk.ui.windows.setShared('', );
                nts.uk.ui.windows.sub.modal("/view/ksc/001/k/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }
             /**
             * close dialog 
             */
            closeDialog(): void {
                let self = this;
                nts.uk.ui.windows.close();   
            }

        }
    }
}