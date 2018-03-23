module nts.uk.com.view.cps001.g {
       
    export module service {
        
        /**
         *  Service paths
         */
        var servicePath: any = {
            getAllList: "bs/employee/temporaryabsence/frame/findTempAbsenceFrameByCId",
        };
        
        /**
         * findAllSequenceMaster
         */
        export function getAllList(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllList);
        }
    }
}
