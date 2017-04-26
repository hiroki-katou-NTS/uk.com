/// <reference path="../../reference.ts"/>
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
                class EditorProcessor {
                    init($input, data) {
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
                        let validator = this.getValidator(data);
                        $input.on(valueUpdate, (e) => {
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
                        $input.blur(() => {
                            if (!readonly) {
                                var formatter = this.getFormatter(data);
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
                    }
                    update($input, data) {
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
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        $input.css('text-align', textalign);
                        if (width.trim() != "")
                            $input.width(width);
                        // Format value
                        var formatted = $input.ntsError('hasError') ? value() : this.getFormatter(data).format(value());
                        $input.val(formatted);
                    }
                    getDefaultOption() {
                        return {};
                    }
                    getFormatter(data) {
                        return new uk.format.NoFormatter();
                    }
                    getValidator(data) {
                        return new validation.NoValidator();
                    }
                }
                /**
                 * TextEditor Processor
                 */
                class TextEditorProcessor extends EditorProcessor {
                    init($input, data) {
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
                        let validator = this.getValidator(data);
                        $input.on("keyup", (e) => {
                            if (!readonly) {
                                var newText = $input.val();
                                var result = validator.validate(newText);
                                $input.ntsError('clear');
                                if (!result.isValid) {
                                    $input.ntsError('set', result.errorMessage);
                                }
                            }
                        });
                        $input.on("blur", (e) => {
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
                    }
                    update($input, data) {
                        super.update($input, data);
                        var textmode = this.editorOption.textmode;
                        $input.attr('type', textmode);
                    }
                    getDefaultOption() {
                        return new nts.uk.ui.option.TextEditorOption();
                    }
                    getFormatter(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    }
                    getValidator(data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    }
                }
                /**
                 * MultilineEditor Processor
                 */
                class MultilineEditorProcessor extends EditorProcessor {
                    update($input, data) {
                        super.update($input, data);
                        var resizeable = this.editorOption.resizeable;
                        $input.css('resize', (resizeable) ? "both" : "none");
                    }
                    getDefaultOption() {
                        return new ui.option.MultilineEditorOption();
                    }
                    getFormatter(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    }
                    getValidator(data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    }
                }
                /**
                 * NumberEditor Processor
                 */
                class NumberEditorProcessor extends EditorProcessor {
                    init($input, data) {
                        super.init($input, data);
                        $input.focus(() => {
                            var selectionType = document.getSelection().type;
                            // Remove separator (comma)
                            $input.val(data.value());
                            // If focusing is caused by Tab key, select text
                            // this code is needed because removing separator deselects.
                            if (selectionType === 'Range') {
                                $input.select();
                            }
                        });
                    }
                    update($input, data) {
                        super.update($input, data);
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
                    }
                    getDefaultOption() {
                        return new nts.uk.ui.option.NumberEditorOption();
                    }
                    getFormatter(data) {
                        return new uk.text.NumberFormatter({ option: this.editorOption });
                    }
                    getValidator(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.NumberValidator(constraintName, this.editorOption);
                    }
                }
                /**
                 * TimeEditor Processor
                 */
                class TimeEditorProcessor extends EditorProcessor {
                    update($input, data) {
                        super.update($input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var width = option.width;
                        var parent = $input.parent();
                        var parentTag = parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            parent.css({ 'width': '100%' });
                        }
                    }
                    getDefaultOption() {
                        return new nts.uk.ui.option.TimeEditorOption();
                    }
                    getFormatter(data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        return new uk.text.TimeFormatter({ inputFormat: inputFormat });
                    }
                    getValidator(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        return new validation.TimeValidator(constraintName, { required: required, outputFormat: inputFormat });
                    }
                }
                /**
                 * Base Editor
                 */
                class NtsEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * TextEditor
                 */
                class NtsTextEditorBindingHandler extends NtsEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * NumberEditor
                 */
                class NtsNumberEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * TimeEditor
                 */
                class NtsTimeEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        new TimeEditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * MultilineEditor
                 */
                class NtsMultilineEditorBindingHandler extends NtsEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().update($(element), valueAccessor());
                    }
                }
                ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
                ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
                ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
                ko.bindingHandlers['ntsMultilineEditor'] = new NtsMultilineEditorBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=editor-ko-ext.js.map
