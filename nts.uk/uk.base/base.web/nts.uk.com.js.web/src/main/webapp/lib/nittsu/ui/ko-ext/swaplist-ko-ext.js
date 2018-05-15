var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
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
                        var BUTTON_SEARCH_WIDTH = 70;
                        var INPUT_SEARCH_PADDING = 65;
                        var $swap = $(element);
                        var elementId = $swap.attr('id');
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var tabIndex = nts.uk.util.isNullOrEmpty($swap.attr("tabindex")) ? "0" : $swap.attr("tabindex");
                        $swap.data("tabindex", tabIndex);
                        $swap.attr("tabindex", "-1");
                        var data = valueAccessor();
                        var originalSource = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var totalWidth = ko.unwrap(data.width);
                        var disableMove = ko.unwrap(data.disableMoveButton);
                        var height = ko.unwrap(data.height);
                        var showSearchBox = ko.unwrap(data.showSearchBox);
                        var primaryKey = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        var leftColumns = data.leftColumns || data.columns;
                        var rightColumns = data.rightColumns || data.columns;
                        var enableRowNumbering = ko.unwrap(data.enableRowNumbering);
                        var defaultSearchText = (data.placeHolder !== undefined) ? ko.unwrap(data.placeHolder) : "コード・名称で検索・・・";
                        var beforeLeft = nts.uk.util.isNullOrUndefined(data.beforeMoveLeft) ? $.noop : data.beforeMoveLeft;
                        var beforeRight = nts.uk.util.isNullOrUndefined(data.beforeMoveRight) ? $.noop : data.beforeMoveRight;
                        var beforeAllL = nts.uk.util.isNullOrUndefined(data.beforeAllLeft) ? $.noop : data.beforeAllLeft;
                        var beforeAllR = nts.uk.util.isNullOrUndefined(data.beforeAllRight) ? $.noop : data.beforeAllRight;
                        var afterLeft = nts.uk.util.isNullOrUndefined(data.afterMoveLeft) ? $.noop : data.afterMoveLeft;
                        var afterRight = nts.uk.util.isNullOrUndefined(data.afterMoveRight) ? $.noop : data.afterMoveRight;
                        var afterAllL = nts.uk.util.isNullOrUndefined(data.afterAllLeft) ? $.noop : data.afterAllLeft;
                        var afterAllR = nts.uk.util.isNullOrUndefined(data.afterAllRight) ? $.noop : data.afterAllRight;
                        data.draggable = false;
                        $swap.wrap("<div class= 'ntsComponent ntsSwapList' id='" + elementId + "_container' tabindex='-1'/>");
                        if (totalWidth !== undefined) {
                            $swap.parent().width(totalWidth);
                        }
                        $swap.parent().height(height);
                        $swap.addClass("ntsSwapList-container");
                        var leftGridWidth = _.sumBy(leftColumns(), function (c) {
                            return c.width;
                        });
                        var rightGridWidth = _.sumBy(rightColumns(), function (c) {
                            return c.width;
                        });
                        var leftIggridColumns = _.map(leftColumns(), function (c) {
                            c["key"] = c.key === undefined ? c.prop : c.key;
                            c["dataType"] = 'string';
                            return c;
                        });
                        var rightIggridColumns = _.map(rightColumns(), function (c) {
                            c["key"] = c.key === undefined ? c.prop : c.key;
                            c["dataType"] = 'string';
                            return c;
                        });
                        var gridHeight = (height - 20);
                        var grid1Id = "#" + elementId + "-grid1";
                        var grid2Id = "#" + elementId + "-grid2";
                        if (!uk.util.isNullOrUndefined(showSearchBox) && (showSearchBox.showLeft || showSearchBox.showRight)) {
                            var initSearchArea = function ($SearchArea, searchMode, searchText) {
                                $SearchArea.append("<div class='ntsSearchTextContainer'/>")
                                    .append("<div class='ntsSearchButtonContainer'/>");
                                if (searchMode === "filter") {
                                    $SearchArea.append("<div class='ntsClearButtonContainer'/>");
                                    $SearchArea.find(".ntsClearButtonContainer")
                                        .append("<button id = " + searchAreaId + "-clear-btn" + " class='ntsSearchButton clear-btn ntsSwap_Component'/>");
                                    $SearchArea.find(".clear-btn").text("解除");
                                }
                                $SearchArea.find(".ntsSearchTextContainer")
                                    .append("<input id = " + searchAreaId + "-input" + " class = 'ntsSearchInput ntsSwap_Component ntsSearchBox nts-editor ntsSearchBox_Component'/>");
                                $SearchArea.find(".ntsSearchButtonContainer")
                                    .append("<button id = " + searchAreaId + "-btn" + " class='ntsSearchButton search-btn caret-bottom ntsSwap_Component'/>");
                                $SearchArea.find(".ntsSearchInput").attr("placeholder", searchText).wrap("<span class='nts-editor-wrapped ntsControl'/>");
                                $SearchArea.find(".search-btn").text("検索");
                            };
                            var searchAreaId = elementId + "-search-area";
                            $swap.append("<div class = 'nts-searchbbox-wrapper ntsSearchArea' id = " + searchAreaId + "/>");
                            var $searchArea = $swap.find(".ntsSearchArea");
                            $searchArea.append("<div class='ntsSwapSearchLeft'/>")
                                .append("<div class='ntsSwapSearchRight'/>");
                            $searchArea.css({ position: "relative" });
                            var searchAreaWidth = leftGridWidth + CHECKBOX_WIDTH;
                            if (showSearchBox.showLeft) {
                                var $searchLeftContainer = $swap.find(".ntsSwapSearchLeft");
                                $searchLeftContainer.width(searchAreaWidth).css({ position: "absolute", left: 0 });
                                initSearchArea($searchLeftContainer, data.searchMode, data.leftSearchBoxText || defaultSearchText);
                                $searchLeftContainer.find(".ntsSearchBox").width(searchAreaWidth - BUTTON_SEARCH_WIDTH - INPUT_SEARCH_PADDING - (data.searchMode === "filter" ? BUTTON_SEARCH_WIDTH : 0));
                            }
                            if (showSearchBox.showRight) {
                                var $searchRightContainer = $swap.find(".ntsSwapSearchRight");
                                $searchRightContainer.width(rightGridWidth + CHECKBOX_WIDTH).css({ position: "absolute", right: 0 });
                                initSearchArea($searchRightContainer, data.searchMode, data.rightSearchBoxText || defaultSearchText);
                                $searchRightContainer.find(".ntsSearchBox").width(rightGridWidth + CHECKBOX_WIDTH - BUTTON_SEARCH_WIDTH - INPUT_SEARCH_PADDING - (data.searchMode === "filter" ? BUTTON_SEARCH_WIDTH : 0));
                            }
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
                            { name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: enableRowNumbering }];
                        $swap.find("#" + elementId + "-gridArea1").width(leftGridWidth + CHECKBOX_WIDTH);
                        $swap.find("#" + elementId + "-gridArea2").width(rightGridWidth + CHECKBOX_WIDTH);
                        var leftCriterion = _.map(leftColumns(), function (c) { return c.key === undefined ? c.prop : c.key; });
                        var rightCriterion = _.map(rightColumns(), function (c) { return c.key === undefined ? c.prop : c.key; });
                        var swapParts = new Array();
                        swapParts.push(new GridSwapPart().listControl($grid1)
                            .searchControl($swap.find(".ntsSwapSearchLeft").find(".search-btn"))
                            .clearControl($swap.find(".ntsSwapSearchLeft").find(".clear-btn"))
                            .searchBox($swap.find(".ntsSwapSearchLeft").find(".ntsSearchBox"))
                            .withDataSource(originalSource)
                            .setSearchCriterion(data.leftSearchCriterion || data.searchCriterion || leftCriterion)
                            .setSearchMode(data.searchMode || "highlight")
                            .setColumns(leftColumns())
                            .setPrimaryKey(primaryKey)
                            .setInnerDrop((data.innerDrag && data.innerDrag.left !== undefined) ? data.innerDrag.left : true)
                            .setOuterDrop((data.outerDrag && data.outerDrag.left !== undefined) ? data.outerDrag.left : true)
                            .setItemsLimit((data.itemsLimit && data.itemsLimit.left !== undefined) ? data.itemsLimit.left : null)
                            .build());
                        swapParts.push(new GridSwapPart().listControl($grid2)
                            .searchControl($swap.find(".ntsSwapSearchRight").find(".search-btn"))
                            .clearControl($swap.find(".ntsSwapSearchRight").find(".clear-btn"))
                            .searchBox($swap.find(".ntsSwapSearchRight").find(".ntsSearchBox"))
                            .withDataSource(data.value())
                            .setSearchCriterion(data.rightSearchCriterion || data.searchCriterion || rightCriterion)
                            .setSearchMode(data.searchMode || "highlight")
                            .setColumns(rightColumns())
                            .setPrimaryKey(primaryKey)
                            .setInnerDrop((data.innerDrag && data.innerDrag.right !== undefined) ? data.innerDrag.right : true)
                            .setOuterDrop((data.outerDrag && data.outerDrag.right !== undefined) ? data.outerDrag.right : true)
                            .setItemsLimit((data.itemsLimit && data.itemsLimit.right !== undefined) ? data.itemsLimit.right : null)
                            .build());
                        this.swapper = new SwapHandler().setModel(new GridSwapList($swap, swapParts));
                        $grid1.igGrid({
                            width: leftGridWidth + CHECKBOX_WIDTH,
                            height: (gridHeight) + "px",
                            primaryKey: primaryKey,
                            columns: leftIggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features,
                            tabIndex: -1
                        });
                        $grid1.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(gridHeight)
                            .attr("tabindex", tabIndex);
                        $grid1.ntsGridList('setupSelecting');
                        $grid2.igGrid({
                            width: rightGridWidth + CHECKBOX_WIDTH,
                            height: (gridHeight) + "px",
                            primaryKey: primaryKey,
                            columns: rightIggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features,
                            tabIndex: -1
                        });
                        if (data.draggable === true) {
                            this.swapper.enableDragDrop(data.value);
                            if (data.multipleDrag && data.multipleDrag.left === true) {
                                this.swapper.Model.swapParts[0].$listControl.addClass("multiple-drag");
                            }
                            if (data.multipleDrag && data.multipleDrag.right === true) {
                                this.swapper.Model.swapParts[1].$listControl.addClass("multiple-drag");
                            }
                        }
                        $grid2.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(gridHeight)
                            .attr("tabindex", tabIndex);
                        $grid2.ntsGridList('setupSelecting');
                        var $moveArea = $swap.find("#" + elementId + "-move-data")
                            .append("<button class='move-button move-forward ntsSwap_Component'><i class='img-icon icon-next'></i></button>")
                            .append("<button class='move-button move-back ntsSwap_Component'><i class='img-icon icon-prev'></i></button>");
                        var $moveForward = $moveArea.find(".move-forward");
                        var $moveBack = $moveArea.find(".move-back");
                        var swapper = this.swapper;
                        $moveForward.click(function () {
                            swapper.Model.move(true, data.value, false, beforeRight, afterRight);
                        });
                        $moveBack.click(function () {
                            swapper.Model.move(false, data.value, false, beforeLeft, afterLeft);
                        });
                        $swap.find(".ntsSwap_Component").attr("tabindex", tabIndex);
                        this.swapper.Model.$container.bind("swaplistgridsizeexceed", function (evt, data) {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_887" });
                        });
                        $swap.find(".ntsSwapGrid").bind("listfilterred", function (evt, data) {
                            var $gridX = $(this);
                            var currentDataSource = $gridX.igGrid('option', 'dataSource');
                            var selected = $gridX.ntsGridList('getSelected');
                            var selectItems = _.filter(currentDataSource, function (itemFilterd) {
                                return _.find(selected, function (item) {
                                    var itemVal = itemFilterd[primaryKey];
                                    if (nts.uk.util.isNullOrUndefined(itemVal) || nts.uk.util.isNullOrUndefined(item["id"])) {
                                        return false;
                                    }
                                    return itemVal.toString() === item["id"].toString();
                                }) !== undefined;
                            });
                            $gridX.ntsGridList("setSelected", _.map(selectItems, primaryKey));
                        });
                    };
                    NtsSwapListBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var $swap = $(element);
                        var disableMove = ko.unwrap(data.disableMoveButton);
                        if (disableMove === true) {
                            $swap.find(".move-button").attr("disabled", "disabled");
                        }
                        else {
                            $swap.find(".move-button").removeAttr("disabled");
                        }
                        var elementId = this.swapper.Model.$container.attr('id');
                        var primaryKey = this.swapper.Model.swapParts[0].primaryKey;
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var $grid1 = this.swapper.Model.swapParts[0].$listControl;
                        var $grid2 = this.swapper.Model.swapParts[1].$listControl;
                        var currentSource = $grid1.igGrid('option', 'dataSource');
                        var currentSelectedList = $grid2.igGrid('option', 'dataSource');
                        var newSources = (data.dataSource !== undefined ? data.dataSource() : data.options());
                        var newSelectedList = data.value();
                        _.remove(newSources, function (item) {
                            return _.find(newSelectedList, function (selected) {
                                return selected[primaryKey] === item[primaryKey];
                            }) !== undefined;
                        });
                        this.swapper.Model.swapParts[0].setDataSource(newSources.slice());
                        this.swapper.Model.transportBuilder.setFirst(newSources);
                        this.swapper.Model.swapParts[1].setDataSource(newSelectedList.slice());
                        this.swapper.Model.transportBuilder.setSecond(newSelectedList);
                    };
                    NtsSwapListBindingHandler.prototype.makeBindings = function () {
                        var handler = this;
                        return {
                            init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
                                var newHandler = Object.create(handler);
                                if (handler.init) {
                                    handler.init.call(newHandler, element, valueAccessor, allBindings, viewModel, bindingContext);
                                }
                                if (handler.update) {
                                    ko.computed({
                                        read: handler.update.bind(newHandler, element, valueAccessor, allBindings, viewModel, bindingContext),
                                        disposeWhenNodeIsRemoved: element
                                    });
                                }
                            }
                        };
                    };
                    return NtsSwapListBindingHandler;
                }());
                ko.bindingHandlers['ntsSwapList'] = new NtsSwapListBindingHandler().makeBindings();
                var SwapHandler = (function () {
                    function SwapHandler() {
                    }
                    SwapHandler.prototype.setModel = function (model) {
                        this.model = model;
                        return this;
                    };
                    Object.defineProperty(SwapHandler.prototype, "Model", {
                        get: function () {
                            return this.model;
                        },
                        enumerable: true,
                        configurable: true
                    });
                    SwapHandler.prototype.handle = function (parts, value) {
                        var self = this;
                        var model = this.model;
                        for (var id in parts) {
                            var options = {
                                items: "tbody > tr",
                                containment: this.model.$container,
                                cursor: "move",
                                connectWith: ".ntsSwapGrid",
                                placeholder: "ui-state-highlight",
                                helper: this._createHelper,
                                appendTo: this.model.$container,
                                start: function (evt, ui) {
                                    model.transportBuilder.at(model.sender(ui));
                                },
                                beforeStop: function (evt, ui) {
                                    self._beforeStop.call(this, model, evt, ui);
                                },
                                update: function (evt, ui) {
                                    self._update.call(this, model, evt, ui, value);
                                }
                            };
                            this.model.swapParts[parts[id]].initDraggable(options);
                        }
                    };
                    SwapHandler.prototype._createHelper = function (evt, ui) {
                        var selectedRowElms = $(evt.currentTarget).igGrid("selectedRows");
                        selectedRowElms.sort(function (one, two) {
                            return one.index - two.index;
                        });
                        var $helper;
                        if ($(evt.currentTarget).hasClass("multiple-drag") && selectedRowElms.length > 1) {
                            $helper = $("<div><table><tbody></tbody></table></div>").addClass("select-drag");
                            var rowId = ui.data("row-idx");
                            var selectedItems = selectedRowElms.map(function (elm) { return elm.element; });
                            var height = 0;
                            $.each(selectedItems, function () {
                                $helper.find("tbody").append($(this).clone());
                                height += $(this).outerHeight();
                                if (rowId !== this.data("row-idx"))
                                    $(this).hide();
                            });
                            $helper.height(height);
                            $helper.find("tr").first().children().each(function (idx) {
                                $(this).width(ui.children().eq(idx).width());
                            });
                        }
                        else {
                            $helper = ui.clone();
                            $helper.children().each(function (idx) {
                                $(this).width(ui.children().eq(idx).width());
                            });
                        }
                        return $helper[0];
                    };
                    SwapHandler.prototype._beforeStop = function (model, evt, ui) {
                        var partId = model.transportBuilder.startAt === "first" ? 0 : 1;
                        var destPartId = model.receiver(ui) === "first" ? 0 : 1;
                        model.transportBuilder.toAdjacent(model.neighbor(ui)).target(model.target(ui));
                        var max = model.swapParts[destPartId].itemsLimit;
                        if (ui.helper.hasClass("select-drag") === true) {
                            var rowsInHelper = ui.helper.find("tr");
                            var rows = rowsInHelper.toArray();
                            if (model.transportBuilder.startAt === model.receiver(ui)
                                || (model.swapParts[partId].outerDrop === false
                                    && model.transportBuilder.startAt !== model.receiver(ui))
                                || (!uk.util.isNullOrUndefined(max) && (rows.length + model.swapParts[destPartId].dataSource.length > max))) {
                                $(this).sortable("cancel");
                                for (var idx in rows) {
                                    model.swapParts[partId].$listControl.find("tbody").children()
                                        .eq($(rows[idx]).data("row-idx")).show();
                                }
                                if (!uk.util.isNullOrUndefined(max) && (rows.length + model.swapParts[destPartId].dataSource.length > max)) {
                                    model.$container.trigger($.Event("swaplistgridsizeexceed"), [model.swapParts[destPartId].$listControl, max]);
                                }
                                return;
                            }
                            else {
                                var standardIdx = ui.placeholder.index();
                                var targetIdx = ui.item.data("row-idx");
                                var rowsBefore = new Array();
                                var rowsAfter = new Array();
                                for (var id in rows) {
                                    if ($(rows[id]).data("row-idx") < targetIdx)
                                        rowsBefore.push(rows[id]);
                                    else if ($(rows[id]).data("row-idx") > targetIdx)
                                        rowsAfter.push(rows[id]);
                                }
                                model.swapParts[destPartId].$listControl.find("tbody").children()
                                    .eq(standardIdx - 1).before(rowsBefore).after(rowsAfter);
                                var sourceRows = model.swapParts[partId].$listControl.find("tbody").children();
                                for (var index = rowsAfter.length - 1; index >= 0; index--) {
                                    if ($(rows[index]).data("row-idx") === targetIdx)
                                        continue;
                                    sourceRows.eq($(rowsAfter[index]).data("row-idx") - 1).remove();
                                }
                                for (var val in rowsBefore) {
                                    sourceRows.eq($(rowsBefore[val]).data("row-idx")).remove();
                                }
                            }
                        }
                        else if ((model.swapParts[partId].innerDrop === false
                            && model.transportBuilder.startAt === model.receiver(ui))
                            || (model.swapParts[partId].outerDrop === false
                                && model.transportBuilder.startAt !== model.receiver(ui))
                            || (!uk.util.isNullOrUndefined(max) && model.swapParts[destPartId].dataSource.length >= max)) {
                            $(this).sortable("cancel");
                            if (!uk.util.isNullOrUndefined(max) && model.swapParts[destPartId].dataSource.length >= max) {
                                model.$container.trigger($.Event("swaplistgridsizeexceed"), [model.swapParts[destPartId].$listControl, max]);
                            }
                        }
                    };
                    SwapHandler.prototype._update = function (model, evt, ui, value) {
                        if (ui.item.closest("table").length === 0)
                            return;
                        model.transportBuilder.directTo(model.receiver(ui)).update();
                        if (model.transportBuilder.startAt === model.transportBuilder.direction) {
                            model.transportBuilder.startAt === "first"
                                ? model.swapParts[0].bindData(model.transportBuilder.getFirst())
                                : model.swapParts[1].bindData(model.transportBuilder.getSecond());
                        }
                        else {
                            model.swapParts[0].bindData(model.transportBuilder.getFirst());
                            model.swapParts[1].bindData(model.transportBuilder.getSecond());
                        }
                        value(model.transportBuilder.getSecond());
                        setTimeout(function () { model.dropDone(); }, 0);
                    };
                    SwapHandler.prototype.enableDragDrop = function (value, parts) {
                        parts = parts || [0, 1];
                        this.model.enableDrag(this, value, parts, this.handle);
                    };
                    return SwapHandler;
                }());
                var SwapModel = (function () {
                    function SwapModel($container, swapParts) {
                        this.$container = $container;
                        this.swapParts = swapParts;
                        this.transportBuilder = new ListItemTransporter(this.swapParts[0].dataSource, this.swapParts[1].dataSource)
                            .primary(this.swapParts[0].primaryKey);
                    }
                    return SwapModel;
                }());
                var SearchResult = (function () {
                    function SearchResult(results, indices) {
                        this.data = results;
                        this.indices = indices;
                    }
                    return SearchResult;
                }());
                var SwapPart = (function () {
                    function SwapPart() {
                        this.searchMode = "highlight";
                        this.innerDrop = true;
                        this.outerDrop = true;
                    }
                    SwapPart.prototype.listControl = function ($listControl) {
                        this.$listControl = $listControl;
                        return this;
                    };
                    SwapPart.prototype.searchControl = function ($searchControl) {
                        this.$searchControl = $searchControl;
                        return this;
                    };
                    SwapPart.prototype.clearControl = function ($clearControl) {
                        this.$clearControl = $clearControl;
                        return this;
                    };
                    SwapPart.prototype.searchBox = function ($searchBox) {
                        this.$searchBox = $searchBox;
                        return this;
                    };
                    SwapPart.prototype.setSearchMode = function (searchMode) {
                        this.searchMode = searchMode;
                        return this;
                    };
                    SwapPart.prototype.setSearchCriterion = function (searchCriterion) {
                        this.searchCriterion = searchCriterion;
                        return this;
                    };
                    SwapPart.prototype.withDataSource = function (dataSource) {
                        this.dataSource = dataSource;
                        this.resetOriginalDataSource();
                        return this;
                    };
                    SwapPart.prototype.setDataSource = function (dataSource) {
                        this.bindData(dataSource);
                        this.resetOriginalDataSource();
                        return this;
                    };
                    SwapPart.prototype.setColumns = function (columns) {
                        this.columns = columns;
                        return this;
                    };
                    SwapPart.prototype.setPrimaryKey = function (primaryKey) {
                        this.primaryKey = primaryKey;
                        return this;
                    };
                    SwapPart.prototype.setInnerDrop = function (innerDrop) {
                        this.innerDrop = innerDrop;
                        return this;
                    };
                    SwapPart.prototype.setOuterDrop = function (outerDrop) {
                        this.outerDrop = outerDrop;
                        return this;
                    };
                    SwapPart.prototype.setItemsLimit = function (itemsLimit) {
                        this.itemsLimit = itemsLimit;
                        return this;
                    };
                    SwapPart.prototype.initDraggable = function (opts) {
                        this.$listControl.sortable(opts).disableSelection();
                    };
                    SwapPart.prototype.resetOriginalDataSource = function () {
                        this.originalDataSource = _.cloneDeep(this.dataSource);
                    };
                    SwapPart.prototype.search = function () {
                        var searchContents = this.$searchBox.val();
                        var orders = new Array();
                        if (nts.uk.util.isNullOrEmpty(searchContents)) {
                            nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("FND_E_SEARCH_NOWORD"));
                            return null;
                        }
                        var searchCriterion = this.searchCriterion;
                        if (this.originalDataSource === undefined)
                            this.resetOriginalDataSource();
                        var results = _.filter(this.originalDataSource, function (value, index) {
                            var found = false;
                            _.forEach(searchCriterion, function (criteria) {
                                if (value[criteria].toString().indexOf(searchContents) !== -1) {
                                    found = true;
                                    return false;
                                }
                                else
                                    return true;
                            });
                            orders.push(index);
                            return found;
                        });
                        return new SearchResult(results, orders);
                    };
                    SwapPart.prototype.bindData = function (src) {
                        this.bindIn(src);
                        this.dataSource = src;
                    };
                    SwapPart.prototype.bindSearchEvent = function () {
                        var self = this;
                        var proceedSearch = this.proceedSearch;
                        var clearFilter = this.clearFilter;
                        this.$searchControl.click(function (evt, ui) {
                            proceedSearch.apply(self);
                        });
                        this.$clearControl.click(function (evt, ui) {
                            clearFilter.apply(self);
                            ;
                        });
                        this.$searchBox.keydown(function (evt, ui) {
                            var $input = this;
                            if (evt.which === 13) {
                                proceedSearch.apply(self);
                                _.defer(function () {
                                    $input.focus();
                                });
                            }
                        });
                    };
                    SwapPart.prototype.proceedSearch = function () {
                        if (this.searchMode === "filter") {
                            var results = this.search();
                            if (results === null)
                                return;
                            this.bindData(results.data);
                            this.$listControl.trigger("listfilterred");
                        }
                        else {
                            this.highlightSearch();
                        }
                    };
                    SwapPart.prototype.clearFilter = function () {
                        if (this.searchMode === "filter") {
                            if (this.originalDataSource === undefined) {
                                this.resetOriginalDataSource();
                            }
                            this.bindData(this.originalDataSource);
                        }
                    };
                    SwapPart.prototype.build = function () {
                        this.bindSearchEvent();
                        return this;
                    };
                    return SwapPart;
                }());
                var GridSwapPart = (function (_super) {
                    __extends(GridSwapPart, _super);
                    function GridSwapPart() {
                        _super.apply(this, arguments);
                    }
                    GridSwapPart.prototype.search = function () {
                        return _super.prototype.search.call(this);
                    };
                    GridSwapPart.prototype.highlightSearch = function () {
                        var value = this.$searchBox.val();
                        if (nts.uk.util.isNullOrEmpty(value)) {
                            nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("FND_E_SEARCH_NOWORD"));
                            return;
                        }
                        var source = this.dataSource.slice();
                        var selected = this.$listControl.ntsGridList("getSelected");
                        if (selected.length > 0) {
                            var gotoEnd = source.splice(0, selected[0].index + 1);
                            source = source.concat(gotoEnd);
                        }
                        var iggridColumns = _.map(this.columns, function (c) {
                            c["key"] = c.key === undefined ? c.prop : c.key;
                            c["dataType"] = 'string';
                            return c;
                        });
                        var searchedValues = _.find(source, function (val) {
                            return _.find(iggridColumns, function (x) {
                                return x !== undefined && x !== null && val[x["key"]].toString().indexOf(value) >= 0;
                            }) !== undefined;
                        });
                        if (searchedValues === undefined) {
                            nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("FND_E_SEARCH_NOHIT"));
                            return;
                        }
                        this.$listControl.ntsGridList('setSelected', searchedValues !== undefined ? [searchedValues[this.primaryKey]] : []);
                        if (searchedValues !== undefined && (selected.length === 0 ||
                            selected[0].id !== searchedValues[this.primaryKey])) {
                            var current = this.$listControl.igGrid("selectedRows");
                            if (current.length > 0 && this.$listControl.igGrid("hasVerticalScrollbar")) {
                                this.$listControl.igGrid("virtualScrollTo", current[0].index === source.length - 1
                                    ? current[0].index : current[0].index + 1);
                            }
                        }
                    };
                    GridSwapPart.prototype.bindIn = function (src) {
                        this.$listControl.igGrid("option", "dataSource", src);
                        this.$listControl.igGrid("dataBind");
                    };
                    return GridSwapPart;
                }(SwapPart));
                var GridSwapList = (function (_super) {
                    __extends(GridSwapList, _super);
                    function GridSwapList() {
                        _super.apply(this, arguments);
                    }
                    GridSwapList.prototype.sender = function (opts) {
                        return opts.item.closest("table")[0].id == this.swapParts[0].$listControl.attr("id")
                            ? "first" : "second";
                    };
                    GridSwapList.prototype.receiver = function (opts) {
                        return opts.item.closest("table")[0].id == this.swapParts[1].$listControl.attr("id")
                            ? "second" : "first";
                    };
                    GridSwapList.prototype.target = function (opts) {
                        if (opts.helper !== undefined && opts.helper.hasClass("select-drag")) {
                            return opts.helper.find("tr").map(function () {
                                return $(this).data("id");
                            });
                        }
                        return [opts.item.data("id")];
                    };
                    GridSwapList.prototype.neighbor = function (opts) {
                        return opts.item.prev().length === 0 ? "ceil" : opts.item.prev().data("id");
                    };
                    GridSwapList.prototype.dropDone = function () {
                        var self = this;
                        self.swapParts[0].$listControl.igGridSelection("clearSelection");
                        self.swapParts[1].$listControl.igGridSelection("clearSelection");
                        setTimeout(function () {
                            (self.transportBuilder.direction === "first"
                                ? self.swapParts[0].$listControl
                                : self.swapParts[1].$listControl).igGrid("virtualScrollTo", self.transportBuilder.incomeIndex);
                            (self.transportBuilder.startAt === "first"
                                ? self.swapParts[0].$listControl
                                : self.swapParts[1].$listControl).igGrid("virtualScrollTo", self.transportBuilder.outcomeIndex + 1);
                        }, 0);
                    };
                    GridSwapList.prototype.enableDrag = function (ctx, value, parts, cb) {
                        var self = this;
                        for (var idx in parts) {
                            this.swapParts[parts[idx]].$listControl.on("iggridrowsrendered", function (evt, ui) {
                                cb.call(ctx, parts, value);
                            });
                        }
                    };
                    GridSwapList.prototype.move = function (forward, value, moveAll, beforMove, afterMove) {
                        var primaryKey = this.transportBuilder.primaryKey;
                        var $source = forward === true ? this.swapParts[0].$listControl : this.swapParts[1].$listControl;
                        var sourceList = forward === true ? this.swapParts[0].dataSource : this.swapParts[1].dataSource;
                        var $dest = forward === true ? this.swapParts[1].$listControl : this.swapParts[0].$listControl;
                        var destList = forward === true ? this.swapParts[1].dataSource : this.swapParts[0].dataSource;
                        var max = forward === true ? this.swapParts[1].itemsLimit : this.swapParts[0].itemsLimit;
                        var oldSource = _.cloneDeep(destList);
                        if (moveAll) {
                            var selectedIds = sourceList.map(function (row) { return row[primaryKey]; });
                            if (beforMove(forward, oldSource, selectedIds) == false) {
                                return;
                            }
                            if (!uk.util.isNullOrUndefined(max) && (selectedIds.length + destList.length > max)) {
                                this.$container.trigger($.Event("swaplistgridsizeexceed"), [$dest, max]);
                                return;
                            }
                            this.transportBuilder.at(forward ? "first" : "second").directTo(forward ? "second" : "first")
                                .toAdjacent(destList.length > 0 ? destList[destList.length - 1][primaryKey] : null).update(moveAll);
                        }
                        else {
                            var selectedRows = $source.igGrid("selectedRows");
                            if (nts.uk.util.isNullOrEmpty(selectedRows)) {
                                return;
                            }
                            if (!uk.util.isNullOrUndefined(max) && (selectedRows.length + destList.length > max)) {
                                this.$container.trigger($.Event("swaplistgridsizeexceed"), [$dest, max]);
                                return;
                            }
                            selectedRows.sort(function (one, two) {
                                return one.index - two.index;
                            });
                            var firstSelected = selectedRows[0];
                            var selectedIds = selectedRows.map(function (row) { return row.id; });
                            if (beforMove(forward, oldSource, selectedIds) == false) {
                                return;
                            }
                            this.transportBuilder.at(forward ? "first" : "second").directTo(forward ? "second" : "first")
                                .target(selectedIds).toAdjacent(destList.length > 0 ? destList[destList.length - 1][primaryKey] : null).update(moveAll);
                        }
                        var firstSource = this.transportBuilder.getFirst();
                        var secondSource = this.transportBuilder.getSecond();
                        this.swapParts[0].setDataSource(firstSource);
                        this.swapParts[1].setDataSource(secondSource);
                        value(secondSource);
                        $source.igGridSelection("clearSelection");
                        $dest.igGridSelection("clearSelection");
                        afterMove(forward, oldSource, _.cloneDeep(forward ? secondSource : firstSource));
                        if (forward) {
                            var selectIndex = firstSource.length === 0 ? -1
                                : (firstSource.length - 1 < firstSelected.index ? firstSource.length - 1 : firstSelected.index);
                        }
                        else {
                            var selectIndex = secondSource.length === 0 ? -1
                                : (secondSource.length - 1 < firstSelected.index ? secondSource.length - 1 : firstSelected.index);
                        }
                        setTimeout(function () {
                            $source.igGrid("virtualScrollTo", selectIndex);
                            $dest.igGrid("virtualScrollTo", destList.length - 1);
                            if (selectIndex >= 0) {
                                $source.igGridSelection("selectRowById", forward ? firstSource[selectIndex][primaryKey] : secondSource[selectIndex][primaryKey]);
                            }
                            if (!forward) {
                                $dest.ntsGridList("setSelected", selectedIds);
                            }
                        }, 10);
                    };
                    return GridSwapList;
                }(SwapModel));
                var ListItemTransporter = (function () {
                    function ListItemTransporter(firstList, secondList) {
                        this.firstList = firstList;
                        this.secondList = secondList;
                    }
                    ListItemTransporter.prototype.first = function (firstList) {
                        this.firstList = firstList;
                        return this;
                    };
                    ListItemTransporter.prototype.second = function (secondList) {
                        this.secondList = secondList;
                        return this;
                    };
                    ListItemTransporter.prototype.at = function (startAt) {
                        this.startAt = startAt;
                        return this;
                    };
                    ListItemTransporter.prototype.directTo = function (direction) {
                        this.direction = direction;
                        return this;
                    };
                    ListItemTransporter.prototype.out = function (index) {
                        this.outcomeIndex = index;
                        return this;
                    };
                    ListItemTransporter.prototype.into = function (index) {
                        this.incomeIndex = index;
                        return this;
                    };
                    ListItemTransporter.prototype.primary = function (primaryKey) {
                        this.primaryKey = primaryKey;
                        return this;
                    };
                    ListItemTransporter.prototype.target = function (targetIds) {
                        this.targetIds = targetIds;
                        return this;
                    };
                    ListItemTransporter.prototype.toAdjacent = function (adjId) {
                        if (adjId === null)
                            adjId = "ceil";
                        this.adjacentIncomeId = adjId;
                        return this;
                    };
                    ListItemTransporter.prototype.indexOf = function (list, targetId) {
                        var _this = this;
                        return _.findIndex(list, function (elm) { return elm[_this.primaryKey].toString() === targetId.toString(); });
                    };
                    ListItemTransporter.prototype.move = function (src, dest, moveAll) {
                        if (moveAll) {
                            this.moveAllItems(src, dest);
                        }
                        else {
                            this.moveNormal(src, dest);
                        }
                    };
                    ListItemTransporter.prototype.moveNormal = function (src, dest) {
                        for (var i = 0; i < this.targetIds.length; i++) {
                            this.outcomeIndex = this.indexOf(src, this.targetIds[i]);
                            if (this.outcomeIndex === -1)
                                return;
                            var target = src.splice(this.outcomeIndex, 1);
                            this.incomeIndex = this.indexOf(dest, this.adjacentIncomeId) + 1;
                            if (this.incomeIndex === 0) {
                                if (this.adjacentIncomeId === "ceil")
                                    this.incomeIndex = 0;
                                else if (target !== undefined) {
                                    src.splice(this.outcomeIndex, 0, target[0]);
                                    return;
                                }
                            }
                            dest.splice(this.incomeIndex + i, 0, target[0]);
                        }
                    };
                    ListItemTransporter.prototype.moveAllItems = function (src, dest) {
                        dest.push.apply(dest, src);
                        _.remove(src);
                    };
                    ListItemTransporter.prototype.determineDirection = function () {
                        if (this.startAt.toLowerCase() !== this.direction.toLowerCase()
                            && this.direction.toLowerCase() === "second") {
                            return "firstToSecond";
                        }
                        else if (this.startAt.toLowerCase() !== this.direction.toLowerCase()
                            && this.direction.toLowerCase() === "first") {
                            return "secondToFirst";
                        }
                        else if (this.startAt.toLowerCase() === this.direction.toLowerCase()
                            && this.direction.toLowerCase() === "first") {
                            return "insideFirst";
                        }
                        else
                            return "insideSecond";
                    };
                    ListItemTransporter.prototype.update = function (moveAll) {
                        switch (this.determineDirection()) {
                            case "firstToSecond":
                                this.move(this.firstList, this.secondList, moveAll);
                                break;
                            case "secondToFirst":
                                this.move(this.secondList, this.firstList, moveAll);
                                break;
                            case "insideFirst":
                                this.move(this.firstList, this.firstList, moveAll);
                                break;
                            case "insideSecond":
                                this.move(this.secondList, this.secondList, moveAll);
                                break;
                        }
                    };
                    ListItemTransporter.prototype.getFirst = function () {
                        return this.firstList;
                    };
                    ListItemTransporter.prototype.getSecond = function () {
                        return this.secondList;
                    };
                    ListItemTransporter.prototype.setFirst = function (first) {
                        this.firstList = first;
                    };
                    ListItemTransporter.prototype.setSecond = function (second) {
                        this.secondList = second;
                    };
                    return ListItemTransporter;
                }());
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=swaplist-ko-ext.js.map