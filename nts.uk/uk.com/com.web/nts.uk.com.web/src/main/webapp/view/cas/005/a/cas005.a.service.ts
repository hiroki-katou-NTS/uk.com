module nts.uk.com.view.cas005.a {
    export module service {
        var paths = {
            getListWebMenu : "ctx/sys/auth/webmenu/getlistwebmenu",
            getAllWorkPlaceFunction :"auth/wplmanagementauthority/workplacefunction/getlistworkplacefunction",
            getAllWorkPlaceAuthority : "auth/wplmanagementauthority/workplaceauthority/getallWorkplaceauthority",
            getAllWorkPlaceAuthorityById : "com/auth/wplmanagementauthority/workplaceauthority/getallWorkplaceauthoritybyid",
            addRoleCas005 :"screen/sys/auth/cas005/addrolecas005",
            updateRoleCas005 :"screen/sys/auth/cas005/updaterolecas005",
            deleteRoleCas005 :"screen/sys/auth/cas005/deleterolecas005",
            getRoleByRoleTiesById :"sys/portal/webmenu/webmenulinking/getrolebyroletiesbyid",
            getEmploymentRoleById : "at/auth/workplace/employmentrole/getemploymentrolebyid",

            // ①<<ScreenQuery>> Webメニューリストを取得する
            findAllMenu : "sys/portal/webmenu/findallmenu",
            // ②<<ScreenQuery>>ロールとWebメニュー紐付けを取得する
            findRoleAndWebMenu : "sys/portal/webmenu/findroleandwebmenu"
        }
        
        //get list web menu 
        export function getListWebMenu() : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getListWebMenu);
        }
        //get list WorkPlace Function
        export function getAllWorkPlaceFunction() : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getAllWorkPlaceFunction);
        }
        
        //get list WorkPlace Authority
        export function getAllWorkPlaceAuthority() : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getAllWorkPlaceAuthority);
        }
        //get list WorkPlace Authority By Id
        export function getAllWorkPlaceAuthorityById(roleId: string) : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getAllWorkPlaceAuthorityById+"/"+roleId);
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
        //getRoleByRoleTiesById
        export function getRoleByRoleTiesById(roleId : string) : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getRoleByRoleTiesById+"/"+roleId);    
        }
        
        //get Employment Role By Id
        export function getEmploymentRoleById(roleId : string) : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getEmploymentRoleById+"/"+roleId);    
        }
        export function exportExcel(): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = "CAS005";
            if (program.length > 1){
                program.shift();
                domainType = domainType + program.join(" ");
            }
            let _params = { domainId: "RoleEmployment", 
                        domainType: domainType,
                        languageId: "ja", 
                        reportType: 0};
            return nts.uk.request.exportFile('/masterlist/report/print',_params);
        }
        // GET LIST
        export function findAllMenu() : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.findAllMenu);
        }  // GET LIST
        export function findRoleAndWebMenu(command : any) : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.findRoleAndWebMenu,command);
        }
    }
}
    