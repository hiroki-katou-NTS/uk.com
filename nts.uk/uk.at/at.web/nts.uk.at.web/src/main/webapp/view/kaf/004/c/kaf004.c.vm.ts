module nts.uk.at.view.kaf004.c.viewmodel {
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
           openEDialog() {
            var self = this;
            nts.uk.ui.windows.setShared("ShowScreen", "E");
            nts.uk.request.jump("/view/kaf/004/b/index.xhtml", {showScreen: "E"});
        }
          openFDialog() {
            var self = this;
            nts.uk.ui.windows.setShared("ShowScreen", "F");
            nts.uk.request.jump("/view/kaf/004/b/index.xhtml", {showScreen: "F"});
        }
    }
}