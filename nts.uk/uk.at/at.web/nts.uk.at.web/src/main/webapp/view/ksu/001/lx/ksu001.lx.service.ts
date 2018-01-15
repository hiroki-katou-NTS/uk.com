module nts.uk.at.view.ksu001.lx {
    export module service {
        let paths: any = {
            findAllByWorkPlace: "at/schedule/shift/team/find/{0}",
            findByTeamCode: "",
            addTeam: "at/schedule/shift/team/add",
            updateTeam: "at/schedule/shift/team/update",
            deleteTeam: "at/schedule/shift/team/delete",

        }

        export function saveTeam(isCreated, team: any): JQueryPromise<any> {
            let path = isCreated ? paths.addTeam : paths.updateTeam;
            return nts.uk.request.ajax("at", path, team);
        }
        export function findAllByWorkPlace(workPlaceId: string): JQueryPromise<any> {
            let path = nts.uk.text.format(paths.findAllByWorkPlace, workPlaceId);
            return nts.uk.request.ajax(path);
        }
        export function removeTeam(team: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteTeam, team);
        }

    }
}