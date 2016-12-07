interface JQuery {
    ntsPopup(args: any): JQuery;
    ntsError(action: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsError {
        var DATA_HAS_ERROR = 'haserror';

        $.fn.ntsError = function(action: string, message: string): any {
            var $control = $(this);

            var result;
            switch (action) {
                case 'set':
                    return setError($control, message);
                case 'clear':
                    var x = [];
                    for (var i = 0; i < $control.length; i++) {
                        x.push(clearErrors($($control[i])));
                    }
                    return x;
                //                    return clearErrors($control);
                case 'hasError':    
                    for (var i = 0; i < $control.length; i++) {
                        if(hasError($($control[i]))){
                            return true;    
                        }
                    }
                    return false;
            }

        }

        function setError($control: JQuery, message: string) {
            $control.data(DATA_HAS_ERROR, true);
            ui.errors.add({
                location: $control.data('name') || "",
                message: message,
                $control: $control
            });

            $control.parent().addClass('error');

            return $control;
        }

        function clearErrors($control: JQuery) {
            $control.data(DATA_HAS_ERROR, false);
            ui.errors.removeByElement($control);

            $control.removeClass('error');

            return $control;
        }

        function hasError($control: JQuery) {
            return $control.data(DATA_HAS_ERROR) === true;
        }
    }

    module ntsPopup {
        let DATA_INSTANCE_NAME = 'nts-popup-panel';

        $.fn.ntsPopup = function() {

            if (arguments.length === 1) {
                var p: any = arguments[0];
                if (_.isPlainObject(p)) {
                    return init.apply(this, arguments);
                }
            }

            if (typeof arguments[0] === 'string') {
                return handleMethod.apply(this, arguments);
            }
        }

        function init(param): JQuery {
            var popup = new NtsPopupPanel($(this), param.position);

            _.defer(function() {
                $(window).mousedown(function(e) {
                    if ($(e.target).closest(popup.$panel).length === 0) {
                        popup.hide();
                    }
                });
            });

            return popup.$panel;
        }

        function handleMethod() {
            var methodName: string = arguments[0];
            var popup: NtsPopupPanel = $(this).data(DATA_INSTANCE_NAME);

            switch (methodName) {
                case 'show':
                    popup.show();
                    break;
            }
        }

        class NtsPopupPanel {

            $panel: JQuery;
            position: any;

            constructor($panel: JQuery, position: any) {

                this.position = position;

                this.$panel = $panel
                    .data(DATA_INSTANCE_NAME, this)
                    .addClass('popup-panel')
                    .appendTo('body');
            }

            show() {
                this.$panel
                    .css({
                        visibility: 'hidden',
                        display: 'block'
                    })
                    .position(this.position)
                    .css({
                        visibility: 'visible'
                    });
            }

            hide() {
                this.$panel.css({
                    display: 'none'
                });
            }
        }
    }
}