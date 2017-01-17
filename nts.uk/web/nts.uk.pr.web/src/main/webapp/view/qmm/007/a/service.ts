module nts.uk.pr.view.qmm007.a {
    export module service {
        export class UnitPriceHistoryNode {
            code: string;
            name: string;
            monthRange: string;
            nodeText: string;
            isChild: boolean;
            childs: any;
            constructor(code: string, name: string, monthRange: string, isChild: boolean, childs?: Array<UnitPriceHistoryNode>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.monthRange = monthRange;
                self.nodeText = self.code + ' ' + self.name;
                self.isChild = isChild;
                self.childs = childs;
                if (self.isChild == true) {
                    self.nodeText = self.monthRange;
                }
            }
        }

        var paths: any = {
        }
        var mockData = [new UnitPriceHistoryNode('001', 'ガソリン単価', '2016/04 ~ 9999/12', false, [new UnitPriceHistoryNode('0011', 'ガソリン単価', '2016/04 ~ 9999/12', true), new UnitPriceHistoryNode('0012', 'ガソリン単価', '2015/04 ~ 2016/03', true)]),
            new UnitPriceHistoryNode('002', '宿直単価', '2016/04 ~ 9999/12', false, [new UnitPriceHistoryNode('0021', '宿直単価', '2016/04 ~ 9999/12', true), new UnitPriceHistoryNode('0022', '宿直単価', '2015/04 ~ 2016/03', true)])];

        export function getUnitPriceHistoryList(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            dfd.resolve(mockData);
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
            export class UnitPriceDto {
                code: string;
                name: string;
            }

            export class UnitPriceHistoryDto {
                unitPriceCode: number;
                unitPriceName: string;
                startDate: DateTimeDto;
                budget: number;
                fixPaySettingType: SettingType;
                fixPayAtr: ApplySetting;
                fixPayAtrMonthly: ApplySetting;
                fixPayAtrDayMonth: ApplySetting;
                fixPayAtrDaily: ApplySetting;
                fixPayAtrHourly: ApplySetting;
                memo: string;

                /*constructor(unitPriceCode: number,
                    startDate: DateTimeDto,
                    budget: number,
                    fixPaySettingType: SettingType,
                    fixPayAtr: ApplySetting,
                    fixPayAtrMonthly: ApplySetting,
                    fixPayAtrDayMonth: ApplySetting,
                    fixPayAtrDaily: ApplySetting,
                    fixPayAtrHourly: ApplySetting,
                    memo: string) {
                    this.unitPriceCode = unitPriceCode;
                    this.startDate = startDate;
                    this.budget = budget;
                    this.fixPaySettingType = fixPaySettingType;
                    this.fixPayAtr = fixPayAtr;
                    this.fixPayAtrMonthly = fixPayAtrMonthly;
                    this.fixPayAtrDayMonth = fixPayAtrDayMonth;
                    this.fixPayAtrDaily = fixPayAtrDaily;
                    this.fixPayAtrHourly = fixPayAtrHourly;
                }*/
            }

            export class DateTimeDto {
                year: number;
                month: number;
                day: number;
                hour: number;
                minute: number;

                /*constructor(date: Date) {
                    this.year = date.getFullYear();
                    this.month = date.getMonth();
                    this.day = date.getDate();
                    this.hour = date.getHours();
                    this.minute = date.getMinutes();
                }*/
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
