module cps002.e.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;

    export class DemoViewModel {
        cardNoMode: KnockoutObservable<boolean> = ko.observable(false);
        generateEmCode: KnockoutObservable<string> = ko.observable("");
        constructor() {
            let self = this;
        }

        start() {
            let self = this;
        }
        emplCodeModeClick() {
            let self = this;
            self.cardNoMode(false);
            setShared("cardNoMode", self.cardNoMode());
            modal('index.xhtml').onClosed(function(){
                let val = getShared("CPS002_PARAM");
                self.generateEmCode(val);
            });
        }

        cardNoModeClick() {
            let self = this;
            self.cardNoMode(true);
            setShared("cardNoMode", self.cardNoMode());
            modal('index.xhtml').onClosed(function(){
                self.generateEmCode(getShared("CPS002_PARAM"));
            });
        }
    }
}