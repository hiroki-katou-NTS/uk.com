module nts.uk.com.view.cmm022.a {
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;

    const LAST_INDEX_ERA_NAME_SYTEM: number = 3;
    export module viewmodel {
        export class ScreenModel {
                listMaster: KnockoutObservableArray<any> = ko.observableArray([]);
            constructor() {
                let self = this;
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                service.getListMaster().done((data: any) => {
                    self.listMaster(data);
                    console.log(data);
                }).always(() => {
                    nts.uk.ui.errors.clearAll();
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                });

                return dfd.promise();
            }
            
            newItem(){
                let self = this;
                setShared('listMasterToB', self.listMaster());
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {
                    
                });
            }
            
            dialogC(){
                nts.uk.ui.windows.sub.modal('/view/cmm/022/c/index.xhtml').onClosed(function(): any {
                });                
            }

        }


    }
}