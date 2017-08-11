module nts.custombinding {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import random = nts.uk.util.randomId;
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class LetControl implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            // Make a modified binding context, with extra properties, and apply it to descendant elements
            ko.applyBindingsToDescendants(bindingContext.extend(valueAccessor), element);

            return { controlsDescendantBindings: true };
        }
    }

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

                    .layout-control .item-classification div.item-control>*,
                    .layout-control .item-classification div.item-controls>* {
                        overflow: hidden;
                        display: inline-block;
                        vertical-align: middle;
                    }

                    .layout-control .item-classification div.item-controls>* {
                        vertical-align: top;
                    }

                    .layout-control .item-classification div.item-controls table,
                    .layout-control .item-classification div.item-controls table th,
                    .layout-control .item-classification div.item-controls table td {
                        width: 380px;
                        border: 1px solid #ccc;
                    }

                    .layout-control .item-classification div.item-controls table th {
                        padding: 3px;
                        line-height: 24px;
                        background-color: #E0F59E;
                    }

                    .layout-control .item-classification div.item-controls table td {
                        line-height: 24px;
                    }

                    .layout-control .item-classification div.item-sperator>hr {
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
                                <div data-bind="if: $data.layoutItemType == 0">
                                    <div data-bind="let: { item: $data.listItemDf[0], listItemDf: $data.listItemDf}" class="item-control">
                                        <div data-bind="ntsFormLabel: {}, text: className"></div>
                                        <div data-bind="if: item.itemTypeState.itemType == 1">
                                            <div id="combo-box" data-bind="ntsComboBox: {
                                                options: ko.observableArray([]),
                                                optionsValue: 'code',
                                                visibleItemsCount: 5,
                                                value: ko.observable(''),
                                                optionsText: 'name',
                                                editable: false,
                                                enable: true,
                                                columns: [{ prop: 'name', length: 10 }]}"></div>
                                        </div>
                                        <div data-bind="if: item.itemTypeState.itemType == 2">
                                            <div data-bind="let: { single: item.itemTypeState.dataTypeState }">
                                                <div data-bind="if: single.dataTypeValue == 1">
                                                    <input data-bind="ntsTextEditor: {
                                                                value: ko.observable(''),
                                                                constraint: '',
                                                                option: {},
                                                                required: false,
                                                                enable: true,
                                                                readonly: true,
                                                                immediate: false}" />
                                                </div>
                                                <div data-bind="if: single.dataTypeValue == 2">
                                                    <input data-bind="ntsNumberEditor: {
                                                            value: ko.observable(undefined)
                                                        }" />
                                                </div>
                                                <div data-bind="if: single.dataTypeValue == 3">
                                                    <div data-bind="ntsDatePicker: {value: ko.observable(undefined), dateFormat: 'YYYY/MM/DD'}"></div>
                                                </div>
                                                <div data-bind="if: single.dataTypeValue == 4">
                                                    <input data-bind="ntsTimeEditor: {value: ko.observable(undefined), inputFormat: 'date'}" />
                                                </div>
                                                <div data-bind="if: single.dataTypeValue == 5">
                                                    <input data-bind="ntsTimeEditor: {value: ko.observable(undefined), inputFormat: 'date'}" />
                                                </div>
                                                <div data-bind="if: single.dataTypeValue == 6">
                                                    <div id="combo-box" data-bind="ntsComboBox: {
                                                        options: ko.observableArray([]),
                                                        optionsValue: 'code',
                                                        visibleItemsCount: 5,
                                                        value: ko.observable(''),
                                                        optionsText: 'name',
                                                        editable: false,
                                                        enable: true,
                                                        columns: [{ prop: 'name', length: 10 }]}"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div data-bind="if: $data.layoutItemType == 1" class="item-controls">
                                    <div data-bind="ntsFormLabel: {}, text: className"></div>
                                    <div data-bind="let: { items: listItemDf }">
                                        <table>
                                            <thead>
                                                <tr data-bind="foreach: items">
                                                    <th data-bind="text: itemName"></th>
                                                </tr>
                                            </thead>
                                            <tbody data-bind="foreach: [1, 2, 3]">
                                                <tr data-bind="foreach: items">
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div data-bind="if: $data.layoutItemType == 2" class="item-sperator">
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
            getCat: 'ctx/bs/person/info/category/findby/{0}',
            getCats: "ctx/bs/person/info/category/findby/company",
            getGroups: 'ctx/bs/person/groupitem/getAll',
            getItemCats: 'ctx/bs/person/info/ctgItem/findby/categoryId/{0}',
            getItemGroups: 'ctx/bs/person/groupitem/getAllItemDf/{0}',
            getItemsById: 'ctx/bs/person/info/ctgItem/findby/itemId/{0}',
            getItemsByIds: 'ctx/bs/person/info/ctgItem/findby/listItemId',
        };

        services = {
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
                enable: ko.observable(true),
                value: ko.observable(0),
                options: ko.observableArray([{
                    id: CAT_OR_GROUP.CATEGORY,
                    name: text('CPS007_6')
                }, {
                        id: CAT_OR_GROUP.GROUP,
                        name: text('CPS007_7')
                    }]),
                optionsValue: 'id',
                optionsText: 'name'
            },
            comboxbox: {
                enable: ko.observable(true),
                editable: ko.observable(false),
                value: ko.observable(''),
                options: ko.observableArray([]),
                optionsValue: 'id',
                optionsText: 'categoryName',
                columns: [{ prop: 'categoryName', length: 15 }]
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
                optionsText: 'itemName',
                columns: [{ key: 'itemName', length: 15 }]
            },
            sortable: {
                data: ko.observableArray([]),
                isEnabled: ko.observable(true),
                beforeMove: (data, evt, ui) => {
                    let self = this,
                        opts = self.options,
                        sindex: number = data.sourceIndex,
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
                removeItem: (data: IItemClassification) => {
                    let self = this,
                        opts = self.options,
                        items = opts.sortable.data;

                    items.remove((x: IItemClassification) => x.layoutID == data.layoutID);

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
                },
                pushItem: (data: IItemClassification) => {
                    let self = this,
                        opts = self.options,
                        items: KnockoutObservableArray<IItemClassification> = opts.sortable.data;

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
            opts.radios.value.subscribe(mode => {
                // remove all data in listbox
                opts.listbox.options.removeAll();

                if (mode == CAT_OR_GROUP.CATEGORY) { // get item by category
                    services.getCats().done((data: any) => {
                        if (data && data.categoryList) {
                            opts.comboxbox.options(data.categoryList);
                            if (opts.comboxbox.value() == data.categoryList[0].id) {
                                opts.comboxbox.value.valueHasMutated();
                            } else {
                                opts.comboxbox.value(data.categoryList[0].id);
                            }
                        }
                        else {
                            // remove listbox data
                            opts.listbox.value(undefined);
                        }
                    });
                } else { // get item by group
                    services.getGroups().done((data: Array<IItemGroup>) => {
                        if (data && data.length) {
                            // map Array<IItemGroup> to Array<IItemDefinition>
                            let _items: Array<IItemDefinition> = _.map(data, x => {
                                return {
                                    id: x.personInfoItemGroupID,
                                    itemName: x.fieldGroupName,
                                    itemTypeState: undefined
                                };
                            });

                            opts.listbox.options(_items);
                            opts.listbox.value(_items[0].id);
                        } else {
                            opts.listbox.value(undefined);
                        }
                    });

                    $(ctrls.button).text(text('CPS007_20'));
                }
            });
            opts.radios.value.valueHasMutated();

            // load listbox data
            opts.comboxbox.value.subscribe(cid => {
                if (cid) {
                    let data: Array<IItemCategory> = ko.toJS(opts.comboxbox.options),
                        item: IItemCategory = _.find(data, x => x.id == cid);

                    // remove all item in list item for init new data
                    opts.listbox.options.removeAll();
                    if (item) {
                        switch (item.categoryType) {
                            case IT_CAT_TYPE.SINGLE:
                            case IT_CAT_TYPE.CONTINU:
                            case IT_CAT_TYPE.NODUPLICATE:
                                $(ctrls.button).text(text('CPS007_11'));
                                services.getItemByCat(item.id).done((data: Array<IItemDefinition>) => {
                                    if (data && data.length) {
                                        opts.listbox.options(data);
                                        opts.listbox.value(undefined);
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
                                opts.listbox.value(undefined);
                                opts.listbox.options.push(def);
                                break;
                        }
                    } else {
                        // select undefine
                        opts.listbox.value(undefined);
                    }
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
            });

            $(ctrls.button).on('click', () => {
                // アルゴリズム「項目追加処理」を実行する
                // Execute the algorithm "項目追加処理"
                if (!ko.toJS(opts.listbox.value)) {
                    alert(text('Msg_203'));
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
                            setShared('CPS007B_PARAM', { category: cat, chooseItems: [] });
                            modal('../b/index.xhtml').onClosed(() => {
                                let data = getShared('CPS007B_VALUE') || { category: undefined, chooseItems: [] };

                                if (data.category && data.category.id && data.chooseItems && data.chooseItems.length) {
                                    services.getCat(data.category.id).done((_cat: IItemCategory) => {
                                        services.getItemsByIds(data.chooseItems.map(x => x.id)).done((_data: Array<IItemDefinition>) => {
                                            let item: IItemClassification = {
                                                layoutID: random(),
                                                dispOrder: -1,
                                                className: _cat.categoryName,
                                                personInfoCategoryID: undefined,
                                                layoutItemType: IT_CLA_TYPE.LIST,
                                                listItemDf: _data
                                            };
                                            opts.sortable.data.push(item);
                                            opts.listbox.value(undefined);
                                        });
                                    });
                                }
                            });
                        }
                        else { // single item
                            let idefid = ko.toJS(opts.listbox.value),
                                idef = _.find(ko.toJS(opts.listbox.options), (x: IItemDefinition) => x.id == idefid),
                                item: IItemClassification = {
                                    layoutID: random(),
                                    dispOrder: -1,
                                    personInfoCategoryID: undefined,
                                    layoutItemType: IT_CLA_TYPE.ITEM,
                                    listItemDf: []
                                };

                            if (idef) {
                                services.getItemsById(idef.id).done((def: IItemDefinition) => {
                                    if (def) {
                                        item.listItemDf = [def];
                                        item.className = def.itemName;
                                        item.personInfoCategoryID = def.perInfoCtgId;

                                        if (opts.sortable.pushItem(item)) {
                                            opts.listbox.value(undefined);
                                        } else {
                                            // 画面項目「選択可能項目一覧」で選択している項目が既に画面に配置されている場合
                                            // When the item selected in the screen item "selectable item list" has already been arranged on the screen
                                            alert(text('Msg_202'));
                                        }
                                    }
                                });
                            }
                        }
                    }
                } else { // group mode
                    let id = ko.toJS(opts.listbox.value),
                        groups: Array<any> = ko.unwrap(opts.listbox.options),
                        group: any = _.find(groups, x => x.id == id);

                    if (group) {
                        services.getItemByGroup(group.id).done((data: Array<IItemDefinition>) => {
                            if (data && data.length) {
                                _.each(data, x => {

                                    let _items: IItemClassification = {
                                        layoutID: random(),
                                        className: x.itemName,
                                        dispOrder: 0,
                                        personInfoCategoryID: x.perInfoCtgId,
                                        layoutItemType: IT_CLA_TYPE.ITEM,
                                        listItemDf: [x]
                                    };

                                    opts.sortable.data.push(_items);
                                    opts.listbox.value(undefined);
                                });
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
            opts.sortable.data.subscribe((data: Array<IItemClassification>) => {
                _.each(data, (x, i) => { x.dispOrder = i + 1; x.layoutID = random() });
            });

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

    interface IItemCategory {
        id: string;
        categoryName: string;
        categoryType: IT_CAT_TYPE;
    }

    interface IItemGroup {
        personInfoItemGroupID: string;
        fieldGroupName: string;
        dispOrder: number;
    }

    interface IItemClassification {
        layoutID?: string;
        dispOrder?: number;
        className?: string; // only for display if classification is set or duplication item
        personInfoCategoryID?: string;
        layoutItemType: IT_CLA_TYPE;
        listItemDf: Array<IItemDefinition>;
    }

    interface IItemDefinition {
        id: string;
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

    interface IItemTypeState extends ISetItem, ISingleItem {
        itemType: ITEM_TYPE; // Set || Single
    }

    interface ISetItem {
        items?: Array<string>; // Set ids value
    }

    interface ISingleItem {
        dataTypeState?: IItemDefinitionData // Single item value
    }

    interface IItemDefinitionData extends IItemTime, IItemDate, IItemString, IItemTimePoint {
        dataTypeValue: ITEM_SINGLE_TYPE; // type of value of item
    }

    interface IItemTime {
        min?: number;
        max?: number;
    }

    interface IItemDate {
        dateItemType?: number;
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
        NumericItemMin?: number;
        NumericItemMax?: number;
    }

    interface IItemSelection extends IItemMasterSelection, IItemEnumSelection, IItemCodeNameSelection {
        referenceType?: number;
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
        ITEM = 0, // single item
        LIST = 1, // list item
        SPER = 2 // line item
    }

    // define ITEM_CATEGORY_TYPE
    enum IT_CAT_TYPE {
        SINGLE = 1, // Single info
        MULTI = 2, // Multi info
        CONTINU = 3, // Continuos history
        NODUPLICATE = 4, //No duplicate history
        DUPLICATE = 5 // Duplicate history
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
        DESIGNATED_MASTER = 1,
        // 2:コード名称(CodeName)
        CODE_NAME = 2,
        // 3:列挙型(Enum)
        ENUM = 3
    }
}
ko.bindingHandlers['let'] = new nts.custombinding.LetControl();
ko.bindingHandlers["ntsLayoutControl"] = new nts.custombinding.LayoutControl();
