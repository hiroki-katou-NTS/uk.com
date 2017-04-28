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
                    this.switchs = ko.observableArray([
                        { code: '0', name: '表示する' },
                        { code: '1', name: '表示しない' }
                    ]);
                    this.swapListColumns = ko.observableArray([
                        { headerText: 'コード', key: 'code', width: 50 },
                        { headerText: '名称', key: 'name', width: 180 }
                    ]);
                    this.itemListColumns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 50 },
                        { headerText: '名称', prop: 'name', width: 180 }]);
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
                        self.aggregateItemCategories()[val].clearError();
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
                        dfd.resolve();
                    });
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
                    this.temporarySelectedCode = ko.observable(null);
                    this.fullCategoryName = this.getFullCategoryName(categoryName, paymentType);
                    var masterItemInCate = masterItems.filter(function (item) { return item.category == categoryName; });
                    this.aggregateItemDetail = ko.observable(new AggregateItemDetail(paymentType, categoryName, masterItemInCate));
                    var self = this;
                    self.dirty = new nts.uk.ui.DirtyChecker(self.aggregateItemDetail);
                    self.aggregateItemSelectedCode.subscribe(function (code) {
                        if (self.temporarySelectedCode() == code) {
                            return;
                        }
                        self.confirmDirtyAndExecute(function () {
                            self.clearError();
                            if (code) {
                                self.temporarySelectedCode(code);
                                self.loadDetailAggregateItem(self.category, self.paymentType, code).done(function (res) {
                                    self.aggregateItemDetail(new AggregateItemDetail(paymentType, categoryName, masterItemInCate, res));
                                    self.dirty.reset();
                                    self.setStyle();
                                });
                            }
                            else {
                                self.temporarySelectedCode('');
                                self.aggregateItemDetail(new AggregateItemDetail(paymentType, categoryName, masterItemInCate));
                                self.dirty.reset();
                                self.setStyle();
                            }
                        }, function () {
                            self.aggregateItemSelectedCode(self.temporarySelectedCode());
                        });
                    });
                }
                AggregateCategory.prototype.getFullCategoryName = function (category, paymentType) {
                    var categoryName = '';
                    switch (category) {
                        case Category.PAYMENT:
                            categoryName = '支給';
                            break;
                        case Category.DEDUCTION:
                            categoryName = '控除';
                            break;
                        case Category.ATTENDANCE:
                            categoryName = '勤怠';
                            break;
                        default:
                            categoryName = '';
                    }
                    var paymentTypeName = paymentType == PaymentType.SALARY ? '給与' : '賞与';
                    return paymentTypeName + categoryName;
                };
                AggregateCategory.prototype.loadAggregateItemByCategory = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    i.service.findAggregateItemsByCategory(self.category, self.paymentType).done(function (res) {
                        self.itemList(res);
                        if (!self.aggregateItemSelectedCode()) {
                            var selectedCode = res.length > 0 ? res[0].code : null;
                            self.aggregateItemSelectedCode(selectedCode);
                        }
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
                    self.aggregateItemSelectedCode(null);
                };
                AggregateCategory.prototype.save = function () {
                    var self = this;
                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                        return;
                    }
                    i.service.save(self.aggregateItemDetail()).done(function () {
                        i.service.findAggregateItemsByCategory(self.category, self.paymentType).done(function (res) {
                            self.itemList(res);
                            self.dirty.reset();
                            self.aggregateItemSelectedCode(self.aggregateItemDetail().code());
                        });
                    }).fail(function (res) {
                        self.clearError();
                        if (res.messageId == 'ER005') {
                            $('#code-input').ntsError('set', res.message);
                        }
                        else {
                            $('#btnSave').ntsError('set', res.message);
                        }
                    });
                };
                AggregateCategory.prototype.remove = function () {
                    var self = this;
                    if (!self.aggregateItemSelectedCode()) {
                        return;
                    }
                    nts.uk.ui.dialog.confirm('出力項目設定からもデータを削除します。\r\nよろしいですか？').ifYes(function () {
                        i.service.remove(self.category, self.paymentType, self.aggregateItemSelectedCode()).done(function () {
                            var itemSelected = self.itemList().filter(function (item) { return item.code == self.aggregateItemSelectedCode(); })[0];
                            var indexSelected = self.itemList().indexOf(itemSelected);
                            self.itemList.remove(itemSelected);
                            if (self.itemList() && self.itemList().length > 0) {
                                var currentItem = self.itemList()[indexSelected];
                                if (currentItem) {
                                    self.aggregateItemSelectedCode(currentItem.code);
                                }
                                else {
                                    self.aggregateItemSelectedCode(self.itemList()[indexSelected - 1].code);
                                }
                            }
                            else {
                                self.aggregateItemSelectedCode(null);
                            }
                        });
                    });
                };
                AggregateCategory.prototype.close = function () {
                    var self = this;
                    self.confirmDirtyAndExecute(function () { return nts.uk.ui.windows.close(); });
                };
                AggregateCategory.prototype.setStyle = function () {
                    $('.master-table-label').attr('style', 'width: ' + $('#swap-list-gridArea1').width() + 'px');
                    $('.sub-table-label').attr('style', 'width: ' + $('#swap-list-gridArea2').width() + 'px');
                };
                AggregateCategory.prototype.clearError = function () {
                    if (nts.uk.ui._viewModel) {
                        $('#btnSave').ntsError('clear');
                        $('#code-input').ntsError('clear');
                        $('#name-input').ntsError('clear');
                    }
                };
                AggregateCategory.prototype.confirmDirtyAndExecute = function (functionToExecute, functionToExecuteIfNo) {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            functionToExecute();
                        }).ifNo(function () {
                            if (functionToExecuteIfNo) {
                                functionToExecuteIfNo();
                            }
                        });
                    }
                    else {
                        functionToExecute();
                    }
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
                    this.showNameZeroCode = ko.observable(this.showNameZeroValue() ? '0' : '1');
                    this.showValueZeroCode = ko.observable(this.showValueZeroValue() ? '0' : '1');
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