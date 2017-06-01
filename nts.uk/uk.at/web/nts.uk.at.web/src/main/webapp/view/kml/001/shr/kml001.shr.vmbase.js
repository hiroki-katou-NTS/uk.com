var kml001;
(function (kml001) {
    var shr;
    (function (shr) {
        var vmbase;
        (function (vmbase) {
            var GridPersonCostCalculation = (function () {
                function GridPersonCostCalculation(dateRange) {
                    var self = this;
                    self.dateRange = dateRange;
                }
                return GridPersonCostCalculation;
            }());
            vmbase.GridPersonCostCalculation = GridPersonCostCalculation;
            var PersonCostCalculation = (function () {
                function PersonCostCalculation(companyID, historyID, startDate, endDate, unitPrice, memo, premiumSets) {
                    var self = this;
                    self.companyID = ko.observable(companyID);
                    self.historyID = ko.observable(historyID);
                    self.startDate = ko.observable(startDate);
                    self.endDate = ko.observable(endDate);
                    self.unitPrice = ko.observable(unitPrice);
                    self.memo = ko.observable(memo);
                    var koPremiumSets = [];
                    premiumSets.forEach(function (premiumSet) { koPremiumSets.push(ProcessHandler.fromObjectPremiumSet(premiumSet)); });
                    self.premiumSets = ko.observableArray(koPremiumSets);
                }
                return PersonCostCalculation;
            }());
            vmbase.PersonCostCalculation = PersonCostCalculation;
            var PremiumSetting = (function () {
                function PremiumSetting(companyID, historyID, premiumID, rate, attendanceID, name, displayNumber, useAtr, attendanceItems) {
                    var self = this;
                    self.companyID = ko.observable(companyID);
                    self.historyID = ko.observable(historyID);
                    self.premiumID = ko.observable(premiumID);
                    self.rate = ko.observable(rate);
                    self.attendanceID = ko.observable(attendanceID);
                    self.name = ko.observable(name);
                    self.displayNumber = ko.observable(displayNumber);
                    self.useAtr = ko.observable(useAtr);
                    var koAttendanceItems = [];
                    attendanceItems.forEach(function (item) {
                        koAttendanceItems.push(new vmbase.AttendanceItem(item.shortAttendanceID, item.name));
                    });
                    self.attendanceItems = ko.observableArray(koAttendanceItems);
                }
                return PremiumSetting;
            }());
            vmbase.PremiumSetting = PremiumSetting;
            var AttendanceItem = (function () {
                function AttendanceItem(shortAttendanceID, name) {
                    var self = this;
                    self.shortAttendanceID = shortAttendanceID;
                    self.name = name;
                }
                return AttendanceItem;
            }());
            vmbase.AttendanceItem = AttendanceItem;
            var PremiumItem = (function () {
                function PremiumItem(companyID, iD, attendanceID, name, displayNumber, useAtr) {
                    var self = this;
                    self.companyID = ko.observable(companyID);
                    self.iD = ko.observable(iD);
                    self.attendanceID = ko.observable(attendanceID);
                    self.name = ko.observable(name);
                    self.displayNumber = ko.observable(displayNumber);
                    self.useAtr = ko.observable(useAtr);
                }
                return PremiumItem;
            }());
            vmbase.PremiumItem = PremiumItem;
            var ProcessHandler = (function () {
                function ProcessHandler() {
                }
                /**
                 * convert PersonCostCalculation JS object to PersonCostCalculation knockoutJS object
                 */
                ProcessHandler.fromObjectPerconCost = function (object) {
                    return new PersonCostCalculation(object.companyID, object.historyID, object.startDate, object.endDate, object.unitPrice, object.memo, object.premiumSets);
                };
                /**
                 * convert PersonCostCalculation knockoutJS object to PersonCostCalculation JS object
                 */
                ProcessHandler.toObjectPersonCost = function (koObject) {
                    var premiumSets = [];
                    koObject.premiumSets().forEach(function (koPremiumSet) { premiumSets.push(ProcessHandler.toObjectPremiumSet(koPremiumSet)); });
                    return {
                        companyID: koObject.companyID(),
                        historyID: koObject.historyID(),
                        startDate: koObject.startDate(),
                        endDate: koObject.endDate(),
                        unitPrice: koObject.unitPrice(),
                        memo: koObject.memo(),
                        premiumSets: premiumSets
                    };
                };
                /**
                 * convert PremiumSetting JS object to PremiumSetting knockoutJS object
                 */
                ProcessHandler.fromObjectPremiumSet = function (object) {
                    return new PremiumSetting(object.companyID, object.historyID, object.premiumID, object.rate, object.attendanceID, object.name, object.displayNumber, object.useAtr, object.attendanceItems);
                };
                /**
                 * convert PremiumSetting knockoutJS object to PremiumSetting JS object
                 */
                ProcessHandler.toObjectPremiumSet = function (koObject) {
                    return {
                        companyID: koObject.companyID(),
                        historyID: koObject.historyID(),
                        premiumID: koObject.premiumID(),
                        rate: koObject.rate(),
                        attendanceID: koObject.attendanceID(),
                        name: koObject.name(),
                        displayNumber: koObject.displayNumber(),
                        useAtr: koObject.useAtr(),
                        attendanceItems: _.map(koObject.attendanceItems(), function (item) { return { shortAttendanceID: item.shortAttendanceID, name: item.name }; })
                    };
                };
                /**
                 * get one day before input date as string format
                 */
                ProcessHandler.getOneDayBefore = function (date) {
                    var numberDate = Date.parse(date);
                    // get before day by subtraction one day milliseconds
                    var dayBefore = new Date(numberDate - 24 * 60 * 60 * 1000);
                    return dayBefore.getFullYear() + '/' + (dayBefore.getMonth() + 1) + '/' + dayBefore.getDate();
                };
                /**
                 * get one day after input date as string format
                 */
                ProcessHandler.getOneDayAfter = function (date) {
                    var numberDate = Date.parse(date);
                    // get after day by addition one day milliseconds
                    var dayBefore = new Date(numberDate + 24 * 60 * 60 * 1000);
                    return dayBefore.getFullYear() + '/' + (dayBefore.getMonth() + 1) + '/' + dayBefore.getDate();
                };
                /**
                 * check input date in range, if date in range return true
                 */
                ProcessHandler.validateDateRange = function (inputDate, startDate, endDate) {
                    return (Date.parse(startDate) <= Date.parse(inputDate)) && (Date.parse(inputDate) <= Date.parse(endDate));
                };
                /**
                 * check input date before or equal date
                 */
                ProcessHandler.validateDateInput = function (inputDate, date) {
                    return (Date.parse(inputDate) <= Date.parse(date));
                };
                return ProcessHandler;
            }());
            vmbase.ProcessHandler = ProcessHandler;
            (function (UseAtr) {
                UseAtr[UseAtr["NotUse"] = 0] = "NotUse";
                UseAtr[UseAtr["Use"] = 1] = "Use";
            })(vmbase.UseAtr || (vmbase.UseAtr = {}));
            var UseAtr = vmbase.UseAtr;
            (function (UnitPrice) {
                UnitPrice[UnitPrice["Price_1"] = 0] = "Price_1";
                UnitPrice[UnitPrice["Price_2"] = 1] = "Price_2";
                UnitPrice[UnitPrice["Price_3"] = 2] = "Price_3";
                UnitPrice[UnitPrice["Standard"] = 3] = "Standard";
                UnitPrice[UnitPrice["Contract"] = 4] = "Contract";
            })(vmbase.UnitPrice || (vmbase.UnitPrice = {}));
            var UnitPrice = vmbase.UnitPrice;
            (function (MSG) {
                MSG[MSG["MSG015"] = "登録しました。"] = "MSG015";
                MSG[MSG["MSG018"] = "選択中のデータを削除しますか？"] = "MSG018";
                MSG[MSG["MSG065"] = "最新の履歴の有効開始日より以前の有効開始日を登録できません。"] = "MSG065";
                MSG[MSG["MSG066"] = "割増項目が設定されてません。"] = "MSG066";
                MSG[MSG["MSG102"] = "最新の履歴開始日以前に履歴を追加することはできません。"] = "MSG102";
                MSG[MSG["MSG128"] = "最後の履歴を削除することができません。"] = "MSG128";
            })(vmbase.MSG || (vmbase.MSG = {}));
            var MSG = vmbase.MSG;
        })(vmbase = shr.vmbase || (shr.vmbase = {}));
    })(shr = kml001.shr || (kml001.shr = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.shr.vmbase.js.map