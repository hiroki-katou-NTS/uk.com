module nts.uk.com.view.cmf002.t.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        checkExistCode: "exio/exi/condset/checkExistCode/{0}/{1}",
        excuteCopy: "exio/exo/condset/excuteCopy"
    }

    export function checkExistCode(systemType: number, conditionSetCd: string): JQueryPromise<any> {
        let _path = format(paths.checkExistCode, systemType, conditionSetCd);
        return ajax('com', _path);
    };

    export function excuteCopy(command: any): JQueryPromise<any>{
        return ajax("com", paths.excuteCopy, command);
    };
}
