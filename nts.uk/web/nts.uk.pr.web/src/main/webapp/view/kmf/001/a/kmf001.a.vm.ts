module nts.uk.pr.view.kmf001.a {
    export module viewmodel {
        export class ScreenModel {

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
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

            // 優先順位の設定
            public openPrioritySettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/b/index.xhtml', { title: '優先順位の設定', dialogClass: 'no-close' });
            }

            // 年次有給休暇
            public openAnnualPaidLeaveSettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/c/index.xhtml', { title: '優先順位の設定', dialogClass: 'no-close' });
            }

            // 積立年休
            public openYearlyReservedSettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/d/index.xhtml', { title: '優先順位の設定', dialogClass: 'no-close' });
            }

            // 代休
            public openTemporaryHolidaysSettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/f/index.xhtml', { title: '優先順位の設定', dialogClass: 'no-close' });
            }

            // 振休
            public openResurgenceSettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/h/index.xhtml', { title: '優先順位の設定', dialogClass: 'no-close' });
            }

            // 60H超休
            public open60hOvertimeSettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/j/index.xhtml', { title: '優先順位の設定', dialogClass: 'no-close' });
            }

            // 看護介護休暇
            public openNursingCareLeaveSettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/l/index.xhtml', { title: '優先順位の設定', dialogClass: 'no-close' });
            }
        }
    }
}