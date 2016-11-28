var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ItemModel = (function () {
                function ItemModel(id, name) {
                    var self = this;
                    this.id = id;
                    this.name = name;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var ListBox = (function () {
                function ListBox() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new ItemModel("01", "レーザープリンタ"),
                        new ItemModel("02", "レーザープリンタ"),
                        new ItemModel("03", "レーザープリンタ"),
                        new ItemModel("04", "レーザープリンタ"),
                        new ItemModel("05", "レーザー（圧着式）"),
                        new ItemModel("06", "レーザー（圧着式）"),
                        new ItemModel("07", "ドットプリンタ7"),
                        new ItemModel("08", "ドットプリンタ8"),
                        new ItemModel("09", "ドットプリンタ9"),
                        new ItemModel("10", "ドットプリンタ10"),
                        new ItemModel("11", "ドットプリンタ11"),
                        new ItemModel("12", "ドットプリンタ12"),
                        new ItemModel("13", "ドットプリンタ13"),
                        new ItemModel("14", "ドットプリンタ14"),
                        new ItemModel("15", "ドットプリンタ15"),
                        new ItemModel("16", "ドットプリンタ16"),
                        new ItemModel("17", "ドットプリンタ17"),
                        new ItemModel("18", "ドットプリンタ18"),
                        new ItemModel("19", "ドットプリンタ19"),
                        new ItemModel("20", "ドットプリンタ20"),
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable('');
                    self.selectedCode = ko.observable('01');
                    self.itemName = ko.observable('');
                    self.itemList();
                    $('#list-box').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#list-box').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                }
                return ListBox;
            }());
            viewmodel.ListBox = ListBox;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.listBox = new ListBox();
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
