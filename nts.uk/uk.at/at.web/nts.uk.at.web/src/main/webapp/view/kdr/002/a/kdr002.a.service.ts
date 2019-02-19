module nts.uk.at.view.kdr002.a {
    import ajax = nts.uk.request.ajax;

    export module service {

        let path: any = {
            findClosureByEmpID: "ctx/at/shared/service/workrule/closure/find_by_empid"
        };

        export function findClosureByEmpID(): JQueryPromise<any> {
            return ajax("at", path.findClosureByEmpID);
        }

    }
}