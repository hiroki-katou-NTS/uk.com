module kdl010.b.viewmodel {
    export class ScreenModel {
        workLocationCD: KnockoutObservable<string>;
        workLocationName: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.workLocationCD = ko.observable("");
            self.workLocationName = ko.observable("");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        openDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('SelectWorkLocation', self.workLocationCD());
            console.log(self.workLocationCD);
            nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() =>{
            var self = this;
            var returnItem = nts.uk.ui.windows.getShared("workLocation");
            self.workLocationCD(returnItem.workLocationCD);
            self.workLocationName(returnItem.workLocationName);
            });
        }
    }
}