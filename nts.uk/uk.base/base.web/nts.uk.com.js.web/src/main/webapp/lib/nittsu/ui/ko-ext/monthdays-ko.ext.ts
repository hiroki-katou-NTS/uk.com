/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * Dialog binding handler
     */
    class NtsMonthDaysBindingHandler implements KnockoutBindingHandler {

        /**
         * Init. 
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let $container = $(element),
                getComboBinding = (originalBinding, value, source) => {
                    return _.extend(_.clone(originalBinding), {
                        options: ko.observableArray(source),
                        optionsValue: 'value',
                        value: value,
                        optionsText: 'text'
                    });
                },
                getMonths = () => _.range(1, 13).map(m => ({ text: m, value: m })),
                getDaysInMonth = (month: number) => _.range(1, moment(month, "MM").daysInMonth() + 1).map(m => ({ text: m, value: m }));

            let value = ko.unwrap(data.value);
            let dataName = ko.unwrap(data.name);
            let enable = data.enable === undefined ? true : ko.unwrap(data.enable);

            $container.data("tabindex", $container.attr("tabindex") || 0).removeAttr("tabindex");

            $container.addClass("ntsControl ntsMonthDays_Container");

            let $control = $('<div>', { class: 'ntsMonthDays' }),
                $monthPicker = $("<div>", { "class": "ntsMonthPicker ntsComboBox ntsMonthDays_Component", id: nts.uk.util.randomId() }),
                $dayPicker = $("<div>", { "class": "ntsDayPicker ntsComboBox ntsMonthDays_Component", id: nts.uk.util.randomId() }),
                $monthLabel = $("<div>", {
                    "class": "ntsMonthLabel ntsLabel ntsMonthDays_Component",
                    id: nts.uk.util.randomId(),
                    html: '<label>月</label>'
                }),
                $dayLabel = $("<div>", {
                    "class": "ntsDayLabel ntsLabel ntsMonthDays_Component",
                    id: nts.uk.util.randomId(),
                    html: '<label>日</label>'
                });


            $control.append($monthPicker).append($monthLabel).append($dayPicker).append($dayLabel).appendTo($container);
            // trong custom control nay hinh nhu ngoai init va update, no hok nhan method khac dua a ok
            let monthValueAccessor = getComboBinding(data, ko.observable(1), getMonths()),
                dayValueAccessor = getComboBinding(data, ko.observable(1), getDaysInMonth(1));

            // month change
            monthValueAccessor.value.subscribe(v => {
                // change options of combobox days
                let days = getDaysInMonth(v),
                    curentDay = ko.toJS(dayValueAccessor.value);

                dayValueAccessor.value(_.min([curentDay, days.length]));
                dayValueAccessor.options(days);
            });

            // bind data out
            ko.computed({
                read: () => {
                    let currentMonth = ko.toJS(monthValueAccessor.value),
                        curentDay = ko.toJS(dayValueAccessor.value);

                    data.value(currentMonth * 100 + curentDay);
                }
            })

            ko.computed({
                read: () => {
                    let value = Number(ko.toJS(data.value));

                    if (_.isNumber(value)) {
                        let month = Math.floor(value / 100),
                            day = value % 100;

                        monthValueAccessor.value(month);
                        dayValueAccessor.value(day);
                    }
                }
            })

            // day accessor cuar 2 cbox vao data
            $container.data("cusVal", { month: monthValueAccessor, day: dayValueAccessor });

            ko.bindingHandlers["ntsComboBox"].init($monthPicker[0], () => monthValueAccessor, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers["ntsComboBox"].init($dayPicker[0], () => dayValueAccessor, allBindingsAccessor, viewModel, bindingContext);
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor(),
                $container = $(element),
                value = ko.unwrap(data.value),
                enable = data.enable === undefined ? true : ko.unwrap(data.enable),
                $monthPicker = $container.find(".ntsMonthPicker"),
                $dayPicker = $container.find(".ntsDayPicker"),
                bindedVal = $container.data("cusVal");

            //            if(enable !== false){
            //                $monthPicker.igCombo('option', 'disabled', false);
            //                $dayPicker.igCombo('option', 'disabled', false);    
            //                $container.find("input").attr("tabindex", $container.data("tabindex"));            
            //            } else {
            //                $monthPicker.igCombo('option', 'disabled', true);
            //                $dayPicker.igCombo('option', 'disabled', true); 

            $container.find("input").attr("tabindex", "-1");

            ko.bindingHandlers["ntsComboBox"].update($monthPicker[0], () => bindedVal.month, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers["ntsComboBox"].update($dayPicker[0], () => bindedVal.day, allBindingsAccessor, viewModel, bindingContext);
        }
    }

    ko.bindingHandlers['ntsMonthDays'] = new NtsMonthDaysBindingHandler();
}
