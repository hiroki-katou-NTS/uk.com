/// <reference path="../../reference.ts"/>

module nts.uk.ui.jqueryExtentions {
    import isNull = nts.uk.util.isNullOrUndefined;
    import isEmpty = nts.uk.util.isNullOrEmpty;
    module ntsButtonTable {

        $.fn.ntsButtonTable = function(method: string, option?: any, option2?: any, option3?: any): any{
            let $element = $(this);
            let builder: TableBuildingConstructor;
            switch(method){
                case "init": {
                    builder = new TableBuildingConstructor($element, option);
                    builder.startBuildTable();
                    break;
                } 
                case "dataSource": {
                    builder = $element.data("builder");
                    if(isNull(option) || !$.isArray(option)){
                        return builder.getDataSource();
                    }
                    
                    builder.setDataSource(option);
                    builder.drawTable();   
                    break;
                } 
                case "column": { 
                    builder = $element.data("builder");
                    if(isNull(option)){
                        return builder.column;
                    }
                    
                    if(option !== builder.column){
                        builder.setColumn(option);
                        builder.startBuildTable();    
                    }
                    break;
                } 
                case "row": {  
                    builder = $element.data("builder");
                    if(isNull(option)){
                        return builder.row;
                    }
                    
                    if(option !== builder.row){
                        builder.setRow(option);
                        builder.drawTable();    
                    }
                    break;   
                } 
                case "cellAt": {
                    builder = $element.data("builder");
                    let tbody: JQuery = this.container.find("tbody");
                    let rowAt = tbody.find("tr:nth-child("+ option + ")");
                    let cellAt = tbody.find("td:nth-child("+ option2 + ")");
                    return {
                        element: cellAt,
                        data: cellAt.data("cell-data"),
                        rowIdx: option,
                        columnIdx: option2,
                    };
                } 
                case "setCellValue": {
                    builder = $element.data("builder");
                    let tbody: JQuery = this.container.find("tbody");
                    let rowAt = tbody.find("tr:nth-child("+ option + ")");
                    let cellAt = tbody.find("td:nth-child("+ option2 + ")");
                    builder.setCellValue(cellAt.find("button"), option3);
                } 
                case "getSelectedCells": {
                    builder = $element.data("builder");
                    let selectedButton = builder.find(".ntsButtonCellSelected");
                    return _.map(selectedButton, function(c){
                        let button = $(c);
                        let cell = button.parent();
                        let rowIdx = parseInt(cell.parent().attr("data-idx"));
                        let columnIdx = parseInt(cell.parent().attr("column-idx"));
                        return {
                            element: cell,
                            data: cell.data("cell-data"),
                            rowIdx: rowIdx,
                            columnIdx: columnIdx,
                        };
                    });
                } 
                case "setSelectedCell": {
                    builder = $element.data("builder");
                    let tbody: JQuery = this.container.find("tbody");
                    let rowAt = tbody.find("tr:nth-child("+ option + ")");
                    let cellAt = tbody.find("td:nth-child("+ option2 + ")");
                    if(!cellAt.hasClass("ntsButtonCellSelected")){
                        cellAt.addClass("ntsButtonCellSelected");
                    }
                    break;
                }
                case "clearSelectedCellAt": {
                    builder = $element.data("builder");
                    let tbody: JQuery = this.container.find("tbody");
                    let rowAt = tbody.find("tr:nth-child("+ option + ")");
                    let cellAt = tbody.find("td:nth-child("+ option2 + ")");
                    if(cellAt.hasClass("ntsButtonCellSelected")){
                        cellAt.removeClass("ntsButtonCellSelected");
                    }
                    break;
                }
                case "clearAllSelectedCells": {
                    builder = $element.data("builder");
                    this.container.find(".ntsButtonCellSelected").removeClass("ntsButtonCellSelected");
                    break;
                }
                case "getDataCells": {
                    builder = $element.data("builder");
                    let dataButton = builder.find(".ntsButtonCellData");
                    return _.map(dataButton, function(c){
                        let button = $(c);
                        let cell = button.parent();
                        let rowIdx = parseInt(cell.parent().attr("data-idx"));
                        let columnIdx = parseInt(cell.parent().attr("column-idx"));
                        return {
                            element: cell,
                            data: cell.data("cell-data"),
                            rowIdx: rowIdx,
                            columnIdx: columnIdx,
                        };
                    });
                } 
                default: 
                    break; 
            } 
            
            $element.data("builder", builder);   
            return;        
        }

        
        class TableBuildingConstructor {
            container: JQuery;
            mode: string;
            contextMenu: any;
            disableMenuOnDataNotSet: Array<any>;
            clickOnAction: Function;
            row: number;
            column: number;
            id: string;
            source: any;
            width: number;
            
            constructor(container: JQuery, option: any){
                this.container = container;
                this.mode = option.mode; 
                this.clickOnAction = option.click;
                this.row = option.row;
                this.column = option.column;
                this.source = _.cloneDeep(option.source);
                this.id = nts.uk.util.randomId();
                this.width = option.width;
                
                this.disableMenuOnDataNotSet = option.disableMenuOnDataNotSet;
                this.cloneContextMenu(option.contextMenu);
            }
            
            setDataSource(source: Array<any>){
                this.source = _.cloneDeep(source);
            }
            
            getDataSource(): Array<any> {
                return _.clone(this.source);
            }
            
            setColumn(columnSize: number){
                this.column = columnSize;
            }
            
            setRow(rowSize: number){
                this.row = rowSize;
            }
            
            cloneContextMenu(contextMenu: Array<any>){
                let self = this;
                let menu = _.map(contextMenu, function(m){
                    let action = function (){
                        m.action().done(function(result){
                            let element: JQuery = self.container.data("context-opening");
                            element.trigger("contextmenufinished", result);    
                        });
                    }
                    return new nts.uk.ui.contextmenu.ContextMenuItem(m.id, m.text, action, m.style);
                });
                this.contextMenu = new nts.uk.ui.contextmenu.ContextMenu(".menu" + this.id, menu);    
            }
            
            startBuildTable(){
                let self = this;
                self.container.empty();
                let table = $("<table>", {"class": "ntsButtonTable ntsTable", id: this.id});
                let tbody = $("<tbody>", {"class": "data-area"});
                let colgroup = $("<colgroup>", {"class": "col-definition"});
                
                for(let i = 0; i < this.column; i++){
                   let col = $("<col>", {width: isNull(self.width) ? 100 : (self.width / self.column) });
                    col.appendTo(colgroup);
                }
                
                colgroup.appendTo(table);
                tbody.appendTo(table);
                table.appendTo(this.container);
                this.drawTable();
            }
            
            drawTable(){
                let tbody = this.container.find("tbody");
                tbody.empty();
                for(let i = 0; i < this.row; i++){
                   this.buildRow(tbody, i, this.id + "-row-" + i, this.source[i]);
                }
            }
            
            buildRow(container: JQuery, dataIdx: number, id: string, rowData: any) {
                let row = $("<tr>", {"class": "ntsRow ntsButtonTableRow", id: id, attr: {"data-idx": dataIdx, "data-id": id}});
                
                for(let i = 0; i < this.column; i++){
                    let idx = dataIdx*this.column + i;
                    this.buildCell(row, idx, id + "-cell-" + idx, isNull(rowData) || isNull(rowData[i]) ? {} : rowData[i], i);
                }
                
                row.appendTo(container);
            }
            
            buildCell(container: JQuery, dataIdx: number, id: string, data: any, columnIdx) {
                let self = this;
                let cell = $("<td>", {"class": "ntsCell ntsButtonTableCell", id: id, attr: {"data-idx": dataIdx, "data-id": id, "column-idx": columnIdx}});
                let contextClass = "menu" + this.id;
                let button = $("<button>", {"class": "ntsButtonCell ntsButtonTableButton " + contextClass, attr: {"data-idx": dataIdx, "data-id": id}});
                button.text(isEmpty(data.text) ? "+" : data.text);
                button.width(isNull(self.width) ? 90 : (self.width / self.column - 10));
                if (!isEmpty(data.text)) {
                    button.addClass("ntsButtonCellData");   
                    button.attr("title", data.tooltip);
                    button.data("empty-cell", false);
                } else {
                    button.data("empty-cell", true);
                }
                button.click(function(evt, ui){
                    let c = $(this);
                    if(self.mode === "master"){
                        if(_.isFunction(self.clickOnAction)){
                            self.clickOnAction().done(function(result){
                                self.setCellValue(c, result);   
                            });
                        }
                    } else {
                        if(!c.data("empty-cell")){
                            if(c.hasClass("ntsButtonCellSelected")){
                                c.removeClass("ntsButtonCellSelected");    
                            } else {
                                c.addClass("ntsButtonCellSelected");    
                            }
                        }
                    }
                });
                button.contextmenu(function() {
                    let c = $(this);
                    let enable: boolean = c.data("empty-cell");
                    if(self.mode === "master"){
                        self.contextMenu.setEnable(!enable);   
                        if(enable){
                            return false;     
                        }
                    } else {
                        if(!isEmpty(self.disableMenuOnDataNotSet)){
                            _.forEach (self.disableMenuOnDataNotSet, function(target){
                                self.contextMenu.setEnableItem(!enable, target);
                            })
                        }    
                    }
                    self.container.data("context-opening", button);
                    
                });
                button.bind("contextmenufinished", function(evt, result?: any) {
                    let c = $(this);
                    self.setCellValue(c, result);
                });
                
                button.appendTo(cell);
                cell.appendTo(container);
            }
            
            setCellValue(button: JQuery, data: any){
                if(!isNull(data)){
                    button.data("cell-data", _.cloneDeep(data));
                    button.text(data.text);
                    button.attr("title", data.tooltip);
                    button.addClass("ntsButtonCellData"); 
                    button.data("empty-cell", false);
                } else {
                    button.data("cell-data", null);
                    button.text("+");
                    button.removeAttr("title");
                    button.removeClass("ntsButtonCellData");
                    button.data("empty-cell", true);
                    data = {};
                }
                let cell = button.parent();
                let rowIdx = parseInt(cell.parent().attr("data-idx"));
                let columnIdx = parseInt(cell.parent().attr("column-idx"));
                this.source[rowIdx][columnIdx] = data;
            }
        }
        
        export class TableButtonEntity {
            rowId: number;
            columnId: number;
            viewText: string;
            tooltipText: string;
            
            constructor (rowId?: number, columnId?: number, viewText?: string, tooltipText?: string){
                this.rowId = rowId;
                this.columnId = columnId;
                this.viewText = viewText;
                this.tooltipText = tooltipText;
            }
            
            setRowId(rowId: number): void{
                this.rowId = rowId;
            }
            
            setColumnId(columnId: number): void{
                this.columnId = columnId;
            }
            
            setViewText(viewText: string): void{
                this.viewText = viewText;
            }
            
            setTooltipText(tooltipText: string): void{
                this.tooltipText = tooltipText;
            }
            
            getRowId(): number{
                return this.rowId;
            }
            
            getColumnId(): number{
                return this.columnId;
            }
            
            getViewText(rowId: number): string{
                return this.viewText;
            }
            
            getTooltipText(): string{
                return this.tooltipText;
            }
        }
    }
}