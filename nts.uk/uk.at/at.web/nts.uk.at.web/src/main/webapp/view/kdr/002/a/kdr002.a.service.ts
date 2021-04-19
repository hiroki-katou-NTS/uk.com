module nts.uk.at.view.kdr002.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {

        let path: any = {
            findClosureByEmpID: "ctx/at/shared/workrule/closure/find_by_empid",
            exportExcel: "screen/at/yearholidaymanagement/export",
            findClosureById: "ctx/at/shared/workrule/closure/findById/{0}",
            findAllClosure: "ctx/at/shared/workrule/closure/findAll"
        };

        export function findClosureByEmpID(): JQueryPromise<any> {
            return ajax("at", path.findClosureByEmpID);
        }

        export function exportExcel(data: any) {
            return nts.uk.request.exportFile("at", path.exportExcel, data);
        }


        export function findClosureById(id: any): JQueryPromise<any> {
            return ajax("at", format(path.findClosureById, id));
         }

        export function findAllClosure(): JQueryPromise<any> {
            return ajax("at", path.findAllClosure);
        }


        export function saveCharacteristic(companyId: string, userId: string, obj: any): void {
            nts.uk.characteristics.save("screenInfo"
                                    + "_companyId_" + companyId
                                    + "_userId_" + userId, obj);
        }

        export function restoreCharacteristic(companyId: string, userId: string): JQueryPromise<any> {
            return nts.uk.characteristics.restore("screenInfo"
                                    + "_companyId_" + companyId
                                    + "_userId_" + userId);
        }

        export module model {

            // Data class
            export class Closure {
                companyId: any;
                closureId: number;
                useClassification: string;
                closureMonth: number;
                closureHistories: any;
            }
        }

    }


}