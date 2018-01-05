module nts.uk.at.view.ksu001.n {

    export module service {
        let paths: any = {
            findAllRank: "at/schedule/shift/rank/findAll",
            findAllRankSetting: "at/schedule/shift/rank/ranksetting/findAll",
            addRankSetting: "at/schedule/shift/rank/ranksetting/addList",
            findWorkPlaceById: "bs/employee/workplace/info/findDetail",

        }

        export function findAllRank(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAllRank);
        }
        export function findAllRankSetting(listEmployee: Array<String>): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAllRankSetting, listEmployee);
        }
        export function addRankSetting(rankSetting: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.addRankSetting, rankSetting);
        }
        export function getWorkPlaceById(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.findWorkPlaceById, data);
        }

    }
}