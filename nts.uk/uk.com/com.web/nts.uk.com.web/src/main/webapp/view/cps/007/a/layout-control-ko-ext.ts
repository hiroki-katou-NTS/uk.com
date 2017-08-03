module nts.custombinding {

    import ajax = nts.uk.request.ajax;
    import text = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class LayoutControl implements KnockoutBindingHandler {
        $tmp = $(`<div>
                <style type="text/css" rel="stylesheet">
                    .layout-control.editable{
                        width: 1000px;
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

                    .layout-control #cps007_btn_add {
                        width: 140px;
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
                    
                    .layout-control .add-buttons {
                        margin-right: 15px;
                        padding-top: 220px;
                    }
                    
                    .layout-control .drag-panel {
                        border: 1px solid #ccc;
                        border-radius: 10px;
                        width: 572px;
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
                    
                    .layout-control .item-classification>div.item-control>*,
                    .layout-control .item-classification>div.item-controls>* {
                        overflow: hidden;
                        display: inline-block;
                        vertical-align: middle;
                    }

                    .layout-control .item-classification>div.item-controls>* {
                        vertical-align: top;
                    }

                    .layout-control .item-classification>div.item-controls table,
                    .layout-control .item-classification>div.item-controls table th,
                    .layout-control .item-classification>div.item-controls table td {
                        width: 380px;
                        border: 1px solid #ccc;
                    }

                    .layout-control .item-classification>div.item-controls table th {
                        padding: 3px;
                        line-height: 24px;
                        background-color: #E0F59E;
                    }

                    .layout-control .item-classification>div.item-controls table td {
                        line-height: 24px;
                    }
                    
                    .layout-control .item-classification>div.item-sperator>hr {
                        padding: 0;
                        margin: 6px 0;
                        margin-right: 20px;
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
                    
                    .layout-control .item-classification textarea.nts-editor {
                        width: 280px;
                        height: 70px;
                    }
                    
                    .layout-control .item-classification .form-label {
                        width: 100px;
                        line-height: 37px;
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
                                <div data-bind="if: $data.typeId != 1 && $data.typeId != 2" class="item-control">
                                    <div data-bind="ntsFormLabel: {}, text: name"></div>
                                    <input tabindex="-1" data-bind="ntsTextEditor: {
                                                value: ko.observable(''),
                                                constraint: '',
                                                option: {},
                                                required: false,
                                                enable: true,
                                                readonly: true,
                                                immediate: false}" />
                                </div>
                                <div data-bind="if: $data.typeId == 1" class="item-controls">
                                    <div data-bind="ntsFormLabel: {}, text: name"></div>
                                    <div>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>0</th>
                                                    <th>1</th>
                                                    <th>2</th>
                                                </tr>
                                            </thead>
                                            <tbody data-bind="foreach: [1, 2, 3]">
                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div data-bind="if: $data.typeId == 2" class="item-sperator">
                                    <hr />
                                </div>
                                <span class="close-btn" data-bind="click: function() { ko.bindingHandlers['ntsLayoutControl'].options.sortable.removeItem($data); }">✖</span>
                            </div>
                        </div>
                        <button id="cps007_btn_line"></button>
                    </div>
                </div>
            </div>`);

        api = {
            getCats: '',
            getGroups: '',
            getItemCats: '/{0}',
            getItemGroups: '/{0}',
            getItemsByIds: '/{0}',
        };

        services = {
            getCats: () => {
                let self = this,
                    api = self.api;

                return $.Deferred().resolve([
                    {
                        id: 'ID1',
                        code: 'COD1',
                        name: 'CATEGORY 01',
                        typeId: 1
                    },
                    {
                        id: 'ID2',
                        code: 'COD2',
                        name: 'CATEGORY 02',
                        typeId: 2
                    },
                    {
                        id: 'ID3',
                        code: 'COD3',
                        name: 'CATEGORY 03',
                        typeId: 3
                    },
                    {
                        id: 'ID4',
                        code: 'COD4',
                        name: 'CATEGORY 04',
                        typeId: 4
                    },
                    {
                        id: 'ID5',
                        code: 'COD5',
                        name: 'CATEGORY 05',
                        typeId: 5
                    }
                ]).promise();
                //return ajax(api.getCats);
            },
            getGroups: () => {
                let self = this,
                    api = self.api;

                return $.Deferred().resolve([
                    {
                        id: 'ID1',
                        code: 'COD1',
                        name: 'GROUP 01',
                        typeId: 1
                    },
                    {
                        id: 'ID2',
                        code: 'COD2',
                        name: 'GROUP 02',
                        typeId: 2
                    },
                    {
                        id: 'ID3',
                        code: 'COD3',
                        name: 'GROUP 03',
                        typeId: 3
                    },
                    {
                        id: 'ID4',
                        code: 'COD4',
                        name: 'GROUP 04',
                        typeId: 4
                    },
                    {
                        id: 'ID5',
                        code: 'COD5',
                        name: 'GROUP 05',
                        typeId: 5
                    }
                ]).promise();
                //return ajax(api.getGroups);
            },
            getItemByCat: (cid) => {
                let self = this,
                    api = self.api;

                return $.Deferred().resolve([
                    {
                        id: 'ID1',
                        code: 'COD1',
                        name: 'ITEM CAT [' + cid + '] ' + 1,
                        typeId: 0
                    },
                    {
                        id: 'ID2',
                        code: 'COD2',
                        name: 'ITEM CAT [' + cid + '] ' + 2,
                        typeId: 0
                    },
                    {
                        id: 'ID3',
                        code: 'COD3',
                        name: 'ITEM CAT [' + cid + '] ' + 3,
                        typeId: 0
                    },
                    {
                        id: 'ID4',
                        code: 'COD4',
                        name: 'ITEM CAT [' + cid + '] ' + 4,
                        typeId: 0
                    },
                    {
                        id: 'ID5',
                        code: 'COD5',
                        name: 'ITEM CAT [' + cid + '] ' + 5,
                        typeId: 0
                    }
                ]).promise();

                //return ajax(format(api.getItemCats, cid));
            },
            getItemByGroup: (gid) => {
                let self = this,
                    api = self.api;

                return $.Deferred().resolve([
                    {
                        id: 'ID1',
                        code: 'COD1',
                        name: 'GROUP [' + gid + '] ' + 1,
                        typeId: 0
                    },
                    {
                        id: 'ID2',
                        code: 'COD2',
                        name: 'GROUP [' + gid + '] ' + 2,
                        typeId: 0
                    },
                    {
                        id: 'ID3',
                        code: 'COD3',
                        name: 'GROUP [' + gid + '] ' + 3,
                        typeId: 0
                    },
                    {
                        id: 'ID4',
                        code: 'COD4',
                        name: 'GROUP [' + gid + '] ' + 4,
                        typeId: 0
                    },
                    {
                        id: 'ID5',
                        code: 'COD5',
                        name: 'GROUP [' + gid + '] ' + 5,
                        typeId: 1
                    }
                ]).promise();
                //return ajax(format(api.getItemGroups, gid));
            },
            getItemsByIds: (ids: Array<any>) => {
                let self = this,
                    api = self.api;

                //return ajax(format(api.getItemsByIds, ids));
            }
        };

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
                enable: ko.observable(true),
                options: ko.observableArray([]),
                value: ko.observable(''),
                optionsValue: 'id',
                optionsText: 'name',
                columns: [{ prop: 'name', length: 15 }]
            },
            searchbox: {
                targetKey: undefined,
                mode: 'igGrid',
                comId: 'grid',
                items: ko.observableArray([]),
                selected: ko.observableArray([]),
                selectedKey: 'id',
                fields: ['name']
            },
            listbox: {
                enable: ko.observable(true),
                multiple: ko.observable(false),
                rows: 15,
                options: ko.observableArray([]),
                value: ko.observable(undefined),
                optionsValue: 'id',
                optionsText: 'name',
                columns: [{ key: 'name', length: 15 }]
            },
            sortable: {
                data: ko.observableArray([]),
                isEnabled: ko.observable(true),
                beforeMove: (data, evt, ui) => {
                    let item = data.item,
                        sindex = data.sourceIndex,
                        tindex = data.targetIndex,
                        direct = sindex > tindex,
                        source = data.targetParent();


                    // cancel drop if two line is sibling
                    if (item.typeId == IT_CLA_TYPE.SPER) {
                        let front = source[tindex - 1] || { id: '-1', typeId: -1 },
                            replc = source[tindex] || { id: '-1', typeId: -1 },
                            next = source[tindex + 1] || { id: '-1', typeId: -1 };

                        if (!direct) { // drag from top to below
                            if (next.typeId == IT_CLA_TYPE.SPER || replc.typeId == IT_CLA_TYPE.SPER) {
                                data.cancelDrop = true;
                            }
                        } else {  // drag from below to top
                            if (replc.typeId == IT_CLA_TYPE.SPER || front.typeId == IT_CLA_TYPE.SPER) {
                                data.cancelDrop = true;
                            }
                        }
                    } else { // if item is list or object
                        let front = source[sindex - 1] || { id: '-1', typeId: -1 },
                            next = source[sindex + 1] || { id: '-1', typeId: -1 };

                        if (front.typeId == IT_CLA_TYPE.SPER && next.typeId == IT_CLA_TYPE.SPER) {
                            data.cancelDrop = true;
                        }
                    }
                },
                afterMove: (data, evt, ui) => {
                    /*let self = this,
                        opts = self.options,
                        source: Array<any> = ko.unwrap(opts.sortable.data),
                        maps: Array<number> = _(source).map((x, i) => (x.typeId == IT_CLA_TYPE.SPER) ? i : -1)
                            .filter(x => x != -1).value();

                    // remove next line if two line is sibling
                    _.each(maps, (x, i) => {
                        if (maps[i + 1] == x + 1) {
                            opts.sortable.data.remove(m => {
                                let item = ko.unwrap(opts.sortable.data)[maps[i + 1]];
                                return item.typeId == IT_CLA_TYPE.SPER && item.id == m.id;
                            });
                        }
                    });*/
                },
                removeItem: (data) => {
                    let self = this,
                        opts = self.options,
                        items = opts.sortable.data;

                    items.remove(x => x.id == data.id);

                    let source: Array<any> = ko.unwrap(items),
                        maps: Array<number> = _(source).map((x, i) => (x.typeId == IT_CLA_TYPE.SPER) ? i : -1)
                            .filter(x => x != -1).value()

                    // remove next line if two line is sibling
                    _.each(maps, (x, i) => {
                        if (maps[i + 1] == x + 1) {
                            items.remove(m => {
                                let item = ko.unwrap(items)[maps[i + 1]];
                                return item.typeId == IT_CLA_TYPE.SPER && item.id == m.id;
                            });
                        }
                    });
                }
            }
        };

        constructor() {
            let self = this,
                opts = self.options,
                ctrls = self.controls,
                services = self.services;

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
            $(ctrls.button).text(text('CPS007_11'));


            // subscribe handle
            // load combobox data
            opts.radios.value.subscribe(d => {
                if (!d) {
                    services.getCats().done((data: Array<IItemCategory>) => {
                        if (data && data.length) {
                            opts.comboxbox.options(data);
                            opts.comboxbox.value(data[0].id);
                        }
                        else {
                            opts.comboxbox.value(undefined);
                            opts.comboxbox.options.removeAll();

                            // remove listbox data
                            opts.listbox.value(undefined);
                            opts.listbox.options.removeAll();
                        }
                        opts.comboxbox.value.valueHasMutated();
                    });
                } else {
                    // remove comboxbox data
                    opts.comboxbox.value(undefined);
                    opts.comboxbox.options.removeAll();

                    // update list box to group data
                    opts.listbox.options.removeAll();
                    services.getGroups().done((data: Array<IItemDefinition>) => {
                        if (data && data.length) {
                            opts.listbox.options(data);
                            opts.listbox.value(data[0].id);
                        } else {
                            opts.listbox.value(undefined);
                        }
                    });

                    $(ctrls.button).text(text('CPS007_20'));
                }
            });
            opts.radios.value.valueHasMutated();

            // load listbox data
            opts.comboxbox.value.subscribe(d => {
                if (d) {
                    opts.listbox.options.removeAll();
                    let data: Array<IItemCategory> = ko.toJS(opts.comboxbox.options),
                        item = _.find(data, x => x.id == d);
                    if (item) {
                        switch (item.typeId) {
                            case 1:
                            case 3:
                            case 4:
                                $(ctrls.button).text(text('CPS007_11'));
                                services.getItemByCat(item.id).done((data) => {
                                    if (data) {
                                        opts.listbox.options(data);
                                        opts.listbox.value(data[0].id);
                                    }
                                });
                                break;
                            case 2:
                            case 5:
                                $(ctrls.button).text(text('CPS007_10'));

                                // create item for listbox
                                // itemname: categoryname + text('CPS007_21')
                                let def: IItemDefinition = {
                                    id: item.id,
                                    code: item.code,
                                    name: item.name + text('CPS007_21'),
                                    typeId: item.typeId
                                };
                                opts.listbox.value(def.id);
                                opts.listbox.options.push(def);
                                break;
                        }
                    } else {
                        opts.listbox.value(undefined);
                    }
                }
            });
            opts.comboxbox.value.valueHasMutated();

            // events handler
            $(ctrls.line).on('click', function() {
                // add line to list sortable
                let data: Array<any> = ko.unwrap(opts.sortable.data),
                    last = _.last(data),
                    item: any = {
                        id: 'ID' + (data.length + 1),
                        code: 'COD' + (data.length + 1),
                        name: 'Line Item ' + (data.length + 1),
                        typeId: IT_CLA_TYPE.SPER
                    };

                if (last && last.typeId != IT_CLA_TYPE.SPER) {
                    opts.sortable.data.push(item);
                }
            });

            $(ctrls.button).on('click', () => {
                // category mode
                if (ko.unwrap(opts.radios.value) == 0) {
                    let cid: string = ko.toJS(opts.comboxbox.value),
                        cats: Array<IItemCategory> = ko.toJS(opts.comboxbox.options),
                        cat: IItemCategory = _.find(cats, x => x.id == cid);

                    if (cat) {
                        // multiple items
                        if (cat.typeId == 2 || cat.typeId == 5) {
                            setShared('CPS007_PARAM', { category: { id: 'ID1' }, chooseItems: [] });
                            modal('../b/index.xhtml').onClosed(() => {
                                let data = getShared('CPS007_VALUE') || { chooseItems: [] };
                                if (data.chooseItems && data.chooseItems.length) {
                                    let data = ko.unwrap(opts.sortable.data),
                                        item: any = {
                                            id: data.length + 1,
                                            name: '0000' + (data.length + 1)
                                        };
                                    opts.sortable.data.push(item);
                                }
                            });
                        }
                        else { // single item
                            let data = ko.unwrap(opts.sortable.data),
                                item: any = {
                                    id: data.length + 1,
                                    code: 'COD' + data.length + 1,
                                    name: 'Single Item ' + (data.length + 1)
                                };
                            opts.sortable.data.push(item);
                        }
                    }
                } else { // group mode
                    let id = ko.toJS(opts.listbox.value),
                        groups: Array<any> = ko.unwrap(opts.listbox.options),
                        group: any = _.find(groups, x => x.id == id);

                    if (group) {
                        services.getItemByGroup(group.id).done((data: Array<any>) => {
                            if (data && data.length) {
                                _.each(data, x => opts.sortable.data.push(x));
                            }
                        });
                    }
                }
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

            // validate editAble
            if (ko.unwrap(access.editAble) != undefined) {
                if (typeof access.editAble == 'function') {
                    $.extend(opts.sortable, {
                        isEnabled: access.editAble
                    });
                }
                else {
                    $.extend(opts.sortable, {
                        isEnabled: ko.observable(access.editAble)
                    });
                }
                opts.sortable.isEnabled.subscribe(x => {
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
                opts.sortable.isEnabled.valueHasMutated();
            } else {
                $.extend(opts.sortable, {
                    isEnabled: ko.observable(true)
                });
            }

            // extend data of sortable with valueAccessor data prop
            $.extend(opts.sortable, { data: access.data });

            // extend data of sortable with valueAccessor beforeMove prop
            if (access.beforeMove) {
                $.extend(opts.sortable, { beforeMove: access.beforeMove });
            }

            // extend data of sortable with valueAccessor afterMove prop
            if (access.afterMove) {
                $.extend(opts.sortable, { afterMove: access.afterMove });
            }

            ko.bindingHandlers['ntsSortable'].init(ctrls.sortable, function() {
                return opts.sortable;
            }, allBindingsAccessor, viewModel, bindingContext);

            $element
                .addClass('ntsControl layout-control')
                .append(self.$tmp);

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

    interface IItemGroup {
        id: string;
        name: string;
        dispOrder: number;
    }

    interface IItemCategory {
        id: string;
        code: string;
        name: string;
        typeId: number;
    }

    interface IItemClassification {

    }

    interface IItemDefinition {
        id: string;
        code: string;
        name: string;
        typeId?: number;
    }


    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = 0, // single item
        LIST = 1, // list item
        SPER = 2 // line item
    }
}

ko.bindingHandlers["ntsLayoutControl"] = new nts.custombinding.LayoutControl();