module nts.uk.at.view.kmk011.h {

    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {

            selectWorkTypeCheck: KnockoutObservable<number>;

            constructor() {
                let _self = this;
                _self.selectWorkTypeCheck = ko.observable(0);

                _self.selectWorkTypeCheck.subscribe(function() {
                });
            }

            public start_page(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();

                dfd.resolve();
                return dfd.promise();
            }
            
            private closeSaveDialog(): void{
                let _self = this;
                nts.uk.ui.windows.close();
            }
            
            private closeDialog(): void {
                nts.uk.ui.windows.close();    
            }
        }
    }
}