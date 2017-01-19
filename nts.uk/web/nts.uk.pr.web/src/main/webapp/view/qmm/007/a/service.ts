module nts.uk.pr.view.qmm007.a {
    export module service {

        var paths: any = {
            getUnitPriceHistoryList: "pr/proto/unitprice/findall",
            getUnitPriceHistoryDetail: "pr/proto/unitprice/find",
            createUnitPriceHistory: "pr/proto/unitprice/create",
            updateUnitPriceHistory: "pr/proto/unitprice/update",
            removeUnitPriceHistory: "pr/proto/unitprice/remove"
        };

        function convertToTreeList(unitPriceHistoryList: Array<UnitPriceHistoryDto>): Array<UnitPriceHistoryNode> {
            var groupByCode = {};

            // group by unit price code
            unitPriceHistoryList.forEach(item => {
                var c = item.unitPriceCode;
                groupByCode[c] = item;
            });
            // convert to array
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


        export function getUnitPriceHistoryList(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getUnitPriceHistoryList)
                .done(res => {
                    var unitPriceHistoryList: Array<model.UnitPriceHistoryDto> = res;
                    dfd.resolve(convertToTreeList(unitPriceHistoryList));
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function getUnitPriceHistoryDetail(id: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.getUnitPriceHistoryDetail + "/" + id)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function create(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            var data = { unitPriceHistory: unitPriceHistory };
            console.log(data);
        }

        export function update(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            var data = { unitPriceHistory: unitPriceHistory };
            console.log(data);
        }

        export function remove(id: string): JQueryPromise<any> {
            console.log(id);
            return null;
        }

        /**
        * Model namespace.
        */
        export module model {
            export class UnitPriceHistoryDto {
                id: string;
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

                /*constructor(
                    id: string,
                    unitPriceCode: string,
                    unitPriceName: string,
                    startMonth: string,
                    endMonth: string,
                    budget: number,
                    fixPaySettingType: SettingType,
                    fixPayAtr: ApplySetting,
                    fixPayAtrMonthly: ApplySetting,
                    fixPayAtrDayMonth: ApplySetting,
                    fixPayAtrDaily: ApplySetting,
                    fixPayAtrHourly: ApplySetting,
                    memo: string) {
                    this.id = id;
                    this.unitPriceCode = unitPriceCode;
                    this.unitPriceName = unitPriceName;
                    this.startMonth = startMonth;
                    this.endMonth = endMonth;
                    this.budget = budget;
                    this.fixPaySettingType = fixPaySettingType;
                    this.fixPayAtr = fixPayAtr;
                    this.fixPayAtrMonthly = fixPayAtrMonthly;
                    this.fixPayAtrDayMonth = fixPayAtrDayMonth;
                    this.fixPayAtrDaily = fixPayAtrDaily;
                    this.fixPayAtrHourly = fixPayAtrHourly;
                }*/
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
