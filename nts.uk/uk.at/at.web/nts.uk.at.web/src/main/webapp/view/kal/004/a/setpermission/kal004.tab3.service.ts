module nts.uk.at.view.kal004.tab3.service {
    var paths: any = {
        getListRoleName: "ctx/sys/auth/role/getrolebylistroleid"
    }
    export function getListRoleName(data): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getListRoleName);
    }


}