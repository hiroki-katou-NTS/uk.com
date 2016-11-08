module nts.uk.ui.koExtentions {

    import validation = nts.uk.ui.validation;

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
     * Switch button binding handler
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
                $(this).data('value', '');
                $(this).find('.nts-list-box > li').removeClass("ui-selected");
                $(this).find('.nts-list-box > li > div').removeClass("ui-selected");
                $(this).trigger("selectionChange");
            }
            $.fn.selectAll = function() {
                $(this).find('.nts-list-box > li').addClass("ui-selected");
                $(this).find('.nts-list-box').data("ui-selectable")._mouseStop(null);
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
                totalWidth += 50;
                $('.nts-list-box > li').css({'min-width': totalWidth});
                $('.nts-list-box').css({'min-width': totalWidth});
                container.css({'min-width': totalWidth});
            }
           
        }
    }
     
    ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
    ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
    ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
    ko.bindingHandlers['ntsTextBox'] = new NtsTextBoxBindingHandler();
}