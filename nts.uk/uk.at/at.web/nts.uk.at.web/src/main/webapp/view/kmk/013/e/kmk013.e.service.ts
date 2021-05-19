module nts.uk.at.view.kmk013.e {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/rounding/findByCid",
            save:"shared/caculation/holiday/rounding/updateRoundingMonth",
            getMontItem: "at/record/divergencetime/setting/getMonthlyAttendanceDivergenceName"
        };

        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }

        export function save(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }

        /**
        * get all divergence item id(id co the chon)
        */
        export function getPossibleItem(arrPossible: Array<number>): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("at", paths.getMontItem, arrPossible);
        }
    }
}