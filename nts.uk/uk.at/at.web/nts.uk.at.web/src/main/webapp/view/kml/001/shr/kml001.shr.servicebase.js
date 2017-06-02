var kml001;
(function (kml001) {
    var shr;
    (function (shr) {
        var servicebase;
        (function (servicebase) {
            var paths = {
                personCostCalculationSelect: "at/schedule/budget/premium/findPersonCostCalculationByCompanyID",
                personCostCalculationInsert: "at/schedule/budget/premium/insertPersonCostCalculation",
                personCostCalculationUpdate: "at/schedule/budget/premium/updatePersonCostCalculation",
                personCostCalculationDelete: "at/schedule/budget/premium/deletePersonCostCalculation",
                premiumItemSelect: "at/schedule/budget/premium/findPremiumItemByCompanyID",
                premiumItemUpdate: "at/schedule/budget/premium/updatePremiumItem",
                getAttendanceItems: "at/share/attendanceitem/getPossibleAttendanceItem",
                getAttendanceItemByType: "at/share/attendanceType/getByType/",
            };
            function personCostCalculationSelect() {
                return nts.uk.request.ajax(paths.personCostCalculationSelect);
            }
            servicebase.personCostCalculationSelect = personCostCalculationSelect;
            function personCostCalculationInsert(command) {
                return nts.uk.request.ajax(paths.personCostCalculationInsert, command);
            }
            servicebase.personCostCalculationInsert = personCostCalculationInsert;
            function personCostCalculationUpdate(command) {
                return nts.uk.request.ajax(paths.personCostCalculationUpdate, command);
            }
            servicebase.personCostCalculationUpdate = personCostCalculationUpdate;
            function personCostCalculationDelete(command) {
                return nts.uk.request.ajax(paths.personCostCalculationDelete, command);
            }
            servicebase.personCostCalculationDelete = personCostCalculationDelete;
            function premiumItemSelect() {
                return nts.uk.request.ajax(paths.premiumItemSelect);
            }
            servicebase.premiumItemSelect = premiumItemSelect;
            function premiumItemUpdate(command) {
                return nts.uk.request.ajax(paths.premiumItemUpdate, command);
            }
            servicebase.premiumItemUpdate = premiumItemUpdate;
            function getAttendanceItems(command) {
                return nts.uk.request.ajax(paths.getAttendanceItems, command);
            }
            servicebase.getAttendanceItems = getAttendanceItems;
            function getAttendanceItemByType(command) {
                return nts.uk.request.ajax(paths.getAttendanceItemByType + command);
            }
            servicebase.getAttendanceItemByType = getAttendanceItemByType;
        })(servicebase = shr.servicebase || (shr.servicebase = {}));
    })(shr = kml001.shr || (kml001.shr = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.shr.servicebase.js.map