module nts.uk.com.view.cmm020.a {
    export module service {
        /**
         * define path to service
         */
        var paths: any = {
            getAllEraNameItem: "",
            saveEraName: "",
            deleteEraName: ""
        };

        export function getAllEraNameItem(): JQueryPromise<Array<viewmodel.model.EraItem>> {
            return nts.uk.request.ajax("at", paths.getAllEraNameItem);
        }

        export function createEraName(eraNameItem: viewmodel.model.EraItem): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveEraName,eraNameItem);
        }

        export function deleteEraName(eraNameItem: viewmodel.model.EraItem): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.deleteEraName,eraNameItem);
        }
    }

 
}