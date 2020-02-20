module jhn003.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        findPersonReport: "hr/notice/report/regis/person/report/search",
        approvalAll: "hr/notice/report/regis/person/report/approval-all",
        startPage: "hr/notice/report/regis/person/report/start-page",
    };

    export function findPersonReport(query) {
        return ajax("hr", paths.findPersonReport, query);
    }

    export function approvalAll(command) {
        return ajax("hr", paths.approvalAll, command);
    }

    export function startPage(command) {
        return ajax("hr", paths.startPage);
    }

}