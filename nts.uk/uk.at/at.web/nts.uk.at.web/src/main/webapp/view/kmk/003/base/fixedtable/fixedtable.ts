module nts.fixedtable {

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
        
        /**
        * Set width table
        */
        width?: number;
        
        helpImageUrl?: string;
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
        
        /**
         * Enable column
         */
        enable?: boolean;
        
        isRoudingColumn?: boolean;
        
        unitAttrName?: string;
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
        width: number;
        tableStyle: TableStyle;
        
        $element: JQuery;
        $tableSelector: any;
        mapControl: Array<IControl>;
        tabindex: number;
        isEnableAllControl: KnockoutObservable<boolean>;
        roudingDataSource: KnockoutObservableArray<any>;
        isSelectSpecialUnit: KnockoutObservable<boolean>;
        specialRoudingDataSource: KnockoutObservableArray<any>;
        helpImageUrl: string;
        isMultipleSelect: boolean;
        tableId: string;
        
        constructor(data: FixTableOption, isDisableAll?: any) {
            let self = this;
            self.isMultipleSelect = data.isMultipleSelect ? data.isMultipleSelect : false;
            
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
            self.helpImageUrl = data.helpImageUrl;
            self.itemList = data.dataSource;
            self.isEnableAllControl = ko.observable(isDisableAll ? isDisableAll() : true);
            self.roudingDataSource = ko.observableArray([
                { value: 0, localizedName: '切り捨て', fieldName: 'Enum_Rounding_Down' },
                { value: 1, localizedName: '切り上げ', fieldName: 'Enum_Rounding_Up' },
                { value: 2, localizedName: '未満切捨、以上切上', fieldName: 'Enum_Rounding_Down_Over' }
            ]);
            self.specialRoudingDataSource = ko.observableArray([
                { value: 0, localizedName: '切り捨て', fieldName: 'Enum_Rounding_Down' },
                { value: 1, localizedName: '切り上げ', fieldName: 'Enum_Rounding_Up' }
            ]);

            
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
                    // Add default data
                    self.addMinRows();
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
            
            // update width table
            if (!self.width) {
                self.width = self.tableStyle.width;
            }
            
            // render html table
            return self.renderTable();
        }

        /**
         * Add minimum rows
         */
        public addMinRows(): void {
            let self = this;
            if (self.minRow > 0 && self.itemList().length == 0) {
                for (let i = 0; i < self.minRow; i++) {
                    self.addRowItem();
                }
            }
        }
        
        /**
         * Add a row table
         */
        public addRowItem() {
            let self = this;
            let row: any = {};
            _.forEach(self.columns, (column: FixColumn) => {
                let value: any = JSON.parse(JSON.stringify(ko.unwrap(column.defaultValue)));
                row[column.key] = ko.observable(value);
                
                // Subscriber columns
                row[column.key].subscribe((newValue: any) => {
                    self.itemList.valueHasMutated();
                });
            });
            self.itemList.push(row);
            const e = self.$element;
            if (e) {
                e.find('.time-range-editor').ntsError('clear');
                e.find('.time-range-editor').each((index, element) => {
                    $('#' + element.id).validateTimeRange();
                });
            }
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
            self.$element.find('.nts-editor').ntsError('clear');
            self.$element.find('.ntsControl').ntsError('clear');
            self.$element.find('.time-range-editor').ntsError('clear');
            _.defer(() => {
                self.itemList(self.itemList().filter(item => item.isChecked() == false));
                self.$element.find('.nts-editor').each((index, element) => {
                    if (!element.id) {
                        element.id = nts.uk.util.randomId();
                    } 
                    
                    $('#' + element.id).ntsEditor('validate');
                });
                self.$element.find('.time-range-editor').each((index, element) => {
                    $('#' + element.id).validateTimeRange();
                });
                
                // Focus check box default last item of table after remove.
                if (self.itemList().length > 0) {
                    self.$element.find('.column-checkbox-header').last().focus();
                }
            });
        }
        
        /**
         * calStyleTable
         */
        public calStyleTable() {
            let self = this;
            let heigthCell = 33;
            self.tableStyle.height = heigthCell * self.maxRowDisplay + 31;
            
            self.tableStyle.width = self.columns.map(column => column.width).reduce((a, b) => a + b, 0) + 30;
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
            _.forEach(self.itemList(), (row, index) => {
                if (index >= self.minRow) {
                    row.isChecked(newValue);
                }
            });
        }
        
        /**
         * renderTable
         */
        private renderTable(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            
            // add tbody table
            self.$tableSelector.append("<tbody data-bind='rended: itemList, foreach: itemList'>");
            
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
            if (self.isMultipleSelect) {
                rowHtml += "<td class='check-box-column' style='text-align: center;'><div class='column-checkbox-header' data-bind=\"attr: {tabindex: $parent.tabindex}, "
                    + "visible: $index() >= $parent.minRow, ntsCheckBox: { checked: isChecked, "
                    + "enable: true, text:''}\"></div></td>";
            }
            
            // add html column base setting
            _.forEach(self.columns, (item: FixColumn) => {
                
                // set width fixed control TimeRange
                if (item.template.indexOf('ntsTimeRangeEditor') != -1) {
                    item.width = 203;
                }
                if (item.template.indexOf('ntsComboBox') != -1) {
                    item.width = item.width < 70 ? 70 : item.width;
                }
                if (item.isRoudingColumn) {
                    rowHtml += '<!-- ko if: '+ item.unitAttrName +'() == 4 || '+ item.unitAttrName +'() == 6 -->'
                                    + self.generateColumnHtml(item, false)
                                    + '<!-- /ko -->'
                                    + '<!-- ko ifnot: '+ item.unitAttrName +'() == 4 || '+ item.unitAttrName +'() == 6 -->'
                                    + self.generateColumnHtml(item, true)
                                    + '<!-- /ko -->';
                    return;
                }
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
        private generateColumnHtml(columnSetting: FixColumn, isSpecialUnit?: boolean): string {
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
            newProperties = self.updateElement(newProperties, keyValue, columnSetting.key, columnSetting.enable);
            
            // insert option value of control if has
            let keyOptionValue: string = infoControl.keyOptionValue;
            if (keyOptionValue) {
                if (columnSetting.isRoudingColumn) {
                    newProperties = self.updateElement(newProperties, keyOptionValue,
                    isSpecialUnit ? "$parent.specialRoudingDataSource" : '$parent.specialRoudingDataSource', columnSetting.enable);
                } else {
                    // add data source for control
                    self.lstDataSource[columnSetting.key] = columnSetting.dataSource;

                    // update option cotrol html
                    newProperties = self.updateElement(newProperties, keyOptionValue,
                        "$parent.lstDataSource." + columnSetting.key, columnSetting.enable);
                }
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
            
            // Add enable attr to control.
            template = template.replace('enable: true', 'enable: $parent.isEnableAllControl');
            template = template.replace('enable:true', 'enable: $parent.isEnableAllControl');
            
            // get cssClassName
            let cssClassName: string = '';
            if (!nts.uk.text.isNullOrEmpty(columnSetting.cssClassName)) {
                cssClassName = columnSetting.cssClassName;
            }
            if (template.indexOf('ntsCheckBox') > -1) {
                cssClassName += 'center-align check-box-column';
            }
            return "<td class='" + cssClassName + "'>" + template + "</td>";
        }
        
        /**
         * Add/update width control
         */
        private updateWidthControl(template: string, properties: string, width: number): string {
            let self = this;
            
            // ======================================== ntsComboBox ===================================
            if (template.indexOf('ntsComboBox') > -1) {
                let idx: number = template.indexOf('data-bind');
                return template.substring(0, idx-1) + " style='width: " + (width - 5) + "px;float:left;' "
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
                return template.replace(properties, properties + ",option:{width:'" + width + "', textalign: 'center'}");
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
        private updateElement(input: string, keyValue: string, value: string, enable: boolean): string {
            let self = this;
            
            // get index
            let idxKey: number = input.indexOf(keyValue);
            
            // keyValue not found
            if (idxKey == undefined || idxKey == -1) {
                input = keyValue + "" + value + "," + input; 
            } else {
                input = input.replace(self.subString(input, idxKey), keyValue + "" + value);
            }
            
            //=================== Update Enable/Disable column ===================
            if (enable == undefined) {
                enable = true;
            }
            let keyEnable: string = "enable:true";
            let keyDisable: string = "enable:false";
            
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

        public initEventChangeComboBox(element: JQuery) {
            var self = this;
            if (element) {
                element.delegate('.ui-igcombo-wrapper', "igcomboselectionchanged", function(evt, ui) {
                    setTimeout(() =>
                        self.itemList.valueHasMutated()
                        , 200);
                });
            }
        }
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
    init = (element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
        bindingContext: KnockoutBindingContext) => {
        let input: any = valueAccessor();
        let webserviceLocator: any = nts.uk.request.location.siteRoot
            .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
            .mergeRelativePath('/view/kmk/003/base/fixedtable/fixedtable.xhtml').serialize();

        //get data
        let data: nts.fixedtable.FixTableOption = input.option;

        let screenModel = new nts.fixedtable.FixTableScreenModel(data,input.isEnableAllControl);
        if (input.isEnableAllControl) {
            input.isEnableAllControl.subscribe(function(value: boolean) {
                screenModel.isEnableAllControl(value);
            });
        }
        $(element).load(webserviceLocator, function() {
            screenModel.$element = $(element);
            screenModel.initialScreen().done(() => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);

                // set height table
                //screenModel.$tableSelector.height(screenModel.tableStyle.height);

                if (document.getElementById($(element)[0].id)) {
                    document.getElementById($(element)[0].id).addEventListener('timerangedatachange', function(event) {
                        screenModel.itemList.valueHasMutated();
                    });
                }
                screenModel.initEventChangeComboBox($(element));
                screenModel.$element.find('.table-fixed-kmk003').ntsFixedTable({height: screenModel.tableStyle.height});
                //screenModel.$tableSelector.ntsFixedTable({ height: 120, width: 814 });
                screenModel.$element.on('click', '.check-box-column > div', function(event){
                    _.defer(() => screenModel.itemList.valueHasMutated());
                });
                screenModel.$element.on('keypress', '.check-box-column > div', function(event){
                    if (event.keyCode === 0 || event.keyCode === 32) {
                        event.preventDefault();
                        _.defer(() => screenModel.itemList.valueHasMutated());
                    }
                });
                
                screenModel.$element.on('change', '.time-edior-column', function(event){
                    _.defer(() => screenModel.itemList.valueHasMutated());
                });
                
                screenModel.$element.on('change', '.nts-input', function(event){
                    _.defer(() => screenModel.itemList.valueHasMutated());
                });
                // Add default data
                screenModel.addMinRows();
            });
        });
    }

    /**
     * Update
     */
    update = (element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
        bindingContext: KnockoutBindingContext) => {
    }

}

ko.bindingHandlers['ntsFixTableCustom'] = new FixTableBindingHandler();
