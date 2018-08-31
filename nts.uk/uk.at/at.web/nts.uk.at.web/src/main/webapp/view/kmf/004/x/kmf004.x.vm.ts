module nts.uk.at.view.kmf004.x.viewmodel {
    export class ScreenModel {
        
        constructor() {
            var self = this;
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            $('#special-holiday-setting').focus();
            
            dfd.resolve();

            return dfd.promise();
        }
        
        openADialog(): void {
            nts.uk.request.jump("/view/kmf/004/a/index.xhtml", {});
        }
        
        openIDialog(): void {
            nts.uk.request.jump("/view/kmf/004/i/index.xhtml", {});
        }
    }
}