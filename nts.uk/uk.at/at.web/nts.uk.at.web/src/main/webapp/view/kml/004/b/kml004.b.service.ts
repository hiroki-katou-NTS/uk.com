module nts.uk.at.view.kmk004.b.service {
        /**
         *  Service paths
         */
        var paths: any = {
            findAll: "ctx/at/share/vacation/setting/annualpaidleave/find/setting"
        }

        export function getAll(){   
            return nts.uk.request.ajax(paths.findAll); 
        }
        
}
