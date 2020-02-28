module nts.uk.at.view.kbt002.b {
    export module service {
        var paths: any = {
            getEnumDataList: 'at/function/processexec/getEnum',
            findWorkplaceTree: "bs/employee/workplace/config/info/findAll",
            getProcExecList: 'at/function/processexec/getProcExecList',
            saveProcExec: 'at/function/processexec/saveProcExec',
            deleteProcExec: 'at/function/processexec/removeProcExec',
            getAlarmByUser: 'at/function/alarm/kal/001/pattern/setting',
            findWkpTreeNew: 'bs/employee/wkpdep/get-wkpdepinfo-kcp004'
        }
    
        export function findWorkplaceTree(baseDate: Date, systemType : number): JQueryPromise<any> {
            return nts.uk.request.ajax('com', paths.findWkpTreeNew, { 
                startMode: 0,
                baseDate: baseDate, 
                systemType: systemType,
                restrictionOfReferenceRange: true });
        }
        
        export function getEnumDataList(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getEnumDataList);
        }
        
        export function getProcExecList(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getProcExecList);
        }
    
        export function saveProcExec(command: any) {
            return nts.uk.request.ajax("at", paths.saveProcExec, command);
        }
    
        export function deleteProcExec(command: any) {
            return nts.uk.request.ajax("at", paths.deleteProcExec, command);
        }
        export function getAlarmByUser() {
            return nts.uk.request.ajax("at", paths.getAlarmByUser);
        }
//        
//        export function getEmployeeInfo(empId: string): JQueryPromise<any> {
//            return nts.uk.request.ajax("com", paths.getEmpInfo + empId);
//        };
    }
}