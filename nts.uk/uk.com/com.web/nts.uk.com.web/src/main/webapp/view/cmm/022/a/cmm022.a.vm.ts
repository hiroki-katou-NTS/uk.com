module nts.uk.com.view.cmm022.a {
    import blockUI = nts.uk.ui.block;

    const LAST_INDEX_ERA_NAME_SYTEM: number = 3;
    export module viewmodel {
        export class ScreenModel {

            constructor() {
                let self = this;
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();

                dfd.resolve();

                return dfd.promise();
            }
            
            newItem(){
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {
                });
            }


        }


    }
}