// TreeGrid Node
module qpp014.b.viewmodel {
    export class ScreenModel {
        b_stepList: Array<nts.uk.ui.NtsWizardStep>;
        b_stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;

        constructor() {
            let self = this;
            self.b_stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }
            ];
            self.b_stepSelected = ko.observable({ id: 'step-2', content: '.step-2' });

        }
          
    }
    

};
