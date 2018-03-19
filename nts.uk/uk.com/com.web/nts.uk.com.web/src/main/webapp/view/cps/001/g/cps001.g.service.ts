module nts.uk.com.view.cps001.g {
       
    export module service {
        import format = nts.uk.text.format;
        import ajax = nts.uk.request.ajax;
        /**
         *  Service paths
         */
        var servicePath: any = {
            getAllList: "bs/employee/temporaryabsence/frame/getList",
            getDetails: "bs/employee/temporaryabsence/frame/getDetail",
            register: "bs/employee/temporaryabsence/frame/register"
        };
        
        /**
         * findAllSequenceMaster
         */
        export function getAllList(): JQueryPromise<any> {
            return ajax(servicePath.getAllList);
        }
        
        export function getDetail(grantDate: Date) {
            return ajax(servicePath.getDetails,moment.utc(grantDate, "YYYY/MM/DD"));
        }
        
        export function register(command: any) {
            return ajax(servicePath.register, command);
        }
    }
}
