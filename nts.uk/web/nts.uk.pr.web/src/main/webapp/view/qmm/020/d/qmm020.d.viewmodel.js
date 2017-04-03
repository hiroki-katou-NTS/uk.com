var qmm020;
(function (qmm020) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.itemList = ko.observableArray([]);
                    self.selectedCode = ko.observableArray([]);
                    var dataDTree = [new NodeDTree('0001', '部門0001', '0001', '給与明細書001', '0001', '賞与明細書001', []),
                        new NodeDTree('0003', '部門0003', '0003', '給与明細書003', '0003', '賞与明細書003', []),
                        new NodeDTree('0004', '部門0004', '0004', '給与明細書004', '0004', '賞与明細書004', []),
                        new NodeDTree('0005', '部門0005', '0005', '給与明細書005', '0005', '賞与明細書005', []),
                        new NodeDTree('0002', '部門0006', '0002', '給与明細書002', '', '', []),
                        new NodeDTree('0006', '部門0007', '0006', '給与明細書006', '', '', []),
                        new NodeDTree('0007', '部門0008', '0007', '給与明細書007', '', '', [new NodeDTree('0008', '部門0009', '0008', '給与明細書008', '', '', [new NodeDTree('0008-1', '部門0008-1', '0008-1', '給与明細書008-1', '', '', []),
                                new NodeDTree('0008-2', '部門0008-2', '0008-2', '給与明細書008-2', '', '', [])]),
                            new NodeDTree('0009', '部門0009', '0009', '給与明細書009', '', '', [])]),
                        new NodeDTree('0010', '部門0010', '0010', '給与明細書010', '', '', []),
                        new NodeDTree('0011', '部門0011', '', '', '', '', []),
                        new NodeDTree('0012', '', '', '', '', '', [])];
                    var primaryKey = "code";
                    var multiple = true;
                    var childField = "childs";
                    var columns = [{ headerText: "", width: "250px", key: 'code', dataType: "string", hidden: true },
                        { headerText: "コード/名称", key: 'nodeText', width: "230px", dataType: "string" },
                        { headerText: "", key: 'paymentDocCode', dataType: "string", hidden: true },
                        { headerText: "", key: 'paymentDocName', dataType: "string", hidden: true },
                        { headerText: "", key: 'bonusDocCode', dataType: "string", hidden: true },
                        { headerText: "", key: 'bonusDocName', dataType: "string", hidden: true },
                        {
                            headerText: "給与明細書", key: "paymentDocCode", dataType: "string", width: "250px", unbound: true,
                            template: "<input type='button' value='選択'/><label style='margin-left:5px;'>${paymentDocCode}</label><label style='margin-left:15px;'>${paymentDocName}</label>"
                        },
                        {
                            headerText: "賞与明細書", key: "bonusDocCode", dataType: "string", width: "250px", unbound: true,
                            template: "<input type='button' value='選択'/><label style='margin-left:5px;'>${bonusDocCode}</label><label style='margin-left:15px;'>${bonusDocName}</label>"
                        },
                    ];
                    self.dataSourceDTree = dataDTree;
                    self.selectedValueDTree = ko.observableArray([]);
                    var $treegrid = $("#treegrid");
                    self.selectedValueDTree.subscribe(function (newValue) {
                        $treegrid.igTreeGridSelection("clearSelection");
                        newValue.forEach(function (id) {
                            $treegrid.igTreeGridSelection("selectRowById", id);
                        });
                    });
                    var treeGridId = "treegrid";
                    $treegrid.igTreeGrid({
                        width: 800,
                        height: 300,
                        dataSource: dataDTree,
                        primaryKey: primaryKey,
                        columns: columns,
                        childDataKey: childField,
                        initialExpandDepth: 10,
                        features: [
                            {
                                name: "Selection",
                                multipleSelection: true,
                                activation: true,
                                rowSelectionChanged: function (evt, ui) {
                                    var selectedRows = ui.selectedRows;
                                    self.selectedValueDTree(_.map(selectedRows, function (row) {
                                        return row.id;
                                    }));
                                }
                            },
                            {
                                name: "RowSelectors",
                                enableCheckBoxes: multiple,
                                checkBoxMode: "biState"
                            }]
                    });
                    $treegrid.closest('.ui-igtreegrid').addClass('nts-treegridview');
                    $treegrid.on("selectChange", function () {
                        var scrollContainer = $("#" + treeGridId + "_scroll");
                        var row1 = "" + self.selectedValueDTree()[0];
                        if (row1) {
                            var index = 1;
                            var rowHeight = $('#' + treeGridId + "_" + row1).height();
                            scrollContainer.scrollTop(rowHeight * index);
                        }
                    });
                }
                ScreenModel.prototype.openJDialog = function () {
                    alert('J');
                };
                ScreenModel.prototype.openKDialog = function () {
                    alert('K');
                };
                ScreenModel.prototype.openMDialog = function () {
                    alert('M');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var NodeDTree = (function () {
                function NodeDTree(code, name, paymentDocCode, paymentDocName, bonusDocCode, bonusDocName, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.paymentDocCode = paymentDocCode;
                    self.paymentDocName = paymentDocName;
                    self.bonusDocCode = bonusDocCode;
                    self.bonusDocName = bonusDocName;
                    self.childs = childs;
                }
                return NodeDTree;
            }());
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm020.d || (qmm020.d = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.d.viewmodel.js.map