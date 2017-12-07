module nts.uk.com.view.cas013.a {
	import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        export class Service {
            paths = {
                getRoleType: "ctx/sys/auth/grant/roleindividual/getRoleType",
                getRole: "ctx/sys/auth/grant/roleindividual/getRoles",
                getRoleGrants: "ctx/sys/auth/grant/roleindividual/getRoleGrants",
            }
            constructor() {

            }
            getRoleTypes(): JQueryPromise<any> {
                return ajax("com", this.paths.getRoleType);
            };
            getRole(roleType: string): JQueryPromise<any> {
                return ajax("com", this.paths.getRole +'/'+ roleType);
            };
            getRoleGrants(role: string): JQueryPromise<any> {
                return ajax("com", this.paths.getRoleGrants, role);
            };
            getByUserIdAndRoleId(roleId: string, userId: string): JQueryPromise<any> {
                var data = {
                    roleId: roleId,
                    userId: userId
                };
                return ajax("com", this.paths.getRoleGrants, data);
            };
            
            

        }
    }
}