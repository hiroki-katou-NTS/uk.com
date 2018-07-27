/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * Let binding handler
     */
    class NtsLetBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            // Make a modified binding context, with extra properties, and apply it to descendant elements
            ko.applyBindingsToDescendants(bindingContext.extend(valueAccessor), element);

            return { controlsDescendantBindings: true };
        }

        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => { }
    }

    ko.virtualElements.allowedBindings.let = true;
    ko.bindingHandlers['let'] = new NtsLetBindingHandler();
}
