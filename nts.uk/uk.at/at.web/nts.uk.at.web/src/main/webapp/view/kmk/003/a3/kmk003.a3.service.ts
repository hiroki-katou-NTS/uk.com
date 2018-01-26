module nts.uk.at.view.kmk003.a3 {
    export module service {

        /**
         *  Service paths
         */
        let servicePath: any = {
            findAllOvertimeWorkFrame: "at/shared/overtimeworkframe/findAll"
        };

        /**
         * function find all overtime work frame
         */
        export function findAllOvertimeWorkFrame(): JQueryPromise<model.OvertimeWorkFrameFindDto[]> {
            return nts.uk.request.ajax(servicePath.findAllOvertimeWorkFrame);
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
