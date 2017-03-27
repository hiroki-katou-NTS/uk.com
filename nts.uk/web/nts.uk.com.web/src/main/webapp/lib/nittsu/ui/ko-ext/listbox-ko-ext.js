/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                function selectOnListBox(event) {
                    var container = $(event.delegateTarget);
                    container.find(".ui-selected").removeClass('ui-selected');
                    $(this).addClass('ui-selected');
                    container.data('value', $(this).data('value'));
                    document.getElementById(container.parent().attr('id')).dispatchEvent(event.data.event);
                }
                function unbindMultible($target) {
                    $target.selectable("destroy");
                }
                function bindMultible($target, changeEvent) {
                    $target.selectable({
                        filter: 'li',
                        selected: function (event, ui) {
                        },
                        stop: function (event, ui) {
                            // Add selected value.
                            var data = [];
                            $("li.ui-selected", $target).each(function (index, opt) {
                                data[index] = $(opt).data('value');
                            });
                            $target.data('value', data);
                            // fire event change.
                            document.getElementById($target.parent().attr('id')).dispatchEvent(changeEvent);
                        },
                        selecting: function (event, ui) {
                            if (event.shiftKey) {
                                if ($(ui.selecting).attr("clicked") !== "true") {
                                    var source = $target.find("li");
                                    var clicked = _.find(source, function (row) {
                                        return $(row).attr("clicked") === "true";
                                    });
                                    if (clicked === undefined) {
                                        $(ui.selecting).attr("clicked", "true");
                                    }
                                    else {
                                        $target.find("li").attr("clicked", "");
                                        $(ui.selecting).attr("clicked", "true");
                                        var start = parseInt($(clicked).attr("data-idx"));
                                        var end = parseInt($(ui.selecting).attr("data-idx"));
                                        var max = start > end ? start : end;
                                        var min = start < end ? start : end;
                                        var range = _.filter(source, function (row) {
                                            var index = parseInt($(row).attr("data-idx"));
                                            return index >= min && index <= max;
                                        });
                                        $(range).addClass("ui-selected");
                                    }
                                }
                            }
                            else if (!event.ctrlKey) {
                                $target.find("li").attr("clicked", "");
                                $(ui.selecting).attr("clicked", "true");
                            }
                        }
                    });
                }
                function bindSingleSelectListBox($target, changeEvent) {
                    $target.on("click", "li", { event: changeEvent }, selectOnListBox);
                }
                function unbindSingleSelectListBox($target) {
                    $target.off("click", "li");
                }
                function selectOneRow(container, selectedValue) {
                    container.find("li").removeClass("ui-selected");
                    var target = _.find($('li', container), function (opt) {
                        var optValue = $(opt).data('value');
                        return optValue == selectedValue;
                    });
                    $(target).addClass('ui-selected');
                }
                function selectMultiRow(container, selectedValues) {
                    container.find("li").removeClass("ui-selected");
                    _.forEach(selectedValues, function (selectedValue) {
                        var target = _.find($('li', container), function (opt) {
                            var optValue = $(opt).data('value');
                            return optValue == selectedValue;
                        });
                        $(target).addClass('ui-selected');
                    });
                }
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
                        // Get options
                        var options = ko.unwrap(data.options);
                        // Get options value
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
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
                            bindMultible(selectListBoxContainer, changeEvent);
                        }
                        else {
                            bindSingleSelectListBox(selectListBoxContainer, changeEvent);
                        }
                        // Fire event.
                        container.on('selectionChange', (function (e) {
                            // Check is multi-selection.
                            var changingEvent = new CustomEvent("selectionChanging", {
                                detail: itemsSelected,
                                bubbles: true,
                                cancelable: true,
                            });
                            var isMulti = container.data("multiple");
                            var itemsSelected = selectListBoxContainer.data('value');
                            // Dispatch/Trigger/Fire the event => use event.detai to get selected value.
                            document.getElementById(container.attr('id')).dispatchEvent(changingEvent);
                            if (!changingEvent.returnValue) {
                                // revert select.
                                console.log(selectedValue);
                                selectListBoxContainer.data('value', data.value());
                                if (isMulti) {
                                    selectMultiRow(selectListBoxContainer, data.value());
                                }
                                else {
                                    selectOneRow(selectListBoxContainer, data.value());
                                }
                            }
                            else {
                                data.value(itemsSelected);
                                container.data("selected", !isMulti ? itemsSelected : itemsSelected.slice());
                            }
                        }));
                        container.data("multiple", isMultiSelect);
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
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
                        var columns = data.columns;
                        var rows = data.rows;
                        // Container.
                        var container = $(element);
                        var selectListBoxContainer = container.find('.nts-list-box');
                        var maxWidthCharacter = 15;
                        var required = ko.unwrap(data.required) || false;
                        container.data('required', required);
                        var getOptionValue = function (item) {
                            if (optionValue === undefined) {
                                return item;
                            }
                            else {
                                return item[optionValue];
                            }
                        };
                        var originalOptions = container.data("options");
                        var init = container.data("init");
                        var originalSelected = container.data("selected");
                        var oldMultiOption = container.data("multiple");
                        if (oldMultiOption !== isMultiSelect && !init) {
                            var changeEvent = new CustomEvent("selectionChange", {
                                detail: {},
                            });
                            if (oldMultiOption) {
                                unbindMultible(selectListBoxContainer);
                                bindSingleSelectListBox(selectListBoxContainer, changeEvent);
                                container.find("li").removeClass("ui-selected");
                                if (selectedValue.length > 0) {
                                    selectOneRow(selectListBoxContainer, selectedValue[0]);
                                    data.value(selectedValue[0]);
                                }
                            }
                            else {
                                unbindSingleSelectListBox(selectListBoxContainer);
                                bindMultible(selectListBoxContainer, changeEvent);
                                if (!uk.text.isNullOrEmpty(selectedValue)) {
                                    var array = [];
                                    array.push(selectedValue);
                                    data.value(array);
                                }
                            }
                            container.data("multiple", isMultiSelect);
                        }
                        if (!_.isEqual(originalOptions, options) || init) {
                            if (!init) {
                                //remove children
                                selectListBoxContainer.empty();
                            }
                            // Append options.
                            options.forEach(function (item, idx) {
                                // Check option is Selected
                                var isSelected = false;
                                if (isMultiSelect) {
                                    isSelected = selectedValue.indexOf(getOptionValue(item)) !== -1;
                                }
                                else {
                                    isSelected = selectedValue === getOptionValue(item);
                                }
                                var target = _.find($('li', container), function (opt) {
                                    var optValue = $(opt).data('value');
                                    return optValue == getOptionValue(item);
                                });
                                if (init || target === undefined) {
                                    // Add option
                                    var selectedClass = isSelected ? 'ui-selected' : '';
                                    var itemTemplate = '';
                                    if (columns && columns.length > 0) {
                                        columns.forEach(function (col, cIdx) {
                                            itemTemplate += '<div class="nts-column nts-list-box-column-' + cIdx + '">' + item[col.key !== undefined ? col.key : col.prop] + '</div>';
                                        });
                                    }
                                    else {
                                        itemTemplate = '<div class="nts-column nts-list-box-column-0">' + item[optionText] + '</div>';
                                    }
                                    $('<li/>').addClass(selectedClass).attr("data-idx", idx)
                                        .html(itemTemplate).data('value', getOptionValue(item))
                                        .appendTo(selectListBoxContainer);
                                }
                                else {
                                    var targetOption = $(target);
                                    if (isSelected) {
                                        targetOption.addClass('ui-selected');
                                    }
                                    else {
                                        targetOption.removeClass('ui-selected');
                                    }
                                }
                            });
                            var padding = 10;
                            var rowHeight = 28;
                            // Set width for multi columns
                            if (columns && columns.length > 0) {
                                var totalWidth = 0;
                                columns.forEach(function (item, cIdx) {
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
                        var haveDate = isMultiSelect ? !uk.text.isNullOrEmpty(selectedValue) && selectedValue.length > 0
                            : !uk.text.isNullOrEmpty(selectedValue);
                        if (haveDate && (!_.isEqual(originalSelected, selectedValue) || init)) {
                            selectListBoxContainer.data('value', selectedValue);
                            container.trigger('selectionChange');
                        }
                        if (isMultiSelect) {
                            // Check enable
                            if (!enable) {
                                selectListBoxContainer.selectable("disable");
                                ;
                                container.addClass('disabled');
                            }
                            else {
                                selectListBoxContainer.selectable("enable");
                                container.removeClass('disabled');
                            }
                        }
                        else {
                            if (!enable) {
                                //selectListBoxContainer.selectable("disable");;
                                container.off("click", "li");
                                container.addClass('disabled');
                            }
                            else {
                                //selectListBoxContainer.selectable("enable");
                                if (container.hasClass("disabled")) {
                                    container.on("click", "li", { event: container.data("selectionChange") }, selectOnListBox);
                                    container.removeClass('disabled');
                                }
                            }
                        }
                        container.data("enable", enable);
                    };
                    return ListBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
