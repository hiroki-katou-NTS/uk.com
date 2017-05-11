module kdl021.a.service {
    var paths = {
        getAllDivItem: "at/record/divergencetime/getPossibleItem"
    }
    /**
    * get all divergence item id(id co the chon)
    */
    export function getPossibleItem(arrPossible: Array<string>): JQueryPromise<Array<model.DivergenceItem>> {
        return nts.uk.request.ajax("at", paths.getAllDivItem , arrPossible);
    }
    export module model {
        export class DivergenceItem {
            id: number;
            name: string;
            displayNumber: number;
            useAtr: number;
            attendanceAtr: number;
        }
    }

}