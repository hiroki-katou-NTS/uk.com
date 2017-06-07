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
                        var self = this;
                        return _.cloneDeep(source);
                    };
                    return SearchBox;
                }());
                var SearchResult = (function () {
                    function SearchResult() {
                        this.options = [];
                        this.selectItems = [];
                    }
                    return SearchResult;
                }());
                var SearchPub = (function () {
                    function SearchPub(key, mode, source, searchField, childField) {
                        this.seachBox = new SearchBox(source, searchField, childField);
                        ;
                        this.mode = nts.uk.util.isNullOrEmpty(mode) ? "highlight" : mode;
                        this.key = key;
                    }
                    SearchPub.prototype.search = function (searchKey, selectedItems) {
                        var result = new SearchResult();
                        var filted = this.seachBox.search(searchKey);
                        if (!nts.uk.util.isNullOrEmpty(filted)) {
                            var key_1 = this.key;
                            if (this.mode === "highlight") {
                                result.options = this.seachBox.getDataSource();
                                var index = 0;
                                if (!nts.uk.util.isNullOrEmpty(selectedItems)) {
                                    var firstItemValue_1 = $.isArray(selectedItems)
                                        ? selectedItems[0]["id"].toString() : selectedItems["id"].toString();
                                    index = _.findIndex(filted, function (item) {
                                        return item[key_1].toString() === firstItemValue_1;
                                    });
                                    if (!nts.uk.util.isNullOrUndefined(index)) {
                                        index++;
                                    }
                                }
                                if (index >= 0) {
                                    result.selectItems = [filted[index >= filted.length ? 0 : index]];
                                }
                            }
                            else if (this.mode === "filter") {
                                result.options = filted;
                                var selectItem = _.filter(filted, function (itemFilterd) {
                                    return _.find(selectedItems, function (item) {
                                        var itemVal = itemFilterd[key_1];
                                        return itemVal === item["id"];
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
                        var searchBox = $(element);
                        var data = ko.unwrap(valueAccessor());
                        var fields = ko.unwrap(data.fields);
                        var searchText = (data.searchText !== undefined) ? ko.unwrap(data.searchText) : "検索";
                        var placeHolder = (data.placeHolder !== undefined) ? ko.unwrap(data.placeHolder) : "コード・名称で検索・・・";
                        var selected = data.selected;
                        var searchMode = (data.searchMode !== undefined) ? ko.unwrap(data.searchMode) : "highlight";
                        var selectedKey = null;
                        if (data.selectedKey) {
                            selectedKey = ko.unwrap(data.selectedKey);
                        }
                        var dataSource = ko.unwrap(data.items);
                        var childField = null;
                        if (data.childField) {
                            childField = ko.unwrap(data.childField);
                        }
                        var component;
                        var targetMode = data.mode;
                        if (targetMode === "listbox") {
                            component = $("#" + ko.unwrap(data.comId)).find(".ntsListBox");
                            targetMode = "igGrid";
                        }
                        else {
                            component = $("#" + ko.unwrap(data.comId));
                        }
                        var $container = $(element);
                        $container.append("<span class='nts-editor-wrapped ntsControl'><input class='ntsSearchBox nts-editor' type='text' /></span>");
                        $container.append("<button class='search-btn caret-bottom'>" + searchText + "</button>");
                        var $button = $container.find("button.search-btn");
                        var $input = $container.find("input.ntsSearchBox");
                        var buttonWidth = $button.outerWidth(true);
                        if (searchMode === "filter") {
                            $container.append("<button class='clear-btn'>解除</button>");
                            var $clearButton = $container.find("button.clear-btn");
                            buttonWidth += $clearButton.outerWidth(true);
                            $clearButton.click(function (evt, ui) {
                                if (component.length === 0) {
                                    component = $("#" + ko.unwrap(data.comId)).find(".ntsListBox");
                                }
                                var srh = $container.data("searchObject");
                                component.igGrid("option", "dataSource", srh.seachBox.getDataSource());
                                component.igGrid("dataBind");
                                $container.data("searchKey", null);
                                component.attr("filtered", false);
                            });
                        }
                        $input.attr("placeholder", placeHolder);
                        $input.attr("data-name", "検索テキストボックス");
                        $input.outerWidth($container.outerWidth(true) - buttonWidth);
                        var primaryKey = ko.unwrap(data.targetKey);
                        var searchObject = new SearchPub(primaryKey, searchMode, dataSource, fields, childField);
                        $container.data("searchObject", searchObject);
                        var search = function (searchKey) {
                            if (targetMode) {
                                var selectedItems = void 0;
                                if (targetMode == 'igGrid') {
                                    if (component.length === 0) {
                                        component = $("#" + ko.unwrap(data.comId)).find(".ntsListBox");
                                    }
                                    selectedItems = component.ntsGridList("getSelected");
                                }
                                else if (targetMode == 'igTree') {
                                    selectedItems = component.ntsTreeView("getSelected");
                                }
                                var srh = $container.data("searchObject");
                                var result = srh.search(searchKey, selectedItems);
                                if (nts.uk.util.isNullOrEmpty(result.options) && searchMode === "highlight") {
                                    nts.uk.ui.dialog.alert("#FND_E_SEARCH_NOHIT");
                                    return;
                                }
                                var isMulti = targetMode === 'igGrid' ? component.igGridSelection('option', 'multipleSelection')
                                    : component.igTreeGridSelection('option', 'multipleSelection');
                                var selectedProperties = _.map(result.selectItems, primaryKey);
                                var selectedValue = void 0;
                                if (selectedKey !== null) {
                                    selectedValue = isMulti ? _.map(result.selectItems, selectedKey) :
                                        result.selectItems.length > 0 ? result.selectItems[0][selectedKey] : undefined;
                                }
                                else {
                                    selectedValue = isMulti ? [result.selectItems] :
                                        result.selectItems.length > 0 ? result.selectItems[0] : undefined;
                                }
                                if (targetMode === 'igGrid') {
                                    if (searchMode === "filter") {
                                        $container.data("filteredSrouce", result.options);
                                        component.attr("filtered", true);
                                        selected(selectedValue);
                                    }
                                    else {
                                        selected(selectedValue);
                                    }
                                    component.ntsGridList("setSelected", selectedProperties);
                                }
                                else if (targetMode == 'igTree') {
                                    component.ntsTreeView("setSelected", selectedProperties);
                                    data.selected(selectedValue);
                                }
                                _.defer(function () {
                                    component.trigger("selectChange");
                                });
                                $container.data("searchKey", searchKey);
                            }
                        };
                        var nextSearch = function () {
                            var searchKey = $input.val();
                            if (nts.uk.util.isNullOrEmpty(searchKey)) {
                                nts.uk.ui.dialog.alert("#FND_E_SEARCH_NOWORD");
                                return;
                            }
                            search(searchKey);
                        };
                        $input.keydown(function (event) {
                            if (event.which == 13) {
                                event.preventDefault();
                                nextSearch();
                            }
                        });
                        $button.click(function () {
                            nextSearch();
                        });
                    };
                    NtsSearchBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $searchBox = $(element);
                        var data = valueAccessor();
                        var arr = ko.unwrap(data.items);
                        var searchMode = ko.unwrap(data.searchMode);
                        var primaryKey = ko.unwrap(data.targetKey);
                        var selectedValue = ko.unwrap(data.selected);
                        var targetMode = data.mode;
                        var component;
                        if (targetMode === "listbox") {
                            component = $("#" + ko.unwrap(data.comId)).find(".ntsListBox");
                            targetMode = "igGrid";
                        }
                        else {
                            component = $("#" + ko.unwrap(data.comId));
                        }
                        var srhX = $searchBox.data("searchObject");
                        if (searchMode === "filter") {
                            var filteds_1 = $searchBox.data("filteredSrouce");
                            if (!nts.uk.util.isNullOrUndefined(filteds_1)) {
                                var source = _.filter(arr, function (item) {
                                    return _.find(filteds_1, function (itemFilterd) {
                                        return itemFilterd[primaryKey] === item[primaryKey];
                                    }) !== undefined || _.find(srhX.getDataSource(), function (oldItem) {
                                        return oldItem[primaryKey] === item[primaryKey];
                                    }) === undefined;
                                });
                                component.igGrid("option", "dataSource", source);
                                component.igGrid("dataBind");
                            }
                        }
                        srhX.setDataSource(arr);
                    };
                    return NtsSearchBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsSearchBox'] = new NtsSearchBoxBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=searchbox-ko-ext.js.map