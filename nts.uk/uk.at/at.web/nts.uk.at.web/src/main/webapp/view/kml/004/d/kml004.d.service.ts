module nts.uk.at.view.kmk004.d.service {
        /**
         *  Service paths
         */
        var paths: any = {
            findAll: "ctx/at/schedule/shift/totaltimes/getallitem"
        }

        export function getAll(){   
            return nts.uk.request.ajax(paths.findAll); 
        }
        
}
