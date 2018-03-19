module nts.uk.at.view.kdw006 {
    export module viewmodel {
        export class ScreenModel {

            constructor() {
            }

            openB() {
                let self = this;
                self.checkMode();
                nts.uk.request.jump("/view/kdw/006/b/index.xhtml");
            }

            openC() {
                let self = this;
                self.checkMode();
                nts.uk.request.jump("/view/kdw/006/c/index.xhtml");
            }

            openD() {
                let self = this;
                self.checkMode();
                nts.uk.request.jump("/view/kdw/006/d/index.xhtml");
            }

            open002Setting() {
                let self = this;
                self.checkMode();
                nts.uk.request.jump("/view/kdw/002/a/index.xhtml");
            }

            open002Control() {
                let self = this;
                self.checkMode();
                nts.uk.request.jump("/view/kdw/002/b/index.xhtml");
            }
            open007() {
                let self = this;
                self.checkMode();
                nts.uk.request.jump("/view/kdw/007/a/index.xhtml");
            }
            open008() {
                let self = this;
                self.checkMode();
                nts.uk.request.jump("/view/kdw/008/a/index.xhtml");
            }

            // set params
            checkMode() {
                let self = this;
                let mode = $("#sidebar").ntsSideBar("getCurrent");
                if (mode == 1) {
                    nts.uk.ui.windows.setShared('mode', "DAILY");
                } else if (mode == 2) {
                    nts.uk.ui.windows.setShared('mode', "MONTHLY");
                } else {
                    nts.uk.ui.windows.setShared('mode', "COMMON");
                }
            }
        }
    }
}
