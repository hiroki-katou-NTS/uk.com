module nts.uk.pr.view.qmm020.i.service {
    let paths = {
        getStatementLinkPerson: "core/wageprovision/statementbindingsetting/getStatementLinkPerson"
    };

    export function getStatementLinkPerson(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getStatementLinkPerson, data);
    }
}