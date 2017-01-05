var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;
                var EditorProcessor = (function () {
                    function EditorProcessor() {
                    }
                    EditorProcessor.prototype.init = function ($input, data) {
                        var _this = this;
                        var setValue = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        var atomWidth = 9;
                        //9 * 160 = 1440 max width, TextEditor shouldnt reach this width
                        // need to consider more
                        if (constraint && constraint.maxLength) {
                            var autoWidth = constraint.maxLength <= 160 ? constraint.maxLength * atomWidth : "100%";
                            $input.width(autoWidth);
                        }
                        $input.addClass('nts-editor').addClass("nts-input");
                        $input.wrap("<span class= 'nts-editor-wrapped'/>");
                        $input.change(function () {
                            var validator = _this.getValidator(data);
                            var formatter = _this.getFormatter(data);
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                setValue(result.parsedValue);
                                $input.val(formatter.format(result.parsedValue));
                            }
                            else {
                                $input.ntsError('set', result.errorMessage);
                                setValue(newText);
                            }
                        });
                    };
                    EditorProcessor.prototype.update = function ($input, data) {
                        var getValue = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var option = (data.option !== undefined) ? ko.unwrap(data.option) : ko.mapping.fromJS(this.getDefaultOption());
                        var placeholder = ko.unwrap(option.placeholder || '');
                        var width = ko.unwrap(option.width || '');
                        var textalign = ko.unwrap(option.textalign || '');
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        if (width.trim() != "")
                            $input.width(width);
                        if (textalign.trim() != "")
                            $input.css('text-align', textalign);
                        var formatted = $input.ntsError('hasError')
                            ? getValue()
                            : this.getFormatter(data).format(getValue());
                        $input.val(formatted);
                    };
                    EditorProcessor.prototype.getDefaultOption = function () {
                        return {};
                    };
                    EditorProcessor.prototype.getFormatter = function (data) {
                        return new uk.format.NoFormatter();
                    };
                    EditorProcessor.prototype.getValidator = function (data) {
                        return new validation.NoValidator();
                    };
                    return EditorProcessor;
                }());
                var DynamicEditorProcessor = (function (_super) {
                    __extends(DynamicEditorProcessor, _super);
                    function DynamicEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    DynamicEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        if (data.editortype) {
                            var editortype = ko.unwrap(data.editortype);
                            switch (editortype) {
                                case 'numbereditor':
                                    return NumberEditorProcessor.prototype.getValidator(data);
                                case 'timeeditor':
                                    return TimeEditorProcessor.prototype.getValidator(data);
                                case 'multilineeditor':
                                    return MultilineEditorProcessor.prototype.getValidator(data);
                                default:
                                    return TextEditorProcessor.prototype.getValidator(data);
                            }
                        }
                        else {
                            if (constraint) {
                                if (constraint.valueType === 'String') {
                                    return TextEditorProcessor.prototype.getValidator(data);
                                }
                                else if (data.option) {
                                    var option = ko.unwrap(data.option);
                                    //If inputFormat presented, this is Date or Time Editor
                                    if (option.inputFormat) {
                                        return TimeEditorProcessor.prototype.getValidator(data);
                                    }
                                    else {
                                        return NumberEditorProcessor.prototype.getValidator(data);
                                    }
                                }
                            }
                            return validation.createValidator(constraintName);
                        }
                    };
                    DynamicEditorProcessor.prototype.getFormatter = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        if (data.editortype) {
                            var editortype = ko.unwrap(data.editortype);
                            switch (editortype) {
                                case 'numbereditor':
                                    return NumberEditorProcessor.prototype.getFormatter(data);
                                case 'timeeditor':
                                    return TimeEditorProcessor.prototype.getFormatter(data);
                                case 'multilineeditor':
                                    return MultilineEditorProcessor.prototype.getFormatter(data);
                                default:
                                    return TextEditorProcessor.prototype.getFormatter(data);
                            }
                        }
                        else {
                            if (constraint) {
                                if (constraint.valueType === 'String') {
                                    return TextEditorProcessor.prototype.getFormatter(data);
                                }
                                else if (data.option) {
                                    var option = ko.unwrap(data.option);
                                    //If inputFormat presented, this is Date or Time Editor
                                    if (option.inputFormat) {
                                        return TimeEditorProcessor.prototype.getFormatter(data);
                                    }
                                    else {
                                        return NumberEditorProcessor.prototype.getFormatter(data);
                                    }
                                }
                            }
                            return new uk.format.NoFormatter();
                        }
                    };
                    return DynamicEditorProcessor;
                }(EditorProcessor));
                var TextEditorProcessor = (function (_super) {
                    __extends(TextEditorProcessor, _super);
                    function TextEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TextEditorProcessor.prototype.update = function ($input, data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var textmode = editorOption.textmode;
                        $input.attr('type', textmode);
                        _super.prototype.update.call(this, $input, data);
                    };
                    TextEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TextEditorOption();
                    };
                    TextEditorProcessor.prototype.getFormatter = function (data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: editorOption });
                    };
                    TextEditorProcessor.prototype.getValidator = function (data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, required);
                    };
                    return TextEditorProcessor;
                }(EditorProcessor));
                var NumberEditorProcessor = (function (_super) {
                    __extends(NumberEditorProcessor, _super);
                    function NumberEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    NumberEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        $input.css({ 'text-align': 'right', "box-sizing": "border-box" });
                        var parent = $input.parent();
                        var width = option.width ? option.width : '100%';
                        parent.css({ "display": "inline-block" });
                        var parentTag = parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a") {
                            parent.css({ 'width': '100%' });
                        }
                        if (option.currencyformat !== undefined && option.currencyformat !== null) {
                            var marginLeft = 0;
                            var marginRight = 0;
                            if ($input.css('margin-left') !== "") {
                                marginLeft = parseFloat($input.css('margin-left').split("px")[0]);
                                marginRight = parseFloat($input.css('margin-left').split("px")[0]);
                            }
                            parent.addClass("currency").addClass(option.currencyposition === 'left' ? 'currencyLeft' : 'currencyRight');
                            if (marginLeft !== 0) {
                                parent.css({ "marginLeft": marginLeft + "px" });
                            }
                            if (marginRight !== 0) {
                                parent.css({ "marginRight": marginRight + "px" });
                            }
                            var paddingLeft = (option.currencyposition === 'left' ? 11 : 0) + 'px';
                            var paddingRight = (option.currencyposition === 'right' ? 11 : 0) + 'px';
                            $input.css({
                                'paddingLeft': paddingLeft, 'paddingRight': paddingRight,
                                'width': width, "marginLeft": "0px", "marginRight": "0px"
                            });
                        }
                        else {
                            $input.css({ 'paddingLeft': '12px', 'width': width });
                        }
                    };
                    NumberEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.NumberEditorOption();
                    };
                    NumberEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new uk.text.NumberFormatter({ option: option });
                    };
                    NumberEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new validation.NumberValidator(constraintName, option);
                    };
                    return NumberEditorProcessor;
                }(EditorProcessor));
                var TimeEditorProcessor = (function (_super) {
                    __extends(TimeEditorProcessor, _super);
                    function TimeEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TimeEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        $input.css({ 'text-align': 'right', "box-sizing": "border-box" });
                        var parent = $input.parent();
                        parent.css({ "display": "inline-block" });
                        var parentTag = parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a") {
                            parent.css({ 'width': '100%' });
                        }
                        var width = option.width ? option.width : '100%';
                        $input.css({ 'paddingLeft': '12px', 'width': width });
                    };
                    TimeEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TimeEditorOption();
                    };
                    TimeEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new uk.text.TimeFormatter({ option: option });
                    };
                    TimeEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new validation.TimeValidator(constraintName, option);
                    };
                    return TimeEditorProcessor;
                }(EditorProcessor));
                var MultilineEditorProcessor = (function (_super) {
                    __extends(MultilineEditorProcessor, _super);
                    function MultilineEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    MultilineEditorProcessor.prototype.update = function ($input, data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var resizeable = ko.unwrap(editorOption.resizeable);
                        $input.css('resize', (resizeable) ? "both" : "none");
                        _super.prototype.update.call(this, $input, data);
                    };
                    MultilineEditorProcessor.prototype.getDefaultOption = function () {
                        return new ui_1.option.MultilineEditorOption();
                    };
                    MultilineEditorProcessor.prototype.getFormatter = function (data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: editorOption });
                    };
                    MultilineEditorProcessor.prototype.getValidator = function (data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, required);
                    };
                    return MultilineEditorProcessor;
                }(EditorProcessor));
                /**
                 * Editor
                 */
                var NtsEditorBindingHandler = (function () {
                    function NtsEditorBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().init($(element), valueAccessor());
                    };
                    /**
                     * Update
                     */
                    NtsEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsEditorBindingHandler;
                }());
                var NtsDynamicEditorBindingHandler = (function (_super) {
                    __extends(NtsDynamicEditorBindingHandler, _super);
                    function NtsDynamicEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    NtsDynamicEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new DynamicEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsDynamicEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new DynamicEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsDynamicEditorBindingHandler;
                }(NtsEditorBindingHandler));
                /**
                 * TextEditor
                 */
                var NtsTextEditorBindingHandler = (function (_super) {
                    __extends(NtsTextEditorBindingHandler, _super);
                    function NtsTextEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    /**
                     * Init.
                     */
                    NtsTextEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().init($(element), valueAccessor());
                    };
                    /**
                     * Update
                     */
                    NtsTextEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsTextEditorBindingHandler;
                }(NtsEditorBindingHandler));
                /**
                 * SearchBox Binding Handler
                 */
                var filteredArray = function (array, searchTerm, fields, childField) {
                    //if items is empty return empty array
                    if (!array) {
                        return [];
                    }
                    if (!(searchTerm instanceof String)) {
                        searchTerm = "" + searchTerm;
                    }
                    var flatArr = nts.uk.util.flatArray(array, childField);
                    var filter = searchTerm.toLowerCase();
                    //if filter is empty return all the items
                    if (!filter) {
                        return flatArr;
                    }
                    //filter data
                    var filtered = ko.utils.arrayFilter(flatArr, function (item) {
                        var i = fields.length;
                        while (i--) {
                            var prop = fields[i];
                            var strProp = ko.unwrap(item[prop]).toLocaleLowerCase();
                            if (strProp.indexOf(filter) !== -1) {
                                return true;
                            }
                            ;
                        }
                        return false;
                    });
                    return filtered;
                };
                var getNextItem = function (selected, arr, selectedKey, compareKey, isArray) {
                    var current = null;
                    if (isArray) {
                        if (selected.length > 0)
                            current = selected[0];
                    }
                    else if (selected !== undefined && selected !== '' && selected !== null) {
                        current = selected;
                    }
                    if (arr.length > 0) {
                        if (current) {
                            for (var i = 0; i < arr.length - 1; i++) {
                                var item = arr[i];
                                if (selectedKey) {
                                    if (item[selectedKey] === current)
                                        return arr[i + 1][selectedKey];
                                }
                                else if (item[compareKey] === current[compareKey])
                                    return arr[i + 1];
                            }
                        }
                        if (selectedKey)
                            return arr[0][selectedKey];
                        return arr[0];
                    }
                    return undefined;
                };
                var NtsSearchBoxBindingHandler = (function (_super) {
                    __extends(NtsSearchBoxBindingHandler, _super);
                    function NtsSearchBoxBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    /**
                     * Init.
                     */
                    NtsSearchBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var fields = ko.unwrap(data.fields);
                        var selected = data.selected;
                        var selectedKey = null;
                        if (data.selectedKey) {
                            selectedKey = ko.unwrap(data.selectedKey);
                        }
                        var arr = ko.unwrap(data.items);
                        var component = $("#" + ko.unwrap(data.comId));
                        var filteredArr = data.filteredItems;
                        var childField = null;
                        if (data.childField) {
                            childField = ko.unwrap(data.childField);
                        }
                        var $container = $(element);
                        $container.append("<input class='ntsSearchBox' type='text' />");
                        $container.append("<button class='search-btn'>Search</button>");
                        var $input = $container.find("input.ntsSearchBox");
                        var $button = $container.find("button.search-btn");
                        var nextSearch = function () {
                            var filtArr = filteredArr();
                            var compareKey = fields[0];
                            var isArray = $.isArray(selected());
                            var selectedItem = getNextItem(selected(), filtArr, selectedKey, compareKey, isArray);
                            if (!isArray)
                                selected(selectedItem);
                            else {
                                selected([]);
                                selected.push(selectedItem);
                            }
                            component.trigger("selectChange");
                            //console.log(selectedItem); 
                        };
                        $input.keyup(function () {
                            $input.change();
                            //console.log('change');
                        }).keydown(function (event) {
                            if (event.which == 13) {
                                event.preventDefault();
                                nextSearch();
                            }
                        });
                        $input.change(function (event) {
                            var searchTerm = $input.val();
                            filteredArr(filteredArray(arr, searchTerm, fields, childField));
                        });
                        $button.click(nextSearch);
                    };
                    NtsSearchBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return NtsSearchBoxBindingHandler;
                }(NtsEditorBindingHandler));
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
                        new NumberEditorProcessor().init($(element), valueAccessor());
                    };
                    /**
                     * Update
                     */
                    NtsNumberEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().update($(element), valueAccessor());
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
                        new TimeEditorProcessor().init($(element), valueAccessor());
                    };
                    /**
                     * Update
                     */
                    NtsTimeEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        new TimeEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsTimeEditorBindingHandler;
                }());
                /**
                 * MultilineEditor
                 */
                var NtsMultilineEditorBindingHandler = (function (_super) {
                    __extends(NtsMultilineEditorBindingHandler, _super);
                    function NtsMultilineEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    /**
                     * Init.
                     */
                    NtsMultilineEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().init($(element), valueAccessor());
                    };
                    /**
                     * Update
                     */
                    NtsMultilineEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsMultilineEditorBindingHandler;
                }(NtsEditorBindingHandler));
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
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
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
                        // Enable
                        (enable === true) ? $('button', container).prop("disabled", false) : $('button', container).prop("disabled", true);
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
                        // Get data
                        var data = valueAccessor();
                        var setChecked = data.checked;
                        var textId = data.text;
                        var checkBoxText;
                        // Container
                        var container = $(element);
                        container.addClass("ntsControl");
                        if (textId) {
                            checkBoxText = textId;
                        }
                        else {
                            checkBoxText = container.text();
                            container.text('');
                        }
                        var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                        var checkBox = $('<input type="checkbox">').on("change", function () {
                            if (typeof setChecked === "function")
                                setChecked($(this).is(":checked"));
                        }).appendTo(checkBoxLabel);
                        var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                        var label = $("<span class='label'></span>").text(checkBoxText).appendTo(checkBoxLabel);
                        checkBoxLabel.appendTo(container);
                    };
                    /**
                     * Update
                     */
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        var checkBox = $(element).find("input[type='checkbox']");
                        // Checked
                        checkBox.prop("checked", checked);
                        // Enable
                        (enable === true) ? checkBox.removeAttr("disabled") : checkBox.attr("disabled", "disabled");
                    };
                    return NtsCheckboxBindingHandler;
                }());
                /**
                 * Multi Checkbox
                 */
                var NtsMultiCheckBoxBindingHandler = (function () {
                    function NtsMultiCheckBoxBindingHandler() {
                    }
                    NtsMultiCheckBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        $(element).addClass("ntsControl");
                    };
                    NtsMultiCheckBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        // Get option or option[optionValue]
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            container.empty();
                            _.forEach(options, function (option) {
                                var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                                var checkBox = $('<input type="checkbox">').data("value", getOptionValue(option)).on("change", function () {
                                    var _this = this;
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue.push($(self).data("value"));
                                    else
                                        selectedValue.remove(_.find(selectedValue(), function (value) {
                                            return _.isEqual(value, $(_this).data("value"));
                                        }));
                                }).appendTo(checkBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                                var label = $("<span class='label'></span>").text(option[optionText]).appendTo(checkBoxLabel);
                                checkBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", options.slice());
                        }
                        // Checked
                        container.find("input[type='checkbox']").prop("checked", function () {
                            var _this = this;
                            return (_.find(selectedValue(), function (value) {
                                return _.isEqual(value, $(_this).data("value"));
                            }) !== undefined);
                        });
                        // Enable
                        (enable === true) ? container.find("input[type='checkbox']").removeAttr("disabled") : container.find("input[type='checkbox']").attr("disabled", "disabled");
                    };
                    return NtsMultiCheckBoxBindingHandler;
                }());
                /**
                 * RadioBox Group
                 */
                var NtsRadioBoxGroupBindingHandler = (function () {
                    function NtsRadioBoxGroupBindingHandler() {
                    }
                    NtsRadioBoxGroupBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        $(element).addClass("ntsControl");
                    };
                    NtsRadioBoxGroupBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            var radioName = uk.util.randomId();
                            container.empty();
                            _.forEach(options, function (option) {
                                var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                                var radioBox = $('<input type="radio">').attr("name", radioName).data("value", getOptionValue(option)).on("change", function () {
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue($(self).data("value"));
                                }).appendTo(radioBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(radioBoxLabel);
                                var label = $("<span class='label'></span>").text(option[optionText]).appendTo(radioBoxLabel);
                                radioBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", options.slice());
                        }
                        // Checked
                        var checkedRadio = _.find(container.find("input[type='radio']"), function (item) {
                            return _.isEqual($(item).data("value"), selectedValue());
                        });
                        if (checkedRadio !== undefined)
                            $(checkedRadio).prop("checked", true);
                        // Enable
                        (enable === true) ? container.find("input[type='radio']").removeAttr("disabled") : container.find("input[type='radio']").attr("disabled", "disabled");
                    };
                    return NtsRadioBoxGroupBindingHandler;
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
                        var editable = ko.unwrap(data.editable);
                        var enable = ko.unwrap(data.enable);
                        var columns = ko.unwrap(data.columns);
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
                        var required = data.required || false;
                        // Container.
                        var container = $(element);
                        container.data('required', required);
                        // Default value.
                        var selectSize = 6;
                        // Create select.
                        container.append('<ol class="nts-list-box"></ol>');
                        var selectListBoxContainer = container.find('.nts-list-box');
                        // Create changing event.
                        var changeEvent = new CustomEvent("selectionChange", {
                            detail: {},
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
                            });
                            // Dispatch/Trigger/Fire the event => use event.detai to get selected value.
                            document.getElementById(container.attr('id')).dispatchEvent(changingEvent);
                            data.value(itemsSelected);
                            // Create event changed.
                            var changedEvent = new CustomEvent("selectionChanged", {
                                detail: itemsSelected,
                                bubbles: true,
                                cancelable: false
                            });
                            // Dispatch/Trigger/Fire the event => use event.detai to get selected value.
                            document.getElementById(container.attr('id')).dispatchEvent(changedEvent);
                        }));
                        // Create method.
                        $.fn.deselectAll = function () {
                            $(this).data('value', '');
                            $(this).find('.nts-list-box > li').removeClass("ui-selected");
                            $(this).find('.nts-list-box > li > div').removeClass("ui-selected");
                            $(this).trigger("selectionChange");
                        };
                        $.fn.selectAll = function () {
                            $(this).find('.nts-list-box > li').addClass("ui-selected");
                            $(this).find('.nts-list-box').data("ui-selectable")._mouseStop(null);
                        };
                        $.fn.validate = function () {
                            var $container = $(this);
                            var required = $container.data('required');
                            var $currentListBox = $container.find('.nts-list-box');
                            if (required) {
                                var itemsSelected = $container.data('value');
                                if (itemsSelected === undefined || itemsSelected === null || itemsSelected.length == 0) {
                                    $currentListBox.ntsError('set', 'at least 1 item selection required');
                                    return false;
                                }
                                else {
                                    $currentListBox.ntsError('clear');
                                    return true;
                                }
                            }
                        };
                        $.fn.ntsListBox = function (method) {
                            switch (method) {
                                case 'deselectAll':
                                    this.deselectAll();
                                    break;
                                case 'selectAll':
                                    this.selectAll();
                                    break;
                                case 'validate':
                                    return this.validate();
                                default:
                                    break;
                            }
                        };
                        container.on('validate', (function (e) {
                            // Check empty value
                            var itemsSelected = container.data('value');
                            if (itemsSelected === undefined || itemsSelected === null || itemsSelected.length == 0) {
                                selectListBoxContainer.ntsError('set', 'at least 1 item selection required');
                            }
                            else {
                                selectListBoxContainer.ntsError('clear');
                            }
                        }));
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
                        if (!(selectedValue === undefined || selectedValue === null || selectedValue.length == 0)) {
                            container.trigger('validate');
                        }
                    };
                    return ListBoxBindingHandler;
                }());
                /**
                 * GridList binding handler
                 */
                function calculateTop(options, id, key) {
                    var atomTop = 23.6363525390625;
                    var len = options.length;
                    var index = 0;
                    for (var i = 0; i < len; i++) {
                        var item = options[i];
                        if (item[key] == id) {
                            index = i;
                            break;
                        }
                    }
                    return atomTop * i;
                }
                var NtsGridListBindingHandler = (function () {
                    function NtsGridListBindingHandler() {
                    }
                    NtsGridListBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var HEADER_HEIGHT = 27;
                        var $grid = $(element);
                        if (nts.uk.util.isNullOrUndefined($grid.attr('id'))) {
                            throw new Error('the element NtsGridList must have id attribute.');
                        }
                        var data = valueAccessor();
                        var optionsValue = data.optionsValue;
                        var options = ko.unwrap(data.options);
                        var observableColumns = data.columns;
                        var iggridColumns = _.map(observableColumns(), function (c) {
                            return {
                                headerText: c.headerText,
                                key: c.prop,
                                width: c.width,
                                dataType: 'string'
                            };
                        });
                        var features = [];
                        features.push({ name: 'Selection', multipleSelection: data.multiple });
                        features.push({ name: 'Sorting', type: 'local' });
                        features.push({ name: 'RowSelectors', enableCheckBoxes: data.multiple, enableRowNumbering: true });
                        $grid.igGrid({
                            width: data.width,
                            height: (data.height - HEADER_HEIGHT) + "px",
                            primaryKey: data.optionsValue,
                            columns: iggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features
                        });
                        $grid.ntsGridList('setupSelecting');
                        $grid.bind('selectionchanged', function () {
                            if (data.multiple) {
                                var selecteds = $grid.ntsGridList('getSelected');
                                var selectedIdSet_1 = {};
                                selecteds.forEach(function (s) { selectedIdSet_1[s.id] = true; });
                                var selectedOptions = _.filter(data.options(), function (o) { return selectedIdSet_1[o[optionsValue]]; });
                                data.value(_.map(selectedOptions, function (o) { return o[optionsValue]; }));
                            }
                            else {
                                var selected_1 = $grid.ntsGridList('getSelected');
                                var selectedOption = _.find(data.options(), function (o) { return o[optionsValue] === selected_1.id; });
                                data.value(selectedOption[optionsValue]);
                            }
                        });
                        var gridId = $grid.attr('id');
                        $grid.on("selectChange", function () {
                            var scrollContainer = $("#" + gridId + "_scrollContainer");
                            var row1 = null;
                            var selectedRows = $grid.igGrid("selectedRows");
                            if (selectedRows && selectedRows.length > 0)
                                row1 = $grid.igGrid("selectedRows")[0].id;
                            else {
                                var selectedRow = $grid.igGrid("selectedRow");
                                if (selectedRow && selectedRow.id) {
                                    row1 = $grid.igGrid("selectedRow").id;
                                }
                            }
                            if (row1) {
                                scrollContainer.scrollTop(calculateTop(options, row1, optionsValue));
                            }
                        });
                    };
                    NtsGridListBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $grid = $(element);
                        var data = valueAccessor();
                        var currentSource = $grid.igGrid('option', 'dataSource');
                        if (!_.isEqual(currentSource, data.options())) {
                            $grid.igGrid('option', 'dataSource', data.options().slice());
                            $grid.igGrid("dataBind");
                        }
                        if (data.value)
                            $grid.ntsGridList('setSelected', data.value());
                        $grid.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(data.height);
                    };
                    return NtsGridListBindingHandler;
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
                        var columns = null;
                        if (data.columns)
                            columns = ko.unwrap(data.columns);
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
                        var headers = ["コード", "コード／名称"];
                        if (data.headers) {
                            headers = ko.unwrap(data.headers);
                        }
                        var displayColumns = [{ headerText: headers[0], key: optionsValue, dataType: "string", hidden: true },
                            { headerText: headers[1], key: optionsText, width: "200px", dataType: "string" }];
                        if (columns)
                            displayColumns = columns;
                        if (extColumns) {
                            displayColumns = displayColumns.concat(extColumns);
                        }
                        // Init ig grid.
                        var $treegrid = $(element);
                        $(element).igTreeGrid({
                            width: width,
                            height: height,
                            dataSource: options,
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
                        var treeGridId = $treegrid.attr('id');
                        $(element).closest('.ui-igtreegrid').addClass('nts-treegridview');
                        $treegrid.on("selectChange", function () {
                            var scrollContainer = $("#" + treeGridId + "_scroll");
                            var row1 = null;
                            var selectedRows = $treegrid.igTreeGrid("selectedRows");
                            if (selectedRows && selectedRows.length > 0)
                                row1 = $treegrid.igTreeGrid("selectedRows")[0].id;
                            else {
                                var selectedRow = $treegrid.igTreeGrid("selectedRow");
                                if (selectedRow && selectedRow.id) {
                                    row1 = $treegrid.igTreeGrid("selectedRow").id;
                                }
                            }
                            if (row1) {
                                scrollContainer.scrollTop(calculateTop(options, row1, optionsValue));
                            }
                            //console.log(row1);
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
                        if (!selectedValues) {
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
                            $(element).igTreeGridSelection("clearSelection");
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
                        // Get data
                        var data = valueAccessor();
                        // Get step list
                        var options = ko.unwrap(data.steps);
                        var theme = ko.unwrap(data.theme);
                        var cssClass = "nts-wizard " + "theme-" + theme;
                        // Container
                        var container = $(element);
                        // Create steps
                        for (var i = 0; i < options.length; i++) {
                            var contentClass = ko.unwrap(options[i].content);
                            var htmlStep = container.children('.steps').children(contentClass).html();
                            var htmlContent = container.children('.contents').children(contentClass).html();
                            container.append('<h1 class="' + contentClass + '">' + htmlStep + '</h1>');
                            container.append('<div>' + htmlContent + '</div>');
                        }
                        var icon = container.find('.header .image').data('icon');
                        // Remove html
                        var header = container.children('.header');
                        container.children('.header').remove();
                        container.children('.steps').remove();
                        container.children('.contents').remove();
                        // Create wizard
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
                        // Add default class
                        container.addClass(cssClass);
                        container.children('.steps').children('ul').children('li').children('a').before('<div class="nts-steps"></div>');
                        container.children('.steps').children('ul').children('li').children('a').addClass('nts-step-contents');
                        //container.children('.steps').children('ul').children('.first').addClass('begin');
                        container.children('.steps').children('ul').children('.last').addClass('end');
                        container.children('.steps').children('ul').children('li').not('.begin').not('.end').children('.nts-steps').addClass('nts-steps-middle');
                        container.find('.nts-steps-middle').append('<div class="nts-vertical-line"></div><div class="nts-bridge"><div class="nts-point"></div><div class="nts-horizontal-line"></div></div>');
                        // Remove old class
                        container.children('.steps').children('ul').children('li').removeClass('step-current');
                        container.children('.steps').children('ul').children('li').removeClass('step-prev');
                        container.children('.steps').children('ul').children('li').removeClass('step-next');
                        // Add new class
                        container.children('.steps').children('ul').children('.current').addClass('step-current');
                        container.children('.steps').children('ul').children('.done').addClass('step-prev');
                        container.children('.steps').children('ul').children('.step-current').nextAll('li').not('.done').addClass('step-next');
                        // Remove content
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
                        var primitiveValue = ko.unwrap(data.constraint);
                        var isRequired = ko.unwrap(data.required) === true;
                        var isInline = ko.unwrap(data.inline) === true;
                        var isEnable = ko.unwrap(data.enable) !== false;
                        var $formLabel = $(element).addClass('form-label');
                        $('<label/>').html($formLabel.html()).appendTo($formLabel.empty());
                        if (!isEnable) {
                            $formLabel.addClass('disabled');
                        }
                        else {
                            $formLabel.removeClass('disabled');
                        }
                        if (isRequired) {
                            $formLabel.addClass('required');
                        }
                        if (primitiveValue !== undefined) {
                            $formLabel.addClass(isInline ? 'inline' : 'broken');
                            var constraintText = NtsFormLabelBindingHandler.buildConstraintText(primitiveValue);
                            $('<i/>').text(constraintText).appendTo($formLabel);
                        }
                    };
                    /**
                     * Update
                     */
                    NtsFormLabelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    NtsFormLabelBindingHandler.buildConstraintText = function (primitiveValues) {
                        if (!Array.isArray(primitiveValues))
                            primitiveValues = [primitiveValues];
                        var constraintText = "";
                        _.forEach(primitiveValues, function (primitiveValue) {
                            var constraint = __viewContext.primitiveValueConstraints[primitiveValue];
                            switch (constraint.valueType) {
                                case 'String':
                                    constraintText += (constraintText.length > 0) ? "/" : "";
                                    constraintText += uk.text.getCharType(primitiveValue).buildConstraintText(constraint.maxLength);
                                    break;
                                default:
                                    constraintText += 'ERROR';
                                    break;
                            }
                        });
                        return constraintText;
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
                /**
                 * Datepicker binding handler
                 */
                var DatePickerBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function DatePickerBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    DatePickerBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Container.
                        var container = $(element);
                        var date = ko.unwrap(data.value);
                        var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
                        var length = 10, atomWidth = 9;
                        if (dateFormat === "yyyy/MM/dd DDD") {
                            length = 16;
                        }
                        else if (dateFormat === "yyyy/MM/dd D") {
                            length = 14;
                        }
                        container.attr('value', nts.uk.time.formatDate(date, dateFormat));
                        container.datepicker({
                            format: 'yyyy/mm/dd',
                            language: 'ja-JP'
                        });
                        container.on('change', function (event) {
                            data.value(new Date(container.val().substring(0, 10)));
                        });
                        container.width(atomWidth * length);
                    };
                    /**
                     * Update
                     */
                    DatePickerBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var container = $(element);
                        var date = ko.unwrap(data.value);
                        var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
                        //container.attr('value', nts.uk.time.formatDate(date, dateFormat));
                        container.val(nts.uk.time.formatDate(date, dateFormat));
                    };
                    return DatePickerBindingHandler;
                }());
                /**
                 * TabPanel Binding Handler
                 */
                var TabPanelBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function TabPanelBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    TabPanelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var tabs = ko.unwrap(data.dataSource);
                        var direction = ko.unwrap(data.direction || "horizontal");
                        // Container.
                        var container = $(element);
                        // Create title.
                        container.prepend('<ul></ul>');
                        var titleContainer = container.children('ul');
                        for (var i = 0; i < tabs.length; i++) {
                            var id = tabs[i].id;
                            var title = tabs[i].title;
                            titleContainer.append('<li><a href="#' + id + '">' + title + '</a></li>');
                            // Wrap content.
                            var content = tabs[i].content;
                            container.children(content).wrap('<div id="' + id + '"></div>');
                        }
                        container.tabs({
                            activate: function (evt, ui) {
                                data.active(ui.newPanel[0].id);
                                container.children('ul').children('.ui-tabs-active').addClass('active');
                                container.children('ul').children('li').not('.ui-tabs-active').removeClass('active');
                                container.children('ul').children('.ui-state-disabled').addClass('disabled');
                                container.children('ul').children('li').not('.ui-state-disabled').removeClass('disabled');
                            }
                        }).addClass(direction);
                    };
                    /**
                     * Update
                     */
                    TabPanelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Get tab list.
                        var tabs = ko.unwrap(data.dataSource);
                        // Container.
                        var container = $(element);
                        // Select tab.
                        var activeTab = tabs.filter(function (tab) { return tab.id == data.active(); })[0];
                        var indexActive = tabs.indexOf(activeTab);
                        container.tabs("option", "active", indexActive);
                        // Disable & visible tab.
                        tabs.forEach(function (tab) {
                            if (tab.enable()) {
                                container.tabs("enable", '#' + tab.id);
                                container.children('#' + tab.id).children('div').show();
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').removeClass('disabled');
                            }
                            else {
                                container.tabs("disable", '#' + tab.id);
                                container.children('#' + tab.id).children('div').hide();
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').addClass('disabled');
                            }
                            if (!tab.visible()) {
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').hide();
                            }
                            else {
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').show();
                            }
                        });
                    };
                    return TabPanelBindingHandler;
                }());
                var NtsSwapListBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function NtsSwapListBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsSwapListBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var HEADER_HEIGHT = 27;
                        var CHECKBOX_WIDTH = 70;
                        var MOVE_AREA_WIDTH = 100;
                        var $swap = $(element);
                        var elementId = $swap.attr('id');
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var data = valueAccessor();
                        var originalSource = ko.unwrap(data.options);
                        var selectedValues = ko.unwrap(data.value);
                        var primaryKey = data.optionsValue;
                        var totalwidth = ko.unwrap(data.width);
                        var height = ko.unwrap(data.height);
                        var showSearchBox = ko.unwrap(data.showSearchBox);
                        var columns = data.columns;
                        var gridWidth = _.sumBy(columns(), function (c) {
                            return c.width;
                        });
                        var iggridColumns = _.map(columns(), function (c) {
                            return {
                                headerText: c.headerText,
                                key: c.prop,
                                width: c.width,
                                dataType: 'string'
                            };
                        });
                        var features = [];
                        features.push({ name: 'Selection', multipleSelection: true });
                        features.push({ name: 'Sorting', type: 'local' });
                        features.push({ name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: true });
                        $swap.wrap("<div class= 'ntsComponent ntsSwapList'/>");
                        $swap.parent().css({ width: totalwidth + 'px', height: height + 'px', overflowY: 'auto', overflowX: 'auto', display: 'table' });
                        $swap.css({ display: 'table', tableLayout: 'fixed' });
                        if (showSearchBox) {
                            var searchAreaId = elementId + "-search-area";
                            $swap.append("<div class = 'ntsSearchArea' id = " + searchAreaId + "/>");
                            $swap.find(".ntsSearchArea").css({ display: "table-row" }).append("<input id = " + searchAreaId + "-input" + " class = 'ntsSearchInput'/>")
                                .append("<button class='ntsSearchButton'/>");
                            $swap.find(".ntsSearchInput").attr("placeholder", "コード・名称で検索・・・");
                            $swap.find(".ntsSearchButton").css({ "marginLeft": '10px' }).text("Search").click(function () {
                                var value = $swap.find(".ntsSearchInput").val();
                                var source = $(grid2Id).igGrid("option", "dataSource");
                                var selected = $(grid1Id).ntsGridList("getSelected");
                                var tempOrigiSour = originalSource.slice();
                                var findSource;
                                if (selected.length > 0) {
                                    var gotoEnd = tempOrigiSour.splice(0, selected[0].index + 1);
                                    findSource = tempOrigiSour.concat(gotoEnd);
                                }
                                else {
                                    findSource = tempOrigiSour;
                                }
                                var notExisted = _.filter(findSource, function (list) {
                                    return _.filter(source, function (data) {
                                        return data[primaryKey] === list[primaryKey];
                                    }).length <= 0;
                                });
                                var searchedValues = _.find(notExisted, function (val) {
                                    return _.valuesIn(val).filter(function (x) {
                                        return x.toString().indexOf(value) >= 0;
                                    }).length > 0;
                                });
                                $(grid1Id).ntsGridList('setSelected', searchedValues !== undefined ? [searchedValues[primaryKey]] : []);
                                if (searchedValues !== undefined) {
                                    if (selected.length === 0 || selected[0].id !== searchedValues[primaryKey]) {
                                        var scrollContainer = $(grid1Id + "_scrollContainer");
                                        var current = $(grid1Id).ntsGridList("getSelected");
                                        if (current.length > 0) {
                                            var rowidstr = "tr[data-id='" + current[0].id + "']";
                                            scrollContainer.scrollTop($(rowidstr).position().top);
                                        }
                                    }
                                }
                            });
                        }
                        $swap.append("<div class = 'ntsSwapGridArea ntsSwapComponent' id = " + elementId + "-gridArea1" + "/>");
                        $swap.append("<div class = 'ntsMoveDataArea ntsSwapComponent' id = " + elementId + "-move-data" + "/>");
                        $swap.append("<div class = 'ntsSwapGridArea ntsSwapComponent' id = " + elementId + "-gridArea2" + "/>");
                        $swap.find("#" + elementId + "-gridArea1").append("<table class = 'ntsSwapGrid' id = " + elementId + "-grid1" + "/>");
                        $swap.find("#" + elementId + "-gridArea2").append("<table class = 'ntsSwapGrid' id = " + elementId + "-grid2" + "/>");
                        var $grid1 = $swap.find("#" + elementId + "-grid1");
                        var $grid2 = $swap.find("#" + elementId + "-grid2");
                        $grid1.igGrid({
                            width: gridWidth + CHECKBOX_WIDTH,
                            height: (height - HEADER_HEIGHT) + "px",
                            primaryKey: primaryKey,
                            columns: iggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features
                        });
                        $grid1.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(height);
                        $grid1.ntsGridList('setupSelecting');
                        $grid2.igGrid({
                            width: gridWidth + CHECKBOX_WIDTH,
                            height: (height - HEADER_HEIGHT) + "px",
                            primaryKey: primaryKey,
                            columns: iggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features
                        });
                        $grid2.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(height);
                        $grid2.ntsGridList('setupSelecting');
                        var grid1Id = "#" + $grid1.attr('id');
                        var grid2Id = "#" + $grid2.attr('id');
                        $swap.find(".ntsSwapComponent").css({ display: 'table-cell' });
                        var $moveArea = $swap.find("#" + elementId + "-move-data");
                        $moveArea.css({ height: '100%', width: MOVE_AREA_WIDTH + 'px', display: 'table-cell', verticalAlign: 'middle' });
                        $moveArea.append("<button class = 'move-button move-forward'/>");
                        $moveArea.append("<button class = 'move-button move-back'/>");
                        var $moveForward = $moveArea.find(".move-forward");
                        $moveForward.text("forward");
                        var $moveBack = $moveArea.find(".move-back");
                        $moveBack.text("back");
                        $swap.find(".move-forward").css({ marginBottom: '5px' });
                        $moveForward.click(function () {
                            var employeeList = [];
                            var selectedEmployees = $(grid1Id).igGrid("selectedRows");
                            if (selectedEmployees.length > 0) {
                                $(grid1Id).igGridSelection("clearSelection");
                                var source = $(grid1Id).igGrid("option", "dataSource");
                                for (var i = 0; i < selectedEmployees.length; i++) {
                                    var current = source[selectedEmployees[i].index];
                                    if (current[primaryKey] === selectedEmployees[i].id) {
                                        employeeList.push(current);
                                    }
                                    else {
                                        var sameCodes = _.filter(source, function (subject) {
                                            return subject[primaryKey] === selectedEmployees[i].id;
                                        });
                                        if (sameCodes.length > 0) {
                                            employeeList.push(sameCodes[0]);
                                        }
                                    }
                                }
                                var currentSelected = data.value(); //$(grid2Id).igGrid("option", "dataSource");
                                var notExisted = _.filter(employeeList, function (list) {
                                    return _.filter(currentSelected, function (data) {
                                        return data[primaryKey] === list[primaryKey];
                                    }).length <= 0;
                                });
                                if (notExisted.length > 0) {
                                    data.value(currentSelected.concat(notExisted));
                                    var newSource = _.filter(source, function (list) {
                                        var x = _.filter(notExisted, function (data) {
                                            return data[primaryKey] === list[primaryKey];
                                        });
                                        return (x.length <= 0);
                                    });
                                    $(grid1Id).igGrid("option", "dataSource", newSource);
                                    $(grid1Id).igGrid("option", "dataBind");
                                }
                            }
                        });
                        $moveBack.click(function () {
                            var employeeList = [];
                            var selectedEmployees = $(grid2Id).igGrid("selectedRows");
                            if (selectedEmployees.length > 0) {
                                $(grid2Id).igGridSelection("clearSelection");
                                var source = $(grid2Id).igGrid("option", "dataSource");
                                for (var i = 0; i < selectedEmployees.length; i++) {
                                    var current = source[selectedEmployees[i].index];
                                    if (current[primaryKey] === selectedEmployees[i].id) {
                                        employeeList.push(current);
                                    }
                                    else {
                                        var sameCodes = _.filter(source, function (subject) {
                                            return subject[primaryKey] === selectedEmployees[i].id;
                                        });
                                        if (sameCodes.length > 0) {
                                            employeeList.push(sameCodes[0]);
                                        }
                                    }
                                }
                                var currentSource = $(grid1Id).igGrid("option", "dataSource");
                                var notExisted = _.filter(employeeList, function (list) {
                                    return _.filter(currentSource, function (data) {
                                        return data[primaryKey] === list[primaryKey];
                                    }).length <= 0;
                                });
                                if (notExisted.length > 0) {
                                    var newSource = _.filter(source, function (list) {
                                        var x = _.filter(notExisted, function (data) {
                                            return data[primaryKey] === list[primaryKey];
                                        });
                                        return (x.length <= 0);
                                    });
                                    data.value(newSource);
                                    $(grid1Id).igGrid("option", "dataSource", currentSource.concat(notExisted));
                                    $(grid1Id).igGrid("option", "dataBind");
                                }
                            }
                        });
                    };
                    /**
                     * Update
                     */
                    NtsSwapListBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $swap = $(element);
                        var data = valueAccessor();
                        var elementId = $swap.attr('id');
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var $grid1 = $swap.find("#" + elementId + "-grid1");
                        var $grid2 = $swap.find("#" + elementId + "-grid2");
                        var currentSource = $grid1.igGrid('option', 'dataSource');
                        if (!_.isEqual(currentSource, data.options())) {
                            $grid1.igGrid('option', 'dataSource', data.options().slice());
                            $grid1.igGrid("dataBind");
                        }
                        var currentSelected = $grid2.igGrid('option', 'dataSource');
                        if (!_.isEqual(currentSelected, data.value())) {
                            $grid2.igGrid('option', 'dataSource', data.value().slice());
                            $grid2.igGrid("dataBind");
                        }
                    };
                    return NtsSwapListBindingHandler;
                }());
                ko.bindingHandlers['ntsTabPanel'] = new TabPanelBindingHandler();
                ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
                ko.bindingHandlers['ntsWizard'] = new WizardBindingHandler();
                ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
                ko.bindingHandlers['ntsLinkButton'] = new NtsLinkButtonBindingHandler();
                ko.bindingHandlers['ntsDynamicEditor'] = new NtsDynamicEditorBindingHandler();
                ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
                ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
                ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
                ko.bindingHandlers['ntsMultilineEditor'] = new NtsMultilineEditorBindingHandler();
                ko.bindingHandlers['ntsDialog'] = new NtsDialogBindingHandler();
                ko.bindingHandlers['ntsErrorDialog'] = new NtsErrorDialogBindingHandler();
                ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
                ko.bindingHandlers['ntsRadioBoxGroup'] = new NtsRadioBoxGroupBindingHandler();
                ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
                ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
                ko.bindingHandlers['ntsGridList'] = new NtsGridListBindingHandler();
                ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
                ko.bindingHandlers['ntsSwapList'] = new NtsSwapListBindingHandler();
                ko.bindingHandlers['ntsSearchBox'] = new NtsSearchBoxBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
