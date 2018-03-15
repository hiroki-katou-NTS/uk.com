module nts.uk.at.view.kmk011.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "",
            find: "",
        };

        /**
       * get all item selected(item da duoc chon)
       */
        export function getItemSelected(divTimeId: number): JQueryPromise<Array<viewmodel.model.TimeItemSet>> {
            return nts.uk.request.ajax("at", path.getItemSet + divTimeId);
        }
        /**
        * get name(item da duoc chon)
        */
        export function getNameItemSelected(lstItemId: Array<number>): JQueryPromise<Array<viewmodel.model.DivergenceItem>> {
            return nts.uk.request.ajax("at", path.getAllName, lstItemId);
        }
        /**
        * get all attendance item id(id co the chon)
        */
        export function getAllAttItem(divType: number): JQueryPromise<Array<viewmodel.model.AttendanceType>> {
            return nts.uk.request.ajax("at", path.getAllAttItem);
        }
        /**
        * update time item id (da duoc chon lai)
        */
        export function updateTimeItemId(lstItemId: Array<viewmodel.model.DivergenceTimeItem>): JQueryPromise<Array<viewmodel.model.DivergenceTimeItem>> {
            return nts.uk.request.ajax("at", path.updateTimeItemId, lstItemId);
        }
        /**
        * get all divergence time
        */
        export function getAllDivTime(): JQueryPromise<Array<viewmodel.model.DivergenceTime>> {
            return nts.uk.request.ajax("at", path.getAllDivTime);
        }
        /**
         * update divergence time
         */
        export function updateDivTime(Object: viewmodel.model.ObjectDivergence): JQueryPromise<Array<viewmodel.model.DivergenceTimeItem>> {
            return nts.uk.request.ajax("at", path.updateDivTime, Object);
        }
    }
    
}