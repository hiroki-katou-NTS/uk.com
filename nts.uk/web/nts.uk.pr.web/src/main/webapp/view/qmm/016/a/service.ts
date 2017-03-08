module nts.uk.pr.view.qmm016 {
    /**
     * Module service.
     */
    export module service {
        export class Service implements base.simplehistory.service.Service<model.WageTable, model.WageTableHistory> {
            loadMasterModelList(): JQueryPromise<Array<model.WageTable>> {
                var dfd = $.Deferred<Array<model.WageTable>>();
                dfd.resolve([{
                    code: '001',
                    name: 'Name 001',
                    historyList: [{
                        uuid: 'uuid001',
                        start: 201601,
                        end: 201602
                    }, {
                        uuid: 'uuid002',
                        start: 201603,
                        end: 201604
                    }]
                }]);
                return dfd.promise();
            }
        }
        export var instance: Service = new Service();
    }
    
    /**
     * Model module.
     */
    export module model {
        /**
         * Wage table.
         */
        export interface WageTable extends base.simplehistory.model.MasterModel<WageTableHistory> {}
        
        /**
         * Wage table history model.
         */
        export interface WageTableHistory extends base.simplehistory.model.HistoryModel {}
    }
}