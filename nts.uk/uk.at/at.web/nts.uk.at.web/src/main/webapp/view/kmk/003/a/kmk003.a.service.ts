module nts.uk.at.view.kmk003.a {
    export module service {

        /**
        *  Service paths
        */
        let servicePath: any = {
            getAllWorktime: "at/shared/worktimeset/findAll",
            getPredByWorkTimeCode: "at/shared/pred/findByWorkTimeCode",
            savePred: "at/shared/pred/save",
        };

        export function findAllWorkTimeSet(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllWorktime);
        }

        export function getPredByWorkTimeCode(workTimeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getPredByWorkTimeCode + "/" + workTimeCode);
        }
        
        export function savePred(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.savePred, data);
        }

        /**
         * Data Model
         */
        export module model {

        }
    }
}
