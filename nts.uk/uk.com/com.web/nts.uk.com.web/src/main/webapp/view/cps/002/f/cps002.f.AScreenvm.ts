module cps002.f.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;

    export class DemoViewModel {
        constructor() {
            let self = this;
        }

        start() {
            let self = this;
        }
        getPerInfoCtg(){
            let self = this;
            modal("index.xhtml");
        }
    }
}