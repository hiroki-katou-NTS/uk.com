var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var base;
                (function (base) {
                    var simplehistory;
                    (function (simplehistory) {
                        var dialogbase;
                        (function (dialogbase) {
                            var ScreenMode = (function () {
                                function ScreenMode() {
                                }
                                ScreenMode.MODE_MASTER_HISTORY = 0;
                                ScreenMode.MODE_HISTORY_ONLY = 1;
                                return ScreenMode;
                            }());
                            dialogbase.ScreenMode = ScreenMode;
                        })(dialogbase = simplehistory.dialogbase || (simplehistory.dialogbase = {}));
                    })(simplehistory = base.simplehistory || (base.simplehistory = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
