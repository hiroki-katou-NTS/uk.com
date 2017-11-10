module nts.uk.at.view.kdw001.b {
    import getText = nts.uk.resource.getText;

    export module viewmodel {
        export class ScreenModel {
            //Declare import cScreenmodel, dScreenmodel
            cScreenmodel: any;
            dScreenmodel: any;

            //Declare for checkBox area
            calDivisionCheck: KnockoutObservable<boolean>;
            dateDivisionCheck: KnockoutObservable<boolean>;
            reflectEmbossingCheck: KnockoutObservable<boolean>;
            masterInforCheck: KnockoutObservable<boolean>;
            childCareCheck: KnockoutObservable<boolean>;
            closedResetCheck: KnockoutObservable<boolean>;
            workingTimeCheck: KnockoutObservable<boolean>;
            transferTimeCheck: KnockoutObservable<boolean>;
            //enable for checkBox area
            enableAll: KnockoutObservable<boolean>;
            recreateEnable: KnockoutObservable<boolean>;
            forciblyEnable: KnockoutObservable<boolean>;
            //Declare for checkBox 
            dailyCreatedCheck: KnockoutObservable<boolean>;
            dailyCalCheck: KnockoutObservable<boolean>;
            approvalResultCheck: KnockoutObservable<boolean>;
            forciblyCommitCheck: KnockoutObservable<boolean>;
            MonthCountCheck: KnockoutObservable<boolean>;
            //Declare for radio button group
            // enable2: KnockoutObservable<boolean>;
            // selectedValue2: KnockoutObservable<any>;
            //selectedValue3: KnockoutObservable<any>;
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
            selectedCreatDivisionCode: any;
            selectedCalDivisionCode: any
            selectedReflectClassCode: any;
            selectedAggregateClassCode: any;

            //Declare checkbox properties
            checked: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;

            //Declare wizard properties
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;
            activeStep: KnockoutObservable<number>;


            //re-create devision
            itemRecreateDevision: KnockoutObservableArray<any>;
            selectedRecreateDevision: KnockoutObservable<number>;

            //data from screen C
            dataC: any;
            constructor() {
                var self = this;
                self.dataC = nts.uk.ui.windows.getShared("KDW001_C_LISTEMPID_STARTDATE_ENDDATE");
                nts.uk.ui.dialog.alert(self.dataC);

                //import cScreenModel, dScreenModel
                self.cScreenmodel = new nts.uk.at.view.kdw001.c.viewmodel.ScreenModel();
                self.dScreenmodel = new nts.uk.at.view.kdw001.d.viewmodel.ScreenModel();

                //Init for checkBox Area
                self.calDivisionCheck = ko.observable(false);
                self.dateDivisionCheck = ko.observable(false);
                self.reflectEmbossingCheck = ko.observable(false);
                self.masterInforCheck = ko.observable(false);
                self.childCareCheck = ko.observable(false);
                self.closedResetCheck = ko.observable(false);
                self.workingTimeCheck = ko.observable(false);
                self.transferTimeCheck = ko.observable(false);

                //Init checkBox
                self.dailyCreatedCheck = ko.observable(true);
                self.dailyCalCheck = ko.observable(true);
                self.approvalResultCheck = ko.observable(true);
                self.forciblyCommitCheck = ko.observable(false);
                self.MonthCountCheck = ko.observable(true);


                // init enable 
                self.recreateEnable = ko.observable(false);
                self.forciblyEnable = ko.observable(false);




                //Init for radio button group
                //   self.enable2 = ko.observable(true);
                // self.selectedValue2 = ko.observable(false);
                //   self.selectedValue3 = ko.observable(true);
                self.itemRecreateDevision = ko.observableArray([
                    new RecreateDevisionModel(1, getText('KDW001_56')),
                    new RecreateDevisionModel(2, getText('KDW001_57'))
                ]);
                self.selectedRecreateDevision = ko.observable(2);



                //   self.option1 = ko.observable({ value: 1, text: getText('KDW001_56') });
                //   self.option2 = ko.observable({ value: 2, text: getText('KDW001_57') });

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
                self.selectedCreatDivisionCode = ko.observable(1);
                self.selectedCalDivisionCode = ko.observable(1);
                self.selectedReflectClassCode = ko.observable(1);
                self.selectedAggregateClassCode = ko.observable(1);
                // Init enable check box for area
                self.enableAll = ko.observable(false);

                //  *3 
                self.dailyCreatedCheck.subscribe((x) => {
                    //*2
                    if (x == true && self.selectedCreatDivisionCode() == 2) {
                        self.recreateEnable(true);
                    } else {
                        self.recreateEnable(false);
                    }


                    if (x == true && self.selectedCreatDivisionCode() == 2 && self.selectedRecreateDevision() == 2) {
                        self.enableAll(true);
                    } else {
                        self.enableAll(false);
                    }
                });
                self.selectedRecreateDevision.subscribe((x) => {
                    if (x == 2 && self.selectedCreatDivisionCode() == 2 && self.dailyCreatedCheck() == true) {
                        self.enableAll(true);
                    } else {
                        self.enableAll(false);
                    }
                });

                self.selectedCreatDivisionCode.subscribe((x) => {
                    if (self.dailyCreatedCheck() == true == true && x == 2) {
                        self.recreateEnable(true);
                    } else {
                        self.recreateEnable(false);
                    }

                    if (x == 2 && self.selectedRecreateDevision() == 2 && self.dailyCreatedCheck() == true) {
                        self.enableAll(true);
                    } else {
                        self.enableAll(false);
                    }
                });


                //*7

                self.approvalResultCheck.subscribe((x) => {
                    if (x == true && self.selectedReflectClassCode() == 2) {
                        self.forciblyEnable(true);
                    } else {
                        self.forciblyEnable(false);
                    }
                });

                self.selectedReflectClassCode.subscribe((x) => {
                    if (x == 2 && self.approvalResultCheck() == true) {
                        self.forciblyEnable(true);
                    } else {
                        self.forciblyEnable(false);
                    }
                });

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

            }

            opendScreenD() {

                $("#wizard").ntsWizard("next");
            }

            opendScreenC() {
                $("#wizard").ntsWizard("prev");
            }

            navigateView() {
                nts.uk.request.jump("/view/kdw/001/a/index.xhtml");
            }
        }
    }

    class RecreateDevisionModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}
