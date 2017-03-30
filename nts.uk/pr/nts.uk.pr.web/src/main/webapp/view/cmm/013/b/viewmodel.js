var cmm013;
(function (cmm013) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //self.listBox = new ListBox();
                    self.itemList = ko.observableArray([]);
                    self.selectedCode = ko.observable(null);
                }
                ScreenModel.prototype.buildItemList = function () {
                    var self = this;
                    self.itemList.removeAll();
                    self.itemList.push(new ItemModel('1', 'B0005', 'レーザー', '1'));
                    self.itemList.push(new ItemModel('2', 'B0006', 'プリンタ', '1'));
                    self.itemList.push(new ItemModel('3', 'B0007', 'プリンタ４', '3'));
                    self.itemList.push(new ItemModel('4', 'B0008', 'プリンタ5', '4'));
                    self.itemList.push(new ItemModel('5', 'B0009', 'プリンタ6', '5'));
                    self.itemList.push(new ItemModel('6', 'B0010', 'プリンタ7', '6'));
                    self.itemList.push(new ItemModel('7', 'B0011', 'レーザー', '7'));
                    self.itemList.push(new ItemModel('8', 'B0012', 'プリンタ', '8'));
                    self.itemList.push(new ItemModel('9', 'B0013', 'プリンタ４', '9'));
                    self.itemList.push(new ItemModel('10', 'B0014', 'プリンタ5', '10'));
                    self.itemList.push(new ItemModel('11', 'B0015', 'プリンタ6', '11'));
                    self.itemList.push(new ItemModel('12', 'B0016', 'プリンタ7', '12'));
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.buildItemList();
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.chooseItem = function () {
                    var self = this;
                    var item = _.find(self.itemList(), function (item) { return item.name === self.selectedCode(); });
                    nts.uk.ui.windows.setShared('selectedName', item.name);
                    nts.uk.ui.windows.setShared('selectedCode', item.code);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(stt, code, name, code1) {
                    this.stt = stt;
                    this.code = code;
                    this.name = name;
                    this.code1 = code1;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = cmm013.b || (cmm013.b = {}));
})(cmm013 || (cmm013 = {}));
