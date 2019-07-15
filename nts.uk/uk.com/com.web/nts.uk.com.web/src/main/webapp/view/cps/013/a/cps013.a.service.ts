module nts.uk.com.view.cps013.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths: any = {
            checkCategoryHasItem: "ctx/pereg/check/category/checkHasCtg"
    }

    export function checkHasCtg(CheckDataFromUI :any) {
        return ajax(paths.checkCategoryHasItem , CheckDataFromUI);
    }
}