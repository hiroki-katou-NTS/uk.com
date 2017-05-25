module nts.uk.pr.view.qpp021.b {
    export module service {

        var paths = {
            printService: "/file/paymentdata/print",
            paymentReportPrint: "screen/pr/QPP021/saveAsPdf",
            getPaydayProcessing: "/pr/core/paydayprocessing/getbyccdanddisatr1",
            getLineItemLayout: "pr/proto/layout/findHeadAndHistByYM/{0}",
            
        };

        export function getPaydayProcessing(): JQueryPromise<any> {
            return nts.uk.request.ajax("pr", paths.getPaydayProcessing, "0001");
        }
        
        export function getLineItemLayout(baseYM: number): JQueryPromise<any> {
            let path_ = nts.uk.text.format(paths.getLineItemLayout, baseYM);
            return nts.uk.request.ajax("pr", path_);
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