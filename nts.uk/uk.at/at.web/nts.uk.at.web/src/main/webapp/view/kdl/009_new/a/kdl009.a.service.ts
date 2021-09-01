module nts.uk.at.view.kdl009.a {
  export module service {
        /**
         *  Service paths
         */
		var servicePath: any = {
			getStartHolidayConf: "at/request/employment/getStartHolidayConf"
		};
		
		export function getStartHolidayConf(listEmpId : any): JQueryPromise<any> {
            return nts.uk.request.ajax( "at", servicePath.getStartHolidayConf,listEmpId);
        }
	}
}