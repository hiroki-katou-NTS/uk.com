var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var init;
            (function (init) {
                var _start;
                __viewContext.ready = function (callback) {
                    _start = callback;
                };
                __viewContext.bind = function (contentViewModel) {
                    ui._viewModel = {
                        content: contentViewModel,
                        kiban: new KibanViewModel() // Kiban's view model
                    };
                    ko.applyBindings(ui._viewModel);
                };
                $(function () {
                    __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                        .map(function (v) { return JSON.parse(v); });
                    _start.call(__viewContext);
                });
                // Kiban ViewModel
                var KibanViewModel = (function () {
                    function KibanViewModel() {
                        var self = this;
                        self.errorDialogViewModel = new nts.uk.ui.errors.ErrorsViewModel();
                    }
                    return KibanViewModel;
                }());
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
