var qmm012;
(function (qmm012) {
    var j;
    (function (j) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //gridlist
                    self.items = ko.observableArray([
                        new ItemModel('001', 'name1', "name1"),
                        new ItemModel('002', 'name1', "name1"),
                        new ItemModel('003', 'name1', "name1"),
                        new ItemModel('004', 'name1', "name1"),
                        new ItemModel('005', 'name1', "name1"),
                        new ItemModel('006', 'name1', "name1"),
                        new ItemModel('007', 'name1', "name1"),
                        new ItemModel('008', 'name1', "name1"),
                        new ItemModel('009', 'name1', "name1"),
                        new ItemModel('010', 'name1', "name1"),
                        new ItemModel('011', 'name1', "name1"),
                        new ItemModel('012', 'name1', "name1"),
                        new ItemModel('013', 'name1', "name1"),
                        new ItemModel('014', 'name1', "name1"),
                        new ItemModel('015', 'name1', "name1"),
                        new ItemModel('016', 'name1', "name1"),
                        new ItemModel('017', 'name1', "name1"),
                        new ItemModel('018', 'name1', "name1"),
                        new ItemModel('019', 'name1', "name1"),
                        new ItemModel('020', 'name1', "name1")
                    ]);
                    self.columns = ko.observableArray([
                        { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢晢ｿｽ', prop: 'code', width: 40 },
                        { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', prop: 'name', width: 130 },
                        { headerText: '陷奇ｽｰ陋ｻ�ｽｷ騾包ｽｨ陷ｷ蜥ｲ�ｽｧ�ｽｰ', prop: 'description', width: 150 },
                        { headerText: '陷奇ｽｰ陋ｻ�ｽｷ騾包ｽｨ�ｽｧ�ｽｰ', prop: 'mieo', width: 130 },
                        { headerText: '陷奇ｽｰ陋ｻ�ｽｷ騾包ｽｨ�ｽｰ', prop: 'pika', width: 170 }
                    ]);
                    self.currentCode = ko.observable();
                }
                ScreenModel.prototype.SubmitDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.CloseDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name, description) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                }
                return ItemModel;
            }());
        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
    })(j = qmm012.j || (qmm012.j = {}));
})(qmm012 || (qmm012 = {}));
