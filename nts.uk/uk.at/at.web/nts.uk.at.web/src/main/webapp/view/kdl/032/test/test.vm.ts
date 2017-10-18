module test.viewmodel {
    export class ScreenModel {
        companyID: KnockoutObservable<string>;
        deviationTimeID: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.reasonCD = ko.observable("001");
            self.divergenceTimeID = ko.observable("");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        openDialog() {
            var self = this;
             var dataSetShare = {
                reasonCD: self.reasonCD(),
                divergenceTimeID: self.divergenceTimeID()
               
            };
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared('KDL032', dataSetShare );
            nts.uk.ui.windows.sub.modal("/view/kdl/032/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
                var returnWorkLocationCD = nts.uk.ui.windows.getShared("KDL010workLocation");
                if (returnWorkLocationCD !== undefined) {
                    self.workLocationCD(returnWorkLocationCD);
                    nts.uk.ui.block.clear(); 
                }
                else{
                    self.workLocationCD = ko.observable("");
                    nts.uk.ui.block.clear();}
            }); 
        }
    }
}