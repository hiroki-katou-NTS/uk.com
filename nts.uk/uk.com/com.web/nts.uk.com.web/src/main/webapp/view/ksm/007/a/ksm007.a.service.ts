module nts.uk.at.view.ksm007.a {
    export module service {
        let rootPath = "bs/schedule/employeeinfo/workplacegroup";
        var paths = {
            exportExcel: "com/employee/workplace/group/workplaceReport",
            getWorkplaceInfo: rootPath + "/getWorkplaceInfo",
            getWorkplaceGroup: rootPath + "/getWorkplaceGroup",
            getListWorkplaceId: rootPath + "/getListWorkplaceId",
            registerWorkplaceGroup: rootPath + "/registerWorkplaceGroup",
            updateWorkplaceGroup: rootPath + "/updateWorkplaceGroup",
            deleteWorkplaceGroup: rootPath + "/deleteWorkplaceGroup"
        }
        
        export function exportExcel(): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportExcel);
        }

        export function getWorkplaceInfo(data): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getWorkplaceInfo, data);
        }

        export function getWorkplaceGroupInfo(wkpId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getWorkplaceGroup + "/" + wkpId);
        }
        
        export function getWorkplaceByGroup(wkpId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListWorkplaceId + "/" + wkpId);
        }

        export function registerWorkplaceGroup(data): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.registerWorkplaceGroup, data);
        }

        export function updateWorkplaceGroup(data): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateWorkplaceGroup, data);
        }

        export function deleteWorkplaceGroup(data): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.deleteWorkplaceGroup, data);
        }
        
    }

}