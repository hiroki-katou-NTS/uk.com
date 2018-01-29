/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsError(action: string, param?: any, errorCode?: string): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsError {
        var DATA_HAS_ERROR = 'hasError';
        var DATA_GET_ERROR = 'getError';

        $.fn.ntsError = function(action: string, message: any, errorCode?: string): any {
            var $control = $(this);
            if (action === DATA_HAS_ERROR) {
                return _.some($control, c => hasError($(c)));
            } else if (action === DATA_GET_ERROR) {
                return getErrorByElement($control.first());        
            } else {
                $control.each(function(index) {
                    var $item = $(this);
                    $item = processErrorOnItem($item, message, action, errorCode);
                });
                return $control;
            }

        }

        function processErrorOnItem($control: JQuery, message: any, action: string, errorCode: string) {
            switch (action) {
                case 'set':
                    return setError($control, message, errorCode);
                case 'clear':
                    return clearErrors($control);
                case 'clearByCode':
                    return clearErrorByCode($control, message);
            }
        }
        
        function getErrorByElement($control: JQuery) {
            return ui.errors.getErrorByElement($control);
        }

        function setError($control: JQuery, message: any, errorCode: string) {
            $control.data(DATA_HAS_ERROR, true);
            ui.errors.add({
                location: $control.data('name') || "",
                message: message,
                errorCode: errorCode,
                $control: $control
            });
            $control.parent().addClass('error');
            return $control;
        }

        function clearErrors($control: JQuery) {
            $control.data(DATA_HAS_ERROR, false);
            ui.errors.removeByElement($control);
            $control.parent().removeClass('error');
            return $control;
        }

        function clearErrorByCode($control: JQuery, errorCode: string) {
            ui.errors.removeByCode($control, errorCode);
            let remainErrors = ui.errors.getErrorByElement($control);
            if(util.isNullOrUndefined(remainErrors)) {
                $control.data(DATA_HAS_ERROR, false);
                $control.parent().removeClass('error');
            }
            return $control;
        }

        function hasError($control: JQuery) {
            return $control.data(DATA_HAS_ERROR) === true;
        }
    }
}
