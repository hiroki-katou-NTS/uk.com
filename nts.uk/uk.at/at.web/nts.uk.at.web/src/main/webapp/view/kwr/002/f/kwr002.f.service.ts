module nts.uk.com.view.kwr002.f {
    export module service {

        const SLASH = "/";

        const paths: any = {
            executeCopy: "com/function/attendancerecord/duplicate/executeCopy",

        }

        export function executeCopy(dataCopy: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at",paths.executeCopy, dataCopy);
        }
    }
}