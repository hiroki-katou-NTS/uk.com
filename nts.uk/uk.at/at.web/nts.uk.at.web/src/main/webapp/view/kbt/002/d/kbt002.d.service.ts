module nts.uk.at.view.kbt002.d {
    export module service {
        var paths: any = {
            getEnumDataList: 'at/function/processexec/getEnum',
        }
    
        export function getEnumDataList(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getEnumDataList);;
        }
    }
}