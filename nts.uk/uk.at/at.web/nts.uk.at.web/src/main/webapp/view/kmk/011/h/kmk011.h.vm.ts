module nts.uk.at.view.kmk011.h {

    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {

            selectWorkTypeCheck: KnockoutObservable<boolean>;

            constructor() {
                let _self = this;
                _self.selectWorkTypeCheck = ko.observable(true);
            }

            public start_page(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();

                service.find().done(function(value: any) {
                   if(value != null) {
                       _self.selectWorkTypeCheck(value.workTypeUseSet)
                   }
                });
                dfd.resolve();
                return dfd.promise();
            }

            private closeSaveDialog(): void {
                let _self = this;
                nts.uk.ui.windows.close();
            }

            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }

        export class DivergenceReferenceTimeUsageUnit {
            workTypeUseSet: number;
        }

        export enum BoolValue {
            TRUE = 1,
            FALSE = 0
        }
    }
}