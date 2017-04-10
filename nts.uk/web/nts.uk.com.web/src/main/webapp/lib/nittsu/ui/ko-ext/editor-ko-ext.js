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
                        var valueUpdate = (immediate === true) ? 'input' : 'change';
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
                            var formatter = _this.getFormatter(data);
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            if (result.isValid) {
                                $input.val(formatter.format(result.parsedValue));
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
                    };
                    EditorProcessor.prototype.update = function ($input, data) {
                        var value = data.value;
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
                            ? value()
                            : this.getFormatter(data).format(value());
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
                /**
                 * MultilineEditor Processor
                 */
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
                        return new ui.option.MultilineEditorOption();
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
                 * NumberEditor Processor
                 */
                var NumberEditorProcessor = (function (_super) {
                    __extends(NumberEditorProcessor, _super);
                    function NumberEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    NumberEditorProcessor.prototype.init = function ($input, data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        $input.focus(function () {
                            var selectionType = document.getSelection().type;
                            // remove separator (comma)
                            $input.val(data.value());
                            // if focusing is caused by Tab key, select text.
                            // this code is needed because removing separator deselects.
                            if (selectionType === 'Range') {
                                $input.select();
                            }
                        });
                        _super.prototype.init.call(this, $input, data);
                    };
                    NumberEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var align = option.textalign !== "left" ? "right" : "left";
                        $input.css({ 'text-align': align, "box-sizing": "border-box" });
                        var $parent = $input.parent();
                        var width = option.width; // ? option.width : '100%';
                        var parentTag = $parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            $parent.css({ 'width': '100%' });
                        }
                        if (option.currencyformat !== undefined && option.currencyformat !== null) {
                            $parent.addClass("symbol").addClass(option.currencyposition === 'left' ? 'symbol-left' : 'symbol-right');
                            $input.width(width);
                            var format = option.currencyformat === "JPY" ? "\u00A5" : '$';
                            $parent.attr("data-content", format);
                        }
                        else if (option.symbolChar !== undefined && option.symbolChar !== "" && option.symbolPosition !== undefined) {
                            $parent.addClass("symbol").addClass(option.symbolPosition === 'right' ? 'symbol-right' : 'symbol-left');
                            $input.width(width);
                            $parent.attr("data-content", option.symbolChar);
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
                        $input.css({ 'text-align': 'right', "box-sizing": "border-box" });
                        var parent = $input.parent();
                        parent.css({ "display": "inline-block" });
                        var parentTag = parent.parent().prop("tagName").toLowerCase();
                        var width = option.width; // ? option.width : '100%';
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            parent.css({ 'width': '100%' });
                        }
                        $input.css({ 'paddingLeft': '12px', 'width': width });
                    };
                    TimeEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TimeEditorOption();
                    };
                    TimeEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        //var inputFormat: string = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        return new uk.text.TimeFormatter({ inputFormat: option.inputFormat });
                    };
                    TimeEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new validation.TimeValidator(constraintName, option);
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