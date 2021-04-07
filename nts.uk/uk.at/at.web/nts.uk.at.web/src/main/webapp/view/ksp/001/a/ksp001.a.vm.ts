module nts.uk.at.view.ksp001.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import jump = nts.uk.request.jump;

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

        openB() {
            jump("/view/ksp/001/b/index.xhtml");
        }

        openD() {
            jump("/view/ksp/001/d/index.xhtml");
        }

        openKDW008() {
            jump("at", "/view/kdw/008/d/index.xhtml", { ShareObject: true, isMobile: true });
        }

    }
}
