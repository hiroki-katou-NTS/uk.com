module nts.uk.at.view.kdl023.a.viewmodel {

    import BaseScreenModel = kdl023.base.viewmodel.BaseScreenModel;

    export class ScreenModel extends BaseScreenModel {
        /**
         * Get default pattern reflection.
         */
        getDefaultPatternReflection() {
            let self = this;
            return {
                employeeId: 'empconfirm',
                reflectionMethod: 0, // Overwrite
                patternClassification: 1, // Confirmation
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
            return 'empconfirm';
        }
    }
}