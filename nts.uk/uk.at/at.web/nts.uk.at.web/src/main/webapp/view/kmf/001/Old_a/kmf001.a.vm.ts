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
                $('#annual-paid-leave-setting').focus();
                // resolve.
                dfd.resolve();

                return dfd.promise();
            }

            // Export Excel
            public exportExcel(){
                nts.uk.pr.view.kmf001.a.service.exportExcel().done(function (data) {

                }).fail(function (res: any) {
                    nts.uk.ui.dialog.alertError(res).then(function () {
                        nts.uk.ui.block.clear();
                    });
                }).always(() => {
                    block.clear();
                });
            }

            // 優先順位の設定
            public openPrioritySettingDialog(): void {
                nts.uk.ui.windows.sub.modal('/view/kmf/001/b/index.xhtml');
            }

            // 年次有給休暇
            public openAnnualPaidLeaveSettingPage(): void {
                nts.uk.request.jump("/view/kmf/001/c/index.xhtml", {});
            }

            // 積立年休
            public openYearlyReservedSettingPage(): void {
                nts.uk.request.jump("/view/kmf/001/d/index.xhtml", {});
            }

            // 代休
            public openTemporaryHolidaysSettingPage(): void {
                nts.uk.request.jump("/view/kmf/001/f/index.xhtml", {});
            }

            // 振休
            public openResurgenceSettingPage(): void {
                nts.uk.request.jump("/view/kmf/001/h/index.xhtml", {});
            }

            // 60H超休
            public open60hOvertimeSettingPage(): void {
                nts.uk.request.jump("/view/kmf/001/j/index.xhtml", {});
            }

            // 看護介護休暇
            public openNursingCareLeaveSettingPage(): void {
                nts.uk.request.jump("/view/kmf/001/l/index.xhtml", {});
            }
        }
    }
}