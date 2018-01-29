module nts.uk.at.view.kbt002.c {
    export module service {
        var paths: any = {
            getEnumDataList: 'at/function/processexec/getEnum',
            getExecSetting: 'at/function/processexec/getExecSetting/',
            saveExecSetting: 'at/function/processexec/saveExecSetting/',
        }
        
        export function getEnumDataList(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getEnumDataList);;
        }
    
        export function getExecSetting(execItemCd: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getExecSetting + execItemCd);
        };
        
        export function saveExecSetting(command: any) {
            return nts.uk.request.ajax("at", paths.saveExecSetting, command);
        }
    }
}