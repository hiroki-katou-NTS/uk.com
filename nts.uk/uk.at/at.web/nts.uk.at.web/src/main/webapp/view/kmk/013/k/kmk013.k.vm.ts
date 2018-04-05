module nts.uk.at.view.kmk013.k {
    export module viewmodel {
        export class ScreenModel {
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                $( "#ot-set-btn-k3_2" ).focus(); 
                return dfd.promise();
            }
            
            otSetBtn(): void {
                nts.uk.request.jump("/view/kmk/010/a/index.xhtml");
            }
            
            outOfOderSetBtn(): void {
                nts.uk.request.jump("/view/kmk/013/q/index.xhtml");
            }
            
            regLegalPrescribeTimeBtn(): void {
                nts.uk.request.jump("/view/kmk/004/a/index.xhtml");
            }
        }
    }
}