module cps001.e.MainScreen.vm {
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    import subModal = nts.uk.ui.windows.sub.modal;
    import jump = nts.uk.request.jump;

    export class ViewModel {
        imageId: KnockoutObservable<string> = ko.observable("");
        sid: KnockoutObservable<string> = ko.observable("");

        constructor() {;
        }

       

        OpenFModal() {

            let self = this;
            self.sid("000426a2-181b-4c7f-abc8-6fff9f4f983a");
            setShared("employeeId", self.sid());
            subModal('/view/cps/001/e/index.xhtml', { title: '' }).onClosed(function(): any {
                 self.imageId(getShared("imageId"));
            });
        }

       
    }

}