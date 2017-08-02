import ajax = nts.uk.request.ajax;
import text = nts.uk.resource.getText;

class LayoutControl implements KnockoutBindingHandler {
    controls = {
        label: undefined,
        radios: undefined,
        combobox: undefined,
        searchbox: undefined,
        listbox: undefined,
        button: undefined,
        sortable: undefined,
        line: undefined,
    };

    options = {
        radios: {
            value: ko.observable(0),
            options: [{ id: 0, name: text('CPS007_6') }, { id: 1, name: text('CPS007_7') }],
            optionsValue: 'id',
            optionsText: 'name',
            enable: ko.observable(true),
        },
        comboxbox: {
            editable: false,
            enable: undefined,
            options: ko.observableArray([{ id: 0, name: text('CPS007_6') }, { id: 1, name: text('CPS007_7') }]),
            value: ko.observable(0),
            optionsValue: 'id',
            optionsText: 'name',
            columns: [{ prop: 'name', length: 15 }]
        },
        searchbox: {
            targetKey: undefined,
            mode: 'igGrid',
            comId: 'grid',
            items: undefined,
            selected: undefined,
            selectedKey: 'cid',
            fields: ['cname']
        },
        listbox: {
            enable: ko.observable(true),
            multiple: ko.observable(false),
            rows: 15,
            options: ko.observableArray([]),
            value: ko.observable(undefined),
            optionsValue: 'cid',
            optionsText: 'cname',
            columns: [{ key: 'cname', length: 15 }]
        },
        sortable: {
            data: undefined,
            isEnabled: undefined,
            beforeMove: (data, evt, ui) => {
                debugger;
            }
        }
    };

    $tmp = $(`<div>
                <style type="text/css" rel="stylesheet">                    
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
                    
                    .layout-control .add-buttons {
                        margin-right: 15px;
                        padding-top: 220px;
                    }
                    
                    .layout-control .drag-panel {
                        border: 1px solid #ccc;
                        border-radius: 10px;
                        width: 600px;
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
                    
                    .layout-control.editable div.ui-sortable {
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
                    
                    .layout-control .item-classification>* {
                        display: inline-block;
                        vertical-align: middle;
                    }
                    
                    .layout-control .item-classification.ui-sortable-helper {
                        cursor: pointer;
                    }
                    
                    .layout-control .item-classification.ui-sortable-placeholder {
                        border: 1px dashed #ddd;
                        visibility: visible !important;
                    }
                    
                    .layout-control.editable .item-classification:hover,
                    .layout-control.editable .item-classification.selected {
                        background-color: #eee;
                        border: 1px dashed #aaa;
                    }
                    
                    .layout-control .item-classification>hr {
                        padding: 0;
                        margin: 6px 0;
                        margin-right: 20px;
                    }
                    
                    .layout-control .item-classification textarea.nts-editor {
                        width: 280px;
                        height: 70px;
                    }
                    
                    .layout-control .item-classification .form-label {
                        width: 100px;
                        line-height: 37px;
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
                    
                    .layout-control.editable .item-classification:hover>.close-btn {
                        display: block;
                    }
                </style>
                <div class="left-area">
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
                            <div class="form-group item-classification">
                                <div data-bind="ntsFormLabel: {}, text: cname"></div>
                                <input data-bind="ntsTextEditor: {
                                            value: ko.observable(''),
                                            constraint: '',
                                            option: {},
                                            required: false,
                                            enable: true,
                                            readonly: false,
                                            immediate: false}" />
                                <span class="close-btn" data-bind="click: $parent.removeItem.bind($parent, $data)">✖</span>
                            </div>
                        </div>
                        <button id="cps007_btn_line"></button>
                    </div>
                </div>
            </div>`);

    constructor() {
        let self = this,
            opts = self.options,
            ctrls = self.controls;

        // extend option
        $.extend(opts.comboxbox, {
            enable: ko.computed(() => !opts.radios.value())
        });

        $.extend(opts.searchbox, {
            targetKey: 'cps007_lst_control',
            items: ko.computed(opts.listbox.options),
            selected: opts.listbox.value
        });

        // get all id of controls
        $.extend(self.controls, {
            label: self.$tmp.find('#cps007_lbl_control')[0],
            radios: self.$tmp.find('#cps007_rdg_control')[0],
            combobox: self.$tmp.find('#cps007_cbx_control')[0],
            searchbox: self.$tmp.find('#cps007_sch_control')[0],
            listbox: self.$tmp.find('#cps007_lst_control')[0],
            button: self.$tmp.find('#cps007_btn_add')[0],
            sortable: self.$tmp.find('#cps007_srt_control')[0],
            line: self.$tmp.find('#cps007_btn_line')[0]
        });

        // change text of label
        $(ctrls.label).text(text('CPS007_5'));
        $(ctrls.line).text(text('CPS007_19'));
        $(ctrls.button).text(text('CPS007_20'));


        // subscribe handle
        opts.radios.value.subscribe(d => {
            if (!d) {
                // 
                ajax('').done((data: Array<any>) => {
                    opts.comboxbox.options(data);
                });
            }
        });

        // events handler
        $(ctrls.line).on('click', function() {
            let data = ko.toJS(opts.sortable.data);
            let item: any = {
                cid: data.length + 1,
                cname: '0000' + (data.length + 1)
            };
            opts.sortable.data.push(item);
        });
    }

    init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
        let self = this,
            opts = self.options,
            ctrls = self.controls,
            $element = $(element),
            access = valueAccessor();

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

        // extend data of sortable with valueAccessor data prop
        $.extend(opts.sortable, {
            data: access.data
        });

        if (ko.toJS(access.editAble) != undefined) {
            if (typeof access.editAble == 'function') {
                access.editAble.subscribe(x => {
                    if (!x) {
                        self.$tmp.find('.left-area, .add-buttons, #cps007_btn_line').hide();
                        $element
                            .addClass('readonly')
                            .removeClass('editable');
                    } else {
                        $element
                            .addClass('editable')
                            .removeClass('readonly');
                        
                        self.$tmp.find('.left-area, .add-buttons, #cps007_btn_line').show();
                    }
                });

                $.extend(opts.sortable, {
                    isEnabled: access.editAble
                });
                access.editAble.valueHasMutated();
            }
            else {
                $.extend(opts.sortable, {
                    isEnabled: ko.observable(access.editAble)
                });
            }
        } else {
            $.extend(opts.sortable, {
                isEnabled: ko.observable(true)
            });
        }

        // extend data of sortable with valueAccessor beforeMove prop
        if (access.beforeMove) {
            $.extend(opts.sortable, { beforeMove: access.beforeMove });
        }

        ko.bindingHandlers['ntsSortable'].init(ctrls.sortable, function() {
            return opts.sortable;
        }, allBindingsAccessor, viewModel, bindingContext);

        $element
            .addClass('ntsControl layout-control ' + (!ko.toJS(opts.sortable.isEnabled) ? 'readonly' : 'editable'))
            .append(self.$tmp);


        //demo
        opts.sortable.data.subscribe(data => {
            opts.listbox.options(data);
        });

        // Also tell KO *not* to bind the descendants itself, otherwise they will be bound twice
        return { controlsDescendantBindings: true };
    }

    update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
        let self = this,
            opts = self.options,
            ctrls = self.controls,
            $element = $(element),
            access = valueAccessor();

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

ko.bindingHandlers["ntsLayoutControl"] = new LayoutControl();