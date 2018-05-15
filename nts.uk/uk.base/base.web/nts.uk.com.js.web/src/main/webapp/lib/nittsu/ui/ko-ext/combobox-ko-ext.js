var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var notSelected;
                (function (notSelected) {
                    var DATA = "not-selected";
                    function set($element, value) {
                        $element.data(DATA, value);
                    }
                    notSelected.set = set;
                    function get($element) {
                        return $element.data(DATA) === true;
                    }
                    notSelected.get = get;
                })(notSelected || (notSelected = {}));
                var required;
                (function (required) {
                    var DATA = "required";
                    function set($element, value) {
                        $element.data(DATA, value);
                    }
                    required.set = set;
                    function get($element) {
                        return $element.data(DATA) === true;
                    }
                    required.get = get;
                })(required || (required = {}));
                var controlName;
                (function (controlName) {
                    var DATA = "control-name";
                    function set($element, value) {
                        $element.data(DATA, value);
                    }
                    controlName.set = set;
                    function get($element) {
                        return $element.data(DATA);
                    }
                    controlName.get = get;
                })(controlName || (controlName = {}));
                var ComboBoxBindingHandler = (function () {
                    function ComboBoxBindingHandler() {
                    }
                    ComboBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var container = $(element);
                        container.data("tabindex", container.attr("tabindex"));
                        container.removeAttr("tabindex");
                        container.keypress(function (evt, ui) {
                            var code = evt.which || evt.keyCode;
                            if (code === 32) {
                                container.igCombo("openDropDown");
                                evt.preventDefault();
                            }
                        });
                        container.bind("validate", function () {
                            if (required.get(container) && notSelected.get(container)) {
                                container.ntsError("set", uk.resource.getMessage("FND_E_REQ_SELECT", [controlName.get(container)]), "FND_E_REQ_SELECT");
                            }
                            else {
                                container.ntsError("clear");
                            }
                        });
                        ui.bindErrorStyle.useDefaultErrorClass(container);
                    };
                    ComboBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var self = this;
                        var options = ko.unwrap(data.options);
                        var optionValue = data.optionsValue === undefined ? null : ko.unwrap(data.optionsValue);
                        var optionText = data.optionsText === undefined ? null : ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var editable = ko.unwrap(data.editable);
                        var isRequired = ko.unwrap(data.required) === true;
                        var enable = data.enable !== undefined ? ko.unwrap(data.enable) : true;
                        var selectFirstIfNull = !(ko.unwrap(data.selectFirstIfNull) === false);
                        var columns = ko.unwrap(data.columns);
                        var visibleItemsCount = data.visibleItemsCount === undefined ? 5 : ko.unwrap(data.visibleItemsCount);
                        var dropDownAttachedToBody = data.dropDownAttachedToBody === undefined ? null : ko.unwrap(data.dropDownAttachedToBody);
                        if (dropDownAttachedToBody === null) {
                            if ($(element).closest(".ui-iggrid").length != 0)
                                dropDownAttachedToBody = true;
                            else
                                dropDownAttachedToBody = false;
                        }
                        var container = $(element);
                        var comboMode = editable ? 'editable' : 'dropdown';
                        controlName.set(container, ko.unwrap(data.name));
                        var distanceColumns = '     ';
                        var fillCharacter = ' ';
                        var maxWidthCharacter = 15;
                        var defVal = new nts.uk.util.value.DefaultValue().onReset(container, data.value);
                        var getValue = function (item) {
                            return optionValue === null ? item : item[optionValue];
                        };
                        required.set(container, isRequired);
                        if (selectFirstIfNull && options.length !== 0 && uk.util.isNullOrEmpty(selectedValue)) {
                            selectedValue = getValue(options[0]);
                            data.value(selectedValue);
                            notSelected.set(container, false);
                            container.ntsError("clear");
                        }
                        else {
                            var isValidValue = !uk.util.isNullOrUndefined(selectedValue) && _.some(options, function (item) { return getValue(item) === selectedValue; });
                            notSelected.set(container, !isValidValue);
                            if (!isValidValue) {
                                notSelected.set(container, true);
                            }
                            else {
                                notSelected.set(container, false);
                                container.ntsError("clear");
                            }
                        }
                        var haveColumn = columns && columns.length > 0;
                        var isChangeOptions = !_.isEqual(container.data("options"), options);
                        if (isChangeOptions) {
                            container.data("options", options.slice());
                            options = options.map(function (option) {
                                var newOptionText = '';
                                if (haveColumn) {
                                    _.forEach(columns, function (item, i) {
                                        var prop = option[item.prop];
                                        if (uk.util.isNullOrUndefined(prop)) {
                                            prop = "";
                                        }
                                        var length = item.length;
                                        if (i === columns.length - 1) {
                                            newOptionText += prop;
                                        }
                                        else {
                                            newOptionText += uk.text.padRight(prop, fillCharacter, length) + distanceColumns;
                                        }
                                    });
                                }
                                else {
                                    newOptionText = optionText === null ? option : option[optionText];
                                }
                                option['nts-combo-label'] = newOptionText;
                                return option;
                            });
                        }
                        var $input = container.find(".ui-igcombo-field");
                        var currentColumnSetting = container.data("columns");
                        var currentComboMode = container.data("comboMode");
                        var isInitCombo = !_.isEqual(currentColumnSetting, columns) || !_.isEqual(currentComboMode, comboMode);
                        if (isInitCombo) {
                            if (container.data("igCombo") != null) {
                                container.igCombo('destroy');
                                container.removeClass('ui-state-disabled');
                            }
                            var itemTemplate = undefined;
                            if (haveColumn) {
                                itemTemplate = '<div class="nts-combo-item">';
                                _.forEach(columns, function (item, i) {
                                    itemTemplate += '<div class="nts-column nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                                });
                                itemTemplate += '</div>';
                            }
                            container.igCombo({
                                dataSource: options,
                                valueKey: data.optionsValue,
                                visibleItemsCount: visibleItemsCount,
                                dropDownAttachedToBody: dropDownAttachedToBody,
                                textKey: 'nts-combo-label',
                                mode: comboMode,
                                disabled: !enable,
                                placeHolder: '',
                                tabIndex: nts.uk.util.isNullOrEmpty(container.data("tabindex")) ? 0 : parseInt(container.data("tabindex")),
                                enableClearButton: false,
                                initialSelectedItems: [
                                    { value: selectedValue }
                                ],
                                itemTemplate: itemTemplate,
                                selectionChanged: function (evt, ui) {
                                    if (ui.items.length > 0) {
                                        data.value(getValue(ui.items[0].data));
                                    }
                                }
                            });
                            $input = container.find(".ui-igcombo-field");
                            $input.focus(function (evt, ui) {
                                $input[0].selectionStart = 0;
                                $input[0].selectionEnd = 0;
                            });
                        }
                        else {
                            container.igCombo("option", "disabled", !enable);
                        }
                        if (!enable) {
                            defVal.applyReset(container, data.value);
                            $input.attr("disabled", "disabled");
                        }
                        else {
                            $input.removeAttr("disabled");
                        }
                        if (isChangeOptions && !isInitCombo) {
                            container.igCombo("option", "dataSource", options);
                            container.igCombo("dataBind");
                        }
                        if (notSelected.get(container) || uk.util.isNullOrUndefined(selectedValue)) {
                            container.igCombo("value", "");
                        }
                        else {
                            container.igCombo("value", selectedValue);
                        }
                        container.data("columns", _.cloneDeep(columns));
                        container.data("comboMode", comboMode);
                        var isDropDownWidthSpecified = false;
                        if (haveColumn && (isChangeOptions || isInitCombo)) {
                            var componentWidth = 0;
                            var $dropDownOptions = $(container.igCombo("dropDown"));
                            _.forEach(columns, function (item, i) {
                                isDropDownWidthSpecified = (isDropDownWidthSpecified || item.lengthDropDown !== undefined);
                                if (item.lengthDropDown === undefined) {
                                    item.lengthDropDown = item.length;
                                }
                                var componentColumnWidth = item.length * maxWidthCharacter + 10;
                                var dropDownColumnWidth = item.lengthDropDown * maxWidthCharacter + 10;
                                $dropDownOptions.find('.nts-combo-column-' + i).css("width", dropDownColumnWidth);
                                componentWidth += componentColumnWidth + 10;
                            });
                            container.css({ 'min-width': componentWidth });
                            if (isDropDownWidthSpecified) {
                                container.find(".ui-igcombo-dropdown").css("width", "auto");
                            }
                        }
                        if (notSelected.get(container)) {
                        }
                    };
                    return ComboBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=combobox-ko-ext.js.map