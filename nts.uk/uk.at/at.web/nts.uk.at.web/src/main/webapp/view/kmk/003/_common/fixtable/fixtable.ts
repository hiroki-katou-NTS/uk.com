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
         * Option control type if have
         */
        properties?: any;
    }

    /************************************************ SCREEN MODEL ************************************************
    ***************************************************************************************************************/
    
    /**
     * ControlType
     */
    class ControlType {
        static CheckBox: number = 1;
        static TimeEditor: number = 2;
        static DateRangeEditor: number = 3;
        static ComboBox: number = 4;
    }
    
    /**
     * TableStyle
     */
    interface TableStyle {
        height: number;
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
        
        constructor() {
            let self = this;
            
            self.itemList = ko.observableArray([]);
            self.isSelectAll = ko.observable(false);
            
            self.tableStyle = {
                height: 0
            };
            self.lstDataSource = {};
            
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
                self.calHeightTable();
                
                // render table
                self.renderTable().done(() => {
                    ko.cleanNode($input[0]);
                    ko.applyBindings(self, $input[0]);
                    
                    dfd.resolve();
                });
            });
            return dfd.promise();
        }
        
        /**
         * calHeightTable
         */
        private calHeightTable() {
            let self = this;
            let heigthCell = 36;
            self.tableStyle.height = heigthCell * self.maxRows;
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
                    + "enable: true}, rended: $parent.itemList\"></div></td>";
            }
            
            // add html column base setting
            _.forEach(self.columns, (item: FixColumn) => {
                rowHtml += self.renderColumnHtml(item);
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
         * renderColumnHtml
         */
        private renderColumnHtml(columnSetting: FixColumn): string {
            let self = this;
            let htmlColumn: string = "";
            
            switch (columnSetting.controlType) {
                case ControlType.CheckBox:
                    htmlColumn = self.getCheckBoxHtml(columnSetting);
                    break;
                case ControlType.TimeEditor:
                    htmlColumn = self.getTimeEditorHtml(columnSetting);
                    break;
                case ControlType.DateRangeEditor:
                    htmlColumn = self.getDateRangeEditorHtml(columnSetting);
                    break;
                case ControlType.ComboBox:
                    htmlColumn = self.getComboBoxHtml(columnSetting);
                    break;
                default:
                    break;
            }
            return "<td style='text-align: center;'>" + htmlColumn + "</td>";
        }
        
        /**
         * getCheckBoxControl
         */
        private getCheckBoxHtml(columnSetting: FixColumn): string {
            
            let properties: any = columnSetting.properties;
            
            // define option of check box
            let checkBoxHtml: string = "<div data-bind=\"ntsCheckBox: { checked: " + columnSetting.key + ",";
            
            if (!properties) {
                return checkBoxHtml.substring(0, checkBoxHtml.length -1).concat("}\"/></div>");
            }
            
            if (properties.enable != undefined) {
                checkBoxHtml += "enable: " + properties.enable + ",";
            }
            if (properties.text) {
                checkBoxHtml += "text: '" + properties.text + "',";
            }
            return checkBoxHtml.substring(0, checkBoxHtml.length -1).concat("}\"/></div>");
        }
        
        /**
         * getTimeEditorHtml
         */
        private getTimeEditorHtml(columnSetting: FixColumn): string {
            let properties: any = columnSetting.properties;
            
            let timeEditorHtml: string = `<div><input data-bind="ntsTimeEditor: {
                                        value: ` + columnSetting.key + ",option: {width: '80'},";
            if (!properties) {
                return timeEditorHtml.substring(0, timeEditorHtml.length -1).concat("}\" /></div>");
            }
            
            if (properties.name) {
                timeEditorHtml += "name: '" + properties.name + "',";
            }
            if (properties.constraint) {
                timeEditorHtml += "constraint: " + properties.constraint + ",";
            }
            if (properties.required) {
                timeEditorHtml += "required: " + properties.required + ",";
            }
            if (properties.enable) {
                timeEditorHtml += "enable: " + properties.enable + ",";
            }
            if (properties.readonly) {
                timeEditorHtml += "readonly: " + properties.readonly + ",";
            }
            if (properties.inputFormat) {
                timeEditorHtml += "inputFormat: '" + properties.inputFormat + "',";
            }
            if (properties.immediate) {
                timeEditorHtml += "immediate: " + properties.immediate + ",";
            }
            if (properties.mode) {
                timeEditorHtml += "mode: '" + properties.mode + "',";
            }
            return timeEditorHtml.substring(0, timeEditorHtml.length -1).concat("}\" /></div>");
        }
        
        /**
         * getDateRangeEditorHtml
         */
        private getDateRangeEditorHtml(columnSetting: FixColumn): string {
            let properties: any = columnSetting.properties;
            
            let html: string = "<div data-bind=\"ntsDateRangePicker: {value: " + columnSetting.key + ","; 
            if (!properties) {
                return html.substring(0, html.length -1).concat("}\" /></div>");
            }
            
            if (properties.type) {
                html += "type: '" + properties.type + "',";
            }
            if (properties.maxRange) {
                html += "maxRange: '" + properties.maxRange + "',";
            }
            if (properties.name) {
                html += "name: '#[" + properties.name + "]',";
            }
            if (properties.startName) {
                html += "startName: '#[" + properties.startName + "]',";
            }
            if (properties.endName) {
                html += "endName: '#[" + properties.endName + "]',";
            }
            if (properties.enable) {
                html += "enable: " + properties.enable + ",";
            }
            if (properties.required) {
                html += "required: " + properties.required + ",";
            }
            if (properties.showNextPrevious) {
                html += "showNextPrevious: " + properties.showNextPrevious + ",";
            }
            return html.substring(0, html.length -1).concat("}\" /></div>");
        }
        
        /**
         * getComboBoxHtml
         */
        private getComboBoxHtml(columnSetting: FixColumn): string {
            let self = this;
            let properties: any = columnSetting.properties;
            
            // add data source
            self.lstDataSource[columnSetting.key] = properties.options;
            
            // render html
            let html: string = "<div data-bind=\"ntsComboBox: {options: $parent.lstDataSource." + columnSetting.key + ", optionsValue: '"
                + properties.optionsValue + "',value: " + columnSetting.key + ",optionsText: '" + properties.optionsText + "',"; 
            if (!properties) {
                return html.substring(0, html.length -1).concat("}\" /></div>");
            }
            html += "columns: " + (properties.columns ? properties.columns : []) + ",";
            if (properties.visibleItemsCount) {
                html += "visibleItemsCount: " + properties.visibleItemsCount + ",";
            }
            if (properties.editable) {
                html += "editable: " + properties.editable + ",";
            }
            if (properties.enable) {
                html += "enable: " + properties.enable + ",";
            }
            return html.substring(0, html.length -1).concat("}\" /></div>");
        }
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
