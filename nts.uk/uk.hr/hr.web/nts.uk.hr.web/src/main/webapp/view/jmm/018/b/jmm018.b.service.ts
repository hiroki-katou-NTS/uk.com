module nts.uk.com.view.jmm018.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findEventMenu: "hrdeveventmenu/eventmenuoperation/getAll",
                save: "hrdeveventmenu/eventmenuoperation/update-event-menu",
            };
        
        /**
         * 
         */
        export function findEventMenu(): JQueryPromise<any>{
            return nts.uk.request.ajax(path.findEventMenu);
        }
        
        /**
         * 
         */
        export function saveEventMenu(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.save, data);
        }
    }
    
}