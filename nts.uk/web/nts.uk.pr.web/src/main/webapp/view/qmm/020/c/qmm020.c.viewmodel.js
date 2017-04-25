var qmm020;
(function (qmm020) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.firstLoad = false;
                    var self = this;
                    var dfd = $.Deferred();
                    self.itemList = ko.observableArray([]);
                    self.selectedCode = ko.observable('');
                    self.isEnable = ko.observable(true);
                    self.selectedList = ko.observableArray([]);
                    self.itemHist = ko.observable(null);
                    self.itemTotalList = ko.observableArray([]);
                    self.itemListDetail = ko.observableArray([]);
                    self.histId = ko.observable(null);
                    self.maxItem = ko.observable(null);
                    self.maxDate = "";
                    self.currentItem = ko.observable(new TotalModel({
                        historyId: '',
                        employmentCode: '',
                        paymentDetailCode: '',
                        bonusDetailCode: '',
                        startYm: '',
                        endYm: ''
                    }));
                    self.selectedCode.subscribe(function (codeChange) {
                        //                service.getAllEmployeeAllotSetting(ko.toJS(codeChange)).done(function(data) {
                        //                    self.itemListDetail([]);
                        //                    if (data && data.length > 0) {
                        //                        _.map(data, function(item) {
                        //                            self.itemListDetail.push(new EmployeeAllotSettingDto(item.companyCode, item.historyId, item.employeeCode, item.employeeName, item.paymentDetailCode
                        //                                , item.paymentDetailName, item.bonusDetailCode, item.bonusDetailName));
                        //                        });
                        //
                        //                    }
                        //                    if (self.firstLoad)
                        //                        $("#C_LST_001").igGrid("option", "dataSource", ko.mapping.toJS(self.itemListDetail));
                        //                    else
                        //                        self.LoadData(self.itemListDetail);
                        //                    dfd.resolve();
                        //                }).fail(function(res) {
                        //                    // Alert message
                        //                    alert(res);
                        //                });
                        //                dfd.promise();
                        c.service.getEmployeeDetail(ko.toJS(codeChange)).done(function (data) {
                            var employeeItem = [];
                            if (data && data.length > 0) {
                                _.map(data, function (item) {
                                    employeeItem.push(new TotalModel({ historyId: item.historyId, employmentCode: item.employmentCode, paymentDetailCode: item.paymentDetailCode, bonusDetailCode: item.bonusDetailCode }));
                                });
                            }
                            if (self.firstLoad)
                                $("#C_LST_001").igGrid("option", "dataSource", employeeItem);
                            else
                                self.LoadData(employeeItem);
                            dfd.resolve();
                        }).fail(function (res) {
                            // Alert message
                            alert(res);
                        });
                        dfd.promise();
                    });
                    // Array Data 1 
                    //            let employment1 = ko.mapping.fromJS([
                    //                { "NO": 1, "ID": "000000001", "Name": "正社員", "PaymentDocID": "K001", "PaymentDocName": "給与明細書001", "BonusDocID": "S001", "BonusDocName": "賞与明細書001" },
                    //                { "NO": 2, "ID": "000000002", "Name": "DucPham社員", "PaymentDocID": "K002", "PaymentDocName": "給与明細書002", "BonusDocID": "S001", "BonusDocName": "賞与明細書002" },
                    //                { "NO": 3, "ID": "000000003", "Name": "HoangMai社員", "PaymentDocID": "K003", "PaymentDocName": "給与明細書003", "BonusDocID": "S001", "BonusDocName": "賞与明細書003" }
                    //            ]);
                    //
                    //            self.dataSource = ko.mapping.toJS(employment1());
                    //console.log(self.dataSource);
                    //Build IgGrid
                    //SCREEN C
                    //Event : Click to button Sentaku on igGrid
                    //            var openPaymentDialog = function(evt, ui) {
                    //                if (ui.colKey === "PaymentDocID") {
                    //                    //Gọi hàm open SubWindow
                    //                    //Khi close Subwindow, get dc cái new object(ID, Name... )
                    //                    let row = _.find(employment1(), function(item) {
                    //                        //return item.ID() === ui.rowKey;
                    //                    });
                    //                    //row.PaymentDocName("test");
                    //                    //self.buildGrid("#C_LST_001", "C_BTN_001", "C_BTN_002");
                    //                }
                    //            }
                    //            self.start();
                    /**
                     * find maxItem by endate
                     */
                }
                ScreenModel.prototype.LoadData = function (itemList) {
                    var self = this;
                    $("#C_LST_001").igGrid({
                        columns: [
                            { headerText: "", key: "NO", dataType: "string", width: "20px" },
                            { headerText: "コード", key: "employmentCode", dataType: "string", width: "100px" },
                            { headerText: "名称", key: "employmentName", dataType: "string", width: "200px" },
                            { headerText: "", key: "paymentDetailCode", dataType: "string", hidden: true },
                            { headerText: "", key: "paymentDetailName", dataType: "string", hidden: true },
                            { headerText: "", key: "bonusDetailCode", dataType: "string", hidden: true },
                            { headerText: "", key: "bonusDetailName", dataType: "string", hidden: true },
                            {
                                headerText: "給与明細書", key: "paymentDetailCode", dataType: "string", width: "250px", unbound: true,
                                template: "<input type='button' id='" + "C_BTN_001" + "' value='選択'/><label style='margin-left:5px;'>${paymentDetailCode}</label><label style='margin-left:15px;'>${paymentDetailName}</label>"
                            },
                            {
                                headerText: "賞与明細書", key: "bonusDetailCode", dataType: "string", width: "20%", unbound: true,
                                template: "<input type='button' id='" + "C_BTN_002" + "' value='選択'/><label style='margin-left:5px;'>${bonusDetailCode}</label><label style='margin-left:15px;'>${bonusDetailName}</label>"
                            },
                        ],
                        features: [{
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true,
                                activation: false,
                            }],
                        virtualization: true,
                        virtualizationMode: 'continuous',
                        width: "800px",
                        height: "240px",
                        primaryKey: "employmentCode",
                        dataSource: ko.mapping.toJS(itemList)
                    });
                    self.firstLoad = true;
                };
                //find histId to subscribe
                ScreenModel.prototype.getHist = function (value) {
                    var self = this;
                    return _.find(self.itemList(), function (item) {
                        return item.historyId === value;
                    });
                };
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
                // start function
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    //fill employ data to c_LST_001
                    c.service.getEmployeeName().done(function (data) {
                        var employeeItem = [];
                        if (data && data.length > 0) {
                            _.map(data, function (item) {
                                employeeItem.push(new TotalModel({ historyId: item.historyId, employmentCode: item.employmentCode, employmentName: item.employmentName }));
                            });
                        }
                        if (self.firstLoad)
                            $("#C_LST_001").igGrid("option", "dataSource", employeeItem);
                        else
                            self.LoadData(employeeItem);
                        dfd.resolve();
                    }).fail(function (res) {
                        // Alert message
                        alert(res);
                    });
                    dfd.promise();
                    //Get list startDate, endDate of History  
                    var totalItem = [];
                    c.service.getEmployeeAllotHeaderList().done(function (data) {
                        if (data.length > 0) {
                            _.forEach(data, function (item) {
                                totalItem.push(new TotalModel({ historyId: item.historyId, startEnd: item.startYm + ' ~ ' + item.endYm, endYm: item.endYm }));
                            });
                            self.itemTotalList(totalItem);
                        }
                        else {
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        // Alert message
                        alert(res);
                    });
                    console.log(ko.toJSON(self.itemTotalList()));
                    c.service.getAllotEmployeeMaxDate().done(function (itemMax) {
                        //                self.maxDate = (itemMax.startYm || "").toString();
                        //                self.maxItem(itemMax);
                        var maxDate = _.find(self.itemTotalList(), function (obj) { return parseInt(obj.endYm()) == itemMax; });
                        self.maxDate = (maxDate.startYm || "").toString();
                        self.maxItem(maxDate);
                    }).fail(function (res) {
                        alert(res);
                    });
                    // Return.
                    return dfd.promise();
                };
                //click register button
                ScreenModel.prototype.register = function () {
                    var self = this;
                    var current = _.find(self.itemTotalList(), function (item) { return item.historyId == self.currentItem().historyId(); });
                    //            debugger;
                    if (current) {
                    }
                };
                //Open dialog Add History
                ScreenModel.prototype.openJDialog = function () {
                    var self = this;
                    //            debugger;
                    var historyScreenType = "1";
                    var valueShareJDialog = historyScreenType + "~" + "201701";
                    nts.uk.ui.windows.setShared('valJDialog', valueShareJDialog);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { title: '明細書の紐ずけ＞履歴追加' })
                        .onClosed(function () {
                        var returnJDialog = nts.uk.ui.windows.getShared('returnJDialog');
                        var modeRadio = returnJDialog.split("~")[0];
                        var returnValue = returnJDialog.split("~")[1];
                        if (returnValue != '') {
                            //                        let employeeAllotSettings = new Array<EmployeeAllotSettingDto>();
                            var items = self.itemTotalList();
                            var addItem;
                            var copItem;
                            if (modeRadio === "2") {
                                addItem = new TotalModel({
                                    historyId: '',
                                    employmentCode: '',
                                    paymentDetailCode: '',
                                    bonusDetailCode: '',
                                    startYm: returnValue,
                                    endYm: '999912',
                                    startEnd: (returnValue + ' ~ ' + '999912')
                                });
                                items.push(addItem);
                            }
                            else {
                                copItem = new TotalModel({
                                    historyId: '',
                                    employmentCode: '',
                                    employmentName: '',
                                    paymentDetailCode: '',
                                    paymentDetailName: '',
                                    bonusDetailCode: '',
                                    bonusDetailName: '',
                                    startYm: returnValue,
                                    endYm: '999912',
                                    startEnd: (returnValue + ' ~ ' + '999912')
                                });
                                self.currentItem().historyId(copItem.historyId());
                                self.currentItem().startYm(returnValue);
                                self.currentItem().endYm('999912');
                                self.currentItem().employmentCode(self.maxItem().historyId());
                                //get employmentName, paymentDetailName, paymentDetailCode
                                var dfd_1 = $.Deferred();
                                c.service.getAllEmployeeAllotSetting(ko.toJS(self.maxItem().historyId())).done(function (data) {
                                    self.itemListDetail([]);
                                    if (data && data.length > 0) {
                                        _.map(data, function (item) {
                                            self.itemListDetail.push(new copItem(item.historyId, item.employmentCode, item.employmentName, item.paymentDetailCode, item.paymentDetailName, item.bonusDetailCode, item.bonusDetailName, item.startYm, item.endYm, item.startEnd));
                                        });
                                        self.currentItem().employmentCode(ko.toJS(self.itemListDetail()[0].employmentCode));
                                        self.currentItem().employmentName(ko.toJS(self.itemListDetail()[0].employmentName));
                                        self.currentItem().paymentDetailCode(ko.toJS(self.itemListDetail()[0].paymentDetailCode));
                                        self.currentItem().paymentDetailName(ko.toJS(self.itemListDetail()[0].paymentDetailName));
                                        self.currentItem().bonusDetailCode(ko.toJS(self.itemListDetail()[0].bonusDetailCode));
                                        self.currentItem().bonusDetailName(ko.toJS(self.itemListDetail()[0].bonusDetailName));
                                    }
                                    if (self.firstLoad)
                                        $("#C_LST_001").igGrid("option", "dataSource", ko.mapping.toJS(self.itemListDetail));
                                    else
                                        self.LoadData(self.itemListDetail);
                                    dfd_1.resolve();
                                }).fail(function (res) {
                                    // Alert message
                                    alert(res);
                                });
                                dfd_1.promise();
                                items.push(copItem);
                            }
                            self.itemTotalList([]);
                            self.itemTotalList(items);
                        }
                    });
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
                function ItemModel(histId, startEnd, endYm) {
                    var self = this;
                    self.histId = (histId);
                    self.startEnd = startEnd;
                    self.endYm = endYm;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var EmployeeAllotSettingDto = (function () {
                function EmployeeAllotSettingDto(companyCode, historyId, employmentCode, employmentName, paymentDetailCode, paymentDetailName, bonusDetailCode, bonusDetailName) {
                    this.companyCode = ko.observable(companyCode);
                    this.historyId = ko.observable(historyId);
                    this.employmentCode = ko.observable(employmentCode);
                    this.employmentName = ko.observable(employmentName);
                    this.paymentDetailCode = ko.observable(paymentDetailCode);
                    this.paymentDetailName = ko.observable(paymentDetailName);
                    this.bonusDetailCode = ko.observable(bonusDetailCode);
                    this.bonusDetailName = ko.observable(bonusDetailName);
                }
                return EmployeeAllotSettingDto;
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
                function EmployeeSettingDetailModel(companyCode, historyId, employmentCode, paymentDetailCode, bonusDetailCode) {
                    this.companyCode = companyCode;
                    this.historyId = historyId;
                    this.employmentCode = employmentCode;
                    this.paymentDetailCode = paymentDetailCode;
                    this.bonusDetailCode = bonusDetailCode;
                }
                return EmployeeSettingDetailModel;
            }());
            viewmodel.EmployeeSettingDetailModel = EmployeeSettingDetailModel;
            var TotalModel = (function () {
                function TotalModel(param) {
                    this.companyCode = ko.observable(param.companyCode);
                    this.historyId = ko.observable(param.historyId);
                    this.startEnd = param.startEnd;
                    this.employmentCode = ko.observable(param.employmentCode);
                    this.employmentName = ko.observable(param.employmentName);
                    this.paymentDetailCode = ko.observable(param.paymentDetailCode);
                    this.paymentDetailName = ko.observable(param.paymentDetailName);
                    this.bonusDetailCode = ko.observable(param.bonusDetailCode);
                    this.bonusDetailName = ko.observable(param.bonusDetailName);
                    this.startYm = ko.observable(param.startYm);
                    this.endYm = ko.observable(param.endYm);
                }
                return TotalModel;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm020.c || (qmm020.c = {}));
})(qmm020 || (qmm020 = {}));
