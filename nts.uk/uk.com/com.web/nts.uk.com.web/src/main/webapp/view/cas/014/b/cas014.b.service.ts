module nts.uk.com.view.cas014.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        export class Service {
            paths = {
                getAllRoleSet: "ctx/sys/auth/grant/rolesetperson/allroleset",
                getAllRoleSetPerson: "ctx/sys/auth/grant/rolesetperson/selectroleset/{0}",
                registerData: "ctx/sys/auth/grant/rolesetperson/register", 
                deleteData: "ctx/sys/auth/grant/rolesetperson/delete"
            }
            constructor() {

            }

            getAllRoleSet(): JQueryPromise<any> {
                return ajax("com", this.paths.getAllRoleSet);
            };
            
            getAllRoleSetPerson(roleSetCd: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getAllRoleSetPerson, roleSetCd);
                return nts.uk.request.ajax("com", _path);
            };

            registerData(data: any): JQueryPromise<any> {
                return ajax("com", this.paths.registerData, data);
            };
            
            deleteData(data: any): JQueryPromise<any> {
                return ajax("com", this.paths.deleteData, data);
            }

        }
    }
}