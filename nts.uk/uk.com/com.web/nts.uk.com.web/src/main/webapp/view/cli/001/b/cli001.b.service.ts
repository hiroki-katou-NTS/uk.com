module cli001.b.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
        findUserBySearchInput: "ctx/sys/gateway/securitypolicy/lockoutdata/findUserBySearchInput",
    }

    export function findByFormSearch(searchInput: any): JQueryPromise<any> {
        return ajax("com", paths.findUserBySearchInput, {value: searchInput});
    };
}
