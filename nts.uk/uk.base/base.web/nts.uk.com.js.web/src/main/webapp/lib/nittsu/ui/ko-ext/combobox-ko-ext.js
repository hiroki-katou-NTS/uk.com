var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var count = nts.uk.text.countHalf;
                var WoC = 9, MINWIDTH = 'auto', TAB_INDEX = 'tabindex', KEYPRESS = 'keypress', KEYDOWN = 'keydown', FOCUS = 'focus', VALIDATE = 'validate', OPENDDL = 'openDropDown', CLOSEDDL = 'closeDropDown', OPENED = 'dropDownOpened', IGCOMB = 'igCombo', OPTION = 'option', ENABLE = 'enable', EDITABLE = 'editable', DROPDOWN = 'dropdown', COMBOROW = 'nts-combo-item', COMBOCOL = 'nts-column nts-combo-column', DATA = '_nts_data', CHANGED = '_nts_changed', SHOWVALUE = '_nts_show', NAME = '_nts_name', CWIDTH = '_nts_col_width', VALUE = '_nts_value', REQUIRED = '_nts_required';
                var ComboBoxBindingHandler = (function () {
                    function ComboBoxBindingHandler() {
                        this.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                            var template = '', $element = $(element), accessor = valueAccessor(), options = ko.unwrap(accessor.options), enable = _.has(accessor, 'enable') ? ko.unwrap(accessor.enable) : true, editable = _.has(accessor, 'editable') ? ko.unwrap(accessor.editable) : false, required = _.has(accessor, 'required') ? ko.unwrap(accessor.required) : false, optionsText = _.has(accessor, 'optionsText') ? ko.unwrap(accessor.optionsText) : null, optionsValue = _.has(accessor, 'optionsValue') ? ko.unwrap(accessor.optionsValue) : null, columns = _.has(accessor, 'columns') ? ko.unwrap(accessor.columns) : [{ prop: optionsText }], visibleItemsCount = _.has(accessor, 'visibleItemsCount') ? ko.unwrap(accessor.visibleItemsCount) : 5, dropDownAttachedToBody = _.has(accessor, 'dropDownAttachedToBody') ? ko.unwrap(accessor.dropDownAttachedToBody) : false, $show = $('<div>', {
                                'class': 'nts-toggle-dropdown',
                                'style': 'padding-left: 2px; color: #000; height: 29px',
                                click: function (evt) {
                                    if ($element.data(IGCOMB)) {
                                        if ($element.igCombo(OPENED)) {
                                            $element.igCombo(CLOSEDDL);
                                            evt.stopPropagation();
                                        }
                                        else {
                                            $element.igCombo(OPENDDL, function () { }, true, true);
                                            evt.stopPropagation();
                                        }
                                    }
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
                                        _template = _template.replace("${" + k + "}", option[k]);
                                    });
                                    $show.html(_template);
                                    _.each(ks, function (k) {
                                        $show.find("." + k.toLowerCase() + ":not(:last-child)")
                                            .css('width', cws[k] * WoC + "px");
                                        $show.find("." + k.toLowerCase())
                                            .css('height', '31px')
                                            .css('line-height', '27px')
                                            .find('.nts-column:last-child').css('margin-right', 0);
                                        ;
                                    });
                                }
                                else {
                                    $show.empty();
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
                                }
                                else {
                                    $element
                                        .removeClass('error')
                                        .ntsError("clear");
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
                                mode: editable ? EDITABLE : DROPDOWN,
                                disabled: !ko.toJS(enable),
                                enableClearButton: false,
                                itemTemplate: template,
                                dropDownWidth: "auto",
                                tabIndex: $element.attr('tabindex') || 0,
                                visibleItemsCount: visibleItemsCount,
                                dropDownAttachedToBody: dropDownAttachedToBody,
                                rendered: function (evt, ui) {
                                    $element
                                        .find('.ui-igcombo')
                                        .css('background', '#f6f6f6')
                                        .find('.ui-igcombo-fieldholder').hide();
                                    $element
                                        .find('.ui-igcombo-hidden-field')
                                        .parent()
                                        .append($show)
                                        .css('overflow', 'hidden');
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
                                    $element.trigger(CHANGED, [CHANGED, true]);
                                    setTimeout(function () {
                                        var data = $element.data(DATA);
                                        if (!data[EDITABLE] && !data[VALUE]) {
                                            $element.trigger(CHANGED, [VALUE, $element.igCombo('value')]);
                                            data = $element.data(DATA);
                                        }
                                        accessor.value(data[VALUE]);
                                        $element
                                            .trigger(VALIDATE, [true])
                                            .trigger(SHOWVALUE)
                                            .focus();
                                    }, 10);
                                },
                                dropDownOpening: function (evt, ui) {
                                    var data = $element.data(DATA), cws = data[CWIDTH], ks = _.keys(cws);
                                    $element
                                        .find('.ui-igcombo-fieldholder')
                                        .prependTo(ui.list);
                                    var $input = ui.list
                                        .find('.ui-igcombo-fieldholder')
                                        .css('height', !!data[EDITABLE] ? '' : '0px')
                                        .css('padding', !!data[EDITABLE] ? '3px' : '')
                                        .css('background-color', !!data[EDITABLE] ? '#f6f6f6' : '')
                                        .show()
                                        .find('input')
                                        .css('width', '0px')
                                        .css('height', !!data[EDITABLE] ? '29px' : '0px')
                                        .css('border', !!data[EDITABLE] ? '1px solid #ccc' : 'none');
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
                                    setTimeout(function () {
                                        $input.css('width', ($(ui.list).width() - 6) + 'px');
                                    }, 25);
                                }
                            })
                                .trigger(CHANGED, [DATA, options])
                                .addClass('ntsControl')
                                .on('blur', function () { $element.css('box-shadow', ''); })
                                .on('focus', function () {
                                $element
                                    .css('outline', 'none')
                                    .css('box-shadow', '0 0 1px 1px #0096f2');
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
                                        value = undefined;
                                    }
                                    else {
                                        value = vio[optionsValue];
                                    }
                                }
                                else {
                                    value = undefined;
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
                            if ($element.data("igCombo")) {
                                var data = $element.data(DATA), olds = data[DATA];
                                if (!_.isEqual(olds, options)) {
                                    $element.igCombo(OPTION, "dataSource", options);
                                }
                                $element
                                    .igCombo(OPTION, "disabled", !enable)
                                    .igCombo("value", value);
                                $element
                                    .trigger(VALIDATE, [true]);
                                if (!value) {
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
//# sourceMappingURL=combobox-ko-ext.js.map