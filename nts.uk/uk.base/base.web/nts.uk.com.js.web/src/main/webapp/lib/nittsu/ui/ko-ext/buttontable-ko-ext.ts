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
            let mode: any = (data.mode !== undefined) ? ko.unwrap(data.mode) : "normal";
            let row: any = (data.row !== undefined) ? ko.unwrap(data.row) : 1;
            let column: any = (data.column !== undefined) ? ko.unwrap(data.column) : 1;
            let contextMenu = (data.contextMenu !== undefined) ? ko.unwrap(data.contextMenu) : [];
            let disableMenuOnDataNotSet = (data.disableMenuOnDataNotSet !== undefined) ? ko.unwrap(data.disableMenuOnDataNotSet) : [];
            let width: any = (data.width !== undefined) ? ko.unwrap(data.width) : 400;
            let clickAction = data.click;
            let selectedCells = ko.unwrap(data.selectedCells);
            
            $(element).ntsButtonTable("init", {
                mode: mode,
                click: clickAction,
                row: row,
                column :column,
                source : source,
                width : width,
                disableMenuOnDataNotSet : disableMenuOnDataNotSet,
                contextMenu: contextMenu
            });
        }

        /**
		 * Update
		 */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            
            let source: any = ko.unwrap(data.source);
            let row: any = (data.row !== undefined) ? ko.unwrap(data.row) : 1;
            let column: any = (data.column !== undefined) ? ko.unwrap(data.column) : 1;
            let selectedCells = (data.selectedCells !== undefined) ? ko.unwrap(data.selectedCells) : [];
            
            let container = $(element);
            let oldSource = container.ntsButtonTable("dataSource");
            if(!_.isEqual(oldSource, source)) {
                container.ntsButtonTable("dataSource", source);
            } 
            
            container.ntsButtonTable("row", row);
            container.ntsButtonTable("column", column);
        }
    }
    
    ko.bindingHandlers['ntsTableButton'] = new NtsTableButtonBindingHandler();
}