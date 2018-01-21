module cps003.g.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        constructor() {
            let self = this;
        }

        pushData() {
            let self = this;

            setShared('CPS003G_VALUE', {});
            self.close();
        }

        close() {
            close();
        }
    }

    interface IModelDto {
    }
}