module nts.uk.at.view.kdl006.parent {

    export module viewModel {
        
        export class ScreenModel {
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve(); 
                return dfd.promise();
            }
            
            public openDialog(): void {
                let _self = this;
                nts.uk.ui.block.grayout();
                nts.uk.ui.windows.sub.modal("/view/kdl/006/a/index.xhtml").onClosed(() => {
                    nts.uk.ui.block.clear();
                });
            }
        }
    }
}