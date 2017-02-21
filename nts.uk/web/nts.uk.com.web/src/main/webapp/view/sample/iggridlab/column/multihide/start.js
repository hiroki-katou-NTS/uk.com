__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.items = ko.observableArray(testdata.createHogeArray(100));
            this.first = this.items()[0];
        }
        ScreenModel.prototype.showColumn = function () {
            $('#grid').igGridHiding('showColumn', 1);
            $('#grid').igGridHiding('showColumn', 2);
            $('#grid').igGrid('option', 'width', 400);
        };
        ScreenModel.prototype.hideColumn = function () {
            $('#grid').igGridHiding('hideColumn', 1);
            $('#grid').igGridHiding('hideColumn', 2);
            $('#grid').igGrid('option', 'width', 100);
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
