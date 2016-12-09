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
                    var DATA_HAS_ERROR = 'hasError';
                    $.fn.ntsError = function (action, message) {
                        var $control = $(this);
                        if (action === DATA_HAS_ERROR) {
                            return _.some($control, function (c) { return hasError($(c)); });
                        }
                        else {
                            $control.each(function (index) {
                                var $item = $(this);
                                $item = processErrorOnItem($item, message, action);
                            });
                            return $control;
                        }
                    };
                    //function for set and clear error
                    function processErrorOnItem($control, message, action) {
                        switch (action) {
                            case 'set':
                                return setError($control, message);
                            case 'clear':
                                return clearErrors($control);
                        }
                    }
                    function setError($control, message) {
                        $control.data(DATA_HAS_ERROR, true);
                        ui.errors.add({
                            location: $control.data('name') || "",
                            message: message,
                            $control: $control
                        });
                        $control.parent().addClass('error');
                        return $control;
                    }
                    function clearErrors($control) {
                        $control.data(DATA_HAS_ERROR, false);
                        ui.errors.removeByElement($control);
                        $control.parent().removeClass('error');
                        return $control;
                    }
                    function hasError($control) {
                        return $control.data(DATA_HAS_ERROR) === true;
                    }
                })(ntsError || (ntsError = {}));
                var ntsPopup;
                (function (ntsPopup) {
                    var DATA_INSTANCE_NAME = 'nts-popup-panel';
                    $.fn.ntsPopup = function () {
                        if (arguments.length === 1) {
                            var p = arguments[0];
                            if (_.isPlainObject(p)) {
                                return init.apply(this, arguments);
                            }
                        }
                        if (typeof arguments[0] === 'string') {
                            return handleMethod.apply(this, arguments);
                        }
                    };
                    function init(param) {
                        var popup = new NtsPopupPanel($(this), param.position);
                        var dismissible = param.dismissible === false;
                        _.defer(function () {
                            if (!dismissible) {
                                $(window).mousedown(function (e) {
                                    //console.log(dismissible);
                                    if ($(e.target).closest(popup.$panel).length === 0) {
                                        popup.hide();
                                    }
                                });
                            }
                        });
                        return popup.$panel;
                    }
                    function handleMethod() {
                        var methodName = arguments[0];
                        var popup = $(this).data(DATA_INSTANCE_NAME);
                        switch (methodName) {
                            case 'show':
                                popup.show();
                                break;
                            case 'hide':
                                popup.hide();
                                break;
                            case 'destroy':
                                popup.hide();
                                popup.destroy();
                                break;
                        }
                    }
                    var NtsPopupPanel = (function () {
                        function NtsPopupPanel($panel, position) {
                            this.position = position;
                            this.$panel = $panel
                                .data(DATA_INSTANCE_NAME, this)
                                .addClass('popup-panel')
                                .appendTo('body');
                        }
                        NtsPopupPanel.prototype.show = function () {
                            this.$panel
                                .css({
                                visibility: 'hidden',
                                display: 'block'
                            })
                                .position(this.position)
                                .css({
                                visibility: 'visible'
                            });
                        };
                        NtsPopupPanel.prototype.hide = function () {
                            this.$panel.css({
                                display: 'none'
                            });
                        };
                        NtsPopupPanel.prototype.destroy = function () {
                            this.$panel = null;
                        };
                        return NtsPopupPanel;
                    }());
                })(ntsPopup || (ntsPopup = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
