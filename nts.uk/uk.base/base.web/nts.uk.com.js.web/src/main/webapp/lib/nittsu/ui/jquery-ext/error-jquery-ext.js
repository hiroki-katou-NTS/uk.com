var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            ui.DATA_SET_ERROR_STYLE = "set-error-style";
            ui.DATA_CLEAR_ERROR_STYLE = "clear-error-style";
            ui.DATA_HAS_ERROR = 'hasError';
            ui.DATA_GET_ERROR = 'getError';
            var bindErrorStyle;
            (function (bindErrorStyle) {
                function setError($element, callback) {
                    $element.data(ui.DATA_SET_ERROR_STYLE, callback);
                }
                bindErrorStyle.setError = setError;
                function clearError($element, callback) {
                    $element.data(ui.DATA_CLEAR_ERROR_STYLE, callback);
                }
                bindErrorStyle.clearError = clearError;
                function useDefaultErrorClass($element) {
                    setError($element, function () { $element.addClass("error"); });
                    clearError($element, function () { $element.removeClass("error"); });
                }
                bindErrorStyle.useDefaultErrorClass = useDefaultErrorClass;
            })(bindErrorStyle = ui.bindErrorStyle || (ui.bindErrorStyle = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsError;
                (function (ntsError) {
                    $.fn.ntsError = function (action, message, errorCode, businessError) {
                        var $control = $(this);
                        if (action === ui.DATA_HAS_ERROR) {
                            return _.some($control, function (c) { return hasError($(c)); });
                        }
                        else if (action === ui.DATA_GET_ERROR) {
                            return getErrorByElement($control.first());
                        }
                        else {
                            $control.each(function (index) {
                                var $item = $(this);
                                $item = processErrorOnItem($item, message, action, errorCode, businessError);
                            });
                            return $control;
                        }
                    };
                    function processErrorOnItem($control, message, action, errorCode, businessError) {
                        switch (action) {
                            case 'check':
                                return $control.trigger("validate");
                            case 'set':
                                return setError($control, message, errorCode, businessError);
                            case 'clear':
                                return clearErrors($control);
                            case 'clearByCode':
                                return clearErrorByCode($control, message);
                            case 'clearKibanError':
                                return clearKibanError($control);
                        }
                    }
                    function getErrorByElement($control) {
                        return ui.errors.getErrorByElement($control);
                    }
                    function setError($control, message, errorCode, businessError) {
                        ui.errors.add({
                            location: $control.data('name') || "",
                            message: message,
                            errorCode: errorCode,
                            $control: $control,
                            businessError: businessError
                        });
                        return $control;
                    }
                    function clearErrors($control) {
                        ui.errors.removeByElement($control);
                        return $control;
                    }
                    function clearErrorByCode($control, errorCode) {
                        ui.errors.removeByCode($control, errorCode);
                        return $control;
                    }
                    function clearKibanError($control) {
                        ui.errors.removeCommonError($control);
                        return $control;
                    }
                    function hasError($control) {
                        return $control.data(ui.DATA_HAS_ERROR) === true;
                    }
                })(ntsError || (ntsError = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=error-jquery-ext.js.map