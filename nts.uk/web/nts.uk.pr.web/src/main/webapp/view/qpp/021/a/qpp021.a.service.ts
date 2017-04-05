module qpp021.a.service {

    var paths = {
        printService: "/file/paymentdata/print",
    };

    export function print(query: any): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        //nts.uk.request.ajax(paths.getComparingPrintSet).done(function(res: any) {
            dfd.resolve();
        //}).fail(function(error: any) {
            //dfd.reject(error);
       // })
        return dfd.promise();
    }
}
