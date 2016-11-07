module nts.uk.ui.koExtentions {

    import validation = nts.uk.ui.validation;

    /**
     * TextBox
     */
    class NtsTextBoxBindingHandler implements KnockoutBindingHandler {

        constraint: validation.CharType;

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var setValue: (newText: string) => {} = data.value;
            this.constraint = validation.getCharType(data.constraint);

            var $input = $(element);

            $input.change(function() {
                var newText = $input.val();
                setValue(newText);
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var getValue: () => string = data.value;

            var $input = $(element);
            var newText = getValue();

            $input.val(newText);
        }
    }

    /**
     * Switch button binding handler
     */
    class NtsSwitchButtonBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var selectedCssClass = 'selected';
            // Get options.
            var options: Array<any> = ko.unwrap(data.options);

            // Get options value.
            var optionValue = ko.unwrap(data.optionsValue);
            var optionText = ko.unwrap(data.optionsText);
            var selectedValue = ko.unwrap(data.value);
            // Container.
            var container = $(element);

            // Remove deleted button.
            $('button', container).each(function(index, btn) {
                var $btn = $(btn);
                var btnValue = $(btn).data('swbtn');
                // Check if btn is contained in options.
                var foundFlag = _.findIndex(options, function(opt) {
                    return opt[optionValue] == btnValue;
                }) != -1;

                if (!foundFlag) {
                    $btn.remove();
                    return;
                }
            })

            // Start binding new state.
            _.forEach(options, function(opt) {
                var value = opt[optionValue];
                var text = opt[optionText];

                // Find button.
                var targetBtn: JQuery;
                $('button', container).each(function(index, btn) {
                    var btnValue = $(btn).data('swbtn');
                    if (btnValue == value) {
                        targetBtn = $(btn);
                    }

                    if (btnValue == selectedValue) {
                        $(btn).addClass(selectedCssClass);
                    } else {
                        $(btn).removeClass(selectedCssClass);
                    }
                })

                if (targetBtn) {
                    // Do nothing.
                } else {
                    // Recreate
                    var btn = $('<button>').text(text)
                        .addClass('nts-switch-button')
                        .data('swbtn', value)
                        .on('click', function() {
                            var selectedValue = $(this).data('swbtn');
                            data.value(selectedValue);
                            $('button', container).removeClass(selectedCssClass);
                            $(this).addClass(selectedCssClass);
                        })
                    if (selectedValue == value) {
                        btn.addClass(selectedCssClass);
                    }
                    container.append(btn);
                }
            })
        }
    }

    class NtsCheckboxBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            $(element).addClass("ntsCheckBox");
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();

            // Container.
            var container = $(element);

            // Get options.
            var checked: boolean = ko.unwrap(data.checked);
            var enable: boolean = ko.unwrap(data.enable);
            var textId: string = data.text;
            var checkBoxText: string;
            if (textId) {
                checkBoxText = /*nts.ui.name*/(textId);
            } else {
                checkBoxText = container.text();
                container.text('');
            }

            var checkBox: JQuery;
            if ($('input[type="checkbox"]', container).length == 0) {
                // Init new.
                checkBox = $('<input type="checkbox">').appendTo(container);
                var checkBoxLabel = $("<label><span></span></label>").appendTo(container).append(checkBoxText);
                $(container).on('click', "label", function() {
                    // Do nothing if disable.
                    if (container.hasClass('disabled')) {
                        return;
                    }

                    // Change value.
                    checkBox.prop("checked", !checkBox.prop("checked"));
                    data.checked(checkBox.prop("checked"));
                });
            } else {
                checkBox = $('input[type="checkbox"]', container);
            }

            // Set state.
            checkBox.prop("checked", checked);

            // Disable.
            if (enable == false) {
                container.addClass('disabled');
            } else {
                container.removeClass('disabled');
            }
        }
    }
    ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
    ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
    ko.bindingHandlers['ntsTextBox'] = new NtsTextBoxBindingHandler();
}