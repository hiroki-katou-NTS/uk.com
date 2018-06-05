module nts.uk.at.view.kmk002.b {
    export module service {

        /**
         *  Service paths
         */
        let paths: any = {
            findEmpCondition: 'ctx/at/record/optionalitem/empcondition/find',
            saveEmpCondition: 'ctx/at/record/optionalitem/empcondition/save',
        }

        /**
         * Call service to get employment condition
         */
        export function find(itemNo: string): JQueryPromise<model.EmpConditionDto> {
            return nts.uk.request.ajax(paths.findEmpCondition + '/' + itemNo);
        }

        /**
         * Call service to save employment condition
         */
        export function save(command: model.EmpConditionDto): JQueryPromise<model.EmpConditionDto> {
            return nts.uk.request.ajax(paths.saveEmpCondition, command);
        }

        /**
         * Data model
         */
        export module model {
            export interface EmpConditionDto {
                optionalItemNo: number;
                empConditions: Array<ConditionDto>;
            }
            export interface ConditionDto {
                empCd: string;
                empName: string;
                empApplicableAtr: number;
            }
        }

    }
}