﻿/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {
    import validation = nts.uk.ui.validation;

    /**
     * BaseEditor Processor
     */
    class EditorProcessor {

        init($input: JQuery, data: any) {
            var value: (newText: string) => {} = data.value;
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var constraint = validation.getConstraint(constraintName);
            var immediate: boolean = ko.unwrap(data.immediate !== undefined ? data.immediate : 'false');
            var readonly: boolean = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
            var valueUpdate: string = (immediate === true) ? 'input' : 'change';
            var characterWidth: number = 9;
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
                } else {
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

            $input.on('validate', (function(e: Event) {
                var newText = $input.val();
                var result = validator.validate(newText);
                $input.ntsError('clear');
                if (!result.isValid) {
                    $input.ntsError('set', result.errorMessage);
                }
            }));
        }

        update($input: JQuery, data: any) {
            var value: () => string = data.value;
            var required: boolean = (data.required !== undefined) ? ko.unwrap(data.required) : false;
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
            var readonly: boolean = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
            var option: any = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var placeholder: string = option.placeholder;
            var textalign: string = option.textalign;
            var width: string = option.width;
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

        getDefaultOption(): any {
            return {};
        }

        getFormatter(data: any): format.IFormatter {
            return new format.NoFormatter();
        }

        getValidator(data: any): validation.IValidator {
            return new validation.NoValidator();
        }
    }

    /**
     * TextEditor Processor
     */
    class TextEditorProcessor extends EditorProcessor {

        init($input: JQuery, data: any) {
            var value: KnockoutObservable<string> = data.value;
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var constraint = validation.getConstraint(constraintName);
            var readonly: boolean = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
            var characterWidth: number = 9;
            if (constraint && constraint.maxLength && !$input.is("textarea")) {
                var autoWidth = constraint.maxLength * characterWidth;
                $input.width(autoWidth);
            }
            $input.addClass('nts-editor nts-input');
            $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");

            let validator = this.getValidator(data);
            $input.on("keyup", (e) => {
                var newText = $input.val();
                var result = validator.validate(newText);
                $input.ntsError('clear');
                if (!result.isValid) {
                    $input.ntsError('set', result.errorMessage);
                }
            });

            $input.on("blur", (e) => {
                if (!readonly) {
                    var newText = $input.val();
                    var result = validator.validate(newText, {isCheckExpression: true});
                    $input.ntsError('clear');
                    if (result.isValid) {
                        if(value() === result.parsedValue){
                            $input.val(result.parsedValue);    
                        } else {
                            value(result.parsedValue);    
                        }
                    } else {
                        $input.ntsError('set', result.errorMessage);
                        value(newText);
                    }
                }
            });
            
            $input.on('validate', (function(e: Event) {
                var newText = $input.val();
                var result = validator.validate(newText);
                $input.ntsError('clear');
                if (!result.isValid) {
                    $input.ntsError('set', result.errorMessage);
                }
            }));
        }
        
        update($input: JQuery, data: any) {
            var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var textmode: string = editorOption.textmode;
            $input.attr('type', textmode);
            super.update($input, data);
        }

        getDefaultOption(): any {
            return new nts.uk.ui.option.TextEditorOption();
        }

        getFormatter(data: any): format.IFormatter {
            var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var constraint = validation.getConstraint(constraintName);
            return new text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: editorOption });
        }

        getValidator(data: any): validation.IValidator {
            var required: boolean = (data.required !== undefined) ? ko.unwrap(data.required) : false;
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            return new validation.StringValidator(constraintName, {required: required});
        }
    }

    /**
     * MultilineEditor Processor
     */
    class MultilineEditorProcessor extends EditorProcessor {

        update($input: JQuery, data: any) {
            var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var resizeable: string = editorOption.resizeable;
            $input.css('resize', (resizeable) ? "both" : "none");
            super.update($input, data);
        }

        getDefaultOption(): any {
            return new option.MultilineEditorOption();
        }

        getFormatter(data: any): format.IFormatter {
            var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var constraint = validation.getConstraint(constraintName);
            return new text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: editorOption });
        }

        getValidator(data: any): validation.IValidator {
            var required: boolean = (data.required !== undefined) ? ko.unwrap(data.required) : false;
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            return new validation.StringValidator(constraintName, {required: required});
        }
    }

    /**
     * NumberEditor Processor
     */
    class NumberEditorProcessor extends EditorProcessor {

        init($input: JQuery, data: any) {
            var option: any = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            $input.focus(() => {
                var selectionType = document.getSelection().type;
                // remove separator (comma)
                $input.val(data.value());
                
                // if focusing is caused by Tab key, select text.
                // this code is needed because removing separator deselects.
                if (selectionType === 'Range') {
                    $input.select();
                }
            });
            super.init($input, data);
        }

        update($input: JQuery, data: any) {
            super.update($input, data);
            var option: any = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var $parent = $input.parent();
            var width: string = option.width;
            var parentTag = $parent.parent().prop("tagName").toLowerCase();
            if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                $parent.css({ 'width': '100%' });
            }
            $input.css("box-sizing", "border-box");
            if (width.trim() != "")
                $input.width(width);
            if (option.currencyformat !== undefined && option.currencyformat !== null) {
                $parent.addClass("symbol").addClass(option.currencyposition === 'left' ? 'symbol-left' : 'symbol-right');
                var format = option.currencyformat === "JPY" ? "\u00A5" : '$';
                $parent.attr("data-content", format);
            } else if (option.symbolChar !== undefined && option.symbolChar !== "" && option.symbolPosition !== undefined) {
                $parent.addClass("symbol").addClass(option.symbolPosition === 'right' ? 'symbol-right' : 'symbol-left');
                $parent.attr("data-content", option.symbolChar);
            }
        }

        getDefaultOption(): any {
            return new nts.uk.ui.option.NumberEditorOption();
        }

        getFormatter(data: any): format.IFormatter {
            var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            return new text.NumberFormatter({ option: option });
        }

        getValidator(data: any): validation.IValidator {
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            return new validation.NumberValidator(constraintName, option);
        }
    }

    /**
     * TimeEditor Processor
     */
    class TimeEditorProcessor extends EditorProcessor {

        update($input: JQuery, data: any) {
            super.update($input, data);
            var option: any = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var width: string = option.width;
            var parent = $input.parent();
            var parentTag = parent.parent().prop("tagName").toLowerCase();
            if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                parent.css({ 'width': '100%' });
            }
        }

        getDefaultOption(): any {
            return new nts.uk.ui.option.TimeEditorOption();
        }

        getFormatter(data: any): format.IFormatter {
            var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var inputFormat: string = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
            return new text.TimeFormatter({ inputFormat: inputFormat });
        }

        getValidator(data: any): validation.IValidator {
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var required: boolean = (data.required !== undefined) ? ko.unwrap(data.required) : false;
            var inputFormat: string = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
            return new validation.TimeValidator(constraintName, {required: required, outputFormat: inputFormat});
        }
    }

    /**
     * Base Editor
     */
    class NtsEditorBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new EditorProcessor().init($(element), valueAccessor());
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
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
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new TextEditorProcessor().init($(element), valueAccessor());
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new TextEditorProcessor().update($(element), valueAccessor());
        }
    }

    /**
     * NumberEditor
     */
    class NtsNumberEditorBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new NumberEditorProcessor().init($(element), valueAccessor());
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new NumberEditorProcessor().update($(element), valueAccessor());
        }
    }

    /**
     * TimeEditor
     */
    class NtsTimeEditorBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new TimeEditorProcessor().init($(element), valueAccessor());
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
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
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new MultilineEditorProcessor().init($(element), valueAccessor());
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new MultilineEditorProcessor().update($(element), valueAccessor());
        }
    }

    ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
    ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
    ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
    ko.bindingHandlers['ntsMultilineEditor'] = new NtsMultilineEditorBindingHandler();
}