module nts.uk.pr.view.qmm007.a {
    export module service {

        var paths: any = {
            getUnitPriceHistoryList: "pr/proto/unitprice/findall",
            getUnitPriceHistoryDetail: "pr/proto/unitprice/find",
            createUnitPriceHistory: "pr/proto/unitprice/create",
            updateUnitPriceHistory: "pr/proto/unitprice/update",
            removeUnitPriceHistory: "pr/proto/unitprice/remove"
        };

        // collect data from model
        export function collectData(unitPriceHistoryModel: model.UnitPriceHistoryModel) {
            var dto = new model.UnitPriceHistoryDto();
            dto.id = unitPriceHistoryModel.id;
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
            var data = unitPriceHistory;
            return nts.uk.request.ajax(paths.createUnitPriceHistory, data);
        }

        export function update(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            var data = unitPriceHistory;
            return nts.uk.request.ajax(paths.updateUnitPriceHistory, data);
        }

        export function remove(id: string): JQueryPromise<any> {
            var request = {id: id};
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

                constructor() { }
            }

            export class UnitPriceHistoryModel {
                id: string;
                unitPriceCode: KnockoutObservable<string>;
                unitPriceName: KnockoutObservable<string>;
                startMonth: KnockoutObservable<string>;
                endMonth: KnockoutObservable<string>;
                budget: KnockoutObservable<number>;
                fixPaySettingType: KnockoutObservable<string>;
                fixPayAtr: KnockoutObservable<string>;
                fixPayAtrMonthly: KnockoutObservable<string>;
                fixPayAtrDayMonth: KnockoutObservable<string>;
                fixPayAtrDaily: KnockoutObservable<string>;
                fixPayAtrHourly: KnockoutObservable<string>;
                memo: KnockoutObservable<string>;

                constructor() {
                    this.id = '';
                    this.unitPriceCode = ko.observable('');
                    this.unitPriceName = ko.observable('');
                    this.startMonth = ko.observable('2017/01');
                    this.endMonth = ko.observable('（平成29年01月） ~');
                    this.budget = ko.observable(null);
                    this.fixPaySettingType = ko.observable('Company');
                    this.fixPayAtr = ko.observable('NotApply');
                    this.fixPayAtrMonthly = ko.observable('NotApply');
                    this.fixPayAtrDayMonth = ko.observable('NotApply');
                    this.fixPayAtrDaily = ko.observable('NotApply');
                    this.fixPayAtrHourly = ko.observable('NotApply');
                    this.memo = ko.observable('');
                }
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
