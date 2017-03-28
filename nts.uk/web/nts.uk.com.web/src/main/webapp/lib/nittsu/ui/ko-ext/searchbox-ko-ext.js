var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var filteredArray = function (array, searchTerm, fields, childField) {
                    if (!array) {
                        return [];
                    }
                    if (!(searchTerm instanceof String)) {
                        searchTerm = "" + searchTerm;
                    }
                    var flatArr = nts.uk.util.flatArray(array, childField);
                    var filter = searchTerm.toLowerCase();
                    if (!filter) {
                        return flatArr;
                    }
                    var filtered = ko.utils.arrayFilter(flatArr, function (item) {
                        var i = fields.length;
                        while (i--) {
                            var prop = fields[i];
                            var strProp = ("" + item[prop]).toLocaleLowerCase();
                            if (strProp.indexOf(filter) !== -1) {
                                return true;
                            }
                            ;
                        }
                        return false;
                    });
                    return filtered;
                };
                var getNextItem = function (selected, arr, searchResult, selectedKey, isArray) {
                    var current = null;
                    if (isArray) {
                        if (selected.length > 0)
                            current = selected[0];
                    }
                    else if (selected !== undefined && selected !== '' && selected !== null) {
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
                                if ((i < searchResult.length - 1) && item[selectedKey] == current)
                                    return searchResult[i + 1][selectedKey];
                            }
                            return searchResult[nextIndex][selectedKey];
                        }
                        return searchResult[0][selectedKey];
                    }
                    return undefined;
                };
                var NtsSearchBoxBindingHandler = (function () {
                    function NtsSearchBoxBindingHandler() {
                    }
                    NtsSearchBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                        var nextSearch = function () {
                            var filtArr = searchBox.data("searchResult");
                            var compareKey = fields[0];
                            var isArray = $.isArray(selected());
                            var selectedItem = getNextItem(selected(), nts.uk.util.flatArray(arr, childField), filtArr, selectedKey, isArray);
                            if (data.mode) {
                                if (data.mode == 'igGrid') {
                                    var selectArr = [];
                                    selectArr.push("" + selectedItem);
                                    component.ntsGridList("setSelected", selectArr);
                                    data.selected(selectArr);
                                    component.trigger("selectChange");
                                }
                                else if (data.mode == 'igTree') {
                                    var liItem = $("li[data-value='" + selectedItem + "']");
                                    component.igTree("expandToNode", liItem);
                                    component.igTree("select", liItem);
                                }
                            }
                            else {
                                if (!isArray)
                                    selected(selectedItem);
                                else {
                                    selected([]);
                                    selected.push(selectedItem);
                                }
                                component.trigger("selectChange");
                            }
                        };
                        $input.keyup(function () {
                            $input.change();
                        }).keydown(function (event) {
                            if (event.which == 13) {
                                event.preventDefault();
                                nextSearch();
                            }
                        });
                        $input.change(function (event) {
                            var searchTerm = $input.val();
                            searchBox.data("searchResult", filteredArray(ko.unwrap(data.items), searchTerm, fields, childField));
                        });
                        $button.click(nextSearch);
                    };
                    NtsSearchBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                    };
                    return NtsSearchBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsSearchBox'] = new NtsSearchBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=searchbox-ko-ext.js.map