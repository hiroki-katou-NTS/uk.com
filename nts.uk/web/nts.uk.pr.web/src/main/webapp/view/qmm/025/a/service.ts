module qmm025.a.service {
    var paths: any = {
        findAll: "pr/core/rule/law/tax/residential/input/findAll",
        getYearKey: "pr/proto/paymentdatemaster/processing/findbylogin",
        remove: "pr/core/rule/law/tax/residential/input/remove"
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

    export function getYearKey(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.getYearKey)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function remove(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.remove, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}