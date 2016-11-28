var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
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
                        new ItemModel("21", "ドットプリンタ21")
                    ]);
                    self.itemName = ko.observable("");
                    self.currentCode = ko.observable(3);
                    self.selectedCode = ko.observable(null);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                    self.selectLayoutCode = ko.observable("001");
                    self.itemList();
                    $('#LST_001').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#LST_001').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
                    h.service.getAllLayout().done(function (layout) {
                        self.layouts(layout);
                        //                self.startDialog();
                    });
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(companyCode, personalWaveName) {
                    this.companyCode = companyCode;
                    this.personalWaveName = personalWaveName;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
