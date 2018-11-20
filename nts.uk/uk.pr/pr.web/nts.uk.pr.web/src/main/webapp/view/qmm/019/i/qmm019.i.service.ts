module nts.uk.pr.view.qmm019.i {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getSalIndAmountName: "core/wageprovision/statementlayout/getSalIndAmountName/{0}"
        }

        export function getSalIndAmountName(cateIndicator: number): JQueryPromise<any> {
            let _path = format(paths.getSalIndAmountName, cateIndicator);
            return ajax('pr', _path);
        }
    }
}