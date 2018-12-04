module nts.uk.pr.view.qmm019.p {
    import ajax = nts.uk.request.ajax;
    import exportFile = nts.uk.request.exportFile;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getCurrentProcessingDate: "core/wageprovision/statementlayout/getCurrentProcessingDate",
            getStatementLayoutByProcessingDate: "core/wageprovision/statementlayout/getStatementLayoutByProcessingDate/{0}",
            exportExcel: "file/core/wageprovision/statementlayout/export",
        }

        export function getCurrentProcessingDate(): JQueryPromise<any> {
            return ajax('pr', paths.getCurrentProcessingDate);
        }

        export function getStatementLayoutByProcessingDate(processingDate: any): JQueryPromise<any> {
            let _path = format(paths.getStatementLayoutByProcessingDate, processingDate);
            return ajax('pr', _path);
        }

        export function exportExcel(dto: any): JQueryPromise<any> {
            return exportFile('pr', paths.exportExcel, dto);
        }
    }
}