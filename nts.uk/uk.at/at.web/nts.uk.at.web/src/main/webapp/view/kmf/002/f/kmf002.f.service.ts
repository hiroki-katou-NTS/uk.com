module nts.uk.at.view.kmf002.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "at/shared/publicholidaymanagementusageunit/save",
            findAll: "at/shared/publicholidaymanagementusageunit/find",            
        };
        
        export function saveManageUnit(isManageEmployeePublicHd: number, isManageWkpPublicHd: number, isManageEmpPublicHd: number): JQueryPromise<any> {
          let data: any = {};
          data.isManageEmployeePublicHd = isManageEmployeePublicHd; 
          data.isManageWkpPublicHd = isManageWkpPublicHd;
          data.isManageEmpPublicHd = isManageEmpPublicHd;
          return nts.uk.request.ajax("at", path.save, data);
        }
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }  
    }
}