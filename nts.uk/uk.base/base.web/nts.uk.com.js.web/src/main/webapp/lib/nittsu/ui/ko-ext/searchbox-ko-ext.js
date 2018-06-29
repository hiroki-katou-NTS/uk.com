var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var SearchBox = (function () {
                    function SearchBox(source, searchField, childField) {
                        this.childField = childField;
                        this.source = nts.uk.util.isNullOrEmpty(source) ? [] : this.cloneDeep(source);
                        this.searchField = searchField;
                    }
                    SearchBox.prototype.search = function (searchKey) {
                        var self = this;
                        if (nts.uk.util.isNullOrEmpty(this.source)) {
                            return [];
                        }
                        var flatArr = nts.uk.util.flatArray(this.source, this.childField);
                        var filtered = _.filter(flatArr, function (item) {
                            return _.find(self.searchField, function (x) {
                                if (x !== undefined && x !== null) {
                                    var val = item[x].toString();
                                    return val.indexOf(searchKey.toString()) >= 0;
                                }
                                return false;
                            }) !== undefined;
                        });
                        return filtered;
                    };
                    SearchBox.prototype.setDataSource = function (source) {
                        this.source = nts.uk.util.isNullOrEmpty(source) ? [] : this.cloneDeep(source);
                    };
                    SearchBox.prototype.getDataSource = function () {
                        return this.cloneDeep(this.source);
                    };
                    SearchBox.prototype.cloneDeep = function (source) {
                        var self = this;
                        return self.cloneDeepX(source);
                    };
                    SearchBox.prototype.cloneDeepX = function (source) {
                        return _.cloneDeep(source);
                    };
                    return SearchBox;
                }());
                koExtentions.SearchBox = SearchBox;
                var SearchResult = (function () {
                    function SearchResult() {
                        this.options = [];
                        this.selectItems = [];
                    }
                    return SearchResult;
                }());
                koExtentions.SearchResult = SearchResult;
                var SearchPub = (function () {
                    function SearchPub(key, mode, source, searchField, childField) {
                        this.seachBox = new SearchBox(source, searchField, childField);
                        ;
                        this.mode = nts.uk.util.isNullOrEmpty(mode) ? "highlight" : mode;
                        this.key = key;
                    }
                    SearchPub.prototype.search = function (searchKey, selectedItems) {
                        var result = new SearchResult();
                        var filtered = this.seachBox.search(searchKey);
                        if (!nts.uk.util.isNullOrEmpty(filtered)) {
                            var key_1 = this.key;
                            if (this.mode === "highlight") {
                                result.options = this.seachBox.getDataSource();
                                var index = 0;
                                if (!nts.uk.util.isNullOrEmpty(selectedItems)) {
                                    var firstItemValue_1 = $.isArray(selectedItems)
                                        ? selectedItems[0]["id"].toString() : selectedItems["id"].toString();
                                    index = _.findIndex(filtered, function (item) {
                                        return item[key_1].toString() === firstItemValue_1;
                                    });
                                    if (!nts.uk.util.isNullOrUndefined(index)) {
                                        index++;
                                    }
                                }
                                if (index >= 0) {
                                    result.selectItems = [filtered[index >= filtered.length ? 0 : index]];
                                }
                            }
                            else if (this.mode === "filter") {
                                result.options = filtered;
                                var selectItem = _.filter(filtered, function (itemFilterd) {
                                    return _.find(selectedItems, function (item) {
                                        var itemVal = itemFilterd[key_1];
                                        if (nts.uk.util.isNullOrUndefined(itemVal) || nts.uk.util.isNullOrUndefined(item["id"])) {
                                            return false;
                                        }
                                        return itemVal.toString() === item["id"].toString();
                                    }) !== undefined;
                                });
                                result.selectItems = selectItem;
                            }
                        }
                        return result;
                    };
                    SearchPub.prototype.setDataSource = function (source) {
                        this.seachBox.setDataSource(source);
                    };
                    SearchPub.prototype.getDataSource = function () {
                        return this.seachBox.getDataSource();
                    };
                    return SearchPub;
                }());
                koExtentions.SearchPub = SearchPub;
                var NtsSearchBoxBindingHandler = (function () {
                    function NtsSearchBoxBindingHandler() {
                    }
                    NtsSearchBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var minusWidth = 0;
                        var data = ko.unwrap(valueAccessor());
                        var fields = ko.unwrap(data.fields);
                        var searchText = (data.searchText !== undefined) ? ko.unwrap(data.searchText) : "検索";
                        var placeHolder = (data.placeHolder !== undefined) ? ko.unwrap(data.placeHolder) : "コード・名称で検索・・・";
                        var searchMode = (data.searchMode !== undefined) ? ko.unwrap(data.searchMode) : "highlight";
                        var label = (data.label !== undefined) ? ko.unwrap(data.label) : "";
                        var enable = ko.unwrap(data.enable);
                        var selectedKey = null;
                        if (data.selectedKey) {
                            selectedKey = ko.unwrap(data.selectedKey);
                        }
                        var dataSource = ko.unwrap(data.items);
                        var childField = null;
                        if (data.childField) {
                            childField = ko.unwrap(data.childField);
                        }
                        var targetMode = data.mode;
                        if (targetMode === "listbox") {
                            targetMode = "igGrid";
                        }
                        var $container = $(element);
                        var tabIndex = nts.uk.util.isNullOrEmpty($container.attr("tabindex")) ? "0" : $container.attr("tabindex");
                        $container.addClass("nts-searchbbox-wrapper").removeAttr("tabindex");
                        $container.append("<div class='input-wrapper'><span class='nts-editor-wrapped ntsControl'><input class='ntsSearchBox nts-editor ntsSearchBox_Component' type='text' /></span></div>");
                        $container.append("<div class='input-wrapper'><button class='search-btn caret-bottom ntsSearchBox_Component'>" + searchText + "</button></div>");
                        if (!nts.uk.util.isNullOrEmpty(label)) {
                            var $formLabel = $("<div>", { text: label });
                            $formLabel.prependTo($container);
                            ko.bindingHandlers["ntsFormLabel"].init($formLabel[0], function () {
                                return {};
                            }, allBindingsAccessor, viewModel, bindingContext);
                            minusWidth += $formLabel.outerWidth(true);
                        }
                        var $button = $container.find("button.search-btn");
                        var $input = $container.find("input.ntsSearchBox");
                        minusWidth += $button.outerWidth(true);
                        if (searchMode === "filter") {
                            $container.append("<button class='clear-btn ntsSearchBox_Component'>" + nts.uk.ui.toBeResource.clear + "</button>");
                            var $clearButton = $container.find("button.clear-btn");
                            minusWidth += $clearButton.outerWidth(true);
                            $clearButton.click(function (evt, ui) {
                                var component = $("#" + ko.unwrap(data.comId));
                                if (component.hasClass("listbox-wrapper")) {
                                    component = $("#" + ko.unwrap(data.comId)).find(".ntsListBox");
                                }
                                var srh = $container.data("searchObject");
                                $input.val("");
                                component.igGrid("option", "dataSource", srh.seachBox.getDataSource());
                                component.igGrid("dataBind");
                                $container.data("searchKey", null);
                                component.attr("filtered", "false");
                                _.defer(function () {
                                    component.trigger("selectChange");
                                });
                            });
                        }
                        $input.attr("placeholder", placeHolder);
                        $input.attr("data-name", nts.uk.ui.toBeResource.searchBox);
                        $input.outerWidth($container.outerWidth(true) - minusWidth);
                        var primaryKey = ko.unwrap(data.targetKey);
                        var searchObject = new SearchPub(primaryKey, searchMode, dataSource, fields, childField);
                        $container.data("searchObject", searchObject);
                        var search = function (searchKey) {
                            if (targetMode) {
                                var selectedItems = void 0, isMulti = void 0;
                                var component_1 = $("#" + ko.unwrap(data.comId));
                                if (targetMode == 'igGrid') {
                                    if (component_1.hasClass("listbox-wrapper")) {
                                        component_1 = $("#" + ko.unwrap(data.comId)).find(".ntsListBox");
                                    }
                                    selectedItems = component_1.ntsGridList("getSelected");
                                    isMulti = component_1.igGridSelection('option', 'multipleSelection');
                                }
                                else if (targetMode == 'igTree') {
                                    selectedItems = component_1.ntsTreeView("getSelected");
                                    isMulti = component_1.igTreeGridSelection('option', 'multipleSelection');
                                }
                                else if (targetMode == 'igTreeDrag') {
                                    selectedItems = component_1.ntsTreeDrag("getSelected");
                                    isMulti = component_1.ntsTreeDrag('option', 'isMulti');
                                }
                                var srh_1 = $container.data("searchObject");
                                var result_1 = srh_1.search(searchKey, selectedItems);
                                if (nts.uk.util.isNullOrEmpty(result_1.options)) {
                                    var mes = '';
                                    if (searchMode === "highlight") {
                                        mes = nts.uk.resource.getMessage("FND_E_SEARCH_NOHIT");
                                    }
                                    else {
                                        mes = nts.uk.ui.toBeResource.targetNotFound;
                                    }
                                    nts.uk.ui.dialog.alert(mes).then(function () {
                                        $input.focus();
                                        $input.select();
                                    });
                                    return false;
                                }
                                var selectedProperties = _.map(result_1.selectItems, primaryKey);
                                if (targetMode === 'igGrid') {
                                    component_1.ntsGridList("setSelected", selectedProperties);
                                    if (searchMode === "filter") {
                                        $container.data("filteredSrouce", result_1.options);
                                        component_1.attr("filtered", "true");
                                        var source = _.filter(data.items(), function (item) {
                                            return _.find(result_1.options, function (itemFilterd) {
                                                return itemFilterd[primaryKey] === item[primaryKey];
                                            }) !== undefined || _.find(srh_1.getDataSource(), function (oldItem) {
                                                return oldItem[primaryKey] === item[primaryKey];
                                            }) === undefined;
                                        });
                                        component_1.igGrid("option", "dataSource", _.cloneDeep(source));
                                        component_1.igGrid("dataBind");
                                        component_1.trigger("selectionchanged");
                                    }
                                    else {
                                        component_1.trigger("selectionchanged");
                                    }
                                }
                                else if (targetMode == 'igTree') {
                                    component_1.ntsTreeView("setSelected", selectedProperties);
                                    component_1.trigger("selectionchanged");
                                }
                                else if (targetMode == 'igTreeDrag') {
                                    component_1.ntsTreeDrag("setSelected", selectedProperties);
                                }
                                _.defer(function () {
                                    component_1.trigger("selectChange");
                                });
                                $container.data("searchKey", searchKey);
                            }
                            return true;
                        };
                        var nextSearch = function () {
                            var searchKey = $input.val();
                            if (nts.uk.util.isNullOrEmpty(searchKey)) {
                                nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("FND_E_SEARCH_NOWORD")).then(function () {
                                    $input.focus();
                                });
                                return false;
                            }
                            return search(searchKey);
                        };
                        $input.keydown(function (event) {
                            if (event.which == 13) {
                                event.preventDefault();
                                var result_2 = nextSearch();
                                _.defer(function () {
                                    if (result_2) {
                                        $input.focus();
                                    }
                                });
                            }
                        });
                        $button.click(function () {
                            nextSearch();
                        });
                        $container.find(".ntsSearchBox_Component").attr("tabindex", tabIndex);
                        if (enable === false) {
                            $container.find(".ntsSearchBox_Component").attr('disabled', 'disabled');
                        }
                        return { 'controlsDescendantBindings': true };
                    };
                    NtsSearchBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $searchBox = $(element);
                        var data = valueAccessor();
                        var arr = ko.unwrap(data.items);
                        var searchMode = ko.unwrap(data.searchMode);
                        var primaryKey = ko.unwrap(data.targetKey);
                        var enable = ko.unwrap(data.enable);
                        var component;
                        if (data.mode === "listbox") {
                            component = $("#" + ko.unwrap(data.comId)).find(".ntsListBox");
                        }
                        else {
                            component = $("#" + ko.unwrap(data.comId));
                        }
                        var srhX = $searchBox.data("searchObject");
                        if (component.attr("filtered") === "true") {
                            var isCheck = component.triggerHandler("checknewitem");
                            if (isCheck !== false) {
                                var currentSoruce_1 = srhX.getDataSource();
                                var newItems = _.filter(arr, function (i) {
                                    return _.find(currentSoruce_1, function (ci) {
                                        return ci[primaryKey] === i[primaryKey];
                                    }) === undefined;
                                });
                                if (!nts.uk.util.isNullOrEmpty(newItems)) {
                                    var gridSources_1 = component.igGrid("option", "dataSource");
                                    _.forEach(newItems, function (item) {
                                        gridSources_1.push(item);
                                    });
                                    component.igGrid("option", "dataSource", _.cloneDeep(gridSources_1));
                                    component.igGrid("dataBind");
                                }
                            }
                        }
                        srhX.setDataSource(arr);
                        if (enable === false) {
                            $searchBox.find(".ntsSearchBox_Component").attr('disabled', 'disabled');
                        }
                        else {
                            $searchBox.find(".ntsSearchBox_Component").removeAttr('disabled');
                        }
                    };
                    return NtsSearchBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsSearchBox'] = new NtsSearchBoxBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=searchbox-ko-ext.js.map