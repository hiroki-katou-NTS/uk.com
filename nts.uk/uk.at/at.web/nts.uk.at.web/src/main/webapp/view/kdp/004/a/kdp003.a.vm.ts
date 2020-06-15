module kdp003.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    import format = nts.uk.text.format;
    import subModal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import jump = nts.uk.request.jump;

    const __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {
        
        administratorMode  = false;

        constructor() {
            let self = this;
            console.log('constructor');
        }
        
        start(infoAdministrator): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            console.log('start ' + infoAdministrator);
            if (infoAdministrator) {
                self.administratorMode = true;
            } else {
                self.administratorMode = false;
                subModal('/view/kdp/004/k/index.xhtml', { title: '' }).onClosed(() => {
                    dfd.resolve();
                });
            }

            return dfd.promise();
        }

        newMode() {
            let self = this;
        }

    }

    class Layout {

        constructor() {
            let self = this;
        }
    }

    export interface IPersonAuth {
        functionNo: number;
        functionName: string;
        available: boolean;
    }

    export enum FunctionNo {
        No1_Allow_DelEmp = 1, 
        No2_Allow_UploadAva = 2 
    }



}