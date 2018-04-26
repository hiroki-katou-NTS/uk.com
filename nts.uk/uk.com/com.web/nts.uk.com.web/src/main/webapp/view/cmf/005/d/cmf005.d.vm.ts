module nts.uk.com.view.cmf005.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

    export class ScreenModel {
        //        linkText: KnockoutObservable<string>;
        stepList: Array<NtsWizardStep>;
        stepSelected: KnockoutObservable<NtsWizardStep>;
        activeStep: KnockoutObservable<number>;

        ccg001ComponentOption: GroupOption;

        constructor() {
            let self = this;
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }];
//            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
            
            todoScreenD();
        }

        todoScreenD() {
            let self = this;
            self.loadScreenD();
            $('#ex_output_wizard').ntsWizard("goto", 2);
        }

        loadScreenD() {
            let self = this;
            // Set component option
            self.ccg001ComponentOption = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: true,
                showAllClosure: true,
                showPeriod: true,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,

                /**
                * Self-defined function: Return data from CCG001
                * @param: data: the data return from CCG001
                */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                }
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);
        }


        //        initComponents() {
        //            //View menu step
        //            var self = this;
        //            self.stepList = [
        //                { content: '.step-1' },
        //                { content: '.step-2' },
        //                { content: '.step-3' }
        //            ];
        //            self.activeStep = ko.observable(1);
        //            self.stepSelected = ko.observable({ id: 'step-2', content: '.step-2' });
        //         }
    }
}