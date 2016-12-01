var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            //    export class ItemModel {
            //        //        id: any;
            //        //        name: any;
            //        //        constructor(id, name) {
            //        //            var self = this;
            //        //            this.id        //            this.name = name;
            //
            //        companyCode: any;
            //        personalWageName: any;
            //        constructor(companyCode, personalWageName) {
            //            var self = this;
            //            this.companyCode = companyCode;
            //            this.personalWageName = personalWageName;
            //        }
            //
            //
            //    }
            //    export class ListBox {
            //        
            //        itemName: KnockoutObservable<any>;
            //        currentCode: KnockoutObservable<any>;
            //        selectedCode: KnockoutObservable<any>;
            //        selectedName: KnockoutObservable<any>;
            //        isEnable: KnockoutObservable<any>;
            //        selectedCodes: KnockoutObservableArray<any>;
            //        personalWages: KnockoutObservableArray<service.model.PersonalWageNameDto>;
            //        
            //
            //        constructor() {
            //            var self = this;
            //            self.itemList = ko.observableArray([]);
            //            self.personalWages = ko.observableArray([]);
            //
            //            self.itemName = ko.observable('');
            //            self.currentCode = ko.observable('0');
            //            self.selectedCode = ko.observable('01');
            //            self.isEnable = ko.observable(true);
            //            self.selectedCodes = ko.observableArray([]);
            //            
            //        }
            //
            //    }
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //self.listBox = new ListBox();
                    self.itemList = ko.observableArray([]);
                    self.personalWages = ko.observableArray([]);
                    self.selectedCode = ko.observable(null);
                }
                ScreenModel.prototype.buildItemList = function () {
                    var self = this;
                    //            self.itemList.removeAll();
                    _.forEach(self.personalWages(), function (personalWage) {
                        var companyCode = personalWage.companyCode;
                        if (companyCode.length == 1) {
                            companyCode = "0" + personalWage.companyCode;
                        }
                        self.itemList.push(new ItemWage(personalWage.personalWageCode, personalWage.personalWageName));
                    });
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    //var categoryAtr = ko.observable(nts.uk.ui.windows.getShared('categoryAtr'));
                    var categoryAtr = 1;
                    h.service.getPersonalWageNames(categoryAtr).done(function (data) {
                        self.personalWages(data);
                        self.buildItemList();
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res);
                    });
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.chooseItem = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('selectedName', self.selectedName());
                    nts.uk.ui.windows.setShared('selectedCode', self.selectedCode());
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemWage = (function () {
                function ItemWage(wageCode, wageName) {
                    this.wageCode = wageCode;
                    this.wageName = wageName;
                }
                return ItemWage;
            }());
            viewmodel.ItemWage = ItemWage;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
