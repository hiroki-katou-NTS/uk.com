module nts.uk.at.view.kml002.f.service {
    /**
     *  Service paths
     */
   var paths: any = {
            findByAtr: "at/schedule/budget/external/findByAtr"
        }
    
    export function getByAtr(param){   
                return nts.uk.request.ajax(paths.findByAtr, param);  
            }
}
    

