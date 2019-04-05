module nts.uk.pr.view.qmm008.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        init: "ctx/pr/core/socialinsurance/salaryhealth/startwelfare",
        update: "ctx/pr/core/socialinsurance/salaryhealth/updatewelfare",
        count: "ctx/pr/core/socialinsurance/salaryhealth/countwelfare",
        exportExcel: "file/core/socialinsurance/export"
    }
    export function init(command): JQueryPromise<any> {
        return ajax(paths.init, command);
    }
    export function update(command): JQueryPromise<any> {
        return ajax("pr", paths.update, command);
    }
    export function count(command): JQueryPromise<any> {
        return ajax(paths.count, command);
    }

    export function exportExcel(type: number): JQueryPromise<any> {
        let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
        let domainType = "QMM008";
        if (program.length > 1){
            program.shift();
            domainType = domainType + program.join(" ");
        }
        let data = {
            exportType: type
        }
        return nts.uk.request.exportFile( paths.exportExcel, data);
    }
}