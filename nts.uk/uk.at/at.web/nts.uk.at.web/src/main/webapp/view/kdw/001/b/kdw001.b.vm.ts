module nts.uk.at.view.kdw001.b {
    export module viewmodel {
        export class ScreenModel {
            //Declare for help button properties
            enable1: KnockoutObservable<boolean>;
            
            //Declare for switch button properties
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            
            //Declare checkbox properties
            checked: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;

            //Declare wizard properties
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;
            activeStep: KnockoutObservable<number>;

            constructor() {
                var self = this;
                //Init for help button
                 self.enable1 = ko.observable(true);
                
                //Init for switch button
                self.roundingRules = ko.observableArray([
                    { code: '1', name: '通常作成' },
                    { code: '2', name: '再作成' }
                ]);
                self.selectedRuleCode = ko.observable(1);                
                
                //Init checkbox properties
                self.checked = ko.observable(true);
                self.enable = ko.observable(true);
                
                //Init wizard
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' }
                ];
                self.activeStep = ko.observable(0);
                self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
            }

            navigateView() {

            }
        }
    }
}
