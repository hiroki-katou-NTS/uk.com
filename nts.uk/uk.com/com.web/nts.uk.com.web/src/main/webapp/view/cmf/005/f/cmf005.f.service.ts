module nts.uk.com.view.cmf005.f {
    
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
        
        var paths = {
            findManagementDel: "ctx/sys/assist/app/findManagementDel/{0}",
            setInterruptDeleting: "ctx/sys/assist/app/setInterruptDeleting",
            deleteManagementDel: "ctx/sys/assist/app/deleteManagementDel"
        }   
    
        /**
         * get Management Deletion
         */
        export function findManagementDel(delId: string): JQueryPromise<any> {
            let _path = format(paths.findManagementDel, delId);
            return ajax('com', _path);
        }
        
        /**
         * delete Management Deletion when interupt/error/done
         */
        export function deleteManagementDel(command: any): JQueryPromise<any> {
            return ajax("com", paths.deleteManagementDel, command);
        }
        
        
        /**
         * update interrupt process
         */
        export function setInterruptDeleting(command: any): JQueryPromise<any> {
            return ajax("com", paths.setInterruptDeleting, command);
        }
    }
}