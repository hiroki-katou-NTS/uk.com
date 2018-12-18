module nts.uk.at.view.kmk006.temp {

    export module service {
        import exportFile = nts.uk.request.exportFile;
        var paths = {
           
        }

        export function exportExcel(languageId: string, domainId, domainType: string, baseDate: any): JQueryPromise<any> {
            return exportFile('/masterlist/report/print', { domainId: domainId, domainType: domainType, languageId: languageId, reportType: 0, data: baseDate });
        }
    }
}