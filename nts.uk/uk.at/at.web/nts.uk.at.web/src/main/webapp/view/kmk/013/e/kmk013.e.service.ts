module nts.uk.at.view.kmk013.e {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/rounding/findByCid",
            findExcByCId : "shared/caculation/holiday/rounding/findExcByCid",
//            save:"shared/caculation/holiday/rounding/add",
            save:"shared/caculation/holiday/rounding/updateRoundingMonth",
            saveExcOut: "shared/caculation/holiday/rounding/updateExcoutRound",
            getIdMonth:"ctx/at/shared/outsideot/setting/findAll/mothItem",
            getOTCalc:"ctx/at/shared/outsideot/setting/findById",
            getMontItem: "at/record/divergencetime/getMonthlyAttendanceDivergenceName"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }
        export function getIdMonth():JQueryPromise<any>{
             return nts.uk.request.ajax(paths.getIdMonth);
        }
        /**
        * get all divergence item id(id co the chon)
        */
        export function getPossibleItem(arrPossible: Array<number>): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("at", paths.getMontItem, arrPossible);
        }

        /**
         * save Excess outside time
         */
        export function saveExcOut(obj): JQueryPromise<any>  {
                return nts.uk.request.ajax(paths.saveExcOut,obj);
        }
        
        export function findExcByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findExcByCId);
        }
        export function getOTCalc(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getOTCalc);
        }
    }
}