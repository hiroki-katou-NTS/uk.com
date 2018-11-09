module nts.uk.pr.view.qmm041.c.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
        editEmpSalUnitPriceHis: "ctx/pr/core/wageprovision/empsalunitprice/editHistory",
        deleteEmpSalUnitPriceHis: "ctx/pr/core/wageprovision/empsalunitprice/deleteHistory"
    }

    export function editEmpSalUnitPriceHis(command): JQueryPromise<any> {
        return ajax(paths.editEmpSalUnitPriceHis, command);
    }

    export function deleteEmpSalUnitPriceHis(command): JQueryPromise<any> {
        return ajax(paths.deleteEmpSalUnitPriceHis, command);
    }
}