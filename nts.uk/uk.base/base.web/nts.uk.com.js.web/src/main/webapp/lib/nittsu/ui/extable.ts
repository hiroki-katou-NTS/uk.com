/// <reference path="../reference.ts"/>

module nts.uk.ui.exTable {
     
    let NAMESPACE = "extable";
    let DISTANCE: number = 3;
    let SPACE: number = 10;
    let HEADER = "xheader";
    let HEADER_PRF = "ex-header-";
    let BODY_PRF = "ex-body-";
    let HEADER_TBL_PRF = "extable-header-";
    let BODY_TBL_PRF = "extable-body-";
    let H_BTN_CLS = "ex-height-btn";
    let LEFTMOST = "leftmost";
    let MIDDLE = "middle";
    let DETAIL = "detail";
    let VERTICAL_SUM = "vert-sum";
    let HORIZONTAL_SUM = "horz-sum";
    let LEFT_HORZ_SUM = "left-horz-sum";
    let CRUD = "crud";
    let ADD_ROW = "add-row";
    let DEL_ROWS = "delete-rows";
    let H_BTN_HEIGHT = "24px";
    // Body height setting mode
    let DYNAMIC = "dynamic";
    let FIXED = "fixed";
    // Update mode
    let COPY_PASTE = "copyPaste";
    let EDIT = "edit";
    let STICK = "stick";
    let Connector = {};
    
    export class ExTable {
        $container: JQuery;
        $commander: JQuery;
        $follower: JQuery;
        headerHeight: string;
        bodyHeight: string;
        bodyRowHeight: string;
        horzSumHeaderHeight: string;
        horzSumBodyHeight: string;
        horzSumBodyRowHeight: string;
        leftmostHeader: any;
        leftmostContent: any;
        middleHeader: any;
        middleContent: any;
        detailHeader: any
        detailContent: any;
        verticalSumHeader: any;
        verticalSumContent: any;
        leftHorzSumHeader: any;
        leftHorzSumContent: any;
        horizontalSumHeader: any;
        horizontalSumContent: any;
        headers: any;
        bodies: any;
        features: any;
        // Settings
        areaResize: boolean;
        heightSetter: any;
        // dynamic or fixed
        bodyHeightSetMode: string = DYNAMIC;
        windowXOccupation: number = 0;
        windowYOccupation: number = 0;
        updateMode: string = EDIT;
        pasteOverWrite: boolean = true;
        stickOverWrite: boolean = true;
        viewMode: string;
        determination: any;
        modifications: any;
        
        constructor($container: JQuery, options: any) {
            this.$container = $container;
            this.$commander = options.primaryTable;
            this.$follower = options.secondaryTable;
            this.bodyRowHeight = options.bodyRowHeight;
            this.headerHeight = options.headerHeight;
            this.bodyHeight = options.bodyHeight;
            this.horzSumHeaderHeight = options.horizontalSumHeaderHeight;
            this.horzSumBodyHeight = options.horizontalSumBodyHeight;
            this.horzSumBodyRowHeight = options.horizontalSumBodyRowHeight;
            this.areaResize = options.areaResize;
            this.heightSetter = options.heightSetter;
            this.bodyHeightSetMode = options.bodyHeightMode;
            this.windowXOccupation = options.windowXOccupation;
            this.windowYOccupation = options.windowYOccupation;
            if (options.updateMode) { 
                this.updateMode = options.updateMode; 
            }
            this.pasteOverWrite = options.pasteOverWrite;
            this.stickOverWrite = options.stickOverWrite;
            this.viewMode = options.viewMode;
            this.determination = options.determination;
            this.features = options.features;
            this.$container.data(internal.X_OCCUPY, this.windowXOccupation);
            this.$container.data(internal.Y_OCCUPY, this.windowYOccupation);
            helper.makeConnector();
        }
        setUpdateMode(updateMode: any) {
            this.updateMode = updateMode;
            this.detailContent.updateMode = updateMode;
        }
        setViewMode(mode: any) {
            this.viewMode = mode;
            this.detailContent.viewMode = mode;
        }
        LeftmostHeader(leftmostHeader: any) {
            this.leftmostHeader = _.cloneDeep(leftmostHeader);
            this.setHeaderClass(this.leftmostHeader, LEFTMOST);
            return this;
        }
        LeftmostContent(leftmostContent: any) {
            this.leftmostContent = _.cloneDeep(leftmostContent);
            this.setBodyClass(this.leftmostContent, LEFTMOST);
            this.leftmostContent.updateMode = this.updateMode;
            if (feature.isEnable(this.features, feature.UPDATING)) {
                this.leftmostHeader.width = parseInt(this.leftmostHeader.width) + controls.CHECKBOX_COL_WIDTH + "px";
                controls.addCheckBoxDef([ this.leftmostHeader, this.leftmostContent ]);
            }
            return this;
        }
        MiddleHeader(middleHeader: any) {
            this.middleHeader = _.cloneDeep(middleHeader);
            this.setHeaderClass(this.middleHeader, MIDDLE);
            this.middleHeader.updateMode = this.updateMode;
            return this;
        }
        MiddleContent(middleContent: any) {
            this.middleContent = _.cloneDeep(middleContent);
            this.setBodyClass(this.middleContent, MIDDLE);
            return this;
        }
        DetailHeader(detailHeader: any) {
            this.detailHeader = _.cloneDeep(detailHeader);
            this.setHeaderClass(this.detailHeader, DETAIL);
            return this;
        }
        DetailContent(detailContent: any) {
            this.detailContent = _.cloneDeep(detailContent);
            this.setBodyClass(this.detailContent, DETAIL);
            this.detailContent.updateMode = this.updateMode;
            this.detailContent.viewMode = this.viewMode;
            return this;
        }
        VerticalSumHeader(verticalSumHeader: any) {
            this.verticalSumHeader = _.cloneDeep(verticalSumHeader);
            this.setHeaderClass(this.verticalSumHeader, VERTICAL_SUM);
            return this;
        }
        VerticalSumContent(verticalSumContent: any) {
            this.verticalSumContent = _.cloneDeep(verticalSumContent);
            this.setBodyClass(this.verticalSumContent, VERTICAL_SUM);
            return this;
        }
        LeftHorzSumHeader(leftHorzSumHeader: any) {
            this.leftHorzSumHeader = _.cloneDeep(leftHorzSumHeader);
            this.setHeaderClass(this.leftHorzSumHeader, LEFT_HORZ_SUM);
            return this;
        }
        LeftHorzSumContent(leftHorzSumContent: any) {
            this.leftHorzSumContent = _.cloneDeep(leftHorzSumContent);
            this.setBodyClass(this.leftHorzSumContent, LEFT_HORZ_SUM);
            return this;
        }
        HorizontalSumHeader(horizontalSumHeader: any) {
            this.horizontalSumHeader = _.cloneDeep(horizontalSumHeader);
            this.setHeaderClass(this.horizontalSumHeader, HORIZONTAL_SUM);
            return this;
        }
        HorizontalSumContent(horizontalSumContent: any) {
            this.horizontalSumContent = _.cloneDeep(horizontalSumContent);
            this.setBodyClass(this.horizontalSumContent, HORIZONTAL_SUM);
            return this;
        }
        setHeaderClass(options: any, part: string) {
            options.tableClass = HEADER_TBL_PRF + part;
            options.containerClass = HEADER_PRF + part;
        }
        setBodyClass(options: any, part: string) {
            options.tableClass = BODY_TBL_PRF + part;
            options.containerClass = BODY_PRF + part;
        }
        
        /**
         * Create.
         */
        create() {
            let self = this;
            let left: string = "0px";
            let top: string = "0px";
            if (!self.satisfyPrebuild()) return;
            self.headers = _.filter([ self.leftmostHeader, self.middleHeader, self.detailHeader, self.verticalSumHeader ], (h) => {
                return !util.isNullOrUndefined(h);
            });
            self.bodies = _.filter([ self.leftmostContent, self.middleContent, self.detailContent, self.verticalSumContent ], (b) => {
                return !util.isNullOrUndefined(b);
            });
            
            // Get part widths
            let widthParts, gridHeight;
            storage.area.getPartWidths(self.$container).ifPresent(function(parts) {
                widthParts = JSON.parse(parts);
                return null;
            });
            // Get grid height
            storage.tableHeight.get(self.$container).ifPresent(function(height) {
                gridHeight = JSON.parse(height);
                return null;
            });
            
            self.$container.addClass(NAMESPACE);
            self.$container.data(NAMESPACE, self);
            let headerWrappers = [], bodyWrappers = [];
            for (let i = 0; i < self.headers.length; i++) {
                if (!util.isNullOrUndefined(self.headers[i])) {
                    self.headers[i].overflow = "hidden";
                    self.headers[i].height = self.headerHeight;
                    self.headers[i].isHeader = true;
                    self.setWrapperWidth(self.headers[i], widthParts);
                    let $headerWrapper = render.createWrapper("0px", left, self.headers[i]);
                    self.$container.append($headerWrapper.addClass(HEADER));
                    render.process($headerWrapper, self.headers[i]);
                    left = parseInt(left) + parseInt(self.headers[i].width) + DISTANCE + "px";
                    top = parseInt(self.headers[i].height) + DISTANCE + "px";
                    headerWrappers.push($headerWrapper);
                }
            }
            left = "0px";
            for (let i = 0; i < self.bodies.length; i++) {
                let $bodyWrapper;
                if (!util.isNullOrUndefined(self.bodies[i])) {
                    self.bodies[i].rowHeight = self.bodyRowHeight;
                    self.bodies[i].height = gridHeight ? gridHeight : self.bodyHeight;
                    self.bodies[i].width = self.headers[i].width;
                    self.setWrapperWidth(self.bodies[i], widthParts);
                    $bodyWrapper = render.createWrapper(top, left, self.bodies[i]);
                    self.$container.append($bodyWrapper);
                    if (i === self.bodies.length - 1 && !util.isNullOrUndefined($bodyWrapper)) {
                        self.bodies[i].overflow = "scroll";
                        self.bodies[i].width = $bodyWrapper.width() + helper.getScrollWidth();
                        self.bodies[i].height = $bodyWrapper.height() + helper.getScrollWidth();
                        scroll.syncDoubDirVerticalScrolls(_.concat(bodyWrappers, $bodyWrapper));
                    } else if (i > 0 && i < self.bodies.length - 1) {
                        self.bodies[i].overflowX = "scroll";
                        self.bodies[i].overflowY = "hidden";
                        self.bodies[i].height = $bodyWrapper.height() + helper.getScrollWidth();
                        scroll.bindVertWheel($bodyWrapper);
                    } else {
                        scroll.bindVertWheel($bodyWrapper);
                    }
                    render.process($bodyWrapper, self.bodies[i]);
                    left = parseInt(left) + parseInt(self.bodies[i].width) + DISTANCE + "px";
                    if (self.bodies[i].containerClass !== BODY_PRF + DETAIL) {
                        scroll.syncHorizontalScroll(headerWrappers[i], $bodyWrapper);
                    }
                    bodyWrappers.push($bodyWrapper);
                    if (feature.isEnable(self.headers[i].features, feature.COLUMN_RESIZE)) {
                        new resize.ColumnAdjuster(headerWrappers[i].find("table"), $bodyWrapper.find("table")).handle();
                    }
                }
            }
            
            self.createHorzSums();
            self.setupCrudArea();
            self.generalSettings(headerWrappers, bodyWrappers);
        }
        
        /**
         * Create horizontal sums.
         */
        createHorzSums() {
            let self = this;
            let $detailHeader = self.$container.find("." + HEADER_PRF + DETAIL);
            let $detailContent = self.$container.find("." + BODY_PRF + DETAIL);
            let headerTop = $detailHeader.height() + $detailContent.height() + DISTANCE + helper.getScrollWidth() + SPACE;
            let bodyTop = headerTop + parseInt(self.horzSumHeaderHeight) + DISTANCE + "px";
            let sumPosLeft = $detailHeader.css("left");
            let leftHorzWidth = parseInt(sumPosLeft) - DISTANCE;
            let $leftSumHeaderWrapper, $leftSumContentWrapper, $sumHeaderWrapper, $sumContentWrapper;
            // Items summary
            if (self.leftHorzSumHeader) {
                self.leftHorzSumHeader.height = self.horzSumHeaderHeight;
                self.leftHorzSumHeader.width = leftHorzWidth;
                self.leftHorzSumHeader.overflow = "hidden";
                self.leftHorzSumHeader.isHeader = true;
                $leftSumHeaderWrapper = render.createWrapper(headerTop + "px", "0xp", self.leftHorzSumHeader);
                self.$container.append($leftSumHeaderWrapper.addClass(HEADER));
                render.process($leftSumHeaderWrapper, self.leftHorzSumHeader);
            }
            if (self.leftHorzSumContent) {
                self.leftHorzSumContent.rowHeight = self.horzSumBodyRowHeight;
                self.leftHorzSumContent.height = parseInt(self.horzSumBodyHeight) + helper.getScrollWidth() + "px";
                self.leftHorzSumContent.width = leftHorzWidth;
                $leftSumContentWrapper = render.createWrapper(bodyTop, "0px", self.leftHorzSumContent);
                self.leftHorzSumContent.overflowX = "scroll";
                self.leftHorzSumContent.overflowY = "hidden";
                self.$container.append($leftSumContentWrapper);
                render.process($leftSumContentWrapper, self.leftHorzSumContent);
                scroll.bindVertWheel($leftSumContentWrapper);
            }
            
            // Main summary
            if (self.horizontalSumHeader) {
                self.horizontalSumHeader.height = self.horzSumHeaderHeight;
                self.horizontalSumHeader.width = $detailHeader.width();
                self.horizontalSumHeader.overflow = "hidden";
                self.horizontalSumHeader.isHeader = true;
                $sumHeaderWrapper = render.createWrapper(headerTop + "px", sumPosLeft, self.horizontalSumHeader);
                self.$container.append($sumHeaderWrapper.addClass(HEADER));
                render.process($sumHeaderWrapper, self.horizontalSumHeader);
            }
            if (self.horizontalSumContent) {
                self.horizontalSumContent.rowHeight = self.horzSumBodyRowHeight;
                self.horizontalSumContent.height = parseInt(self.horzSumBodyHeight) + helper.getScrollWidth() + "px";
                self.horizontalSumContent.width = $detailContent.width(); 
                $sumContentWrapper = render.createWrapper(bodyTop, sumPosLeft, self.horizontalSumContent);
                self.horizontalSumContent.overflow = "scroll";
                self.$container.append($sumContentWrapper);
                render.process($sumContentWrapper, self.horizontalSumContent);
                scroll.syncHorizontalScroll($leftSumHeaderWrapper, $leftSumContentWrapper);
                scroll.syncDoubDirVerticalScrolls([ $leftSumContentWrapper, $sumContentWrapper ]);
            }
            if (self.$commander) {
                self.$commander.on(events.MOUSEIN_COLUMN, function(evt: any, colIndex: any) {
                    helper.highlightColumn(self.$container, colIndex);
                });
                self.$commander.on(events.MOUSEOUT_COLUMN, function(evt: any, colIndex: any) {
                    helper.unHighlightColumn(self.$container, colIndex);
                });
                let pHorzHeader = self.$commander.find("." + HEADER_PRF + HORIZONTAL_SUM);
                let pHorzBody = self.$commander.find("." + BODY_PRF + HORIZONTAL_SUM);
                let stream = _.concat(self.$commander.find("div[class*='" + DETAIL + "']").toArray().map(function(val) {
                            return $(val);
                        }), pHorzHeader, pHorzBody, $detailHeader, $detailContent, $sumHeaderWrapper, $sumContentWrapper);
                scroll.syncDoubDirHorizontalScrolls(stream);
            } else if (self.$follower) { 
            } else {
                scroll.syncDoubDirHorizontalScrolls([ $detailHeader, $detailContent, $sumHeaderWrapper, $sumContentWrapper ]);
            }
        }
        
        /**
         * Setup crud area.
         */
        setupCrudArea() {
            let self = this;
            let updateF = feature.find(self.features, feature.UPDATING);
            if (updateF) {
                let $area = $("<div/>").addClass(NAMESPACE + "-" + CRUD);
                let $rowAdd = $("<button/>").addClass(NAMESPACE + "-" + ADD_ROW).on(events.CLICK_EVT, function() {
                    update.insertNewRow(self.$container);
                }).appendTo($area);
                let $rowDel = $("<button/>").addClass(NAMESPACE + "-" + DEL_ROWS).on(events.CLICK_EVT, function() {
                    update.deleteRows(self.$container);
                }).appendTo($area);
                let dftRowAddTxt = "新規行の追加";
                let dftRowDelTxt = "行の削除";
                if (updateF.addNew) {
                    $rowAdd.addClass(updateF.addNew.buttonClass || "proceed").text(updateF.addNew.buttonText || dftRowAddTxt);    
                } else {
                    $rowAdd.addClass("proceed").text(dftRowAddTxt);
                }
                if (updateF.delete) {
                    $rowDel.addClass(updateF.delete.buttonClass || "danger").text(updateF.delete.buttonText || dftRowDelTxt);
                } else {
                    $rowDel.addClass("danger").text(dftRowDelTxt);
                }
                self.$container.before($area);
            }
        }
        
        /**
         * General settings.
         */
        generalSettings(headerWrappers: Array<JQuery>, bodyWrappers: Array<JQuery>) {
            let self = this;
            self.$container.on(events.BODY_HEIGHT_CHANGED, resize.onBodyHeightChanged);
//            if (self.heightSetter && self.heightSetter.showBodyHeightButton) {
//                
//                let $lastHeader = headerWrappers[headerWrappers.length - 1];
//                let btnPosTop = $lastHeader.height() - parseInt(H_BTN_HEIGHT) - DISTANCE;
//                let btnPosLeft = parseInt($lastHeader.css("left")) + $lastHeader.outerWidth();
//                let $heightSetBtn = $("<button/>").addClass(H_BTN_CLS)
//                    .css({ position: "absolute", height: H_BTN_HEIGHT, top: btnPosTop + "px", left: btnPosLeft + "px" })
//                    .text("H").on(events.CLICK_EVT, self.heightSetter.click);
//                $lastHeader.after($heightSetBtn);
//            }
            resize.fitWindowWidth(self.$container);
            $(window).on(events.RESIZE, $.proxy(resize.fitWindowWidth, self, self.$container));
            let horzSumExists = !util.isNullOrUndefined(self.horizontalSumHeader);
            if (self.bodyHeightSetMode === DYNAMIC) {
                resize.fitWindowHeight(self.$container, bodyWrappers, horzSumExists);
                $(window).on(events.RESIZE, $.proxy(resize.fitWindowHeight, self, self.$container, bodyWrappers, horzSumExists));
            } else {
                let cHeight = 0;
                let stream = self.$container.find("div[class*='" + DETAIL + "'], div[class*='" + LEFT_HORZ_SUM + "']");
                stream.each(function() {
                    cHeight += $(this).height();
                });
                if (stream.length === 4) {
                    cHeight += (SPACE + DISTANCE);
                }
                self.$container.height(cHeight + SPACE);
            }
            
            if (self.$follower) {
                self.$follower.on(events.COMPLETED, function() {
                    if (self.areaResize) {
                        new resize.AreaAdjuster(self.$container, headerWrappers, bodyWrappers, self.$follower).handle();
                        self.$container.on(events.AREA_RESIZE_END, $.proxy(resize.onAreaComplete, self));
                    }
                    let formerWidth = 0, latterWidth = 0;
                    _.forEach(headerWrappers, function(header) {
                        if (header.hasClass(HEADER_PRF + LEFTMOST)) {
                            formerWidth += header.width();
                        } else if (header.hasClass(HEADER_PRF + MIDDLE)) {
                            formerWidth += header.width() + DISTANCE;   
                        } else if (header.hasClass(HEADER_PRF + DETAIL)) {
                            latterWidth += header.width();
                        }
                    });
                    let $lm = self.$follower.find("div[class*='" + LEFTMOST + "']");
                    let diff = formerWidth - parseInt($lm[0].style.width);
                    $lm.width(formerWidth);
                    let $depDetailHeader = self.$follower.find("." + HEADER_PRF + DETAIL);
                    $depDetailHeader.width(latterWidth);
                    let $depDetail = self.$follower.find("." + BODY_PRF + DETAIL);
                    let left = parseInt($depDetail.css("left")) + diff;
                    $depDetailHeader.css("left", left);
                    $depDetail.css("left", left);
                    $depDetail.width(latterWidth + helper.getScrollWidth());
                    let depLmHeader = _.filter($lm, function(e) {
                        return $(e).hasClass(HEADER_PRF + LEFTMOST);
                    });
                    resize.saveSizes(self.$follower, $(depLmHeader[0]), $depDetailHeader, formerWidth, latterWidth);
                });
            } else if (self.areaResize) {
                new resize.AreaAdjuster(self.$container, headerWrappers, bodyWrappers, self.$follower).handle();
                self.$container.on(events.AREA_RESIZE_END, $.proxy(resize.onAreaComplete, self));
            }
            storage.area.init(self.$container, headerWrappers);
            storage.tableHeight.init(self.$container);
            
            // Edit done
            update.editDone(self.$container);
            $(document).on(events.CLICK_EVT, function(evt: any) {
                update.outsideClick(self.$container, $(evt.target));
            });
            events.onModify(self.$container);
            selection.checkUp(self.$container);
            copy.on(self.$container.find("." + BODY_PRF + DETAIL), self.updateMode);
            self.$container.on(events.OCCUPY_UPDATE, function(evt: any, reserve: any) {
                if (self.bodyHeightSetMode === FIXED) return;
                if (reserve && reserve.x) {
                    self.$container.data(internal.X_OCCUPY, reserve.x);
                    resize.fitWindowWidth(self.$container);
                }
                if (reserve && reserve.y) {
                    self.$container.data(internal.Y_OCCUPY, reserve.y);
                    resize.fitWindowHeight(self.$container, bodyWrappers, horzSumExists);
                }
            });
            if (self.$commander) {
                events.trigger(self.$container, events.COMPLETED);
            }
        }

        /**
         * Satisfy prebuild.
         */
        satisfyPrebuild() {
            if (util.isNullOrUndefined(this.$container) || util.isNullOrUndefined(this.headerHeight) 
                || util.isNullOrUndefined(this.bodyHeight) || util.isNullOrUndefined(this.bodyRowHeight)
                || util.isNullOrUndefined(this.horzSumBodyRowHeight)) 
                return false;
            return true;
        }
        
        /**
         * Set wrapper width.
         */
        setWrapperWidth(options: any, widthParts: any) {
            if (!widthParts) return;
            let width = widthParts[options.containerClass];
            if (!util.isNullOrUndefined(width)) {
                options.width = width + "px";
            } 
        }
    }
    
    module render {
        export let HIGHLIGHT_CLS: string = "highlight";
        export let CHILD_CELL_CLS: string = "child-cell";
        export let COL_ICON_CLS: string = "column-icon";
        
        /**
         * Process.
         */
        export function process($container: JQuery, options: any, isUpdate?: boolean) {
            let levelStruct = synthesizeHeaders(options);
            options.levelStruct = levelStruct;
            if (options.isHeader) {
                if (Object.keys(levelStruct).length > 1) {
                    groupHeader($container, options, isUpdate);
                    return;
                }
            } else {
                options.float = options.float === false ? false : true;
            }
            table($container, options, isUpdate);
        }
        
        /**
         * Group header.
         */
        function groupHeader($container: JQuery, options: any, isUpdate: boolean) {
            let $table = $("<table><tbody></tbody></table>").addClass(options.tableClass)
                            .css({ position: "relative", tableLayout: "fixed", width: "100%", borderCollapse: "separate" })
                            .appendTo($container);
            let $tbody = $table.find("tbody");
            if (!isUpdate) {
                $container.css({ height: options.height, width: options.width });
            }
            if (!util.isNullOrUndefined(options.overflow)) $container.css("overflow", options.overflow);
            else if (!util.isNullOrUndefined(options.overflowX) && !util.isNullOrUndefined(options.overflowY)) {
                $container.css("overflow-x", options.overflowX);
                $container.css("overflow-y", options.overflowY);
            }
            let $colGroup = $("<colgroup/>").insertBefore($tbody);
            generateColGroup($colGroup, options.columns);
            let painter: GroupHeaderPainter = new GroupHeaderPainter(options);
            painter.rows($tbody);
        }
        
        /**
         * Generate column group.
         */
        function generateColGroup($colGroup: JQuery, columns: any) {
            _.forEach(columns, function(col: any) {
                if (!util.isNullOrUndefined(col.group)) {
                    generateColGroup($colGroup, col.group);
                    return;
                }
                let $col = $("<col/>").width(col.width);
                $colGroup.append($col);
                if (col.visible === false) $col.hide();
            });
        }
        
        /**
         * Table.
         */
        export function table($container: JQuery, options: any, isUpdate: boolean) {
            let $table = $("<table><tbody></tbody></table>").addClass(options.tableClass)
                            .css({ position: "relative", tableLayout: "fixed", width: "100%", borderCollapse: "separate" })
                            .appendTo($container);
            let $tbody = $table.find("tbody");
            if (!isUpdate) {
                $container.css({ height: options.height, width: options.width });
            }
            if (!util.isNullOrUndefined(options.overflow)) $container.css("overflow", options.overflow);
            else if (!util.isNullOrUndefined(options.overflowX) && !util.isNullOrUndefined(options.overflowY)) {
                $container.css("overflow-x", options.overflowX);
                $container.css("overflow-y", options.overflowY);
            }
            let $colGroup = $("<colgroup/>").insertBefore($tbody);
            generateColGroup($colGroup, options.columns);
            
            let dataSource;
            if (!util.isNullOrUndefined(options.dataSource)) {
                 dataSource = options.dataSource;   
            } else {
                let item = {};
                _.forEach(options.columns, function(col: any) {
                    item[col.key] = col.headerText;
                });
                dataSource = [item]; 
            }
            begin($container, dataSource, options);
        }
        
        /**
         * Begin.
         */
        export function begin($container: JQuery, dataSource: Array<any>, options: any) {
            if (options.float) {
                let cloud: intan.Cloud = new intan.Cloud($container, dataSource, options);
                $container.data(internal.TANGI, cloud);
                cloud.renderRows(true);   
                return;
            }
            normal($container, dataSource, options);
        }
        
        /**
         * Normal.
         */
        export function normal($container: JQuery, dataSource: Array<any>, options: any) {
            let rowConfig = { css: { height: options.rowHeight }};
            let headerRowHeightFt;
            if (options.isHeader) {
                headerRowHeightFt = feature.find(options.features, feature.HEADER_ROW_HEIGHT);
            }
            let painter: Painter = new Painter($container, options);
            $container.data(internal.CANON, { _origDs: _.cloneDeep(dataSource), dataSource: dataSource, primaryKey: options.primaryKey, painter: painter });
            let $tbody = $container.find("tbody");
            _.forEach(dataSource, function(item: any, index: number) {
                if (!util.isNullOrUndefined(headerRowHeightFt)) {
                    rowConfig = { css: { height: headerRowHeightFt.rows[index] }};
                }
                $tbody.append(painter.row(item, rowConfig, index));
            });
        }
        
        /**
         * Synthesize headers.
         */
        export function synthesizeHeaders(options: any) {
            let level: any = {};
            peelStruct(options.columns, level, 0);
            let rowCount = Object.keys(level).length; 
            if (rowCount > 1) {
                _.forEach(Object.keys(level), function(key: any) {
                    _.forEach(level[key], function(col: any) {
                        if (util.isNullOrUndefined(col.colspan) || col.colspan === 1) {
                            col.rowspan = rowCount - parseInt(key);
                        }
                    });
                });
            }
            return level;
        }
        
        /**
         * Peel struct.
         */
        function peelStruct(columns: Array<any>, level: any, currentLevel: number) {
            let colspan = 0, noGroup = 0;
            _.forEach(columns, function(col: any) {
                let clonedCol = _.clone(col);
                let colCount = 0;
                if (!util.isNullOrUndefined(col.group)) {
                    colCount = col.group.length;
                    noGroup++;
                    let ret: any = peelStruct(col.group, level, currentLevel + 1);
                    if (!util.isNullOrUndefined(ret)) {
                        colCount += ret;
                    }
                    clonedCol.colspan = colCount;
                }
                if (util.isNullOrUndefined(level[currentLevel])) {
                    level[currentLevel] = [];
                }
                level[currentLevel].push(clonedCol);
                colspan += colCount;
            });
            return colspan !== 0 ? (colspan - noGroup) : undefined;
        }
        
        abstract class Conditional {
            options: any;
            columnsMap: {[ key: string ]: any };
            visibleColumns: Array<any>;
            hiddenColumns: Array<any>;
            visibleColumnsMap: {[ key: string ]: any };
            hiddenColumnsMap: {[ key: string ]: any };
            constructor(options: any) {
                this.options = options;
                let columns = helper.classifyColumns(options);
                this.visibleColumns = columns.visibleColumns;
                this.hiddenColumns = columns.hiddenColumns;
                this.visibleColumnsMap = helper.getColumnsMap(this.visibleColumns);
                this.hiddenColumnsMap = helper.getColumnsMap(this.hiddenColumns);
            }
        }
        
        export class Painter extends Conditional {
            $container: JQuery;
            constructor($container: JQuery, options: any) {
                super(options);
                this.$container = $container;
                if (!util.isNullOrUndefined(options.levelStruct)) {
                    this.columnsMap = helper.columnsMapFromStruct(options.levelStruct);
                } else {
                    this.columnsMap = _.groupBy(options.columns, "key");
                }
            }
            
            /**
             * Cell.
             */
            cell(rData: any, rowIdx: number, key: string): JQuery {
                let self = this;
                let cData = rData[key]; 
                let data = cData && _.isObject(cData) && cData.constructor !== Array && _.isFunction(self.options.view) ? 
                            helper.viewData(self.options.view, self.options.viewMode, cData) : cData;
                let column: any = self.columnsMap[key];
                if (util.isNullOrUndefined(column)) return;
                let ws = column.css && column.css.whiteSpace ? column.css.whiteSpace : "nowrap";
                let $td = $("<td/>").data(internal.VIEW, rowIdx + "-" + key)
                            .css({ borderWidth: "1px", overflow: "hidden", whiteSpace: ws, position: "relative" });
                self.highlight($td);
                
                if (!self.visibleColumnsMap[key]) $td.hide();
                if (!util.isNullOrUndefined(data) && data.constructor === Array) {
                    let incellHeight = parseInt(self.options.rowHeight) / 2 - 3;
                    let borderStyle = "solid 1px transparent";
                   _.forEach(data, function(item: any, idx: number) {
                       let $div = $("<div/>").addClass(CHILD_CELL_CLS).text(item);
                       if (idx < data.length - 1) {
                           $div.css({ borderTop: borderStyle, borderLeft: borderStyle, 
                                      borderRight: borderStyle, borderBottom: "dashed 1px #AAB7B8", top: "0px" });
                       } else {
                           $div.css({ border: borderStyle, top: (incellHeight + 2) + "px" });
                       }
                       $td.append($div.css({ position: "absolute", left: "0px", height: incellHeight + "px", 
                                               width: "98%", textAlign: "center" }));
                       if (column.handlerType) {
                           let handler = cellHandler.get(column.handlerType);
                           if (handler) handler($div, self.options, helper.call(column.supplier, rData, rowIdx, key));
                       }
                       cellHandler.rClick($div, column, helper.call(column.rightClick, rData, rowIdx, key));
                       spread.bindSticker($div, rowIdx, key, self.options);
                   });
                   style.detCell(self.$container, $td, rowIdx, key);
                   return $td.css({ padding: "0px" });
                }
                if (!util.isNullOrUndefined(column.handlerType) && !self.options.isHeader) {
                    let handler = cellHandler.get(column.handlerType);
                    if (!util.isNullOrUndefined(handler)) {
                        handler($td, self.options, helper.call(column.supplier, rData, rowIdx, key));
                    }
                }
                if (self.options.isHeader) {
                    if (!util.isNullOrUndefined(column.icon) && column.icon.for === "header") {
                        let $icon = $("<span/>").addClass(COL_ICON_CLS + " " + column.icon.class);
                        $icon.appendTo($td.css({ paddingLeft: column.icon.width }));
                        if (column.icon.popup && typeof column.icon.popup === "function") {
                            $icon.css({ cursor: "pointer" });
                            new widget.PopupPanel($icon, column.icon.popup(), "bottom right");
                        }
                        $("<div/>").html(data).appendTo($td);
                    } else if (helper.containsBr(data)) { 
                        $td.html(data); 
                    } else {
                        $td.text(data);
                    }
                } else if (!self.options.isHeader) {
                    if (!util.isNullOrUndefined(column.icon) && column.icon.for === "body") {
                        let $icon = $("<span/>").addClass(COL_ICON_CLS + " " + column.icon.class);
                        $icon.appendTo($td.css({ paddingLeft: column.icon.width }));
                    } else if (!column.control) {
                        $td.text(data);
                    }
                    controls.check($td, column, data, helper.call(column.handler, rData, rowIdx, key));
                    cellHandler.rClick($td, column, helper.call(column.rightClick, rData, rowIdx, key));
                } 
                spread.bindSticker($td, rowIdx, key, self.options);
                style.detCell(self.$container, $td, rowIdx, key);
                return $td;
            }
            
            /**
             * Row.
             */
            row(data: any, config: any, rowIdx: number): JQuery {
                let self = this;
                let $tr: any = $("<tr/>").css(config.css);
                let headerCellStyleFt, headerPopupFt, bodyCellStyleFt;
                if (self.options.isHeader) {
                    headerCellStyleFt = feature.find(self.options.features, feature.HEADER_CELL_STYLE);
                    headerPopupFt = feature.find(self.options.features, feature.HEADER_POP_UP);
                } else {
                    bodyCellStyleFt = feature.find(self.options.features, feature.BODY_CELL_STYLE);
                }
                
                let onChecked = function(checked, rowIndex) {
                    let $grid = self.options.isHeader ? self.$container.siblings("." + BODY_PRF + LEFTMOST)
                                : self.$container;
                    controls.tick(checked, $grid, self.options.isHeader, rowIndex);
                };
                if (!data[controls.CHECKED_KEY] && self.options.columns[0].key === controls.CHECKED_KEY) {
                    $tr.append($("<td/>").data(internal.VIEW, rowIdx + "-" + controls.CHECKED_KEY).css(controls.checkBoxCellStyles())
                        .append(controls.createCheckBox(self.$container, { initValue: false, onChecked: onChecked })));
                }
                _.forEach(Object.keys(data), function(key: any, index: number) {
                    if (!self.visibleColumnsMap[key] && !self.hiddenColumnsMap[key]) return;
                    if (key === controls.CHECKED_KEY) {
                        $tr.append($("<td/>").css(controls.checkBoxCellStyles())
                            .append(controls.createCheckBox(self.$container, { initValue: false, onChecked: onChecked })));
                        return;
                    }
                    let $cell = self.cell(data, rowIdx, key);
                    $tr.append($cell);
                    // Styles
                    if (!util.isNullOrUndefined(headerCellStyleFt)) {
                        _.forEach(headerCellStyleFt.decorator, function(colorDef: any) {
                            if (key === colorDef.columnKey) {
                                if ((!util.isNullOrUndefined(colorDef.rowId) && colorDef.rowId === rowIdx)
                                        || util.isNullOrUndefined(colorDef.rowId)) {
                                    $cell.addClass(colorDef.clazz);
                                    return false;
                                }
                            }
                        });
                    } else if (!util.isNullOrUndefined(bodyCellStyleFt)) {
                        _.forEach(bodyCellStyleFt.decorator, function(colorDef: any) {
                            if (key === colorDef.columnKey && data[self.options.primaryKey] === colorDef.rowId) {
                                let $childCells = $cell.find("." + CHILD_CELL_CLS);
                                if (!util.isNullOrUndefined(colorDef.innerIdx) && $childCells.length > 0) {
                                    let $child = $($childCells[colorDef.innerIdx]);
                                    $child.addClass(colorDef.clazz);
                                    if (colorDef.clazz === style.HIDDEN_CLS) {
                                        $child.data("hide", $child.text());
                                        $child.text("");
                                    }
                                } else {
                                    $cell.addClass(colorDef.clazz);
                                    if (colorDef.clazz == style.HIDDEN_CLS) {
                                        $cell.data("hide", $cell.text());
                                        $cell.text("");
                                    }
                                    return false;
                                }
                            }
                        });
                    }
                    if (util.isNullOrUndefined(self.columnsMap[key])) return;
                    let cellStyle = self.columnsMap[key].style;
                    if (!util.isNullOrUndefined(cellStyle)) {
                        cellStyle(new style.CellStyleParam($cell, data[key], data, rowIdx, key));
                    }
                });
                widget.bind($tr, rowIdx, headerPopupFt);
                style.detColumn(self.$container, $tr, rowIdx);
                return $tr;
            }
            
            /**
             * Highlight.
             */
            jqHighlight($td: JQuery) {
                let self = this;
                if (self.options.isHeader || self.options.containerClass !== BODY_PRF + DETAIL) return;
                let $targetContainer = self.$container;
                let $targetHeader = $targetContainer.siblings("." + self.options.containerClass.replace(BODY_PRF, HEADER_PRF));
                $td.on(events.MOUSE_OVER, function() {
                    let colIndex = $td.index();
                    let $tr = $td.closest("tr"); 
                    let rowIndex = $tr.index();
                    $tr.find("td").addClass(HIGHLIGHT_CLS);
                    let $horzSumHeader = $targetContainer.siblings("." + HEADER_PRF + HORIZONTAL_SUM);
                    let $horzSumContent = $targetContainer.siblings("." + BODY_PRF + HORIZONTAL_SUM);;
                    $targetContainer.siblings("div[class*='" + BODY_PRF + "']").each(function() {
                        if ($(this).hasClass(BODY_PRF + LEFT_HORZ_SUM) || $(this).hasClass(BODY_PRF + HORIZONTAL_SUM))
                            return;
                        $(this).find("tbody > tr:eq(" + rowIndex + ")").find("td").addClass(HIGHLIGHT_CLS);
                    });
                    $tr.siblings("tr").each(function() {
                        $(this).find("td:eq(" + colIndex + ")").addClass(HIGHLIGHT_CLS);
                    });
                    $targetHeader.find("tr").each(function() {
                        $(this).find("td:eq(" + colIndex + ")").addClass(HIGHLIGHT_CLS); 
                    });
                    if ($horzSumHeader.length > 0 && $horzSumHeader.css("display") !== "none") {
                        $horzSumHeader.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").addClass(HIGHLIGHT_CLS);
                        });
                        $horzSumContent.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").addClass(HIGHLIGHT_CLS);
                        });
                    }
                    events.trigger(self.$container.closest("." + NAMESPACE), events.MOUSEIN_COLUMN, colIndex);
                });
                
                $td.on(events.MOUSE_OUT, function() {
                    $td.removeClass(HIGHLIGHT_CLS);
                    let colIndex = $td.index();
                    let $tr = $td.closest("tr");
                    $tr.find("td").removeClass(HIGHLIGHT_CLS);
                    let rowIndex = $tr.index();
                    let $horzSumHeader = $targetContainer.siblings("." + HEADER_PRF + HORIZONTAL_SUM);
                    let $horzSumContent = $targetContainer.siblings("." + BODY_PRF + HORIZONTAL_SUM);
                    $targetContainer.siblings("div[class*='" + BODY_PRF + "']").each(function() {
                        if ($(this).hasClass(BODY_PRF + LEFT_HORZ_SUM) || $(this).hasClass(BODY_PRF + HORIZONTAL_SUM))
                            return;
                        $(this).find("tbody > tr:eq(" + rowIndex + ")").find("td").removeClass(HIGHLIGHT_CLS);
                    });
                    $tr.siblings("tr").each(function() {
                        $(this).find("td:eq(" + colIndex + ")").removeClass(HIGHLIGHT_CLS);
                    });
                    $targetHeader.find("tr").each(function() {
                        $(this).find("td:eq(" + colIndex + ")").removeClass(HIGHLIGHT_CLS); 
                    });
                    if ($horzSumHeader.length > 0 && $horzSumHeader.css("display") !== "none") {
                        $horzSumHeader.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").removeClass(HIGHLIGHT_CLS);
                        });
                        $horzSumContent.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").removeClass(HIGHLIGHT_CLS);
                        });
                    }
                    events.trigger(self.$container.closest("." + NAMESPACE), events.MOUSEOUT_COLUMN, colIndex);
                });
            }
            
            /**
             * Highlight.
             */
            highlight($td: JQuery) {
                let self = this;
                if (self.options.isHeader || self.options.containerClass !== BODY_PRF + DETAIL) return;
                let $targetContainer = self.$container;
                let targetHeader = helper.firstSibling($targetContainer[0], self.options.containerClass.replace(BODY_PRF, HEADER_PRF));
                $td.on(events.MOUSE_OVER, function() {
                    let colIndex = helper.indexInParent($td[0]);
                    let tr = helper.closest($td[0], "tr"); 
                    let rowIndex = helper.indexInParent(tr);
                    helper.addClass1n(tr.children, HIGHLIGHT_CLS);
                    let horzSumHeader = helper.firstSibling($targetContainer[0], HEADER_PRF + HORIZONTAL_SUM);
                    let horzSumContent = helper.firstSibling($targetContainer[0], BODY_PRF + HORIZONTAL_SUM);
                    let bodies = helper.classSiblings($targetContainer[0], BODY_PRF);
                    for (let i = 0; i < bodies.length; i++) {
                        if (!helper.hasClass(bodies[i], BODY_PRF + LEFT_HORZ_SUM)
                            && !helper.hasClass(bodies[i], BODY_PRF + HORIZONTAL_SUM)) {
                            let rowElm = bodies[i].getElementsByTagName("tr")[rowIndex];
                            if (rowElm) {
                                helper.addClass1n(rowElm.getElementsByTagName("td"), HIGHLIGHT_CLS);
                            }
                        }
                    }
                    helper.consumeSiblings(tr, (elm) => { 
                        let tds = elm.getElementsByTagName("td");
                        if (!tds || tds.length === 0) return;
                        helper.addClass1n(tds[colIndex], HIGHLIGHT_CLS); 
                    });
                    _.forEach(targetHeader.getElementsByTagName("tr"), function(t) {
                        let tds = t.getElementsByTagName("td");
                        if (!tds || tds.length === 0) return;
                        helper.addClass1n(tds[colIndex], HIGHLIGHT_CLS);
                    });
                    if (horzSumHeader && horzSumHeader.style.display !== "none") {
                        _.forEach(horzSumHeader.getElementsByTagName("tr"), function(t) {
                            let tds = t.getElementsByTagName("td");
                            if (!tds || tds.length === 0) return;
                            helper.addClass1n(tds[colIndex], HIGHLIGHT_CLS);
                        });
                        _.forEach(horzSumContent.getElementsByTagName("tr"), function(t) {
                            let tds = t.getElementsByTagName("td");
                            if (!tds || tds.length === 0) return;
                            helper.addClass1n(tds[colIndex], HIGHLIGHT_CLS);
                        });
                    }
                    self.$container.data(internal.COLUMN_IN, colIndex);
                    events.trigger(self.$container.closest("." + NAMESPACE), events.MOUSEIN_COLUMN, colIndex);
                });
                
                $td.on(events.MOUSE_OUT, function() {
                    helper.removeClass1n($td[0], HIGHLIGHT_CLS);
                    let colIndex = helper.indexInParent($td[0]);
                    let tr = helper.closest($td[0], "tr"); 
                    let rowIndex = helper.indexInParent(tr);
                    helper.removeClass1n(tr.children, HIGHLIGHT_CLS);
                    let horzSumHeader = helper.firstSibling($targetContainer[0], HEADER_PRF + HORIZONTAL_SUM);
                    let horzSumContent = helper.firstSibling($targetContainer[0], BODY_PRF + HORIZONTAL_SUM);
                    let bodies = helper.classSiblings($targetContainer[0], BODY_PRF);
                    for (let i = 0; i < bodies.length; i++) {
                        if (!helper.hasClass(bodies[i], BODY_PRF + LEFT_HORZ_SUM)
                            && !helper.hasClass(bodies[i], BODY_PRF + HORIZONTAL_SUM)) {
                            helper.removeClass1n(bodies[i].getElementsByTagName("tr")[rowIndex].getElementsByTagName("td"), HIGHLIGHT_CLS);
                        }
                    }
                    helper.consumeSiblings(tr, (elm) => { 
                        let tds = elm.getElementsByTagName("td");
                        if (!tds || tds.length === 0) return;
                        helper.removeClass1n(tds[colIndex], HIGHLIGHT_CLS); 
                    });
                    _.forEach(targetHeader.getElementsByTagName("tr"), function(t) { 
                        let tds = t.getElementsByTagName("td");
                        if (!tds || tds.length === 0) return;
                        helper.removeClass1n(tds[colIndex], HIGHLIGHT_CLS);
                    });
                    if (horzSumHeader && horzSumHeader.style.display !== "none") {
                        _.forEach(horzSumHeader.getElementsByTagName("tr"), function(t) {
                            let tds = t.getElementsByTagName("td");
                            if (!tds || tds.length === 0) return;
                            helper.removeClass1n(tds[colIndex], HIGHLIGHT_CLS);
                        });
                        _.forEach(horzSumContent.getElementsByTagName("tr"), function(t) {
                            let tds = t.getElementsByTagName("td");
                            if (!tds || tds.length === 0) return;
                            helper.removeClass1n(tds[colIndex], HIGHLIGHT_CLS);
                        });
                    }
                    self.$container.data(internal.COLUMN_IN, -1);
                    events.trigger(self.$container.closest("." + NAMESPACE), events.MOUSEOUT_COLUMN, colIndex);
                });
            }
        }
        
        export class GroupHeaderPainter extends Conditional {
            levelStruct: any;
            constructor(options: any) {
                super(options);
                this.levelStruct = options.levelStruct;
                this.columnsMap = helper.columnsMapFromStruct(this.levelStruct);
            }
            
            /**
             * Cell.
             */
            cell(text: any, rowIdx: number, cell: any): JQuery {
                let self = this;
                let $td = $("<td/>").data(internal.VIEW, rowIdx + "-" + cell.key)
                            .css({ "border-width": "1px", "overflow": "hidden", "white-space": "nowrap", "border-collapse": "collapse" });
                if (!util.isNullOrUndefined(cell.rowspan) && cell.rowspan > 1) $td.attr("rowspan", cell.rowspan);
                if (!util.isNullOrUndefined(cell.colspan) && cell.colspan > 1) $td.attr("colspan", cell.colspan);
                else if (!self.visibleColumnsMap[cell.key]) $td.hide();
                
                if (!util.isNullOrUndefined(cell.icon) && cell.icon.for === "header") {
                    let $icon = $("<span/>").addClass(COL_ICON_CLS + " " + cell.icon.class);
                    $icon.css("top", "20%").appendTo($td.css({ paddingLeft: cell.icon.width }));
                    if (cell.icon.popup && typeof cell.icon.popup === "function") {
                        $icon.css({ cursor: "pointer" });
                        new widget.PopupPanel($icon, cell.icon.popup(), "bottom right");
                    }
                    $("<div/>").html(text).appendTo($td);
                } else if (helper.containsBr(text)) { 
                    $td.html(text); 
                } else {
                    $td.text(text);
                }
                return $td;
            }
            
            /**
             * Rows.
             */
            rows($tbody: JQuery) {
                let self = this;
                let css = { height: self.options.rowHeight };
                let headerRowHeightFt = feature.find(self.options.features, feature.HEADER_ROW_HEIGHT);
                let headerCellStyleFt = feature.find(self.options.features, feature.HEADER_CELL_STYLE);
                _.forEach(Object.keys(self.levelStruct), function(rowIdx: any) {
                    if (!util.isNullOrUndefined(headerRowHeightFt)) {
                        css = { height: headerRowHeightFt.rows[rowIdx] };
                    }
                    let $tr = $("<tr/>").css(css);
                    let oneLevel = self.levelStruct[rowIdx];
                    _.forEach(oneLevel, function(cell: any) {
                        if (!self.visibleColumnsMap[cell.key] && !self.hiddenColumnsMap[cell.key]
                            && (util.isNullOrUndefined(cell.colspan) || cell.colspan == 1)) return;
                        let $cell = self.cell(cell.headerText, rowIdx, cell);
                        $tr.append($cell);
                        if (!util.isNullOrUndefined(headerCellStyleFt)) {
                            _.forEach(headerCellStyleFt.decorator, function(colorDef: any) {
                                if (colorDef.columnKey === cell.key) {
                                    if ((!util.isNullOrUndefined(colorDef.rowId) && colorDef.rowId === rowIdx)
                                        || util.isNullOrUndefined(colorDef.rowId)) {
                                        $cell.addClass(colorDef.clazz);   
                                    } 
                                    return false;
                                }
                            });
                        }
                        if (util.isNullOrUndefined(self.columnsMap[cell.key])) return;
                        let cellStyle = self.columnsMap[cell.key].style;
                        if (!util.isNullOrUndefined(cellStyle)) {
                            cellStyle(new style.CellStyleParam($cell, cell.headerText, undefined, rowIdx, cell.key)); 
                        }
                    });
                    $tbody.append($tr);
                });
            }
        }
        
        /**
         * Extra.
         */
        export function extra(className: string, height: number) {
            return $("<tr/>").addClass("extable-" + className).height(height);
        }
        
        /**
         * Wrapper styles.
         */
        export function wrapperStyles(top: string, left: string, width: string, height: string) {
            return { 
                position: "absolute",
                overflow: "hidden",
                top: top,
                left: left,
                width: width,
                height: height,
                border: "solid 1px #CCC"
            };   
        }
        
        /**
         * Create wrapper.
         */
        export function createWrapper(top: string, left: string, options: any) {
            return $("<div/>").data(internal.EX_PART, options.containerClass).addClass(options.containerClass)
                                .css(wrapperStyles(top, left, options.width, options.height));
        }
        
        /**
         * Grid cell.
         */
        export function gridCell($grid: JQuery, rowIdx: any, columnKey: any, innerIdx: any, valueObj: any, isRestore?: boolean) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let updateMode = helper.getExTableFromGrid($grid).updateMode;
            let $cell = selection.cellAt($grid, rowIdx, columnKey);
            if ($cell === intan.NULL) return;
            let origDs = helper.getOrigDS($grid);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let viewFn = gen.painter.options.view;
            let viewMode = gen.painter.options.viewMode;
            let value = valueObj;
            let fields = gen.painter.options.fields;
            if (_.isFunction(viewFn)) {
                value = helper.viewData(viewFn, viewMode, valueObj);
            }
            let touched = false;
            let $childCells = $cell.find("." + CHILD_CELL_CLS);
            if ($childCells.length > 0) {
                if (value.constructor === Array) {
                    _.forEach(value, function(val: any, i: number) {
                        let $c = $($childCells[i]);
                        $c.text(val);
                        let mTouch = trace(origDs, $c, rowIdx, columnKey, i, valueObj, fields);
                        if (!touched) touched = mTouch;
                        if (updateMode === EDIT) {
                            validation.validate($grid, $c, rowIdx, columnKey, i, val);
                        }
                    });
                } else {
                    let $c = $($childCells[innerIdx]);
                    $c.text(value);
                    touched = trace(origDs, $c, rowIdx, columnKey, innerIdx, valueObj, fields);
                    if (updateMode === EDIT) {
                        validation.validate($grid, $c, rowIdx, columnKey, innerIdx, value);
                    }
                }
            } else {
                $cell.text(value);
                touched = trace(origDs, $cell, rowIdx, columnKey, -1, valueObj, fields);
                if (updateMode === EDIT) {
                    validation.validate($grid, $cell, rowIdx, columnKey, -1, value);
                }
            }
            return touched;
        }
        
        /**
         * Grid row.
         */
        export function gridRow($grid: JQuery, rowIdx: any, data: any, isRestore?: boolean) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let updateMode = helper.getExTableFromGrid($grid).updateMode;
            let $row = selection.rowAt($grid, rowIdx);   
            let $cells = $row.find("td").filter(function() {
                return $(this).css("display") !== "none";
            });
            let visibleColumns = helper.gridVisibleColumns($grid);
            let origDs = helper.getOrigDS($grid);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let viewFn = gen.painter.options.view;
            let viewMode = gen.painter.options.viewMode;
            let fields = gen.painter.options.fields;
            _.forEach(Object.keys(data), function(key: any) {
                _.forEach(visibleColumns, function(col: any, index: number) {
                    if (col.key === key) {
                        let $target = $cells.eq(index);
                        let childCells = $target.find("." + CHILD_CELL_CLS);
                        if (childCells.length > 0) {
                            let cData = data[key];
                            if (_.isFunction(viewFn)) {
                                cData = helper.viewData(viewFn, viewMode, data[key]);
                            }
                            if (cData.constructor === Array) {
                                _.forEach(cData, function(d, i) {
                                    let $c = $(childCells[i]);
                                    $c.text(d);
                                    if (updateMode === EDIT) {
                                        validation.validate($exTable, $grid, $c, rowIdx, key, i, d);
                                    }
                                    trace(origDs, $c, rowIdx, key, i, data[key], fields);
                                });
                                return false;
                            }
                            $(childCells[1]).text(data[key]);
                            trace(origDs, $(childCells[1]), rowIdx, key, 1, data[key], fields);
                            if (updateMode === EDIT) {
                                validation.validate($exTable, $grid, $(childCells[1]), rowIdx, key, 1, data[key]);
                            }
                        } else {
                            let cData = data[key];
                            if (_.isFunction(viewFn)) {
                                cData = helper.viewData(viewFn, viewMode, data[key]);
                            }
                            $target.text(cData);
                            trace(origDs, $target, rowIdx, key, -1, data[key], fields);
                            if (updateMode === EDIT) {
                                validation.validate($exTable, $grid, $target, rowIdx, key, -1, data[key]);
                            }
                        }
                        return false;
                    } 
                });
            });
        }
        
        /**
         * Trace.
         */
        function trace(ds: Array<any>, $cell: JQuery, rowIdx: any, key: any, innerIdx: any, value: any, fields?: any) {
            if (!ds || ds.length === 0) return;
            let oVal = ds[rowIdx][key];
            
            if (!util.isNullOrUndefined(oVal) && helper.isEqual(oVal, value, fields)) {
                $cell.removeClass(update.EDITED_CLS);
                return false;
            }
            $cell.addClass(update.EDITED_CLS);
            return true;
        }
    }
    
    module intan {
        export let TOP_SPACE = "top-space";
        export let BOTTOM_SPACE = "bottom-space";
        export let NULL = $([]);
        export class Cloud {
            $container: JQuery;
            options: any;
            rowsOfBlock: number;
            blocksOfCluster: number;
            rowHeight: number;
            blockHeight: number;
            clusterHeight: number;
            _origDs: Array<any>;
            dataSource: Array<any>;
            primaryKey: string;
            topOffset: number;
            bottomOffset: number;
            currentCluster: number;
            startIndex: number;
            endIndex: number;
            painter: render.Painter;
            
            constructor($container: JQuery, dataSource: Array<any>, options: any) {
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
            
            /**
             * Set cells style.
             */
            setCellsStyle() {
                let self = this;
                let bodyStylesFt = feature.find(self.options.features, feature.BODY_CELL_STYLE);
                if (!bodyStylesFt) return;
                self.$container.data(internal.CELLS_STYLE, bodyStylesFt.decorator);
            }
            
            /**
             * Get cluster no.
             */
            getClusterNo() {
                return Math.floor(this.$container.scrollTop() / (this.clusterHeight - this.blockHeight));
            }
            
            /**
             * Render rows.
             */
            renderRows(manual?: boolean) {
                let self = this;
                let clusterNo = self.getClusterNo();
                if (manual) self.currentCluster = clusterNo;
                if (self.dataSource.length < self.rowsOfBlock) {
                    self.topOffset = 0;
                    self.bottomOffset = 0;
                }
                
                let rowsOfCluster = self.blocksOfCluster * self.rowsOfBlock;
                let startRowIdx = self.startIndex = Math.max((rowsOfCluster - self.rowsOfBlock) * clusterNo, 0);
                let endRowIdx = self.endIndex = startRowIdx + rowsOfCluster;
                self.topOffset = Math.max(startRowIdx * self.rowHeight, 0);
                self.bottomOffset = Math.max((self.dataSource.length - endRowIdx) * self.rowHeight, 0);
                let rowConfig = { css: { height: self.rowHeight } };
                let $tbody = self.$container.find("tbody");
                $tbody.empty();
                $tbody.append(render.extra(TOP_SPACE, self.topOffset));
                for (let i = startRowIdx; i < endRowIdx; i++) {
                    if (util.isNullOrUndefined(this.dataSource[i])) continue;
                    $tbody.append(self.painter.row(this.dataSource[i], rowConfig, i));
                } 
                $tbody.append(render.extra(BOTTOM_SPACE, self.bottomOffset));
                
                if (self.$container.hasClass(BODY_PRF + DETAIL)) {
                    self.selectCellsIn();
                    self.dirtyCellsIn();
                    self.errorCellsIn();
                    self.detCellsIn();
                    self.editCellIn();
                } else if (self.$container.hasClass(BODY_PRF + LEFTMOST)) {
                    self.selectedRowsIn();
                    self.dirtyCellsIn();
                    self.errorCellsIn();
                    self.editCellIn();
                }
            }
            
            /**
             * OnScroll.
             */
            onScroll() {
                let self = this;
                self.$container.off(events.SCROLL_EVT + ".detail").on(events.SCROLL_EVT + ".detail", function() {
                    let inClusterNo = self.getClusterNo();
                    if (self.currentCluster !== inClusterNo) {
                        self.currentCluster = inClusterNo;
                        if (self.$container.hasClass(BODY_PRF + DETAIL)) {
                            let colIn = self.$container.data(internal.COLUMN_IN);
                            if (!util.isNullOrUndefined(colIn) && colIn !== -1) {
                                helper.unHighlightGrid(self.$container.siblings("." + HEADER_PRF + DETAIL)[0], colIn);
                                let $sumHeader = self.$container.siblings("." + HEADER_PRF + HORIZONTAL_SUM);
                                let $sumBody = self.$container.siblings("." + BODY_PRF + HORIZONTAL_SUM);
                                if ($sumHeader.length > 0 && $sumHeader[0].style.display !== "none") {
                                    helper.unHighlightGrid($sumHeader[0], colIn);
                                    helper.unHighlightGrid($sumBody[0], colIn);
                                }
                                events.trigger(self.$container.closest("." + NAMESPACE), events.MOUSEOUT_COLUMN, colIn);
                            }
                        }
                        self.renderRows();
                    }
                });
            }
            
            /**
             * Roll to.
             */
            rollTo(cell: any) {
                let self = this;
                if (self.startIndex <= cell.rowIndex && self.endIndex >= cell.rowIndex) {
                    let $cell = selection.cellAt(self.$container, cell.rowIndex, cell.columnKey);
                    let tdIndex = $cell.index();
                    let tdPosLeft = 0, tdPosTop = 0;
                    $cell.siblings("td:lt(" + tdIndex + ")").each(function() {
                        if ($(this).css("display") !== "none") {
                            tdPosLeft += $(this).outerWidth();
                        }
                    });
                    let $tr = $cell.parent();
                    let trIndex = $tr.index();
                    $tr.siblings("tr:lt(" + trIndex + ")").each(function() {
                        tdPosTop += $(this).outerHeight();
                    });
                    if ((self.$container.scrollTop() + self.$container.height()) < (tdPosTop + 100)
                        || self.$container.scrollTop() > tdPosTop) {
                        self.$container.scrollTop(tdPosTop);
                    }
                    if ((self.$container.scrollLeft() + self.$container.width()) < (tdPosLeft + 100)
                        || self.$container.scrollLeft() > tdPosLeft) {
                        self.$container.scrollLeft(tdPosLeft);
                    }
                } else {
                    self.$container.scrollTop(cell.rowIndex * self.rowHeight);
                    let $cell = selection.cellAt(self.$container, cell.rowIndex, cell.columnKey);
                    let tdPosLeft = 0;
                    $cell.siblings("td:lt(" + $cell.index() + ")").each(function() {
                        if ($(this).css("display") !== "none") {
                            tdPosLeft += $(this).outerWidth();
                        }
                    });
                    self.$container.scrollLeft(tdPosLeft);
                }
            }
            
            /**
             * Edit cell in.
             */
            editCellIn() {
                let self = this;
                let $exTable = self.$container.closest("." + NAMESPACE);
                let updateMode = $exTable.data(NAMESPACE).updateMode;
                let editor = $exTable.data(update.EDITOR);
                if (updateMode !== EDIT || util.isNullOrUndefined(editor) || editor.land !== self.options.containerClass) return;
                let editorRowIdx = parseInt(editor.rowIdx);
                if (util.isNullOrUndefined(editor) || editorRowIdx < self.startIndex || editorRowIdx > self.endIndex) return;
                let $editRow = self.$container.find("tr:eq(" + (editorRowIdx - self.startIndex + 1) + ")");
                let editorColumnIdx;
                _.forEach(self.painter.visibleColumns, function(c: any, idx: number) {
                    if (c.key === editor.columnKey) {
                        editorColumnIdx = idx;
                        return false;
                    }
                });
                if (!util.isNullOrUndefined(editorColumnIdx)) {
                    let $editorCell = $editRow.find("td").filter(function() {
                        return $(this).css("display") !== "none";
                    }).eq(editorColumnIdx);
                    let $childCells = $editorCell.find("." + render.CHILD_CELL_CLS);
                    update.edit($exTable, $childCells.length > 0 ? $($childCells[1]) : $editorCell, editor.land, editor.value, true);
                }
            }
            
            /**
             * Select cells in.
             */
            selectCellsIn() {
                let self = this;
                let $exTable = self.$container.closest("." + NAMESPACE);
                let updateMode = $exTable.data(NAMESPACE).updateMode;
                if (updateMode !== COPY_PASTE) return;
                let selectedCells = self.$container.data(internal.SELECTED_CELLS);
                if (util.isNullOrUndefined(selectedCells) || selectedCells.length === 0) return;
                _.forEach(Object.keys(selectedCells), function(rowIdx: any, index: number) {
                    if (rowIdx >= self.startIndex && rowIdx <= self.endIndex) {
                        _.forEach(selectedCells[rowIdx], function(colKey: any) {
                            let $cell = selection.cellAt(self.$container, rowIdx, colKey);
                            if ($cell === intan.NULL || !$cell) return;
                            selection.markCell($cell);
                        });
                    }
                });
            }
            
            /**
             * Select rows in.
             */
            selectedRowsIn() {
                let self = this;
                let selectedRows = self.$container.data(internal.SELECTED_ROWS);
                if (!selectedRows || !selectedRows.items || selectedRows.items.length === 0) return;
                for (let i = self.startIndex; i <= self.endIndex; i++) {
                    if (selectedRows.items[i]) {
                        controls.tick(true, self.$container, false, i);
                    }
                }
            }
            
            /**
             * Dirty cells in.
             */
            dirtyCellsIn() {
                let self = this;
                let $exTable = self.$container.closest("." + NAMESPACE);
                let updateMode = $exTable.data(NAMESPACE).updateMode;
                let histories;
                if (self.options.containerClass === BODY_PRF + LEFTMOST) {
                    histories = self.$container.data(internal.EDIT_HISTORY);
                    if (!histories) return;
                    self.each(histories);
                    return;
                }
                if (updateMode === COPY_PASTE) {
                    histories = self.$container.data(internal.COPY_HISTORY);
                    if (!histories) return;
                    for(let i = histories.length - 1; i >= 0; i--) {
                        self.each(histories[i].items);
                    }
                } else if (updateMode === EDIT) {
                    histories = self.$container.data(internal.EDIT_HISTORY);
                    if (!histories) return;
                    self.each(histories);
                } else if (updateMode === STICK) {
                    histories = self.$container.data(internal.STICK_HISTORY);
                    if (!histories) return;
                    _.forEach(histories, function(items: any) {
                        self.each(items);
                    });
                }
            }
            
            /**
             * Error cells in.
             */
            errorCellsIn() {
                let self = this;
                let $exTable = self.$container.closest("." + NAMESPACE);
                let updateMode = $exTable.data(NAMESPACE).updateMode;
                let errs = $exTable.data(errors.ERRORS);
                if (!errs || errs.length === 0) return;
                self.each(errs, errors.ERROR_CLS);
            }
            
            /**
             * Det cells in.
             */
            detCellsIn() {
                let self = this;
                let det = self.$container.data(internal.DET);
                if (!det) return;
                _.forEach(Object.keys(det), function(rIdx: any) {
                    if (rIdx >= self.startIndex && rIdx <= self.endIndex) {
                        _.forEach(det[rIdx], function(key: any) {
                            let $cell = selection.cellAt(self.$container, rIdx, key);
                            if ($cell === intan.NULL || !$cell) return;
                            helper.markCellWith(style.DET_CLS, $cell);
                        });
                    }
                });
            }
            
            /**
             * Each.
             */
            each(items: any, styler?: any) {
                let self = this;
                styler = styler || update.EDITED_CLS;
                _.forEach(items, function(item: any) {
                    if (item.rowIndex >= self.startIndex && item.rowIndex <= self.endIndex) {
                        let $cell = selection.cellAt(self.$container, item.rowIndex, item.columnKey);
                        if ($cell === intan.NULL || !$cell) return;
                        helper.markCellWith(styler, $cell, item.innerIdx, item.value);
                    }
                });
            }
        }
    }
    
    module cellHandler {
        export let ROUND_GO: string = "ex-round-go";
        
        /**
         * Get.
         */
        export function get(handlerType: string) {
            switch (handlerType.toLowerCase()) {
                case "input": 
                    return cellInput;
                case "tooltip":
                    return tooltip;
                case "roundtrip":
                    return roundGo;
            }
        }
        
        /**
         * Cell input.
         */
        export function cellInput($cell: JQuery, options: any, supplier?: any) {
            if (util.isNullOrUndefined(options.updateMode) || options.updateMode !== EDIT) return;
            $cell.addClass(update.EDITABLE_CLS);
            $cell.on(events.CLICK_EVT, function(evt: any) {
                if ($cell.find("input").length > 0) {
                    evt.stopImmediatePropagation();
                    return;
                }
                let $exTable = $cell.closest("." + NAMESPACE);
                if (evt.ctrlKey && $exTable.data(NAMESPACE).determination) return;
                update.edit($exTable, $cell, options.containerClass);
            });
        }
        
        /**
         * Tooltip.
         */
        export function tooltip($cell: any, options: any, supplier?: any) {
            let $content = supplier();
            if (util.isNullOrUndefined($content)) return;
            new widget.Tooltip($cell, { sources: $content });
        }
        
        /**
         * Round go.
         */
        export function roundGo($cell: any, options: any, supplier?: any) {
            if (!supplier || typeof supplier !== "function") return;
            $cell.addClass(ROUND_GO).on(events.CLICK_EVT, function(evt: any) {
                let $grid = $cell.closest("table").parent();
                if (errors.occurred($grid.closest("." + NAMESPACE))) return false;
                let coord = helper.getCellCoord($cell);
                $cell.closest("." + NAMESPACE).one(events.ROUND_RETREAT, function(evt: any, value: any) {
                    let ds = helper.getDataSource($grid);
                    if (!ds || ds.length === 0) return;
                    if (ds[coord.rowIdx][coord.columnKey] !== value) {
                        update.gridCell($grid, coord.rowIdx, coord.columnKey, -1, value);
                        update.pushEditHistory($grid, new selection.Cell(coord.rowIdx, coord.columnKey, value, -1));
                    }
                });
                supplier();
            });
        }
        
        /**
         * rClick.
         */
        export function rClick($cell: JQuery, column: any, cb: any) {
            if (util.isNullOrUndefined(column.rightClick) || typeof column.rightClick !== "function") return;
            $cell.on(events.MOUSE_DOWN, function(evt: any) {
                if (evt.which === 3 || evt.button === 2) {
                    evt.preventDefault();
                    cb();
                }
            });
            $cell.on(events.CM, function() {
                return false;
            });
        }
    }
    
    module update {
        export let EDITOR = "editor";
        export let EDITED_CLS = "edited";
        export let EDIT_CELL_CLS = "edit-cell";
        export let EDITOR_CLS = "ex-editor";
        export let EDITABLE_CLS = "ex-editable";
        let EMPTY_TBL_HEIGHT: string = "1px";
        
        export class Editor {
            $editor: JQuery;
            land: any;
            rowIdx: any;
            columnKey: any;
            innerIdx: any;
            value: any;
            constructor($editor: JQuery, land: any, rowIdx: any, columnKey: any, innerIdx: any, value: any) {
                this.$editor = $editor;
                this.land = land;
                this.rowIdx = rowIdx;
                this.columnKey = columnKey;
                this.innerIdx = innerIdx;
                this.value = value;
            }
        }
        
        /**
         * Edit.
         */
        export function edit($exTable: JQuery, $cell: JQuery, land: any, value?: any, forced?: boolean) {
            let $grid = $exTable.find("." + BODY_PRF + DETAIL);
            let $body = !land ? $grid : helper.getTable($exTable, land);
            if (!forced && errors.occurred($exTable)) return;
            if (!forced && ($cell.is("." + style.DET_CLS)
                || $cell.is("." + style.HIDDEN_CLS) || $cell.is("." + style.SEAL_CLS))) {
                outsideClick($exTable, $cell, true);
                return;
            }
            let editor = $exTable.data(EDITOR);
            let $editor, $input, inputVal, innerIdx = -1;
            let coord = helper.getCellCoord($cell);
            if (util.isNullOrUndefined(editor)) {
                let content = $cell.text();
                inputVal = value ? value : content;
                $input = $("<input/>").css({ border: "none", width: "96%", height: "92%", outline: "none" })
                            .val(inputVal);
                
                $editor = $("<div/>").addClass(EDITOR_CLS)
                        .css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4, backgroundColor: "#FFF",
                                border: "solid 1px #E67E22" }).append($input);
                if ($cell.is("div")) {
                    $editor.css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4 });
                    innerIdx = $cell.index();
                }
                $exTable.data(EDITOR, new Editor($editor, land, coord.rowIdx, coord.columnKey, innerIdx, inputVal));
                events.trigger($exTable, events.START_EDIT, [ $editor, content ]);
                $cell.addClass(EDIT_CELL_CLS).empty();
                $cell.append($editor);
                editing($exTable, $editor, land);
                $input.select();
                validation.validate($body, $cell, coord.rowIdx, coord.columnKey, innerIdx, inputVal);
                selection.tickRows($exTable.find("." + BODY_PRF + LEFTMOST), false);
            } else {
                $editor = editor.$editor;
                $input = $editor.find("input");
                let content = $input.val();
                let editingLand = editor.land;
                let cont = function(cb?: any) {
                    if ($editor.css("display") === "none") $editor.show();
                    if ($cell.is("div")) {
                        $editor.css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4 });
                        innerIdx = $cell.index();
                    } else {
                        $editor.css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4 });
                    }
                    let $editingCell = $editor.closest("." + EDIT_CELL_CLS).removeClass(EDIT_CELL_CLS);
                    let cellText = $cell.text();
                    inputVal = value ? value : cellText;
                    $input.val(inputVal);
                    triggerStopEdit($exTable, $editingCell, editingLand, content);
                    if (cb && _.isFunction(cb)) {
                        cb();
                    }
                    // Update editing cell coord
                    editor.land = land;
                    editor.rowIdx = coord.rowIdx;
                    editor.columnKey = coord.columnKey;
                    editor.innerIdx = innerIdx;
                    editor.value = inputVal;
                    $cell.addClass(EDIT_CELL_CLS).empty();
                    $cell.append($editor);
                    editing($exTable, $editor, land);
                    $input.select();
                    validation.validate($body, $cell, coord.rowIdx, coord.columnKey, innerIdx, inputVal);
                    selection.tickRows($exTable.find("." + BODY_PRF + LEFTMOST), false);
                };
                
                let $editingGrid = !editingLand ? helper.getMainTable($exTable) : helper.getTable($exTable, editor.land);
                let visibleColumns = helper.getVisibleColumnsOn($editingGrid); 
                let columnDf;
                _.forEach(visibleColumns, function(col: any) {
                    if (col.key === editor.columnKey) {
                        columnDf = col;
                        return false;
                    }
                });
                if (!columnDf) return;
                if (columnDf.ajaxValidate && _.isFunction(columnDf.ajaxValidate.request)) {
                    helper.block($exTable);
                    columnDf.ajaxValidate.request(content).done(function(res) {
                        cont(helper.call(columnDf.ajaxValidate.onValid, 
                            { rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res));
                    }).fail(function(res) {
                        let $target = selection.cellAt($editingGrid, editor.rowIdx, editor.columnKey);
                        if ($target !== intan.NULL) {
                            errors.add($exTable, $target, editor.rowIdx, editor.columnKey, editor.innerIdx, editor.value);
                        }
                        if (_.isFunction(columnDf.ajaxValidate.onFailed)) {
                            columnDf.ajaxValidate.onFailed({ rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res);
                        }
                    }).always(function() {
                        helper.unblock($exTable);
                    });
                    return;
                }
                cont();
            }
        }
        
        /**
         * Editing.
         */
        function editing($exTable: JQuery, $editor: JQuery, land: any) {
            let $input = $editor.find("input");
            $input.off(events.KEY_UP);
            $input.on(events.KEY_UP, function(evt: any) {
                let value = $input.val();
                if (evt.keyCode === $.ui.keyCode.ENTER) {
                    let $grid;
                    if (!land) { 
                        $grid = helper.getMainTable($exTable);
                    } else {
                        $grid = $exTable.find("." + land);
                    }
                    let editor = $exTable.data(EDITOR);
                    if (errors.occurred($exTable) || !editor) return;
                    let visibleColumns = helper.getVisibleColumnsOn(!editor.land ? helper.getMainTable($exTable) : helper.getTable($exTable, editor.land)); 
                    let columnDf;
                    _.forEach(visibleColumns, function(col: any) {
                        if (col.key === editor.columnKey) {
                            columnDf = col;
                            return false;
                        }
                    });
                    if (!columnDf) return;
                    if (columnDf.ajaxValidate && _.isFunction(columnDf.ajaxValidate.request)) {
                        helper.block($exTable);
                        columnDf.ajaxValidate.request(value).done(function(res) {
                            let $parent = $editor.parent();
                            $parent.removeClass(EDIT_CELL_CLS);
                            let currentCell = new selection.Cell(editor.rowIdx, editor.columnKey, undefined, editor.innerIdx);
                            $exTable.data(EDITOR, null);
                            triggerStopEdit($exTable, $parent, land, value);
                            if (_.isFunction(columnDf.ajaxValidate.onValid)) {
                                columnDf.ajaxValidate.onValid({ rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res);
                            }
                            if (land !== BODY_PRF + DETAIL) return;
                            let cell = helper.nextCellOf($grid, currentCell);
                            internal.getGem($grid).rollTo(cell);
                            _.defer(function() {
                                let $cell = selection.cellAt($grid, cell.rowIndex, cell.columnKey);
                                if (util.isNullOrUndefined(cell.innerIdx) || cell.innerIdx === -1) {
                                    edit($exTable, $cell, land);
                                    return;
                                }
                                edit($exTable, $($cell.find("." + render.CHILD_CELL_CLS)[cell.innerIdx]), land);
                            });
                        }).fail(function(res) {
                            let $target = selection.cellAt($grid, editor.rowIdx, editor.columnKey);
                            if ($target !== intan.NULL) {
                                errors.add($exTable, $target, editor.rowIdx, editor.columnKey, editor.innerIdx, editor.value);
                            }
                            if (_.isFunction(columnDf.ajaxValidate.onFailed)) {
                                columnDf.ajaxValidate.onFailed({ rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res);
                            }
                        }).always(function() {
                            helper.unblock($exTable);
                        });
                    } else {
                        let $parent = $editor.parent();
                        $parent.removeClass(EDIT_CELL_CLS);
                        let currentCell = new selection.Cell(editor.rowIdx, editor.columnKey, undefined, editor.innerIdx);
                        $exTable.data(EDITOR, null);
                        triggerStopEdit($exTable, $parent, land, value);
                        if (land !== BODY_PRF + DETAIL) return;
                        let cell = helper.nextCellOf($grid, currentCell);
                        internal.getGem($grid).rollTo(cell);
                        _.defer(function() {
                            let $cell = selection.cellAt($grid, cell.rowIndex, cell.columnKey);
                            if (util.isNullOrUndefined(cell.innerIdx) || cell.innerIdx === -1) {
                                edit($exTable, $cell, land);
                                return;
                            }
                            edit($exTable, $($cell.find("." + render.CHILD_CELL_CLS)[cell.innerIdx]), land);
                        });
                    }
                } else {
                    let editor = $exTable.data(EDITOR);
                    if (util.isNullOrUndefined(editor)) return;
                    editor.value = value;
                    let $grid = !editor.land ? helper.getMainTable($exTable) : helper.getTable($exTable, editor.land);
                    validation.validate($grid, editor.$editor.closest("." + EDIT_CELL_CLS), 
                                editor.rowIdx, editor.columnKey, editor.innerIdx, editor.value);
                }
            });
        }
        
        /**
         * Trigger stop edit.
         */
        export function triggerStopEdit($exTable: JQuery, $cell: JQuery, land: any, value: any) {
            if ($cell.length === 0) return;
            let innerIdx = -1;
            if ($cell.is("div")) {
                innerIdx = $cell.index();
            }
            let coord = helper.getCellCoord($cell);
            if (!coord) return;
            events.trigger($exTable, events.STOP_EDIT, 
                            { land: land, rowIndex: coord.rowIdx, columnKey: coord.columnKey, innerIdx: innerIdx, value: value });
        }
        
        /**
         * Edit done.
         */
        export function editDone($exTable: JQuery) {
            let $grid = $exTable.find("." + BODY_PRF + DETAIL);
            let fts = $exTable.data(NAMESPACE).detailContent.features;
            let timeRangeFt = feature.find(fts, feature.TIME_RANGE);
            let timeRangerDef;
            if (!util.isNullOrUndefined(timeRangeFt)) {
                timeRangerDef = _.groupBy(timeRangeFt.ranges, "rowId");
                $grid.data(internal.TIME_VALID_RANGE, timeRangerDef);
            }
            $exTable.on(events.STOP_EDIT, function(evt: any, ui: any) {
                postEdit($exTable, ui, timeRangerDef);
            });
        }
        
        /**
         * Post edit.
         */
        function postEdit($exTable: JQuery, ui: any, timeRangerDef?: any) {
            let $body = !ui.land ? $exTable.find("." + BODY_PRF + DETAIL) : $exTable.find("." + ui.land);
            let $cell = selection.cellAt($body, ui.rowIndex, ui.columnKey);
            let result = validation.validate($body, $cell, ui.rowIndex, ui.columnKey, ui.innerIdx, ui.value, timeRangerDef);
            if (!result.isValid) return;
            ui.value = result.value;
            
            let res = cellData($exTable, ui);
            if (!util.isNullOrUndefined(res)) {
                let newValObj = ui.value;
                if (_.isObject(res)) {
                    let $main = helper.getMainTable($exTable);
                    let gen = $main.data(internal.TANGI) || $main.data(internal.CANON);
                    let upperInput = gen.painter.options.upperInput;
                    let lowerInput = gen.painter.options.lowerInput;
                    newValObj = _.cloneDeep(res);
                    if (ui.innerIdx === 0) {
                        newValObj[upperInput] = ui.value;
                    } else if (ui.innerIdx === 1) {
                        newValObj[lowerInput] = ui.value;
                    }
                }
                pushEditHistory($body, new selection.Cell(ui.rowIndex, ui.columnKey, res, ui.innerIdx));
                helper.markCellWith(EDITED_CLS, $cell, ui.innerIdx);
                events.trigger($exTable, events.CELL_UPDATED, [ new selection.Cell(ui.rowIndex, ui.columnKey, newValObj, ui.innerIdx) ]);
            }
            setText($cell, ui.innerIdx, ui.value);
        }
        
        /**
         * Set text.
         */
        export function setText($cell: JQuery, innerIdx: any, value: any) {
            let $childCells = $cell.find("." + render.CHILD_CELL_CLS);
            if (!util.isNullOrUndefined(innerIdx) && innerIdx > -1 && $childCells.length > 0) {
                $($childCells[innerIdx]).text(value)
            } else {
                $cell.text(value);
            }
        }
        
        /**
         * Outside click.
         */
        export function outsideClick($exTable: JQuery, $target: JQuery, immediate?: boolean) {
            if (immediate || !$target.is("." + update.EDITABLE_CLS)) {
                if ($exTable.data("blockUI.isBlocked") === 1 || errors.occurred($exTable)) return;
                let editor = $exTable.data(update.EDITOR);
                if (util.isNullOrUndefined(editor)) return;
                
                let $input = editor.$editor.find("input");
                let content = $input.val();
                let mo = function(cb?: any) {
                    let innerIdx = -1;
                    let $parent = editor.$editor.closest("." + update.EDITABLE_CLS).removeClass(update.EDIT_CELL_CLS);
                    let $g = $parent.closest("table").parent();
                    if ($parent.length === 0 || $g.length === 0) return; 
                    if ($parent.is("div")) innerIdx = $parent.index();
                    $parent.text(content);
                    postEdit($exTable, { rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: innerIdx, 
                                        value: content, land: ($g.data(internal.TANGI) || $g.data(internal.CANON)).painter.options.containerClass });
                    if (cb && _.isFunction(cb)) {
                        cb();
                    }
                    $exTable.data(update.EDITOR, null);
                };
                let $grid = !editor.land ? helper.getMainTable($exTable) : helper.getTable($exTable, editor.land);
                let visibleColumns = helper.getVisibleColumnsOn($grid); 
                let columnDf;
                _.forEach(visibleColumns, function(col: any) {
                    if (col.key === editor.columnKey) {
                        columnDf = col;
                        return false;
                    }
                });
                if (!columnDf) return;
                if (!$target.is("." + cellHandler.ROUND_GO) 
                    && columnDf.ajaxValidate && _.isFunction(columnDf.ajaxValidate.request)) {
                    helper.block($exTable);
                    columnDf.ajaxValidate.request(content).done(function(res) {
                        mo(helper.call(columnDf.ajaxValidate.onValid, 
                            { rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res))
                    }).fail(function(res) {
                        let $target = selection.cellAt($grid, editor.rowIdx, editor.columnKey);
                        if ($target !== intan.NULL) {
                            errors.add($exTable, $target, editor.rowIdx, editor.columnKey, editor.innerIdx, editor.value);
                        }
                        if (_.isFunction(columnDf.ajaxValidate.onFailed)) {
                            columnDf.ajaxValidate.onFailed({ rowIndex: editor.rowIdx, columnKey: editor.columnKey, innerIdx: editor.innerIdx }, res);
                        }
                    }).always(function() {
                        helper.unblock($exTable);
                    });
                    return;
                }
                mo();
            }
        }
        
        /**
         * Cell data.
         */
        export function cellData($exTable: JQuery, ui: any) {
            let exTable = $exTable.data(NAMESPACE);
            if (!exTable) return;
            let oldVal, innerIdx = ui.innerIdx, f;
            if (ui.land === BODY_PRF + LEFTMOST) {
                f = "leftmostContent";
            } else if (ui.land === BODY_PRF + MIDDLE) {
                f = "middleContent";
            } else {
                f = "detailContent";
            }
            if (util.isNullOrUndefined(ui.innerIdx)) {
                innerIdx = exTable[f].dataSource[ui.rowIndex][ui.columnKey].constructor === Array ? 1 : -1;     
            }
            let currentVal = exTable[f].dataSource[ui.rowIndex][ui.columnKey];
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
                    } else {
                        exTable[f].dataSource[ui.rowIndex][ui.columnKey] = ui.value;
                    }
                    return oldVal;
                } 
                return null;
            }
            let $main = !ui.land ? helper.getMainTable($exTable) : helper.getTable($exTable, ui.land);
            let gen = $main.data(internal.TANGI) || $main.data(internal.CANON);
            let upperInput = gen.painter.options.upperInput;
            let lowerInput = gen.painter.options.lowerInput;
            let field;
            if (ui.innerIdx === 0) {
                field = upperInput;
            } else if (ui.innerIdx === 1) {
                field = lowerInput;
            }
            if (currentVal[field] !== ui.value) {
                oldVal = _.cloneDeep(currentVal);
                exTable[f].dataSource[ui.rowIndex][ui.columnKey][field] = ui.value;
                return oldVal;
            }
            return null;
        }
        
        /**
         * Row data.
         */
        export function rowData($exTable: JQuery, rowIdx: any, data: any) {
            let exTable = $exTable.data(NAMESPACE);
            if (!exTable) return;
            _.assignInWith(exTable.detailContent.dataSource[rowIdx], data, function(objVal, srcVal) {
                if (objVal.constructor === Array && srcVal.constructor !== Array) {
                    objVal[1] = srcVal;
                    return objVal;
                }
                return srcVal;
            });
        }
        
        /**
         * Grid cell.
         */
        export function gridCell($grid: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any, isRestore?: boolean) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            let cData = gen.dataSource[rowIdx][columnKey];
            let origDs = gen._origDs;
            let $table = $grid.closest("." + NAMESPACE);
            if (cData.constructor === Array) {
                if (value.constructor === Array) {
                    _.forEach(cData, function(d: any, i: number) {
                        gen.dataSource[rowIdx][columnKey][i] = value[i];
                        if (!helper.isEqual(origDs[rowIdx][columnKey][i], value[i])) {
                            events.trigger($table, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, value[i], i) ]);
                        }
                    });
                } else {
                    gen.dataSource[rowIdx][columnKey][innerIdx] = value;
                    if (!helper.isEqual(origDs[rowIdx][columnKey][innerIdx], value)) {
                        events.trigger($table, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, value, innerIdx) ]);
                    }
                }
            } else {
                gen.dataSource[rowIdx][columnKey] = value;
                if (!helper.isEqual(origDs[rowIdx][columnKey], value)) {
                    events.trigger($table, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, value, -1) ]);
                }
            }
            render.gridCell($grid, rowIdx, columnKey, innerIdx, value, isRestore);
        }
        
        /**
         * Grid row.
         */
        export function gridRow($grid: JQuery, rowIdx: any, data: any, isRestore?: boolean) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            _.assignInWith(gen.dataSource[rowIdx], data, function(objVal, srcVal) {
                if (objVal.constructor === Array && srcVal.constructor !== Array) {
                    objVal[1] = srcVal;
                    return objVal;
                }
                return srcVal;
            });
            render.gridRow($grid, rowIdx, data, isRestore);
        }
        
        /**
         * Grid cell ow.
         */
        export function gridCellOw($grid: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any, txId: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let pk = helper.getPrimaryKey($grid);
            if (!gen || helper.isDetCell($grid, rowIdx, columnKey)
                || helper.isXCell($grid, gen.dataSource[rowIdx][pk], columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) return;
            let cData = gen.dataSource[rowIdx][columnKey];
            let opt = gen.options;
            if (!exTable.pasteOverWrite 
                && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, cData))) return;
            let changedData;
            if (cData.constructor === Array) {
                if (value.constructor === Array) {
                    changedData = _.cloneDeep(cData);
                    _.forEach(cData, function(d: any, i: number) {
                        gen.dataSource[rowIdx][columnKey][i] = value[i];
                    });
                } else {
                    changedData = cData[innerIdx];
                    gen.dataSource[rowIdx][columnKey][innerIdx] = value;
                }
            } else if (_.isObject(cData) && !_.isObject(value)) {
                return;
            } else {
                changedData = cData;
                gen.dataSource[rowIdx][columnKey] = value;
            }
            let touched = render.gridCell($grid, rowIdx, columnKey, innerIdx, value);
            if (touched) {
                pushHistory($grid, [ new selection.Cell(rowIdx, columnKey, changedData) ], txId);
                events.trigger($exTable, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, value, innerIdx) ]);
            }
        }
        
        /**
         * Grid row ow.
         */
        export function gridRowOw($grid: JQuery, rowIdx: any, data: any, txId: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let pk = helper.getPrimaryKey($grid);
            if (!gen) return;
            // Create history
            let changedCells = [];
            let origData = _.cloneDeep(data);
            let clonedData = _.cloneDeep(data);
            let opt = gen.options;
            _.assignInWith(gen.dataSource[rowIdx], clonedData, function(objVal, srcVal, key, obj, src) {
                if ((!exTable.pasteOverWrite 
                    && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, objVal)))
                    || helper.isDetCell($grid, rowIdx, key)
                    || helper.isXCell($grid, gen.dataSource[rowIdx][pk], key, style.HIDDEN_CLS, style.SEAL_CLS)) {
                    src[key] = objVal;
                    return objVal;
                }
                if (!util.isNullOrUndefined(src[key]) && !helper.isEqual(src[key], obj[key])) {
                    changedCells.push(new selection.Cell(rowIdx, key, objVal));
                } else {
                    delete origData[key];
                }
                if (objVal.constructor === Array && srcVal.constructor !== Array) {
                    objVal[1] = srcVal;
                    return objVal;
                }
                return srcVal;
            });
            _.forEach(Object.keys(clonedData), function(k: any) {
                if (!helper.isEqual(clonedData[k], origData[k])) {
                    delete origData[k];
                }
            });
            render.gridRow($grid, rowIdx, origData);
            if (changedCells.length > 0) {
                pushHistory($grid, changedCells, txId);
                events.trigger($exTable, events.ROW_UPDATED, [ events.createRowUi(rowIdx, origData) ]);
            }
        }
        
        /**
         * Stick grid cell ow.
         */
        export function stickGridCellOw($grid: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let pk = helper.getPrimaryKey($grid);
            if (!gen || helper.isDetCell($grid, rowIdx, columnKey)
                || helper.isXCell($grid, gen.dataSource[rowIdx][pk], columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) return;
            let cData = gen.dataSource[rowIdx][columnKey];
            let opt = gen.options;
            if (!exTable.stickOverWrite 
                && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, cData))) return;
            let changedData = _.cloneDeep(cData);
            gen.dataSource[rowIdx][columnKey] = value;
            let touched = render.gridCell($grid, rowIdx, columnKey, innerIdx, value);
            if (touched) {
                pushStickHistory($grid, [ new selection.Cell(rowIdx, columnKey, changedData, innerIdx) ]);
                events.trigger($exTable, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, value, innerIdx) ]);
            }
        }
        
        /**
         * Stick grid row ow.
         */
        export function stickGridRowOw($grid: JQuery, rowIdx: any, data: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let pk = helper.getPrimaryKey($grid);
            if (!gen) return;
            // Create history
            let changedCells = [];
            let origData = _.cloneDeep(data);
            let clonedData = _.cloneDeep(data);
            let opt = gen.options;
            _.assignInWith(gen.dataSource[rowIdx], clonedData, function(objVal, srcVal, key, obj, src) {
                if ((!exTable.stickOverWrite 
                    && !helper.isEmpty(helper.viewData(opt.view, opt.viewMode, objVal)))
                    || helper.isDetCell($grid, rowIdx, key)
                    || helper.isXCell($grid, gen.dataSource[rowIdx][pk], key, style.HIDDEN_CLS, style.SEAL_CLS)) {
                    src[key] = objVal;
                    return objVal;
                }
                if (!util.isNullOrUndefined(src[key]) && !helper.isEqual(src[key], obj[key])) {
                    changedCells.push(new selection.Cell(rowIdx, key, _.cloneDeep(objVal)));
                } else {
                    delete origData[key];
                }
                if (objVal.constructor === Array && srcVal.constructor !== Array) {
                    objVal[1] = srcVal;
                    return objVal;
                }
                return srcVal;
            });
            _.forEach(Object.keys(clonedData), function(k: any) {
                if (!helper.isEqual(clonedData[k], origData[k])) {
                    delete origData[k];
                }
            });
            render.gridRow($grid, rowIdx, origData);
            if (changedCells.length > 0) {
                pushStickHistory($grid, changedCells);
                events.trigger($exTable, events.ROW_UPDATED, [ events.createRowUi(rowIdx, origData) ]);
            }
        }
        
        /**
         * Push history.
         */
        export function pushHistory($grid: JQuery, cells: Array<any>, txId: any) {
            let history = $grid.data(internal.COPY_HISTORY);
            if (!history || history.length === 0) {
                history = [{ txId: txId, items: cells }];
                $grid.data(internal.COPY_HISTORY, history);
                return;
            }
            let latestHistory = history[history.length - 1];
            if (latestHistory.txId === txId) {
                _.forEach(cells, function(cell) {
                    latestHistory.items.push(cell);
                });
            } else {
                let newHis = { txId: txId, items: cells };
                history.push(newHis);
            }
        }
        
        /**
         * Push edit history.
         */
        export function pushEditHistory($grid: JQuery, cell: any) {
            let history = $grid.data(internal.EDIT_HISTORY);
            if (!history || history.length === 0) {
                $grid.data(internal.EDIT_HISTORY, [ cell ]);
                return;
            }
            history.push(cell);   
        }
        
        /**
         * Push stick history.
         */
        export function pushStickHistory($grid: JQuery, cells: Array<any>) {
            let history = $grid.data(internal.STICK_HISTORY);
            if (!history || history.length === 0) {
                $grid.data(internal.STICK_HISTORY, [ cells ]);
                return;
            }
            history.push(cells);
        }
        
        /**
         * Remove stick history.
         */
        export function removeStickHistory($grid: JQuery, cells: Array<any>) {
            let history = $grid.data(internal.STICK_HISTORY);
            if (!history || history.length === 0) return;
            _.remove(history, h => cells.some(c => helper.areSameCells(c, h)));
        }
        
        /**
         * Insert new row.
         */
        export function insertNewRow($container: JQuery) {
            let rowIndex;
            $container.find("div[class*='" + BODY_PRF + "']").filter(function() {
                return !$(this).hasClass(BODY_PRF + HORIZONTAL_SUM) && !$(this).hasClass(BODY_PRF + LEFT_HORZ_SUM);
            }).each(function(index: number) {
                let ds = internal.getDataSource($(this));
                let origDs = helper.getOrigDS($(this));
                let newRec = {};
                rowIndex = ds.length;
                let columns = helper.gridColumnsMap($(this));
                if (!ds || !columns) return;
                _.forEach(columns, function(value: any, key: any) {
                    if (key === controls.CHECKED_KEY) return;
                    newRec[key] = "";
                });
                let gen = $(this).data(internal.TANGI) || $(this).data(internal.CANON);
                let newRow = gen.painter.row(newRec, { css: { height: gen.painter.options.rowHeight } }, ds.length);
                if (!util.isNullOrUndefined(gen.startIndex)) {
                    $(this).find("tbody tr:last").before(newRow);
                } else {
                    $(this).find("tbody").append(newRow);
                }
                origDs[ds.length] = _.cloneDeep(newRec);
                ds[ds.length] = newRec;
            });
            let $grid = helper.getMainTable($container);
            $grid.scrollTop($grid[0].scrollHeight);
            controls.tick(true, $container.find("." + BODY_PRF + LEFTMOST), false, rowIndex);
        }
        
        /**
         * Delete rows.
         */
        export function deleteRows($container: JQuery) {
            let $grid = helper.getLeftmostTable($container);
            let rows = $grid.data(internal.SELECTED_ROWS);
            if (!rows || !rows.items || rows.items.length === 0) return;
            if (rows.count > 0) {
                rows.selectAll = false;
                let $allBox = $grid.siblings("." + HEADER_PRF + LEFTMOST).find("table tr:first td:first input");
                if ($allBox.is(":checked")) $allBox.prop("checked", false);
            }
            retrackEditor($container, rows.items);
            let $t = $container.find("div[class*='" + BODY_PRF + "']").filter(function() {
                return !$(this).hasClass(BODY_PRF + HORIZONTAL_SUM) && !$(this).hasClass(BODY_PRF + LEFT_HORZ_SUM);
            });
            $t.each(function() {
                let elm = this;
                _.forEach([ internal.EDIT_HISTORY, internal.COPY_HISTORY, internal.STICK_HISTORY, 
                            internal.SELECTED_CELLS, errors.ERRORS ], function(name) {
                    retrackRecords($(elm), rows.items, name);
                });
            });
            $t.each(function(index: number) {
                let ds = internal.getDataSource($(this));
                let origDs = helper.getOrigDS($(this));
                if (!ds || ds.length === 0) return;
                if (ds.length > rows.items.length) rows.items[ds.length - 1] = false;
                let count = rows.count;
                for (let i = rows.items.length - 1; i >= 0; i--) {
                    let $row = selection.rowAt($(this), i);
                    if (rows.items[i]) {
                        if ($row !== intan.NULL && !$row.hasClass(NAMESPACE + "-" + intan.BOTTOM_SPACE)) {
                            $row.remove();
                        }
                        ds.splice(i, 1);
                        origDs.splice(i, 1);
                        if (index === $t.length - 1) {
                            rows.items[i] = false;
                            rows.count--;
                        }
                        count--;
                    } else {
                        if ($row !== intan.NULL) {
                            $row.find("td").each(function() {
                                let view = $(this).data(internal.VIEW);
                                if (view) {
                                    let coord = view.split("-");
                                    $(this).data(internal.VIEW, parseInt(coord[0]) - count + "-" + coord[1]);
                                }
                            });
                        }
                    }
                }
                
                if (ds.length === 0) {
                    $(this).find("." + NAMESPACE + "-" + intan.BOTTOM_SPACE).height(EMPTY_TBL_HEIGHT);
                }
            });
        }
        
        /**
         * Retrack editor.
         */
        function retrackEditor($container: JQuery, rows: any) {
            let count = 0;
            let editor: any = $container.data(update.EDITOR);
            if (!editor) return;    
            if (rows[editor.rowIdx]) {
                $container.data(update.EDITOR, null);
                return;
            }
            for (let i = 0; i < editor.rowIdx; i++) {
                if (rows[i]) count++;
            }
            if (count > 0) editor.rowIdx = editor.rowIdx - count;
        }
        
        /**
         * Retrack records.
         */
        function retrackRecords($grid: JQuery, rows: any, name: any) {
            let histories: any = $grid.data(name);
            if (!histories) return;
            if (name === internal.COPY_HISTORY) {
                for (let i = histories.length - 1; i >= 0; i--) {
                    each(histories[i].items, rows);
                }
            } else if (name === internal.SELECTED_CELLS) {
                _.forEach(Object.keys(histories).sort((a: any, b: any) => a - b), function(key: any, index: any) {
                    let count = 0;
                    if (rows[key]) {
                        delete histories[key];
                        return;
                    }
                    for (let i = 0; i < key; i++) {
                        if (rows[i]) count++;
                    }
                    if (count > 0) {
                        let newKey = key - count;
                        histories[newKey] = histories[key];
                        delete histories[key];
                    }
                });
            } else {
                each(histories, rows);
            }
        }
        
        /**
         * Each.
         */
        function each(histories: any, rows: any) {
            let removes = [];
            _.forEach(histories, function(his: any, index: any) {
                let count = 0;
                if (rows[his.rowIndex]) {
                    removes.push(index);
                    return;
                }
                for (let i = 0; i < his.rowIndex; i++) {
                    if (rows[i]) count++;
                }
                if (count > 0) his.rowIndex = his.rowIndex - count;
            });
            
            while (removes.length > 0) {
                histories.splice(removes.pop(), 1);
            }
        }
    }
    
    module copy {
        export let PASTE_ID = "pasteHelper";
        export let COPY_ID = "copyHelper";
        enum Mode {
            SINGLE,
            MULTIPLE
        }
        
        export class History {
            cells: Array<selection.Cell>;
            constructor(cells: Array<any>) {
                this.cells = cells;
            }
        }
        
        export class Printer {
            $grid: JQuery;
            options: any;
            mode: Mode;
            constructor(options?: any) {
                this.options = options;
            }
            
            /**
             * Hook.
             */
            hook($grid: JQuery) {
                var self = this;
                self.$grid = $grid;
                self.$grid.attr("tabindex", 0).css("outline", "none");
                self.$grid.on(events.FOCUS_IN, function(evt: any) {
                    if ($("#pasteHelper").length > 0 && $("#copyHelper").length > 0) return;
                    var pasteArea = $("<textarea/>").attr("id", PASTE_ID).css({ "opacity": 0, "overflow": "hidden" })
                                        .on(events.PASTE, $.proxy(self.paste, self));
                    var copyArea = $("<textarea/>").attr("id", COPY_ID).css({ "opacity": 0, "overflow": "hidden" });
                    $("<div/>").css({ "position": "fixed", "top": -10000, "left": -10000 })
                                .appendTo($(document.body)).append(pasteArea).append(copyArea);
                });
                
                self.$grid.on(events.KEY_DOWN, function(evt: any) {
                    if (evt.ctrlKey && helper.isPasteKey(evt)) {
                        $("#pasteHelper").focus();
                    } else self.getOp(evt);
                    _.defer(function() {
                        selection.focus(self.$grid);
                    });
                });
            }
            
            /**
             * Get op.
             */
            getOp(evt: any) {
                let self = this;
                if (evt.ctrlKey && helper.isCopyKey(evt)) {
                    self.copy();
                } else if (evt.ctrlKey && helper.isCutKey(evt)) {
//                    self.cut();   
                } else if (evt.ctrlKey && helper.isUndoKey(evt)) {
                    self.undo();
                }
            }
            
            /**
             * Copy.
             */
            copy(cut?: boolean) {
                let self = this;
                let selectedCells: Array<any> = selection.getSelectedCells(this.$grid);
                let copiedData;
                if (selectedCells.length === 1) {
                    let cell = selectedCells[0];
                    let ds = internal.getDataSource(self.$grid);
                    let pk = helper.getPrimaryKey(self.$grid);
                    if (helper.isDetCell(self.$grid, cell.rowIndex, cell.columnKey)
                        || helper.isXCell(self.$grid, ds[cell.rowIndex][pk], cell.columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) return;
                    this.mode = Mode.SINGLE;
                    copiedData = _.isObject(selectedCells[0].value) ? JSON.stringify(selectedCells[0].value) : selectedCells[0].value;
                } else {
                    this.mode = Mode.MULTIPLE;
                    copiedData = this.converseStructure(selectedCells, cut);
                }
                $("#copyHelper").val(copiedData).select();
                document.execCommand("copy");
                return selectedCells;
            }
            
            /**
             * Convert structure.
             */
            converseStructure(cells: Array<any>, cut: boolean): string {
                let self = this;
                let maxRow = 0;
                let minRow = 0;
                let maxColumn = 0;
                let minColumn = 0;
                let structure = [];
                let structData: string = "";
                _.forEach(cells, function(cell: any, index: number) {
                    let rowIndex = cell.rowIndex;
                    let columnIndex = helper.getDisplayColumnIndex(self.$grid, cell.columnKey);
                    if (index === 0) {
                        minRow = maxRow = rowIndex;
                        minColumn = maxColumn = columnIndex;
                    }
                    if (rowIndex < minRow) minRow = rowIndex;
                    if (rowIndex > maxRow) maxRow = rowIndex;
                    if (columnIndex < minColumn) minColumn = columnIndex;
                    if (columnIndex > maxColumn) maxColumn = columnIndex;
                    if (util.isNullOrUndefined(structure[rowIndex])) {
                        structure[rowIndex] = {};
                    }
                    let ds = internal.getDataSource(self.$grid);
                    let pk = helper.getPrimaryKey(self.$grid);
                    if (helper.isDetCell(self.$grid, rowIndex, cell.columnKey)
                        || helper.isXCell(self.$grid, ds[rowIndex][pk], cell.columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) {
                        structure[rowIndex][columnIndex] = undefined;
                    } else structure[rowIndex][columnIndex] = helper.stringValue(cell.value);
                });
                
                for (var i = minRow; i <= maxRow; i++) {
                    for (var j = minColumn; j <= maxColumn; j++) {
                        if (util.isNullOrUndefined(structure[i]) || util.isNullOrUndefined(structure[i][j])
                            || util.isNullOrEmpty(structure[i][j])) {
                            structData += "null";
                        } else {
                            structData += structure[i][j];
                        }
                        
                        if (j === maxColumn) structData += "\n";
                        else structData += "\t";
                    }
                }
                return structData;
            }
            
            /**
             * Cut.
             */
            cut() {
                var self = this;
                var selectedCells = this.copy(true);
                _.forEach(selectedCells, function(cell: any) {
                    let $cell = selection.cellAt(self.$grid, cell.rowIndex, cell.columnKey);
                    let value: any = "";
                    if ($cell.find("." + render.CHILD_CELL_CLS).length > 0) {
                        value = ["", ""];
                    }
                    update.gridCell(self.$grid, cell.rowIndex, cell.columnKey, -1, value);
                });
            } 
                
            /**
             * Paste.
             */
            paste(evt: any) {
                if (this.mode === Mode.SINGLE) {
                    this.pasteSingleCell(evt);
                } else {
                    this.pasteRange(evt);
                }
            }
            
            /**
             * Paste single cell.
             */
            pasteSingleCell(evt: any) {
                let self = this;
                let cbData = this.getClipboardContent(evt);
                cbData = helper.getCellData(cbData);
                let selectedCells = selection.getSelectedCells(this.$grid);
                let txId = util.randomId();
                _.forEach(selectedCells, function(cell: any, index: number) {
                    update.gridCellOw(self.$grid, cell.rowIndex, cell.columnKey, -1, cbData, txId);
                });
            }
                
            /**
             * Paste range.
             */
            pasteRange(evt: any) {
                var cbData = this.getClipboardContent(evt);
                cbData = this.process(cbData);
                this.updateWith(cbData);
            }
                
            /**
             * Process.
             */
            process(data: string) {
                var dataRows = _.map(data.split("\n"), function(row) {
                    return _.map(row.split("\t"), function(cData) {
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
            }
                
            /**
             * Update with.
             */
            updateWith(data: any) {
                let self = this;
                let selectedCell: any = selection.getSelectedCells(self.$grid)[0];
                if (selectedCell === undefined) return;
                let visibleColumns = helper.gridVisibleColumns(self.$grid);
                let rowIndex = selectedCell.rowIndex;
                let startColumnIndex = helper.indexOf(selectedCell.columnKey, visibleColumns);
                if (startColumnIndex === -1) return;
                let txId = util.randomId();
                let ds = internal.getDataSource(self.$grid);
                let size = ds ? ds.length : 0;
                _.forEach(data, function(row: any, idx: number) {
                    let rowData = {};
                    let columnKey = selectedCell.columnKey;
                    let columnIndex = startColumnIndex;
                    for (var i = 0; i < row.length; i++) {
                        if (!util.isNullOrUndefined(row[i]) && row[i].constructor !== Array && row[i].trim() === "null") {
                            columnKey = helper.nextKeyOf(columnIndex++, visibleColumns);
                            if (!columnKey) break; 
                            continue;
                        }
                        rowData[columnKey] = helper.getCellData(row[i]);
                        columnKey = helper.nextKeyOf(columnIndex++, visibleColumns);
                        if (!columnKey) break;
                    }
                    if (rowIndex >= size) return false; 
                    update.gridRowOw(self.$grid, rowIndex, rowData, txId);
                    rowIndex++;
                });
            }
            
            /**
             * Undo.
             */
            undo() {
                let self = this;
                let histories = self.$grid.data(internal.COPY_HISTORY);
                if (!histories || histories.length === 0) return;
                let tx = histories.pop();
                _.forEach(tx.items, function(item: any) {
                    update.gridCell(self.$grid, item.rowIndex, item.columnKey, -1, item.value, true);
                    internal.removeChange(self.$grid, item);
                });
            }
            
            /**
             * Get clipboard content.
             */
            getClipboardContent(evt: any) {
                if (window.clipboardData) {
                    window.event.returnValue = false;
                    return window.clipboardData.getData("text");
                } else {
                    return evt.originalEvent.clipboardData.getData("text/plain");
                }
            }
        }
            
        /**
         * On.
         */
        export function on($grid: JQuery, updateMode: any) {
            if (updateMode === COPY_PASTE) {
                new Printer().hook($grid);
            }
        }
        
        /**
         * Off.
         */
        export function off($grid: JQuery, updateMode: any) {
            if (updateMode !== COPY_PASTE) {
                $grid.off(events.FOCUS_IN).off(events.KEY_DOWN);
                $("#" + COPY_ID).remove();
                $("#" + PASTE_ID).remove();
            }
        }
    }
    
    module spread {
        export let SINGLE = "single";
        export let MULTIPLE = "multiple";
        export class Sticker {
            mode: any = MULTIPLE;
            data: any;
            validate: any = () => true;
            constructor(data?: any) {
                this.data = data;
            }
        }
        
        /**
         * Bind sticker.
         */
        export function bindSticker($cell: JQuery, rowIdx: any, columnKey: any, options: any) {
            if (options.containerClass !== BODY_PRF + DETAIL || util.isNullOrUndefined(options.updateMode) 
                || options.updateMode !== STICK) return;
            $cell.on(events.CLICK_EVT, function(evt: any) {
                if (evt.ctrlKey) return;
                let $grid = $cell.closest("." + BODY_PRF + DETAIL);
                let sticker = $grid.data(internal.STICKER);
                if (!sticker || util.isNullOrUndefined(sticker.data) 
                    || util.isNullOrUndefined(sticker.validate)) return;
                let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
                let visibleColumns = gen.painter.visibleColumns;
                let data = {};
                let key = columnKey;
                let colIndex = helper.indexOf(key, visibleColumns);
                if (sticker.mode === SINGLE) {
                    let result;
                    if ((result = sticker.validate(rowIdx, columnKey, sticker.data)) !== true) {
                        // TODO: show error
                        result();
                        return;
                    }
                    update.stickGridCellOw($grid, rowIdx, columnKey, -1, sticker.data);
                    return;
                }
                _.forEach(sticker.data, function(cData: any) {
                    data[key] = cData;
                    key = helper.nextKeyOf(colIndex++, visibleColumns);
                    if (!key) return false;
                });
                update.stickGridRowOw($grid, rowIdx, data);
            });
        }
    }
    
    module validation {
        export let TIME_SPLIT = ":";
        export let TIME_PTN = /^\d+:\d{2}$/;
        export let SHORT_TIME_PTN = /^\d+$/;
        export let NUMBER_PTN = /^\d+$/;
        export let MINUTE_MAX = 59;
        export let HOUR_MAX = 24;
        export let DEF_HOUR_MAX = 9999;
        export let DEF_HOUR_MIN = 0;
        export let DEF_MIN_MAXMIN = 0;
        
        class Result {
            isValid: boolean;
            value: any
            constructor(isValid: boolean, value?: any) {
                this.isValid = isValid;
                this.value = value;
            }
            
            static fail() {
                return new Result(false);  
            }
            static ok(value: any) {
                return new Result(true, value);
            }
        }
        
        /**
         * Validate.
         */
        export function validate($body: JQuery, $cell: JQuery, rowIdx: any, columnKey: any, 
                                innerIdx: any, value: any, timeRangerDef?: any) {
            let vtor = validation.mandate($body, columnKey, innerIdx);
            let gen = $body.data(internal.TANGI) || $body.data(internal.CANON);
            let rowId = gen.dataSource[rowIdx][gen.primaryKey];
            timeRangerDef = timeRangerDef || $body.data(internal.TIME_VALID_RANGE);
            let formatValue;
            if (vtor) {
                if ((vtor.actValid === internal.TIME || vtor.actValid === internal.DURATION) && timeRangerDef) {
                    let ranges = timeRangerDef[rowId];
                    _.forEach(ranges, function(range) {
                        if (range && range.columnKey === columnKey && range.innerIdx === innerIdx) { 
                            vtor.max = range.max;
                            vtor.min = range.min;
                            return Result.fail();
                        }
                    });
                }
                
                let isValid;
                if (vtor.required && (_.isUndefined(value) || _.isEmpty(value))) {
                    isValid = false;
                } else if (!_.isUndefined(value) && !_.isEmpty(value)) {
                    if (vtor.actValid === internal.TIME) {
                        isValid = isTimeClock(value);
                        formatValue = formatTime(value); 
                    } else if (vtor.actValid === internal.DURATION) {
                        isValid = isTimeDuration(value, vtor.max, vtor.min);
                        formatValue = formatTime(value);
                    } else if (vtor.actValid === internal.NUMBER) {
                        isValid = isNumber(value, vtor.max, vtor.min);
                        formatValue = (_.isUndefined(value) || _.isEmpty(value)) ? "" : Number(value);
                    } else {
                        isValid = true;
                        formatValue = value;
                    }
                } else {
                    isValid = true;
                    formatValue = "";
                }
                let $tbl = $body.closest("." + NAMESPACE);
                if (!isValid) {
                    errors.add($tbl, $cell, rowIdx, columnKey, innerIdx, value);
    //                cellData($exTable, rowIdx, columnKey, innerIdx, value);
//                    update.setText($cell, innerIdx, value);
                    return Result.fail();
                }
                if (errors.any($cell, innerIdx)) errors.remove($tbl, $cell, rowIdx, columnKey, innerIdx);
            }
            return Result.ok(formatValue);
        }
        
        /**
         * Mandate.
         */
        export function mandate($grid: JQuery, columnKey: any, innerIdx: any) {
            let visibleColumns = helper.getVisibleColumnsOn($grid);
            let actValid, dataType, max, min, required;
            _.forEach(visibleColumns, function(col: any) {
                if (col.key === columnKey) {
                    if (!col.dataType) return false;
                    dataType = col.dataType.toLowerCase();
                    actValid = which(innerIdx, dataType, internal.TIME) 
                                || which(innerIdx, dataType, internal.DURATION)
                                || which(innerIdx, dataType, internal.NUMBER)
                                || which(innerIdx, dataType, internal.TEXT);
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
        
        /**
         * Which.
         */
        function which(innerIdx: any, dataType: any, type: string) {
            let actValid;
            if (dataType && dataType.indexOf(type) !== -1) {
                if (!util.isNullOrUndefined(innerIdx) && innerIdx > -1) {
                    _.forEach(dataType.split(internal.DT_SEPARATOR), function(p: any, index: any) {
                        if (p === type && index === innerIdx) {
                            actValid = type;
                            return false;
                        }
                    });
                } else {
                    actValid = type;
                }
            }
            return actValid;
        }
        
        /**
         * Check time clock.
         */
        export function isTimeClock(time: string) {
            if (util.isNullOrUndefined(time)) return false;
            time = time.trim();
            let hour, minute;
            if (TIME_PTN.test(time)) {
                let parts = time.split(TIME_SPLIT);
                hour = parseInt(parts[0]);
                minute = parseInt(parts[1]);
            } else if (SHORT_TIME_PTN.test(time)) {
                let totalTime = parseInt(time); 
                minute = totalTime % 100;
                hour = Math.floor(totalTime / 100);
            }
            if (((hour !== NaN && hour >= 0 && hour <= HOUR_MAX) || hour === NaN) 
                && minute !== NaN && minute >= 0 && minute <= MINUTE_MAX) return true;
            return false;   
        }
        
        /**
         * Check time duration.
         */
        export function isTimeDuration(time: string, max?: string, min?: string) {
            if (util.isNullOrUndefined(time)) return false;
            time = time.trim();
            let hour, minute, minMM, maxHour = DEF_HOUR_MAX, minHour = DEF_HOUR_MIN;
            let maxMM = minMM = DEF_MIN_MAXMIN;
            let maxTime = parse(max) || { hour: DEF_HOUR_MAX, minute: DEF_MIN_MAXMIN };
            let minTime = parse(min) || { hour: DEF_HOUR_MIN, minute: DEF_MIN_MAXMIN };
            if (TIME_PTN.test(time)) {
                let parts = time.split(TIME_SPLIT);
                hour = parseInt(parts[0]);
                minute = parseInt(parts[1]);
            } else if (SHORT_TIME_PTN.test(time)) {
                let totalTime = parseInt(time);
                minute = totalTime % 100;
                hour = Math.floor(totalTime / 100);
            }
            if (((util.isNullOrUndefined(hour) || hour === NaN) && (util.isNullOrUndefined(minute) || minute === NaN))
                || minute > MINUTE_MAX) return false;
            let targetTime = { hour: hour, minute: minute };
            if (compare(targetTime, maxTime) > 0 || compare(targetTime, minTime) < 0) return false;
            return true; 
        }
        
        /**
         * Check number.
         */
        function isNumber(value: any, max: any, min: any) {
            if (!NUMBER_PTN.test(value)) return false;
            let int = parseInt(value);
            if ((!util.isNullOrUndefined(max) && int > parseInt(max))
                || (!util.isNullOrUndefined(min) && int < parseInt(min))) return false;
            return true;
        }
        
        /**
         * Compare.
         */
        function compare(one: any, other: any) {
            if (one.hour > other.hour) {
                return 1;
            } else if (one.hour < other.hour) {
                return -1;
            } else if (one.minute > other.minute) {
                return 1;
            } else if (one.minute < other.minute) {
                return -1;
            }
            return 0;
        }
        
        /**
         * Parse.
         */
        function parse(time: string) {
            if (TIME_PTN.test(time)) {
                let parts = time.split(TIME_SPLIT);
                let hour = parseInt(parts[0]);
                let minute = parseInt(parts[1]);
                return { 
                    hour: hour,
                    minute: minute 
                };
            }
        }
        
        /**
         * Format time.
         */
        export function formatTime(time: string) {
            let minute, hour;
            if (SHORT_TIME_PTN.test(time)) {
                let totalTime = parseInt(time);
                minute = totalTime % 100;
                hour = Math.floor(totalTime / 100);
            }
            if (!util.isNullOrUndefined(hour) && hour !== NaN 
                && !util.isNullOrUndefined(minute) && minute !== NaN) {
                if (minute < 10) minute = "0" + minute;
                return hour + TIME_SPLIT + minute;
            }
            if (!util.isNullOrUndefined(hour) && hour === NaN 
                && !util.isNullOrUndefined(minute) && minute !== NaN) {
                return minute;
            }
            return time;
        }
    }
    
    module errors {
        export let ERROR_CLS = "x-error"; 
        export let ERRORS = "errors";
        
        /**
         * Add.
         */
        export function add($grid: JQuery, $cell: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any) {
            if (any($cell, innerIdx)) return;
            let errors = $grid.data(ERRORS);
            let newErr = new selection.Cell(rowIdx, columnKey, value, innerIdx);
            if (!errors) {
                errors = [ newErr ];
                $grid.data(ERRORS, errors);
            } else {
                errors.push(newErr);
            }
            if ($cell.is("td") && !util.isNullOrUndefined(innerIdx) && innerIdx > -1) {
                $cell.find("div:eq(" + innerIdx + ")").addClass(ERROR_CLS);
            } else {
                $cell.addClass(ERROR_CLS);
            }
        }
        
        /**
         * Remove.
         */
        export function remove($grid: JQuery, $cell: JQuery, rowIdx: any, columnKey: any, innerIdx: any) {
            let errors = $grid.data(ERRORS);
            if (!errors) return;
            let idx;
            _.forEach(errors, function(err: any, index: any) {
                if (helper.areSameCells(err, { rowIndex: rowIdx, columnKey: columnKey, innerIdx: innerIdx })) {
                    idx = index;
                    return false;
                }
            });
            if (!util.isNullOrUndefined(idx)) {
                errors.splice(idx, 1);
                if ($cell.is("td") && !util.isNullOrUndefined(innerIdx) && innerIdx > -1) {
                    $cell.find("div:eq(" + innerIdx + ")").removeClass(ERROR_CLS);
                } else {
                    $cell.removeClass(ERROR_CLS);
                }
            }
        }
        
        /**
         * Clear.
         */
        export function clear($grid: JQuery) {
            $grid.data(ERRORS, null);
        } 
        
        /**
         * Any.
         */
        export function any($cell: JQuery, innerIdx: any) {
            let $childCells = $cell.find("." + render.CHILD_CELL_CLS);
            if (!util.isNullOrUndefined(innerIdx) && $childCells.length > 0) {
                return $($childCells[innerIdx]).hasClass(ERROR_CLS);
            }
            return $cell.hasClass(ERROR_CLS);
        }
        
        /**
         * Occurred.
         */
        export function occurred($grid: JQuery) {
            let errs = $grid.data(ERRORS);
            if (!errs) return false;
            return errs.length > 0;
        }
    }
    
    module selection {
        export let CELL_SELECTED_CLS = "x-cell-selected";
        export let ROW_SELECTED_CLS = "x-row-selected";
        export class Cell {
            rowIndex: any;
            columnKey: any;
            innerIdx: any;
            value: any;
            constructor(rowIdx: any, columnKey: any, value: any, innerIdx?: any) {
                this.rowIndex = rowIdx;
                this.columnKey = columnKey;
                this.value = value;
                this.innerIdx = innerIdx;
            }
        }
        
        /**
         * Check up.
         */
        export function checkUp($exTable: JQuery) {
            if ($exTable.data(NAMESPACE).updateMode !== COPY_PASTE) return;
            let $detailContent = $exTable.find("." + BODY_PRF + DETAIL);
            let isSelecting;
            $detailContent[0].onselectstart = function() {
                return false;
            };
            $detailContent.on(events.MOUSE_DOWN, function(evt: any) {
                let $target = $(evt.target);
                isSelecting = true;
                if (!$target.is("." + render.CHILD_CELL_CLS) && !$target.is("td")) return;
                if (evt.shiftKey) {
                    selectRange($detailContent, $target);
                    return;
                }
                if (!evt.ctrlKey) {
                    clearAll($detailContent);
                }
                selectCell($detailContent, $target);
            }).on(events.MOUSE_UP, function(evt: any) {
                isSelecting = false;
            }).on(events.MOUSE_MOVE, function(evt: any) {
                if (isSelecting) {
                    selectRange($detailContent, $(evt.target));
                }
            });
        }
        
        /**
         * Select range.
         */
        function selectRange($grid: JQuery, $cell: JQuery) {
            if (util.isNullOrUndefined($cell) || $cell.length === 0) return;
            let lastSelected = $grid.data(internal.LAST_SELECTED);
            if (!lastSelected) {
                selectCell($grid, $cell);
                return;
            }
            clearAll($grid);
            let toCoord = helper.getCellCoord($cell); 
            let minRowIdx = Math.min(lastSelected.rowIdx, toCoord.rowIdx);
            let maxRowIdx = Math.max(lastSelected.rowIdx, toCoord.rowIdx);
            for (let i = minRowIdx; i < maxRowIdx + 1; i++) { 
                cellInRange($grid, i, lastSelected.columnKey, toCoord.columnKey);
            }
        }
        
        /**
         * Mark cell.
         */
        export function markCell($cell: JQuery) {
            if ($cell.is("." + render.CHILD_CELL_CLS)) {
                $cell.addClass(CELL_SELECTED_CLS);
                $cell.siblings("." + render.CHILD_CELL_CLS).addClass(CELL_SELECTED_CLS);
                return true;
            } else if ($cell.is("td")) {
                let childCells = $cell.find("." + render.CHILD_CELL_CLS);
                if (childCells.length > 0) {
                    childCells.addClass(CELL_SELECTED_CLS);
                } else {
                    $cell.addClass(CELL_SELECTED_CLS);
                }
                return true;
            }
            return false;
        }
        
        /**
         * Select cell.
         */
        export function selectCell($grid: JQuery, $cell: JQuery, notLast?: boolean) {
            if (!markCell($cell)) return;
            let coord = helper.getCellCoord($cell);
            addSelect($grid, coord.rowIdx, coord.columnKey, notLast);
            tickRows($grid.siblings("." + BODY_PRF + LEFTMOST), false);
        }
        
        /**
         * Add select.
         */
        export function addSelect($grid: JQuery, rowIdx: any, columnKey: any, notLast?: boolean) {
            let selectedCells = $grid.data(internal.SELECTED_CELLS);
            if (!notLast) $grid.data(internal.LAST_SELECTED, { rowIdx: rowIdx, columnKey: columnKey });
            if (!selectedCells) {
                selectedCells = {};
                selectedCells[rowIdx] = [ columnKey ];
                $grid.data(internal.SELECTED_CELLS, selectedCells);
                return;
            }
            if (!selectedCells[rowIdx]) {
                selectedCells[rowIdx] = [ columnKey ];
                return;
            }
            if (_.find(selectedCells[rowIdx], function(key) {
                return key === columnKey;
            }) === undefined) {
                selectedCells[rowIdx].push(columnKey);
            }
        }
        
        /**
         * Clear.
         */
        export function clear($grid: JQuery, rowIdx: any, columnKey: any) {
            let selectedCells = $grid.data(internal.SELECTED_CELLS);
            if (!selectedCells) return;
            let row = selectedCells[rowIdx];
            if (!row || row.length === 0) return;
            let colIdx;
            _.forEach(row, function(key: any, index: number) {
                if (key === columnKey) {
                    colIdx = index;
                    return false;
                }
            });
            if (util.isNullOrUndefined(colIdx)) return;
            row.splice(colIdx, 1);
            let selectedCell = cellAt($grid, rowIdx, columnKey);
            if (selectedCell === intan.NULL) return;
            if (selectedCell && selectedCell.length > 0) {
                let $childCells = selectedCell.find("." + render.CHILD_CELL_CLS);
                if ($childCells.length > 0) {
                    $childCells.removeClass(CELL_SELECTED_CLS);
                } else {
                    selectedCell.removeClass(CELL_SELECTED_CLS);
                }
            }
        }
        
        /**
         * Clear all.
         */
        export function clearAll($grid: JQuery) {
            let selectedCells = $grid.data(internal.SELECTED_CELLS);
            if (!selectedCells) return;
            _.forEach(Object.keys(selectedCells), function(rowIdx: any, index: number) {
                if (!rowExists($grid, rowIdx)) return;
                _.forEach(selectedCells[rowIdx], function(col: any, i: number) {
                    let $cell = cellAt($grid, rowIdx, col);
                    if ($cell && $cell.length > 0) {
                        let childCells = $cell.find("." + render.CHILD_CELL_CLS);
                        if (childCells.length > 0) {
                            childCells.removeClass(CELL_SELECTED_CLS);
                        } else {
                            $cell.removeClass(CELL_SELECTED_CLS);
                        }
                    }
                });
            });
            $grid.data(internal.SELECTED_CELLS, null);
        }
        
        /**
         * Cell at.
         */
        export function cellAt($grid: JQuery, rowIdx: any, columnKey: any) {
            let $row = rowAt($grid, rowIdx);
            return getCellInRow($grid, $row, columnKey);
        }
        
        /**
         * Row at.
         */
        export function rowAt($grid: JQuery, rowIdx: any) {
            let virt = $grid.data(internal.TANGI);
            if (!virt) return $grid.find("tr:eq(" + (parseInt(rowIdx) + 1) + ")");
            if (virt.startIndex > rowIdx || virt.endIndex < rowIdx)
                return intan.NULL;
            return $grid.find("tr:eq(" + (parseInt(rowIdx) - virt.startIndex + 1) + ")");
        }
        
        /**
         * Cell of.
         */
        export function cellOf($grid: JQuery, rowId: any, columnKey: any) {
            let $row = rowOf($grid, rowId);
            return getCellInRow($grid, $row, columnKey);
        }
        
        /**
         * Row of.
         */
        export function rowOf($grid: JQuery, rowId: any) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            let start = gen.startIndex || 0;
            let end = gen.endIndex || gen.dataSource.length - 1;
            for (let i = start; i <= end; i++) {
                if (gen.dataSource[i][gen.primaryKey] === rowId) {
                    return rowAt($grid, i);
                }
            }
        }
        
        /**
         * Row exists.
         */
        export function rowExists($grid: JQuery, rowIdx: any) {
            let virt = $grid.data(internal.TANGI);
            if (virt && (virt.startIndex > rowIdx || virt.endIndex < rowIdx))
                return false;
            return true;
        }
        
        /**
         * Cell in range.
         */
        export function cellInRange($grid: JQuery, rowIdx: any, startKey: any, endKey: any) {
            let range = [];
            let $row = rowAt($grid, rowIdx);
            if ($row === intan.NULL) return;
            let colRange = columnIndexRange($grid, startKey, endKey);
            if (colRange.start === -1 || colRange.end === -1) return;
            let min = Math.min(colRange.start, colRange.end);
            let max = Math.max(colRange.start, colRange.end);
            let $td = $row.find("td").filter(function() {
                return $(this).css("display") !== "none";
            }).each(function(index) {
                if (index >= min && index <= max) {
                    let childCells = $(this).find("." + render.CHILD_CELL_CLS);
                    if (childCells.length > 0) {
                        childCells.addClass(CELL_SELECTED_CLS);
                    } else {
                        $(this).addClass(CELL_SELECTED_CLS);
                    }
                    addSelect($grid, rowIdx, colRange.columns[index].key, true);
                    range.push($(this));
                } else if (index > max) return false;
            });
            return range;
        }
        
        /**
         * Get cell in row.
         */
        export function getCellInRow($grid: JQuery, $row: JQuery, columnKey: any) {
            if ($row === intan.NULL || !$row) return intan.NULL;
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let visibleColumns = gen.painter.visibleColumns
            let columnIdx;
            _.forEach(visibleColumns, function(c: any, idx: number) {
                if (c.key === columnKey) {
                    columnIdx = idx;
                    return false;
                }
            });
            if (util.isNullOrUndefined(columnIdx)) return intan.NULL;
            return $row.find("td").filter(function() {
                return $(this).css("display") !== "none";
            }).eq(columnIdx);
        }
        
        /**
         * Column index range.
         */
        export function columnIndexRange($grid: JQuery, startKey: any, endKey: any) {
            let cloud: intan.Cloud = $grid.data(internal.TANGI);
            let canon: any = $grid.data(internal.CANON);
            let visibleColumns;
            if (!util.isNullOrUndefined(cloud)) {
                visibleColumns = cloud.painter.visibleColumns;
            } else {
                visibleColumns = canon.painter.visibleColumns;
            }
            let startColumnIdx = -1, endColumnIdx = -1;
            _.forEach(visibleColumns, function(c: any, idx: number) {
                if (c.key === startKey) {
                    startColumnIdx = idx;
                } 
                if (c.key === endKey) {
                    endColumnIdx = idx;
                }
                if (startColumnIdx !== -1 && endColumnIdx !== -1) return false;
            });
            return {
                start: startColumnIdx,
                end: endColumnIdx,
                columns: visibleColumns
            };
        }
        
        /**
         * Get selected cells.
         */
        export function getSelectedCells($grid: JQuery) {   
            let selectedCells = $grid.data(internal.SELECTED_CELLS);
            let generator = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let dataSource = generator.dataSource;
            let cells = [];
            _.forEach(Object.keys(selectedCells), function(rowIdx: any) {
                _.forEach(selectedCells[rowIdx], function(colKey: any) {
                    cells.push(new Cell(rowIdx, colKey, dataSource[rowIdx][colKey]));
                });
            });
            return cells;
        }
        
        /**
         * Off.
         */
        export function off($exTable: JQuery) {
            if ($exTable.data(NAMESPACE).updateMode === COPY_PASTE) return;
            let $detailContent = $exTable.find("." + BODY_PRF + DETAIL);
            $detailContent[0].onselectstart = function() {
                return true;
            };
            $detailContent.off(events.MOUSE_DOWN).off(events.MOUSE_UP).off(events.MOUSE_MOVE);
        }
        
        /**
         * Focus.
         */
        export function focus($grid: JQuery) {
            $grid.focus();
        }
        
        /**
         * Focus latest.
         */
        export function focusLatest($grid: JQuery) {
            let latest = $grid.data(internal.LAST_SELECTED);
            if (!latest) return;
            let $cell = selection.cellAt($grid, latest.rowIdx, latest.columnKey);
            if ($cell === intan.NULL || $cell.length === 0) return;
            $cell.focus();
        }
        
        /**
         * Select row.
         */
        export function selectRow($grid: JQuery, rowIndex: any) {
            let $row = selection.rowAt($grid, rowIndex);
            if ($row !== intan.NULL && !$row.hasClass(NAMESPACE + "-" + intan.BOTTOM_SPACE)) {
                $row.addClass(ROW_SELECTED_CLS);
            }
            setTimeout(() => {
                $row = selection.rowAt($grid.siblings("div[class*='" + BODY_PRF + "']").filter(function() {
                    return !$(this).hasClass(BODY_PRF + HORIZONTAL_SUM) && !$(this).hasClass(BODY_PRF + LEFT_HORZ_SUM);
                }), rowIndex);
                if ($row !== intan.NULL && !$row.hasClass(NAMESPACE + "-" + intan.BOTTOM_SPACE)) {
                    $row.addClass(ROW_SELECTED_CLS);
                }
            }, 60);
            let selectedRows = $grid.data(internal.SELECTED_ROWS);
            if (!selectedRows) {
                selectedRows = {};
                selectedRows.items = [];
                selectedRows.items[rowIndex] = true;
                selectedRows.count = (selectedRows.count || 0) + 1;
                $grid.data(internal.SELECTED_ROWS, selectedRows);
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
        
        /**
         * Deselect row.
         */
        export function deselectRow($grid: JQuery, rowIndex: any) {
            selection.rowAt($grid, rowIndex).removeClass(ROW_SELECTED_CLS);
            selection.rowAt($grid.siblings("div[class*='" + BODY_PRF + "']").filter(function() {
                return !$(this).hasClass(BODY_PRF + HORIZONTAL_SUM) && !$(this).hasClass(BODY_PRF + LEFT_HORZ_SUM);
            }), rowIndex).removeClass(ROW_SELECTED_CLS);
            let selectedRows = $grid.data(internal.SELECTED_ROWS);
            if (!selectedRows || !selectedRows.items || selectedRows.items.length === 0) return;
            selectedRows.items[rowIndex] = false;
            selectedRows.count--;
        }
        
        /**
         * Tick rows.
         */
        export function tickRows($grid: JQuery, flag: boolean, limit?: boolean) {
            let selectedRows = $grid.data(internal.SELECTED_ROWS);
            if (!selectedRows || !selectedRows.items || selectedRows.items.length === 0) return;
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let start = limit ? gen.startIndex : 0;
            let end = limit ? gen.endIndex : selectedRows.items.length - 1;
            for (let i = start; i <= end; i++) {
                if (selectedRows.items[i]) {
                    controls.tick(flag, $grid, false, i);
                }
            }
        }
    }
    
    module resize {
        export let AGENCY = "ex-agency";
        export let LINE = "ex-line";
        export let RESIZE_COL = "resize-column";
        export let AREA_AGENCY = "ex-area-agency";
        export let RESIZE_AREA = "resize-area";
        export let AREA_LINE = "ex-area-line";
        export let STAY_CLS = "x-stay";
        export class ColumnAdjuster {
            $headerTable: JQuery;
            $contentTable: JQuery;
            $colGroup: JQuery;
            $agency: JQuery;
            $ownerDoc: JQuery;
            // Resizable td list
            standardCells: Array<any>;
            actionDetails: any;
            
            constructor($headerTable: JQuery, $contentTable: JQuery, options?: any) {
                this.$headerTable = $headerTable;
                this.$contentTable = $contentTable;
                this.$ownerDoc = $($headerTable[0].ownerDocument);
                this.standardCells = [];
            }
            
            /**
             * Handle.
             */
            handle() {
                let self = this;
                self.$agency = $("<div/>").addClass(AGENCY).css({ position: "relative", width: self.$headerTable.outerWidth() });
                self.$headerTable.before(self.$agency);
                self.$colGroup = self.$headerTable.find("colgroup > col");
                let trList = self.$headerTable.find("tbody > tr");
                let targetColumnIdx = 0;
                _.forEach(trList, function(tr) {
                    let tdList = $(tr).find("td");
                    for (let i = 0; i < tdList.length; i++) {
                        if (self.standardCells.length >= self.$colGroup.length) return false;
                        let $td = $(tdList[i]);
                        let colspan = $td.attr("colspan");
                        if (!util.isNullOrUndefined(colspan) && parseInt(colspan) > 1) continue;
                        self.standardCells.push($td);
                        
                        let $targetCol = $(self.$colGroup[targetColumnIdx]);
                        let $line = $("<div/>").addClass(LINE).data(RESIZE_COL, $targetCol).css(lineStyles("-3px"));  
                        self.$agency.append($line);
                        
                        // Line positions
                        let height = self.$headerTable.height(); 
                        let left = $td.outerWidth() + ($td.offset().left - self.$agency.offset().left);
                        $line.css({ left: left, height: height });
                        targetColumnIdx++;
                    } 
                });
                self.$agency.on(events.MOUSE_DOWN, $.proxy(self.cursorDown, self));  
            }
            
            /**
             * Cursor down.
             */
            cursorDown(event: any) {
                let self = this;
                if (self.actionDetails) {
                    self.cursorUp(event);
                }
                let $targetGrip = $(event.target);
                let gripIndex = $targetGrip.index();
                let $leftCol = $targetGrip.data(RESIZE_COL);
                let $rightCol = self.$colGroup.eq(gripIndex + 1);
                let leftWidth = $leftCol.width();
                let rightWidth = $rightCol.width();
                self.actionDetails = {
                    $targetGrip: $targetGrip,
                    gripIndex: gripIndex,
                    $leftCol: $leftCol,
                    $rightCol: $rightCol,
                    xCoord: getCursorX(event),
                    widths: {
                        left : leftWidth,
                        right: rightWidth
                    },
                    changedWidths: {
                        left: leftWidth,
                        right: rightWidth
                    }
                };
                self.$ownerDoc.on(events.MOUSE_MOVE, $.proxy(self.cursorMove, self));
                self.$ownerDoc.on(events.MOUSE_UP, $.proxy(self.cursorUp, self));
                event.preventDefault();
            }
            
            /**
             * Cursor move.
             */
            cursorMove(event: any) {
                let self = this;
                if (!self.actionDetails) return;
                let distance = getCursorX(event) - self.actionDetails.xCoord;
                if (distance === 0) return;
                let leftWidth, rightWidth;
                if (distance > 0) {
                    leftWidth = self.actionDetails.widths.left + distance;
                    rightWidth = self.actionDetails.widths.right - distance;
                } else {
                    leftWidth = self.actionDetails.widths.left + distance;
                    rightWidth = self.actionDetails.widths.right - distance;
                } 
                if (leftWidth <= 20 || rightWidth <= 20) return;
                
                self.actionDetails.changedWidths.left = leftWidth;
                self.actionDetails.changedWidths.right = rightWidth;
                if (self.actionDetails.$leftCol) {
                    self.setWidth(self.actionDetails.$leftCol, leftWidth);
                    let $contentLeftCol = self.$contentTable.find("colgroup > col").eq(self.actionDetails.gripIndex);
                    self.setWidth($contentLeftCol, leftWidth);
                }
                if (self.actionDetails.$rightCol) {
                    self.setWidth(self.actionDetails.$rightCol, rightWidth);
                    let $contentRightCol = self.$contentTable.find("colgroup > col").eq(self.actionDetails.gripIndex + 1);
                    self.setWidth($contentRightCol, rightWidth);
                }
            }
            
            /**
             * Cursor up.
             */
            cursorUp(event: any) {
                let self = this;
                self.$ownerDoc.off(events.MOUSE_MOVE);
                self.$ownerDoc.off(events.MOUSE_UP);
                self.syncLines();
                self.actionDetails = null;
            }
            
            /**
             * Set width.
             */
            setWidth($col: JQuery, width: any) {
                $col.width(width);
            }
            
            /**
             * Sync lines.
             */
            syncLines() {
                let self = this;
                self.$agency.width(self.$headerTable.width());
                _.forEach(self.standardCells, function($td: JQuery, index: number) {
                    let height = self.$headerTable.height(); 
                    let left = $td.outerWidth() + ($td.offset().left - self.$agency.offset().left);
                    self.$agency.find("div:eq(" + index + ")").css({ left: left, height: height });
                });
            }
        }
        
        export class AreaAdjuster {
            $container: JQuery;
            $ownerDoc: JQuery;
            headerWrappers: Array<JQuery>;
            bodyWrappers: Array<JQuery>;
            $leftHorzSumHeader: JQuery;
            $leftHorzSumContent: JQuery;
            $horzSumHeader: JQuery;
            $horzSumContent: JQuery;
            $areaAgency: JQuery;
            $depLeftmostHeader: JQuery;
            $depLeftmostBody: JQuery;
            $depDetailHeader: JQuery;
            $depDetailBody: JQuery;
            actionDetails: any;
            
            constructor($container: JQuery, headerWrappers: Array<JQuery>, bodyWrappers: Array<JQuery>, $follower: JQuery) {
                this.$container = $container;
                this.headerWrappers = headerWrappers;
                this.bodyWrappers = bodyWrappers;
                this.$ownerDoc = $($container[0].ownerDocument);
                this.$leftHorzSumHeader = this.$container.find("." + HEADER_PRF + LEFT_HORZ_SUM);
                this.$leftHorzSumContent = this.$container.find("." + BODY_PRF + LEFT_HORZ_SUM);
                this.$horzSumHeader = this.$container.find("." + HEADER_PRF + HORIZONTAL_SUM);
                this.$horzSumContent = this.$container.find("." + BODY_PRF + HORIZONTAL_SUM);
                if ($follower) {
                    this.$depLeftmostHeader = $follower.find("." + HEADER_PRF + LEFTMOST);
                    this.$depLeftmostBody = $follower.find("." + BODY_PRF + LEFTMOST);
                    this.$depDetailHeader = $follower.find("." + HEADER_PRF + DETAIL);
                    this.$depDetailBody = $follower.find("." + BODY_PRF + DETAIL); 
                }
            }
            
            /**
             * Handle.
             */
            handle() {
                let self = this;
                self.$areaAgency = $("<div/>").addClass(AREA_AGENCY).css({ position: "relative", width: self.$container.width() });
                self.headerWrappers[0].before(self.$areaAgency);
                _.forEach(self.headerWrappers, function($wrapper: JQuery, index: number) {
                    if (index === self.headerWrappers.length - 1) return;
                    let $line = $("<div/>").addClass(AREA_LINE).data(RESIZE_AREA, $wrapper).css(lineStyles("0px"));  
                    self.$areaAgency.append($line);
                    // Line positions
                    let height = $wrapper.height() + self.bodyWrappers[index].height(); 
                    let left = $wrapper.outerWidth() + ($wrapper.offset().left - self.$areaAgency.offset().left);
                    $line.css({ left: left, height: height });
                    if ($wrapper.hasClass(HEADER_PRF + LEFTMOST) && self.headerWrappers[index + 1].hasClass(HEADER_PRF + MIDDLE)) 
                        $line.addClass(STAY_CLS);
                });
                self.$areaAgency.on(events.MOUSE_DOWN, $.proxy(self.cursorDown, self)); 
            }
            
            /**
             * Cursor down.
             */
            cursorDown(event: any) {
                let self = this;
                if (self.actionDetails) {
                    self.cursorUp(event);
                }
                let $targetGrip = $(event.target);
                if ($targetGrip.hasClass(STAY_CLS)) return;
                let gripIndex = $targetGrip.index();
                let $leftArea = $targetGrip.data(RESIZE_AREA);
                let $rightArea = self.headerWrappers[gripIndex + 1];
                let leftWidth = $leftArea.width();
                let rightWidth = !util.isNullOrUndefined($rightArea) ? $rightArea.width() : 0;
                let leftHorzSumWidth = self.$leftHorzSumHeader.width();
                self.actionDetails = {
                    $targetGrip: $targetGrip,
                    gripIndex: gripIndex,
                    $leftArea: $leftArea,
                    $rightArea: $rightArea,
                    xCoord: getCursorX(event),
                    rightAreaPosLeft: $rightArea ? $rightArea.css("left") : 0,
                    widths: {
                        left : leftWidth,
                        right: rightWidth,
                        leftHorzSum: leftHorzSumWidth
                    },
                    changedWidths: {
                        left: leftWidth,
                        right: rightWidth,
                        leftHorzSum: leftHorzSumWidth
                    }
                };
                self.$ownerDoc.on(events.MOUSE_MOVE, $.proxy(self.cursorMove, self));
                self.$ownerDoc.on(events.MOUSE_UP, $.proxy(self.cursorUp, self));
                events.trigger(self.$container, events.AREA_RESIZE_STARTED, [ $leftArea, $rightArea, leftWidth, rightWidth ]);
                event.preventDefault();
            }
            
            /**
             * Cursor move.
             */
            cursorMove(event: any) {
                let self = this;
                if (!self.actionDetails) return;
                let distance = getCursorX(event) - self.actionDetails.xCoord;
                if (distance === 0) return;
                let $bodyLeftArea, $bodyRightArea, leftWidth, rightWidth;
                // TODO: Use if here because there may be changes later.
                if (distance > 0) {
                    leftWidth = self.actionDetails.widths.left + distance;
                    rightWidth = self.actionDetails.widths.right - distance;
                } else {
                    leftWidth = self.actionDetails.widths.left + distance;
                    rightWidth = self.actionDetails.widths.right - distance;
                }
                if (!self.isResizePermit(leftWidth, rightWidth)) return;
                self.actionDetails.changedWidths.left = leftWidth;
                self.actionDetails.changedWidths.right = rightWidth;
                if (self.actionDetails.$leftArea) {
                    self.setWidth(self.actionDetails.$leftArea, leftWidth);
                    $bodyLeftArea = self.bodyWrappers[self.actionDetails.gripIndex];
                    if (self.actionDetails.gripIndex === self.bodyWrappers.length - 1) {
                        self.setWidth($bodyLeftArea, leftWidth + helper.getScrollWidth());
                    } else {
                        self.setWidth($bodyLeftArea, leftWidth);
                    }
                }
                let newPosLeft;
                if (self.actionDetails.$rightArea) {
                    self.setWidth(self.actionDetails.$rightArea, rightWidth);
                    newPosLeft = (parseInt(self.actionDetails.rightAreaPosLeft) + distance) + "px";
                    self.actionDetails.$rightArea.css("left", newPosLeft);
                    $bodyRightArea = self.bodyWrappers[self.actionDetails.gripIndex + 1];
                    if (self.actionDetails.gripIndex === self.bodyWrappers.length - 2) {
                        self.setWidth($bodyRightArea, rightWidth + helper.getScrollWidth());    
                    } else {
                        self.setWidth($bodyRightArea, rightWidth);
                    }
                    $bodyRightArea.css("left", newPosLeft);
                }
                self.reflectSumTblsSize(distance, leftWidth, rightWidth, newPosLeft);
                events.trigger(self.$container, events.AREA_RESIZE, [ self.actionDetails.$leftArea, self.actionDetails.$rightArea, leftWidth, rightWidth ]);
            }
            
            /**
             * Reflect size.
             */
            reflectSumTblsSize(diff: number, leftWidth: number, rightWidth: number, posLeft: number) {
                let self = this;
                let $leftArea = self.actionDetails.$leftArea;
                let $rightArea = self.actionDetails.$rightArea;
                let scrollWidth = helper.getScrollWidth();
                if ($rightArea && $rightArea.hasClass(HEADER_PRF + DETAIL)) {
                    let horzLeftWidth = self.actionDetails.widths.leftHorzSum + diff;
                    self.setWidth(self.$leftHorzSumHeader, horzLeftWidth);
                    self.setWidth(self.$leftHorzSumContent, horzLeftWidth);
                    self.setWidth(self.$horzSumHeader, rightWidth);
                    self.setWidth(self.$horzSumContent, rightWidth + scrollWidth);
                    self.$horzSumHeader.css("left", posLeft);
                    self.$horzSumContent.css("left", posLeft);
                    if (self.$depLeftmostHeader) {
                        self.setWidth(self.$depLeftmostHeader, horzLeftWidth);
                        self.setWidth(self.$depLeftmostBody, horzLeftWidth);
                        self.setWidth(self.$depDetailHeader, rightWidth);
                        self.setWidth(self.$depDetailBody, rightWidth + scrollWidth);
                        self.$depDetailHeader.css("left", posLeft);
                        self.$depDetailBody.css("left", posLeft);
                    }
                } else if ($leftArea && $leftArea.hasClass(HEADER_PRF + DETAIL)) {
                    self.setWidth(self.$horzSumHeader, leftWidth);
                    self.setWidth(self.$horzSumContent, leftWidth + scrollWidth);
                    if (self.$depDetailHeader) {
                        self.setWidth(self.$depDetailHeader, leftWidth);
                        self.setWidth(self.$depDetailBody, leftWidth + scrollWidth);
                    }
                }
            }
            
            /**
             * Check resize permit.
             */
            isResizePermit(leftWidth: number, rightWidth: number) {
                let self = this;
                let leftAreaMaxWidth = 0, rightAreaMaxWidth = 0;
                if (leftWidth <= 20 || (self.actionDetails.widths.right > 0 && rightWidth <= 20)) return false;
                if (self.actionDetails.$leftArea) {
                    let leftAreaColGroup = self.actionDetails.$leftArea[0].querySelectorAll("table > colgroup > col");
                    let size = leftAreaColGroup.length;
                    for (let i = 0; i < size; i++) {
                        leftAreaMaxWidth += (parseInt(leftAreaColGroup[i].style.width) + 1);
                    }
                    if (leftWidth >= leftAreaMaxWidth) return false;               
                }
                if (self.actionDetails.$rightArea) {
                    let rightAreaColGroup = self.actionDetails.$rightArea[0].querySelectorAll("table > colgroup > col");
                    let size = rightAreaColGroup.length;
                    for (let i = 0; i < size; i++) {
                        rightAreaMaxWidth += (parseInt(rightAreaColGroup[i].style.width) + 1);
                    }
                    if (rightWidth >= rightAreaMaxWidth) return false;
                }
                return true;
            }
            
            /**
             * Cursor up.
             */
            cursorUp(event: any) {
                let self = this;
                if (!self.actionDetails) return;
                self.$ownerDoc.off(events.MOUSE_MOVE);
                self.$ownerDoc.off(events.MOUSE_UP);
                self.syncLines();
                events.trigger(self.$container, events.AREA_RESIZE_END, 
                                [ self.actionDetails.$leftArea, self.actionDetails.$rightArea, 
                                  self.actionDetails.changedWidths.left, self.actionDetails.changedWidths.right ]);
                self.actionDetails = null;
            }
            
            /**
             * Set width.
             */
            setWidth($wrapper: JQuery, width: any) {
                $wrapper.width(width);
            }
            
            /**
             * Sync lines.
             */
            syncLines() {
                let self = this;
                self.$areaAgency.width(self.$container.width());
                
                _.forEach(self.headerWrappers, function($wrapper: JQuery, index: number) {
                    let height = $wrapper.height() + self.bodyWrappers[index].height(); 
                    let left = $wrapper.outerWidth() + ($wrapper.offset().left - self.$areaAgency.offset().left);
                    self.$areaAgency.find("div:eq(" + index + ")").css({ left: left, height: height });
                });
            }
        }
        
        /**
         * Get cursorX.
         */
        function getCursorX(event: any) {
            return event.pageX;
        }
        
        /**
         * Line styles.
         */
        function lineStyles(marginLeft: string) {
            return { position: "absolute", cursor: "ew-resize", width: "4px", zIndex: 2, marginLeft: marginLeft };
        }
        
        /**
         * Fit window height.
         */
        export function fitWindowHeight($container: JQuery, wrappers: Array<JQuery>, horzSumExists: boolean) {
            let height = window.innerHeight - parseInt($container.data(internal.Y_OCCUPY)) - 100;
            let $horzSumHeader, $horzSumBody, decreaseAmt; 
            wrappers = wrappers || _.map($container.find("div[class*='" + BODY_PRF + "']").filter(function() {
                return !$(this).hasClass(BODY_PRF + HORIZONTAL_SUM) && !$(this).hasClass(BODY_PRF + LEFT_HORZ_SUM);
            }), function(elm) { return $(elm); });
            
            if (horzSumExists) {
                $horzSumHeader = $container.find("." + HEADER_PRF + HORIZONTAL_SUM);
                $horzSumBody = $container.find("." + BODY_PRF + HORIZONTAL_SUM);
                decreaseAmt = $horzSumHeader.height() + $horzSumBody.height() + DISTANCE + SPACE;
                height -= decreaseAmt;
            }
            _.forEach(wrappers, function($wrapper: JQuery) {
                if (($wrapper.css("overflow-x") && $wrapper.css("overflow-x") !== "scroll") 
                    || ($wrapper.css("overflow") && $wrapper.css("overflow") !== "scroll")) {
                    $wrapper.height(height - helper.getScrollWidth());
                } else {
                    $wrapper.height(height);
                }
            });
            
            if (horzSumExists) {
                repositionHorzSum($container, $horzSumHeader, $horzSumBody);
            }
            
            let cHeight = 0, showCount = 0;
            let stream = $container.find("div[class*='" + DETAIL + "'], div[class*='" + LEFT_HORZ_SUM + "']"); 
            stream.each(function() {
                if ($(this).css("display") !== "none") {
                    showCount++;
                    cHeight += $(this).height();
                }
            });
            if (showCount === 4) {
                cHeight += (SPACE + DISTANCE);
            }
            $container.height(cHeight + SPACE);
            events.trigger($container, events.BODY_HEIGHT_CHANGED, height);
        }
        
        /**
         * Fit window width.
         */
        export function fitWindowWidth($container: JQuery) {
            let table = $container.data(NAMESPACE);
            if (table.$commander) return;
            let $vertSumHeader = $container.find("." + HEADER_PRF + VERTICAL_SUM);
            let $vertSumContent = $container.find("." + BODY_PRF + VERTICAL_SUM);
            let $detailHeader = $container.find("." + HEADER_PRF + DETAIL);
            let $detailBody = $container.find("." + BODY_PRF + DETAIL);
            let width = window.innerWidth - $detailHeader.offset().left;
            let scrollWidth = helper.getScrollWidth();
            let $sup = table.$follower;
            if ($vertSumHeader.length > 0 && $vertSumHeader.css("display") !== "none") {
                width = width - parseInt($container.data(internal.X_OCCUPY)) - $vertSumContent.width();    
                $detailHeader.width(width);
                $detailBody.width(width);
                $container.find("." + HEADER_PRF + HORIZONTAL_SUM).width(width);
                $container.find("." + BODY_PRF + HORIZONTAL_SUM).width(width + helper.getScrollWidth());
                repositionVertSum($container, $vertSumHeader, $vertSumContent);
                syncDetailAreaLine($container, $detailHeader, $detailBody);
                if ($sup) {
                    $sup.find("." + HEADER_PRF + DETAIL).width(width);
                    $sup.find("." + BODY_PRF + DETAIL).width(width + scrollWidth);  
                }
                return;
            }
            width = width - parseInt($container.data(internal.X_OCCUPY));
            $detailHeader.width(width - scrollWidth) ;
            $detailBody.width(width);
            $container.find("." + HEADER_PRF + HORIZONTAL_SUM).width(width - scrollWidth);
            $container.find("." + BODY_PRF + HORIZONTAL_SUM).width(width);
            if ($sup) {
                $sup.find("." + HEADER_PRF + DETAIL).width(width - scrollWidth);
                $sup.find("." + BODY_PRF + DETAIL).width(width);
            }
        }
        
        /**
         * Sync detail area line.
         */
        function syncDetailAreaLine($container: JQuery, $detailHeader: JQuery, $detailBody: JQuery) {
            let $agency = $container.find("." + resize.AREA_AGENCY);
            if ($agency.length === 0) return;
            let height = $detailHeader.height() + $detailBody.height(); 
            let left = $detailHeader.outerWidth() + ($detailHeader.offset().left - $agency.offset().left);
            let index;
            $container.find("div[class*='" + HEADER_PRF + "']").each(function(idx: number) {
                if ($(this).hasClass(HEADER_PRF + DETAIL)) {
                    index = idx;
                    return false;
                }
            });
            $agency.find("div:eq(" + index + ")").css({ left: left, height: height });
        }
        
        /**
         * Reposition horzSum.
         */
        export function repositionHorzSum($container: JQuery, $horzSumHeader?: JQuery, $horzSumBody?: JQuery) {
            $horzSumHeader = $horzSumHeader || $container.find("." + HEADER_PRF + HORIZONTAL_SUM);
            $horzSumBody = $horzSumBody || $container.find("." + BODY_PRF + HORIZONTAL_SUM);
            let headerTop = $container.find("." + HEADER_PRF + DETAIL).height() 
                            + $container.find("." + BODY_PRF + DETAIL).height() + DISTANCE + SPACE;
            let bodyTop = headerTop + DISTANCE + $horzSumHeader.height();
            $container.find("." + HEADER_PRF + LEFT_HORZ_SUM).css("top", headerTop);
            $container.find("." + BODY_PRF + LEFT_HORZ_SUM).css("top", bodyTop);
            $horzSumHeader.css("top", headerTop);
            $horzSumBody.css("top", bodyTop);
        }
        
        /**
         * Reposition vertSum.
         */
        export function repositionVertSum($container: JQuery, $vertSumHeader?: JQuery, $vertSumContent?: JQuery) {
            $vertSumHeader = $vertSumHeader || $container.find("." + HEADER_PRF + VERTICAL_SUM);
            $vertSumContent = $vertSumContent || $container.find("." + BODY_PRF + VERTICAL_SUM);
            let $detailHeader = $container.find("." + HEADER_PRF + DETAIL);
            let posLeft = $detailHeader.css("left");
            let vertSumLeft = parseInt(posLeft) + $detailHeader.width() + DISTANCE;
            $vertSumHeader.css("left", vertSumLeft);
            $vertSumContent.css("left", vertSumLeft);
        }
        
        /**
         * Set height.
         */
        export function setHeight($container: JQuery, height: any) {
            $container.find("div[class*='" + BODY_PRF + "']").each(function() {
                if ($(this).hasClass(BODY_PRF + HORIZONTAL_SUM) || $(this).hasClass(BODY_PRF + LEFT_HORZ_SUM)) return;
                $(this).height(height);
            });
            
            let cHeight = 0, showCount = 0;
            let stream = $container.find("div[class*='" + DETAIL + "'], div[class*='" + LEFT_HORZ_SUM + "']"); 
            stream.each(function() {
                if ($(this).css("display") !== "none") {
                    showCount++;
                    cHeight += $(this).height();
                }
            });
            if (showCount === 4) {
                cHeight += (SPACE + DISTANCE);
            }
            $container.height(cHeight + SPACE);
            events.trigger($container, events.BODY_HEIGHT_CHANGED, height);
        }
        
        /**
         * On area complete.
         */
        export function onAreaComplete(event: any, $leftArea: JQuery, $rightArea: JQuery, 
                                        leftWidth: number, rightWidth: number) {
            let self = this;
            saveSizes(self.$container, $leftArea, $rightArea, leftWidth, rightWidth);
        }
        
        /**
         * Save sizes.
         */
        export function saveSizes($container: JQuery, $leftArea: JQuery, $rightArea: JQuery, 
                                    leftWidth: number, rightWidth: number) {
            if ($leftArea) {
                storage.area.save($container, $leftArea.data(internal.EX_PART), leftWidth);
            }
            if ($rightArea) {
                storage.area.save($container, $rightArea.data(internal.EX_PART), rightWidth); 
            }
        }
        
        /**
         * On body height changed.
         */
        export function onBodyHeightChanged(event: any, height) {
            let $container = $(event.target);
            storage.tableHeight.save($container, height);
            repositionHorzSum($container);
        }
    }
    
    module storage {
        export let AREA_WIDTHS = "areawidths";
        export let TBL_HEIGHT = "tableheight";
        
        abstract class Store {
            /**
             * Get storage key.
             */
            abstract getStorageKey($container: JQuery);
            
            /**
             * Check value exists.
             */
            initValueExists($container: JQuery) {
                let self = this;
                let storeKey = self.getStorageKey($container);
                let value = uk.localStorage.getItem(storeKey);
                return value.isPresent();
            }
            
            /**
             * Get store item.
             */
            getStoreItem($container: JQuery, item: string) {
                return request.location.current.rawUrl + "/" + $container.attr("id") + "/" + item;
            }
            
            /**
             * Get value.
             */
            getValue($container: JQuery) {
                let storeKey = this.getStorageKey($container);
                return uk.localStorage.getItem(storeKey);
            }
        }
        
        export module area {
            class Cache extends Store {
                getStorageKey($container: JQuery) {
                    return this.getStoreItem($container, AREA_WIDTHS);
                } 
            }
            let cache = new Cache();
            
            /**
             * Init.
             */
            export function init($container: JQuery, parts: Array<JQuery>) {
                if (cache.initValueExists($container)) {
                    return;
                }
                let partWidths: {[ key: string ]: number } = {};
                _.forEach(parts, function(part: JQuery, index: number) {
                    let key = helper.getClassOfHeader(part);
                    partWidths[key] = part.width();
                });
                saveAll($container, partWidths);
            }
            
            /**
             * Load.
             */
            export function load($container: JQuery) {
                let storeKey = cache.getStorageKey($container);
                uk.localStorage.getItem(storeKey).ifPresent((parts) => {
                    let widthParts: any = JSON.parse(parts);
                    setWidths($container, widthParts);
                    return null;
                });
            }
            
            /**
             * Save.
             */
            export function save($container: JQuery, keyClass: string, partWidth: number) {
                let storeKey = cache.getStorageKey($container);
                let partsWidth = uk.localStorage.getItem(storeKey);
                let widths = {};
                if (partsWidth.isPresent()) {
                    widths = JSON.parse(partsWidth.get());
                    widths[keyClass] = partWidth;
                } else {
                    widths[keyClass] = partWidth;
                }
                uk.localStorage.setItemAsJson(storeKey, widths);
            }
            
            /**
             * Save all.
             */
            function saveAll($container: JQuery, widths: {[ key: string ]: number }) {
                let storeKey = cache.getStorageKey($container);
                let partWidths = uk.localStorage.getItem(storeKey);
                if (!partWidths.isPresent()) {
                    uk.localStorage.setItemAsJson(storeKey, widths);
                }
            }
            
            /**
             * Get part widths.
             */
            export function getPartWidths($container: JQuery) {
                return cache.getValue($container);
            }            
            
            /**
             * Set widths.
             */
            function setWidths($container: JQuery, parts: {[ key: string ]: number }) {
                let partKeys = Object.keys(parts);
                _.forEach(partKeys, function(keyClass: any, index: number) {
                    setWidth($container, keyClass, parts[keyClass]);
                });
            }
            
            /**
             * Set width.
             */
            function setWidth($container: JQuery, keyClass: string, width: number) {
                $container.find("." + keyClass).width(width);
                $container.find("." + Connector[keyClass]).width(width);
            }
              
        }
        
        export module tableHeight {
            class Cache2 extends Store {
                getStorageKey($container: JQuery) {
                    return this.getStoreItem($container, TBL_HEIGHT);
                }
            }
            let cache = new Cache2();
            
            /**
             * Init.
             */
            export function init($container: JQuery) {
                if (cache.initValueExists($container)) {
                    return;
                }
                let $bodies = $container.find("div[class*='" + BODY_PRF + "']");
                if ($bodies.length === 0) return;
                save($container, $($bodies[0]).height());
            }
            
            /**
             * Load.
             */
            export function load($container: JQuery) {
                let storeKey = cache.getStorageKey($container);
                uk.localStorage.getItem(storeKey).ifPresent((height) => {
                    let h = JSON.parse(height);
                    resize.setHeight($container, height);
                    return null;
                });
            }
            
            /**
             * Get.
             */
            export function get($container: JQuery) {
                return cache.getValue($container);
            }
            
            /**
             * Save.
             */
            export function save($container: JQuery, height: number) {
                let storeKey = cache.getStorageKey($container);
                uk.localStorage.setItemAsJson(storeKey, height);
            }
        }
    }
    
    module scroll {
        export let SCROLL_SYNCING = "scroll-syncing";
        export let VERT_SCROLL_SYNCING = "vert-scroll-syncing";
        
        /**
         * Bind vertWheel.
         */
        export function bindVertWheel($container: JQuery) {
            $container.on(events.MOUSE_WHEEL, function(event: any) {
                let delta = event.originalEvent.wheelDeltaY;
                let direction = delta > 0 ? -1 : 1;
                let value = $container.scrollTop() + event.originalEvent.deltaY;
//                $container.stop().animate({ scrollTop: value }, 10);
                $container.scrollTop(value);
                event.preventDefault();
                event.stopImmediatePropagation();
            });
            if ($container.css("overflow-y") !== "hidden") {
                $container.css("overflow-y", "hidden");
            }
        }
        
        /**
         * Unbind vertWheel.
         */
        export function unbindVertWheel($container: JQuery) {
            $container.off(events.MOUSE_WHEEL);
            $container.css("overflow-y", "scroll");
        }
        
        /**
         * Sync scrolls.
         */
        export function syncDoubDirHorizontalScrolls(wrappers: Array<JQuery>) {
            _.forEach(wrappers, function($main: JQuery, index: number) {
                if (!$main) return;
                $main.on(events.SCROLL_EVT, function() {
                    _.forEach(wrappers, function($depend: JQuery, i: number) {
                        if (i === index || !$depend) return;
                        let mainSyncing = $main.data(SCROLL_SYNCING);
                        if (!mainSyncing) {
                            $depend.data(SCROLL_SYNCING, true);
                            $depend.scrollLeft($main.scrollLeft());
                        }
                    });
                    $main.data(SCROLL_SYNCING, false);
                });
            });
        }
        
        /**
         * Sync scrolls.
         */
        export function syncDoubDirVerticalScrolls(wrappers: Array<JQuery>) {
            _.forEach(wrappers, function($main: JQuery, index: number) {
                    $main.on(events.SCROLL_EVT, function(event: any) {
                        _.forEach(wrappers, function($depend: JQuery, i: number) {
                            if (i === index) return;
                            let mainSyncing = $main.data(VERT_SCROLL_SYNCING);
                            if (!mainSyncing) {
                                $depend.data(VERT_SCROLL_SYNCING, true);
                                $depend.scrollTop($main.scrollTop());
                            }
                        });
                        $main.data(VERT_SCROLL_SYNCING, false);
                    });
            });
        }
        
        /**
         * Sync scroll.
         */
        export function syncHorizontalScroll($headerWrap: JQuery, $bodyWrap: JQuery) {
            $bodyWrap.on(events.SCROLL_EVT, function() {
                $headerWrap.scrollLeft($bodyWrap.scrollLeft());
            });
        }
        
        /**
         * Sync scroll.
         */
        export function syncVerticalScroll($pivotBody: JQuery, bodyWraps: Array<JQuery>) {
            $pivotBody.on(events.SCROLL_EVT, function() {
                _.forEach(bodyWraps, function(body: JQuery) {
                    body.scrollTop($pivotBody.scrollTop());
                });
            });
        }
    }
    
    module controls {
        export let LINK_BUTTON: string = "link";
        export let LINK_CLS: string = "x-link";
        export let CHECKED_KEY: string = "xCheckbox";
        export let CHECKBOX_COL_WIDTH = 40;
        
        /**
         * Check.
         */
        export function check($td: JQuery, column: any, data: any, action: any) {
            if (!util.isNullOrUndefined(column.control)) {
                switch(column.control) {
                    case LINK_BUTTON:
                        $("<a/>").addClass(LINK_CLS).on(events.CLICK_EVT, function(evt) {
                            action();
                        }).text(data).appendTo($td);
                        break;
                }
            }
        }
        
        /**
         * Add checkbox def.
         */
        export function addCheckBoxDef(arr: any) {
            _.forEach(arr, function(opt: any) {
                opt.columns.unshift({ key: CHECKED_KEY, headerText: CHECKED_KEY, width: CHECKBOX_COL_WIDTH + "px" });
            });
        }
        
        /**
         * Create checkbox.
         */
        export function createCheckBox($grid: JQuery, ui: any) {
            var checkBoxText: string;
            var $wrapper = $("<div/>").addClass("nts-checkbox-container").on(events.CLICK_EVT, function(e) {
                if ($grid && errors.occurred($grid.closest("." + NAMESPACE))) e.preventDefault();
            });

            var $checkBoxLabel = $("<label class='ntsCheckBox'></label>");
            var $checkBox = $('<input type="checkbox">').on("change", function() {
                let cellCoord = helper.getCellCoord($(this).closest("td"));
                let rowIndex = 0;
                if (cellCoord) rowIndex = cellCoord.rowIdx;
                ui.onChecked($(this).is(":checked"), rowIndex);
            }).appendTo($checkBoxLabel);
            var $box = $("<span class='box'></span>").appendTo($checkBoxLabel);
            if (ui.text && ui.text.length > 0) {
                var label = $("<span class='label'></span>").text(ui.text).appendTo($checkBoxLabel);
            }
            $checkBoxLabel.appendTo($wrapper);

            var checked = ui.initValue !== undefined ? ui.initValue : true;
            var $checkBox = $wrapper.find("input[type='checkbox']");

            if (checked === true) $checkBox.prop("checked", true);
            else $checkBox.prop("checked", false);
            return $wrapper;
        }
        
        /**
         * Checkbox cell styles.
         */
        export function checkBoxCellStyles() {
            return { padding: "1px 1px", textAlign: "center" };
        }
        
        /**
         * Tick.
         */
        export function tick(checked: boolean, $grid: JQuery, isHeader: boolean, rowIdx?: any) {
            let $checkBox;
            let ds = internal.getDataSource($grid);
            if (isHeader) {
                $grid.find("tr").find("td:first").each(function(index: any) {
                    $checkBox = $(this).find("input");
                    if (checked) {
                        $checkBox.prop("checked", true);
                    } else {
                        $checkBox.prop("checked", false);
                    }
                });
                let rows = $grid.data(internal.SELECTED_ROWS);
                if (checked) {
                    if (!rows) {
                        rows = {};
                        rows.selectAll = true;
                        $grid.data(internal.SELECTED_ROWS, rows);
                    } else {
                        rows.selectAll = true;
                    }
                    for (let i = 0; i < ds.length; i++) {
                        selection.selectRow($grid, i);
                    }
                } else {
                    rows.selectAll = false;
                    for (let i = 0; i < ds.length; i++) {
                        selection.deselectRow($grid, i);
                    }
                }
            } else {
                let $row =  selection.rowAt($grid, rowIdx);
                $checkBox = $row.find("td:first input");
                if (checked) {
                    $checkBox.prop("checked", true);
                    selection.selectRow($grid, rowIdx);
                } else {
                    $checkBox.prop("checked", false);
                    selection.deselectRow($grid, rowIdx);
                }
                
                let rows = $grid.data(internal.SELECTED_ROWS);
                let $allBox = $grid.siblings("." + HEADER_PRF + LEFTMOST).find("table tr:first td:first input");
                if (rows.count === ds.length) {
                    rows.selectAll = true;
                    if (!$allBox.is(":checked")) $allBox.prop("checked", true);
                } else {
                    rows.selectAll = false;
                    if ($allBox.is(":checked")) $allBox.prop("checked", false);
                }
            }
        }
    }
    
    module events {
        export let SCROLL_EVT = "scroll";
        export let CLICK_EVT = "click";
        export let MOUSE_DOWN = "mousedown";
        export let MOUSE_MOVE = "mousemove";
        export let MOUSE_UP = "mouseup";
        export let MOUSE_OVER = "mouseover";
        export let MOUSE_ENTER = "mouseenter";
        export let MOUSE_OUT = "mouseout";
        export let FOCUS_IN = "focusin";
        export let PASTE = "paste";
        export let MOUSE_WHEEL = "wheel";
        export let RESIZE = "resize";
        export let KEY_DOWN = "keydown";
        export let KEY_UP = "keyup";
        export let CM = "contextmenu";
        export let AREA_RESIZE_STARTED = "extablearearesizestarted";
        export let AREA_RESIZE = "extablearearesize";
        export let AREA_RESIZE_END = "extablearearesizeend";
        export let BODY_HEIGHT_CHANGED = "extablebodyheightchanged";
        export let OCCUPY_UPDATE = "extableoccupyupdate";
        export let START_EDIT = "extablestartedit";
        export let STOP_EDIT = "extablestopedit";
        export let CELL_UPDATED = "extablecellupdated";
        export let ROW_UPDATED = "extablerowupdated";
        export let POPUP_SHOWN = "xpopupshown";
        export let POPUP_INPUT_END = "xpopupinputend";
        export let ROUND_RETREAT = "extablecellretreat";
        export let CHECK_ALL = "extableselectallrows";
        export let CHECK_ROW = "extableselectrow";
        export let MOUSEIN_COLUMN = "extablemouseincolumn";
        export let MOUSEOUT_COLUMN = "extablemousoutcolumn";
        export let COMPLETED = "extablecompleted"; 
        
        /**
         * Trigger.
         */
        export function trigger($target: JQuery, eventName: string, args?: any) {
            $target.trigger($.Event(eventName), args);
        }
        
        /**
         * On modify.
         */
        export function onModify($exTable: JQuery) {
            $exTable.on(CELL_UPDATED, function(evt: any, ui: any) {
                let exTable = $exTable.data(NAMESPACE);
                if (!exTable) return;
                if (ui.value.constructor === Array && (util.isNullOrUndefined(ui.innerIdx) || ui.innerIdx === -1)) {
                    pushChange(exTable, ui.rowIndex, new selection.Cell(ui.rowIndex, ui.columnKey, ui.value[0], 0));
                    pushChange(exTable, ui.rowIndex, new selection.Cell(ui.rowIndex, ui.columnKey, ui.value[1], 1));
                    return;
                }
                pushChange(exTable, ui.rowIndex, ui); 
            });
            
            $exTable.on(ROW_UPDATED, function(evt: any, ui: any) {
                let exTable = $exTable.data(NAMESPACE);
                if (!exTable) return;
                let cells = [];
                _.forEach(Object.keys(ui.data), function(k, i) {
                    if (ui.data[k].constructor === Array && ui.data[k].length === 2) {
                        cells.push(new selection.Cell(ui.rowIndex, k, ui.data[k][0], 0));
                        cells.push(new selection.Cell(ui.rowIndex, k, ui.data[k][1], 1));
                        return;
                    }
                    cells.push(new selection.Cell(ui.rowIndex, k, ui.data[k], -1));
                });
                _.forEach(cells, function(c, i) {
                    pushChange(exTable, c.rowIndex, c);
                });
            });
        }
        
        /**
         * Push change.
         */
        function pushChange(exTable: any, rowIdx: any, cell: any) {
            let modifies = exTable.modifications;
            if (!modifies) {
                exTable.modifications = {};
                exTable.modifications[rowIdx] = [ cell ];
                return;
            }
            if (!modifies[rowIdx]) {
                modifies[rowIdx] = [ cell ];
                return;
            }
            let rData = modifies[rowIdx];
            let cellUpdated = false;
            _.forEach(rData, function(c, i) {
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
        
        /**
         * Create row ui.
         */
        export function createRowUi(rowIdx: any, data: any) {
            return {
                rowIndex: rowIdx,
                data: data
            };
        }
    }
    
    module feature {
        export let UPDATING = "Updating";
        export let HEADER_ROW_HEIGHT = "HeaderRowHeight";
        export let HEADER_CELL_STYLE = "HeaderCellStyle";
        export let HEADER_POP_UP = "HeaderPopups";
        export let BODY_CELL_STYLE = "BodyCellStyle";
        export let COLUMN_RESIZE = "ColumnResize";
        export let TIME_RANGE = "TimeRange"
        
        /**
         * Is enable.
         */
        export function isEnable(features: any, name: string) {
            return _.find(features, function(feature: any) {
                return feature.name === name;
            }) !== undefined;
        }
        
        /**
         * Find.
         */
        export function find(features: any, name: string) {
            return _.find(features, function(feature: any) {
                return feature.name === name;
            });
        }
    }
    
    module style {
        export let DET_CLS: string = "xdet";
        export let HIDDEN_CLS: string = "xhidden";
        export let SEAL_CLS: string = "xseal";
        
        export class CellStyleParam {
            $cell: JQuery;
            cellData: any;
            rowData: any;
            rowIdx: any;
            columnKey: any;
            constructor($cell, cellData, rowData, rowIdx, columnKey) {
                this.$cell = $cell;
                this.cellData = cellData;
                this.rowData = rowData;
                this.rowIdx = rowIdx;
                this.columnKey = columnKey;
            }
        }
        
        export class Cell {
            rowId: any;
            columnKey: any;
            constructor(rowId: any, columnKey: any) {
                this.rowId = rowId;
                this.columnKey = columnKey;
            }
        }
        
        /**
         * Det column.
         */
        export function detColumn($grid: JQuery, $row: JQuery, rowIdx: any) {
            let $tbl = $grid.closest("." + NAMESPACE);
            let detOpt = $tbl.data(NAMESPACE).determination;
            if (!detOpt || !$grid.hasClass(HEADER_PRF + DETAIL)) return;
            _.forEach(detOpt.rows, function(i: any) {
                if (i === rowIdx) {
                    $row.on(events.MOUSE_DOWN, function(evt: any) {
                        if (!evt.ctrlKey) return;
                        let $main = helper.getMainTable($tbl);
                        let gen = $main.data(internal.TANGI) || $main.data(internal.CANON);
                        let ds = gen.dataSource;
                        let primaryKey = helper.getPrimaryKey($main);
                        let start = gen.startIndex || 0;
                        let end = gen.endIndex || ds.length - 1;
                        let $hCell = $(evt.target);
                        let coord = helper.getCellCoord($hCell);
                        let det = $main.data(internal.DET);
                        if (!det) {
                            det = {};
                        }
                        
                        let xRows = [];
                        let xCellsInColumn = _.filter(ds, (r, i) => {
                            if (helper.isXCell($main, r[primaryKey], coord.columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) {
                                xRows.push(i);
                                return true;
                            }
                            return false; 
                        });
                        
                        let rows = Object.keys(det);
                        if (rows.length >= (ds.length - xCellsInColumn.length)) {
                            let flaw = false;
                            let indices = {};
                            _.forEach(rows, function(k, i) {
                                let found = false;
                                _.forEach(det[k], (c, j) => { 
                                    if (c === coord.columnKey) {
                                        indices[k] = j;
                                        found = true;
                                        return false;
                                    }
                                });
                                if (!found && !xRows.some((val) => parseInt(k) === val)) {
                                    flaw = true;
                                    return false;
                                }
                            });
                            if (!flaw) {
                                let rKeys = Object.keys(indices); 
                                _.forEach(rKeys, function(k, i) {
                                    let col = det[k].splice(indices[k], 1);
                                    if (det[k].length === 0) delete det[k];
                                    let $c = selection.cellAt($main, k, col[0]);
                                    helper.stripCellWith(DET_CLS, $c);
                                });
                                return;
                            }
                        }
                        _.forEach(ds, function(item: any, index: number) {
                            if (index >= start && index < end) {
                                let $c = selection.cellAt($main, index, coord.columnKey);
                                if ($c === intan.NULL || $c.length === 0 || !helper.isDetable($c)) return;
                                helper.markCellWith(DET_CLS, $c);
                            } else if (helper.isXCell($main, item[primaryKey], coord.columnKey, style.HIDDEN_CLS, style.SEAL_CLS)) return;
                            
                            if (!det[index]) {
                                det[index] = [ coord.columnKey ];
                                $main.data(internal.DET, det);
                            } else {
                                let dup;
                                _.forEach(det[index], function(key) {
                                    if (key === coord.columnKey) {
                                        dup = true;
                                        return false;
                                    }
                                });
                                if (!dup) {
                                    det[index].push(coord.columnKey);
                                }
                            }
                        });
                    });
                    return false;
                }
            });
        }
        
        /**
         * Det cell.
         */
        export function detCell($grid: JQuery, $cell: any, rowIdx: any, columnKey: any) {
            let $tbl = $grid.closest("." + NAMESPACE);
            let detOpt = $tbl.data(NAMESPACE).determination;
            if (!detOpt) return;
            if ($grid.hasClass(BODY_PRF + LEFTMOST)) {
                _.forEach(detOpt.columns, function(key: any) {
                    if (key === columnKey) {
                        $cell.on(events.MOUSE_DOWN, function(evt: any) {
                            if (!evt.ctrlKey) return;
                            let $main = helper.getMainTable($tbl);
                            let coord = helper.getCellCoord($cell);
                            let $targetRow = selection.rowAt($main, coord.rowIdx);
                            if ($targetRow === intan.NULL || !$targetRow) return;
                            
                            let colKeys = _.map(helper.gridVisibleColumns($main), "key");
                            let det = $main.data(internal.DET);
                            let rowDet;
                            
                            let undetables = [];
                            let detables = $targetRow.find("td").filter(function() {
                                return $(this).css("display") !== "none";
                            }).filter(function(i) {
                                if (!helper.isDetable($(this))) {
                                    undetables.push(i);
                                    return false;
                                }
                                return true;
                            });
                            for (var i = undetables.length - 1; i >= 0; i--) {
                                colKeys.splice(undetables[i], 1);
                            }
                            
                            if (det && (rowDet = det[coord.rowIdx]) && rowDet.length === colKeys.length) {
                                helper.stripCellsWith(DET_CLS, $targetRow.find("td").filter(function() {
                                    return $(this).css("display") !== "none";
                                }));
//                                det[coord.rowIdx] = [];
                                delete det[coord.rowIdx];
                                return;
                            }
                            helper.markCellsWith(DET_CLS, detables);
                            
                            if (!det) {
                                det = {};
                                det[coord.rowIdx] = colKeys;
                                $main.data(internal.DET, det);
                            } else if (!det[coord.rowIdx]) {
                                det[coord.rowIdx] = colKeys;
                            } else {
                                let dup;
                                _.forEach(colKeys, function(k) {
                                    dup = false;
                                    _.forEach(det[coord.rowIdx], function(existedKey) {
                                        if (existedKey === k) {
                                            dup = true;
                                            return false;
                                        }
                                    });
                                    if (!dup) {
                                        det[coord.rowIdx].push(k);
                                    }
                                });
                                
                            }
                        });
                        return false;
                    }
                });
            } else if ($grid.hasClass(BODY_PRF + DETAIL)) {
                let $childCells = $cell.find("." + render.CHILD_CELL_CLS);
                let $target = $cell;
                if ($childCells.length > 0) {
                    $target = $childCells;
                }
                $target.on(events.MOUSE_DOWN, function(evt: any) {
                    onDetSingleCell(evt, $tbl, $cell, rowIdx, columnKey);
                });
            }
        }
        
        /**
         * On det single cell.
         */
        function onDetSingleCell(evt: any, $tbl: JQuery, $cell: JQuery, rowIdx: any, columnKey: any) {
            if (!evt.ctrlKey || !helper.isDetable($cell)) return;
            let $main = helper.getMainTable($tbl);
            let det = $main.data(internal.DET);
            if (!det) {
                det = {};
                det[rowIdx] = [ columnKey ];
                $main.data(internal.DET, det);
            } else if (!det[rowIdx]) {
                det[rowIdx] = [ columnKey ];
            } else {
                let dup = -1;
                _.forEach(det[rowIdx], function(key: any, index: any) {
                    if (key === columnKey) {
                        dup = index;
                        return false;
                    }
                });
                if (dup > -1) {
                    let a = [];
                    det[rowIdx].splice(dup, 1);
                    if (det[rowIdx].length === 0) delete det[rowIdx];
                    helper.stripCellWith(DET_CLS, $cell);
                    return;
                }
                det[rowIdx].push(columnKey);
            }
            helper.markCellWith(DET_CLS, $cell);
        }
    }
    
    module func {
        let LEFT_TBL: string = "leftmost";
        let HORZ_SUM: string = "horizontalSummaries";
        let VERT_SUM: string = "verticalSummaries";
        $.fn.exTable = function(name: any, ...params: Array<any>) {
            let self = this;
            switch(name) {
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
                    return setViewMode(self, params[0], params[1]);
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
        
        /**
         * Change grid height mode.
         */
        function changeGridHeightMode($container: JQuery, mode: string) {
            if (mode === DYNAMIC) {
                let bodyWrappers = [], horzSumExists = false;
                let $bodyWrappers = $container.find("div[class*='" + BODY_PRF + "']").each(function() {
                    if ($(this).hasClass(BODY_PRF + HORIZONTAL_SUM) || $(this).hasClass(BODY_PRF + LEFT_HORZ_SUM)) {
                        horzSumExists = true;
                        return;
                    }
                    bodyWrappers.push($(this));
                });
                
                $(window).on(events.RESIZE, $.proxy(resize.fitWindowHeight, undefined, $container, bodyWrappers, horzSumExists));
            } else {
                $(window).off(events.RESIZE, resize.fitWindowHeight);
            }
        }
        
        /**
         * Hide horzSum.
         */
        function hideHorzSum($container: JQuery) {
            $container.find("." + HEADER_PRF + LEFT_HORZ_SUM).hide();
            $container.find("." + BODY_PRF + LEFT_HORZ_SUM).hide();
            $container.find("." + HEADER_PRF + HORIZONTAL_SUM).hide();
            $container.find("." + BODY_PRF + HORIZONTAL_SUM).hide();
            resize.fitWindowHeight($container, undefined, false);
        }
        
        /**
         * Show horzSum.
         */
        function showHorzSum($container: JQuery) {
            $container.find("." + HEADER_PRF + LEFT_HORZ_SUM).show();
            $container.find("." + BODY_PRF + LEFT_HORZ_SUM).show();
            $container.find("." + HEADER_PRF + HORIZONTAL_SUM).show();
            $container.find("." + BODY_PRF + HORIZONTAL_SUM).show();
            resize.fitWindowHeight($container, undefined, true);
        }
        
        /**
         * Hide vertSum.
         */
        function hideVertSum($container: JQuery) {
            $container.find("." + HEADER_PRF + VERTICAL_SUM).hide();
            $container.find("." + BODY_PRF + VERTICAL_SUM).hide();
            resize.fitWindowWidth($container);
            scroll.unbindVertWheel($container.find("." + BODY_PRF + DETAIL));
        }
        
        /**
         * Show vertSum.
         */
        function showVertSum($container: JQuery) {
            let $vertSumBody = $container.find("." + BODY_PRF + VERTICAL_SUM);
            let $detailBody = $container.find("." + BODY_PRF + DETAIL);
            $container.find("." + HEADER_PRF + VERTICAL_SUM).show();
            $vertSumBody.show();
            resize.fitWindowWidth($container);
            scroll.bindVertWheel($detailBody);
            $vertSumBody.scrollTop($detailBody.scrollTop());
        }
        
        /**
         * Update table.
         */
        function updateTable($container: JQuery, name: string, header: any, body: any, keepStates?: boolean) {
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
        
        /**
         * Update leftmost.
         */
        function updateLeftmost($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.leftmostHeader, header);
                let $header = $container.find("." + HEADER_PRF + LEFTMOST);
                let pu = $header.find("table").data(internal.POPUP);
                $header.empty();
                render.process($header, exTable.leftmostHeader, true);
                if (pu && pu.css("display") !== "none") pu.hide();
            }
            if (body) {
                _.assignIn(exTable.leftmostContent, body);
                let $body = $container.find("." + BODY_PRF + LEFTMOST);
                $body.empty();
                render.process($body, exTable.leftmostContent, true);
            }
        }
        
        /**
         * Update middle.
         */
        function updateMiddle($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.middleHeader, header);
                let $header = $container.find("." + HEADER_PRF + MIDDLE);
                let pu = $header.find("table").data(internal.POPUP);
                $header.empty();
                render.process($header, exTable.middleHeader, true);
                if (pu && pu.css("display") !== "none") pu.hide();
            }
            if (body) {
                _.assignIn(exTable.middleContent, body);
                let $body = $container.find("." + BODY_PRF + MIDDLE);
                $body.empty();
                render.process($body, exTable.middleContent, true);
            }
        }
        
        /**
         * Update detail.
         */
        function updateDetail($container: JQuery, header: any, body: any, keepStates: boolean) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.detailHeader, header);
                let $header = $container.find("." + HEADER_PRF + DETAIL);
                let pu = $header.find("table").data(internal.POPUP);
                $header.empty();
                render.process($header, exTable.detailHeader, true);
                if (pu && pu.css("display") !== "none") pu.hide();
            }
            if (body) {
                _.assignIn(exTable.detailContent, body);
                let $body = $container.find("." + BODY_PRF + DETAIL);
                $body.empty();
                if (!keepStates) internal.clearStates($body);
                render.process($body, exTable.detailContent, true);
            }
        }
        
        /**
         * Update vertSum.
         */
        function updateVertSum($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.verticalSumHeader, header);
                let $header = $container.find("." + HEADER_PRF + VERTICAL_SUM);
                let pu = $header.find("table").data(internal.POPUP);
                $header.empty();
                render.process($header, exTable.verticalSumHeader, true);
                if (pu && pu.css("display") !== "none") pu.hide();
            }
            if (body) {
                _.assignIn(exTable.verticalSumContent, body);
                let $body = $container.find("." + BODY_PRF + VERTICAL_SUM);
                $body.empty();
                render.process($body, exTable.verticalSumContent, true);
            }
        }
        
        /**
         * Update leftHorzSum.
         */
        function updateLeftHorzSum($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.leftHorzSumHeader, header);
                let $header = $container.find("." + HEADER_PRF + LEFT_HORZ_SUM);
                let pu = $header.find("table").data(internal.POPUP);
                $header.empty();
                render.process($header, exTable.leftHorzSumHeader, true);
                if (pu && pu.css("display") !== "none") pu.hide();
            }
            if (body) {
                _.assignIn(exTable.leftHorzSumContent, body);
                let $body = $container.find("." + BODY_PRF + LEFT_HORZ_SUM);
                $body.empty();
                render.process($body, exTable.leftHorzSumContent, true);
            }
        }
        
        /**
         * Update horzSum.
         */
        function updateHorzSum($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.horizontalSumHeader, header);
                let $header = $container.find("." + HEADER_PRF + HORIZONTAL_SUM);
                let pu = $header.find("table").data(internal.POPUP);
                $header.empty();
                render.process($header, exTable.horizontalSumHeader, true);
                if (pu && pu.css("display") !== "none") pu.hide();
            }
            if (body) {
                _.assignIn(exTable.horizontalSumContent, body);
                let $body = $container.find("." + BODY_PRF + HORIZONTAL_SUM);
                $body.empty();
                render.process($body, exTable.horizontalSumContent, true);
            }
        }
        
        /**
         * Set update mode.
         */
        function setUpdateMode($container: JQuery, mode: string, occupation?: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (!mode) return exTable.updateMode;
            if (exTable.updateMode === mode) return;
            exTable.setUpdateMode(mode);
            if (occupation) {
                events.trigger($container, events.OCCUPY_UPDATE, occupation);
            }
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            render.begin($grid, internal.getDataSource($grid), exTable.detailContent);
            selection.tickRows($container.find("." + BODY_PRF + LEFTMOST), true);
            if (mode === COPY_PASTE) {
                selection.checkUp($container);
                copy.on($grid, mode);
                return;
            }
            selection.off($container);
            copy.off($grid, mode);
        }
        
        /**
         * Set view mode.
         */
        function setViewMode($container: JQuery, mode: string, occupation?: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (!mode) return exTable.viewMode;
            if (occupation) {
                events.trigger($container, events.OCCUPY_UPDATE, occupation);
            }
            
            if (exTable.viewMode === mode) return;
            if (exTable.updateMode === EDIT) {
                if (errors.occurred($container)) return;
                let editor = $container.data(update.EDITOR);
                if (editor) {
                    $editor = editor.$editor;
                    $input = $editor.find("input");
                    let $editingCell = $editor.closest("." + update.EDIT_CELL_CLS).removeClass(update.EDIT_CELL_CLS);
                    update.triggerStopEdit($container, $editingCell, editor.land, $input.val());
                    $container.data(update.EDITOR, null);
                }
            }
            exTable.setViewMode(mode);
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            render.begin($grid, internal.getDataSource($grid), exTable.detailContent);
        }
        
        /**
         * Set paste overwrite.
         */
        function setPasteOverWrite($container: JQuery, overwrite: boolean) {
            let exTable: any = $container.data(NAMESPACE);
            exTable.pasteOverWrite = overwrite;
        }
        
        /**
         * Set stick overwrite.
         */
        function setStickOverWrite($container: JQuery, overwrite: boolean) {
            let exTable: any = $container.data(NAMESPACE);
            exTable.stickOverWrite = overwrite;
        }
        
        /**
         * Set stick mode.
         */
        function setStickMode($container: JQuery, mode: any) {
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            let sticker = $grid.data(internal.STICKER);
            if (!sticker) {
                sticker = new spread.Sticker();
                sticker.mode = mode;
                $grid.data(internal.STICKER, sticker);
            } else {
                sticker.mode = mode;
            }
        }
        
        /**
         * Set stick data.
         */
        function setStickData($container: JQuery, data: any) {
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            let sticker = $grid.data(internal.STICKER);
            if (!sticker) {
                sticker = new spread.Sticker(data);
                $grid.data(internal.STICKER, sticker);
            } else {
                sticker.data = data;
            }
        }
        
        /**
         * Set stick validate.
         */
        function setStickValidate($container: JQuery, validate: any) {
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            let sticker = $grid.data(internal.STICKER);
            if (!sticker) {
                sticker = new spread.Sticker();
                sticker.validate = validate;
                $grid.data(internal.STICKER, sticker);
            } else {
                sticker.validate = validate;
            }
        }
        
        /**
         * Undo stick.
         */
        function undoStick($container: JQuery) {
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            let histories = $grid.data(internal.STICK_HISTORY);
            if (!histories) return;
            let items = histories.pop();
            _.forEach(items, function(i: any) {
                update.gridCell($grid, i.rowIndex, i.columnKey, -1, i.value, true);
                internal.removeChange($grid, i);
            });
        }
        
        /**
         * Clear histories.
         */
        function clearHistories($container: JQuery, type: string) {
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            let histType;
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
        
        /**
         * Lock cell.
         */
        function lockCell($container: JQuery, rowId: any, columnKey: any) {
            let $table = helper.getMainTable($container);
            let ds = helper.getDataSource($table);
            let pk = helper.getPrimaryKey($table);
            let i = -1;
            _.forEach(ds, function(r, j) {
                if (r[pk] === rowId) {
                    i = j;
                    return false;
                }
            });
            if (i === -1) return;
            let locks = $table.data(internal.DET);
            let found = -1;
            if (locks && locks[i] && locks[i].length > 0) { 
                _.forEach(locks[i], function(c, j) {
                    if (c === columnKey) {
                        found = j;
                        return false;
                    }
                });
            }
            
            if (found === -1) {
                let $cell = selection.cellAt($table, i, columnKey);
                if (!locks) {
                    locks = {};
                    locks[i] = [ columnKey ];
                    $table.data(internal.DET, locks);
                } else if (locks && !locks[i]) {
                    locks[i] = [ columnKey ];
                } else locks[i].push(columnKey);
                helper.markCellWith(style.DET_CLS, $cell);
            }
        }
        
        /**
         * Unlock cell.
         */
        function unlockCell($container: JQuery, rowId: any, columnKey: any) {
            let $table = helper.getMainTable($container);
            let ds = helper.getDataSource($table);
            let pk = helper.getPrimaryKey($table);
            let i = -1;
            _.forEach(ds, function(r, j) {
                if (r[pk] === rowId) {
                    i = j;
                    return false;
                }
            });
            if (i === -1) return;
            let locks = $table.data(internal.DET);
            let found = -1;
            if (locks && locks[i] && locks[i].length > 0) {
                _.forEach(locks[i], function(c, j) {
                    if (c === columnKey) {
                        found = j;
                        return false;
                    }
                });
            }
            
            if (found > -1) {
                let $cell = selection.cellAt($table, i, columnKey);
                locks[i].splice(found, 1);
                if (locks[i].length === 0) delete locks[i];
                helper.stripCellWith(style.DET_CLS, $cell);
            }
        }
        
        /**
         * Return popup value.
         */
        function returnPopupValue($container: JQuery, value: any) {
            let header = helper.getMainHeader($container).find("table:first");
            if (!header) return;
            let $pu = header.data(internal.POPUP);
            if (!$pu) return;
            events.trigger($pu, events.POPUP_INPUT_END, { value: value });
        }
        
        /**
         * Get data source.
         */
        function getDataSource($container: JQuery, name: any) {
            switch (name) {
                case "leftmost":
                    return helper.getPartialDataSource($container, LEFTMOST);
                case "middle": 
                    return helper.getPartialDataSource($container, MIDDLE);
                case "detail":
                    return helper.getPartialDataSource($container, DETAIL);
                case "verticalSummaries":
                    return helper.getPartialDataSource($container, VERTICAL_SUM);
                case "leftHorizontalSummaries":
                    return helper.getPartialDataSource($container, LEFT_HORZ_SUM);
                case "horizontalSummaries":
                    return helper.getPartialDataSource($container, HORIZONTAL_SUM);
            }
        }
        
        /**
         * Get cell by index.
         */
        function getCellByIndex($container: JQuery, rowIndex: any, columnKey: any) {
            let $tbl = helper.getMainTable($container);
            if ($tbl.length === 0) return;
            return selection.cellAt($tbl, rowIndex, columnKey);
        }
        
        /**
         * Get cell by Id.
         */
        function getCellById($container: JQuery, rowId: any, columnKey: any) {
            let $tbl = helper.getMainTable($container);
            if ($tbl.length === 0) return;
            return selection.cellOf($tbl, rowId, columnKey);
        }
        
        /**
         * Get updated cells.
         */
        function getUpdatedCells($container: JQuery) {
            let data = $container.data(NAMESPACE).modifications;
            if (!data) return [];
            return helper.valuesArray(data);
        }
        
        /**
         * Round retreat.
         */
        function roundRetreat($container: JQuery, value: any) {
            if (!value) return;
            events.trigger($container, events.ROUND_RETREAT, value);
        }
        
        /**
         * Set cell value.
         */
        function setCellValue($container: JQuery, name: any, rowId: any, columnKey: any, value: any) {
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
        
        /**
         * Set cell value by index.
         */
        function setCellValueByIndex($container: JQuery, name: any, rowIdx: any, columnKey: any, value: any) {
            switch(name) {
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
        
        /**
         * Set value.
         */
        function setValue($container: JQuery, selector: any, rowId: any, columnKey: any, value: any) {
            let $grid = $container.find("." + selector);
            if ($grid.length === 0) return;
            let rowIdx = helper.getRowIndex($grid, rowId);
            let ds = helper.getDataSource($grid);
            if (rowIdx === -1 || !ds || ds.length === 0) return;
            if (selector === BODY_PRF + LEFTMOST) {
                if (ds[rowIdx][columnKey] !== value) {
                    update.gridCell($grid, rowIdx, columnKey, -1, value);
                    update.pushEditHistory($grid, new selection.Cell(rowIdx, columnKey, value, -1));
                }
            } else {
                ds[rowIdx][columnKey] = value;
                refreshCell($grid, rowId, columnKey, value);
            }
        }
        
        /**
         * Set value by index.
         */
        function setValueByIndex($container: JQuery, selector: any, rowIdx: any, columnKey, value: any) {
            let $grid = $container.find("." + selector);
            if ($grid.length === 0) return;
            let ds = helper.getDataSource($grid);
            if (!ds || ds.length === 0) return;
            if (selector === BODY_PRF + LEFTMOST) {
                if (ds[rowIdx][columnKey] !== value) {
                    update.gridCell($grid, rowIdx, columnKey, -1, value);
                    update.pushEditHistory($grid, new selection.Cell(rowIdx, columnKey, value, -1));
                }
            } else {
                ds[rowIdx][columnKey] = value;
                refreshCellByIndex($grid, rowIdx, columnKey, value);
            }
        }
        
        /**
         * Refresh cell.
         */
        function refreshCell($grid: JQuery, rowId: any, columnKey: any, value?: any) {
            let $c = selection.cellOf($grid, rowId, columnKey);
            if ($c === intan.NULL || !$c) return;
            if (util.isNullOrUndefined(value)) {
                let ds = helper.getClonedDs($grid);
                if (!ds || ds.length === 0) return;
                let rIdx = helper.getRowIndex($grid, rowId);
                if (rIdx === -1) return;
                value = ds[rIdx][columnKey];
            }
            $c.text(value);
        }
        
        /**
         * Refresh cell by index.
         */
        function refreshCellByIndex($grid: JQuery, rowIdx: any, columnKey: any, value?: any) {
            let $c = selection.cellAt($grid, rowIdx, columnKey);
            if ($c === intan.NULL || !$c) return;
            if (util.isNullOrUndefined(value)) {
                let ds = helper.getClonedDs($grid);
                if (!ds || ds.length === 0) return;
                value = ds[rowIdx][columnKey];
            }
            $c.text(value);
        }
        
        /**
         * Set row Id.
         */
        function setRowId($container: JQuery, rowIndex: any, value: any) {
            $container.find("div[class*='" + BODY_PRF + "']").filter(function() {
                return !$(this).hasClass(BODY_PRF + HORIZONTAL_SUM) && !$(this).hasClass(BODY_PRF + LEFT_HORZ_SUM);
            }).each(function() {
                let key = helper.getPrimaryKey($(this));
                let ds = internal.getDataSource($(this));
                if (!ds || ds.length === 0 || !ds[rowIndex]) return;
                if (ds[rowIndex][key] !== value) {
                    update.gridCell($(this), rowIndex, key, -1, value);
                    update.pushEditHistory($(this), new selection.Cell(rowIndex, key, value, -1));
                }
            });
        }
        
        /**
         * Save scroll.
         */
        function saveScroll($container: JQuery) {
            let key = request.location.current.rawUrl + "/" + $container.attr("id") + "/scroll";
            let scroll: any = {};
            let tbl = helper.getMainTable($container);
            scroll.v = tbl.scrollTop();
            scroll.h = tbl.scrollLeft();
            uk.localStorage.setItemAsJson(key, scroll);
        }
        
        /**
         * Scroll back.
         */
        function scrollBack($container: JQuery, where: number) {
            let key = request.location.current.rawUrl + "/" + $container.attr("id") + "/scroll";
            let item = uk.localStorage.getItem(key);
            if (!item.isPresent()) return; 
            let tbl = helper.getMainTable($container);
            let scroll = JSON.parse(item.get());
            switch (where) {
                case 0:
                    tbl.scrollLeft(scroll.h);
                    break;
                case 1: 
                    tbl.scrollTop(scroll.v);
                    break;
                case 2:
                    tbl.scrollLeft(scroll.h);
                    tbl.scrollTop(scroll.v);
                    break;
            }
        }
    }
    
    module internal {
        export let X_OCCUPY: string = "ex-x-occupy";
        export let Y_OCCUPY: string = "ex-y-occupy";
        export let TANGI: string = "x-tangi";
        export let CANON: string = "x-canon";
        export let STICKER: string = "x-sticker";
        export let DET: string = "x-det";
        export let PAINTER: string = "painter";
        export let CELLS_STYLE: string = "body-cells-style";
        export let VIEW: string = "view";
        export let EX_PART: string = "expart";
        export let TIME_VALID_RANGE = "time-validate-range";
        export let SELECTED_CELLS: string = "selected-cells";
        export let LAST_SELECTED: string = "last-selected";
        export let SELECTED_ROWS: string = "selected-rows";
        export let COPY_HISTORY: string = "copy-history";
        export let EDIT_HISTORY: string = "edit-history";
        export let STICK_HISTORY: string = "stick-history";
        export let TOOLTIP: string = "tooltip";
        export let CONTEXT_MENU: string = "context-menu";
        export let POPUP: string = "popup";
        export let TEXT: string = "text";
        export let TIME: string = "time";
        export let DURATION: string = "duration";
        export let NUMBER: string = "number";
        export let DT_SEPARATOR: string = "/";
        export let COLUMN_IN: string = "column-in";
        
        /**
         * Get gem.
         */
        export function getGem($grid: JQuery) {
            return $grid.data(internal.TANGI) || $grid.data(internal.CANON);
        }
        
        /**
         * Get data source.
         */
        export function getDataSource($grid: JQuery) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            return gen.dataSource;
        }
        
        /**
         * Remove change.
         */
        export function removeChange($grid: JQuery, cell: any) {
            let origDs = helper.getOrigDS($grid);
            let exTable = helper.getExTableFromGrid($grid);
            if (!origDs || !exTable) return;
            let oVal = origDs[cell.rowIndex][cell.columnKey];
            let cells = exTable.modifications[cell.rowIndex];
            if (!cells) return;
            let index = -1;
            _.forEach(cells, function(c: any, i: number) {
                if (helper.areSameCells(cell, c) && cell.value === oVal) {
                    index = i;
                    return false;
                }
            });
            exTable.modifications[cell.rowIndex].splice(index, 1);
        }
        
        /**
         * Clear states.
         */
        export function clearStates($grid: JQuery) {
            $grid.data(SELECTED_CELLS, null);
            $grid.data(LAST_SELECTED, null);
            $grid.data(COPY_HISTORY, null);
            $grid.data(EDIT_HISTORY, null);
            $grid.data(STICK_HISTORY, null);
            let exTable = helper.getExTableFromGrid($grid);
            if (!exTable) return;
            exTable.modifications = {};
        }
    }
    
    module helper {
        
        /**
         * Get scroll width.
         */
        export function getScrollWidth() {
            var $outer = $('<div>').css({ visibility: 'hidden', width: 100, overflow: 'scroll' }).appendTo('body'),
            widthWithScroll = $('<div>').css({ width: '100%' }).appendTo($outer).outerWidth();
            $outer.remove();
            return 100 - widthWithScroll;
        }
        
        /**
         * Get table.
         */
        export function getTable($exTable: JQuery, name: string) {
            return $exTable.find("." + name);
        }
        
        /**
         * Get main header.
         */
        export function getMainHeader($exTable: JQuery) {
            return $exTable.find("." + HEADER_PRF + DETAIL);
        }
        
        /**
         * Get main table.
         */
        export function getMainTable($exTable: JQuery) {
            return $exTable.find("." + BODY_PRF + DETAIL);
        }
        
        /**
         * Get leftmost table.
         */
        export function getLeftmostTable($exTable: JQuery) {
            return $exTable.find("." + BODY_PRF + LEFTMOST);
        }
        
        /**
         * Get exTable from grid.
         */
        export function getExTableFromGrid($grid: JQuery) {
            return $grid.closest("." + NAMESPACE).data(NAMESPACE);
        }
        
        /**
         * Get visible columns.
         */
        export function getVisibleColumnsOn($grid: JQuery) {
            return ($grid.data(internal.TANGI) || $grid.data(internal.CANON)).painter.visibleColumns;
        }
        
        /**
         * Get visible columns.
         */
        export function getVisibleColumns(options: any) {
            let visibleColumns = [];
            filterColumns(options.columns, visibleColumns, []);
            return visibleColumns;
        }
        
        /**
         * Get origDS.
         */
        export function getOrigDS($grid: JQuery) {
            return ($grid.data(internal.TANGI) || $grid.data(internal.CANON))._origDs;
        }
        
        /**
         * Get data source.
         */
        export function getDataSource($grid: JQuery) {
            return ($grid.data(internal.TANGI) || $grid.data(internal.CANON)).dataSource;
        }
        
        /**
         * Get clonedDs.
         */
        export function getClonedDs($grid: JQuery) {
            return _.cloneDeep(getDataSource($grid));
        }
        
        /**
         * Get primary key.
         */
        export function getPrimaryKey($grid: JQuery) {
            return ($grid.data(internal.TANGI) || $grid.data(internal.CANON)).primaryKey;
        }
        
        /**
         * Classify columns.
         */
        export function classifyColumns(options: any) {
            let visibleColumns = [];
            let hiddenColumns = [];
            filterColumns(options.columns, visibleColumns, hiddenColumns);
            return {
                        visibleColumns: visibleColumns,
                        hiddenColumns: hiddenColumns
                   };
        }
        
        /**
         * Filter columns.
         */
        function filterColumns(columns: Array<any>, visibleColumns: Array<any>, hiddenColumns: Array<any>) {
            _.forEach(columns, function(col: any) {
                if (!util.isNullOrUndefined(col.visible) && col.visible === false) {
                    hiddenColumns.push(col);
                    return;
                }
                if (!util.isNullOrUndefined(col.group) && col.group.length > 0) { 
                    filterColumns(col.group, visibleColumns, hiddenColumns);
                } else {
                    visibleColumns.push(col);
                }
            });
        }
        
        /**
         * Get columns map.
         */
        export function getColumnsMap(columns: any) {
            return _.groupBy(columns, "key");
        }
        
        /**
         * Get columns map from struct.
         */
        export function columnsMapFromStruct(levelStruct: any) {
            let map = {};
            _.forEach(Object.keys(levelStruct), function(nth: any) {
                _.forEach(levelStruct[nth], function(col: any) {
                    if (!util.isNullOrUndefined(col.key)) {
                        map[col.key] = col;
                    }
                });
            });
            return map; 
        }
        
        /**
         * Get partial data source.
         */
        export function getPartialDataSource($table: any, name: any) {
            return {
                header: getClonedDs($table.find("." + HEADER_PRF + name)),
                body: getClonedDs($table.find("." + BODY_PRF + name))
            };
        }
        
        /**
         * Make connector.
         */
        export function makeConnector() {
            Connector[HEADER_PRF + LEFTMOST] = BODY_PRF + LEFTMOST;
            Connector[HEADER_PRF + MIDDLE] = BODY_PRF + MIDDLE;
            Connector[HEADER_PRF + DETAIL] = BODY_PRF + DETAIL;
            Connector[HEADER_PRF + VERTICAL_SUM] = BODY_PRF + VERTICAL_SUM;
            Connector[HEADER_PRF + HORIZONTAL_SUM] = BODY_PRF + HORIZONTAL_SUM;
        }
        
        /**
         * Get class of header.
         */
        export function getClassOfHeader($part: JQuery) {
            return $part.data(internal.EX_PART);
        }
        
        /**
         * Is paste key.
         */
        export function isPasteKey(evt: any) {
            return evt.keyCode === 86;
        }
        
        /**
         * Is copy key.
         */
        export function isCopyKey(evt: any) {
            return evt.keyCode === 67;
        }
        
        /**
         * Is cut key.
         */
        export function isCutKey(evt: any) {
            return evt.keyCode === 88;
        }
        
        /**
         * Is undo key.
         */
        export function isUndoKey(evt: any) {
            return evt.keyCode === 90;
        }
        
        /**
         * Get cell coord.
         */
        export function getCellCoord($cell: JQuery) {
            if ($cell.length === 0) return;
            let $td = $cell;
            if ($cell.is("div")) {
                $td = $cell.closest("td");
            }
            let view = $td.data(internal.VIEW);
            if (!view) return;
            let coord = view.split("-");
            if (util.isNullOrUndefined(coord[0]) || util.isNullOrUndefined(coord[1])) return;
            return {
                rowIdx: Number(coord[0]),
                columnKey: coord[1]
            };
        }
        
        /**
         * Get display column index.
         */
        export function getDisplayColumnIndex($grid: JQuery, key: any) {
            let generator = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            let visibleColumns = generator.painter.visibleColumns;
            let index;
            _.forEach(visibleColumns, function(c: any, i: number) {
                if (c.key === key) {
                    index = i;
                    return false;
                }
            });
            return index;
        }
        
        /**
         * Get row index.
         */
        export function getRowIndex($grid: JQuery, rowId: any) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            let start = gen.startIndex || 0;
            let end = gen.endIndex || gen.dataSource.length - 1;
            for (let i = start; i <= end; i++) {
                if (gen.dataSource[i][gen.primaryKey] === rowId) {
                    return i;
                }
            }
            return -1;
        }
        
        /**
         * Grid visible columns.
         */
        export function gridVisibleColumns($grid: JQuery) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            return gen.painter.visibleColumns;
        }
        
        /**
         * Grid columns map.
         */
        export function gridColumnsMap($grid: JQuery) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            return gen.painter.columnsMap;
        }
        
        /**
         * Mark cell.
         */
        export function markCellWith(clazz: any, $cell: JQuery, nth?: any, value?: any) {
            let $childCells = $cell.find("." + render.CHILD_CELL_CLS);
            if ($cell.is("td") && $childCells.length > 0) {
                if (!util.isNullOrUndefined(nth) && nth !== -1) {
                    $($childCells[nth]).addClass(clazz);
                    if (clazz === errors.ERROR_CLS) $($childCells[nth]).text(value);
                } else {
                    $childCells.addClass(clazz);
                }
                return;
            }
            $cell.addClass(clazz);
            if (clazz === errors.ERROR_CLS) $cell.text(value);
        }
        
        /**
         * Strip cell.
         */
        export function stripCellWith(clazz: any, $cell: JQuery, nth?: any) {
            let $childCells = $cell.find("." + render.CHILD_CELL_CLS);
            if ($cell.is("td") && $childCells.length > 0) {
                if (!util.isNullOrUndefined(nth) && nth !== -1) {
                    $($childCells[nth]).removeClass(clazz);
                } else $childCells.removeClass(clazz);
                return;
            }
            $cell.removeClass(clazz);
        }
        
        /**
         * Mark cells.
         */
        export function markCellsWith(clazz: any, $cells: JQuery) {
            $cells.each(function() {
                markCellWith(clazz, $(this));
            });
        }
        
        /**
         * Strip cells.
         */
        export function stripCellsWith(clazz: any, $cells: JQuery) {
            $cells.each(function() {
                stripCellWith(clazz, $(this));
            });
        }
        
        /**
         * Is detable.
         */
        export function isDetable($cell: JQuery) {
            let children = $cell.children("." + render.CHILD_CELL_CLS);
            return !($cell.is("." + style.HIDDEN_CLS) || $cell.is("." + style.SEAL_CLS)
                    || (children.length > 0 
                        && ($(children[0]).is("." + style.HIDDEN_CLS) 
                            || $(children[0]).is("." + style.SEAL_CLS))));
        }
        
        /**
         * Column index.
         */
        export function indexOf(columnKey: any, visibleColumns: any) {
            let index = -1;
            _.forEach(visibleColumns, function(column: any, i: number) {
                if (column.key === columnKey) {
                    index = i;
                    return false;
                }
            });
            return index;
        }
        
        /**
         * Next column key.
         */
        export function nextKeyOf(columnIndex: number, visibleColumns: any) {
            if (columnIndex >= visibleColumns.length - 1) return;
            return visibleColumns[columnIndex + 1].key;
        }
        
        /**
         * Next cell.
         */
        export function nextCellOf($grid: JQuery, cell: any) {
            let key, rowIndex, innerIdx;
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            let visibleColumns = gen.painter.visibleColumns;
            key = nextKeyOf(indexOf(cell.columnKey, visibleColumns), visibleColumns);
            if (key) {
                return new selection.Cell(cell.rowIndex, key, undefined, cell.innerIdx); 
            }
            key = visibleColumns[0].key;
            if (cell.rowIndex >= gen.dataSource.length - 1) {
                if (cell.innerIdx === -1) {
                    rowIndex = 0;
                    innerIdx = -1;
                } else if (cell.innerIdx === 0) {
                    rowIndex = Number(cell.rowIndex);
                    innerIdx = 1;
                } else if (cell.innerIdx === 1) {
                    rowIndex = 0;
                    innerIdx = 0;
                }
            } else {
                if (cell.innerIdx === -1) {
                    rowIndex = Number(cell.rowIndex) + 1;
                    innerIdx = -1;
                } else if (cell.innerIdx === 0) {
                    rowIndex = Number(cell.rowIndex);
                    innerIdx = 1;
                } else if (cell.innerIdx === 1) {
                    rowIndex = Number(cell.rowIndex) + 1;
                    innerIdx = 0;
                }
            }
            return new selection.Cell(rowIndex, key, undefined, innerIdx);
        }
        
        /**
         * Call.
         */
        export function call(fn: any, ...args: any[]) {
            return function() {
                return fn.apply(null, args);
            };
        }
        
        /**
         * Check br.
         */
        export function containsBr(text: string) {
            return text && text.indexOf("<br/>") > -1;
        }
        
        /**
         * Compare cells.
         */
        export function areSameCells(one: any, other: any) {
            if (parseInt(one.rowIndex) !== parseInt(other.rowIndex)
                || one.columnKey !== other.columnKey
                || one.innerIdx !== other.innerIdx) return false;
            return true;
        }
        
        /**
         * Is det cell.
         */
        export function isDetCell($grid: JQuery, rowIdx: any, key: any) {
            let $cell = selection.cellAt($grid, rowIdx, key);
            let $childCells = $cell.children("." + render.CHILD_CELL_CLS);
            return ($childCells.length === 0 && $cell.is("." + style.DET_CLS))
                    || ($childCells.length > 0 && $($childCells[0]).is("." + style.DET_CLS));
        }
        
        /**
         * Is xcell.
         */
        export function isXCell($grid: JQuery, rowId: any, key: any, ...clazz: any[]) {
            let cellsStyle = $grid.data(internal.CELLS_STYLE);
            if (!cellsStyle) return;
            let result = _.find(cellsStyle, function(deco) {
                return deco.columnKey === key && deco.rowId === rowId && clazz.some(c => deco.clazz === c); 
            });
            return result !== undefined;
        }
        
        /**
         * Is xcell shown.
         */
        export function isXCellShown($grid: JQuery, rowIdx: any, key: any, ...clazz: any[]) {
            let $cell = selection.cellAt($grid, rowIdx, key);
            let $childCells = $cell.children("." + render.CHILD_CELL_CLS);
            let returnVal = false;
            _.forEach(clazz, function(c) {
                if (($childCells.length === 0 && $cell.is("." + c))
                        || ($childCells.length > 0 && $($childCells[0]).is("." + c))) {
                    returnVal = true;
                    return false;
                }
            });
            return returnVal;
        }
        
        /**
         * Is empty.
         */
        export function isEmpty(obj: any) {
            if (obj && obj.constructor === Array) {
                let empty = true;
                _.forEach(obj, function(o) {
                    if (!util.isNullOrUndefined(o)) {
                        empty = false;
                        return false;
                    }
                });
                return empty;
            }
            
            if (!obj) return true;
            return false;
        }
        
        /**
         * Values array.
         */
        export function valuesArray(obj: any) {
            let values = [];
            _.forEach(Object.keys(obj), function(k, i) {
                values = _.concat(values, obj[k]);
            });
            return values;
        }
        
        /**
         * String value.
         */
        export function stringValue(val: any) {
            return _.isObject(val) ? JSON.stringify(val) : val;
        }
        
        /**
         * Get cell data.
         */
        export function getCellData(data: any) {
            try {
                return JSON.parse(data);
            } catch (e) {
                return data;
            }
        }
        
        /**
         * View data.
         */
        export function viewData(view: any, viewMode: any, obj: any) {
            if (!view || !viewMode) return;
            let result = [];
            _.forEach(view(viewMode), function(f) {
                if (!f) return;
                result.push(obj[f]);
            });
            return result.length === 1 ? result[0] : result;
        }
        
        /**
         * Is equal.
         */
        export function isEqual(one: any, two: any, fields?: Array<any>) {
            if (_.isObject(one) && _.isObject(two)) {
                return (fields && fields.length > 0) 
                        ? _.isEqual(_.omitBy(one, (d, p) => fields.every(f => f !== p)),
                                    _.omitBy(two, (d, p) => fields.every(f => f !== p)))
                        : _.isEqual(_.omit(one, _.isFunction), _.omit(two, _.isFunction));
            }
            return _.isEqual(one, two);
        }
        
        /**
         * Block.
         */
        export function block($exTable: JQuery) {
            (<any> $exTable).block({
                message: null,
                fadeIn: 200,
                css: {
                    width: $exTable.width(),
                    height: $exTable.height()
                }
            });
        }
        
        /**
         * Unblock.
         */
        export function unblock($exTable: JQuery) {
            (<any> $exTable).unblock();
        }
        
        /**
         * Highlight column.
         */
        export function highlightColumn($container: JQuery, columnIndex: any) {
            let grid = $container[0].querySelector("." + BODY_PRF + DETAIL);
            let header = $container[0].querySelector("." + HEADER_PRF + DETAIL);
            _.forEach(grid.getElementsByTagName("tr"), function(t) {
                let tds = t.getElementsByTagName("td");
                if (!tds || tds.length === 0) return;
                helper.addClass1n(tds[columnIndex], render.HIGHLIGHT_CLS);
            });
            _.forEach(header.getElementsByTagName("tr"), function(t) {
                let tds = t.getElementsByTagName("td");
                if (!tds || tds.length === 0) return;
                helper.addClass1n(tds[columnIndex], render.HIGHLIGHT_CLS);
            });
        }
        
        /**
         * Unhighlight column.
         */
        export function unHighlightColumn($container: JQuery, columnIndex: any) {
            let grid = $container[0].querySelector("." + BODY_PRF + DETAIL);
            let header = $container[0].querySelector("." + HEADER_PRF + DETAIL);
            unHighlightGrid(grid, columnIndex);
            unHighlightGrid(header, columnIndex);
        }
        
        /**
         * Unhighlight grid.
         */
        export function unHighlightGrid(grid: Element, columnIndex: any) {
            if (!grid) return;
            _.forEach(grid.getElementsByTagName("tr"), function(t) {
                let tds = t.getElementsByTagName("td");
                if (!tds || tds.length === 0) return;
                helper.removeClass1n(tds[columnIndex], render.HIGHLIGHT_CLS);
            });
        }
        
        /**
         * First sibling.
         */
        export function firstSibling(node: any, clazz: any) {
            let parent = node.parentElement;
            if (!parent) return;
            let children = parent.children;
            for (let i = 0; i < children.length; i++) {
                if (node !== children[i] && children[i].classList.contains(clazz)) {
                    return children[i];
                }
            }
        }
        
        /**
         * Class siblings.
         */
        export function classSiblings(node: any, partialClass: any) {
            let parent = node.parentElement;
            if (!parent) return;
            let children = parent.children;
            let results = [];
            for (let i = 0; i < children.length; i++) {
                if (children[i] === node) continue;
                let classList = children[i].classList;
                for (let j = 0; j < classList.length; j++) {
                    if (classList.item(j).indexOf(partialClass) >= 0) {
                        results.push(children[i]);
                    }
                }
            }
            return results;
        }
        
        /**
         * Consume siblings.
         */
        export function consumeSiblings(node: any, op: any) {
            let parent = node.parentElement;
            if (!parent) return;
            let children = parent.children;
            for (let i = 0; i < children.length; i++) {
                if (node !== children[i]) {
                    op(children[i]);
                }
            }
        }
        
        /**
         * Closest.
         */
        export function closest(el, selector) {
            let matches;
            ['matches', 'webkitMatchesSelector', 'mozMatchesSelector', 'msMatchesSelector', 'oMatchesSelector'].some(function(fn) {
                if (typeof document.body[fn] === 'function') {
                    matches = fn;
                    return true;
                }
                return false;
            });
        
            let parent;
            while (el) {
                parent = el.parentElement;
                if (parent && parent[matches](selector)) {
                    return parent;
                }
                el = parent;
            }
        }
        
        /**
         * Add class.
         */
        export function addClass1n(node, clazz) {
            if (node && node.constructor !== HTMLCollection) {
                let children = node.querySelectorAll("." + render.CHILD_CELL_CLS); 
                if (children.length > 0) addClass(children, clazz);
                else addClass(node, clazz);
                return;
            }
            for (let i = 0; i < node.length; i++) {
                let children = node[i].querySelectorAll("." + render.CHILD_CELL_CLS);
                if (children.length > 0) addClass(children, clazz);
                else addClass(node[i], clazz);
            }
        }
        
        /**
         * Remove class.
         */
        export function removeClass1n(node, clazz) {
            if (node && node.constructor !== HTMLCollection) {
                let children = node.querySelectorAll("." + render.CHILD_CELL_CLS);
                if (children.length > 0) removeClass(children, clazz);
                else removeClass(node, clazz);
                return;
            }
            for (let i = 0; i < node.length; i++) {
                let children = node[i].querySelectorAll("." + render.CHILD_CELL_CLS);
                if (children.length > 0) removeClass(children, clazz);
                else removeClass(node[i], clazz);
            }
        }
        
        /**
         * Add class.
         */
        export function addClass(node, clazz) {
            if (node && node.constructor !== HTMLCollection && node.constructor !== NodeList) {
                node.classList.add(clazz);
                return;
            }
            for (let i = 0; i < node.length; i++) {
                if (!node[i].classList.contains(clazz)) {
                    node[i].classList.add(clazz);
                }
            }
        }
        
        /**
         * Remove class.
         */
        export function removeClass(node, clazz) {
            if (node && node.constructor !== HTMLCollection && node.constructor !== NodeList) {
                node.classList.remove(clazz);
                return;
            }
            for (let i = 0; i < node.length; i++) {
                if (node[i].classList.contains(clazz)) {
                    node[i].classList.remove(clazz);
                }
            }
        }
        
        /**
         * Has class.
         */
        export function hasClass(node, clazz) {
            return node.classList.contains(clazz);
        }
        
        /**
         * Index.
         */
        export function indexInParent(node) {
            let parent = node.parentElement;
            if (!parent) return;
            let children = parent.children;
            let index = 0;
            for (let i = 0; i < children.length; i++) {
                 if (children[i] === node) return index;
                 if (children[i].nodeType === 1) index++;
            }
            return -1;
        }
    }
    
    module widget {
        export let MENU = "menu";
        export let POPUP = "popup";
        export let MENU_CLS = "x-context-menu";
        export let POPUP_CLS = "x-popup-panel";
        export let PARTITION_CLS = "partition";
        export let MENU_ITEM_CLS = "menu-item";
        export let MENU_ICON_CLS = "menu-icon";
        export let DISABLED_CLS = "disabled"; 
        
        /**
         * Widget.
         */
        abstract class XWidget {
            $table: JQuery;
            $selector: JQuery;
            constructor($selector: JQuery) {
                this.$selector = $selector;
            }
            
            /**
             * Initialize. 
             */
            abstract initialize();
            
            /**
             * Get table.
             */
            getTable() {
                this.$table = this.$selector.closest("table");
            }
        }
        
        /**
         * Tooltip.
         */
        export class Tooltip extends XWidget {
            options: any;
            defaultOpts: any;
            constructor($selector: JQuery, options: any) {
                super($selector);
                this.options = options;
                this.defaultOpts = { 
                    showRight: true,
                    width: "100px"
                };
                this.initialize();
            }
            
            /**
             * Initialize.
             */
            initialize() {
                let self = this;
                $.extend(true, self.options, self.defaultOpts);
                self.$selector.on(events.MOUSE_OVER, function(evt) {
                    self.getTable();
                    if (self.$table.length === 0) return;
                    let $t2 = self.$table.data(internal.TOOLTIP);
                    if (!$t2) {
                        $t2 = $("<div/>").addClass(cssClass(self.options));
                        $t2.appendTo("body");
                        self.$table.data(internal.TOOLTIP, $t2);
                    }
                    $t2.empty().append(self.options.sources).css({ visibility: "visible" })
                        .position({ my: "left top", at: "left+" + self.$selector.outerWidth() + " top+5", of: self.$selector });
                });
                self.$selector.on(events.MOUSE_OUT, function(evt) {
                    self.getTable();
                    if (self.$table.length === 0) return;
                    let $t2 = self.$table.data(internal.TOOLTIP);
                    if (!$t2 || $t2.css("display") === "none") return;
                    $t2.css({ visibility: "hidden" });
                });
            }
        }
        
        /**
         * Popup.
         */
        abstract class Popup extends XWidget {
            position: any;
            constructor($selector: JQuery) {
                super($selector);
                this.initialize();
            }
            
            /**
             * Initialize.
             */
            initialize() {
                let self = this;
                self.$selector.on(events.MOUSE_DOWN, function(evt: any) {
                    self.getTable();
                    if (evt.ctrlKey && self.$table.closest("." + NAMESPACE).data(NAMESPACE).determination) return;
                    self.click(evt);
                });
            }
            
            /**
             * Click.
             */
            abstract click(evt: any);
        } 
        
        /**
         * Context menu.
         */
        export class ContextMenu extends Popup {
            items: Array<MenuItem>;
            constructor($selector: JQuery, items: Array<MenuItem>) {
                super($selector);
                this.items = items;         
            }
            
            /**
             * Click.
             */
            click(evt: any) {
                let self = this;
                let $menu = self.$table.data(internal.CONTEXT_MENU);
                if (!$menu) {
                    $menu = $("<ul/>").addClass(MENU_CLS).appendTo("body").hide();
                    _.forEach(self.items, function(item: MenuItem) {
                        self.createItem($menu, item);
                    });
                    self.$table.data(internal.CONTEXT_MENU, $menu);
                }
                if ($menu.css("display") === "none") {
                    let pos = eventPageOffset(evt, false);
                    $menu.show().css({ top: pos.pageY, left: pos.pageX });
                } else {
                    $menu.hide();
                }
                let $pu = self.$table.data(internal.POPUP);
                if ($pu && $pu.css("display") !== "none") {
                    $pu.hide();
                }
                update.outsideClick(self.$table.closest("." + NAMESPACE), self.$selector);
                evt.stopPropagation();
                hideIfOutside($menu); 
            }
            
            /**
             * Create item.
             */
            createItem($menu: JQuery, item: any) {
                if (item.id === PARTITION_CLS) {
                    $("<li/>").addClass(MENU_ITEM_CLS + " " + PARTITION_CLS).appendTo($menu);
                    return;
                }
                let $li = $("<li/>").addClass(MENU_ITEM_CLS).text(item.text)
                    .on(events.CLICK_EVT, function(evt: any) {
                        if (item.disabled) return;
                        item.selectHandler(item.id);
                        $menu.hide();
                }).appendTo($menu);
                if (item.disabled) {
                    $li.addClass(DISABLED_CLS);
                }
                if (item.icon) {
                    $li.append($("<span/>").addClass(MENU_ICON_CLS + " " + item.icon));
                }
            }
        }
        
        /**
         * Menu item.
         */
        export class MenuItem {
            id: string;
            text: string;
            selectHandler: (id: any) => void;
            disabled: boolean;
            icon: any;
            constructor(text: string, selectHandler: (ui: any) => void, disabled: boolean, icon: any) {
                this.text = text;
                this.selectHandler = selectHandler ? selectHandler : $.noop();
                this.disabled = disabled;
                this.icon = icon;
            }
        }
        
        /**
         * Popup panel.
         */
        export class PopupPanel extends Popup {
            $panel: JQuery
            constructor($selector: JQuery, $panel: JQuery, position?: any) {
                super($selector);
                this.$panel = $panel;
                this.position = position;
            }
            
            /**
             * Click.
             */
            click(evt: any) {
                let self = this;
                let $pu = self.$table.data(internal.POPUP);
                if (!$pu) {
                    $pu = self.$panel.addClass(POPUP_CLS).hide();
                    self.$table.data(internal.POPUP, $pu);
                }
                if ($pu.css("display") === "none") {
                    let pos = eventPageOffset(evt, false);
                    $pu.show().css(self.getPosition($pu, pos, self.position || "top left"));
                    events.trigger(self.$table, events.POPUP_SHOWN, $(evt.target));
                    self.addListener($pu, $(evt.target));
                } else {
                    $pu.hide();
                }
                let $menu = self.$table.data(internal.CONTEXT_MENU);
                if ($menu && $menu.css("display") !== "none") {
                    $menu.hide();
                }
                evt.stopPropagation();
                hideIfOutside($pu);
            }
            
            /**
             * Add listener.
             */
            addListener($pu: JQuery, $t: JQuery) {
                let self = this;
                $pu.off(events.POPUP_INPUT_END);
                $pu.on(events.POPUP_INPUT_END, function(evt: any, ui: any) {
                    let $header = self.$selector.closest("table").parent();
                    if ($header.hasClass(HEADER)) {
                        let ds = helper.getDataSource($header);
                        if (!ds || ds.length === 0) return;
                        let coord = helper.getCellCoord($t);
                        ds[coord.rowIdx][coord.columnKey] = ui.value;
                        $t.text(ui.value);
                        $pu.hide();
                    }
                });
            }
            
            /**
             * Get position.
             */
            getPosition($pu: JQuery, pos: any, my: any) {
                if (my === "top left") {
                    return { top: pos.pageY - $pu.outerHeight(), left: pos.pageX - $pu.outerWidth() };
                } else if (my === "bottom left") {
                    return { top: pos.pageY, left: pos.pageX - $pu.outerWidth() };
                } else if (my === "top right") {
                    return { top: pos.pageY - $pu.outerHeight(), left: pos.pageX };
                } else if (my === "bottom right") {
                    return { top: pos.pageY, left: pos.pageX };
                }
            }
        }
        
        /**
         * Bind.
         */
        export function bind($row: JQuery, rowIdx: any, headerPopupFt: any) {
            let wType;
            if (!headerPopupFt) return;
            _.forEach(headerPopupFt.menu.rows, function(rId: any) {
                if (rId === rowIdx) {
                    new ContextMenu($row, headerPopupFt.menu.items);
                    wType = MENU;
                    return false;
                }
            });
            if (wType) return;
            _.forEach(headerPopupFt.popup.rows, function(rId: any) {
                if (rId === rowIdx) {
                    new PopupPanel($row, headerPopupFt.popup.provider());
                    wType = POPUP;
                    return false;
                }
            });
            return wType;
        }
        
        /**
         * Hide.
         */
        function hideIfOutside($w: JQuery) {
            $(document).on(events.MOUSE_DOWN, function(evt) {
                if (outsideOf($w, evt.target)) {
                    $w.hide();
                }
            });
            
            var outsideOf = function($container, target) {
                return !$container.is(target) && $container.has(target).length === 0;
            };
        }
        
        /**
         * Offset.
         */
        function eventPageOffset(evt, isFixed) {
            var scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
            var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
            return isFixed ? { pageX: evt.pageX - scrollLeft, pageY: evt.pageY - scrollTop } 
                           : { pageX: evt.pageX, pageY: evt.pageY };
        }
        
        /**
         * Class.
         */
        function cssClass(options) {
            var css = options.showBelow ? 'bottom' : 'top';
            css += '-';
            css += (options.showRight ? 'right' : 'left');
            css += '-tooltip';
            return css;
        }
    }
}