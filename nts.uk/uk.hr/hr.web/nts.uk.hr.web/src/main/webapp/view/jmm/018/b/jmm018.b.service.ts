module nts.uk.com.view.jmm018.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findEventMenu: "hrdeveventmenu/eventmenuoperation/getAll",
                save: "screen/com/systemresource/save",
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
        export function saveSysResourceSetting(data: model.SystemResourceSaveCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(path.save, data);
        }
    }
    
}