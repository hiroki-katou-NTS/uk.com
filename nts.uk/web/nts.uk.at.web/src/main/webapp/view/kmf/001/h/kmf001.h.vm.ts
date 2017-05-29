module nts.uk.pr.view.kmf001.h {
    export module viewmodel {
        export class ScreenModel {

            settingModel: KnockoutObservable<SubstVacationSettingModel>;

            // Switch button data source
            switchApplyDs: KnockoutObservableArray<SwitchButtonDataSource>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;

                self.settingModel = ko.observable(new SubstVacationSettingModel());
                self.switchApplyDs = ko.observableArray<SwitchButtonDataSource>([
                    { code: ApplySetting.APPLY, name: '管理する' },
                    { code: ApplySetting.NOTAPPLY, name: '管理しない' }
                ]);
            }

            /**
             * Start page.
             */
            private startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                // resolve.
                dfd.resolve();

                return dfd.promise();
            }


        }

        export class ApplySetting {
            static APPLY = 'Apply';
            static NOTAPPLY = 'NotApply';
        }

        export interface SwitchButtonDataSource {
            code: string;
            name: string;
        }

        export class SubstVacationSettingModel {
            isManage: KnockoutObservable<number>;
            expirationDate: KnockoutObservable<number>;
            allowPrepaidLeave: KnockoutObservable<number>;
        }
    }
}