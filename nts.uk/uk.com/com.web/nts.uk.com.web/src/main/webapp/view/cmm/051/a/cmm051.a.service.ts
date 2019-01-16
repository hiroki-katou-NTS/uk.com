module nts.uk.com.view.cmm051.a {
    export module service {
        var paths: any = {
            findAllWkpManager: "at/auth/workplace/manager/findAll/",
            saveWkpManager: "at/auth/workplace/manager/save/",
            deleteWkpManager: "at/auth/workplace/manager/remove/",
            getEmpInfo: "ctx/sys/auth/grant/rolesetperson/getempinfo/"
        }
        
        export function saveAsExcel(languageId: string, date: string): JQueryPromise<any> {
            let _params = { domainId: "WorkPlaceSelection", 
                        domainType: "CMM051職場管理者の登録", 
                        languageId: languageId, 
                        reportType: 0,mode : 1 , baseDate : date };
            return nts.uk.request.exportFile('/masterlist/report/print', _params);           
        }

        export function findAllWkpManagerByWkpId(wkpId: string): JQueryPromise<Array<base.WorkplaceManager>> {
            return nts.uk.request.ajax("com", paths.findAllWkpManager + wkpId);
        }

        export function saveWkpManager(command: any) {
            return nts.uk.request.ajax("com", paths.saveWkpManager, command);
        }

        export function deleteWkpManager(command: any) {
            return nts.uk.request.ajax("com", paths.deleteWkpManager, command);
        }

        export function getEmployeeInfo(empId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.getEmpInfo + empId);
        };

    }
}