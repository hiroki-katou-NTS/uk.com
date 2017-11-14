/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
	 * Accordion binding handler
	 */
    class NtsTableButtonBindingHandler implements KnockoutBindingHandler {

        /**
		 * Init.
		 */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            
            let source: any = ko.unwrap(data.source);
            let row: any = (data.row !== undefined) ? ko.unwrap(data.row) : 0;
            let column: any = (data.column !== undefined) ? ko.unwrap(data.column) : 0;
            let contextMenu = (data.menu !== undefined) ? ko.unwrap(data.menu) : {};
            let clickOnSetted = data.clickOnSetted;
            let clickOnNotSet = data.clickOnNotSet;
            
            let container = $(element);
        }

        /**
		 * Update
		 */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            
        }
    }
    
    ko.bindingHandlers['ntsTableButton'] = new NtsTableButtonBindingHandler();
}