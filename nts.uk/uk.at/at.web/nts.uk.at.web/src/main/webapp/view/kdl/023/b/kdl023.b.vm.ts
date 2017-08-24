module nts.uk.at.view.kdl023.b.viewmodel {

    import BaseScreenModel = kdl023.base.viewmodel.BaseScreenModel;

    export class ScreenModel extends BaseScreenModel {

        /**
         * Event onclick button Decide.
         */
        public decide(): void {
            let self = this;

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
                nts.uk.ui.windows.setShared('listDateSetting', self.optionDates());
                self.closeDialog();
            }
        }

        /**
         * Get default pattern reflection.
         */
        getDefaultPatternReflection() {
            let self = this;
            return {
                employeeId: 'empconfig',
                reflectionMethod: 0, // Overwrite
                patternClassification: 1, // Configuration
                statutorySetting: {
                    useClassification: false,
                    workTypeCode: self.listWorkType()[0].workTypeCode
                },
                nonStatutorySetting: {
                    useClassification: false,
                    workTypeCode: self.listWorkType()[0].workTypeCode
                },
                holidaySetting: {
                    useClassification: false,
                    workTypeCode: self.listWorkType()[0].workTypeCode
                }
            }
        }

        /**
         * Get domain key
         */
        getDomainKey() {
            return 'empconfig';
        }
    }
}