module nts.uk.at.view.kdw006 {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            opendScreenA() {
                nts.uk.request.jump("/view/kdw/002/d/index.xhtml");
            }

            opendScreenB() {
                nts.uk.request.jump("/view/kdw/002/a/index.xhtml");
            }
            
            opendScreenC() {
                nts.uk.request.jump("/view/kdw/007/a/index.xhtml");
            }
            
            opendScreenD() {
                nts.uk.request.jump("/view/kdw/008/d/index.xhtml");
            }
            
            opendOperationSetting() {
                nts.uk.request.jump("/view/kdw/006/b/index.xhtml");
            }
            openKDW002(){
                nts.uk.request.jump("/view/kdw/002/c/index.xhtml");
            }
        }
    }
}
