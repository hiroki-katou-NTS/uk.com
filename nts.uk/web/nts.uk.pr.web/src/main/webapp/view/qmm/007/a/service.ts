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
        * Model namespace.
        */
        export module model {

            export class UnitPriceHistoryDto {
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

            export class UnitPriceHistoryItemDto {
                id: string;
                startMonth: number;
                endMonth: number;
                constructor(id: string, startMonth: number, endMonth: number) {
                    this.id = id;
                    this.startMonth = startMonth;
                    this.endMonth = endMonth;
                }
            }

            export class UnitPriceItemDto {
                unitPriceCode: string;
                unitPriceName: string;
                histories: Array<UnitPriceHistoryItemDto>;
                constructor(unitPriceCode: string, unitPriceName: string, histories: Array<UnitPriceHistoryItemDto>) {
                    this.unitPriceCode = unitPriceCode;
                    this.unitPriceName = unitPriceName;
                    this.histories = histories;
                }
            }
        }
    }
}
