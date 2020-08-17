/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr004.test {
    const API = {
        PDF_ALL : "order/report/all/pdf",
        PDF_DETAIL : "order/report/detail/pdf",
        EXCEL : "order/report/print/excel",
        EXCEL_DETAL : "order/report/print/excel-detail",
        exportFile: "bento/report/reservation/month"
    };
    const PATH = {
        REDIRECT : '/view/ccg/008/a/index.xhtml'
    }

    @bean()
    export class KMR004AViewModel extends ko.ViewModel {


        created() {

        }

        mounted(){

        }
        printPDF(){
            let vm = this;
            $("#exportTitle").trigger("validate");
            vm.$blockui("invisible");
            nts.uk.request.exportFile("at", API.PDF_ALL).done(() => {
                vm.$blockui("clear");
            }).fail((res: any) => {
                vm.$dialog.error({ messageId : res.messageId }).then(function(){
                    vm.$blockui("clear");
                });
            });
        }

        printPDFDetail(){
            let vm = this;
            $("#exportTitle").trigger("validate");
            vm.$blockui("invisible");
            nts.uk.request.exportFile("at", API.PDF_DETAIL).done(() => {
                vm.$blockui("clear");
            }).fail((res: any) => {
                vm.$dialog.error({ messageId : res.messageId }).then(function(){
                    vm.$blockui("clear");
                });
            });
        }

        printExcel(){
            let vm = this;
            $("#exportTitle").trigger("validate");
            vm.$blockui("invisible");
            nts.uk.request.exportFile("at", API.EXCEL).done((data) => {
                vm.$blockui("clear");
            }).fail((res: any) => {
                vm.$dialog.error({ messageId : res.messageId }).then(function(){
                    vm.$blockui("clear");
                });
            });
        }

        printExcelDetail(){
            let vm = this;
            $("#exportTitle").trigger("validate");
            vm.$blockui("invisible");
            nts.uk.request.exportFile("at", API.EXCEL_DETAL).done((data) => {
                vm.$blockui("clear");
            }).fail((res: any) => {
                vm.$dialog.error({ messageId : res.messageId }).then(function(){
                    vm.$blockui("clear");
                });
            });
        }

    }
}
