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
        export function getUnitPriceHistoryList(): JQueryPromise<Array<UnitPriceHistoryDto>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getUnitPriceHistoryList)
                .done(res => {
                    dfd.resolve(convertToTreeList(res));
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
            var data = unitPriceHistory;
            return nts.uk.request.ajax(paths.createUnitPriceHistory, data);
        }

        /**
         *  Update UnitPriceHistory
         */
        export function update(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            var data = unitPriceHistory;
            return nts.uk.request.ajax(paths.updateUnitPriceHistory, data);
        }

        /**
         *  Remove UnitPriceHistory
         */
        export function remove(id: string, version: number): JQueryPromise<any> {
            var request = { id: id, version: version };
            return nts.uk.request.ajax(paths.removeUnitPriceHistory, request);
        }

        /**
         * Convert list from dto to treegrid
         */
        function convertToTreeList(unitPriceHistoryList: Array<UnitPriceHistoryDto>): Array<UnitPriceHistoryNode> {
            var groupByCode = {};

            // group by unit price code
            unitPriceHistoryList.forEach(item => {
                var c = item.unitPriceCode;
                groupByCode[c] = item;
            });
            // convert groupByCode to array
            var arr = Object.keys(groupByCode).map(key => groupByCode[key]);

            // convert to tree node
            var parentNodes = arr.map(item => new model.UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, item.startMonth, item.endMonth, false, []));
            var childNodes = unitPriceHistoryList.map(item => new model.UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, item.startMonth, item.endMonth, true));

            var treeList: Array<UnitPriceHistoryNode> = parentNodes.map(parent => {
                childNodes.forEach(child => parent.childs.push(parent.unitPriceCode == child.unitPriceCode ? child : ''));
                return parent;
            });
            return treeList;
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

            export class UnitPriceHistoryNode {
                id: string
                unitPriceCode: string;
                unitPriceName: string;
                startMonth: string;
                endMonth: string;
                nodeText: string;
                isChild: boolean;
                childs: Array<UnitPriceHistoryNode>;
                constructor(id: string,
                    unitPriceCode: string,
                    unitPriceName: string,
                    startMonth: string,
                    endMonth: string,
                    isChild: boolean,
                    childs?: Array<UnitPriceHistoryNode>) {
                    var self = this;
                    self.isChild = isChild;
                    self.unitPriceCode = unitPriceCode;
                    self.unitPriceName = unitPriceName;
                    self.startMonth = startMonth;
                    self.endMonth = endMonth;
                    self.id = self.isChild == true ? id : id + id;
                    self.childs = childs;
                    self.nodeText = self.isChild == true ? self.startMonth + ' ~ ' + self.endMonth : self.unitPriceCode + ' ' + self.unitPriceName;
                }
            }

        }
    }
}
