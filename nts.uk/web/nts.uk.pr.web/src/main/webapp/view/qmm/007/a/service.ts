module nts.uk.pr.view.qmm007 {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            getUnitPriceHistoryList: "pr/proto/unitprice/findall",
            getUnitPriceHistoryDetail: "pr/proto/unitprice/find",
            createUnitPriceHistory: "pr/proto/unitprice/create",
            updateUnitPriceHistory: "pr/proto/unitprice/update",
            removeUnitPriceHistory: "pr/proto/unitprice/remove"
        };

        /**
         * Normal service.
         */
        export class Service extends base.simplehistory.service.BaseService<model.UnitPrice, model.UnitPriceHistory> {
            constructor(path: base.simplehistory.service.Path) {
                super(path);
            }

            /**
             * Find history by id.
             */
            findHistoryByUuid(id: string): JQueryPromise<model.UnitPriceHistoryDto> {
                return nts.uk.request.ajax(paths.getUnitPriceHistoryDetail + "/" + id);
            }
        }

        /**
         * Service intance.
         */
        export var instance = new Service({
            historyMasterPath: 'pr/proto/unitprice/masterhistory',
            createHisotyPath: 'pr/proto/unitprice/history/create',
            deleteHistoryPath: 'pr/proto/unitprice/history/delete'
        });

        /**
         *  Update UnitPriceHistory
         */
        export function update(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateUnitPriceHistory, unitPriceHistory);
        }

        /**
        * Model namespace.
        */
        export module model {
            export interface UnitPrice extends base.simplehistory.model.MasterModel<UnitPriceHistory> {};
            export interface UnitPriceHistory extends base.simplehistory.model.HistoryModel {}
            export interface UnitPriceHistoryDto {
                id: string;
                unitPriceCode: string;
                unitPriceName: string;
                startMonth: number;
                endMonth: number;
                budget: number;
                fixPaySettingType: string;
                fixPayAtr: string;
                fixPayAtrMonthly: string;
                fixPayAtrDayMonth: string;
                fixPayAtrDaily: string;
                fixPayAtrHourly: string;
                memo: string;
            }
        }
    }
}
