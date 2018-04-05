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
                nts.uk.request.jump("/view/kdw/002/a/index.xhtml");
            }

            open002Control() {
                nts.uk.request.jump("/view/kdw/002/b/index.xhtml");
            }
            
            open007() {
                nts.uk.request.jump("/view/kdw/007/a/index.xhtml");
            }
            
             open008() {
                nts.uk.request.jump("/view/kdw/008/a/index.xhtml");
            }

            open006_G() {
                
                nts.uk.request.jump("/view/kdw/006/g/index.xhtml");
            }

            openKDW002(){
                let isDaily = false;
                nts.uk.request.jump("/view/kdw/002/c/index.xhtml",{ShareObject : isDaily });
            }

        }
    }
}
