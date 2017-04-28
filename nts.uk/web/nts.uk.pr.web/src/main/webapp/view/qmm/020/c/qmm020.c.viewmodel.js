var qmm020;
(function (qmm020) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    var dfd = $.Deferred();
                    self.itemList = ko.observableArray([]);
                    self.selectedCode = ko.observable('');
                    self.isEnable = ko.observable(true);
                    self.selectedList = ko.observableArray([]);
                    self.employeeTotal = ko.observableArray([]);
                    self.itemHist = ko.observable(null);
                    self.itemTotalList = ko.observableArray([]);
                    self.itemListDetail = ko.observableArray([]);
                    self.itemAllotSetting = ko.observableArray([]);
                    self.histId = ko.observable(null);
                    self.maxItem = ko.observable(null);
                    self.maxDate = "";
                    self.dataSource = ko.observableArray([]);
                    self.currentItem = ko.observable(new TotalModel({
                        historyId: '',
                        employmentCode: '',
                        paymentDetailCode: '',
                        bonusDetailCode: '',
                        startYm: '',
                        endYm: ''
                    }));
                    self.selectedCode.subscribe(function (codeChange) {
                        //let x =  self.getEmpName();
                        //self.itemAllotSetting([]);
                        c.service.getEmployeeDetail(ko.toJS(codeChange)).done(function (data) {
                            var employeeItems = [];
                            if (data && data.length > 0) {
                                employeeItems = $("#C_LST_001").igGrid("option", "dataSource");
                                if (employeeItems.length == 0) {
                                    employeeItems = data;
                                }
                                var _loop_1 = function(i_1) {
                                    var item = employeeItems[i_1];
                                    var itemAllotSetting = _.find(data, function (allot) { return item.employmentCode == allot.employeeCode; });
                                    if (itemAllotSetting) {
                                        item.paymentDetailCode = itemAllotSetting.paymentDetailCode;
                                        item.paymentDetailName = itemAllotSetting.paymentDetailName;
                                        item.bonusDetailCode = itemAllotSetting.bonusDetailCode;
                                        item.bonusDetailName = itemAllotSetting.bonusDetailName;
                                    }
                                    else {
                                        item.paymentDetailCode = undefined;
                                        item.paymentDetailName = undefined;
                                        item.bonusDetailCode = undefined;
                                        item.bonusDetailName = undefined;
                                    }
                                };
                                for (var i_1 in employeeItems) {
                                    _loop_1(i_1);
                                }
                                // Set data to grid
                                $("#C_LST_001").igGrid("option", "dataSource", employeeItems);
                                dfd.resolve();
                            }
                            self.itemAllotSetting(employeeItems);
                            dfd.resolve();
                        }).fail(function (res) {
                            //Alert message
                            alert(res);
                        });
                        dfd.promise();
                    });
                    // init columns and title for grid
                    self.LoadData([]);
                    // call first method
                    self.start();
                    $("#C_BTN_001").click(function () {
                        alert("The paragraph was clicked.");
                    });
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
                                template: "<input type='button' data-bind='click: abc' class='" + "C_BTN_001" + "' value='選択'/><label style='margin-left:5px;'>${paymentDetailCode}</label><label style='margin-left:15px;'>${paymentDetailName}</label>"
                            },
                            {
                                headerText: "賞与明細書", key: "bonusDetailCode", dataType: "string", width: "20%", unbound: true,
                                template: "<input type='button' data-bind='click: abc' class='" + "C_BTN_002" + "' value='選択'/><label style='margin-left:5px;'>${bonusDetailCode}</label><label style='margin-left:15px;'>${bonusDetailName}</label>"
                            },
                        ],
                        features: [{
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true,
                                activation: false,
                            },
                        ],
                        virtualization: true,
                        virtualizationMode: 'continuous',
                        width: "800px",
                        height: "240px",
                        primaryKey: "employmentCode",
                        dataSource: itemList
                    });
                };
                //find histId to subscribe
                ScreenModel.prototype.getHist = function (value) {
                    var self = this;
                    return _.find(self.itemList(), function (item) {
                        return item.historyId === value;
                    });
                };
                //find employeeName initiation
                ScreenModel.prototype.getEmpName = function (value) {
                    var self = this;
                    value = _.find(ko.mapping.toJS(self.employeeTotal()), function (o) {
                        return o.employmentCode == '00001';
                    });
                    return value;
                };
                // getEmpCode initiation
                ScreenModel.prototype.getEmpcode = function (value) {
                    var self = this;
                    value = _.find(ko.mapping.toJS(self.employeeTotal()), function (h) {
                        return null;
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
                                employeeItem.push({ historyId: item.historyId, employmentCode: item.employmentCode, employmentName: item.employmentName });
                            });
                            dfd.resolve();
                        }
                        self.employeeTotal(employeeItem);
                        self.currentItem(employeeItem.employmentName);
                        // Set datafor grid
                        $("#C_LST_001").igGrid("option", "dataSource", employeeItem);
                        console.log(self.dataSource);
                        console.log('111111111111111111111111111111111111111');
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
                            //                    let max = _.maxBy(self.itemList(), (itemMax) => { return itemMax.endYm });
                            dfd.resolve();
                        }
                        else {
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        // Alert message
                        alert(res);
                    });
                    console.log((self.itemTotalList()));
                    c.service.getAllotEmployeeMaxDate().done(function (itemMax) {
                        var maxDate = _.find(self.itemTotalList(), function (obj) { return parseInt(obj.endYm()) == itemMax; });
                        self.maxDate = (maxDate.startYm || "").toString();
                        self.maxItem(maxDate);
                        console.log(self.maxItem());
                        console.log(self.maxDate);
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res);
                    });
                    // Return.
                    return dfd.promise();
                };
                //click register button
                /**
                 *
                 */
                //        register() {
                //            var self = this;
                //            var current = _.find(self.itemTotalList(), function(item: IModel) { return item.historyId == self.currentItem().historyId(); });
                //            //            debugger;
                //            if (current) {
                //                //                service.insertEmAllot(current).done(function() {
                //                //                }).fail(function(res) {
                //                //                    alert(res);
                //                //                });
                //            }
                //        }
                //Open dialog Add History
                ScreenModel.prototype.openJDialog = function () {
                    var self = this;
                    debugger;
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
                                        dfd_1.resolve();
                                    }
                                    $("#C_LST_001").igGrid("option", "dataSource", ko.mapping.toJS(self.itemListDetail));
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
                ScreenModel.prototype.openMDialog = function () {
                    var self = this;
                    var valueShareMDialog = self.currentItem().startYm();
                    //debugger;
                    nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '譏守ｴｰ譖ｸ縺ｮ驕ｸ謚�' }).onClosed(function () {
                        //get selected code from M dialog
                        //get Name payment Name
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
            var EmpDetail = (function () {
                function EmpDetail() {
                }
                return EmpDetail;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm020.c || (qmm020.c = {}));
})(qmm020 || (qmm020 = {}));
$(function () {
    $(document).on("click", ".C_BTN_001", function () {
        var self = this;
        var valueShareMDialog = self.currentItem.startYm;
        //debugger;
        nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
        nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '譏守ｴｰ譖ｸ縺ｮ驕ｸ謚�' }).onClosed(function () {
            //get selected code from M dialog
            //get Name payment Name
        });
    });
});
