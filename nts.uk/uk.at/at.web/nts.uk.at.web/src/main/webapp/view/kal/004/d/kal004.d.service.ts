module nts.uk.com.view.kal004.d.service {
    var paths: any = {
        searchUser : "ctx/sys/auth/user/findByKey",
    }

    export function searchUser(key: string, Special: boolean, Multi: boolean): JQueryPromise<any> {
        var userKeyDto = {
                    key: key,
                    Special: Special,
                    Multi: Multi
                };
        return nts.uk.request.ajax("com", paths.searchUser, userKeyDto);
    }



}