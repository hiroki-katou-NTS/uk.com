module qpp021.b.viewmodel {
    export class ScreenModel {
        stepList: Array<nts.uk.ui.NtsWizardStep>;
        stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;

        constructor() {
            var self = this;
            self.stepList = [
                { content: '.A_LBL_002-step' },
                { content: '.A_LBL_003-step' },
                { content: '.A_LBL_004-step' },
            ];
            self.stepSelected = ko.observable({ id: 'A_LBL_002-step', content: '.A_LBL_002-step' });
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        next() {
            $('#wizard').ntsWizard("next");
        }

    }
}