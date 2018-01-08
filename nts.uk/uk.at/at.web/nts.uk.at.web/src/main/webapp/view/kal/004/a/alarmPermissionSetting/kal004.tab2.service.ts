module nts.uk.at.view.kal004.tab2.viewmodel {
    export module service {
        var paths = {
            getListRoleByRoleType: "ctx/sys/auth/role/getlistrolebytype"
        }
        
        /** get getListRoleByRoleType by roleType */
        export function getListRoleByRoleType(roleType : any): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("com", paths.getListRoleByRoleType + "/" + roleType);
        }
        
    }
}
