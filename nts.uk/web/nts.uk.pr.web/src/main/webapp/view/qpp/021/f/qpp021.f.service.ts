module nts.uk.pr.view.qpp021.f {

    export module service {
         var paths: any = {
            findRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/two/find",
            saveRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/two/save"
        };

        //connection service find
        export function findRefundPadding(): JQueryPromise<model.RefundPaddingTwoDto> {
            //call service server
            return nts.uk.request.ajax(paths.findRefundPadding);
        }
        
        //connection service save
        export function saveRefundPadding(dto: model.RefundPaddingTwoDto): JQueryPromise<void> {
            //call service server
            var data = { dto: dto };
            return nts.uk.request.ajax(paths.saveRefundPadding, data);
        }


        export module model {
            export class RefundPaddingTwoDto {
                /** The upper area padding top. */
                upperAreaPaddingTop: number;

                /** The under area padding top. */
                underAreaPaddingTop: number;

                /** The padding left. */
                paddingLeft: number;

                /** The break line margin. */
                breakLineMargin: number;

                /** The is show break line. */
                isShowBreakLine: number;
            }

        }

    }

}