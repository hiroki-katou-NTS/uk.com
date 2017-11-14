/// <reference path="../../reference.ts"/>

module nts.uk.ui.jqueryExtentions {
    import isNull = nts.uk.util.isNullOrUndefined;
    import isEmpty = nts.uk.util.isNullOrEmpty;
    module ntsButtonTable {

        $.fn.ntsButtonTable = function(method: string, option?: any) {
            let $element = $(this);
            switch(method){
                case "init": {
                    let builder: TableBuildingConstructor = new TableBuildingConstructor($element, option);
                    builder.startBuildTable();
                    $element.data("builder", builder);
                    break;
                } 
                case "selectByFileId": {   
                } 
                case "showByUrl": { 
                } 
                case "clear": {     
                } 
                case "getImgStatus": {  
                } 
                default: 
                    return; 
            }            
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
                this.source = option.source;
                this.id = nts.uk.util.randomId();
                this.width = option.width;
                
                this.disableMenuOnDataNotSet = option.disableMenuOnDataNotSet;
                this.cloneContextMenu(option.contextMenu);
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
                this.drawTable(this.source);
            }
            
            drawTable(source: any){
                let tbody = this.container.find("tbody");
                for(let i = 0; i < this.row; i++){
                   this.buildRow(tbody, i, this.id + "-row-" + i, source[i]);
                }
            }
            
            buildRow(container: JQuery, dataIdx: number, id: string, rowData: any) {
                let row = $("<tr>", {"class": "ntsRow ntsButtonTableRow", id: id, attr: {"data-idx": dataIdx, "data-id": id}});
                
                for(let i = 0; i < this.column; i++){
                    let idx = dataIdx*this.column + i;
                    this.buildCell(row, idx, id + "-cell-" + idx, isNull(rowData) || isNull(rowData[i]) ? {} : rowData[i]);
                }
                
                row.appendTo(container);
            }
            
            buildCell(container: JQuery, dataIdx: number, id: string, data: any) {
                let self = this;
                let cell = $("<td>", {"class": "ntsCell ntsButtonTableCell", id: id, attr: {"data-idx": dataIdx, "data-id": id}});
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
                                if(!isNull(result)){
                                    c.data("cell-data", _.cloneDeep(result));
                                    c.text(result.text);
                                    c.attr("title", result.tooltip);
                                    c.addClass("ntsButtonCellData"); 
                                    c.data("empty-cell", false);
                                } else {
                                    c.data("cell-data", null);
                                    c.text("+");
                                    c.removeAttr("title");
                                    c.removeClass("ntsButtonCellData");
                                    c.data("empty-cell", true);
                                }   
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
                    alert(c.attr("data-idx"));
                    if(!isNull(result)){
                        c.data("cell-data", _.cloneDeep(result));
                        c.text(result.text);
                        c.attr("title", result.tooltip);
                        c.addClass("ntsButtonCellData"); 
                        c.data("empty-cell", false);
                    } else {
                        c.data("cell-data", null);
                        c.text("+");
                        c.removeAttr("title");
                        c.removeClass("ntsButtonCellData");
                        c.data("empty-cell", true);
                    }
                });
                
                button.appendTo(cell);
                cell.appendTo(container);
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