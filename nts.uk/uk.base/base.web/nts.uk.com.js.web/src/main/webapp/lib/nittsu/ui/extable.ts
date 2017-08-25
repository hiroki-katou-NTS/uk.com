/// <reference path="../reference.ts"/>

module nts.uk.ui.exTable {
     
    let NAMESPACE = "extable";
    let DISTANCE: number = 3;
    let SPACE: number = 30;
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
        modifications: Array<any>;
        
        constructor($container: JQuery, options: any) {
            this.$container = $container;
            this.$container.css("position", "relative");
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
            this.$container.data(internal.X_OCCUPY, this.windowXOccupation);
            this.$container.data(internal.Y_OCCUPY, this.windowYOccupation);
            helper.makeConnector();
        }
        setUpdateMode(updateMode: any) {
            this.updateMode = updateMode;
            this.detailContent.updateMode = updateMode;
        }
        LeftmostHeader(leftmostHeader: any) {
            this.leftmostHeader = _.cloneDeep(leftmostHeader);
            this.setHeaderClass(this.leftmostHeader, LEFTMOST);
            return this;
        }
        LeftmostContent(leftmostContent: any) {
            this.leftmostContent = _.cloneDeep(leftmostContent);
            this.setBodyClass(this.leftmostContent, LEFTMOST);
            return this;
        }
        MiddleHeader(middleHeader: any) {
            this.middleHeader = _.cloneDeep(middleHeader);
            this.setHeaderClass(this.middleHeader, MIDDLE);
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
            self.generalSettings(headerWrappers, bodyWrappers);
        }
        
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
            }
            scroll.syncHorizontalScroll($leftSumHeaderWrapper, $leftSumContentWrapper);
            scroll.syncDoubDirHorizontalScrolls([ $detailHeader, $detailContent, $sumHeaderWrapper, $sumContentWrapper ]);
            scroll.syncDoubDirVerticalScrolls([ $leftSumContentWrapper, $sumContentWrapper ]);
        }
        
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
            }
            if (self.areaResize) {
                new resize.AreaAdjuster(self.$container, headerWrappers, bodyWrappers).handle();
                self.$container.on(events.AREA_RESIZE_END, $.proxy(resize.onAreaComplete, self));
            }
            storage.area.init(self.$container, headerWrappers);
            storage.tableHeight.init(self.$container);
            
            // Edit done
            update.editDone(self.$container);
            update.outsideClick(self.$container);
            events.onModify(self.$container);
            selection.checkUp(self.$container);
            copy.on(self.$container.find("." + BODY_PRF + DETAIL), self.updateMode);
        }

        satisfyPrebuild() {
            if (util.isNullOrUndefined(this.$container) || util.isNullOrUndefined(this.headerHeight) 
                || util.isNullOrUndefined(this.bodyHeight) || util.isNullOrUndefined(this.bodyRowHeight)
                || util.isNullOrUndefined(this.horzSumBodyRowHeight)) 
                return false;
            return true;
        }
        
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
        
        export function begin($container: JQuery, dataSource: Array<any>, options: any) {
            if (options.float) {
                let cloud: intan.Cloud = new intan.Cloud($container, dataSource, options);
                $container.data(internal.TANGI, cloud);
                cloud.renderRows(true);   
                return;
            }
            normal($container, dataSource, options);
        }
        
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
        
        function peelStruct(columns: Array<any>, level: any, currentLevel: number) {
            _.forEach(columns, function(col: any) {
                let clonedCol = _.clone(col);
                if (!util.isNullOrUndefined(col.group)) {
                    clonedCol.colspan = col.group.length;
                    peelStruct(col.group, level, currentLevel + 1);
                }
                if (util.isNullOrUndefined(level[currentLevel])) {
                    level[currentLevel] = [];
                }
                level[currentLevel].push(clonedCol);
            });
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
            
            cell(rData: any, rowIdx: number, key: string): JQuery {
                let self = this;
                let cData = rData[key]; 
                let data = cData && _.isObject(cData) && cData.constructor !== Array && _.isFunction(self.options.view) ? 
                            self.options.view(self.options.viewMode, cData) : cData;
                let column: any = self.columnsMap[key];
                if (util.isNullOrUndefined(column)) return;
                let $td = $("<td/>").data(internal.VIEW, rowIdx + "-" + key)
                            .css({ borderWidth: "1px", overflow: "hidden", whiteSpace: "nowrap", position: "relative" });
                self.highlight($td);
                
                if (!self.visibleColumnsMap[key]) $td.hide();
                if (!util.isNullOrUndefined(data) && data.constructor === Array) {
                    let incellHeight = parseInt(self.options.rowHeight) / 2 - 3;
                    let borderStyle = "solid 1px transparent";
                   _.forEach(data, function(item: any, idx: number) {
                       let $div = $("<div/>").addClass(CHILD_CELL_CLS).text(item);
                       if (idx < data.length - 1) {
                           $div.css({ borderTop: borderStyle, borderLeft: borderStyle, 
                                      borderRight: borderStyle, borderBottom: "dashed 1px #ccc", top: "0px" });
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
                if (!util.isNullOrUndefined(column.handlerType)) {
                    let handler = cellHandler.get(column.handlerType);
                    if (!util.isNullOrUndefined(handler)) {
                        handler($td, self.options, helper.call(column.supplier, rData, rowIdx, key));
                    }
                }
                if (self.options.isHeader && helper.containsBr(data)) $td.html(data); 
                else if (self.options.isHeader || !column.control) $td.text(data);
                if (!self.options.isHeader) {
                    if (!util.isNullOrUndefined(column.icon)) {
                        let $icon = $("<span/>").addClass(COL_ICON_CLS + " " + column.icon);
                        $icon.appendTo($td.css({ paddingLeft: column.iconWidth }));
                    }
                    controls.check($td, column, data, helper.call(column.handler, rData, rowIdx, key));
                    cellHandler.rClick($td, column, helper.call(column.rightClick, rData, rowIdx, key));
                }
                spread.bindSticker($td, rowIdx, key, self.options);
                style.detCell(self.$container, $td, rowIdx, key);
                return $td;
            }
            
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
                _.forEach(Object.keys(data), function(key: any, index: number) {
                    if (!self.visibleColumnsMap[key] && !self.hiddenColumnsMap[key]) return;
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
                                    $($childCells[colorDef.innerIdx]).addClass(colorDef.clazz);
                                } else {
                                    $cell.addClass(colorDef.clazz);
                                }
                                return false;
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
            
            highlight($td: JQuery) {
                let self = this;
                if (self.options.isHeader || self.options.containerClass !== BODY_PRF + DETAIL) return;
                $td.on(events.MOUSE_OVER, function() {
                    let colIndex = $td.index();
                    let $tr = $td.closest("tr"); 
                    let rowIndex = $tr.index();
                    $tr.find("td").addClass(HIGHLIGHT_CLS);
                    let $targetContainer = $td.closest("." + self.options.containerClass);
                    let $targetHeader = $targetContainer.siblings("." + self.options.containerClass.replace(BODY_PRF, HEADER_PRF));
                    let $horzSumHeader = $targetContainer.siblings("." + HEADER_PRF + HORIZONTAL_SUM);
                    let $horzSumContent = $targetContainer.siblings("." + BODY_PRF + HORIZONTAL_SUM);
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
                    if ($horzSumHeader.css("display") !== "none") {
                        $horzSumHeader.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").addClass(HIGHLIGHT_CLS);
                        });
                        $horzSumContent.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").addClass(HIGHLIGHT_CLS);
                        });
                    }
                });
                
                $td.on(events.MOUSE_OUT, function() {
                    $td.removeClass(HIGHLIGHT_CLS);
                    let colIndex = $td.index();
                    let $tr = $td.closest("tr");
                    $tr.find("td").removeClass(HIGHLIGHT_CLS);
                    let rowIndex = $tr.index();
                    let $targetContainer = $td.closest("." + self.options.containerClass);
                    let $targetHeader = $targetContainer.siblings("." + self.options.containerClass.replace(BODY_PRF, HEADER_PRF));
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
                    if ($horzSumHeader.css("display") !== "none") {
                        $horzSumHeader.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").removeClass(HIGHLIGHT_CLS);
                        });
                        $horzSumContent.find("tr").each(function() {
                            $(this).find("td:eq(" + colIndex + ")").removeClass(HIGHLIGHT_CLS);
                        });
                    }
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
            
            cell(text: any, rowIdx: number, cell: any): JQuery {
                let self = this;
                let $td = $("<td/>").data(internal.VIEW, rowIdx + "-" + cell.key)
                            .css({ "border-width": "1px", "overflow": "hidden", "white-space": "nowrap", "border-collapse": "collapse" });
                if (!util.isNullOrUndefined(cell.rowspan) && cell.rowspan > 1) $td.attr("rowspan", cell.rowspan);
                if (!util.isNullOrUndefined(cell.colspan) && cell.colspan > 1) $td.attr("colspan", cell.colspan);
                else if (!self.visibleColumnsMap[cell.key]) $td.hide();
                return helper.containsBr(text) ? $td.html(text) : $td.text(text);
            }
            
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
        
        export function extra(className: string, height: number) {
            return $("<tr/>").addClass("extable-" + className).height(height);
        }
        
        export function wrapperStyles(top: string, left: string, width: string, height: string) {
            return { 
                position: "absolute",
                overflow: "hidden",
                top: top,
                left: left,
                width: width,
                height: height,
                border: "solid 1px #ccc"
            };   
        }
        
        export function createWrapper(top: string, left: string, options: any) {
            return $("<div/>").data(internal.EX_PART, options.containerClass).addClass(options.containerClass)
                                .css(wrapperStyles(top, left, options.width, options.height));
        }
        
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
            if (_.isFunction(viewFn)) {
                value = viewFn(viewMode, valueObj);
            }
            let $childCells = $cell.find("." + CHILD_CELL_CLS);
            if ($childCells.length > 0) {
                if (value.constructor === Array) {
                    _.forEach(value, function(val: any, i: number) {
                        let $c = $($childCells[i]);
                        $c.text(val);
                        trace(origDs, $c, rowIdx, columnKey, i, valueObj);
                        if (updateMode === EDIT) {
                            validation.validate($exTable, $grid, $c, rowIdx, columnKey, i, val);
                        }
                    });
                } else {
                    let $c = $($childCells[innerIdx]);
                    $c.text(value);
                    trace(origDs, $c, rowIdx, columnKey, innerIdx, valueObj);
                    if (updateMode === EDIT) {
                        validation.validate($exTable, $grid, $c, rowIdx, columnKey, innerIdx, value);
                    }
                }
            } else {
                $cell.text(value);
                trace(origDs, $cell, rowIdx, columnKey, -1, valueObj);
                if (updateMode === EDIT) {
                    validation.validate($exTable, $grid, $cell, rowIdx, columnKey, -1, value);
                }
            }
        }
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
            _.forEach(Object.keys(data), function(key: any) {
                _.forEach(visibleColumns, function(col: any, index: number) {
                    if (col.key === key) {
                        let $target = $cells.eq(index);
                        let childCells = $target.find("." + CHILD_CELL_CLS);
                        if (childCells.length > 0) {
                            let cData = data[key];
                            if (_.isFunction(viewFn)) {
                                cData = viewFn(viewMode, data[key]);
                            }
                            if (cData.constructor === Array) {
                                _.forEach(cData, function(d, i) {
                                    let $c = $(childCells[i]);
                                    $c.text(d);
                                    if (updateMode === EDIT) {
                                        validation.validate($exTable, $grid, $c, rowIdx, key, i, d);
                                    }
                                    trace(origDs, $c, rowIdx, key, i, cData);
                                });
                                return false;
                            }
                            $(childCells[1]).text(data[key]);
                            trace(origDs, $(childCells[1]), rowIdx, key, 1, data[key]);
                            if (updateMode === EDIT) {
                                validation.validate($exTable, $grid, $(childCells[1]), rowIdx, key, 1, data[key]);
                            }
                        } else {
                            $target.text(viewFn(viewMode, data[key]));
                            trace(origDs, $target, rowIdx, key, -1, data[key]);
                            if (updateMode === EDIT) {
                                validation.validate($exTable, $grid, $target, rowIdx, key, -1, data[key]);
                            }
                        }
                        return false;
                    } 
                });
            });
        }
        function trace(ds: Array<any>, $cell: JQuery, rowIdx: any, key: any, innerIdx: any, value: any) {
            if (!ds || ds.length === 0) return;
            let oVal = ds[rowIdx][key];
            
            if (!util.isNullOrUndefined(oVal) && _.isEqual(oVal, value)) {
                $cell.removeClass(update.EDITED_CLS);
            } else {
                $cell.addClass(update.EDITED_CLS);
            }
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
                this.onScroll();
            }
            
            getClusterNo() {
                return Math.floor(this.$container.scrollTop() / (this.clusterHeight - this.blockHeight));
            }
            
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
                }
            }
            
            onScroll() {
                let self = this;
                self.$container.on(events.SCROLL_EVT, function() {
                    let inClusterNo = self.getClusterNo();
                    if (self.currentCluster !== inClusterNo) {
                        self.currentCluster = inClusterNo;
                        self.renderRows();
                    }
                });
            }
            
            editCellIn() {
                let self = this;
                let $exTable = self.$container.closest("." + NAMESPACE);
                let updateMode = $exTable.data(NAMESPACE).updateMode;;
                let editor = $exTable.data(update.EDITOR);
                if (updateMode !== EDIT || util.isNullOrUndefined(editor)) return;
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
                    update.edit($exTable, $childCells.length > 0 ? $($childCells[1]) : $editorCell, editor.value, true);
                }
            }
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
            dirtyCellsIn() {
                let self = this;
                let $exTable = self.$container.closest("." + NAMESPACE);
                let updateMode = $exTable.data(NAMESPACE).updateMode;
                let histories;
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
            errorCellsIn() {
                let self = this;
                let $exTable = self.$container.closest("." + NAMESPACE);
                let updateMode = $exTable.data(NAMESPACE).updateMode;
                let errs = self.$container.data(errors.ERRORS);
                if (!errs || errs.length === 0) return;
                self.each(errs, errors.ERROR_CLS);
            }
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
        export function get(handlerType: string) {
            switch (handlerType.toLowerCase()) {
                case "input": 
                    return cellInput;
                case "tooltip":
                    return tooltip;
            }
        }
        
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
                update.edit($exTable, $cell);
            });
        }
        
        export function tooltip($cell: any, options: any, supplier?: any) {
            let $content = supplier();
            if (util.isNullOrUndefined($content)) return;
            new widget.Tooltip($cell, { sources: $content });
        }
        
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
        
        export class Editor {
            $editor: JQuery;
            rowIdx: any;
            columnKey: any;
            innerIdx: any;
            value: any;
            constructor($editor: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any) {
                this.$editor = $editor;
                this.rowIdx = rowIdx;
                this.columnKey = columnKey;
                this.innerIdx = innerIdx;
                this.value = value;
            }
        }
        
        export function edit($exTable: JQuery, $cell: JQuery, value?: any, forced?: boolean) {
            let $grid = $exTable.find("." + BODY_PRF + DETAIL);
            if (!forced && errors.occurred($grid)) return;
            let editor = $exTable.data(EDITOR);
            let $editor, $input, inputVal, innerIdx = -1;
            let coord = helper.getCellCoord($cell);
            if (util.isNullOrUndefined(editor)) {
                let content = $cell.text();
                inputVal = value ? value : content;
                $input = $("<input/>").css({ border: "none", width: "100%", outline: "none", position: "relative", top: "25%" })
                            .val(inputVal);
                
                $editor = $("<div/>").addClass(EDITOR_CLS)
                        .css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4, backgroundColor: "#FFF",
                                border: "solid 1px #E67E22" }).append($input);
                if ($cell.is("div")) {
                    $editor.css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4 });
                    $input.css("top", "");
                    innerIdx = $cell.index();
                }
                $exTable.data(EDITOR, new Editor($editor, coord.rowIdx, coord.columnKey, innerIdx, inputVal));
                events.trigger($exTable, events.START_EDIT, [ $editor, content ]);
            } else {
                $editor = editor.$editor;
                if ($editor.css("display") === "none") $editor.show();
                $input = $editor.find("input");
                if ($cell.is("div")) {
                    $editor.css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4 });
                    $input.css("top", "");
                    innerIdx = $cell.index();
                } else {
                    $editor.css({ height: $cell.outerHeight() - 4, width: $cell.outerWidth() - 4 });
                    $input.css("top", "25%");
                }
                let content = $input.val();
                let $editingCell = $editor.closest("." + EDIT_CELL_CLS).removeClass(EDIT_CELL_CLS);
                let cellText = $cell.text();
                inputVal = value ? value : cellText;
                $input.val(inputVal);
                // Update editing cell coord
                editor.rowIdx = coord.rowIdx;
                editor.columnKey = coord.columnKey;
                editor.innerIdx = innerIdx;
                editor.value = inputVal;
                triggerStopEdit($exTable, $editingCell, content);
            }
            
            $cell.addClass(EDIT_CELL_CLS).empty();
            $cell.append($editor);
            editing($exTable, $editor);
            $input.select();
            validation.validate($exTable, $grid, $cell, coord.rowIdx, coord.columnKey, innerIdx, inputVal);
        }
        
        function editing($exTable: JQuery, $editor: JQuery) {
            let $input = $editor.find("input");
            $input.off(events.KEY_UP);
            $input.on(events.KEY_UP, function(evt: any) {
                let value = $input.val();
                if (evt.keyCode === $.ui.keyCode.ENTER) {
                    if (errors.occurred(helper.getMainTable($exTable))) return;
                    let $parent = $editor.parent();
                    $parent.removeClass(EDIT_CELL_CLS);
                    $exTable.data(EDITOR, null);
                    triggerStopEdit($exTable, $parent, value);
                } else {
                    let editor = $exTable.data(EDITOR);
                    if (util.isNullOrUndefined(editor)) return;
                    editor.value = value;
                    validation.validate($exTable, helper.getMainTable($exTable), editor.$editor.closest("." + EDIT_CELL_CLS), 
                                editor.rowIdx, editor.columnKey, editor.innerIdx, editor.value);
                }
            });
        }
        
        function triggerStopEdit($exTable: JQuery, $cell: JQuery, value: any) {
            if ($cell.length === 0) return;
            let innerIdx = -1;
            if ($cell.is("div")) {
                innerIdx = $cell.index();
            }
            let coord = helper.getCellCoord($cell);
            if (!coord) return;
            events.trigger($exTable, events.STOP_EDIT, _.concat(coord.rowIdx, coord.columnKey, innerIdx, value));
        }
        
        export function editDone($exTable: JQuery) {
            let $grid = $exTable.find("." + BODY_PRF + DETAIL);
            let fts = $exTable.data(NAMESPACE).detailContent.features;
            let timeRangeFt = feature.find(fts, feature.TIME_RANGE);
            let timeRangerDef;
            if (!util.isNullOrUndefined(timeRangeFt)) {
                timeRangerDef = _.groupBy(timeRangeFt.ranges, "rowId");
                $grid.data(internal.TIME_VALID_RANGE, timeRangerDef);
            }
            $exTable.on(events.STOP_EDIT, function(evt: any, rowIdx: any, columnKey: any, innerIdx: any, value: any) {
                postEdit($exTable, rowIdx, columnKey, innerIdx, value, timeRangerDef);
            });
        }
        
        function postEdit($exTable: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any, timeRangerDef?: any) {
            let $body = $exTable.find("." + BODY_PRF + DETAIL);
            let $cell = selection.cellAt($body, rowIdx, columnKey);
            let result = validation.validate($exTable, $body, $cell, rowIdx, columnKey, innerIdx, value, timeRangerDef);
            if (!result) return;
            value = validation.formatTime(value);
            
            let res = cellData($exTable, rowIdx, columnKey, innerIdx, value);
            if (!util.isNullOrUndefined(res)) {
                let newValObj = value;
                if (_.isObject(res)) {
                    let $main = helper.getMainTable($exTable);
                    let gen = $main.data(internal.TANGI) || $main.data(internal.CANON);
                    let upperInput = gen.painter.options.upperInput;
                    let lowerInput = gen.painter.options.lowerInput;
                    newValObj = _.cloneDeep(res);
                    if (innerIdx === 0) {
                        newValObj[upperInput] = value;
                    } else if (innerIdx === 1) {
                        newValObj[lowerInput] = value;
                    }
                }
                pushEditHistory($body, new selection.Cell(rowIdx, columnKey, res, innerIdx));
                helper.markCellWith(EDITED_CLS, $cell, innerIdx);
                events.trigger($exTable, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, newValObj, innerIdx) ]);
            }
            setText($cell, innerIdx, value);
        }
        
        export function setText($cell: JQuery, innerIdx: any, value: any) {
            let $childCells = $cell.find("." + render.CHILD_CELL_CLS);
            if (!util.isNullOrUndefined(innerIdx) && innerIdx > -1 && $childCells.length > 0) {
                $($childCells[innerIdx]).text(value)
            } else {
                $cell.text(value);
            }
        }
        
        export function outsideClick($exTable: JQuery) {
            $exTable.on(events.CLICK_EVT, function(evt: any) {
                if (!$(evt.target).is("." + update.EDITABLE_CLS)) {
                    if (errors.occurred(helper.getMainTable($exTable))) return;
                    let editor = $exTable.data(update.EDITOR);
                    if (util.isNullOrUndefined(editor)) return;
                    let innerIdx = -1;
                    let $parent = editor.$editor.closest("." + update.EDITABLE_CLS).removeClass(update.EDIT_CELL_CLS);
                    if ($parent.length === 0) return; 
                    if ($parent.is("div")) innerIdx = $parent.index();
                    let $input = editor.$editor.find("input");
                    let content = $input.val();
                    $parent.text(content);
                    postEdit($exTable, editor.rowIdx, editor.columnKey, innerIdx, content);
                    $exTable.data(update.EDITOR, null);
                }
            });
        }
        
        export function cellData($exTable: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any) {
            let exTable = $exTable.data(NAMESPACE);
            if (!exTable) return;
            let oldVal;
            if (util.isNullOrUndefined(innerIdx)) {
                innerIdx = exTable.detailContent.dataSource[rowIdx][columnKey].constructor === Array ? 1 : -1;     
            }
            let currentVal = exTable.detailContent.dataSource[rowIdx][columnKey];
            if (innerIdx === -1) {
                if (currentVal !== value) {
                    oldVal = _.cloneDeep(currentVal);
                    exTable.detailContent.dataSource[rowIdx][columnKey] = value;
                    return oldVal;
                } 
                return null;
            }
            let $main = helper.getMainTable($exTable);
            let gen = $main.data(internal.TANGI) || $main.data(internal.CANON);
            let upperInput = gen.painter.options.upperInput;
            let lowerInput = gen.painter.options.lowerInput;
            let field;
            if (innerIdx === 0) {
                field = upperInput;
            } else if (innerIdx === 1) {
                field = lowerInput;
            }
            if (currentVal[field] !== value) {
                oldVal = _.cloneDeep(currentVal);
                exTable.detailContent.dataSource[rowIdx][columnKey][field] = value;
                return oldVal;
            }
            return null;
        }
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
        export function gridCell($grid: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any, isRestore?: boolean) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            let cData = gen.dataSource[rowIdx][columnKey];
            if (cData.constructor === Array) {
                if (value.constructor === Array) {
                    _.forEach(cData, function(d: any, i: number) {
                        gen.dataSource[rowIdx][columnKey][i] = value[i];
                    });
                } else {
                    gen.dataSource[rowIdx][columnKey][innerIdx] = value;
                }
            } else {
                gen.dataSource[rowIdx][columnKey] = value;
            }
            render.gridCell($grid, rowIdx, columnKey, innerIdx, value, isRestore);
        }
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
        export function gridCellOw($grid: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any, txId: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            let cData = gen.dataSource[rowIdx][columnKey];
            if (!exTable.pasteOverWrite && !util.isNullOrEmpty(cData)) return;
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
            } else {
                changedData = cData;
                gen.dataSource[rowIdx][columnKey] = value;
            }
            render.gridCell($grid, rowIdx, columnKey, innerIdx, value);
            pushHistory($grid, [ new selection.Cell(rowIdx, columnKey, changedData) ], txId);
            events.trigger($exTable, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, value, innerIdx) ]);
        }
        export function gridRowOw($grid: JQuery, rowIdx: any, data: any, txId: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            // Create history
            let changedCells = [];
            _.assignInWith(gen.dataSource[rowIdx], data, function(objVal, srcVal, key, obj, src) {
                if (!util.isNullOrUndefined(src[key])) {
                    changedCells.push(new selection.Cell(rowIdx, key, objVal));
                }
                if (!exTable.pasteOverWrite && !util.isNullOrEmpty(objVal)) {
                    src[key] = objVal;
                    return objVal;
                }
                if (objVal.constructor === Array && srcVal.constructor !== Array) {
                    objVal[1] = srcVal;
                    return objVal;
                }
                return srcVal;
            });
            render.gridRow($grid, rowIdx, data);
            pushHistory($grid, changedCells, txId);
            events.trigger($exTable, events.ROW_UPDATED, [ events.createRowUi(rowIdx, data) ]);
        }
        
        export function stickGridCellOw($grid: JQuery, rowIdx: any, columnKey: any, innerIdx: any, value: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            let cData = gen.dataSource[rowIdx][columnKey];
            if (!exTable.stickOverWrite && !util.isNullOrEmpty(cData)) return;
            let changedData = _.cloneDeep(cData);
            gen.dataSource[rowIdx][columnKey] = value;
            render.gridCell($grid, rowIdx, columnKey, innerIdx, value);
            pushStickHistory($grid, [ new selection.Cell(rowIdx, columnKey, changedData) ]);
            events.trigger($exTable, events.CELL_UPDATED, [ new selection.Cell(rowIdx, columnKey, value, innerIdx) ]);
        }
        export function stickGridRowOw($grid: JQuery, rowIdx: any, data: any) {
            let $exTable = $grid.closest("." + NAMESPACE);
            let exTable = $exTable.data(NAMESPACE);
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            // Create history
            let changedCells = [];
            let origData = _.cloneDeep(data);
            let clonedData = _.cloneDeep(data);
            _.assignInWith(gen.dataSource[rowIdx], clonedData, function(objVal, srcVal, key, obj, src) {
                if (!util.isNullOrUndefined(src[key])) {
                    changedCells.push(new selection.Cell(rowIdx, key, _.cloneDeep(objVal)));
                }
                if (!exTable.stickOverWrite && !util.isNullOrEmpty(objVal)) {
                    src[key] = objVal;
                    return objVal;
                }
                if (objVal.constructor === Array && srcVal.constructor !== Array) {
                    objVal[1] = srcVal;
                    return objVal;
                }
                return srcVal;
            });
            _.forEach(Object.keys(clonedData), function(k: any) {
                if (!_.isEqual(data[k], origData[k])) {
                    delete origData[k];
                }
            });
            render.gridRow($grid, rowIdx, origData);
            pushStickHistory($grid, changedCells);
            events.trigger($exTable, events.ROW_UPDATED, [ events.createRowUi(rowIdx, origData) ]);
        }
        
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
        export function pushEditHistory($grid: JQuery, cell: any) {
            let history = $grid.data(internal.EDIT_HISTORY);
            if (!history || history.length === 0) {
                $grid.data(internal.EDIT_HISTORY, [ cell ]);
                return;
            }
            history.push(cell);   
        }
        export function pushStickHistory($grid: JQuery, cells: Array<any>) {
            let history = $grid.data(internal.STICK_HISTORY);
            if (!history || history.length === 0) {
                $grid.data(internal.STICK_HISTORY, [ cells ]);
                return;
            }
            history.push(cells);
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
            getOp(evt: any) {
                let self = this;
                if (evt.ctrlKey && helper.isCopyKey(evt)) {
                    self.copy();
                } else if (evt.ctrlKey && helper.isCutKey(evt)) {
                    self.cut();   
                } else if (evt.ctrlKey && helper.isUndoKey(evt)) {
                    self.undo();
                }
            }
            
            copy(cut?: boolean) {
                let selectedCells: Array<any> = selection.getSelectedCells(this.$grid);
                let copiedData;
                if (selectedCells.length === 1) {
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
                    structure[rowIndex][columnIndex] = helper.stringValue(cell.value);
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
                
            paste(evt: any) {
                if (this.mode === Mode.SINGLE) {
                    this.pasteSingleCell(evt);
                } else {
                    this.pasteRange(evt);
                }
            }
            
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
                
            pasteRange(evt: any) {
                var cbData = this.getClipboardContent(evt);
                cbData = this.process(cbData);
                this.updateWith(cbData);
            }
                
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
                
            updateWith(data: any) {
                let self = this;
                let selectedCell: any = selection.getSelectedCells(self.$grid)[0];
                if (selectedCell === undefined) return;
                let visibleColumns = helper.gridVisibleColumns(self.$grid);
                let rowIndex = selectedCell.rowIndex;
                let startColumnIndex = helper.indexOf(selectedCell.columnKey, visibleColumns);
                if (startColumnIndex === -1) return;
                let txId = util.randomId();
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
                    update.gridRowOw(self.$grid, rowIndex, rowData, txId);
                    rowIndex++;
                });
            }
            
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
            
            getClipboardContent(evt: any) {
                if (window.clipboardData) {
                    window.event.returnValue = false;
                    return window.clipboardData.getData("text");
                } else {
                    return evt.originalEvent.clipboardData.getData("text/plain");
                }
            }
        }
            
        export function on($grid: JQuery, updateMode: any) {
            if (updateMode === COPY_PASTE) {
                new Printer().hook($grid);
            }
        }
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
        
        export function bindSticker($cell: JQuery, rowIdx: any, columnKey: any, options: any) {
            if (options.containerClass !== BODY_PRF + DETAIL || util.isNullOrUndefined(options.updateMode) 
                || options.updateMode !== STICK) return;
            $cell.on(events.CLICK_EVT, function(evt: any) {
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
        export let MINUTE_MAX = 59;
        export let HOUR_MAX = 24;
        export let DEF_HOUR_MAX = 9999;
        export let DEF_HOUR_MIN = 0;
        export let DEF_MIN_MAXMIN = 0;
        
        export function validate($exTable: JQuery, $body: JQuery, $cell: JQuery, rowIdx: any, columnKey: any, 
                                innerIdx: any, value: any, timeRangerDef?: any) {
            let vtor = validation.mandate($exTable, columnKey, innerIdx);
            let gen = $body.data(internal.TANGI) || $body.data(internal.CANON);
            let rowId = gen.dataSource[rowIdx][gen.primaryKey];
            timeRangerDef = timeRangerDef || $body.data(internal.TIME_VALID_RANGE);
            if (timeRangerDef) {
                let ranges = timeRangerDef[rowId];
                _.forEach(ranges, function(range) {
                    if (range && range.columnKey === columnKey && range.innerIdx === innerIdx) { 
                        vtor.max = range.max;
                        vtor.min = range.min;
                        return false;
                    }
                });
            }
            if (vtor) {
                let isValid = vtor.actValid === internal.TIME ? validation.isTimeClock(value) 
                                                : validation.isTimeDuration(value, vtor.max, vtor.min);
                if (!isValid) {
                    errors.add($body, $cell, rowIdx, columnKey, innerIdx, value);
    //                cellData($exTable, rowIdx, columnKey, innerIdx, value);
//                    update.setText($cell, innerIdx, value);
                    return false;
                }
                if (errors.any($cell, innerIdx)) errors.remove($body, $cell, rowIdx, columnKey, innerIdx);
            }
            return true;
        }
        
        export function mandate($exTable: JQuery, columnKey: any, innerIdx: any) {
            let visibleColumns = helper.getVisibleColumnsOn($exTable);
            let actValid, dataType, max, min;
            _.forEach(visibleColumns, function(col: any) {
                if (col.key === columnKey) {
                    dataType = col.dataType.toLowerCase();
                    actValid = which(innerIdx, dataType, internal.TIME) 
                                || which(innerIdx, dataType, internal.DURATION);
                    max = col.max;
                    min = col.min;
                    return false; 
                }
            });
            if (actValid)
                return {
                    actValid: actValid,
                    max: max,
                    min: min
                };
        }
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
        
        export function remove($grid: JQuery, $cell: JQuery, rowIdx: any, columnKey: any, innerIdx: any) {
            let errors = $grid.data(ERRORS);
            if (!errors) return;
            let idx;
            _.forEach(errors, function(err: any, index: any) {
                if (err.rowIndex === rowIdx && err.columnKey === columnKey && err.innerIdx === innerIdx) {
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
        export function clear($grid: JQuery) {
            $grid.data(ERRORS, null);
        } 
        
        export function any($cell: JQuery, innerIdx: any) {
            let $childCells = $cell.find("." + render.CHILD_CELL_CLS);
            if (!util.isNullOrUndefined(innerIdx) && $childCells.length > 0) {
                return $($childCells[innerIdx]).hasClass(ERROR_CLS);
            }
            return $cell.hasClass(ERROR_CLS);
        }
        export function occurred($grid: JQuery) {
            let errs = $grid.data(ERRORS);
            if (!errs) return false;
            return errs.length > 0;
        }
    }
    
    module selection {
        export let CELL_SELECTED_CLS = "selected";
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
        export function selectCell($grid: JQuery, $cell: JQuery, notLast?: boolean) {
            if (!markCell($cell)) return;
            let coord = helper.getCellCoord($cell);
            addSelect($grid, coord.rowIdx, coord.columnKey, notLast);
        }
        
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
        
        export function cellAt($grid: JQuery, rowIdx: any, columnKey: any) {
            let $row = rowAt($grid, rowIdx);
            return getCellInRow($grid, $row, columnKey);
        }
        export function rowAt($grid: JQuery, rowIdx: any) {
            let virt = $grid.data(internal.TANGI);
            if (!virt) return $grid.find("tr:eq(" + (parseInt(rowIdx) + 1) + ")");
            if (virt.startIndex > rowIdx || virt.endIndex < rowIdx)
                return intan.NULL;
            return $grid.find("tr:eq(" + (parseInt(rowIdx) - virt.startIndex + 1) + ")");
        }
        export function cellOf($grid: JQuery, rowId: any, columnKey: any) {
            let $row = rowOf($grid, rowId);
            return getCellInRow($grid, $row, columnKey);
        }
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
        export function rowExists($grid: JQuery, rowIdx: any) {
            let virt = $grid.data(internal.TANGI);
            if (virt && (virt.startIndex > rowIdx || virt.endIndex < rowIdx))
                return false;
            return true;
        }
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
            if (util.isNullOrUndefined(columnIdx)) return;
            return $row.find("td").filter(function() {
                return $(this).css("display") !== "none";
            }).eq(columnIdx);
        }
        export function columnIndexRange($grid: JQuery, startKey: any, endKey: any) {
            let cloud: intan.Cloud = $grid.data(internal.TANGI);
            let canon: any = $grid.data(internal.PAINTER);
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
        
        export function off($exTable: JQuery) {
            if ($exTable.data(NAMESPACE).updateMode === COPY_PASTE) return;
            let $detailContent = $exTable.find("." + BODY_PRF + DETAIL);
            $detailContent[0].onselectstart = function() {
                return true;
            };
            $detailContent.off(events.MOUSE_DOWN).off(events.MOUSE_UP).off(events.MOUSE_MOVE);
        }
        
        export function focus($grid: JQuery) {
            $grid.focus();
        }
        export function focusLatest($grid: JQuery) {
            let latest = $grid.data(internal.LAST_SELECTED);
            if (!latest) return;
            let $cell = selection.cellAt($grid, latest.rowIdx, latest.columnKey);
            if ($cell === intan.NULL || $cell.length === 0) return;
            $cell.focus();
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
            
            cursorUp(event: any) {
                let self = this;
                self.$ownerDoc.off(events.MOUSE_MOVE);
                self.$ownerDoc.off(events.MOUSE_UP);
                self.syncLines();
                self.actionDetails = null;
            }
            
            setWidth($col: JQuery, width: any) {
                $col.width(width);
            }
            
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
            actionDetails: any;
            
            constructor($container: JQuery, headerWrappers: Array<JQuery>, bodyWrappers: Array<JQuery>) {
                this.$container = $container;
                this.headerWrappers = headerWrappers;
                this.bodyWrappers = bodyWrappers;
                this.$ownerDoc = $($container[0].ownerDocument);
                this.$leftHorzSumHeader = this.$container.find("." + HEADER_PRF + LEFT_HORZ_SUM);
                this.$leftHorzSumContent = this.$container.find("." + BODY_PRF + LEFT_HORZ_SUM);
                this.$horzSumHeader = this.$container.find("." + HEADER_PRF + HORIZONTAL_SUM);
                this.$horzSumContent = this.$container.find("." + BODY_PRF + HORIZONTAL_SUM);
            }
            
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
                    if ($wrapper.hasClass(HEADER_PRF + LEFTMOST)) $line.addClass(STAY_CLS);
                });
                self.$areaAgency.on(events.MOUSE_DOWN, $.proxy(self.cursorDown, self)); 
            }
            
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
            
            reflectSumTblsSize(diff: number, leftWidth: number, rightWidth: number, posLeft: number) {
                let self = this;
                let $leftArea = self.actionDetails.$leftArea;
                let $rightArea = self.actionDetails.$rightArea;
                if ($rightArea && $rightArea.hasClass(HEADER_PRF + DETAIL)) {
                    let horzLeftWidth = self.actionDetails.widths.leftHorzSum + diff;
                    self.setWidth(self.$leftHorzSumHeader, horzLeftWidth);
                    self.setWidth(self.$leftHorzSumContent, horzLeftWidth);
                    self.setWidth(self.$horzSumHeader, rightWidth);
                    self.setWidth(self.$horzSumContent, rightWidth + helper.getScrollWidth());
                    self.$horzSumHeader.css("left", posLeft);
                    self.$horzSumContent.css("left", posLeft);
                } else if ($leftArea && $leftArea.hasClass(HEADER_PRF + DETAIL)) {
                    self.setWidth(self.$horzSumHeader, leftWidth);
                    self.setWidth(self.$horzSumContent, leftWidth + helper.getScrollWidth());
                }
            }
            
            isResizePermit(leftWidth: number, rightWidth: number) {
                let self = this;
                let leftAreaMaxWidth = 0, rightAreaMaxWidth = 0;
                if (leftWidth <= 20 || (self.actionDetails.widths.right > 0 && rightWidth <= 20)) return false;
                if (self.actionDetails.$leftArea) {
                    let $leftAreaColGroup = self.actionDetails.$leftArea.find("table > colgroup > col");
                    $leftAreaColGroup.each(function(i: number, c: any) {
                        if (i < $leftAreaColGroup.length) {
                            leftAreaMaxWidth += $(c).width() + 1;
                        } else {
                            leftAreaMaxWidth += $(c).width();
                        }
                    }); 
                    if (leftWidth >= leftAreaMaxWidth) return false;               
                }
                if (self.actionDetails.$rightArea) {
                    let $rightAreaColGroup = self.actionDetails.$rightArea.find("table > colgroup > col");
                    $rightAreaColGroup.each(function(i: number, c: any) {
                        if (i < $rightAreaColGroup.length) {
                            rightAreaMaxWidth += $(c).width() + 1;
                        } else {
                            rightAreaMaxWidth += $(c).width();
                        }
                    });
                    if (rightWidth >= rightAreaMaxWidth) return false;
                }
                return true;
            }
            
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
            
            setWidth($wrapper: JQuery, width: any) {
                $wrapper.width(width);
            }
            
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
        
        function getCursorX(event: any) {
            return event.pageX;
        }
        function lineStyles(marginLeft: string) {
            return { position: "absolute", cursor: "ew-resize", width: "4px", zIndex: 2, marginLeft: marginLeft };
        }
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
            events.trigger($container, events.BODY_HEIGHT_CHANGED, height);
        }
        export function fitWindowWidth($container: JQuery) {
            let $vertSumHeader = $container.find("." + HEADER_PRF + VERTICAL_SUM);
            let $vertSumContent = $container.find("." + BODY_PRF + VERTICAL_SUM);
            let $detailHeader = $container.find("." + HEADER_PRF + DETAIL);
            let $detailBody = $container.find("." + BODY_PRF + DETAIL);
            let width = window.innerWidth - $detailHeader.offset().left;
            if ($vertSumHeader.length > 0 && $vertSumHeader.css("display") !== "none") {
                width = width - parseInt($container.data(internal.X_OCCUPY)) - $vertSumContent.width();    
                $detailHeader.width(width);
                $detailBody.width(width);
                $container.find("." + HEADER_PRF + HORIZONTAL_SUM).width(width);
                $container.find("." + BODY_PRF + HORIZONTAL_SUM).width(width + helper.getScrollWidth());
                repositionVertSum($container, $vertSumHeader, $vertSumContent);
                syncDetailAreaLine($container, $detailHeader, $detailBody);
                return;
            }
            width = width - parseInt($container.data(internal.X_OCCUPY));
            $detailHeader.width(width - helper.getScrollWidth()) ;
            $detailBody.width(width);
            $container.find("." + HEADER_PRF + HORIZONTAL_SUM).width(width - helper.getScrollWidth());
            $container.find("." + BODY_PRF + HORIZONTAL_SUM).width(width);
        }
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
        export function repositionVertSum($container: JQuery, $vertSumHeader?: JQuery, $vertSumContent?: JQuery) {
            $vertSumHeader = $vertSumHeader || $container.find("." + HEADER_PRF + VERTICAL_SUM);
            $vertSumContent = $vertSumContent || $container.find("." + BODY_PRF + VERTICAL_SUM);
            let $detailHeader = $container.find("." + HEADER_PRF + DETAIL);
            let posLeft = $detailHeader.css("left");
            let vertSumLeft = parseInt(posLeft) + $detailHeader.width() + DISTANCE;
            $vertSumHeader.css("left", vertSumLeft);
            $vertSumContent.css("left", vertSumLeft);
        }
        export function setHeight($container: JQuery, height: any) {
            $container.find("div[class*='" + BODY_PRF + "']").each(function() {
                if ($(this).hasClass(BODY_PRF + HORIZONTAL_SUM) || $(this).hasClass(BODY_PRF + LEFT_HORZ_SUM)) return;
                $(this).height(height);
            });
            events.trigger($container, events.BODY_HEIGHT_CHANGED, height);
        }
        
        export function onAreaComplete(event: any, $leftArea: JQuery, $rightArea: JQuery, 
                                        leftWidth: number, rightWidth: number) {
            let self = this;
            if ($leftArea) {
                storage.area.save(self.$container, $leftArea.data(internal.EX_PART), leftWidth);
            }
            if ($rightArea) {
                storage.area.save(self.$container, $rightArea.data(internal.EX_PART), rightWidth); 
            }
        }
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
            abstract getStorageKey($container: JQuery);
            initValueExists($container: JQuery) {
                let self = this;
                let storeKey = self.getStorageKey($container);
                let value = uk.localStorage.getItem(storeKey);
                return value.isPresent();
            }
            getStoreItem($container: JQuery, item: string) {
                return request.location.current.rawUrl + "/" + $container.attr("id") + "/" + item;
            }
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
            
            export function load($container: JQuery) {
                let storeKey = cache.getStorageKey($container);
                uk.localStorage.getItem(storeKey).ifPresent((parts) => {
                    let widthParts: any = JSON.parse(parts);
                    setWidths($container, widthParts);
                    return null;
                });
            }
            
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
            
            function saveAll($container: JQuery, widths: {[ key: string ]: number }) {
                let storeKey = cache.getStorageKey($container);
                let partWidths = uk.localStorage.getItem(storeKey);
                if (!partWidths.isPresent()) {
                    uk.localStorage.setItemAsJson(storeKey, widths);
                }
            }
            
            export function getPartWidths($container: JQuery) {
                return cache.getValue($container);
            }            
            
            function setWidths($container: JQuery, parts: {[ key: string ]: number }) {
                let partKeys = Object.keys(parts);
                _.forEach(partKeys, function(keyClass: any, index: number) {
                    setWidth($container, keyClass, parts[keyClass]);
                });
            }
            
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
            export function init($container: JQuery) {
                if (cache.initValueExists($container)) {
                    return;
                }
                let $bodies = $container.find("div[class*='" + BODY_PRF + "']");
                if ($bodies.length === 0) return;
                save($container, $($bodies[0]).height());
            }
            
            export function load($container: JQuery) {
                let storeKey = cache.getStorageKey($container);
                uk.localStorage.getItem(storeKey).ifPresent((height) => {
                    let h = JSON.parse(height);
                    resize.setHeight($container, height);
                    return null;
                });
            }
            export function get($container: JQuery) {
                return cache.getValue($container);
            }
            
            export function save($container: JQuery, height: number) {
                let storeKey = cache.getStorageKey($container);
                uk.localStorage.setItemAsJson(storeKey, height);
            }
        }
    }
    
    module scroll {
        export let SCROLL_SYNCING = "scroll-syncing";
        export let VERT_SCROLL_SYNCING = "vert-scroll-syncing";
        export function bindVertWheel($container: JQuery) {
            $container.on(events.MOUSE_WHEEL, function(event: any) {
                let delta = event.originalEvent.wheelDeltaY;
                let direction = delta > 0 ? -1 : 1;
                let value = $container.scrollTop() + event.originalEvent.deltaY;
                $container.stop().animate({ scrollTop: value }, 70);
                event.preventDefault();
                event.stopImmediatePropagation();
            });
            if ($container.css("overflow-y") !== "hidden") {
                $container.css("overflow-y", "hidden");
            }
        }
        export function unbindVertWheel($container: JQuery) {
            $container.off(events.MOUSE_WHEEL);
            $container.css("overflow-y", "scroll");
        }
        
        export function syncDoubDirHorizontalScrolls(wrappers: Array<JQuery>) {
            _.forEach(wrappers, function($main: JQuery, index: number) {
                $main.on(events.SCROLL_EVT, function() {
                    _.forEach(wrappers, function($depend: JQuery, i: number) {
                        if (i === index) return;
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
        
        export function syncHorizontalScroll($headerWrap: JQuery, $bodyWrap: JQuery) {
            $bodyWrap.on(events.SCROLL_EVT, function() {
                $headerWrap.scrollLeft($bodyWrap.scrollLeft());
            });
        }
        
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
        export let START_EDIT = "extablestartedit";
        export let STOP_EDIT = "extablestopedit";
        export let CELL_UPDATED = "extablecellupdated";
        export let ROW_UPDATED = "extablerowupdated";
        export let POPUP_SHOWN = "xpopupshown";
        export let POPUP_INPUT_END = "xpopupinputend";
        
        export function trigger($target: JQuery, eventName: string, args: any) {
            $target.trigger($.Event(eventName), args);
        }
        
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
        
        export function createRowUi(rowIdx: any, data: any) {
            return {
                rowIndex: rowIdx,
                data: data
            };
        }
    }
    
    module feature {
        export let HEADER_ROW_HEIGHT = "HeaderRowHeight";
        export let HEADER_CELL_STYLE = "HeaderCellStyle";
        export let HEADER_POP_UP = "HeaderPopups";
        export let BODY_CELL_STYLE = "BodyCellStyle";
        export let COLUMN_RESIZE = "ColumnResize";
        export let TIME_RANGE = "TimeRange"
        
        export function isEnable(features: any, name: string) {
            return _.find(features, function(feature: any) {
                return feature.name === name;
            }) !== undefined;
        }
        
        export function find(features: any, name: string) {
            return _.find(features, function(feature: any) {
                return feature.name === name;
            });
        }
    }
    
    module style {
        export let DET_CLS: string = "xdet";
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
                        _.forEach(ds, function(item: any, index: number) {
                            if (index >= start && index < end) {
                                let $c = selection.cellAt($main, index, coord.columnKey);
                                if ($c === intan.NULL || $c.length === 0) return;
                                helper.markCellWith(DET_CLS, $c);
                            }
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
                            helper.markCellsWith(DET_CLS, $targetRow.find("td").filter(function() {
                                return $(this).css("display") !== "none";
                            }));
                            
                            let colKeys = _.map(helper.gridVisibleColumns($main), "key");
                            let det = $main.data(internal.DET);
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
        
        function onDetSingleCell(evt: any, $tbl: JQuery, $cell: JQuery, rowIdx: any, columnKey: any) {
            if (!evt.ctrlKey) return;
            let $main = helper.getMainTable($tbl);
            let det = $main.data(internal.DET);
            if (!det) {
                det = {};
                det[rowIdx] = [ columnKey ];
                $main.data(internal.DET, det);
            } else if (!det[rowIdx]) {
                det[rowIdx] = [ columnKey ];
            } else {
                let dup;
                _.forEach(det[rowIdx], function(key: any) {
                    if (key === columnKey) {
                        dup = true;
                        return false;
                    }
                });
                if (!dup) {
                    det[rowIdx].push(columnKey);
                }
            }
            helper.markCellWith(DET_CLS, $cell);
        }
    }
    
    module func {
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
                    updateTable(self, params[0], params[1], params[2]);
                    break;
                case "updateMode":
                    setUpdateMode(self, params[0]);
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
                case "stickUndo":
                    undoStick(self);
                    break;
                case "clearHistories":
                    clearHistories(self, params[0]);
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
                case "dataSource":
                    return getDataSource(self, params[0]);
                case "cellByIndex":
                    return getCellByIndex(self, params[0], params[1]);
                case "cellById":
                    return getCellById(self, params[0], params[1]);
                case "updatedCells":
                    return getUpdatedCells(self);
            }
        };
        
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
        function hideHorzSum($container: JQuery) {
            $container.find("." + HEADER_PRF + LEFT_HORZ_SUM).hide();
            $container.find("." + BODY_PRF + LEFT_HORZ_SUM).hide();
            $container.find("." + HEADER_PRF + HORIZONTAL_SUM).hide();
            $container.find("." + BODY_PRF + HORIZONTAL_SUM).hide();
            resize.fitWindowHeight($container, undefined, false);
        }
        function showHorzSum($container: JQuery) {
            $container.find("." + HEADER_PRF + LEFT_HORZ_SUM).show();
            $container.find("." + BODY_PRF + LEFT_HORZ_SUM).show();
            $container.find("." + HEADER_PRF + HORIZONTAL_SUM).show();
            $container.find("." + BODY_PRF + HORIZONTAL_SUM).show();
            resize.fitWindowHeight($container, undefined, true);
        }
        function hideVertSum($container: JQuery) {
            $container.find("." + HEADER_PRF + VERTICAL_SUM).hide();
            $container.find("." + BODY_PRF + VERTICAL_SUM).hide();
            resize.fitWindowWidth($container);
            scroll.unbindVertWheel($container.find("." + BODY_PRF + DETAIL));
        }
        function showVertSum($container: JQuery) {
            let $vertSumBody = $container.find("." + BODY_PRF + VERTICAL_SUM);
            let $detailBody = $container.find("." + BODY_PRF + DETAIL);
            $container.find("." + HEADER_PRF + VERTICAL_SUM).show();
            $vertSumBody.show();
            resize.fitWindowWidth($container);
            scroll.bindVertWheel($detailBody);
            $vertSumBody.scrollTop($detailBody.scrollTop());
        }
        
        function updateTable($container: JQuery, name: string, header: any, body: any) {
            switch (name) {
                case "leftmost":
                    updateLeftmost($container, header, body);
                    break;
                case "middle": 
                    updateMiddle($container, header, body);
                    break;
                case "detail":
                    updateDetail($container, header, body);
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
        
        function updateLeftmost($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.leftmostHeader, header);
                let $header = $container.find("." + HEADER_PRF + LEFTMOST);
                $header.empty();
                render.process($header, exTable.leftmostHeader, true);
            }
            if (body) {
                _.assignIn(exTable.leftmostContent, body);
                let $body = $container.find("." + BODY_PRF + LEFTMOST);
                $body.empty();
                render.process($body, exTable.leftmostContent, true);
            }
        }
        function updateMiddle($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.middleHeader, header);
                let $header = $container.find("." + HEADER_PRF + MIDDLE);
                $header.empty();
                render.process($header, exTable.middleHeader, true);
            }
            if (body) {
                _.assignIn(exTable.middleContent, body);
                let $body = $container.find("." + BODY_PRF + MIDDLE);
                $body.empty();
                render.process($body, exTable.middleContent, true);
            }
        }
        function updateDetail($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.detailHeader, header);
                let $header = $container.find("." + HEADER_PRF + DETAIL);
                $header.empty();
                render.process($header, exTable.detailHeader, true);
            }
            if (body) {
                _.assignIn(exTable.detailContent, body);
                let $body = $container.find("." + BODY_PRF + DETAIL);
                $body.empty();
                render.process($body, exTable.detailContent, true);
            }
        }
        function updateVertSum($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.verticalSumHeader, header);
                let $header = $container.find("." + HEADER_PRF + VERTICAL_SUM);
                $header.empty();
                render.process($header, exTable.verticalSumHeader, true);
            }
            if (body) {
                _.assignIn(exTable.verticalSumContent, body);
                let $body = $container.find("." + BODY_PRF + VERTICAL_SUM);
                $body.empty();
                render.process($body, exTable.verticalSumContent, true);
            }
        }
        function updateLeftHorzSum($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.leftHorzSumHeader, header);
                let $header = $container.find("." + HEADER_PRF + LEFT_HORZ_SUM);
                $header.empty();
                render.process($header, exTable.leftHorzSumHeader, true);
            }
            if (body) {
                _.assignIn(exTable.leftHorzSumContent, body);
                let $body = $container.find("." + BODY_PRF + LEFT_HORZ_SUM);
                $body.empty();
                render.process($body, exTable.leftHorzSumContent, true);
            }
        }
        function updateHorzSum($container: JQuery, header: any, body: any) {
            let exTable: any = $container.data(NAMESPACE);
            if (header) {
                _.assignIn(exTable.horizontalSumHeader, header);
                let $header = $container.find("." + HEADER_PRF + HORIZONTAL_SUM);
                $header.empty();
                render.process($header, exTable.horizontalSumHeader, true);
            }
            if (body) {
                _.assignIn(exTable.horizontalSumContent, body);
                let $body = $container.find("." + BODY_PRF + HORIZONTAL_SUM);
                $body.empty();
                render.process($body, exTable.horizontalSumContent, true);
            }
        }
        function setUpdateMode($container: JQuery, mode: string) {
            let exTable: any = $container.data(NAMESPACE);
            if (exTable.updateMode === mode) return;
            exTable.setUpdateMode(mode);
            let $grid = $container.find("." + BODY_PRF + DETAIL);
            render.begin($grid, internal.getDataSource($grid), exTable.detailContent);
            if (mode === COPY_PASTE) {
                selection.checkUp($container);
                copy.on($grid, mode);
                return;
            }
            selection.off($container);
            copy.off($grid, mode);
        }
        function setPasteOverWrite($container: JQuery, overwrite: boolean) {
            let exTable: any = $container.data(NAMESPACE);
            exTable.pasteOverWrite = overwrite;
        }
        function setStickOverWrite($container: JQuery, overwrite: boolean) {
            let exTable: any = $container.data(NAMESPACE);
            exTable.stickOverWrite = overwrite;
        }
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
        function returnPopupValue($container: JQuery, value: any) {
            let $pu = $container.data(internal.POPUP);
            if (!$pu) return;
            events.trigger($pu, events.POPUP_INPUT_END, { value: value });
        }
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
        function getCellByIndex($container: JQuery, rowIndex: any, columnKey: any) {
            let $tbl = helper.getMainTable($container);
            if ($tbl.length === 0) return;
            return selection.cellAt($tbl, rowIndex, columnKey);
        }
        function getCellById($container: JQuery, rowId: any, columnKey: any) {
            let $tbl = helper.getMainTable($container);
            if ($tbl.length === 0) return;
            return selection.cellOf($tbl, rowId, columnKey);
        }
        function getUpdatedCells($container: JQuery) {
            let data = $container.data(NAMESPACE).modifications;
            if (!data) return [];
            return helper.valuesArray(data);
        }
        function setCellValue($container: JQuery, name: any, rowId: any, columnKey: any, value: any) {
            switch (name) {
                case "horizontalSummaries":
                    setValue($container, BODY_PRF + HORIZONTAL_SUM, rowId, columnKey, value);
                    break;
                case "verticalSummaries":
                    setValue($container, BODY_PRF + VERTICAL_SUM, rowId, columnKey, value);
                    break;
            }
        }
        function setCellValueByIndex($container: JQuery, name: any, rowIdx: any, columnKey: any, value: any) {
            switch(name) {
                case HORZ_SUM:
                    setValueByIndex($container, BODY_PRF + HORIZONTAL_SUM, rowIdx, columnKey, value);
                    break;
                case VERT_SUM:
                    setValueByIndex($container, BODY_PRF + VERTICAL_SUM, rowIdx, columnKey, value);
                    break;
            }   
        }
        function setValue($container: JQuery, selector: any, rowId: any, columnKey: any, value: any) {
            let $grid = $container.find("." + selector);
            if ($grid.length === 0) return;
            let rowIdx = helper.getRowIndex($grid, rowId);
            let ds = helper.getDataSource($grid);
            if (rowIdx === -1 || !ds || ds.length === 0) return;
            ds[rowIdx][columnKey] = value;
            refreshCell($grid, rowId, columnKey, value);
        }
        function setValueByIndex($container: JQuery, selector: any, rowIdx: any, columnKey, value: any) {
            let $grid = $container.find("." + selector);
            if ($grid.length === 0) return;
            let ds = helper.getDataSource($grid);
            if (!ds || ds.length === 0) return;
            ds[rowIdx][columnKey] = value;
            refreshCellByIndex($grid, rowIdx, columnKey, value);
        }
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
    }
    
    module internal {
        export let X_OCCUPY: string = "ex-x-occupy";
        export let Y_OCCUPY: string = "ex-y-occupy";
        export let TANGI: string = "x-tangi";
        export let CANON: string = "x-canon";
        export let STICKER: string = "x-sticker";
        export let DET: string = "x-det";
        export let PAINTER: string = "painter";
        export let VIEW: string = "view";
        export let EX_PART: string = "expart";
        export let TIME_VALID_RANGE = "time-validate-range";
        export let SELECTED_CELLS: string = "selected-cells";
        export let LAST_SELECTED: string = "last-selected";
        export let COPY_HISTORY: string = "copy-history";
        export let EDIT_HISTORY: string = "edit-history";
        export let STICK_HISTORY: string = "stick-history";
        export let TOOLTIP: string = "tooltip";
        export let CONTEXT_MENU: string = "context-menu";
        export let POPUP: string = "popup";
        export let TEXT: string = "text";
        export let TIME: string = "time";
        export let DURATION: string = "duration";
        export let DT_SEPARATOR: string = "/";
        
        export function getDataSource($grid: JQuery) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            return gen.dataSource;
        }
        
        
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
    }
    
    module helper {
        export function getScrollWidth() {
            var $outer = $('<div>').css({ visibility: 'hidden', width: 100, overflow: 'scroll' }).appendTo('body'),
            widthWithScroll = $('<div>').css({ width: '100%' }).appendTo($outer).outerWidth();
            $outer.remove();
            return 100 - widthWithScroll;
        }
        export function getMainTable($exTable: JQuery) {
            return $exTable.find("." + BODY_PRF + DETAIL);
        }
        export function getExTableFromGrid($grid: JQuery) {
            return $grid.closest("." + NAMESPACE).data(NAMESPACE);
        }
        export function getVisibleColumnsOn($exTable: JQuery) {
            let $main = getMainTable($exTable);
            let gen = $main.data(internal.TANGI) || $main.data(internal.CANON);
            return gen.painter.visibleColumns;
        }
        export function getVisibleColumns(options: any) {
            let visibleColumns = [];
            filterColumns(options.columns, visibleColumns, []);
            return visibleColumns;
        }
        export function getOrigDS($grid: JQuery) {
            return ($grid.data(internal.TANGI) || $grid.data(internal.CANON))._origDs;
        }
        export function getDataSource($grid: JQuery) {
            return ($grid.data(internal.TANGI) || $grid.data(internal.CANON)).dataSource;
        }
        export function getClonedDs($grid: JQuery) {
            return _.cloneDeep(getDataSource($grid));
        }
        export function getPrimaryKey($grid: JQuery) {
            return ($grid.data(internal.TANGI) || $grid.data(internal.CANON)).primaryKey;
        }
        export function classifyColumns(options: any) {
            let visibleColumns = [];
            let hiddenColumns = [];
            filterColumns(options.columns, visibleColumns, hiddenColumns);
            return {
                        visibleColumns: visibleColumns,
                        hiddenColumns: hiddenColumns
                   };
        }
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
        
        export function getColumnsMap(columns: any) {
            return _.groupBy(columns, "key");
        }
        
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
        export function getPartialDataSource($table: any, name: any) {
            return {
                header: getClonedDs($table.find("." + HEADER_PRF + name)),
                body: getClonedDs($table.find("." + BODY_PRF + name))
            };
        }
        
        export function makeConnector() {
            Connector[HEADER_PRF + LEFTMOST] = BODY_PRF + LEFTMOST;
            Connector[HEADER_PRF + MIDDLE] = BODY_PRF + MIDDLE;
            Connector[HEADER_PRF + DETAIL] = BODY_PRF + DETAIL;
            Connector[HEADER_PRF + VERTICAL_SUM] = BODY_PRF + VERTICAL_SUM;
            Connector[HEADER_PRF + HORIZONTAL_SUM] = BODY_PRF + HORIZONTAL_SUM;
        }
        export function getClassOfHeader($part: JQuery) {
            return $part.data(internal.EX_PART);
        }
        
        export function isPasteKey(evt: any) {
            return evt.keyCode === 86;
        }
        export function isCopyKey(evt: any) {
            return evt.keyCode === 67;
        }
        export function isCutKey(evt: any) {
            return evt.keyCode === 88;
        }
        export function isUndoKey(evt: any) {
            return evt.keyCode === 90;
        }
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
                rowIdx: coord[0],
                columnKey: coord[1]
            };
        }
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
        export function gridVisibleColumns($grid: JQuery) {
            let gen = $grid.data(internal.TANGI) || $grid.data(internal.CANON);
            if (!gen) return;
            return gen.painter.visibleColumns;
        }
        
        
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
        export function markCellsWith(clazz: any, $cells: JQuery) {
            $cells.each(function() {
                markCellWith(clazz, $(this));
            });
        }
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
        export function nextKeyOf(columnIndex: number, visibleColumns: any) {
            if (columnIndex >= visibleColumns.length - 1) return;
            return visibleColumns[columnIndex + 1].key;
        }
        export function call(fn: any, ...args: any[]) {
            return function() {
                return fn.apply(null, args);
            };
        }
        export function containsBr(text: string) {
            return text && text.indexOf("<br/>") > -1;
        }
        export function areSameCells(one: any, other: any) {
            if (parseInt(one.rowIndex) !== parseInt(other.rowIndex)
                || one.columnKey !== other.columnKey
                || one.innerIdx !== other.innerIdx) return false;
            return true;
        }
        export function valuesArray(obj: any) {
            let values = [];
            _.forEach(Object.keys(obj), function(k, i) {
                values = _.concat(values, obj[k]);
            });
            return values;
        }
        export function stringValue(val: any) {
            return _.isObject(val) ? JSON.stringify(val) : val;
        }
        export function getCellData(data: any) {
            try {
                return JSON.parse(data);
            } catch (e) {
                return data;
            }
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
        
        abstract class XWidget {
            $table: JQuery;
            $selector: JQuery;
            constructor($selector: JQuery) {
                this.$selector = $selector;
            }
            abstract initialize();
            getTable() {
                this.$table = this.$selector.closest("." + NAMESPACE);
            }
        }
        
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
        
        abstract class Popup extends XWidget {
            constructor($selector: JQuery) {
                super($selector);
                this.initialize();
            }
            initialize() {
                let self = this;
                self.$selector.on(events.MOUSE_DOWN, function(evt: any) {
                    self.getTable();
                    if (evt.ctrlKey && self.$table.data(NAMESPACE).determination) return;
                    self.click(evt);
                });
            }
            abstract click(evt: any);
        } 
        
        export class ContextMenu extends Popup {
            items: Array<MenuItem>;
            constructor($selector: JQuery, items: Array<MenuItem>) {
                super($selector);
                this.items = items;         
            }
            
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
                evt.stopPropagation();
                hideIfOutside($menu); 
            }
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
        
        export class PopupPanel extends Popup {
            $panel: JQuery
            constructor($selector: JQuery, $panel: JQuery) {
                super($selector);
                this.$panel = $panel;
            }
            click(evt: any) {
                let self = this;
                let $pu = self.$table.data(internal.POPUP);
                if (!$pu) {
                    $pu = self.$panel.addClass(POPUP_CLS).hide();
                    self.$table.data(internal.POPUP, $pu);
                }
                if ($pu.css("display") === "none") {
                    let pos = eventPageOffset(evt, false);
                    $pu.show().css({ top: pos.pageY - $pu.outerHeight(), left: pos.pageX - $pu.outerWidth() });
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
        }
        
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
        
        function eventPageOffset(evt, isFixed) {
            var scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
            var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
            return isFixed ? { pageX: evt.pageX - scrollLeft, pageY: evt.pageY - scrollTop } 
                           : { pageX: evt.pageX, pageY: evt.pageY };
        }
        
        function cssClass(options) {
            var css = options.showBelow ? 'bottom' : 'top';
            css += '-';
            css += (options.showRight ? 'right' : 'left');
            css += '-tooltip';
            return css;
        }
    }
}