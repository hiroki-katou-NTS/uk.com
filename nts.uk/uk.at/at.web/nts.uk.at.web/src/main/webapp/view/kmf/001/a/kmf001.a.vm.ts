module nts.uk.at.view.kmf001.a.viewmodel {
    export class ScreenModel {

        constructor() {
            var self = this;

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }


        // 優先順位の設定
        public openKMF001B(): void {
            nts.uk.ui.windows.sub.modal('/view/kmf/001/b/index.xhtml');
        }


        // 年次有給休暇
        public openKMF001C(): void {
            nts.uk.request.jump("/view/kmf/001/c/index.xhtml", {});
        }
        // 積立年休
        public openKMF001D(): void {
            nts.uk.request.jump("/view/kmf/001/d/index.xhtml", {});
        }
        //Screen F, G
        public openKMF001F(): void {
            nts.uk.request.jump("/view/kmf/001/f/index.xhtml", {});
        }
        //Screen H, I
        public openKMF001H(): void {
            nts.uk.request.jump("/view/kmf/001/h/index.xhtml", {});
        }
        // 60H超休
        public openKMF001J(): void {
            nts.uk.request.jump("/view/kmf/001/j/index.xhtml", {});
        }
        // 看護介護休暇
        public openKMF001L(): void {
            nts.uk.request.jump("/view/kmf/001/l/index.xhtml", {});
        }
        //ScreenM
        public openKMF001M(): void {
            nts.uk.request.jump("/view/kmf/001/m/index.xhtml", {});
        }
        
        // Export Excel
        public exportExcel() {
            nts.uk.at.view.kmf001.a.service.exportExcel().done(function(data) {

            }).fail(function(res: any) {
                nts.uk.ui.dialog.alertError(res).then(function() {
                    nts.uk.ui.block.clear();
                });
            }).always(() => {
                block.clear();
            });
        }

    }
}