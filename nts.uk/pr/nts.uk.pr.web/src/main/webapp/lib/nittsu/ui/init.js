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
                __viewContext.bind = function (viewModel) {
                    ui._viewModel = {
                        content: viewModel // developer's view model
                    };
                };
                $(function () {
                    _start.call(__viewContext);
                    ko.applyBindings(ui._viewModel);
                });
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
