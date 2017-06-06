/// <reference path="../../reference.ts"/>
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
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;
                /**
                 * BaseEditor Processor
                 */
                var EditorProcessor = (function () {
                    function EditorProcessor() {
                    }
                    EditorProcessor.prototype.init = function ($input, data) {
                        var _this = this;
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        var immediate = ko.unwrap(data.immediate !== undefined ? data.immediate : 'false');
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var valueUpdate = (immediate === true) ? 'input' : 'change';
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : {};
                        this.editorOption = $.extend(this.getDefaultOption(), option);
                        var characterWidth = 9;
                        if (constraint && constraint.maxLength && !$input.is("textarea")) {
                            var autoWidth = constraint.maxLength * characterWidth;
                            $input.width(autoWidth);
                        }
                        $input.addClass('nts-editor nts-input');
                        $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
                        var validator = this.getValidator(data);
                        $input.on(valueUpdate, function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                value(result.parsedValue);
                            }
                            else {
                                $input.ntsError('set', result.errorMessage);
                                value(newText);
                            }
                        });
                        // Format on blur
                        $input.blur(function () {
                            if (!readonly) {
                                var formatter = _this.getFormatter(data);
                                var newText = $input.val();
                                var result = validator.validate(newText);
                                if (result.isValid) {
                                    $input.val(formatter.format(result.parsedValue));
                                }
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage);
                            }
                        }));
                        new nts.uk.util.value.DefaultValue().onReset($input, data.value);
                    };
                    EditorProcessor.prototype.update = function ($input, data) {
                        var value = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : {};
                        this.editorOption = $.extend(this.getDefaultOption(), option);
                        var placeholder = this.editorOption.placeholder;
                        var textalign = this.editorOption.textalign;
                        var width = this.editorOption.width;
                        // Properties
                        if (enable !== false) {
                            $input.removeAttr('disabled');
                        }
                        else {
                            $input.attr('disabled', 'disabled');
                            new nts.uk.util.value.DefaultValue().applyReset($input, value);
                        }
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        $input.css('text-align', textalign);
                        if (width.trim() != "")
                            $input.width(width);
                        // Format value
                        var formatted = $input.ntsError('hasError') ? value() : this.getFormatter(data).format(value());
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
                /**
                 * TextEditor Processor
                 */
                var TextEditorProcessor = (function (_super) {
                    __extends(TextEditorProcessor, _super);
                    function TextEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TextEditorProcessor.prototype.init = function ($input, data) {
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var characterWidth = 9;
                        if (constraint && constraint.maxLength && !$input.is("textarea")) {
                            var autoWidth = constraint.maxLength * characterWidth;
                            $input.width(autoWidth);
                        }
                        $input.addClass('nts-editor nts-input');
                        $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
                        var validator = this.getValidator(data);
                        $input.on("keyup", function (e) {
                            var code = e.keyCode || e.which;
                            if (!readonly && code.toString() !== '9') {
                                var newText = $input.val();
                                var result = validator.validate(newText);
                                $input.ntsError('clear');
                                if (!result.isValid) {
                                    $input.ntsError('set', result.errorMessage);
                                }
                            }
                        });
                        $input.on("blur", function (e) {
                            if (!readonly) {
                                var newText = $input.val();
                                var result = validator.validate(newText, { isCheckExpression: true });
                                $input.ntsError('clear');
                                if (result.isValid) {
                                    if (value() === result.parsedValue) {
                                        $input.val(result.parsedValue);
                                    }
                                    else {
                                        value(result.parsedValue);
                                    }
                                }
                                else {
                                    $input.ntsError('set', result.errorMessage);
                                    value(newText);
                                }
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage);
                            }
                        }));
                        new nts.uk.util.value.DefaultValue().onReset($input, data.value);
                    };
                    TextEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var textmode = this.editorOption.textmode;
                        $input.attr('type', textmode);
                    };
                    TextEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TextEditorOption();
                    };
                    TextEditorProcessor.prototype.getFormatter = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    };
                    TextEditorProcessor.prototype.getValidator = function (data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    };
                    return TextEditorProcessor;
                }(EditorProcessor));
                /**
                 * MultilineEditor Processor
                 */
                var MultilineEditorProcessor = (function (_super) {
                    __extends(MultilineEditorProcessor, _super);
                    function MultilineEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    MultilineEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var resizeable = this.editorOption.resizeable;
                        $input.css('resize', (resizeable) ? "both" : "none");
                    };
                    MultilineEditorProcessor.prototype.getDefaultOption = function () {
                        return new ui.option.MultilineEditorOption();
                    };
                    MultilineEditorProcessor.prototype.getFormatter = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    };
                    MultilineEditorProcessor.prototype.getValidator = function (data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    };
                    return MultilineEditorProcessor;
                }(EditorProcessor));
                /**
                 * NumberEditor Processor
                 */
                var NumberEditorProcessor = (function (_super) {
                    __extends(NumberEditorProcessor, _super);
                    function NumberEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    NumberEditorProcessor.prototype.init = function ($input, data) {
                        _super.prototype.init.call(this, $input, data);
                        $input.focus(function () {
                            var selectionType = document.getSelection().type;
                            // Remove separator (comma)
                            $input.val(data.value());
                            // If focusing is caused by Tab key, select text
                            // this code is needed because removing separator deselects.
                            if (selectionType === 'Range') {
                                $input.select();
                            }
                        });
                    };
                    NumberEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var $parent = $input.parent();
                        var width = this.editorOption.width;
                        var parentTag = $parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            $parent.css({ 'width': '100%' });
                        }
                        $input.css("box-sizing", "border-box");
                        if (width.trim() != "")
                            $input.width(width);
                        if (this.editorOption.currencyformat !== undefined && this.editorOption.currencyformat !== null) {
                            $parent.addClass("symbol").addClass(this.editorOption.currencyposition === 'left' ? 'symbol-left' : 'symbol-right');
                            var format = this.editorOption.currencyformat === "JPY" ? "\u00A5" : '$';
                            $parent.attr("data-content", format);
                        }
                        else if (this.editorOption.symbolChar !== undefined && this.editorOption.symbolChar !== "" && this.editorOption.symbolPosition !== undefined) {
                            $parent.addClass("symbol").addClass(this.editorOption.symbolPosition === 'right' ? 'symbol-right' : 'symbol-left');
                            $parent.attr("data-content", this.editorOption.symbolChar);
                        }
                        if (!nts.uk.util.isNullOrEmpty(this.editorOption.defaultValue)
                            && nts.uk.util.isNullOrEmpty(data.value())) {
                            data.value(this.editorOption.defaultValue);
                        }
                    };
                    NumberEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.NumberEditorOption();
                    };
                    NumberEditorProcessor.prototype.getFormatter = function (data) {
                        return new uk.text.NumberFormatter({ option: this.editorOption });
                    };
                    NumberEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        this.editorOption['required'] = required;
                        return new validation.NumberValidator(constraintName, this.editorOption);
                    };
                    return NumberEditorProcessor;
                }(EditorProcessor));
                /**
                 * TimeEditor Processor
                 */
                var TimeEditorProcessor = (function (_super) {
                    __extends(TimeEditorProcessor, _super);
                    function TimeEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TimeEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var width = option.width;
                        var parent = $input.parent();
                        var parentTag = parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            parent.css({ 'width': '100%' });
                        }
                    };
                    TimeEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TimeEditorOption();
                    };
                    TimeEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        return new uk.text.TimeFormatter({ inputFormat: inputFormat });
                    };
                    TimeEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        var mode = (data.mode !== undefined) ? ko.unwrap(data.mode) : "";
                        return new validation.TimeValidator(constraintName, { required: required, outputFormat: inputFormat, mode: mode });
                    };
                    return TimeEditorProcessor;
                }(EditorProcessor));
                /**
                 * Base Editor
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
                ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
                ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
                ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
                ko.bindingHandlers['ntsMultilineEditor'] = new NtsMultilineEditorBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=editor-ko-ext.js.map