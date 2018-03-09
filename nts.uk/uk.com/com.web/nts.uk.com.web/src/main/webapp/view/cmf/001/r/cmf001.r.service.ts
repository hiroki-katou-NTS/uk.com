module nts.uk.com.view.cmf001.r.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getLogResults: "exio/exi/execlog/getLogResults/{0}",
        getErrorLogs:  "exio/exi/execlog/getErrorLogs/{0}",
        exportDatatoCsv: "exio/exi/execlog/export",
    }

    /**
    * ドメインモデル「外部受入実行結果ログ」を取得する
    */
    export function getLogResults(imexProcessID: string) {
        let _path = format(paths.getLogResults, imexProcessID);
        return nts.uk.request.ajax("com", _path);
    }

    /**
    * ドメインモデル「外部受入エラーログ」を取得する
    */
    export function getErrorLogs(imexProcessID: string) {
        let _path = format(paths.getErrorLogs, imexProcessID);
        return nts.uk.request.ajax("com", _path);
    }
    
    /**
     * download export file
     */
    export function exportDatatoCsv(data : Model.ErrorContentDto[]): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportDatatoCsv, data);
    }
    
    export module Model {
        export interface ResultLog {
            externalProcessIdName: string;
            processStartDatetime: string;
            targetCount: string;
            errorCount: string;
            normalCount: string;
        }
        export interface ErrorLog {
            recordNumber: string;
            csvErrorItemName: string;
            itemName: string;
            itemValue: string;
            errorContents: string;
        }
        export interface ErrorContentDto {
            resultLog: ResultLog;
            errorLog: ErrorLog[];
        }
    }
}