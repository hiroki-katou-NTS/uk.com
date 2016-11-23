module nts.uk.ui.koExtentions {

    import validation = nts.uk.ui.validation;
    
    /**
     * TextEditor
     */
    class NtsTextEditorBindingHandler implements KnockoutBindingHandler {

        constraint: validation.CharType;

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            var setValue: (newText: string) => {} = data.value;
            this.constraint = validation.getCharType(data.constraint);
            console.log(this.constraint);
            var $input = $(element);

            $input.change(function() {
                var newText = $input.val();
                bindingContext.$data.change(newText);
                setValue(newText);
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var getValue: () => string = data.value;
            var option: any = ko.unwrap(data.option);
            var textmode: string = ko.unwrap(option.textmode);
            var enable: boolean = ko.unwrap(option.enable);
            var readonly: boolean = ko.unwrap(option.readonly);
            var placeholder: string = ko.unwrap(option.placeholder);
            var width: string = ko.unwrap(option.width);
            var textalign: string = ko.unwrap(option.textalign);
            this.constraint = validation.getCharType(data.constraint);    
            var $input = $(element);
            
            $input.attr('type',textmode);
            if(enable !== false)
                $input.removeAttr('disabled');
            else
                $input.attr('disabled','disabled');
            if(readonly === false)
                $input.removeAttr('readonly');
            else
                $input.attr('readonly','readonly');
            $input.attr('placeholder', placeholder);
            if(width.trim() != "")
                $input.width(width);
            if(textalign.trim() != "")
                $input.css('text-align', textalign);
            
            var newText = getValue();
            var isError = this.constraint.validate(newText);
            
            $input.val(newText);
        }
    }
    
    /**
     * NumberEditor
     */
    class NtsNumberEditorBindingHandler implements KnockoutBindingHandler {

        constraint: validation.CharType;

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            var setValue: (newText: string) => {} = data.value;
            this.constraint = validation.getCharType(data.constraint);
            var $input = $(element);

            $input.change(function() {
                var newText = $input.val();
                bindingContext.$data.change(newText);
                setValue(newText);
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var getValue: () => string = data.value;
            var option: any = ko.unwrap(data.option);
            
            var enable: boolean = ko.unwrap(option.enable);
            var readonly: boolean = ko.unwrap(option.readonly);
            var placeholder: string = ko.unwrap(option.placeholder);
            var width: string = ko.unwrap(option.width);
            var textalign: string = ko.unwrap(option.textalign);
            
            var $input = $(element);
            
            $input.attr('type', 'number');
            if(enable !== false)
                $input.removeAttr('disabled');
            else
                $input.attr('disabled','disabled');
            if(readonly === false)
                $input.removeAttr('readonly');
            else
                $input.attr('readonly','readonly');
            $input.attr('placeholder', placeholder);
            if(width.trim() != "")
                $input.width(width);
            if(textalign.trim() != "")
                $input.css('text-align', textalign);
            
            var newText = getValue();
            $input.val(newText);
        }
    }
    
    /**
     * TimeEditor
     */
    class NtsTimeEditorBindingHandler implements KnockoutBindingHandler {

        constraint: validation.CharType;

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            var setValue: (newText: string) => {} = data.value;
            this.constraint = validation.getCharType(data.constraint);
            var $input = $(element);

            $input.change(function() {
                var newText = $input.val();
                bindingContext.$data.change(newText);
                setValue(newText);
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var getValue: () => string = data.value;
            var option: any = ko.unwrap(data.option);
            
            var enable: boolean = ko.unwrap(option.enable);
            var readonly: boolean = ko.unwrap(option.readonly);
            var placeholder: string = ko.unwrap(option.placeholder);
            var width: string = ko.unwrap(option.width);
            var textalign: string = ko.unwrap(option.textalign);
            
            var $input = $(element);
            
            $input.attr('type', 'time');
            if(enable !== false)
                $input.removeAttr('disabled');
            else
                $input.attr('disabled','disabled');
            if(readonly === false)
                $input.removeAttr('readonly');
            else
                $input.attr('readonly','readonly');
            $input.attr('placeholder', placeholder);
            if(width.trim() != "")
                $input.width(width);
            if(textalign.trim() != "")
                $input.css('text-align', textalign);
            
            var newText = getValue();
            $input.val(newText);
        }
    }
    
    /**
     * TextEditor
     */
    class NtsMaskEditorBindingHandler implements KnockoutBindingHandler {

        constraint: validation.CharType;

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            var setValue: (newText: string) => {} = data.value;
            this.constraint = validation.getCharType(data.constraint);
            var $input = $(element);

            $input.change(function() {
                var newText = $input.val();
                bindingContext.$data.change(newText);
                setValue(newText);
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var getValue: () => string = data.value;
            var option: any = ko.unwrap(data.option);
            var textmode: string = ko.unwrap(option.textmode);
            var enable: boolean = ko.unwrap(option.enable);
            var readonly: boolean = ko.unwrap(option.readonly);
            var placeholder: string = ko.unwrap(option.placeholder);
            var width: string = ko.unwrap(option.width);
            var textalign: string = ko.unwrap(option.textalign);
            
            var $input = $(element);
            
            $input.attr('type',textmode);
            if(enable !== false)
                $input.removeAttr('disabled');
            else
                $input.attr('disabled','disabled');
            if(readonly === false)
                $input.removeAttr('readonly');
            else
                $input.attr('readonly','readonly');
            $input.attr('placeholder', placeholder);
            if(width.trim() != "")
                $input.width(width);
            if(textalign.trim() != "")
                $input.css('text-align', textalign);
            
            var newText = getValue();
            $input.val(newText);
        }
    }
    
    /**
     * TextBox
     */
    class NtsTextBoxBindingHandler implements KnockoutBindingHandler {

        constraint: validation.CharType;

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var setValue: (newText: string) => {} = data.value;
            this.constraint = validation.getCharType(data.constraint);
            var $input = $(element);

            $input.change(function() {
                var newText = $input.val();
                setValue(newText);
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var data = valueAccessor();
            var getValue: () => string = data.value;

            var $input = $(element);
            var newText = getValue();

            $input.val(newText);
        }
    }
    
    /**
     * Multi Checkbox
     */
    class NtsMultiCheckBoxBindingHandler implements KnockoutBindingHandler {
        constructor() {}
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext) {
            element.innerHTML = "<input type='checkbox' data-bind='checked: isChecked, checkedValue: item' /><label data-bind='text: content'></label>";
            /*var childBindingContext = bindingContext.createChildContext(
                    bindingContext.$rawData,
                    null, // Optionally, pass a string here as an alias for the data item in descendant contexts
                    function(context) {
                        ko.utils.extend(context, valueAccessor());
                    });*/
            var childBindingContext = bindingContext.extend(valueAccessor);
            ko.applyBindingsToDescendants(childBindingContext, element);
            return {controlsDescendantBindings: true};
        }
        
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            
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
            var headers: Array<any> = ko.unwrap(data.headers);
            var errors: Array<any> = ko.unwrap(data.errors);
            var displayrows: number = ko.unwrap(option.displayrows);
            var maxrows: number = ko.unwrap(option.maxrows);
            var autoclose: boolean = ko.unwrap(option.autoclose);
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
            // Create dialog
            $dialog.dialog({
                title: title,
                modal: modal,
                autoOpen: show,
                closeOnEscape: false,
                width: 550,
                buttons: dialogbuttons,
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
            var headers: Array<any> = ko.unwrap(data.headers);
            var errors: Array<any> = ko.unwrap(data.errors);
            var displayrows: number = ko.unwrap(option.displayrows);
            var maxrows: number = ko.unwrap(option.maxrows);
            var autoclose: boolean = ko.unwrap(option.autoclose);
            var show: boolean = ko.unwrap(option.show);
            
            var $dialog = $("#ntsErrorDialog");
            
            if(autoclose === true && errors.length == 0)
                show = false;
            if (show == true) {
                $dialog.dialog("open");
                // Create Error Table
                var $errorboard = $("<div id='error-board'></div>");
                var $errortable = $("<table></table>");
                // Header
                var $header = $("<thead><tr></tr></thead>");
                $header.find("tr").append("<th style='width: 35px'></th>");
                headers.forEach(function(header,index) {
                    if (header.visible) {
                        let $headerElement = $("<th>" + header.text + "</th>").width(header.width);
                        $header.find("tr").append($headerElement);
                    }
                });
                $errortable.append($header);
                // Body
                var $body = $("<tbody></tbody>");
                errors.forEach(function(error, index) {
                    if(index < maxrows) {
                        // Row
                        let $row = $("<tr></tr>");
                        $row.append("<td style='width:35px'>" + (index + 1) + "</td>");
                        headers.forEach(function(header){
                            if (header.visible)
                                if (error.hasOwnProperty(header.name)) {
                                    // TD
                                    let $column = $("<td>" + error[header.name] + "</td>").width(header.width);
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
                if(errors.length > maxrows)
                    $message.text("Showing " + maxrows + " in total " + errors.length + " errors");
                $dialog.html("");
                $dialog.append($errorboard).append($message);
                // Calculate body height base on displayrow
                $body.height(displayrows * $(">:first-child", $body).outerHeight() + 1);
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
            })
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
            $(element).addClass("ntsCheckBox");
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();

            // Container.
            var container = $(element);

            // Get options.
            var checked: boolean = ko.unwrap(data.checked);
            var enable: boolean = ko.unwrap(data.enable);
            var textId: string = data.text;
            var checkBoxText: string;
            if (textId) {
                checkBoxText = /*nts.ui.name*/(textId);
            } else {
                checkBoxText = container.text();
                container.text('');
            }

            var checkBox: JQuery;
            if ($('input[type="checkbox"]', container).length == 0) {
                // Init new.
                checkBox = $('<input type="checkbox">').appendTo(container);
                var checkBoxLabel = $("<label><span></span></label>").appendTo(container).append(checkBoxText);
                $(container).on('click', "label", function() {
                    // Do nothing if disable.
                    if (container.hasClass('disabled')) {
                        return;
                    }

                    // Change value.
                    checkBox.prop("checked", !checkBox.prop("checked"));
                    data.checked(checkBox.prop("checked"));
                });
            } else {
                checkBox = $('input[type="checkbox"]', container);
            }

            // Set state.
            checkBox.prop("checked", checked);

            // Disable.
            if (enable == false) {
                container.addClass('disabled');
            } else {
                container.removeClass('disabled');
            }
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
            var editable = data.editable;
            var enable: boolean = data.enable;
            var columns: Array<any> = data.columns;

            // Container.
            var container = $(element);
            var comboMode: string = editable ? 'editable' : 'dropdown';

            // Default values.
            var distanceColumns = '     ';
            var fillCharacter = ' '; // Character used fill to the columns.
            var maxWidthCharacter = 15;

            // Check selected code.
            if (options.filter(item => item[optionValue] === selectedValue).length == 0 && !editable) {
                selectedValue = options.length > 0 ? options[0][optionValue] : '';
                data.value(selectedValue);
            }

            // Delete igCombo.
            if (container.data("igCombo") != null) {
                container.igCombo('destroy');
                container.removeClass('ui-state-disabled');
            }

            // Set attribute for multi column.
            var itemTempalate: string = undefined;
            options = options.map((option) => {
                var newOptionText: string = '';

                // Check muti columns.
                if (columns && columns.length > 0) {
                    var i = 0;
                    itemTempalate = '<div class="nts-combo-item">';
                    columns.forEach(item => {
                        var prop: string = option[item.prop];
                        var length: number = item.length;

                        var proLength: number = prop.length;
                        while (proLength < length && i != columns.length - 1) {
                            // Add space character to properties.
                            prop += fillCharacter;

                            proLength++;
                        }
                        if (i == columns.length - 1) {
                            newOptionText += prop;
                        } else {
                            newOptionText += prop + distanceColumns;
                        }

                        // Set item template.
                        itemTempalate += '<div class="nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                        i++;
                    });
                    itemTempalate += '</div>';
                } else {
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
                selectionChanged: function(evt: any, ui: any) {
                    if (ui.items.length > 0) {
                        data.value(ui.items[0].data[optionValue]);
                    }
                }
            });

            // Set width for multi columns.
            if (columns && columns.length > 0) {
                var i = 0;
                var totalWidth = 0;
                columns.forEach(item => {
                    var length: number = item.length;
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
        }
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
            
            // Get options.
            var options: Array<any> = ko.unwrap(data.options);
    
            // Get options value.
            var optionValue = ko.unwrap(data.optionsValue);
            var optionText = ko.unwrap(data.optionsText);
            var selectedValue = ko.unwrap(data.value);
            var isMultiSelect = data.multiple;
            var enable: boolean = data.enable;
            var columns: Array<any> = data.columns;
            
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
                selected: function(event, ui) { 
                },
                stop: function( event, ui ) {
                    
                    // If not Multi Select.
                    if (!isMultiSelect) {
                        $(event.target).children('.ui-selected').not(':first').removeClass('ui-selected');
                        $(event.target).children('li').children('.ui-selected').removeClass('ui-selected');
                    }
                    
                    // Add selected value.
                    var data: any = isMultiSelect ? [] : '';
                    var i = 0;
                    $("li.ui-selected").each(function(index, opt) {
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
                unselecting: function( event, ui ) {
                    $(event.target).children('li').not('.ui-selected').children('.ui-selected').removeClass('ui-selected')
                }
            });
            
            // Fire event.
            container.on('selectionChange', (function(e: Event) {
                // Check is multi-selection.
                var itemsSelected: any = container.data('value');
                
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
                } else {
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
            $.fn.deselectAll = function() {
                $(this.selector).data('value', '');
                $(this.selector + ' > .nts-list-box > li').removeClass("ui-selected");
                $(this.selector + ' > .nts-list-box > li > div').removeClass("ui-selected");
                $(this.selector).trigger("selectionChange");
            }
            $.fn.selectAll = function() {
                $(this.selector + ' > .nts-list-box > li').addClass("ui-selected");
                $(this.selector + ' > .nts-list-box').data("ui-selectable")._mouseStop(null);
            }
            $.fn.ntsListBox = function(method: string) {
                switch(method) {
                    case 'deselectAll':
                        this.deselectAll();
                        break;
                    case 'selectAll':
                        this.selectAll();
                        break;
                    default:
                        break;
                }
            }
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
            var optionValue = ko.unwrap(data.optionsValue);
            var optionText = ko.unwrap(data.optionsText);
            var selectedValue = ko.unwrap(data.value);
            var isMultiSelect = data.multiple;
            var enable: boolean = data.enable;
            var columns: Array<any> = data.columns;
            
            // Container.
            var container = $(element);
            var selectListBoxContainer = container.find('.nts-list-box');
            var maxWidthCharacter = 15;
            
            // Check selected code.
            if (!isMultiSelect && options.filter(item => item[optionValue] == selectedValue).length == 0) {
                selectedValue = '';
            } 
            
            // Remove options.
            $('li', container).each(function(index, option) {
                var optValue = $(option).data('value');
                // Check if btn is contained in options.
                var foundFlag = _.findIndex(options, function(opt) {
                    return opt[optionValue] == optValue;
                }) != -1;
                if (!foundFlag) {
                    
                    // Remove selected if not found option.
                    selectedValue = jQuery.grep(selectedValue, function(value: string) {
                        return value != optValue;
                    });
                    option.remove();
                    return;
                } 
            })
            
            // Append options.
            options.forEach(item => {
                // Find option.
                var targetOption : JQuery;
                $('li', container).each(function(index, opt) {
                    var optValue = $(opt).data('value');
                    if (optValue == item[optionValue]) {
                        targetOption = $(opt); 
                        return;
                    }
                })
                
                // Check option is Selected.
                var isSelected: boolean = false;
                if (isMultiSelect) {
                    isSelected = (<Array<string>>selectedValue).indexOf(item[optionValue]) != -1;
                } else {
                    isSelected = selectedValue == item[optionValue];
                }
                
                if (!targetOption) {
                    // Add option.
                    var selectedClass = isSelected ? 'ui-selected' : '';
                    var itemTemplate: string = '';
                    if (columns && columns.length > 0) {
                        var i = 0;
                        columns.forEach(col => {
                            var prop: string = item[col.prop];
                            itemTemplate += '<div class="nts-column nts-list-box-column-' + i + '">' + prop + '</div>';
                            i++;
                        });
                    } else {
                        itemTemplate = '<div class="nts-column nts-list-box-column-0">' + item[optionText] + '</div>';
                    }
                    selectListBoxContainer.append('<li data-value="' + item[optionValue] + '" class="' + selectedClass + '"> ' + itemTemplate + ' </li>');
                } else {
                    if (isSelected) {
                        targetOption.addClass('ui-selected');
                    } else {
                        targetOption.removeClass('ui-selected');
                    }
                }
                
            });
            
            // Set value.
            container.data('value', selectedValue);
            container.trigger('selectionChange');
            
            // Check enable.
            if (!enable) {
                selectListBoxContainer.selectable( "disable" );;
                container.addClass('disabled');
            } else {
                selectListBoxContainer.selectable( "enable" );
                container.removeClass('disabled');
            }
            
            // Set width for multi columns.
            if (columns && columns.length > 0) {
                var i = 0;
                var totalWidth = 0;
                columns.forEach(item => {
                    var length: number = item.length;
                    $('.nts-list-box-column-' + i).width(length * maxWidthCharacter + 20);
                    totalWidth += length * maxWidthCharacter + 20;
                    i++;
                });
                var ntsCommonPadding = $('.nts-column').css('padding').split('px')[0];
                var padding = 10;
                if(ntsCommonPadding){
                    padding = parseInt(ntsCommonPadding);
                }
                totalWidth += 50 + padding*columns.length;
                $('.nts-list-box > li').css({'min-width': totalWidth});
                $('.nts-list-box').css({'min-width': totalWidth});
                container.css({'min-width': totalWidth});
            }
           
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
            var options: Array<any> = ko.unwrap(data.options);
            var optionsValue = ko.unwrap(data.optionsValue);
            var optionsText = ko.unwrap(data.optionsText);

            var selectedValues: Array<any> = ko.unwrap(data.selectedValues);
            var singleValue = ko.unwrap(data.value);

            var optionsChild = ko.unwrap(data.optionsChild);
            var extColumns: Array<any> = ko.unwrap(data.extColumns);

            // Default.
            var showCheckBox = ko.unwrap(data.showCheckBox);
            showCheckBox = showCheckBox != undefined ? showCheckBox : true;

            var enable = ko.unwrap(data.enable);
            enable = enable != undefined ? enable : true;

            var height = ko.unwrap(data.height);
            height = height ? height : '100%';

            width = width ? width : '100%';
            var width = ko.unwrap(data.width);

            var displayColumns: Array<any> = [{ headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
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
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var options: Array<any> = ko.unwrap(data.options);
            var selectedValues: Array<any> = ko.unwrap(data.selectedValues);
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
            var olds = _.map($(element).igTreeGridSelection("selectedRow"), function(row: any) {
                return row.id;
            });

            // Not change, do nothing.
            if (selectedValues) {
                if (_.isEqual(selectedValues.sort(), olds.sort())) {
                    return;
                }
                // Update.
                $(element).igTreeGridSelection("clearSelection");
                selectedValues.forEach(function(val) {
                    $(element).igTreeGridSelection("selectRowById", val);
                })
            }

            if (singleValue) {
                if (olds.length > 0 && olds[0] == singleValue) {
                    return;
                }
                $(element).igTreeGridSelection("selectRowById", singleValue);
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
            // Get data.
            var data = valueAccessor();
            // Get step list.
            var options: Array<any> = ko.unwrap(data.steps);
            // Container.
            var container = $(element);

            // Create steps.
            for (var i = 0; i < options.length; i++) {
                var contentClass: string = ko.unwrap(options[i].content);
                var htmlStep = container.children('.steps').children(contentClass).html();
                var htmlContent = container.children('.contents').children(contentClass).html();
                container.append('<h1 class="' + contentClass + '">' + htmlStep + '</h1>');
                container.append('<div>' + htmlContent + '</div>');
            }
            var icon = container.children('.steps').children('.begin').data('icon');

            // Remove html.
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
            container.children('.steps').children('ul').children('.first').addClass('begin');
            container.children('.steps').children('ul').children('.last').addClass('end');
            container.children('.steps').children('ul').children('li').not('.begin').not('.end').children('.nts-steps').addClass('nts-steps-middle');
            container.find('.nts-steps-middle').append('<div class="nts-vertical-line"></div><div class="nts-bridge"><div class="nts-point"></div><div class="nts-horizontal-line"></div></div>')

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

            container.find('.nts-steps').first().attr('style', 'background-image: url("' + icon + '")');

            $.fn.begin = function() {
                $(this).setStep(0);
            }

            $.fn.end = function() {
                $(this).setStep(options.length - 1);
            }
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
            var primitiveValueName = ko.unwrap(data.constraint);
            var isRequired = ko.unwrap(data.required) === true;
            var isInline = ko.unwrap(data.inline) === true;
            var isEnable = ko.unwrap(data.enable) !== false;
            console.log(isEnable);
            var $formLabel = $(element).addClass('form-label');
            
            $('<label/>').text($formLabel.text()).appendTo($formLabel.empty());
            if(!isEnable) {
                $formLabel.addClass('disabled');
            } else {
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
        }
        
        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }
        
        static buildConstraintText(primitiveValueName: string) {
            var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
                
            var constraintText: string;
            switch (constraint.valueType) {
                case 'String':
                    return uk.ui.validation.getCharType(primitiveValueName).buildConstraintText(constraint.maxLength);
                default:
                    return 'ERROR';
            }
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
            var jump = data.jump;
            
            var linkText = $(element).text();
            var $linkButton = $(element).wrap('<div/>').parent().empty()
                .text(linkText)
                .addClass('link-button')
                .click(function () {
                    alert(jump);
                });
        }
        
        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }
    }

    
    ko.bindingHandlers['ntsWizard'] = new WizardBindingHandler();

    ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
    ko.bindingHandlers['ntsLinkButton'] = new NtsLinkButtonBindingHandler();
    ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
    ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
    ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
    ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
    ko.bindingHandlers['ntsMaskEditor'] = new NtsMaskEditorBindingHandler();
    ko.bindingHandlers['ntsTextBox'] = new NtsTextBoxBindingHandler();
    ko.bindingHandlers['ntsDialog'] = new NtsDialogBindingHandler();
    ko.bindingHandlers['ntsErrorDialog'] = new NtsErrorDialogBindingHandler();
    ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
    ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
    ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
    ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
    ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
}