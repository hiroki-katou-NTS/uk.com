module nts.uk.at.view.kmk012.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "ctx/at/share/vacation/setting/annualpaidleave/startsetdailyperform/save",
            findByCid: "ctx/at/share/vacation/setting/annualpaidleave/startsetdailyperform/find",            
        };
        
        export function save(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save, command);
        }
        
        export function findByCid(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findByCid);
        }  
    }
}