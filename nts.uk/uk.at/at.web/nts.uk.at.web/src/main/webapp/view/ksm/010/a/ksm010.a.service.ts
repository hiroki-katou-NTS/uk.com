module nts.uk.at.view.ksm010.a {

    import ajax = nts.uk.request.ajax;

    export module service {
        let paths = {
            getListRank: "at/schedule/employeeinfo/rank/getListRank",
            getRank: "at/schedule/employeeinfo/rank/getRank",
            insert: "at/schedule/employeeinfo/rank/insert",
            updateRank: "at/schedule/employeeinfo/rank/updateRank",
            deleteRank: "at/schedule/employeeinfo/rank/deleteRank"
        }


        export function getListRank(): JQueryPromise<any> {
            return ajax('at', paths.getListRank);
        }

        export function getRank(rankCd: string): JQueryPromise<any> {
            return ajax('at', paths.getRank + '/' + rankCd);
        }

        export function insert(command: a.Rank): JQueryPromise<any> {
            return ajax('at', paths.insert, command);
        }

        export function updateRank(command: a.Rank): JQueryPromise<any> {
            return ajax('at', paths.updateRank, command);
        }

        export function deleteRank(command: a.Rank): JQueryPromise<any> {
            return ajax('at', paths.deleteRank, command);
        }
    }
}