module nts.uk.pr.view.qmm007.a {
    export module service {

        var paths: any = {
            getUnitPriceHistoryList: "pr/proto/unitprice/findall"
        };

        function convertToTreeList(): void{
            var mockData = [];
            var parentNodes = [];
            mockData.map( item => {
                var s = item.unitPriceCode ;
                if (s in parentNodes) parentNodes[s] = item; 
                else parentNodes[s] = item;
            });
            parentNodes.map(item => {new model.UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, item.monthRange, false)});
            var childNodes = mockData.map( item => new model.UnitPriceHistoryNode(item.id, item.unitPriceCode, item.unitPriceName, item.monthRange, true));
            console.log(mockData);
            console.log(parentNodes);
            console.log(childNodes);
            var merged = childNodes.concat(parentNodes); 
            console.log(merged);
        }

        export function getUnitPriceHistoryList(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getUnitPriceHistoryList)
                .done(function(res: Array<any>) {
                    dfd.resolve(null);
                    var unitPriceHistoryList: Array<model.UnitPriceHistoryDto> = res;
                    console.log(unitPriceHistoryList);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function create(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            var data = { unitPriceHistory: unitPriceHistory };
            return null;
        }

        export function update(unitPriceHistory: model.UnitPriceHistoryDto): JQueryPromise<any> {
            var data = { unitPriceHistory: unitPriceHistory };
            return null;
        }

        export function remove(id: string): JQueryPromise<any> {
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
                fixPaySettingType: SettingType;
                fixPayAtr: ApplySetting;
                fixPayAtrMonthly: ApplySetting;
                fixPayAtrDayMonth: ApplySetting;
                fixPayAtrDaily: ApplySetting;
                fixPayAtrHourly: ApplySetting;
                memo: string;

                constructor(
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
                }
            }

            export class UnitPriceHistoryNode{
            id: string;
            code: string;
            name: string;
            monthRange: MonthRange;
            nodeText: string;
            isChild: boolean;
            childs: any;
            constructor(id: string, code: string, name: string, monthRange: MonthRange, isChild: boolean, childs?: Array<UnitPriceHistoryNode>) {
                var self = this;
                self.id = id;
                self.code = code;
                self.name = name;
                self.monthRange = monthRange;
                self.isChild = isChild;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
                if (self.isChild == true) {
                    self.nodeText = self.monthRange.startMonth+self.monthRange.endMonth;
                }
            }
            }

            export class MonthRange {
                startMonth: string;
                endMonth: string;
                constructor(startMonth: string, endMonth: string) {
                    this.startMonth = startMonth;
                    this.endMonth = endMonth;
                }
            }

            export enum SettingType {
                Company = 0,
                Contract = 1
            }

            export enum ApplySetting {
                Apply = 1,
                NotApply = 0
            }

        }
    }
}
