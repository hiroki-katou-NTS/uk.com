module cps002.i.Ascreen.vm {
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

        constructor() {;
        }

       

        OpenFModal() {

            let self = this;
            setShared("imageId", self.imageId());
            subModal('/view/cps/002/i/index.xhtml', { title: '' }).onClosed(function(): any {
                self.imageId(getShared("imageId"));
            });
        }

       
    }

}