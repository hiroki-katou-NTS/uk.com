module nts.uk.at.view.kmk011.c {
    export module service {
        /**
         * define path to service
         */
        var paths: any = {
            save: "",
            find: "",
 
            getAllDivReason: "at/record/divergencetime/setting/getalldivreason/",
            addDivReason: "at/record/divergencetime/setting/adddivreason",
            updateDivReason: "at/record/divergencetime/setting/updatedivreason",
            deleteDivReason: "at/record/divergencetime/setting/deletedivreason"
        };

        export function save(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.save);
        }

        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAll);
        }

        /**
            * get all divergence reason
        */
        export function getAllDivReason(divTimeId: string): JQueryPromise<Array<viewmodel.model.DivergenceReason>> {
            return nts.uk.request.ajax("at", paths.getAllDivReason + divTimeId);
        }
        /**
        * add divergence reason
        */
        export function addDivReason(divReason: viewmodel.model.DivergenceReason): JQueryPromise<Array<viewmodel.model.DivergenceReason>> {
            return nts.uk.request.ajax("at", paths.addDivReason, divReason);
        }
        /**
        * update divergence reason
        */
        export function updateDivReason(divReason: viewmodel.model.DivergenceReason): JQueryPromise<Array<viewmodel.model.DivergenceReason>> {
            return nts.uk.request.ajax("at", paths.updateDivReason, divReason);
        }
        /**
        * delete divergence reason
        */
        export function deleteDivReason(divReason: viewmodel.model.DivergenceReason): JQueryPromise<Array<viewmodel.model.DivergenceReason>> {
            return nts.uk.request.ajax("at", paths.deleteDivReason, divReason);
        }

    }
}