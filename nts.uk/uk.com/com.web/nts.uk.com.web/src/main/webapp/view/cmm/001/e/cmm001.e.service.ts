module cmm001.e {
    export module service {
        var paths = {
            getAllMasterCopyCategory: "sys/assist/mastercopy/category/getAllMasterCopyCategory",
            exportErrorCsv: "sys/assist/mastercopy/data/export",
        }

        export function getAllMasterCopyCategory() {
            return nts.uk.request.ajax(paths.getAllMasterCopyCategory);
        }

        export function exportErrorCsv(data: model.ErrorContentDto[]): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportErrorCsv, data);
        }

    }
}