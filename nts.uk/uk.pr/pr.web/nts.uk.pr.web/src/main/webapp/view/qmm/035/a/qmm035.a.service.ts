module nts.uk.pr.view.qmm035.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        exportExcel: "ctx/pr/report/printdata/comlegalrecord/export",
        defaultData: "ctx/pr/report/printdata/comlegalrecord/start",
        findByCode: "ctx/pr/report/printdata/comlegalrecord/getbycode/{0}",
        update: "ctx/pr/report/printdata/comlegalrecord/update",
        create: "ctx/pr/report/printdata/comlegalrecord/create",
        delete: "ctx/pr/report/printdata/comlegalrecord/delete"
    };

    export function exportExcel(): JQueryPromise<any>{
        return nts.uk.request.exportFile(paths.exportExcel);
    }

    export function defaultData(): JQueryPromise<any> {
        return ajax("pr", paths.defaultData);
    }

    export function findByCode(code: string): JQueryPromise<any> {
        let _path = format(paths.findByCode, code);
        return ajax('pr', _path);
    }

    export function update(command): JQueryPromise<any> {
        return ajax('pr', paths.update, command);
    }

    export function create(command): JQueryPromise<any> {
        return ajax('pr', paths.create, command);
    }

    export function deleteCompany(command: any) {
        return ajax('pr', paths.delete, command);
    }
}