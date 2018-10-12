module nts.uk.pr.view.qmm018.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getStatemetItemData: "ctx/pr/core/averagewagecalculationset/getStatemetItemData",
    }
    export function getStatemetItemData(): JQueryPromise<any> {
        var _path = format(paths.getStatemetItemData);
        return ajax('pr', _path);
    };
}
