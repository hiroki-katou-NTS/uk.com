module nts.uk.com.view.cmf002.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        //TODO: Fake ws
        getCndSet: "exio/exo/stdoutconset/getCndSet{0}"
    }

    // Get 出力条件設定（定型）
    export function getCndSet(cid: string ): JQueryPromise<any> {
        return ajax(format(paths.getCndSet, cid));
    }
}