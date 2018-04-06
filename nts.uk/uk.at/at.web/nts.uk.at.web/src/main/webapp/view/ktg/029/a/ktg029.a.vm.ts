module nts.uk.at.view.ktg029.a.viewmodel {
    export class ScreenModel {

        
        constructor() {
            var self = this;
            
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            console.log('abc');
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        
        openKAF015Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kaf/015/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openCMM045Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/cmm/045/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDW003Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdw/003/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL033Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/033/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL029Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/029/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL009Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/009/a/index.xhtml').onClosed(function(): any {
            });

        }
        
        openKDL017Dialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/017/a/index.xhtml').onClosed(function(): any {
            });

        }
    }

}

