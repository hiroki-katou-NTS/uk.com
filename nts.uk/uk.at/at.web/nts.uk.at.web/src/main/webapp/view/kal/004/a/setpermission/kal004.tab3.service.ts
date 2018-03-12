module nts.uk.at.view.kal004.tab3.service {
    var paths: any = {
        getListRoleName: "ctx/sys/auth/role/get/rolename/by/roleids"
    }
    export function getListRoleName(data :Array<string>): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getListRoleName , data);
    }


}