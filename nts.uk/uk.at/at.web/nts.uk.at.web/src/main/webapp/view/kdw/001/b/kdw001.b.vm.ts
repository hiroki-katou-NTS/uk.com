module nts.uk.at.view.kdw001.b {
    import getText = nts.uk.resource.getText;
    
    export module viewmodel {
        export class ScreenModel {
            //Declare import cScreenmodel, dScreenmodel
            cScreenmodel: any;
            dScreenmodel: any;
            
            //Declare for checkBox area
            cbEnable: KnockoutObservable<boolean>;
            
            //Declare for radio button group
            enable2: KnockoutObservable<boolean>;
            selectedValue2: KnockoutObservable<any>;
            selectedValue3: KnockoutObservable<any>;
            option1: KnockoutObservable<any>;
            option2: KnockoutObservable<any>;

            //Declare for help button properties
            enable1: KnockoutObservable<boolean>;

            //Declare for switch button properties
            roundingRules5: KnockoutObservableArray<any>;
            roundingRules4: KnockoutObservableArray<any>;
            roundingRules3: KnockoutObservableArray<any>;
            roundingRules1: KnockoutObservableArray<any>;
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
                
                //import cScreenModel, dScreenModel
                self.cScreenmodel = new nts.uk.at.view.kdw001.c.viewmodel.ScreenModel();
                self.dScreenmodel = new nts.uk.at.view.kdw001.d.viewmodel.ScreenModel();
                
                //Init for checkBox Area
                self.cbEnable = ko.observable(true);
                
                //Init for radio button group
                self.enable2 = ko.observable(true);
                self.selectedValue2 = ko.observable(false);
                self.selectedValue3 = ko.observable(true);
                self.option1 = ko.observable({ value: 1, text: getText('KDW001_56') });
                self.option2 = ko.observable({ value: 2, text: getText('KDW001_57') });
                
                //Init for help button
                self.enable1 = ko.observable(true);

                //Init for switch button
                self.roundingRules5 = ko.observableArray([
                    { code: '1', name: getText('KDW001_69') },
                    { code: '2', name: getText('KDW001_70') }
                ]);
                self.roundingRules4 = ko.observableArray([
                    { code: '1', name: getText('KDW001_66') },
                    { code: '2', name: getText('KDW001_67') }
                ]);
                self.roundingRules3 = ko.observableArray([
                    { code: '1', name: getText('KDW001_64') },
                    { code: '2', name: getText('KDW001_65') }
                ]);
                self.roundingRules1 = ko.observableArray([
                    { code: '1', name: getText('KDW001_54') },
                    { code: '2', name: getText('KDW001_55') }
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
                
                //Get activeStep value from a screen or c screen
//                __viewContext.transferred.ifPresent(data => {
//                    self.activeStep(data.activeStep);
//                });
                
                //self.stepSelected = ko.observable({ id: 'step-2', content: '.step-2' });
            }
            
            opendScreenD() {
                //nts.uk.request.jump("/view/kdw/001/b/index.xhtml", {"activeStep": 2, "screenName": "B"});
//                let self = this;
//                self.activeStep(2);
                $("#wizard").ntsWizard("next");
            }
            
            opendScreenC() {
                //nts.uk.request.jump("/view/kdw/001/b/index.xhtml", {"activeStep": 0});
//                let self = this;
//                self.activeStep(0);
                $("#wizard").ntsWizard("prev");
            }
            
            navigateView() {
                nts.uk.request.jump("/view/kdw/001/a/index.xhtml");
            }
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}
