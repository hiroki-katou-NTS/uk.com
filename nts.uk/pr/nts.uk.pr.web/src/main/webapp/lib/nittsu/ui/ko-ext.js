var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;
                /**
                 * TextEditor
                 */
                var NtsTextEditorBindingHandler = (function () {
                    function NtsTextEditorBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsTextEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setValue = data.value;
                        var $input = $(element);
                        this.constraint = (data.constraint !== undefined) ? validation.getCharType(data.constraint) : validation.getCharType("");
                        $input.change(function () {
                            var newText = $input.val();
                            var result = validation.parseTime(newText);
                            if (result.success) {
                                $input.ntsError('clear');
                                setValue(result.format());
                            }
                            else {
                                $input.ntsError('set', 'invalid text');
                                setValue(newText);
                            }
                        });
                    };
                    /**
                     * Update
                     */
                    NtsTextEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var getValue = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : true;
                        var option = (data.option !== undefined) ? ko.unwrap(data.option) : ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption());
                        var textmode = ko.unwrap(option.textmode);
                        var placeholder = ko.unwrap(option.placeholder);
                        var width = ko.unwrap(option.width);
                        var textalign = ko.unwrap(option.textalign);
                        var $input = $(element);
                        $input.attr('type', textmode);
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        if (width.trim() != "")
                            $input.width(width);
                        if (textalign.trim() != "")
                            $input.css('text-align', textalign);
                        var newText = getValue();
                        $input.val(newText);
                    };
                    return NtsTextEditorBindingHandler;
                }());
                /**
                 * NumberEditor
                 */
                var NtsNumberEditorBindingHandler = (function () {
                    function NtsNumberEditorBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsNumberEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setValue = data.value;
                        var option = (viewModel.option !== undefined) ? ko.unwrap(viewModel.option) : ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption());
                        this.constraint = (data.constraint !== undefined) ? validation.getCharType(data.constraint) : validation.getCharType("");
                        var $input = $(element);
                        $input.change(function () {
                            var newText = $input.val();
                            if (validation.isDecimal(newText, option)) {
                                $input.ntsError('clear');
                            }
                            else {
                                $input.ntsError('set', 'invalid number');
                            }
                            setValue(newText);
                        });
                    };
                    /**
                     * Update
                     */
                    NtsNumberEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var getValue = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : true;
                        var option = (viewModel.option !== undefined) ? ko.unwrap(viewModel.option) : ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption());
                        var placeholder = ko.unwrap(option.placeholder);
                        var width = ko.unwrap(option.width);
                        var textalign = ko.unwrap(option.textalign);
                        var $input = $(element);
                        $input.attr('type', 'text');
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        if (width.trim() != "")
                            $input.width(width);
                        if (textalign.trim() != "")
                            $input.css('text-align', textalign);
                        var newText = getValue();
                        newText = uk.text.formatNumber(validation.isDecimal(newText) ? parseFloat(newText)
                            : parseFloat(newText.toString().replace(option.groupseperator(), '')), option);
                        $input.val(newText);
                    };
                    return NtsNumberEditorBindingHandler;
                }());
                /**
                 * TimeEditor
                 */
                var NtsTimeEditorBindingHandler = (function () {
                    function NtsTimeEditorBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsTimeEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setValue = data.value;
                        var option = (data.option !== undefined) ? ko.unwrap(data.option) : ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption());
                        var $input = $(element);
                        $input.change(function () {
                            var newText = $input.val();
                            var result;
                            if (option.inputFormat() === "yearmonth") {
                                result = validation.parseYearMonth(newText);
                            }
                            else {
                                result = validation.parseTime(newText);
                            }
                            if (result.success) {
                                $input.ntsError('clear');
                                $input.val(result.format());
                                setValue(result.toValue());
                            }
                            else {
                                $input.ntsError('set', 'invalid time');
                                setValue(newText);
                            }
                        });
                    };
                    /**
                     * Update
                     */
                    NtsTimeEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var getValue = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : true;
                        var option = (viewModel.option !== undefined) ? ko.unwrap(viewModel.option) : ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption());
                        var placeholder = ko.unwrap(option.placeholder);
                        var width = ko.unwrap(option.width);
                        var textalign = ko.unwrap(option.textalign);
                        var $input = $(element);
                        $input.attr('type', 'text');
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        if (width.trim() != "")
                            $input.width(width);
                        if (textalign.trim() != "")
                            $input.css('text-align', textalign);
                        var result;
                        if (option.inputFormat() === "yearmonth") {
                            result = validation.parseYearMonth(data.value());
                        }
                        else {
                            result = validation.parseTime(data.value());
                        }
                        if (result.success) {
                            $input.val(result.format());
                        }
                        else {
                            $input.val(data.value());
                        }
                    };
                    return NtsTimeEditorBindingHandler;
                }());
                /**
                 * MaskEditor
                 */
                var NtsMaskEditorBindingHandler = (function () {
                    function NtsMaskEditorBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsMaskEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setValue = data.value;
                        this.constraint = (data.constraint !== undefined) ? validation.getCharType(data.constraint) : validation.getCharType("");
                        var $input = $(element);
                        $input.change(function () {
                            var newText = $input.val();
                            setValue(newText);
                        });
                    };
                    /**
                     * Update
                     */
                    NtsMaskEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var getValue = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : true;
                        var option = (viewModel.option !== undefined) ? ko.unwrap(viewModel.option) : ko.mapping.fromJS(new nts.uk.ui.option.MaskEditorOption());
                        var placeholder = ko.unwrap(option.placeholder);
                        var width = ko.unwrap(option.width);
                        var textalign = ko.unwrap(option.textalign);
                        var $input = $(element);
                        $input.attr('type', 'text');
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        if (width.trim() != "")
                            $input.width(width);
                        if (textalign.trim() != "")
                            $input.css('text-align', textalign);
                        var newText = getValue();
                        $input.val(newText);
                    };
                    return NtsMaskEditorBindingHandler;
                }());
                /**
                 * Multi Checkbox
                 */
                var NtsMultiCheckBoxBindingHandler = (function () {
                    function NtsMultiCheckBoxBindingHandler() {
                    }
                    NtsMultiCheckBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        element.innerHTML = "<input type='checkbox' data-bind='checked: isChecked, checkedValue: item' /><label data-bind='text: content'></label>";
                        /*var childBindingContext = bindingContext.createChildContext(
                                bindingContext.$rawData,
                                null, // Optionally, pass a string here as an alias for the data item in descendant contexts
                                function(context) {
                                    ko.utils.extend(context, valueAccessor());
                                });*/
                        var childBindingContext = bindingContext.extend(valueAccessor);
                        ko.applyBindingsToDescendants(childBindingContext, element);
                        return { controlsDescendantBindings: true };
                    };
                    NtsMultiCheckBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return NtsMultiCheckBoxBindingHandler;
                }());
                /**
                 * Dialog binding handler
                 */
                var NtsDialogBindingHandler = (function () {
                    function NtsDialogBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsDialogBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    /**
                     * Update
                     */
                    NtsDialogBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var message = ko.unwrap(data.message);
                        var modal = ko.unwrap(option.modal);
                        var show = ko.unwrap(option.show);
                        var buttons = ko.unwrap(option.buttons);
                        var $dialog = $("<div id='ntsDialog'></div>");
                        if (show == true) {
                            $('body').append($dialog);
                            // Create Buttons
                            var dialogbuttons = [];
                            var _loop_1 = function(button) {
                                dialogbuttons.push({
                                    text: ko.unwrap(button.text),
                                    "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                    click: function () { button.click(bindingContext.$data, $dialog); }
                                });
                            };
                            for (var _i = 0, buttons_1 = buttons; _i < buttons_1.length; _i++) {
                                var button = buttons_1[_i];
                                _loop_1(button);
                            }
                            // Create dialog
                            $dialog.dialog({
                                title: title,
                                modal: modal,
                                closeOnEscape: false,
                                buttons: dialogbuttons,
                                dialogClass: "no-close",
                                open: function () {
                                    $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                                    $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                                    $('.ui-widget-overlay').last().css('z-index', 120000);
                                },
                                close: function (event) {
                                    bindingContext.$data.option.show(false);
                                }
                            }).text(message);
                        }
                        else {
                            // Destroy dialog
                            if ($('#ntsDialog').dialog("instance") != null)
                                $('#ntsDialog').dialog("destroy");
                            $('#ntsDialog').remove();
                        }
                    };
                    return NtsDialogBindingHandler;
                }());
                /**
                 * Error Dialog binding handler
                 */
                var NtsErrorDialogBindingHandler = (function () {
                    function NtsErrorDialogBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsErrorDialogBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var headers = ko.unwrap(option.headers);
                        var modal = ko.unwrap(option.modal);
                        var show = ko.unwrap(option.show);
                        var buttons = ko.unwrap(option.buttons);
                        var $dialog = $("<div id='ntsErrorDialog'></div>");
                        $('body').append($dialog);
                        // Create Buttons
                        var dialogbuttons = [];
                        var _loop_2 = function(button) {
                            dialogbuttons.push({
                                text: ko.unwrap(button.text),
                                "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                click: function () { button.click(bindingContext.$data, $dialog); }
                            });
                        };
                        for (var _i = 0, buttons_2 = buttons; _i < buttons_2.length; _i++) {
                            var button = buttons_2[_i];
                            _loop_2(button);
                        }
                        // Calculate width
                        var dialogWidth = 40 + 35 + 17;
                        headers.forEach(function (header, index) {
                            if (ko.unwrap(header.visible)) {
                                dialogWidth += ko.unwrap(header.width);
                            }
                        });
                        // Create dialog
                        $dialog.dialog({
                            title: title,
                            modal: modal,
                            autoOpen: show,
                            closeOnEscape: false,
                            width: dialogWidth,
                            buttons: dialogbuttons,
                            dialogClass: "no-close",
                            open: function () {
                                $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                                $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                                $('.ui-widget-overlay').last().css('z-index', 120000);
                            },
                            close: function (event) {
                                bindingContext.$data.option.show(false);
                            }
                        });
                    };
                    /**
                     * Update
                     */
                    NtsErrorDialogBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var errors = ko.unwrap(data.errors);
                        var headers = ko.unwrap(option.headers);
                        var displayrows = ko.unwrap(option.displayrows);
                        var maxrows = ko.unwrap(option.maxrows);
                        var autoclose = ko.unwrap(option.autoclose);
                        var show = ko.unwrap(option.show);
                        var $dialog = $("#ntsErrorDialog");
                        if (autoclose === true && errors.length == 0)
                            show = false;
                        if (show == true) {
                            $dialog.dialog("open");
                            // Create Error Table
                            var $errorboard = $("<div id='error-board'></div>");
                            var $errortable = $("<table></table>");
                            // Header
                            var $header = $("<thead><tr></tr></thead>");
                            $header.find("tr").append("<th style='width: 35px'></th>");
                            headers.forEach(function (header, index) {
                                if (ko.unwrap(header.visible)) {
                                    var $headerElement = $("<th>" + ko.unwrap(header.text) + "</th>").width(ko.unwrap(header.width));
                                    $header.find("tr").append($headerElement);
                                }
                            });
                            $errortable.append($header);
                            // Body
                            var $body = $("<tbody></tbody>");
                            errors.forEach(function (error, index) {
                                if (index < maxrows) {
                                    // Row
                                    var $row_1 = $("<tr></tr>");
                                    $row_1.append("<td style='width:35px'>" + (index + 1) + "</td>");
                                    headers.forEach(function (header) {
                                        if (ko.unwrap(header.visible))
                                            if (error.hasOwnProperty(ko.unwrap(header.name))) {
                                                // TD
                                                var $column = $("<td>" + error[ko.unwrap(header.name)] + "</td>").width(ko.unwrap(header.width));
                                                $row_1.append($column);
                                            }
                                    });
                                    $body.append($row_1);
                                }
                            });
                            $errortable.append($body);
                            $errorboard.append($errortable);
                            // Errors over maxrows message
                            var $message = $("<div></div>");
                            if (errors.length > maxrows)
                                $message.text("Showing " + maxrows + " in total " + errors.length + " errors");
                            $dialog.html("");
                            $dialog.append($errorboard).append($message);
                            // Calculate body height base on displayrow
                            $body.height(Math.min(displayrows, errors.length) * $(">:first-child", $body).outerHeight() + 1);
                        }
                        else {
                            $dialog.dialog("close");
                        }
                    };
                    return NtsErrorDialogBindingHandler;
                }());
                /**
                 * Switch button binding handler
                 */
                var NtsSwitchButtonBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function NtsSwitchButtonBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsSwitchButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    /**
                     * Update
                     */
                    NtsSwitchButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var selectedCssClass = 'selected';
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        // Container.
                        var container = $(element);
                        // Remove deleted button.
                        $('button', container).each(function (index, btn) {
                            var $btn = $(btn);
                            var btnValue = $(btn).data('swbtn');
                            // Check if btn is contained in options.
                            var foundFlag = _.findIndex(options, function (opt) {
                                return opt[optionValue] == btnValue;
                            }) != -1;
                            if (!foundFlag) {
                                $btn.remove();
                                return;
                            }
                        });
                        // Start binding new state.
                        _.forEach(options, function (opt) {
                            var value = opt[optionValue];
                            var text = opt[optionText];
                            // Find button.
                            var targetBtn;
                            $('button', container).each(function (index, btn) {
                                var btnValue = $(btn).data('swbtn');
                                if (btnValue == value) {
                                    targetBtn = $(btn);
                                }
                                if (btnValue == selectedValue) {
                                    $(btn).addClass(selectedCssClass);
                                }
                                else {
                                    $(btn).removeClass(selectedCssClass);
                                }
                            });
                            if (targetBtn) {
                            }
                            else {
                                // Recreate
                                var btn = $('<button>').text(text)
                                    .addClass('nts-switch-button')
                                    .data('swbtn', value)
                                    .on('click', function () {
                                    var selectedValue = $(this).data('swbtn');
                                    data.value(selectedValue);
                                    $('button', container).removeClass(selectedCssClass);
                                    $(this).addClass(selectedCssClass);
                                });
                                if (selectedValue == value) {
                                    btn.addClass(selectedCssClass);
                                }
                                container.append(btn);
                            }
                        });
                    };
                    return NtsSwitchButtonBindingHandler;
                }());
                var NtsCheckboxBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function NtsCheckboxBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsCheckboxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        $(element).addClass("ntsCheckBox");
                    };
                    /**
                     * Update
                     */
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Container.
                        var container = $(element);
                        // Get options.
                        var checked = ko.unwrap(data.checked);
                        var enable = ko.unwrap(data.enable);
                        var textId = data.text;
                        var checkBoxText;
                        if (textId) {
                            checkBoxText = (textId);
                        }
                        else {
                            checkBoxText = container.text();
                            container.text('');
                        }
                        var checkBox;
                        if ($('input[type="checkbox"]', container).length == 0) {
                            // Init new.
                            checkBox = $('<input type="checkbox">').appendTo(container);
                            var checkBoxLabel = $("<label><span></span></label>").appendTo(container).append(checkBoxText);
                            $(container).on('click', "label", function () {
                                // Do nothing if disable.
                                if (container.hasClass('disabled')) {
                                    return;
                                }
                                // Change value.
                                checkBox.prop("checked", !checkBox.prop("checked"));
                                data.checked(checkBox.prop("checked"));
                            });
                        }
                        else {
                            checkBox = $('input[type="checkbox"]', container);
                        }
                        // Set state.
                        checkBox.prop("checked", checked);
                        // Disable.
                        if (enable == false) {
                            container.addClass('disabled');
                        }
                        else {
                            container.removeClass('disabled');
                        }
                    };
                    return NtsCheckboxBindingHandler;
                }());
                /**
                 * ComboBox binding handler
                 */
                var ComboBoxBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function ComboBoxBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    ComboBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    /**
                     * Update
                     */
                    ComboBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var self = this;
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var editable = data.editable;
                        var enable = data.enable;
                        var columns = data.columns;
                        // Container.
                        var container = $(element);
                        var comboMode = editable ? 'editable' : 'dropdown';
                        // Default values.
                        var distanceColumns = '     ';
                        var fillCharacter = ' '; // Character used fill to the columns.
                        var maxWidthCharacter = 15;
                        // Check selected code.
                        if (options.filter(function (item) { return item[optionValue] === selectedValue; }).length == 0 && !editable) {
                            selectedValue = options.length > 0 ? options[0][optionValue] : '';
                            data.value(selectedValue);
                        }
                        // Delete igCombo.
                        if (container.data("igCombo") != null) {
                            container.igCombo('destroy');
                            container.removeClass('ui-state-disabled');
                        }
                        // Set attribute for multi column.
                        var itemTempalate = undefined;
                        options = options.map(function (option) {
                            var newOptionText = '';
                            // Check muti columns.
                            if (columns && columns.length > 0) {
                                var i = 0;
                                itemTempalate = '<div class="nts-combo-item">';
                                columns.forEach(function (item) {
                                    var prop = option[item.prop];
                                    var length = item.length;
                                    var proLength = prop.length;
                                    while (proLength < length && i != columns.length - 1) {
                                        // Add space character to properties.
                                        prop += fillCharacter;
                                        proLength++;
                                    }
                                    if (i == columns.length - 1) {
                                        newOptionText += prop;
                                    }
                                    else {
                                        newOptionText += prop + distanceColumns;
                                    }
                                    // Set item template.
                                    itemTempalate += '<div class="nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                                    i++;
                                });
                                itemTempalate += '</div>';
                            }
                            else {
                                newOptionText = option[optionText];
                            }
                            // Add label attr.
                            option['nts-combo-label'] = newOptionText;
                            return option;
                        });
                        // Create igCombo.
                        container.igCombo({
                            dataSource: options,
                            valueKey: data.optionsValue,
                            textKey: 'nts-combo-label',
                            mode: comboMode,
                            disabled: !enable,
                            placeHolder: '',
                            enableClearButton: false,
                            initialSelectedItems: [
                                { value: selectedValue }
                            ],
                            itemTemplate: itemTempalate,
                            selectionChanged: function (evt, ui) {
                                if (ui.items.length > 0) {
                                    data.value(ui.items[0].data[optionValue]);
                                }
                            }
                        });
                        // Set width for multi columns.
                        if (columns && columns.length > 0) {
                            var i = 0;
                            var totalWidth = 0;
                            columns.forEach(function (item) {
                                var length = item.length;
                                $('.nts-combo-column-' + i).width(length * maxWidthCharacter + 10);
                                if (i != columns.length - 1) {
                                    $('.nts-combo-column-' + i).css({ 'float': 'left' });
                                }
                                totalWidth += length * maxWidthCharacter + 10;
                                i++;
                            });
                            $('.nts-combo-item').css({ 'min-width': totalWidth });
                            container.css({ 'min-width': totalWidth });
                        }
                    };
                    return ComboBoxBindingHandler;
                }());
                /**
                 * ListBox binding handler
                 */
                var ListBoxBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function ListBoxBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    ListBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = data.multiple;
                        var enable = data.enable;
                        var columns = data.columns;
                        var rows = data.rows;
                        // Container.
                        var container = $(element);
                        // Default value.
                        var selectSize = 6;
                        // Create select.
                        container.append('<ol class="nts-list-box"></ol>');
                        var selectListBoxContainer = container.find('.nts-list-box');
                        // Create changing event.
                        var changeEvent = new CustomEvent("selectionChange", {
                            detail: {},
                            bubbles: true,
                            cancelable: true,
                        });
                        // Bind selectable.
                        selectListBoxContainer.selectable({
                            selected: function (event, ui) {
                            },
                            stop: function (event, ui) {
                                // If not Multi Select.
                                if (!isMultiSelect) {
                                    $(event.target).children('.ui-selected').not(':first').removeClass('ui-selected');
                                    $(event.target).children('li').children('.ui-selected').removeClass('ui-selected');
                                }
                                // Add selected value.
                                var data = isMultiSelect ? [] : '';
                                var i = 0;
                                $("li.ui-selected").each(function (index, opt) {
                                    var optValue = $(opt).data('value');
                                    if (!isMultiSelect) {
                                        data = optValue;
                                        return;
                                    }
                                    data[i] = optValue;
                                    i++;
                                });
                                container.data('value', data);
                                // fire event change.
                                document.getElementById(container.attr('id')).dispatchEvent(changeEvent);
                            },
                            unselecting: function (event, ui) {
                                $(event.target).children('li').not('.ui-selected').children('.ui-selected').removeClass('ui-selected');
                            }
                        });
                        // Fire event.
                        container.on('selectionChange', (function (e) {
                            // Check is multi-selection.
                            var itemsSelected = container.data('value');
                            // Create changing event.
                            var changingEvent = new CustomEvent("selectionChanging", {
                                detail: itemsSelected,
                                bubbles: true,
                                cancelable: true,
                            });
                            // Dispatch/Trigger/Fire the event => use event.detai to get selected value.
                            document.getElementById(container.attr('id')).dispatchEvent(changingEvent);
                            if (!changingEvent.returnValue) {
                                // revert select.
                                $(this).val(selectedValue);
                                data.value(selectedValue);
                            }
                            else {
                                data.value(itemsSelected);
                                // Create event changed.
                                var changedEvent = new CustomEvent("selectionChanged", {
                                    detail: itemsSelected,
                                    bubbles: true,
                                    cancelable: false
                                });
                                // Dispatch/Trigger/Fire the event => use event.detai to get selected value.
                                document.getElementById(container.attr('id')).dispatchEvent(changedEvent);
                            }
                        }));
                        // Create method.
                        $.fn.deselectAll = function () {
                            $(this.selector).data('value', '');
                            $(this.selector + ' > .nts-list-box > li').removeClass("ui-selected");
                            $(this.selector + ' > .nts-list-box > li > div').removeClass("ui-selected");
                            $(this.selector).trigger("selectionChange");
                        };
                        $.fn.selectAll = function () {
                            $(this.selector + ' > .nts-list-box > li').addClass("ui-selected");
                            $(this.selector + ' > .nts-list-box').data("ui-selectable")._mouseStop(null);
                        };
                        $.fn.ntsListBox = function (method) {
                            switch (method) {
                                case 'deselectAll':
                                    this.deselectAll();
                                    break;
                                case 'selectAll':
                                    this.selectAll();
                                    break;
                                default:
                                    break;
                            }
                        };
                    };
                    /**
                     * Update
                     */
                    ListBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = data.multiple;
                        var enable = data.enable;
                        var columns = data.columns;
                        var rows = data.rows;
                        // Container.
                        var container = $(element);
                        var selectListBoxContainer = container.find('.nts-list-box');
                        var maxWidthCharacter = 15;
                        var getOptionValue = function (item) {
                            if (optionValue === undefined) {
                                return item;
                            }
                            else {
                                return item[optionValue];
                            }
                        };
                        // Check selected code.
                        if (!isMultiSelect && options.filter(function (item) { return getOptionValue(item) === selectedValue; }).length == 0) {
                            selectedValue = '';
                        }
                        // Remove options.
                        $('li', container).each(function (index, option) {
                            var optValue = $(option).data('value');
                            // Check if btn is contained in options.
                            var foundFlag = _.findIndex(options, function (opt) {
                                return getOptionValue(opt) == optValue;
                            }) != -1;
                            if (!foundFlag) {
                                // Remove selected if not found option.
                                selectedValue = jQuery.grep(selectedValue, function (value) {
                                    return value != optValue;
                                });
                                option.remove();
                                return;
                            }
                        });
                        // Append options.
                        options.forEach(function (item) {
                            // Find option.
                            var targetOption;
                            $('li', container).each(function (index, opt) {
                                var optValue = $(opt).data('value');
                                if (optValue == getOptionValue(item)) {
                                    targetOption = $(opt);
                                    return;
                                }
                            });
                            // Check option is Selected.
                            var isSelected = false;
                            if (isMultiSelect) {
                                isSelected = selectedValue.indexOf(getOptionValue(item)) != -1;
                            }
                            else {
                                isSelected = selectedValue == getOptionValue(item);
                            }
                            if (!targetOption) {
                                // Add option.
                                var selectedClass = isSelected ? 'ui-selected' : '';
                                var itemTemplate = '';
                                if (columns && columns.length > 0) {
                                    var i = 0;
                                    columns.forEach(function (col) {
                                        var prop = item[col.prop];
                                        itemTemplate += '<div class="nts-column nts-list-box-column-' + i + '">' + prop + '</div>';
                                        i++;
                                    });
                                }
                                else {
                                    itemTemplate = '<div class="nts-column nts-list-box-column-0">' + item[optionText] + '</div>';
                                }
                                $('<li/>')
                                    .addClass(selectedClass)
                                    .html(itemTemplate)
                                    .data('value', getOptionValue(item))
                                    .appendTo(selectListBoxContainer);
                            }
                            else {
                                if (isSelected) {
                                    targetOption.addClass('ui-selected');
                                }
                                else {
                                    targetOption.removeClass('ui-selected');
                                }
                            }
                        });
                        // Set value.
                        container.data('value', selectedValue);
                        container.trigger('selectionChange');
                        // Check enable.
                        if (!enable) {
                            selectListBoxContainer.selectable("disable");
                            ;
                            container.addClass('disabled');
                        }
                        else {
                            selectListBoxContainer.selectable("enable");
                            container.removeClass('disabled');
                        }
                        var padding = 10;
                        // Set width for multi columns.
                        if (columns && columns.length > 0) {
                            var i = 0;
                            var totalWidth = 0;
                            columns.forEach(function (item) {
                                var length = item.length;
                                $('.nts-list-box-column-' + i).width(length * maxWidthCharacter + 20);
                                totalWidth += length * maxWidthCharacter + 20;
                                i++;
                            });
                            if ($('.nts-column').css('padding')) {
                                var ntsCommonPadding = $('.nts-column').css('padding').split('px')[0];
                                padding = parseInt(ntsCommonPadding) * 2;
                            }
                            totalWidth += padding * (columns.length + 1); // + 50;
                            $('.nts-list-box > li').css({ 'min-width': totalWidth });
                            $('.nts-list-box').css({ 'min-width': totalWidth });
                            container.css({ 'min-width': totalWidth });
                        }
                        if (rows && rows > 0) {
                            container.css({ 'height': rows * (18 + padding) });
                            $('.nts-list-box').css({ 'height': rows * (18 + padding) });
                            container.css({ 'overflowX': 'hidden', 'overflowY': 'auto' });
                        }
                    };
                    return ListBoxBindingHandler;
                }());
                /**
                 * TreeGrid binding handler
                 */
                var NtsTreeGridViewBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function NtsTreeGridViewBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsTreeGridViewBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionsValue = ko.unwrap(data.optionsValue);
                        var optionsText = ko.unwrap(data.optionsText);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        var optionsChild = ko.unwrap(data.optionsChild);
                        var extColumns = ko.unwrap(data.extColumns);
                        // Default.
                        var showCheckBox = ko.unwrap(data.showCheckBox);
                        showCheckBox = showCheckBox != undefined ? showCheckBox : true;
                        var enable = ko.unwrap(data.enable);
                        enable = enable != undefined ? enable : true;
                        var height = ko.unwrap(data.height);
                        height = height ? height : '100%';
                        width = width ? width : '100%';
                        var width = ko.unwrap(data.width);
                        var displayColumns = [{ headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
                            { headerText: "コード／名称", key: optionsText, width: "200px", dataType: "string" }];
                        if (extColumns) {
                            displayColumns = displayColumns.concat(extColumns);
                        }
                        // Init ig grid.
                        $(element).igTreeGrid({
                            width: width,
                            height: height,
                            dataSource: options,
                            autoGenerateColumns: false,
                            primaryKey: optionsValue,
                            columns: displayColumns,
                            childDataKey: optionsChild,
                            initialExpandDepth: 10,
                            features: [
                                {
                                    name: "Selection",
                                    multipleSelection: true,
                                    activation: true,
                                    rowSelectionChanged: function (evt, ui) {
                                        var selectedRows = ui.selectedRows;
                                        if (ko.unwrap(data.multiple)) {
                                            if (ko.isObservable(data.selectedValues)) {
                                                data.selectedValues(_.map(selectedRows, function (row) {
                                                    return row.id;
                                                }));
                                            }
                                        }
                                        else {
                                            if (ko.isObservable(data.value)) {
                                                data.value(selectedRows[0].id);
                                            }
                                        }
                                    }
                                },
                                {
                                    name: "RowSelectors",
                                    enableCheckBoxes: showCheckBox,
                                    checkBoxMode: "biState"
                                }]
                        });
                    };
                    /**
                     * Update
                     */
                    NtsTreeGridViewBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        // Clear selection.
                        if (selectedValues && selectedValues.length == 0) {
                            $(element).igTreeGridSelection("clearSelection");
                        }
                        // Update datasource.
                        $(element).igTreeGrid("option", "dataSource", options);
                        // Set multiple data source.
                        var multiple = ko.unwrap(data.multiple);
                        multiple = multiple != undefined ? multiple : true;
                        $(element).igTreeGridSelection("option", "multipleSelection", multiple);
                        // Set show checkbox.
                        var showCheckBox = ko.unwrap(data.showCheckBox);
                        showCheckBox = showCheckBox != undefined ? showCheckBox : true;
                        $(element).igTreeGridRowSelectors("option", "enableCheckBoxes", showCheckBox);
                        // Compare value.
                        var olds = _.map($(element).igTreeGridSelection("selectedRow"), function (row) {
                            return row.id;
                        });
                        // Not change, do nothing.
                        if (selectedValues) {
                            if (_.isEqual(selectedValues.sort(), olds.sort())) {
                                return;
                            }
                            // Update.
                            $(element).igTreeGridSelection("clearSelection");
                            selectedValues.forEach(function (val) {
                                $(element).igTreeGridSelection("selectRowById", val);
                            });
                        }
                        if (singleValue) {
                            if (olds.length > 0 && olds[0] == singleValue) {
                                return;
                            }
                            $(element).igTreeGridSelection("selectRowById", singleValue);
                        }
                    };
                    return NtsTreeGridViewBindingHandler;
                }());
                var WizardBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function WizardBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    WizardBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Get step list.
                        var options = ko.unwrap(data.steps);
                        // Container.
                        var container = $(element);
                        // Create steps.
                        for (var i = 0; i < options.length; i++) {
                            var contentClass = ko.unwrap(options[i].content);
                            var htmlStep = container.children('.steps').children(contentClass).html();
                            var htmlContent = container.children('.contents').children(contentClass).html();
                            container.append('<h1 class="' + contentClass + '">' + htmlStep + '</h1>');
                            container.append('<div>' + htmlContent + '</div>');
                        }
                        var icon = container.find('.header .image').data('icon');
                        // Remove html.
                        var header = container.children('.header');
                        container.children('.header').remove();
                        container.children('.steps').remove();
                        container.children('.contents').remove();
                        // Create wizard.
                        container.steps({
                            headerTag: "h1",
                            bodyTag: "div",
                            transitionEffect: "slideLeft",
                            stepsOrientation: "vertical",
                            titleTemplate: '<div>#title#</div>',
                            enablePagination: false,
                            enableFinishButton: false,
                            autoFocus: false,
                            onStepChanged: function () {
                                // Remove old class.
                                container.children('.steps').children('ul').children('li').removeClass('step-current');
                                container.children('.steps').children('ul').children('li').removeClass('step-prev');
                                container.children('.steps').children('ul').children('li').removeClass('step-next');
                                // Add new class.
                                container.children('.steps').children('ul').children('.done').addClass('disabled');
                                container.children('.steps').children('ul').children('.current').addClass('step-current');
                                container.children('.steps').children('ul').children('.done').addClass('step-prev');
                                container.children('.steps').children('ul').children('.step-current').nextAll('li').not('.done').addClass('step-next');
                                return true;
                            }
                        });
                        // Add default class.
                        container.addClass('nts-wizard');
                        container.children('.steps').children('ul').children('li').children('a').before('<div class="nts-steps"></div>');
                        container.children('.steps').children('ul').children('li').children('a').addClass('nts-step-contents');
                        //container.children('.steps').children('ul').children('.first').addClass('begin');
                        container.children('.steps').children('ul').children('.last').addClass('end');
                        container.children('.steps').children('ul').children('li').not('.begin').not('.end').children('.nts-steps').addClass('nts-steps-middle');
                        container.find('.nts-steps-middle').append('<div class="nts-vertical-line"></div><div class="nts-bridge"><div class="nts-point"></div><div class="nts-horizontal-line"></div></div>');
                        // Remove old class.
                        container.children('.steps').children('ul').children('li').removeClass('step-current');
                        container.children('.steps').children('ul').children('li').removeClass('step-prev');
                        container.children('.steps').children('ul').children('li').removeClass('step-next');
                        // Add new class.
                        container.children('.steps').children('ul').children('.current').addClass('step-current');
                        container.children('.steps').children('ul').children('.done').addClass('step-prev');
                        container.children('.steps').children('ul').children('.step-current').nextAll('li').not('.done').addClass('step-next');
                        // Remove content.
                        container.find('.actions').hide();
                        // Add Header
                        container.children('.steps').prepend(header);
                        container.find('.header .image').attr('style', 'background-image: url("' + icon + '")');
                        $.fn.begin = function () {
                            $(this).setStep(0);
                        };
                        $.fn.end = function () {
                            $(this).setStep(options.length - 1);
                        };
                    };
                    /**
                     * Update
                     */
                    WizardBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return WizardBindingHandler;
                }());
                /**
                 * FormLabel
                 */
                var NtsFormLabelBindingHandler = (function () {
                    function NtsFormLabelBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsFormLabelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var primitiveValueName = ko.unwrap(data.constraint);
                        var isRequired = ko.unwrap(data.required) === true;
                        var isInline = ko.unwrap(data.inline) === true;
                        var isEnable = ko.unwrap(data.enable) !== false;
                        var $formLabel = $(element).addClass('form-label');
                        $('<label/>').text($formLabel.text()).appendTo($formLabel.empty());
                        if (!isEnable) {
                            $formLabel.addClass('disabled');
                        }
                        else {
                            $formLabel.removeClass('disabled');
                        }
                        if (isRequired) {
                            $formLabel.addClass('required');
                        }
                        if (primitiveValueName !== undefined) {
                            $formLabel.addClass(isInline ? 'inline' : 'broken');
                            var constraintText = NtsFormLabelBindingHandler.buildConstraintText(primitiveValueName);
                            $('<i/>').text(constraintText).appendTo($formLabel);
                        }
                    };
                    /**
                     * Update
                     */
                    NtsFormLabelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    NtsFormLabelBindingHandler.buildConstraintText = function (primitiveValueName) {
                        var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
                        var constraintText;
                        switch (constraint.valueType) {
                            case 'String':
                                return uk.ui.validation.getCharType(primitiveValueName).buildConstraintText(constraint.maxLength);
                            default:
                                return 'ERROR';
                        }
                    };
                    return NtsFormLabelBindingHandler;
                }());
                /**
                 * LinkButton
                 */
                var NtsLinkButtonBindingHandler = (function () {
                    function NtsLinkButtonBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsLinkButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var jump = data.jump;
                        var linkText = $(element).text();
                        var $linkButton = $(element).wrap('<div/>').parent().empty()
                            .text(linkText)
                            .addClass('link-button')
                            .click(function () {
                            alert(jump);
                        });
                    };
                    /**
                     * Update
                     */
                    NtsLinkButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return NtsLinkButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsWizard'] = new WizardBindingHandler();
                ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
                ko.bindingHandlers['ntsLinkButton'] = new NtsLinkButtonBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
                ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
                ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
                ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
                ko.bindingHandlers['ntsMaskEditor'] = new NtsMaskEditorBindingHandler();
                ko.bindingHandlers['ntsDialog'] = new NtsDialogBindingHandler();
                ko.bindingHandlers['ntsErrorDialog'] = new NtsErrorDialogBindingHandler();
                ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
                ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
                ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
