/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * FormLabel
     */
    class NtsFormLabelBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var primitiveValue = ko.unwrap(data.constraint);
            var isRequired = ko.unwrap(data.required) === true;
            var isInline = ko.unwrap(data.inline) === true;
            var isEnable = ko.unwrap(data.enable) !== false;
            var $formLabel = $(element).addClass('form-label');

            $('<label/>').html($formLabel.html()).appendTo($formLabel.empty());
            if (!isEnable) {
                $formLabel.addClass('disabled');
            } else {
                $formLabel.removeClass('disabled');
            }
            if (isRequired) {
                $formLabel.addClass('required');
            }

            if (primitiveValue !== undefined) {
                $formLabel.addClass(isInline ? 'inline' : 'broken');

                var constraintText = util.getConstraintMes(primitiveValue);
                $('<i/>').text(constraintText).appendTo($formLabel);
            }
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }
    }
    
    ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
}