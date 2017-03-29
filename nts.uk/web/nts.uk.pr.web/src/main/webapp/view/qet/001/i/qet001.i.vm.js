var qet001;
(function (qet001) {
    var i;
    (function (i) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.aggregateItemCategories = ko.observableArray([]);
                    this.masterItems = ko.observableArray([]);
                    this.selectedTab = ko.observable(0);
                    var self = this;
                    $("#sidebar-area > div > ul > li").on('click', function () {
                        var index = $("#sidebar-area > div > ul > li").index(this);
                        $("#sidebar").ntsSideBar("active", index);
                        self.selectedTab(index);
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
                    i.service.findMasterItems().done(function (res) {
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
                ScreenModel.prototype.afterRender = function () {
                    $('.master-table-label').width($('#swap-list-gridArea1').width());
                    $('.sub-table-label').width($('#swap-list-gridArea2').width());
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
                    self.dirty = new nts.uk.ui.DirtyChecker(self.aggregateItemDetail);
                    self.aggregateItemSelectedCode.subscribe(function (code) {
                        if (code == undefined || code == null || code == '') {
                            self.aggregateItemDetail(new AggregateItemDetail(paymentType, categoryName, masterItemInCate));
                            self.setStyle();
                            self.dirty.reset();
                            return;
                        }
                        self.loadDetailAggregateItem(self.category, self.paymentType, code).done(function (res) {
                            self.aggregateItemDetail(new AggregateItemDetail(paymentType, categoryName, masterItemInCate, res));
                            self.dirty.reset();
                            self.setStyle();
                        });
                    });
                }
                AggregateCategory.prototype.loadAggregateItemByCategory = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    i.service.findAggregateItemsByCategory(self.category, self.paymentType).done(function (res) {
                        self.itemList(res);
                        self.aggregateItemSelectedCode(res.length > 0 ? res[0].code : null);
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                AggregateCategory.prototype.loadDetailAggregateItem = function (category, paymentType, code) {
                    var dfd = $.Deferred();
                    i.service.findAggregateItemDetail(category, paymentType, code).done(function (data) {
                        dfd.resolve(data);
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                AggregateCategory.prototype.switchToCreateMode = function () {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm('変更された内容が登録されていません。\r\nよろしいですか。').ifYes(function () {
                            self.aggregateItemSelectedCode(null);
                        });
                    }
                };
                AggregateCategory.prototype.save = function () {
                    var self = this;
                    $('#code-input').ntsError('clear');
                    $('#name-input').ntsError('clear');
                    $('#code-input').ntsEditor('validate');
                    $('#name-input').ntsEditor('validate');
                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                        return;
                    }
                    i.service.save(self.aggregateItemDetail()).done(function () {
                        nts.uk.ui.dialog.alert('Save success!');
                        self.loadAggregateItemByCategory();
                    }).fail(function (res) {
                        $('#code-input').ntsError('set', res.message);
                    });
                };
                AggregateCategory.prototype.remove = function () {
                    var self = this;
                    if (self.aggregateItemSelectedCode() == null) {
                        return;
                    }
                    i.service.remove(self.category, self.paymentType, self.aggregateItemSelectedCode()).done(function () {
                        var itemSelected = self.itemList().filter(function (item) { return item.code == self.aggregateItemSelectedCode(); })[0];
                        var indexSelected = self.itemList().indexOf(itemSelected);
                        self.itemList.remove(itemSelected);
                        if (self.itemList.length == 0) {
                            self.aggregateItemSelectedCode(null);
                            return;
                        }
                        if (self.itemList()[indexSelected]) {
                            self.aggregateItemSelectedCode(self.itemList()[indexSelected].code);
                            return;
                        }
                        self.aggregateItemSelectedCode(self.itemList()[indexSelected - 1].code);
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                AggregateCategory.prototype.close = function () {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm('変更された内容が登録されていません。\r\nよろしいですか。').ifYes(function () {
                            nts.uk.ui.windows.close();
                        });
                    }
                };
                AggregateCategory.prototype.setStyle = function () {
                    $('.master-table-label').attr('style', 'width: ' + $('#swap-list-gridArea1').width() + 'px');
                    $('.sub-table-label').attr('style', 'width: ' + $('#swap-list-gridArea2').width() + 'px');
                };
                return AggregateCategory;
            }());
            viewmodel.AggregateCategory = AggregateCategory;
            var AggregateItemDetail = (function () {
                function AggregateItemDetail(paymentType, category, masterItems, item) {
                    this.code = item == undefined ? ko.observable('') : ko.observable(item.code);
                    this.name = item == undefined ? ko.observable('') : ko.observable(item.name);
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
                    this.createMode = ko.observable(item == undefined);
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
        })(viewmodel = i.viewmodel || (i.viewmodel = {}));
    })(i = qet001.i || (qet001.i = {}));
})(qet001 || (qet001 = {}));
//# sourceMappingURL=qet001.i.vm.js.map