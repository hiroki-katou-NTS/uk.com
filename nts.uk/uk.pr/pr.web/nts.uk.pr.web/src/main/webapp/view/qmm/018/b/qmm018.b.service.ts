module nts.uk.pr.view.qmm018.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        
        let paths = {
            getStatementItemDataByCategory: "ctx/pr/core/averagewagecalculationset/getStatementItemDataByCategory/{0}"
        }

        export function getStatementItemDataByCategory(categoryAtr: number): JQueryPromise<any> {
            let _path = format(paths.getStatementItemDataByCategory, categoryAtr);
            return ajax('pr', _path);
        }
    }
}
