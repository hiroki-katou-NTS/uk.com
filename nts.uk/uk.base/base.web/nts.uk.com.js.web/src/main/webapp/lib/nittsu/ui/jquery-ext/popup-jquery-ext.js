var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsPopup;
                (function (ntsPopup) {
                    var DATA_INSTANCE_NAME = 'nts-popup-panel';
                    ;
                    $.fn.ntsPopup = handler;
                    function handler(action, option) {
                        var $control = $(this);
                        if (typeof action !== 'string') {
                            handler.call(this, "init", action);
                        }
                        switch (action) {
                            case 'init':
                                init($control, option);
                                break;
                            case 'show':
                                show($control);
                                break;
                            case 'hide':
                                hide($control);
                                break;
                            case 'destroy':
                                destroy($control);
                                break;
                            case 'toggle':
                                toggle($control);
                                break;
                        }
                    }
                    function init(control, option) {
                        control.addClass("popup-panel").css("z-index", 100).show();
                        var defaultoption = {
                            trigger: "",
                            position: {
                                my: 'left top',
                                at: 'left bottom',
                                of: control.siblings('.show-popup')
                            },
                            showOnStart: false,
                            dismissible: true
                        };
                        var setting = $.extend({}, defaultoption, option);
                        control.data("option", setting);
                        $(setting.trigger).on("click.popup", function (e) {
                            show(control);
                        });
                        if (setting.dismissible) {
                            $(window).on("mousedown.popup", function (e) {
                                if (!$(e.target).is(control)
                                    && control.has(e.target).length === 0
                                    && !$(e.target).is(setting.trigger)) {
                                    hide(control);
                                }
                            });
                        }
                        if (setting.showOnStart)
                            show(control);
                        else
                            hide(control);
                        return control;
                    }
                    function show(control) {
                        control.css({
                            visibility: 'visible',
                        });
                        control.position(control.data("option").position);
                        return control;
                    }
                    function hide(control) {
                        control.css({
                            visibility: 'hidden',
                            top: "-9999px",
                            left: "-9999px"
                        });
                        return control;
                    }
                    function destroy(control) {
                        hide(control);
                        $(control.data("option").trigger).off("click.popup");
                        $(window).off("click.popup");
                        return control;
                    }
                    function toggle(control) {
                        var isDisplaying = control.css("visibility");
                        if (isDisplaying === 'hidden') {
                            show(control);
                        }
                        else {
                            hide(control);
                        }
                        return control;
                    }
                    var NtsPopupPanel = (function () {
                        function NtsPopupPanel($panel, option) {
                            var parent = $panel.parent();
                            this.$panel = $panel
                                .data(DATA_INSTANCE_NAME, this)
                                .addClass('popup-panel')
                                .appendTo(parent);
                            this.$panel.css("z-index", 100);
                        }
                        return NtsPopupPanel;
                    }());
                })(ntsPopup = jqueryExtentions.ntsPopup || (jqueryExtentions.ntsPopup = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=popup-jquery-ext.js.map