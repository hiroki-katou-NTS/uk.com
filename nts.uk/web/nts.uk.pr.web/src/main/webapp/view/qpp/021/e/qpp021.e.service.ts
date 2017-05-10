module nts.uk.pr.view.qpp021.e {

    export module service {

        var paths: any = {
            findRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/once/find",
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

        export module model {

            export class RefundPaddingOnceDto {
                /** The padding top. */
                paddingTop: number;

                /** The padding left. */
                paddingLeft: number;
            }
        }
    }
}