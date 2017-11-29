module cmm051.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import IWorkplaceManager = base.IWorkplaceManager;
    
    export class ScreenModel {
        constructor() {
            let self = this;
        }
        start() {
            let self = this;
            var dfd = $.Deferred();
            self.getWkpManagerList().done(() => {
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        private getWkpManagerList(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            
            nts.uk.ui.block.grayout();
            
            service.findAllWkpManager().done(function(data: Array<IWorkplaceManager>) {
                nts.uk.ui.block.clear();
                
                // TODO
                dfd.resolve();
            }).fail((res: any) => {
                nts.uk.ui.block.clear();
//                self.showMessageError(res);
            });
            return dfd.promise();
        }
        
        initWorkplace() {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();

        }
        
        addWorkplace() {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();

        }
        
        deleteWorkplace() {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();

        }
    }
}