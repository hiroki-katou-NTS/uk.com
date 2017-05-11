module kdl021.a.service {
    var paths = {
        getAllDivItem: "at/record/divergencetime/getAllItem"
    }
    /**
    * get all divergence item id(id co the chon)
    */
    export function getAllDivItemId(): JQueryPromise<Array<model.DivergenceItem>> {
        return nts.uk.request.ajax("at", paths.getAllDivItem);
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