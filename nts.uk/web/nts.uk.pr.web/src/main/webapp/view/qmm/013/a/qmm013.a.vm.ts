module qmm013.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        itemList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<PersonalUnitPrice>;
        displayAll: KnockoutObservable<boolean>;
        selectedId: KnockoutObservable<number>;
        roundingRules: KnockoutObservableArray<any>;
        isCompany: KnockoutObservable<boolean>;
        SEL_004: KnockoutObservableArray<any>;
        dirty: nts.uk.ui.DirtyChecker;
        isCreated: any;
        confirmDirty: boolean = false;
        messages: KnockoutObservableArray<any>;
        indexRow: KnockoutObservable<number>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.currentItem = ko.observable(new PersonalUnitPrice(null, null, null, null, null, null, null, null, null, null, null, null, null));
            self.currentCode = ko.observable();
            self.displayAll = ko.observable(true);
            self.selectedId = ko.observable(0);
            self.isCreated = ko.observable(true);
            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
            self.indexRow = ko.observable(0);
            self.columns = ko.observableArray([
                { headerText: 'コード', width: 100, key: 'code', formatter: _.escape },
                { headerText: '名称', width: 150, key: 'name', formatter: _.escape },
                { headerText: '廃止', width: 50, key: 'abolition', }
            ]);

            self.messages = ko.observableArray([
                { messageId: "AL001", message: "変更された内容が登録されていません。\r\nよろしいですか。" },
                { messageId: "ER001", message: "＊が入力されていません。" },
                { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n＊を確認してください。" },
                { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" }
            ]);
            self.itemList = ko.observableArray([
                new BoxModel(0, '全員一律で指定する'),
                new BoxModel(1, '給与約束形態ごとに指定する')
            ]);

            self.roundingRules = ko.observableArray([
                { code: '1', name: '対象' },
                { code: '0', name: '対象外' }
            ]);

            self.SEL_004 = ko.observableArray([
                { code: '0', name: '時間単価' },
                { code: '1', name: '金額' },
                { code: '2', name: 'その他' },
            ]);

            self.currentCode.subscribe(function(newCode) {
                if (!self.checkDirty()) {
                    self.selectedUnitPrice(newCode);
                    self.isCreated(false);
                }
                else {
                    if (self.confirmDirty) {
                        self.confirmDirty = false;
                        return;
                    }
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function() {
                        self.selectedUnitPrice(newCode);
                        self.isCreated(false);
                    }).ifNo(function() {
                        self.confirmDirty = true;
                        self.currentCode(self.currentItem().personalUnitPriceCode())
                    })
                }
            });

            self.displayAll.subscribe(function(newValue) {
                if (!self.checkDirty()) {
                    self.getPersonalUnitPriceList().done(function() {
                        self.selectedFirstUnitPrice();
                    });
                } else {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function() {
                        self.getPersonalUnitPriceList().done(function() {
                            self.selectedFirstUnitPrice();
                        });
                    })
                }
            });

            self.isCompany = ko.computed(function() {
                return !(self.currentItem().paymentSettingType() == 0);
            });

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.getPersonalUnitPriceList().done(function() {
                self.selectedFirstUnitPrice();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        getPersonalUnitPriceList() {
            var self = this;
            var dfd = $.Deferred();
            service.getPersonalUnitPriceList(self.displayAll()).done(function(data) {
                var items = _.map(data, function(item) {
                    var abolition = item.displayAtr == 0 ? '<i class="icon icon-close"></i>' : "";
                    return new ItemModel(item.personalUnitPriceCode, item.personalUnitPriceName, abolition);
                });
                self.items(items);
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }

        selectedUnitPrice(code) {
            var self = this;
            if (!code) {
                return;
            }
            service.getPersonalUnitPrice(code).done(function(res) {
                self.currentItem(self.selectedFirst(res));
                self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem)
            }).fail(function(res) {
                alert(res.message);
            });
        }

        selectedFirst(item): PersonalUnitPrice {
            var self = this;
            return new PersonalUnitPrice(
                item.personalUnitPriceCode,
                item.personalUnitPriceName,
                item.personalUnitPriceShortName,
                item.displayAtr == 0,
                item.uniteCode,
                item.paymentSettingType,
                item.fixPaymentAtr,
                item.fixPaymentMonthly,
                item.fixPaymentDayMonth,
                item.fixPaymentDaily,
                item.fixPaymentHoursly,
                item.unitPriceAtr,
                item.memo);
        }

        checkDirty(): boolean {
            var self = this;
            if (self.dirty.isDirty()) {
                return true;
            } else {
                return false;
            }
        };

        /**
         * 新規(Clear form)
         */
        btn_001() {
            var self = this;
            self.clearError();
            self.confirmDirty = true;
            if (!self.checkDirty()) {
                self.currentItem(new PersonalUnitPrice(null, null, null, false, "2", 1, null, false, false, false, false, false, null));
                self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                self.currentCode("");
                self.isCreated(true);
            } else {
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function() {
                    self.currentItem(new PersonalUnitPrice(null, null, null, false, "2", 1, null, false, false, false, false, false, null));
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.currentCode("");
                    self.isCreated(true);
                })
            }
        }

        /**
         * 登録(Add button)
         */
        btn_002() {
            var self = this;
            self.confirmDirty = true;
            var PersonalUnitPrice = {
                personalUnitPriceCode: self.currentItem().personalUnitPriceCode(),
                personalUnitPriceName: self.currentItem().personalUnitPriceName(),
                personalUnitPriceShortName: self.currentItem().personalUnitPriceShortName(),
                displayAtr: self.currentItem().displayAtr() ? 0 : 1,
                uniteCode: "2",
                paymentSettingType: self.currentItem().paymentSettingType(),
                fixPaymentAtr: self.currentItem().fixPaymentAtr(),
                fixPaymentMonthly: self.currentItem().fixPaymentMonthly() ? 0 : 1,
                fixPaymentDayMonth: self.currentItem().fixPaymentDayMonth() ? 0 : 1,
                fixPaymentDaily: self.currentItem().fixPaymentDaily() ? 0 : 1,
                fixPaymentHoursly: self.currentItem().fixPaymentHoursly() ? 0 : 1,
                unitPriceAtr: self.currentItem().unitPriceAtr() ? 0 : 1,
                memo: self.currentItem().memo()
            };
            service.addPersonalUnitPrice(self.isCreated(), PersonalUnitPrice).done(function() {
                self.getPersonalUnitPriceList();
                self.currentCode(PersonalUnitPrice.personalUnitPriceCode);
                self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
            }).fail(function(error) {
                if (error.messageId == self.messages()[2].messageId) {  // ER005
                    nts.uk.ui.dialog.alert(self.messages()[2].message);
                } else if (error.messageId == self.messages()[1].messageId) {
                    $('#INP_002').ntsError('set', self.messages()[1].message);
                    $('#INP_003').ntsError('set', self.messages()[1].message);
                }
            });
        }

        /**
         * 削除(Delete button)
         */
        btn_004() {
            var self = this;
            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                var data = {
                    personalUnitPriceCode: self.currentItem().personalUnitPriceCode()
                };
                self.indexRow(
                    _.findIndex(self.items(), function(x) {
                        return x.code === self.currentCode();
                    })
                );
                service.removePersonalUnitPrice(data).done(function() {
                    // reload list   
                    self.getPersonalUnitPriceList().done(function() {
                        if (self.items().length > self.indexRow()) {
                            self.currentCode(self.items()[self.indexRow()].code);
                        } else if (self.indexRow() == 0) {
                            self.btn_001();
                        } else if (self.items().length == self.indexRow() && self.indexRow() != 0) {
                            self.currentCode(self.items()[self.indexRow() - 1].code);
                        }
                    });
                }).fail(function(error) {
                    alert(error.message);
                });
            })
        }

        selectedFirstUnitPrice() {
            var self = this;
            if (self.items().length > 0) {
                self.currentCode(self.items()[0].code);
                self.selectedUnitPrice(self.items()[0].code);
            } else {
                self.btn_001();
            }
        }

        clearError() {
            $('#INP_002').ntsError('clear');
            $('#INP_003').ntsError('clear');
        }

    }

    class ItemModel {
        code: string;
        name: string;
        abolition: string;

        constructor(code: string, name: string, abolition: string) {
            this.code = code;
            this.name = name;
            this.abolition = abolition;
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class PersonalUnitPrice {
        personalUnitPriceCode: KnockoutObservable<string>;
        personalUnitPriceName: KnockoutObservable<string>;
        personalUnitPriceShortName: KnockoutObservable<string>;
        displayAtr: KnockoutObservable<boolean>;
        uniteCode: KnockoutObservable<string>;
        paymentSettingType: KnockoutObservable<number>;
        fixPaymentAtr: KnockoutObservable<boolean>;
        fixPaymentMonthly: KnockoutObservable<boolean>;
        fixPaymentDayMonth: KnockoutObservable<boolean>;
        fixPaymentDaily: KnockoutObservable<boolean>;
        fixPaymentHoursly: KnockoutObservable<boolean>;
        unitPriceAtr: KnockoutObservable<boolean>;
        memo: KnockoutObservable<string>;

        constructor(personalUnitPriceCode: string, personalUnitPriceName: string, personalUnitPriceShortName: string,
            displayAtr: boolean, uniteCode: string, paymentSettingType: number, fixPaymentAtr: boolean,
            fixPaymentMonthly: boolean, fixPaymentDayMonth: boolean, fixPaymentDaily: boolean, fixPaymentHoursly: boolean,
            unitPriceAtr: boolean, memo: string) {
            this.personalUnitPriceCode = ko.observable(personalUnitPriceCode);
            this.personalUnitPriceName = ko.observable(personalUnitPriceName);
            this.personalUnitPriceShortName = ko.observable(personalUnitPriceShortName);
            this.displayAtr = ko.observable(displayAtr);
            this.uniteCode = ko.observable(uniteCode);
            this.paymentSettingType = ko.observable(paymentSettingType);
            this.fixPaymentAtr = ko.observable(fixPaymentAtr);
            this.fixPaymentMonthly = ko.observable(fixPaymentMonthly);
            this.fixPaymentDayMonth = ko.observable(fixPaymentDayMonth);
            this.fixPaymentDaily = ko.observable(fixPaymentDaily);
            this.fixPaymentHoursly = ko.observable(fixPaymentHoursly);
            this.unitPriceAtr = ko.observable(unitPriceAtr);
            this.memo = ko.observable(memo);
        }
    }
}