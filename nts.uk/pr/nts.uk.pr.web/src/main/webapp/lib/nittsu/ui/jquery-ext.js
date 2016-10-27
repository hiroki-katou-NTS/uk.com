var nts;
(function (nts) {
    (function (uk) {
        (function (ui) {
            (function (jqueryExtentions) {
                var ntsTextBox;
                (function (ntsTextBox) {
                    $.fn.ntsTextBox = function () {
                        if (arguments.length === 1) {
                            var p = arguments[0];
                            if (_.isPlainObject(p)) {
                                return init(p);
                            }
                        }
                    };

                    function init(param) {
                        return null;
                    }
                })(ntsTextBox || (ntsTextBox = {}));
            })(ui.jqueryExtentions || (ui.jqueryExtentions = {}));
            var jqueryExtentions = ui.jqueryExtentions;
        })(uk.ui || (uk.ui = {}));
        var ui = uk.ui;
    })(nts.uk || (nts.uk = {}));
    var uk = nts.uk;
})(nts || (nts = {}));
