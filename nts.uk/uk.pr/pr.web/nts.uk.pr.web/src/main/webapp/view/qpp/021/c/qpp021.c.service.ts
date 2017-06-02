module qpp021.c.service {

    var paths = {
        printService: "/file/paymentdata/print",
    };

    export function printC(query: any): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        //nts.uk.request.ajax(paths.getComparingPrintSet).done(function(res: any) {
            dfd.resolve();
        //}).fail(function(error: any) {
            //dfd.reject(error);
       // })
        return dfd.promise();
    }
}
