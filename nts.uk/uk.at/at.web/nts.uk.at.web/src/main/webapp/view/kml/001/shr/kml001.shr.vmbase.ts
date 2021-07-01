module nts.uk.at.view.kml001.shr {
  export module vmbase {
    export class GridPersonCostCalculation {
      dateRange: string;
      companyId?: string;
      historyId?: string;
      startDate?: string;
      endDate?: string;
      constructor(dateRange: string, companyId?: string, historyId?: string, startDate?: string, endDate?: string) {
        var self = this;
        self.dateRange = dateRange; //display on the list
        self.companyId = companyId;
        self.historyId = historyId;
        self.startDate = startDate;
        self.endDate = endDate;
      }
    }

    export interface PersonCostCalculationInterface {
      companyID: string;
      historyID: string;
      startDate: string;
      endDate: string;
      unitPrice: number;
      memo: string;
      premiumSets: Array<PremiumSettingInterface>;
      personCostRoundingSetting: any;
      calculationSetting: number;
      roundingUnitPrice: number;
      unit: number;
      inUnits: number;
      workingHour: number;
      howToSetUnitPrice: number;
      workingHoursUnitPrice: number;
    }

    export class PersonCostCalculation {
      companyID: KnockoutObservable<string>;
      historyID: KnockoutObservable<string>;
      startDate: KnockoutObservable<string>;
      endDate: KnockoutObservable<string>;
      unitPrice: KnockoutObservable<number> = ko.observable(0);
      memo: KnockoutObservable<string>;
      premiumSets: KnockoutObservableArray<PremiumSetting>;
      personCostRoundingSetting: KnockoutObservable<any>;
      //ver9
      calculationSetting: KnockoutObservable<number> = ko.observable(1);
      roundingUnitPrice: KnockoutObservable<number>;
      unit: KnockoutObservable<number>;
      inUnits: KnockoutObservable<number>;
      workingHour: KnockoutObservable<number>;
      howToSetUnitPrice: KnockoutObservable<number>;
      workingHoursUnitPrice: KnockoutObservable<number>;

      constructor(
        companyID: string, historyID: string,
        startDate: string, endDate: string,
        unitPrice: number, memo: string,
        personCostRoundingSetting: any,
        premiumSets: Array<PremiumSettingInterface>,
        calculationSetting: number = 1, roundingUnitPrice: number = 0,
        unit: number = 1, inUnits: number = 0, workingHour: number
      ) {
        var self = this;
        self.companyID = ko.observable(companyID);
        self.historyID = ko.observable(historyID);
        self.startDate = ko.observable(startDate);
        self.endDate = ko.observable(endDate);
        self.unitPrice = ko.observable(unitPrice);
        self.memo = ko.observable(memo);
        self.personCostRoundingSetting = ko.observable(personCostRoundingSetting);
        self.premiumSets = ko.observableArray(_.map(premiumSets, premiumSet => vmbase.ProcessHandler.fromObjectPremiumSet(premiumSet)));
        //ver9
        self.calculationSetting = ko.observable(calculationSetting);
        self.howToSetUnitPrice = ko.observable(calculationSetting);
        self.roundingUnitPrice = ko.observable(roundingUnitPrice);
        self.unit = ko.observable(unit);
        self.inUnits = ko.observable(inUnits);
        self.workingHour = ko.observable(workingHour);
        self.workingHoursUnitPrice = ko.observable(workingHour);

        self.calculationSetting.subscribe(value => {
          if (value == 0) {
            $("#premium-set-div .nts-fixed-body-wrapper").height('400px');
          } else {
            $("#premium-set-div .nts-fixed-body-wrapper").height('440px');
          }
        });
      }


      public updateData(data: PersonCostCalculationInterface) {
        var self = this;
        self.companyID(data.companyID);
        self.historyID(data.historyID);
        self.startDate(data.startDate);
        self.endDate(data.endDate);
        self.unitPrice(data.unitPrice);
        self.memo(data.memo);
        self.premiumSets(_.map(data.premiumSets, premiumSet => vmbase.ProcessHandler.fromObjectPremiumSet(premiumSet)));
        self.personCostRoundingSetting(data.personCostRoundingSetting);
        //ver9
        self.calculationSetting(data.calculationSetting);
        self.roundingUnitPrice(data.roundingUnitPrice);
        self.unit(data.unit);
        self.inUnits(data.inUnits);
        self.workingHour(data.workingHour);
      }
    }

    export interface PremiumSettingInterface {
      companyID: string;
      historyID: string;
      displayNumber: number;
      rate: number;
      name: string;
      useAtr: number;
      attendanceItems: Array<AttendanceItem>;
      unitPrice: number;
    }

    export class PremiumSetting {
      companyID: KnockoutObservable<string>;
      historyID: KnockoutObservable<string>;
      displayNumber: KnockoutObservable<number>;
      rate: KnockoutObservable<number>;
      name: KnockoutObservable<string>;
      useAtr: KnockoutObservable<number>;
      attendanceItems: KnockoutObservableArray<AttendanceItem>;
      unitPrice: KnockoutObservable<number>;

      constructor(companyID: string, historyID: string, displayNumber: number, rate: number,
        name: string, useAtr: number, attendanceItems: Array<AttendanceItem>, unitPrice: number) {
        var self = this;
        self.companyID = ko.observable(companyID);
        self.historyID = ko.observable(historyID);
        self.displayNumber = ko.observable(displayNumber);
        self.rate = ko.observable(rate);
        self.name = ko.observable(name);
        self.useAtr = ko.observable(useAtr);
        let koAttendanceItems = [];
        attendanceItems.forEach(function (item) {
          koAttendanceItems.push(new vmbase.AttendanceItem(item.shortAttendanceID, item.name));
        });
        self.attendanceItems = ko.observableArray(koAttendanceItems);
        self.unitPrice = ko.observable(unitPrice);
      }

    }

    export class AttendanceItem {
      shortAttendanceID: number;
      name: string;
      constructor(shortAttendanceID: number, name: string) {
        var self = this;
        self.shortAttendanceID = shortAttendanceID;
        self.name = name;
      }
    }

    export class PremiumItem {
      companyID: KnockoutObservable<string>;
      displayNumber: KnockoutObservable<number>;
      name: KnockoutObservable<string>;
      useAtr: KnockoutObservable<number>;
      isChange: KnockoutObservable<boolean>;
      unitPrice: KnockoutObservable<number>;
      constructor(companyID: string, displayNumber: number, name: string, useAtr: number, isChange: boolean, unitPrice: number) {
        var self = this;
        self.companyID = ko.observable(companyID);
        self.displayNumber = ko.observable(displayNumber);
        self.name = ko.observable(name);
        self.useAtr = ko.observable(useAtr);
        self.isChange = ko.observable(isChange);
        self.unitPrice = ko.observable(unitPrice);
      }
    }

    export class PremiumItemLanguage {
      companyID: KnockoutObservable<string>;
      displayNumber: KnockoutObservable<number>;
      langID: KnockoutObservable<string>;
      name: KnockoutObservable<string>;
      constructor(companyID: string, displayNumber: number, langID: string, name: string) {
        var self = this;
        self.companyID = ko.observable(companyID);
        self.displayNumber = ko.observable(displayNumber);
        self.langID = ko.observable(langID);
        self.name = ko.observable(name);
      }
    }

    export class ProcessHandler {

      /**
       * convert PersonCostCalculation JS object to PersonCostCalculation knockoutJS object 
       */
      static fromObjectPerconCost(object: PersonCostCalculationInterface): PersonCostCalculation {
        return new PersonCostCalculation(
          object.companyID,
          object.historyID,
          object.startDate,
          object.endDate,
          object.unitPrice,
          object.memo,
          object.personCostRoundingSetting,
          object.premiumSets,
          object.calculationSetting,
          object.roundingUnitPrice,
          object.unit,
          object.inUnits,
          object.workingHour
        );
      }

      /**
       * convert PersonCostCalculation knockoutJS object to PersonCostCalculation JS object
       */
      static toObjectPersonCost(koObject: PersonCostCalculation): PersonCostCalculationInterface {
        let premiumSets: Array<PremiumSettingInterface> = [];
        koObject.premiumSets().forEach(function (koPremiumSet) { premiumSets.push(ProcessHandler.toObjectPremiumSet(koPremiumSet)); });
        return {
          companyID: koObject.companyID(),
          historyID: koObject.historyID(),
          startDate: koObject.startDate(),
          endDate: koObject.endDate(),
          unitPrice: koObject.unitPrice(),
          memo: koObject.memo(),
          premiumSets: premiumSets,
          personCostRoundingSetting: koObject.personCostRoundingSetting(),
          calculationSetting: koObject.calculationSetting(),
          roundingUnitPrice: koObject.roundingUnitPrice(),
          unit: koObject.unit(),
          inUnits: koObject.inUnits(),
          workingHour: koObject.workingHour(),
          howToSetUnitPrice: koObject.calculationSetting(),
          workingHoursUnitPrice: koObject.workingHour()
        };
      }

      /**
       * convert PremiumSetting JS object to PremiumSetting knockoutJS object 
       */
      static fromObjectPremiumSet(object: PremiumSettingInterface): PremiumSetting {
        return new PremiumSetting(
          object.companyID,
          object.historyID,
          object.displayNumber,
          object.rate,
          object.name,
          object.useAtr,
          object.attendanceItems,
          object.unitPrice
        );
      }

      /**
       * convert PremiumSetting knockoutJS object to PremiumSetting JS object
       */
      static toObjectPremiumSet(koObject: PremiumSetting): PremiumSettingInterface {
        return {
          companyID: koObject.companyID(),
          historyID: koObject.historyID(),
          displayNumber: koObject.displayNumber(),
          rate: koObject.rate(),
          name: koObject.name(),
          useAtr: koObject.useAtr(),
          attendanceItems: _.map(koObject.attendanceItems(), function (item) { return { shortAttendanceID: item.shortAttendanceID, name: item.name } }),
          unitPrice: koObject.unitPrice()
        };
      }

      static createPersonCostCalFromValue(objectPersonCostCalculation: PersonCostCalculationInterface, premiumItems: Array<PremiumItem>): PersonCostCalculation {
        let personCostCalculation = new PersonCostCalculation("", "", "", "", 0, "", null, [], 1, 0, 0, 1, 0);
        var self = this;
        personCostCalculation.companyID(objectPersonCostCalculation.companyID);
        personCostCalculation.historyID(objectPersonCostCalculation.historyID);
        personCostCalculation.startDate(objectPersonCostCalculation.startDate);
        personCostCalculation.endDate(objectPersonCostCalculation.endDate);
        personCostCalculation.unitPrice(objectPersonCostCalculation.unitPrice);
        personCostCalculation.memo(objectPersonCostCalculation.memo);
        let koPremiumSets = [];
        premiumItems.forEach(function (premiumItem, index) {
          if (premiumItem.useAtr()) {
            let premiumSet = _.find(objectPersonCostCalculation.premiumSets, function (o) {
              return o.displayNumber == index + 1;
            })
            if (premiumSet) {
              koPremiumSets.push(ProcessHandler.fromObjectPremiumSet(premiumSet));
            } else {
              koPremiumSets.push(
                new vmbase.PremiumSetting(
                  "", "", premiumItem.displayNumber(),
                  100, premiumItem.name(), premiumItem.useAtr(),
                  [], premiumItem.unitPrice()
                )
              );
            }
          }
        });
        personCostCalculation.premiumSets(koPremiumSets);
        return personCostCalculation;
      }

      /**
       * get one day before input date as string format
       */
      static getOneDayBefore(date: string) {
        return moment(date).add(-1, 'days').format("YYYY/MM/DD");
      }

      /**
       * get one day after input date as string format
       */
      static getOneDayAfter(date: string) {
        return moment(date).add(1, 'days').format("YYYY/MM/DD");
      }

      /**
       * check input date in range, if date in range return true
       */
      static validateDateRange(inputDate: string, startDate: string, endDate: string) {
        return moment(inputDate).isBetween(moment(this.getOneDayBefore(startDate)), moment(this.getOneDayAfter(endDate)));
      }

      /**
       * check input date before or equal date
       */
      static validateDateInput(inputDate: string, date: string) {
        return moment(inputDate).isSameOrAfter(moment(date));
      }
    }

    export enum UseAtr {
      NotUse = 0,
      Use = 1
    }

    export enum UnitPrice {
      Price_1 = 0,
      Price_2 = 1,
      Price_3 = 2,
      Standard = 3,
      Contract = 4
    }

    export enum MSG {
      MSG015 = <any>"登録しました。",
      MSG018 = <any>"選択中のデータを削除しますか？",
      MSG065 = <any>"最新の履歴の有効開始日より以前の有効開始日を登録できません。",
      MSG066 = <any>"割増項目が設定されてません。",
      MSG102 = <any>"最新の履歴開始日以前に履歴を追加することはできません。",
      MSG128 = <any>"最後の履歴を削除することができません。"
    }

    //人件費単価端数処理
    export enum UnitPriceRounding {
      // 切り上げ
      ROUND_UP = 0, //Enum_UnitPriceRounding_roundUp
      //切り捨て
      TRUNCATION = 1,//Enum_UnitPriceRounding_truncation
      //四捨五入
      DOWN_4_UP_5 = 2,//Enum_UnitPriceRounding_down4Up5
    }
    // 端数処理位置
    export enum AmountUnit {

      NONE = 0,//default
      /** The one yen. */
      // 1円
      ONE_YEN = 1,//Enum_AmountUnit_oneYen

      /** The ten yen. */
      // 10円
      TEN_YEN = 10, //Enum_AmountUnit_tenYen

      /** The one hundred yen. */
      // 100円
      ONE_HUNDRED_YEN = 100,//Enum_AmountUnit_oneHundredYen

      /** The one thousand yen. */
      // 1000円
      ONE_THOUSAND_YEN = 1000, //Enum_AmountUnit_oneThousandYen
    }
    // 端数処理
    export enum Rounding {

      /** The rounding down. */
      // 切り捨て
      ROUNDING_DOWN = 0, //"切り捨て", "Enum_Rounding_Down"),

      /** The rounding up. */
      // 切り上げ
      ROUNDING_UP = 1, //"切り上げ", "Enum_Rounding_Up"),

      /** The rounding down over. */
      // 未満切捨、以上切上
      ROUNDING_DOWN_OVER = 2, //"未満切捨、以上切上", "Enum_Rounding_Down_Over");
    }

    export enum InUnits {
      /** The truncation. */
      // 切り捨て
      TRUNCATION = 0, //"ENUM_ROUNDING_TRUNCATION"

      /** The round up. */
      // 切り上げ
      ROUND_UP = 1, //"ENUM_ROUNDING_ROUND_UP"

      /** The down 1 up 2. */
      // 一捨二入
      DOWN_1_UP_2 = 2, //"ENUM_ROUNDING_DOWN_1_UP_2"

      /** The down 2 up 3. */
      // 二捨三入
      DOWN_2_UP_3 = 3, //"ENUM_ROUNDING_DOWN_2_UP_3"

      /** The down 3 up 4. */
      // 三捨四入
      DOWN_3_UP_4 = 4, //"ENUM_ROUNDING_DOWN_3_UP_4"

      /** The down 4 up 5. */
      // 四捨五入
      DOWN_4_UP_5 = 5, //"ENUM_ROUNDING_DOWN_4_UP_5"

      /** The down 5 up 6. */
      // 五捨六入
      DOWN_5_UP_6 = 6, //"ENUM_ROUNDING_DOWN_5_UP_6"

      /** The down 6 up 7. */
      // 六捨七入
      DOWN_6_UP_7 = 7, //"ENUM_ROUNDING_DOWN_6_UP_7"

      /** The down 7 up 8. */
      // 七捨八入
      DOWN_7_UP_8 = 8, //"ENUM_ROUNDING_DOWN_7_UP_8"

      /** The down 8 up 9. */
      // 八捨九入
      DOWN_8_UP_9 = 9, //"ENUM_ROUNDING_DOWN_8_UP_9"
    }

    export enum CalculationSetting {
      Premium_Rate = 0,
      Unit_Price = 1
    }
  }
}