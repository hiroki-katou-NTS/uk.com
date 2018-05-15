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
                        var $functionsArea = $('#functions-area');
                        var $functionsAreaBottom = $('#functions-area-bottom');
                        if ($functionsArea.length > 0) {
                            _.defer(function () {
                                $('#func-notifier-errors').position({ my: 'left+5 top-5', at: 'left bottom', of: $('#functions-area') });
                            });
                        }
                        else if ($functionsAreaBottom.length > 0) {
                            _.defer(function () {
                                $('#func-notifier-errors').position({ my: 'left+5 top+48', at: 'left top', of: $('#functions-area-bottom') });
                            });
                        }
                        else {
                            return;
                        }
                    });
                })(error || (error = {}));
            })(notify = ui.notify || (ui.notify = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=notify.js.map