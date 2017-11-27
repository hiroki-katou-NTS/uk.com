module nts.uk.com.view.cas014.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        export class Service {
            paths = {
                getAllRoleSet: "ctx/sys/auth/grant/rolesetperson/allroleset",
                getAllRoleSetPerson: "ctx/sys/auth/grant/rolesetperson/selectroleset/{0}",
                registerData: "ctx/sys/auth/grant/rolesetperson/register", 
                getEmpInfo: "ctx/sys/auth/grant/rolesetperson/getempinfo/{0}",
                deleteData: "ctx/sys/auth/grant/rolesetperson/delete"
            }
            constructor() {

            }

            getAllRoleSet(): JQueryPromise<any> {
                return ajax("com", this.paths.getAllRoleSet);
            };
            
            getAllRoleSetPerson(roleSetCd: string): JQueryPromise<any> {
                let _path = format(this.paths.getAllRoleSetPerson, roleSetCd);
                return nts.uk.request.ajax("com", _path);
            };
            
            getEmployeeInfo(empId: string): JQueryPromise<any> {
                let _path = format(this.paths.getEmpInfo, empId);
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