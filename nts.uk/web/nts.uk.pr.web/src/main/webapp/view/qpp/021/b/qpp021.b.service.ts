module nts.uk.pr.view.qpp021.b {
    export module service {

        var paths = {
            printService: "/file/paymentdata/print",
            paymentReportPrint: "screen/pr/QPP021/saveAsPdf"
        };

        export function printB(query: any): JQueryPromise<any> {
            let dfd = $.Deferred<any>();
            //nts.uk.request.ajax(paths.getComparingPrintSet).done(function(res: any) {
            dfd.resolve();
            //}).fail(function(error: any) {
            //dfd.reject(error);
            // })
            return dfd.promise();
        }


        //print data query
        export function paymentReportPrint(query: model.PaymentReportQueryDto): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.paymentReportPrint, query);
        }

        export module model {
            export class PaymentReportQueryDto {

                /** The processing no. */
                processingNo: number;

                /** The processing YM. */
                processingYM: number;

                /** The select prit types. */
                selectPrintTypes: number;

                /** The specification codes. */
                specificationCodes: number[];

                /** The layout items. */
                layoutItems: number;
            }

        }
    }

}