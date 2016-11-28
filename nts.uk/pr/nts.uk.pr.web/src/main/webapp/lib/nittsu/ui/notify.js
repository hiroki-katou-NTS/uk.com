var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var notify;
            (function (notify) {
                var error;
                (function (error) {
                    ui.documentReady.add(function () {
                        $('#func-notifier-errors').position({ my: 'left+5 top-5', at: 'left bottom', of: $('#functions-area') });
                    });
                })(error || (error = {}));
            })(notify = ui.notify || (ui.notify = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
