var qmm025;
(function (qmm025) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.isDeleteEnable = ko.observable(false);
                    self.yearKey = ko.observable(0);
                    self.yearInJapanEmpire = ko.computed(function () {
                        return nts.uk.time.yearInJapanEmpire(self.yearKey()).toString();
                    });
                    self.personId = ko.observableArray([]);
                    self.countRowChecked = 0;
                    self.isFiredFromCheckbox = ko.observable(false);
                    self.resiTax05 = ko.observable(0);
                    self.resiTax06 = ko.observable(0);
                    self.resiTax07 = ko.observable(0);
                    self.resiTax08 = ko.observable(0);
                    self.resiTax09 = ko.observable(0);
                    self.resiTax10 = ko.observable(0);
                    self.resiTax11 = ko.observable(0);
                    self.resiTax12 = ko.observable(0);
                    self.resiTax01 = ko.observable(0);
                    self.resiTax02 = ko.observable(0);
                    self.resiTax03 = ko.observable(0);
                    self.resiTax04 = ko.observable(0);
                    // checkbox square
                    $.ig.checkboxMarkupClasses = "ui-state-default ui-corner-all ui-igcheckbox-small";
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    $.when(self.getYearKey()).done(function () {
                        self.findAll().done(function () {
                            dfd.resolve();
                        }).fail(function (res) {
                            dfd.reject(res);
                        });
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.findAll = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm025.a.service.findAll(self.yearKey())
                        .done(function (data) {
                        var perResiTaxData = [];
                        if (data[0] === undefined) {
                            qmm025.a.service.findAll(self.yearKey() - 1)
                                .done(function (data) {
                                if (data[0] === undefined) {
                                    alert("ERROR!");
                                }
                                else {
                                    self.getData(perResiTaxData, data);
                                }
                                dfd.resolve();
                            })
                                .fail(function (res) {
                                dfd.reject(res);
                            });
                        }
                        else {
                            self.getData(perResiTaxData, data);
                            dfd.resolve();
                        }
                    })
                        .fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                //lay du lieu tu DB de hien thi ra man hinh
                ScreenModel.prototype.getData = function (perResiTaxData, data) {
                    if (perResiTaxData === void 0) { perResiTaxData = []; }
                    var self = this;
                    perResiTaxData.push(new ResidenceTax('NSVC', '0000001', 'name', 'Vietnam', false, data[0].residenceTax[0].value, data[0].residenceTax[1].value, data[0].residenceTax[2].value, data[0].residenceTax[3].value, data[0].residenceTax[4].value, data[0].residenceTax[5].value, data[0].residenceTax[6].value, data[0].residenceTax[7].value, data[0].residenceTax[8].value, data[0].residenceTax[9].value, data[0].residenceTax[10].value, data[0].residenceTax[11].value));
                    perResiTaxData.push(new ResidenceTax('NSVC', '0000002', 'name1', 'Japan', false, 25000, 25000, 25000, 25000, 25000, 15000, 25000, 25000, 25000, 25000, 25000, 25000));
                    self.items(perResiTaxData);
                    self.bindGrid(self.items());
                };
                ScreenModel.prototype.getYearKey = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm025.a.service.getYearKey()
                        .done(function (res) {
                        self.yearKey(res.currentProcessingYm);
                        dfd.resolve();
                    })
                        .fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    var obj = {
                        residenceCode: '000010',
                        personId: '000426a2-181b-4c7f-abc8-6fff9f4f983a',
                        yearKey: self.yearKey(),
                        residenceTaxBn: 1000,
                        residenceTaxAve: 850,
                        residenceTaxLumpAtr: 1,
                        residenceTaxLumpYm: 10,
                        residenceTaxLevyAtr: 0,
                        residenceTax: [
                            { "month": 1, "value": self.resiTax07() }, { "month": 2, "value": self.resiTax07() }, { "month": 3, "value": self.resiTax07() },
                            { "month": 4, "value": self.resiTax07() }, { "month": 5, "value": self.resiTax05() }, { "month": 6, "value": self.resiTax06() },
                            { "month": 7, "value": self.resiTax07() }, { "month": 8, "value": 1212 }, { "month": 9, "value": self.resiTax07() },
                            { "month": 10, "value": self.resiTax07() }, { "month": 11, "value": self.resiTax07() }, { "month": 12, "value": self.resiTax07() },
                        ]
                    };
                    qmm025.a.service.update(obj)
                        .done(function () {
                        self.findAll();
                    })
                        .fail(function () {
                    });
                };
                ScreenModel.prototype.remove = function () {
                    var self = this;
                    var obj = {
                        personId: self.personId(),
                        yearKey: self.yearKey()
                    };
                    qmm025.a.service.remove(obj)
                        .done(function () {
                        self.findAll();
                    })
                        .fail(function () {
                    });
                };
                ScreenModel.prototype.bindGrid = function (items) {
                    var self = this;
                    //tinh lai tong khi row bi thay doi- updating when row edited
                    $("#grid").on("iggridupdatingeditrowended", function (event, ui) {
                        var grid = ui.owner.grid;
                        self.resiTax05(ui.values["residenceTax05"]);
                        self.resiTax06(ui.values["residenceTax06"]);
                        self.resiTax07(ui.values["residenceTax07"]);
                        var totalValue = 0;
                        if (ui.values["checkAllMonth"]) {
                            totalValue = self.resiTax05() + self.resiTax06() + self.resiTax07() * 10 || ui.values["residenceTaxPerYear"];
                            //                    $("#grid").igGridUpdating("setCellValue", ui.rowID, "residenceTax08", residenceTax07);
                            //set color khi thay doi trang thai cua totalValue
                            for (var i = 3; i < 12; i++) {
                                $(grid.cellAt(i, 0)).removeClass('columnCss');
                            }
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax08').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax09').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax10').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax11').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax12').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax01').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax02').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax03').text(self.resiTax07() + " 円");
                            $("#grid").igGrid('cellById', ui.rowID, 'residenceTax04').text(self.resiTax07() + " 円");
                        }
                        else {
                            totalValue = self.resiTax05() + self.resiTax06() + self.resiTax07() || ui.values["residenceTaxPerYear"];
                            //set color khi thay doi trang thai cua totalValue
                            for (var i = 3; i < 12; i++) {
                                $(grid.cellAt(i, 0)).addClass('columnCss');
                            }
                        }
                        $("#grid").igGrid('cellById', ui.rowID, 'residenceTaxPerYear').text(totalValue);
                    });
                    $("#grid").igGrid({
                        columns: [
                            { headerText: "部門", key: "department", dataType: "string", width: "100px", formatter: _.escape },
                            { headerText: "コード", key: "code", dataType: "string", width: "100px", formatter: _.escape },
                            { headerText: "名称", key: "name", dataType: "string", width: "120px", formatter: _.escape },
                            { headerText: "住民税納付先", key: "add", dataType: "string", width: "100px", formatter: _.escape },
                            { headerText: "年税額", key: "residenceTaxPerYear", dataType: "number", width: "100px", columnCssClass: "align_right" },
                            { headerText: "全月", key: "checkAllMonth", dataType: "bool", width: "35px" },
                            { headerText: "6月", key: "residenceTax06", dataType: "number", width: "100px", columnCssClass: "align_right", template: "${residenceTax06} 円" },
                            { headerText: "7月", key: "residenceTax07", dataType: "number", width: "100px", columnCssClass: "align_right", template: "${residenceTax07} 円" },
                            { headerText: "8月", key: "residenceTax08", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax08} 円" },
                            { headerText: "9月", key: "residenceTax09", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax09} 円" },
                            { headerText: "10月", key: "residenceTax10", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax10} 円" },
                            { headerText: "11月", key: "residenceTax11", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax11} 円" },
                            { headerText: "12月", key: "residenceTax12", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax12} 円" },
                            { headerText: "1月", key: "residenceTax01", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax01} 円" },
                            { headerText: "2月", key: "residenceTax02", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax02} 円" },
                            { headerText: "3月", key: "residenceTax03", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax03} 円" },
                            { headerText: "4月", key: "residenceTax04", dataType: "number", width: "100px", columnCssClass: "columnCss align_right", template: "${residenceTax04} 円" },
                            { headerText: "5月", key: "residenceTax05", dataType: "number", width: "100px", columnCssClass: "align_right", template: "${residenceTax05} 円" }
                        ],
                        renderCheckboxes: true,
                        primaryKey: 'code',
                        autoGenerateColumns: false,
                        dataSource: items,
                        width: "1200px",
                        height: "550px",
                        features: [
                            {
                                name: "ColumnFixing",
                                showFixButtons: false,
                                fixingDirection: "left",
                                columnSettings: [
                                    {
                                        columnKey: "department",
                                        isFixed: true,
                                        allowFixing: false,
                                        readOnly: true
                                    },
                                    {
                                        columnKey: "code",
                                        isFixed: true,
                                        allowFixing: false
                                    },
                                    {
                                        columnKey: "name",
                                        isFixed: true,
                                        allowFixing: false
                                    },
                                    {
                                        columnKey: "add",
                                        isFixed: true,
                                        allowFixing: false
                                    },
                                    {
                                        columnKey: "residenceTaxPerYear",
                                        isFixed: true,
                                        allowFixing: false,
                                    }]
                            },
                            {
                                name: 'Paging',
                                type: "local",
                                pageSize: 20,
                                pageSizeDropDownTrailingLabel: "件",
                                pagerRecordsLabelTemplate: "${recordCount} 件のデータ",
                                nextPageLabelText: "",
                                prevPageLabelText: "",
                            },
                            {
                                name: 'Selection',
                                mode: "cell",
                                multipleSelection: true,
                                //allow use arrow keys for the selection of cells/rows
                                activation: true,
                                // click vao row, sau khi chuyen sang trang khac roi quay lai van click tai row do
                                persist: true,
                                rowSelectionChanging: function (evt, ui) {
                                    var grid = ui.owner.grid;
                                    if (self.isFiredFromCheckbox()) {
                                        self.isFiredFromCheckbox(false);
                                    }
                                    else {
                                        //in this case selection is caused by regular selection and it is canceled by returning false
                                        return false;
                                    }
                                },
                            },
                            {
                                name: "Updating",
                                editMode: "row",
                                enableAddRow: false,
                                enableDeleteRow: false,
                                //                        startEditTriggers: 'enter dblclick',
                                //                        editCellStarting: function(evt, ui) {
                                //                            if (ui.columnKey === "checkAllMonth") {
                                //                                ui.value = !ui.value;
                                //                            }
                                //                        },
                                editCellStarting: function (evt, ui) {
                                    // Test for condition on which to cancel cell editing
                                    // Here I use columnIndex property. This will disable from editing the first column in the grid.
                                    if (ui.columnIndex === 0 || ui.columnIndex === 1 || ui.columnIndex === 2 || ui.columnIndex === 12)
                                        // cancel the editing
                                        return false;
                                },
                                columnSettings: [
                                    { columnKey: "department", readOnly: true },
                                    { columnKey: "code", readOnly: true },
                                    { columnKey: "name", readOnly: true },
                                    { columnKey: "add", readOnly: true },
                                    { columnKey: "residenceTaxPerYear", readOnly: true },
                                    { columnKey: "residenceTax06", required: true },
                                    { columnKey: "residenceTax07", required: true },
                                    { columnKey: "residenceTax08", readOnly: true },
                                    { columnKey: "residenceTax09", readOnly: true },
                                    { columnKey: "residenceTax10", readOnly: true },
                                    { columnKey: "residenceTax11", readOnly: true },
                                    { columnKey: "residenceTax12", readOnly: true },
                                    { columnKey: "residenceTax01", readOnly: true },
                                    { columnKey: "residenceTax02", readOnly: true },
                                    { columnKey: "residenceTax03", readOnly: true },
                                    { columnKey: "residenceTax04", readOnly: true },
                                    { columnKey: "residenceTax05", required: true }
                                ]
                            },
                            {
                                name: 'RowSelectors',
                                //hien checkbox dau tien
                                enableCheckBoxes: true,
                                //enableSelectAllForPaging: false -> khong hien dong chu selectAllRow
                                enableSelectAllForPaging: false,
                                checkBoxStateChanging: function (evt, ui) {
                                    self.isFiredFromCheckbox(true);
                                },
                                //lay rowId de xac dinh xem xoa row nao
                                checkBoxStateChanged: function (evt, ui) {
                                    if (ui.state == "on") {
                                        self.personId().push(ui.rowKey);
                                        self.countRowChecked += 1;
                                    }
                                    else {
                                        _.pull(self.personId(), ui.rowKey);
                                        self.countRowChecked -= 1;
                                    }
                                    if (self.countRowChecked == 0) {
                                        self.isDeleteEnable(false);
                                    }
                                    else {
                                        self.isDeleteEnable(true);
                                    }
                                },
                            }
                        ]
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ResidenceTax = (function () {
                function ResidenceTax(department, code, name, add, checkAllMonth, residenceTax01, residenceTax02, residenceTax03, residenceTax04, residenceTax05, residenceTax06, residenceTax07, residenceTax08, residenceTax09, residenceTax10, residenceTax11, residenceTax12) {
                    this.department = department;
                    this.code = code;
                    this.name = name;
                    this.add = add;
                    this.checkAllMonth = checkAllMonth;
                    this.residenceTax01 = residenceTax01;
                    this.residenceTax02 = residenceTax02;
                    this.residenceTax03 = residenceTax03;
                    this.residenceTax04 = residenceTax04;
                    this.residenceTax05 = residenceTax05;
                    this.residenceTax06 = residenceTax06;
                    this.residenceTax07 = residenceTax07;
                    this.residenceTax08 = residenceTax08;
                    this.residenceTax09 = residenceTax09;
                    this.residenceTax10 = residenceTax10;
                    this.residenceTax11 = residenceTax11;
                    this.residenceTax12 = residenceTax12;
                    this.residenceTaxPerYear = this.residenceTax05 + this.residenceTax06 + this.residenceTax07;
                }
                return ResidenceTax;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm025.a || (qmm025.a = {}));
})(qmm025 || (qmm025 = {}));
//# sourceMappingURL=qmm025.a.viewmodel.js.map