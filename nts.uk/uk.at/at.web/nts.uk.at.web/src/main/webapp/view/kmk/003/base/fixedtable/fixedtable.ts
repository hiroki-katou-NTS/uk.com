module nts.uk.at.view.kmk003.base.fixedtable {

    /************************************************ PARAMETERS INITIAL FIXTABLE **********************************
    ***************************************************************************************************************/

    /**
     * FixTableOption
     */
    export interface FixTableOption {

        /**
         * total row maximum table
         */
        maxRow: number;
        
        /**
         * total row minimum table
         */
        minRow: number;
        
        /**
         * total row display
         */
        maxRowDisplay: number;
        
        /**
         * data source table
         */
        dataSource: KnockoutObservableArray<any>;

        /**
         * Show/hide addItem and removeItem button
         */
        isShowButton: boolean;
        
        /**
         * set tabIndex
         */
        tabindex?: number;

        /**
         * list columns table
         */
        columns: Array<FixColumn>;

        /**
         * Show/hide multiple select checkbox
         */
        isMultipleSelect: boolean;
    }

    /**
     * IControl
     */
    interface IControl {
        
        /**
         * control type kiban team
         */
        controlType: string;
        
        /**
         * key value of control
         */
        keyValue: string;
        
        /**
         * needed when control has option.Ex: comboBox, ...
         */
        keyOptionValue?: string;
    }
    
    /**
     * FixColumn
     */
    interface FixColumn {

        /**
         * Header text of column
         */
        headerText: string;

        /**
         * Primary key column
         */
        key: string;
        
        /**
         * Default value column
         */
        defaultValue: any;

        /**
         * width column
         */
        width: number;

        /**
         * Control type (control of Kiban team)
         */
        controlType: number;

        /**
         * dataSource control if has. Ex: option comboBox, ...
         */
        dataSource: Array<any>;

        /**
         * temple html
         */
        template: string;
        
        /**
         * class JQuery
         */
        cssClassName?: string;
    }

    /************************************************ SCREEN MODEL ************************************************
    ***************************************************************************************************************/
    
    /**
     * TableStyle
     */
    interface TableStyle {
        height: number;
        width: number;
    }
    
    /**
     * FixTableScreenModel
     */
    export class FixTableScreenModel {

        itemList: KnockoutObservableArray<any>;
        
        // needed when model multiple select
        isSelectAll: KnockoutObservable<boolean>;
        isVisibleSelectAll: KnockoutObservable<boolean>;
        
        isEnaleAddButton: KnockoutObservable<boolean>;
        isEnaleRemoveButton: KnockoutObservable<boolean>;
        
        // needed when has comboBox, ...
        lstDataSource: any;
        
        isShowButton: boolean;
        columns: Array<FixColumn>;
        maxRow: number;
        minRow: number;
        maxRowDisplay: number;
        tableStyle: TableStyle;
        
        $element: any;
        $tableSelector: any;
        mapControl: Array<IControl>;
        tabindex: number;
        
        tableId: string;
        
        constructor(data: FixTableOption) {
            let self = this;
            
            // set data parameter
            self.isShowButton = data.isShowButton;
            self.columns = data.columns;
            self.maxRow = data.maxRow;
            self.minRow = data.minRow;
            self.maxRowDisplay = data.maxRowDisplay;
            if (!self.maxRowDisplay) {
                self.maxRowDisplay = 10;
            }
            self.tabindex = data.tabindex;
            if (!self.tabindex) {
                self.tabindex = -1;
            }
            self.itemList = data.dataSource;
            
            self.sortTimeASC();
            
            // add properties isChecked when multiple select
            self.addCheckBoxItemAtr();
            
            self.isSelectAll = ko.observable(false);
            
            self.isVisibleSelectAll = ko.computed(() => {
                return self.minRow < 1;
            });
            
            self.isEnaleAddButton = ko.observable(false);
            self.isEnaleRemoveButton = ko.observable(false);
            
            self.tableStyle = {
                height: 0,
                width: 0
            };
            self.lstDataSource = {};
            self.mapControl = self.createMapControl();
            
            self.tableId = "fixed-table-custom-" + nts.uk.util.randomId();
            
            // subscribe isSelectAll
            self.isSelectAll.subscribe(newValue => {
                self.computedChange(newValue);
            });
            // subscribe itemList
            self.itemList.subscribe((newList) => {
                if (!newList) {
                     self.isSelectAll(false);
                    return;
                }
                
                // update status button
                self.isEnaleAddButton(newList.length < self.maxRow);
                self.isEnaleRemoveButton(newList.length > self.minRow);
                
                if (newList.length <= 0) {
                    self.isSelectAll(false);
                    return;
                }
                // add properties isChecked when multiple select
                self.addCheckBoxItemAtr();
                self.subscribeChangeCheckbox();
            });
        }

        /**
         * Initial screen
         */
        public initialScreen(): JQueryPromise<void> {
            let self = this;
            let dfd: any = $.Deferred<void>();
            
            // update table id
            $('#fixed-table-custom').attr('id', self.tableId);
            
            self.$tableSelector = $('#' + self.tableId);
            
            // update status button
            self.isEnaleAddButton(self.itemList().length < self.maxRow);
            self.isEnaleRemoveButton(self.itemList().length > self.minRow);
            
            // calculate height table
            self.calStyleTable();
            
            // render html table
            return self.renderTable();
        }
        
        /**
         * Add a row table
         */
        public addRowItem() {
            let self = this;
            let row: any = {};
            _.forEach(self.columns, (column: FixColumn) => {
                row[column.key] = ko.observable(ko.unwrap(column.defaultValue));
            });
            self.itemList.push(row);
        }
        
        /**
         * Remove row table
         */
        public removeItem() {
            let self = this;
            
            // find item is checked
            let lstItemChecked: Array<any> = self.itemList().filter(item => item.isChecked() == true);
            if (lstItemChecked.length <= 0) {
                return;
            }
            self.itemList(self.itemList().filter(item => item.isChecked() == false));
        }
        
        /**
         * Sort Time editor ASC
         */
        private sortTimeASC() {
            let self = this;
            let firstColumn: FixColumn = self.columns[0];
            
            self.itemList().sort(function(a, b) {
                
                // get & unwrap value
                let rawValue1: any = ko.utils.unwrapObservable(a[firstColumn.key]);
                if (!rawValue1) {
                    return;
                }
                if (typeof rawValue1 == 'object') {
                    rawValue1 = rawValue1.startTime ? rawValue1.startTime : null;
                }
                
                let rawValue2: any = ko.utils.unwrapObservable(b[firstColumn.key]);
                if (!rawValue2) {
                    return;
                }
                if (typeof rawValue2 == 'object') {
                    rawValue2 = rawValue2.startTime ? rawValue2.startTime : null;
                }
                
                // convert time to minutes
                let value1: any = nts.uk.time.parseTime(rawValue1).toValue();
                let value2: any = nts.uk.time.parseTime(rawValue2).toValue();
                
                if (value1 < value2) {
                    return -1;
                } else if (value1 > value2) {
                    return 1;
                }
                return 0;
            });
        }
        
        /**
         * calStyleTable
         */
        public calStyleTable() {
            let self = this;
            let heigthCell = 35;
            self.tableStyle.height = heigthCell * self.maxRowDisplay + 1;
            
            self.tableStyle.width = self.columns.map(column => column.width).reduce((a, b) => a + b, 0);
        }
        
        /**
         * subscribeChangeCheckbox
         */
        private subscribeChangeCheckbox() {
            let self = this;
            
            // case empty list
            if (!self.itemList() || self.itemList().length <= 0) {
                self.isSelectAll(false);
                return;
            }
            let selectAll: any = self.itemList().every(item => item.isChecked() == true);
            if (selectAll == false) {
                selectAll = null;
            }
            self.isSelectAll(selectAll);
        }
        
        /**
         * computedChange
         */
        private computedChange(newValue: boolean) {
            let self = this;
            
            // select all true
            if (newValue == null) {
                return;
            }
            _.forEach(self.itemList(), row => {
                row.isChecked(newValue);
            });
        }
        
        /**
         * renderTable
         */
        private renderTable(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            
            // add tbody table
            self.$tableSelector.append("<tbody data-bind='foreach: itemList'>");
            
            // add html row
            self.renderRowHtml();
            
            dfd.resolve();
            return dfd.promise();
        }
        
        /**
         * renderRowHtml
         */
        private renderRowHtml() {
            let self = this;
            let rowHtml: string = "";
            
            // mode multiple
            rowHtml += "<td style='text-align: center;'><div data-bind=\"attr: {tabindex: $parent.tabindex}, " 
                    + "visible: $index() >= $parent.minRow, ntsCheckBox: { checked: isChecked, "
                    + "enable: true, text:''}\"></div></td>";
            
            // add html column base setting
            _.forEach(self.columns, (item: FixColumn) => {
                rowHtml += self.generateColumnHtml(item);
            });
            
            // add html row in table
            self.$tableSelector.append("<tr>" + rowHtml + "</tr>");
        }
        
        /**
         * addCheckBoxItemAtr
         */
        private addCheckBoxItemAtr() {
            let self = this;
            // if mode multiple, set default check box false
            _.forEach(self.itemList(), row => {
                
                // if not has property isChecked
                if (!row.hasOwnProperty('isChecked')) {
                    row.isChecked = ko.observable(false);
                    
                    // subscribe
                    row.isChecked.subscribe(() => {
                        self.subscribeChangeCheckbox();
                    });
                }
            });
        }
        
        /**
         * Define key word of control
         * 
         */
        private createMapControl(): any {
            let self = this;
            
            // define infor control
            let mapControl: Array<IControl> = [
                {controlType: 'ntsCheckBox', keyValue: 'checked:'},
                {controlType: 'ntsTimeEditor', keyValue: 'value:'},
                {controlType: 'ntsComboBox', keyValue: 'value:', keyOptionValue: 'options:'},
                {controlType: 'ntsTimeRangeEditor', keyValue: 'value:'}
            ];
            return mapControl;
        }
        
        /**
         * Generate control html
         */
        private generateColumnHtml(columnSetting: FixColumn): string {
            let self = this;
            
            // get template
            let template: string = columnSetting.template;
            
            // get infor key word control
            let infoControl: IControl = self.getInfoControl(template);
            
            let keyValue: string = infoControl.keyValue;
            
            // not found control
            if (!keyValue) {
                return;
            }
            let oldProperties: string = template.substring(template.indexOf("{") + 1, template.lastIndexOf("}"));
            
            // remove spaces
            let newProperties: string = oldProperties.replace(/\s/g, '');
            
            // insert key value of control
            newProperties = self.updateElement(newProperties, keyValue, columnSetting.key);
            
            // insert option value of control if has
            let keyOptionValue: string = infoControl.keyOptionValue;
            if (keyOptionValue) {
                // add data source for control
                self.lstDataSource[columnSetting.key] = columnSetting.dataSource;
                
                // update option cotrol html
                newProperties = self.updateElement(newProperties, keyOptionValue,
                    "$parent.lstDataSource." + columnSetting.key);
            }

            // update tabindex
            let idxSpace: number = template.indexOf(' ');
            template = template.substring(0, idxSpace) + " tabindex=\"" + self.tabindex + "\" "
                + template.substring(idxSpace + 1, template.length);
            
            // ==== add tabindex for ntsTimeRangeEditor ====
            if (template.indexOf('ntsTimeRangeEditor') != -1) {
                newProperties = newProperties + ",tabindex:" + self.tabindex;
            }
            
            template = template.replace(oldProperties, newProperties.replace(/"/g, "'"));
            
            // update width control
            template = self.updateWidthControl(template, newProperties, columnSetting.width);
            
            return "<td style='text-align: center;' class='" + columnSetting.cssClassName + "'>" + template + "</td>";
        }
        
        /**
         * Add/update width control
         */
        private updateWidthControl(template: string, properties: string, width: number): string {
            let self = this;
            
            // ======================================== ntsComboBox ===================================
            if (template.indexOf('ntsComboBox') > -1) {
                let idx: number = template.indexOf('data-bind');
                return template.substring(0, idx-1) + " style='width: " + (width - 5) + "px;' "
                    + template.substring(idx, template.length);
            }

            // ======================================== ntsTimeEditor ===================================
            if (template.indexOf('ntsTimeEditor') == -1) {
                return template;
            }
            
            width = width - 27;
            
            // find index
            let idx: number = properties.indexOf('option');

            // no option
            if (idx == -1) {
                return template.replace(properties, properties + ",option:{width:'" + width + "'}");
            }
            // has option 
            let oldOption: string = properties.substring(properties.indexOf("{") + 1, properties.lastIndexOf("}"));
            let newOption: string = oldOption;

            // check has option width
            let idxKey: number = newOption.indexOf('width');
            if (idxKey == -1) {
                newOption = newOption.replace(self.subString(newOption, idxKey), "width:" + width);
            } else {
                newOption += ",width:'" + width + "'";
            }
            return template.replace(properties, properties.replace(oldOption, newOption));
        }
        
        /**
         * Get control infor
         */
        private getInfoControl(template: string): IControl {
            let self = this;
            return self.mapControl.filter(item => template.indexOf(item.controlType) > -1)[0];
        }
        
        /**
         * update element html
         */
        private updateElement(input: string, keyValue: string, value: string): string {
            let self = this;
            
            // get index
            let idxKey: number = input.indexOf(keyValue);
            
            // keyValue not found
            if (idxKey == undefined || idxKey == -1) {
                input = keyValue + "" + value + "," + input; 
            } else {
                input = input.replace(self.subString(input, idxKey), keyValue + "" + value);
            }
            return input;
        }
        
        /**
         * substring
         */
        private subString(input: string, idxStart: number): string {
            let result: string = "";
            let charComma: string = ',';
            
            for (var i = idxStart; i < input.length; i++) {
                
                // first char comma
                if (input.charAt(i) == charComma) {
                    break;
                }
                result += input.charAt(i);
            }
            return result;
        }
    }
    
    /**
     * FixTableBindingHandler
     */
    class FixTableBindingHandler implements KnockoutBindingHandler {
        
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            
            let webserviceLocator: any = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/base/fixedtable/fixedtable.xhtml').serialize();
            
            //get data
            let input: any = valueAccessor();
            let data: FixTableOption = input.option;

            let screenModel = new FixTableScreenModel(data);
            screenModel.$element = $(element);
            $(element).load(webserviceLocator, function() {
                screenModel.initialScreen().done(() => {
                    ko.cleanNode($(element)[0]);
                    ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                    
                    // set height table
                    screenModel.$tableSelector.height(screenModel.tableStyle.height);
                });
            });
        }

    }
    
    ko.bindingHandlers['ntsFixTableCustom'] = new FixTableBindingHandler();
}