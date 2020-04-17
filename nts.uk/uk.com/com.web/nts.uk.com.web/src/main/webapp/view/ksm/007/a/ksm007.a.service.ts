module nts.uk.at.view.ksm007.a {
    export module service {
        var paths = {
            exportExcel: "com/employee/workplace/group/workplaceReport"
        }
         export function exportExcel(): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportExcel);
        }
        
    }

}