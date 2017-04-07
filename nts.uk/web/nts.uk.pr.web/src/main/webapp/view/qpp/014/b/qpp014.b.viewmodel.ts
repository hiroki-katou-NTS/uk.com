// TreeGrid Node
module qpp014.b.viewmodel {
    export class ScreenModel {
        viewmodeld = new qpp014.d.viewmodel.ScreenModel();
        viewmodelg = new qpp014.g.viewmodel.ScreenModel();
        viewmodelh = new qpp014.h.viewmodel.ScreenModel();

        b_stepList: Array<nts.uk.ui.NtsWizardStep>;
        b_stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;

        constructor() {
            let self = this;
            self.b_stepList = [
                { content: '.step-1' },
                { content: '.step-2' }
            ];
            self.b_stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        
        /**
         * back to screen J
         */
        goToScreenA(): void {
            nts.uk.request.jump("/view/qpp/014/a/index.xhtml");
        }

        /**
         * go to screen J
         */
        goToScreenJ(): void {
            nts.uk.ui.windows.sub.modal("/view/qpp/014/j/index.xhtml", { title: "振込チェックリスト", dialogClass: "no-close" });
        }
    }


};
