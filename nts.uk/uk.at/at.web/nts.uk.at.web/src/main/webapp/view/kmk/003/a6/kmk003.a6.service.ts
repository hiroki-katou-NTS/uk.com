module nts.uk.at.view.kmk003.a6 {
    export module service {

        /**
         *  Service paths
         */
        let servicePath: any = {
//            findAllOvertimeWorkFrame: "at/shared/overtimeworkframe/findAll"
            //update specs ver 7.6
             findAllUsedWorkDayoffFrame: "at/shared/workdayoffframe/findall/used"
        };

        /**
         * function find all overtime work frame
         */
        export function findAllUsedWorkDayoffFrame(): JQueryPromise<model.WorkDayoffFrameFindDto[]> {
            return nts.uk.request.ajax(servicePath.findAllUsedWorkDayoffFrame);
        }

        /**
         * Data Model
         */
        export module model {
            export interface WorkDayoffFrameFindDto {
                //休出枠NO
                workdayoffFrNo: number;
                //使用区分
                useClassification: number;
                //振替枠名称
                transferFrName: string;
                //休出枠名称
                workdayoffFrName: string;
            }
        }
    }
}
