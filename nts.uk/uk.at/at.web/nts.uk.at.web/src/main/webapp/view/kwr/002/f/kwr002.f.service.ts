module nts.uk.com.view.kwr002.f {
    export module service {

        const SLASH = "/";

        const paths: any = {
            findCopy: "com/function/attendancerecord/duplicate/findCopy",
            executeCopy: "com/function/attendancerecord/duplicate/executeCopy",
        }

        export function findCopy(dataCopy: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at",paths.findCopy, dataCopy);
        }

        export function executeCopy(dataCopy: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at",paths.executeCopy, dataCopy);
        }
    }
}