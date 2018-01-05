module nts.uk.at.view.kmk013.e {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/rounding/findByCid",
            save:"shared/caculation/holiday/rounding/add",
            getIdMonth:"ctx/at/shared/outsideot/setting/findAll/mothItem",
            getMontItem: "at/record/divergencetime/AttendanceDivergenceName"
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

    }
}