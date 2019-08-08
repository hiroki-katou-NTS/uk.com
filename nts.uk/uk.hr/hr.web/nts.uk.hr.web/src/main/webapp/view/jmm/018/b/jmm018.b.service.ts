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
    
    /**
     * Model define.
     */
    export module model {
        export class SystemResourceDto {
            resourceId: string;
            resourceContent: string;
            
            constructor(id: string, content: string){
                this.resourceId = id;
                this.resourceContent = content;
            }
        }
        
        export class SystemResourceSaveCommand {
            listData: Array<model.SystemResourceDto>;

            constructor(data: Array<model.SystemResourceDto>){
               this.listData = data;
            }
        }
    }
    
}