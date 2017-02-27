module qmm005.a.services {
    let path: any = {
        getString: "pr/core/paydayprocessing/getstring"
    };
    
    export function getString() {
        var dfd = $.Deferred<any>();
        var a = nts.uk.request.ajax(path.getString, { name: 'Vuong' })
            .done(function(res) {
                console.log(res);
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            });
    }
}