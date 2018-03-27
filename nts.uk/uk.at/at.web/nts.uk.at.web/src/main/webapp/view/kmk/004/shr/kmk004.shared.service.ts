module nts.uk.at.view.kmk004.shared.model {
    
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findBeginningMonth: 'basic/company/beginningmonth/find'
        }
        export function getStartMonth(): JQueryPromise<any> {
            return nts.uk.request.ajax('com', servicePath.findBeginningMonth);
        }
    }
}