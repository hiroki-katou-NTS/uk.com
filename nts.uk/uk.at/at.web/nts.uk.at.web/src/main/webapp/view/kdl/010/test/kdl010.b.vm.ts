module kdl010.b.viewmodel {
    export class ScreenModel {
        workLocationCD: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.workLocationCD = ko.observable("");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        openDialog() {
            var self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.workLocationCD());
            nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
                var returnWorkLocationCD = nts.uk.ui.windows.getShared("KDL010workLocation");
                if (returnWorkLocationCD !== undefined) {
                    self.workLocationCD(returnWorkLocationCD);
                    nts.uk.ui.block.clear();
                }
                else{
                    self.workLocationCD = ko.observable("");
                    nts.uk.ui.block.clear();
                }
            });
        }
    }
}