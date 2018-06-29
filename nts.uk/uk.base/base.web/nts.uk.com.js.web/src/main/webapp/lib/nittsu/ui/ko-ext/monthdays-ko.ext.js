var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsMonthDaysBindingHandler = (function () {
                    function NtsMonthDaysBindingHandler() {
                    }
                    NtsMonthDaysBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var $container = $(element);
                        var self = this;
                        var value = ko.unwrap(data.value);
                        var dataName = ko.unwrap(data.name);
                        var enable = data.enable === undefined ? true : ko.unwrap(data.enable);
                        var tabIndex = nts.uk.util.isNullOrEmpty($container.attr("tabindex")) ? "0" : $container.attr("tabindex");
                        $container.removeAttr("tabindex");
                        $container.data("tabindex", tabIndex);
                        $container.addClass("ntsControl ntsMonthDays_Container");
                        $container.append("<div class='ntsMonthDays'/>");
                        var $control = $container.find(".ntsMonthDays");
                        $control.append("<div class='ntsMonthPicker ntsComboBox ntsMonthDays_Component' /><div class='ntsMonthLabel ntsLabel ntsMonthDays_Component'/>" +
                            "<div class='ntsDayPicker ntsComboBox ntsMonthDays_Component' /><div class='ntsDayLabel ntsLabel ntsMonthDays_Component'/>");
                        var $monthPicker = $control.find(".ntsMonthPicker");
                        var $dayPicker = $control.find(".ntsDayPicker");
                        var $monthLabel = $control.find(".ntsMonthLabel");
                        var $dayLabel = $control.find(".ntsDayLabel");
                        $monthLabel.append("<label>月</label>");
                        $dayLabel.append("<label>日</label>");
                        $monthPicker.igCombo({
                            dataSource: NtsMonthDaysBindingHandler.getMonths(),
                            textKey: "text",
                            valueKey: "value",
                            width: "60px",
                            height: "30px",
                            mode: "dropdown",
                            selectionChanged: function (evt, ui) {
                                var currentMonth = ui.items[0].data.value;
                                var currentDay = $dayPicker.igCombo("selectedItems");
                                var days = NtsMonthDaysBindingHandler.getDaysInMonth(currentMonth);
                                var value = currentDay[0].data.value > days.length ? days.length : currentDay[0].data.value;
                                $dayPicker.igCombo("option", "dataSource", days);
                                data.value(currentMonth * 100 + value);
                            }
                        });
                        $dayPicker.igCombo({
                            dataSource: NtsMonthDaysBindingHandler.getDaysInMonth(1),
                            textKey: "text",
                            valueKey: "value",
                            width: "60px",
                            height: "30px",
                            mode: "dropdown",
                            selectionChanged: function (evt, ui) {
                                var currentDay = ui.items[0].data.value;
                                var currentMonth = $monthPicker.igCombo("selectedItems")[0].data.value;
                                data.value(currentMonth * 100 + currentDay);
                            }
                        });
                    };
                    NtsMonthDaysBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var $container = $(element);
                        var value = ko.unwrap(data.value);
                        var enable = data.enable === undefined ? true : ko.unwrap(data.enable);
                        var $monthPicker = $container.find(".ntsMonthPicker");
                        var $dayPicker = $container.find(".ntsDayPicker");
                        if (enable !== false) {
                            $monthPicker.igCombo('option', 'disabled', false);
                            $dayPicker.igCombo('option', 'disabled', false);
                            $container.find("input").attr("tabindex", $container.data("tabindex"));
                        }
                        else {
                            $monthPicker.igCombo('option', 'disabled', true);
                            $dayPicker.igCombo('option', 'disabled', true);
                            $container.find("input").attr("tabindex", "-1");
                        }
                        if (!nts.uk.util.isNullOrUndefined(value) && nts.uk.ntsNumber.isNumber(value)) {
                            var month = nts.uk.ntsNumber.trunc(parseInt(value) / 100);
                            var day = parseInt(value) % 100;
                            $monthPicker.igCombo("value", month);
                            $dayPicker.igCombo("value", day);
                        }
                        var currentDay = $dayPicker.igCombo("selectedItems")[0].data.value;
                        var currentMonth = $monthPicker.igCombo("selectedItems")[0].data.value;
                        data.value(currentMonth * 100 + currentDay);
                    };
                    NtsMonthDaysBindingHandler.getMonths = function () {
                        var monthSource = [];
                        while (monthSource.length < 12) {
                            monthSource.push({ text: monthSource.length + 1, value: monthSource.length + 1 });
                        }
                        return monthSource;
                    };
                    NtsMonthDaysBindingHandler.getDaysInMonth = function (month) {
                        var daysInMonth = moment(month, "MM").daysInMonth();
                        if (daysInMonth !== NaN) {
                            var days = [];
                            while (days.length < daysInMonth) {
                                days.push({ text: days.length + 1, value: days.length + 1 });
                            }
                            return days;
                        }
                        return [];
                    };
                    return NtsMonthDaysBindingHandler;
                }());
                ko.bindingHandlers['ntsMonthDays'] = new NtsMonthDaysBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=monthdays-ko.ext.js.map