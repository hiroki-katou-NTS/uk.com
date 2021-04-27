/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013 {
    export module common {
        /**
         * Workplaces Category
         * @returns  Array
         */
        export function workplaceCategory() {
            return [
                {code: WorkplaceCategory.MASTER_CHECK_BASIC, name: nts.uk.resource.getText("KAL020_21") },
                {code: WorkplaceCategory.MASTER_CHECK_WORKPLACE, name: nts.uk.resource.getText("KAL020_210") },
                {code: WorkplaceCategory.MASTER_CHECK_DAILY, name: nts.uk.resource.getText("KAL020_103") },
                {code: WorkplaceCategory.SCHEDULE_DAILY, name: nts.uk.resource.getText("KAL020_300") },
                {code: WorkplaceCategory.MONTHLY, name: nts.uk.resource.getText("KAL020_401") },
                {code: WorkplaceCategory.APPLICATION_APPROVAL, name: nts.uk.resource.getText("KAL020_501") },
            ];
        }

        export enum WorkplaceCategory {
            MASTER_CHECK_BASIC = 0,// マスタチェック(基本)
            MASTER_CHECK_WORKPLACE = 1,// マスタチェック(職場)
            MASTER_CHECK_DAILY = 2,// マスタチェック(日次)
            SCHEDULE_DAILY = 3, // "スケジュール／日次",
            MONTHLY = 4,// 月次
            APPLICATION_APPROVAL = 5, //"申請承認"
        }

        export class AlarmPattern {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;

            constructor(code?: string, name?: string) {
                this.code = ko.observable(code);
                this.name = ko.observable(name);
            }
        }

        export class Alarm {
            code: string;
            name: string;

            constructor(code?: string, name?: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class Category {
            code: number;
            name: string;

            constructor(code?: number, name?: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class CategoryPattern {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;

            constructor(code?: string, name?: string) {
                this.code = ko.observable(code);
                this.name = ko.observable(name);
            }
        }

        export class AlarmDto {
            isChecked: KnockoutObservable<boolean> = ko.observable(false);
            no: KnockoutObservable<number> = ko.observable(null);
            classification: KnockoutObservable<number> = ko.observable(null);
            name: KnockoutObservable<string> = ko.observable(null);
            message: KnockoutObservable<string> = ko.observable(null);
            clsText: string;

            constructor(isChecked: boolean, no: number, classification: number, name: string, message: string) {
                this.isChecked(isChecked);
                this.no(no);
                this.classification(classification);
                this.name(name);
                this.message(message);

                switch (classification) {
                    case 1: {
                        this.clsText = "ER";
                        break;
                    }
                    case 2: {
                        this.clsText = "AL";
                        break;
                    }
                    case 3: {
                        this.clsText = "";
                        break;
                    }
                }

            }
        }

        export class CheckConditionDto {
            isCheck: KnockoutObservable<boolean> = ko.observable(false);
            id: string;
            no: KnockoutObservable<number> = ko.observable(null);
            checkItem: KnockoutObservable<number> = ko.observable(null);
            useAtr: KnockoutObservable<number> = ko.observable(null);
            name: KnockoutObservable<string> = ko.observable(null);
            minValue: KnockoutObservable<string> = ko.observable(null);
            maxValue: KnockoutObservable<string> = ko.observable(null);
            operator: KnockoutObservable<number> = ko.observable(null);
            message: KnockoutObservable<string> = ko.observable(null);

            checkCond: KnockoutObservable<string> = ko.observable(null);

            additionAttendanceItems: KnockoutObservableArray<number> = ko.observableArray([]);
            substractionAttendanceItems: KnockoutObservableArray<number> = ko.observableArray([]);

            checkCondB: KnockoutObservable<number> = ko.observable(null);

            constructor(isCheck: boolean, id: string,
                        no: number, checkItem: number,
                        useAtr: number, name: string,
                        minValue: string, maxValue: string,
                        operator: number, message: string,
                        checkCond: string, checkCondB: number,
                        addItems: Array<number>, subItems: Array<number>) {
                this.isCheck(isCheck);
                this.id = id;
                this.no(no);
                this.checkItem(checkItem);
                this.useAtr(useAtr);
                this.name(name);
                this.minValue(minValue);
                this.maxValue(maxValue);
                this.operator(operator);
                this.message(message);
                this.checkCond(checkCond);
                this.checkCondB(checkCondB);
                this.additionAttendanceItems(addItems);
                this.substractionAttendanceItems(subItems);
            }
        }
    }
}