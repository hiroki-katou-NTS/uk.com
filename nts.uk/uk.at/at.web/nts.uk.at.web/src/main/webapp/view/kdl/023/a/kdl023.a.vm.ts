module nts.uk.at.view.kdl023.a.viewmodel {

    import BaseScreenModel = kdl023.base.viewmodel.BaseScreenModel;
    import service = nts.uk.at.view.kdl023.base.service;
    import MonthlyPatternRegisterCommand = nts.uk.at.view.kdl023.base.service.MonthlyPatternRegisterCommand;

    export class ScreenModel extends BaseScreenModel {


        public decide(): void {
            let self = this;
            let param : MonthlyPatternRegisterCommand = {

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

                });

            }
        }
    }
}