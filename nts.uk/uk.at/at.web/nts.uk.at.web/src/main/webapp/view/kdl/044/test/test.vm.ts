module test.viewmodel {

    import setShared = nts.uk.ui.windows.setShared;
    import IDataTransfer = nts.uk.at.view.kdl044.a.viewmodel.IDataTransfer
    
    export class ScreenModel {
        constructor() {
            let self = this;
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        openDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            let dataSetShare :IDataTransfer = {
                isMultiSelect: true,
                permission: true,
                filter: 0,
                shifutoCodes: ["1", "2", "3"]
            }
            setShared( 'kdl044Data', dataSetShare );
            nts.uk.ui.windows.sub.modal( "/view/kdl/044/a/index.xhtml", { dialogClass: "no-close" } )
                .onClosed(() => {
                    nts.uk.ui.block.clear();
                });
        }
    }

}