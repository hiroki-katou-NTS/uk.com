__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.items = ko.observableArray(testdata.createHogeArray(100));
            this.first = this.items()[0];
        }
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
    nts.uk.ui.ig.grid.header.getLabel('grid_test', 'code')
        .append($('<button />').text('hello').click(function () { alert('hello'); }));
});
