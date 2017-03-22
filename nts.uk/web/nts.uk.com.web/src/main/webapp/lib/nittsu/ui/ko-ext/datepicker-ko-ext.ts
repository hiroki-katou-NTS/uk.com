/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * Datepicker binding handler
     */
    function randomString(length, chars) {
        var result = '';
        for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
        return result;
    }
    class DatePickerBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            // Container.
            var container = $(element);
            if (!container.attr("id")) {
                var idString = randomString(10, 'abcdefghijklmnopqrstuvwxy0123456789zABCDEFGHIJKLMNOPQRSTUVWXYZ');
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
            container.append("<input id='" + idatr + "_input' class='ntsDatepicker nts-input' />");
            var $input = container.find('#' + idatr + "_input");
            var button = null;
            if (data.button) button = idatr + "_button";
            $input.prop("readonly", true);

            var value = ko.unwrap(data.value);
            var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
            var containerFormat = 'yyyy/mm/dd';
            var length = 10, atomWidth = 9.5;
            if (dateFormat === "yyyy/MM/dd DDD") {
                length = 16;
            } else if (dateFormat === "yyyy/MM/dd D") {
                length = 14;
            } else if (dateFormat === "yyyy/MM") {
                length = 7;
                containerFormat = 'yyyy/mm';
            }
            if (containerFormat != 'yyyy/mm')
                //datepicker case
                $input.attr('value', nts.uk.time.formatDate(value, dateFormat));
            else //yearmonth picker case 
                $input.attr('value', value);
            if (button) {
                container.append("<input type='button' id='" + button + "' class='datepicker-btn' />");
                (<any>$input).datepicker({
                    format: containerFormat, // cast to avoid error
                    language: 'ja-JP',
                    trigger: "#" + button,
                    autoHide: autoHide,
                    startDate: startDate,
                    endDate: endDate
                });
            }
            else (<any>$input).datepicker({
                format: containerFormat, // cast to avoid error
                language: 'ja-JP',
                autoHide: autoHide,
                startDate: startDate,
                endDate: endDate
            });
            container.data("format", containerFormat);
            if (containerFormat !== 'yyyy/mm')
                $input.on('change', (event: any) => {
                    data.value(new Date($input.val().substring(0, 10)));
                });
            else
                $input.on('change', (event: any) => {
                    let result = nts.uk.time.parseYearMonth($input.val());
                    data.value(result.toValue());
                });
            $input.width(Math.floor(atomWidth * length));
            if (data.disabled !== undefined && ko.unwrap(data.disabled) == true) {
                $input.prop("disabled", true);
                if (button) {
                    container.find('.datepicker-btn').prop("disabled", true);
                }
            }
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var container = $(element);
            var idatr = container.attr("id");
            var newValue = ko.unwrap(data.value);
            //console.log(newValue);                       
            var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
            var $input = container.find('#' + idatr + "_input");
            var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
            var formatOptions = container.data("format");
            var oldDate = $input.datepicker("getDate");
            if (formatOptions != 'yyyy/mm') {
                var oldDate = $input.datepicker("getDate");
                if (oldDate.getFullYear() != newValue.getFullYear() || oldDate.getMonth() != newValue.getMonth() || oldDate.getDate() != newValue.getDate())
                    $input.datepicker("setDate", newValue);
                $input.val(nts.uk.time.formatDate(newValue, dateFormat));
            } else {
                let formatted = nts.uk.time.parseYearMonth(newValue);
                var newDate = new Date(formatted.format() + "/01");
                var oldDate = $input.datepicker("getDate");
                if (oldDate.getFullYear() != newDate.getFullYear() || oldDate.getMonth() != newDate.getMonth())
                    $input.datepicker("setDate", newDate);
                $input.val(formatted.format());
            }
            if (data.disabled !== undefined && ko.unwrap(data.disabled) == true) {
                $input.prop("disabled", true);
                if (data.button) {
                    container.find('.datepicker-btn').prop("disabled", true);
                }
            }
        }
    }
    
    ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
}