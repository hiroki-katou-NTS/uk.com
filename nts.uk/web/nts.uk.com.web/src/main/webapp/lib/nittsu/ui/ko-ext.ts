module nts.uk.ui.koExtentions {

    import validation = nts.uk.ui.validation;

    class EditorProcessor {

        init($input: JQuery, data: any) {
            var setValue: (newText: string) => {} = data.value;
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var constraint = validation.getConstraint(constraintName);
            var characterWidth: number = 9;
            if (constraint && constraint.maxLength && !$input.is("textarea")) {
                var autoWidth = constraint.maxLength * characterWidth;
                $input.width(autoWidth);
            }
            $input.addClass('nts-editor').addClass("nts-input");
            $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
            
            $input.change(() => {
                var validator = this.getValidator(data);
                var newText = $input.val();
                var result = validator.validate(newText);
                $input.ntsError('clear');
                if (result.isValid) {
                    setValue(result.parsedValue);
                } else {
                    $input.ntsError('set', result.errorMessage);
                    setValue(newText);
                }
            });
            
            // format on blur
            $input.blur(() => {
                var validator = this.getValidator(data);
                var formatter = this.getFormatter(data);
                var newText = $input.val();
                var result = validator.validate(newText);
                if (result.isValid) {
                    $input.val(formatter.format(result.parsedValue));
                }
            });
        }

        update($input: JQuery, data: any) {
            var getValue: () => string = data.value;
            var required: boolean = (data.required !== undefined) ? ko.unwrap(data.required) : false;
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
            var readonly: boolean = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
            var option: any = (data.option !== undefined) ? ko.unwrap(data.option) : ko.mapping.fromJS(this.getDefaultOption());
            var placeholder: string = ko.unwrap(option.placeholder || '');
            var width: string = ko.unwrap(option.width || '');
            var textalign: string = ko.unwrap(option.textalign || '');

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

    class DynamicEditorProcessor extends EditorProcessor {

        getValidator(data: any): validation.IValidator {
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
            } else {
                if (constraint) {
                    if (constraint.valueType === 'String') {
                        return TextEditorProcessor.prototype.getValidator(data);
                    } else if (data.option) {
                        var option = ko.unwrap(data.option);
                        //If inputFormat presented, this is Date or Time Editor
                        if (option.inputFormat) {
                            return TimeEditorProcessor.prototype.getValidator(data);
                        } else {
                            return NumberEditorProcessor.prototype.getValidator(data);
                        }
                    }
                }
                return validation.createValidator(constraintName);
            }
        }

        getFormatter(data: any): format.IFormatter {
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
            } else {
                if (constraint) {
                    if (constraint.valueType === 'String') {
                        return TextEditorProcessor.prototype.getFormatter(data);
                    } else if (data.option) {
                        var option = ko.unwrap(data.option);
                        //If inputFormat presented, this is Date or Time Editor
                        if (option.inputFormat) {
                            return TimeEditorProcessor.prototype.getFormatter(data);
                        } else {
                            return NumberEditorProcessor.prototype.getFormatter(data);
                        }
                    }
                }
                return new format.NoFormatter();
            }
        }
    }

    class TextEditorProcessor extends EditorProcessor {

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
            return new validation.StringValidator(constraintName, required);
        }
    }

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
            var align = option.textalign !== "left" ? "right" : "left";
            $input.css({ 'text-align': align, "box-sizing": "border-box" });
            var $parent = $input.parent();
            var width = option.width;// ? option.width : '100%';
            var parentTag = $parent.parent().prop("tagName").toLowerCase();
            if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                $parent.css({ 'width': '100%' });
            }
            if (option.currencyformat !== undefined && option.currencyformat !== null) {
                $parent.addClass("symbol").addClass(
                    option.currencyposition === 'left' ? 'symbol-left' : 'symbol-right');
                $input.width(width);
                var format = option.currencyformat === "JPY" ? "\u00A5" : '$';
                $parent.attr("data-content", format);
            } else if (option.symbolChar !== undefined && option.symbolChar !== "" && option.symbolPosition !== undefined) {
                $parent.addClass("symbol").addClass(
                    option.symbolPosition === 'right' ? 'symbol-right' : 'symbol-left');
                $input.width(width);
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


    class TimeEditorProcessor extends EditorProcessor {

        update($input: JQuery, data: any) {
            super.update($input, data);
            var option: any = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();

            $input.css({ 'text-align': 'right', "box-sizing": "border-box" });
            var parent = $input.parent();
            parent.css({ "display": "inline-block" });
            var parentTag = parent.parent().prop("tagName").toLowerCase();
            var width = option.width;// ? option.width : '100%';
            if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                parent.css({ 'width': '100%' });
            }
            $input.css({ 'paddingLeft': '12px', 'width': width });
        }

        getDefaultOption(): any {
            return new nts.uk.ui.option.TimeEditorOption();
        }

        getFormatter(data: any): format.IFormatter {
            var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            return new text.TimeFormatter({ option: option });
        }

        getValidator(data: any): validation.IValidator {
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            return new validation.TimeValidator(constraintName, option);
        }
    }

    class MultilineEditorProcessor extends EditorProcessor {

        update($input: JQuery, data: any) {
            var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
            var resizeable: string = ko.unwrap(editorOption.resizeable);
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
            return new validation.StringValidator(constraintName, required);
        }
    }

    /**
     * Editor
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

    class NtsDynamicEditorBindingHandler extends NtsEditorBindingHandler {

        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new DynamicEditorProcessor().init($(element), valueAccessor());
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new DynamicEditorProcessor().update($(element), valueAccessor());
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
     * SearchBox Binding Handler
     */
    var filteredArray = function(array, searchTerm, fields, childField) {
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
        var filtered = ko.utils.arrayFilter(flatArr, function(item) {
            var i = fields.length;
            while (i--) {
                var prop = fields[i];
                var strProp = ("" + item[prop]).toLocaleLowerCase();
                if (strProp.indexOf(filter) !== -1) {
                    return true;
                };
            }
            return false;
        });
        return filtered;
    };
    var getNextItem = function(selected, arr, searchResult, selectedKey, isArray) {
        //        console.log(selected + "," + selectedKey + "," + compareKey);
        //        console.log(isArray);
        var current = null;
        if (isArray) {
            if (selected.length > 0) current = selected[0];
        } else if (selected !== undefined && selected !== '' && selected !== null) {
            current = selected;
        }
        if (searchResult.length > 0) {
            if (current) {
                var currentIndex = nts.uk.util.findIndex(arr, current, selectedKey);
                var nextIndex = 0;
                var found = false;
                for (var i = 0; i < searchResult.length; i++) {
                    var item = searchResult[i];
                    var itemIndex = nts.uk.util.findIndex(arr, item[selectedKey], selectedKey);
                    if (!found && itemIndex >= currentIndex + 1) {
                        found = true;
                        nextIndex = i;
                    }
                    if ((i < searchResult.length - 1) && item[selectedKey] == current) return searchResult[i + 1][selectedKey];
                }
                return searchResult[nextIndex][selectedKey];
            }
            return searchResult[0][selectedKey];
        }
        return undefined;
    }
    class NtsSearchBoxBindingHandler extends NtsEditorBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            
            var searchBox = $(element);
            var data = valueAccessor();
            var fields = ko.unwrap(data.fields);
            var searchText = (data.searchText !== undefined) ? ko.unwrap(data.searchText) : "検索";
            var placeHolder = (data.placeHolder !== undefined) ? ko.unwrap(data.placeHolder) : "コード・名称で検索・・・";
            var selected = data.selected;
            var selectedKey = null;
            if (data.selectedKey) {
                selectedKey = ko.unwrap(data.selectedKey);
            }
            var arr = ko.unwrap(data.items);
            var component = $("#" + ko.unwrap(data.comId));
            var childField = null;
            if (data.childField) {
                childField = ko.unwrap(data.childField);
            }
            searchBox.data("searchResult", nts.uk.util.flatArray(arr, childField));
            var $container = $(element);
            $container.append("<input class='ntsSearchBox' type='text' />");
            $container.append("<button class='search-btn caret-bottom'>" + searchText + "</button>");
            var $input = $container.find("input.ntsSearchBox");
            $input.attr("placeholder", placeHolder);
            var $button = $container.find("button.search-btn");
            $input.outerWidth($container.outerWidth(true) - $button.outerWidth(true));
            var nextSearch = function() {
                var filtArr = searchBox.data("searchResult");
                var compareKey = fields[0];
                var isArray = $.isArray(selected());
                var selectedItem = getNextItem(selected(), nts.uk.util.flatArray(arr, childField), filtArr, selectedKey, isArray);
                //                console.log(selectedItem);
                if (data.mode) {
                    if (data.mode == 'igGrid') {
                        var selectArr = []; selectArr.push("" + selectedItem);
                        component.ntsGridList("setSelected", selectArr);
                        data.selected(selectArr);
                        component.trigger("selectChange");
                    } else if (data.mode == 'igTree') {
                        var liItem = $("li[data-value='" + selectedItem + "']");
                        component.igTree("expandToNode", liItem);
                        component.igTree("select", liItem);
                    }
                } else {
                    if (!isArray) selected(selectedItem);
                    else {
                        selected([]);
                        selected.push(selectedItem);
                    }
                    component.trigger("selectChange");
                    //console.log(selectedItem); 
                }
            }
            $input.keyup(function() {
                $input.change();
                //console.log('change');
            }).keydown(function(event) {
                if (event.which == 13) {
                    event.preventDefault();
                    nextSearch();
                }
            });
            $input.change(function(event) {
                var searchTerm = $input.val();
                searchBox.data("searchResult", filteredArray(ko.unwrap(data.items), searchTerm, fields, childField));
            });
            $button.click(nextSearch);
        }
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var searchBox = $(element);
            var $input = searchBox.find("input.ntsSearchBox");
            var searchTerm = $input.val();
            var data = valueAccessor();
            var arr = ko.unwrap(data.items);
            var fields = ko.unwrap(data.fields);
            var childField = null;
            if (data.childField) {
                childField = ko.unwrap(data.childField);
            }
            searchBox.data("searchResult", filteredArray(arr, searchTerm, fields, childField));
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

    /**
     * Dialog binding handler
     */
    class NtsDialogBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var option: any = ko.unwrap(data.option);
            var title: string = ko.unwrap(data.title);
            var message: string = ko.unwrap(data.message);
            var modal: boolean = ko.unwrap(option.modal);
            var show: boolean = ko.unwrap(option.show);
            var buttons: any = ko.unwrap(option.buttons);

            var $dialog = $("<div id='ntsDialog'></div>");
            if (show == true) {
                $('body').append($dialog);
                // Create Buttons
                var dialogbuttons = [];
                for (let button of buttons) {
                    dialogbuttons.push({
                        text: ko.unwrap(button.text),
                        "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                        click: function() { button.click(bindingContext.$data, $dialog) }
                    });
                }
                // Create dialog
                $dialog.dialog({
                    title: title,
                    modal: modal,
                    closeOnEscape: false,
                    buttons: dialogbuttons,
                    dialogClass: "no-close",
                    open: function() {
                        $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                        $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                        $('.ui-widget-overlay').last().css('z-index', 120000);
                    },
                    close: function(event) {
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
        }
    }

    /**
     * Error Dialog binding handler
     */
    class NtsErrorDialogBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var option: any = ko.unwrap(data.option);
            var title: string = ko.unwrap(data.title);
            var headers: Array<any> = ko.unwrap(option.headers);
            var modal: boolean = ko.unwrap(option.modal);
            var show: boolean = ko.unwrap(option.show);
            var buttons: any = ko.unwrap(option.buttons);

            var $dialog = $("<div id='ntsErrorDialog'></div>");

            $('body').append($dialog);
            // Create Buttons
            var dialogbuttons = [];
            for (let button of buttons) {
                dialogbuttons.push({
                    text: ko.unwrap(button.text),
                    "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                    click: function() { button.click(bindingContext.$data, $dialog) }
                });
            }
            // Calculate width
            var dialogWidth: number = 40 + 35 + 17;
            headers.forEach(function(header, index) {
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
                open: function() {
                    $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                    $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                    $('.ui-widget-overlay').last().css('z-index', 120000);
                },
                close: function(event) {
                    bindingContext.$data.option.show(false);
                }
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var option: any = ko.unwrap(data.option);
            var title: string = ko.unwrap(data.title);
            var errors: Array<any> = ko.unwrap(data.errors);
            var headers: Array<any> = ko.unwrap(option.headers);
            var displayrows: number = ko.unwrap(option.displayrows);
            var maxrows: number = ko.unwrap(option.maxrows);
            var autoclose: boolean = ko.unwrap(option.autoclose);
            var show: boolean = ko.unwrap(option.show);

            var $dialog = $("#ntsErrorDialog");

            if (show == true) {
                $dialog.dialog("open");
                // Create Error Table
                var $errorboard = $("<div id='error-board'></div>");
                var $errortable = $("<table></table>");
                // Header
                var $header = $("<thead><tr></tr></thead>");
                $header.find("tr").append("<th style='width: 35px'></th>");
                headers.forEach(function(header, index) {
                    if (ko.unwrap(header.visible)) {
                        let $headerElement = $("<th>" + ko.unwrap(header.text) + "</th>").width(ko.unwrap(header.width));
                        $header.find("tr").append($headerElement);
                    }
                });
                $errortable.append($header);
                // Body
                var $body = $("<tbody></tbody>");
                errors.forEach(function(error, index) {
                    if (index < maxrows) {
                        // Row
                        let $row = $("<tr></tr>");
                        $row.append("<td style='width:35px'>" + (index + 1) + "</td>");
                        headers.forEach(function(header) {
                            if (ko.unwrap(header.visible))
                                if (error.hasOwnProperty(ko.unwrap(header.name))) {
                                    // TD
                                    let $column = $("<td>" + error[ko.unwrap(header.name)] + "</td>").width(ko.unwrap(header.width));
                                    $row.append($column);
                                }
                        });
                        $body.append($row);
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
        }
    }

    /**
     * Switch button binding handler
     */
    class NtsSwitchButtonBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var selectedCssClass = 'selected';
            // Get options.
            var options: Array<any> = ko.unwrap(data.options);

            // Get options value.
            var optionValue = ko.unwrap(data.optionsValue);
            var optionText = ko.unwrap(data.optionsText);
            var selectedValue = ko.unwrap(data.value);
            var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
            // Container.
            var container = $(element);

            // Remove deleted button.
            $('button', container).each(function(index, btn) {
                var $btn = $(btn);
                var btnValue = $(btn).data('swbtn');
                // Check if btn is contained in options.
                var foundFlag = _.findIndex(options, function(opt) {
                    return opt[optionValue] == btnValue;
                }) != -1;

                if (!foundFlag) {
                    $btn.remove();
                    return;
                }
            })

            // Start binding new state.
            _.forEach(options, function(opt) {
                var value = opt[optionValue];
                var text = opt[optionText];

                // Find button.
                var targetBtn: JQuery;
                $('button', container).each(function(index, btn) {
                    var btnValue = $(btn).data('swbtn');
                    if (btnValue == value) {
                        targetBtn = $(btn);
                    }

                    if (btnValue == selectedValue) {
                        $(btn).addClass(selectedCssClass);
                    } else {
                        $(btn).removeClass(selectedCssClass);
                    }
                })

                if (targetBtn) {
                    // Do nothing.
                } else {
                    // Recreate
                    var btn = $('<button>').text(text)
                        .addClass('nts-switch-button')
                        .data('swbtn', value)
                        .on('click', function() {
                            var selectedValue = $(this).data('swbtn');
                            data.value(selectedValue);
                            $('button', container).removeClass(selectedCssClass);
                            $(this).addClass(selectedCssClass);
                        })
                    if (selectedValue == value) {
                        btn.addClass(selectedCssClass);
                    }
                    container.append(btn);
                }
            });
            // Enable
            (enable === true) ? $('button', container).prop("disabled", false) : $('button', container).prop("disabled", true);
        }
    }

    class NtsCheckboxBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var setChecked = data.checked;
            var textId: string = data.text;
            var checkBoxText: string;

            // Container
            var container = $(element);
            container.addClass("ntsControl");

            if (textId) {
                checkBoxText = textId;
            } else {
                checkBoxText = container.text();
                container.text('');
            }

            var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
            var checkBox = $('<input type="checkbox">').on("change", function() {
                if (typeof setChecked === "function")
                    setChecked($(this).is(":checked"));
            }).appendTo(checkBoxLabel);
            var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
            if(checkBoxText && checkBoxText.length > 0)
                var label = $("<span class='label'></span>").text(checkBoxText).appendTo(checkBoxLabel);
            checkBoxLabel.appendTo(container);
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var checked: boolean = ko.unwrap(data.checked);
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;

            // Container
            var container = $(element);
            var checkBox = $(element).find("input[type='checkbox']");

            // Checked
            checkBox.prop("checked", checked);
            // Enable
            (enable === true) ? checkBox.removeAttr("disabled") : checkBox.attr("disabled", "disabled");
        }
    }

    /**
     * Multi Checkbox
     */
    class NtsMultiCheckBoxBindingHandler implements KnockoutBindingHandler {

        constructor() { }

        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext) {
            $(element).addClass("ntsControl");
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var options: any = ko.unwrap(data.options);
            var optionValue: string = ko.unwrap(data.optionsValue);
            var optionText: string = ko.unwrap(data.optionsText);
            var selectedValue: any = data.value;
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;

            // Container
            var container = $(element);

            // Get option or option[optionValue]
            var getOptionValue = (item) => {
                return (optionValue === undefined) ? item : item[optionValue];
            };

            // Render
            if (!_.isEqual(container.data("options"), options)) {
                container.empty();
                _.forEach(options, (option) => {
                    var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                    var checkBox = $('<input type="checkbox">').data("value", getOptionValue(option)).on("change", function() {
                        var self = this;
                        if ($(self).is(":checked"))
                            selectedValue.push($(self).data("value"));
                        else
                            selectedValue.remove(_.find(selectedValue(), (value) => {
                                return _.isEqual(value, $(this).data("value"))
                            }));
                    }).appendTo(checkBoxLabel);
                    var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                    if (option[optionText] && option[optionText].length > 0)
                        var label = $("<span class='label'></span>").text(option[optionText]).appendTo(checkBoxLabel);
                    checkBoxLabel.appendTo(container);
                });
                // Save a clone
                container.data("options", options.slice());
            }

            // Checked
            container.find("input[type='checkbox']").prop("checked", function() {
                return (_.find(selectedValue(), (value) => {
                    return _.isEqual(value, $(this).data("value"))
                }) !== undefined);
            });
            // Enable
            (enable === true) ? container.find("input[type='checkbox']").removeAttr("disabled") : container.find("input[type='checkbox']").attr("disabled", "disabled");
        }
    }

    /**
     * RadioBox Group
     */
    class NtsRadioBoxGroupBindingHandler implements KnockoutBindingHandler {

        constructor() { }

        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext) {
            $(element).addClass("ntsControl");
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var options: any = ko.unwrap(data.options);
            var optionValue: string = ko.unwrap(data.optionsValue);
            var optionText: string = ko.unwrap(data.optionsText);
            var selectedValue: any = data.value;
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;

            // Container
            var container = $(element);

            var getOptionValue = (item) => {
                return (optionValue === undefined) ? item : item[optionValue];
            };

            // Render
            if (!_.isEqual(container.data("options"), options)) {
                var radioName = util.randomId();
                container.empty();
                _.forEach(options, (option) => {
                    var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                    var radioBox = $('<input type="radio">').attr("name", radioName).data("value", getOptionValue(option)).on("change", function() {
                        var self = this;
                        if ($(self).is(":checked"))
                            selectedValue($(self).data("value"));
                    }).appendTo(radioBoxLabel);
                    var box = $("<span class='box'></span>").appendTo(radioBoxLabel);
                    if (option[optionText] && option[optionText].length > 0)
                        var label = $("<span class='label'></span>").text(option[optionText]).appendTo(radioBoxLabel);
                    radioBoxLabel.appendTo(container);
                });

                // Save a clone
                container.data("options", options.slice());
            }

            // Checked
            var checkedRadio = _.find(container.find("input[type='radio']"), (item) => {
                return _.isEqual($(item).data("value"), selectedValue());
            });
            if (checkedRadio !== undefined)
                $(checkedRadio).prop("checked", true);

            // Enable
            (enable === true) ? container.find("input[type='radio']").removeAttr("disabled") : container.find("input[type='radio']").attr("disabled", "disabled");
        }
    }

    /**
     * Help Button
     */
    class NtsHelpButtonBindingHandler implements KnockoutBindingHandler {

        constructor() { }

        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext) {
            // Get data
            var data = valueAccessor();
            var image: string = ko.unwrap(data.image);
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
            var position: string = ko.unwrap(data.position);

            //Position
            var myPositions: Array<string> = position.replace(/[^a-zA-Z ]/gmi, "").split(" ");
            var atPositions: Array<string> = position.split(" ");
            var operator: number = 1;
            var marginDirection: string = "";
            var caretDirection: string = "";
            var caretPosition: string = "";
            if (myPositions[0].search(/(top|left)/i) !== -1) {
                operator = -1;
            }
            if (myPositions[0].search(/(left|right)/i) === -1) {
                atPositions[0] = atPositions.splice(1, 1, atPositions[0])[0];
                myPositions[0] = myPositions.splice(1, 1, myPositions[0])[0];
                caretDirection = myPositions[1] = text.reverseDirection(myPositions[1]);
                caretPosition = "left";
                marginDirection = "margin-top";
            }
            else {
                caretDirection = myPositions[0] = text.reverseDirection(myPositions[0]);
                caretPosition = "top";
                marginDirection = "margin-left";
            }

            // Container
            $(element).on("click", function() {
                if ($popup.is(":visible")) {
                    $popup.hide();
                }
                else {
                    let CARET_WIDTH = parseFloat($caret.css("font-size")) * 2;
                    $popup.show()
                        .css(marginDirection, 0)
                        .position({
                            my: myPositions[0] + " " + myPositions[1],
                            at: atPositions[0] + " " + atPositions[1],
                            of: $(element),
                            collision: "none"
                        })
                        .css(marginDirection, CARET_WIDTH * operator);
                    $caret.css(caretPosition, parseFloat($popup.css(caretPosition)) * -1);
                }
            }).wrap($("<div class='ntsControl ntsHelpButton'></div>"));
            var $container = $(element).closest(".ntsHelpButton");
            var $caret = $("<span class='caret-helpbutton caret-" + caretDirection + "'></span>");
            var $popup = $("<div class='nts-help-button-image'></div>")
                .append($caret)
                .append($("<img src='" + request.resolvePath(image) + "' />"))
                .appendTo($container).hide();
            // Click outside event
            $("html").on("click", function(event) {
                if (!$container.is(event.target) && $container.has(event.target).length === 0) {
                    $container.find(".nts-help-button-image").hide();
                }
            });
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;

            // Enable
            (enable === true) ? $(element).removeAttr("disabled") : $(element).attr("disabled", "disabled");

        }
    }

    /**
     * ComboBox binding handler
     */
    class ComboBoxBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var self = this;

            // Get options.
            var options: Array<any> = ko.unwrap(data.options);

            // Get options value.
            var optionValue = ko.unwrap(data.optionsValue);
            var optionText = ko.unwrap(data.optionsText);
            var selectedValue = ko.unwrap(data.value);
            var editable = ko.unwrap(data.editable);
            var enable: boolean = ko.unwrap(data.enable);
            var columns: Array<any> = ko.unwrap(data.columns);

            // Container.
            var container = $(element);
            var comboMode: string = editable ? 'editable' : 'dropdown';

            // Default values.
            var distanceColumns = '     ';
            var fillCharacter = ' '; // Character used fill to the columns.
            var maxWidthCharacter = 15;

            // Check selected code.
            if (_.find(options, item => item[optionValue] === selectedValue) === undefined && !editable) {
                selectedValue = options.length > 0 ? options[0][optionValue] : '';
                data.value(selectedValue);
            }

            var haveColumn = columns && columns.length > 0;

            var isChangeOptions = !_.isEqual(container.data("options"), options);
            if (isChangeOptions) {
                container.data("options", options.slice());
                options = options.map((option) => {
                    var newOptionText: string = '';

                    // Check muti columns.
                    if (haveColumn) {
                        _.forEach(columns, function(item, i) {
                            var prop: string = option[item.prop];
                            var length: number = item.length;

                            if (i === columns.length - 1) {
                                newOptionText += prop;
                            } else {
                                newOptionText += text.padRight(prop, fillCharacter, length) + distanceColumns;
                            }
                        });

                    } else {
                        newOptionText = option[optionText];
                    }
                    // Add label attr.
                    option['nts-combo-label'] = newOptionText;
                    return option;
                });
            }

            var currentColumnSetting = container.data("columns");
            var currentComboMode = container.data("comboMode");
            var isInitCombo = !_.isEqual(currentColumnSetting, columns) || !_.isEqual(currentComboMode, comboMode);
            if (isInitCombo) {
                // Delete igCombo.
                if (container.data("igCombo") != null) {
                    container.igCombo('destroy');
                    container.removeClass('ui-state-disabled');
                }

                // Set attribute for multi column.
                var itemTemplate: string = undefined;
                if (haveColumn) {
                    itemTemplate = '<div class="nts-combo-item">';
                    _.forEach(columns, function(item, i) {
                        // Set item template.
                        itemTemplate += '<div class="nts-column nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                    });
                    itemTemplate += '</div>';
                }

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
                    itemTemplate: itemTemplate,
                    selectionChanged: function(evt: any, ui: any) {
                        if (ui.items.length > 0) {
                            data.value(ui.items[0].data[optionValue]);
                        }
                    }
                });
            } else {
                container.igCombo("option", "disabled", !enable);
            }
            if (isChangeOptions && !isInitCombo) {
                container.igCombo("option", "dataSource", options);
                container.igCombo("dataBind");
            }
            if (selectedValue !== undefined && selectedValue !== null) {
                container.igCombo("value", selectedValue);
            }

            // Set width for multi columns.
            if (haveColumn && (isChangeOptions || isInitCombo)) {
                var totalWidth = 0;
                var $dropDownOptions = $(container.igCombo("dropDown"));
                _.forEach(columns, function(item, i) {
                    var charLength: number = item.length;
                    var width = charLength * maxWidthCharacter + 10;
                    $dropDownOptions.find('.nts-combo-column-' + i).width(width);
                    if (i != columns.length - 1) {
                        $dropDownOptions.find('.nts-combo-column-' + i).css({ 'float': 'left' });
                    }
                    totalWidth += width + 10;
                });
                $dropDownOptions.find('.nts-combo-item').css({ 'min-width': totalWidth });
                container.css({ 'min-width': totalWidth });
            }

            container.data("columns", columns);
            container.data("comboMode", comboMode);
        }
    }
    
    function selectOnListBox(event) {
        var container = $(event.delegateTarget);
        container.find(".ui-selected").removeClass('ui-selected');
        $(this).addClass('ui-selected');
        container.data('value', $(this).data('value'));
        document.getElementById(container.attr('id')).dispatchEvent(event.data.event);
    }
    /**
     * ListBox binding handler
     */
    class ListBoxBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }
        
        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();

            // Get options
            var options: Array<any> = ko.unwrap(data.options);
            // Get options value
            var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
            var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
            var selectedValue = ko.unwrap(data.value);
            var isMultiSelect = ko.unwrap(data.multiple);
            var enable: boolean = ko.unwrap(data.enable);
            var required = ko.unwrap(data.required) || false;
            // Container
            var container = $(element);
            container.addClass('ntsListBox ntsControl').data('required', required);
            container.data("options", options.slice());
            container.data("init", true);
            container.data("enable", enable);
            
            // Create select
            container.append('<ol class="nts-list-box"></ol>');
            var selectListBoxContainer = container.find('.nts-list-box');

            // Create changing event.
            var changeEvent = new CustomEvent("selectionChange", {
                detail: {},
            });
            container.data("selectionChange", changeEvent);
            if (isMultiSelect) {
                // Bind selectable.
                selectListBoxContainer.selectable({
                    filter: 'li',
                    selected: function(event, ui) {
                    },
                    stop: function(event, ui) {
                        // Add selected value.
                        var data = [];
                        $("li.ui-selected", container).each(function(index, opt) {
                            data[index] = $(opt).data('value');
                        });
                        container.data('value', data);

                        // fire event change.
                        document.getElementById(container.attr('id')).dispatchEvent(changeEvent);
                    },
                    selecting: function(event, ui) {
                        if ((<any>event).shiftKey) {
                            if ($(ui.selecting).attr("clicked") !== "true") {
                                var source = container.find("li");
                                var clicked = _.find(source, function(row) {
                                    return $(row).attr("clicked") === "true";
                                });
                                if (clicked === undefined) {
                                    $(ui.selecting).attr("clicked", "true");
                                } else {
                                    container.find("li").attr("clicked", "");
                                    $(ui.selecting).attr("clicked", "true");
                                    var start = parseInt($(clicked).attr("data-idx"));
                                    var end = parseInt($(ui.selecting).attr("data-idx"));
                                    var max = start > end ? start : end;
                                    var min = start < end ? start : end;
                                    var range = _.filter(source, function(row) {
                                        var index = parseInt($(row).attr("data-idx"));
                                        return index >= min && index <= max;
                                    });
                                    $(range).addClass("ui-selected");
                                }
                            }
                        } else if (!(<any>event).ctrlKey) {
                            container.find("li").attr("clicked", "");
                            $(ui.selecting).attr("clicked", "true");
                        }
                    }
                });
            }
            else {
                container.on("click", "li", { event: changeEvent }, selectOnListBox);
            }
            
            // Fire event.
            container.on('selectionChange', (function(e: Event) {
                // Check is multi-selection.
                var itemsSelected: any = container.data('value');

                data.value(itemsSelected);
                container.data("selected", !isMultiSelect ? itemsSelected : itemsSelected.slice());
            }));
            
            container.on('validate', (function(e: Event) {
                // Check empty value
                var itemsSelected: any = container.data('value');
                if ((itemsSelected === undefined || itemsSelected === null || itemsSelected.length == 0)
                    && container.data("enable")) {
                    selectListBoxContainer.ntsError('set', 'at least 1 item selection required');
                } else {
                    selectListBoxContainer.ntsError('clear');
                }
            }));
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            // Get options.
            var options: Array<any> = ko.unwrap(data.options);

            // Get options value.
            var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
            var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
            var selectedValue = ko.unwrap(data.value);
            var isMultiSelect = ko.unwrap(data.multiple);
            var enable: boolean = ko.unwrap(data.enable);
            var columns: Array<any> = data.columns;
            var rows = data.rows;
            // Container.
            var container = $(element);
            var selectListBoxContainer = container.find('.nts-list-box');
            var maxWidthCharacter = 15;
            var required = ko.unwrap(data.required) || false;
            container.data('required', required);

            var getOptionValue = item => {
                if (optionValue === undefined) {
                    return item;
                } else {
                    return item[optionValue];
                }
            };
            var originalOptions = container.data("options");
            var init = container.data("init");
            var originalSelected = container.data("selected");
            
            if (!_.isEqual(originalOptions, options) || init) {
                if (!init) {
                    // Remove options.
                    $('li', container).each(function(index, option) {
                        var optValue = $(option).data('value');
                        // Check if btn is contained in options.
                        var foundFlag = _.findIndex(options, function(opt) {
                            return getOptionValue(opt) === optValue;
                        }) !== -1;
                        if (!foundFlag) {

                            // Remove selected if not found option.
                            selectedValue = jQuery.grep(selectedValue, function(value: string) {
                                return value !== optValue;
                            });
                            option.remove();
                            return;
                        }
                    })
                }

                // Append options.
                options.forEach((item, idx) => {
                    
                    
                    // Check option is Selected
                    var isSelected: boolean = false;
                    if (isMultiSelect) {
                        isSelected = (<Array<any>>selectedValue).indexOf(getOptionValue(item)) !== -1;
                    } else {
                        isSelected = selectedValue === getOptionValue(item);
                    }
                    var target = _.find($('li', container), function(opt) {
                        var optValue = $(opt).data('value');
                        return optValue == getOptionValue(item);
                    });
                    if (init || target === undefined) {
                        // Add option
                        var selectedClass = isSelected ? 'ui-selected' : '';
                        var itemTemplate: string = '';
                        if (columns && columns.length > 0) {
                            columns.forEach((col, cIdx) => {
                                itemTemplate += '<div class="nts-column nts-list-box-column-' + cIdx + '">' + item[col.key !== undefined ? col.key : col.prop] + '</div>';
                            });
                        } else {
                            itemTemplate = '<div class="nts-column nts-list-box-column-0">' + item[optionText] + '</div>';
                        }
                        
                        $('<li/>').addClass(selectedClass).attr("data-idx", idx)
                            .html(itemTemplate).data('value', getOptionValue(item))
                            .appendTo(selectListBoxContainer);
                        
                    } else {
                        var targetOption = $(target);
                        if (isSelected) {
                            targetOption.addClass('ui-selected');
                        } else {
                            targetOption.removeClass('ui-selected');
                        }
                    }

                });

                var padding = 10;
                var rowHeight = 28;
                // Set width for multi columns
                if (columns && columns.length > 0) {
                    var totalWidth = 0;
                    columns.forEach((item, cIdx) => {
                        container.find('.nts-list-box-column-' + cIdx).width(item.length * maxWidthCharacter + 20);
                        totalWidth += item.length * maxWidthCharacter + 20;
                    });
                    totalWidth += padding * (columns.length + 1); // + 50;
                    container.find('.nts-list-box > li').css({ 'width': totalWidth });
                    container.find('.nts-list-box').css({ 'width': totalWidth });
                    container.css({ 'width': totalWidth });
                }
                if (rows && rows > 0) {
                    container.css('height', rows * rowHeight);
                    container.find('.nts-list-box').css('height', rows * rowHeight);
                }
            }
            container.data("options", options.slice());
            container.data("init", false);

            // Set value
            if (!_.isEqual(originalSelected, selectedValue) || init) {
                container.data('value', selectedValue);
                container.trigger('selectionChange');
            }
            
            if (isMultiSelect) {
                // Check enable
                if (!enable) {
                    selectListBoxContainer.selectable("disable");;
                    container.addClass('disabled');
                } else {
                    selectListBoxContainer.selectable("enable");
                    container.removeClass('disabled');
                }
            } else {
                if (!enable) {
                    //selectListBoxContainer.selectable("disable");;
                    container.off("click", "li");
                    container.addClass('disabled');
                } else {
                    //selectListBoxContainer.selectable("enable");
                    if (container.hasClass("disabled")) {
                        container.on("click", "li", { event: container.data("selectionChange") }, selectOnListBox);
                        container.removeClass('disabled');
                    }
                }
            }
            container.data("enable", enable);
            
            if (!(selectedValue === undefined || selectedValue === null || selectedValue.length == 0)) {
                container.trigger('validate');
            }
        }
    }

    /**
     * GridList binding handler
     */
    class NtsGridListBindingHandler implements KnockoutBindingHandler {

        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var HEADER_HEIGHT = 27;

            var $grid = $(element);

            if (nts.uk.util.isNullOrUndefined($grid.attr('id'))) {
                throw new Error('the element NtsGridList must have id attribute.');
            }

            var data = valueAccessor();
            var optionsValue: string = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
            var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
            var deleteOptions = ko.unwrap(data.deleteOptions);
            var observableColumns = ko.unwrap(data.columns);
            var iggridColumns = _.map(observableColumns, c => {
                c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                c["dataType"] = 'string';
                return c;
            });

            var features = [];
            features.push({ name: 'Selection', multipleSelection: data.multiple });
            features.push({ name: 'Sorting', type: 'local' });
            features.push({ name: 'RowSelectors', enableCheckBoxes: data.multiple, enableRowNumbering: true });

            $grid.igGrid({
                width: data.width,
                height: (data.height) + "px",
                primaryKey: optionsValue,
                columns: iggridColumns,
                virtualization: true,
                virtualizationMode: 'continuous',
                features: features
            });

            if (!util.isNullOrUndefined(deleteOptions) && !util.isNullOrUndefined(deleteOptions.deleteField)
                && deleteOptions.visible === true) {
                var sources = (data.dataSource !== undefined ? data.dataSource : data.options);
                $grid.ntsGridList("setupDeleteButton", {
                    deleteField: deleteOptions.deleteField,
                    sourceTarget: sources
                });
            }

            $grid.ntsGridList('setupSelecting');

            $grid.bind('selectionchanged', () => {
                if (data.multiple) {
                    let selected: Array<any> = $grid.ntsGridList('getSelected');
                    if (selected) {
                        data.value(_.map(selected, s => s.id));
                    } else {
                        data.value([]);
                    }
                } else {
                    let selected = $grid.ntsGridList('getSelected');
                    if (selected) {
                        data.value(selected.id);
                    } else {
                        data.value('');
                    }
                }

            });
            var gridId = $grid.attr('id');
            $grid.setupSearchScroll("igGrid", true);
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var $grid = $(element);
            var data = valueAccessor();
            var optionsValue: string = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
            var currentSource = $grid.igGrid('option', 'dataSource');
            var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
            if (!_.isEqual(currentSource, sources)) {
                $grid.igGrid('option', 'dataSource', sources.slice());
                $grid.igGrid("dataBind");
            }

            var currentSelectedItems = $grid.ntsGridList('getSelected');
            var isEqual = _.isEqualWith(currentSelectedItems, data.value(), function(current, newVal) {
                if ((current === undefined && newVal === undefined) || (current !== undefined && current.id === newVal)) {
                    return true;
                }
            })
            if (!isEqual) {
                $grid.ntsGridList('setSelected', data.value());
            }

            $grid.closest('.ui-iggrid').addClass('nts-gridlist').height(data.height);
        }
    }


    /**
     * TreeGrid binding handler
     */
    class NtsTreeGridViewBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            // Get data.
            var data = valueAccessor();
            var options: Array<any> = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
            var optionsValue = ko.unwrap(data.primaryKey !== undefined ? data.primaryKey : data.optionsValue);
            var optionsText = ko.unwrap(data.primaryText !== undefined ? data.primaryText : data.optionsText);

            var optionsChild = ko.unwrap(data.childDataKey !== undefined ? data.childDataKey : data.optionsChild);
            var extColumns: Array<any> = ko.unwrap(data.columns !== undefined ? data.columns : data.extColumns);

            // Default.
            var showCheckBox = data.showCheckBox !== undefined ? ko.unwrap(data.showCheckBox) : true;

            var enable = data.enable !== undefined ? ko.unwrap(data.enable) : true;

            var height = ko.unwrap(data.height !== undefined ? data.height : '100%');
            var width = ko.unwrap(data.width !== undefined ? data.width : '100%');

            if (extColumns !== undefined && extColumns !== null) {
                var displayColumns = extColumns;
            } else {
                var displayColumns: Array<any> = [
                    { headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
                    { headerText: "コード／名称", key: optionsText, dataType: "string" }
                ];
            }

            // Init ig grid.
            var $treegrid = $(element);
            $(element).igTreeGrid({
                width: width,
                height: height,
                dataSource: _.cloneDeep(options),
                primaryKey: optionsValue,
                columns: displayColumns,
                childDataKey: optionsChild,
                initialExpandDepth: 10,
                features: [
                    {
                        name: "Selection",
                        multipleSelection: true,
                        activation: true,
                        rowSelectionChanged: function(evt: any, ui: any) {
                            var selectedRows: Array<any> = ui.selectedRows;
                            if (ko.unwrap(data.multiple)) {
                                if (ko.isObservable(data.selectedValues)) {
                                    data.selectedValues(_.map(selectedRows, function(row) {
                                        return row.id;
                                    }));
                                }
                            } else {
                                if (ko.isObservable(data.value)) {
                                    data.value(selectedRows.length <= 0 ? undefined : selectedRows[0].id);
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
            $treegrid.setupSearchScroll("igTreeGrid");
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var options: Array<any> = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
            var selectedValues: Array<any> = ko.unwrap(data.selectedValues);
            var singleValue = ko.unwrap(data.value);

            // Update datasource.
            var originalSource = $(element).igTreeGrid('option', 'dataSource');
            if (!_.isEqual(originalSource, options)) {
                $(element).igTreeGrid("option", "dataSource", _.cloneDeep(options));
                $(element).igTreeGrid("dataBind");
            }

            // Set multiple data source.
            var multiple = data.multiple != undefined ? ko.unwrap(data.multiple) : true;
            if ($(element).igTreeGridSelection("option", "multipleSelection") !== multiple) {
                $(element).igTreeGridSelection("option", "multipleSelection", multiple);
            }

            // Set show checkbox.
            var showCheckBox = ko.unwrap(data.showCheckBox != undefined ? data.showCheckBox : true);
            if ($(element).igTreeGridRowSelectors("option", "enableCheckBoxes") !== showCheckBox) {
                $(element).igTreeGridRowSelectors("option", "enableCheckBoxes", showCheckBox);
            }

            // Clear selection.
            if ((selectedValues === null || selectedValues === undefined) && (singleValue === null || singleValue === undefined)) {
                $(element).igTreeGridSelection("clearSelection");
            } else {
                // Compare value.
                var olds = _.map($(element).igTreeGridSelection("selectedRow"), function(row: any) {
                    return row.id;
                });
                // Not change, do nothing.
                if (multiple) {
                    if (_.isEqual(selectedValues.sort(), olds.sort())) {
                        return;
                    }
                    // Update.
                    $(element).igTreeGridSelection("clearSelection");
                    selectedValues.forEach(function(val) {
                        $(element).igTreeGridSelection("selectRowById", val);
                    })
                } else {
                    if (olds.length > 1 && olds[0] === singleValue) {
                        return;
                    }
                    $(element).igTreeGridSelection("clearSelection");
                    $(element).igTreeGridSelection("selectRowById", singleValue);
                }
            }
        }
    }

    class WizardBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            // Get step list
            var options: Array<any> = ko.unwrap(data.steps);
            var theme: string = ko.unwrap(data.theme);
            var cssClass: string = "nts-wizard " + "theme-" + theme;
            // Container
            var container = $(element);

            // Create steps
            for (var i = 0; i < options.length; i++) {
                var contentClass: string = ko.unwrap(options[i].content);
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
                onStepChanged: function() {
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
            }).data("length", options.length);

            // Add default class
            container.addClass(cssClass);
            container.children('.steps').children('ul').children('li').children('a').before('<div class="nts-steps"></div>');
            container.children('.steps').children('ul').children('li').children('a').addClass('nts-step-contents');
            //container.children('.steps').children('ul').children('.first').addClass('begin');
            container.children('.steps').children('ul').children('.last').addClass('end');
            container.children('.steps').children('ul').children('li').not('.begin').not('.end').children('.nts-steps').addClass('nts-steps-middle');
            container.find('.nts-steps-middle').append('<div class="nts-vertical-line"></div><div class="nts-bridge"><div class="nts-point"></div><div class="nts-horizontal-line"></div></div>')

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
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }
    }


    /**
     * FormLabel
     */
    class NtsFormLabelBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var primitiveValue = ko.unwrap(data.constraint);
            var isRequired = ko.unwrap(data.required) === true;
            var isInline = ko.unwrap(data.inline) === true;
            var isEnable = ko.unwrap(data.enable) !== false;
            var $formLabel = $(element).addClass('form-label');

            $('<label/>').html($formLabel.html()).appendTo($formLabel.empty());
            if (!isEnable) {
                $formLabel.addClass('disabled');
            } else {
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
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

        static buildConstraintText(primitiveValues: any) {
            if (!Array.isArray(primitiveValues))
                primitiveValues = [primitiveValues];
            let constraintText: string = "";
            _.forEach(primitiveValues, function(primitiveValue) {
                let constraint = __viewContext.primitiveValueConstraints[primitiveValue];
                switch (constraint.valueType) {
                    case 'String':
                        constraintText += (constraintText.length > 0) ? "/" : "";
                        constraintText += uk.text.getCharType(primitiveValue).buildConstraintText(constraint.maxLength);
                        break;
                    case 'Decimal':
                        constraintText += (constraintText.length > 0) ? "/" : "";
                        constraintText += constraint.min + "～" + constraint.max; 
                        break;
                    case 'Integer':
                        constraintText += (constraintText.length > 0) ? "/" : "";
                        constraintText += constraint.min + "～" + constraint.max; 
                        break;
                    default:
                        constraintText += 'ERROR';
                        break;
                }
            });
            return constraintText;
        }
    }


    /**
     * LinkButton
     */
    class NtsLinkButtonBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var jump = ko.unwrap(data.jump);
            var action = data.action;

            var linkText = $(element).text();
            var $linkButton = $(element).wrap('<div class="ntsControl"/>')
                .addClass('link-button')
                .click(function() {
                    event.preventDefault();
                    if (!nts.uk.util.isNullOrUndefined(action))
                        action.call(viewModel);
                    else if (!nts.uk.util.isNullOrUndefined(jump))
                        nts.uk.request.jump(jump);
                });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }
    }

    /**
     * Datepicker binding handler
     */
    function randomString(length, chars) {
        var result = '';
        for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
        return result;
    }
    class DatePickerBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            // Container.
            var container = $(element);
            if (!container.attr("id")) {
                var idString = randomString(10, 'abcdefghijklmnopqrstuvwxy0123456789zABCDEFGHIJKLMNOPQRSTUVWXYZ');
                container.attr("id", idString);
            }
            container.addClass("ntsControl");
            var startDate = null;
            var endDate = null;
            if (data.startDate) {
                startDate = ko.unwrap(data.startDate);
            }
            if (data.endDate) {
                endDate = ko.unwrap(data.endDate);
            }
            var autoHide = data.autoHide == false ? false : true;
            var idatr = container.attr("id");
            container.append("<input id='" + idatr + "_input' class='ntsDatepicker nts-input' />");
            var $input = container.find('#' + idatr + "_input");
            var button = null;
            if (data.button) button = idatr + "_button";
            $input.prop("readonly", true);

            var value = ko.unwrap(data.value);
            var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
            var containerFormat = 'yyyy/mm/dd';
            var length = 10, atomWidth = 9.5;
            if (dateFormat === "yyyy/MM/dd DDD") {
                length = 16;
            } else if (dateFormat === "yyyy/MM/dd D") {
                length = 14;
            } else if (dateFormat === "yyyy/MM") {
                length = 7;
                containerFormat = 'yyyy/mm';
            }
            if (containerFormat != 'yyyy/mm')
                //datepicker case
                $input.attr('value', nts.uk.time.formatDate(value, dateFormat));
            else //yearmonth picker case 
                $input.attr('value', value);
            if (button) {
                container.append("<input type='button' id='" + button + "' class='datepicker-btn' />");
                (<any>$input).datepicker({
                    format: containerFormat, // cast to avoid error
                    language: 'ja-JP',
                    trigger: "#" + button,
                    autoHide: autoHide,
                    startDate: startDate,
                    endDate: endDate
                });
            }
            else (<any>$input).datepicker({
                format: containerFormat, // cast to avoid error
                language: 'ja-JP',
                autoHide: autoHide,
                startDate: startDate,
                endDate: endDate
            });
            container.data("format", containerFormat);
            if (containerFormat !== 'yyyy/mm')
                $input.on('change', (event: any) => {
                    data.value(new Date($input.val().substring(0, 10)));
                });
            else
                $input.on('change', (event: any) => {
                    let result = nts.uk.time.parseYearMonth($input.val());
                    data.value(result.toValue());
                });
            $input.width(Math.floor(atomWidth * length));
            if (data.disabled !== undefined && ko.unwrap(data.disabled) == true) {
                $input.prop("disabled", true);
                if (button) {
                    container.find('.datepicker-btn').prop("disabled", true);
                }
            }
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var container = $(element);
            var idatr = container.attr("id");
            var newValue = ko.unwrap(data.value);
            //console.log(newValue);                       
            var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
            var $input = container.find('#' + idatr + "_input");
            var dateFormat = data.dateFormat ? ko.unwrap(data.dateFormat) : "yyyy/MM/dd";
            var formatOptions = container.data("format");
            var oldDate = $input.datepicker("getDate");
            if (formatOptions != 'yyyy/mm') {
                var oldDate = $input.datepicker("getDate");
                if (oldDate.getFullYear() != newValue.getFullYear() || oldDate.getMonth() != newValue.getMonth() || oldDate.getDate() != newValue.getDate())
                    $input.datepicker("setDate", newValue);
                $input.val(nts.uk.time.formatDate(newValue, dateFormat));
            } else {
                let formatted = nts.uk.time.parseYearMonth(newValue);
                var newDate = new Date(formatted.format() + "/01");
                var oldDate = $input.datepicker("getDate");
                if (oldDate.getFullYear() != newDate.getFullYear() || oldDate.getMonth() != newDate.getMonth())
                    $input.datepicker("setDate", newDate);
                $input.val(formatted.format());
            }
            if (data.disabled !== undefined && ko.unwrap(data.disabled) == true) {
                $input.prop("disabled", true);
                if (data.button) {
                    container.find('.datepicker-btn').prop("disabled", true);
                }
            }
        }
    }
    /**
     * TabPanel Binding Handler
     */
    class TabPanelBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var tabs: Array<any> = ko.unwrap(data.dataSource);
            var direction: string = ko.unwrap(data.direction || "horizontal");

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
                container.children(content).wrap('<div id="' + id + '"></div>')
            }
            container.tabs({
                activate: function(evt: Event, ui: any) {
                    data.active(ui.newPanel[0].id);
                    container.children('ul').children('.ui-tabs-active').addClass('active');
                    container.children('ul').children('li').not('.ui-tabs-active').removeClass('active');
                    container.children('ul').children('.ui-state-disabled').addClass('disabled');
                    container.children('ul').children('li').not('.ui-state-disabled').removeClass('disabled');
                }
            }).addClass(direction);
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            // Get tab list.
            var tabs: Array<any> = ko.unwrap(data.dataSource);
            // Container.
            var container = $(element);
            // Select tab.
            var activeTab = tabs.filter(tab => { return tab.id == data.active(); })[0];
            var indexActive = tabs.indexOf(activeTab);
            container.tabs("option", "active", indexActive);

            // Disable & visible tab.
            tabs.forEach(tab => {
                if (tab.enable()) {
                    container.tabs("enable", '#' + tab.id);
                    container.children('#' + tab.id).children('div').show();
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').removeClass('disabled');
                } else {
                    container.tabs("disable", '#' + tab.id);
                    container.children('#' + tab.id).children('div').hide();
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').addClass('disabled');
                }
                if (!tab.visible()) {
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').hide();
                } else {
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').show();
                }
            });
        }
    }
    
    class NtsSwapListBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var HEADER_HEIGHT = 27;
            var CHECKBOX_WIDTH = 70;
            var SEARCH_AREA_HEIGHT = 45;
            var BUTTON_SEARCH_WIDTH = 60;
            var INPUT_SEARCH_PADDING = 65;

            var $swap = $(element);
            var elementId = $swap.attr('id');
            if (nts.uk.util.isNullOrUndefined(elementId)) {
                throw new Error('the element NtsSwapList must have id attribute.');
            }

            var data = valueAccessor();
            var originalSource = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
            //            var selectedValues = ko.unwrap(data.value);
            var totalWidth = ko.unwrap(data.width);
            var height = ko.unwrap(data.height);
            var showSearchBox = ko.unwrap(data.showSearchBox);
            var primaryKey: string = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
            var columns: KnockoutObservableArray<any> = data.columns;

            $swap.wrap("<div class= 'ntsComponent ntsSwapList' id='" + elementId + "_container'/>");
            if (totalWidth !== undefined) {
                $swap.parent().width(totalWidth);
            }
            $swap.parent().height(height);
            $swap.addClass("ntsSwapList-container");

            var gridWidth = _.sumBy(columns(), c => {
                return c.width;
            });
            var iggridColumns = _.map(columns(), c => {
                c["key"] = c.key === undefined ? c.prop : c.key;
                c["dataType"] = 'string';
                return c;
            });
            var gridHeight = (height - 20);
            
            var grid1Id = "#" + elementId + "-grid1";
            var grid2Id = "#" + elementId + "-grid2";
            if (!util.isNullOrUndefined(showSearchBox) && (showSearchBox.showLeft || showSearchBox.showEright)) {
                var search = function ($swap, gridId, primaryKey) {
                    var value = $swap.find(".ntsSearchInput").val();
                    var source = $(gridId).igGrid("option", "dataSource").slice();
                    var selected = $(gridId).ntsGridList("getSelected");

                    if (selected.length > 0) {
                        var gotoEnd = source.splice(0, selected[0].index + 1);
                        source = source.concat(gotoEnd);
                    }
                    var searchedValues = _.find(source, function(val) {
                        return _.find(iggridColumns, function(x) {
                            return x !== undefined && x !== null && val[x["key"]].toString().indexOf(value) >= 0;
                        }) !== undefined;
                    });

                    $(gridId).ntsGridList('setSelected', searchedValues !== undefined ? [searchedValues[primaryKey]] : []);

                    if (searchedValues !== undefined && (selected.length === 0 ||
                        selected[0].id !== searchedValues[primaryKey])) {
                        var current = $(gridId).igGrid("selectedRows");
                        if (current.length > 0 && $(gridId).igGrid("hasVerticalScrollbar")) {
                            $(gridId).igGrid("virtualScrollTo", current[0].index === source.length - 1
                                ? current[0].index : current[0].index + 1);
                        }
                    }
                }
                
                var initSearchArea = function ($SearchArea, targetId){
                    $SearchArea.append("<div class='ntsSearchTextContainer'/>")
                        .append("<div class='ntsSearchButtonContainer'/>");
                
                    $SearchArea.find(".ntsSearchTextContainer")
                        .append("<input id = " + searchAreaId + "-input" + " class = 'ntsSearchInput ntsSearchBox'/>");
                    $SearchArea.find(".ntsSearchButtonContainer")
                        .append("<button id = " + searchAreaId + "-btn" + " class='ntsSearchButton search-btn caret-bottom'/>");
                    $SearchArea.find(".ntsSearchInput").attr("placeholder", "コード・名称で検索・・・").keyup(function(event, ui) {
                        if (event.which === 13) {
                            search($SearchArea, targetId, primaryKey);
                        }
                    });
                    $SearchArea.find(".ntsSearchButton").text("検索").click(function(event, ui) {
                        search($SearchArea, targetId, primaryKey);
                    });  
                }
                
                var searchAreaId = elementId + "-search-area";
                $swap.append("<div class = 'ntsSearchArea' id = " + searchAreaId + "/>");
                var $searchArea = $swap.find(".ntsSearchArea");
                $searchArea.append("<div class='ntsSwapSearchLeft'/>")
                    .append("<div class='ntsSwapSearchRight'/>");
                $searchArea.css({position: "relative"});
                var searchAreaWidth = gridWidth + CHECKBOX_WIDTH;
                if(showSearchBox.showLeft){
                    var $searchLeftContainer = $swap.find(".ntsSwapSearchLeft");
                    
                    $searchLeftContainer.width(searchAreaWidth).css({position: "absolute", left: 0});
                    
                    initSearchArea($searchLeftContainer, grid1Id);
                }
                
                if(showSearchBox.showRight){
                    var $searchRightContainer = $swap.find(".ntsSwapSearchRight");
                    
                    $searchRightContainer.width(gridWidth + CHECKBOX_WIDTH).css({position: "absolute", right: 0});
                    
                    initSearchArea($searchRightContainer, grid2Id);
                }
                $searchArea.find(".ntsSearchBox").width(searchAreaWidth - BUTTON_SEARCH_WIDTH - INPUT_SEARCH_PADDING);
                $searchArea.height(SEARCH_AREA_HEIGHT);
                gridHeight -= SEARCH_AREA_HEIGHT;
            }
            $swap.append("<div class= 'ntsSwapArea ntsGridArea'/>");
            $swap.find(".ntsGridArea").append("<div class = 'ntsSwapGridArea ntsSwapComponent' id = " + elementId + "-gridArea1" + "/>")
                .append("<div class = 'ntsMoveDataArea ntsSwapComponent' id = " + elementId + "-move-data" + "/>")
                .append("<div class = 'ntsSwapGridArea ntsSwapComponent' id = " + elementId + "-gridArea2" + "/>");

            $swap.find("#" + elementId + "-gridArea1").append("<table class = 'ntsSwapGrid' id = " + elementId + "-grid1" + "/>");
            $swap.find("#" + elementId + "-gridArea2").append("<table class = 'ntsSwapGrid' id = " + elementId + "-grid2" + "/>");

            var $grid1 = $swap.find(grid1Id);
            var $grid2 = $swap.find(grid2Id);

            var features = [{ name: 'Selection', multipleSelection: true },
                { name: 'Sorting', type: 'local' },
                { name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: true }];

            $swap.find(".nstSwapGridArea").width(gridWidth + CHECKBOX_WIDTH);
            $grid1.igGrid({
                width: gridWidth + CHECKBOX_WIDTH,
                height: (gridHeight) + "px",
                primaryKey: primaryKey,
                columns: iggridColumns,
                virtualization: true,
                virtualizationMode: 'continuous',
                features: features
            });

            $grid1.closest('.ui-iggrid')
                .addClass('nts-gridlist')
                .height(gridHeight);

            $grid1.ntsGridList('setupSelecting');

            $grid2.igGrid({
                width: gridWidth + CHECKBOX_WIDTH,
                height: (gridHeight) + "px",
                primaryKey: primaryKey,
                columns: iggridColumns,
                virtualization: true,
                virtualizationMode: 'continuous',
                features: features
            });

            $grid2.closest('.ui-iggrid')
                .addClass('nts-gridlist')
                .height(gridHeight);

            $grid2.ntsGridList('setupSelecting');

            var $moveArea = $swap.find("#" + elementId + "-move-data")
                .append("<button class = 'move-button move-forward'><i class='icon icon-button-arrow-right'></i></button>")
                .append("<button class = 'move-button move-back'><i class='icon icon-button-arrow-left'></i></button>");
            var $moveForward = $moveArea.find(".move-forward");
            var $moveBack = $moveArea.find(".move-back");

            var move = function(id1, id2, key, currentSource, value, isForward) {
                var selectedEmployees = _.sortBy($(isForward ? id1 : id2).igGrid("selectedRows"), 'id');
                if (selectedEmployees.length > 0) {
                    $(isForward ? id1 : id2).igGridSelection("clearSelection");
                    var source = $(isForward ? id1 : id2).igGrid("option", "dataSource");
                    var employeeList = [];
                    for (var i = 0; i < selectedEmployees.length; i++) {
                        var current = source[selectedEmployees[i].index];
                        if (current[key].toString() === selectedEmployees[i].id.toString()) {
                            employeeList.push(current);
                        } else {
                            var sameCodes = _.find(source, function(subject) {
                                return subject[key].toString() === selectedEmployees[i].id.toString();
                            });
                            if (sameCodes !== undefined) {
                                employeeList.push(sameCodes);
                            }
                        }
                    }
                    var length = value().length;
                    var notExisted = _.filter(employeeList, function(list) {
                        return _.find(currentSource, function(data) {
                            return data[key].toString() === list[key].toString();
                        }) === undefined;
                    });
                    if (notExisted.length > 0) {
                        $(id1).igGrid("virtualScrollTo", 0);
                        $(id2).igGrid("virtualScrollTo", 0);
                        var newSource = _.filter(source, function(list) {
                            return _.find(notExisted, function(data) {
                                return data[key].toString() === list[key].toString();
                            }) === undefined;
                        });
                        var sources = currentSource.concat(notExisted);
                        value(isForward ? sources : newSource);
                        $(id1).igGrid("option", "dataSource", isForward ? newSource : sources);
                        $(id1).igGrid("option", "dataBind");
                        $(id1).igGrid("virtualScrollTo", isForward ? selectedEmployees[0].index - 1 : sources.length - selectedEmployees.length);
                        $(id2).igGrid("virtualScrollTo", isForward ? value().length : selectedEmployees[0].index);
                    }
                }
            }

            $moveForward.click(function() {
                move(grid1Id, grid2Id, primaryKey, data.value(), data.value, true);
            });
            $moveBack.click(function() {
                move(grid1Id, grid2Id, primaryKey, $(grid1Id).igGrid("option", "dataSource"), data.value, false);
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            var $swap = $(element);
            var data = valueAccessor();
            var elementId = $swap.attr('id');
            var primaryKey: string = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
            if (nts.uk.util.isNullOrUndefined(elementId)) {
                throw new Error('the element NtsSwapList must have id attribute.');
            }
            var $grid1 = $swap.find("#" + elementId + "-grid1");
            var $grid2 = $swap.find("#" + elementId + "-grid2");

            var currentSource = $grid1.igGrid('option', 'dataSource');
            var currentSelected = $grid2.igGrid('option', 'dataSource');
            var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
            var selectedSources = data.value();
            _.remove(sources, function(item) {
                return _.find(selectedSources, function(selected) {
                    return selected[primaryKey] === item[primaryKey];
                }) !== undefined;
            });
            if (!_.isEqual(currentSource, sources)) {
                $grid1.igGrid('option', 'dataSource', sources.slice());
                $grid1.igGrid("dataBind");
            }

            if (!_.isEqual(currentSelected, selectedSources)) {
                $grid2.igGrid('option', 'dataSource', selectedSources.slice());
                $grid2.igGrid("dataBind");
            }
        }
    }

    class NtsUpDownBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            var $upDown = $(element);

            if ($upDown.prop("tagName").toLowerCase() !== "div") {
                throw new Error('The element must be a div');
            }
            var data = valueAccessor();

            var elementId = $upDown.attr('id');
            var comId = ko.unwrap(data.comId);
            var childField = ko.unwrap(data.childDataKey);
            var primaryKey = ko.unwrap(data.primaryKey);
            var height = ko.unwrap(data.height);
            var targetType = ko.unwrap(data.type);
            var swapTarget = ko.unwrap(data.swapTarget);

            if (nts.uk.util.isNullOrUndefined(elementId)) {
                throw new Error('The element NtsSwapList must have id attribute.');
            }
            if (nts.uk.util.isNullOrUndefined(comId)) {
                throw new Error('The target element of NtsUpDown is required.');
            }

            $upDown.addClass("ntsComponent ntsUpDown").append("<div class='upDown-container'/>");
            $upDown.find(".upDown-container")
                .append("<button class = 'ntsUpButton ntsButton ntsUpDownButton auto-height' id= '" + elementId + "-up'/>")
                .append("<button class = 'ntsDownButton ntsButton ntsUpDownButton auto-height' id= '" + elementId + "-down'/>");

            var $target = $(comId);

            if (height !== undefined) {
                $upDown.height(height);
                $upDown.find(".upDown-container").height(height);
            } else {
                var targetHeight = $(comId + "_container").height();
                if (targetHeight === undefined) {
                    var h = _.find($(comId).attr("data-bind").split(","), function(attr) {
                        return attr.indexOf("height") >= 0;
                    });
                    if (h !== undefined) {
                        targetHeight = parseFloat(h.split(":")[1]);
                    }
                }
                $upDown.height(targetHeight);
                $upDown.find(".upDown-container").height(targetHeight);
            }

            var $up = $upDown.find(".ntsUpButton");
            var $down = $upDown.find(".ntsDownButton");

            $up.append("<i class='icon icon-button-arrow-top'/>");
            $down.append("<i class='icon icon-button-arrow-bottom'/>");

            var move = function(upDown, $targetElement) {
                var multySelectedRaw = $targetElement.igGrid("selectedRows");
                var singleSelectedRaw = $targetElement.igGrid("selectedRow");
                var selected = [];
                if (multySelectedRaw !== null) {
                    selected = _.filter(multySelectedRaw, function(item) {
                        return item["index"] >= 0;
                    });
                } else if (singleSelectedRaw !== null) {
                    selected.push(singleSelectedRaw);
                } else {
                    return;
                }

                var source = _.cloneDeep($targetElement.igGrid("option", "dataSource"));
                var group = 1;
                var grouped = { "group1": [] };
                if (selected.length > 0) {
                    var size = selected.length;
                    selected = _.sortBy(selected, "index");
                    _.forEach(selected, function(item, idx) {
                        grouped["group" + group].push(item);
                        if (idx !== size - 1 && item["index"] + 1 !== selected[idx + 1]["index"]) {
                            group++;
                            grouped["group" + group] = [];
                        }
                    });
                    var moved = false;
                    _.forEach(_.valuesIn(grouped), function(items: Array<any>) {
                        var firstIndex = items[0].index;
                        var lastIndex = items[items.length - 1].index;
                        if (upDown < 0) {
                            var canMove = firstIndex > 0;
                        } else {
                            var canMove = lastIndex < source.length - 1;
                        }
                        if (canMove) {
                            var removed = source.splice(firstIndex, items.length);
                            _.forEach(removed, function(item, idx) {
                                source.splice(firstIndex + upDown + idx, 0, item);
                            });
                            moved = true;
                        }
                    });
                    if (moved) {
                        $targetElement.igGrid("virtualScrollTo", 0);
                        data.targetSource(source);
                        //                        $targetElement.igGrid("option", "dataSource", source);
                        //                        $targetElement.igGrid("dataBind");
                        var index = upDown + grouped["group1"][0].index;
                        //                        var index = $targetElement.igGrid("selectedRows")[0].index;
                        $targetElement.igGrid("virtualScrollTo", index);
                    }
                }
            }

            var moveTree = function(upDown, $targetElement) {
                var multiSelectedRaw = $targetElement.igTreeGrid("selectedRows");
                var singleSelectedRaw = $targetElement.igTreeGrid("selectedRow");
                //                var targetSource = ko.unwrap(data.targetSource);
                var selected;
                if (multiSelectedRaw !== null) {
                    if (multiSelectedRaw.length !== 1) {
                        return;
                    }
                    selected = multiSelectedRaw[0];
                } else if (singleSelectedRaw !== null) {
                    selected.push(singleSelectedRaw);
                } else {
                    return;
                }

                if (selected["index"] < 0) {
                    return;
                }
                //                var targetSource = ko.unwrap(data.targetSource);
                var source = _.cloneDeep($targetElement.igTreeGrid("option", "dataSource"));

                var result = findChild(upDown, selected["id"], source, false, false);
                var moved = result.moved;
                var changed = result.changed;
                source = result.source;

                if (moved && changed) {
                    data.targetSource(source);
                    //                    $targetElement.igTreeGrid("option", "dataSource", source);
                    //                    $targetElement.igTreeGrid("dataBind");
                    //                    data.targetSource(source);
                    var index = $targetElement.igTreeGrid("selectedRows")[0].index;
                    if (index !== selected["index"]) {
                        var scrollTo = _.sumBy(_.filter($target.igTreeGrid("allRows"), function(row) {
                            return $(row).attr("data-row-idx") < index;
                        }), function(row) {
                            return $(row).height();
                        });
                        $targetElement.igTreeGrid("scrollContainer").scrollTop(scrollTo);
                    }
                }
            }

            var findChild = function(upDown, key, children, moved, changed) {
                var index = -1;
                if (children !== undefined && children !== null && children.length > 0 && !moved && !changed) {
                    _.forEach(children, function(child, idx: number) {
                        if (!moved) {
                            if (child[primaryKey] === key) {
                                index = idx;
                                return false;
                            } else {
                                var result = findChild(upDown, key, child[childField], moved, changed);
                                child[childField] = result.source;
                                moved = result.moved;
                                changed = result.changed;
                            }
                        } else {
                            return false;
                        }
                    });
                    if (index >= 0) {
                        if (upDown < 0) {
                            var canMove = index > 0;
                        } else {
                            var canMove = index < children.length - 1;
                        }
                        if (canMove) {
                            var removed = children.splice(index, 1);
                            children.splice(index + upDown, 0, removed[0]);
                            changed = true;
                        }
                        moved = true;
                    }
                    return {
                        source: children,
                        moved: moved,
                        changed: changed
                    };
                }
                return {
                    source: children,
                    moved: moved,
                    changed: changed
                };
            }

            $up.click(function(event, ui) {
                if (targetType === "tree") {
                    moveTree(-1, $target);
                } else if (targetType === "grid") {
                    move(-1, $target);
                } else if (targetType === "swap") {
                    var swapTargetGrid = swapTarget.toLocaleLowerCase() === "left" ? "-grid1" : "-grid2";
                    move(-1, $(comId + swapTargetGrid));
                }
            });

            $down.click(function(event, ui) {
                if (targetType === "tree") {
                    moveTree(1, $target);
                } else if (targetType === "grid") {
                    move(1, $target);
                } else if (targetType === "swap") {
                    var swapTargetGrid = swapTarget.toLocaleLowerCase() === "left" ? "-grid1" : "-grid2";
                    move(1, $(comId + swapTargetGrid));
                }
            });


        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            var $upDown = $(element);
            var elementId = $upDown.attr('id');
            if (nts.uk.util.isNullOrUndefined(elementId)) {
                throw new Error('the element NtsSwapList must have id attribute.');
            }
            var data = valueAccessor();
        }
    }

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
    ko.bindingHandlers['ntsHelpButton'] = new NtsHelpButtonBindingHandler();
    ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
    ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
    ko.bindingHandlers['ntsGridList'] = new NtsGridListBindingHandler();
    ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
    ko.bindingHandlers['ntsSwapList'] = new NtsSwapListBindingHandler();
    ko.bindingHandlers['ntsSearchBox'] = new NtsSearchBoxBindingHandler();
    ko.bindingHandlers['ntsUpDown'] = new NtsUpDownBindingHandler();
}