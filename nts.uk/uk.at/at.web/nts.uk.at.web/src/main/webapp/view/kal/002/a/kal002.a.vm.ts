module nts.uk.at.view.kal002.a.viewmodel {
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

        openKAL003() { nts.uk.ui.windows.sub.modal("/view/kal/003/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => )};
        openKAL004() { nts.uk.ui.windows.sub.modal("/view/kal/004/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => )};
    }
}

