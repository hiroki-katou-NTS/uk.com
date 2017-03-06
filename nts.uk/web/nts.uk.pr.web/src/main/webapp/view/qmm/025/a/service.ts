module qmm025.a.service {
    var paths: any = {
        findAll: "pr/core/rule/law/tax/residential/input/findAll"
    }
    export function findAll(yearKey: number): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.findAll + "/" + yearKey)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}