module nts.uk.ui.koExtentions {

    import validation = nts.uk.ui.validation;

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

            $input.change(function () {
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

    ko.bindingHandlers['ntsTextBox'] = new NtsTextBoxBindingHandler();
}