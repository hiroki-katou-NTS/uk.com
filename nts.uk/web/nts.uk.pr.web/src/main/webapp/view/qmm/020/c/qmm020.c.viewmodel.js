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
                    // Array Data 1 
                    var employment1 = ko.mapping.fromJS([
                        { "NO": 1, "ID": "000000001", "Name": "正社員", "PaymentDocID": "K001", "PaymentDocName": "給与明細書001", "BonusDocID": "S001", "BonusDocName": "賞与明細書001" },
                        { "NO": 2, "ID": "000000002", "Name": "DucPham社員", "PaymentDocID": "K002", "PaymentDocName": "給与明細書002", "BonusDocID": "S001", "BonusDocName": "賞与明細書002" },
                        { "NO": 3, "ID": "000000003", "Name": "HoangMai社員", "PaymentDocID": "K003", "PaymentDocName": "給与明細書003", "BonusDocID": "S001", "BonusDocName": "賞与明細書003" }
                    ]);
                    // Array Data 2 
                    var employment2 = ko.mapping.fromJS([
                        { "NO": 1, "ID": "000000004", "Name": "ABC社員", "PaymentDocID": "K001", "PaymentDocName": "給与明細書001", "BonusDocID": "S001", "BonusDocName": "賞与明細書001" },
                        { "NO": 2, "ID": "000000005", "Name": "DEF社員", "PaymentDocID": "K002", "PaymentDocName": "給与明細書002", "BonusDocID": "S001", "BonusDocName": "賞与明細書002" },
                        { "NO": 3, "ID": "000000006", "Name": "GHK社員", "PaymentDocID": "K003", "PaymentDocName": "給与明細書003", "BonusDocID": "S001", "BonusDocName": "賞与明細書003" }
                    ]);
                    //self.buildGrid("#C_LST_001", "C_BTN_001", "C_BTN_002");
                    self.dataSource = ko.mapping.toJS(employment1());
                    //console.log(self.dataSource);
                    //Build IgGrid
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
                    //subcribe list box's change
                    self.selectedCode.subscribe(function (codeChange) {
                        if (codeChange.length > 0) {
                            //get ValueName when click to item in HistoryList
                            var row = _.find(self.itemList(), function (item) {
                                var code = self.selectedCode();
                                //                        if (code === item.code) {
                                //                            //self.selectedName(item.name);
                                //                        }
                            });
                        }
                    });
                    //SCREEN C
                    //Event : Click to button Sentaku on igGrid
                    var openPaymentDialog = function (evt, ui) {
                        if (ui.colKey === "PaymentDocID") {
                            //Gọi hàm open SubWindow
                            //Khi close Subwindow, get dc cái new object(ID, Name... )
                            var row = _.find(employment1(), function (item) {
                                //return item.ID() === ui.rowKey;
                            });
                        }
                    };
                    self.start();
                }
                //Selected changed
                ScreenModel.prototype.selectionChanged = function (evt, ui) {
                    //console.log(evt.type);
                    var selectedRows = ui.selectedRows;
                    var arr = [];
                    for (var i = 0; i < selectedRows.length; i++) {
                        arr.push("" + selectedRows[i].id);
                    }
                    this.selectedList(arr);
                };
                ;
                //Search Employment
                //        searchEmployment(idInputSearch: string, idList: string) {
                //            var self = this;
                //            let textSearch: string = $("#C_INP_001").val().trim();
                //            if (textSearch.length === 0) {
                //                nts.uk.ui.dialog.alert("コード/名称が入力されていません。");
                //            } else {
                //                if (self.textSearch !== textSearch) {
                //                    let searchResult = _.filter(self.products, function(item) {
                //                        return _.includes(item.ID, textSearch) || _.includes(item.Name, textSearch);
                //                    });
                //                    self.queueSearchResult = [];
                //                    for (let item of searchResult) {
                //                        self.queueSearchResult.push(item);
                //                    }
                //                    self.textSearch = textSearch;
                //                }
                //                if (self.queueSearchResult.length === 0) {
                //                    nts.uk.g.alert("対象データがありません。");
                //                } else {
                //                    let firstResult: ItemModel = _.first(self.queueSearchResult);
                //                    //self.listBox().selectedCode(firstResult.id);
                //                    $("#grid").igGridSelection("selectRowById", firstResult.ID);
                //                    self.queueSearchResult.shift();
                //                    self.queueSearchResult.push(firstResult);
                //                }
                //            }
                //        }
                // start function
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    //Get list startDate, endDate of History  
                    c.service.getEmployeeAllotHeaderList().done(function (data) {
                        if (data.length > 0) {
                            //TODO 2017.03.14
                            _.forEach(data, function (item) {
                                self.itemList.push(new ItemModel(item.historyId, item.startYm + ' ~ ' + item.endYm));
                            });
                        }
                        else {
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        // Alert message
                        alert(res);
                    });
                    // Return.
                    return dfd.promise();
                };
                //Open dialog Add History
                ScreenModel.prototype.openJDialog = function () {
                    //            var self = this;
                    //            //console.log(self);
                    //            //alert($($("#sidebar-area .navigator a.active")[0]).attr("href"));
                    //            var valueShareJDialog = $($("#sidebar-area .navigator a.active")[0]).attr("href");
                    //            //Get value TabCode + value of selected Name in History List
                    //            valueShareJDialog = valueShareJDialog + "~" + self.selectedName();
                    //            nts.uk.ui.windows.setShared('valJDialog', valueShareJDialog);
                    //            nts.uk.ui.windows.sub.modal('/view/qmm/020/f/index.xhtml', { title: '明細書の紐ずけ＞履歴追加' }).onClosed(function(): any {
                    //                self.returnJDialog = ko.observable(nts.uk.ui.windows.getShared('returnJDialog'));
                    //                //self.itemList.removeAll();
                    //                self.itemList.push(new ItemModel('4', self.returnJDialog() + '~9999/12'));
                    //            });
                };
                //Open dialog Edit History
                ScreenModel.prototype.openKDialog = function () {
                    var self = this;
                    //var singleSelectedCode = self.singleSelectedCode().split(';');
                    //nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '明細書の紐ずけ＞履歴編集' }).onClosed(function () {
                        //self.start(self.singleSelectedCode());
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(histId, startEnd) {
                    var self = this;
                    self.histId = ko.observable(histId);
                    self.startEnd = startEnd;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var EmployeeAllotModel = (function () {
                function EmployeeAllotModel(histId, empCode, startYM, endYM, paymentCode, paymentName, bonusCode, bonusName) {
                    this.histId = ko.observable(histId);
                    this.empCode = ko.observable(empCode);
                    this.startYM = ko.observable(startYM);
                    this.endYM = ko.observable(endYM);
                    this.paymentCode = ko.observable(paymentCode);
                    this.paymentName = ko.observable(paymentName);
                    this.bonusCode = ko.observable(bonusCode);
                    this.bonusName = ko.observable(bonusName);
                }
                return EmployeeAllotModel;
            }());
            var EmployeeSettingHeaderModel = (function () {
                function EmployeeSettingHeaderModel(companyCode, startYm, endYm, historyId) {
                    this.companyCode = companyCode;
                    this.startYm = startYm;
                    this.endYm = endYm;
                    this.historyId = historyId;
                }
                return EmployeeSettingHeaderModel;
            }());
            viewmodel.EmployeeSettingHeaderModel = EmployeeSettingHeaderModel;
            var EmployeeSettingDetailModel = (function () {
                function EmployeeSettingDetailModel() {
                }
                return EmployeeSettingDetailModel;
            }());
            viewmodel.EmployeeSettingDetailModel = EmployeeSettingDetailModel;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm020.c || (qmm020.c = {}));
})(qmm020 || (qmm020 = {}));
