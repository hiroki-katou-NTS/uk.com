module nts.uk.com.view.cas005.a {
    export module service {
        var paths = {
            getListWebMenu : "ctx/sys/auth/webmenu/getlistwebmenu",
            getAllWorkPlaceFunction :"at/auth/workplace/wplmanagementauthority/WorkPlaceFunction/getlistworkplacefunction",
            getAllWorkPlaceAuthority : "at/auth/workplace/wplmanagementauthority/WorkPlaceAuthority/getallWorkplaceauthority",
            addRoleCas005 :"screen/sys/auth/cas005/addrolecas005",
            updateRoleCas005 :"screen/sys/auth/cas005/updaterolecas005",
            deleteRoleCas005 :"screen/sys/auth/cas005/deleterolecas005",
            
        }
        
        //get list web menu 
        export function getListWebMenu() : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getListWebMenu);
        }
        //get list WorkPlace Function
        export function getAllWorkPlaceFunction() : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getAllWorkPlaceFunction);
        }
        
        //get list WorkPlace Authority
        export function getAllWorkPlaceAuthority() : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getAllWorkPlaceAuthority);
        }
        
        //Add role cas005
        export function addRoleCas005(command : any) : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.addRoleCas005,command);
        }
        //update role cas005
        export function updateRoleCas005(command : any) : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.updateRoleCas005,command);
        }
        //delete role cas005
        export function deleteRoleCas005(command : any) : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.deleteRoleCas005,command);
        }
        
        
        
    }
}
    