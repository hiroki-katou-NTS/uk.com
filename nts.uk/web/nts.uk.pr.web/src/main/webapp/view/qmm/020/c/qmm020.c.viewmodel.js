var qmm020;
(function (qmm020) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.itemList = ko.observableArray([]);
                    self.selectedCode = ko.observableArray([]);
                    self.isEnable = ko.observable(true);
                    self.selectedList = ko.observableArray([]);
                    var employment1 = ko.mapping.fromJS([
                        { "NO": 1, "ID": "000000001", "Name": "正社員", "PaymentDocID": "K001", "PaymentDocName": "給与明細書001", "BonusDocID": "S001", "BonusDocName": "賞与明細書001" },
                        { "NO": 2, "ID": "000000002", "Name": "DucPham社員", "PaymentDocID": "K002", "PaymentDocName": "給与明細書002", "BonusDocID": "S001", "BonusDocName": "賞与明細書002" },
                        { "NO": 3, "ID": "000000003", "Name": "HoangMai社員", "PaymentDocID": "K003", "PaymentDocName": "給与明細書003", "BonusDocID": "S001", "BonusDocName": "賞与明細書003" }
                    ]);
                    var employment2 = ko.mapping.fromJS([
                        { "NO": 1, "ID": "000000004", "Name": "ABC社員", "PaymentDocID": "K001", "PaymentDocName": "給与明細書001", "BonusDocID": "S001", "BonusDocName": "賞与明細書001" },
                        { "NO": 2, "ID": "000000005", "Name": "DEF社員", "PaymentDocID": "K002", "PaymentDocName": "給与明細書002", "BonusDocID": "S001", "BonusDocName": "賞与明細書002" },
                        { "NO": 3, "ID": "000000006", "Name": "GHK社員", "PaymentDocID": "K003", "PaymentDocName": "給与明細書003", "BonusDocID": "S001", "BonusDocName": "賞与明細書003" }
                    ]);
                    self.dataSource = ko.mapping.toJS(employment1());
                    $("#C_LST_001").igGrid({
                        columns: [
                            { headerText: "", key: "NO", dataType: "string", width: "20px" },
                            { headerText: "コード", key: "ID", dataType: "string", width: "100px" },
                            { headerText: "名称", key: "Name", dataType: "string", width: "200px" },
                            { headerText: "", key: "PaymentDocID", dataType: "string", hidden: true },
                            { headerText: "", key: "PaymentDocName", dataType: "string", hidden: true },
                            { headerText: "", key: "BonusDocID", dataType: "string", hidden: true },
                            { headerText: "", key: "BonusDocName", dataType: "string", hidden: true },
                            {
                                headerText: "給与明細書", key: "PaymentDocID", dataType: "string", width: "250px", unbound: true,
                                template: "<input type='button' id='" + "C_BTN_001" + "' value='選択'/><label style='margin-left:5px;'>${PaymentDocID}</label><label style='margin-left:15px;'>${PaymentDocName}</label>"
                            },
                            {
                                headerText: "賞与明細書", key: "BonusDoc", dataType: "string", width: "20%", unbound: true,
                                template: "<input type='button' id='" + "C_BTN_002" + "' value='選択'/><label style='margin-left:5px;'>${BonusDocID}</label><label style='margin-left:15px;'>${BonusDocName}</label>"
                            },
                        ],
                        features: [{
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true,
                                activation: false,
                                rowSelectionChanged: this.selectionChanged.bind(this)
                            }],
                        virtualization: true,
                        virtualizationMode: 'continuous',
                        width: "800px",
                        height: "240px",
                        primaryKey: "ID",
                        dataSource: ko.mapping.toJS(employment1())
                    });
                    self.selectedCode.subscribe(function (codeChange) {
                        if (codeChange.length > 0) {
                            var row = _.find(self.itemList(), function (item) {
                                var code = self.selectedCode();
                            });
                        }
                    });
                    var openPaymentDialog = function (evt, ui) {
                        if (ui.colKey === "PaymentDocID") {
                            var row = _.find(employment1(), function (item) {
                            });
                        }
                    };
                    self.start();
                }
                ScreenModel.prototype.selectionChanged = function (evt, ui) {
                    var selectedRows = ui.selectedRows;
                    var arr = [];
                    for (var i = 0; i < selectedRows.length; i++) {
                        arr.push("" + selectedRows[i].id);
                    }
                    this.selectedList(arr);
                };
                ;
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    c.service.getEmployeeAllotHeaderList().done(function (employeeAllotHeader) {
                        if (employeeAllotHeader.length > 0) {
                            self.employeeAllotHead = employeeAllotHeader;
                            var _items_1 = [];
                            _.forEach(employeeAllotHeader, function (histEm, i) {
                                _items_1.push(new ItemModel(histEm.historyId, histEm.startYM + " ~ " + histEm.endYM));
                                if (i == employeeAllotHeader.length - 1) {
                                    self.itemList(_items_1);
                                }
                            });
                        }
                        else {
                            alert('dddd');
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        alert(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openJDialog = function () {
                };
                ScreenModel.prototype.openKDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '明細書の紐ずけ＞履歴編集' }).onClosed(function () {
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
            var EmployeeList = (function () {
                function EmployeeList(code, name, paymentCode, paymentName, bonusCode, bonusName) {
                    this.code = code;
                    this.name = name;
                    this.paymentCode = paymentCode;
                    this.paymentName = paymentName;
                    this.bonusCode = bonusCode;
                    this.bonusName = bonusName;
                }
                return EmployeeList;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm020.c || (qmm020.c = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.c.viewmodel.js.map