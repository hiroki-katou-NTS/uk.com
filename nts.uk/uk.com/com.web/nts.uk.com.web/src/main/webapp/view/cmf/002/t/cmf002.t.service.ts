module nts.uk.com.view.cmf002.t.service {
   // import model = cmf002.share.model;
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        checkExistCode: "exio/exi/condset/checkExistCode/{0}/{1}",
    }

    export function checkExistCode(systemType: number, conditionSetCd: string): JQueryPromise<any> {
        let _path = format(paths.checkExistCode, systemType, conditionSetCd);
        return ajax('com', _path);
    };
}
