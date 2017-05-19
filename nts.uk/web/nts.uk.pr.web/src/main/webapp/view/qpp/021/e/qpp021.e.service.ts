module nts.uk.pr.view.qpp021.e {

    
    export module service {

        var paths: any = {
            findRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/once/find",
            previewRefundPaddingOnce: "screen/pr/QPP021/preview/refundpadding/once",
            saveRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/once/save"
        };

        //connection service find
        export function findRefundPadding(): JQueryPromise<model.RefundPaddingOnceDto> {
            //call service server
            return nts.uk.request.ajax(paths.findRefundPadding);
        }

        //connection service save
        export function saveRefundPadding(dto: model.RefundPaddingOnceDto): JQueryPromise<void> {
            //call service server
            var data = { dto: dto };
            return nts.uk.request.ajax(paths.saveRefundPadding, data);
        }

        // connection preview 
        export function previewRefundPaddingOnce(pageLayout: string): JQueryPromise<any>{
            var data= {pageLayout : pageLayout};
            return nts.uk.request.exportFile(paths.previewRefundPaddingOnce, data);
        }
        export module model {

            export class RefundPaddingOnceDto {
                /** The padding top. */
                paddingTop: number;

                /** The padding left. */
                paddingLeft: number;
            }
            
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
                
                pageOrientation: string;
            }
        }
    }
}