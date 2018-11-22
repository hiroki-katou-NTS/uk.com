module nts.uk.pr.view.qmm019.f {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getStatementItem: "core/wageprovision/statementlayout/getStatementItem",
            getAttendanceItemStById: "core/wageprovision/statementlayout/getAttendanceItemStById/{0}/{1}"
        }

        export function getStatementItem(dataDto: any): JQueryPromise<any> {
            return ajax('pr', paths.getStatementItem, dataDto);
        }

        export function getAttendanceItemStById(categoryAtr: number, itemNameCode: string): JQueryPromise<any> {
            let _path = format(paths.getAttendanceItemStById, categoryAtr, itemNameCode);
            return ajax('pr', _path);
        }
    }
}