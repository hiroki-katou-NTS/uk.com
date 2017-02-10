__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
        }
        ScreenModel.prototype.exportFile = function () {
            nts.uk.request.exportFile('/sample/report/generate').done(function () {
                console.log('DONE!!');
            });
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
