var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsSwapListBindingHandler = (function () {
                    function NtsSwapListBindingHandler() {
                    }
                    NtsSwapListBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                        var totalWidth = ko.unwrap(data.width);
                        var height = ko.unwrap(data.height);
                        var showSearchBox = ko.unwrap(data.showSearchBox);
                        var primaryKey = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        var columns = data.columns;
                        $swap.wrap("<div class= 'ntsComponent ntsSwapList' id='" + elementId + "_container'/>");
                        if (totalWidth !== undefined) {
                            $swap.parent().width(totalWidth);
                        }
                        $swap.parent().height(height);
                        $swap.addClass("ntsSwapList-container");
                        var gridWidth = _.sumBy(columns(), function (c) {
                            return c.width;
                        });
                        var iggridColumns = _.map(columns(), function (c) {
                            c["key"] = c.key === undefined ? c.prop : c.key;
                            c["dataType"] = 'string';
                            return c;
                        });
                        var gridHeight = (height - 20);
                        var grid1Id = "#" + elementId + "-grid1";
                        var grid2Id = "#" + elementId + "-grid2";
                        if (!uk.util.isNullOrUndefined(showSearchBox) && (showSearchBox.showLeft || showSearchBox.showEright)) {
                            var search = function ($swap, gridId, primaryKey) {
                                var value = $swap.find(".ntsSearchInput").val();
                                var source = $(gridId).igGrid("option", "dataSource").slice();
                                var selected = $(gridId).ntsGridList("getSelected");
                                if (selected.length > 0) {
                                    var gotoEnd = source.splice(0, selected[0].index + 1);
                                    source = source.concat(gotoEnd);
                                }
                                var searchedValues = _.find(source, function (val) {
                                    return _.find(iggridColumns, function (x) {
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
                            };
                            var initSearchArea = function ($SearchArea, targetId) {
                                $SearchArea.append("<div class='ntsSearchTextContainer'/>")
                                    .append("<div class='ntsSearchButtonContainer'/>");
                                $SearchArea.find(".ntsSearchTextContainer")
                                    .append("<input id = " + searchAreaId + "-input" + " class = 'ntsSearchInput ntsSearchBox'/>");
                                $SearchArea.find(".ntsSearchButtonContainer")
                                    .append("<button id = " + searchAreaId + "-btn" + " class='ntsSearchButton search-btn caret-bottom'/>");
                                $SearchArea.find(".ntsSearchInput").attr("placeholder", "コード・名称で検索・・・").keyup(function (event, ui) {
                                    if (event.which === 13) {
                                        search($SearchArea, targetId, primaryKey);
                                    }
                                });
                                $SearchArea.find(".ntsSearchButton").text("検索").click(function (event, ui) {
                                    search($SearchArea, targetId, primaryKey);
                                });
                            };
                            var searchAreaId = elementId + "-search-area";
                            $swap.append("<div class = 'ntsSearchArea' id = " + searchAreaId + "/>");
                            var $searchArea = $swap.find(".ntsSearchArea");
                            $searchArea.append("<div class='ntsSwapSearchLeft'/>")
                                .append("<div class='ntsSwapSearchRight'/>");
                            $searchArea.css({ position: "relative" });
                            var searchAreaWidth = gridWidth + CHECKBOX_WIDTH;
                            if (showSearchBox.showLeft) {
                                var $searchLeftContainer = $swap.find(".ntsSwapSearchLeft");
                                $searchLeftContainer.width(searchAreaWidth).css({ position: "absolute", left: 0 });
                                initSearchArea($searchLeftContainer, grid1Id);
                            }
                            if (showSearchBox.showRight) {
                                var $searchRightContainer = $swap.find(".ntsSwapSearchRight");
                                $searchRightContainer.width(gridWidth + CHECKBOX_WIDTH).css({ position: "absolute", right: 0 });
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
                        var move = function (id1, id2, key, currentSource, value, isForward) {
                            var selectedEmployees = _.sortBy($(isForward ? id1 : id2).igGrid("selectedRows"), 'id');
                            if (selectedEmployees.length > 0) {
                                $(isForward ? id1 : id2).igGridSelection("clearSelection");
                                var source = $(isForward ? id1 : id2).igGrid("option", "dataSource");
                                var employeeList = [];
                                for (var i = 0; i < selectedEmployees.length; i++) {
                                    var current = source[selectedEmployees[i].index];
                                    if (current[key].toString() === selectedEmployees[i].id.toString()) {
                                        employeeList.push(current);
                                    }
                                    else {
                                        var sameCodes = _.find(source, function (subject) {
                                            return subject[key].toString() === selectedEmployees[i].id.toString();
                                        });
                                        if (sameCodes !== undefined) {
                                            employeeList.push(sameCodes);
                                        }
                                    }
                                }
                                var length = value().length;
                                var notExisted = _.filter(employeeList, function (list) {
                                    return _.find(currentSource, function (data) {
                                        return data[key].toString() === list[key].toString();
                                    }) === undefined;
                                });
                                if (notExisted.length > 0) {
                                    $(id1).igGrid("virtualScrollTo", 0);
                                    $(id2).igGrid("virtualScrollTo", 0);
                                    var newSource = _.filter(source, function (list) {
                                        return _.find(notExisted, function (data) {
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
                        };
                        $moveForward.click(function () {
                            move(grid1Id, grid2Id, primaryKey, data.value(), data.value, true);
                        });
                        $moveBack.click(function () {
                            move(grid1Id, grid2Id, primaryKey, $(grid1Id).igGrid("option", "dataSource"), data.value, false);
                        });
                    };
                    NtsSwapListBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $swap = $(element);
                        var data = valueAccessor();
                        var elementId = $swap.attr('id');
                        var primaryKey = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var $grid1 = $swap.find("#" + elementId + "-grid1");
                        var $grid2 = $swap.find("#" + elementId + "-grid2");
                        var currentSource = $grid1.igGrid('option', 'dataSource');
                        var currentSelected = $grid2.igGrid('option', 'dataSource');
                        var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
                        var selectedSources = data.value();
                        _.remove(sources, function (item) {
                            return _.find(selectedSources, function (selected) {
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
                    };
                    return NtsSwapListBindingHandler;
                }());
                ko.bindingHandlers['ntsSwapList'] = new NtsSwapListBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=swaplist-ko-ext.js.map