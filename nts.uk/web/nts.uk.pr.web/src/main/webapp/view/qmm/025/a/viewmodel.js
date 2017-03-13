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
                    self.isEnable = ko.observable(false);
                    //get current year
                    self.yearKey = ko.observable(0);
                    self.yearInJapanEmpire = ko.computed(function () {
                        return nts.uk.time.yearInJapanEmpire(self.yearKey()).toString();
                    });
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
                                    perResiTaxData.push(new ResidenceTax('NSVC', data[0].personId, 'name', 'Vietnam', false, data[0].residenceTax[0].value, data[0].residenceTax[1].value, data[0].residenceTax[2].value, data[0].residenceTax[3].value, data[0].residenceTax[4].value, data[0].residenceTax[5].value, data[0].residenceTax[6].value, data[0].residenceTax[7].value, data[0].residenceTax[8].value, data[0].residenceTax[9].value, data[0].residenceTax[10].value, data[0].residenceTax[11].value));
                                    perResiTaxData.push(new ResidenceTax('NSVC', '00001', 'name1', 'Japan', false, 25000, 25000, 25000, 15000, 25000, 25000, 25000, 25000, 25000, 25000, 15000, 25000));
                                    self.items(perResiTaxData);
                                    self.isEnable(true);
                                    self.bindGrid(self.items());
                                }
                                dfd.resolve();
                            })
                                .fail(function (res) {
                                dfd.reject(res);
                            });
                        }
                        else {
                            perResiTaxData.push(new ResidenceTax('NSVC', data[0].personId, 'name', 'Vietnam', false, data[0].residenceTax[0].value, data[0].residenceTax[1].value, data[0].residenceTax[2].value, data[0].residenceTax[3].value, data[0].residenceTax[4].value, data[0].residenceTax[5].value, data[0].residenceTax[6].value, data[0].residenceTax[7].value, data[0].residenceTax[8].value, data[0].residenceTax[9].value, data[0].residenceTax[10].value, data[0].residenceTax[11].value));
                            perResiTaxData.push(new ResidenceTax('NSVC', '00001', 'name1', 'Japan', false, 15000, 25000, 25000, 25000, 25000, 25000, 25000, 25000, 25000, 25000, 25000, 25000));
                            self.items(perResiTaxData);
                            self.isEnable(true);
                            self.bindGrid(self.items());
                            dfd.resolve();
                        }
                    })
                        .fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
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
                ScreenModel.prototype.remove = function () {
                    var self = this;
                    var yearKey = self.yearKey();
                    var obj = {
                        "yearKey": yearKey
                    };
                    qmm025.a.service.remove(obj)
                        .done(function () {
                        self.findAll();
                    })
                        .fail(function () {
                    });
                };
                ScreenModel.prototype.bindGrid = function (data) {
                    var self = this;
                    //tinh lai tong khi row bi thay doi- updating when row edited
                    $("#grid").on("iggridupdatingeditrowended", function (event, ui) {
                        var grid = ui.owner.grid;
                        var residenceTax01 = ui.values["residenceTax01"];
                        var residenceTax02 = ui.values["residenceTax02"];
                        var residenceTax03 = ui.values["residenceTax03"];
                        var residenceTax04 = ui.values["residenceTax04"];
                        var residenceTax05 = ui.values["residenceTax05"];
                        var residenceTax06 = ui.values["residenceTax06"];
                        var residenceTax07 = ui.values["residenceTax07"];
                        var residenceTax08 = ui.values["residenceTax08"];
                        var residenceTax09 = ui.values["residenceTax09"];
                        var residenceTax10 = ui.values["residenceTax10"];
                        var residenceTax11 = ui.values["residenceTax11"];
                        var residenceTax12 = ui.values["residenceTax12"];
                        var totalValue = 0;
                        if (ui.values["checkAllMonth"]) {
                            totalValue = residenceTax06 + residenceTax07 * 11 || ui.values["residenceTaxPerYear"];
                            $("#grid").igGridUpdating("setCellValue", ui.rowID, "residenceTax08", residenceTax07);
                            //set background-color khi thay doi trang thai cua totalValue
                            for (var i = 3; i < 13; i++) {
                                $(grid.cellAt(i, 0)).css('background-color', 'transparent');
                            }
                        }
                        else {
                            totalValue = residenceTax06 + residenceTax07 || ui.values["residenceTaxPerYear"];
                            //set background-color khi thay doi trang thai cua totalValue
                            for (var i = 3; i < 13; i++) {
                                $(grid.cellAt(i, 0)).css('background-color', 'grey');
                            }
                        }
                        $("#grid").igGridUpdating("setCellValue", ui.rowID, "residenceTaxPerYear", totalValue);
                    });
                    $("#grid").igGrid({
                        columns: [
                            { headerText: "部門", key: "department", dataType: "string", width: "100px", formatter: _.escape },
                            { headerText: "コード", key: "code", dataType: "string", width: "100px", formatter: _.escape },
                            { headerText: "名称", key: "name", dataType: "string", width: "120px", formatter: _.escape },
                            { headerText: "住民税納付先", key: "add", dataType: "string", width: "100px", formatter: _.escape },
                            { headerText: "年税額", key: "residenceTaxPerYear", dataType: "number", width: "100px", columnCssClass: "align_right" },
                            { headerText: "全月", key: "checkAllMonth", dataType: "bool", width: "35px" },
                            { headerText: "6月", key: "residenceTax06", dataType: "number", width: "100px", template: "<a style='float: right'>${residenceTax06} 円</a>" },
                            { headerText: "7月", key: "residenceTax07", dataType: "number", width: "100px", template: "<a style='float: right'>${residenceTax07} 円</a>" },
                            { headerText: "8月", key: "residenceTax08", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax08} 円</a>" },
                            { headerText: "9月", key: "residenceTax09", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax09} 円</a>" },
                            { headerText: "10月", key: "residenceTax10", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax10} 円</a>" },
                            { headerText: "11月", key: "residenceTax11", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax11} 円</a>" },
                            { headerText: "12月", key: "residenceTax12", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax12} 円</a>" },
                            { headerText: "1月", key: "residenceTax01", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax01} 円</a>" },
                            { headerText: "2月", key: "residenceTax02", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax02} 円</a>" },
                            { headerText: "3月", key: "residenceTax03", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax03} 円</a>" },
                            { headerText: "4月", key: "residenceTax04", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax04} 円</a>" },
                            { headerText: "5月", key: "residenceTax05", dataType: "number", width: "100px", columnCssClass: "columnCss", template: "<a style='float: right'>${residenceTax05} 円</a>" }
                        ],
                        renderCheckboxes: true,
                        primaryKey: 'code',
                        autoGenerateColumns: false,
                        dataSource: data,
                        //1755px
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
                                        unbound: true
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
                                multipleSelection: true,
                                //allow use arrow keys for the selection of cells/rows
                                activation: true,
                                // click vao row, sau khi chuyen sang trang khac roi quay lai van click tai row do
                                persist: true,
                            },
                            {
                                name: "Updating",
                                editMode: "row",
                                enableAddRow: false,
                                enableDeleteRow: false,
                                editCellStarting: function (evt, ui) {
                                    if (ui.columnKey === "checkAllMonth" && $(evt.originalEvent.target).hasClass("ui-icon-check")) {
                                        ui.value = !ui.value;
                                        if (ui.value) {
                                        }
                                    }
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
                                    { columnKey: "residenceTax05", readOnly: true }
                                ]
                            },
                            {
                                name: 'RowSelectors',
                                enableCheckBoxes: true,
                                //enableSelectAllForPaging: false -> khong hien dong chu selectAllRow
                                enableSelectAllForPaging: false,
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
                    this.residenceTaxPerYear = this.residenceTax06 + this.residenceTax07;
                }
                return ResidenceTax;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm025.a || (qmm025.a = {}));
})(qmm025 || (qmm025 = {}));
