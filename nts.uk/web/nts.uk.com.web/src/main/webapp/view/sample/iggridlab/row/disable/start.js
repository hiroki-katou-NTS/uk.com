__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.items = ko.observableArray(testdata.createHogeArray(100));
            this.first = this.items()[0];
        }
        ScreenModel.prototype.activeRowChanging = function (evt, ui) {
            if (ui.row !== null && ui.row.id === '2') {
                return false;
            }
        };
        ScreenModel.prototype.rowSelectionChanging = function (evt, ui) {
            if (ui.row !== null && ui.row.id === '2') {
                return false;
            }
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
