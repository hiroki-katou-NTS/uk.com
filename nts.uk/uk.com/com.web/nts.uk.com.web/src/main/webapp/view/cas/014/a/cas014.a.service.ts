module nts.uk.com.view.cas014.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            getAllData: "ctx/sys/auth/grant/rolesetjob/start",
            registerData: "ctx/sys/auth/grant/rolesetjob/register",
            saveAsExcel_tab1: "file/at/personrole/saveAsExcel"
        }

        export function getAllData(refDate: any): JQueryPromise<any> {
            return ajax("com", paths.getAllData, { refDate: refDate });
        };

        export function registerData(data: any): JQueryPromise<any> {
            return ajax("com", paths.registerData, data);
        };
        
        export function saveAsExcel_tab1(languageId: string, date: string): JQueryPromise<any> {
                return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "JobInfo", domainType: "勤務種類の登録", languageId: languageId, reportType: 0, option: date});
            }

    }
}
