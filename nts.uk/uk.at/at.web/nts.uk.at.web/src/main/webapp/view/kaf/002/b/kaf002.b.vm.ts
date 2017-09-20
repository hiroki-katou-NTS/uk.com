module nts.uk.at.view.kaf002.b {
    import kaf002 = nts.uk.at.view.kaf002;
    export module viewmodel {
        let __viewContext: any = window["__viewContext"] || {};
        export class ScreenModel {
            cm = new kaf002.cm.viewmodel.ScreenModel();
            kaf000_a2 = new kaf000.a.viewmodel.ScreenModel();
            constructor() {
                var self = this;
                // self.kaf000_a2.start();
            }

            register() {
                var self = this;
                self.cm.register();
            }
        }
    }
}