module nts.uk.ui.jqueryExtentions {

    module ntsTextBox {
        $.fn.ntsTextBox = function () {

            if (arguments.length === 1) {
                var p: any = arguments[0];
                if (_.isPlainObject(p)) {
                    return init(p);
                }
            }
        };

        function init(param): JQuery {
            return null;
        }
    }
}