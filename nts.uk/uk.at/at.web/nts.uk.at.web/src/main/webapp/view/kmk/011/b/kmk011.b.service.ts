module nts.uk.at.view.kmk011.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "",
            find: "",
            getAllDivergenceTime: "at/record/divergencetime/setting/getalldivtime",
            updateDivTime: "at/record/divergencetime/updatedivtime",
            updateTimeItemId: "at/record/divergencetime/updateTimeItemId",
            getAllAttItem: "at/record/divergencetime/getAttendanceDivergenceItem",
            getItemSet: "at/record/divergencetime/getitemset/",
            getAllName: "at/record/divergencetime/AttendanceDivergenceName"
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
        * get all divergence time
        */
        export function getAllDivergenceTime(): JQueryPromise<Array<viewmodel.model.DivergenceTime>> {
            return nts.uk.request.ajax("at", path.getAllDivergenceTime);
        }
      
    }

}