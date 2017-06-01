var kdl021;
(function (kdl021) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.isMulti = true;
                    self.isMulti = nts.uk.ui.windows.getShared('Multiple');
                    self.items = ko.observableArray([]);
                    //header
                    self.columns = ko.observableArray([
                        { headerText: nts.uk.resource.getText("KDL021_3"), prop: 'code', width: 70 },
                        { headerText: nts.uk.resource.getText("KDL021_4"), prop: 'name', width: 230 }
                    ]);
                    self.currentCodeList = ko.observableArray();
                    self.posibleItems = [];
                    self.dataSoure = [];
                    self.start();
                }
                //load data
                ScreenModel.prototype.start = function () {
                    var self = this;
                    //all possible attendance items
                    self.posibleItems = nts.uk.ui.windows.getShared('AllAttendanceObj');
                    //selected attendace items
                    self.currentCodeList(nts.uk.ui.windows.getShared('SelectedAttendanceId'));
                    //the fist item 
                    self.dataSoure.push(new ItemModel("", "選択なし"));
                    //set source
                    if (self.posibleItems.length > 0) {
                        a.service.getPossibleItem(self.posibleItems).done(function (lstItem) {
                            for (var i in lstItem) {
                                self.dataSoure.push(new ItemModel(lstItem[i].attendanceItemId.toString(), lstItem[i].attendanceItemName.toString()));
                            }
                            ;
                            //set source
                            self.items(self.dataSoure);
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    }
                    //勤怠項目の指定が0件の場合
                    //set source
                    self.items(self.dataSoure);
                };
                //event When click to 設定 ボタン
                ScreenModel.prototype.register = function () {
                    var self = this;
                    if (self.currentCodeList().length == 0) {
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_78'));
                    }
                    else {
                        nts.uk.ui.windows.setShared('selectedChildAttendace', self.currentCodeList());
                        nts.uk.ui.windows.close();
                    }
                };
                //検索条件をクリアする
                ScreenModel.prototype.clearClick = function () {
                    var self = this;
                    self.items([]);
                    self.items(self.dataSoure);
                    //selected attendace items
                    self.currentCodeList(nts.uk.ui.windows.getShared('SelectedAttendanceId'));
                };
                //Close Dialog
                ScreenModel.prototype.close = function () {
                    nts.uk.ui.windows.close();
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = kdl021.a || (kdl021.a = {}));
})(kdl021 || (kdl021 = {}));
//# sourceMappingURL=kdl021.a.vm.js.map