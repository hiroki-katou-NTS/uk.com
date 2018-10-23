module nts.uk.pr.view.cmm015.a.service {
    let paths: any = {
        findAllSalaryClsInfo: "pr/core/wageprovision/orginfo/salarycls/salaryclsmaster/findAll",
        getSalaryClsInfo: "pr/core/wageprovision/orginfo/salarycls/salaryclsmaster/get",
        addSalaryClsInfo: "pr/core/wageprovision/orginfo/salarycls/salaryclsmaster/add",
        deleteSalaryClsInfo: "pr/core/wageprovision/orginfo/salarycls/salaryclsmaster/delete",
        updateSalaryClsInfo: "pr/core/wageprovision/orginfo/salarycls/salaryclsmaster/update",
    }

    export function findAllSalaryClsInfo(): JQueryPromise<Array<viewmodel.model.SalaryClsInfoDto>> {
        var dfd = $.Deferred<Array<viewmodel.model.SalaryClsInfoDto>>();
        nts.uk.request.ajax(paths.findAllSalaryClsInfo)
            .done(function (res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function (res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function getSalaryClsInfo(salaryClsCode: string): JQueryPromise<viewmodel.model.SalaryClsInfoDto> {
        var dfd = $.Deferred<viewmodel.model.SalaryClsInfoDto>();
        nts.uk.request.ajax(paths.getSalaryClsInfo + "/" + salaryClsCode)
            .done(function (res: viewmodel.model.SalaryClsInfoDto) {
                dfd.resolve(res);
            })
            .fail(function (res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function addSalaryClsInfo(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.addSalaryClsInfo)
            .done(function (res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function (res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function deleteSalaryClsInfo(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.deleteSalaryClsInfo)
            .done(function (res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function (res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function updateSalaryClsInfo(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.updateSalaryClsInfo)
            .done(function (res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function (res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}