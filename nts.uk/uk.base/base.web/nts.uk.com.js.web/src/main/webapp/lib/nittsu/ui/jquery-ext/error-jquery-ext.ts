/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsError(action: string, param?: any, errorCode?: string, businessError?: boolean): any;
}

module nts.uk.ui {
    export const DATA_SET_ERROR_STYLE = "set-error-style";
    export const DATA_CLEAR_ERROR_STYLE = "clear-error-style";
}

module nts.uk.ui.jqueryExtentions {

    module ntsError {
        var DATA_HAS_ERROR = 'hasError';
        var DATA_GET_ERROR = 'getError';

        $.fn.ntsError = function(action: string, message: any, errorCode?: string, businessError?: boolean): any {
            var $control = $(this);
            if (action === DATA_HAS_ERROR) {
                return _.some($control, c => hasError($(c)));
            } else if (action === DATA_GET_ERROR) {
                return getErrorByElement($control.first());        
            } else {
                $control.each(function(index) {
                    var $item = $(this);
                    $item = processErrorOnItem($item, message, action, errorCode, businessError);
                });
                return $control;
            }

        }

        function processErrorOnItem($control: JQuery, message: any, action: string, errorCode: string, businessError: boolean) {
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
        
        function getErrorByElement($control: JQuery) {
            return ui.errors.getErrorByElement($control);
        }

        function setError($control: JQuery, message: any, errorCode: string, businessError?: boolean) {
            $control.data(DATA_HAS_ERROR, true);
            ui.errors.add({
                location: $control.data('name') || "",
                message: message,
                errorCode: errorCode,
                $control: $control,
                businessError: businessError
            });
            
            ($control.data(DATA_SET_ERROR_STYLE) || function () { $control.parent().addClass('error'); })();
            
            return $control;
        }

        function clearErrors($control: JQuery) {
            $control.data(DATA_HAS_ERROR, false);
            ui.errors.removeByElement($control);
            
            ($control.data(DATA_CLEAR_ERROR_STYLE) || function () { $control.parent().removeClass('error'); })();
            return $control;
        }

        function clearErrorByCode($control: JQuery, errorCode: string) {
            ui.errors.removeByCode($control, errorCode);
            let remainErrors = ui.errors.getErrorByElement($control);
            if(util.isNullOrEmpty(remainErrors)) {
                $control.data(DATA_HAS_ERROR, false);
                ($control.data(DATA_CLEAR_ERROR_STYLE) || function () { $control.parent().removeClass('error'); })();
            }
            return $control;
        }
        
        function clearKibanError($control: JQuery) {
            ui.errors.removeCommonError($control);
            let remainErrors = ui.errors.getErrorByElement($control);
            if(util.isNullOrEmpty(remainErrors)) {
                $control.data(DATA_HAS_ERROR, false);
                ($control.data(DATA_CLEAR_ERROR_STYLE) || function () { $control.parent().removeClass('error'); })();
            }
            return $control;
        }

        function hasError($control: JQuery) {
            return $control.data(DATA_HAS_ERROR) === true;
        }
    }
}
