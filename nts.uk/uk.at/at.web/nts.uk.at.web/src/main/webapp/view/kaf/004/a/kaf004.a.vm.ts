module nts.uk.at.view.kaf004.a.viewmodel {
    export class ScreenModel {


        constructor() {
            var self = this;

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
        openBDialog() {
            var self = this;
            nts.uk.ui.windows.setShared("ShowScreen", "B");
            nts.uk.ui.windows.sub.modal("/view/kaf/004/b/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
            });
        }
        openCDialog() {
            var self = this;
             nts.uk.ui.windows.setShared("ShowScreen", "C");
            nts.uk.ui.windows.sub.modal("/view/kaf/004/b/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
            });
        }
        openDDialog() {
            var self = this;
            nts.uk.ui.windows.setShared("ShowScreen", "D");
            nts.uk.ui.windows.sub.modal("/view/kaf/004/b/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
            });
        }
    }
}