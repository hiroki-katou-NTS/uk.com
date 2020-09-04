module nts.uk.at.view.kdl023.a.viewmodel {

    import BaseScreenModel = kdl023.base.viewmodel.BaseScreenModel;
    import service = nts.uk.at.view.kdl023.base.service;
    import MonthlyPatternRegisterCommand = nts.uk.at.view.kdl023.base.service.MonthlyPatternRegisterCommand;
    import WorkMonthlySetting = nts.uk.at.view.kdl023.base.service.WorkMonthlySetting;

    export class ScreenModel extends BaseScreenModel {
        isOverWrite: KnockoutObservable<boolean>;
        workMonthlySetting: KnockoutObservableArray<WorkMonthlySetting>;

        constructor(isOverWrite: KnockoutObservable<boolean>){
            super();
            let self = this;
            self.isOverWrite(isOverWrite());
        }

        public decide(): void {
            let self = this;
            let param : MonthlyPatternRegisterCommand = {
                isOverWrite : self.isOverWrite(),
                workMonthlySetting: null
            }
            // If calendar's setting is empty.
            if (self.isOptionDatesEmpty()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_512" });
                return;
            }

            if (self.isMasterDataUnregisterd()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_340" }).then(() => {
                    self.closeDialog();
                });
            } else {
                service.registerMonthlyPattern(param).done(() => {
                    nts.uk.ui.windows.setShared('returnedData', ko.toJS(self.reflectionSetting));
                    self.closeDialog();
                }).fail(() => {
                    self.closeDialog();
                });

            }
        }
    }
}