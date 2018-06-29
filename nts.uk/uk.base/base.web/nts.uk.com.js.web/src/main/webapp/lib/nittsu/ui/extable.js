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
            var exTable;
            (function (exTable_1) {
                var NAMESPACE = "extable";
                var DISTANCE = 3;
                var SPACE = 10;
                var HEADER = "xheader";
                var HEADER_PRF = "ex-header-";
                var BODY_PRF = "ex-body-";
                var HEADER_TBL_PRF = "extable-header-";
                var BODY_TBL_PRF = "extable-body-";
                var H_BTN_CLS = "ex-height-btn";
                var LEFTMOST = "leftmost";
                var MIDDLE = "middle";
                var DETAIL = "detail";
                var VERTICAL_SUM = "vert-sum";
                var HORIZONTAL_SUM = "horz-sum";
                var LEFT_HORZ_SUM = "left-horz-sum";
                var CRUD = "crud";
                var ADD_ROW = "add-row";
                var DEL_ROWS = "delete-rows";
                var H_BTN_HEIGHT = "24px";
                var DYNAMIC = "dynamic";
                var FIXED = "fixed";
                var COPY_PASTE = "copyPaste";
                var EDIT = "edit";
                var STICK = "stick";
                var Connector = {};
                var _scrollWidth;
                var ExTable = (function () {
                    function ExTable($container, options) {
                        this.bodyHeightSetMode = DYNAMIC;
                        this.windowXOccupation = 0;
                        this.windowYOccupation = 0;
                        this.updateMode = EDIT;
                        this.pasteOverWrite = true;
                        this.stickOverWrite = true;
                        this.overflowTooltipOn = false;
                        this.$container = $container[0];
                        this.$commander = options.primaryTable ? options.primaryTable[0] : null;
                        this.$follower = options.secondaryTable ? options.secondaryTable[0] : null;
                        this.bodyRowHeight = options.bodyRowHeight;
                        this.headerHeight = options.headerHeight;
                        this.bodyHeight = options.bodyHeight;
                        this.horzSumHeaderHeight = options.horizontalSumHeaderHeight;
                        this.horzSumBodyHeight = options.horizontalSumBodyHeight;
                        this.horzSumBodyRowHeight = options.horizontalSumBodyRowHeight;
                        this.areaResize = options.areaResize;
                        this.remainSizes = !uk.util.isNullOrUndefined(options.remainSizes) ? options.remainSizes : true;
                        this.heightSetter = options.heightSetter;
                        this.bodyHeightSetMode = options.bodyHeightMode;
                        this.windowXOccupation = options.windowXOccupation;
                        this.windowYOccupation = options.windowYOccupation;
                        if (options.updateMode) {
                            this.updateMode = options.updateMode;
                        }
                        this.manipulatorId = options.manipulatorId;
                        this.manipulatorKey = options.manipulatorKey;
                        this.pasteOverWrite = options.pasteOverWrite;
                        this.stickOverWrite = options.stickOverWrite;
                        this.viewMode = options.viewMode;
                        this.overflowTooltipOn = !uk.util.isNullOrUndefined(options.showTooltipIfOverflow)
                            ? options.showTooltipIfOverflow : false;
                        this.determination = options.determination;
                        this.features = options.features;
                        $.data(this.$container, internal.X_OCCUPY, this.windowXOccupation);
                        $.data(this.$container, internal.Y_OCCUPY, this.windowYOccupation);
                        helper.makeConnector();
                    }
                    ExTable.prototype.setUpdateMode = function (updateMode) {
                        this.updateMode = updateMode;
                        this.detailContent.updateMode = updateMode;
                    };
                    ExTable.prototype.setViewMode = function (mode) {
                        this.viewMode = mode;
                        this.detailContent.viewMode = mode;
                    };
                    ExTable.prototype.LeftmostHeader = function (leftmostHeader) {
                        this.leftmostHeader = _.cloneDeep(leftmostHeader);
                        this.setHeaderClass(this.leftmostHeader, LEFTMOST);
                        return this;
                    };
                    ExTable.prototype.LeftmostContent = function (leftmostContent) {
                        this.leftmostContent = _.cloneDeep(leftmostContent);
                        this.setBodyClass(this.leftmostContent, LEFTMOST);
                        this.leftmostContent.updateMode = this.updateMode;
                        if (feature.isEnable(this.features, feature.UPDATING)) {
                            this.leftmostHeader.width = parseInt(this.leftmostHeader.width) + controls.CHECKBOX_COL_WIDTH + "px";
                            controls.addCheckBoxDef([this.leftmostHeader, this.leftmostContent]);
                        }
                        return this;
                    };
                    ExTable.prototype.MiddleHeader = function (middleHeader) {
                        this.middleHeader = _.cloneDeep(middleHeader);
                        this.setHeaderClass(this.middleHeader, MIDDLE);
                        this.middleHeader.updateMode = this.updateMode;
                        return this;
                    };
                    ExTable.prototype.MiddleContent = function (middleContent) {
                        this.middleContent = _.cloneDeep(middleContent);
                        this.setBodyClass(this.middleContent, MIDDLE);
                        return this;
                    };
                    ExTable.prototype.DetailHeader = function (detailHeader) {
                        this.detailHeader = _.cloneDeep(detailHeader);
                        this.setHeaderClass(this.detailHeader, DETAIL);
                        return this;
                    };
                    ExTable.prototype.DetailContent = function (detailContent) {
                        this.detailContent = _.cloneDeep(detailContent);
                        this.setBodyClass(this.detailContent, DETAIL);
                        this.detailContent.updateMode = this.updateMode;
                        this.detailContent.viewMode = this.viewMode;
                        return this;
                    };
                    ExTable.prototype.VerticalSumHeader = function (verticalSumHeader) {
                        this.verticalSumHeader = _.cloneDeep(verticalSumHeader);
                        this.setHeaderClass(this.verticalSumHeader, VERTICAL_SUM);
                        return this;
                    };
                    ExTable.prototype.VerticalSumContent = function (verticalSumContent) {
                        this.verticalSumContent = _.cloneDeep(verticalSumContent);
                        this.setBodyClass(this.verticalSumContent, VERTICAL_SUM);
                        return this;
                    };
                    ExTable.prototype.LeftHorzSumHeader = function (leftHorzSumHeader) {
                        this.leftHorzSumHeader = _.cloneDeep(leftHorzSumHeader);
                        this.setHeaderClass(this.leftHorzSumHeader, LEFT_HORZ_SUM);
                        return this;
                    };
                    ExTable.prototype.LeftHorzSumContent = function (leftHorzSumContent) {
                        this.leftHorzSumContent = _.cloneDeep(leftHorzSumContent);
                        this.setBodyClass(this.leftHorzSumContent, LEFT_HORZ_SUM);
                        return this;
                    };
                    ExTable.prototype.HorizontalSumHeader = function (horizontalSumHeader) {
                        this.horizontalSumHeader = _.cloneDeep(horizontalSumHeader);
                        this.setHeaderClass(this.horizontalSumHeader, HORIZONTAL_SUM);
                        return this;
                    };
                    ExTable.prototype.HorizontalSumContent = function (horizontalSumContent) {
                        this.horizontalSumContent = _.cloneDeep(horizontalSumContent);
                        this.setBodyClass(this.horizontalSumContent, HORIZONTAL_SUM);
                        return this;
                    };
                    ExTable.prototype.setHeaderClass = function (options, part) {
                        options.tableClass = HEADER_TBL_PRF + part;
                        options.containerClass = HEADER_PRF + part;
                        if (uk.util.isNullOrUndefined(options.showTooltipIfOverflow)) {
                            options.overflowTooltipOn = this.overflowTooltipOn;
                        }
                    };
                    ExTable.prototype.setBodyClass = function (options, part) {
                        options.tableClass = BODY_TBL_PRF + part;
                        options.containerClass = BODY_PRF + part;
                        if (uk.util.isNullOrUndefined(options.showTooltipIfOverflow)) {
                            options.overflowTooltipOn = this.overflowTooltipOn;
                        }
                    };
                    ExTable.prototype.create = function () {
                        var self = this;
                        var left = "0px";
                        var top = "0px";
                        if (!self.satisfyPrebuild())
                            return;
                        self.headers = _.filter([self.leftmostHeader, self.middleHeader, self.detailHeader, self.verticalSumHeader], function (h) {
                            return !uk.util.isNullOrUndefined(h);
                        });
                        self.bodies = _.filter([self.leftmostContent, self.middleContent, self.detailContent, self.verticalSumContent], function (b) {
                            return !uk.util.isNullOrUndefined(b);
                        });
                        var widthParts, gridHeight;
                        storage.area.getPartWidths(self.$container).ifPresent(function (parts) {
                            widthParts = JSON.parse(parts);
                            return null;
                        });
                        storage.tableHeight.get(self.$container).ifPresent(function (height) {
                            gridHeight = JSON.parse(height);
                            return null;
                        });
                        self.$container.classList.add(NAMESPACE);
                        $.data(self.$container, NAMESPACE, self);
                        var pTable = $.data(self.$container, NAMESPACE);
                        pTable.owner = { headers: [], bodies: [],
                            find: function (name, where) {
                                var o = this;
                                var elm = o[where].filter(function (e, i) { return e.classList.contains(name); });
                                if (!elm || elm.length === 0)
                                    return null;
                                return elm;
                            } };
                        var scrollWidth = helper.getScrollWidth();
                        var headerWrappers = [], bodyWrappers = [];
                        for (var i = 0; i < self.headers.length; i++) {
                            if (!uk.util.isNullOrUndefined(self.headers[i])) {
                                self.headers[i].overflow = "hidden";
                                self.headers[i].height = self.headerHeight;
                                self.headers[i].isHeader = true;
                                self.setWrapperWidth(self.headers[i], widthParts);
                                var $headerWrapper = render.createWrapper("0px", left, self.headers[i]);
                                if (!uk.util.isNullOrUndefined($headerWrapper.style.maxWidth)
                                    && parseFloat(self.headers[i].width) > parseFloat($headerWrapper.style.maxWidth)) {
                                    self.headers[i].width = $headerWrapper.style.maxWidth;
                                }
                                pTable.owner.headers.push($headerWrapper);
                                $headerWrapper.classList.add(HEADER);
                                self.$container.appendChild($headerWrapper);
                                render.process($headerWrapper, self.headers[i]);
                                left = (parseInt(left) + parseInt(self.headers[i].width) + DISTANCE) + "px";
                                top = (parseInt(self.headers[i].height) + DISTANCE) + "px";
                                headerWrappers.push($headerWrapper);
                            }
                        }
                        left = "0px";
                        for (var i = 0; i < self.bodies.length; i++) {
                            var $bodyWrapper = void 0;
                            if (!uk.util.isNullOrUndefined(self.bodies[i])) {
                                self.bodies[i].rowHeight = self.bodyRowHeight;
                                self.bodies[i].height = gridHeight ? (parseFloat(gridHeight) + "px") : self.bodyHeight;
                                self.bodies[i].width = self.headers[i].width;
                                self.setWrapperWidth(self.bodies[i], widthParts);
                                $bodyWrapper = render.createWrapper(top, left, self.bodies[i]);
                                pTable.owner.bodies.push($bodyWrapper);
                                self.$container.appendChild($bodyWrapper);
                                if (i === self.bodies.length - 1 && !uk.util.isNullOrUndefined($bodyWrapper)) {
                                    self.bodies[i].overflow = "scroll";
                                    self.bodies[i].width = (parseFloat($bodyWrapper.style.width) + scrollWidth) + "px";
                                    self.bodies[i].height = (parseFloat($bodyWrapper.style.height) + scrollWidth) + "px";
                                    if (!uk.util.isNullOrUndefined($bodyWrapper.style.maxWidth)) {
                                        $bodyWrapper.style.maxWidth = (parseFloat($bodyWrapper.style.maxWidth) + scrollWidth) + "px";
                                    }
                                    scroll.syncDoubDirVerticalScrolls(_.concat(bodyWrappers, $bodyWrapper));
                                    scroll.bindVertWheel($bodyWrapper, true);
                                }
                                else if (i > 0 && i < self.bodies.length - 1) {
                                    self.bodies[i].overflowX = "scroll";
                                    self.bodies[i].overflowY = "hidden";
                                    self.bodies[i].height = (parseFloat($bodyWrapper.style.height) + scrollWidth) + "px";
                                    scroll.bindVertWheel($bodyWrapper);
                                }
                                else {
                                    scroll.bindVertWheel($bodyWrapper);
                                }
                                render.process($bodyWrapper, self.bodies[i]);
                                left = (parseInt(left) + parseInt(self.bodies[i].width) + DISTANCE) + "px";
                                if (self.bodies[i].containerClass !== BODY_PRF + DETAIL) {
                                    scroll.syncHorizontalScroll(headerWrappers[i], $bodyWrapper);
                                }
                                bodyWrappers.push($bodyWrapper);
                                if (feature.isEnable(self.headers[i].features, feature.COLUMN_RESIZE)) {
                                    new resize.ColumnAdjuster(headerWrappers[i].find("table"), $bodyWrapper.find("table")).handle();
                                }
                            }
                        }
                        self.createHorzSums(pTable);
                        self.setupCrudArea();
                        self.generalSettings(headerWrappers, bodyWrappers);
                    };
                    ExTable.prototype.createHorzSums = function (table) {
                        var self = this;
                        var $detailHeader = self.$container.querySelector("." + HEADER_PRF + DETAIL);
                        var $detailContent = self.$container.querySelector("." + BODY_PRF + DETAIL);
                        var headerTop = parseFloat($detailHeader.style.height) + parseFloat($detailContent.style.height) + DISTANCE + helper.getScrollWidth() + SPACE;
                        var bodyTop = headerTop + parseFloat(self.horzSumHeaderHeight) + DISTANCE + "px";
                        var sumPosLeft = $detailHeader.style.left;
                        var leftHorzWidth = parseInt(sumPosLeft) - DISTANCE;
                        var $leftSumHeaderWrapper, $leftSumContentWrapper, $sumHeaderWrapper, $sumContentWrapper;
                        if (self.leftHorzSumHeader) {
                            self.leftHorzSumHeader.height = self.horzSumHeaderHeight;
                            self.leftHorzSumHeader.width = leftHorzWidth + "px";
                            self.leftHorzSumHeader.overflow = "hidden";
                            self.leftHorzSumHeader.isHeader = true;
                            $leftSumHeaderWrapper = render.createWrapper(headerTop + "px", "0xp", self.leftHorzSumHeader);
                            table.owner.headers.push($leftSumHeaderWrapper);
                            $leftSumHeaderWrapper.classList.add(HEADER);
                            self.$container.appendChild($leftSumHeaderWrapper);
                            render.process($leftSumHeaderWrapper, self.leftHorzSumHeader);
                        }
                        if (self.leftHorzSumContent) {
                            self.leftHorzSumContent.rowHeight = self.horzSumBodyRowHeight;
                            self.leftHorzSumContent.height = parseFloat(self.horzSumBodyHeight) + helper.getScrollWidth() + "px";
                            self.leftHorzSumContent.width = leftHorzWidth + "px";
                            $leftSumContentWrapper = render.createWrapper(bodyTop, "0px", self.leftHorzSumContent);
                            table.owner.bodies.push($leftSumContentWrapper);
                            self.leftHorzSumContent.overflowX = "scroll";
                            self.leftHorzSumContent.overflowY = "hidden";
                            self.$container.appendChild($leftSumContentWrapper);
                            render.process($leftSumContentWrapper, self.leftHorzSumContent);
                            scroll.bindVertWheel($leftSumContentWrapper);
                        }
                        if (self.horizontalSumHeader) {
                            self.horizontalSumHeader.height = self.horzSumHeaderHeight;
                            self.horizontalSumHeader.width = $detailHeader.style.width;
                            self.horizontalSumHeader.overflow = "hidden";
                            self.horizontalSumHeader.isHeader = true;
                            $sumHeaderWrapper = render.createWrapper(headerTop + "px", sumPosLeft, self.horizontalSumHeader);
                            table.owner.headers.push($sumHeaderWrapper);
                            $sumHeaderWrapper.classList.add(HEADER);
                            self.$container.appendChild($sumHeaderWrapper);
                            render.process($sumHeaderWrapper, self.horizontalSumHeader);
                        }
                        if (self.horizontalSumContent) {
                            self.horizontalSumContent.rowHeight = self.horzSumBodyRowHeight;
                            self.horizontalSumContent.height = parseInt(self.horzSumBodyHeight) + helper.getScrollWidth() + "px";
                            var detailOverflow = $detailContent.style.overflow;
                            var horzSumWidth = parseFloat($detailContent.style.width)
                                + ((!uk.util.isNullOrUndefined(detailOverflow) && !_.isEmpty(detailOverflow) && detailOverflow !== "hidden")
                                    ? 0 : helper.getScrollWidth());
                            self.horizontalSumContent.width = horzSumWidth + "px";
                            $sumContentWrapper = render.createWrapper(bodyTop, sumPosLeft, self.horizontalSumContent);
                            table.owner.bodies.push($sumContentWrapper);
                            self.horizontalSumContent.overflow = "scroll";
                            self.$container.appendChild($sumContentWrapper);
                            render.process($sumContentWrapper, self.horizontalSumContent);
                            scroll.syncHorizontalScroll($leftSumHeaderWrapper, $leftSumContentWrapper);
                            scroll.syncDoubDirVerticalScrolls([$leftSumContentWrapper, $sumContentWrapper]);
                            scroll.bindVertWheel($sumContentWrapper, true);
                        }
                        if (self.$commander) {
                            self.$commander.addXEventListener(events.MOUSEIN_COLUMN, function (evt) {
                                var colIndex = evt.detail;
                                helper.highlightColumn(self.$container, colIndex);
                            });
                            self.$commander.addXEventListener(events.MOUSEOUT_COLUMN, function (evt) {
                                var colIndex = evt.detail;
                                helper.unHighlightColumn(self.$container, colIndex);
                            });
                            var pHorzHeader = self.$commander.querySelector("." + HEADER_PRF + HORIZONTAL_SUM);
                            var pHorzBody = self.$commander.querySelector("." + BODY_PRF + HORIZONTAL_SUM);
                            var cmdTbls = Array.prototype.slice.call(self.$commander.querySelectorAll("div[class*='" + DETAIL + "']"));
                            var stream = _.concat(cmdTbls, pHorzHeader, pHorzBody, $detailHeader, $detailContent, $sumHeaderWrapper, $sumContentWrapper);
                            scroll.syncDoubDirHorizontalScrolls(stream);
                        }
                        else if (self.$follower) {
                        }
                        else {
                            scroll.syncDoubDirHorizontalScrolls([$detailHeader, $detailContent, $sumHeaderWrapper, $sumContentWrapper]);
                        }
                    };
                    ExTable.prototype.setupCrudArea = function () {
                        var self = this;
                        var updateF = feature.find(self.features, feature.UPDATING);
                        if (updateF) {
                            var $area = document.createElement("div");
                            $area.className = NAMESPACE + "-" + CRUD;
                            var $rowAdd = document.createElement("button");
                            $rowAdd.className = NAMESPACE + "-" + ADD_ROW;
                            $rowAdd.addXEventListener(events.CLICK_EVT, function () {
                                update.insertNewRow(self.$container);
                            });
                            $area.appendChild($rowAdd);
                            var $rowDel = document.createElement("button");
                            $rowDel.className = NAMESPACE + "-" + DEL_ROWS;
                            $rowDel.addXEventListener(events.CLICK_EVT, function () {
                                update.deleteRows(self.$container);
                            });
                            $area.appendChild($rowDel);
                            var dftRowAddTxt = "新規行の追加";
                            var dftRowDelTxt = "行の削除";
                            if (updateF.addNew) {
                                $rowAdd.classList.add(updateF.addNew.buttonClass || "proceed");
                                $rowAdd.textContent = updateF.addNew.buttonText || dftRowAddTxt;
                            }
                            else {
                                $rowAdd.classList.add("proceed");
                                $rowAdd.textContent = dftRowAddTxt;
                            }
                            if (updateF.delete) {
                                $rowDel.classList.add(updateF.delete.buttonClass || "danger");
                                $rowDel.textContent = updateF.delete.buttonText || dftRowDelTxt;
                            }
                            else {
                                $rowDel.classList.add("danger");
                                $rowDel.textContent = dftRowDelTxt;
                            }
                            self.$container.insertAdjacentElement("beforebegin", $area);
                        }
                    };
                    ExTable.prototype.generalSettings = function (headerWrappers, bodyWrappers) {
                        var self = this;
                        self.$container.addXEventListener(events.BODY_HEIGHT_CHANGED, resize.onBodyHeightChanged.bind(self));
                        resize.fitWindowWidth(self.$container);
                        window.addXEventListener(events.RESIZE, resize.fitWindowWidth.bind(self, self.$container));
                        var horzSumExists = !uk.util.isNullOrUndefined(self.horizontalSumHeader);
                        if (self.bodyHeightSetMode === DYNAMIC) {
                            resize.fitWindowHeight(self.$container, bodyWrappers, horzSumExists);
                            window.addXEventListener(events.RESIZE, resize.fitWindowHeight.bind(self, self.$container, bodyWrappers, horzSumExists));
                        }
                        else {
                            var cHeight_1 = 0;
                            var stream = self.$container.querySelectorAll("div[class*='" + DETAIL + "'], div[class*='" + LEFT_HORZ_SUM + "']");
                            Array.prototype.slice.call(stream).forEach(function (e) {
                                cHeight_1 += e.style.height;
                            });
                            if (stream.length === 4) {
                                cHeight_1 += (SPACE + DISTANCE);
                            }
                            self.$container.style.height = (cHeight_1 + SPACE) + "px";
                        }
                        if (self.$follower) {
                            self.$follower.addXEventListener(events.COMPLETED, function () {
                                if (self.areaResize) {
                                    new resize.AreaAdjuster(self.$container, headerWrappers, bodyWrappers, self.$follower).handle();
                                    self.$container.addXEventListener(events.AREA_RESIZE_END, resize.onAreaComplete.bind(self));
                                }
                                var formerWidth = 0, latterWidth = 0;
                                _.forEach(headerWrappers, function (header) {
                                    if (header.classList.contains(HEADER_PRF + LEFTMOST)) {
                                        formerWidth += parseFloat(header.style.width);
                                    }
                                    else if (header.classList.contains(HEADER_PRF + MIDDLE)) {
                                        formerWidth += parseFloat(header.style.width) + DISTANCE;
                                    }
                                    else if (header.classList.contains(HEADER_PRF + DETAIL)) {
                                        latterWidth += parseFloat(header.style.width);
                                    }
                                });
                                var $lm = self.$follower.querySelectorAll("div[class*='" + LEFTMOST + "']");
                                var diff = formerWidth - parseInt($lm[0].style.width);
                                for (var i = 0; i < $lm.length; i++) {
                                    $lm[i].style.width = formerWidth + "px";
                                }
                                var $depDetailHeader = self.$follower.querySelector("." + HEADER_PRF + DETAIL);
                                $depDetailHeader.style.width = latterWidth + "px";
                                var $depDetail = self.$follower.querySelector("." + BODY_PRF + DETAIL);
                                var left = parseInt($depDetail.style.left) + diff;
                                $depDetailHeader.style.left = left + "px";
                                $depDetail.style.left = left + "px";
                                $depDetail.style.width = (latterWidth + helper.getScrollWidth()) + "px";
                                var depLmHeader = _.filter($lm, function (e) {
                                    return e.classList.contains(HEADER_PRF + LEFTMOST);
                                });
                                resize.saveSizes(self.$follower, depLmHeader[0], $depDetailHeader, formerWidth, latterWidth);
                            });
                        }
                        else if (self.areaResize) {
                            new resize.AreaAdjuster(self.$container, headerWrappers, bodyWrappers, self.$follower).handle();
                            self.$container.addXEventListener(events.AREA_RESIZE_END, resize.onAreaComplete.bind(self));
                        }
                        if (self.remainSizes) {
                            storage.area.init(self.$container, headerWrappers);
                            storage.tableHeight.init(self.$container);
                        }
                        update.editDone(self.$container);
                        document.addXEventListener(events.CLICK_EVT, function (evt) {
                            update.outsideClick(self.$container, evt.target);
                        });
                        events.onModify(self.$container);
                        selection.checkUp(self.$container);
                        copy.on(self.$container.querySelector("." + BODY_PRF + DETAIL), self.updateMode);
                        self.$container.addXEventListener(events.OCCUPY_UPDATE, function (evt) {
                            if (self.bodyHeightSetMode === FIXED)
                                return;
                            var reserve = evt.detail;
                            if (reserve && reserve.x) {
                                $.data(self.$container, internal.X_OCCUPY, reserve.x);
                                resize.fitWindowWidth(self.$container);
                            }
                            if (reserve && reserve.y) {
                                $.data(self.$container, internal.Y_OCCUPY, reserve.y);
                                resize.fitWindowHeight(self.$container, bodyWrappers, horzSumExists);
                            }
                        });
                        var containerWidth = headerWrappers.map(function (h) { return parseFloat(h.style.width); }).reduce(function (a, v) { return a + v; });
                        self.$container.style.width = (containerWidth + 34) + "px";
                        if (self.$commander) {
                            events.trigger(self.$container, events.COMPLETED);
                        }
                    };
                    ExTable.prototype.satisfyPrebuild = function () {
                        if (uk.util.isNullOrUndefined(this.$container) || uk.util.isNullOrUndefined(this.headerHeight)
                            || uk.util.isNullOrUndefined(this.bodyHeight) || uk.util.isNullOrUndefined(this.bodyRowHeight)
                            || uk.util.isNullOrUndefined(this.horzSumBodyRowHeight))
                            return false;
                        return true;
                    };
                    ExTable.prototype.setWrapperWidth = function (options, widthParts) {
                        if (!widthParts)
                            return;
                        var width = widthParts[options.containerClass];
                        if (!uk.util.isNullOrUndefined(width)) {
                            options.width = width + "px";
                        }
                    };
                    return ExTable;
                }());
                exTable_1.ExTable = ExTable;
                var render;
                (function (render) {
                    render.HIGHLIGHT_CLS = "highlight";
                    render.CHILD_CELL_CLS = "child-cell";
                    render.COL_ICON_CLS = "column-icon";
                    function process($container, options, isUpdate) {
                        var levelStruct = synthesizeHeaders(options);
                        options.levelStruct = levelStruct;
                        if (isUpdate && !uk.util.isNullOrUndefined($container.style.maxWidth) && !_.isEmpty($container.style.maxWidth)) {
                            var maxWidth = calcWidth(options.columns);
                            if (!options.isHeader && options.overflow === "scroll") {
                                $container.style.maxWidth = (maxWidth + helper.getScrollWidth()) + "px";
                            }
                            else {
                                $container.style.maxWidth = maxWidth + "px";
                            }
                        }
                        if (options.isHeader) {
                            if (Object.keys(levelStruct).length > 1) {
                                groupHeader($container, options, isUpdate);
                                return;
                            }
                        }
                        else {
                            options.float = options.float === false ? false : true;
                        }
                        table($container, options, isUpdate);
                    }
                    render.process = process;
                    function groupHeader($container, options, isUpdate) {
                        var $table = selector.create("table").html("<tbody></tbody>").addClass(options.tableClass)
                            .css({ position: "relative", "table-layout": "fixed", width: "100%", "border-collapse": "separate" }).getSingle();
                        $container.appendChild($table);
                        var $tbody = $table.getElementsByTagName("tbody")[0];
                        if (!isUpdate) {
                            $container.style.height = options.height;
                            $container.style.width = options.width;
                        }
                        if (!uk.util.isNullOrUndefined(options.overflow))
                            $container.style.overflow = options.overflow;
                        else if (!uk.util.isNullOrUndefined(options.overflowX) && !uk.util.isNullOrUndefined(options.overflowY)) {
                            $container.style.overflowX = options.overflowX;
                            $container.style.overflowY = options.overflowY;
                        }
                        var $colGroup = document.createElement("colgroup");
                        $table.insertBefore($colGroup, $tbody);
                        generateColGroup($colGroup, options.columns);
                        var painter = new GroupHeaderPainter(options);
                        painter.rows($tbody);
                    }
                    function generateColGroup($colGroup, columns) {
                        _.forEach(columns, function (col) {
                            if (!uk.util.isNullOrUndefined(col.group)) {
                                generateColGroup($colGroup, col.group);
                                return;
                            }
                            var $col = document.createElement("col");
                            $col.style.width = col.width;
                            $colGroup.appendChild($col);
                            if (col.visible === false)
                                $col.style.display = "none";
                        });
                    }
                    function table($container, options, isUpdate) {
                        var $table = document.createElement("table");
                        $table.innerHTML = "<tbody></tbody>";
                        $table.className = options.tableClass;
                        $table.style.position = "relative";
                        $table.style.tableLayout = "fixed";
                        $table.style.width = "100%";
                        $table.style.borderCollapse = "separate";
                        $container.appendChild($table);
                        var $tbody = $table.getElementsByTagName("tbody")[0];
                        if (!isUpdate) {
                            $container.style.height = options.height;
                            $container.style.width = options.width;
                        }
                        if (!uk.util.isNullOrUndefined(options.overflow))
                            $container.style.overflow = options.overflow;
                        else if (!uk.util.isNullOrUndefined(options.overflowX) && !uk.util.isNullOrUndefined(options.overflowY)) {
                            $container.style.overflowX = options.overflowX;
                            $container.style.overflowY = options.overflowY;
                        }
                        var $colGroup = document.createElement("colgroup");
                        $table.insertBefore($colGroup, $tbody);
                        generateColGroup($colGroup, options.columns);
                        var dataSource;
                        if (!uk.util.isNullOrUndefined(options.dataSource)) {
                            dataSource = options.dataSource;
                        }
                        else {
                            var item_1 = {};
                            _.forEach(options.columns, function (col) {
                                item_1[col.key] = col.headerText;
                            });
                            dataSource = [item_1];
                        }
                        begin($container, dataSource, options);
                    }
                    render.table = table;
                    function begin($container, dataSource, options) {
                        if (options.float) {
                            var cloud = new intan.Cloud($container, dataSource, options);
                            $.data($container, internal.TANGI, cloud);
                            cloud.renderRows(true);
                            return;
                        }
                        normal($container, dataSource, options);
                    }
                    render.begin = begin;
                    function normal($container, dataSource, options) {
                        var rowConfig = { css: { height: options.rowHeight } };
                        var headerRowHeightFt;
                        if (options.isHeader) {
                            headerRowHeightFt = feature.find(options.features, feature.HEADER_ROW_HEIGHT);
                        }
                        var painter = new Painter($container, options);
                        $.data($container, internal.CANON, { _origDs: _.cloneDeep(dataSource), dataSource: dataSource, primaryKey: options.primaryKey, painter: painter });
                        var $tbody = $container.querySelector("tbody");
                        _.forEach(dataSource, function (item, index) {
                            if (!uk.util.isNullOrUndefined(headerRowHeightFt)) {
                                rowConfig = { css: { height: headerRowHeightFt.rows[index] } };
                            }
                            $tbody.appendChild(painter.row(item, rowConfig, index));
                        });
                    }
                    render.normal = normal;
                    function synthesizeHeaders(options) {
                        var level = {};
                        peelStruct(options.columns, level, 0);
                        var rowCount = Object.keys(level).length;
                        if (rowCount > 1) {
                            _.forEach(Object.keys(level), function (key) {
                                _.forEach(level[key], function (col) {
                                    if (uk.util.isNullOrUndefined(col.colspan)) {
                                        col.rowspan = rowCount - parseInt(key);
                                    }
                                });
                            });
                        }
                        return level;
                    }
                    render.synthesizeHeaders = synthesizeHeaders;
                    function peelStruct(columns, level, currentLevel) {
                        var colspan = 0, noGroup = 0;
                        _.forEach(columns, function (col) {
                            var clonedCol = _.clone(col);
                            var colCount = 0;
                            if (!uk.util.isNullOrUndefined(col.group)) {
                                colCount = col.group.length;
                                noGroup++;
                                var ret = peelStruct(col.group, level, currentLevel + 1);
                                if (!uk.util.isNullOrUndefined(ret)) {
                                    colCount += ret;
                                }
                                clonedCol.colspan = colCount;
                            }
                            if (uk.util.isNullOrUndefined(level[currentLevel])) {
                                level[currentLevel] = [];
                            }
                            level[currentLevel].push(clonedCol);
                            colspan += colCount;
                        });
                        return colspan !== 0 ? (colspan - noGroup) : undefined;
                    }
                    var Conditional = (function () {
                        function Conditional(options) {
                            this.options = options;
                            var columns = helper.classifyColumns(options);
                            this.visibleColumns = columns.visibleColumns;
                            this.hiddenColumns = columns.hiddenColumns;
                            this.visibleColumnsMap = helper.getColumnsMap(this.visibleColumns);
                            this.hiddenColumnsMap = helper.getColumnsMap(this.hiddenColumns);
                        }
                        return Conditional;
                    }());
                    var Painter = (function (_super) {
                        __extends(Painter, _super);
                        function Painter($container, options) {
                            _super.call(this, options);
                            this.$container = $container;
                            if (!uk.util.isNullOrUndefined(options.levelStruct)) {
                                this.columnsMap = helper.columnsMapFromStruct(options.levelStruct);
                            }
                            else {
                                this.columnsMap = _.groupBy(options.columns, "key");
                            }
                        }
                        Painter.prototype.cell = function (rData, rowIdx, key) {
                            var self = this;
                            var cData = rData[key];
                            var data = cData && _.isObject(cData) && cData.constructor !== Array && _.isFunction(self.options.view) ?
                                helper.viewData(self.options.view, self.options.viewMode, cData) : cData;
                            var column = self.columnsMap[key];
                            if (uk.util.isNullOrUndefined(column))
                                return;
                            var ws = column.css && column.css.whiteSpace ? column.css.whiteSpace : "nowrap";
                            var td = document.createElement("td");
                            $.data(td, internal.VIEW, rowIdx + "-" + key);
                            var tdStyle = "";
                            tdStyle += "; border-width: 1px; overflow: hidden; white-space: "
                                + ws + "; position: relative;";
                            self.highlight(td);
                            if (!self.visibleColumnsMap[key])
                                tdStyle += "; display: none;";
                            if (!uk.util.isNullOrUndefined(data) && data.constructor === Array) {
                                var incellHeight_1 = parseInt(self.options.rowHeight) / 2 - 3;
                                var borderStyle_1 = "solid 1px transparent";
                                _.forEach(data, function (item, idx) {
                                    var divStyle = "";
                                    var div = document.createElement("div");
                                    div.classList.add(render.CHILD_CELL_CLS);
                                    div.innerText = uk.util.isNullOrUndefined(item) ? "" : item;
                                    if (idx < data.length - 1) {
                                        divStyle += "; border-top: " + borderStyle_1 + "; border-left: "
                                            + borderStyle_1 + "; border-right: " + borderStyle_1
                                            + "; border-bottom: dashed 1px #AAB7B8; top: 0px";
                                    }
                                    else {
                                        divStyle += "; border: " + borderStyle_1 + "; top: "
                                            + (incellHeight_1 + 2) + "px";
                                    }
                                    divStyle += "; position: absolute; left: 0px; height: "
                                        + incellHeight_1
                                        + "px; width: 98%; text-align: center;";
                                    div.style.cssText += divStyle;
                                    td.appendChild(div);
                                    if (column.handlerType) {
                                        var handler = cellHandler.get(column.handlerType);
                                        if (handler)
                                            handler(div, self.options, helper.call(column.supplier, rData, rowIdx, key));
                                    }
                                });
                                style.detCell(self.$container, td, rowIdx, key);
                                tdStyle += "; padding: 0px;";
                                td.style.cssText += tdStyle;
                                if (self.options.overflowTooltipOn)
                                    widget.textOverflow(td);
                                return td;
                            }
                            if (!uk.util.isNullOrUndefined(column.handlerType) && !self.options.isHeader) {
                                var handler = cellHandler.get(column.handlerType);
                                if (!uk.util.isNullOrUndefined(handler)) {
                                    handler(td, self.options, helper.call(column.supplier, rData, rowIdx, key));
                                }
                            }
                            if (self.options.isHeader) {
                                if (!uk.util.isNullOrUndefined(column.icon) && column.icon.for === "header") {
                                    var icon = document.createElement("span");
                                    icon.className = render.COL_ICON_CLS + " " + column.icon.class;
                                    tdStyle += "; padding-left: " + column.icon.width + ";";
                                    td.appendChild(icon);
                                    if (column.icon.popup && typeof column.icon.popup === "function") {
                                        icon.style.cursor = "pointer";
                                        new widget.PopupPanel(icon, column.icon.popup, "bottom right");
                                    }
                                    var innerDiv = document.createElement("div");
                                    innerDiv.innerHTML = data;
                                    td.appendChild(innerDiv);
                                }
                                else if (!column.headerControl && helper.containsBr(data)) {
                                    td.innerHTML = data;
                                }
                                else if (!column.headerControl) {
                                    td.innerText = data;
                                }
                                controls.checkHeader(td, column, data, column.headerHandler);
                            }
                            else if (!self.options.isHeader) {
                                if (!uk.util.isNullOrUndefined(column.icon) && column.icon.for === "body") {
                                    var icon = document.createElement("span");
                                    icon.className = render.COL_ICON_CLS + " " + column.icon.class;
                                    tdStyle += "; padding-left: " + column.icon.width + ";";
                                    td.appendChild(icon);
                                }
                                else if (!column.control) {
                                    tdStyle += " text-overflow: ellipsis; -ms-text-overflow: ellipsis;";
                                    td.innerText = data;
                                }
                                controls.check(td, column, data, helper.call(column.handler, rData, rowIdx, key));
                            }
                            style.detCell(self.$container, td, rowIdx, key);
                            td.style.cssText += tdStyle;
                            if (self.options.overflowTooltipOn)
                                widget.textOverflow(td);
                            return td;
                        };
                        Painter.prototype.row = function (data, config, rowIdx) {
                            var self = this;
                            var tr = document.createElement("tr");
                            tr.style.height = parseInt(config.css.height) + "px";
                            var headerCellStyleFt, headerPopupFt, bodyCellStyleFt;
                            if (self.options.isHeader) {
                                headerCellStyleFt = feature.find(self.options.features, feature.HEADER_CELL_STYLE);
                                headerPopupFt = feature.find(self.options.features, feature.HEADER_POP_UP);
                            }
                            else {
                                bodyCellStyleFt = feature.find(self.options.features, feature.BODY_CELL_STYLE);
                            }
                            var onChecked = function (checked, rowIndex) {
                                var $grid = self.options.isHeader ? selector.classSiblings(self.$container, BODY_PRF + LEFTMOST)[0]
                                    : self.$container;
                                controls.tick(checked, $grid, self.options.isHeader, rowIndex);
                            };
                            if (!data[controls.CHECKED_KEY] && self.options.columns[0].key === controls.CHECKED_KEY) {
                                var td = document.createElement("td");
                                $.data(td, internal.VIEW, rowIdx + "-" + controls.CHECKED_KEY);
                                td.style.padding = "1px 1px";
                                td.style.textAlign = "center";
                                td.appendChild(controls.createCheckBox(self.$container, { initValue: false, onChecked: onChecked }));
                                tr.appendChild(td);
                            }
                            _.forEach(Object.keys(data), function (key, index) {
                                if (!self.visibleColumnsMap[key] && !self.hiddenColumnsMap[key])
                                    return;
                                if (key === controls.CHECKED_KEY) {
                                    var td = document.createElement("td");
                                    td.style.padding = "1px 1px";
                                    td.style.textAlign = "center";
                                    td.appendChild(controls.createCheckBox(self.$container, { initValue: false, onChecked: onChecked }));
                                    tr.appendChild(td);
                                    return;
                                }
                                var cell = self.cell(data, rowIdx, key);
                                tr.appendChild(cell);
                                if (!uk.util.isNullOrUndefined(headerCellStyleFt)) {
                                    _.forEach(headerCellStyleFt.decorator, function (colorDef) {
                                        if (key === colorDef.columnKey) {
                                            if ((!uk.util.isNullOrUndefined(colorDef.rowId) && colorDef.rowId === rowIdx)
                                                || uk.util.isNullOrUndefined(colorDef.rowId)) {
                                                helper.addClassList(cell, colorDef.clazz);
                                                return false;
                                            }
                                        }
                                    });
                                }
                                else if (!uk.util.isNullOrUndefined(bodyCellStyleFt)) {
                                    _.forEach(bodyCellStyleFt.decorator, function (colorDef) {
                                        if (key === colorDef.columnKey && data[self.options.primaryKey] === colorDef.rowId) {
                                            var childCells = cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                                            if (!uk.util.isNullOrUndefined(colorDef.innerIdx) && childCells.length > 0) {
                                                var child = childCells[colorDef.innerIdx];
                                                helper.addClassList(child, colorDef.clazz);
                                                if (colorDef.clazz === style.HIDDEN_CLS) {
                                                    $.data(child, "hide", child.textContent);
                                                    child.innerHTML = "";
                                                }
                                            }
                                            else {
                                                helper.addClassList(cell, colorDef.clazz);
                                                if (colorDef.clazz == style.HIDDEN_CLS) {
                                                    $.data(cell, "hide", cell.innerText);
                                                    cell.innerText = "";
                                                }
                                                return false;
                                            }
                                        }
                                    });
                                }
                                if (uk.util.isNullOrUndefined(self.columnsMap[key]))
                                    return;
                                var cellStyle = self.columnsMap[key].style;
                                if (!uk.util.isNullOrUndefined(cellStyle)) {
                                    cellStyle(new style.CellStyleParam($cell, data[key], data, rowIdx, key));
                                }
                            });
                            widget.bind(tr, rowIdx, headerPopupFt);
                            style.detColumn(self.$container, tr, rowIdx);
                            spread.bindRowSticker(tr, rowIdx, self.options);
                            if (!self.options.isHeader) {
                                cellHandler.rRowClick(tr, self.columnsMap, { rData: data, rowIdx: rowIdx });
                            }
                            return tr;
                        };
                        Painter.prototype.highlight = function (td) {
                            var self = this;
                            if (self.options.isHeader || self.options.containerClass !== BODY_PRF + DETAIL || self.options.highlight === false)
                                return;
                            var $targetContainer = self.$container;
                            var targetHeader = helper.firstSibling($targetContainer, self.options.containerClass.replace(BODY_PRF, HEADER_PRF));
                            var extable = helper.getExTableFromGrid($targetContainer);
                            var horzSumHeader, horzSumContent;
                            td.addXEventListener(events.MOUSE_OVER, function () {
                                var colIndex = helper.indexInParent(td);
                                var tr = helper.closest(td, "tr");
                                var rowIndex = helper.indexInParent(tr);
                                helper.addClass1n(tr.children, render.HIGHLIGHT_CLS);
                                if (!horzSumHeader || !horzSumContent) {
                                    horzSumHeader = extable.owner.find(HEADER_PRF + HORIZONTAL_SUM, "headers");
                                    if (horzSumHeader)
                                        horzSumHeader = horzSumHeader[0];
                                    horzSumContent = extable.owner.find(BODY_PRF + HORIZONTAL_SUM, "bodies");
                                    if (horzSumContent)
                                        horzSumContent = horzSumContent[0];
                                }
                                var bodies = extable.owner.bodies;
                                for (var i = 0; i < bodies.length; i++) {
                                    if (!helper.hasClass(bodies[i], BODY_PRF + LEFT_HORZ_SUM)
                                        && !helper.hasClass(bodies[i], BODY_PRF + HORIZONTAL_SUM)) {
                                        var rowElm = bodies[i].getElementsByTagName("tr")[rowIndex];
                                        if (rowElm) {
                                            helper.addClass1n(rowElm.getElementsByTagName("td"), render.HIGHLIGHT_CLS);
                                        }
                                    }
                                }
                                helper.consumeSiblings(tr, function (elm) {
                                    var tds = elm.getElementsByTagName("td");
                                    if (!tds || tds.length === 0)
                                        return;
                                    helper.addClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                });
                                _.forEach(targetHeader.getElementsByTagName("tr"), function (t) {
                                    var tds = t.getElementsByTagName("td");
                                    if (!tds || tds.length === 0)
                                        return;
                                    helper.addClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                });
                                if (horzSumHeader && horzSumHeader.style.display !== "none") {
                                    _.forEach(horzSumHeader.getElementsByTagName("tr"), function (t) {
                                        var tds = t.getElementsByTagName("td");
                                        if (!tds || tds.length === 0)
                                            return;
                                        helper.addClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                    });
                                    _.forEach(horzSumContent.getElementsByTagName("tr"), function (t) {
                                        var tds = t.getElementsByTagName("td");
                                        if (!tds || tds.length === 0)
                                            return;
                                        helper.addClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                    });
                                }
                                $.data(self.$container, internal.COLUMN_IN, colIndex);
                                events.trigger(helper.closest(self.$container, "." + NAMESPACE), events.MOUSEIN_COLUMN, colIndex);
                            });
                            td.addXEventListener(events.MOUSE_OUT, function () {
                                helper.removeClass1n(td, render.HIGHLIGHT_CLS);
                                var colIndex = helper.indexInParent(td);
                                var tr = helper.closest(td, "tr");
                                var rowIndex = helper.indexInParent(tr);
                                helper.removeClass1n(tr.children, render.HIGHLIGHT_CLS);
                                if (!horzSumHeader || !horzSumContent) {
                                    horzSumHeader = extable.owner.find(HEADER_PRF + HORIZONTAL_SUM, "headers");
                                    if (horzSumHeader)
                                        horzSumHeader = horzSumHeader[0];
                                    horzSumContent = extable.owner.find(BODY_PRF + HORIZONTAL_SUM, "bodies");
                                    if (horzSumContent)
                                        horzSumContent = horzSumContent[0];
                                }
                                var bodies = extable.owner.bodies;
                                for (var i = 0; i < bodies.length; i++) {
                                    if (!helper.hasClass(bodies[i], BODY_PRF + LEFT_HORZ_SUM)
                                        && !helper.hasClass(bodies[i], BODY_PRF + HORIZONTAL_SUM)) {
                                        var rowElm = bodies[i].getElementsByTagName("tr")[rowIndex];
                                        if (rowElm) {
                                            helper.removeClass1n(rowElm.getElementsByTagName("td"), render.HIGHLIGHT_CLS);
                                        }
                                    }
                                }
                                helper.consumeSiblings(tr, function (elm) {
                                    var tds = elm.getElementsByTagName("td");
                                    if (!tds || tds.length === 0)
                                        return;
                                    helper.removeClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                });
                                _.forEach(targetHeader.getElementsByTagName("tr"), function (t) {
                                    var tds = t.getElementsByTagName("td");
                                    if (!tds || tds.length === 0)
                                        return;
                                    helper.removeClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                });
                                if (horzSumHeader && horzSumHeader.style.display !== "none") {
                                    _.forEach(horzSumHeader.getElementsByTagName("tr"), function (t) {
                                        var tds = t.getElementsByTagName("td");
                                        if (!tds || tds.length === 0)
                                            return;
                                        helper.removeClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                    });
                                    _.forEach(horzSumContent.getElementsByTagName("tr"), function (t) {
                                        var tds = t.getElementsByTagName("td");
                                        if (!tds || tds.length === 0)
                                            return;
                                        helper.removeClass1n(tds[colIndex], render.HIGHLIGHT_CLS);
                                    });
                                }
                                $.data(self.$container, internal.COLUMN_IN, -1);
                                events.trigger(helper.closest(self.$container, "." + NAMESPACE), events.MOUSEOUT_COLUMN, colIndex);
                            });
                        };
                        return Painter;
                    }(Conditional));
                    render.Painter = Painter;
                    var GroupHeaderPainter = (function (_super) {
                        __extends(GroupHeaderPainter, _super);
                        function GroupHeaderPainter(options) {
                            _super.call(this, options);
                            this.levelStruct = options.levelStruct;
                            this.columnsMap = helper.columnsMapFromStruct(this.levelStruct);
                        }
                        GroupHeaderPainter.prototype.cell = function (text, rowIdx, cell) {
                            var self = this;
                            var $td = document.createElement("td");
                            $.data($td, internal.VIEW, rowIdx + "-" + cell.key);
                            var tdStyle = "; border-width: 1px; overflow: hidden; white-space: nowrap; border-collapse: collapse;";
                            if (!uk.util.isNullOrUndefined(cell.rowspan) && cell.rowspan > 1)
                                $td.setAttribute("rowspan", cell.rowspan);
                            if (!uk.util.isNullOrUndefined(cell.colspan) && cell.colspan > 1)
                                $td.setAttribute("colspan", cell.colspan);
                            else if (uk.util.isNullOrUndefined(cell.colspan) && !self.visibleColumnsMap[cell.key])
                                tdStyle += "; display: none;";
                            var column = self.columnsMap[cell.key];
                            if (!uk.util.isNullOrUndefined(cell.icon) && cell.icon.for === "header") {
                                var $icon = document.createElement("span");
                                $icon.className = render.COL_ICON_CLS + " " + cell.icon.class;
                                $icon.style.top = "20%";
                                tdStyle += "; padding-left: " + cell.icon.width + ";";
                                $td.appendChild($icon);
                                if (cell.icon.popup && typeof cell.icon.popup === "function") {
                                    $icon.style.cursor = "pointer";
                                    new widget.PopupPanel($icon, cell.icon.popup, "bottom right");
                                }
                                var $content = document.createElement("div");
                                $content.innerHTML = text;
                                $td.appendChild($content);
                            }
                            else if ((!column || (column && !column.headerControl)) && helper.containsBr(text)) {
                                $td.innerHTML = text;
                            }
                            else if (!column || (column && !column.headerControl)) {
                                $td.textContent = text;
                            }
                            if (column) {
                                controls.checkHeader($td, column, text, column.headerHandler);
                            }
                            $td.style.cssText += tdStyle;
                            return $td;
                        };
                        GroupHeaderPainter.prototype.rows = function ($tbody) {
                            var self = this;
                            var height = self.options.rowHeight;
                            var headerRowHeightFt = feature.find(self.options.features, feature.HEADER_ROW_HEIGHT);
                            var headerCellStyleFt = feature.find(self.options.features, feature.HEADER_CELL_STYLE);
                            _.forEach(Object.keys(self.levelStruct), function (rowIdx) {
                                if (!uk.util.isNullOrUndefined(headerRowHeightFt)) {
                                    height = headerRowHeightFt.rows[rowIdx];
                                }
                                var $tr = document.createElement("tr");
                                $tr.style.height = height;
                                var oneLevel = self.levelStruct[rowIdx];
                                _.forEach(oneLevel, function (cell) {
                                    if (!self.visibleColumnsMap[cell.key] && !self.hiddenColumnsMap[cell.key]
                                        && uk.util.isNullOrUndefined(cell.colspan))
                                        return;
                                    var $cell = self.cell(cell.headerText, rowIdx, cell);
                                    $tr.appendChild($cell);
                                    if (!uk.util.isNullOrUndefined(headerCellStyleFt)) {
                                        _.forEach(headerCellStyleFt.decorator, function (colorDef) {
                                            if (colorDef.columnKey === cell.key) {
                                                if ((!uk.util.isNullOrUndefined(colorDef.rowId) && colorDef.rowId === rowIdx)
                                                    || uk.util.isNullOrUndefined(colorDef.rowId)) {
                                                    helper.addClassList($cell, colorDef.clazz);
                                                }
                                                return false;
                                            }
                                        });
                                    }
                                    if (uk.util.isNullOrUndefined(self.columnsMap[cell.key]))
                                        return;
                                    var cellStyle = self.columnsMap[cell.key].style;
                                    if (!uk.util.isNullOrUndefined(cellStyle)) {
                                        cellStyle(new style.CellStyleParam($cell, cell.headerText, undefined, rowIdx, cell.key));
                                    }
                                });
                                $tbody.appendChild($tr);
                            });
                        };
                        return GroupHeaderPainter;
                    }(Conditional));
                    render.GroupHeaderPainter = GroupHeaderPainter;
                    function extra(className, height) {
                        var element = document.createElement("tr");
                        element.style.height = height + "px";
                        helper.addClass(element, "extable-" + className);
                        return element;
                    }
                    render.extra = extra;
                    function wrapperStyles(top, left, width, height, maxWidth) {
                        var style = {
                            position: "absolute",
                            overflow: "hidden",
                            top: top,
                            left: left,
                            width: width,
                            height: height,
                            border: "solid 1px #CCC"
                        };
                        if (maxWidth) {
                            style.maxWidth = maxWidth;
                            if (parseFloat(maxWidth) < parseFloat(width))
                                style.width = maxWidth;
                        }
                        return style;
                    }
                    render.wrapperStyles = wrapperStyles;
                    function createWrapper(top, left, options) {
                        var style;
                        if (options.containerClass === HEADER_PRF + DETAIL
                            || options.containerClass === BODY_PRF + DETAIL) {
                            var maxWidth = calcWidth(options.columns);
                            style = wrapperStyles(top, left, options.width, options.height, maxWidth + "px");
                        }
                        else {
                            style = wrapperStyles(top, left, options.width, options.height);
                        }
                        return selector.create("div").data(internal.EX_PART, options.containerClass)
                            .addClass(options.containerClass)
                            .css(style).getSingle();
                    }
                    render.createWrapper = createWrapper;
                    function calcWidth(columns) {
                        var width = 0;
                        columns.forEach(function (c, i) {
                            if (c.group) {
                                width += calcWidth(c.group);
                                return;
                            }
                            width += parseFloat(c.width);
                        });
                        return width;
                    }
                    render.calcWidth = calcWidth;
                    function gridCell($grid, rowIdx, columnKey, innerIdx, valueObj, styleMaker) {
                        var $exTable = helper.closest($grid, "." + NAMESPACE);
                        var x = helper.getExTableFromGrid($grid);
                        var updateMode = x.updateMode;
                        var $cell = selection.cellAt($grid, rowIdx, columnKey);
                        if ($cell === intan.NULL)
                            return;
                        var origDs = helper.getOrigDS($grid);
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var viewFn = gen.painter.options.view;
                        var viewMode = gen.painter.options.viewMode;
                        var value = valueObj;
                        var fields = gen.painter.options.fields;
                        if (_.isFunction(viewFn)) {
                            value = helper.viewData(viewFn, viewMode, valueObj);
                        }
                        var touched;
                        if (styleMaker && _.isFunction(styleMaker)) {
                            var style_1 = styleMaker(rowIdx, columnKey, innerIdx, valueObj);
                            if (style_1) {
                                if (style_1.class)
                                    helper.addClass($cell, style_1.class);
                                else
                                    $cell.style.color = style_1.textColor;
                                makeUp($grid, rowIdx, columnKey, style_1);
                            }
                        }
                        var $childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        if ($childCells.length > 0) {
                            if (value.constructor === Array) {
                                _.forEach(value, function (val, i) {
                                    var $c = $childCells[i];
                                    $c.textContent = val;
                                    var cellObj = new selection.Cell(rowIdx, columnKey, valueObj, i);
                                    var mTouch = trace(origDs, $c, cellObj, fields, x.manipulatorId, x.manipulatorKey);
                                    if (!touched || !touched.dirty)
                                        touched = mTouch;
                                    if (updateMode === EDIT) {
                                        validation.validate($grid, $c, rowIdx, columnKey, i, val);
                                    }
                                });
                            }
                            else {
                                var $c = $childCells[innerIdx];
                                $c.textContent = value;
                                var cellObj = new selection.Cell(rowIdx, columnKey, valueObj, innerIdx);
                                touched = trace(origDs, $c, cellObj, fields, x.manipulatorId, x.manipulatorKey);
                                if (updateMode === EDIT) {
                                    validation.validate($grid, $c, rowIdx, columnKey, innerIdx, value);
                                }
                            }
                        }
                        else {
                            $cell.textContent = value;
                            var cellObj = new selection.Cell(rowIdx, columnKey, valueObj, -1);
                            touched = trace(origDs, $cell, cellObj, fields, x.manipulatorId, x.manipulatorKey);
                            if (updateMode === EDIT) {
                                validation.validate($grid, $cell, rowIdx, columnKey, -1, value);
                            }
                        }
                        return touched;
                    }
                    render.gridCell = gridCell;
                    function gridRow($grid, rowIdx, data, styleMaker) {
                        var $exTable = helper.closest($grid, "." + NAMESPACE);
                        var x = helper.getExTableFromGrid($grid);
                        var updateMode = x.updateMode;
                        var $row = selection.rowAt($grid, rowIdx);
                        var $cells = Array.prototype.slice.call($row.querySelectorAll("td")).filter(function (e) {
                            return e.style.display !== "none";
                        });
                        var visibleColumns = helper.gridVisibleColumns($grid);
                        var origDs = helper.getOrigDS($grid);
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var viewFn = gen.painter.options.view;
                        var viewMode = gen.painter.options.viewMode;
                        var fields = gen.painter.options.fields;
                        var touched;
                        _.forEach(Object.keys(data), function (key) {
                            _.forEach(visibleColumns, function (col, index) {
                                if (col.key === key) {
                                    var cellObj_1;
                                    var $target = $cells[index];
                                    var childCells_1 = $target.querySelectorAll("." + render.CHILD_CELL_CLS);
                                    var cData = data[key];
                                    if (styleMaker && _.isFunction(styleMaker)) {
                                        var style_2 = styleMaker(rowIdx, key, -1, cData);
                                        if (style_2) {
                                            if (style_2.class)
                                                helper.addClass($target, style_2.class);
                                            else
                                                $target.style.color = style_2.textColor;
                                            makeUp($grid, rowIdx, key, style_2);
                                        }
                                    }
                                    if (childCells_1.length > 0) {
                                        if (_.isFunction(viewFn)) {
                                            cData = helper.viewData(viewFn, viewMode, data[key]);
                                        }
                                        if (cData.constructor === Array) {
                                            _.forEach(cData, function (d, i) {
                                                var $c = childCells_1[i];
                                                $c.textContent = d;
                                                if (updateMode === EDIT) {
                                                    validation.validate($exTable, $grid, $c, rowIdx, key, i, d);
                                                }
                                                cellObj_1 = new selection.Cell(rowIdx, key, data[key], i);
                                                touched = trace(origDs, $c, cellObj_1, fields, x.manipulatorId, x.manipulatorKey);
                                            });
                                            return false;
                                        }
                                        childCells_1[1].textContent = data[key];
                                        cellObj_1 = new selection.Cell(rowIdx, key, data[key], 1);
                                        touched = trace(origDs, childCells_1[1], cellObj_1, fields, x.manipulatorId, x.manipulatorKey);
                                        if (updateMode === EDIT) {
                                            validation.validate($exTable, $grid, childCells_1[1], rowIdx, key, 1, data[key]);
                                        }
                                    }
                                    else {
                                        if (_.isFunction(viewFn)) {
                                            cData = helper.viewData(viewFn, viewMode, data[key]);
                                        }
                                        $target.textContent = cData;
                                        cellObj_1 = new selection.Cell(rowIdx, key, data[key], -1);
                                        touched = trace(origDs, $target, cellObj_1, fields, x.manipulatorId, x.manipulatorKey);
                                        if (updateMode === EDIT) {
                                            validation.validate($exTable, $grid, $target, rowIdx, key, -1, data[key]);
                                        }
                                    }
                                    return false;
                                }
                            });
                        });
                        return touched;
                    }
                    render.gridRow = gridRow;
                    function makeUp($grid, rowIdx, key, hypo) {
                        var dCellsStyle = $.data($grid, internal.D_CELLS_STYLE);
                        if (!dCellsStyle) {
                            dCellsStyle = {};
                            dCellsStyle[rowIdx] = [new style.Cell(rowIdx, key, hypo)];
                            $.data($grid, internal.D_CELLS_STYLE, dCellsStyle);
                            return;
                        }
                        var dup;
                        var cells = dCellsStyle[rowIdx];
                        if (cells) {
                            _.forEach(cells, function (c, i) {
                                if (c.columnKey === key) {
                                    c.makeup = hypo;
                                    dup = true;
                                    return false;
                                }
                            });
                            if (!dup) {
                                cells.push(new style.Cell(rowIdx, key, hypo));
                            }
                        }
                        else {
                            dCellsStyle[rowIdx] = [new style.Cell(rowIdx, key, hypo)];
                        }
                    }
                    function trace(ds, $cell, cellObj, fields, manId, manKey) {
                        if (!ds || ds.length === 0)
                            return;
                        var rowObj = ds[cellObj.rowIndex];
                        var oVal = rowObj[cellObj.columnKey];
                        var updCls = update.EDITED_CLS;
                        var updateTarget;
                        if (!uk.util.isNullOrUndefined(manId) && !uk.util.isNullOrUndefined(manKey)) {
                            if (rowObj[manKey] === manId) {
                                updCls = update.TARGET_EDITED_CLS;
                                updateTarget = 1;
                            }
                            else {
                                updCls = update.OTHER_EDITED_CLS;
                                updateTarget = 0;
                            }
                        }
                        if (!uk.util.isNullOrUndefined(oVal) && helper.isEqual(oVal, cellObj.value, fields)) {
                            helper.removeClass($cell, updCls);
                            return { dirty: false, updateTarget: updateTarget };
                        }
                        helper.addClass($cell, updCls);
                        return { dirty: true, updateTarget: updateTarget };
                    }
                })(render || (render = {}));
                var intan;
                (function (intan) {
                    intan.TOP_SPACE = "top-space";
                    intan.BOTTOM_SPACE = "bottom-space";
                    intan.NULL = null;
                    var Cloud = (function () {
                        function Cloud($container, dataSource, options) {
                            this.$container = $container;
                            this.options = options;
                            this.primaryKey = options.primaryKey;
                            this.rowsOfBlock = options.rowsOfBlock || 30;
                            this.blocksOfCluster = options.blocksOfCluster || 3;
                            this.rowHeight = parseInt(options.rowHeight);
                            this.blockHeight = this.rowsOfBlock * this.rowHeight;
                            this.clusterHeight = this.blockHeight * this.blocksOfCluster;
                            this.dataSource = dataSource;
                            this._origDs = _.cloneDeep(dataSource);
                            this.painter = new render.Painter($container, options);
                            this.setCellsStyle();
                            this.onScroll();
                        }
                        Cloud.prototype.setCellsStyle = function () {
                            var self = this;
                            var bodyStylesFt = feature.find(self.options.features, feature.BODY_CELL_STYLE);
                            if (!bodyStylesFt)
                                return;
                            $.data(self.$container, internal.CELLS_STYLE, bodyStylesFt.decorator);
                        };
                        Cloud.prototype.getClusterNo = function () {
                            return Math.floor(this.$container.scrollTop / (this.clusterHeight - this.blockHeight));
                        };
                        Cloud.prototype.renderRows = function (manual) {
                            var self = this;
                            var clusterNo = self.getClusterNo();
                            if (manual)
                                self.currentCluster = clusterNo;
                            if (self.dataSource.length < self.rowsOfBlock) {
                                self.topOffset = 0;
                                self.bottomOffset = 0;
                            }
                            var rowsOfCluster = self.blocksOfCluster * self.rowsOfBlock;
                            var startRowIdx = self.startIndex = Math.max((rowsOfCluster - self.rowsOfBlock) * clusterNo, 0);
                            var endRowIdx = self.endIndex = startRowIdx + rowsOfCluster;
                            self.topOffset = Math.max(startRowIdx * self.rowHeight, 0);
                            self.bottomOffset = Math.max((self.dataSource.length - endRowIdx) * self.rowHeight, 0);
                            var rowConfig = { css: { height: self.rowHeight } };
                            var containerElm = self.$container;
                            var tbody = document.createElement("tbody");
                            tbody.appendChild(render.extra(intan.TOP_SPACE, self.topOffset));
                            for (var i = startRowIdx; i < endRowIdx; i++) {
                                if (uk.util.isNullOrUndefined(this.dataSource[i]))
                                    continue;
                                tbody.appendChild(self.painter.row(this.dataSource[i], rowConfig, i));
                            }
                            tbody.appendChild(render.extra(intan.BOTTOM_SPACE, self.bottomOffset));
                            containerElm.querySelector("table").replaceChild(tbody, containerElm.getElementsByTagName("tbody")[0]);
                            if (self.$container.classList.contains(BODY_PRF + DETAIL)) {
                                self.selectCellsIn();
                                self.dirtyCellsIn();
                                self.errorCellsIn();
                                self.detCellsIn();
                                self.editCellIn();
                                self.madeUpCellsIn();
                            }
                            else if (self.$container.classList.contains(BODY_PRF + LEFTMOST)) {
                                self.selectedRowsIn();
                                self.dirtyCellsIn();
                                self.errorCellsIn();
                                self.editCellIn();
                            }
                            setTimeout(function () {
                                events.trigger(self.$container, events.RENDERED);
                            }, 0);
                        };
                        Cloud.prototype.onScroll = function () {
                            var self = this;
                            self.$container.removeXEventListener(events.SCROLL_EVT + ".detail");
                            self.$container.addXEventListener(events.SCROLL_EVT + ".detail", function () {
                                var inClusterNo = self.getClusterNo();
                                if (self.currentCluster !== inClusterNo) {
                                    self.currentCluster = inClusterNo;
                                    if (self.$container.classList.contains(BODY_PRF + DETAIL)) {
                                        var colIn = $.data(self.$container, internal.COLUMN_IN);
                                        if (!uk.util.isNullOrUndefined(colIn) && colIn !== -1) {
                                            helper.unHighlightGrid(selector.classSiblings(self.$container, HEADER_PRF + DETAIL)[0], colIn);
                                            var $sumHeader = selector.classSiblings(self.$container, HEADER_PRF + HORIZONTAL_SUM);
                                            var $sumBody = selector.classSiblings(self.$container, BODY_PRF + HORIZONTAL_SUM);
                                            if ($sumHeader.length > 0 && $sumHeader[0].style.display !== "none") {
                                                helper.unHighlightGrid($sumHeader[0], colIn);
                                                helper.unHighlightGrid($sumBody[0], colIn);
                                            }
                                            events.trigger(helper.closest(self.$container, "." + NAMESPACE), events.MOUSEOUT_COLUMN, colIn);
                                        }
                                    }
                                    self.renderRows();
                                }
                            });
                        };
                        Cloud.prototype.rollTo = function (cell) {
                            var self = this;
                            if (self.startIndex <= cell.rowIndex && self.endIndex >= cell.rowIndex) {
                                var $cell = selection.cellAt(self.$container, cell.rowIndex, cell.columnKey);
                                var tdIndex = selector.index($cell);
                                var tdPosLeft_1 = 0, tdPosTop_1 = 0;
                                selector.siblingsLt($cell, tdIndex).forEach(function (e) {
                                    if (e.style.display !== "none") {
                                        tdPosLeft_1 += e.offsetWidth;
                                    }
                                });
                                var $tr = $cell.parentElement;
                                var trIndex = selector.index($tr);
                                selector.siblingsLt($tr, trIndex).forEach(function (e) {
                                    tdPosTop_1 += e.offsetHeight;
                                });
                                if ((self.$container.scrollTop + parseFloat(self.$container.style.height)) < (tdPosTop_1 + 100)
                                    || self.$container.scrollTop > tdPosTop_1) {
                                    self.$container.scrollTop = tdPosTop_1;
                                }
                                if ((self.$container.scrollLeft + parseFloat(self.$container.style.width)) < (tdPosLeft_1 + 100)
                                    || self.$container.scrollLeft > tdPosLeft_1) {
                                    self.$container.scrollLeft = tdPosLeft_1;
                                }
                            }
                            else {
                                self.$container.scrollTop = cell.rowIndex * self.rowHeight;
                                var $cell = selection.cellAt(self.$container, cell.rowIndex, cell.columnKey);
                                var tdPosLeft_2 = 0;
                                selector.siblingsLt($cell, selector.index($cell)).forEach(function (e) {
                                    if (e.style.display !== "none") {
                                        tdPosLeft_2 += e.offsetWidth;
                                    }
                                });
                                self.$container.scrollLeft = tdPosLeft_2;
                            }
                        };
                        Cloud.prototype.editCellIn = function () {
                            var self = this;
                            var $exTable = helper.closest(self.$container, "." + NAMESPACE);
                            var updateMode = $.data($exTable, NAMESPACE).updateMode;
                            var editor = $.data($exTable, update.EDITOR);
                            if (updateMode !== EDIT || uk.util.isNullOrUndefined(editor) || editor.land !== self.options.containerClass)
                                return;
                            var editorRowIdx = parseInt(editor.rowIdx);
                            if (uk.util.isNullOrUndefined(editor) || editorRowIdx < self.startIndex || editorRowIdx > self.endIndex)
                                return;
                            var $editRow = self.$container.querySelectorAll("tr")[editorRowIdx - self.startIndex + 1];
                            var editorColumnIdx;
                            _.forEach(self.painter.visibleColumns, function (c, idx) {
                                if (c.key === editor.columnKey) {
                                    editorColumnIdx = idx;
                                    return false;
                                }
                            });
                            if (!uk.util.isNullOrUndefined(editorColumnIdx)) {
                                var $editorCell = Array.prototype.slice.call($editRow.getElementsByTagName("td")).filter(function (e) {
                                    return e.style.display !== "none";
                                })[editorColumnIdx];
                                var $childCells = $editorCell.querySelectorAll("." + render.CHILD_CELL_CLS);
                                update.edit($exTable, !uk.util.isNullOrUndefined(editor.innerIdx)
                                    && editor.innerIdx > -1
                                    && $childCells.length > 0
                                    ? $childCells[editor.innerIdx] : $editorCell, editor.land, editor.value, true);
                            }
                        };
                        Cloud.prototype.selectCellsIn = function () {
                            var self = this;
                            var $exTable = helper.closest(self.$container, "." + NAMESPACE);
                            var updateMode = $.data($exTable, NAMESPACE).updateMode;
                            if (updateMode !== COPY_PASTE)
                                return;
                            var selectedCells = $.data(self.$container, internal.SELECTED_CELLS);
                            if (uk.util.isNullOrUndefined(selectedCells) || selectedCells.length === 0)
                                return;
                            _.forEach(Object.keys(selectedCells), function (rowIdx, index) {
                                if (rowIdx >= self.startIndex && rowIdx <= self.endIndex) {
                                    _.forEach(selectedCells[rowIdx], function (colKey) {
                                        var $cell = selection.cellAt(self.$container, rowIdx, colKey);
                                        if ($cell === intan.NULL || !$cell)
                                            return;
                                        selection.markCell($cell);
                                    });
                                }
                            });
                        };
                        Cloud.prototype.selectedRowsIn = function () {
                            var self = this;
                            var selectedRows = $.data(self.$container, internal.SELECTED_ROWS);
                            if (!selectedRows || !selectedRows.items || selectedRows.items.length === 0)
                                return;
                            for (var i = self.startIndex; i <= self.endIndex; i++) {
                                if (selectedRows.items[i]) {
                                    controls.tick(true, self.$container, false, i);
                                }
                            }
                        };
                        Cloud.prototype.dirtyCellsIn = function () {
                            var self = this;
                            var $exTable = helper.closest(self.$container, "." + NAMESPACE);
                            var updateMode = $.data($exTable, NAMESPACE).updateMode;
                            var histories, targetHis, otherHis;
                            if (self.options.containerClass === BODY_PRF + LEFTMOST) {
                                histories = $.data(self.$container, internal.EDIT_HISTORY);
                                if (!histories)
                                    return;
                                self.each(histories);
                                return;
                            }
                            if (updateMode === COPY_PASTE) {
                                histories = $.data(self.$container, internal.COPY_HISTORY);
                                if (!histories)
                                    return;
                                for (var i = histories.length - 1; i >= 0; i--) {
                                    self.each(histories[i].items);
                                }
                            }
                            else if (updateMode === EDIT) {
                                histories = $.data(self.$container, internal.EDIT_HISTORY);
                                if (histories)
                                    self.each(histories);
                            }
                            else if (updateMode === STICK) {
                                histories = $.data(self.$container, internal.STICK_HISTORY);
                                if (!histories)
                                    return;
                                _.forEach(histories, function (items) {
                                    self.each(items);
                                });
                            }
                        };
                        Cloud.prototype.errorCellsIn = function () {
                            var self = this;
                            var $exTable = helper.closest(self.$container, "." + NAMESPACE);
                            var updateMode = $.data($exTable, NAMESPACE).updateMode;
                            var errs = $.data($exTable, errors.ERRORS);
                            if (!errs || errs.length === 0)
                                return;
                            self.each(errs, errors.ERROR_CLS);
                        };
                        Cloud.prototype.detCellsIn = function () {
                            var self = this;
                            var det = $.data(self.$container, internal.DET);
                            if (!det)
                                return;
                            self.eachKey(det, function (obj) { return obj; }, function ($cell) { return helper.markCellWith(style.DET_CLS, $cell); });
                        };
                        Cloud.prototype.madeUpCellsIn = function () {
                            var self = this;
                            var dCellsStyle = $.data(self.$container, internal.D_CELLS_STYLE);
                            if (!dCellsStyle)
                                return;
                            self.eachKey(dCellsStyle, function (obj) { return obj.columnKey; }, function ($cell, obj) {
                                var makeup = obj.makeup;
                                if (makeup) {
                                    $cell.style.color = makeup.textColor;
                                }
                            });
                        };
                        Cloud.prototype.eachKey = function (obj, key, cb) {
                            var self = this;
                            _.forEach(Object.keys(obj), function (rIdx) {
                                if (rIdx >= self.startIndex && rIdx <= self.endIndex) {
                                    _.forEach(obj[rIdx], function (valObj) {
                                        var $cell = selection.cellAt(self.$container, rIdx, key(valObj));
                                        if ($cell === intan.NULL || !$cell)
                                            return;
                                        cb($cell, valObj);
                                    });
                                }
                            });
                        };
                        Cloud.prototype.each = function (items, styler) {
                            var self = this;
                            styler = styler || update.EDITED_CLS;
                            _.forEach(items, function (item) {
                                if (item.rowIndex >= self.startIndex && item.rowIndex <= self.endIndex) {
                                    var $cell = selection.cellAt(self.$container, item.rowIndex, item.columnKey);
                                    if ($cell === intan.NULL || !$cell)
                                        return;
                                    var itemStyle = void 0;
                                    if (item.updateTarget === 0) {
                                        itemStyle = update.OTHER_EDITED_CLS;
                                    }
                                    else if (item.updateTarget === 1) {
                                        itemStyle = update.TARGET_EDITED_CLS;
                                    }
                                    else
                                        itemStyle = styler;
                                    helper.markCellWith(itemStyle, $cell, item.innerIdx, item.value);
                                }
                            });
                        };
                        return Cloud;
                    }());
                    intan.Cloud = Cloud;
                })(intan || (intan = {}));
                var cellHandler;
                (function (cellHandler) {
                    cellHandler.ROUND_GO = "ex-round-go";
                    function get(handlerType) {
                        switch (handlerType.toLowerCase()) {
                            case "input":
                                return cellInput;
                            case "tooltip":
                                return tooltip;
                            case "roundtrip":
                                return roundGo;
                        }
                    }
                    cellHandler.get = get;
                    function cellInput($cell, options, supplier) {
                        if (uk.util.isNullOrUndefined(options.updateMode) || options.updateMode !== EDIT)
                            return;
                        $cell.classList.add(update.EDITABLE_CLS);
                        $cell.addXEventListener(events.CLICK_EVT, function (evt) {
                            if ($cell.getElementsByTagName("input").length > 0) {
                                evt.stopImmediatePropagation();
                                return;
                            }
                            var $exTable = helper.closest($cell, "." + NAMESPACE);
                            if (evt.ctrlKey && $.data($exTable, NAMESPACE).determination)
                                return;
                            update.edit($exTable, $cell, options.containerClass);
                        });
                    }
                    cellHandler.cellInput = cellInput;
                    function tooltip($cell, options, supplier) {
                        var $content = supplier();
                        if (uk.util.isNullOrUndefined($content))
                            return;
                        new widget.Tooltip($cell, { sources: $content });
                    }
                    cellHandler.tooltip = tooltip;
                    function roundGo($cell, options, supplier) {
                        if (!supplier || typeof supplier !== "function")
                            return;
                        $cell.classList.add(cellHandler.ROUND_GO);
                        $cell.addXEventListener(events.CLICK_EVT, function (evt) {
                            var $grid = helper.closest($cell, "table").parentElement;
                            if (errors.occurred(helper.closest($grid, "." + NAMESPACE)))
                                return false;
                            var coord = helper.getCellCoord($cell);
                            helper.closest($cell, "." + NAMESPACE).addXEventListener(events.ROUND_RETREAT, function (evt) {
                                if (evt.currentTarget.dataset.triggered)
                                    return;
                                var value = evt.detail;
                                var x = helper.getExTableFromGrid($grid);
                                var ds = helper.getDataSource($grid);
                                var manId = x.manipulatorId;
                                var manKey = x.manipulatorKey;
                                if (!ds || ds.length === 0)
                                    return;
                                var rowObj = ds[coord.rowIdx];
                                if (rowObj[coord.columnKey] !== value) {
                                    var updTarget = void 0;
                                    if (!uk.util.isNullOrUndefined(manId) && !uk.util.isNullOrUndefined(manKey)) {
                                        updTarget = rowObj[manKey] === manId ? 1 : 0;
                                    }
                                    update.gridCell($grid, coord.rowIdx, coord.columnKey, -1, value);
                                    update.pushEditHistory($grid, new selection.Cell(coord.rowIdx, coord.columnKey, value, -1), updTarget);
                                }
                                evt.currentTarget.dataset.triggered = true;
                            });
                            supplier();
                        });
                    }
                    cellHandler.roundGo = roundGo;
                    function rClick(cell, column, cb) {
                        if (uk.util.isNullOrUndefined(column.rightClick) || typeof column.rightClick !== "function")
                            return;
                        cell.addXEventListener(events.MOUSE_DOWN, function (evt) {
                            if (evt.which === 3 || evt.button === 2) {
                                evt.preventDefault();
                                cb();
                            }
                        });
                        cell.addXEventListener(events.CM, function () {
                            return false;
                        });
                    }
                    cellHandler.rClick = rClick;
                    function rRowClick(row, columnsMap, args) {
                        row.addXEventListener(events.MOUSE_DOWN, function (evt) {
                            var coord = helper.getCellCoord(evt.target);
                            if (!coord)
                                return;
                            var column = columnsMap[coord.columnKey];
                            if (uk.util.isNullOrUndefined(column.rightClick) || typeof column.rightClick !== "function")
                                return;
                            if (evt.which === 3 || evt.button === 2) {
                                evt.preventDefault();
                                column.rightClick(args.rData, args.rowIdx, coord.columnKey);
                            }
                        });
                        row.addXEventListener(events.CM, function () {
                            return false;
                        });
                    }
                    cellHandler.rRowClick = rRowClick;
                })(cellHandler || (cellHandler = {}));
                var update;
                (function (update) {
                    update.EDITOR = "editor";
                    update.EDITED_CLS = "edited";
                    update.TARGET_EDITED_CLS = "target-edited";
                    update.OTHER_EDITED_CLS = "other-edited";
                    update.EDIT_CELL_CLS = "edit-cell";
                    update.EDITOR_CLS = "ex-editor";
                    update.EDITABLE_CLS = "ex-editable";
                    var EMPTY_TBL_HEIGHT = "1px";
                    var Editor = (function () {
                        function Editor($editor, land, rowIdx, columnKey, innerIdx, value) {
                            this.$editor = $editor;
                            this.land = land;
                            this.rowIdx = rowIdx;
                            this.columnKey = columnKey;
                            this.innerIdx = innerIdx;
                            this.value = value;
                        }
                        return Editor;
                    }());
                    update.Editor = Editor;
                    function edit($exTable, $cell, land, value, forced) {
                        var $grid = $exTable.querySelector("." + BODY_PRF + DETAIL);
                        var $body = !land ? $grid : helper.getTable($exTable, land);
                        var exTable = $.data($exTable, NAMESPACE);
                        if (!forced && (errors.occurred($exTable) || selector.is($cell, "." + style.DET_CLS)
                            || (land === BODY_PRF + DETAIL && exTable.detailContent.banEmptyInput
                                && exTable.detailContent.banEmptyInput.some(function (m) { return m === exTable.viewMode; })
                                && $cell.textContent === "")
                            || selector.is($cell, "." + style.HIDDEN_CLS) || selector.is($cell, "." + style.SEAL_CLS))) {
                            outsideClick($exTable, $cell, true);
                            return;
                        }
                        var editor = $.data($exTable, update.EDITOR);
                        var $editor, $input, inputVal, innerIdx = -1;
                        var coord = helper.getCellCoord($cell);
                        if (uk.util.isNullOrUndefined(editor)) {
                            var content = $cell.textContent;
                            inputVal = value ? value : content;
                            $input = document.createElement("input");
                            $input.style.border = "none";
                            $input.style.width = "96%";
                            $input.style.height = "92%";
                            $input.style.outline = "none";
                            $input.value = inputVal;
                            $editor = document.createElement("div");
                            $editor.className = update.EDITOR_CLS;
                            $editor.style.height = ($cell.offsetHeight - 4) + "px";
                            $editor.style.width = ($cell.offsetWidth - 4) + "px";
                            $editor.style.backgroundColor = "#FFF";
                            $editor.style.border = "solid 1px #E67E22";
                            $editor.appendChild($input);
                            if (selector.is($cell, "div")) {
                                $editor.style.height = ($cell.offsetHeight - 4) + "px";
                                $editor.style.width = ($cell.offsetWidth - 4) + "px";
                                innerIdx = selector.index($cell);
                            }
                            $.data($exTable, update.EDITOR, new Editor($editor, land, coord.rowIdx, coord.columnKey, innerIdx, inputVal));
                            events.trigger($exTable, events.START_EDIT, [$editor, content]);
                            helper.addClass($cell, update.EDIT_CELL_CLS);
                            $cell.innerHTML = "";
                            $cell.appendChild($editor);
                            editing($exTable, $editor, land);
                            $input.select();
                            validation.validate($body, $cell, coord.rowIdx, coord.columnKey, innerIdx, inputVal);
                            selection.tickRows($exTable.querySelector("." + BODY_PRF + LEFTMOST), false);
                        }
                        else {
                            $editor = editor.$editor;
                            $input = $editor.querySelector("input");
                            var content_1 = $input.value;
                            var editingLand_1 = editor.land;
                            var cont_1 = function (cb) {
                                if ($editor.style.display === "none")
                                    $editor.style.display = "";
                                if (selector.is($cell, "div")) {
                                    $editor.style.height = ($cell.offsetHeight - 4) + "px";
                                    $editor.style.width = ($cell.offsetWidth - 4) + "px";
                                    innerIdx = selector.index($cell);
                                }
                                else {
                                    $editor.style.height = ($cell.offsetHeight - 4) + "px";
                                    $editor.style.width = ($cell.offsetWidth - 4) + "px";
                                }
                                var $editingCell = helper.closest($editor, "." + update.EDIT_CELL_CLS);
                                helper.removeClass($editingCell, update.EDIT_CELL_CLS);
                                var cellText = $cell.textContent;
                                inputVal = value ? value : cellText;
                                $input.value = inputVal;
                                triggerStopEdit($exTable, $editingCell, editingLand_1, content_1);
                                if (cb && _.isFunction(cb)) {
                                    cb();
                                }
                                editor.land = land;
                                editor.rowIdx = coord.rowIdx;
                                editor.columnKey = coord.columnKey;
                                editor.innerIdx = innerIdx;
                                editor.value = inputVal;
                                helper.addClass($cell, update.EDIT_CELL_CLS);
                                $cell.innerHTML = "";
                                $cell.appendChild($editor);
                                editing($exTable, $editor, land);
                                $input.select();
                                validation.validate($body, $cell, coord.rowIdx, coord.columnKey, innerIdx, inputVal);
                                selection.tickRows($exTable.querySelector("." + BODY_PRF + LEFTMOST), false);
                            };
                            var $editingGrid_1 = !editingLand_1 ? helper.getMainTable($exTable) : helper.getTable($exTable, editor.land);
                            var visibleColumns = helper.getVisibleColumnsOn($editingGrid_1);
                            var columnDf_1;
                            _.forEach(visibleColumns, function (col) {
                                if (col.key === editor.columnKey) {
                                    columnDf_1 = col;
                                    return false;
                                }
                            });
                            if (!columnDf_1)
                                return;
                            if (columnDf_1.ajaxValidate && _.isFunction(columnDf_1.ajaxValidate.request)) {
                                helper.block($exTable);
                                columnDf_1.ajaxValidate.request(content_1).done(function (res) {
                                    cont_1(helper.call(columnDf_1.ajaxValidate.onValid, { rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res));
                                }).fail(function (res) {
                                    var $target = selection.cellAt($editingGrid_1, editor.rowIdx, editor.columnKey);
                                    if ($target !== intan.NULL) {
                                        errors.add($exTable, $target, editor.rowIdx, editor.columnKey, editor.innerIdx, editor.value);
                                    }
                                    if (_.isFunction(columnDf_1.ajaxValidate.onFailed)) {
                                        columnDf_1.ajaxValidate.onFailed({ rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res);
                                    }
                                }).always(function () {
                                    helper.unblock($exTable);
                                });
                                return;
                            }
                            cont_1();
                        }
                    }
                    update.edit = edit;
                    function editing($exTable, $editor, land) {
                        var $input = $editor.querySelector("input");
                        $input.removeXEventListener(events.KEY_UP);
                        $input.addXEventListener(events.KEY_UP, function (evt) {
                            var value = $input.value;
                            if (evt.keyCode === $.ui.keyCode.ENTER) {
                                var $grid_1;
                                if (!land) {
                                    $grid_1 = helper.getMainTable($exTable);
                                }
                                else {
                                    $grid_1 = $exTable.querySelector("." + land);
                                }
                                var editor_1 = $.data($exTable, update.EDITOR);
                                if (errors.occurred($exTable) || !editor_1)
                                    return;
                                var visibleColumns = helper.getVisibleColumnsOn(!editor_1.land ? helper.getMainTable($exTable) : helper.getTable($exTable, editor_1.land));
                                var columnDf_2;
                                _.forEach(visibleColumns, function (col) {
                                    if (col.key === editor_1.columnKey) {
                                        columnDf_2 = col;
                                        return false;
                                    }
                                });
                                if (!columnDf_2)
                                    return;
                                if (columnDf_2.ajaxValidate && _.isFunction(columnDf_2.ajaxValidate.request)) {
                                    helper.block($exTable);
                                    columnDf_2.ajaxValidate.request(value).done(function (res) {
                                        var $parent = $editor.parentElement;
                                        helper.removeClass($parent, update.EDIT_CELL_CLS);
                                        var currentCell = new selection.Cell(editor_1.rowIdx, editor_1.columnKey, undefined, editor_1.innerIdx);
                                        $.data($exTable, update.EDITOR, null);
                                        triggerStopEdit($exTable, $parent, land, value);
                                        if (_.isFunction(columnDf_2.ajaxValidate.onValid)) {
                                            columnDf_2.ajaxValidate.onValid({ rowIndex: editor_1.rowIdx, columnKey: editor_1.columnKey, innerIdx: editor_1.innerIdx }, res);
                                        }
                                        if (land !== BODY_PRF + DETAIL)
                                            return;
                                        var cell = helper.nextCellOf($grid_1, currentCell);
                                        internal.getGem($grid_1).rollTo(cell);
                                        _.defer(function () {
                                            var $cell = selection.cellAt($grid_1, cell.rowIndex, cell.columnKey);
                                            if (uk.util.isNullOrUndefined(cell.innerIdx) || cell.innerIdx === -1) {
                                                edit($exTable, $cell, land);
                                                return;
                                            }
                                            edit($exTable, $cell.querySelectorAll("." + render.CHILD_CELL_CLS)[cell.innerIdx], land);
                                        });
                                    }).fail(function (res) {
                                        var $target = selection.cellAt($grid_1, editor_1.rowIdx, editor_1.columnKey);
                                        if ($target !== intan.NULL) {
                                            errors.add($exTable, $target, editor_1.rowIdx, editor_1.columnKey, editor_1.innerIdx, editor_1.value);
                                        }
                                        if (_.isFunction(columnDf_2.ajaxValidate.onFailed)) {
                                            columnDf_2.ajaxValidate.onFailed({ rowIndex: editor_1.rowIdx, columnKey: editor_1.columnKey, innerIdx: editor_1.innerIdx }, res);
                                        }
                                    }).always(function () {
                                        helper.unblock($exTable);
                                    });
                                }
                                else {
                                    var $parent = $editor.parentElement;
                                    helper.removeClass($parent, update.EDIT_CELL_CLS);
                                    var currentCell = new selection.Cell(editor_1.rowIdx, editor_1.columnKey, undefined, editor_1.innerIdx);
                                    $.data($exTable, update.EDITOR, null);
                                    triggerStopEdit($exTable, $parent, land, value);
                                    if (land !== BODY_PRF + DETAIL)
                                        return;
                                    var cell_1 = helper.nextCellOf($grid_1, currentCell);
                                    internal.getGem($grid_1).rollTo(cell_1);
                                    _.defer(function () {
                                        var $cell = selection.cellAt($grid_1, cell_1.rowIndex, cell_1.columnKey);
                                        if (uk.util.isNullOrUndefined(cell_1.innerIdx) || cell_1.innerIdx === -1) {
                                            edit($exTable, $cell, land);
                                            return;
                                        }
                                        edit($exTable, $cell.querySelectorAll("." + render.CHILD_CELL_CLS)[cell_1.innerIdx], land);
                                    });
                                }
                            }
                            else {
                                var editor = $.data($exTable, update.EDITOR);
                                if (uk.util.isNullOrUndefined(editor))
                                    return;
                                editor.value = value;
                                var $grid = !editor.land ? helper.getMainTable($exTable) : helper.getTable($exTable, editor.land);
                                validation.validate($grid, helper.closest(editor.$editor, "." + update.EDIT_CELL_CLS), editor.rowIdx, editor.columnKey, editor.innerIdx, editor.value);
                            }
                        });
                    }
                    function triggerStopEdit($exTable, $cell, land, value) {
                        if ($cell.length === 0)
                            return;
                        var innerIdx = -1;
                        if (selector.is($cell, "div")) {
                            innerIdx = selector.index($cell);
                        }
                        var coord = helper.getCellCoord($cell);
                        if (!coord)
                            return;
                        events.trigger($exTable, events.STOP_EDIT, { land: land, rowIndex: coord.rowIdx, columnKey: coord.columnKey, innerIdx: innerIdx, value: value });
                    }
                    update.triggerStopEdit = triggerStopEdit;
                    function editDone($exTable) {
                        var $grid = $exTable.querySelector("." + BODY_PRF + DETAIL);
                        var fts = $.data($exTable, NAMESPACE).detailContent.features;
                        var timeRangeFt = feature.find(fts, feature.TIME_RANGE);
                        var timeRangerDef;
                        if (!uk.util.isNullOrUndefined(timeRangeFt)) {
                            timeRangerDef = _.groupBy(timeRangeFt.ranges, "rowId");
                            $.data($grid, internal.TIME_VALID_RANGE, timeRangerDef);
                        }
                        $exTable.addXEventListener(events.STOP_EDIT, function (evt) {
                            var ui = evt.detail;
                            postEdit($exTable, ui, timeRangerDef);
                        });
                    }
                    update.editDone = editDone;
                    function postEdit($exTable, ui, timeRangerDef) {
                        var $body = !ui.land ? $exTable.querySelector("." + BODY_PRF + DETAIL) : $exTable.querySelector("." + ui.land);
                        var $cell = selection.cellAt($body, ui.rowIndex, ui.columnKey);
                        var result = validation.validate($body, $cell, ui.rowIndex, ui.columnKey, ui.innerIdx, ui.value, timeRangerDef);
                        if (!result.isValid)
                            return;
                        ui.value = result.value;
                        var res = cellData($exTable, ui);
                        if (!uk.util.isNullOrUndefined(res)) {
                            var newValObj = ui.value;
                            if (_.isObject(res.value)) {
                                var $main = helper.getMainTable($exTable);
                                var gen = $.data($main, internal.TANGI) || $.data($main, internal.CANON);
                                var upperInput = gen.painter.options.upperInput;
                                var lowerInput = gen.painter.options.lowerInput;
                                newValObj = _.cloneDeep(res.value);
                                if (ui.innerIdx === 0) {
                                    newValObj[upperInput] = ui.value;
                                }
                                else if (ui.innerIdx === 1) {
                                    newValObj[lowerInput] = ui.value;
                                }
                            }
                            pushEditHistory($body, new selection.Cell(ui.rowIndex, ui.columnKey, res, ui.innerIdx), res.updateTarget);
                            var editCls = uk.util.isNullOrUndefined(res.updateTarget) ? update.EDITED_CLS
                                : (res.updateTarget === 0 ? update.OTHER_EDITED_CLS : update.TARGET_EDITED_CLS);
                            helper.markCellWith(editCls, $cell, ui.innerIdx);
                            events.trigger($exTable, events.CELL_UPDATED, new selection.Cell(ui.rowIndex, ui.columnKey, newValObj, ui.innerIdx));
                        }
                        setText($cell, ui.innerIdx, ui.value);
                    }
                    function setText($cell, innerIdx, value) {
                        var $childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        if (!uk.util.isNullOrUndefined(innerIdx) && innerIdx > -1 && $childCells.length > 0) {
                            $childCells[innerIdx].textContent = value;
                        }
                        else {
                            $cell.textContent = value;
                        }
                    }
                    update.setText = setText;
                    function outsideClick($exTable, $target, immediate) {
                        if (immediate || !selector.is($target, "." + update.EDITABLE_CLS)) {
                            if ($.data($exTable, "blockUI.isBlocked") === 1 || errors.occurred($exTable))
                                return;
                            var editor_2 = $.data($exTable, update.EDITOR);
                            if (uk.util.isNullOrUndefined(editor_2))
                                return;
                            var $input = editor_2.$editor.querySelector("input");
                            var content_2 = $input.value;
                            var mo_1 = function (cb) {
                                var innerIdx = -1;
                                var $parent = helper.closest(editor_2.$editor, "." + update.EDITABLE_CLS);
                                helper.removeClass($parent, update.EDIT_CELL_CLS);
                                var $g = helper.closest($parent, "table").parentElement;
                                if (!$parent || !$g)
                                    return;
                                if (selector.is($parent, "div"))
                                    innerIdx = selector.index($parent);
                                $parent.textContent = content_2;
                                postEdit($exTable, { rowIndex: editor_2.rowIdx, columnKey: editor_2.columnKey, innerIdx: innerIdx,
                                    value: content_2, land: ($.data($g, internal.TANGI) || $.data($g, internal.CANON)).painter.options.containerClass });
                                if (cb && _.isFunction(cb)) {
                                    cb();
                                }
                                $.data($exTable, update.EDITOR, null);
                            };
                            var $grid_2 = !editor_2.land ? helper.getMainTable($exTable) : helper.getTable($exTable, editor_2.land);
                            var visibleColumns = helper.getVisibleColumnsOn($grid_2);
                            var columnDf_3;
                            _.forEach(visibleColumns, function (col) {
                                if (col.key === editor_2.columnKey) {
                                    columnDf_3 = col;
                                    return false;
                                }
                            });
                            if (!columnDf_3)
                                return;
                            if (!selector.is($target, "." + cellHandler.ROUND_GO)
                                && columnDf_3.ajaxValidate && _.isFunction(columnDf_3.ajaxValidate.request)) {
                                helper.block($exTable);
                                columnDf_3.ajaxValidate.request(content_2).done(function (res) {
                                    mo_1(helper.call(columnDf_3.ajaxValidate.onValid, { rowIndex: editor_2.rowIdx, columnKey: editor_2.columnKey, innerIdx: editor_2.innerIdx }, res));
                                }).fail(function (res) {
                                    var $target = selection.cellAt($grid_2, editor_2.rowIdx, editor_2.columnKey);
                                    if ($target !== intan.NULL) {
                                        errors.add($exTable, $target, editor_2.rowIdx, editor_2.columnKey, editor_2.innerIdx, editor_2.value);
                                    }
                                    if (_.isFunction(columnDf_3.ajaxValidate.onFailed)) {
                                        columnDf_3.ajaxValidate.onFailed({ rowIndex: editor_2.rowIdx, columnKey: editor_2.columnKey, innerIdx: editor_2.innerIdx }, res);
                                    }
                                }).always(function () {
                                    helper.unblock($exTable);
                                });
                                return;
                            }
                            mo_1();
                        }
                    }
                    update.outsideClick = outsideClick;
                    function cellData($exTable, ui) {
                        var exTable = $.data($exTable, NAMESPACE);
                        if (!exTable)
                            return;
                        var updateTarget, oldVal, innerIdx = ui.innerIdx, f;
                        if (ui.land === BODY_PRF + LEFTMOST) {
                            f = "leftmostContent";
                        }
                        else if (ui.land === BODY_PRF + MIDDLE) {
                            f = "middleContent";
                        }
                        else {
                            f = "detailContent";
                        }
                        if (uk.util.isNullOrUndefined(ui.innerIdx)) {
                            innerIdx = exTable[f].dataSource[ui.rowIndex][ui.columnKey].constructor === Array ? 1 : -1;
                        }
                        var rowData = exTable[f].dataSource[ui.rowIndex];
                        if (!uk.util.isNullOrUndefined(exTable.manipulatorKey)
                            && !uk.util.isNullOrUndefined(exTable.manipulatorId)) {
                            updateTarget = rowData[exTable.manipulatorKey] === exTable.manipulatorId ? 1 : 0;
                        }
                        var currentVal = rowData[ui.columnKey];
                        if (innerIdx === -1) {
                            if (currentVal !== ui.value) {
                                oldVal = _.cloneDeep(currentVal);
                                if (exTable[f].primaryKey === ui.columnKey) {
                                    if (exTable.leftmostContent) {
                                        exTable.leftmostContent.dataSource[ui.rowIndex][ui.columnKey] = ui.value;
                                    }
                                    if (exTable.middleContent) {
                                        exTable.middleContent.dataSource[ui.rowIndex][ui.columnKey] = ui.value;
                                    }
                                    if (exTable.detailContent) {
                                        exTable.detailContent.dataSource[ui.rowIndex][ui.columnKey] = ui.value;
                                    }
                                }
                                else {
                                    exTable[f].dataSource[ui.rowIndex][ui.columnKey] = ui.value;
                                }
                                return { updateTarget: updateTarget, value: oldVal };
                            }
                            return null;
                        }
                        var $main = !ui.land ? helper.getMainTable($exTable) : helper.getTable($exTable, ui.land);
                        var gen = $.data($main, internal.TANGI) || $.data($main, internal.CANON);
                        var upperInput = gen.painter.options.upperInput;
                        var lowerInput = gen.painter.options.lowerInput;
                        var field;
                        if (ui.innerIdx === 0) {
                            field = upperInput;
                        }
                        else if (ui.innerIdx === 1) {
                            field = lowerInput;
                        }
                        if (currentVal[field] !== ui.value && (!uk.util.isNullOrUndefined(currentVal[field])
                            || ui.value !== "")) {
                            oldVal = _.cloneDeep(currentVal);
                            exTable[f].dataSource[ui.rowIndex][ui.columnKey][field] = ui.value;
                            return { updateTarget: updateTarget, value: oldVal };
                        }
                        return null;
                    }
                    update.cellData = cellData;
                    function rowData($exTable, rowIdx, data) {
                        var exTable = $.data($exTable, NAMESPACE);
                        if (!exTable)
                            return;
                        _.assignInWith(exTable.detailContent.dataSource[rowIdx], data, function (objVal, srcVal) {
                            if (objVal.constructor === Array && srcVal.constructor !== Array) {
                                objVal[1] = srcVal;
                                return objVal;
                            }
                            return srcVal;
                        });
                    }
                    update.rowData = rowData;
                    function gridCell($grid, rowIdx, columnKey, innerIdx, value, isRestore) {
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        var cData = gen.dataSource[rowIdx][columnKey];
                        var origDs = gen._origDs;
                        var $table = helper.closest($grid, "." + NAMESPACE);
                        if (cData.constructor === Array) {
                            if (value.constructor === Array) {
                                _.forEach(cData, function (d, i) {
                                    gen.dataSource[rowIdx][columnKey][i] = value[i];
                                    if (!helper.isEqual(origDs[rowIdx][columnKey][i], value[i])) {
                                        events.trigger($table, events.CELL_UPDATED, new selection.Cell(rowIdx, columnKey, value[i], i));
                                    }
                                });
                            }
                            else {
                                gen.dataSource[rowIdx][columnKey][innerIdx] = value;
                                if (!helper.isEqual(origDs[rowIdx][columnKey][innerIdx], value)) {
                                    events.trigger($table, events.CELL_UPDATED, new selection.Cell(rowIdx, columnKey, value, innerIdx));
                                }
                            }
                        }
                        else {
                            gen.dataSource[rowIdx][columnKey] = value;
                            if (!helper.isEqual(origDs[rowIdx][columnKey], value)) {
                                events.trigger($table, events.CELL_UPDATED, new selection.Cell(rowIdx, columnKey, value, -1));
                            }
                        }
                        render.gridCell($grid, rowIdx, columnKey, innerIdx, value, isRestore);
                    }
                    update.gridCell = gridCell;
                    function gridRow($grid, rowIdx, data, isRestore) {
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        _.assignInWith(gen.dataSource[rowIdx], data, function (objVal, srcVal) {
                            if (objVal.constructor === Array && srcVal.constructor !== Array) {
                                objVal[1] = srcVal;
                                return objVal;
                            }
                            return srcVal;
                        });
                        render.gridRow($grid, rowIdx, data, isRestore);
                    }
                    update.gridRow = gridRow;
                    function gridCellOw($grid, rowIdx, columnKey, innerIdx, value, txId) {
                        var $exTable = helper.closest($grid, "." + NAMESPACE);
                        var exTable = $.data($exTable, NAMESPACE);
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var pk = helper.getPrimaryKey($grid);
                        if (!gen || helper.isDetCell($grid, rowIdx, columnKey)
                            || helper.isXCell($grid, gen.dataSource[rowIdx][pk], columnKey, style.HIDDEN_CLS, style.SEAL_CLS))
                            return;
                        var cData = gen.dataSource[rowIdx][columnKey];
                        var opt = gen.options;
                        if (!exTable.pasteOverWrite
                            && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, cData)))
                            return;
                        var changedData;
                        if (cData.constructor === Array) {
                            if (value.constructor === Array) {
                                changedData = _.cloneDeep(cData);
                                _.forEach(cData, function (d, i) {
                                    gen.dataSource[rowIdx][columnKey][i] = value[i];
                                });
                            }
                            else {
                                changedData = cData[innerIdx];
                                gen.dataSource[rowIdx][columnKey][innerIdx] = value;
                            }
                        }
                        else if (_.isObject(cData) && !_.isObject(value)) {
                            return;
                        }
                        else {
                            changedData = cData;
                            gen.dataSource[rowIdx][columnKey] = value;
                        }
                        var touched = render.gridCell($grid, rowIdx, columnKey, innerIdx, value);
                        if (touched && touched.dirty) {
                            var cellObj = new selection.Cell(rowIdx, columnKey, changedData);
                            cellObj.setTarget(touched.updateTarget);
                            pushHistory($grid, [cellObj], txId);
                            events.trigger($exTable, events.CELL_UPDATED, new selection.Cell(rowIdx, columnKey, value, innerIdx));
                        }
                    }
                    update.gridCellOw = gridCellOw;
                    function gridRowOw($grid, rowIdx, data, txId) {
                        var $exTable = helper.closest($grid, "." + NAMESPACE);
                        var exTable = $.data($exTable, NAMESPACE);
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var pk = helper.getPrimaryKey($grid);
                        if (!gen)
                            return;
                        var changedCells = [];
                        var origData = _.cloneDeep(data);
                        var clonedData = _.cloneDeep(data);
                        var opt = gen.options;
                        _.assignInWith(gen.dataSource[rowIdx], clonedData, function (objVal, srcVal, key, obj, src) {
                            if ((!exTable.pasteOverWrite
                                && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, objVal)))
                                || helper.isDetCell($grid, rowIdx, key)
                                || helper.isXCell($grid, gen.dataSource[rowIdx][pk], key, style.HIDDEN_CLS, style.SEAL_CLS)) {
                                src[key] = objVal;
                                return objVal;
                            }
                            if (!uk.util.isNullOrUndefined(src[key]) && !helper.isEqual(src[key], obj[key])) {
                                changedCells.push(new selection.Cell(rowIdx, key, objVal));
                            }
                            else {
                                delete origData[key];
                            }
                            if (objVal.constructor === Array && srcVal.constructor !== Array) {
                                objVal[1] = srcVal;
                                return objVal;
                            }
                            return srcVal;
                        });
                        _.forEach(Object.keys(clonedData), function (k) {
                            if (!helper.isEqual(clonedData[k], origData[k])) {
                                delete origData[k];
                            }
                        });
                        var touched = render.gridRow($grid, rowIdx, origData);
                        if (changedCells.length > 0) {
                            changedCells.forEach(function (c) { return c.setTarget(touched.updateTarget); });
                            pushHistory($grid, changedCells, txId);
                            events.trigger($exTable, events.ROW_UPDATED, events.createRowUi(rowIdx, origData));
                        }
                    }
                    update.gridRowOw = gridRowOw;
                    function stickGridCellOw($grid, rowIdx, columnKey, innerIdx, value, styleMaker) {
                        var $exTable = helper.closest($grid, "." + NAMESPACE);
                        var exTable = $.data($exTable, NAMESPACE);
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var pk = helper.getPrimaryKey($grid);
                        if (!gen || helper.isDetCell($grid, rowIdx, columnKey)
                            || helper.isXCell($grid, gen.dataSource[rowIdx][pk], columnKey, style.HIDDEN_CLS, style.SEAL_CLS))
                            return;
                        var cData = gen.dataSource[rowIdx][columnKey];
                        var opt = gen.options;
                        if (!exTable.stickOverWrite
                            && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, cData)))
                            return;
                        var changedData = _.cloneDeep(cData);
                        gen.dataSource[rowIdx][columnKey] = value;
                        var touched = render.gridCell($grid, rowIdx, columnKey, innerIdx, value, styleMaker);
                        if (touched && touched.dirty) {
                            var cellObj = new selection.Cell(rowIdx, columnKey, changedData, innerIdx);
                            cellObj.setTarget(touched.updateTarget);
                            pushStickHistory($grid, [cellObj]);
                            events.trigger($exTable, events.CELL_UPDATED, new selection.Cell(rowIdx, columnKey, value, innerIdx));
                        }
                    }
                    update.stickGridCellOw = stickGridCellOw;
                    function stickGridRowOw($grid, rowIdx, data, styleMaker) {
                        var $exTable = helper.closest($grid, "." + NAMESPACE);
                        var exTable = $.data($exTable, NAMESPACE);
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var pk = helper.getPrimaryKey($grid);
                        if (!gen)
                            return;
                        var changedCells = [];
                        var origData = _.cloneDeep(data);
                        var clonedData = _.cloneDeep(data);
                        var opt = gen.options;
                        _.assignInWith(gen.dataSource[rowIdx], clonedData, function (objVal, srcVal, key, obj, src) {
                            if ((!exTable.stickOverWrite
                                && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, objVal)))
                                || helper.isDetCell($grid, rowIdx, key)
                                || helper.isXCell($grid, gen.dataSource[rowIdx][pk], key, style.HIDDEN_CLS, style.SEAL_CLS)) {
                                src[key] = objVal;
                                return objVal;
                            }
                            if (!uk.util.isNullOrUndefined(src[key]) && !helper.isEqual(src[key], obj[key])) {
                                changedCells.push(new selection.Cell(rowIdx, key, _.cloneDeep(objVal)));
                            }
                            else {
                                delete origData[key];
                            }
                            if (objVal.constructor === Array && srcVal.constructor !== Array) {
                                objVal[1] = srcVal;
                                return objVal;
                            }
                            return srcVal;
                        });
                        _.forEach(Object.keys(clonedData), function (k) {
                            if (!helper.isEqual(clonedData[k], origData[k])) {
                                delete origData[k];
                            }
                        });
                        var touched = render.gridRow($grid, rowIdx, origData, styleMaker);
                        if (changedCells.length > 0) {
                            changedCells.forEach(function (c) { return c.setTarget(touched.updateTarget); });
                            pushStickHistory($grid, changedCells);
                            events.trigger($exTable, events.ROW_UPDATED, events.createRowUi(rowIdx, origData));
                        }
                    }
                    update.stickGridRowOw = stickGridRowOw;
                    function pushHistory($grid, cells, txId) {
                        var history = $.data($grid, internal.COPY_HISTORY);
                        if (!history || history.length === 0) {
                            history = [{ txId: txId, items: cells }];
                            $.data($grid, internal.COPY_HISTORY, history);
                            return;
                        }
                        var latestHistory = history[history.length - 1];
                        if (latestHistory.txId === txId) {
                            _.forEach(cells, function (cell) {
                                latestHistory.items.push(cell);
                            });
                        }
                        else {
                            var newHis = { txId: txId, items: cells };
                            history.push(newHis);
                        }
                    }
                    update.pushHistory = pushHistory;
                    function pushEditHistory($grid, cell, editTarget) {
                        var history = $.data($grid, internal.EDIT_HISTORY);
                        if (editTarget === 0 || editTarget === 1) {
                            cell.setTarget(editTarget);
                        }
                        if (!history || history.length === 0) {
                            $.data($grid, internal.EDIT_HISTORY, [cell]);
                            return;
                        }
                        history.push(cell);
                    }
                    update.pushEditHistory = pushEditHistory;
                    function pushStickHistory($grid, cells) {
                        var history = $.data($grid, internal.STICK_HISTORY);
                        if (!history || history.length === 0) {
                            $.data($grid, internal.STICK_HISTORY, [cells]);
                            return;
                        }
                        history.push(cells);
                    }
                    update.pushStickHistory = pushStickHistory;
                    function removeStickHistory($grid, cells) {
                        var history = $.data($grid, internal.STICK_HISTORY);
                        if (!history || history.length === 0)
                            return;
                        _.remove(history, function (h) { return cells.some(function (c) { return helper.areSameCells(c, h); }); });
                    }
                    update.removeStickHistory = removeStickHistory;
                    function insertNewRow($container) {
                        var rowIndex;
                        Array.prototype.slice.call($container.querySelectorAll("div[class*='" + BODY_PRF + "']")).filter(function (e) {
                            return !e.classList.contains(BODY_PRF + HORIZONTAL_SUM) && !e.classList.contains(BODY_PRF + LEFT_HORZ_SUM);
                        }).forEach(function (e) {
                            var ds = internal.getDataSource(e);
                            var origDs = helper.getOrigDS(e);
                            var newRec = {};
                            rowIndex = ds.length;
                            var columns = helper.gridColumnsMap(e);
                            if (!ds || !columns)
                                return;
                            _.forEach(columns, function (value, key) {
                                if (key === controls.CHECKED_KEY)
                                    return;
                                newRec[key] = "";
                            });
                            var gen = $.data(e, internal.TANGI) || $.data(e, internal.CANON);
                            var newRow = gen.painter.row(newRec, { css: { height: gen.painter.options.rowHeight } }, ds.length);
                            if (!uk.util.isNullOrUndefined(gen.startIndex)) {
                                var trList = e.querySelectorAll("tbody tr");
                                trList[trList.length - 1].insertAdjacentElement("beforebegin", newRow);
                            }
                            else {
                                e.querySelector("tbody").appendChild(newRow);
                            }
                            origDs[ds.length] = _.cloneDeep(newRec);
                            ds[ds.length] = newRec;
                        });
                        var $grid = helper.getMainTable($container);
                        $grid.scrollTop = $grid.scrollHeight;
                        var $leftmost = $container.querySelector("." + BODY_PRF + LEFTMOST);
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (rowIndex >= gen.startIndex && rowIndex <= gen.endIndex) {
                            controls.tick(true, $leftmost, false, rowIndex);
                            return;
                        }
                        $leftmost.addXEventListener(events.RENDERED + ".once", function (evt) {
                            controls.tick(true, $leftmost, false, rowIndex);
                            $leftmost.removeXEventListener(events.RENDERED + ".once");
                        });
                    }
                    update.insertNewRow = insertNewRow;
                    function deleteRows($container) {
                        var $grid = helper.getLeftmostTable($container);
                        var rows = $.data($grid, internal.SELECTED_ROWS);
                        if (!rows || !rows.items || rows.items.length === 0)
                            return;
                        if (rows.count > 0) {
                            rows.selectAll = false;
                            var $allBox = selector.classSiblings($grid, HEADER_PRF + LEFTMOST).map(function (e) {
                                var trList = e.querySelectorAll("table tr");
                                return trList[0].querySelectorAll("td")[0].querySelector("input");
                            })[0];
                            if (selector.is($allBox, ":checked"))
                                $allBox.checked = false;
                        }
                        retrackEditor($container, rows.items);
                        var $t = Array.prototype.slice.call($container.querySelectorAll("div[class*='" + BODY_PRF + "']")).filter(function (e) {
                            return !e.classList.contains(BODY_PRF + HORIZONTAL_SUM) && !e.classList.contains(BODY_PRF + LEFT_HORZ_SUM);
                        });
                        $t.forEach(function (elm) {
                            _.forEach([internal.EDIT_HISTORY, internal.COPY_HISTORY, internal.STICK_HISTORY,
                                internal.SELECTED_CELLS, errors.ERRORS], function (name) {
                                retrackRecords(elm, rows.items, name);
                            });
                        });
                        $t.forEach(function (elm, index) {
                            var ds = internal.getDataSource(elm);
                            var origDs = helper.getOrigDS(elm);
                            if (!ds || ds.length === 0)
                                return;
                            if (ds.length > rows.items.length)
                                rows.items[ds.length - 1] = false;
                            var count = rows.count;
                            for (var i = rows.items.length - 1; i >= 0; i--) {
                                var $row = selection.rowAt(elm, i);
                                if (rows.items[i]) {
                                    if ($row !== intan.NULL && !$row.classList.contains(NAMESPACE + "-" + intan.BOTTOM_SPACE)) {
                                        $row.parentNode.removeChild($row);
                                    }
                                    ds.splice(i, 1);
                                    origDs.splice(i, 1);
                                    if (index === $t.length - 1) {
                                        rows.items[i] = false;
                                        rows.items.splice(i, 1);
                                        rows.count--;
                                    }
                                    count--;
                                }
                                else {
                                    if ($row !== intan.NULL) {
                                        selector.queryAll($row, "td").forEach(function (e) {
                                            var view = $.data(e, internal.VIEW);
                                            if (view) {
                                                var coord = view.split("-");
                                                $.data(e, internal.VIEW, parseInt(coord[0]) - count + "-" + coord[1]);
                                            }
                                        });
                                    }
                                }
                            }
                            if (ds.length === 0) {
                                elm.querySelector("." + NAMESPACE + "-" + intan.BOTTOM_SPACE).style.height = EMPTY_TBL_HEIGHT;
                            }
                        });
                    }
                    update.deleteRows = deleteRows;
                    function retrackEditor($container, rows) {
                        var count = 0;
                        var editor = $.data($container, update.EDITOR);
                        if (!editor)
                            return;
                        if (rows[editor.rowIdx]) {
                            $.data($container, update.EDITOR, null);
                            return;
                        }
                        for (var i = 0; i < editor.rowIdx; i++) {
                            if (rows[i])
                                count++;
                        }
                        if (count > 0)
                            editor.rowIdx = editor.rowIdx - count;
                    }
                    function retrackRecords($grid, rows, name) {
                        var histories = $.data($grid, name);
                        if (!histories)
                            return;
                        if (name === internal.COPY_HISTORY) {
                            for (var i = histories.length - 1; i >= 0; i--) {
                                each(histories[i].items, rows);
                            }
                        }
                        else if (name === internal.SELECTED_CELLS) {
                            _.forEach(Object.keys(histories).sort(function (a, b) { return a - b; }), function (key, index) {
                                var count = 0;
                                if (rows[key]) {
                                    delete histories[key];
                                    return;
                                }
                                for (var i = 0; i < key; i++) {
                                    if (rows[i])
                                        count++;
                                }
                                if (count > 0) {
                                    var newKey = key - count;
                                    histories[newKey] = histories[key];
                                    delete histories[key];
                                }
                            });
                        }
                        else {
                            each(histories, rows);
                        }
                    }
                    function each(histories, rows) {
                        var removes = [];
                        _.forEach(histories, function (his, index) {
                            var count = 0;
                            if (rows[his.rowIndex]) {
                                removes.push(index);
                                return;
                            }
                            for (var i = 0; i < his.rowIndex; i++) {
                                if (rows[i])
                                    count++;
                            }
                            if (count > 0)
                                his.rowIndex = his.rowIndex - count;
                        });
                        while (removes.length > 0) {
                            histories.splice(removes.pop(), 1);
                        }
                    }
                })(update || (update = {}));
                var copy;
                (function (copy) {
                    copy.PASTE_ID = "pasteHelper";
                    copy.COPY_ID = "copyHelper";
                    var Mode;
                    (function (Mode) {
                        Mode[Mode["SINGLE"] = 0] = "SINGLE";
                        Mode[Mode["MULTIPLE"] = 1] = "MULTIPLE";
                    })(Mode || (Mode = {}));
                    var History = (function () {
                        function History(cells) {
                            this.cells = cells;
                        }
                        return History;
                    }());
                    copy.History = History;
                    var Printer = (function () {
                        function Printer(options) {
                            this.options = options;
                        }
                        Printer.prototype.hook = function ($grid) {
                            var self = this;
                            self.$grid = $grid;
                            self.$grid.setAttribute("tabindex", 0);
                            self.$grid.style.outline = "none";
                            self.$grid.addXEventListener(events.FOCUS_IN, function (evt) {
                                if (document.querySelector("#pasteHelper") && document.querySelector("#copyHelper"))
                                    return;
                                var pasteArea = document.createElement("textarea");
                                pasteArea.setAttribute("id", copy.PASTE_ID);
                                pasteArea.style.opacity = "0";
                                pasteArea.style.overflow = "hidden";
                                pasteArea.addXEventListener(events.PASTE, self.paste.bind(self));
                                var copyArea = document.createElement("textarea");
                                copyArea.setAttribute("id", copy.COPY_ID);
                                copyArea.style.opacity = "0";
                                copyArea.style.overflow = "hidden";
                                var $div = document.createElement("div");
                                $div.style.position = "fixed";
                                $div.style.top = "-10000px";
                                $div.style.left = "-10000px";
                                document.body.appendChild($div);
                                $div.appendChild(pasteArea);
                                $div.appendChild(copyArea);
                            });
                            self.$grid.addXEventListener(events.KEY_DOWN, function (evt) {
                                if (evt.ctrlKey && helper.isPasteKey(evt)) {
                                    document.querySelector("#pasteHelper").focus();
                                }
                                else
                                    self.getOp(evt);
                                _.defer(function () {
                                    selection.focus(self.$grid);
                                });
                            });
                        };
                        Printer.prototype.getOp = function (evt) {
                            var self = this;
                            if (evt.ctrlKey && helper.isCopyKey(evt)) {
                                self.copy();
                            }
                            else if (evt.ctrlKey && helper.isCutKey(evt)) {
                            }
                            else if (evt.ctrlKey && helper.isUndoKey(evt)) {
                                self.undo();
                            }
                        };
                        Printer.prototype.copy = function (cut) {
                            var self = this;
                            var selectedCells = selection.getSelectedCells(this.$grid);
                            var copiedData;
                            if (selectedCells.length === 1) {
                                var cell = selectedCells[0];
                                var ds = internal.getDataSource(self.$grid);
                                var pk = helper.getPrimaryKey(self.$grid);
                                if (helper.isDetCell(self.$grid, cell.rowIndex, cell.columnKey)
                                    || helper.isXCell(self.$grid, ds[cell.rowIndex][pk], cell.columnKey, style.HIDDEN_CLS, style.SEAL_CLS))
                                    return;
                                this.mode = Mode.SINGLE;
                                copiedData = _.isObject(selectedCells[0].value) ? JSON.stringify(selectedCells[0].value) : selectedCells[0].value;
                            }
                            else {
                                this.mode = Mode.MULTIPLE;
                                copiedData = this.converseStructure(selectedCells, cut);
                            }
                            var $copyHelper = document.querySelector("#copyHelper");
                            $copyHelper.value = copiedData;
                            $copyHelper.select();
                            document.execCommand("copy");
                            return selectedCells;
                        };
                        Printer.prototype.converseStructure = function (cells, cut) {
                            var self = this;
                            var maxRow = 0;
                            var minRow = 0;
                            var maxColumn = 0;
                            var minColumn = 0;
                            var structure = [];
                            var structData = "";
                            _.forEach(cells, function (cell, index) {
                                var rowIndex = parseInt(cell.rowIndex);
                                var columnIndex = helper.getDisplayColumnIndex(self.$grid, cell.columnKey);
                                if (index === 0) {
                                    minRow = maxRow = rowIndex;
                                    minColumn = maxColumn = columnIndex;
                                }
                                if (rowIndex < minRow)
                                    minRow = rowIndex;
                                if (rowIndex > maxRow)
                                    maxRow = rowIndex;
                                if (columnIndex < minColumn)
                                    minColumn = columnIndex;
                                if (columnIndex > maxColumn)
                                    maxColumn = columnIndex;
                                if (uk.util.isNullOrUndefined(structure[rowIndex])) {
                                    structure[rowIndex] = {};
                                }
                                var ds = internal.getDataSource(self.$grid);
                                var pk = helper.getPrimaryKey(self.$grid);
                                if (helper.isDetCell(self.$grid, rowIndex, cell.columnKey)
                                    || helper.isXCell(self.$grid, ds[rowIndex][pk], cell.columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) {
                                    structure[rowIndex][columnIndex] = undefined;
                                }
                                else
                                    structure[rowIndex][columnIndex] = helper.stringValue(cell.value);
                            });
                            for (var i = minRow; i <= maxRow; i++) {
                                for (var j = minColumn; j <= maxColumn; j++) {
                                    if (uk.util.isNullOrUndefined(structure[i]) || uk.util.isNullOrUndefined(structure[i][j])
                                        || uk.util.isNullOrEmpty(structure[i][j])) {
                                        structData += "null";
                                    }
                                    else {
                                        structData += structure[i][j];
                                    }
                                    if (j === maxColumn)
                                        structData += "\n";
                                    else
                                        structData += "\t";
                                }
                            }
                            return structData;
                        };
                        Printer.prototype.cut = function () {
                            var self = this;
                            var selectedCells = this.copy(true);
                            _.forEach(selectedCells, function (cell) {
                                var $cell = selection.cellAt(self.$grid, cell.rowIndex, cell.columnKey);
                                var value = "";
                                if ($cell.querySelectorAll("." + render.CHILD_CELL_CLS).length > 0) {
                                    value = ["", ""];
                                }
                                update.gridCell(self.$grid, cell.rowIndex, cell.columnKey, -1, value);
                            });
                        };
                        Printer.prototype.paste = function (evt) {
                            if (this.mode === Mode.SINGLE) {
                                this.pasteSingleCell(evt);
                            }
                            else {
                                this.pasteRange(evt);
                            }
                        };
                        Printer.prototype.pasteSingleCell = function (evt) {
                            var self = this;
                            var cbData = this.getClipboardContent(evt);
                            cbData = helper.getCellData(cbData);
                            var selectedCells = selection.getSelectedCells(this.$grid);
                            var txId = uk.util.randomId();
                            _.forEach(selectedCells, function (cell, index) {
                                update.gridCellOw(self.$grid, cell.rowIndex, cell.columnKey, -1, cbData, txId);
                            });
                        };
                        Printer.prototype.pasteRange = function (evt) {
                            var cbData = this.getClipboardContent(evt);
                            cbData = this.process(cbData);
                            this.updateWith(cbData);
                        };
                        Printer.prototype.process = function (data) {
                            var dataRows = _.map(data.split("\n"), function (row) {
                                return _.map(row.split("\t"), function (cData) {
                                    return cData.indexOf(",") > 0 ? cData.split(",") : cData;
                                });
                            });
                            var rowsCount = dataRows.length;
                            if ((dataRows[rowsCount - 1].length === 1 && dataRows[rowsCount - 1][0] === "")
                                || (dataRows.length === 1 && dataRows[0].length === 1
                                    && (dataRows[0][0] === "" || dataRows[0][0] === "\r"))) {
                                dataRows.pop();
                            }
                            return dataRows;
                        };
                        Printer.prototype.updateWith = function (data) {
                            var self = this;
                            var selectedCell = selection.getSelectedCells(self.$grid)[0];
                            if (selectedCell === undefined)
                                return;
                            var visibleColumns = helper.gridVisibleColumns(self.$grid);
                            var rowIndex = selectedCell.rowIndex;
                            var startColumnIndex = helper.indexOf(selectedCell.columnKey, visibleColumns);
                            if (startColumnIndex === -1)
                                return;
                            var txId = uk.util.randomId();
                            var ds = internal.getDataSource(self.$grid);
                            var size = ds ? ds.length : 0;
                            _.forEach(data, function (row, idx) {
                                var rowData = {};
                                var columnKey = selectedCell.columnKey;
                                var columnIndex = startColumnIndex;
                                for (var i = 0; i < row.length; i++) {
                                    if (!uk.util.isNullOrUndefined(row[i]) && row[i].constructor !== Array && row[i].trim() === "null") {
                                        columnKey = helper.nextKeyOf(columnIndex++, visibleColumns);
                                        if (!columnKey)
                                            break;
                                        continue;
                                    }
                                    rowData[columnKey] = helper.getCellData(row[i]);
                                    columnKey = helper.nextKeyOf(columnIndex++, visibleColumns);
                                    if (!columnKey)
                                        break;
                                }
                                if (rowIndex >= size)
                                    return false;
                                update.gridRowOw(self.$grid, rowIndex, rowData, txId);
                                rowIndex++;
                            });
                        };
                        Printer.prototype.undo = function () {
                            var self = this;
                            var histories = $.data(self.$grid, internal.COPY_HISTORY);
                            if (!histories || histories.length === 0)
                                return;
                            var tx = histories.pop();
                            _.forEach(tx.items, function (item) {
                                update.gridCell(self.$grid, item.rowIndex, item.columnKey, -1, item.value, true);
                                internal.removeChange(self.$grid, item);
                            });
                        };
                        Printer.prototype.getClipboardContent = function (evt) {
                            if (window.clipboardData) {
                                window.event.returnValue = false;
                                return window.clipboardData.getData("text");
                            }
                            else {
                                return evt.clipboardData.getData("text/plain");
                            }
                        };
                        return Printer;
                    }());
                    copy.Printer = Printer;
                    function on($grid, updateMode) {
                        if (updateMode === COPY_PASTE) {
                            new Printer().hook($grid);
                        }
                    }
                    copy.on = on;
                    function off($grid, updateMode) {
                        if (updateMode !== COPY_PASTE) {
                            $grid.removeXEventListener(events.FOCUS_IN);
                            $grid.removeXEventListener(events.KEY_DOWN);
                            var $copy = document.querySelector("#" + copy.COPY_ID);
                            var $paste = document.querySelector("#" + copy.PASTE_ID);
                            if (uk.util.isNullOrUndefined($copy) || uk.util.isNullOrUndefined($paste))
                                return;
                            $copy.parentNode.removeChild($copy);
                            $paste.parentNode.removeChild($paste);
                        }
                    }
                    copy.off = off;
                })(copy || (copy = {}));
                var spread;
                (function (spread) {
                    spread.SINGLE = "single";
                    spread.MULTIPLE = "multiple";
                    var Sticker = (function () {
                        function Sticker(data) {
                            this.mode = spread.MULTIPLE;
                            this.validate = function () { return true; };
                            this.data = data;
                        }
                        return Sticker;
                    }());
                    spread.Sticker = Sticker;
                    function bindSticker($cell, rowIdx, columnKey, options) {
                        if (options.containerClass !== BODY_PRF + DETAIL || uk.util.isNullOrUndefined(options.updateMode)
                            || options.updateMode !== STICK)
                            return;
                        $cell.addXEventListener(events.CLICK_EVT, function (evt) {
                            if (evt.ctrlKey)
                                return;
                            var $grid = helper.closest($cell, "." + BODY_PRF + DETAIL);
                            var sticker = $.data($grid, internal.STICKER);
                            if (!sticker || uk.util.isNullOrUndefined(sticker.data)
                                || uk.util.isNullOrUndefined(sticker.validate))
                                return;
                            var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                            var visibleColumns = gen.painter.visibleColumns;
                            var data = {};
                            var key = columnKey;
                            var colIndex = helper.indexOf(key, visibleColumns);
                            if (sticker.mode === spread.SINGLE) {
                                var result = void 0;
                                if ((result = sticker.validate(rowIdx, columnKey, sticker.data)) !== true) {
                                    result();
                                    return;
                                }
                                update.stickGridCellOw($grid, rowIdx, columnKey, -1, sticker.data);
                                return;
                            }
                            _.forEach(sticker.data, function (cData) {
                                data[key] = cData;
                                key = helper.nextKeyOf(colIndex++, visibleColumns);
                                if (!key)
                                    return false;
                            });
                            update.stickGridRowOw($grid, rowIdx, data);
                        });
                    }
                    spread.bindSticker = bindSticker;
                    function bindRowSticker($row, rowIdx, options) {
                        if (options.containerClass !== BODY_PRF + DETAIL || uk.util.isNullOrUndefined(options.updateMode)
                            || options.updateMode !== STICK)
                            return;
                        $row.addXEventListener(events.CLICK_EVT, function (evt) {
                            if (evt.ctrlKey)
                                return;
                            var $grid = helper.closest($row, "." + BODY_PRF + DETAIL);
                            var sticker = $.data($grid, internal.STICKER);
                            if (!sticker || uk.util.isNullOrUndefined(sticker.data)
                                || uk.util.isNullOrUndefined(sticker.validate))
                                return;
                            var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                            var visibleColumns = gen.painter.visibleColumns;
                            var data = {};
                            var $cell = evt.target;
                            if (selector.is(evt.target, "." + render.CHILD_CELL_CLS)) {
                                $cell = helper.closest(evt.target, "td");
                            }
                            var coord = helper.getCellCoord($cell);
                            var key = coord.columnKey;
                            var colIndex = helper.indexOf(key, visibleColumns);
                            if (sticker.mode === spread.SINGLE) {
                                var result = void 0;
                                if ((result = sticker.validate(rowIdx, key, sticker.data)) !== true) {
                                    result();
                                    return;
                                }
                                update.stickGridCellOw($grid, rowIdx, key, -1, sticker.data, sticker.styleMaker);
                                return;
                            }
                            _.forEach(sticker.data, function (cData) {
                                data[key] = cData;
                                key = helper.nextKeyOf(colIndex++, visibleColumns);
                                if (!key)
                                    return false;
                            });
                            update.stickGridRowOw($grid, rowIdx, data, sticker.styleMaker);
                        });
                    }
                    spread.bindRowSticker = bindRowSticker;
                })(spread || (spread = {}));
                var validation;
                (function (validation) {
                    validation.TIME_SPLIT = ":";
                    validation.TIME_PTN = /^-?\d+:\d{2}$/;
                    validation.SHORT_TIME_PTN = /^-?\d+$/;
                    validation.NUMBER_PTN = /^\d+$/;
                    validation.MINUTE_MAX = 59;
                    validation.HOUR_MAX = 24;
                    validation.DEF_HOUR_MAX = 9999;
                    validation.DEF_HOUR_MIN = 0;
                    validation.DEF_MIN_MAXMIN = 0;
                    validation.DAY_MINS = 1439;
                    var Result = (function () {
                        function Result(isValid, value) {
                            this.isValid = isValid;
                            this.value = value;
                        }
                        Result.fail = function () {
                            return new Result(false);
                        };
                        Result.ok = function (value) {
                            return new Result(true, value);
                        };
                        return Result;
                    }());
                    function validate($body, $cell, rowIdx, columnKey, innerIdx, value, timeRangerDef) {
                        var vtor = validation.mandate($body, columnKey, innerIdx);
                        var gen = $.data($body, internal.TANGI) || $.data($body, internal.CANON);
                        var rowId = gen.dataSource[rowIdx][gen.primaryKey];
                        timeRangerDef = timeRangerDef || $.data($body, internal.TIME_VALID_RANGE);
                        var formatValue;
                        if (vtor) {
                            if ((vtor.actValid === internal.TIME || vtor.actValid === internal.DURATION) && timeRangerDef) {
                                var ranges = timeRangerDef[rowId];
                                _.forEach(ranges, function (range) {
                                    if (range && range.columnKey === columnKey && range.innerIdx === innerIdx) {
                                        vtor.max = range.max;
                                        vtor.min = range.min;
                                        return Result.fail();
                                    }
                                });
                            }
                            var isValid = void 0;
                            if (vtor.required && (_.isUndefined(value) || _.isEmpty(value))) {
                                isValid = false;
                            }
                            else if (!_.isUndefined(value) && !_.isEmpty(value)) {
                                if (vtor.actValid === internal.TIME) {
                                    isValid = isTimeClock(value);
                                    formatValue = formatTime(value);
                                }
                                else if (vtor.actValid === internal.DURATION) {
                                    isValid = isTimeDuration(value, vtor.max, vtor.min);
                                    formatValue = formatTime(value);
                                }
                                else if (vtor.actValid === internal.NUMBER) {
                                    isValid = isNumber(value, vtor.max, vtor.min);
                                    formatValue = (_.isUndefined(value) || _.isEmpty(value)) ? "" : Number(value);
                                }
                                else {
                                    isValid = true;
                                    formatValue = value;
                                }
                            }
                            else {
                                isValid = true;
                                formatValue = "";
                            }
                            var $tbl = helper.closest($body, "." + NAMESPACE);
                            if (!isValid) {
                                errors.add($tbl, $cell, rowIdx, columnKey, innerIdx, value);
                                return Result.fail();
                            }
                            if (errors.any($cell, innerIdx))
                                errors.remove($tbl, $cell, rowIdx, columnKey, innerIdx);
                        }
                        return Result.ok(formatValue);
                    }
                    validation.validate = validate;
                    function mandate($grid, columnKey, innerIdx) {
                        var visibleColumns = helper.getVisibleColumnsOn($grid);
                        var actValid, dataType, max, min, required;
                        _.forEach(visibleColumns, function (col) {
                            if (col.key === columnKey) {
                                if (!col.dataType)
                                    return false;
                                dataType = col.dataType.toLowerCase();
                                actValid = which(innerIdx, dataType, internal.TIME)
                                    || which(innerIdx, dataType, internal.DURATION)
                                    || which(innerIdx, dataType, internal.NUMBER)
                                    || which(innerIdx, dataType, internal.TEXT);
                                var constraints = ui.validation.getConstraint(col.primitiveValue);
                                if (constraints && (constraints.valueType === "Time" || constraints.valueType === "Clock")) {
                                    max = constraints.max ? constraints.max : col.max;
                                    min = constraints.min ? constraints.min : col.min;
                                    required = constraints.required ? constraints.required : col.required;
                                    return false;
                                }
                                max = col.max;
                                min = col.min;
                                required = col.required;
                                return false;
                            }
                        });
                        if (actValid)
                            return {
                                actValid: actValid,
                                max: max,
                                min: min,
                                required: required
                            };
                    }
                    validation.mandate = mandate;
                    function which(innerIdx, dataType, type) {
                        var actValid;
                        if (dataType && dataType.indexOf(type) !== -1) {
                            if (!uk.util.isNullOrUndefined(innerIdx) && innerIdx > -1) {
                                _.forEach(dataType.split(internal.DT_SEPARATOR), function (p, index) {
                                    if (p === type && index === innerIdx) {
                                        actValid = type;
                                        return false;
                                    }
                                });
                            }
                            else {
                                actValid = type;
                            }
                        }
                        return actValid;
                    }
                    function isTimeClock(time) {
                        if (uk.util.isNullOrUndefined(time))
                            return false;
                        time = time.trim();
                        var hour, minute;
                        if (validation.TIME_PTN.test(time)) {
                            var parts = time.split(validation.TIME_SPLIT);
                            hour = parseInt(parts[0]);
                            minute = parseInt(parts[1]);
                        }
                        else if (validation.SHORT_TIME_PTN.test(time)) {
                            var totalTime = parseInt(time);
                            minute = totalTime % 100;
                            hour = Math.floor(totalTime / 100);
                        }
                        if (((hour !== NaN && hour >= 0 && hour <= validation.HOUR_MAX) || hour === NaN)
                            && minute !== NaN && minute >= 0 && minute <= validation.MINUTE_MAX)
                            return true;
                        return false;
                    }
                    validation.isTimeClock = isTimeClock;
                    function isTimeDuration(time, max, min) {
                        if (uk.util.isNullOrUndefined(time))
                            return false;
                        time = time.trim();
                        var hour, minute, negative, minMM, maxHour = validation.DEF_HOUR_MAX, minHour = validation.DEF_HOUR_MIN;
                        var maxMM = minMM = validation.DEF_MIN_MAXMIN;
                        var maxTime = parse(max) || { hour: validation.DEF_HOUR_MAX, minute: validation.DEF_MIN_MAXMIN, negative: false };
                        var minTime = parse(min) || { hour: validation.DEF_HOUR_MIN, minute: validation.DEF_MIN_MAXMIN, negative: false };
                        if (validation.TIME_PTN.test(time)) {
                            var parts = time.split(validation.TIME_SPLIT);
                            hour = Math.abs(parseInt(parts[0]));
                            minute = parseInt(parts[1]);
                            negative = time.charAt(0) === '-';
                        }
                        else if (validation.SHORT_TIME_PTN.test(time)) {
                            var totalTime = Math.abs(parseInt(time));
                            minute = totalTime % 100;
                            hour = Math.floor(totalTime / 100);
                            negative = time.charAt(0) === '-';
                        }
                        if (((uk.util.isNullOrUndefined(hour) || hour === NaN) && (uk.util.isNullOrUndefined(minute) || minute === NaN))
                            || minute > validation.MINUTE_MAX)
                            return false;
                        var targetTime = getComplement({ hour: hour, minute: minute, negative: negative });
                        if (!targetTime)
                            return false;
                        if (compare(targetTime, maxTime) > 0 || compare(targetTime, minTime) < 0)
                            return false;
                        return true;
                    }
                    validation.isTimeDuration = isTimeDuration;
                    function isNumber(value, max, min) {
                        if (!validation.NUMBER_PTN.test(value))
                            return false;
                        var int = parseInt(value);
                        if ((!uk.util.isNullOrUndefined(max) && int > parseInt(max))
                            || (!uk.util.isNullOrUndefined(min) && int < parseInt(min)))
                            return false;
                        return true;
                    }
                    function compare(one, other) {
                        if (one.negative && !other.negative)
                            return -1;
                        else if (!one.negative && other.negative)
                            return 1;
                        else if (one.negative && other.negative) {
                            return compareAbs(one, other) * (-1);
                        }
                        else
                            return compareAbs(one, other);
                    }
                    function compareAbs(one, other) {
                        if (one.hour > other.hour) {
                            return 1;
                        }
                        else if (one.hour < other.hour) {
                            return -1;
                        }
                        else if (one.minute > other.minute) {
                            return 1;
                        }
                        else if (one.minute < other.minute) {
                            return -1;
                        }
                        return 0;
                    }
                    function getComplement(time) {
                        if (!time.negative)
                            return time;
                        var oTime = validation.DAY_MINS - (time.hour * 60 + time.minute);
                        if (oTime < 0)
                            return;
                        var hour = Math.floor(oTime / 60);
                        var minute = oTime - hour * 60;
                        return { hour: hour, minute: minute, negative: true };
                    }
                    function parse(time) {
                        if (validation.TIME_PTN.test(time)) {
                            var parts = time.split(validation.TIME_SPLIT);
                            var hour = Math.abs(parseInt(parts[0]));
                            var minute = parseInt(parts[1]);
                            return {
                                hour: hour,
                                minute: minute,
                                negative: time.charAt(0) === '-'
                            };
                        }
                    }
                    function formatTime(time) {
                        var minute, hour;
                        if (validation.SHORT_TIME_PTN.test(time)) {
                            var totalTime = Math.abs(parseInt(time));
                            minute = totalTime % 100;
                            hour = Math.floor(totalTime / 100);
                        }
                        if (!uk.util.isNullOrUndefined(hour) && hour !== NaN
                            && !uk.util.isNullOrUndefined(minute) && minute !== NaN) {
                            if (minute < 10)
                                minute = "0" + minute;
                            return (time.charAt(0) === '-' ? "-" : "") + hour + validation.TIME_SPLIT + minute;
                        }
                        if (!uk.util.isNullOrUndefined(hour) && hour === NaN
                            && !uk.util.isNullOrUndefined(minute) && minute !== NaN) {
                            return (time.charAt(0) === '-' ? "-" : "") + minute;
                        }
                        return time;
                    }
                    validation.formatTime = formatTime;
                })(validation || (validation = {}));
                var errors;
                (function (errors_1) {
                    errors_1.ERROR_CLS = "x-error";
                    errors_1.ERRORS = "errors";
                    function add($grid, $cell, rowIdx, columnKey, innerIdx, value) {
                        if (any($cell, innerIdx))
                            return;
                        var errors = $.data($grid, errors_1.ERRORS);
                        var newErr = new selection.Cell(rowIdx, columnKey, value, innerIdx);
                        if (!errors) {
                            errors = [newErr];
                            $.data($grid, errors_1.ERRORS, errors);
                        }
                        else {
                            errors.push(newErr);
                        }
                        if (selector.is($cell, "td") && !uk.util.isNullOrUndefined(innerIdx) && innerIdx > -1) {
                            $cell.querySelectorAll("div")[innerIdx].classList.add(errors_1.ERROR_CLS);
                        }
                        else {
                            $cell.classList.add(errors_1.ERROR_CLS);
                        }
                    }
                    errors_1.add = add;
                    function remove($grid, $cell, rowIdx, columnKey, innerIdx) {
                        var errors = $.data($grid, errors_1.ERRORS);
                        if (!errors)
                            return;
                        var idx;
                        _.forEach(errors, function (err, index) {
                            if (helper.areSameCells(err, { rowIndex: rowIdx, columnKey: columnKey, innerIdx: innerIdx })) {
                                idx = index;
                                return false;
                            }
                        });
                        if (!uk.util.isNullOrUndefined(idx)) {
                            errors.splice(idx, 1);
                            if (selector.is($cell, "td") && !uk.util.isNullOrUndefined(innerIdx) && innerIdx > -1) {
                                $cell.querySelector("div")[innerIdx].classList.remove(errors_1.ERROR_CLS);
                            }
                            else {
                                $cell.classList.remove(errors_1.ERROR_CLS);
                            }
                        }
                    }
                    errors_1.remove = remove;
                    function clear($grid) {
                        $.data($grid, errors_1.ERRORS, null);
                    }
                    errors_1.clear = clear;
                    function any($cell, innerIdx) {
                        var $childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        if (!uk.util.isNullOrUndefined(innerIdx) && $childCells.length > 0) {
                            return $childCells[innerIdx].classList.contains(errors_1.ERROR_CLS);
                        }
                        return $cell.classList.contains(errors_1.ERROR_CLS);
                    }
                    errors_1.any = any;
                    function occurred($grid) {
                        var errs = $.data($grid, errors_1.ERRORS);
                        if (!errs)
                            return false;
                        return errs.length > 0;
                    }
                    errors_1.occurred = occurred;
                })(errors || (errors = {}));
                var selection;
                (function (selection) {
                    selection.CELL_SELECTED_CLS = "x-cell-selected";
                    selection.ROW_SELECTED_CLS = "x-row-selected";
                    var Cell = (function () {
                        function Cell(rowIdx, columnKey, value, innerIdx) {
                            this.rowIndex = rowIdx;
                            this.columnKey = columnKey;
                            this.value = value;
                            this.innerIdx = innerIdx;
                        }
                        Cell.prototype.setTarget = function (target) {
                            this.updateTarget = target;
                        };
                        return Cell;
                    }());
                    selection.Cell = Cell;
                    function checkUp($exTable) {
                        if ($.data($exTable, NAMESPACE).updateMode !== COPY_PASTE)
                            return;
                        var $detailContent = $exTable.querySelector("." + BODY_PRF + DETAIL);
                        var isSelecting;
                        $detailContent.onselectstart = function () {
                            return false;
                        };
                        $detailContent.addXEventListener(events.MOUSE_DOWN, function (evt) {
                            var $target = evt.target;
                            isSelecting = true;
                            if (!selector.is($target, "." + render.CHILD_CELL_CLS) && !selector.is($target, "td"))
                                return;
                            if (evt.shiftKey) {
                                selectRange($detailContent, $target);
                                return;
                            }
                            if (!evt.ctrlKey) {
                                clearAll($detailContent);
                            }
                            selectCell($detailContent, $target);
                        });
                        $detailContent.addXEventListener(events.MOUSE_UP, function (evt) {
                            isSelecting = false;
                        });
                        $detailContent.addXEventListener(events.MOUSE_MOVE, function (evt) {
                            if (isSelecting) {
                                selectRange($detailContent, evt.target);
                            }
                        });
                    }
                    selection.checkUp = checkUp;
                    function selectRange($grid, $cell) {
                        if (uk.util.isNullOrUndefined($cell) || selector.is($cell, "." + BODY_PRF + DETAIL))
                            return;
                        var lastSelected = $.data($grid, internal.LAST_SELECTED);
                        if (!lastSelected) {
                            selectCell($grid, $cell);
                            return;
                        }
                        clearAll($grid);
                        var toCoord = helper.getCellCoord($cell);
                        var minRowIdx = Math.min(lastSelected.rowIdx, toCoord.rowIdx);
                        var maxRowIdx = Math.max(lastSelected.rowIdx, toCoord.rowIdx);
                        for (var i = minRowIdx; i < maxRowIdx + 1; i++) {
                            cellInRange($grid, i, lastSelected.columnKey, toCoord.columnKey);
                        }
                    }
                    function markCell($cell) {
                        if (selector.is($cell, "." + render.CHILD_CELL_CLS)) {
                            $cell.classList.add(selection.CELL_SELECTED_CLS);
                            selector.classSiblings($cell, render.CHILD_CELL_CLS).forEach(function (e) {
                                e.classList.add(selection.CELL_SELECTED_CLS);
                            });
                            return true;
                        }
                        else if (selector.is($cell, "td")) {
                            var childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                            if (childCells.length > 0) {
                                helper.addClass(childCells, selection.CELL_SELECTED_CLS);
                            }
                            else {
                                $cell.classList.add(selection.CELL_SELECTED_CLS);
                            }
                            return true;
                        }
                        return false;
                    }
                    selection.markCell = markCell;
                    function selectCell($grid, $cell, notLast) {
                        if (!markCell($cell))
                            return;
                        var coord = helper.getCellCoord($cell);
                        addSelect($grid, coord.rowIdx, coord.columnKey, notLast);
                        tickRows(selector.classSiblings($grid, BODY_PRF + LEFTMOST), false);
                    }
                    selection.selectCell = selectCell;
                    function addSelect($grid, rowIdx, columnKey, notLast) {
                        var selectedCells = $.data($grid, internal.SELECTED_CELLS);
                        if (!notLast)
                            $.data($grid, internal.LAST_SELECTED, { rowIdx: rowIdx, columnKey: columnKey });
                        if (!selectedCells) {
                            selectedCells = {};
                            selectedCells[rowIdx] = [columnKey];
                            $.data($grid, internal.SELECTED_CELLS, selectedCells);
                            return;
                        }
                        if (!selectedCells[rowIdx]) {
                            selectedCells[rowIdx] = [columnKey];
                            return;
                        }
                        if (_.find(selectedCells[rowIdx], function (key) {
                            return key === columnKey;
                        }) === undefined) {
                            selectedCells[rowIdx].push(columnKey);
                        }
                    }
                    selection.addSelect = addSelect;
                    function clear($grid, rowIdx, columnKey) {
                        var selectedCells = $.data($grid, internal.SELECTED_CELLS);
                        if (!selectedCells)
                            return;
                        var row = selectedCells[rowIdx];
                        if (!row || row.length === 0)
                            return;
                        var colIdx;
                        _.forEach(row, function (key, index) {
                            if (key === columnKey) {
                                colIdx = index;
                                return false;
                            }
                        });
                        if (uk.util.isNullOrUndefined(colIdx))
                            return;
                        row.splice(colIdx, 1);
                        var selectedCell = cellAt($grid, rowIdx, columnKey);
                        if (selectedCell === intan.NULL)
                            return;
                        if (selectedCell) {
                            var $childCells = selectedCell.querySelectorAll("." + render.CHILD_CELL_CLS);
                            if ($childCells.length > 0) {
                                helper.removeClass($childCells, selection.CELL_SELECTED_CLS);
                            }
                            else {
                                helper.removeClass(selectedCell, selection.CELL_SELECTED_CLS);
                            }
                        }
                    }
                    selection.clear = clear;
                    function clearAll($grid) {
                        var selectedCells = $.data($grid, internal.SELECTED_CELLS);
                        if (!selectedCells)
                            return;
                        _.forEach(Object.keys(selectedCells), function (rowIdx, index) {
                            if (!rowExists($grid, rowIdx))
                                return;
                            _.forEach(selectedCells[rowIdx], function (col, i) {
                                var $cell = cellAt($grid, rowIdx, col);
                                if ($cell) {
                                    var childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                                    if (childCells.length > 0) {
                                        helper.removeClass(childCells, selection.CELL_SELECTED_CLS);
                                    }
                                    else {
                                        helper.removeClass($cell, selection.CELL_SELECTED_CLS);
                                    }
                                }
                            });
                        });
                        $.data($grid, internal.SELECTED_CELLS, null);
                    }
                    selection.clearAll = clearAll;
                    function cellAt($grid, rowIdx, columnKey) {
                        var $row = rowAt($grid, rowIdx);
                        return getCellInRow($grid, $row, columnKey);
                    }
                    selection.cellAt = cellAt;
                    function rowAt($grid, rowIdx) {
                        var virt = $.data($grid, internal.TANGI);
                        var rows = $grid.getElementsByTagName("tr");
                        var idx;
                        if (!virt) {
                            idx = (parseInt(rowIdx) + 1);
                            if (!rows || rows.length <= idx)
                                return intan.NULL;
                            return rows[idx];
                        }
                        if (virt.startIndex > rowIdx || virt.endIndex < rowIdx)
                            return intan.NULL;
                        idx = (parseInt(rowIdx) - virt.startIndex + 1);
                        return rows[idx];
                    }
                    selection.rowAt = rowAt;
                    function cellOf($grid, rowId, columnKey) {
                        var $row = rowOf($grid, rowId);
                        return getCellInRow($grid, $row, columnKey);
                    }
                    selection.cellOf = cellOf;
                    function rowOf($grid, rowId) {
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        var start = gen.startIndex || 0;
                        var end = gen.endIndex || gen.dataSource.length - 1;
                        for (var i = start; i <= end; i++) {
                            if (gen.dataSource[i][gen.primaryKey] === rowId) {
                                return rowAt($grid, i);
                            }
                        }
                    }
                    selection.rowOf = rowOf;
                    function rowExists($grid, rowIdx) {
                        var virt = $.data($grid, internal.TANGI);
                        if (virt && (virt.startIndex > rowIdx || virt.endIndex < rowIdx))
                            return false;
                        return true;
                    }
                    selection.rowExists = rowExists;
                    function cellInRange($grid, rowIdx, startKey, endKey) {
                        var range = [];
                        var $row = rowAt($grid, rowIdx);
                        if ($row === intan.NULL)
                            return;
                        var colRange = columnIndexRange($grid, startKey, endKey);
                        if (colRange.start === -1 || colRange.end === -1)
                            return;
                        var min = Math.min(colRange.start, colRange.end);
                        var max = Math.max(colRange.start, colRange.end);
                        var $td = selector.queryAll($row, "td").filter(function (e) {
                            return e.style.display !== "none";
                        }).forEach(function (e, index) {
                            if (index >= min && index <= max) {
                                var childCells = e.querySelectorAll("." + render.CHILD_CELL_CLS);
                                if (childCells.length > 0) {
                                    helper.addClass(childCells, selection.CELL_SELECTED_CLS);
                                }
                                else {
                                    helper.addClass(e, selection.CELL_SELECTED_CLS);
                                }
                                addSelect($grid, rowIdx, colRange.columns[index].key, true);
                                range.push($(this));
                            }
                            else if (index > max)
                                return false;
                        });
                        return range;
                    }
                    selection.cellInRange = cellInRange;
                    function getCellInRow($grid, $row, columnKey) {
                        if ($row === intan.NULL || !$row)
                            return intan.NULL;
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var visibleColumns = gen.painter.visibleColumns;
                        var columnIdx;
                        _.forEach(visibleColumns, function (c, idx) {
                            if (c.key === columnKey) {
                                columnIdx = idx;
                                return false;
                            }
                        });
                        if (uk.util.isNullOrUndefined(columnIdx))
                            return intan.NULL;
                        var cells = Array.prototype.slice.call($row.getElementsByTagName("td")).filter(function (c) {
                            return c.style.display !== "none";
                        });
                        return cells[columnIdx];
                    }
                    selection.getCellInRow = getCellInRow;
                    function columnIndexRange($grid, startKey, endKey) {
                        var cloud = $.data($grid, internal.TANGI);
                        var canon = $.data($grid, internal.CANON);
                        var visibleColumns;
                        if (!uk.util.isNullOrUndefined(cloud)) {
                            visibleColumns = cloud.painter.visibleColumns;
                        }
                        else {
                            visibleColumns = canon.painter.visibleColumns;
                        }
                        var startColumnIdx = -1, endColumnIdx = -1;
                        _.forEach(visibleColumns, function (c, idx) {
                            if (c.key === startKey) {
                                startColumnIdx = idx;
                            }
                            if (c.key === endKey) {
                                endColumnIdx = idx;
                            }
                            if (startColumnIdx !== -1 && endColumnIdx !== -1)
                                return false;
                        });
                        return {
                            start: startColumnIdx,
                            end: endColumnIdx,
                            columns: visibleColumns
                        };
                    }
                    selection.columnIndexRange = columnIndexRange;
                    function getSelectedCells($grid) {
                        var selectedCells = $.data($grid, internal.SELECTED_CELLS);
                        var generator = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var dataSource = generator.dataSource;
                        var cells = [];
                        _.forEach(Object.keys(selectedCells), function (rowIdx) {
                            _.forEach(selectedCells[rowIdx], function (colKey) {
                                cells.push(new Cell(rowIdx, colKey, dataSource[rowIdx][colKey]));
                            });
                        });
                        return cells;
                    }
                    selection.getSelectedCells = getSelectedCells;
                    function off($exTable) {
                        if ($.data($exTable, NAMESPACE).updateMode === COPY_PASTE)
                            return;
                        var $detailContent = $exTable.querySelector("." + BODY_PRF + DETAIL);
                        $detailContent.onselectstart = function () {
                            return true;
                        };
                        $detailContent.removeXEventListener(events.MOUSE_DOWN);
                        $detailContent.removeXEventListener(events.MOUSE_UP);
                        $detailContent.removeXEventListener(events.MOUSE_MOVE);
                    }
                    selection.off = off;
                    function focus($grid) {
                        $grid.focus();
                    }
                    selection.focus = focus;
                    function focusLatest($grid) {
                        var latest = $.data($grid, internal.LAST_SELECTED);
                        if (!latest)
                            return;
                        var $cell = selection.cellAt($grid, latest.rowIdx, latest.columnKey);
                        if ($cell === intan.NULL)
                            return;
                        $cell.focus();
                    }
                    selection.focusLatest = focusLatest;
                    function selectRow($grid, rowIndex) {
                        var $row = selection.rowAt($grid, rowIndex);
                        if ($row !== intan.NULL && !$row.classList.contains(NAMESPACE + "-" + intan.BOTTOM_SPACE)) {
                            helper.addClass($row, selection.ROW_SELECTED_CLS);
                        }
                        setTimeout(function () {
                            var tbls = helper.classSiblings($grid, BODY_PRF).filter(function (e) {
                                return !e.classList.contains(BODY_PRF + HORIZONTAL_SUM) && !e.classList.contains(BODY_PRF + LEFT_HORZ_SUM);
                            });
                            tbls.forEach(function (t) {
                                $row = selection.rowAt(t, rowIndex);
                                if ($row !== intan.NULL && !$row.classList.contains(NAMESPACE + "-" + intan.BOTTOM_SPACE)) {
                                    helper.addClass($row, selection.ROW_SELECTED_CLS);
                                }
                            });
                        }, 60);
                        var selectedRows = $.data($grid, internal.SELECTED_ROWS);
                        if (!selectedRows) {
                            selectedRows = {};
                            selectedRows.items = [];
                            selectedRows.items[rowIndex] = true;
                            selectedRows.count = (selectedRows.count || 0) + 1;
                            $.data($grid, internal.SELECTED_ROWS, selectedRows);
                            return;
                        }
                        if (!selectedRows.items) {
                            selectedRows.items = [];
                            selectedRows.items[rowIndex] = true;
                            selectedRows.count = (selectedRows.count || 0) + 1;
                            return;
                        }
                        if (!selectedRows.items[rowIndex]) {
                            selectedRows.items[rowIndex] = true;
                            selectedRows.count = (selectedRows.count || 0) + 1;
                        }
                    }
                    selection.selectRow = selectRow;
                    function deselectRow($grid, rowIndex) {
                        var selectedRows = $.data($grid, internal.SELECTED_ROWS);
                        if (!selectedRows || !selectedRows.items || selectedRows.items.length === 0)
                            return;
                        selectedRows.items[rowIndex] = false;
                        selectedRows.count--;
                        var row = selection.rowAt($grid, rowIndex);
                        if (!row)
                            return;
                        row.classList.remove(selection.ROW_SELECTED_CLS);
                        var tbls = helper.classSiblings($grid, BODY_PRF).filter(function (e) {
                            return !e.classList.contains(BODY_PRF + HORIZONTAL_SUM) && !e.classList.contains(BODY_PRF + LEFT_HORZ_SUM);
                        });
                        tbls.forEach(function (t) {
                            var bodies = selection.rowAt(t, rowIndex);
                            helper.removeClass(bodies, selection.ROW_SELECTED_CLS);
                        });
                    }
                    selection.deselectRow = deselectRow;
                    function tickRows($grid, flag, limit) {
                        var selectedRows = $.data($grid, internal.SELECTED_ROWS);
                        if (!selectedRows || !selectedRows.items || selectedRows.items.length === 0)
                            return;
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var start = limit ? gen.startIndex : 0;
                        var end = limit ? gen.endIndex : selectedRows.items.length - 1;
                        for (var i = start; i <= end; i++) {
                            if (selectedRows.items[i]) {
                                controls.tick(flag, $grid, false, i);
                            }
                        }
                    }
                    selection.tickRows = tickRows;
                })(selection || (selection = {}));
                var resize;
                (function (resize) {
                    resize.AGENCY = "ex-agency";
                    resize.LINE = "ex-line";
                    resize.RESIZE_COL = "resize-column";
                    resize.AREA_AGENCY = "ex-area-agency";
                    resize.RESIZE_AREA = "resize-area";
                    resize.AREA_LINE = "ex-area-line";
                    resize.STAY_CLS = "x-stay";
                    var ColumnAdjuster = (function () {
                        function ColumnAdjuster($headerTable, $contentTable, options) {
                            this.$headerTable = $headerTable;
                            this.$contentTable = $contentTable;
                            this.$ownerDoc = $headerTable.ownerDocument;
                            this.standardCells = [];
                        }
                        ColumnAdjuster.prototype.handle = function () {
                            var self = this;
                            self.$agency = document.createElement("div");
                            self.$agency.className = resize.AGENCY;
                            self.$agency.style.position = "relative";
                            self.$agency.style.width = self.$headerTable.offsetWidth;
                            self.$headerTable.insertAdjacentElement("beforebegin", self.$agency);
                            self.$colGroup = self.$headerTable.querySelectorAll("colgroup > col");
                            var trList = self.$headerTable.querySelectorAll("tbody > tr");
                            var targetColumnIdx = 0;
                            _.forEach(trList, function (tr) {
                                var tdList = tr.querySelectorAll("td");
                                for (var i = 0; i < tdList.length; i++) {
                                    if (self.standardCells.length >= self.$colGroup.length)
                                        return false;
                                    var $td = tdList[i];
                                    var colspan = $td.getAttribute("colspan");
                                    if (!uk.util.isNullOrUndefined(colspan) && parseInt(colspan) > 1)
                                        continue;
                                    self.standardCells.push($td);
                                    var $targetCol = self.$colGroup[targetColumnIdx];
                                    var $line = document.createElement("div");
                                    $line.className = resize.LINE;
                                    $.data($line, resize.RESIZE_COL, $targetCol);
                                    $line.style.position = "absolute";
                                    $line.style.cursor = "ew-resize";
                                    $line.style.width = "4px";
                                    $line.style.zIndex = 2,
                                        $line.style.marginLeft = "-3px";
                                    self.$agency.appendChild($line);
                                    var height = self.$headerTable.style.height;
                                    var left = $td.offsetWidth + (selector.offset($td).left - selector.offset(self.$agency).left);
                                    $line.style.left = left + "px";
                                    $line.style.height = height;
                                    targetColumnIdx++;
                                }
                            });
                            self.$agency.addXEventListener(events.MOUSE_DOWN, self.cursorDown.bind(self));
                        };
                        ColumnAdjuster.prototype.cursorDown = function (event) {
                            var self = this;
                            if (self.actionDetails) {
                                self.cursorUp(event);
                            }
                            var $targetGrip = event.target;
                            var gripIndex = selector.index($targetGrip);
                            var $leftCol = $.data($targetGrip, resize.RESIZE_COL);
                            var $rightCol = self.$colGroup[gripIndex + 1];
                            var leftWidth = $leftCol.style.width;
                            var rightWidth = $rightCol.style.width;
                            self.actionDetails = {
                                $targetGrip: $targetGrip,
                                gripIndex: gripIndex,
                                $leftCol: $leftCol,
                                $rightCol: $rightCol,
                                xCoord: getCursorX(event),
                                widths: {
                                    left: parseFloat(leftWidth),
                                    right: parseFloat(rightWidth)
                                },
                                changedWidths: {
                                    left: parseFloat(leftWidth),
                                    right: parseFloat(rightWidth)
                                }
                            };
                            self.$ownerDoc.addXEventListener(events.MOUSE_MOVE, self.cursorMove.bind(self));
                            self.$ownerDoc.addXEventListener(events.MOUSE_UP, self.cursorUp.bind(self));
                            event.preventDefault();
                        };
                        ColumnAdjuster.prototype.cursorMove = function (event) {
                            var self = this;
                            if (!self.actionDetails)
                                return;
                            var distance = getCursorX(event) - self.actionDetails.xCoord;
                            if (distance === 0)
                                return;
                            var leftWidth, rightWidth;
                            if (distance > 0) {
                                leftWidth = self.actionDetails.widths.left + distance;
                                rightWidth = self.actionDetails.widths.right - distance;
                            }
                            else {
                                leftWidth = self.actionDetails.widths.left + distance;
                                rightWidth = self.actionDetails.widths.right - distance;
                            }
                            if (leftWidth <= 20 || rightWidth <= 20)
                                return;
                            self.actionDetails.changedWidths.left = leftWidth;
                            self.actionDetails.changedWidths.right = rightWidth;
                            if (self.actionDetails.$leftCol) {
                                self.setWidth(self.actionDetails.$leftCol, leftWidth);
                                var $contentLeftCol = self.$contentTable.querySelectorAll("colgroup > col")[self.actionDetails.gripIndex];
                                self.setWidth($contentLeftCol, leftWidth);
                            }
                            if (self.actionDetails.$rightCol) {
                                self.setWidth(self.actionDetails.$rightCol, rightWidth);
                                var $contentRightCol = self.$contentTable.querySelectorAll("colgroup > col")[self.actionDetails.gripIndex + 1];
                                self.setWidth($contentRightCol, rightWidth);
                            }
                        };
                        ColumnAdjuster.prototype.cursorUp = function (event) {
                            var self = this;
                            self.$ownerDoc.removeXEventListener(events.MOUSE_MOVE);
                            self.$ownerDoc.removeXEventListener(events.MOUSE_UP);
                            self.syncLines();
                            self.actionDetails = null;
                        };
                        ColumnAdjuster.prototype.setWidth = function ($col, width) {
                            $col.style.width = parseFloat(width) + "px";
                        };
                        ColumnAdjuster.prototype.syncLines = function () {
                            var self = this;
                            self.$agency.style.width = self.$headerTable.style.width;
                            _.forEach(self.standardCells, function ($td, index) {
                                var height = self.$headerTable.style.height;
                                var left = $td.offsetWidth + (selector.offset($td).left - selector.offset(self.$agency).left);
                                var div = self.$agency.querySelectorAll("div")[index];
                                div.style.left = left + "px";
                                div.style.height = height;
                            });
                        };
                        return ColumnAdjuster;
                    }());
                    resize.ColumnAdjuster = ColumnAdjuster;
                    var AreaAdjuster = (function () {
                        function AreaAdjuster($container, headerWrappers, bodyWrappers, $follower) {
                            this.$container = $container;
                            this.headerWrappers = headerWrappers;
                            this.bodyWrappers = bodyWrappers;
                            this.$ownerDoc = $container.ownerDocument;
                            this.$leftHorzSumHeader = this.$container.querySelector("." + HEADER_PRF + LEFT_HORZ_SUM);
                            this.$leftHorzSumContent = this.$container.querySelector("." + BODY_PRF + LEFT_HORZ_SUM);
                            this.$horzSumHeader = this.$container.querySelector("." + HEADER_PRF + HORIZONTAL_SUM);
                            this.$horzSumContent = this.$container.querySelector("." + BODY_PRF + HORIZONTAL_SUM);
                            if ($follower) {
                                this.$depLeftmostHeader = $follower.querySelector("." + HEADER_PRF + LEFTMOST);
                                this.$depLeftmostBody = $follower.querySelector("." + BODY_PRF + LEFTMOST);
                                this.$depDetailHeader = $follower.querySelector("." + HEADER_PRF + DETAIL);
                                this.$depDetailBody = $follower.querySelector("." + BODY_PRF + DETAIL);
                            }
                        }
                        AreaAdjuster.prototype.handle = function () {
                            var self = this;
                            self.$areaAgency = document.createElement("div");
                            self.$areaAgency.className = resize.AREA_AGENCY;
                            self.$areaAgency.style.position = "relative";
                            self.$areaAgency.style.width = self.$container.style.width;
                            self.headerWrappers[0].insertAdjacentElement("beforebegin", self.$areaAgency);
                            _.forEach(self.headerWrappers, function ($wrapper, index) {
                                if (index === self.headerWrappers.length - 1)
                                    return;
                                var $line = document.createElement("div");
                                $line.className = resize.AREA_LINE;
                                $.data($line, resize.RESIZE_AREA, $wrapper);
                                $line.style.position = "absolute";
                                $line.style.cursor = "ew-resize";
                                $line.style.width = "4px";
                                $line.style.zIndex = 2;
                                $line.style.marginLeft = "0px";
                                self.$areaAgency.appendChild($line);
                                var height = parseFloat($wrapper.style.height) + parseFloat(self.bodyWrappers[index].style.height);
                                var left = $wrapper.offsetWidth + (selector.offset($wrapper).left - selector.offset(self.$areaAgency).left);
                                $line.style.left = left + "px";
                                $line.style.height = height + "px";
                                if ($wrapper.classList.contains(HEADER_PRF + LEFTMOST)
                                    && self.headerWrappers[index + 1].classList.contains(HEADER_PRF + MIDDLE))
                                    helper.addClass($line, resize.STAY_CLS);
                            });
                            self.$areaAgency.addXEventListener(events.MOUSE_DOWN, self.cursorDown.bind(self));
                        };
                        AreaAdjuster.prototype.cursorDown = function (event) {
                            var self = this;
                            if (self.actionDetails) {
                                self.cursorUp(event);
                            }
                            var $targetGrip = event.target;
                            if ($targetGrip.classList.contains(resize.STAY_CLS))
                                return;
                            var gripIndex = selector.index($targetGrip);
                            var $leftArea = $.data($targetGrip, resize.RESIZE_AREA);
                            var $rightArea = self.headerWrappers[gripIndex + 1];
                            var leftWidth = $leftArea.style.width;
                            var rightWidth = !uk.util.isNullOrUndefined($rightArea) ? $rightArea.style.width : 0;
                            var leftHorzSumWidth;
                            if (self.$leftHorzSumHeader) {
                                leftHorzSumWidth = self.$leftHorzSumHeader.style.width;
                            }
                            else if (self.headerWrappers[0].classList.contains(HEADER_PRF + LEFTMOST)) {
                                leftHorzSumWidth = parseFloat(self.headerWrappers[0].style.width);
                                if (self.headerWrappers[1].classList.contains(HEADER_PRF + MIDDLE)) {
                                    leftHorzSumWidth += (parseFloat(self.headerWrappers[1].style.width) + DISTANCE);
                                }
                            }
                            self.actionDetails = {
                                $targetGrip: $targetGrip,
                                gripIndex: gripIndex,
                                $leftArea: $leftArea,
                                $rightArea: $rightArea,
                                xCoord: getCursorX(event),
                                rightAreaPosLeft: $rightArea ? $rightArea.style.left : 0,
                                widths: {
                                    left: parseFloat(leftWidth),
                                    right: parseFloat(rightWidth),
                                    leftHorzSum: parseFloat(leftHorzSumWidth)
                                },
                                changedWidths: {
                                    left: parseFloat(leftWidth),
                                    right: parseFloat(rightWidth),
                                    leftHorzSum: parseFloat(leftHorzSumWidth)
                                }
                            };
                            self.$ownerDoc.addXEventListener(events.MOUSE_MOVE, self.cursorMove.bind(self));
                            self.$ownerDoc.addXEventListener(events.MOUSE_UP, self.cursorUp.bind(self));
                            events.trigger(self.$container, events.AREA_RESIZE_STARTED, [$leftArea, $rightArea, leftWidth, rightWidth]);
                            event.preventDefault();
                        };
                        AreaAdjuster.prototype.cursorMove = function (event) {
                            var self = this;
                            if (!self.actionDetails)
                                return;
                            var distance = getCursorX(event) - self.actionDetails.xCoord;
                            if (distance === 0)
                                return;
                            var $bodyLeftArea, $bodyRightArea, leftWidth, rightWidth;
                            if (distance > 0) {
                                leftWidth = self.actionDetails.widths.left + distance;
                                rightWidth = self.actionDetails.widths.right - distance;
                            }
                            else {
                                leftWidth = self.actionDetails.widths.left + distance;
                                rightWidth = self.actionDetails.widths.right - distance;
                            }
                            if (!self.isResizePermit(leftWidth, rightWidth))
                                return;
                            self.actionDetails.changedWidths.left = leftWidth;
                            self.actionDetails.changedWidths.right = rightWidth;
                            if (self.actionDetails.$leftArea) {
                                self.setWidth(self.actionDetails.$leftArea, leftWidth);
                                $bodyLeftArea = self.bodyWrappers[self.actionDetails.gripIndex];
                                if (self.actionDetails.gripIndex === self.bodyWrappers.length - 1) {
                                    self.setWidth($bodyLeftArea, leftWidth + helper.getScrollWidth());
                                }
                                else {
                                    self.setWidth($bodyLeftArea, leftWidth);
                                }
                            }
                            var newPosLeft;
                            if (self.actionDetails.$rightArea) {
                                self.setWidth(self.actionDetails.$rightArea, rightWidth);
                                newPosLeft = (parseInt(self.actionDetails.rightAreaPosLeft) + distance) + "px";
                                self.actionDetails.$rightArea.style.left = newPosLeft;
                                $bodyRightArea = self.bodyWrappers[self.actionDetails.gripIndex + 1];
                                if (self.actionDetails.gripIndex === self.bodyWrappers.length - 2) {
                                    self.setWidth($bodyRightArea, rightWidth + helper.getScrollWidth());
                                }
                                else {
                                    self.setWidth($bodyRightArea, rightWidth);
                                }
                                $bodyRightArea.style.left = newPosLeft;
                            }
                            self.reflectSumTblsSize(distance, leftWidth, rightWidth, newPosLeft);
                            events.trigger(self.$container, events.AREA_RESIZE, [self.actionDetails.$leftArea, self.actionDetails.$rightArea, leftWidth, rightWidth]);
                        };
                        AreaAdjuster.prototype.reflectSumTblsSize = function (diff, leftWidth, rightWidth, posLeft) {
                            var self = this;
                            var $leftArea = self.actionDetails.$leftArea;
                            var $rightArea = self.actionDetails.$rightArea;
                            var scrollWidth = helper.getScrollWidth();
                            if ($rightArea && $rightArea.classList.contains(HEADER_PRF + DETAIL)) {
                                var horzLeftWidth = self.actionDetails.widths.leftHorzSum + diff;
                                self.setWidth(self.$leftHorzSumHeader, horzLeftWidth);
                                self.setWidth(self.$leftHorzSumContent, horzLeftWidth);
                                self.setWidth(self.$horzSumHeader, rightWidth);
                                self.setWidth(self.$horzSumContent, rightWidth + scrollWidth);
                                if (self.$horzSumHeader) {
                                    self.$horzSumHeader.style.left = posLeft;
                                    self.$horzSumContent.style.left = posLeft;
                                }
                                if (self.$depLeftmostHeader) {
                                    self.setWidth(self.$depLeftmostHeader, horzLeftWidth);
                                    self.setWidth(self.$depLeftmostBody, horzLeftWidth);
                                    self.setWidth(self.$depDetailHeader, rightWidth);
                                    self.setWidth(self.$depDetailBody, rightWidth + scrollWidth);
                                    self.$depDetailHeader.style.left = posLeft;
                                    self.$depDetailBody.style.left = posLeft;
                                }
                            }
                            else if ($leftArea && $leftArea.classList.contains(HEADER_PRF + DETAIL)) {
                                self.setWidth(self.$horzSumHeader, leftWidth);
                                self.setWidth(self.$horzSumContent, leftWidth + scrollWidth);
                                if (self.$depDetailHeader) {
                                    self.setWidth(self.$depDetailHeader, leftWidth);
                                    self.setWidth(self.$depDetailBody, leftWidth + scrollWidth);
                                }
                            }
                        };
                        AreaAdjuster.prototype.isResizePermit = function (leftWidth, rightWidth) {
                            var self = this;
                            var leftAreaMaxWidth = 0, rightAreaMaxWidth = 0;
                            if (leftWidth <= 20 || (self.actionDetails.widths.right > 0 && rightWidth <= 20))
                                return false;
                            if (self.actionDetails.$leftArea) {
                                var leftAreaColGroup = self.actionDetails.$leftArea.querySelectorAll("table > colgroup > col");
                                var size = leftAreaColGroup.length;
                                for (var i = 0; i < size; i++) {
                                    leftAreaMaxWidth += (parseFloat(leftAreaColGroup[i].style.width) + 1);
                                }
                                var maxWidth = parseFloat(self.actionDetails.$leftArea.style.maxWidth);
                                if (leftWidth >= leftAreaMaxWidth
                                    || (!isNaN(maxWidth) && leftWidth >= maxWidth))
                                    return false;
                            }
                            if (self.actionDetails.$rightArea) {
                                var rightAreaColGroup = self.actionDetails.$rightArea.querySelectorAll("table > colgroup > col");
                                var size = rightAreaColGroup.length;
                                for (var i = 0; i < size; i++) {
                                    rightAreaMaxWidth += (parseFloat(rightAreaColGroup[i].style.width) + 1);
                                }
                                var maxWidth = parseFloat(self.actionDetails.$rightArea.style.maxWidth);
                                if (rightWidth >= rightAreaMaxWidth
                                    || (!isNaN(maxWidth) && rightWidth >= maxWidth))
                                    return false;
                            }
                            return true;
                        };
                        AreaAdjuster.prototype.cursorUp = function (event) {
                            var self = this;
                            if (!self.actionDetails)
                                return;
                            self.$ownerDoc.removeXEventListener(events.MOUSE_MOVE);
                            self.$ownerDoc.removeXEventListener(events.MOUSE_UP);
                            self.syncLines();
                            events.trigger(self.$container, events.AREA_RESIZE_END, [self.actionDetails.$leftArea, self.actionDetails.$rightArea,
                                self.actionDetails.changedWidths.left, self.actionDetails.changedWidths.right]);
                            self.actionDetails = null;
                        };
                        AreaAdjuster.prototype.setWidth = function ($wrapper, width) {
                            if (uk.util.isNullOrUndefined($wrapper))
                                return;
                            $wrapper.style.width = parseFloat(width) + "px";
                        };
                        AreaAdjuster.prototype.syncLines = function () {
                            var self = this;
                            self.$areaAgency.style.width = self.$container.style.width;
                            _.forEach(self.headerWrappers, function ($wrapper, index) {
                                var height = parseFloat($wrapper.style.height) + parseFloat(self.bodyWrappers[index].style.height);
                                var left = $wrapper.offsetWidth + (selector.offset($wrapper).left - selector.offset(self.$areaAgency).left);
                                var div = self.$areaAgency.querySelectorAll("div")[index];
                                if (!div)
                                    return false;
                                div.style.left = left + "px";
                                div.style.height = height + "px";
                            });
                        };
                        return AreaAdjuster;
                    }());
                    resize.AreaAdjuster = AreaAdjuster;
                    function getCursorX(event) {
                        return event.pageX;
                    }
                    function lineStyles(marginLeft) {
                        return { position: "absolute", cursor: "ew-resize", width: "4px", zIndex: 2, marginLeft: marginLeft };
                    }
                    function fitWindowHeight($container, wrappers, horzSumExists) {
                        var height = window.innerHeight - parseInt($.data($container, internal.Y_OCCUPY)) - 100;
                        var $horzSumHeader, $horzSumBody, decreaseAmt;
                        wrappers = wrappers || selector.queryAll($container, "div[class*='" + BODY_PRF + "']").filter(function (e) {
                            return !e.classList.contains(BODY_PRF + HORIZONTAL_SUM) && !e.classList.contains(BODY_PRF + LEFT_HORZ_SUM);
                        });
                        if (horzSumExists) {
                            $horzSumHeader = $container.querySelector("." + HEADER_PRF + HORIZONTAL_SUM);
                            $horzSumBody = $container.querySelector("." + BODY_PRF + HORIZONTAL_SUM);
                            decreaseAmt = parseFloat($horzSumHeader.style.height) + parseFloat($horzSumBody.style.height) + DISTANCE + SPACE;
                            height -= decreaseAmt;
                        }
                        _.forEach(wrappers, function ($wrapper) {
                            if (($wrapper.style.overflowX && $wrapper.style.overflowX === "scroll")
                                || ($wrapper.style.overflow && $wrapper.style.overflow === "scroll")) {
                                $wrapper.style.height = height + "px";
                            }
                            else {
                                $wrapper.style.height = (height - helper.getScrollWidth()) + "px";
                            }
                        });
                        if (horzSumExists) {
                            repositionHorzSum($container, $horzSumHeader, $horzSumBody);
                        }
                        var cHeight = 0, showCount = 0;
                        var stream = selector.queryAll($container, "div[class*='" + DETAIL + "'], div[class*='" + LEFT_HORZ_SUM + "']");
                        stream.forEach(function (e) {
                            if (e.style.display !== "none") {
                                showCount++;
                                cHeight += parseFloat(e.style.height);
                            }
                        });
                        if (showCount === 4) {
                            cHeight += (SPACE + DISTANCE);
                        }
                        $container.style.height = (cHeight + SPACE) + "px";
                        events.trigger($container, events.BODY_HEIGHT_CHANGED, height);
                    }
                    resize.fitWindowHeight = fitWindowHeight;
                    function fitWindowWidth($container) {
                        var table = $.data($container, NAMESPACE);
                        if (table.$commander)
                            return;
                        var $vertSumHeader = $container.querySelector("." + HEADER_PRF + VERTICAL_SUM);
                        var $vertSumContent = $container.querySelector("." + BODY_PRF + VERTICAL_SUM);
                        var $detailHeader = $container.querySelector("." + HEADER_PRF + DETAIL);
                        var $detailBody = $container.querySelector("." + BODY_PRF + DETAIL);
                        var width = window.innerWidth - selector.offset($detailHeader).left;
                        var scrollWidth = helper.getScrollWidth();
                        var $sup = table.$follower;
                        if ($vertSumHeader && $vertSumHeader.style.display !== "none") {
                            width = width - parseFloat($.data($container, internal.X_OCCUPY)) - parseFloat($vertSumContent.style.width);
                            if (!uk.util.isNullOrUndefined($detailHeader.style.maxWidth)
                                && width >= parseFloat($detailHeader.style.maxWidth)) {
                                width = parseFloat($detailHeader.style.maxWidth);
                            }
                            $container.style.width = (parseFloat($container.style.width) + (width - parseFloat($detailBody.style.width))) + "px";
                            $detailHeader.style.width = width + "px";
                            $detailBody.style.width = width + "px";
                            if (storage.area.getPartWidths($container).isPresent()) {
                                storage.area.save($container, $.data($detailHeader, internal.EX_PART), width);
                            }
                            var $horzSumHeader_1 = $container.querySelector("." + HEADER_PRF + HORIZONTAL_SUM);
                            if ($horzSumHeader_1) {
                                $horzSumHeader_1.style.width = width + "px";
                            }
                            var $horzSumContent_1 = $container.querySelector("." + BODY_PRF + HORIZONTAL_SUM);
                            if ($horzSumContent_1) {
                                $horzSumContent_1.style.width = (width + helper.getScrollWidth()) + "px";
                            }
                            repositionVertSum($container, $vertSumHeader, $vertSumContent);
                            syncDetailAreaLine($container, $detailHeader, $detailBody);
                            if ($sup) {
                                var $supHeader = $sup.querySelector("." + HEADER_PRF + DETAIL);
                                if ($supHeader) {
                                    $supHeader.style.width = width + "px";
                                    $sup.querySelector("." + BODY_PRF + DETAIL).style.width = (width + scrollWidth) + "px";
                                }
                            }
                            return;
                        }
                        var $horzSumHeader = $container.querySelector("." + HEADER_PRF + HORIZONTAL_SUM);
                        var $horzSumContent = $container.querySelector("." + BODY_PRF + HORIZONTAL_SUM);
                        width = width - parseFloat($.data($container, internal.X_OCCUPY));
                        if (!uk.util.isNullOrUndefined($detailHeader.style.maxWidth)
                            && width >= parseFloat($detailHeader.style.maxWidth)) {
                            width = parseFloat($detailHeader.style.maxWidth);
                        }
                        $detailHeader.style.width = (width - scrollWidth) + "px";
                        $container.style.width = (parseFloat($container.style.width)
                            + (width - parseFloat($detailBody.style.width))) + "px";
                        $detailBody.style.width = width + "px";
                        if (storage.area.getPartWidths($container).isPresent()) {
                            storage.area.save($container, $.data($detailHeader, internal.EX_PART), width);
                        }
                        if ($horzSumHeader && $horzSumHeader.style.display !== "none") {
                            $horzSumHeader.style.width = (width - scrollWidth) + "px";
                            $horzSumContent.style.width = width + "px";
                        }
                        if ($sup) {
                            var $supHeader = $sup.querySelector("." + HEADER_PRF + DETAIL);
                            if ($supHeader) {
                                $supHeader.style.width = (width - scrollWidth) + "px";
                                $sup.querySelector("." + BODY_PRF + DETAIL).style.width = width + "px";
                            }
                        }
                    }
                    resize.fitWindowWidth = fitWindowWidth;
                    function syncDetailAreaLine($container, $detailHeader, $detailBody) {
                        var $agency = $container.querySelector("." + resize.AREA_AGENCY);
                        if (!$agency)
                            return;
                        var height = parseFloat($detailHeader.style.height) + parseFloat($detailBody.style.height);
                        var left = $detailHeader.offsetWidth + (selector.offset($detailHeader).left - selector.offset($agency).left);
                        var index;
                        selector.queryAll($container, "div[class*='" + HEADER_PRF + "']").forEach(function (e, idx) {
                            if (e.classList.contains(HEADER_PRF + DETAIL)) {
                                index = idx;
                                return false;
                            }
                        });
                        var div = $agency.querySelectorAll("div")[index];
                        div.style.left = left + "px";
                        div.style.height = height + "px";
                    }
                    function repositionHorzSum($container, $horzSumHeader, $horzSumBody) {
                        $horzSumHeader = $horzSumHeader || $container.querySelector("." + HEADER_PRF + HORIZONTAL_SUM);
                        $horzSumBody = $horzSumBody || $container.querySelector("." + BODY_PRF + HORIZONTAL_SUM);
                        if (!$horzSumHeader)
                            return;
                        var headerTop = parseFloat($container.querySelector("." + HEADER_PRF + DETAIL).style.height)
                            + parseFloat($container.querySelector("." + BODY_PRF + DETAIL).style.height) + DISTANCE + SPACE;
                        var bodyTop = headerTop + DISTANCE + parseFloat($horzSumHeader.style.height);
                        $container.querySelector("." + HEADER_PRF + LEFT_HORZ_SUM).style.top = headerTop + "px";
                        $container.querySelector("." + BODY_PRF + LEFT_HORZ_SUM).style.top = bodyTop + "px";
                        $horzSumHeader.style.top = headerTop + "px";
                        $horzSumBody.style.top = bodyTop + "px";
                    }
                    resize.repositionHorzSum = repositionHorzSum;
                    function repositionVertSum($container, $vertSumHeader, $vertSumContent) {
                        $vertSumHeader = $vertSumHeader || $container.querySelector("." + HEADER_PRF + VERTICAL_SUM);
                        $vertSumContent = $vertSumContent || $container.querySelector("." + BODY_PRF + VERTICAL_SUM);
                        var $detailHeader = $container.querySelector("." + HEADER_PRF + DETAIL);
                        var posLeft = $detailHeader.style.left;
                        var vertSumLeft = parseFloat(posLeft) + parseFloat($detailHeader.style.width) + DISTANCE;
                        $vertSumHeader.style.left = vertSumLeft + "px";
                        $vertSumContent.style.left = vertSumLeft + "px";
                    }
                    resize.repositionVertSum = repositionVertSum;
                    function setHeight($container, height) {
                        selector.queryAll($container, "div[class*='" + BODY_PRF + "']").forEach(function (e) {
                            if (e.classList.contains(BODY_PRF + HORIZONTAL_SUM) || e.classList.contains(BODY_PRF + LEFT_HORZ_SUM))
                                return;
                            e.style.height = height + "px";
                        });
                        var cHeight = 0, showCount = 0;
                        var stream = selector.queryAll($container, "div[class*='" + DETAIL + "'], div[class*='" + LEFT_HORZ_SUM + "']");
                        stream.forEach(function (e) {
                            if (e.style.display !== "none") {
                                showCount++;
                                cHeight += parseFloat(e.style.height);
                            }
                        });
                        if (showCount === 4) {
                            cHeight += (SPACE + DISTANCE);
                        }
                        $container.style.height = (cHeight + SPACE) + "px";
                        events.trigger($container, events.BODY_HEIGHT_CHANGED, height);
                    }
                    resize.setHeight = setHeight;
                    function onAreaComplete(args) {
                        var self = this;
                        var detail = args.detail;
                        if (self.remainSizes) {
                            saveSizes(self.$container, detail[0], detail[1], detail[2], detail[3]);
                        }
                    }
                    resize.onAreaComplete = onAreaComplete;
                    function saveSizes($container, $leftArea, $rightArea, leftWidth, rightWidth) {
                        if ($leftArea) {
                            storage.area.save($container, $.data($leftArea, internal.EX_PART), leftWidth);
                        }
                        if ($rightArea) {
                            storage.area.save($container, $.data($rightArea, internal.EX_PART), rightWidth);
                        }
                    }
                    resize.saveSizes = saveSizes;
                    function onBodyHeightChanged(event) {
                        var self = this;
                        var $container = event.target;
                        if (self.remainSizes) {
                            var height = event.detail;
                            storage.tableHeight.save($container, height);
                        }
                        repositionHorzSum($container);
                    }
                    resize.onBodyHeightChanged = onBodyHeightChanged;
                })(resize || (resize = {}));
                var storage;
                (function (storage) {
                    storage.AREA_WIDTHS = "areawidths";
                    storage.TBL_HEIGHT = "tableheight";
                    var Store = (function () {
                        function Store() {
                        }
                        Store.prototype.initValueExists = function ($container) {
                            var self = this;
                            var storeKey = self.getStorageKey($container);
                            var value = uk.localStorage.getItem(storeKey);
                            return value.isPresent();
                        };
                        Store.prototype.getStoreItem = function ($container, item) {
                            return uk.request.location.current.rawUrl + "/" + $container.id + "/" + item;
                        };
                        Store.prototype.getValue = function ($container) {
                            var storeKey = this.getStorageKey($container);
                            return uk.localStorage.getItem(storeKey);
                        };
                        return Store;
                    }());
                    var area;
                    (function (area) {
                        var Cache = (function (_super) {
                            __extends(Cache, _super);
                            function Cache() {
                                _super.apply(this, arguments);
                            }
                            Cache.prototype.getStorageKey = function ($container) {
                                return this.getStoreItem($container, storage.AREA_WIDTHS);
                            };
                            return Cache;
                        }(Store));
                        var cache = new Cache();
                        function init($container, parts) {
                            if (cache.initValueExists($container)) {
                                return;
                            }
                            var partWidths = {};
                            _.forEach(parts, function (part, index) {
                                var key = helper.getClassOfHeader(part);
                                partWidths[key] = parseFloat(part.style.width);
                            });
                            saveAll($container, partWidths);
                        }
                        area.init = init;
                        function load($container) {
                            var storeKey = cache.getStorageKey($container);
                            uk.localStorage.getItem(storeKey).ifPresent(function (parts) {
                                var widthParts = JSON.parse(parts);
                                setWidths($container, widthParts);
                                return null;
                            });
                        }
                        area.load = load;
                        function save($container, keyClass, partWidth) {
                            var storeKey = cache.getStorageKey($container);
                            var partsWidth = uk.localStorage.getItem(storeKey);
                            var widths = {};
                            if (partsWidth.isPresent()) {
                                widths = JSON.parse(partsWidth.get());
                                widths[keyClass] = partWidth;
                            }
                            else {
                                widths[keyClass] = partWidth;
                            }
                            uk.localStorage.setItemAsJson(storeKey, widths);
                        }
                        area.save = save;
                        function saveAll($container, widths) {
                            var storeKey = cache.getStorageKey($container);
                            var partWidths = uk.localStorage.getItem(storeKey);
                            if (!partWidths.isPresent()) {
                                uk.localStorage.setItemAsJson(storeKey, widths);
                            }
                        }
                        function getPartWidths($container) {
                            return cache.getValue($container);
                        }
                        area.getPartWidths = getPartWidths;
                        function setWidths($container, parts) {
                            var partKeys = Object.keys(parts);
                            _.forEach(partKeys, function (keyClass, index) {
                                setWidth($container, keyClass, parts[keyClass]);
                            });
                        }
                        function setWidth($container, keyClass, width) {
                            selector.find($container, "." + keyClass).width(width);
                            selector.find($container, "." + Connector[keyClass]).width(width);
                        }
                    })(area = storage.area || (storage.area = {}));
                    var tableHeight;
                    (function (tableHeight) {
                        var Cache2 = (function (_super) {
                            __extends(Cache2, _super);
                            function Cache2() {
                                _super.apply(this, arguments);
                            }
                            Cache2.prototype.getStorageKey = function ($container) {
                                return this.getStoreItem($container, storage.TBL_HEIGHT);
                            };
                            return Cache2;
                        }(Store));
                        var cache = new Cache2();
                        function init($container) {
                            if (cache.initValueExists($container)) {
                                return;
                            }
                            var $bodies = $container.querySelectorAll("div[class*='" + BODY_PRF + "']");
                            if ($bodies.length === 0)
                                return;
                            save($container, parseFloat($bodies[0].style.height));
                        }
                        tableHeight.init = init;
                        function load($container) {
                            var storeKey = cache.getStorageKey($container);
                            uk.localStorage.getItem(storeKey).ifPresent(function (height) {
                                var h = JSON.parse(height);
                                resize.setHeight($container, height);
                                return null;
                            });
                        }
                        tableHeight.load = load;
                        function get($container) {
                            return cache.getValue($container);
                        }
                        tableHeight.get = get;
                        function save($container, height) {
                            var storeKey = cache.getStorageKey($container);
                            uk.localStorage.setItemAsJson(storeKey, height);
                        }
                        tableHeight.save = save;
                    })(tableHeight = storage.tableHeight || (storage.tableHeight = {}));
                })(storage || (storage = {}));
                var scroll;
                (function (scroll) {
                    scroll.SCROLL_SYNCING = "scroll-syncing";
                    scroll.VERT_SCROLL_SYNCING = "vert-scroll-syncing";
                    function bindVertWheel($container, showY) {
                        var $_container = $($container);
                        $container.addXEventListener(events.MOUSE_WHEEL, function (event) {
                            var delta = event.deltaY;
                            var direction = delta < 0 ? -1 : 1;
                            var value = $_container.scrollTop();
                            var os = helper.isIE() ? 25 : 50;
                            $_container.scrollTop(value + direction * os);
                            event.preventDefault();
                            event.stopImmediatePropagation();
                        });
                        if (!showY && $container.style.overflowY !== "hidden") {
                            $container.style.overflowY = "hidden";
                        }
                    }
                    scroll.bindVertWheel = bindVertWheel;
                    function unbindVertWheel($container) {
                        $container.removeXEventListener(events.MOUSE_WHEEL);
                        $container.style.overflowY = "scroll";
                    }
                    scroll.unbindVertWheel = unbindVertWheel;
                    function syncDoubDirHorizontalScrolls(wrappers) {
                        _.forEach(wrappers, function ($main, index) {
                            if (!$main)
                                return;
                            $main.addXEventListener(events.SCROLL_EVT, function () {
                                _.forEach(wrappers, function ($depend, i) {
                                    if (i === index || !$depend)
                                        return;
                                    var mainSyncing = $.data($main, scroll.SCROLL_SYNCING);
                                    if (!mainSyncing) {
                                        $.data($depend, scroll.SCROLL_SYNCING, true);
                                        $depend.scrollLeft = $main.scrollLeft;
                                    }
                                });
                                $.data($main, scroll.SCROLL_SYNCING, false);
                            });
                        });
                    }
                    scroll.syncDoubDirHorizontalScrolls = syncDoubDirHorizontalScrolls;
                    function syncDoubDirVerticalScrolls(wrappers) {
                        _.forEach(wrappers, function ($main, index) {
                            $main.addXEventListener(events.SCROLL_EVT, function (event) {
                                _.forEach(wrappers, function ($depend, i) {
                                    if (i === index)
                                        return;
                                    var mainSyncing = $.data($main, scroll.VERT_SCROLL_SYNCING);
                                    if (!mainSyncing) {
                                        $.data($depend, scroll.VERT_SCROLL_SYNCING, true);
                                        $depend.scrollTop = $main.scrollTop;
                                    }
                                });
                                $.data($main, scroll.VERT_SCROLL_SYNCING, false);
                            });
                        });
                    }
                    scroll.syncDoubDirVerticalScrolls = syncDoubDirVerticalScrolls;
                    function syncHorizontalScroll($headerWrap, $bodyWrap) {
                        $bodyWrap.addXEventListener(events.SCROLL_EVT, function () {
                            $headerWrap.scrollLeft = $bodyWrap.scrollLeft;
                        });
                    }
                    scroll.syncHorizontalScroll = syncHorizontalScroll;
                    function syncVerticalScroll($pivotBody, bodyWraps) {
                        $pivotBody.addXEventListener(events.SCROLL_EVT, function () {
                            _.forEach(bodyWraps, function (body) {
                                body.scrollTop = $pivotBody.scrollTop;
                            });
                        });
                    }
                    scroll.syncVerticalScroll = syncVerticalScroll;
                })(scroll || (scroll = {}));
                var controls;
                (function (controls) {
                    controls.LINK_BUTTON = "link";
                    controls.LINK_CLS = "x-link";
                    controls.CHECKED_KEY = "xCheckbox";
                    controls.CHECKBOX_COL_WIDTH = 40;
                    function check(td, column, data, action) {
                        if (!uk.util.isNullOrUndefined(column.control)) {
                            switch (column.control) {
                                case controls.LINK_BUTTON:
                                    var a = document.createElement("a");
                                    a.classList.add(controls.LINK_CLS);
                                    a.addXEventListener(events.CLICK_EVT, function (evt) {
                                        action();
                                    });
                                    a.innerText = data;
                                    td.appendChild(a);
                                    break;
                            }
                        }
                    }
                    controls.check = check;
                    function checkHeader(td, column, data, action) {
                        if (column.headerControl) {
                            switch (column.headerControl) {
                                case controls.LINK_BUTTON:
                                    var a = document.createElement("a");
                                    a.classList.add(controls.LINK_CLS);
                                    a.addXEventListener(events.CLICK_EVT, function (evt) {
                                        action();
                                    });
                                    a.innerHTML = data;
                                    td.appendChild(a);
                                    break;
                            }
                        }
                    }
                    controls.checkHeader = checkHeader;
                    function addCheckBoxDef(arr) {
                        _.forEach(arr, function (opt) {
                            opt.columns.unshift({ key: controls.CHECKED_KEY, headerText: controls.CHECKED_KEY, width: controls.CHECKBOX_COL_WIDTH + "px" });
                        });
                    }
                    controls.addCheckBoxDef = addCheckBoxDef;
                    function createCheckBox($grid, ui) {
                        var checkBoxText;
                        var $wrapper = document.createElement("div");
                        $wrapper.className = "nts-checkbox-container";
                        $wrapper.addXEventListener(events.CLICK_EVT, function (e) {
                            if ($grid && errors.occurred(helper.closest($grid, "." + NAMESPACE)))
                                e.preventDefault();
                        });
                        var $checkBoxLabel = document.createElement("label");
                        $checkBoxLabel.className = "ntsCheckBox";
                        var $checkBox = document.createElement("input");
                        $checkBox.type = "checkbox";
                        $checkBox.addXEventListener("change", function () {
                            var cellCoord = helper.getCellCoord(helper.closest($checkBox, "td"));
                            var rowIndex = 0;
                            if (cellCoord)
                                rowIndex = cellCoord.rowIdx;
                            ui.onChecked(selector.is($checkBox, ":checked"), rowIndex);
                        });
                        $checkBoxLabel.appendChild($checkBox);
                        var $box = document.createElement("span");
                        $box.className = "box";
                        $checkBoxLabel.appendChild($box);
                        if (ui.text && ui.text.length > 0) {
                            var label = document.createElement("span");
                            label.className = "label";
                            label.textContent = ui.text;
                            $checkBoxLabel.appendChild(label);
                        }
                        $wrapper.appendChild($checkBoxLabel);
                        var checked = ui.initValue !== undefined ? ui.initValue : true;
                        var $checkBox = $wrapper.querySelector("input[type='checkbox']");
                        if (checked === true)
                            $checkBox.checked = true;
                        else
                            $checkBox.checked = false;
                        return $wrapper;
                    }
                    controls.createCheckBox = createCheckBox;
                    function checkBoxCellStyles() {
                        return { padding: "1px 1px", textAlign: "center" };
                    }
                    controls.checkBoxCellStyles = checkBoxCellStyles;
                    function tick(checked, $grid, isHeader, rowIdx) {
                        var $checkBox;
                        var ds = internal.getDataSource($grid);
                        if (isHeader) {
                            selector.queryAll($grid, "tr").forEach(function (r) {
                                var td = r.querySelectorAll("td")[0];
                                if (!td)
                                    return;
                                $checkBox = td.querySelector("input");
                                if (checked) {
                                    $checkBox.checked = true;
                                }
                                else {
                                    $checkBox.checked = false;
                                }
                            });
                            var rows = $.data($grid, internal.SELECTED_ROWS);
                            if (checked) {
                                if (!rows) {
                                    rows = {};
                                    rows.selectAll = true;
                                    $.data($grid, internal.SELECTED_ROWS, rows);
                                }
                                else {
                                    rows.selectAll = true;
                                }
                                for (var i = 0; i < ds.length; i++) {
                                    selection.selectRow($grid, i);
                                }
                            }
                            else {
                                rows.selectAll = false;
                                for (var i = 0; i < ds.length; i++) {
                                    selection.deselectRow($grid, i);
                                }
                            }
                        }
                        else {
                            var $row = selection.rowAt($grid, rowIdx);
                            if (!$row)
                                return;
                            var $cells = $row.querySelectorAll("td");
                            if (!$cells || $cells.length === 0)
                                return;
                            $checkBox = $cells[0].querySelector("input");
                            if (checked) {
                                $checkBox.checked = true;
                                selection.selectRow($grid, rowIdx);
                            }
                            else {
                                $checkBox.checked = false;
                                selection.deselectRow($grid, rowIdx);
                            }
                            var rows = $.data($grid, internal.SELECTED_ROWS);
                            var $allBox = selector.classSiblings($grid, HEADER_PRF + LEFTMOST)[0].querySelectorAll("table tr")[0]
                                .querySelectorAll("td")[0].querySelector("input");
                            if (rows.count === ds.length) {
                                rows.selectAll = true;
                                if (!selector.is($allBox, ":checked"))
                                    $allBox.checked = true;
                            }
                            else {
                                rows.selectAll = false;
                                if (selector.is($allBox, ":checked"))
                                    $allBox.checked = false;
                            }
                        }
                    }
                    controls.tick = tick;
                })(controls || (controls = {}));
                var events;
                (function (events) {
                    events.SCROLL_EVT = "scroll";
                    events.CLICK_EVT = "click";
                    events.MOUSE_DOWN = "mousedown";
                    events.MOUSE_MOVE = "mousemove";
                    events.MOUSE_UP = "mouseup";
                    events.MOUSE_OVER = "mouseover";
                    events.MOUSE_ENTER = "mouseenter";
                    events.MOUSE_OUT = "mouseout";
                    events.MOUSE_LEAVE = "mouseleave";
                    events.FOCUS_IN = "focusin";
                    events.PASTE = "paste";
                    events.MOUSE_WHEEL = "wheel";
                    events.RESIZE = "resize";
                    events.KEY_DOWN = "keydown";
                    events.KEY_UP = "keyup";
                    events.CM = "contextmenu";
                    events.AREA_RESIZE_STARTED = "extablearearesizestarted";
                    events.AREA_RESIZE = "extablearearesize";
                    events.AREA_RESIZE_END = "extablearearesizeend";
                    events.BODY_HEIGHT_CHANGED = "extablebodyheightchanged";
                    events.OCCUPY_UPDATE = "extableoccupyupdate";
                    events.START_EDIT = "extablestartedit";
                    events.STOP_EDIT = "extablestopedit";
                    events.CELL_UPDATED = "extablecellupdated";
                    events.ROW_UPDATED = "extablerowupdated";
                    events.POPUP_SHOWN = "xpopupshown";
                    events.POPUP_INPUT_END = "xpopupinputend";
                    events.ROUND_RETREAT = "extablecellretreat";
                    events.CHECK_ALL = "extableselectallrows";
                    events.CHECK_ROW = "extableselectrow";
                    events.MOUSEIN_COLUMN = "extablemouseincolumn";
                    events.MOUSEOUT_COLUMN = "extablemousoutcolumn";
                    events.RENDERED = "extablerowsrendered";
                    events.COMPLETED = "extablecompleted";
                    window.addXEventListener = document.addXEventListener = Element.prototype.addXEventListener = addEventListener;
                    window.removeXEventListener = document.removeXEventListener = Element.prototype.removeXEventListener = removeEventListener;
                    function trigger($target, eventName, args) {
                        var event;
                        if (window.CustomEvent) {
                            event = new CustomEvent(eventName, { detail: args });
                        }
                        else {
                            event = document.createEvent('CustomEvent');
                            event.initCustomEvent(eventName, true, true, args);
                        }
                        $target.dispatchEvent(event);
                    }
                    events.trigger = trigger;
                    function addEventListener(event, cb, opts) {
                        var self = this;
                        if (!self.ns)
                            self.ns = {};
                        if (!self.ns[event])
                            self.ns[event] = [cb];
                        else
                            self.ns[event].push(cb);
                        self.addEventListener(event.split(".")[0], cb, opts);
                    }
                    ;
                    function removeEventListener(event, cb) {
                        var self = this;
                        if (!self.ns)
                            return;
                        if (cb) {
                            var keys = Object.keys(self.ns).filter(function (k) {
                                return (k === event || k === event.split(".")[0])
                                    && self.ns[k].indexOf(cb) > -1;
                            });
                            var key = void 0;
                            if (keys.length > 0) {
                                key = keys[0];
                                self.ns[key].splice(self.ns[key].indexOf(cb), 1);
                                if (self.ns[key].length === 0)
                                    delete self.ns[key];
                            }
                            self.removeEventListener(event.split(".")[0], cb);
                            return;
                        }
                        if (!self.ns[event])
                            return;
                        self.ns[event].forEach(function (e) {
                            self.removeEventListener(event.split(".")[0], e);
                        });
                        delete self.ns[event];
                    }
                    function onModify($exTable) {
                        $exTable.addXEventListener(events.CELL_UPDATED, function (evt) {
                            var exTable = $.data($exTable, NAMESPACE);
                            if (!exTable)
                                return;
                            var ui = evt.detail;
                            if (ui.value.constructor === Array && (uk.util.isNullOrUndefined(ui.innerIdx) || ui.innerIdx === -1)) {
                                pushChange(exTable, ui.rowIndex, new selection.Cell(ui.rowIndex, ui.columnKey, ui.value[0], 0));
                                pushChange(exTable, ui.rowIndex, new selection.Cell(ui.rowIndex, ui.columnKey, ui.value[1], 1));
                                return;
                            }
                            pushChange(exTable, ui.rowIndex, ui);
                        });
                        $exTable.addXEventListener(events.ROW_UPDATED, function (evt) {
                            var exTable = $.data($exTable, NAMESPACE);
                            if (!exTable)
                                return;
                            var ui = evt.detail;
                            var cells = [];
                            _.forEach(Object.keys(ui.data), function (k, i) {
                                if (ui.data[k].constructor === Array && ui.data[k].length === 2) {
                                    cells.push(new selection.Cell(ui.rowIndex, k, ui.data[k][0], 0));
                                    cells.push(new selection.Cell(ui.rowIndex, k, ui.data[k][1], 1));
                                    return;
                                }
                                cells.push(new selection.Cell(ui.rowIndex, k, ui.data[k], -1));
                            });
                            _.forEach(cells, function (c, i) {
                                pushChange(exTable, c.rowIndex, c);
                            });
                        });
                    }
                    events.onModify = onModify;
                    function pushChange(exTable, rowIdx, cell) {
                        var modifies = exTable.modifications;
                        if (!modifies) {
                            exTable.modifications = {};
                            exTable.modifications[rowIdx] = [cell];
                            return;
                        }
                        if (!modifies[rowIdx]) {
                            modifies[rowIdx] = [cell];
                            return;
                        }
                        var rData = modifies[rowIdx];
                        var cellUpdated = false;
                        _.forEach(rData, function (c, i) {
                            if (helper.areSameCells(c, cell)) {
                                rData[i].value = cell.value;
                                cellUpdated = true;
                                return false;
                            }
                        });
                        if (!cellUpdated) {
                            modifies[rowIdx].push(cell);
                        }
                    }
                    function createRowUi(rowIdx, data) {
                        return {
                            rowIndex: rowIdx,
                            data: data
                        };
                    }
                    events.createRowUi = createRowUi;
                })(events || (events = {}));
                var feature;
                (function (feature_1) {
                    feature_1.UPDATING = "Updating";
                    feature_1.HEADER_ROW_HEIGHT = "HeaderRowHeight";
                    feature_1.HEADER_CELL_STYLE = "HeaderCellStyle";
                    feature_1.HEADER_POP_UP = "HeaderPopups";
                    feature_1.BODY_CELL_STYLE = "BodyCellStyle";
                    feature_1.COLUMN_RESIZE = "ColumnResize";
                    feature_1.TIME_RANGE = "TimeRange";
                    function isEnable(features, name) {
                        return _.find(features, function (feature) {
                            return feature.name === name;
                        }) !== undefined;
                    }
                    feature_1.isEnable = isEnable;
                    function find(features, name) {
                        return _.find(features, function (feature) {
                            return feature.name === name;
                        });
                    }
                    feature_1.find = find;
                })(feature || (feature = {}));
                var style;
                (function (style) {
                    style.DET_CLS = "xdet";
                    style.HIDDEN_CLS = "xhidden";
                    style.SEAL_CLS = "xseal";
                    var CellStyleParam = (function () {
                        function CellStyleParam($cell, cellData, rowData, rowIdx, columnKey) {
                            this.$cell = $cell;
                            this.cellData = cellData;
                            this.rowData = rowData;
                            this.rowIdx = rowIdx;
                            this.columnKey = columnKey;
                        }
                        return CellStyleParam;
                    }());
                    style.CellStyleParam = CellStyleParam;
                    var Cell = (function () {
                        function Cell(rowIndex, columnKey, makeup) {
                            this.rowIndex = rowIndex;
                            this.columnKey = columnKey;
                            this.makeup = makeup;
                        }
                        return Cell;
                    }());
                    style.Cell = Cell;
                    function detColumn($grid, row, rowIdx) {
                        var $tbl = helper.closest($grid, "." + NAMESPACE);
                        var detOpt = $.data($tbl, NAMESPACE).determination;
                        if (!detOpt || !$grid.classList.contains(HEADER_PRF + DETAIL))
                            return;
                        _.forEach(detOpt.rows, function (i) {
                            if (i === rowIdx) {
                                row.addXEventListener(events.MOUSE_DOWN, function (evt) {
                                    if (!evt.ctrlKey)
                                        return;
                                    var $main = helper.getMainTable($tbl);
                                    var gen = $.data($main, internal.TANGI) || $.data($main, internal.CANON);
                                    var ds = gen.dataSource;
                                    var primaryKey = helper.getPrimaryKey($main);
                                    var start = gen.startIndex || 0;
                                    var end = gen.endIndex || ds.length - 1;
                                    var $hCell = evt.target;
                                    var coord = helper.getCellCoord($hCell);
                                    var det = $.data($main, internal.DET);
                                    if (!det) {
                                        det = {};
                                    }
                                    var xRows = [];
                                    var xCellsInColumn = _.filter(ds, function (r, i) {
                                        if (helper.isXCell($main, r[primaryKey], coord.columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) {
                                            xRows.push(i);
                                            return true;
                                        }
                                        return false;
                                    });
                                    var rows = Object.keys(det);
                                    if (rows.length >= (ds.length - xCellsInColumn.length)) {
                                        var flaw_1 = false;
                                        var indices_1 = {};
                                        _.forEach(rows, function (k, i) {
                                            var found = false;
                                            _.forEach(det[k], function (c, j) {
                                                if (c === coord.columnKey) {
                                                    indices_1[k] = j;
                                                    found = true;
                                                    return false;
                                                }
                                            });
                                            if (!found && !xRows.some(function (val) { return parseInt(k) === val; })) {
                                                flaw_1 = true;
                                                return false;
                                            }
                                        });
                                        if (!flaw_1) {
                                            var rKeys = Object.keys(indices_1);
                                            _.forEach(rKeys, function (k, i) {
                                                var col = det[k].splice(indices_1[k], 1);
                                                if (det[k].length === 0)
                                                    delete det[k];
                                                var $c = selection.cellAt($main, k, col[0]);
                                                if ($c)
                                                    helper.stripCellWith(style.DET_CLS, $c);
                                            });
                                            return;
                                        }
                                    }
                                    _.forEach(ds, function (item, index) {
                                        if (index >= start && index < end) {
                                            var $c = selection.cellAt($main, index, coord.columnKey);
                                            if ($c === intan.NULL || !$c || !helper.isDetable($c))
                                                return;
                                            helper.markCellWith(style.DET_CLS, $c);
                                        }
                                        else if (helper.isXCell($main, item[primaryKey], coord.columnKey, style.HIDDEN_CLS, style.SEAL_CLS))
                                            return;
                                        if (!det[index]) {
                                            det[index] = [coord.columnKey];
                                            $.data($main, internal.DET, det);
                                        }
                                        else {
                                            var dup_1;
                                            _.forEach(det[index], function (key) {
                                                if (key === coord.columnKey) {
                                                    dup_1 = true;
                                                    return false;
                                                }
                                            });
                                            if (!dup_1) {
                                                det[index].push(coord.columnKey);
                                            }
                                        }
                                    });
                                });
                                return false;
                            }
                        });
                    }
                    style.detColumn = detColumn;
                    function detCell($grid, $cell, rowIdx, columnKey) {
                        var $tbl = helper.closest($grid, "." + NAMESPACE);
                        var detOpt = $.data($tbl, NAMESPACE).determination;
                        if (!detOpt)
                            return;
                        if ($grid.classList.contains(BODY_PRF + LEFTMOST)) {
                            _.forEach(detOpt.columns, function (key) {
                                if (key === columnKey) {
                                    $cell.addXEventListener(events.MOUSE_DOWN, function (evt) {
                                        if (!evt.ctrlKey)
                                            return;
                                        var $main = helper.getMainTable($tbl);
                                        var coord = helper.getCellCoord($cell);
                                        var $targetRow = selection.rowAt($main, coord.rowIdx);
                                        if ($targetRow === intan.NULL || !$targetRow)
                                            return;
                                        var colKeys = _.map(helper.gridVisibleColumns($main), "key");
                                        var det = $.data($main, internal.DET);
                                        var rowDet;
                                        var undetables = [];
                                        var detables = selector.queryAll($targetRow, "td").filter(function (e) {
                                            return e.style.display !== "none";
                                        }).filter(function (e, i) {
                                            if (!helper.isDetable(e)) {
                                                undetables.push(i);
                                                return false;
                                            }
                                            return true;
                                        });
                                        for (var i = undetables.length - 1; i >= 0; i--) {
                                            colKeys.splice(undetables[i], 1);
                                        }
                                        if (det && (rowDet = det[coord.rowIdx]) && rowDet.length === colKeys.length) {
                                            helper.stripCellsWith(style.DET_CLS, selector.queryAll($targetRow, "td").filter(function (e) {
                                                return e.style.display !== "none";
                                            }));
                                            delete det[coord.rowIdx];
                                            return;
                                        }
                                        helper.markCellsWith(style.DET_CLS, detables);
                                        if (!det) {
                                            det = {};
                                            det[coord.rowIdx] = colKeys;
                                            $.data($main, internal.DET, det);
                                        }
                                        else if (!det[coord.rowIdx]) {
                                            det[coord.rowIdx] = colKeys;
                                        }
                                        else {
                                            var dup_2;
                                            _.forEach(colKeys, function (k) {
                                                dup_2 = false;
                                                _.forEach(det[coord.rowIdx], function (existedKey) {
                                                    if (existedKey === k) {
                                                        dup_2 = true;
                                                        return false;
                                                    }
                                                });
                                                if (!dup_2) {
                                                    det[coord.rowIdx].push(k);
                                                }
                                            });
                                        }
                                    });
                                    return false;
                                }
                            });
                        }
                        else if ($grid.classList.contains(BODY_PRF + DETAIL)) {
                            var childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                            var target = $cell;
                            if (childCells.length > 0) {
                                target = childCells;
                                _.forEach(Array.prototype.slice.call(childCells), function (c) {
                                    c.addXEventListener(events.MOUSE_DOWN, function (evt) {
                                        onDetSingleCell(evt, $tbl, $cell, rowIdx, columnKey);
                                    });
                                });
                                return;
                            }
                            target.addXEventListener(events.MOUSE_DOWN, function (evt) {
                                onDetSingleCell(evt, $tbl, $cell, rowIdx, columnKey);
                            });
                        }
                    }
                    style.detCell = detCell;
                    function onDetSingleCell(evt, $tbl, $cell, rowIdx, columnKey) {
                        if (!evt.ctrlKey || !helper.isDetable($cell))
                            return;
                        var $main = helper.getMainTable($tbl);
                        var det = $.data($main, internal.DET);
                        if (!det) {
                            det = {};
                            det[rowIdx] = [columnKey];
                            $.data($main, internal.DET, det);
                        }
                        else if (!det[rowIdx]) {
                            det[rowIdx] = [columnKey];
                        }
                        else {
                            var dup_3 = -1;
                            _.forEach(det[rowIdx], function (key, index) {
                                if (key === columnKey) {
                                    dup_3 = index;
                                    return false;
                                }
                            });
                            if (dup_3 > -1) {
                                var a = [];
                                det[rowIdx].splice(dup_3, 1);
                                if (det[rowIdx].length === 0)
                                    delete det[rowIdx];
                                helper.stripCellWith(style.DET_CLS, $cell);
                                return;
                            }
                            det[rowIdx].push(columnKey);
                        }
                        helper.markCellWith(style.DET_CLS, $cell);
                    }
                })(style || (style = {}));
                var func;
                (function (func) {
                    var LEFT_TBL = "leftmost";
                    var HORZ_SUM = "horizontalSummaries";
                    var VERT_SUM = "verticalSummaries";
                    $.fn.exTable = function (name) {
                        var params = [];
                        for (var _i = 1; _i < arguments.length; _i++) {
                            params[_i - 1] = arguments[_i];
                        }
                        var self = this;
                        switch (name) {
                            case "setHeight":
                                resize.setHeight(self, params[0]);
                                break;
                            case "gridHeightMode":
                                changeGridHeightMode(self, params[0]);
                                break;
                            case "hideHorizontalSummary":
                                hideHorzSum(self);
                                break;
                            case "showHorizontalSummary":
                                showHorzSum(self);
                                break;
                            case "hideVerticalSummary":
                                hideVertSum(self);
                                break;
                            case "showVerticalSummary":
                                showVertSum(self);
                                break;
                            case "updateTable":
                                updateTable(self, params[0], params[1], params[2], params[3]);
                                break;
                            case "updateMode":
                                return setUpdateMode(self, params[0], params[1]);
                            case "viewMode":
                                return setViewMode(self, params[0], params[1], params[2]);
                            case "mode":
                                setMode(self, params[0], params[1], params[2]);
                                break;
                            case "pasteOverWrite":
                                setPasteOverWrite(self, params[0]);
                                break;
                            case "stickOverWrite":
                                setStickOverWrite(self, params[0]);
                                break;
                            case "stickMode":
                                setStickMode(self, params[0]);
                                break;
                            case "stickData":
                                setStickData(self, params[0]);
                                break;
                            case "stickValidate":
                                setStickValidate(self, params[0]);
                                break;
                            case "stickStyler":
                                setStickStyler(self, params[0]);
                                break;
                            case "stickUndo":
                                undoStick(self);
                                break;
                            case "clearHistories":
                                clearHistories(self, params[0]);
                                break;
                            case "lockCell":
                                lockCell(self, params[0], params[1]);
                                break;
                            case "unlockCell":
                                unlockCell(self, params[0], params[1]);
                                break;
                            case "popupValue":
                                returnPopupValue(self, params[0]);
                                break;
                            case "cellValue":
                                setCellValue(self, params[0], params[1], params[2], params[3]);
                                break;
                            case "cellValueByIndex":
                                setCellValueByIndex(self, params[0], params[1], params[2], params[3]);
                                break;
                            case "roundGet":
                                roundRetreat(self, params[0]);
                                break;
                            case "dataSource":
                                return getDataSource(self, params[0]);
                            case "cellByIndex":
                                return getCellByIndex(self, params[0], params[1]);
                            case "cellById":
                                return getCellById(self, params[0], params[1]);
                            case "updatedCells":
                                return getUpdatedCells(self);
                            case "lockCells":
                                return getLockCells(self);
                            case "addNewRow":
                                break;
                            case "removeRows":
                                break;
                            case "rowId":
                                setRowId(self, params[0], params[1]);
                                break;
                            case "saveScroll":
                                saveScroll(self);
                                break;
                            case "scrollBack":
                                scrollBack(self, params[0]);
                                break;
                        }
                    };
                    function changeGridHeightMode($container, mode) {
                        if (mode === DYNAMIC) {
                            var bodyWrappers_1 = [], horzSumExists_1 = false;
                            var $bodyWrappers = $container.find("div[class*='" + BODY_PRF + "']").each(function () {
                                if ($(this).hasClass(BODY_PRF + HORIZONTAL_SUM) || $(this).hasClass(BODY_PRF + LEFT_HORZ_SUM)) {
                                    horzSumExists_1 = true;
                                    return;
                                }
                                bodyWrappers_1.push($(this));
                            });
                            $(window).on(events.RESIZE, $.proxy(resize.fitWindowHeight, undefined, $container[0], bodyWrappers_1, horzSumExists_1));
                        }
                        else {
                            $(window).off(events.RESIZE, resize.fitWindowHeight);
                        }
                    }
                    function hideHorzSum($container) {
                        $container.find("." + HEADER_PRF + LEFT_HORZ_SUM).hide();
                        $container.find("." + BODY_PRF + LEFT_HORZ_SUM).hide();
                        $container.find("." + HEADER_PRF + HORIZONTAL_SUM).hide();
                        $container.find("." + BODY_PRF + HORIZONTAL_SUM).hide();
                        resize.fitWindowHeight($container[0], undefined, false);
                    }
                    function showHorzSum($container) {
                        $container.find("." + HEADER_PRF + LEFT_HORZ_SUM).show();
                        $container.find("." + BODY_PRF + LEFT_HORZ_SUM).show();
                        $container.find("." + HEADER_PRF + HORIZONTAL_SUM).show();
                        $container.find("." + BODY_PRF + HORIZONTAL_SUM).show();
                        resize.fitWindowHeight($container[0], undefined, true);
                    }
                    function hideVertSum($container) {
                        $container.find("." + HEADER_PRF + VERTICAL_SUM).hide();
                        $container.find("." + BODY_PRF + VERTICAL_SUM).hide();
                        resize.fitWindowWidth($container[0]);
                        scroll.unbindVertWheel($container.find("." + BODY_PRF + DETAIL)[0]);
                    }
                    function showVertSum($container) {
                        var $vertSumBody = $container.find("." + BODY_PRF + VERTICAL_SUM);
                        var $detailBody = $container.find("." + BODY_PRF + DETAIL);
                        $container.find("." + HEADER_PRF + VERTICAL_SUM).show();
                        $vertSumBody.show();
                        resize.fitWindowWidth($container[0]);
                        scroll.bindVertWheel($detailBody[0]);
                        $vertSumBody.scrollTop($detailBody.scrollTop());
                    }
                    function updateTable($container, name, header, body, keepStates) {
                        switch (name) {
                            case "leftmost":
                                updateLeftmost($container, header, body);
                                break;
                            case "middle":
                                updateMiddle($container, header, body);
                                break;
                            case "detail":
                                updateDetail($container, header, body, keepStates);
                                break;
                            case "verticalSummaries":
                                updateVertSum($container, header, body);
                                break;
                            case "leftHorizontalSummaries":
                                updateLeftHorzSum($container, header, body);
                                break;
                            case "horizontalSummaries":
                                updateHorzSum($container, header, body);
                                break;
                        }
                    }
                    function updateLeftmost($container, header, body) {
                        var exTable = $container.data(NAMESPACE);
                        var sizeAdjust, left, width, offsetWidth = 0;
                        if (!exTable.middleHeader && !exTable.leftHorzSumHeader
                            && !exTable.horizontalSumHeader && !exTable.verticalSumHeader
                            && !exTable.areaResize) {
                            sizeAdjust = true;
                        }
                        if (header) {
                            _.assignIn(exTable.leftmostHeader, header);
                            var $header = $container.find("." + HEADER_PRF + LEFTMOST);
                            if (sizeAdjust && header.columns) {
                                width = render.calcWidth(header.columns);
                                exTable.leftmostHeader.width = width + "px";
                                offsetWidth = Math.abs($header.width() - width);
                                if (offsetWidth > 0) {
                                    var $detailH = $container.find("." + HEADER_PRF + DETAIL);
                                    var $detailB = $container.find("." + BODY_PRF + DETAIL);
                                    var op = $header.width() > width ? -1 : 1;
                                    $header.width(width);
                                    left = parseFloat($detailH.css("left"));
                                    left = left + op * offsetWidth;
                                    $detailH.css("left", left);
                                    $detailB.css("left", left);
                                }
                            }
                            var pu = $header.find("table").data(internal.POPUP);
                            $header.empty();
                            render.process($header[0], exTable.leftmostHeader, true);
                            if (pu && pu.css("display") !== "none")
                                pu.hide();
                        }
                        if (body) {
                            _.assignIn(exTable.leftmostContent, body);
                            var $body = $container.find("." + BODY_PRF + LEFTMOST);
                            if (offsetWidth > 0 && width) {
                                $body.width(width);
                            }
                            $body.empty();
                            render.process($body[0], exTable.leftmostContent, true);
                        }
                    }
                    function updateMiddle($container, header, body) {
                        var exTable = $container.data(NAMESPACE);
                        if (header) {
                            _.assignIn(exTable.middleHeader, header);
                            var $header = $container.find("." + HEADER_PRF + MIDDLE);
                            var pu = $header.find("table").data(internal.POPUP);
                            $header.empty();
                            render.process($header[0], exTable.middleHeader, true);
                            if (pu && pu.css("display") !== "none")
                                pu.hide();
                        }
                        if (body) {
                            _.assignIn(exTable.middleContent, body);
                            var $body = $container.find("." + BODY_PRF + MIDDLE);
                            $body.empty();
                            render.process($body[0], exTable.middleContent, true);
                        }
                    }
                    function updateDetail($container, header, body, keepStates) {
                        var exTable = $container.data(NAMESPACE);
                        if (header) {
                            _.assignIn(exTable.detailHeader, header);
                            var $header = $container.find("." + HEADER_PRF + DETAIL);
                            var pu = $header.find("table").data(internal.POPUP);
                            $header.empty();
                            render.process($header[0], exTable.detailHeader, true);
                            if (pu && pu.css("display") !== "none")
                                pu.hide();
                        }
                        if (body) {
                            _.assignIn(exTable.detailContent, body);
                            var $body = $container.find("." + BODY_PRF + DETAIL);
                            $body.empty();
                            if (!keepStates)
                                internal.clearStates($body[0]);
                            render.process($body[0], exTable.detailContent, true);
                        }
                    }
                    function updateVertSum($container, header, body) {
                        var exTable = $container.data(NAMESPACE);
                        if (header) {
                            _.assignIn(exTable.verticalSumHeader, header);
                            var $header = $container.find("." + HEADER_PRF + VERTICAL_SUM);
                            var pu = $header.find("table").data(internal.POPUP);
                            $header.empty();
                            render.process($header[0], exTable.verticalSumHeader, true);
                            if (pu && pu.css("display") !== "none")
                                pu.hide();
                        }
                        if (body) {
                            _.assignIn(exTable.verticalSumContent, body);
                            var $body = $container.find("." + BODY_PRF + VERTICAL_SUM);
                            $body.empty();
                            render.process($body[0], exTable.verticalSumContent, true);
                        }
                    }
                    function updateLeftHorzSum($container, header, body) {
                        var exTable = $container.data(NAMESPACE);
                        if (header) {
                            _.assignIn(exTable.leftHorzSumHeader, header);
                            var $header = $container.find("." + HEADER_PRF + LEFT_HORZ_SUM);
                            var pu = $header.find("table").data(internal.POPUP);
                            $header.empty();
                            render.process($header[0], exTable.leftHorzSumHeader, true);
                            if (pu && pu.css("display") !== "none")
                                pu.hide();
                        }
                        if (body) {
                            _.assignIn(exTable.leftHorzSumContent, body);
                            var $body = $container.find("." + BODY_PRF + LEFT_HORZ_SUM);
                            $body.empty();
                            render.process($body[0], exTable.leftHorzSumContent, true);
                        }
                    }
                    function updateHorzSum($container, header, body) {
                        var exTable = $container.data(NAMESPACE);
                        if (header) {
                            _.assignIn(exTable.horizontalSumHeader, header);
                            var $header = $container.find("." + HEADER_PRF + HORIZONTAL_SUM);
                            var pu = $header.find("table").data(internal.POPUP);
                            $header.empty();
                            render.process($header[0], exTable.horizontalSumHeader, true);
                            if (pu && pu.css("display") !== "none")
                                pu.hide();
                        }
                        if (body) {
                            _.assignIn(exTable.horizontalSumContent, body);
                            var $body = $container.find("." + BODY_PRF + HORIZONTAL_SUM);
                            $body.empty();
                            render.process($body[0], exTable.horizontalSumContent, true);
                        }
                    }
                    function setUpdateMode($container, mode, occupation) {
                        var exTable = $container.data(NAMESPACE);
                        if (!mode)
                            return exTable.updateMode;
                        if (exTable.updateMode === mode)
                            return;
                        exTable.setUpdateMode(mode);
                        if (occupation) {
                            events.trigger($container[0], events.OCCUPY_UPDATE, occupation);
                        }
                        var $grid = $container.find("." + BODY_PRF + DETAIL);
                        render.begin($grid[0], internal.getDataSource($grid[0]), exTable.detailContent);
                        selection.tickRows($container.find("." + BODY_PRF + LEFTMOST)[0], true);
                        if (mode === COPY_PASTE) {
                            selection.checkUp($container[0]);
                            copy.on($grid[0], mode);
                            return;
                        }
                        selection.off($container[0]);
                        copy.off($grid[0], mode);
                    }
                    function setViewMode($container, mode, occupation, features) {
                        var exTable = $container.data(NAMESPACE);
                        if (!mode)
                            return exTable.viewMode;
                        if (occupation) {
                            events.trigger($container[0], events.OCCUPY_UPDATE, occupation);
                        }
                        if (exTable.viewMode === mode)
                            return;
                        var table = helper.getMainTable($container[0]);
                        if (exTable.updateMode === EDIT) {
                            var editor = $container.data(update.EDITOR);
                            if (editor) {
                                var $editor = editor.$editor;
                                var $input = $editor.querySelector("input");
                                var $editingCell = helper.closest($editor, "." + update.EDIT_CELL_CLS);
                                $editingCell.classList.remove(update.EDIT_CELL_CLS);
                                update.triggerStopEdit($container[0], $editingCell, editor.land, $input.value);
                                $container.data(update.EDITOR, null);
                            }
                            $.data(table, internal.EDIT_HISTORY, null);
                        }
                        else if (exTable.updateMode === COPY_PASTE) {
                            $.data(table, internal.COPY_HISTORY, null);
                        }
                        else if (exTable.updateMode === STICK) {
                            $.data(table, internal.STICK_HISTORY, null);
                        }
                        $.data(table, internal.DET, null);
                        $container.data(errors.ERRORS, null);
                        exTable.setViewMode(mode);
                        if (features && exTable.detailContent.features) {
                            var newFeatures = _.map(exTable.detailContent.features, function (f, i) {
                                var z = -1;
                                _.forEach(features, function (ft, y) {
                                    if (f.name == ft.name) {
                                        z = y;
                                        return false;
                                    }
                                });
                                if (z > -1) {
                                    var fts = features.splice(z, 1);
                                    return fts[0];
                                }
                                return f;
                            });
                            exTable.detailContent.features = newFeatures;
                        }
                        var ds = helper.getOrigDS(table);
                        render.begin(table, _.cloneDeep(ds), exTable.detailContent);
                    }
                    function setMode($container, viewMode, updateMode, occupation) {
                        var exTable = $container.data(NAMESPACE);
                        if (occupation) {
                            events.trigger($container[0], events.OCCUPY_UPDATE, occupation);
                        }
                        var table = helper.getMainTable($container[0]), updateViewMode = false;
                        var ds = helper.getOrigDS(table);
                        if (viewMode && exTable.viewMode !== viewMode) {
                            if (exTable.updateMode === EDIT) {
                                var editor = $container.data(update.EDITOR);
                                if (editor) {
                                    var $editor = editor.$editor;
                                    var $input = $editor.querySelector("input");
                                    var $editingCell = helper.closest($editor, "." + update.EDIT_CELL_CLS);
                                    $editingCell.classList.remove(update.EDIT_CELL_CLS);
                                    update.triggerStopEdit($container[0], $editingCell, editor.land, $input.value);
                                    $container.data(update.EDITOR, null);
                                }
                                $.data(table, internal.EDIT_HISTORY, null);
                            }
                            else if (exTable.updateMode === COPY_PASTE) {
                                $.data(table, internal.COPY_HISTORY, null);
                            }
                            else if (exTable.updateMode === STICK) {
                                $.data(table, internal.STICK_HISTORY, null);
                            }
                            $.data(table, internal.DET, null);
                            $container.data(errors.ERRORS, null);
                            exTable.setViewMode(viewMode);
                            var $grid = $container.find("." + BODY_PRF + DETAIL);
                            updateViewMode = true;
                        }
                        if (updateMode && exTable.updateMode !== updateMode) {
                            exTable.setUpdateMode(updateMode);
                            render.begin(table, ds, exTable.detailContent);
                            selection.tickRows($container.find("." + BODY_PRF + LEFTMOST)[0], true);
                            if (updateMode === COPY_PASTE) {
                                selection.checkUp($container[0]);
                                copy.on(table, updateMode);
                                return;
                            }
                            selection.off($container[0]);
                            copy.off(table, updateMode);
                        }
                        else if (updateViewMode) {
                            render.begin(table, ds, exTable.detailContent);
                        }
                    }
                    function setPasteOverWrite($container, overwrite) {
                        var exTable = $container.data(NAMESPACE);
                        exTable.pasteOverWrite = overwrite;
                    }
                    function setStickOverWrite($container, overwrite) {
                        var exTable = $container.data(NAMESPACE);
                        exTable.stickOverWrite = overwrite;
                    }
                    function setStickMode($container, mode) {
                        var $grid = $container.find("." + BODY_PRF + DETAIL);
                        var sticker = $grid.data(internal.STICKER);
                        if (!sticker) {
                            sticker = new spread.Sticker();
                            sticker.mode = mode;
                            $grid.data(internal.STICKER, sticker);
                        }
                        else {
                            sticker.mode = mode;
                        }
                    }
                    function setStickData($container, data) {
                        var $grid = $container.find("." + BODY_PRF + DETAIL);
                        var sticker = $grid.data(internal.STICKER);
                        if (!sticker) {
                            sticker = new spread.Sticker(data);
                            $grid.data(internal.STICKER, sticker);
                        }
                        else {
                            sticker.data = data;
                        }
                    }
                    function setStickValidate($container, validate) {
                        var $grid = $container.find("." + BODY_PRF + DETAIL);
                        var sticker = $grid.data(internal.STICKER);
                        if (!sticker) {
                            sticker = new spread.Sticker();
                            sticker.validate = validate;
                            $grid.data(internal.STICKER, sticker);
                        }
                        else {
                            sticker.validate = validate;
                        }
                    }
                    function setStickStyler($container, styler) {
                        var $grid = $container.find("." + BODY_PRF + DETAIL);
                        var sticker = $grid.data(internal.STICKER);
                        if (!sticker) {
                            sticker = new spread.Sticker();
                            sticker.styleMaker = styler;
                            $grid.data(internal.STICKER, sticker);
                        }
                        else {
                            sticker.styleMaker = styler;
                        }
                    }
                    function undoStick($container) {
                        var exTable = $container.data(NAMESPACE);
                        if (!exTable || exTable.updateMode !== STICK)
                            return;
                        var $grid = $container.find("." + BODY_PRF + DETAIL);
                        var histories = $grid.data(internal.STICK_HISTORY);
                        if (!histories)
                            return;
                        var items = histories.pop();
                        _.forEach(items, function (i) {
                            update.gridCell($grid[0], i.rowIndex, i.columnKey, -1, i.value, true);
                            internal.removeChange($grid[0], i);
                        });
                    }
                    function clearHistories($container, type) {
                        var $grid = $container.find("." + BODY_PRF + DETAIL);
                        var histType;
                        switch (type) {
                            case "edit":
                                histType = internal.EDIT_HISTORY;
                                break;
                            case "copyPaste":
                                histType = internal.COPY_HISTORY;
                                break;
                            case "stick":
                                histType = internal.STICK_HISTORY;
                                break;
                        }
                        $grid.data(histType, null);
                    }
                    function lockCell($container, rowId, columnKey) {
                        var $table = helper.getMainTable($container[0]);
                        var ds = helper.getDataSource($table);
                        var pk = helper.getPrimaryKey($table);
                        var i = -1;
                        _.forEach(ds, function (r, j) {
                            if (r[pk] === rowId) {
                                i = j;
                                return false;
                            }
                        });
                        if (i === -1)
                            return;
                        var locks = $.data($table, internal.DET);
                        var found = -1;
                        if (locks && locks[i] && locks[i].length > 0) {
                            _.forEach(locks[i], function (c, j) {
                                if (c === columnKey) {
                                    found = j;
                                    return false;
                                }
                            });
                        }
                        if (found === -1) {
                            var $cell = selection.cellAt($table, i, columnKey);
                            if (!locks) {
                                locks = {};
                                locks[i] = [columnKey];
                                $.data($table, internal.DET, locks);
                            }
                            else if (locks && !locks[i]) {
                                locks[i] = [columnKey];
                            }
                            else
                                locks[i].push(columnKey);
                            helper.markCellWith(style.DET_CLS, $cell);
                        }
                    }
                    function unlockCell($container, rowId, columnKey) {
                        var $table = helper.getMainTable($container[0]);
                        var ds = helper.getDataSource($table);
                        var pk = helper.getPrimaryKey($table);
                        var i = -1;
                        _.forEach(ds, function (r, j) {
                            if (r[pk] === rowId) {
                                i = j;
                                return false;
                            }
                        });
                        if (i === -1)
                            return;
                        var locks = $.data($table, internal.DET);
                        var found = -1;
                        if (locks && locks[i] && locks[i].length > 0) {
                            _.forEach(locks[i], function (c, j) {
                                if (c === columnKey) {
                                    found = j;
                                    return false;
                                }
                            });
                        }
                        if (found > -1) {
                            var $cell = selection.cellAt($table, i, columnKey);
                            locks[i].splice(found, 1);
                            if (locks[i].length === 0)
                                delete locks[i];
                            helper.stripCellWith(style.DET_CLS, $cell[0]);
                        }
                    }
                    function returnPopupValue($container, value) {
                        if (!$container)
                            return;
                        var header = helper.getMainHeader($container[0]);
                        if (!header)
                            return;
                        var headerTbl = header.querySelector("table");
                        if (!headerTbl)
                            return;
                        var $pu = $.data(headerTbl, internal.POPUP);
                        if (!$pu)
                            return;
                        events.trigger($pu[0], events.POPUP_INPUT_END, { value: value });
                    }
                    function getDataSource($container, name) {
                        switch (name) {
                            case "leftmost":
                                return helper.getPartialDataSource($container[0], LEFTMOST);
                            case "middle":
                                return helper.getPartialDataSource($container[0], MIDDLE);
                            case "detail":
                                return helper.getPartialDataSource($container[0], DETAIL);
                            case "verticalSummaries":
                                return helper.getPartialDataSource($container[0], VERTICAL_SUM);
                            case "leftHorizontalSummaries":
                                return helper.getPartialDataSource($container[0], LEFT_HORZ_SUM);
                            case "horizontalSummaries":
                                return helper.getPartialDataSource($container[0], HORIZONTAL_SUM);
                        }
                    }
                    function getCellByIndex($container, rowIndex, columnKey) {
                        var $tbl = helper.getMainTable($container[0]);
                        if (!$tbl)
                            return;
                        return selection.cellAt($tbl, rowIndex, columnKey);
                    }
                    function getCellById($container, rowId, columnKey) {
                        var $tbl = helper.getMainTable($container[0]);
                        if (!$tbl)
                            return;
                        return selection.cellOf($tbl, rowId, columnKey);
                    }
                    function getUpdatedCells($container) {
                        var data = $container.data(NAMESPACE).modifications;
                        if (!data)
                            return [];
                        return helper.valuesArray(data);
                    }
                    function getLockCells($container) {
                        var tbl = helper.getMainTable($container[0]);
                        var det = $.data(tbl, internal.DET);
                        if (!det)
                            return [];
                        var cells = [];
                        Object.keys(det).forEach(function (k) {
                            if (!uk.util.isNullOrUndefined(det[k])) {
                                det[k].forEach(function (v) {
                                    cells.push({ rowIndex: k, columnKey: v });
                                });
                            }
                        });
                        return cells;
                    }
                    function roundRetreat($container, value) {
                        if (!value)
                            return;
                        events.trigger($container[0], events.ROUND_RETREAT, value);
                    }
                    function setCellValue($container, name, rowId, columnKey, value) {
                        switch (name) {
                            case LEFT_TBL:
                                setValue($container, BODY_PRF + LEFTMOST, rowId, columnKey, value);
                                break;
                            case HORZ_SUM:
                                setValue($container, BODY_PRF + HORIZONTAL_SUM, rowId, columnKey, value);
                                break;
                            case VERT_SUM:
                                setValue($container, BODY_PRF + VERTICAL_SUM, rowId, columnKey, value);
                                break;
                        }
                    }
                    function setCellValueByIndex($container, name, rowIdx, columnKey, value) {
                        switch (name) {
                            case LEFT_TBL:
                                setValueByIndex($container, BODY_PRF + LEFTMOST, rowIdx, columnKey, value);
                                break;
                            case HORZ_SUM:
                                setValueByIndex($container, BODY_PRF + HORIZONTAL_SUM, rowIdx, columnKey, value);
                                break;
                            case VERT_SUM:
                                setValueByIndex($container, BODY_PRF + VERTICAL_SUM, rowIdx, columnKey, value);
                                break;
                        }
                    }
                    function setValue($container, selector, rowId, columnKey, value) {
                        var $grid = $container.find("." + selector);
                        if ($grid.length === 0)
                            return;
                        var rowIdx = helper.getRowIndex($grid[0], rowId);
                        var ds = helper.getDataSource($grid[0]);
                        if (rowIdx === -1 || !ds || ds.length === 0)
                            return;
                        if (selector === BODY_PRF + LEFTMOST) {
                            if (ds[rowIdx][columnKey] !== value) {
                                update.gridCell($grid[0], rowIdx, columnKey, -1, value);
                                update.pushEditHistory($grid[0], new selection.Cell(rowIdx, columnKey, value, -1));
                            }
                        }
                        else {
                            ds[rowIdx][columnKey] = value;
                            refreshCell($grid, rowId, columnKey, value);
                        }
                    }
                    function setValueByIndex($container, selector, rowIdx, columnKey, value) {
                        var $grid = $container.find("." + selector);
                        if ($grid.length === 0)
                            return;
                        var x = helper.getExTableFromGrid($grid[0]);
                        var ds = helper.getDataSource($grid[0]);
                        if (!ds || ds.length === 0)
                            return;
                        var rowObj = ds[rowIdx];
                        if (!rowObj)
                            return;
                        var updTarget;
                        if (!uk.util.isNullOrUndefined(x.manipulatorId)
                            && !uk.util.isNullOrUndefined(x.manipulatorKey)) {
                            updTarget = x.manipulatorId === rowObj[x.manipulatorKey] ? 1 : 0;
                        }
                        if (selector === BODY_PRF + LEFTMOST) {
                            if (rowObj[columnKey] !== value) {
                                update.gridCell($grid[0], rowIdx, columnKey, -1, value);
                                update.pushEditHistory($grid[0], new selection.Cell(rowIdx, columnKey, value, -1), updTarget);
                            }
                        }
                        else {
                            rowObj[columnKey] = value;
                            refreshCellByIndex($grid, rowIdx, columnKey, value);
                        }
                    }
                    function refreshCell($grid, rowId, columnKey, value) {
                        var $c = selection.cellOf($grid[0], rowId, columnKey);
                        if ($c === intan.NULL || !$c)
                            return;
                        if (uk.util.isNullOrUndefined(value)) {
                            var ds = helper.getClonedDs($grid[0]);
                            if (!ds || ds.length === 0)
                                return;
                            var rIdx = helper.getRowIndex($grid[0], rowId);
                            if (rIdx === -1)
                                return;
                            value = ds[rIdx][columnKey];
                        }
                        $c.textContent = value;
                    }
                    function refreshCellByIndex($grid, rowIdx, columnKey, value) {
                        var $c = selection.cellAt($grid[0], rowIdx, columnKey);
                        if ($c === intan.NULL || !$c)
                            return;
                        if (uk.util.isNullOrUndefined(value)) {
                            var ds = helper.getClonedDs($grid);
                            if (!ds || ds.length === 0)
                                return;
                            value = ds[rowIdx][columnKey];
                        }
                        $c.textContent = value;
                    }
                    function setRowId($container, rowIndex, value) {
                        $container.find("div[class*='" + BODY_PRF + "']").filter(function () {
                            return !$(this).hasClass(BODY_PRF + HORIZONTAL_SUM) && !$(this).hasClass(BODY_PRF + LEFT_HORZ_SUM);
                        }).each(function () {
                            var key = helper.getPrimaryKey(this);
                            var x = helper.getExTableFromGrid(this);
                            var ds = helper.getDataSource(this);
                            var rowObj = ds[rowIndex];
                            if (!ds || ds.length === 0 || !rowObj)
                                return;
                            var updTarget;
                            if (!uk.util.isNullOrUndefined(x.manipulatorId)
                                && !uk.util.isNullOrUndefined(x.manipulatorKey)) {
                                updTarget = x.manipulatorId === rowObj[x.manipulatorKey] ? 1 : 0;
                            }
                            if (rowObj[key] !== value) {
                                update.gridCell(this, rowIndex, key, -1, value);
                                update.pushEditHistory(this, new selection.Cell(rowIndex, key, value, -1), updTarget);
                            }
                        });
                    }
                    function saveScroll($container) {
                        var key = uk.request.location.current.rawUrl + "/" + $container.attr("id") + "/scroll";
                        var scroll = {};
                        var $tbl = $container.find("." + BODY_PRF + DETAIL);
                        scroll.v = $tbl.scrollTop();
                        scroll.h = $tbl.scrollLeft();
                        uk.localStorage.setItemAsJson(key, scroll);
                    }
                    function scrollBack($container, where) {
                        var key = uk.request.location.current.rawUrl + "/" + $container.attr("id") + "/scroll";
                        var item = uk.localStorage.getItem(key);
                        if (!item.isPresent())
                            return;
                        var $tbl = $container.find("." + BODY_PRF + DETAIL);
                        var scroll = JSON.parse(item.get());
                        switch (where) {
                            case 0:
                                $tbl.scrollLeft(scroll.h);
                                break;
                            case 1:
                                $tbl.scrollTop(scroll.v);
                                break;
                            case 2:
                                $tbl.scrollLeft(scroll.h);
                                $tbl.scrollTop(scroll.v);
                                break;
                        }
                    }
                })(func || (func = {}));
                var internal;
                (function (internal) {
                    internal.X_OCCUPY = "ex-x-occupy";
                    internal.Y_OCCUPY = "ex-y-occupy";
                    internal.TANGI = "x-tangi";
                    internal.CANON = "x-canon";
                    internal.STICKER = "x-sticker";
                    internal.DET = "x-det";
                    internal.PAINTER = "painter";
                    internal.CELLS_STYLE = "body-cells-style";
                    internal.D_CELLS_STYLE = "d-body-cells-style";
                    internal.VIEW = "view";
                    internal.EX_PART = "expart";
                    internal.TIME_VALID_RANGE = "time-validate-range";
                    internal.SELECTED_CELLS = "selected-cells";
                    internal.LAST_SELECTED = "last-selected";
                    internal.SELECTED_ROWS = "selected-rows";
                    internal.COPY_HISTORY = "copy-history";
                    internal.EDIT_HISTORY = "edit-history";
                    internal.TARGET_EDIT_HISTORY = "target-edit-history";
                    internal.OTHER_EDIT_HISTORY = "other-edit-history";
                    internal.STICK_HISTORY = "stick-history";
                    internal.TOOLTIP = "tooltip";
                    internal.CONTEXT_MENU = "context-menu";
                    internal.POPUP = "popup";
                    internal.TEXT = "text";
                    internal.TIME = "time";
                    internal.DURATION = "duration";
                    internal.NUMBER = "number";
                    internal.DT_SEPARATOR = "/";
                    internal.COLUMN_IN = "column-in";
                    function getGem($grid) {
                        return $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                    }
                    internal.getGem = getGem;
                    function getDataSource($grid) {
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        return gen.dataSource;
                    }
                    internal.getDataSource = getDataSource;
                    function removeChange($grid, cell) {
                        var origDs = helper.getOrigDS($grid);
                        var exTable = helper.getExTableFromGrid($grid);
                        if (!origDs || !exTable)
                            return;
                        var oVal = origDs[cell.rowIndex][cell.columnKey];
                        var cells = exTable.modifications[cell.rowIndex];
                        if (!cells)
                            return;
                        var index = -1;
                        _.forEach(cells, function (c, i) {
                            if (helper.areSameCells(cell, c) && cell.value === oVal) {
                                index = i;
                                return false;
                            }
                        });
                        exTable.modifications[cell.rowIndex].splice(index, 1);
                    }
                    internal.removeChange = removeChange;
                    function clearStates($grid) {
                        $.data($grid, internal.SELECTED_CELLS, null);
                        $.data($grid, internal.LAST_SELECTED, null);
                        $.data($grid, internal.COPY_HISTORY, null);
                        $.data($grid, internal.EDIT_HISTORY, null);
                        $.data($grid, internal.STICK_HISTORY, null);
                        $.data($grid, internal.DET, null);
                        var exTable = helper.getExTableFromGrid($grid);
                        if (!exTable)
                            return;
                        exTable.modifications = {};
                    }
                    internal.clearStates = clearStates;
                })(internal || (internal = {}));
                var selector;
                (function (selector) {
                    function find(p, sel) {
                        return new Manipulator().addNodes(p.querySelectorAll(sel));
                    }
                    selector.find = find;
                    function create(str) {
                        return new Manipulator().addElement(document.createElement(str));
                    }
                    selector.create = create;
                    function is(el, sel) {
                        var matches = el.matches || el.matchesSelector || el.msMatchesSelector || el.mozMatchesSelector || el.webkitMatchesSelector || el.oMatchesSelector;
                        if (matches)
                            return matches.call(el, sel);
                        return $(el).is(sel);
                    }
                    selector.is = is;
                    function index(el) {
                        return Array.prototype.slice.call(el.parentNode.children).indexOf(el);
                    }
                    selector.index = index;
                    function queryAll(el, sel) {
                        return Array.prototype.slice.call(el.querySelectorAll(sel));
                    }
                    selector.queryAll = queryAll;
                    function offset(el) {
                        var rect = el.getBoundingClientRect();
                        return {
                            top: rect.top + document.body.scrollTop,
                            left: rect.left + document.body.scrollLeft
                        };
                    }
                    selector.offset = offset;
                    function classSiblings(node, clazz) {
                        var parent = node.parentElement;
                        if (!parent)
                            return;
                        var children = parent.children;
                        var results = [];
                        for (var i = 0; i < children.length; i++) {
                            if (children[i] === node)
                                continue;
                            var classList = children[i].classList;
                            for (var j = 0; j < classList.length; j++) {
                                if (classList.item(j) === clazz) {
                                    results.push(children[i]);
                                    break;
                                }
                            }
                        }
                        return results;
                    }
                    selector.classSiblings = classSiblings;
                    function siblingsLt(el, index) {
                        var parent = el.parentNode;
                        if (!parent)
                            return;
                        var children = parent.children;
                        var results = [];
                        for (var i = 0; i < children.length; i++) {
                            if (i < index) {
                                if (children[i] !== el)
                                    results.push(children[i]);
                            }
                            else
                                return results;
                        }
                    }
                    selector.siblingsLt = siblingsLt;
                    var Manipulator = (function () {
                        function Manipulator() {
                        }
                        Manipulator.prototype.addNodes = function (nodes) {
                            if (!nodes || nodes.length === 0)
                                return;
                            this.elements = Array.prototype.slice.call(self.elements);
                            return this;
                        };
                        Manipulator.prototype.addElements = function (elements) {
                            this.elements = elements;
                            return this;
                        };
                        Manipulator.prototype.addElement = function (element) {
                            if (!this.elements)
                                this.elements = [];
                            this.elements.push(element);
                            return this;
                        };
                        Manipulator.prototype.html = function (str) {
                            this.elements.forEach(function (e) {
                                e.innerHTML = str;
                            });
                            return this;
                        };
                        Manipulator.prototype.width = function (w) {
                            var self = this;
                            this.elements.forEach(function (e) {
                                e.style.width = parseInt(w) + "px";
                            });
                            return this;
                        };
                        Manipulator.prototype.height = function (h) {
                            var self = this;
                            this.elements.forEach(function (e) {
                                e.style.height = parseInt(h) + "px";
                            });
                            return this;
                        };
                        Manipulator.prototype.data = function (name, value) {
                            this.elements.forEach(function (e) {
                                $.data(e, name, value);
                            });
                            return this;
                        };
                        Manipulator.prototype.addClass = function (clazz) {
                            this.elements.forEach(function (e) {
                                e.classList.add(clazz);
                            });
                            return this;
                        };
                        Manipulator.prototype.css = function (style) {
                            this.elements.forEach(function (e) {
                                Object.keys(style).forEach(function (k) {
                                    ;
                                    if (k === "maxWidth") {
                                        e.style.setProperty("max-width", style[k]);
                                        return;
                                    }
                                    e.style.setProperty(k, style[k]);
                                });
                            });
                            return this;
                        };
                        Manipulator.prototype.getSingle = function () {
                            return this.elements[0];
                        };
                        Manipulator.prototype.get = function () {
                            return this.elements;
                        };
                        return Manipulator;
                    }());
                    selector.Manipulator = Manipulator;
                })(selector || (selector = {}));
                var helper;
                (function (helper) {
                    function isIE() {
                        return window.navigator.userAgent.indexOf("MSIE") > -1 || window.navigator.userAgent.match(/trident/i);
                    }
                    helper.isIE = isIE;
                    function isChrome() {
                        return window.chrome;
                    }
                    helper.isChrome = isChrome;
                    function isEdge() {
                        return window.navigator.userAgent.indexOf("Edge") > -1;
                    }
                    helper.isEdge = isEdge;
                    function getScrollWidth() {
                        if (_scrollWidth)
                            return _scrollWidth;
                        var $outer = document.body.appendChild(selector.create("div").css({ visibility: 'hidden', width: "100px", overflow: 'scroll' }).getSingle());
                        var $inner = selector.create("div").css({ width: '100%' }).getSingle();
                        $outer.appendChild($inner);
                        var widthWithScroll = $inner.offsetWidth;
                        $outer.parentNode.removeChild($outer);
                        _scrollWidth = 100 - widthWithScroll;
                        return _scrollWidth;
                    }
                    helper.getScrollWidth = getScrollWidth;
                    function getTable($exTable, name) {
                        return $exTable.querySelector("." + name);
                    }
                    helper.getTable = getTable;
                    function getMainHeader($exTable) {
                        return $exTable.querySelector("." + HEADER_PRF + DETAIL);
                    }
                    helper.getMainHeader = getMainHeader;
                    function getMainTable($exTable) {
                        return $exTable.querySelector("." + BODY_PRF + DETAIL);
                    }
                    helper.getMainTable = getMainTable;
                    function getLeftmostTable($exTable) {
                        return $exTable.querySelector("." + BODY_PRF + LEFTMOST);
                    }
                    helper.getLeftmostTable = getLeftmostTable;
                    function getExTableFromGrid($grid) {
                        return $.data(helper.closest($grid, "." + NAMESPACE), NAMESPACE);
                    }
                    helper.getExTableFromGrid = getExTableFromGrid;
                    function getVisibleColumnsOn($grid) {
                        return ($.data($grid, internal.TANGI) || $.data($grid, internal.CANON)).painter.visibleColumns;
                    }
                    helper.getVisibleColumnsOn = getVisibleColumnsOn;
                    function getVisibleColumns(options) {
                        var visibleColumns = [];
                        filterColumns(options.columns, visibleColumns, []);
                        return visibleColumns;
                    }
                    helper.getVisibleColumns = getVisibleColumns;
                    function getOrigDS($grid) {
                        return ($.data($grid, internal.TANGI) || $.data($grid, internal.CANON))._origDs;
                    }
                    helper.getOrigDS = getOrigDS;
                    function getDataSource($grid) {
                        return ($.data($grid, internal.TANGI) || $.data($grid, internal.CANON)).dataSource;
                    }
                    helper.getDataSource = getDataSource;
                    function getClonedDs($grid) {
                        return _.cloneDeep(getDataSource($grid));
                    }
                    helper.getClonedDs = getClonedDs;
                    function getPrimaryKey($grid) {
                        return ($.data($grid, internal.TANGI) || $.data($grid, internal.CANON)).primaryKey;
                    }
                    helper.getPrimaryKey = getPrimaryKey;
                    function classifyColumns(options) {
                        var visibleColumns = [];
                        var hiddenColumns = [];
                        filterColumns(options.columns, visibleColumns, hiddenColumns);
                        return {
                            visibleColumns: visibleColumns,
                            hiddenColumns: hiddenColumns
                        };
                    }
                    helper.classifyColumns = classifyColumns;
                    function filterColumns(columns, visibleColumns, hiddenColumns) {
                        _.forEach(columns, function (col) {
                            if (!uk.util.isNullOrUndefined(col.visible) && col.visible === false) {
                                hiddenColumns.push(col);
                                return;
                            }
                            if (!uk.util.isNullOrUndefined(col.group) && col.group.length > 0) {
                                filterColumns(col.group, visibleColumns, hiddenColumns);
                            }
                            else {
                                visibleColumns.push(col);
                            }
                        });
                    }
                    function getColumnsMap(columns) {
                        return _.groupBy(columns, "key");
                    }
                    helper.getColumnsMap = getColumnsMap;
                    function columnsMapFromStruct(levelStruct) {
                        var map = {};
                        _.forEach(Object.keys(levelStruct), function (nth) {
                            _.forEach(levelStruct[nth], function (col) {
                                if (!uk.util.isNullOrUndefined(col.key)) {
                                    map[col.key] = col;
                                }
                            });
                        });
                        return map;
                    }
                    helper.columnsMapFromStruct = columnsMapFromStruct;
                    function getPartialDataSource($table, name) {
                        return {
                            header: getClonedDs($table.querySelector("." + HEADER_PRF + name)),
                            body: getClonedDs($table.querySelector("." + BODY_PRF + name))
                        };
                    }
                    helper.getPartialDataSource = getPartialDataSource;
                    function makeConnector() {
                        Connector[HEADER_PRF + LEFTMOST] = BODY_PRF + LEFTMOST;
                        Connector[HEADER_PRF + MIDDLE] = BODY_PRF + MIDDLE;
                        Connector[HEADER_PRF + DETAIL] = BODY_PRF + DETAIL;
                        Connector[HEADER_PRF + VERTICAL_SUM] = BODY_PRF + VERTICAL_SUM;
                        Connector[HEADER_PRF + HORIZONTAL_SUM] = BODY_PRF + HORIZONTAL_SUM;
                    }
                    helper.makeConnector = makeConnector;
                    function getClassOfHeader($part) {
                        return $.data($part, internal.EX_PART);
                    }
                    helper.getClassOfHeader = getClassOfHeader;
                    function isPasteKey(evt) {
                        return evt.keyCode === 86;
                    }
                    helper.isPasteKey = isPasteKey;
                    function isCopyKey(evt) {
                        return evt.keyCode === 67;
                    }
                    helper.isCopyKey = isCopyKey;
                    function isCutKey(evt) {
                        return evt.keyCode === 88;
                    }
                    helper.isCutKey = isCutKey;
                    function isUndoKey(evt) {
                        return evt.keyCode === 90;
                    }
                    helper.isUndoKey = isUndoKey;
                    function getCellCoord($cell) {
                        if (!$cell)
                            return;
                        var $td = $cell;
                        if (selector.is($cell, "div")) {
                            $td = closest($cell, "td");
                        }
                        var view = $.data($td, internal.VIEW);
                        if (!view)
                            return;
                        var coord = view.split("-");
                        if (uk.util.isNullOrUndefined(coord[0]) || uk.util.isNullOrUndefined(coord[1]))
                            return;
                        return {
                            rowIdx: parseFloat(coord[0]),
                            columnKey: coord[1]
                        };
                    }
                    helper.getCellCoord = getCellCoord;
                    function getDisplayColumnIndex($grid, key) {
                        var generator = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        var visibleColumns = generator.painter.visibleColumns;
                        var index;
                        _.forEach(visibleColumns, function (c, i) {
                            if (c.key === key) {
                                index = i;
                                return false;
                            }
                        });
                        return index;
                    }
                    helper.getDisplayColumnIndex = getDisplayColumnIndex;
                    function getRowIndex($grid, rowId) {
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        var start = gen.startIndex || 0;
                        var end = gen.endIndex || gen.dataSource.length - 1;
                        for (var i = start; i <= end; i++) {
                            if (gen.dataSource[i][gen.primaryKey] === rowId) {
                                return i;
                            }
                        }
                        return -1;
                    }
                    helper.getRowIndex = getRowIndex;
                    function gridVisibleColumns($grid) {
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        return gen.painter.visibleColumns;
                    }
                    helper.gridVisibleColumns = gridVisibleColumns;
                    function gridColumnsMap($grid) {
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        return gen.painter.columnsMap;
                    }
                    helper.gridColumnsMap = gridColumnsMap;
                    function markCellWith(clazz, $cell, nth, value) {
                        var $childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        if (selector.is($cell, "td") && $childCells.length > 0) {
                            if (!uk.util.isNullOrUndefined(nth) && nth !== -1) {
                                $childCells[nth].classList.add(clazz);
                                if (clazz === errors.ERROR_CLS)
                                    $childCells[nth].textContent = value;
                            }
                            else {
                                helper.addClass($childCells, clazz);
                            }
                            return;
                        }
                        $cell.classList.add(clazz);
                        if (clazz === errors.ERROR_CLS)
                            $cell.textContent = value;
                    }
                    helper.markCellWith = markCellWith;
                    function stripCellWith(clazz, $cell, nth) {
                        var $childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        if (selector.is($cell, "td") && $childCells.length > 0) {
                            if (!uk.util.isNullOrUndefined(nth) && nth !== -1) {
                                $childCells[nth].classList.remove(clazz);
                            }
                            else
                                helper.removeClass($childCells, clazz);
                            return;
                        }
                        $cell.classList.remove(clazz);
                    }
                    helper.stripCellWith = stripCellWith;
                    function markCellsWith(clazz, $cells) {
                        $cells.forEach(function (e) {
                            markCellWith(clazz, e);
                        });
                    }
                    helper.markCellsWith = markCellsWith;
                    function stripCellsWith(clazz, $cells) {
                        $cells.forEach(function (e) {
                            stripCellWith(clazz, e);
                        });
                    }
                    helper.stripCellsWith = stripCellsWith;
                    function isDetable($cell) {
                        var children = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        return !(selector.is($cell, "." + style.HIDDEN_CLS) || selector.is($cell, "." + style.SEAL_CLS)
                            || (children.length > 0
                                && (selector.is(children[0], "." + style.HIDDEN_CLS)
                                    || selector.is(children[0], "." + style.SEAL_CLS))));
                    }
                    helper.isDetable = isDetable;
                    function indexOf(columnKey, visibleColumns) {
                        var index = -1;
                        _.forEach(visibleColumns, function (column, i) {
                            if (column.key === columnKey) {
                                index = i;
                                return false;
                            }
                        });
                        return index;
                    }
                    helper.indexOf = indexOf;
                    function nextKeyOf(columnIndex, visibleColumns) {
                        if (columnIndex >= visibleColumns.length - 1)
                            return;
                        return visibleColumns[columnIndex + 1].key;
                    }
                    helper.nextKeyOf = nextKeyOf;
                    function nextCellOf($grid, cell) {
                        var key, rowIndex, innerIdx;
                        var gen = $.data($grid, internal.TANGI) || $.data($grid, internal.CANON);
                        if (!gen)
                            return;
                        var visibleColumns = gen.painter.visibleColumns;
                        key = nextKeyOf(indexOf(cell.columnKey, visibleColumns), visibleColumns);
                        if (key) {
                            return new selection.Cell(cell.rowIndex, key, undefined, cell.innerIdx);
                        }
                        key = visibleColumns[0].key;
                        if (cell.rowIndex >= gen.dataSource.length - 1) {
                            if (cell.innerIdx === -1) {
                                rowIndex = 0;
                                innerIdx = -1;
                            }
                            else if (cell.innerIdx === 0) {
                                rowIndex = Number(cell.rowIndex);
                                innerIdx = 1;
                            }
                            else if (cell.innerIdx === 1) {
                                rowIndex = 0;
                                innerIdx = 0;
                            }
                        }
                        else {
                            if (cell.innerIdx === -1) {
                                rowIndex = Number(cell.rowIndex) + 1;
                                innerIdx = -1;
                            }
                            else if (cell.innerIdx === 0) {
                                rowIndex = Number(cell.rowIndex);
                                innerIdx = 1;
                            }
                            else if (cell.innerIdx === 1) {
                                rowIndex = Number(cell.rowIndex) + 1;
                                innerIdx = 0;
                            }
                        }
                        return new selection.Cell(rowIndex, key, undefined, innerIdx);
                    }
                    helper.nextCellOf = nextCellOf;
                    function call(fn) {
                        var args = [];
                        for (var _i = 1; _i < arguments.length; _i++) {
                            args[_i - 1] = arguments[_i];
                        }
                        return function () {
                            return fn.apply(null, args);
                        };
                    }
                    helper.call = call;
                    function containsBr(text) {
                        return text && text.indexOf("<br/>") > -1;
                    }
                    helper.containsBr = containsBr;
                    function areSameCells(one, other) {
                        if (parseInt(one.rowIndex) !== parseInt(other.rowIndex)
                            || one.columnKey !== other.columnKey
                            || one.innerIdx !== other.innerIdx)
                            return false;
                        return true;
                    }
                    helper.areSameCells = areSameCells;
                    function isDetCell($grid, rowIdx, key) {
                        var $cell = selection.cellAt($grid, rowIdx, key);
                        var $childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        return ($childCells.length === 0 && selector.is($cell, "." + style.DET_CLS))
                            || ($childCells.length > 0 && selector.is($childCells[0], "." + style.DET_CLS));
                    }
                    helper.isDetCell = isDetCell;
                    function isXCell($grid, rowId, key) {
                        var clazz = [];
                        for (var _i = 3; _i < arguments.length; _i++) {
                            clazz[_i - 3] = arguments[_i];
                        }
                        var cellsStyle = $.data($grid, internal.CELLS_STYLE);
                        if (!cellsStyle)
                            return;
                        var result = _.find(cellsStyle, function (deco) {
                            return deco.columnKey === key && deco.rowId === rowId && clazz.some(function (c) { return deco.clazz === c; });
                        });
                        return result !== undefined;
                    }
                    helper.isXCell = isXCell;
                    function isXCellShown($grid, rowIdx, key) {
                        var clazz = [];
                        for (var _i = 3; _i < arguments.length; _i++) {
                            clazz[_i - 3] = arguments[_i];
                        }
                        var $cell = selection.cellAt($grid, rowIdx, key);
                        var $childCells = $cell.querySelectorAll("." + render.CHILD_CELL_CLS);
                        var returnVal = false;
                        _.forEach(clazz, function (c) {
                            if (($childCells.length === 0 && selector.is($cell, "." + c))
                                || ($childCells.length > 0 && selector.is($childCells[0], "." + c))) {
                                returnVal = true;
                                return false;
                            }
                        });
                        return returnVal;
                    }
                    helper.isXCellShown = isXCellShown;
                    function isEmpty(obj) {
                        if (obj && obj.constructor === Array) {
                            var empty_1 = true;
                            _.forEach(obj, function (o) {
                                if (!uk.util.isNullOrUndefined(o)) {
                                    empty_1 = false;
                                    return false;
                                }
                            });
                            return empty_1;
                        }
                        if (!obj)
                            return true;
                        return false;
                    }
                    helper.isEmpty = isEmpty;
                    function valuesArray(obj) {
                        var values = [];
                        _.forEach(Object.keys(obj), function (k, i) {
                            values = _.concat(values, obj[k]);
                        });
                        return values;
                    }
                    helper.valuesArray = valuesArray;
                    function stringValue(val) {
                        return _.isObject(val) ? JSON.stringify(val) : val;
                    }
                    helper.stringValue = stringValue;
                    function getCellData(data) {
                        try {
                            return JSON.parse(data);
                        }
                        catch (e) {
                            return data;
                        }
                    }
                    helper.getCellData = getCellData;
                    function viewData(view, viewMode, obj) {
                        if (!view || !viewMode)
                            return;
                        var result = [];
                        _.forEach(view(viewMode), function (f) {
                            if (!f)
                                return;
                            result.push(obj[f]);
                        });
                        return result.length === 1 ? result[0] : result;
                    }
                    helper.viewData = viewData;
                    function isEqual(one, two, fields) {
                        if (_.isObject(one) && _.isObject(two)) {
                            return (fields && fields.length > 0)
                                ? _.isEqual(_.omitBy(one, function (d, p) { return fields.every(function (f) { return f !== p; }); }), _.omitBy(two, function (d, p) { return fields.every(function (f) { return f !== p; }); }))
                                : _.isEqual(_.omit(one, _.isFunction), _.omit(two, _.isFunction));
                        }
                        return _.isEqual(one, two);
                    }
                    helper.isEqual = isEqual;
                    function block(exTable) {
                        var $exTable = $(exTable);
                        $exTable.block({
                            message: null,
                            fadeIn: 200,
                            css: {
                                width: $exTable.width(),
                                height: $exTable.height()
                            }
                        });
                    }
                    helper.block = block;
                    function unblock(exTable) {
                        $(exTable).unblock();
                    }
                    helper.unblock = unblock;
                    function highlightColumn($container, columnIndex) {
                        var grid = $container.querySelector("." + BODY_PRF + DETAIL);
                        var header = $container.querySelector("." + HEADER_PRF + DETAIL);
                        _.forEach(grid.getElementsByTagName("tr"), function (t) {
                            var tds = t.getElementsByTagName("td");
                            if (!tds || tds.length === 0)
                                return;
                            helper.addClass1n(tds[columnIndex], render.HIGHLIGHT_CLS);
                        });
                        _.forEach(header.getElementsByTagName("tr"), function (t) {
                            var tds = t.getElementsByTagName("td");
                            if (!tds || tds.length === 0)
                                return;
                            helper.addClass1n(tds[columnIndex], render.HIGHLIGHT_CLS);
                        });
                    }
                    helper.highlightColumn = highlightColumn;
                    function unHighlightColumn($container, columnIndex) {
                        var grid = $container.querySelector("." + BODY_PRF + DETAIL);
                        var header = $container.querySelector("." + HEADER_PRF + DETAIL);
                        unHighlightGrid(grid, columnIndex);
                        unHighlightGrid(header, columnIndex);
                    }
                    helper.unHighlightColumn = unHighlightColumn;
                    function unHighlightGrid(grid, columnIndex) {
                        if (!grid)
                            return;
                        _.forEach(grid.getElementsByTagName("tr"), function (t) {
                            var tds = t.getElementsByTagName("td");
                            if (!tds || tds.length === 0)
                                return;
                            helper.removeClass1n(tds[columnIndex], render.HIGHLIGHT_CLS);
                        });
                    }
                    helper.unHighlightGrid = unHighlightGrid;
                    function firstSibling(node, clazz) {
                        var parent = node.parentElement;
                        if (!parent)
                            return;
                        var children = parent.children;
                        for (var i = 0; i < children.length; i++) {
                            if (node !== children[i] && children[i].classList.contains(clazz)) {
                                return children[i];
                            }
                        }
                    }
                    helper.firstSibling = firstSibling;
                    function classSiblings(node, partialClass) {
                        var parent = node.parentElement;
                        if (!parent)
                            return;
                        var children = parent.children;
                        var results = [];
                        for (var i = 0; i < children.length; i++) {
                            if (children[i] === node)
                                continue;
                            var classList = children[i].classList;
                            for (var j = 0; j < classList.length; j++) {
                                if (classList.item(j).indexOf(partialClass) >= 0) {
                                    results.push(children[i]);
                                }
                            }
                        }
                        return results;
                    }
                    helper.classSiblings = classSiblings;
                    function consumeSiblings(node, op) {
                        var parent = node.parentElement;
                        if (!parent)
                            return;
                        var children = parent.children;
                        for (var i = 0; i < children.length; i++) {
                            if (node !== children[i]) {
                                op(children[i]);
                            }
                        }
                    }
                    helper.consumeSiblings = consumeSiblings;
                    function closest(el, selector) {
                        var matches;
                        ['matches', 'webkitMatchesSelector', 'mozMatchesSelector', 'msMatchesSelector', 'oMatchesSelector'].some(function (fn) {
                            if (typeof document.body[fn] === 'function') {
                                matches = fn;
                                return true;
                            }
                            return false;
                        });
                        var parent;
                        while (el) {
                            parent = el.parentElement;
                            if (parent && parent[matches](selector)) {
                                return parent;
                            }
                            el = parent;
                        }
                    }
                    helper.closest = closest;
                    function addClass1n(node, clazz) {
                        if (node && node.constructor !== HTMLCollection) {
                            var children = node.querySelectorAll("." + render.CHILD_CELL_CLS);
                            if (children.length > 0)
                                addClass(children, clazz);
                            else
                                addClass(node, clazz);
                            return;
                        }
                        for (var i = 0; i < node.length; i++) {
                            var children = node[i].querySelectorAll("." + render.CHILD_CELL_CLS);
                            if (children.length > 0)
                                addClass(children, clazz);
                            else
                                addClass(node[i], clazz);
                        }
                    }
                    helper.addClass1n = addClass1n;
                    function removeClass1n(node, clazz) {
                        if (node && node.constructor !== HTMLCollection) {
                            var children = node.querySelectorAll("." + render.CHILD_CELL_CLS);
                            if (children.length > 0)
                                removeClass(children, clazz);
                            else
                                removeClass(node, clazz);
                            return;
                        }
                        for (var i = 0; i < node.length; i++) {
                            var children = node[i].querySelectorAll("." + render.CHILD_CELL_CLS);
                            if (children.length > 0)
                                removeClass(children, clazz);
                            else
                                removeClass(node[i], clazz);
                        }
                    }
                    helper.removeClass1n = removeClass1n;
                    function addClass(node, clazz) {
                        if (node && node.constructor !== HTMLCollection && node.constructor !== NodeList) {
                            node.classList.add(clazz);
                            return;
                        }
                        for (var i = 0; i < node.length; i++) {
                            if (!node[i].classList.contains(clazz)) {
                                node[i].classList.add(clazz);
                            }
                        }
                    }
                    helper.addClass = addClass;
                    function removeClass(node, clazz) {
                        if (node && node.constructor !== HTMLCollection && node.constructor !== NodeList) {
                            node.classList.remove(clazz);
                            return;
                        }
                        for (var i = 0; i < node.length; i++) {
                            if (node[i].classList.contains(clazz)) {
                                node[i].classList.remove(clazz);
                            }
                        }
                    }
                    helper.removeClass = removeClass;
                    function hasClass(node, clazz) {
                        return node.classList.contains(clazz);
                    }
                    helper.hasClass = hasClass;
                    function indexInParent(node) {
                        var parent = node.parentElement;
                        if (!parent)
                            return;
                        var children = parent.children;
                        var index = 0;
                        for (var i = 0; i < children.length; i++) {
                            if (children[i] === node)
                                return index;
                            if (children[i].nodeType === 1)
                                index++;
                        }
                        return -1;
                    }
                    helper.indexInParent = indexInParent;
                    function addClassList(cell, clazz) {
                        if (!clazz)
                            return;
                        clazz.split(" ").forEach(function (c, i) {
                            cell.classList.add(c);
                        });
                    }
                    helper.addClassList = addClassList;
                })(helper || (helper = {}));
                var widget;
                (function (widget) {
                    widget.MENU = "menu";
                    widget.POPUP = "popup";
                    widget.MENU_CLS = "x-context-menu";
                    widget.POPUP_CLS = "x-popup-panel";
                    widget.PARTITION_CLS = "partition";
                    widget.MENU_ITEM_CLS = "menu-item";
                    widget.MENU_ICON_CLS = "menu-icon";
                    widget.DISABLED_CLS = "disabled";
                    var XWidget = (function () {
                        function XWidget($selector) {
                            this.$selector = $selector;
                        }
                        XWidget.prototype.getTable = function () {
                            this.$table = helper.closest(this.$selector, "table");
                        };
                        return XWidget;
                    }());
                    var Tooltip = (function (_super) {
                        __extends(Tooltip, _super);
                        function Tooltip($selector, options) {
                            _super.call(this, $selector);
                            this.options = options;
                            this.defaultOpts = {
                                showRight: true,
                                width: "100px"
                            };
                            this.initialize();
                        }
                        Tooltip.prototype.initialize = function () {
                            var self = this;
                            $.extend(true, self.options, self.defaultOpts);
                            self.$selector.addXEventListener(events.MOUSE_OVER, function (evt) {
                                self.getTable();
                                if (self.$table.length === 0)
                                    return;
                                var $t2 = $.data(self.$table, internal.TOOLTIP);
                                if (!$t2) {
                                    $t2 = $("<div/>").addClass(cssClass(self.options));
                                    $t2.appendTo("body");
                                    $.data(self.$table, internal.TOOLTIP, $t2);
                                }
                                $t2.empty().append(self.options.sources).css({ visibility: "visible" })
                                    .position({ my: "left top", at: "left+" + self.$selector.offsetWidth + " top+5", of: self.$selector });
                            });
                            self.$selector.addXEventListener(events.MOUSE_OUT, function (evt) {
                                self.getTable();
                                if (!self.$table)
                                    return;
                                var $t2 = $.data(self.$table, internal.TOOLTIP);
                                if (!$t2 || $t2.css("display") === "none")
                                    return;
                                $t2.css({ visibility: "hidden" });
                            });
                        };
                        return Tooltip;
                    }(XWidget));
                    widget.Tooltip = Tooltip;
                    var Popup = (function (_super) {
                        __extends(Popup, _super);
                        function Popup($selector) {
                            _super.call(this, $selector);
                            this.initialize();
                        }
                        Popup.prototype.initialize = function () {
                            var self = this;
                            self.$selector.addXEventListener(events.MOUSE_DOWN, function (evt) {
                                self.getTable();
                                if (evt.ctrlKey && $.data(helper.closest(self.$table, "." + NAMESPACE), NAMESPACE).determination)
                                    return;
                                self.click(evt);
                            });
                        };
                        return Popup;
                    }(XWidget));
                    var ContextMenu = (function (_super) {
                        __extends(ContextMenu, _super);
                        function ContextMenu($selector, items) {
                            _super.call(this, $selector);
                            this.items = items;
                        }
                        ContextMenu.prototype.click = function (evt) {
                            var self = this;
                            var $menu = $.data(self.$table, internal.CONTEXT_MENU);
                            if (!$menu) {
                                $menu = $("<ul/>").addClass(widget.MENU_CLS).appendTo("body").hide();
                                _.forEach(self.items, function (item) {
                                    self.createItem($menu, item);
                                });
                                $.data(self.$table, internal.CONTEXT_MENU, $menu);
                            }
                            if ($menu.css("display") === "none") {
                                $menu.show().css({ top: evt.pageY, left: evt.pageX });
                            }
                            else {
                                $menu.hide();
                            }
                            var $pu = $.data(self.$table, internal.POPUP);
                            if ($pu && $pu.css("display") !== "none") {
                                $pu.hide();
                            }
                            update.outsideClick(helper.closest(self.$table, "." + NAMESPACE), self.$selector);
                            evt.stopPropagation();
                            hideIfOutside($menu);
                        };
                        ContextMenu.prototype.createItem = function ($menu, item) {
                            if (item.id === widget.PARTITION_CLS) {
                                $("<li/>").addClass(widget.MENU_ITEM_CLS + " " + widget.PARTITION_CLS).appendTo($menu);
                                return;
                            }
                            var $li = $("<li/>").addClass(widget.MENU_ITEM_CLS).text(item.text)
                                .on(events.CLICK_EVT, function (evt) {
                                if (item.disabled)
                                    return;
                                item.selectHandler(item.id);
                                $menu.hide();
                            }).appendTo($menu);
                            if (item.disabled) {
                                $li.addClass(widget.DISABLED_CLS);
                            }
                            if (item.icon) {
                                $li.append($("<span/>").addClass(widget.MENU_ICON_CLS + " " + item.icon));
                            }
                        };
                        return ContextMenu;
                    }(Popup));
                    widget.ContextMenu = ContextMenu;
                    var MenuItem = (function () {
                        function MenuItem(text, selectHandler, disabled, icon) {
                            this.text = text;
                            this.selectHandler = selectHandler ? selectHandler : $.noop();
                            this.disabled = disabled;
                            this.icon = icon;
                        }
                        return MenuItem;
                    }());
                    widget.MenuItem = MenuItem;
                    var PopupPanel = (function (_super) {
                        __extends(PopupPanel, _super);
                        function PopupPanel($selector, provider, position) {
                            _super.call(this, $selector);
                            this.provider = provider;
                            this.position = position;
                        }
                        PopupPanel.prototype.click = function (evt) {
                            var self = this;
                            var $pu = $.data(self.$table, internal.POPUP);
                            var $cell = evt.target;
                            if (!selector.is(evt.target, "td")) {
                                $cell = helper.closest(evt.target, "td");
                            }
                            var coord = helper.getCellCoord($cell);
                            self.$panel = self.provider(coord.columnKey);
                            if (!$pu) {
                                $pu = self.$panel.addClass(widget.POPUP_CLS).hide();
                                $.data(self.$table, internal.POPUP, $pu);
                            }
                            if ($pu.css("display") === "none") {
                                var pos = eventPageOffset(evt, false);
                                $pu.show().css(self.getPosition($pu, pos, self.position || "top left"));
                                events.trigger(self.$table, events.POPUP_SHOWN, $(evt.target));
                                self.addListener($pu, $(evt.target));
                            }
                            else {
                                $pu.hide();
                            }
                            var $menu = $.data(self.$table, internal.CONTEXT_MENU);
                            if ($menu && $menu.css("display") !== "none") {
                                $menu.hide();
                            }
                            evt.stopPropagation();
                            hideIfOutside($pu);
                        };
                        PopupPanel.prototype.addListener = function ($pu, $t) {
                            var self = this;
                            $pu.off(events.POPUP_INPUT_END);
                            $pu.on(events.POPUP_INPUT_END, function (evt) {
                                var ui = evt.detail;
                                var $header = helper.closest(self.$selector, "table").parentElement;
                                if ($header.classList.contains(HEADER)) {
                                    var ds = helper.getDataSource($header);
                                    if (!ds || ds.length === 0)
                                        return;
                                    var coord = helper.getCellCoord($t[0]);
                                    ds[coord.rowIdx][coord.columnKey] = ui.value;
                                    $t.text(ui.value);
                                    $pu.hide();
                                }
                            });
                        };
                        PopupPanel.prototype.getPosition = function ($pu, pos, my) {
                            if (my === "top left") {
                                return { top: pos.pageY - $pu.outerHeight() - 49, left: pos.pageX - $pu.outerWidth() };
                            }
                            else if (my === "bottom left") {
                                return { top: pos.pageY - 49, left: pos.pageX - $pu.outerWidth() };
                            }
                            else if (my === "top right") {
                                return { top: pos.pageY - $pu.outerHeight() - 49, left: pos.pageX };
                            }
                            else if (my === "bottom right") {
                                return { top: pos.pageY - 49, left: pos.pageX };
                            }
                        };
                        return PopupPanel;
                    }(Popup));
                    widget.PopupPanel = PopupPanel;
                    function bind(row, rowIdx, headerPopupFt) {
                        var wType;
                        if (!headerPopupFt)
                            return;
                        if (headerPopupFt.menu) {
                            _.forEach(headerPopupFt.menu.rows, function (rId) {
                                if (rId === rowIdx) {
                                    new ContextMenu(row, headerPopupFt.menu.items);
                                    wType = widget.MENU;
                                    return false;
                                }
                            });
                        }
                        if (wType)
                            return;
                        if (headerPopupFt.popup) {
                            _.forEach(headerPopupFt.popup.rows, function (rId) {
                                if (rId === rowIdx) {
                                    new PopupPanel(row, headerPopupFt.popup.provider);
                                    wType = widget.POPUP;
                                    return false;
                                }
                            });
                        }
                        return wType;
                    }
                    widget.bind = bind;
                    function textOverflow($cell) {
                        $cell.addXEventListener(events.MOUSE_ENTER + ".celloverflow", function (evt) {
                            var $target = $(evt.target);
                            if (!displayFullText($target)) {
                                var $link = $target.find("a");
                                if ($link.length > 0) {
                                    displayFullText($link, $target);
                                }
                            }
                        });
                    }
                    widget.textOverflow = textOverflow;
                    function displayFullText($target, $s) {
                        if ($target.outerWidth() < $target[0].scrollWidth) {
                            var $container_1 = $s ? $s : $target;
                            var $view_1 = $("<div />").addClass("x-cell-overflow")
                                .text($target.text() || $target.val())
                                .appendTo("body")
                                .position({
                                my: "left top",
                                at: "left bottom",
                                of: $container_1,
                                collision: "flip"
                            }).on(events.MOUSE_OVER, function (evt) {
                                $view_1.remove();
                            });
                            $container_1[0].addXEventListener(events.MOUSE_LEAVE + ".celloverflow", function (evt) {
                                $view_1.remove();
                                setTimeout(function () {
                                    $container_1[0].removeXEventListener(events.MOUSE_LEAVE + ".celloverflow");
                                }, 0);
                            });
                            return true;
                        }
                    }
                    function hideIfOutside($w) {
                        $(document).on(events.MOUSE_DOWN, function (evt) {
                            if (outsideOf($w, evt.target)) {
                                $w.hide();
                            }
                        });
                        var outsideOf = function ($container, target) {
                            return !$container.is(target) && $container.has(target).length === 0;
                        };
                    }
                    function eventPageOffset(evt, isFixed) {
                        var scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
                        var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
                        var $contentsArea = $("#contents-area");
                        return isFixed ? { pageX: evt.pageX + $contentsArea.scrollLeft() - scrollLeft,
                            pageY: evt.pageY + $contentsArea.scrollTop() - scrollTop }
                            : { pageX: evt.pageX + $contentsArea.scrollLeft(),
                                pageY: evt.pageY + $contentsArea.scrollTop() };
                    }
                    function cssClass(options) {
                        var css = options.showBelow ? 'bottom' : 'top';
                        css += '-';
                        css += (options.showRight ? 'right' : 'left');
                        css += '-tooltip';
                        return css;
                    }
                })(widget || (widget = {}));
            })(exTable = ui_1.exTable || (ui_1.exTable = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=extable.js.map