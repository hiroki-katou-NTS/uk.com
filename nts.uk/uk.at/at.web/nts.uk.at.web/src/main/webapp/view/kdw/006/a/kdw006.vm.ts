module nts.uk.at.view.kdw006 {
    export module viewmodel {
        export class ScreenModel {
            constructor(dataShare) {
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            openB() {
                nts.uk.request.jump("/view/kdw/006/b/index.xhtml");
            }

            openC() {
                nts.uk.request.jump("/view/kdw/006/c/index.xhtml");
            }

            openD() {
                nts.uk.request.jump("/view/kdw/006/d/index.xhtml");
            }


            open002Setting() {
                let isDaily = true;
                nts.uk.request.jump("/view/kdw/002/a/index.xhtml", { ShareObject: isDaily });
            }

            open002Control() {
                let isDaily = true;
                nts.uk.request.jump("/view/kdw/002/c/index.xhtml", { ShareObject: isDaily });
            }

            open007() {
                let isDaily = 0;
                nts.uk.request.jump("/view/kdw/007/a/index.xhtml", { ShareObject: isDaily });
            }

            open008() {
                let isDaily = true;
                nts.uk.request.jump("/view/kdw/008/d/index.xhtml", { ShareObject: isDaily });
            }

            open006_G() {
                nts.uk.request.jump("/view/kdw/006/g/index.xhtml");
            }

            open002Month() {
                let isDaily = false;
                nts.uk.request.jump("/view/kdw/002/a/index.xhtml", { ShareObject: isDaily });
            }

            open002ControlMonth() {
                let isDaily = false;
                nts.uk.request.jump("/view/kdw/002/c/index.xhtml", { ShareObject: isDaily });
            }

            open007Month() {
                let isDaily = 1;
                nts.uk.request.jump("/view/kdw/007/a/index.xhtml", { ShareObject: isDaily });
            }

            open008Month() {
                let isDaily = false;
                nts.uk.request.jump("/view/kdw/008/d/index.xhtml", { ShareObject: isDaily });
            }

            openKDW002() {
                let isDaily = false;
                nts.uk.request.jump("/view/kdw/002/c/index.xhtml", { ShareObject: isDaily });
            }

        }
    }
}
