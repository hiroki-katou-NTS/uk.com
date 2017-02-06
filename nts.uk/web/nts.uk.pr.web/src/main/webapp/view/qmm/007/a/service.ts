module nts.uk.pr.view.qmm007.a {
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
         *  Find all UnitPriceHistory
         */
        export function getUnitPriceHistoryList(): JQueryPromise<Array<model.UnitPriceHistoryDto>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getUnitPriceHistoryList)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /**
         *  Find UnitPriceHistory by Id
         */
        export function find(id: string): JQueryPromise<model.UnitPriceHistoryDto> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.getUnitPriceHistoryDetail + "/" + id)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /**
         *  Create UnitPriceHistory
         */
        export function create(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.createUnitPriceHistory, unitPriceHistory);
        }

        /**
         *  Update UnitPriceHistory
         */
        export function update(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateUnitPriceHistory, unitPriceHistory);
        }

        /**
         *  Remove UnitPriceHistory
         */
        export function remove(id: string, version: number): JQueryPromise<any> {
            var request = { id: id, version: version };
            return nts.uk.request.ajax(paths.removeUnitPriceHistory, request);
        }

        /**
         * Collect data from model and convert to dto.
         */
        export function collectData(unitPriceHistoryModel): model.UnitPriceHistoryDto {
            var dto = new service.model.UnitPriceHistoryDto();
            dto.id = unitPriceHistoryModel.id;
            dto.version = unitPriceHistoryModel.version;
            dto.unitPriceCode = unitPriceHistoryModel.unitPriceCode();
            dto.unitPriceName = unitPriceHistoryModel.unitPriceName();
            dto.startMonth = unitPriceHistoryModel.startMonth();
            dto.endMonth = unitPriceHistoryModel.endMonth();
            dto.budget = unitPriceHistoryModel.budget();
            dto.fixPaySettingType = unitPriceHistoryModel.fixPaySettingType();
            dto.fixPayAtr = unitPriceHistoryModel.fixPayAtr();
            dto.fixPayAtrMonthly = unitPriceHistoryModel.fixPayAtrMonthly();
            dto.fixPayAtrDayMonth = unitPriceHistoryModel.fixPayAtrDayMonth();
            dto.fixPayAtrDaily = unitPriceHistoryModel.fixPayAtrDaily();
            dto.fixPayAtrHourly = unitPriceHistoryModel.fixPayAtrHourly();
            dto.memo = unitPriceHistoryModel.memo();
            return dto;
        }

        /**
        * Model namespace.
        */
        export module model {
            export class UnitPriceHistoryDto {
                id: string;
                version: number;
                unitPriceCode: string;
                unitPriceName: string;
                startMonth: string;
                endMonth: string;
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
