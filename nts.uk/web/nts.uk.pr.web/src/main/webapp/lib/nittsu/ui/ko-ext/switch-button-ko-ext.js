var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsSwitchButtonBindingHandler = (function () {
                    function NtsSwitchButtonBindingHandler() {
                    }
                    NtsSwitchButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    NtsSwitchButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var selectedCssClass = 'selected';
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        $('button', container).each(function (index, btn) {
                            var $btn = $(btn);
                            var btnValue = $(btn).data('swbtn');
                            var foundFlag = _.findIndex(options, function (opt) {
                                return opt[optionValue] == btnValue;
                            }) != -1;
                            if (!foundFlag) {
                                $btn.remove();
                                return;
                            }
                        });
                        _.forEach(options, function (opt) {
                            var value = opt[optionValue];
                            var text = opt[optionText];
                            var targetBtn;
                            $('button', container).each(function (index, btn) {
                                var btnValue = $(btn).data('swbtn');
                                if (btnValue == value) {
                                    targetBtn = $(btn);
                                }
                                if (btnValue == selectedValue) {
                                    $(btn).addClass(selectedCssClass);
                                }
                                else {
                                    $(btn).removeClass(selectedCssClass);
                                }
                            });
                            if (targetBtn) {
                            }
                            else {
                                var btn = $('<button>').text(text)
                                    .addClass('nts-switch-button')
                                    .data('swbtn', value)
                                    .on('click', function () {
                                    var selectedValue = $(this).data('swbtn');
                                    data.value(selectedValue);
                                    $('button', container).removeClass(selectedCssClass);
                                    $(this).addClass(selectedCssClass);
                                });
                                if (selectedValue == value) {
                                    btn.addClass(selectedCssClass);
                                }
                                container.append(btn);
                            }
                        });
                        (enable === true) ? $('button', container).prop("disabled", false) : $('button', container).prop("disabled", true);
                    };
                    return NtsSwitchButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
