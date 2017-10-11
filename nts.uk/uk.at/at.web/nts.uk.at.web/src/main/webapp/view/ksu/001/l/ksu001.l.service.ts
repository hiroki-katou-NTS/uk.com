module nts.uk.at.view.ksu001.l {
    export module service {
        let paths: any = {
            findAll: "at/schedule/shift/team/teamsetting/findAll",
            addTeam: "at/schedule/shift/team/teamsetting/add",
            findAllByWorkPlace: "at/schedule/shift/team/find/{0}",
        }

        export function addEmToTeam(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.addTeam, data);
        }
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAll);
        }
        export function findAllByWorkPlace(workPlaceId: string): JQueryPromise<any> {
            let path = nts.uk.text.format(paths.findAllByWorkPlace, workPlaceId);
            return nts.uk.request.ajax(path);
        }

    }
}