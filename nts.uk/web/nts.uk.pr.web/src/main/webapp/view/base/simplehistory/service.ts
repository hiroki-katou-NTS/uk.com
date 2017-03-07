module nts.uk.pr.view.base.simlehistory {
    export module model {
        /**
         * Contains master model.
         */
        export interface MasterModel<T extends HistoryModel> {
            /**
             * Contains code master.
             */
            code: string;

            /**
             * Contains name master.
             */
            name: string;

            /**
             * History list.
             */
            historyList: T[];
        }
        
        export interface HistoryModel {
            /**
             * Hisotry uuid.
             */
            uuid: string;

            /**
             * Contains start of history.
             */
            start: number;

            /**
             * Contain end of history.
             */
            end: number;
        }
    }
    
    export module service {
        /**
         * Services.
         */
        export interface Service<T extends model.MasterModel<V>, V extends model.HistoryModel> {
            loadMasterModelList(): JQueryPromise<Array<T>>;
        }

        /**
         * Contains path.
         */
        export interface Path {
            historyMasterPath: string;
        }
        
        /**
         * Simple base service.
         * Provide load master with path.
         */
        export abstract class BaseService<T extends model.MasterModel<V>, V extends model.HistoryModel> implements Service<T, V> {
            path: Path;
            constructor(path: Path) {
                var self = this;
                self.path = path;
            }

            /**
             * Load master model list.
             */
            loadMasterModelList(): JQueryPromise<Array<T>> {
                var self = this;
                return nts.uk.request.ajax(self.path.historyMasterPath);
            }
        }
    }
}