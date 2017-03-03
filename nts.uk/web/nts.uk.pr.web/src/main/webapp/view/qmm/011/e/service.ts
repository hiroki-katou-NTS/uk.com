module nts.uk.pr.view.qmm011.e {
    import InsuranceBusinessTypeUpdateDto = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessTypeUpdateDto;
    export module service {
        var paths: any = {
            updateInsuranceBusinessType: "pr/insurance/labor/businesstype/update"
        };
        export function updateInsuranceBusinessType(insuranceBusinessType: InsuranceBusinessTypeUpdateDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { insuranceBusinessType: insuranceBusinessType, companyCode: "CC0001" };
            nts.uk.request.ajax(paths.updateInsuranceBusinessType, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
    }
}