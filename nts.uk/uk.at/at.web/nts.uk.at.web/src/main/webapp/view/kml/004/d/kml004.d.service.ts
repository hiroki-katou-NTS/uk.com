module nts.uk.at.view.kml004.d.service {
        /**
         *  Service paths
         */
        var paths: any = {
            findAll: "ctx/at/schedule/shift/totaltimes/getallitem",
            findCNTCode: "at/schedule/schedulehorizontal/findCNT"
        }

        export function getAll(){   
            return nts.uk.request.ajax(paths.findAll); 
        }
        
        export function getCNT(param){   
                return nts.uk.request.ajax(paths.findCNTCode, param);  
            }
}
