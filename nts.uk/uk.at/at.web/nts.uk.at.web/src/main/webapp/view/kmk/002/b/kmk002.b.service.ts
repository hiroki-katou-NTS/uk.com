module nts.uk.at.view.kmk002.b {
    export module service {

        let paths: any = {
            findEmpCondition: 'ctx/at/record/optionalitem/empcondition/find',
            saveEmpCondition: 'ctx/at/record/optionalitem/empcondition/save',
        }

        export function find(itemNo: string): JQueryPromise<model.EmpConditionDto> {
            return nts.uk.request.ajax(paths.findEmpCondition + '/' + itemNo);
        }

        export function save(command: model.EmpConditionDto): JQueryPromise<model.EmpConditionDto> {
            return nts.uk.request.ajax(paths.saveEmpCondition, command);
        }

        export module model {
            export interface EmpConditionDto {
                optionalItemNo: string;
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