module nts.custombinding {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import random = nts.uk.util.randomId;
    import text = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import parseTime = nts.uk.time.parseTime;
    import clearError = nts.uk.ui.errors.clearAll;

    let writeConstraint = window['nts']['uk']['ui']['validation']['writeConstraint'],
        writeConstraints = window['nts']['uk']['ui']['validation']['writeConstraints'],
        parseTimeWidthDay = window['nts']['uk']['time']['minutesBased']['clock']['dayattr']['create'];

    export class LayoutControl implements KnockoutBindingHandler {
        private style = `<style type="text/css" rel="stylesheet" id="layout_style">
                    .layout-control.dragable{
                        width: 1245px;
                    }
                    .layout-control .left-area,
                    .layout-control .right-area,
                    .layout-control .add-buttons,
                    .layout-control .drag-panel {
                        float: left;
                    }

                    .layout-control .left-area {
                        margin-right: 15px;
                    }

                    .layout-control .form-group {
                        margin-bottom: 5px;
                    }

                    .layout-control .control-group {
                        margin-top: 10px;
                        padding-left: 10px;
                    }

                    .layout-control #cps007_cbx_control {
                        min-width: 248px;
                    }

                    .layout-control .ntsControl.radio-control {
                        width: 100%;
                        padding-bottom: 3px;
                    }

                    .layout-control .ntsControl.radio-control .ntsRadioBox {
                        width: 50%;
                        padding-right: 5px;
                        box-sizing: border-box;
                    }

                    .layout-control .ntsControl.search-control .nts-editor {
                        width: 178px !important;
                    }

                    .layout-control .ui-iggrid-scrolldiv {
                        background-color: #fff;
                    }

                    .layout-control .add-buttons {
                        margin-right: 15px;
                        padding-top: 220px;
                    }

                    .layout-control .drag-panel {
                        border: 1px solid #ccc;
                        border-radius: 10px;
                        width: 810px;
                        height: 615px;
                        padding: 10px;
                        box-sizing: border-box;
                    }

                    .layout-control div.ui-sortable {
                        overflow-x: hidden;
                        overflow-y: scroll;
                        padding-right: 10px;
                        box-sizing: border-box;
                    }

                    .layout-control.readonly div.ui-sortable {
                        height: 100%;
                    }

                    .layout-control.dragable div.ui-sortable {
                        max-height: 94%;
                        margin-bottom: 3px;
                    }

                    .layout-control .item-classification {
                        padding: 3px;
                        position: relative;
                        box-sizing: border-box;
                        background-color: #fff;
                        border: 1px dashed transparent;
                    }

                    .layout-control .item-classification div.item-control>*,
                    .layout-control .item-classification div.item-controls>* {
                        vertical-align: top;
                        display: inline-block;
                    }

                    .layout-control .item-classification div.item-control>.set-items,
                    .layout-control .item-classification div.item-control>.single-items {
                        margin-top: 3px;
                    }

                    .layout-control .item-classification div.multiple-items {
                        overflow: hidden;
                    }

                    .layout-control .item-classification div.set-item,
                    .layout-control .item-classification div.item-control>div {
                        display: inline-block;
                    }

                    .layout-control .item-classificatio div.set-item.set-item-sperator {
                        width: 37px;
                        text-align: center;
                    }

                    .layout-control .item-classification .table-container {
                        max-width: calc(100% - 225px);
                        color: #000;
                        padding-top: 35px;
                        position: relative;
                        border: 1px solid #aaa;
                        background-color: #CFF1A5;
                        background: -webkit-repeating-linear-gradient(#CFF1A5, #CFF1A5 35px, #aaa 36px, #CFF1A5 36px);
                        background: -o-repeating-linear-gradient(#CFF1A5, #CFF1A5 35px, #aaa 36px, #CFF1A5 36px);
                        background: -moz-repeating-linear-gradient(#CFF1A5, #CFF1A5 35px, #aaa 36px, #CFF1A5 36px);
                        background: repeating-linear-gradient(#CFF1A5, #CFF1A5 35px, #aaa 36px, #CFF1A5 36px);
                    }

                    .layout-control.dragable .item-classification .table-container {
                        max-width: calc(100% - 240px);
                    }
                
                    .layout-control .item-classification .table-container.header-2rows {
                        padding-top: 70px;
                    }
                
                    .layout-control .item-classification .table-container.header-3rows {
                        padding-top: 105px;
                    }
                
                    .layout-control .item-classification .table-container>div {
                        overflow-y: auto;
                        max-height: 205px;
                        border-top: 1px solid #aaa;
                    }
                
                    .layout-control .item-classification .table-container>div table {
                        border-collapse: collapse;
                    }
                
                    .layout-control .item-classification td {
                        background-color: #fff;
                        border-left: 1px solid #aaa;
                    }
                
                    .layout-control .item-classification td,
                    .layout-control .item-classification th {
                        padding: 0px;
                        border: 1px solid #aaa;
                    }
                
                    .layout-control .item-classification td:first-child {
                        border-left: none;
                    }
                
                    .layout-control .item-classification td:last-child {
                        border-right: none;
                    }
                
                    .layout-control .item-classification th {
                        height: 0;
                        line-height: 0;
                        border: none;
                        color: transparent;
                        white-space: nowrap;
                    }
                
                    .layout-control .item-classification th div {
                        top: 0;    
                        height: 35px;
                        padding: 3px;
                        color: #000;
                        overflow: hidden;
                        line-height: 32px;
                        position: absolute;
                        box-sizing: border-box;
                        background: transparent;
                        border-left: 1px solid #aaa;
                    }
                
                    .layout-control .item-classification thead>tr:first-child div {
                        top: 0;
                    }
                
                    .layout-control .item-classification thead>tr:nth-child(2) div {
                        top: 35px;
                    }
                
                    .layout-control .item-classification thead>tr:nth-child(3) div {
                        top: 70px;
                    }

                    .layout-control .item-classification th.index,
                    .layout-control .item-classification td.index {
                        min-width: 30px;
                        text-align: center;
                    }

                    .layout-control .item-classification td input,
                    .layout-control .item-classification td textarea {
                        border: 1px solid transparent;
                        border-radius: 0;
                    }

                    .layout-control .item-classification td input:focus,
                    .layout-control .item-classification td textarea:focus {
                        border: 1px dashed #0096f2;
                        box-shadow: none;
                    }
                
                    .layout-control .item-classification th:first-child div {
                      border: none;
                    }
                
                    .layout-control .item-classification tbody tr:first-child td {
                      border-top: none;
                    }
                
                    .layout-control .item-classification tbody tr:last-child td {
                      border-bottom: none;
                    }

                    .layout-control .item-classification div.item-sperator>hr {
                        padding: 0;
                        margin: 6px 2px 6px 0;
                    }

                    .layout-control.dragable .item-classification div.item-sperator>hr {
                        margin-right: 20px;
                    }

                    .layout-control .item-classification.ui-sortable-helper {
                        cursor: pointer;
                    }

                    .layout-control .item-classification.ui-sortable-placeholder {
                        border: 1px dashed #ddd;
                        visibility: visible !important;
                    }

                    .layout-control.dragable .item-classification:hover,
                    .layout-control.dragable .item-classification.selected {
                        background-color: #eee;
                        border: 1px dashed #aaa;
                    }

                    .layout-control .item-classification .item-control textarea.nts-editor,
                    .layout-control .item-classification .item-controls textarea.nts-editor {
                        width: 368px;
                        height: 70px;
                        overflow-y: scroll;
                    }

                    .layout-control .item-classification .item-controls .ntsControl,
                    .layout-control .item-classification .item-controls textarea.nts-editor,
                    .layout-control .item-classification .item-controls .ui-igcombo-wrapper {
                        display: block;
                    }

                    .layout-control .item-classification .item-controls .ui-igcombo-wrapper {
                        height: 30px;
                    }

                    .layout-control .item-classification .item-controls .ui-igcombo.ui-state-default {
                        border: none;
                    }

                    .layout-control .item-classification .item-control .ui-igcombo-wrapper .ui-igcombo-buttonicon,
                    .layout-control .item-classification .item-controls .ui-igcombo-wrapper .ui-igcombo-buttonicon {
                        display: none;    
                    }

                    .layout-control .item-classification .item-control .ui-igcombo-wrapper .ui-igcombo-button:before,
                    .layout-control .item-classification .item-controls .ui-igcombo-wrapper .ui-igcombo-button:before {
                        top: 3px;
                        left: 4px;
                        font-size: 1.5em;
                        content: '▾';
                        display: block;
                        position: absolute;
                    }

                    .layout-control .item-classification .ui-igcombo-wrapper {
                        width: auto;
                        width: initial;                            
                    }

                    .layout-control .item-classification .form-label {
                        width: 210px;
                        white-space: nowrap;
                    }

                    .layout-control .item-classification>.close-btn {
                        top: 0;
                        right: 5px;
                        display: none;
                        cursor: pointer;
                        position: absolute;
                    }

                    .layout-control .item-classification>.close-btn:hover {
                        color: #f00;
                    }

                    .layout-control.dragable .item-classification:hover>.close-btn {
                        display: block;
                    }

                    .layout-control.readonly [disabled],
                    .layout-control.dragable [disabled] {
                        background-color: #fff;
                    }

                    .layout-control .index .remove-btn,
                    .layout-control.dragable .index:hover .remove-btn {
                        display: none;
                    }

                    .layout-control.inputable tr:hover .number,
                    .layout-control.inputable .index:hover .number {
                        display: block;
                    }

                    .layout-control.inputable tr:hover .remove-btn,
                    .layout-control.inputable .index:hover .remove-btn {
                        display: none;
                    }

                    .layout-control.inputable .remove-btn:hover {
                        color: #f00;
                        cursor: pointer;
                        -webkit-touch-callout: none;
                        -webkit-user-select: none;
                        -khtml-user-select: none;
                        -moz-user-select: none;
                        -ms-user-select: none;
                        user-select: none;
                    }

                    .layout-control.dragable .add-rows,
                    .layout-control.dragable .add-rows button,
                    .layout-control.inputable .add-rows,
                    .layout-control.inputable .add-rows button,
                    .layout-control.readonly:not(.inputable) .add-rows,
                    .layout-control.readonly:not(.inputable) .add-rows button {
                        display: none;
                    }

                    .layout-control.dragable .color-operation-case-character,
                    .layout-control.readonly:not(.inputable) .color-operation-case-character {
                        color: #000 !important;
                    }
                </style>`;

        private tmp = `<div class="left-area">
                    <div id="cps007_lbl_control"></div>
                    <div class="control-group">
                        <div class="form-group">
                            <div id="cps007_rdg_control" class="radio-control ntsControl"></div>
                        </div>
                        <div class="form-group">
                            <div id="cps007_cbx_control" class="combobox-control ntsControl"></div>
                        </div>
                        <div class="form-group">
                            <div id="cps007_sch_control" class="search-control ntsControl"></div>
                        </div>
                        <div class="form-group">
                            <div id="cps007_lst_control" class="listbox-control ntsControl"></div>
                        </div>
                    </div>
                </div>
                <div class="right-area cf">
                    <div class="add-buttons">
                        <button id="cps007_btn_add"></button>
                    </div>
                    <div class="drag-panel">
                        <div id="cps007_srt_control">
                            <div class="form-group item-classification"
                                    data-bind="let: {
                                        text: nts.uk.resource.getText,
                                        CAT_TYPE: {  
                                            SINGLE : 1,
                                            MULTI: 2,
                                            CONTI: 3, /* continuos history hasn't end date */
                                            NODUP: 4,
                                            DUPLI: 5,
                                            CONTIWED: 6 /* continuos history has end date */
                                        },
                                        LAYOUT_TYPE: {
                                            ITEM: 'ITEM',
                                            LIST: 'LIST',
                                            SEPRL: 'SeparatorLine'
                                        },
                                        ITEM_TYPE: {
                                            STRING: 1,
                                            NUMERIC: 2,
                                            DATE: 3,
                                            TIME: 4,
                                            TIMEPOINT: 5,
                                            SELECTION: 6
                                        },
                                        STRING_TYPE: {
                                            ANY: 1,
                                            ANYHALFWIDTH: 2,
                                            ALPHANUMERIC: 3,
                                            NUMERIC: 4,
                                            KANA: 5
                                        },
                                        DATE_TYPE: {
                                            YYYYMMDD: 1,
                                            YYYYMM: 2,
                                            YYYY: 3
                                        },
                                        CTRL_TYPE: {
                                            SET: 1,
                                            SINGLE: 2
                                        },
                                        cls: $data, 
                                        _item: items && _.find(items(), function(x, i) { return i == 0}), 
                                        _items: items && _.filter(items(), function(x, i) { return i > 0}),
                                        __items: items && _.filter(items(), function(x, i) { return i >= 0})
                                    }">
                                <!-- ko if: layoutItemType == LAYOUT_TYPE.ITEM -->
                                <div class="item-control" data-bind="let: { _constraint: _(__items.length == 1 ? __items : _items)
                                        .filter(function(x) { return (__items.length == 1 ? [ITEM_TYPE.DATE, ITEM_TYPE.SELECTION] : [ITEM_TYPE.DATE, ITEM_TYPE.TIME, ITEM_TYPE.TIMEPOINT, ITEM_TYPE.SELECTION]).indexOf((x.item||{}).dataTypeValue) == -1})
                                        .map(function(x) { return x.itemDefId.replace(/[-_]/g, '') })
                                        .value() }">
                                    <div data-bind="ntsFormLabel: { 
                                        text: className || '',
                                        cssClass: ko.computed(function() {
                                            if(!_item.showColor()) {
                                                return '';
                                            }
                                            
                                            if(!!_.find(__items, function(x) { return x.value() })) {
                                                 return '';
                                            }
                                            return 'color-operation-case-character'; 
                                        }),
                                        required: !!_.find(__items, function(x) { return x.required }),
                                        constraint: _constraint.length && _constraint || undefined  }"></div>
                                    <!-- ko if: (_item || {}).type == CTRL_TYPE.SET -->
                                    <div class="set-items">
                                        <!-- ko foreach: { data: _items, as: 'set'} -->
                                        <div class="set-item set-item-sperator" data-bind="css: { 'hidden': !$index() }">
                                            <!-- ko if: [ITEM_TYPE.DATE, ITEM_TYPE.TIME, ITEM_TYPE.TIMEPOINT].indexOf(set.item.dataTypeValue) > -1 -->
                                                <span data-bind="text: text('CPS001_89')"></span>
                                            <!-- /ko -->
                                        </div>
                                        <div data-bind="template: {
                                                data: set,
                                                name: 'ctr_template'
                                            }" class="set-item"></div>
                                        <!-- /ko -->
                                    </div>
                                    <!-- /ko -->
                                    <!-- ko if: (_item || {}).type == CTRL_TYPE.SINGLE -->
                                    <div class="single-items">
                                        <!-- ko foreach: {data: __items, as: 'single'} -->
                                        <div data-bind="template: { 
                                                data: single,
                                                name: 'ctr_template'
                                            }" class="single-item"></div>
                                        <!-- /ko -->
                                    </div>
                                    <!-- /ko -->
                                </div>
                                <!-- /ko -->
                                <!-- ko if: layoutItemType == LAYOUT_TYPE.LIST -->     
                                <div class="item-controls">
                                    <div data-bind="ntsFormLabel: { required: !!_.find(_items, function(x) { return !!x.required }), text: className || '' }"></div>
                                    <div class="multiple-items table-container header-1rows">
                                        <div class="table-scroll" 
                                                data-bind="event: { 
                                                    scroll: function(viewModel, event) {
                                                        let target = $(event.target),
                                                            thFirst = target.find('table th:first'); 
                                                        target.find('table th div').css('margin-left', thFirst.offset().left - target.offset().left + 'px');
                                                    },
                                                    mousewheel: function(viewModel, event) {
                                                        let direct = event.originalEvent.wheelDelta / 120 > 0,
                                                            target = $(event.target),
                                                            table = target.closest('table'),
                                                            closest = target.closest('.table-scroll');
                                                    }
                                                }">
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <!-- ko foreach: { data: _item, as: 'header' } -->
                                                        <th data-bind="template: { afterRender: function(childs, data) { let div = $(childs[1]); setInterval(function() { div.css('width', (div.parent().width() - 3) + 'px') }, 0); } }">
                                                            <div data-bind="ntsFormLabel: { 
                                                                constraint: [ITEM_TYPE.DATE, ITEM_TYPE.TIME, ITEM_TYPE.TIMEPOINT, ITEM_TYPE.SELECTION].indexOf((header.item||{}).dataTypeValue) == -1 ? header.itemDefId.replace(/[-_]/g, '') : undefined,
                                                                required: header.required, 
                                                                text: header.itemName || '',
                                                                inline: true }"></div>
                                                        </th>
                                                        <!-- /ko -->
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <!-- ko foreach: { data: __items, as: '_row' } -->
                                                    <tr>
                                                        <!-- ko foreach: { data: _row, as: '_column' } -->
                                                        <td data-bind="template: { 
                                                                data: _column,
                                                                name: 'ctr_template'
                                                            }, click: function(data, event) { $(event.target).find('input').focus(); }">
                                                        </td>
                                                        <!-- /ko -->
                                                    </tr>
                                                    <!-- /ko -->
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <!-- /ko -->
                                <!-- ko if: layoutItemType == LAYOUT_TYPE.SEPRL -->
                                <div class="item-sperator">
                                    <hr />
                                </div>
                                <!-- /ko -->
                                <span class="close-btn" data-bind="click: function($data, event) { ko.bindingHandlers['ntsLayoutControl'].remove(cls, event); }">✖</span>
                            </div>
                        </div>
                        <button id="cps007_btn_line"></button>
                    </div>
                </div>
                <script type="text/html" id="ctr_template">
                    <div data-bind="let: {
                            nameid : itemDefId.replace(/[-_]/g, '')
                        }">
                        <!-- ko if: item.dataTypeValue == ITEM_TYPE.STRING -->
                        <!-- ko if: item.stringItemType == STRING_TYPE.NUMERIC || item.stringItemLength < 40 || ([STRING_TYPE.ANY, STRING_TYPE.KANA].indexOf(item.stringItemType) > -1 && item.stringItemLength <= 80) -->
                        <input data-bind=" ntsTextEditor: {
                                name: itemName,
                                value: value,
                                constraint: nameid,
                                required: required,
                                option: {
                                    textmode: 'text'
                                },
                                enable: editable,
                                readonly: readonly,
                                immediate: false
                            },  attr: {
                                id: nameid,
                                nameid: nameid,
                                title: itemName
                            }," />
                        <!-- /ko -->
                        <!-- ko if: item.stringItemType != STRING_TYPE.NUMERIC && (([STRING_TYPE.ANY, STRING_TYPE.KANA].indexOf(item.stringItemType) == -1 && item.stringItemLength >= 40) || ([STRING_TYPE.ANY, STRING_TYPE.KANA].indexOf(item.stringItemType) > -1 && item.stringItemLength > 80)) -->
                        <textarea data-bind="ntsMultilineEditor: {
                                name: itemName,
                                value: value,
                                constraint: nameid,
                                required: required,
                                option: {
                                    textmode: 'text'
                                },
                                enable: editable,
                                readonly: readonly,
                                immediate: false 
                            }, attr: { 
                                id: nameid, 
                                nameid: nameid,
                                title: itemName
                            }" />
                        <!-- /ko -->
                        <!-- /ko -->
                        <!-- ko if: item.dataTypeValue == ITEM_TYPE.NUMERIC -->
                        <input data-bind="ntsNumberEditor: { 
                                    name: itemName,
                                    value: value,
                                    constraint: nameid,
                                    required: required,
                                    option: {
                                        grouplength: 3,
                                        decimallength: 2,
                                        textalign: 'left'
                                    },
                                    enable: editable,
                                    readonly: readonly
                                }, attr: {
                                    id: nameid, 
                                    nameid: nameid,
                                    title: itemName
                                }" />
                        <!-- /ko -->
                        <!-- ko if: item.dataTypeValue == ITEM_TYPE.DATE -->
                        <!-- ko if: index <= 1 -->
                        <div data-bind="ntsDatePicker: {
                                name: itemName,
                                value: value,
                                startDate: startDate,
                                endDate: endDate,
                                constraint: nameid,
                                dateFormat: item.dateItemType == DATE_TYPE.YYYYMMDD ? 'YYYY/MM/DD' : (item.dateItemType == DATE_TYPE.YYYYMM ? 'YYYY/MM' : 'YYYY'),
                                enable: editable,
                                readonly: readonly
                            }, attr: { 
                                id: nameid, 
                                nameid: nameid,
                                title: itemName
                            }"></div>
                        <!-- /ko -->
                        <!-- ko if: index == 2 -->
                        <!-- ko if: typeof ctgType !== 'undefined' -->
                            <!-- ko if: [CAT_TYPE.CONTI].indexOf(ctgType) > -1 -->
                            <div data-bind="text: value, attr: { title: itemName}"></div>
                            <!-- /ko -->
                            <!-- ko if: [CAT_TYPE.CONTI].indexOf(ctgType) == -1 -->
                            <div data-bind="ntsDatePicker: {
                                    name: itemName,
                                    value: value,
                                    startDate: startDate,
                                    endDate: endDate,
                                    constraint: nameid,
                                    dateFormat: item.dateItemType == DATE_TYPE.YYYYMMDD ? 'YYYY/MM/DD' : (item.dateItemType == DATE_TYPE.YYYYMM ? 'YYYY/MM' : 'YYYY'),
                                    enable: editable,
                                    readonly: readonly
                                }, attr: { 
                                    id: nameid, 
                                    nameid: nameid,
                                    title: itemName
                                }"></div>
                            <!-- /ko -->
                        <!-- /ko -->
                        <!-- ko if: typeof ctgType === 'undefined' -->
                            <div data-bind="ntsDatePicker: {
                                    name: itemName,
                                    value: value,
                                    startDate: startDate,
                                    endDate: endDate,
                                    constraint: nameid,
                                    dateFormat: item.dateItemType == DATE_TYPE.YYYYMMDD ? 'YYYY/MM/DD' : (item.dateItemType == DATE_TYPE.YYYYMM ? 'YYYY/MM' : 'YYYY'),
                                    enable: editable,
                                    readonly: readonly
                                }, attr: { 
                                    id: nameid, 
                                    nameid: nameid,
                                    title: itemName
                                }"></div>
                        <!-- /ko -->
                        <!-- /ko -->
                        <!-- /ko -->
                        <!-- ko if: item.dataTypeValue == ITEM_TYPE.TIME -->
                        <input data-bind="ntsTimeEditor: {
                                    name: itemName,
                                    value: value,
                                    constraint: nameid,
                                    required: required,
                                    inputFormat: 'time',
                                    enable: editable,
                                    mode: 'time',
                                    readonly: readonly
                                }, attr: {
                                    id: nameid, 
                                    nameid: nameid,
                                    title: itemName
                                }" />
                        <!-- /ko -->
                        <!-- ko if: item.dataTypeValue == ITEM_TYPE.TIMEPOINT -->
                        <input data-bind="ntsTimeWithDayEditor: { 
                                    name: itemName,
                                    constraint: nameid,
                                    value: value,
                                    enable: editable, 
                                    readonly: readonly,
                                    required: required
                                }, attr: {
                                    id: nameid, 
                                    nameid: nameid,
                                    title: itemName
                                }" />
                        <!-- /ko -->
                        <!-- ko if: item.dataTypeValue == ITEM_TYPE.SELECTION -->
                        <div data-bind="ntsComboBox: {
                                    name: itemName,
                                    value: value,
                                    options: ko.observableArray(lstComboBoxValue || []),
                                    optionsText: 'optionText',
                                    optionsValue: 'optionValue',
                                    enable: editable,
                                    visibleItemsCount: 5,
                                    dropDownAttachedToBody: true,
                                    columns: [{ prop: 'optionText', length: 10 }]
                                }, attr: {
                                    id: nameid,
                                    nameid: nameid,
                                    title: itemName
                                }"></div>
                        <!-- /ko -->
                    </div>
                </script>`;

        private api = {
            getCat: 'ctx/pereg/person/info/category/find/companyby/{0}',
            getCats: "ctx/pereg/person/info/category/findby/company",
            getGroups: 'ctx/pereg/person/groupitem/getAll',
            getItemCats: 'ctx/pereg/person/info/ctgItem/layout/findby/categoryId/{0}',
            getItemGroups: 'ctx/pereg/person/groupitem/getAllItemDf/{0}',
            getItemsById: 'ctx/pereg/person/info/ctgItem/layout/findby/itemId/{0}',
            getItemsByIds: 'ctx/pereg/person/info/ctgItem/layout/findby/listItemId',
        };

        private services = {
            getCat: (cid) => {
                let self = this,
                    api = self.api;

                return ajax(format(api.getCat, cid));
            },
            getCats: () => {
                let self = this,
                    api = self.api;

                return ajax(api.getCats);
            },
            getGroups: () => {
                let self = this,
                    api = self.api;

                return ajax(api.getGroups);
            },
            getItemByCat: (cid) => {
                let self = this,
                    api = self.api;

                return ajax(format(api.getItemCats, cid));
            },
            getItemByGroup: (gid) => {
                let self = this,
                    api = self.api;

                return ajax(format(api.getItemGroups, gid));
            },
            getItemsById: (id: string) => {
                let self = this,
                    api = self.api;

                return ajax(format(api.getItemsById, id));
            },
            getItemsByIds: (ids: Array<any>) => {
                let self = this,
                    api = self.api;

                return ajax(api.getItemsByIds, ids);
            }
        };

        remove = (item, sender) => {
            let target = $(sender.target),
                layout = target.parents('.layout-control'),
                opts = layout.data('options');

            opts.sortable.removeItem(item, false);
        };

        private _constructor = (element?: HTMLElement, valueAccessor?: any) => {
            let self = this,
                services = self.services,
                $element = $(element),
                opts = {
                    callback: () => {
                    },
                    radios: {
                        enable: ko.observable(true),
                        value: ko.observable(0),
                        options: ko.observableArray([{
                            id: CAT_OR_GROUP.CATEGORY,
                            name: text('CPS007_6'),
                            enable: ko.observable(true)
                        }, {
                                id: CAT_OR_GROUP.GROUP,
                                name: text('CPS007_7'),
                                enable: ko.observable(true)
                            }]),
                        optionsValue: 'id',
                        optionsText: 'name'
                    },
                    comboxbox: {
                        enable: ko.observable(true),
                        editable: ko.observable(false),
                        visibleItemsCount: 10,
                        value: ko.observable(''),
                        options: ko.observableArray([]),
                        optionsValue: 'id',
                        optionsText: 'categoryName',
                        columns: [{ prop: 'categoryName', length: 15 }]
                    },
                    searchbox: {
                        mode: 'listbox',
                        comId: 'cps007_lst_control',
                        items: ko.observableArray([]),
                        selected: ko.observableArray([]),
                        targetKey: 'id',
                        selectedKey: 'id',
                        fields: ['itemName'],
                        placeHolder: '名称で検索…'
                    },
                    listbox: {
                        enable: ko.observable(true),
                        multiple: ko.observable(true),
                        rows: 15,
                        options: ko.observableArray([]),
                        value: ko.observableArray([]),
                        optionsValue: 'id',
                        optionsText: 'itemName',
                        columns: [{ key: 'itemName', headerText: text('CPS007_9'), length: 15 }]
                    },
                    sortable: {
                        data: ko.observableArray([]),
                        outData: ko.observableArray([]),
                        isEnabled: ko.observable(true),
                        isEditable: ko.observable(0),
                        showColor: ko.observable(false),
                        beforeMove: (data, evt, ui) => {
                            let sindex: number = data.sourceIndex,
                                tindex: number = data.targetIndex,
                                direct: boolean = sindex > tindex,
                                item: IItemClassification = data.item,
                                source: Array<IItemClassification> = ko.unwrap(opts.sortable.data);


                            // cancel drop if two line is sibling
                            if (item.layoutItemType == IT_CLA_TYPE.SPER) {
                                let front = source[tindex - 1] || { layoutID: '-1', layoutItemType: -1 },
                                    replc = source[tindex] || { layoutID: '-1', layoutItemType: -1 },
                                    next = source[tindex + 1] || { layoutID: '-1', layoutItemType: -1 };

                                if (!direct) { // drag from top to below
                                    if ([next.layoutItemType, replc.layoutItemType].indexOf(IT_CLA_TYPE.SPER) > -1) {
                                        data.cancelDrop = true;
                                    }
                                } else {  // drag from below to top
                                    if ([replc.layoutItemType, front.layoutItemType].indexOf(IT_CLA_TYPE.SPER) > -1) {
                                        data.cancelDrop = true;
                                    }
                                }
                            } else { // if item is list or object
                                let front = source[sindex - 1] || { layoutID: '-1', layoutItemType: -1 },
                                    next = source[sindex + 1] || { layoutID: '-1', layoutItemType: -1 };

                                if (front.layoutItemType == IT_CLA_TYPE.SPER && next.layoutItemType == IT_CLA_TYPE.SPER) {
                                    data.cancelDrop = true;
                                }
                            }
                        },
                        removeItem: (data: IItemClassification, byItemId?: boolean) => {
                            let items: KnockoutObservableArray<IItemClassification> = opts.sortable.data;

                            if (!byItemId) { // remove item by classification id (virtual id)
                                items.remove((x: IItemClassification) => x.layoutID == data.layoutID);
                            } else if (data.listItemDf) { // remove item by item definition id
                                items.remove((x: IItemClassification) => x.listItemDf && x.listItemDf[0].id == data.listItemDf[0].id);
                            }

                            let source: Array<any> = ko.unwrap(items),
                                maps: Array<number> = _(source).map((x: IItemClassification, i) => (x.layoutItemType == IT_CLA_TYPE.SPER) ? i : -1)
                                    .filter(x => x != -1)
                                    .orderBy(x => x).value()

                            // remove next line if two line is sibling
                            _.each(maps, (x, i) => {
                                if (maps[i + 1] == x + 1) {
                                    items.remove((m: IItemClassification) => {
                                        let item: IItemClassification = ko.unwrap(items)[maps[i + 1]];
                                        return item && item.layoutItemType == IT_CLA_TYPE.SPER && item.layoutID == m.layoutID;
                                    });
                                }
                            });
                            return opts.sortable;
                        },
                        findExist: (ids: Array<string>) => {
                            let items: Array<IItemClassification> = opts.sortable.data();

                            if (!ids || !ids.length) {
                                return [];
                            }

                            // return items if it's exist in list
                            return _(items)
                                .map((x: IItemClassification) => x.listItemDf)
                                .flatten()
                                .filter((x: IItemDefinition) => x && ids.indexOf(x.id) > -1)
                                .value();
                        },
                        pushItem: (data: IItemClassification) => {
                            let items: KnockoutObservableArray<IItemClassification> = opts.sortable.data;

                            switch (data.layoutItemType) {
                                case IT_CLA_TYPE.ITEM:
                                    let item = _.find(ko.unwrap(items), (x: IItemClassification) => x.layoutItemType == IT_CLA_TYPE.ITEM && x.listItemDf[0].id == data.listItemDf[0].id);
                                    if (!item) {
                                        items.push(data);
                                        return true;
                                    } else {
                                        return false;
                                    }
                                case IT_CLA_TYPE.LIST:
                                    items.push(data);
                                    return true;
                                case IT_CLA_TYPE.SPER:
                                    // add line to list sortable
                                    let last: any = _.last(ko.unwrap(items));

                                    if (last && last.layoutItemType != IT_CLA_TYPE.SPER) {
                                        items.push(data);
                                        return true;
                                    } else {
                                        return false;
                                    }
                            }
                        },
                        pushItems: (defs: Array<IItemDefinition>, groupMode?: boolean) => {
                            let self = this,
                                services = self.services,
                                removeItems = (data: Array<IItemClassification>) => {
                                    if (data && data.length) {
                                        _.each(data, x => opts.sortable.removeItem(x, true));
                                    }
                                },
                                pushItems = (defs: Array<IItemDefinition>) => {
                                    _(defs)
                                        .filter(x => !x.isAbolition) // remove all item if it's abolition
                                        .each(def => {
                                            let item: IItemClassification = {
                                                layoutID: random(),
                                                dispOrder: -1,
                                                personInfoCategoryID: undefined,
                                                layoutItemType: IT_CLA_TYPE.ITEM,
                                                listItemDf: []
                                            };

                                            def.dispOrder = -1;
                                            item.listItemDf = [def];
                                            item.className = def.itemName;
                                            item.personInfoCategoryID = def.perInfoCtgId;

                                            // setitem
                                            if (def.itemTypeState.itemType == ITEM_TYPE.SET) {
                                                services.getItemsByIds(def.itemTypeState.items).done((defs: Array<IItemDefinition>) => {
                                                    if (defs && defs.length) {
                                                        _(defs)
                                                            .filter(x => !x.isAbolition)
                                                            .orderBy(x => x.dispOrder)
                                                            .each((x, i) => {
                                                                x.dispOrder = i + 1;
                                                                item.listItemDf.push(x);
                                                            });

                                                        opts.sortable.pushItem(item);
                                                    }
                                                });
                                            } else {
                                                opts.sortable.pushItem(item);
                                            }
                                        });
                                };

                            if (!defs || !defs.length) {
                                return;
                            }

                            // remove all item if it's cancelled by user
                            defs = _.filter(defs, x => !x.isAbolition);

                            // find duplicate items
                            let dups = opts.sortable.findExist(defs.map(x => x.id));

                            if (groupMode) {
                                if (dups && dups.length) {
                                    // 情報メッセージ（#Msg_204#,既に配置されている項目名,選択したグループ名）を表示する
                                    // Show Msg_204 if itemdefinition is exist
                                    info({
                                        messageId: 'Msg_204',
                                        messageParams: dups.map((x: IItemDefinition) => x.itemName)
                                    })
                                        .then(() => {
                                            removeItems(dups.map((x: IItemDefinition) => {
                                                return {
                                                    layoutID: random(),
                                                    dispOrder: -1,
                                                    personInfoCategoryID: undefined,
                                                    layoutItemType: IT_CLA_TYPE.ITEM,
                                                    listItemDf: [x]
                                                };
                                            }));
                                            pushItems(defs);
                                        });
                                } else {
                                    pushItems(defs);
                                }
                            } else {
                                let dupids = dups.map((x: IItemDefinition) => x.id),
                                    nodups = defs.filter((x: IItemDefinition) => dupids.indexOf(x.id) == -1);

                                if (dupids && dupids.length) {
                                    // 画面項目「選択可能項目一覧」で選択している項目が既に画面に配置されている場合
                                    // When the item selected in the screen item "selectable item list" has already been arranged on the screen
                                    alert({
                                        messageId: 'Msg_202',
                                        messageParams: dups.map((x: IItemDefinition) => x.itemName)
                                    });
                                }

                                pushItems(nodups);
                            }

                            // remove all item selected in list box
                            opts.listbox.value.removeAll();
                        }
                    }
                },
                ctrls = {
                    label: undefined,
                    radios: undefined,
                    combobox: undefined,
                    searchbox: undefined,
                    listbox: undefined,
                    button: undefined,
                    sortable: undefined,
                    line: undefined,
                },
                access = valueAccessor(),
                editable = (x: any) => {
                    if (typeof x == 'number') {
                        opts.sortable.isEditable(x);

                        if (x == 1) {
                            opts.sortable.isEnabled(true);
                        } else {
                            opts.sortable.isEnabled(false);
                        }

                        if (x == 2) {
                            $element.addClass("inputable");
                        }
                    } else {
                        opts.sortable.isEditable(0);

                        if (x) {
                            opts.sortable.isEnabled(true);
                        } else {
                            opts.sortable.isEnabled(false);
                        }
                    }
                },
                exceptConsts: Array<string> = [],
                // render primative value to viewContext
                primitiveConst = (x) => {
                    let dts = x.item,
                        constraint: any = {
                            itemName: x.itemName,
                            itemCode: x.itemDefId.replace(/[-_]/g, ""),
                            required: x.required// !!x.isRequired
                        };

                    if (dts) {
                        switch (dts.dataTypeValue) {
                            default:
                            case ITEM_SINGLE_TYPE.STRING:
                                constraint.valueType = "String";
                                constraint.maxLength = dts.stringItemLength || undefined;
                                constraint.stringExpression = '';

                                switch (dts.stringItemType) {
                                    default:
                                    case ITEM_STRING_TYPE.ANY:
                                        constraint.charType = 'Any';
                                        break;
                                    case ITEM_STRING_TYPE.ANYHALFWIDTH:
                                        constraint.charType = 'AnyHalfWidth';
                                        break;
                                    case ITEM_STRING_TYPE.ALPHANUMERIC:
                                        constraint.charType = 'AlphaNumeric';
                                        break;
                                    case ITEM_STRING_TYPE.NUMERIC:
                                        constraint.charType = 'Numeric';
                                        if (dts.decimalPart > 0) {
                                            constraint.valueType = "Decimal";
                                            constraint.mantissaMaxLength = dts.decimalPart;
                                        } else {
                                            constraint.valueType = "Integer";
                                        }
                                        break;
                                    case ITEM_STRING_TYPE.KANA:
                                        constraint.charType = 'Kana';
                                        break;
                                }
                                break;
                            case ITEM_SINGLE_TYPE.NUMERIC:
                                if (dts.decimalPart == 0) {
                                    constraint.valueType = "Integer";
                                } else {
                                    constraint.valueType = "Decimal";
                                    constraint.mantissaMaxLength = dts.decimalPart;
                                }

                                let max = (Math.pow(10, dts.integerPart) - Math.pow(10, -(dts.decimalPart || 0)));

                                constraint.charType = 'Numeric';
                                constraint.min = dts.numericItemMin || 0;
                                constraint.max = dts.numericItemMax || max;
                                break;
                            case ITEM_SINGLE_TYPE.DATE:
                                constraint.valueType = "Date";
                                constraint.max = parseTime(dts.max, true).format() || '';
                                constraint.min = parseTime(dts.min, true).format() || '';
                                break;
                            case ITEM_SINGLE_TYPE.TIME:
                                constraint.valueType = "Time";
                                constraint.max = parseTime(dts.max, true).format();
                                constraint.min = parseTime(dts.min, true).format();
                                break;
                            case ITEM_SINGLE_TYPE.TIMEPOINT:
                                constraint.valueType = "Clock";
                                constraint.max = parseTimeWidthDay(dts.timePointItemMax).shortText;
                                constraint.min = parseTimeWidthDay(dts.timePointItemMin).shortText;
                                break;
                            case ITEM_SINGLE_TYPE.SELECTION:
                                constraint.valueType = "Selection";
                                break;
                        }
                    }
                    return constraint;
                },
                primitiveConsts = () => {
                    let constraints = _(ko.unwrap(opts.sortable.data))
                        .map((x: any) => _.has(x, "items") && ko.toJS(x.items))
                        .flatten()
                        .flatten()
                        .filter((x: any) => _.has(x, "item") && !_.isEqual(x.item, {}))
                        .map((x: any) => primitiveConst(x))
                        .filter((x: any) => exceptConsts.indexOf(x.itemCode) == -1)
                        .value();

                    if (constraints && constraints.length) {
                        exceptConsts = [];
                        writeConstraints(constraints);
                    }
                },
                scrollDown = () => {
                    // remove old selected items
                    $(ctrls.sortable)
                        .find('.form-group.item-classification')
                        .removeClass('selected');
                    // scroll to bottom
                    $(ctrls.sortable).scrollTop($(ctrls.sortable).prop("scrollHeight"));
                    // select lastest item
                    setTimeout(() => {
                        $(ctrls.sortable)
                            .find('.form-group.item-classification:last-child')
                            .addClass('selected');
                    }, 0);
                };

            // add style to <head> on first run
            if (!$('#layout_style').length) {
                $('head').append(self.style);
            }

            $element
                .append(self.tmp)
                .addClass('ntsControl layout-control');



            // binding callback function to control
            if (access.callback) {
                $.extend(opts, { callback: access.callback });
            }

            // binding output data value 
            if (access.outData) {
                $.extend(opts.sortable, { outData: access.outData });
            }

            // change color text
            if (access.showColor) {
                $.extend(opts.sortable, { showColor: access.showColor });
            }

            // validate editAble
            if (ko.unwrap(access.editAble) != undefined) {
                if (ko.isObservable(access.editAble)) {
                    access.editAble.subscribe(editable);
                    access.editAble.valueHasMutated();
                } else {
                    editable(access.editAble);
                }
            }

            // sortable
            opts.sortable.isEnabled.subscribe(x => {
                if (!x) {
                    $element
                        .addClass('readonly')
                        .removeClass('dragable');

                    $element.find('.left-area, .add-buttons, #cps007_btn_line').hide();
                } else {
                    $element
                        .addClass('dragable')
                        .removeClass('readonly');

                    $element.find('.left-area, .add-buttons, #cps007_btn_line').show();
                }
            });
            opts.sortable.isEnabled.valueHasMutated();

            // inputable (editable)
            opts.sortable.isEditable.subscribe(x => {
                let data: Array<IItemClassification> = ko.unwrap(opts.sortable.data);
                _.each(data, icl => {
                    _.each(icl.listItemDf, (e: IItemDefinition) => {
                        if (e.itemTypeState && e.itemTypeState.dataTypeState) {
                            let state = e.itemTypeState.dataTypeState;
                            if (x == 2) {
                                if (state.editable && ko.isObservable(state.editable)) {
                                    state.editable(true);
                                } else {
                                    state.editable = ko.observable(true);
                                }

                                if (state.readonly && ko.isObservable(state.readonly)) {
                                    state.readonly(false);
                                } else {
                                    state.readonly = ko.observable(false);
                                }
                            } else {
                                if (state.editable && ko.isObservable(state.editable)) {
                                    state.editable(false);
                                } else {
                                    state.editable = ko.observable(false);
                                }

                                if (state.readonly && ko.isObservable(state.readonly)) {
                                    state.readonly(true);
                                } else {
                                    state.readonly = ko.observable(true);
                                }
                            }
                            state.editable.valueHasMutated();
                        }
                    });
                });
            });
            opts.sortable.isEditable.valueHasMutated();

            // extend option
            $.extend(opts.comboxbox, { enable: ko.computed(() => !opts.radios.value()) });

            $.extend(opts.searchbox, {
                items: ko.computed(opts.listbox.options),
                selected: opts.listbox.value
            });

            // extend data of sortable with valueAccessor data prop
            $.extend(opts.sortable, { data: access.data });
            opts.sortable.data.subscribe((data: Array<IItemClassification>) => {
                // remove all sibling sperators
                let maps: Array<number> = _(data)
                    .map((x, i) => (x.layoutItemType == 2) ? i : -1)
                    .filter(x => x != -1).value();

                _.each(maps, (t, i) => {
                    if (maps[i + 1] == t + 1) {
                        _.remove(data, (m: IItemClassification) => {
                            let item: IItemClassification = data[maps[i + 1]];
                            return item && item.layoutItemType == 2 && item.layoutID == m.layoutID;
                        });
                    }
                });

                opts.sortable.isEditable.valueHasMutated();
                _.each(data, (x, i) => {
                    // define common function for init new item value
                    let isStr = (item: any) => {
                        if (!item) {
                            return false;
                        }

                        switch (item.dataTypeValue) {
                            default:
                                return false;
                            case ITEM_SINGLE_TYPE.STRING:
                            case ITEM_SINGLE_TYPE.SELECTION:
                                return true;
                        }
                    },
                        modifitem = (def: any, item?: any) => {
                            if (!item) {
                                item = {};
                            }

                            def.itemCode = _.has(def, "itemCode") && def.itemCode || item.itemCode;
                            def.itemName = _.has(def, "itemName") && def.itemName || item.itemName;
                            def.itemDefId = _.has(def, "itemDefId") && def.itemDefId || item.id;
                            def.required = _.has(def, "required") && def.required || !!item.isRequired;

                            def.categoryCode = _.has(def, "categoryCode") && def.categoryCode || '';

                            def.lstComboBoxValue = _.has(def, "lstComboBoxValue") ? def.lstComboBoxValue : [];

                            def.hidden = _.has(def, "actionRole") ? def.actionRole == ACTION_ROLE.HIDDEN : true;
                            def.readonly = _.has(def, "actionRole") ? def.actionRole == ACTION_ROLE.VIEW_ONLY : !!opts.sortable.isEnabled();
                            def.editable = _.has(def, "actionRole") ? def.actionRole == ACTION_ROLE.EDIT : !!opts.sortable.isEditable();
                            def.showColor = ko.isObservable(opts.sortable.showColor) ? opts.sortable.showColor : ko.observable(opts.sortable.showColor);

                            def.type = _.has(def, "type") ? def.type : (item.itemTypeState || <any>{}).itemType;
                            def.item = _.has(def, "item") ? def.item : $.extend({}, ((item || <any>{}).itemTypeState || <any>{}).dataTypeState || {});

                            def.value = ko.isObservable(def.value) ? def.value : ko.observable(isStr(def.item) && def.value ? String(def.value) : def.value);
                            def.value.subscribe(x => {
                                let inputs = [],
                                    proc = function(data: any): any {
                                        if (!data.item) {
                                            return {
                                                value: String(data.value),
                                                typeData: 1
                                            };
                                        }

                                        switch (data.item.dataTypeValue) {
                                            default:
                                            case ITEM_SINGLE_TYPE.STRING:
                                                return {
                                                    value: data.value ? String(data.value) : undefined,
                                                    typeData: 1
                                                };
                                            case ITEM_SINGLE_TYPE.TIME:
                                            case ITEM_SINGLE_TYPE.NUMERIC:
                                            case ITEM_SINGLE_TYPE.TIMEPOINT:
                                                return {
                                                    value: data.value ? String(data.value).replace(/:/g, '') : undefined,
                                                    typeData: 2
                                                };
                                            case ITEM_SINGLE_TYPE.DATE:
                                                return {
                                                    value: data.value ? moment.utc(data.value).format("YYYY/MM/DD") : undefined,
                                                    typeData: 3
                                                };
                                            case ITEM_SINGLE_TYPE.SELECTION:
                                                switch (data.item.referenceType) {
                                                    case ITEM_SELECT_TYPE.ENUM:
                                                        return {
                                                            value: data.value ? String(data.value) : undefined,
                                                            typeData: 2
                                                        };
                                                    case ITEM_SELECT_TYPE.CODE_NAME:
                                                        return {
                                                            value: data.value ? String(data.value) : undefined,
                                                            typeData: 1
                                                        };
                                                    case ITEM_SELECT_TYPE.DESIGNATED_MASTER:
                                                        let value: number = data.value ? Number(data.value) : undefined;
                                                        if (value) {
                                                            if (String(value) == String(data.value)) {
                                                                return {
                                                                    value: data.value ? String(data.value) : undefined,
                                                                    typeData: 2
                                                                };
                                                            } else {
                                                                return {
                                                                    value: data.value ? String(data.value) : undefined,
                                                                    typeData: 1
                                                                };
                                                            }
                                                        } else {
                                                            return {
                                                                value: data.value ? String(data.value) : undefined,
                                                                typeData: 1
                                                            };
                                                        }
                                                }
                                        }
                                    };

                                _(opts.sortable.data())
                                    .filter(x => _.has(x, "items") && _.isFunction(x.items))
                                    .map(x => ko.toJS(x.items))
                                    .flatten()
                                    .filter((x: any) => _.has(x, "item") && !!x.item)
                                    .map((x: any) => {
                                        if (_.isArray(x)) {
                                            return x.map((m: any) => {
                                                let data = proc(m);
                                                return {
                                                    recordId: m.recordId,
                                                    categoryCd: m.categoryCode,
                                                    definitionId: m.itemDefId,
                                                    itemCode: m.itemCode,
                                                    value: data.value,
                                                    'type': data.typeData
                                                }
                                            });
                                        } else {
                                            let data = proc(x);
                                            return {
                                                recordId: x.recordId,
                                                categoryCd: x.categoryCode,
                                                definitionId: x.itemDefId,
                                                itemCode: x.itemCode,
                                                value: data.value,
                                                'type': data.typeData
                                            };
                                        }
                                    })
                                    .groupBy((x: any) => x.categoryCd)
                                    .each(x => {
                                        if (_.isArray(_.first(x))) {
                                            _.each(x, k => {
                                                let group = _.groupBy(k, (m: any) => !!m.recordId);
                                                _.each(group, g => {
                                                    let first: any = _.first(g);
                                                    inputs.push({
                                                        recordId: first.recordId,
                                                        categoryCd: first.categoryCd,
                                                        items: g.map(m => {
                                                            return {
                                                                definitionId: m.definitionId,
                                                                itemCode: m.itemCode,
                                                                value: m.value,
                                                                'type': m.type
                                                            };
                                                        })
                                                    });
                                                });
                                            });
                                        } else {
                                            let group = _.groupBy(x, (m: any) => !!m.recordId);
                                            _.each(group, g => {
                                                let first: any = _.first(g);
                                                inputs.push({
                                                    recordId: first.recordId,
                                                    categoryCd: first.categoryCd,
                                                    items: g.map(m => {
                                                        return {
                                                            definitionId: m.definitionId,
                                                            itemCode: m.itemCode,
                                                            value: m.value,
                                                            'type': m.type
                                                        };
                                                    })
                                                });
                                            });
                                        }
                                    });
                                // change value
                                opts.sortable.outData(inputs);
                            });
                            def.value.valueHasMutated();
                        };

                    x.dispOrder = i + 1;
                    x.layoutID = random();

                    if (!x.items) {
                        x.items = ko.observableArray([]);
                    }

                    if (!ko.isObservable(x.items)) {
                        if (!_.isArray(x.items)) {
                            x.items = ko.observableArray([]);
                        } else {
                            x.items = ko.observableArray(x.items);
                        }
                    }

                    if (x.listItemDf) {
                        switch (x.layoutItemType) {
                            case IT_CLA_TYPE.ITEM:
                                _.each((x.listItemDf || []), (item, i) => {
                                    let def = _.find(x.items(), (m: any) => m.itemDefId == item.id);
                                    if (!def) {
                                        def = {
                                            index: i,
                                            categoryCode: x.categoryCode || x.personInfoCategoryID, // miss categoryCode;
                                            itemCode: item.itemCode,
                                            itemName: item.itemName,
                                            itemDefId: item.id,
                                            value: undefined
                                        };
                                        x.items.push(def);
                                    } else {
                                        def.index = i;
                                    }

                                    modifitem(def, item);
                                });
                                break;
                            case IT_CLA_TYPE.LIST:
                                // define row number
                                let rn = _.map(ko.toJS(x.items), x => x).length;
                                if (rn < 3) {
                                    rn = 3;
                                }
                                _.each(_.range(rn), i => {
                                    let row = x.items()[i];

                                    if (!row || !_.isArray(row)) {
                                        row = [];
                                    }

                                    x.items()[i] = row;

                                    _.each((x.listItemDf || []), (item, j) => {
                                        let def = _.find(row, (m: any) => m.itemDefId == item.id);

                                        if (!def) {
                                            def = {
                                                index: j,
                                                categoryCode: x.categoryCode || x.personInfoCategoryID, // miss categoryCode;
                                                itemCode: item.itemCode,
                                                itemName: item.itemName,
                                                itemDefId: item.id,
                                                value: undefined
                                            };
                                            row.push(def);
                                        } else {
                                            def.index = j;
                                        }
                                        modifitem(def, item);
                                    });
                                });
                                break;
                            case IT_CLA_TYPE.SPER:
                                x.items = undefined;
                                break;
                        }
                    }

                    switch (x.layoutItemType) {
                        case IT_CLA_TYPE.ITEM:
                            _.each((x.items()), (def, i) => {
                                def.index = i;
                                modifitem(def);
                            });
                            break;
                        case IT_CLA_TYPE.LIST:
                            // define row number
                            let rn = _.map(ko.toJS(x.items), x => x).length;

                            _.each(_.range(rn), i => {
                                let row = x.items()[i];

                                if (!row || !_.isArray(row)) {
                                    row = [];
                                }

                                x.items()[i] = row;

                                _.each(row, (def, j) => {
                                    def.index = j;
                                    modifitem(def);
                                });
                            });
                            break;
                        case IT_CLA_TYPE.SPER:
                            x.items = undefined;
                            break;
                    }

                    // validation set item range
                    switch (x.layoutItemType) {
                        case IT_CLA_TYPE.ITEM:
                            _.each((x.items()), (def, i) => {
                                if (_.has(def, "item") && !_.isNull(def.item)) {
                                    // validate date range
                                    switch (def.item.dataTypeValue) {
                                        case ITEM_SINGLE_TYPE.DATE:
                                            if (def.index == 0) {
                                                def.endDate = ko.observable();
                                                def.startDate = ko.observable();
                                            }

                                            if (def.index == 1) {
                                                let next = x.items()[2] || { item: {}, value: () => ko.observable() };
                                                if (next.item.dataTypeValue == ITEM_SINGLE_TYPE.DATE
                                                    && _.has(next, "value")
                                                    && ko.isObservable(next.value)) {

                                                    def.endDate = ko.computed(() => {
                                                        return moment.utc(ko.toJS(next.value) || '9999/12/31').add(ko.toJS(next.value) ? -1 : 0, "days").toDate();
                                                    });
                                                    def.startDate = ko.observable();

                                                    next.endDate = ko.observable();
                                                    next.startDate = ko.computed(() => {
                                                        return moment.utc(ko.toJS(def.value) || '1900/01/01').add(ko.toJS(def.value) ? 1 : 0, "days").toDate();
                                                    });
                                                }
                                            }

                                            if (def.index == 2) {
                                                /*let prev = x.items()[1] || { value: () => ko.observable() };
                                                if (prev.item.dataTypeValue == ITEM_SINGLE_TYPE.DATE
                                                    && _.has(prev, "value")
                                                    && ko.isObservable(prev.value)) {

                                                    prev.endDate = ko.computed(() => {
                                                        return moment.utc(ko.toJS(def.value)).add(-1, "days").toDate();
                                                    });
                                                    prev.startDate = ko.observable();

                                                    def.endDate = ko.observable();
                                                    def.startDate = ko.computed(() => {
                                                        return moment.utc(ko.toJS(prev.value)).add(1, "days").toDate();
                                                    });
                                                }*/

                                                if (def.ctgType == IT_CAT_TYPE.CONTINU) {
                                                    if (def.value() == '9999/12/31') {
                                                        def.value('');
                                                    }
                                                }
                                            }

                                            if (!_.has(def, "endDate")) {
                                                def.endDate = ko.observable();
                                            }

                                            if (!_.has(def, "startDate")) {
                                                def.startDate = ko.observable();
                                            }
                                            break;
                                        case ITEM_SINGLE_TYPE.TIME:
                                            if (def.index == 1) {
                                                def.value.subscribe(v => {
                                                    let next = x.items()[2] || { value: () => ko.observable(undefined) };
                                                    if (next.item && next.item.dataTypeValue == ITEM_SINGLE_TYPE.TIME
                                                        && _.has(next, "value")
                                                        && ko.isObservable(next.value)) {
                                                        let clone = _.cloneDeep(next);
                                                        clone.item.min = def.value() + 1;

                                                        let primi = primitiveConst(v ? clone : next);

                                                        exceptConsts.push(primi.itemCode);
                                                        writeConstraint(primi.itemCode, primi);
                                                    }
                                                });
                                                def.value.valueHasMutated();
                                            }

                                            if (def.index == 2) {
                                                def.value.subscribe(v => {
                                                    let prev = x.items()[1] || { value: () => ko.observable(undefined) };
                                                    if (prev.item && prev.item.dataTypeValue == ITEM_SINGLE_TYPE.TIME
                                                        && _.has(prev, "value")
                                                        && ko.isObservable(prev.value)) {
                                                        let clone = _.cloneDeep(prev);
                                                        clone.item.max = def.value() - 1;

                                                        let primi = primitiveConst(v ? clone : prev);

                                                        exceptConsts.push(primi.itemCode);
                                                        writeConstraint(primi.itemCode, primi);
                                                    }
                                                });
                                                def.value.valueHasMutated();
                                            }
                                            break;
                                        case ITEM_SINGLE_TYPE.TIMEPOINT:
                                            if (def.index == 1) {
                                                def.value.subscribe(v => {
                                                    let next = x.items()[2] || { value: () => ko.observable(undefined) };
                                                    if (next.item && next.item.dataTypeValue == ITEM_SINGLE_TYPE.TIMEPOINT
                                                        && _.has(next, "value")
                                                        && ko.isObservable(next.value)) {
                                                        let clone = _.cloneDeep(next);
                                                        clone.item.timePointItemMin = def.value() + 1;

                                                        let primi = primitiveConst(v ? clone : next);

                                                        exceptConsts.push(primi.itemCode);
                                                        writeConstraint(primi.itemCode, primi);
                                                    }
                                                });
                                                def.value.valueHasMutated();
                                            }
                                            if (def.index == 2) {
                                                def.value.subscribe(v => {
                                                    let prev = x.items()[1] || { value: () => ko.observable(undefined) };
                                                    if (prev.item && prev.item.dataTypeValue == ITEM_SINGLE_TYPE.TIMEPOINT
                                                        && _.has(prev, "value")
                                                        && ko.isObservable(prev.value)) {
                                                        let clone = _.cloneDeep(prev);
                                                        clone.item.timePointItemMax = def.value() - 1;

                                                        let primi = primitiveConst(v ? clone : prev);

                                                        exceptConsts.push(primi.itemCode);
                                                        writeConstraint(primi.itemCode, primi);
                                                    }
                                                });
                                                def.value.valueHasMutated();
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                        case IT_CLA_TYPE.LIST:
                            // define row number
                            let rn = _.map(ko.toJS(x.items), x => x).length;

                            _.each(_.range(rn), i => {
                                let row = x.items()[i];

                                if (!row || !_.isArray(row)) {
                                    row = [];
                                }

                                x.items()[i] = row;

                                _.each(row, (def, j) => {
                                    // call some validate function at here
                                });
                            });
                            break;
                        case IT_CLA_TYPE.SPER:
                            x.items = undefined;
                            break;
                    }
                });
                // clear all error on switch new layout
                clearError();

                // write primitive constraints to viewContext
                primitiveConsts();
            });
            opts.sortable.data.valueHasMutated();

            // get all id of controls
            $.extend(ctrls, {
                label: $element.find('#cps007_lbl_control')[0],
                radios: $element.find('#cps007_rdg_control')[0],
                combobox: $element.find('#cps007_cbx_control')[0],
                searchbox: $element.find('#cps007_sch_control')[0],
                listbox: $element.find('#cps007_lst_control')[0],
                button: $element.find('#cps007_btn_add')[0],
                sortable: $element.find('#cps007_srt_control')[0],
                line: $element.find('#cps007_btn_line')[0]
            });

            // change text of label
            $(ctrls.label).text(text('CPS007_5'));
            $(ctrls.line).text(text('CPS007_19'));
            $(ctrls.button).text(text('CPS007_11'));


            // subscribe handle
            // load combobox data
            opts.radios.value.subscribe(mode => {
                // remove all data in listbox
                opts.listbox.options.removeAll();

                if (opts.sortable.isEditable() != 0) {
                    return;
                }

                if (mode == CAT_OR_GROUP.CATEGORY) { // get item by category
                    opts.comboxbox.options.removeAll();
                    services.getCats().done((data: any) => {
                        if (data && data.categoryList && data.categoryList.length) {
                            let cats = _.filter(data.categoryList, (x: IItemCategory) => !x.isAbolition && !x.categoryParentCode);

                            let dfds: Array<JQueryDeferred<any>> = [];

                            _.each(cats, cat => {
                                let dfd = $.Deferred<any>();
                                services.getItemByCat(cat.id).done((data: Array<IItemDefinition>) => {
                                    let items = _.filter(_.flatten(data) as Array<IItemDefinition>, x => !x.isAbolition);
                                    if (items.length) {
                                        dfd.resolve(cat);
                                    } else {
                                        dfd.resolve(false);
                                    }
                                }).fail(x => dfd.reject(false));

                                dfds.push(dfd);
                            });

                            // push all category to combobox when done
                            $.when.apply($, dfds).then(function() {
                                let items: Array<IItemCategory> = _.filter(_.flatten(arguments), x => !!x);
                                if (items && items.length) {
                                    opts.comboxbox.options(items);
                                } else {
                                    // show message if hasn't any category
                                    if (ko.toJS(opts.sortable.isEnabled)) {
                                        alert(text('Msg_288')).then(opts.callback);
                                    }
                                }
                            });
                        } else {
                            // show message if hasn't any category
                            if (ko.toJS(opts.sortable.isEnabled)) {
                                alert(text('Msg_288')).then(opts.callback);
                            }
                        }
                    });
                } else { // get item by group
                    // change text in add-button to [グループを追加　→]
                    $(ctrls.button).text(text('CPS007_20'));
                    services.getGroups().done((data: Array<IItemGroup>) => {
                        // prevent if slow networks
                        if (opts.radios.value() != CAT_OR_GROUP.GROUP) {
                            return;
                        }
                        if (data && data.length) {
                            // map Array<IItemGroup> to Array<IItemDefinition>
                            // 「個人情報項目定義」が取得できなかった「項目グループ」以外を、画面項目「グループ一覧」に表示する
                            // remove groups when it does not contains any item definition (by hql)
                            _.each(data, group => {
                                opts.listbox.options.push({
                                    id: group.personInfoItemGroupID,
                                    itemName: group.fieldGroupName,
                                    itemTypeState: undefined,
                                    dispOrder: group.dispOrder
                                });
                            });
                        }
                    });
                }

                // remove listbox data
                opts.listbox.value.removeAll();
            });
            opts.radios.value.valueHasMutated();

            // load listbox data
            opts.comboxbox.value.subscribe(cid => {
                if (opts.sortable.isEditable() != 0) {
                    return;
                }

                if (cid) {
                    let data: Array<IItemCategory> = ko.toJS(opts.comboxbox.options),
                        item: IItemCategory = _.find(data, x => x.id == cid);

                    // remove all item in list item for init new data
                    opts.listbox.options.removeAll();
                    if (item) {
                        switch (item.categoryType) {
                            case IT_CAT_TYPE.SINGLE:
                            case IT_CAT_TYPE.CONTINU:
                            case IT_CAT_TYPE.CONTINUWED:
                            case IT_CAT_TYPE.NODUPLICATE:
                                $(ctrls.button).text(text('CPS007_11'));
                                services.getItemByCat(item.id).done((data: Array<IItemDefinition>) => {
                                    // prevent if slow networks
                                    if (opts.radios.value() != CAT_OR_GROUP.CATEGORY) {
                                        return;
                                    }
                                    if (data && data.length) {
                                        // get all item defined in category with abolition = 0
                                        // order by dispOrder asc
                                        data = _(data)
                                            .filter(m => !m.isAbolition)
                                            .filter(f => {
                                                if (location.href.indexOf('/view/cps/007/a/') > -1) {
                                                    if (item.id === "COM1_00000000000000000000000_CS00002") {
                                                        return f.id !== "COM1_000000000000000_CS00002_IS00003";
                                                    }

                                                    if (item.id === "COM1_00000000000000000000000_CS00003") {
                                                        return f.id !== "COM1_000000000000000_CS00003_IS00020";
                                                    }
                                                }

                                                return true;
                                            })
                                            .orderBy(m => m.dispOrder).value();

                                        opts.listbox.options(data);
                                        opts.listbox.value.removeAll();
                                    }
                                });
                                break;
                            case IT_CAT_TYPE.MULTI:
                            case IT_CAT_TYPE.DUPLICATE:
                                $(ctrls.button).text(text('CPS007_10'));

                                // create item for listbox
                                // itemname: categoryname + text('CPS007_21')
                                let def: IItemDefinition = {
                                    id: item.id,
                                    itemName: item.categoryName + text('CPS007_21'),
                                    itemTypeState: undefined, // item.categoryType
                                };
                                opts.listbox.value.removeAll();
                                opts.listbox.options.push(def);
                                break;
                        }
                    } else {
                        // select undefine
                        opts.listbox.value.removeAll();
                    }
                }
            });

            opts.listbox.options.subscribe(x => {
                if (!x || !x.length) {
                    $(ctrls.button).prop('disabled', true);
                } else {
                    $(ctrls.button).prop('disabled', false);
                }
            });

            // disable group if not has any group
            services.getGroups().done((data: Array<any>) => {
                if (!data || !data.length) {
                    opts.radios.options().filter(x => x.id == CAT_OR_GROUP.GROUP).forEach(x => x.enable(false));
                }
            });

            // events handler
            $(ctrls.line).on('click', function() {
                let item: IItemClassification = {
                    layoutID: random(),
                    dispOrder: -1,
                    personInfoCategoryID: undefined,
                    listItemDf: undefined,
                    layoutItemType: IT_CLA_TYPE.SPER
                };

                // add line to list sortable
                opts.sortable.pushItem(item);
                scrollDown();
            });

            $(ctrls.sortable)
                .on('click', (evt) => {
                    setTimeout(() => {
                        $(ctrls.sortable)
                            .find('.form-group.item-classification')
                            .removeClass('selected');
                    }, 0);
                })
                .on('mouseover', '.form-group.item-classification', (evt) => {
                    $(evt.target)
                        .removeClass('selected');
                });


            $(ctrls.button).on('click', () => {
                // アルゴリズム「項目追加処理」を実行する
                // Execute the algorithm "項目追加処理"
                let ids: Array<string> = ko.toJS(opts.listbox.value);

                if (!ids || !ids.length) {
                    alert({ messageId: 'Msg_203' });
                    return;
                }

                // category mode
                if (ko.unwrap(opts.radios.value) == CAT_OR_GROUP.CATEGORY) {
                    let cid: string = ko.toJS(opts.comboxbox.value),
                        cats: Array<IItemCategory> = ko.toJS(opts.comboxbox.options),
                        cat: IItemCategory = _.find(cats, x => x.id == cid);

                    if (cat) {
                        // multiple items
                        if ([IT_CAT_TYPE.MULTI, IT_CAT_TYPE.DUPLICATE].indexOf(cat.categoryType) > -1) {
                            // 画面項目「カテゴリ選択」で選択している情報が、既に配置されているかチェックする
                            // if category is exist in sortable box.
                            let _catcls = _.find(ko.unwrap(opts.sortable.data), (x: IItemClassification) => x.personInfoCategoryID == cat.id);
                            if (_catcls) {
                                alert({
                                    messageId: 'Msg_202',
                                    messageParams: [cat.categoryName]
                                });
                                return;
                            }

                            setShared('CPS007B_PARAM', { category: cat, chooseItems: [] });
                            modal('../../007/b/index.xhtml').onClosed(() => {
                                let data = getShared('CPS007B_VALUE') || { category: undefined, chooseItems: [] };

                                if (data.category && data.category.id && data.chooseItems && data.chooseItems.length) {
                                    services.getCat(data.category.id).done((_cat: IItemCategory) => {

                                        if (!_cat || !!_cat.isAbolition) {
                                            return;
                                        }

                                        let ids: Array<string> = data.chooseItems.map(x => x.id);
                                        services.getItemsByIds(ids).done((_data: Array<IItemDefinition>) => {
                                            // sort againt by ids
                                            _.each(_data, x => x.dispOrder = ids.indexOf(x.id) + 1);

                                            _data = _.orderBy(_data, x => x.dispOrder);

                                            let item: IItemClassification = {
                                                layoutID: random(),
                                                dispOrder: -1,
                                                className: _cat.categoryName,
                                                personInfoCategoryID: _cat.id,
                                                layoutItemType: IT_CLA_TYPE.LIST,
                                                listItemDf: _data
                                            };
                                            opts.sortable.data.push(item);
                                            opts.listbox.value.removeAll();
                                            scrollDown();
                                        });
                                    });
                                }
                            });
                        }
                        else { // set or single item
                            let idefid: Array<string> = ko.toJS(opts.listbox.value),
                                idefs = _.filter(ko.toJS(opts.listbox.options), (x: IItemDefinition) => idefid.indexOf(x.id) > -1);

                            if (idefs && idefs.length) {
                                services.getItemsByIds(idefs.map(x => x.id)).done((defs: Array<IItemDefinition>) => {
                                    if (defs && defs.length) {
                                        opts.sortable.pushItems(defs, false);
                                        scrollDown();
                                    }
                                });
                            }
                        }
                    }
                } else { // group mode
                    let ids: Array<string> = ko.toJS(opts.listbox.value),
                        groups: Array<any> = ko.unwrap(opts.listbox.options),
                        filters: Array<any> = _.filter(groups, x => ids.indexOf(x.id) > -1);

                    if (filters && filters.length) {
                        let dfds: Array<JQueryDeferred<any>> = [];

                        _.each(filters, group => {
                            let dfd = $.Deferred<any>();
                            services.getItemByGroup(group.id).done((data: Array<IItemDefinition>) => {
                                dfd.resolve(data);
                            }).fail(x => dfd.reject(false));

                            dfds.push(dfd);
                        });

                        // push all item to sortable when done
                        $.when.apply($, dfds).then(function() {
                            // remove all item if it's abolition
                            let items = _(_.flatten(arguments) as Array<IItemDefinition>)
                                .filter(x => !x.isAbolition)
                                .orderBy(x => x.dispOrder)
                                .value();

                            if (items && items.length) {
                                opts.sortable.pushItems(items, true);
                                scrollDown();
                            }
                        });
                    }
                }
            });

            // set data controls and option to element
            $element.data('options', opts);
            $element.data('controls', ctrls);
        }

        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {

            // call private constructor
            this._constructor(element, valueAccessor);

            let $element = $(element),
                opts = $element.data('options'),
                ctrls = $element.data('controls');

            ko.bindingHandlers['ntsFormLabel'].init(ctrls.label, function() {
                return {};
            }, allBindingsAccessor, viewModel, bindingContext);
            // init radio box group
            ko.bindingHandlers['ntsRadioBoxGroup'].init(ctrls.radios, function() {
                return opts.radios;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsComboBox'].init(ctrls.combobox, function() {
                return opts.comboxbox;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsSearchBox'].init(ctrls.searchbox, function() {
                return opts.searchbox;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsListBox'].init(ctrls.listbox, function() {
                return opts.listbox;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsSortable'].init(ctrls.sortable, function() {
                return opts.sortable;
            }, allBindingsAccessor, viewModel, bindingContext);

            // Also tell KO *not* to bind the descendants itself, otherwise they will be bound twice
            return { controlsDescendantBindings: true };
        }

        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let self = this,
                $element = $(element),
                opts = $element.data('options'),
                ctrls = $element.data('controls');

            ko.bindingHandlers['ntsFormLabel'].update(ctrls.label, function() {
                return {};
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsRadioBoxGroup'].update(ctrls.radios, function() {
                return opts.radios;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsComboBox'].update(ctrls.combobox, function() {
                return opts.comboxbox;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsSearchBox'].update(ctrls.searchbox, function() {
                return opts.searchbox;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsListBox'].update(ctrls.listbox, function() {
                return opts.listbox;
            }, allBindingsAccessor, viewModel, bindingContext);

            ko.bindingHandlers['ntsSortable'].update(ctrls.sortable, function() {
                return opts.sortable;
            }, allBindingsAccessor, viewModel, bindingContext);

            // Also tell KO *not* to bind the descendants itself, otherwise they will be bound twice
            return { controlsDescendantBindings: true };
        }
    }

    interface IItemCategory {
        id: string;
        categoryName: string;
        categoryType: IT_CAT_TYPE;
        isAbolition?: number;
        categoryParentCode?: string;
    }

    interface IItemGroup {
        personInfoItemGroupID: string;
        fieldGroupName: string;
        dispOrder: number;
    }

    interface IItemClassification {
        layoutID?: string;
        dispOrder?: number;
        categoryCode?: string;
        className?: string; // only for display if classification is set or duplication item
        personInfoCategoryID?: string;
        layoutItemType: IT_CLA_TYPE;
        listItemDf: Array<IItemDefinition>; // layoutItemType == 0 ? [1] : layoutItemType == 1 ? [A, B, C] : undefined;
        items?: any; // [{value: }] || [{c: 1, value: }, {c: 2, value: }], [[{r: 1, c: 1, value: }, {}], [{}, {}], [{}, {}], [{}, {}]] , undefined
        values?: any;
    }

    interface IItemDefinition {
        id: string;
        dispOrder?: number;
        perInfoCtgId?: string;
        itemCode?: string;
        itemName: string;
        isAbolition?: number;
        isFixed?: number;
        isRequired?: number;
        systemRequired?: number;
        requireChangable?: number;
        itemTypeState: IItemTypeState;
    }

    interface IItemDefinitionValue {
        id: string;
        row?: number;
        col?: number;
        itemCode?: string;
        itemName?: string;
        itemValue: any;
    }

    interface IItemTypeState extends ISetItem, ISingleItem {
        itemType: ITEM_TYPE; // Set || Single
    }

    interface ISetItem {
        items?: Array<string>; // Set ids value
    }

    interface ISingleItem {
        dataTypeState?: IItemDefinitionData // Single item value
    }

    interface IItemDefinitionData extends IItemTime, IItemDate, IItemString, IItemTimePoint, IItemNumeric, IItemSelection {
        dataTypeValue: ITEM_SINGLE_TYPE; // type of value of item
        editable?: KnockoutObservable<boolean>;
        readonly?: KnockoutObservable<boolean>;
    }

    interface IItemTime {
        min?: number;
        max?: number;
    }

    interface IItemDate {
        dateItemType?: DateType;
    }

    interface IItemString {
        stringItemDataType?: ITEM_STRING_DTYPE;
        stringItemLength?: number;
        stringItemType?: ITEM_STRING_TYPE;
    }

    interface IItemTimePoint {
        timePointItemMin?: number;
        timePointItemMax?: number;
    }

    interface IItemNumeric {
        numericItemMinus?: number;
        numericItemAmount?: number;
        integerPart?: number;
        decimalPart?: number;
        numericItemMin?: number;
        numericItemMax?: number;
    }

    interface IItemSelection extends IItemMasterSelection, IItemEnumSelection, IItemCodeNameSelection {
        referenceType?: ITEM_SELECT_TYPE;
    }

    interface IItemMasterSelection {
        masterType?: string;
    }

    interface IItemEnumSelection {
        typeCode?: string;
    }

    interface IItemCodeNameSelection {
        enumName?: string;
    }

    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = <any>"ITEM", // single item
        LIST = <any>"LIST", // list item
        SPER = <any>"SeparatorLine" // line item
    }

    // define ITEM_CATEGORY_TYPE
    enum IT_CAT_TYPE {
        SINGLE = 1, // Single info
        MULTI = 2, // Multi info
        CONTINU = 3, // Continuos history
        NODUPLICATE = 4, //No duplicate history
        DUPLICATE = 5, // Duplicate history
        CONTINUWED = 6 // Continuos history with end date
    }

    // defined CATEGORY or GROUP mode
    enum CAT_OR_GROUP {
        CATEGORY = 0, // category mode
        GROUP = 1 // group mode
    }

    // define ITEM_TYPE is set or single item
    enum ITEM_TYPE {
        SET = 1, // List item info
        SINGLE = 2 // Single item info
    }

    // define ITEM_SINGLE_TYPE
    // type of item if it's single item
    enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6
    }

    // define ITEM_STRING_DATA_TYPE
    enum ITEM_STRING_DTYPE {
        FIXED_LENGTH = 1, // fixed length
        VARIABLE_LENGTH = 2 // variable length
    }

    enum ITEM_STRING_TYPE {
        ANY = 1,
        // 2:全ての半角文字(AnyHalfWidth)
        ANYHALFWIDTH = 2,
        // 3:半角英数字(AlphaNumeric)
        ALPHANUMERIC = 3,
        // 4:半角数字(Numeric)
        NUMERIC = 4,
        // 5:全角カタカナ(Kana)
        KANA = 5
    }

    // define ITEM_SELECT_TYPE
    // type of item if it's selection item
    enum ITEM_SELECT_TYPE {
        // 1:専用マスタ(DesignatedMaster)
        DESIGNATED_MASTER = <any>"DESIGNATED_MASTER",
        // 2:コード名称(CodeName)
        CODE_NAME = <any>"CODE_NAME",
        // 3:列挙型(Enum)
        ENUM = <any>"ENUM"
    }

    enum DateType {
        YEARMONTHDAY = 1,
        YEARMONTH = 2,
        YEAR = 3
    }

    enum ACTION_ROLE {
        HIDDEN = <any>"HIDDEN",
        VIEW_ONLY = <any>"VIEW_ONLY",
        EDIT = <any>"EDIT"
    }
}

ko.bindingHandlers["ntsLayoutControl"] = new nts.custombinding.LayoutControl();
