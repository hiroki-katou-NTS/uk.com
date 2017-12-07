module nts.uk.com.view.cas013.a {
	import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        export class Service {
            paths = {
                getAllDataRoleType: "ctx/sys/auth/roletype/getallbyroletype",
                getByRoleId: "ctx/sys/auth/roletype/getrolebyroleId",
                getByUserIdAndRoleId: "ctx/sys/auth/roletype/getrolegrantbyuserId",
            }
            constructor() {

            }
            getAllData(value: any): JQueryPromise<any> {
                return ajax("com", this.paths.getAllDataRoleType, value);
            };
            getByRoleId(roleId: string): JQueryPromise<any> {
                return ajax("com", this.paths.getByRoleId, roleId);
            };
            getByUserIdAndRoleId(roleId: string, userId: string): JQueryPromise<any> {
                var data = {
                    roleId: roleId,
                    userId: userId
                };
                return ajax("com", this.paths.getByUserIdAndRoleId, data);
            };
            
            

        }
    }
}