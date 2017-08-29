module nts.uk.at.view.kdw003.a.viewmodel {
    export class ScreenModel {

        dateRanger: KnockoutObservable<any>;
        displayFormatOptions: KnockoutObservableArray<any>;
        displayFormat: KnockoutObservable<number>;
        ccg001: any;
        lstEmployee: KnockoutObservableArray<any>;
        baseDate: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.dateRanger = ko.observable({
                startDate: '2000/01/01',
                endDate: '2000/01/31'
            });
            //            self.displayFormatOptions = ko.observableArray([
            //                {code: 0, name: nts.uk.resource.getText("Enum_DisplayFormat_Individual")},
            //                {code: 1, name: nts.uk.resource.getText("Enum_DisplayFormat_ByDate")},
            //                {code: 2, name: nts.uk.resource.getText("Enum_DisplayFormat_ErrorAlarm")}
            //            ]);
            self.displayFormatOptions = ko.observableArray([
                { code: 0, name: "個人別" },
                { code: 1, name: "日付別" },
                { code: 2, name: "エラー・アラーム" }
            ]);
            self.displayFormat = ko.observable(0);
            self.baseDate = ko.observable(new Date());
            self.ccg001 = {
                baseDate: self.baseDate,
                //Show/hide options
               isQuickSearchTab: true,
               isAdvancedSearchTab: true,
               isAllReferableEmployee: true,
               isOnlyMe: true,
               isEmployeeOfWorkplace: true,
               isEmployeeWorkplaceFollow: true,
               isMutipleCheck: true,
               isSelectAllEmployee: true,
                onSearchAllClicked: function(dataList) {
                    self.lstEmployee(dataList);
                },
                onSearchOnlyClicked: function(data) {
                    var dataEmployee = [];
                    dataEmployee.push(data);
                    self.lstEmployee(dataEmployee);
                },
                onSearchOfWorkplaceClicked: function(dataList) {
                    self.lstEmployee(dataList);
                },

                onSearchWorkplaceChildClicked: function(dataList) {
                    self.lstEmployee(dataList);
                },
                onApplyEmployee: function(dataEmployee) {
                    self.lstEmployee(dataEmployee);
                }

            }
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
}