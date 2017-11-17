module kmk003.common.fixtable {

    /************************************************ PARAMETERS INITIAL FIXTABLE **********************************
    ***************************************************************************************************************/

    /**
     * FixTableOption
     */
    export interface FixTableOption {

        /**
         * height table
         */
        maxRows: number;
        
        /**
         * data source table
         */
        dataSource: KnockoutObservableArray<any>;

        /**
         * is Multiple select.
         */
        isMultipleSelect: boolean;
        
        /**
         * set tabIndex
         */
        tabindex?: number;

        /**
         * list columns table
         */
        columns: Array<FixColumn>;
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
        
        // needed when has comboBox, ...
        lstDataSource: any;
        
        isMultiple: boolean;
        columns: Array<FixColumn>;
        maxRows: number;
        tableStyle: TableStyle;
        
        $tableSelector: any;
        mapControl: Array<IControl>;
        tabindex: number;
        
        constructor() {
            let self = this;
            
            self.itemList = ko.observableArray([]);
            self.isSelectAll = ko.observable(false);
            
            self.tableStyle = {
                height: 0,
                width: 0
            };
            self.lstDataSource = {};
            self.mapControl = self.createMapControl();
            
            // subscribe
            self.isSelectAll.subscribe(newValue => {
                self.computedChange(newValue);
            });
        }

        /**
         * init
         */
        public init($input: JQuery, data: FixTableOption): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            
            ko.cleanNode($input[0]);
            
            // set data parameter
            self.isMultiple = data.isMultipleSelect;
            self.columns = data.columns;
            self.maxRows = data.maxRows;
            if (!self.maxRows) {
                self.maxRows = 10;
            }
            self.tabindex = data.tabindex;
            if (!self.tabindex) {
                self.tabindex = -1;
            }
            self.itemList = data.dataSource;
            
            // add properties isChecked when multiple select
            if (self.isMultiple) {
                self.addCheckBoxItemAtr();
            }

            // subscribe itemList
            self.itemList.subscribe((newList) => {
                if (!newList || newList.length <= 0) {
                    self.isSelectAll(false);
                    return;
                }
                // add properties isChecked when multiple select
                if (self.isMultiple) {
                    self.addCheckBoxItemAtr();
                }
                
                // re-load table
                self.loadTable($input);
            });

            // render table
            self.loadTable($input).done(() => {
                dfd.resolve();
            });
            
            return dfd.promise();
        }
        
        /**
         * loadTable
         */
        private loadTable($input: JQuery): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            
            let webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/_common/fixtable/fixtable.xhtml').serialize();
            $input.load(webserviceLocator, function() {
                self.$tableSelector = $('#fixed-table-custom');
                ko.cleanNode(self.$tableSelector[0]);
                
                // calculate height table
                self.calStyleTable();
                
                // render table
                self.renderTable().done(() => {
                    ko.cleanNode($input[0]);
                    ko.applyBindings(self, $input[0]);

                    // override width control
                    self.overrideWidthControl();
                    
                    dfd.resolve();
                });
            });
            return dfd.promise();
        }
        
        /**
         * Override width control
         */
        private overrideWidthControl() {
            let self = this;
            
            // control ntsTimeEditor
            let timeColumn: FixColumn = self.columns.filter(column => column.template.indexOf('TimeEditor') > -1)[0]; 
            $('.' + timeColumn.cssClassName).find("span input").width(timeColumn.width - 27);
            
            // control ntsComboBox
            let comboBoxColumn: FixColumn = self.columns.filter(column => column.template.indexOf('ComboBox') > -1)[0]; 
            $('.' + comboBoxColumn.cssClassName).each(function() {
                $(this).find("div").first().width(comboBoxColumn.width - 5);
            });
        }
        
        /**
         * calStyleTable
         */
        private calStyleTable() {
            let self = this;
            let heigthCell = 36;
            self.tableStyle.height = heigthCell * self.maxRows + 1;
            
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
        private renderTable():JQueryPromise<void> {
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
            if (self.isMultiple) {
                rowHtml += "<td style='text-align: center;'><div data-bind=\"ntsCheckBox: { checked: isChecked, "
                    + "enable: true, text:''}, rended: $parent.itemList\"></div></td>";
            }
            
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
                    row.isChecked.subscribe((newList) => {
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
                {controlType: 'ntsDateRangePicker', keyValue: 'value:'}
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
            let oldProperties: string = template.substring(template.indexOf("{") + 1, template.indexOf("}"));
            
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
            
            template = template.replace(oldProperties, newProperties.replace(/"/g, "'"));
            
            return "<td style='text-align: center;' class='" + columnSetting.cssClassName + "'>" + template + "</td>";
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
}

/**
 * Defined Jquery interface.
 */
interface JQuery {

    /**
     * Nts fix table component.
     */
    ntsFixTableCustom(option: kmk003.common.fixtable.FixTableOption): JQueryPromise<void>;

    /**
     * Focus component.
     */
    focusFixTable(): void;
}

(function($: any) {
    $.fn.ntsFixTableCustom = function(option: kmk003.common.fixtable.FixTableOption): JQueryPromise<void> {

        // Return.
        return new kmk003.common.fixtable.FixTableScreenModel().init(this, option);
    }
} (jQuery));
