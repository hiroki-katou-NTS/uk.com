module nts.uk.at.view.kmk013.q {
    export module service {
        let paths: any = {
            findAllRoleOfOpenPeriod: "at/shared/workrecord/monthlyresults/roleopenperiod/find",
            getEnumRoleOfOpenPeriod: "at/shared/workrecord/monthlyresults/roleopenperiod/enum/roleofopenperiod",
            findAllRoleOvertimeWork: "at/shared/workrecord/monthlyresults/roleofovertimework/find",
            getEnumRoleOvertimeWork: "at/shared/workrecord/monthlyresults/roleofovertimework/enum/roleotwork",

            findOvertimeworkframe: "at/shared/overtimeworkframe/findAll",
            findWorkdayoffframe: "at/shared/workdayoffframe/findAll",
            saveRoleOvertimeWork:"at/shared/overtimeworkframe/kmk013/save",
            saveRoleOfOpenPeriod:"at/shared/workdayoffframe/kmk013/save",
        }
        
        export function findAllRoleOfOpenPeriod(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAllRoleOfOpenPeriod);
        }
        
        export function saveRoleOfOpenPeriod(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.saveRoleOfOpenPeriod,obj);
        }
        
        export function getEnumRoleOfOpenPeriod(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getEnumRoleOfOpenPeriod);
        }
        
        export function findAllRoleOvertimeWork(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAllRoleOvertimeWork);
        }
        
        export function saveRoleOvertimeWork(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.saveRoleOvertimeWork,obj);
        }
        
        export function getEnumRoleOvertimeWork(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getEnumRoleOvertimeWork);
        }
        
        
        export function findOvertimeworkframe(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findOvertimeworkframe);
        }
        
        export function findWorkdayoffframe(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findWorkdayoffframe);
        }

    }
}


