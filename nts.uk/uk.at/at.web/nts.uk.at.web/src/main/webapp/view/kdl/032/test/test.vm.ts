module test.viewmodel {
    export class ScreenModel {
        reasonCD: KnockoutObservable<string>;
        deviationTimeID: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.reasonCD = ko.observable("");
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
            let temp = self.reasonCD();
             var dataSetShare = {
                reasonCD: temp,
                divergenceTimeID: self.divergenceTimeID()
               
            };
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared('KDL032', dataSetShare );
            nts.uk.ui.windows.sub.modal("/view/kdl/032/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
                var returnData = nts.uk.ui.windows.getShared("ReturnData");
                if (returnData !== undefined) {
                    self.reasonCD(returnData);
                    nts.uk.ui.block.clear(); 
                }
                else{
                    nts.uk.ui.block.clear();}
            }); 
        }
    }
    interface dataSetShare {
        reasonCD: string;
        divergenceTimeID: string;
        
    }
    
}