module nts.uk.pr.view.qpp018.a {
    export module service {

        // Service paths.
        var servicePath = {
            getallInsuranceOffice: "ctx/pr/report/insurance/office/findAll",
            saveAsPdf: "screen/pr/QPP018/saveAsPdf"
        };
        /**
         * get All Insurance Office
         */
        export function getAllInsuranceOffice(): JQueryPromise<model.InsuranceOffice[]> {
            return nts.uk.request.ajax(servicePath.getallInsuranceOffice);
        }
        
        export function saveAsPdf(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveAsPdf);
        }

        export module model {
            export class InsuranceOffice {
                code: string;
                name: string;
            }
        }
    }
}
