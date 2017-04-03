var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var DatePickerBindingHandler = (function () {
                    function DatePickerBindingHandler() {
                    }
                    DatePickerBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var container = $(element);
                        if (!container.attr("id")) {
                            var idString = nts.uk.util.randomId();
                            container.attr("id", idString);
                        }
                        container.addClass("ntsControl");
                        var startDate = null;
                        var endDate = null;
                        if (data.startDate) {
                            startDate = ko.unwrap(data.startDate);
                        }
                        if (data.endDate) {
                            endDate = ko.unwrap(data.endDate);
                        }
                        var autoHide = data.autoHide == false ? false : true;
                        var idatr = container.attr("id");
                        container.append("<input id='" + idatr + "-input' class='ntsDatepicker nts-input' />");
                        var $input = container.find('#' + idatr + "-input");
                        var button = null;
                        if (data.button)
                            button = idatr + "_button";
                        $input.prop("readonly", true);
                        var value = ko.unwrap(data.value);
                        var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
                        var containerFormat = 'yyyy/mm/dd';
                        var length = 10, atomWidth = 9.5;
                        if (dateFormat === "yyyy/MM/dd DDD") {
                            length = 16;
                        }
                        else if (dateFormat === "yyyy/MM/dd D") {
                            length = 14;
                        }
                        else if (dateFormat === "yyyy/MM") {
                            length = 7;
                            containerFormat = 'yyyy/mm';
                        }
                        if (containerFormat != 'yyyy/mm')
                            $input.attr('value', nts.uk.time.formatDate(value, dateFormat));
                        else
                            $input.attr('value', value);
                        if (button) {
                            container.append("<input type='button' id='" + button + "' class='datepicker-btn' />");
                            $input.datepicker({
                                format: containerFormat,
                                language: 'ja-JP',
                                trigger: "#" + button,
                                autoHide: autoHide,
                                startDate: startDate,
                                endDate: endDate
                            });
                        }
                        else
                            $input.datepicker({
                                format: containerFormat,
                                language: 'ja-JP',
                                autoHide: autoHide,
                                startDate: startDate,
                                endDate: endDate
                            });
                        container.data("format", containerFormat);
                        if (containerFormat !== 'yyyy/mm')
                            $input.on('change', function (event) {
                                data.value(new Date($input.val().substring(0, 10)));
                            });
                        else
                            $input.on('change', function (event) {
                                var result = nts.uk.time.parseYearMonth($input.val());
                                data.value(result.toValue());
                            });
                        $input.width(Math.floor(atomWidth * length));
                        if (data.disabled !== undefined && ko.unwrap(data.disabled) == true) {
                            $input.prop("disabled", true);
                            if (button) {
                                container.find('.datepicker-btn').prop("disabled", true);
                            }
                        }
                    };
                    DatePickerBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var container = $(element);
                        var newValue = ko.unwrap(data.value);
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
                        var disabled = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
                        var idatr = container.attr("id");
                        var $input = container.find('#' + idatr + "-input");
                        var formatOptions = container.data("format");
                        var oldDate = $input.datepicker("getDate");
                        if (formatOptions != 'yyyy/mm') {
                            var oldDate = $input.datepicker("getDate");
                            if (oldDate.getFullYear() != newValue.getFullYear() || oldDate.getMonth() != newValue.getMonth() || oldDate.getDate() != newValue.getDate())
                                $input.datepicker("setDate", newValue);
                            $input.val(nts.uk.time.formatDate(newValue, dateFormat));
                        }
                        else {
                            var formatted = nts.uk.time.parseYearMonth(newValue);
                            var newDate = new Date(formatted.format() + "/01");
                            var oldDate = $input.datepicker("getDate");
                            if (oldDate.getFullYear() != newDate.getFullYear() || oldDate.getMonth() != newDate.getMonth())
                                $input.datepicker("setDate", newDate);
                            $input.val(formatted.format());
                        }
                        $input.prop("disabled", disabled);
                        if (data.button)
                            container.find('.datepicker-btn').prop("disabled", disabled);
                    };
                    return DatePickerBindingHandler;
                }());
                ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
