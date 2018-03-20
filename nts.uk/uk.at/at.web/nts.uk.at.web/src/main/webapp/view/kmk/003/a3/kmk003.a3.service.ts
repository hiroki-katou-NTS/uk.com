module nts.uk.at.view.kmk003.a3 {
    export module service {

        /**
         *  Service paths
         */
        let servicePath: any = {
            findAllUsedOvertimeWorkFrame: "at/shared/overtimeworkframe/findall/used"
        };

        /**
         * function find all overtime work frame
         */
        export function findAllUsedOvertimeWorkFrame(): JQueryPromise<model.OvertimeWorkFrameFindDto[]> {
            return nts.uk.request.ajax(servicePath.findAllUsedOvertimeWorkFrame);
        }

        /**
         * Data Model
         */
        export module model {
            export interface OvertimeWorkFrameFindDto {
                overtimeWorkFrNo: number;
                overtimeWorkFrName: string;
                transferFrName: string;
                useAtr: number;
            }
        }
    }
}
