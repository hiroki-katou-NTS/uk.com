<<<<<<< HEAD
/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            /** Event to notify document ready to initialize UI. */
            ui.documentReady = $.Callbacks();
            /** Event to notify ViewModel built to bind. */
            ui.viewModelBuilt = $.Callbacks();
            // Kiban ViewModel
            var KibanViewModel = (function () {
                function KibanViewModel() {
                    this.title = ko.observable('');
                    this.errorDialogViewModel = new nts.uk.ui.errors.ErrorsViewModel();
                }
                return KibanViewModel;
            }());
            ui.KibanViewModel = KibanViewModel;
            var init;
            (function (init) {
                var _start;
                __viewContext.ready = function (callback) {
                    _start = callback;
                };
                __viewContext.bind = function (contentViewModel) {
                    var kiban = new KibanViewModel();
                    ui._viewModel = {
                        content: contentViewModel,
                        kiban: kiban,
                        errors: {
                            isEmpty: ko.computed(function () { return !kiban.errorDialogViewModel.occurs(); })
                        }
                    };
                    kiban.title(__viewContext.title || 'THIS IS TITLE');
                    ui.viewModelBuilt.fire(ui._viewModel);
                    ko.applyBindings(ui._viewModel);
                };
                $(function () {
                    ui.documentReady.fire();
                    __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                        .map(function (v) { return JSON.parse(v); });
                    _.defer(function () { return _start.call(__viewContext); });
                });
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
=======
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            ui.documentReady = $.Callbacks();
            ui.viewModelBuilt = $.Callbacks();
            var KibanViewModel = (function () {
                function KibanViewModel() {
                    this.title = ko.observable('');
                    this.errorDialogViewModel = new nts.uk.ui.errors.ErrorsViewModel();
                }
                return KibanViewModel;
            }());
            ui.KibanViewModel = KibanViewModel;
            var init;
            (function (init) {
                var _start;
                __viewContext.ready = function (callback) {
                    _start = callback;
                };
                __viewContext.bind = function (contentViewModel) {
                    var kiban = new KibanViewModel();
                    ui._viewModel = {
                        content: contentViewModel,
                        kiban: kiban,
                        errors: {
                            isEmpty: ko.computed(function () { return !kiban.errorDialogViewModel.occurs(); })
                        }
                    };
                    kiban.title(__viewContext.title || 'THIS IS TITLE');
                    ui.viewModelBuilt.fire(ui._viewModel);
                    ko.applyBindings(ui._viewModel);
                };
                $(function () {
                    ui.documentReady.fire();
                    __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                        .map(function (v) { return JSON.parse(v); });
                    _.defer(function () { return _start.call(__viewContext); });
                });
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
>>>>>>> basic/develop
//# sourceMappingURL=init.js.map