var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var _ = window['_'], $ = window['$'], ko = window['ko'];
                var count = nts.uk.text.countHalf;
                var WoC = 9, MINWIDTH = 'auto', TAB_INDEX = 'tabindex', KEYPRESS = 'keypress', KEYDOWN = 'keydown', FOCUS = 'focus', VALIDATE = 'validate', OPENDDL = 'openDropDown', CLOSEDDL = 'closeDropDown', OPENED = 'dropDownOpened', IGCOMB = 'igCombo', OPTION = 'option', ENABLE = 'enable', EDITABLE = 'editable', DROPDOWN = 'dropdown', COMBOROW = 'nts-combo-item', COMBOCOL = 'nts-column nts-combo-column', DATA = '_nts_data', CHANGED = '_nts_changed', SHOWVALUE = '_nts_show', NAME = '_nts_name', CWIDTH = '_nts_col_width', VALUE = '_nts_value', REQUIRED = '_nts_required';
                var ComboBoxBindingHandler = (function () {
                    function ComboBoxBindingHandler() {
                        this.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                            var template = '', $element = $(element), accessor = valueAccessor(), options = ko.unwrap(accessor.options), enable = _.has(accessor, 'enable') ? ko.unwrap(accessor.enable) : true, editable = _.has(accessor, 'editable') ? ko.unwrap(accessor.editable) : false, required = _.has(accessor, 'required') ? ko.unwrap(accessor.required) : false, optionsText = _.has(accessor, 'optionsText') ? ko.unwrap(accessor.optionsText) : null, optionsValue = _.has(accessor, 'optionsValue') ? ko.unwrap(accessor.optionsValue) : null, columns = _.has(accessor, 'columns') ? ko.unwrap(accessor.columns) : [{ prop: optionsText }], visibleItemsCount = _.has(accessor, 'visibleItemsCount') ? ko.unwrap(accessor.visibleItemsCount) : 5, dropDownAttachedToBody = _.has(accessor, 'dropDownAttachedToBody') ? ko.unwrap(accessor.dropDownAttachedToBody) : true, $show = $('<div>', {
                                'class': 'nts-toggle-dropdown',
                                'css': {
                                    'color': '#000',
                                    'height': '30px',
                                    'padding-left': '3px',
                                    'position': 'absolute',
                                    'left': '0px',
                                    'right': '0px'
                                }
                            });
                            options = _(options)
                                .filter(function (x) { return _.isObject(x); })
                                .value();
                            if (!!$element.closest(".ui-iggrid").length) {
                                dropDownAttachedToBody = true;
                            }
                            if (_.isArray(columns)) {
                                template = "<div class='" + COMBOROW + "'>" + _.map(columns, function (c, i) { return ("<div data-ntsclass='" + (c.toggle || '') + "' class='" + COMBOCOL + "-" + i + " " + c.prop.toLowerCase() + " " + (c.toggle || '') + "'>${" + c.prop + "}&nbsp;</div>"); }).join('') + "</div>";
                            }
                            if (!$element.attr('tabindex')) {
                                $element.attr('tabindex', 0);
                            }
                            $element
                                .on(SHOWVALUE, function (evt) {
                                var data = $element.data(DATA), cws = data[CWIDTH], ks = _.keys(cws);
                                var option = _.find(data[DATA], function (t) { return t[optionsValue] == data[VALUE]; }), _template = template;
                                if (option) {
                                    _.each(_.keys(option), function (k) {
                                        _template = _template.replace("${" + k + "}", _.escape(option[k]));
                                    });
                                    $show.html(_template);
                                    _.each(ks, function (k) {
                                        $show.find("." + k.toLowerCase() + ":not(:last-child)")
                                            .css('width', cws[k] * WoC + "px");
                                        $show.find("." + k.toLowerCase())
                                            .css({
                                            'height': '31px',
                                            'line-height': '27px'
                                        })
                                            .find('.nts-column:last-child')
                                            .css('margin-right', 0);
                                    });
                                }
                                else {
                                    if (!_.isNil(ko.toJS(accessor.nullText)) && !_.isNil(data[SHOWVALUE])) {
                                        $show.html($('<div>', { 'class': 'nts-combo-column', text: _.escape(ko.toJS(accessor.nullText)), css: { 'line-height': '27px' } }));
                                    }
                                    else {
                                        $show.empty();
                                    }
                                }
                            })
                                .on(CHANGED, function (evt, key, value) {
                                if (value === void 0) { value = undefined; }
                                var data = $element.data(DATA) || {};
                                {
                                    data[key] = value;
                                    $element.data(DATA, data);
                                }
                            })
                                .on(VALIDATE, function (evt, ui) {
                                var data = $element.data(DATA), value = data[VALUE];
                                if ((ui ? data[CHANGED] : true) && data[ENABLE] && data[REQUIRED] && (_.isEmpty(String(value).trim()) || _.isNil(value))) {
                                    $element
                                        .addClass('error')
                                        .ntsError("set", uk.resource.getMessage("FND_E_REQ_SELECT", [data[NAME]]), "FND_E_REQ_SELECT");
                                    if (accessor.value.addError) {
                                        accessor.value.addError("FND_E_REQ_SELECT", { MsgId: "FND_E_REQ_SELECT" });
                                    }
                                }
                                else {
                                    $element
                                        .removeClass('error')
                                        .ntsError("clear");
                                    if (accessor.value.removeError) {
                                        accessor.value.removeError("FND_E_REQ_SELECT");
                                    }
                                }
                            })
                                .on(KEYDOWN, function (evt, ui) {
                                if ($element.data(IGCOMB)) {
                                    if ([13].indexOf(evt.which || evt.keyCode) > -1) {
                                        $element
                                            .find('.ui-igcombo-button')
                                            .trigger('click');
                                    }
                                    else if ([32, 38, 40].indexOf(evt.which || evt.keyCode) > -1) {
                                        if (!$element.igCombo(OPENED)) {
                                            $element
                                                .find('.ui-igcombo-button')
                                                .trigger('click');
                                        }
                                    }
                                }
                            })
                                .igCombo({
                                loadOnDemandSettings: {
                                    enabled: true,
                                    pageSize: 15
                                },
                                dataSource: options,
                                placeHolder: '',
                                textKey: 'nts_' + optionsText,
                                valueKey: optionsValue,
                                mode: EDITABLE,
                                disabled: !ko.toJS(enable),
                                enableClearButton: false,
                                itemTemplate: template,
                                dropDownWidth: "auto",
                                tabIndex: $element.attr('tabindex') || 0,
                                visibleItemsCount: visibleItemsCount,
                                dropDownAttachedToBody: dropDownAttachedToBody,
                                rendered: function (evt, ui) {
                                    setTimeout(function () {
                                        $(ui.owner.dropDown()[0])
                                            .css({
                                            top: '-99999px',
                                            left: '-99999px'
                                        });
                                    }, 100);
                                    $element
                                        .find('.ui-igcombo')
                                        .css('background', '#fff')
                                        .find('.ui-igcombo-fieldholder').hide();
                                    $element
                                        .find('.ui-igcombo-hidden-field')
                                        .parent()
                                        .prepend($show)
                                        .css({
                                        'overflow': 'hidden',
                                        'position': 'relative'
                                    })
                                        .find('.ui-igcombo-button')
                                        .css({
                                        'float': 'none',
                                        'width': '100%',
                                        'border': '0px',
                                        'padding': '0px',
                                        'position': 'absolute',
                                        'box-sizing': 'border-box',
                                        'background-color': 'transparent'
                                    })
                                        .find('.ui-igcombo-buttonicon')
                                        .text('▼')
                                        .css({
                                        'right': '0px',
                                        'font-size': '0.85rem',
                                        'top': '0px',
                                        'bottom': '0px',
                                        'display': 'block',
                                        'background-color': '#ececec',
                                        'width': '30px',
                                        'text-align': 'center',
                                        'line-height': '30px',
                                        'margin': '0px',
                                        'border-left': '1px solid #ccc'
                                    })
                                        .removeClass('ui-icon')
                                        .removeClass('ui-icon-triangle-1-s');
                                },
                                itemsRendered: function (evt, ui) {
                                    var data = $element.data(DATA) || {}, cws = data[CWIDTH] || [], ks = _.keys(cws);
                                    _.each(ks, function (k) {
                                        $("[class*=ui-igcombo-orientation]")
                                            .find("." + k.toLowerCase() + ":not(:last-child)")
                                            .css('width', cws[k] * WoC + "px");
                                    });
                                },
                                selectionChanged: function (evt, ui) {
                                    if (!_.size(ui.items)) {
                                        $element.trigger(CHANGED, [VALUE, null]);
                                    }
                                    else {
                                        var value = ui.items[0]["data"][optionsValue];
                                        $element.trigger(CHANGED, [VALUE, value]);
                                    }
                                },
                                dropDownClosed: function (evt, ui) {
                                    var data = $element.data(DATA);
                                    if (data) {
                                        $element.trigger(CHANGED, [CHANGED, true]);
                                        var sto_1 = setTimeout(function () {
                                            var data = $element.data(DATA);
                                            if (!data[EDITABLE] && _.isNil(data[VALUE])) {
                                                $element.trigger(CHANGED, [VALUE, $element.igCombo('value')]);
                                                data = $element.data(DATA);
                                            }
                                            if (_.isArray(data[VALUE]) && !_.size(data[VALUE])) {
                                                $element.trigger(CHANGED, [VALUE, ko.toJS(accessor.value)]);
                                                data = $element.data(DATA);
                                            }
                                            accessor.value(data[VALUE]);
                                            $element
                                                .trigger(VALIDATE, [true])
                                                .trigger(SHOWVALUE);
                                            clearTimeout(sto_1);
                                        }, 10);
                                        if (data[ENABLE]) {
                                            var f = $(':focus');
                                            if (f.hasClass('ui-igcombo-field')
                                                || !(f.is('input') || f.is('select') || f.is('textarea') || f.is('a') || f.is('button'))
                                                || ((f.is('p') || f.is('div') || f.is('span') || f.is('table') || f.is('ul') || f.is('li') || f.is('tr')) && _.isNil(f.attr('tabindex')))) {
                                                $element.focus();
                                            }
                                        }
                                    }
                                },
                                dropDownOpening: function (evt, ui) {
                                    var data = $element.data(DATA), cws = data[CWIDTH], ks = _.keys(cws);
                                    $element
                                        .find('.ui-igcombo-fieldholder')
                                        .prependTo(ui.list);
                                    var $input = ui.list
                                        .attr('dropdown-for', $element.attr('id'))
                                        .find('.ui-igcombo-fieldholder')
                                        .css({
                                        'height': !!data[EDITABLE] ? '' : '0px',
                                        'padding': !!data[EDITABLE] ? '3px' : '',
                                        'background-color': !!data[EDITABLE] ? '#f6f6f6' : ''
                                    })
                                        .show()
                                        .find('input')
                                        .prop('readonly', !data[EDITABLE])
                                        .css({
                                        'width': '0px',
                                        'height': !!data[EDITABLE] ? '30px' : '0px',
                                        'text-indent': !!data[EDITABLE] ? '0' : '-99999px',
                                        'border': !!data[EDITABLE] ? '1px solid #ccc' : 'none'
                                    });
                                    if (!$input.data('_nts_bind')) {
                                        $input
                                            .on(KEYDOWN, function (evt, ui) {
                                            if ([13].indexOf(evt.which || evt.keyCode) > -1) {
                                                if ($element.data(IGCOMB)) {
                                                    $element
                                                        .find('.ui-igcombo-button')
                                                        .trigger('click');
                                                }
                                            }
                                        })
                                            .data('_nts_bind', true)
                                            .attr('tabindex', -1);
                                    }
                                    _.each(ks, function (k) {
                                        $(ui.list).find("." + k.toLowerCase() + (_.size(ks) == 1 ? '' : ':not(:last-child)'))
                                            .css('width', cws[k] * WoC + "px");
                                    });
                                    $(ui.list)
                                        .css('min-width', $element.width() + 'px')
                                        .find('.nts-column:last-child')
                                        .css('margin-right', 0);
                                    var sto = setTimeout(function () {
                                        $input.css({
                                            'width': ($(ui.list).width() - 6) + 'px'
                                        });
                                        clearTimeout(sto);
                                    }, 25);
                                }
                            })
                                .trigger(CHANGED, [DATA, options])
                                .trigger(CHANGED, [TAB_INDEX, $element.attr(TAB_INDEX) || 0])
                                .addClass('ntsControl')
                                .on('blur', function () { $element.css('box-shadow', ''); })
                                .on('focus', function () {
                                $element
                                    .css({
                                    'outline': 'none',
                                    'box-shadow': '0 0 1px 1px #0096f2'
                                });
                            });
                        };
                        this.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                            var ss = new Date().getTime(), $element = $(element), accessor = valueAccessor(), width = _.has(accessor, 'width') ? ko.unwrap(accessor.width) : undefined, name = ko.unwrap(accessor.name), value = ko.unwrap(accessor.value), options = ko.unwrap(accessor.options), selectFirstIfNull = !(ko.unwrap(accessor.selectFirstIfNull) === false), enable = _.has(accessor, 'enable') ? ko.unwrap(accessor.enable) : true, editable = _.has(accessor, 'editable') ? ko.unwrap(accessor.editable) : false, required = _.has(accessor, 'required') ? ko.unwrap(accessor.required) : false, optionsText = _.has(accessor, 'optionsText') ? ko.unwrap(accessor.optionsText) : null, optionsValue = _.has(accessor, 'optionsValue') ? ko.unwrap(accessor.optionsValue) : null, columns = _.has(accessor, 'columns') ? ko.unwrap(accessor.columns) : [{ prop: optionsText }];
                            options = _(options)
                                .filter(function (x) { return _.isObject(x); })
                                .value();
                            var props = columns.map(function (c) { return c.prop; }), vkl = _(options)
                                .map(function (m) {
                                if (!!m) {
                                    return _(m)
                                        .keys(m)
                                        .map(function (t) { return ({
                                        k: t,
                                        w: _.max([count(_.trim(m[t])), (_.find(columns, function (c) { return c.prop == t; }) || {}).length || 0])
                                    }); })
                                        .filter(function (m) { return props.indexOf(m.k) > -1; })
                                        .keyBy('k')
                                        .mapValues('w')
                                        .value();
                                }
                                return undefined;
                            }).filter(function (f) { return !!f; }).value(), cws = _(props)
                                .map(function (p) { return ({ k: p, v: _.maxBy(vkl, p) }); })
                                .map(function (m) { return ({ k: m.k, v: (m.v || {})[m.k] || 0 }); })
                                .keyBy('k')
                                .mapValues('v')
                                .value();
                            options = _(options)
                                .map(function (m) {
                                var c = {}, k = ko.toJS(m), t = k[optionsText], v = k[optionsValue], n = _.omit(k, [optionsValue]), nt = _.map(props, function (p) { return k[p]; }).join(' ').trim();
                                c[optionsValue] = !_.isNil(v) ? v : '';
                                c['nts_' + optionsText] = nt || t || ' ';
                                return _.extend(n, c);
                            })
                                .value();
                            var vio = _.find(options, function (f) { return f[optionsValue] == value; });
                            if (!vio) {
                                if (selectFirstIfNull) {
                                    vio = _.head(options);
                                    if (!vio) {
                                        value = null;
                                    }
                                    else {
                                        value = vio[optionsValue];
                                    }
                                }
                                else {
                                    value = null;
                                    if (!_.isNil(ko.toJS(accessor.value))) {
                                        $element.trigger(CHANGED, [SHOWVALUE, ko.toJS(accessor.value)]);
                                    }
                                }
                                accessor.value(value);
                            }
                            if (_.has($element.data(DATA), VALUE)) {
                                $element.trigger(CHANGED, [CHANGED, true]);
                            }
                            $element
                                .trigger(CHANGED, [CWIDTH, cws])
                                .trigger(CHANGED, [NAME, name])
                                .trigger(CHANGED, [VALUE, value])
                                .trigger(CHANGED, [ENABLE, enable])
                                .trigger(CHANGED, [EDITABLE, editable])
                                .trigger(CHANGED, [REQUIRED, required]);
                            var sto = setTimeout(function () {
                                if ($element.data("igCombo")) {
                                    $element
                                        .igCombo(OPTION, "disabled", !enable);
                                    clearTimeout(sto);
                                }
                            }, 100);
                            if ($element.data("igCombo")) {
                                var data = $element.data(DATA), olds = data[DATA];
                                if (!_.isEqual(olds, options)) {
                                    $element.igCombo(OPTION, "dataSource", options);
                                }
                                $element
                                    .igCombo("value", value);
                                if (!enable) {
                                    $element.removeAttr(TAB_INDEX);
                                }
                                else {
                                    $element.attr(TAB_INDEX, data[TAB_INDEX]);
                                }
                                $element
                                    .trigger(VALIDATE, [true]);
                                if (_.isNil(value)) {
                                    $element
                                        .igCombo("deselectAll");
                                }
                                if (width) {
                                    if (width != MINWIDTH) {
                                        $element.igCombo("option", "width", width);
                                    }
                                    else {
                                        $element
                                            .igCombo("option", "width", (_.sum(_.map(cws, function (c) { return c; })) * WoC + 60) + 'px');
                                    }
                                }
                            }
                            $element
                                .trigger(CHANGED, [DATA, options])
                                .trigger(SHOWVALUE);
                        };
                    }
                    return ComboBoxBindingHandler;
                }());
                koExtentions.ComboBoxBindingHandler = ComboBoxBindingHandler;
                ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
