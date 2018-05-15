var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var ListBoxBindingHandler = (function () {
                    function ListBoxBindingHandler() {
                    }
                    ListBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
                        var columns = data.columns;
                        var $element = $(element);
                        var elementId = $element.addClass("listbox-wrapper").attr("id");
                        if (nts.uk.util.isNullOrUndefined($element.attr("tabindex"))) {
                            $element.attr("tabindex", "0");
                        }
                        $element.data("tabindex", $element.attr("tabindex"));
                        var gridId = elementId;
                        if (nts.uk.util.isNullOrUndefined(gridId)) {
                            gridId = nts.uk.util.randomId();
                        }
                        else {
                            gridId += "_grid";
                        }
                        $element.append("<table id='" + gridId + "' class='ntsListBox ntsControl'/>");
                        var container = $element.find("#" + gridId);
                        container.data("options", options.slice());
                        container.data("init", true);
                        container.data("enable", enable);
                        var changeEvent = new CustomEvent("selectionChange", {
                            detail: {},
                        });
                        container.data("selectionChange", changeEvent);
                        var features = [];
                        features.push({ name: 'Selection', multipleSelection: isMultiSelect });
                        var maxWidthCharacter = 15;
                        var gridFeatures = ko.unwrap(data.features);
                        var width = 0;
                        var iggridColumns = [];
                        if (nts.uk.util.isNullOrUndefined(columns)) {
                            iggridColumns.push({ "key": optionValue, "width": 10 * maxWidthCharacter + 20, "headerText": '', "columnCssClass": 'nts-column', 'hidden': true });
                            iggridColumns.push({ "key": optionText, "width": 10 * maxWidthCharacter + 20, "headerText": '', "columnCssClass": 'nts-column' });
                            width += 10 * maxWidthCharacter + 20;
                            container.data("fullValue", true);
                        }
                        else {
                            var isHaveKey_1 = false;
                            iggridColumns = _.map(columns, function (c) {
                                c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                                c["width"] = c["length"] * maxWidthCharacter + 20;
                                c["headerText"] = '';
                                c["columnCssClass"] = 'nts-column';
                                width += c["length"] * maxWidthCharacter + 20;
                                if (optionValue === c["key"]) {
                                    isHaveKey_1 = true;
                                }
                                return c;
                            });
                            if (!isHaveKey_1) {
                                iggridColumns.push({ "key": optionValue, "width": 10 * maxWidthCharacter + 20, "headerText": '', "columnCssClass": 'nts-column', 'hidden': true });
                            }
                        }
                        var gridHeaderHeight = 24;
                        container.igGrid({
                            width: width + "px",
                            height: (data.rows * 28 + gridHeaderHeight) + "px",
                            primaryKey: optionValue,
                            columns: iggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features,
                            tabIndex: -1
                        });
                        container.ntsGridList('setupSelecting');
                        container.bind('iggridselectionrowselectionchanging', function (evt, uiX) {
                            if (container.data("enable") === false) {
                                return false;
                            }
                            var itemSelected = uiX.row.id;
                            var dataSource = container.igGrid('option', "dataSource");
                            if (container.data("fullValue")) {
                                itemSelected = _.find(dataSource, function (d) {
                                    return d[optionValue].toString() === itemSelected.toString();
                                });
                            }
                            var changingEvent = new CustomEvent("selectionChanging", {
                                detail: itemSelected,
                                bubbles: true,
                                cancelable: false,
                            });
                            container.data("chaninged", true);
                            document.getElementById(elementId).dispatchEvent(changingEvent);
                        });
                        container.bind('selectionchanged', function () {
                            var itemSelected;
                            if (container.igGridSelection('option', 'multipleSelection')) {
                                var selected = container.ntsGridList('getSelected');
                                if (!nts.uk.util.isNullOrEmpty(selected)) {
                                    itemSelected = _.map(selected, function (s) { return s.id; });
                                }
                                else {
                                    itemSelected = [];
                                }
                            }
                            else {
                                var selected = container.ntsGridList('getSelected');
                                if (!nts.uk.util.isNullOrEmpty(selected)) {
                                    itemSelected = selected.id;
                                }
                                else {
                                    itemSelected = ('');
                                }
                            }
                            container.data("selected", itemSelected);
                            var isMultiOld = container.igGridSelection('option', 'multipleSelection');
                            if (container.data("fullValue")) {
                                var dataSource = container.igGrid('option', "dataSource");
                                if (isMultiOld) {
                                    itemSelected = _.filter(dataSource, function (d) {
                                        itemSelected.indexOf(d[optionValue].toString()) >= 0;
                                    });
                                }
                                else {
                                    itemSelected = _.find(dataSource, function (d) {
                                        return d[optionValue].toString() === itemSelected.toString();
                                    });
                                }
                            }
                            if (container.data("chaninged") !== true) {
                                var changingEvent = new CustomEvent("selectionChanging", {
                                    detail: itemSelected,
                                    bubbles: true,
                                    cancelable: false,
                                });
                                document.getElementById(container.attr('id')).dispatchEvent(changingEvent);
                            }
                            container.data("chaninged", false);
                            container.data("ui-changed", true);
                            if (!_.isEqual(itemSelected, data.value())) {
                                data.value(itemSelected);
                            }
                        });
                        container.setupSearchScroll("igGrid", true);
                        container.ntsGridList("setupScrollWhenBinding");
                        container.data("multiple", isMultiSelect);
                        $("#" + gridId + "_container").find("#" + gridId + "_headers").closest("tr").hide();
                        $("#" + gridId + "_container").height($("#" + gridId + "_container").height() - gridHeaderHeight);
                    };
                    ListBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
                        var columns = data.columns;
                        var rows = data.rows;
                        var container = $(element).find(".ntsListBox");
                        var currentSource = container.igGrid('option', 'dataSource');
                        if (container.data("enable") !== enable) {
                            if (!enable) {
                                container.ntsGridList('unsetupSelecting');
                                container.addClass("disabled");
                                $(element).attr("tabindex", "-1");
                            }
                            else {
                                container.ntsGridList('setupSelecting');
                                container.removeClass("disabled");
                                $(element).attr("tabindex", $(element).data("tabindex"));
                            }
                        }
                        container.data("enable", enable);
                        if (!((String(container.attr("filtered")) === "true") || container.data("ui-changed") === true)) {
                            var currentSources = sources.slice();
                            var observableColumns = _.filter(ko.unwrap(data.columns), function (c) {
                                c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                                return c["isDateColumn"] !== undefined && c["isDateColumn"] !== null && c["isDateColumn"] === true;
                            });
                            _.forEach(currentSources, function (s) {
                                _.forEach(observableColumns, function (c) {
                                    var key = c["key"] === undefined ? c["prop"] : c["key"];
                                    s[key] = moment(s[key]).format(c["format"]);
                                });
                            });
                            if (!_.isEqual(currentSources, container.igGrid('option', 'dataSource'))) {
                                container.igGrid('option', 'dataSource', currentSources);
                                container.igGrid("dataBind");
                            }
                        }
                        else if (String(container.attr("filtered")) === "true") {
                            var filteredSource_1 = [];
                            _.forEach(currentSource, function (item) {
                                var itemX = _.find(sources, function (s) {
                                    return s[optionValue] === item[optionValue];
                                });
                                if (!nts.uk.util.isNullOrUndefined(itemX)) {
                                    filteredSource_1.push(itemX);
                                }
                            });
                            if (!_.isEqual(filteredSource_1, currentSource)) {
                                container.igGrid('option', 'dataSource', _.cloneDeep(filteredSource_1));
                                container.igGrid("dataBind");
                            }
                        }
                        var isMultiOld = container.igGridSelection('option', 'multipleSelection');
                        if (isMultiOld !== isMultiSelect) {
                            container.igGridSelection('option', 'multipleSelection', isMultiSelect);
                            if (isMultiOld && !nts.uk.util.isNullOrUndefined(data.value()) && data.value().length > 0) {
                                data.value(data.value()[0]);
                            }
                            else if (!isMultiOld && !nts.uk.util.isNullOrUndefined(data.value())) {
                                data.value([data.value()]);
                            }
                            var dataValue = data.value();
                            if (container.data("fullValue")) {
                                if (isMultiOld) {
                                    dataValue = _.map(dataValue, optionValue);
                                }
                                else {
                                    dataValue = dataValue[optionValue];
                                }
                            }
                            container.ntsGridList('setSelected', dataValue);
                        }
                        else {
                            var dataValue = data.value();
                            if (container.data("fullValue")) {
                                if (isMultiOld) {
                                    dataValue = _.map(dataValue, optionValue);
                                }
                                else {
                                    dataValue = dataValue[optionValue];
                                }
                            }
                            var currentSelectedItems = container.ntsGridList('getSelected');
                            if (isMultiOld) {
                                if (currentSelectedItems) {
                                    currentSelectedItems = _.map(currentSelectedItems, function (s) { return s["id"]; });
                                }
                                else {
                                    currentSelectedItems = [];
                                }
                                if (dataValue) {
                                    dataValue = _.map(dataValue, function (s) { return s.toString(); });
                                }
                            }
                            else {
                                if (currentSelectedItems) {
                                    currentSelectedItems = currentSelectedItems.id;
                                }
                                else {
                                    currentSelectedItems = ('');
                                }
                                if (dataValue) {
                                    dataValue = dataValue.toString();
                                }
                            }
                            var isEqual = _.isEqual(currentSelectedItems, dataValue);
                            if (!isEqual) {
                                _.defer(function () { container.trigger("selectChange"); });
                                container.ntsGridList('setSelected', dataValue);
                            }
                        }
                        container.data("ui-changed", false);
                        container.closest('.ui-iggrid').addClass('nts-gridlist').height(data.height);
                    };
                    return ListBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=listbox-ko-ext.js.map