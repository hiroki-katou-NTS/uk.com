var qet001;
(function (qet001) {
    var i;
    (function (i_1) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.switchToCreateMode = function () {
                };
                ScreenModel.prototype.save = function () {
                };
                ScreenModel.prototype.remove = function () {
                };
                ScreenModel.prototype.close = function () {
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
                    var masterItemInCate = masterItems.filter(function (item) { return item.paymentType == paymentType
                        && item.category == categoryName; });
                    this.aggregateItemDetail = ko.observable(new AggregateItemDetail(paymentType, categoryName, masterItemInCate));
                }
                AggregateCategory.prototype.loadAggregateItemByCategory = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    for (var i = 0; i < 10; i++) {
                    }
                    return dfd.promise();
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
                    this.showNameZeroValue = item == undefined ? ko.observable(false)
                        : ko.observable(item.showNameZeroValue);
                    this.showValueZeroValue = item == undefined ? ko.observable(false)
                        : ko.observable(item.showValueZeroValue);
                    this.masterItems = ko.observableArray(masterItems);
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
