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
        height: number;
        
        /**
         * data source table
         */
        dataSource: Array<any>;

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
        options?: any;
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
        lstColumnTemplate: Array<string>;
    }
    
    /**
     * FixTableScreenModel
     */
    export class FixTableScreenModel {

        itemList: KnockoutObservableArray<any>;
        
        // needed when model multiple select
        isSelectAll: KnockoutObservable<boolean>;
        
        isMultiple: boolean;
        columns: Array<FixColumn>;
        tableStyle: TableStyle;
        
        $tableSelector: any;
        
        constructor() {
            let self = this;
            
            self.itemList = ko.observableArray([]);
            self.isSelectAll = ko.observable(false);
            
            self.tableStyle = {
                height: 0,
                lstColumnTemplate: null
            };
            
            // subscribe
            self.itemList.subscribe((newList) => {
                if (!newList || newList.length <= 0) {
                    return;
                }
                // add properties isChecked when multiple select
                if (self.isMultiple) {
                    self.addCheckBoxItemAtr(newList);
                }
            });
            self.isSelectAll.subscribe(newValue => {
                return self.subscribeChanged(newValue);
            });
            
            // Create Customs handle For event rened nts grid.
            (<any>ko.bindingHandlers).rended = {
            update: function(element: any, valueAccessor: any, allBindings: KnockoutAllBindingsAccessor) {
                    let itemList: Array<any> = ko.unwrap(valueAccessor());
                    // event changed combo box
                    self.computedChangeCheckbox(itemList);
                }
            }
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
            
            self.itemList(data.dataSource);
            self.columns = data.columns;

            let webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/_common/fixtable/fixtable.xhtml').serialize();

            $input.load(webserviceLocator, function() {
                self.$tableSelector = $('#fixed-table-custom');
                
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
         * computedChangeCheckbox
         */
        private computedChangeCheckbox(itemList: Array<any>) {
            let self = this;
            if (self.isSelectAll()) {
                return;
            }
            self.isSelectAll(itemList.every(item => item.isChecked() == true));
        }
        
        /**
         * subscribeChanged
         */
        private subscribeChanged(newValue: boolean) {
            let self = this;
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
        private addCheckBoxItemAtr(itemList: Array<any>) {
            let self = this;
            // if mode multiple, set default check box false
            _.forEach(itemList, row => {
                row.isChecked = ko.observable(false);
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
            
            let options: any = columnSetting.options;
            
            // define option of check box
            let checkBoxHtml: string = "<div data-bind=\"ntsCheckBox: { checked: " + columnSetting.key + ",";
            
            if (!options) {
                return checkBoxHtml.substring(0, checkBoxHtml.length -1).concat("}\"/></div>");
            }
            
            if (options.enable != undefined) {
                checkBoxHtml += "enable: " + options.enable + ",";
            }
            if (options.text) {
                checkBoxHtml += "text: '" + options.text + "',";
            }
            return checkBoxHtml.substring(0, checkBoxHtml.length -1).concat("}\"/></div>");
        }
        
        /**
         * getTimeEditorHtml
         */
        private getTimeEditorHtml(columnSetting: FixColumn): string {
            let options: any = columnSetting.options;
            
            let timeEditorHtml: string = `<div><input data-bind="ntsTimeEditor: {
                                        value: ` + columnSetting.key + ",";
            if (!options) {
                return timeEditorHtml.substring(0, timeEditorHtml.length -1).concat("}\" /></div>");
            }
            
            if (options.name) {
                timeEditorHtml += "name: '" + options.name + "',";
            }
            if (options.constraint) {
                timeEditorHtml += "constraint: " + options.constraint + ",";
            }
            if (options.option) {
                timeEditorHtml += "option: " + options.option + ",";
            }
            if (options.required) {
                timeEditorHtml += "required: " + options.required + ",";
            }
            if (options.enable) {
                timeEditorHtml += "enable: " + options.enable + ",";
            }
            if (options.readonly) {
                timeEditorHtml += "readonly: " + options.readonly + ",";
            }
            if (options.inputFormat) {
                timeEditorHtml += "inputFormat: '" + options.inputFormat + "',";
            }
            if (options.immediate) {
                timeEditorHtml += "immediate: " + options.immediate + ",";
            }
            if (options.mode) {
                timeEditorHtml += "mode: '" + options.mode + "',";
            }
            return timeEditorHtml.substring(0, timeEditorHtml.length -1).concat("}\" /></div>");
        }
        
        /**
         * getDateRangeEditorHtml
         */
        private getDateRangeEditorHtml(columnSetting: FixColumn): string {
            let options: any = columnSetting.options;
            
            let html: string = "<div data-bind=\"ntsDateRangePicker: {value: " + columnSetting.key + ","; 
            if (!options) {
                return html.substring(0, html.length -1).concat("}\" /></div>");
            }
            
            if (options.type) {
                html += "type: '" + options.type + "',";
            }
            if (options.maxRange) {
                html += "maxRange: '" + options.maxRange + "',";
            }
            if (options.name) {
                html += "name: '#[" + options.name + "]',";
            }
            if (options.startName) {
                html += "startName: '#[" + options.startName + "]',";
            }
            if (options.endName) {
                html += "endName: '#[" + options.endName + "]',";
            }
            if (options.enable) {
                html += "enable: " + options.enable + ",";
            }
            if (options.required) {
                html += "required: " + options.required + ",";
            }
            if (options.showNextPrevious) {
                html += "showNextPrevious: " + options.showNextPrevious + ",";
            }
            return html.substring(0, html.length -1).concat("}\" /></div>");
        }
        
        /**
         * getComboBoxHtml
         */
        private getComboBoxHtml(columnSetting: FixColumn): string {
            let options: any = columnSetting.options;
            
            let html: string = "<div data-bind=\"ntsComboBox: {options: " + ko.observableArray(options.options) + ", optionsValue: '"
                + options.optionsValue + "',value: " + columnSetting.key + ",optionsText: '" + options.optionsText + "',"; 
            if (!options) {
                return html.substring(0, html.length -1).concat("}\" /></div>");
            }
            html += "columns: " + JSON.stringify(options.columns ? options.columns : []) + ",";
            if (options.visibleItemsCount) {
                html += "visibleItemsCount: " + options.visibleItemsCount + ",";
            }
            if (options.editable) {
                html += "editable: " + options.editable + ",";
            }
            if (options.enable) {
                html += "enable: " + options.enable + ",";
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
