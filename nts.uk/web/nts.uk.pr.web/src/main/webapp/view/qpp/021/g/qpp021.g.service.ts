module nts.uk.pr.view.qpp021.g {
    export module service {
        var paths: any = {
            findAllEmployee: "basic/organization/employment/findallemployments",
            findContactItemSettings: "ctx/pr/report/payment/contact/item/findSettings",
            saveContactItemSettings: "ctx/pr/report/payment/contact/item/save"

        };


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
        }
    }