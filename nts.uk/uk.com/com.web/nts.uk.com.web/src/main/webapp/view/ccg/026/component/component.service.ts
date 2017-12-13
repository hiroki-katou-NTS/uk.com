module nts.uk.com.view.ccg026.a.component {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getListOfDescriptionWorkPlacePermission: "ctx/sys/auth/role/getListOfDescriptionWorkPlacePermissionByRoleId/{0}",
            getListOfAvialabilityWorkPlacePermission: "ctx/sys/auth/role/getListOfAvialabilityWorkPlacePermissionByRoleId/{0}"
        }
        /**
         * get getListOfFunctionPermission by roleId
         */
        export function getListOfDescriptionFunctionPermission(roleId : string, classification : number): JQueryPromise<Array<any>> {
            
            switch (classification) {
            
            case 1: // workplace
                return ajax("com", format(paths.getListOfDescriptionWorkPlacePermission, roleId));
            default:
                let dfd = $.Deferred();
            
                dfd.resolve([]);
                return dfd.promise();
            }

        }
        export function getListOfAviabilityFunctionPermission(roleId : string, classification : number): JQueryPromise<Array<any>> {
            switch (classification) {
            case 1: // workplace
                return ajax("com", format(paths.getListOfAvialabilityWorkPlacePermission, roleId));
            default:
                break;
            }
            return [];
        }
        
    }
}
