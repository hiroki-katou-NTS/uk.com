module kml001.shr.vmbase {
    export class GridPersonCostCalculation {
        historyID: string;
        dateRange: string;  
        constructor(historyID: string, dateRange: string) {
            var self = this;
            self.historyID = historyID;
            self.dateRange = dateRange;
        }  
    }

    export interface PersonCostCalculationInterface {
        companyID: string;
        historyID: string;
        memo: string;
        unitPrice: number;
        startDate: string;
        endDate: string;
        premiumSets: Array<any>;
    }

    export class PersonCostCalculation {
        companyID: KnockoutObservable<string>;
        historyID: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        unitPrice: KnockoutObservable<number>;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        premiumSets : KnockoutObservableArray<PremiumSetting>;
        constructor(companyID: string, historyID: string, memo: string, unitPrice: number, startDate: string, endDate: string, premiumSets: Array<any>) {
            var self = this;
            self.companyID = ko.observable(companyID);
            self.historyID = ko.observable(historyID);
            self.memo = ko.observable(memo);
            self.unitPrice = ko.observable(unitPrice);
            self.startDate = ko.observable(startDate);
            self.endDate = ko.observable(endDate);
            let koPremiumSets = [];
            premiumSets.forEach(function(premiumSet){koPremiumSets.push(ProcessHandler.fromObjectPremiumSet(premiumSet));});
            self.premiumSets = ko.observableArray(koPremiumSets);
        }
    }
    
    export interface PremiumSettingInterface {
        companyID: string;
        historyID: string;
        attendanceID: string;
        premiumName: string;
        internalID: string;
        useAtr: number;
        premiumRate: number;
        timeItemIDs: Array<any>;
    }
    
    export class PremiumSetting {
        companyID: KnockoutObservable<string>;
        historyID: KnockoutObservable<string>;
        attendanceID: KnockoutObservable<string>; 
        premiumName: KnockoutObservable<string>;
        internalID: KnockoutObservable<string>;
        useAtr: KnockoutObservable<number>;
        premiumRate: KnockoutObservable<number>;
        timeItemIDs: KnockoutObservableArray<string>;
        constructor(companyID: string, historyID: string, attendanceID: string, premiumName: string, internalID: string, useAtr: number, premiumRate: number, timeItemIDs: Array<string>) {
            var self = this;
            self.companyID = ko.observable(companyID);
            self.historyID = ko.observable(historyID);
            self.attendanceID = ko.observable(attendanceID);
            self.premiumName = ko.observable(premiumName);
            self.internalID = ko.observable(internalID);
            self.useAtr = ko.observable(useAtr);
            self.premiumRate = ko.observable(premiumRate);
            self.timeItemIDs = ko.observableArray(timeItemIDs);
        }
        
    }
    
    export class TimeItem {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            var self = this;
            self.code = code;
            self.name = name;    
        }    
    }
    
    export class ExtraTimeItem {
        companyID: KnockoutObservable<string>;
        extraItemID: KnockoutObservable<string>; 
        name: KnockoutObservable<string>;
        timeItemID: KnockoutObservable<string>;
        useAtr: KnockoutObservable<number>;
        constructor(companyID: string, extraItemID: string, name: string, timeItemID: string, useAtr: number) {
            var self = this;
            self.extraItemID = ko.observable(extraItemID);
            self.companyID = ko.observable(companyID);
            self.useAtr = ko.observable(useAtr);
            self.timeItemID = ko.observable(timeItemID);
            self.name = ko.observable(name);
        }
    }
    
    export class ProcessHandler {
        static fromObjectPerconCost(object: PersonCostCalculationInterface): PersonCostCalculation {
            return new PersonCostCalculation(
                object.companyID, 
                object.historyID, 
                object.memo, 
                object.unitPrice, 
                object.startDate, 
                object.endDate,
                object.premiumSets);
        }
        static toObjectPersonCost(koObject: PersonCostCalculation): PersonCostCalculationInterface {
            let premiumSets = [];
            koObject.premiumSets().forEach(function(koPremiumSet){premiumSets.push(ProcessHandler.toObjectPremiumSet(koPremiumSet));});
            return {
                companyID: koObject.companyID(),
                historyID: koObject.historyID(),
                memo: koObject.memo(),
                unitPrice: koObject.unitPrice(),
                startDate: koObject.startDate(),
                endDate: koObject.endDate(),
                premiumSets : premiumSets     
            };    
        }
        static fromObjectPremiumSet(object: PremiumSettingInterface): PremiumSetting {
            return new PremiumSetting(
                object.companyID,
                object.historyID,
                object.attendanceID,
                object.premiumName,
                object.internalID,
                object.useAtr,
                object.premiumRate,
                object.timeItemIDs);
        }
        static toObjectPremiumSet(koObject: PremiumSetting): PremiumSettingInterface {
            return {
                companyID: koObject.companyID(),
                historyID: koObject.historyID(),
                attendanceID: koObject.attendanceID(), 
                premiumName: koObject.premiumName(),
                internalID: koObject.internalID(),
                useAtr: koObject.useAtr(),
                premiumRate: koObject.premiumRate(),
                timeItemIDs: koObject.timeItemIDs()     
            };    
        }
        static getOneDayBefore(date: string) {
            let numberDate = Date.parse(date);
            let dayBefore = new Date(numberDate - 24 * 60 * 60 * 1000);
            return dayBefore.getFullYear() + '/' + (dayBefore.getMonth() + 1) + '/' + dayBefore.getDate();
        }

        static getOneDayAfter(date: string) {
            let numberDate = Date.parse(date);
            let dayBefore = new Date(numberDate + 24 * 60 * 60 * 1000);
            return dayBefore.getFullYear() + '/' + (dayBefore.getMonth() + 1) + '/' + dayBefore.getDate();
        }
        static validateDateRange(inputDate: string, startDate: string, endDate: string){
            return (Date.parse(startDate) <= Date.parse(inputDate))&&(Date.parse(inputDate)<=Date.parse(endDate));    
        }
        static validateDateInput(inputDate: string, date: string){
            return (Date.parse(inputDate) <= Date.parse(date));
        }
    }

    export enum CategoryAtr {
        PAYMENT = 0,
        DEDUCTION = 1,
        PERSONAL_TIME = 2,
        ARTICLES = 3,
        OTHER = 9
    }

    export enum Error {
        ER001 = <any>"が入力されていません。",
        ER007 = <any>"が選択されていません。",
        ER010 = <any>"対象データがありません。",
    }
}