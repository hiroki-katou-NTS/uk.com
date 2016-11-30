var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ItemModel = (function () {
                function ItemModel(companyCode, personalWageName) {
                    var self = this;
                    this.companyCode = companyCode;
                    this.personalWageName = personalWageName;
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
                    self.personalWages = ko.observableArray([]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable('0');
                    self.selectedCode = ko.observable('01');
                    self.selectedName = ko.computed(function () {
                        var item = _.find(self.itemList(), function (item) {
                            return item.id === self.selectedCode();
                        });
                        return (item !== undefined) ? item.name : null;
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
                ScreenModel.prototype.buildItemList = function () {
                    var self = this;
                    //            self.listBox.itemList.removeAll();
                    _.forEach(self.listBox.personalWages(), function (personalWage) {
                        var companyCode = personalWage.companyCode;
                        if (companyCode.length == 1) {
                            companyCode = "0" + personalWage.companyCode;
                        }
                        self.listBox.itemList.push(new ItemModel(companyCode, personalWage.personalWageName));
                    });
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.buildItemList();
                    $('#LST_001').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#LST_001').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
                    h.service.getPersonalWageNames().done(function (personalWage) {
                        self.listBox.personalWages(personalWage);
                        self.buildItemList();
                    });
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.chooseItem = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('selectedName', self.listBox.selectedName());
                    nts.uk.ui.windows.setShared('selectedCode', self.listBox.selectedCode());
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
