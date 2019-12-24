module nts.uk.pr.view.qmm013.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        
        import exportFile = nts.uk.request.exportFile;
        let paths = {
            getUnitPriceData: "ctx/pr/core/wageprovision/unitpricename/getUnitPriceData/{0}/{1}",
            getAllUnitPriceName: "ctx/pr/core/wageprovision/unitpricename/getAllUnitPriceName/{0}",
            registerUnitPriceData: "ctx/pr/core/wageprovision/unitpricename/registerUnitPriceData",
            removeUnitPriceData: "ctx/pr/core/wageprovision/unitpricename/removeUnitPriceData",
            exportExcel: "file/core/wageprovision/salaryperunit/exportExcel"
        }

        export function getUnitPriceData(cid: string, code: string): JQueryPromise<any> {
            let _path = format(paths.getUnitPriceData, cid, code);
            return ajax('pr', _path);
        }

        export function getAllUnitPriceName(isdisplayAbolition: boolean): JQueryPromise<any> {
            let _path = format(paths.getAllUnitPriceName, isdisplayAbolition);
            return ajax('pr', _path);
        }
        
        export function registerUnitPriceData(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.registerUnitPriceData, command);
        }
        
        export function removeUnitPriceData(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.removeUnitPriceData, command);
        }
        export function exportExcel(): JQueryPromise<any> {
            let _path = format(paths.exportExcel);
            return exportFile(_path);
        }

    }
}
