module nts.uk.pr.view.qpp021.g {
    export module service {
        var paths: any = {
            findRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/three/find",
            saveRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/three/save",
            previewRefundPaddingThree: "screen/pr/QPP021/preview/refundpadding/three",
        };

        //connection service find
        export function findRefundPadding(): JQueryPromise<model.RefundPaddingThreeDto> {
            //call service server
            return nts.uk.request.ajax(paths.findRefundPadding);
        }
        
        //connection service save
        export function saveRefundPadding(dto: model.RefundPaddingThreeDto): JQueryPromise<void> {
            //call service server
            var data = { dto: dto };
            return nts.uk.request.ajax(paths.saveRefundPadding, data);
        }

        
        //connection service preview
        export function previewRefundPaddingThree(dto: model.PaymentReportPreviewQueryDto): JQueryPromise<any>{
            //call service server
            return nts.uk.request.exportFile(paths.previewRefundPaddingThree, dto);
        }

        export module model {

            export class RefundPaddingThreeDto {

                /** The upper area padding top. */
                upperAreaPaddingTop: number;

                /** The middle area padding top. */
                middleAreaPaddingTop: number;

                /** The under area padding top. */
                underAreaPaddingTop: number;

                /** The padding left. */
                paddingLeft: number;

                /** The break line margin top. */
                breakLineMarginTop: number;

                /** The break line margin buttom. */
                breakLineMarginButtom: number;

                /** The is show break line. */
                isShowBreakLine: number;
            }
            
             export class PaymentReportPreviewQueryDto {
                /** The page layout. */
                pageLayout: number;

                refundPaddingThreeDto: RefundPaddingThreeDto;
            }
        }
    }
}