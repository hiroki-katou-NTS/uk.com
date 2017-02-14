var qet001;
(function (qet001) {
    var i;
    (function (i_1) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.aggregateItemCategories = ko.observableArray([]);
                    this.masterItems = ko.observableArray([]);
                    this.selectedTab = ko.observable(0);
                    var self = this;
                    $("#sidebar-area > ul > li").on('click', function () {
                        self.selectedTab($("#sidebar-area > ul > li").index(this));
                    });
                    self.selectedTab.subscribe(function (val) {
                        if (val == undefined || val == null) {
                            return;
                        }
                        self.aggregateItemCategories()[val].loadAggregateItemByCategory();
                    });
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    i_1.service.findMasterItems().done(function (res) {
                        self.masterItems(res);
                        self.aggregateItemCategories.push(new AggregateCategory(PaymentType.SALARY, Category.PAYMENT, res));
                        self.aggregateItemCategories.push(new AggregateCategory(PaymentType.SALARY, Category.DEDUCTION, res));
                        self.aggregateItemCategories.push(new AggregateCategory(PaymentType.SALARY, Category.ATTENDANCE, res));
                        self.aggregateItemCategories.push(new AggregateCategory(PaymentType.BONUS, Category.PAYMENT, res));
                        self.aggregateItemCategories.push(new AggregateCategory(PaymentType.BONUS, Category.DEDUCTION, res));
                        self.aggregateItemCategories.push(new AggregateCategory(PaymentType.BONUS, Category.ATTENDANCE, res));
                        self.aggregateItemCategories()[0].loadAggregateItemByCategory();
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var AggregateCategory = (function () {
                function AggregateCategory(paymentType, categoryName, masterItems) {
                    this.itemList = ko.observableArray([]);
                    this.category = categoryName;
                    this.paymentType = paymentType;
                    this.aggregateItemSelectedCode = ko.observable(null);
                    this.itemListColumns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 90 },
                        { headerText: '名称', prop: 'name', width: 100 }]);
                    var masterItemInCate = masterItems.filter(function (item) { return item.category == categoryName; });
                    this.aggregateItemDetail = ko.observable(new AggregateItemDetail(paymentType, categoryName, masterItemInCate));
                    var self = this;
                    self.aggregateItemSelectedCode.subscribe(function (code) {
                        if (code == undefined || code == null || code == '') {
                            return;
                        }
                        self.loadDetailAggregateItem(code).done(function (res) {
                            self.aggregateItemDetail(new AggregateItemDetail(paymentType, categoryName, masterItemInCate, res));
                        });
                    });
                }
                AggregateCategory.prototype.loadAggregateItemByCategory = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    var items = [];
                    for (var i = 0; i < 10; i++) {
                        items.push({ code: 'AGG' + i, name: 'Aggregate item ' + i, category: self.category,
                            paymentType: self.paymentType, showNameZeroValue: true, showValueZeroValue: true });
                    }
                    self.itemList(items);
                    dfd.resolve();
                    return dfd.promise();
                };
                AggregateCategory.prototype.loadDetailAggregateItem = function (code) {
                    var dfd = $.Deferred();
                    var self = this;
                    var selectedCode = code;
                    var selectedNumber = parseInt(selectedCode.substring(selectedCode.length - 1, selectedCode.length));
                    var item = { code: selectedCode, name: 'Aggregate item ' + selectedNumber,
                        category: self.category, paymentType: self.paymentType, showNameZeroValue: true,
                        showValueZeroValue: true, subItems: [
                            { code: 'MI' + selectedNumber, name: 'sub item ' + selectedNumber },
                            { code: 'MI' + selectedNumber + 2, name: 'sub item ' + selectedNumber + 2 },
                        ] };
                    dfd.resolve(item);
                    return dfd.promise();
                };
                AggregateCategory.prototype.switchToCreateMode = function () {
                };
                AggregateCategory.prototype.save = function () {
                };
                AggregateCategory.prototype.remove = function () {
                };
                AggregateCategory.prototype.close = function () {
                };
                return AggregateCategory;
            }());
            viewmodel.AggregateCategory = AggregateCategory;
            var AggregateItemDetail = (function () {
                function AggregateItemDetail(paymentType, category, masterItems, item) {
                    this.code = item == undefined ? ko.observable(null) : ko.observable(item.code);
                    this.name = item == undefined ? ko.observable(null) : ko.observable(item.name);
                    this.paymentType = paymentType;
                    this.category = category;
                    this.showNameZeroValue = item == undefined ? ko.observable(true)
                        : ko.observable(item.showNameZeroValue);
                    this.showValueZeroValue = item == undefined ? ko.observable(true)
                        : ko.observable(item.showValueZeroValue);
                    this.subItems = item == undefined ? ko.observableArray([]) : ko.observableArray(item.subItems);
                    this.switchs = ko.observableArray([
                        { code: '0', name: '表示する' },
                        { code: '1', name: '表示しない' }
                    ]);
                    this.showNameZeroCode = ko.observable(this.showNameZeroValue() ? '0' : '1');
                    this.showValueZeroCode = ko.observable(this.showValueZeroValue() ? '0' : '1');
                    this.swapListColumns = ko.observableArray([
                        { headerText: 'コード', key: 'code', width: 100 },
                        { headerText: '名称', key: 'name', width: 160 }
                    ]);
                    var self = this;
                    self.showNameZeroValue = ko.computed(function () {
                        return self.showNameZeroCode() == '0';
                    });
                    self.showValueZeroValue = ko.computed(function () {
                        return self.showValueZeroCode() == '0';
                    });
                    var subItemCodes = self.subItems().map(function (item) { return item.code; });
                    var masterItemsExcluded = masterItems.filter(function (item) { return subItemCodes.indexOf(item.code) == -1; });
                    self.masterItems = ko.observableArray(masterItemsExcluded);
                }
                return AggregateItemDetail;
            }());
            viewmodel.AggregateItemDetail = AggregateItemDetail;
            var Category = (function () {
                function Category() {
                }
                Category.PAYMENT = 'Payment';
                Category.DEDUCTION = 'Deduction';
                Category.ATTENDANCE = 'Attendance';
                return Category;
            }());
            viewmodel.Category = Category;
            var PaymentType = (function () {
                function PaymentType() {
                }
                PaymentType.SALARY = 'Salary';
                PaymentType.BONUS = 'Bonus';
                return PaymentType;
            }());
            viewmodel.PaymentType = PaymentType;
        })(viewmodel = i_1.viewmodel || (i_1.viewmodel = {}));
    })(i = qet001.i || (qet001.i = {}));
})(qet001 || (qet001 = {}));
