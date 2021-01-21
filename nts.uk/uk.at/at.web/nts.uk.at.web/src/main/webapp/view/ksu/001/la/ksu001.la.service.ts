module nts.uk.at.view.ksu001.la {
    export module service {
        var paths = {
            findAll: "ctx/at/schedule/scheduleteam/findByWKPGRPID",
            findDetail:"ctx/at/schedule/scheduleteam/findDetail",
            register:"ctx/at/schedule/scheduleteam/register",
            update:"ctx/at/schedule/scheduleteam/update",
            remove:"ctx/at/schedule/scheduleteam/delete",
            findWorkplaceGroup:"bs/schedule/employeeinfo/workplacegroup/workplacegroupemployee",
            findEmpOrgInfo: "screen/at/schedule/scheduleteam/empOrgInfo",            
        }

        /** Get an employee's workplace group  */
        export function findWorkplaceGroup(date: any):JQueryPromise<any>{
            return nts.uk.request.ajax("com", paths.findWorkplaceGroup, date);
        }

        export function findAll(WKPGRPID: string): JQueryPromise<any>{
            return nts.uk.request.ajax("at", paths.findAll + "/" + WKPGRPID);
        }

        export function findDetail(id: string, teamCode: string): JQueryPromise<any>{
            return nts.uk.request.ajax("at", paths.findDetail + "/" + id + "/" +teamCode);
        }      

        export function register(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.register, param);
        }

        export function update(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.update, param);
        }

        export function remove(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.remove, param);
        }

        export function findEmpOrgInfo(param: any): JQueryPromise<any>{
            return nts.uk.request.ajax("at", paths.findEmpOrgInfo, param);
        }     
    }  
}