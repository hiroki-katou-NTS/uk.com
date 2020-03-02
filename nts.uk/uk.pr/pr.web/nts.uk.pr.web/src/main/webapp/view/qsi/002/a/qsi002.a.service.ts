module nts.uk.pr.view.qsi002.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            index: "ctx/pr/report/printconfig/insurenamechangenoti/index",
            getSocialInsurNotiCreateSetById: "ctx/pr/report/printconfig/insurenamechangenoti/getSocialInsurNotiCreateSetById",
            exportPDF: "ctx/pr/report/printconfig/insurenamechangenoti/exportPDF"
        };

        export function index(data : string){
            return nts.uk.request.ajax(path.index, data);
        }

        export function getSocialInsurNotiCreateSetById(){
            return nts.uk.request.ajax(path.getSocialInsurNotiCreateSetById);
        }

        export function exportPDF(data : string){
            return nts.uk.request.exportFile(path.exportPDF, data);
        }


    }
}