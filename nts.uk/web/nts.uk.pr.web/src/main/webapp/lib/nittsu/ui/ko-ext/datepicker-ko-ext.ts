/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

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
            var data = valueAccessor();
            var value = data.value;
            var dateFormat: string = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
            dateFormat = text.getISO8601Format(dateFormat);
            var hasDayofWeek: boolean = (dateFormat.indexOf("ddd") !== -1);
            var dayofWeekFormat: string = dateFormat.replace(/[^d]/g, "");
            dateFormat = dateFormat.replace(/d/g,"").trim();
            var valueFormat: string = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : dateFormat;
            var disabled: boolean = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
            var button: boolean = (data.button !== undefined) ? ko.unwrap(data.button) : false;
            var startDate: any = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
            var endDate: any = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
            var autoHide: boolean = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;

            var container = $(element);
            if (!container.attr("id")) {
                var idString = nts.uk.util.randomId();
                container.attr("id", idString);
            }
            container.addClass("ntsControl nts-datepicker-wrapper").data("init", true);
            var inputClass: string = "";
            if (dateFormat.length < 10)
                inputClass = "yearmonth-picker";
            var $input: any = $("<input id='" + container.attr("id") + "-input' class='ntsDatepicker nts-input' />")
                                .addClass(inputClass);
            container.append($input);
            if (hasDayofWeek) {
                var lengthClass: string = (dayofWeekFormat.length > 3) ? "long-day" : "short-day";
                var $label: any = $("<label id='" + container.attr("id") + "-label' for='" + container.attr("id") + "-input' class='dayofweek-label' />");
                $input.addClass(lengthClass);
                container.append($label);
            }
            
            // Init Datepicker
            $input.datepicker({
                date: value(),
                language: 'ja-JP',
                format: dateFormat,
                startDate: startDate,
                endDate: endDate,
                autoHide: autoHide,
            });

            $input.on("change", (e) => {
                var newText = $input.val();
                var isValid: boolean = moment(newText, valueFormat).isValid();
                $input.ntsError('clear');
                if (isValid) {
                    var dateFormatValue = moment(newText, valueFormat).format(dateFormat);
                    var valueFormatValue = moment(newText, valueFormat).format(valueFormat);
                    $input.val(dateFormatValue);
                    if (hasDayofWeek)
                        $label.text("(" + moment(newText, valueFormat).format(dayofWeekFormat) + ")");
                    value(valueFormatValue);
                }
                else {
                    $input.ntsError('set', "Invalid format");
                    value(newText);
                }
            });

            $input.on('validate', (function(e: Event) {
                var newText = $input.val();
                var isValid: boolean = moment(newText, valueFormat).isValid();
                $input.ntsError('clear');
                if (!isValid) {
                    $input.ntsError('set', "Invalid format");
                }
            }));
            
            var length = 10, atomWidth = 9.5;
            //$input.width(Math.floor(atomWidth * length));
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            var value = data.value;
            var dateFormat: string = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
            dateFormat = text.getISO8601Format(dateFormat);
            var hasDayofWeek: boolean = (dateFormat.indexOf("ddd") !== -1);
            var dayofWeekFormat: string = dateFormat.replace(/[^d]/g, "");
            dateFormat = dateFormat.replace(/d/g,"").trim();
            var valueFormat: string = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : dateFormat;
            var disabled: boolean = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
            var button: boolean = (data.button !== undefined) ? ko.unwrap(data.button) : false;
            var startDate: any = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
            var endDate: any = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
            var autoHide: boolean = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;

            var container = $(element);
            var init = container.data("init");
            container.data("init", false);
            var $input: any = container.find(".nts-input");
            var $label: any = container.find(".dayofweek-label");
            
            // Value Binding
            var isValid: boolean = moment(value(), valueFormat).isValid();
            if (isValid) {
                var dateFormatValue = moment(value(), valueFormat).format(dateFormat);
                if (init === true || (moment($input.datepicker('getDate')).format(dateFormat) !== dateFormatValue)) {
                    $input.datepicker('setDate', dateFormatValue);
                    if (hasDayofWeek)
                        $label.text("(" + moment(value(), valueFormat).format(dayofWeekFormat) + ")");
                }
            }
            else {
                $input.ntsError('set', "Invalid format");
                $input.val(value());
            }
            
            // Properties Binding
            $input.datepicker('setStartDate', startDate);
            $input.datepicker('setEndDate', endDate);
            $input.prop("disabled", disabled);
            if (data.button)
                container.find('.datepicker-btn').prop("disabled", disabled);
        }
    }

    ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
}