module nts.uk.ui.mgrid {
    
    const MGRID = "mgrid";
    const FIXED = "mgrid-fixed";
    const FREE = "mgrid-free";
    const HEADER = "mgrid-header";
    const BODY = "mgrid-body";
    const Default = "default";
    const SheetDef = "_sheetDef";
    const DISTANCE = 1;
    const BODY_ROW_HEIGHT = 29;
    const SUM_HEIGHT = 27;
    let defaultOptions = { columns: [], features: [] };
    let _scrollWidth, _maxFixedWidth = 0, _maxFreeWidth, _columnsMap = {}, _dataSource,
        _hasFixed, _validators = {}, _mDesc, _mEditor, _cloud, _hr, _direction, _errors = [], 
        _errorColumns, _errorsOnPage, _$grid, _pk, _pkType, _summaries, _objId, _getObjId, _hasSum, _pageSize, _currentPage, _currentSheet, _start, _end, 
        _headerHeight, _zeroHidden, _paging = false, _sheeting = false, _mafollicle = {}, _vessel = () => _mafollicle[_currentPage][_currentSheet], 
        _cstifle = () => _mafollicle[SheetDef][_currentSheet].columns, _specialColumn = {}, _specialLinkColumn = {},
        _dirties = {}, _headerWrappers, _bodyWrappers, _sumWrappers,
        _fixedControlMap = {}, _cellStates, _features, _leftAlign, _header,
        _prtDiv = document.createElement("div"), _prtCell = document.createElement("td");
    
    export class MGrid {
        
        $container: HTMLElement;
        headers: Array<any>;
        bodies: Array<any>;
        fixedHeader: any = { containerClass: FIXED };
        fixedBody: any = { containerClass: FIXED };
        header: any = { containerClass: FREE };
        body: any = { containerClass: FREE };
        fixedSummaries: any = { containerClass: FIXED + "-summaries" };
        summaries: any = { containerClass: FREE + "-summaries" }; 
        
        constructor($container: HTMLElement, options: any) {
            let self = this;
            self.$container = $container;
            _$grid = $($container);
            _pk = options.primaryKey;
            _pkType = options.primaryKeyDataType;
            _features = options.features;
            _objId = options.userId;
            _getObjId = options.getUserId;
            _errorColumns = options.errorColumns;
            _errorsOnPage = options.errorsOnPage;
            _headerHeight = options.headerHeight;
            _zeroHidden = options.hideZero;
            _.assignIn(self, _.cloneDeep(options));  
            self.makeDefault();  
        }
        
        makeDefault() {
            let self = this;
            self.fixedHeader = _.assignIn(self.fixedHeader, _.cloneDeep(defaultOptions), { ntsControls: self.ntsControls });
            self.fixedBody = _.assignIn(self.fixedBody, _.cloneDeep(defaultOptions));
            self.header = _.assignIn(self.header, _.cloneDeep(defaultOptions), { ntsControls: self.ntsControls });
            self.body = _.assignIn(self.body, _.cloneDeep(defaultOptions));
            self.compreOptions();
            _$grid.mGrid({});
        }
        
        compreOptions() {
            let self = this;
            if (self.features) {
                let columnFixFt = tn.find(self.features, tn.COLUMN_FIX);
                let colParts;
                
                let pageFt = tn.find(self.features, tn.PAGING);
                if (pageFt) {
                    _paging = true;
                    _pageSize = pageFt.pageSize;
                    _currentPage = pageFt.currentPageIndex;
                    let remainder, pageSources, size = self.dataSource.length; 
                    let noPage = Math.floor(size / _pageSize);
                    for (let i = 0; i < noPage; i++) {
                        let s = i * _pageSize;
                        let src = _.slice(self.dataSource, s, s + _pageSize);
                        _mafollicle[i] = { dataSource: src, origDs: _.cloneDeep(src) };
                    }
                    
                    if ((remainder = size % _pageSize) !== 0) {
                        let s = _pageSize * noPage;
                        let src = _.slice(self.dataSource, s, s + _pageSize);
                        _mafollicle[noPage] = { dataSource: src, origDs: _.cloneDeep(src) };
                    }
                } else {
                    _currentPage = Default;
                    _mafollicle[_currentPage] = { dataSource: self.dataSource, origDs: _.cloneDeep(self.dataSource) };
                }
                
                let sheetFt = tn.find(self.features, tn.SHEET);
                let sheetDef = {};
                if (sheetFt) {
                    _sheeting = true;
                    _currentSheet = sheetFt.initialDisplay;
                    _.forEach(sheetFt.sheets, s => {
                        let sheetCols = [];
                        _.forEach(s.columns, c => {
                            let sc = _.find(self.columns, col => {
                                if (col.group) {
                                    return col.group[0].key === c;
                                } else return col.key === c;
                            });
                            
                            if (sc) sheetCols.push(sc);
                        });
                        
                        sheetDef[s.name] = { columns: sheetCols, text: s.text };
                    });
                } else {
                    _currentSheet = Default;
                    sheetDef[Default] = { columns: self.columns, text: "Sheet" };
                }
                
                _mafollicle[SheetDef] = sheetDef;
                
                if (columnFixFt && columnFixFt.columnSettings) {
                    let fixedColumns = _.filter(columnFixFt.columnSettings, c => c.isFixed);
                    if (_sheeting) {
                        let fixedCols = _.filter(self.columns, c => {
                            return _.some(fixedColumns, f => f.columnKey === c.key);
                        });
                        
                        colParts = [ fixedCols, _cstifle() ];
                    } else {
                        colParts = _.partition(self.columns, c => { 
                            return _.some(fixedColumns, f => f.columnKey === c.key);
                        });
                    }
                    
                    self.fixedHeader.columns = colParts[0];
                    self.fixedHeader.height = self.headerHeight;
                    self.fixedBody.columns = colParts[0];
                    self.header.columns = colParts[1];
                    self.header.height = self.headerHeight;
                    self.body.columns = colParts[1];
                    _hasFixed = true;
                    let headerStyles = tn.find(self.features, tn.HEADER_STYLE);
                    if (headerStyles) {
                        let styleParts = _.partition(headerStyles.columns, c => {
                            return _.some(fixedColumns, f => f.columnKey === c.key);
                        });
                        
                        self.fixedHeader.features.push({ name: tn.HEADER_STYLE, columns: styleParts[0] });
                        self.header.features.push({ name: tn.HEADER_STYLE, columns: styleParts[1] });
                    }
                } else {
                    self.header.columns = self.columns;
                    self.body.columns = self.columns;
                }
                
                let summaries = tn.find(self.features, tn.SUMMARIES);
                if (summaries) {
                    _summaries = {};
                    self.fixedSummaries.columns = colParts[0];
                    self.fixedSummaries.height = SUM_HEIGHT + "px";
                    self.summaries.columns = colParts[1];
                    self.summaries.height = SUM_HEIGHT + "px";
                    
                    _.forEach(summaries.columnSettings, s => {
                        let sum = { calculator: s.summaryCalculator };
                        if (s.summaryCalculator === "Time") {
                            sum.total = moment.duration("0");
                        } else if (s.summaryCalculator === "Number") {
                            sum.total = 0;
                        }
                        
                        _summaries[s.columnKey] = sum;
                    });
                }
            }
        }
        
        create() {
            let self = this;
            let left: string = "0px";
            let top: string = "0px";
            let start = performance.now();
            self.headers = _.filter([ self.fixedHeader, self.header ], h => {
                return h && h.columns;
            });
            
            self.bodies = _.filter([ self.fixedBody, self.body ], b => {
                return b && b.columns;
            });
            
            self.$container.classList.add(MGRID);
            $.data(self.$container, MGRID, self);
            let pTable = $.data(self.$container, MGRID); 
            pTable.owner = { headers: [], bodies: [] };
            
            let scrollWidth = ti.getScrollWidth();
            let headerWrappers = [], bodyWrappers = [], sumWrappers = [], headerColGroup = [], 
                bodyColGroup = [], sumColGroup = [], $fixedHeaderTbl, painters = [], controlMap = {};
            let $frag = document.createDocumentFragment();
            let freeWrapperWidth;
            self.headers.forEach((headPart, i) => {
                if (!_.isNil(self.headers[i])) {
                    headPart.overflow = "hidden";
                    if (headPart.containerClass === FREE) {
                        freeWrapperWidth = parseFloat(self.width) - _maxFixedWidth;
                        headPart.width = freeWrapperWidth + "px";
                    }
                    headPart.isHeader = true;
                    let $headerWrapper = v.createWrapper("0px", left, headPart);
                    pTable.owner.headers.push($headerWrapper); 
                    $headerWrapper.classList.add(HEADER);
//                    self.$container.appendChild($headerWrapper);
                    $frag.appendChild($headerWrapper);
                    let tablePart = v.process($headerWrapper, headPart);
                    let $tbl = tablePart.$table;
                    painters.push(tablePart.painter);
                    
                    if (headPart.containerClass === FIXED) {
                        left = (parseInt(left) + _maxFixedWidth + DISTANCE) + "px";
                        $fixedHeaderTbl = $tbl;
                        _fixedControlMap = tablePart.controlMap;
                    } else {
                        $fixedHeaderTbl.style.height = self.headerHeight;
                        $tbl.style.height = self.headerHeight;
                        top = (parseFloat(self.headerHeight) + DISTANCE) + "px";
                        _mafollicle[_currentPage][_currentSheet] = {};
                        _vessel().$hGroup = $tbl.querySelector("colgroup");
                        _vessel().$hBody = $tbl.querySelector("tbody");
                        _mafollicle[SheetDef][_currentSheet].hColArr = tablePart.cols;
                    }
                    headerWrappers.push($headerWrapper);
                    headerColGroup.push(tablePart.cols);
                    _.assignIn(controlMap, tablePart.controlMap);
                }
                
                if (i === self.headers.length - 1) {
                    _header = headPart;
                    _mafollicle[SheetDef][_currentSheet].levelStruct = headPart.levelStruct; 
                }
            });
            
            _headerWrappers = headerWrappers;
            let bodyHeight = parseFloat(self.height) - parseFloat(self.headerHeight);
            self.bodies.forEach((bodyPart, i) => {
                let $bodyWrapper: HTMLElement, alignLeft = 0;
                if (!_.isNil(bodyPart)) {
                    bodyPart.rowHeight = BODY_ROW_HEIGHT + "px";
                    
                    if (bodyPart.containerClass === FIXED) {
                        bodyPart.height = (bodyHeight - scrollWidth) + "px";
                        bodyPart.width = _maxFixedWidth + "px"; 
                    } else {
                        bodyPart.height = bodyHeight + "px";
                        bodyPart.width = (parseFloat(self.headers[i].width) + scrollWidth) + "px";
                        alignLeft = left;
                    }
                    
                    $bodyWrapper = v.createWrapper(top, alignLeft, bodyPart);
                    pTable.owner.bodies.push($bodyWrapper);
                    bodyWrappers.push($bodyWrapper);
                    $frag.appendChild($bodyWrapper);
                    
                    if (bodyPart.containerClass === FREE && !_.isNil($bodyWrapper)) {
                        bodyPart.overflow = "scroll";
                        tc.syncDoubDirVerticalScrolls(bodyWrappers);
                        if (!self.fixedSummaries || !self.fixedSummaries.columns) {
                            tc.syncHorizontalScroll(headerWrappers[i], $bodyWrapper);
                        }
                        tc.bindVertWheel($bodyWrapper, true);
                    } else {
                        tc.bindVertWheel($bodyWrapper);   
                    }
                    
                    let result = v.table($bodyWrapper, bodyPart);
                    if (bodyPart.containerClass === FREE) {
                        _vessel().$bGroup = result.$table.querySelector("colgroup");
                        _mafollicle[SheetDef][_currentSheet].bColArr = result.cols;
                    }
                    bodyColGroup.push(result.cols);
                }
            });
            
            _hasSum = !_.isNil(self.summaries.columns);
            let artifactOptions = { primaryKey: self.primaryKey, controlMap: controlMap, features: self.features, hasSum: _hasSum };
            _dataSource = _mafollicle[_currentPage].dataSource;
            _mafollicle[SheetDef][_currentSheet].controlMap = controlMap;
            _mafollicle[SheetDef][_currentSheet].painters = painters;
            v.construe(self.$container, bodyWrappers, artifactOptions);
            _bodyWrappers = bodyWrappers;
            let dWrapper = _hasFixed ? bodyWrappers[1] : bodyWrapper[0];
            _vessel().$bBody = dWrapper.querySelector("tbody");
            
            top = parseFloat(self.height) + DISTANCE - scrollWidth - SUM_HEIGHT;
            [ self.fixedSummaries, self.summaries ].filter(s => s && s.columns).forEach((sumPart, i) => {
                let alignLeft = i === 0 ? 0 : left;
                if (sumPart.containerClass === FREE + "-summaries") {
                    sumPart.width = self.headers[i].width;
                }
                
                let $sumDiv = v.createWrapper(top + "px", alignLeft, sumPart);
                $frag.appendChild($sumDiv);
                let $tbl = document.createElement("table");
                $sumDiv.appendChild($tbl);
                let $tbody = document.createElement("tbody");
                $tbl.appendChild($tbody);
                let $colGroup = document.createElement("colgroup");
                $tbl.insertBefore($colGroup, $tbody);
                let ptr, cols = [], $tr = document.createElement("tr");
                $tr.style.height = "27px";
                $tbody.appendChild($tr);
                
                if (sumPart.containerClass === FREE + "-summaries") {
                    _.forEach(bodyColGroup[1], c => {
                        let col = c.cloneNode(true);
                        $colGroup.appendChild(col);
                        cols.push(col);
                    });
                    
                    ptr = painters[1];
                    tc.syncDoubDirHorizontalScrolls([ headerWrappers[i], bodyWrappers[i], $sumDiv ]);
                    _mafollicle[SheetDef][_currentSheet].sumColArr = cols;
                } else {
                    _.forEach(bodyColGroup[0], c => {
                        let col = c.cloneNode(true);
                        $colGroup.appendChild(col);
                        cols.push(col);
                    });
                    
                    ptr = painters[0];
                }
                
                sumColGroup.push(cols);
                _.forEach(ptr.visibleColumns, c => {
                    let sum = _summaries[c.key]; 
                    let $td = _prtCell.cloneNode();
                    $tr.appendChild($td);
                    
                    if (!sum) return;
                    if (sum.calculator === "Time") {
                        let time = sum.total.asHours();
                        let hour = Math.floor(time);
                        let minute = (time - hour) * 60;
                        let roundMin = Math.round(minute);
                        let minuteStr = roundMin < 10 ? ("0" + roundMin) : String(roundMin);
                        $td.textContent = hour + ":" + minuteStr;
                    } else if (sum.calculator === "Number") {
                        $td.textContent = sum.total;
                    } else {
                        $td.textContent = sum.calculator;
                    }
                });
                
                _vessel().$sumGroup = $colGroup;
                _vessel().$sumBody = $tbody;
                sumWrappers.push($sumDiv);
            });
            
            _sumWrappers = sumWrappers;
            gp.imiPages($frag, top, self.width);
            gp.imiSheets($frag, _paging ? top + gp.PAGE_HEIGHT : top, self.width);
            _leftAlign = left;
            
            let sizeUi = { headerWrappers: headerWrappers, bodyWrappers: bodyWrappers,
                            sumWrappers: sumWrappers, headerColGroup: headerColGroup,
                            bodyColGroup: bodyColGroup, sumColGroup: sumColGroup };
            let freeAdjuster = new kt.ColumnAdjuster([ _maxFixedWidth, freeWrapperWidth ], self.headerHeight, sizeUi);
            kt._adjuster = freeAdjuster;
            freeAdjuster.handle();
            su.binding(self.$container);
            lch.checkUp(self.$container);
            
            self.$container.appendChild($frag);
            console.log(performance.now() - start);
        }
    }
    
    module tn {
        export const COLUMN_FIX = "ColumnFixing";
        export const SUMMARIES = "Summaries";
        export const PAGING = "Paging";
        export const SHEET = "Sheet";
        export const RESIZING = "Resizing";
        export const HEADER_STYLE = "HeaderStyles";
        export const CELL_STYLE = "CellStyles";
        
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
    
    module v {
        export const CELL_CLS = "mcell";
        export const DATA = "md";
        
        export function process($container: HTMLElement, options: any, isUpdate?: boolean) {
            let levelStruct = synthesizeHeaders(options);
            options.levelStruct = levelStruct;
            
            if (isUpdate && !_.isNil($container.style.maxWidth) && !_.isEmpty($container.style.maxWidth)) {
                let maxWidth = calcWidth(options.columns);
                if (!options.isHeader && options.overflow === "scroll") {
                    $container.style.maxWidth = (maxWidth + ti.getScrollWidth()) + "px";
                } else {
                    $container.style.maxWidth = maxWidth + "px";
                }
            }
            
            if (options.isHeader) {
                if (Object.keys(levelStruct).length > 1) {
                    return groupHeader($container, options, isUpdate);
                }
            } else {
                options.float = options.float === false ? false : true;
            }
            
            let result = table($container, options, isUpdate);
            let painter = paint($container, options);
            
            return { $table: result.$table, cols: result.cols, controlMap: result.controlMap, painter: painter };
        }
        
        /**
         * Group header.
         */
        function groupHeader($container: HTMLElement, options: any, isUpdate: boolean) {
            let $table = selector.create("table").html("<tbody></tbody>").addClass(options.containerClass + "-table")
                        .css({ position: "relative", "table-layout": "fixed", width: "100%", 
                                "border-collapse": "separate", "user-select": "none" }).getSingle();
            $container.appendChild($table);
            let $tbody = $table.getElementsByTagName("tbody")[0];
            if (!isUpdate) {
                $container.style.height = options.height;
                $container.style.width = options.width;
            }
            if (!util.isNullOrUndefined(options.overflow)) $container.style.overflow = options.overflow;
            else if (!util.isNullOrUndefined(options.overflowX) && !util.isNullOrUndefined(options.overflowY)) {
                $container.style.overflowX = options.overflowX;
                $container.style.overflowY = options.overflowY;
            }
            let $colGroup = document.createElement("colgroup"); 
            $table.insertBefore($colGroup, $tbody);
            let colGroup = generateColGroup($colGroup, options.columns, options.ntsControls);
            let painter: GroupHeaderPainter = new GroupHeaderPainter(options);
            painter.rows($tbody);
            return { $table: $table, cols: colGroup.cols, controlMap: colGroup.controlMap, painter: painter };
        }
        
        /**
         * Generate column group.
         */
        function generateColGroup($colGroup: HTMLElement, columns: any, ntsControls?: Array<any>) {
            let cols = [], controlMap = {};
            _.forEach(columns, function(col: any) {
                if (!_.isNil(col.group)) {
                    let colGroup = generateColGroup($colGroup, col.group, ntsControls);
                    if (colGroup) {
                        colGroup.cols.forEach(c => cols.push(c));
                        _.assignIn(controlMap, colGroup.controlMap);
                    }
                    return;
                }
                let $col = document.createElement("col");
                $col.style.width = col.width;
                $colGroup.appendChild($col);
                cols.push($col);
                
                if (ntsControls && col.ntsControl) {
                    let foundControl = _.find(ntsControls, c => c.name === col.ntsControl);
                    if (foundControl) controlMap[col.key] = foundControl;
                }
                
                if (col.hidden === true) $col.style.display = "none";
            });
            
            return { cols: cols, controlMap: controlMap };
        }
        
        /**
         * Table.
         */
        export function table($container: HTMLElement, options: any, isUpdate?: boolean) {
            let $table = document.createElement("table");
            $table.innerHTML = "<tbody></tbody>";
            $table.className = options.containerClass + "-table";
            $container.appendChild($table);
            let $tbody = $table.getElementsByTagName("tbody")[0];
            if (!isUpdate) {
                $container.style.height = options.height;
                $container.style.width = options.width;
            }
            if (!util.isNullOrUndefined(options.overflow)) $container.style.overflow = options.overflow;
            else if (!util.isNullOrUndefined(options.overflowX) && !util.isNullOrUndefined(options.overflowY)) {
                $container.style.overflowX = options.overflowX;
                $container.style.overflowY = options.overflowY;
            }
            let $colGroup = document.createElement("colgroup");
            $table.insertBefore($colGroup, $tbody);
            let colDef = generateColGroup($colGroup, options.columns, options.ntsControls);

            return { $table: $table, cols: colDef.cols, controlMap: colDef.controlMap };
        }
        
        export function paint($container: HTMLElement, options: any) {
            let dataSource;
            if (!_.isNil(options.dataSource)) {
                 dataSource = options.dataSource;   
            } else {
                let item = {};
                _.forEach(options.columns, function(col: any) {
                    item[col.key] = col.headerText;
                });
                dataSource = [item]; 
            }
            
            return normal($container, dataSource, options);
        }
        
        /**
         * Normal.
         */
        export function normal($container: HTMLElement, dataSource: Array<any>, options: any) {
            let painter: Painter = new Painter($container, options);
            $.data($container, lo.CANON, { _origDs: _.cloneDeep(dataSource), dataSource: dataSource, primaryKey: options.primaryKey, painter: painter });
            let $tbody = $container.querySelector("tbody");
            _.forEach(dataSource, function(item: any, index: number) {
                let $tr = painter.row(item, undefined, index);
                $tbody.appendChild($tr);
            });
            return painter;
        }
        
        /**
         * Begin.
         */
        export function construe($container: HTMLElement, containers: Array<HTMLElement>, options: any, single?: any) {
            if (options.features) {
                let cellStyleFt = tn.find(options.features, tn.CELL_STYLE);    
                if (cellStyleFt) {
                    if (_cellStates) options.states = _cellStates;
                    else {
                        [ "states" ].forEach(ft => {
                            if (cellStyleFt[ft]) {
                                let typeFt = _.groupBy(cellStyleFt[ft], "rowId");
                                _.forEach(typeFt, (value, key) => {
                                    typeFt[key] = _.groupBy(typeFt[key], (item) => {
                                        return item.columnKey;
                                    });
                                });
                                
                                _cellStates = typeFt;
                                options[ft] = _cellStates; 
                            }
                        });
                    }
                }
            }
            
            if (!_cloud) _cloud = new intan.Cloud(containers, options);
            let res = single ? _cloud.renderSideRows(true) : _cloud.renderRows(true);
            if (!res) return;
            let start = res.start, end = res.end, cursor;
            if (_.isNil(_mDesc)) {
                _mDesc = {};
                $.data($container, lo.DESC, _mDesc);
                if (!_.isNil(res.fixedColIdxes)) {
                    _mDesc.fixedColIdxes = res.fixedColIdxes;
                }
                
                if (_.isNil(_mDesc.fixedRows)) { 
                    _mDesc.fixedRows = [];
                    _mDesc.fixedRowElements = [];
                }
                _mDesc.rows = [];
                _mDesc.rowElements = [];
            }
               
            if (!_.isNil(res.colIdxes) && (_.isNil(_mDesc.colIdxes) || _mDesc.colIdxes.length === 0)) {
                _mDesc.colIdxes = res.colIdxes;
            }
            
            for (let i = start; i <= end; i++) {
                cursor = i - start;
                if (res.fixedRows) {
                    _mDesc.fixedRows[i] = res.fixedRows[cursor];
                    _mDesc.fixedRowElements[i] = res.fixedRowElements[cursor];
                }
                _mDesc.rows[i] = res.rows[cursor];
                _mDesc.rowElements[i] = res.rowElements[cursor];
            }
            
            if (!_vessel()) {
                _mafollicle[_currentPage][_currentSheet] = {};
            }
            _vessel().desc = _mDesc;
            _vessel().errors = _errors;
            _vessel().dirties = _dirties;
            _vessel().zeroHidden = _zeroHidden;
            
            if (!_.isNil(_currentPage)) {
                let openRange = _pageSize * _currentPage;
                let closeRange = _pageSize * (_currentPage + 1) - 1; 
            }
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
                        if (util.isNullOrUndefined(col.colspan)) {
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
                if (!_.isNil(col.group)) {
                    colCount = col.group.length;
                    noGroup++;
                    let ret: any = peelStruct(col.group, level, currentLevel + 1);
                    if (!util.isNullOrUndefined(ret)) {
                        colCount += ret;
                    }
                    clonedCol.colspan = colCount;
                }
                
                if (_.isNil(level[currentLevel])) {
                    level[currentLevel] = [];
                }
                
                level[currentLevel].push(clonedCol);
                colspan += colCount;
                
                if (col.constraint) {
                    let validator = new hpl.ColumnFieldValidator(col.headerText, col.constraint.primitiveValue, col.constraint);
                    _validators[col.key] = validator;
                }
                
                let linkType = col.ntsType; 
                if (linkType) {
                    let parts = linkType.split("_");
                    if (!parts || parts.length !== 2) return; 
                    if (parts[0] === "comboCode") {
                        _specialColumn[col.key] = parts[1];
                        _specialColumn[parts[1]] = col.key;
                    } else {
                        _specialLinkColumn[col.key] = { column: parts[1], changed: col.onChange };
                    }
                }
            });
            
            return colspan !== 0 ? (colspan - noGroup) : undefined;
        }
        
        export function getConstraintName(key: string) {
            let column = _columnsMap[key];
            if (!column) return;
            let constraint = column.constraint;
            return constraint.primitiveValue ? ui.validation.getConstraint(constraint.primitiveValue).valueType
                        : constraint.cDisplayType;
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
                let columns = ti.classifyColumns(options);
                this.visibleColumns = columns.visibleColumns;
                this.hiddenColumns = columns.hiddenColumns;
                this.visibleColumnsMap = ti.getColumnsMap(this.visibleColumns);
                this.hiddenColumnsMap = ti.getColumnsMap(this.hiddenColumns);
                _.assignIn(_columnsMap, this.visibleColumnsMap); 
            }
        }
        
        export class Painter extends Conditional {
            $container: HTMLElement;
            styles: any;
            
            constructor($container: HTMLElement, options: any) {
                super(options);
                this.$container = $container;
                
                if (options.features) {
                    let headerStyle = tn.find(options.features, tn.HEADER_STYLE);
                    if (headerStyle) {
                        this.styles = _.groupBy(headerStyle.columns, "key");
                    }
                }
                
                if (!_.isNil(options.levelStruct)) {
                    this.columnsMap = ti.columnsMapFromStruct(options.levelStruct);
                } else {
                    this.columnsMap = _.groupBy(options.columns, "key");
                }
            }
            
            cell(rData: any, rowIdx: number, key: string): HTMLElement {
                let self = this;
                let cData = rData[key]; 
                let data = cData;
                let column: any = self.columnsMap[key];
                if (util.isNullOrUndefined(column)) return;
                let ws = column.css && column.css.whiteSpace ? column.css.whiteSpace : "nowrap";
                let td = document.createElement("td");
                $.data(td, lo.VIEW, rowIdx + "-" + key);
                
                let tdStyle = "";
                tdStyle += "; border-width: 1px; overflow: hidden; white-space: " 
                            + ws + "; position: relative;";
                
                if (!self.visibleColumnsMap[key]) tdStyle += "; display: none;";
                let hStyle;
                if (self.styles && (hStyle = self.styles[key])) {
                    _.forEach(hStyle[0].colors, c => {
                        if (c.indexOf('#') === 0) {
                            tdStyle += "; background-color: " + c + ";";
                        } else td.classList.add(c);
                    });
                }
                
                td.innerHTML = data;
                td.style.cssText += tdStyle;
                
                return td;
            }
            
            row(data: any, config: any, rowIdx: number): HTMLElement {
                let self = this;
                let tr: any = document.createElement("tr");
                if (config) {
                    tr.style.height = parseFloat(config.css.height) + "px";
                }
                
                _.forEach(Object.keys(data), function(key: any, index: number) {
                    if (!self.visibleColumnsMap[key] && !self.hiddenColumnsMap[key]) return;
                    let cell = self.cell(data, rowIdx, key);
                    tr.appendChild(cell);
                });
                
                return tr;
            }
        }
        
        export class GroupHeaderPainter extends Conditional {
            levelStruct: any;
            styles: any;
            
            constructor(options: any) {
                super(options);
                this.levelStruct = options.levelStruct;
                this.columnsMap = ti.columnsMapFromStruct(this.levelStruct);
                if (options.features) {
                    let styleFt = tn.find(options.features, tn.HEADER_STYLE);
                    if (styleFt) {
                        this.styles = _.groupBy(styleFt.columns, "key");
                    }
                } 
            }
            
            /**
             * Cell.
             */
            cell(text: any, rowIdx: number, cell: any): HTMLElement {
                let self = this;
                let $td = document.createElement("td");
                $.data($td, lo.VIEW, rowIdx + "-" + cell.key);
                let tdStyle = "; border-width: 1px; overflow: hidden; white-space: nowrap; border-collapse: collapse;";
                if (!_.isNil(cell.rowspan) && cell.rowspan > 1) $td.setAttribute("rowspan", cell.rowspan);
                if (!_.isNil(cell.colspan) && cell.colspan > 1) $td.setAttribute("colspan", cell.colspan);
                else if (_.isNil(cell.colspan) && !self.visibleColumnsMap[cell.key]) tdStyle += "; display: none;";
                let column = self.columnsMap[cell.key]; 
                
                let hStyle;
                if (self.styles && ((hStyle = self.styles[cell.key])
                    || (cell.group && (hStyle = self.styles[cell.group[0].key])))) {
                    _.forEach(hStyle[0].colors, c => {
                        if (c.indexOf('#') === 0) {
                            tdStyle += "; background-color: " + c + ";";
                        } else $td.classList.add(c);
                    });
                }
                
                $td.innerHTML = text;
                $td.style.cssText += tdStyle;
                return $td;
            }
            
            /**
             * Rows.
             */
            rows($tbody: HTMLElement) {
                let self = this;
                _.forEach(Object.keys(self.levelStruct), function(rowIdx: any) {
                    
                    let $tr = document.createElement("tr");
                    let oneLevel = self.levelStruct[rowIdx];
                    _.forEach(oneLevel, function(cell: any) {
                        if (!self.visibleColumnsMap[cell.key] && !self.hiddenColumnsMap[cell.key]
                            && _.isNil(cell.colspan)) return;
                        let $cell = self.cell(cell.headerText, rowIdx, cell);
                        $tr.appendChild($cell);
                    });
                    
                    $tbody.appendChild($tr);
                });
            }
        }
        
        export class ConcurrentPainter {
            painter: any;
            painters: Array<Painter>;
            controlMap: any;
            primaryKey: string;
            columns: Array<string> = [];
            states: any;
            protoCell: HTMLElement;
            protoRow: HTMLElement;
            
            constructor(ui: any) {
                this.revive();
                this.primaryKey = ui.primaryKey;
                this.states = ui.states;
                this.protoRow = document.createElement("tr");
                this.protoCell = document.createElement("td");
            }
            
            revive() {
                this.painters = _mafollicle[SheetDef][_currentSheet].painters;
                this.columns = [];
                _.forEach(this.painters, p => _.forEach(p.visibleColumns, c => {
                    this.columns.push(c.key);
                }));
                
                this.controlMap = _mafollicle[SheetDef][_currentSheet].controlMap;
            }
            
            cell(rData: any, rowIdx: number, key: string, fixed: boolean) {
                let self = this;
                let cData = rData[key]; 
                let data = cData, columnsMap, visibleColumnsMap;
                columnsMap = self.painters[fixed ? 0 : 1].columnsMap;
                visibleColumnsMap = self.painters[fixed ? 0 : 1].visibleColumnsMap;
                
                let column: any = columnsMap[key];
                if (_.isNil(column)) return;
                let ws = column.css && column.css.whiteSpace ? column.css.whiteSpace : "nowrap";
//                let td = document.createElement("td");
                let td = self.protoCell.cloneNode(true);
                td.classList.add(CELL_CLS);
                td.tabIndex = -1;
                $.data(td, lo.VIEW, rowIdx + "-" + key);
                
                let tdStyle = "";
                tdStyle += "; border-width: 1px; overflow: hidden; white-space: " 
                            + ws + "; position: relative; padding: 0px 2px;";
                
                let col = visibleColumnsMap[key];
                if (!col) tdStyle += "; display: none;";
                else if (col[0].columnCssClass === hpl.CURRENCY_CLS) {
                    td.classList.add(hpl.CURRENCY_CLS);
                }
                let controlDef = self.controlMap[key];
                
                let id = rData[self.primaryKey];
                let rState, cState;
                if (self.states && (rState = self.states[id]) && (cState = rState[key])) {
                    _.forEach(cState, s => {
                        _.forEach(s.state, st => {
                            if (st.indexOf('#') === 0) {
                                tdStyle += "; color: " + cState + ";";
                            } else td.classList.add(st);
                        });
                    });
                    rState = null;
                    cState = null;
                }
                
                if (column.ntsControl === dkn.LABEL) {
                    td.classList.add(dkn.LABEL_CLS);
                    td.innerHTML = data;
                } else if (controlDef) {
                    let ui: any = {
                        rowIdx: rowIdx,
                        rowId: id,
                        columnKey: key,
                        controlDef: controlDef,
                        update: (v, i) => {
                            su.wedgeCell(_$grid[0], { rowIdx: (_.isNil(i) ? rowIdx : i), columnKey: key }, v)
                        },
                        deleteRow: su.deleteRow,
                        initValue: data,
                        rowObj: rData,
                        enable: !td.classList.contains(color.Disable)
                    };
                    
                    let control = dkn.getControl(controlDef.controlType);
                    if (control) {
                        let $control = control(ui);
                        if (controlDef.controlType !== dkn.COMBOBOX) {
                            td.appendChild($control);
                        } else {
                            td.innerHTML = $control.name;
                            $.data(td, "code", $control.code);
                        }
                    }
                } else if (_zeroHidden && ti.isZero(data)) {
                    td.textContent = "";
                    dkn.textBox(key);
                } else {
                    let formatted = su.format(column, data);
                    td.innerHTML = formatted;
                    dkn.textBox(key);
                }
                td.style.cssText += tdStyle;
                $.data(td, DATA, data);
                
                let sum = _summaries[key];
                if (sum) {
                    switch (sum.calculator) {
                        case "Time":
                            sum.total.add(moment.duration(data));
                            break;
                        case "Number": 
                            sum.total += (!_.isNil(data) ? parseFloat(data) : 0);
                            break;
                    } 
                }
                
                return td;
            }
            
            row(data: any, config: any, rowIdx: number) {
                let self = this;
                let fixedVColumns, fixedCount;
                if (rowIdx === 0) {
                    fixedVColumns = self.painters[0].visibleColumns;
                    fixedCount = fixedVColumns.length;
                }
                let fixedVColumnsMap = self.painters[0].visibleColumnsMap;
                let dVColumnsMap = self.painters[1].visibleColumnsMap; 
                let fixedColIdxes = {}, colIdxes = {},
                    fixedElements = [], elements = [],
                    fixedTr, tr: any = self.protoRow.cloneNode(true); //document.createElement("tr");
                if (fixedVColumnsMap) {
//                    fixedTr = document.createElement("tr");
                    fixedTr = self.protoRow.cloneNode(true);
                }
                
                if (config) {
                    fixedTr.style.height = parseFloat(config.css.height) + "px";
                    tr.style.height = parseFloat(config.css.height) + "px";
                }
                
                _.forEach(self.columns, function(key: any, index: number) {
                    let cell;
                    if (dVColumnsMap[key]) {
                        cell = self.cell(data, rowIdx, key);
                        tr.appendChild(cell);
                        elements.push(cell);
                        if (rowIdx === 0) colIdxes[key] = index - fixedCount;
                    } else {
                        cell = self.cell(data, rowIdx, key, true);
                        fixedTr.appendChild(cell);
                        fixedElements.push(cell);
                        if (rowIdx === 0) fixedColIdxes[key] = index;
                    }
                });
                
                fixedTr.addXEventListener(ssk.MOUSE_OVER, evt => {
                    self.hoover(evt);
                });
                
                tr.addXEventListener(ssk.MOUSE_OVER, evt => {
                    self.hoover(evt);
                });
                
                fixedTr.addXEventListener(ssk.MOUSE_OUT, evt => {
                    self.hoover(evt, true);
                });
                
                tr.addXEventListener(ssk.MOUSE_OUT, evt => {
                    self.hoover(evt, true);
                });
                
                let ret = { fixedRow: fixedTr, row: tr, fixedElements: fixedElements, elements: elements }; 
                if (rowIdx === 0) {
                    ret.fixedColIdxes = fixedColIdxes;
                    ret.colIdxes = colIdxes;
                }
                
                return ret;
            }
            
            hoover(evt: any, out?: boolean) {
                let $tCell = evt.target;
                if (!selector.is($tCell, "td." + CELL_CLS)) return;
                let coord = ti.getCellCoord($tCell);
                if (!coord) return;
                let elms;
                if (_mDesc.fixedRows && (elms = _mDesc.fixedRows[coord.rowIdx])) {
                    _.forEach(elms, c => {
                        if (!c.classList.contains(color.HOVER) && !out) {
                            c.classList.add(color.HOVER);
                            _hr = coord.rowIdx;
                        } else if (c.classList.contains(color.HOVER) && out) {
                            c.classList.remove(color.HOVER);
                        }
                    });
                }
                
                if (_mDesc.rows && (elms = _mDesc.rows[coord.rowIdx])) {
                    _.forEach(elms, c => {
                        if (!c.classList.contains(color.HOVER) && !out) {
                            c.classList.add(color.HOVER);
                            _hr = coord.rowIdx;
                        } else if (c.classList.contains(color.HOVER) && out) {
                            c.classList.remove(color.HOVER);
                        }
                    });
                }
            }
        }
        
        export class SidePainter {
            columnsMap: any;
            visibleColumns: Array<any>;
            visibleColumnsMap: any;
            controlMap: any;
            primaryKey: string;
            states: any;
            protoCell: HTMLElement;
            protoRow: HTMLElement;
            
            constructor(ui: any) {
                this.revive();
                this.primaryKey = ui.primaryKey;
                this.states = ui.states;
                this.protoRow = document.createElement("tr");
                this.protoCell = document.createElement("td");
            }
            
            revive() {
                let colCls = ti.classifyColumns({ columns: _cstifle() });
                this.visibleColumns = colCls.visibleColumns;
                this.visibleColumnsMap = ti.getColumnsMap(this.visibleColumns);
                this.controlMap = _mafollicle[SheetDef][_currentSheet].controlMap;
                let levelStruct = _mafollicle[SheetDef][_currentSheet].levelStruct;
                
                if (!_.isNil(levelStruct)) {
                    this.columnsMap = ti.columnsMapFromStruct(levelStruct);
                } else {
                    this.columnsMap = _.groupBy(_cstifle(), "key");
                }
            }
            
            cell(rData: any, rowIdx: number, key: string, fixed: boolean) {
                let self = this;
                let cData = rData[key]; 
                let data = cData;
                
                let column = self.columnsMap[key];
                if (_.isNil(column)) return;
                let ws = column.css && column.css.whiteSpace ? column.css.whiteSpace : "nowrap";
//                let td = document.createElement("td");
                let td = self.protoCell.cloneNode(true);
                td.classList.add(CELL_CLS);
                td.tabIndex = -1;
                $.data(td, lo.VIEW, rowIdx + "-" + key);
                
                let tdStyle = "";
                tdStyle += "; border-width: 1px; overflow: hidden; white-space: " 
                            + ws + "; position: relative; padding: 0px 2px;";
                
                let col = self.visibleColumnsMap[key];
                if (!col) tdStyle += "; display: none;";
                else if (col[0].columnCssClass === hpl.CURRENCY_CLS) {
                    td.classList.add(hpl.CURRENCY_CLS);
                }
                let controlDef = self.controlMap[key];
                
                let id = rData[self.primaryKey];
                let rState, cState;
                if (self.states && (rState = self.states[id]) && (cState = rState[key])) {
                    _.forEach(cState, s => {
                        _.forEach(s.state, st => {
                            if (st.indexOf('#') === 0) {
                                tdStyle += "; color: " + cState + ";";
                            } else td.classList.add(st);
                        });
                    });
                    rState = null;
                    cState = null;
                }
                
                if (column.ntsControl === dkn.LABEL) {
                    td.classList.add(dkn.LABEL_CLS);
                    td.innerHTML = data;
                } else if (controlDef) {
                    let ui: any = {
                        rowIdx: rowIdx,
                        rowId: id,
                        columnKey: key,
                        controlDef: controlDef,
                        update: (v, i) => {
                            su.wedgeCell(_$grid[0], { rowIdx: (_.isNil(i) ? rowIdx : i), columnKey: key }, v)
                        },   
                        deleteRow: su.deleteRow,
                        initValue: data,
                        rowObj: rData,
                        enable: !td.classList.contains(color.Disable)
                    };
                    
                    let control = dkn.getControl(controlDef.controlType);
                    if (control) {
                        let $control = control(ui);
                        if (controlDef.controlType !== dkn.COMBOBOX) {
                            td.appendChild($control);
                        } else {
                            td.innerHTML = $control.name;
                            $.data(td, "code", $control.code);
                        }
                    }
                } else if (_zeroHidden && ti.isZero(data)) {
                    td.textContent = "";
                    dkn.textBox(key);
                } else {
                    let formatted = su.format(column, data);
                    td.innerHTML = formatted;
                    dkn.textBox(key);
                }
                td.style.cssText += tdStyle;
                $.data(td, DATA, data);
                
                let sum = _summaries[key];
                if (sum) {
                    switch (sum.calculator) {
                        case "Time":
                            sum.total.add(moment.duration(data));
                            break;
                        case "Number": 
                            sum.total += (!_.isNil(data) ? parseFloat(data) : 0);
                            break;
                    } 
                }
                
                return td;
            }
            
            row(data: any, config: any, rowIdx: number) {
                let self = this; 
                let colIdxes = {}, elements = [], 
                    tr: any = self.protoRow.cloneNode(true); //document.createElement("tr");
                
                if (config) {
                    tr.style.height = parseFloat(config.css.height) + "px";
                }
                
                _.forEach(self.visibleColumns, function(col: any, index: number) {
                    let cell, key = col.key;
                    cell = self.cell(data, rowIdx, key);
                    tr.appendChild(cell);
                    elements.push(cell);
                    if (rowIdx === 0) colIdxes[key] = index;
                });
                
                let ret = { row: tr, elements: elements }; 
                if (rowIdx === 0) {
                    ret.colIdxes = colIdxes;
                }
                
                return ret;
            }
        }
        
        /**
         * Extra.
         */
        export function extra(className: string, height: number) {
            let element = document.createElement("tr");
            element.style.height = height + "px";
            ti.addClass(element, "extable-" + className);
            return element;
        }
        
        export function wrapperStyles(top: string, left: string, width: string, maxWidth?: string, height: string) {
            let style: any = { 
                position: "absolute",
                overflow: "hidden",
                top: top,
                left: left,
                width: width,
                border: "solid 1px #CCC"
            };
            
            if (maxWidth) {
                style.maxWidth = maxWidth;
                if (parseFloat(maxWidth) < parseFloat(width)) style.width = maxWidth;
            }
            
            if (height) {
                style.height = height;
            }
            
            return style;   
        }
        
        export function calcWidth(columns: Array<any>) {
            let width = 0;
            columns.forEach(function(c, i) {
                if (c.hidden === true) return;
                if (c.group) {
                    width += calcWidth(c.group);
                    return;
                }
                width += parseFloat(c.width);
            });
            return width;
        }
        
        export function createWrapper(top: string, left: string, options: any) {
            let style, width, maxWidth;
            if (options.containerClass === FREE) {
                if (!_maxFreeWidth || options.new) {
                    _maxFreeWidth = calcWidth(options.columns);
                } 
                style = wrapperStyles(top, left, options.width, _maxFreeWidth + "px", options.height); 
            } else if (options.containerClass === FIXED) {
                if (!_maxFixedWidth || options.new) {
                    _maxFixedWidth = calcWidth(options.columns);
                }
                style = wrapperStyles(top, left, _maxFixedWidth + "px", undefined, options.height);
            } else if (options.containerClass === gp.PAGING_CLS || options.containerClass === gp.SHEET_CLS) {
                style = wrapperStyles(top, left, options.width, undefined, options.height);
                style["background-color"] = "#E9E9E9";
                style["border"] = "1px solid #dddddd";
                style["color"] = "#333333";
            } else {
                width = options.containerClass === FIXED + "-summaries" ? _maxFixedWidth + "px" : options.width;
                style = wrapperStyles(top, left, width, undefined, options.height);
                style["z-index"] = 1; 
                style["background-color"] = "#F6F6F6";
            }
            
            return selector.create("div").data(lo.MPART, options.containerClass)
                        .addClass(options.containerClass)
                        .css(style).getSingle();
        }
    }
    
    module intan {
        export const TOP_SPACE = "top-space";
        export const BOTTOM_SPACE = "bottom-space";
        export const NULL = null;
        
        export class Cloud {
            $fixedContainer: HTMLElement;
            $container: HTMLElement;
            options: any;
            rowsOfBlock: number;
            blocksOfCluster: number;
            rowHeight: number;
            blockHeight: number;
            clusterHeight: number;
            primaryKey: string;
            topOffset: number;
            bottomOffset: number;
            currentCluster: number;
            startIndex: number;
            endIndex: number;
            hasSum: boolean;
            painter: v.ConcurrentPainter;
            sidePainter: v.SidePainter;
            
            constructor(containers: Array<any>, options: any) {
                this.$fixedContainer = containers[0];
                this.$container = containers[1];
                this.options = options;
                this.primaryKey = options.primaryKey;
                this.rowsOfBlock = options.rowsOfBlock || 30;
                this.blocksOfCluster = options.blocksOfCluster || 3;
                this.rowHeight = parseInt(BODY_ROW_HEIGHT);
                this.blockHeight = this.rowsOfBlock * this.rowHeight;
                this.clusterHeight = this.blockHeight * this.blocksOfCluster;
                this.hasSum = options.hasSum;
                let ui = { 
                            primaryKey: this.primaryKey, 
                            states: options.states,
                            levelStruct: options.levelStruct
                        };
                this.painter = new v.ConcurrentPainter(ui);
                this.sidePainter = new v.SidePainter(ui);
                this.onScroll();
            }
            
            /**
             * Get cluster no.
             */
            getClusterNo() {
                return Math.floor(this.$container.scrollTop / (this.clusterHeight - this.blockHeight));
            }
            
            /**
             * Render rows.
             */
            renderRows(manual?: boolean) {
                let self = this;
                let clusterNo = self.getClusterNo();
                if (manual) self.currentCluster = clusterNo;
                if (_dataSource.length < self.rowsOfBlock) {
                    self.topOffset = 0;
                    self.bottomOffset = 0;
                }
                
                let rowsOfCluster = self.blocksOfCluster * self.rowsOfBlock;
                let startRowIdx = _start = self.startIndex = Math.max((rowsOfCluster - self.rowsOfBlock) * clusterNo, 0);
                let endRowIdx = self.endIndex = startRowIdx + rowsOfCluster;
                _end = endRowIdx - 1;
                self.topOffset = Math.max(startRowIdx * self.rowHeight, 0);
                self.bottomOffset = Math.max((_dataSource.length - endRowIdx) * self.rowHeight, 0);
                let rowConfig = { css: { height: self.rowHeight } };
                
                let containerElm = self.$container;
                let fixedTbody, tbody = document.createElement("tbody");
                let topSpace = v.extra(TOP_SPACE, self.topOffset);
                
                if (self.$fixedContainer) {
                    fixedTbody = document.createElement("tbody");
                    fixedTbody.appendChild(topSpace.cloneNode(true));
                }
                
                tbody.appendChild(topSpace);
                let res = {}, fixedRows = [], rows = [], fixedRowElements = [], rowElements = [], min, max;
                for (let i = startRowIdx; i < endRowIdx; i++) {
                    if (_.isNil(_dataSource[i])) continue;
                    let rElm;
                    
                    if (_mDesc && _mDesc.rowElements && (rElm = _mDesc.rowElements[i])) {
                        tbody.appendChild(rElm);
                        if (self.$fixedContainer) {
                            fixedTbody.appendChild(_mDesc.fixedRowElements[i]);
                        }
                        continue;
                    }
                    
                    if (_.isNil(min)) min = i;
                    max = Math.max(_.isNil(min) ? startRowIdx : min, i);
                    let rowElms = self.painter.row(_dataSource[i], rowConfig, i);
                    tbody.appendChild(rowElms.row);
                    rowElements.push(rowElms.row);
                    
                    if (self.$fixedContainer) {
                        fixedTbody.appendChild(rowElms.fixedRow);
                        fixedRowElements.push(rowElms.fixedRow);
                    }
                    
                    fixedRows.push(rowElms.fixedElements);
                    rows.push(rowElms.elements);
                    if (i === 0) {
                        res.fixedColIdxes = rowElms.fixedColIdxes;
                        res.colIdxes = rowElms.colIdxes;
                    }
                } 
                
                let bottomSpace = v.extra(BOTTOM_SPACE, self.hasSum ? self.bottomOffset + SUM_HEIGHT : self.bottomOffset);
                tbody.appendChild(bottomSpace);
                containerElm.querySelector("table").replaceChild(tbody, containerElm.getElementsByTagName("tbody")[0]);
                
                if (self.$fixedContainer) {
                    fixedTbody.appendChild(bottomSpace.cloneNode(true));
                    self.$fixedContainer.querySelector("table").replaceChild(fixedTbody, self.$fixedContainer.getElementsByTagName("tbody")[0]);
                }
                
                if (rows.length === 0) return;
                
                res.fixedRows = fixedRows;
                res.rows = rows;
                res.fixedRowElements = fixedRowElements;
                res.rowElements = rowElements;
                res.start = !_.isNil(min) ? min : startRowIdx;
                res.end = max;
                
                setTimeout(() => {
                    ssk.trigger(self.$container, ssk.RENDERED);
                }, 0);
                
                return res;
            }
            
            /**
             * OnScroll.
             */
            onScroll() {
                let self = this;
                self.$container.removeXEventListener(ssk.SCROLL_EVT + ".detail");
                self.$container.addXEventListener(ssk.SCROLL_EVT + ".detail", function() {
                    let inClusterNo = self.getClusterNo();
                    if (self.currentCluster !== inClusterNo) {
                        self.currentCluster = inClusterNo;
                        let res = self.renderRows();
                        let hCols;
                        if (!_.isNil(_hr) && (hCols = _mDesc.rows[_hr])) {
                            _.forEach(hCols, c => {    
                                if (c.classList.contains(color.HOVER)) {
                                    c.classList.remove(color.HOVER);
                                }
                            });
                        }
                        
                        if (!_.isNil(_hr) && (hCols = _mDesc.fixedRows[_hr])) {
                            _.forEach(hCols, c => {
                                if (c.classList.contains(color.HOVER)) {
                                    c.classList.remove(color.HOVER);
                                }
                            });
                        }
                        
                        if (!res) return;
                        let start = res.start, end = res.end, cursor;
                        for (let i = start; i <= end; i++) {
                            cursor = i - start;
                            _mDesc.fixedRows[i] = res.fixedRows[cursor];
                            _mDesc.rows[i] = res.rows[cursor];
                            _mDesc.fixedRowElements[i] = res.fixedRowElements[cursor];
                            _mDesc.rowElements[i] = res.rowElements[cursor];
                        }
                    }
                });
            }
            
            renderSideRows(manual?: boolean) {
                let self = this;
                let clusterNo = self.getClusterNo();
                if (manual) self.currentCluster = clusterNo;
                if (_dataSource.length < self.rowsOfBlock) {
                    self.topOffset = 0;
                    self.bottomOffset = 0;
                }
                
                let rowsOfCluster = self.blocksOfCluster * self.rowsOfBlock;
                let startRowIdx = _start = self.startIndex = Math.max((rowsOfCluster - self.rowsOfBlock) * clusterNo, 0);
                let endRowIdx = self.endIndex = startRowIdx + rowsOfCluster;
                _end = endRowIdx - 1;
                self.topOffset = Math.max(startRowIdx * self.rowHeight, 0);
                self.bottomOffset = Math.max((_dataSource.length - endRowIdx) * self.rowHeight, 0);
                let rowConfig = { css: { height: self.rowHeight } };
                
                let containerElm = self.$container;
                let tbody = document.createElement("tbody");
                let topSpace = v.extra(TOP_SPACE, self.topOffset);
                
                tbody.appendChild(topSpace);
                let res = {}, rows = [], rowElements = [], min, max;
                for (let i = startRowIdx; i < endRowIdx; i++) {
                    if (_.isNil(_dataSource[i])) continue;
                    let rElm;
                    
                    if (_mDesc && _mDesc.rowElements && (rElm = _mDesc.rowElements[i])) {
                        tbody.appendChild(rElm);
                        continue;
                    }
                    
                    if (_.isNil(min)) min = i;
                    max = Math.max(_.isNil(min) ? startRowIdx : min, i);
                    let rowElms = self.sidePainter.row(_dataSource[i], rowConfig, i);
                    tbody.appendChild(rowElms.row);
                    rowElements.push(rowElms.row);
                    rows.push(rowElms.elements);
                    
                    if (i === 0) {
                        res.colIdxes = rowElms.colIdxes;
                    }
                } 
                
                let bottomSpace = v.extra(BOTTOM_SPACE, self.hasSum ? self.bottomOffset + SUM_HEIGHT : self.bottomOffset);
                tbody.appendChild(bottomSpace);
                containerElm.querySelector("table").replaceChild(tbody, containerElm.getElementsByTagName("tbody")[0]);
                
                if (rows.length === 0) return;
                
                res.rows = rows;
                res.rowElements = rowElements;
                res.start = !_.isNil(min) ? min : startRowIdx;
                res.end = max;
                
                setTimeout(() => {
                    ssk.trigger(self.$container, ssk.RENDERED);
                }, 0);
                
                return res;
            }
        }
    }
    
    module tc {
        export let SCROLL_SYNCING = "scroll-syncing";
        export let VERT_SCROLL_SYNCING = "vert-scroll-syncing";
        
        /**
         * Bind vertWheel.
         */
        export function bindVertWheel($container: HTMLElement, showY?: boolean) {
            let $_container = $($container);
            $container.addXEventListener(ssk.MOUSE_WHEEL, function(event: any) {
                let delta = event.deltaY;
                let direction = delta < 0 ? -1 : 1;
                let value = $_container.scrollTop();
//                $container.stop().animate({ scrollTop: value }, 10);
                let os = ti.isIE() ? 25 : 50;
                $_container.scrollTop(value + direction * os);
                event.preventDefault();
                event.stopImmediatePropagation();
            });
            if (!showY && $container.style.overflowY !== "hidden") {
                $container.style.overflowY = "hidden";
            }
        }
        
        /**
         * Unbind vertWheel.
         */
        export function unbindVertWheel($container: HTMLElement) {
            $container.removeXEventListener(ssk.MOUSE_WHEEL);
            $container.style.overflowY = "scroll";
        }
        
        /**
         * Sync scrolls.
         */
        export function syncDoubDirHorizontalScrolls(wrappers: Array<HTMLElement>) {
            _.forEach(wrappers, function($main: HTMLElement, index: number) {
                if (!$main) return;
                $main.addXEventListener(ssk.SCROLL_EVT, function() {
                    _.forEach(wrappers, function($depend: HTMLElement, i: number) {
                        if (i === index || !$depend) return;
                        let mainSyncing = $.data($main, SCROLL_SYNCING);
                        if (!mainSyncing) {
                            $.data($depend, SCROLL_SYNCING, true);
                            $depend.scrollLeft = $main.scrollLeft;
                        }
                    });
                    $.data($main, SCROLL_SYNCING, false);
                });
            });
        }
        
        /**
         * Sync scrolls.
         */
        export function syncDoubDirVerticalScrolls(wrappers: Array<HTMLElement>) {
            _.forEach(wrappers, function($main: HTMLElement, index: number) {
                    $main.addXEventListener(ssk.SCROLL_EVT, function(event: any) {
                        _.forEach(wrappers, function($depend: HTMLElement, i: number) {
                            if (i === index) return;
                            let mainSyncing = $.data($main, VERT_SCROLL_SYNCING);
                            if (!mainSyncing) {
                                $.data($depend, VERT_SCROLL_SYNCING, true);
                                $depend.scrollTop = $main.scrollTop;
                            }
                        });
                        $.data($main, VERT_SCROLL_SYNCING, false);
                    });
            });
        }
        
        /**
         * Sync scroll.
         */
        export function syncHorizontalScroll($headerWrap: HTMLElement, $bodyWrap: HTMLElement) {
            $bodyWrap.addXEventListener(ssk.SCROLL_EVT, function() {
                $headerWrap.scrollLeft = $bodyWrap.scrollLeft;
            });
        }
        
        /**
         * Sync scroll.
         */
        export function syncVerticalScroll($pivotBody: HTMLElement, bodyWraps: Array<HTMLElement>) {
            $pivotBody.addXEventListener(ssk.SCROLL_EVT, function() {
                _.forEach(bodyWraps, function(body: HTMLElement) {
                    body.scrollTop = $pivotBody.scrollTop;
                });
            });
        }
        
        export function visualJumpTo($grid: HTMLElement, index: any) {
            if (index >= _cloud.startIndex && index < _cloud.endIndex) return;
            let tbl = $grid.querySelector("." + FREE + ":not(.mgrid-header)");
            tbl.scrollTop = index * BODY_ROW_HEIGHT;
            return true;
        }
    }
    
    module kt {
        export const AGENCY = "mgrid-agency";
        export const FIXED_LINE = "mgrid-fixed-line";
        export const LINE = "mgrid-line";
        export const RESIZE_COL = "resizeColumn";
        export const RESIZE_NO = "resizeNo";
        export const AREA_AGENCY = "mgrid-area-agency";
        export const RESIZE_AREA = "resize-area";
        export const AREA_LINE = "mgrid-area-line";
        export const STAY_CLS = "mgrid-stay";
        
        export let _adjuster;
        export let _widths = {};
        
        export class ColumnAdjuster {
            headerWrappers: Array<HTMLElement>;
            bodyWrappers: Array<HTMLElement>;
            sumWrappers: Array<HTMLElement>;
            headerColGroup: Array<HTMLElemenet> = [];
            bodyColGroup: Array<HTMLElement> = [];
            sumColGroup: Array<HTMLElement> = [];
            widths: Array<string>;
            height: string;
            
            $fixedAgency: HTMLElement;
            $agency: HTMLElement;
            fixedLines: Array<HTMLElement> = [];
            lines: Array<HTMLElement> = []; 
            $ownerDoc: HTMLElement;
            actionDetails: any;
            
            constructor(widths: Array<string>, height: any, sizeUi: any) {
                this.headerWrappers = sizeUi.headerWrappers;
                this.bodyWrappers = sizeUi.bodyWrappers;
                this.sumWrappers = sizeUi.sumWrappers;
                _.forEach(sizeUi.headerColGroup, g => {
                    if (g) {
                        let vCols = g.filter(c => c.style.display !== "none");
                        this.headerColGroup.push(vCols);
                    }
                });
                
                _.forEach(sizeUi.bodyColGroup, g => {
                    if (g) {
                        let vCols = g.filter(c => c.style.display !== "none");
                        this.bodyColGroup.push(vCols);
                    }
                });
                
                _.forEach(sizeUi.sumColGroup, g => {
                    if (g) {
                        let vCols = g.filter(c => c.style.display !== "none");
                        this.sumColGroup.push(vCols);
                    }
                });
                this.widths = widths;
                this.height = height;
                this.$ownerDoc = this.headerWrappers[0].ownerDocument;
                _widths._fixed = parseFloat(widths[0]);
                _widths._unfixed = parseFloat(widths[1]); 
            }
            
            nostal(headerColGroup: any, bodyColGroup: any, sumColGroup: any) {
                let i = _hasFixed ? 1 : 0;
                this.headerColGroup[i] = headerColGroup.filter(c => c.style.display !== "none");
                this.bodyColGroup[i] = bodyColGroup.filter(c => c.style.display !== "none");
                this.sumColGroup[i] = sumColGroup.filter(c => c.style.display !== "none");
                this.widths = [ kt._widths._fixed, kt._widths._unfixed ];
                let agency;
                if (_hasFixed) {
                    agency = this.headerWrappers[0].querySelector("." + AGENCY);
                    if (agency) agency.remove();
                }
                
                agency = this.headerWrappers[1].querySelector("." + AGENCY);
                if (agency) agency.remove();
                this.fixedLines = [];
                this.lines = [];
            }
            
            /**
             * Handle.
             */
            handle() {
                let self = this;
                self.$fixedAgency = document.createElement("div"); 
                self.$fixedAgency.className = AGENCY;
                self.$fixedAgency.style.cssText = "; position: relative; width: " + self.widths[0];
                let $fixedHeaderTable = self.headerWrappers[0].querySelector("table");
                $fixedHeaderTable.insertAdjacentElement("beforebegin", self.$fixedAgency);
                let left = 0, hiddenCount = 0;
                
                _.forEach(self.headerColGroup[0], ($targetCol, i) => {
                    if ($targetCol.style.display === "none") {
                        hiddenCount++;
                        return;
                    }
                    let $line = document.createElement("div");
                    $line.className = FIXED_LINE;
                    $.data($line, RESIZE_COL, $targetCol); 
                    $.data($line, RESIZE_NO, i - hiddenCount);
                    self.$fixedAgency.appendChild($line);
                    
                    left += (i === self.headerColGroup[0].length ? DISTANCE : 0) + parseFloat($targetCol.style.width);
                    $line.style.left = left + "px";
                    $line.style.height = self.height;
                    self.fixedLines.push($line);
                });
                
                self.fixedHiddenCount = hiddenCount;
                left = 0;
                self.$agency = document.createElement("div");
                self.$agency.className = AGENCY;
                self.$agency.style.cssText = "; position: relative; width: " + self.widths[1];
                let $headerTable = self.headerWrappers[1].querySelector("table");
                $headerTable.insertAdjacentElement("beforebegin", self.$agency);
                hiddenCount = 0;
                _.forEach(self.headerColGroup[1], ($targetCol, i) => {
                    if ($targetCol.style.display === "none") {
                        hiddenCount++;
                        return;
                    }
                    let $line = document.createElement("div");
                    $line.className = LINE;
                    $.data($line, RESIZE_COL, $targetCol);
                    $.data($line, RESIZE_NO, i - hiddenCount);
                    self.$agency.appendChild($line);
                    left += parseFloat($targetCol.style.width);
                    $line.style.left = left + "px";
                    $line.style.height = self.height;
                    self.lines.push($line);
                });
                
                self.hiddenCount = hiddenCount;
                self.$fixedAgency.removeXEventListener(ssk.MOUSE_DOWN);
                self.$fixedAgency.addXEventListener(ssk.MOUSE_DOWN, self.cursorDown.bind(self));  
                self.$agency.removeXEventListener(ssk.MOUSE_DOWN);
                self.$agency.addXEventListener(ssk.MOUSE_DOWN, self.cursorDown.bind(self));
            }
            
            /**
             * Cursor down.
             */
            cursorDown(event: any) {
                let self = this;
                if (self.actionDetails) {
                    self.cursorUp(event);
                }
                let $targetGrip = event.target;
                let gripIndex = $.data($targetGrip, RESIZE_NO);
                let $leftCol = $.data($targetGrip, RESIZE_COL);
                let headerGroup, isFixed = false;
                if ($targetGrip.classList.contains(FIXED_LINE)) {
                    headerGroup = self.headerColGroup[0];
                    isFixed = true;
                } else {
                    headerGroup = self.headerColGroup[1];
                }
                
                let breakArea, wrapperLeft, wrapperRight, leftAlign;
                if (isFixed && self.headerColGroup.length > 1 && gripIndex === self.headerColGroup[0].length - 1) {
                    breakArea = true;
                }
                
                if (self.headerWrappers.length > 1) {
                    wrapperLeft = self.headerWrappers[0].style.width;
                    wrapperRight = self.headerWrappers[1].style.width;
                    leftAlign = self.headerWrappers[1].style.left;
                }   
                
                let $rightCol = headerGroup[gripIndex + 1];
                let leftWidth = $leftCol.style.width;
                let rightWidth;
                if ($rightCol) rightWidth = $rightCol.style.width;
                self.actionDetails = {
                    $targetGrip: $targetGrip,
                    gripIndex: gripIndex,
                    $leftCol: $leftCol,
                    $rightCol: $rightCol,
                    xCoord: getCursorX(event),
                    isFixed: isFixed,
                    breakArea: breakArea,
                    leftAlign: parseFloat(leftAlign),
                    widths: {
                        left : parseFloat(leftWidth),
                        right: rightWidth ? parseFloat(rightWidth) : undefined,
                        wrapperLeft: parseFloat(wrapperLeft),
                        wrapperRight: parseFloat(wrapperRight)
                    },
                    changedWidths: {
                        left: parseFloat(leftWidth),
                        right: rightWidth ? parseFloat(rightWidth) : undefined
                    }
                };
                
                self.$ownerDoc.addXEventListener(ssk.MOUSE_MOVE, self.cursorMove.bind(self));
                self.$ownerDoc.addXEventListener(ssk.MOUSE_UP, self.cursorUp.bind(self));
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
                let leftWidth, rightWidth, leftAreaWidth, rightAreaWidth, leftAlign;
                leftWidth = self.actionDetails.widths.left + distance;
                rightWidth = self.actionDetails.widths.right - distance;
                
                if (leftWidth <= 20 || rightWidth <= 20) return;
                if (self.actionDetails.breakArea) {
                    leftAreaWidth = self.actionDetails.widths.wrapperLeft + distance;
                    rightAreaWidth = self.actionDetails.widths.wrapperRight - distance;
                    leftAlign = self.actionDetails.leftAlign + distance;
                }
                
                self.actionDetails.changedWidths.left = leftWidth;
                self.actionDetails.changedWidths.right = rightWidth;
                let bodyGroup, sumGroup;
                if (self.actionDetails.isFixed) {
                    bodyGroup = self.bodyColGroup[0];
                    if (self.sumWrappers.length > 0) sumGroup = self.sumColGroup[0];
                } else { 
                    bodyGroup = self.bodyColGroup[1];
                    if (self.sumWrappers.length > 0) sumGroup = self.sumColGroup[1];
                }
                
                if (self.actionDetails.$leftCol) {
                    self.setWidth(self.actionDetails.$leftCol, leftWidth);
                    let $contentLeftCol = bodyGroup[self.actionDetails.gripIndex];
                    self.setWidth($contentLeftCol, leftWidth);
                    if (self.sumWrappers.length > 0) {
                        let $sumLeftCol = sumGroup[self.actionDetails.gripIndex];
                        self.setWidth($sumLeftCol, leftWidth);
                    }
                    
                    if (leftAreaWidth) {
                        self.setWidth(self.headerWrappers[0], leftAreaWidth);
                        self.setWidth(self.bodyWrappers[0], leftAreaWidth);
                        if (self.sumWrappers.length > 0) self.setWidth(self.sumWrappers[0], leftAreaWidth);
                        _widths._fixed = leftAreaWidth;
                    }
                }
                
                if (self.actionDetails.$rightCol) {
                    self.setWidth(self.actionDetails.$rightCol, rightWidth);
                    let $contentRightCol = bodyGroup[self.actionDetails.gripIndex + 1];
                    self.setWidth($contentRightCol, rightWidth);
                    if (self.sumWrappers.length > 0) {
                        let $sumRightCol = sumGroup[self.actionDetails.gripIndex + 1];
                        self.setWidth($sumRightCol, rightWidth);
                    }
                }
                
                if (rightAreaWidth) {
                    self.setWidth(self.headerWrappers[1], rightAreaWidth);
                    self.setWidth(self.bodyWrappers[1], rightAreaWidth + ti.getScrollWidth());
                    self.headerWrappers[1].style.left = leftAlign + "px";
                    self.bodyWrappers[1].style.left = leftAlign + "px";
                    if (self.sumWrappers.length > 0) {
                        self.setWidth(self.sumWrappers[1], rightAreaWidth);
                        self.sumWrappers[1].style.left = leftAlign + "px";
                    }
                    _widths._unfixed = rightAreaWidth;
                }
            }
            
            /**
             * Cursor up.
             */
            cursorUp(event: any) {
                let self = this;
                self.$ownerDoc.removeXEventListener(ssk.MOUSE_MOVE);
                self.$ownerDoc.removeXEventListener(ssk.MOUSE_UP);
                self.syncLines();
                self.actionDetails = null;
            }
            
            /**
             * Set width.
             */
            setWidth($col: HTMLElement, width: any) {
                $col.style.width = parseFloat(width) + "px";
            }
            
            /**
             * Sync lines.
             */
            syncLines() {
                let self = this;
                self.$agency.style.width = self.headerWrappers[self.actionDetails.isFixed ? 0 : 1].style.width;
                let left = 0;
                _.forEach(self.headerColGroup[self.actionDetails.isFixed ? 0 : 1], function($td: HTMLElement, index: number) {
                    if ($td.style.display === "none") return;
                    left += parseFloat($td.style.width);
                    if (index < self.actionDetails.gripIndex) return;
                    if (index > self.actionDetails.gripIndex) return false;
                    let lineArr = self.actionDetails.isFixed ? self.fixedLines : self.lines;
                    let div = lineArr[index];
                    div.style.left = left + "px";
                });
            }
        }
        
        function getCursorX(event: any) {
            return event.pageX;
        }
    }
    
    module lo {
        export const MPART = "mPart";
        export const VIEW = "mView";
        export const LAST_SELECT = "mLastSelect";
        export const SELECTED_CELLS = "mSelectedCells";
        export const DESC = "mDescription";
        export const CBX_SELECTED = "selectedValue";
        export const CBX_SELECTED_TD = "code";
        export const CBX_ITEM_VALUE = "value";
        
        $.widget("md.mGrid", {
            options: {},
            _create: function() {    
            },
            directEnter: function(direct) {
                this.element.data("enterDirect", direct);
            },
            dataSource: function() {
                return _.cloneDeep(_dataSource);
            },
            errors: function() {
                return _.cloneDeep(_errors);
            },
            disableNtsControlAt: function(id, key) {
                let idx = _.findIndex(_dataSource, r => r[_pk] === id);
                if (_.isNil(idx)) return;
                let $cell = lch.cellAt(_$grid[0], idx, key);
                if (_.isNil($cell) || $cell.classList.contains(color.Disable)) return;
                $cell.classList.add(color.Disable);
                switch (dkn.controlType[key]) {
                    case dkn.LINK_LABEL:
                        let link = $cell.querySelector(".mlink-button");
                        if (link) {
                            link.removeXEventListener(ssk.CLICK_EVT);
                            link.style.color = "#333";
                            link.style.cursor = "default";
                        }
                        break;
                    case dkn.BUTTON:
                    case dkn.DELETE_BUTTON:
                        let btn = $cell.querySelector(".mbutton");
                        if (btn) btn.disabled = true;
                        break;
                    case dkn.FLEX_IMAGE:
                        let img = $cell.querySelector("span");
                        if (img) {
                            img.removeXEventListener(ssk.CLICK_EVT);
                            img.style.cursor = "default";
                        }
                        break;
                    case dkn.CHECKBOX:
                        let check = $cell.querySelector("input");
                        if (check) {
                            check.setAttribute("disabled", "disabled");
                        }
                        break;
                }
                
                color.pushState(id, key, color.Disable);
            },
            enableNtsControlAt: function(id, key) {
                let idx = _.findIndex(_dataSource, r => r[_pk] === id);
                if (_.isNil(idx)) return;
                let $cell = lch.cellAt(_$grid[0], idx, key);
                if (_.isNil($cell) || !$cell.classList.contains(color.Disable)) return;
                $cell.classList.remove(color.Disable);
                switch (dkn.controlType[key]) {
                    case dkn.LINK_LABEL:
                        let link = $cell.querySelector(".mlink-button");
                        if (link) {
                            link.addXEventListener(ssk.CLICK_EVT, $.data(link, ssk.CLICK_EVT));
                            link.style.color = "#0066CC";
                            link.style.cursor = "pointer";
                        }
                        break;
                    case dkn.BUTTON:
                    case dkn.DELETE_BUTTON:
                        let btn = $cell.querySelector(".mbutton");
                        if (btn) btn.disabled = false;
                        break;
                    case dkn.FLEX_IMAGE:
                        let img = $cell.querySelector("span");
                        if (img) {
                            img.addXEventListener(ssk.CLICK_EVT, $.data(img, ssk.CLICK_EVT));
                            img.style.cursor = "pointer";
                        }
                        break;
                    case dkn.CHECKBOX:
                        let check = $cell.querySelector("input");
                        if (check) {
                            check.removeAttribute("disabled");
                        }
                        break;
                }
                
                color.popState(id, key, color.Disable);
            },
            setState: function(id, key, states) {
                let idx = _.findIndex(_dataSource, r => r[_pk] === id);
                if (_.isNil(idx)) return;
                let $cell = lch.cellAt(_$grid[0], idx, key);
                if (_.isNil($cell)) return;
                _.forEach(states, s => {
                    if (!$cell.classList.contains(s))
                        $cell.classList.add(s);
                });
                
                color.pushState(id, key, states);
            },
            hideZero: function(val) {
                if (changeZero(val)) {
                    _zeroHidden = val;
                    if (_vessel()) _vessel().zeroHidden = val;
                }
            },
            updatedCells: function() {
                let arr = [];
                let toNumber = false, column = _columnsMap[_pk];
                if ((column && _.toLower(column[0].dataType) === "number")
                    || _.toLower(_pkType) === "number") {
                    toNumber = true;
                }
                
                _.forEach(Object.keys(_dirties), r => {
                    _.forEach(Object.keys(_dirties[r]), c => {
                        arr.push({ rowId: (toNumber ? parseFloat(r) : r), columnKey: c, value: _dirties[r][c] });
                    });
                });
                
                return arr;
            },
            updateCell: function(id, key, val) {
                let idx = _.findIndex(_dataSource, r => r[_pk] === id);
                if (_.isNil(idx)) return;
                let $cell = lch.cellAt(_$grid[0], idx, key);
                if (_.isNil($cell)) return;
                if (dkn.controlType[key] === dkn.TEXTBOX) {
                    let col = _columnsMap[key];
                    if (!col || col.length === 0) return;
                    val = String(val);
                    let formatted = su.format(col[0], val);
                    $cell.innerHTML = formatted;
                    $.data($cell, v.DATA, val);
                    su.wedgeCell(_$grid[0], { rowIdx: idx, columnKey: key }, val);
                }
            },
            getCellValue: function(id, key) {
                let idx = _.findIndex(_dataSource, r => r[_pk] === id);
                if (_.isNil(idx)) return;
                return _dataSource[idx][key];
            },
            selectedSheet: function() {
                return _currentSheet;
            },
            selectedPage: function() {
                return _currentPage;
            },  
        });
        
        export function changeZero(hide: any) {
            let ves, desc, realVal;
            if (_zeroHidden === hide) return false;
            if ((ves = _vessel()) && (desc = ves.desc)) {
                _.forEach(desc.rows, r => {
                    _.forEach(r, c => {
                        let key = ti.getCellCoord(c).columnKey;
                        let control = dkn.controlType[key];
                        if (control !== dkn.TEXTBOX) return;
                        let content = c.textContent;
                        if (hide && ti.isZero(content)) {
                            c.textContent = "";
                        } else if (content === "" && (realVal = $.data(c, v.DATA)) !== "") {
                            let format = su.format(_columnsMap[key][0], realVal);
                            c.textContent = format;
                        }
                    });
                });
            }
            
            return true;
        }
    }
    
    module su {
        
        export const EDITOR = "meditor";
        
        export function binding($grid: HTMLElement) {
            
            $grid.addXEventListener(ssk.MOUSE_DOWN, evt => {
                let $tCell = evt.target;
                if (!$tCell || !selector.is($tCell, "." + v.CELL_CLS)
                    || $tCell.classList.contains(color.Disable)
                    || $tCell.classList.contains(dkn.LABEL_CLS)) return;
                let coord = ti.getCellCoord($tCell);
                let control = dkn.controlType[coord.columnKey];
                let cEditor = _mEditor;
                
                if (!control || ti.isEqual(coord, cEditor, [ "rowIdx", "columnKey" ])
                    || (control === dkn.TEXTBOX && !$tCell.classList.contains(lch.CELL_SELECTED_CLS))) return;
                let $editor = dkn.controlType[dkn.TEXTBOX].my;
                let $input = $editor.querySelector("input.medit");
                let cType = {};
                if (control === dkn.TEXTBOX && $tCell.classList.contains(lch.CELL_SELECTED_CLS)) {
                    endEdit($grid);
                    if ($tCell.classList.contains(hpl.CURRENCY_CLS)) {
                        $tCell.classList.remove(hpl.CURRENCY_CLS);
                        $editor.classList.add(hpl.CURRENCY_CLS);
                    }
                    
                    $tCell.textContent = "";
                    $tCell.classList.add(dkn.CONTROL_CLS);
                    $tCell.appendChild($editor);
                    let data = $.data($tCell, v.DATA);
                    $input.value = !_.isNil(data) ? data : "";
                    cType.type = dkn.TEXTBOX;
                    setTimeout(() => {
                        $input.select();
                    }, 0);
                } else if (control.type === dkn.COMBOBOX && !$tCell.querySelector(".mcombo-wrapper")) {
                    endEdit($grid);
                    $tCell.textContent = "";
                    $tCell.classList.add(dkn.CONTROL_CLS);
                    let $combo = control.my.querySelector("." + dkn.CBX_CLS);
                    let $comboValue = control.my.querySelector(".mcombo-value");
                    let items = control.dropdown.querySelectorAll(".mcombo-listitem");
                    let code = $.data($tCell, lo.CBX_SELECTED_TD);
                    _.forEach(items, i => {
                        let value = $.data(i, lo.CBX_ITEM_VALUE);
                        
                        if (i.classList.contains("selecteditem")) {
                            i.classList.remove("selecteditem");
                        }
                        
                        if (code === value) {
                            let $item = i.querySelector(".mcombo-item");
                            if ($item) {
                                $comboValue.innerHTML = "";
                                $comboValue.appendChild($item.cloneNode(true));
                                i.classList.add("selecteditem");
                            }
                        }
                    });
                    
                    $tCell.appendChild(control.my);
                    $.data(control.my, lo.CBX_SELECTED, code);
                    dkn.openDD(control.dropdown, control.my);
                    $combo.classList.add(dkn.CBX_ACTIVE_CLS);
                    cType.type = dkn.COMBOBOX;
                }
                
                _mEditor = _.assignIn(coord, cType);
                evt.stopPropagation();
            });
            
            document.addXEventListener(ssk.MOUSE_DOWN, evt => {
                if (!evt.target) return;
                if (!selector.is(evt.target, "input.medit")
                    && !selector.is(evt.target, "div[class*='mcombo']")) {
                    endEdit($grid);
                }
            });
            
            $grid.addXEventListener(ssk.KEY_DOWN, evt => {
                let $grid = evt.currentTarget, $tCell = evt.target;
                if (!$grid) return;
                if (ti.isEnterKey(evt)) {
                    let direct = $.data($grid, "enterDirect");
                    if (evt.shiftKey) {
                        lch.selectPrev($grid, direct);
                    } else {
                        lch.selectNext($grid, direct);        
                    }
                } else if (ti.isTabKey(evt)) {
                    evt.preventDefault();
                    if (evt.shiftKey) {
                        lch.selectPrev($grid);
                    } else {
                        lch.selectNext($grid);
                    }
                } else if (ti.isArrowLeft(evt)) {
                    evt.preventDefault();
                    lch.selectPrev($grid);
                } else if (ti.isArrowRight(evt)) {
                    evt.preventDefault();
                    lch.selectNext($grid);
                } else if (ti.isArrowUp(evt)) {
                    evt.preventDefault();
                    lch.selectPrev($grid, "below");
                } else if (ti.isArrowDown(evt)) {
                    evt.preventDefault();
                    lch.selectNext($grid, "below");
                }
                
                // Get input
                if (ti.isAlphaNumeric(evt) || ti.isMinusSymbol(evt) || ti.isDeleteKey(evt)) {
                    if (!$tCell || !selector.is($tCell, "." + v.CELL_CLS)
                        || $tCell.classList.contains(color.Disable)
                        || $tCell.classList.contains(dkn.LABEL_CLS)) return;
                    let coord = ti.getCellCoord($tCell);
                    let control = dkn.controlType[coord.columnKey];
                    let cEditor = _mEditor;
                    
                    if (!control || ti.isEqual(coord, cEditor, [ "rowIdx", "columnKey" ])
                        || (control === dkn.TEXTBOX && !$tCell.classList.contains(lch.CELL_SELECTED_CLS))) return;
                    let $editor = dkn.controlType[dkn.TEXTBOX].my;
                    let $input = $editor.querySelector("input.medit");
                    let cType = {};
                    if (control === dkn.TEXTBOX && $tCell.classList.contains(lch.CELL_SELECTED_CLS)) {
                        endEdit($grid);
                        if ($tCell.classList.contains(hpl.CURRENCY_CLS)) {
                            $tCell.classList.remove(hpl.CURRENCY_CLS);
                            $editor.classList.add(hpl.CURRENCY_CLS);
                        }
                        
                        $tCell.textContent = "";
                        $tCell.classList.add(dkn.CONTROL_CLS);
                        $tCell.appendChild($editor);
//                        $input.value = evt.key;
                        if (ti.isDeleteKey(evt) && cEditor === null) {
                            $input.value = "";
                        }
                        
                        $input.focus();
                        cType.type = dkn.TEXTBOX;
                    }
                    
                    _mEditor = _.assignIn(coord, cType);
                }
            });
        }
        
        export function endEdit($grid: HTMLElement) {
            let editor = _mEditor;
            if (!editor) return;
            let $bCell = lch.cellAt($grid, editor.rowIdx, editor.columnKey);
            if (editor.type === dkn.TEXTBOX) {
                let $editor = dkn.controlType[dkn.TEXTBOX].my;
                let $input = $editor.querySelector("input.medit");
                let inputVal = $input.value;
                if ($bCell) {
                    let val = wedgeCell($grid, editor, inputVal);
                    let column = _columnsMap[editor.columnKey];
                    if (!column) return;
                    $bCell.textContent = format(column[0], inputVal);
                    $.data($bCell, v.DATA, val);
                    
                    if ($editor.classList.contains(hpl.CURRENCY_CLS)) {
                        $editor.classList.remove(hpl.CURRENCY_CLS);
                        $bCell.classList.add(hpl.CURRENCY_CLS);
                    }
                    $input.value = "";
                    let sCol = _specialColumn[editor.columnKey];
                    if (sCol) {
                        let cbx = dkn.controlType[sCol];
                        wedgeCell($grid, { rowIdx: editor.rowIdx, columnKey: sCol }, inputVal);
                        let selectedOpt = _.find(cbx.options, o => o.code === inputVal); 
                        if (!_.isNil(selectedOpt)) {
                            let $cbxCell = lch.cellAt($grid, editor.rowIdx, sCol);
                            $cbxCell.textContent = selectedOpt ? selectedOpt.name : "";
                            $.data($cbxCell, lo.CBX_SELECTED_TD, inputVal);
                        }
                    } else if ((sCol = _specialLinkColumn[editor.columnKey]) && sCol.changed) {
                        let data = _mafollicle[_currentPage].origDs[editor.rowIdx];
                        sCol.changed(editor.columnKey, data[_pk], inputVal, data[editor.columnKey]).done(res => {
                            let $linkCell = lch.cellAt($grid, editor.rowIdx, sCol.column);
                            if ($linkCell) {
                                $linkCell.querySelector("a").textContent = res;
                                wedgeCell($grid, { rowIdx: editor.rowIdx, columnKey: sCol.column }, res);
                            }
                        });
                    }
                }
            } else if (editor.type === dkn.COMBOBOX) {
                let cbx = dkn.controlType[editor.columnKey];
                let bSelectedValue = $.data(cbx.my, lo.CBX_SELECTED);
                let $combo = cbx.my.querySelector("." + dkn.CBX_CLS);
                wedgeCell($grid, editor, bSelectedValue);
                let selectedOpt = _.find(cbx.options, o => o.code === bSelectedValue); 
                $bCell.textContent = selectedOpt ? selectedOpt.name : ""; 
                if (cbx.dropdown && cbx.dropdown.style.top !== "-99999px") {
                    dkn.closeDD(cbx.dropdown);
                    $combo.classList.remove(dkn.CBX_ACTIVE_CLS);
                }
            }
            
            $bCell.classList.remove(dkn.CONTROL_CLS);
            _mEditor = null;
        }
        
        export function wedgeCell($grid: HTMLElement, coord: any, cellValue: any) {
            let valueType = hpl.getValueType($grid, coord.columnKey);
            if (!_.isNil(cellValue) && !_.isEmpty(cellValue) 
                && (valueType === "TimeWithDay" || valueType === "Clock")) {
                try {
                    cellValue = time.minutesBased.clock.dayattr.create(
                        time.minutesBased.clock.dayattr.parseString(String(cellValue)).asMinutes).shortText;
                } catch(e) {}
            }
            
            let rData = _dataSource[coord.rowIdx];
            if (_.isNil(rData)) return;
            let id = rData[_pk];
            
            let origDs = _mafollicle[_currentPage].origDs;
            if (!origDs) return;
            let origVal = origDs[coord.rowIdx][coord.columnKey];
            let column = _columnsMap[coord.columnKey];
            if (!column) return;
            if (_.toLower(column[0].dataType) === "number") {
                cellValue = parseFloat(cellValue);
            }
            
            if (!_zeroHidden) {
                if (cellValue === origVal) {
                    if (!_.isNil(_dirties[id]) && !_.isNil(_dirties[id][coord.columnKey])) {
                        delete _dirties[id][coord.columnKey];
                    }
                    
                    let $cell = lch.cellAt($grid, coord.rowIdx, coord.columnKey);
                    if (!$cell) return;
                    $cell.classList.remove(color.ManualEditTarget);
                    $cell.classList.remove(color.ManualEditOther);
                    return;
                }
                
                if (!_dirties[id]) {
                    _dirties[id] = {};
                    _dirties[id][coord.columnKey] = cellValue;
                } else if (!_dirties[id][coord.columnKey]) {
                    _dirties[id][coord.columnKey] = cellValue;
                }
                rData[coord.columnKey] = cellValue;
                if (!_.isNil(_objId) && !_.isNil(_getObjId) && _.isFunction(_getObjId)) {
                    let cId = _getObjId(id);
                    let $cell = lch.cellAt($grid, coord.rowIdx, coord.columnKey);
                    if (!$cell) return;
                    if (cId === _objId) {
                        $cell.classList.add(color.ManualEditTarget);
                    } else $cell.classList.add(color.ManualEditOther);
                }
            } else if (_zeroHidden && (origVal === 0 || origVal === "0" || origVal === "0:00")
                && (cellValue === "" || _.isNil(cellValue))) {
                
            }
            
            return cellValue;
        }
        
        export function deleteRow() {
        }
        
        export function format(column: any, value: any) {
            if (column.constraint) {
                let constraint = column.constraint;
                let valueType = constraint.primitiveValue ? ui.validation.getConstraint(constraint.primitiveValue).valueType
                            : constraint.cDisplayType;
                if (!_.isNil(value)) {
                    if (valueType === "TimeWithDay") {
                        let minutes = time.minutesBased.clock.dayattr.parseString(value).asMinutes;
                        let timeOpts = { timeWithDay: true };
                        let formatter = new text.TimeWithDayFormatter(timeOpts);
                        if (!util.isNullOrUndefined(minutes)) {
                            try {
                                value = formatter.format(minutes);
                            } catch(e) {}
                        }
                    } else if (valueType === "Clock") {
                        let minutes = time.minutesBased.clock.dayattr.parseString(value).asMinutes;
                        let timeOpts = { timeWithDay: false };
                        let formatter = new text.TimeWithDayFormatter(timeOpts);
                        if (!util.isNullOrUndefined(minutes)) {
                            try {
                                value = formatter.format(minutes);
                            } catch(e) {}
                        }
                    } else if (valueType === "Currency") { 
                        let currencyOpts: any = new ui.option.CurrencyEditorOption();
                        currencyOpts.grouplength = constraint.groupLength | 3;
                        currencyOpts.decimallength = constraint.decimalLength | 2;
                        currencyOpts.currencyformat = constraint.currencyFormat ? constraint.currencyFormat : "JPY";
                        let groupSeparator = constraint.groupSeparator || ",";
                        let rawValue = text.replaceAll(value, groupSeparator, "");
                        let formatter = new uk.text.NumberFormatter({ option: currencyOpts });
                        let numVal = Number(rawValue);
                        if (!isNaN(numVal)) value = formatter.format(numVal);
                        else value = rawValue;
                    }
                }
            }
            
            return value;
        }
    }
    
    module ssk {
        export let SCROLL_EVT = "scroll";
        export let CLICK_EVT = "click";
        export let MOUSE_DOWN = "mousedown";
        export let MOUSE_MOVE = "mousemove";
        export let MOUSE_UP = "mouseup";
        export let MOUSE_OVER = "mouseover";
        export let MOUSE_ENTER = "mouseenter";
        export let MOUSE_OUT = "mouseout";
        export let MOUSE_LEAVE = "mouseleave";
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
        export let RENDERED = "extablerowsrendered";
        export let COMPLETED = "extablecompleted";
        
        window.addXEventListener = document.addXEventListener = Element.prototype.addXEventListener = addEventListener;
        window.removeXEventListener = document.removeXEventListener = Element.prototype.removeXEventListener = removeEventListener;
        
        /**
         * Trigger.
         */
        export function trigger($target: HTMLElement, eventName: string, args?: any) {
            let event;
            if (window.CustomEvent) {
                event = new CustomEvent(eventName, { detail: args });
            } else {
                event = document.createEvent('CustomEvent');
                event.initCustomEvent(eventName, true, true, args);
            }
            $target.dispatchEvent(event);
        }
        
        function addEventListener(event, cb, opts) {
            let self = this;
            if (!self.ns) self.ns = {};
            if (!self.ns[event]) self.ns[event] = [ cb ];
            else self.ns[event].push(cb);
            self.addEventListener(event.split(".")[0], cb, opts);  
        };
        
        function removeEventListener(event, cb?) {
            let self = this;
            if (!self.ns) return;
            if (cb) {
                let keys = Object.keys(self.ns).filter(function(k) {
                    return (k === event || k === event.split(".")[0])
                            && self.ns[k].indexOf(cb) > -1;
                });
                
                let key;
                if (keys.length > 0) {
                    key = keys[0];
                    self.ns[key].splice(self.ns[key].indexOf(cb), 1);
                    if (self.ns[key].length === 0) delete self.ns[key];
                }
                self.removeEventListener(event.split(".")[0], cb);
                return;
            }
            
            if (!self.ns[event]) return;
            self.ns[event].forEach(function(e) {
                self.removeEventListener(event.split(".")[0], e); 
            });
            delete self.ns[event];
        }
    }
    
    export module gp {
        export const PAGING_CLS = "mgrid-paging";
        export const SHEET_CLS = "mgrid-sheet";
        export const PAGE_HEIGHT = 44;
        export const SHEET_HEIGHT = 30;
        
        export function imiPages($container: HTMLElement, top: any, width: any) {
            if (!_paging) return;
            let $pageArea = v.createWrapper(top + ti.getScrollWidth() + SUM_HEIGHT + "px", 0, 
                { width: parseFloat(width) + ti.getScrollWidth() + "px", height: PAGE_HEIGHT + "px", containerClass: PAGING_CLS });
            $container.appendChild($pageArea);
            let $recDesc = document.createElement("span");
            $recDesc.classList.add("mgrid-pagerecordlabel");
            $recDesc.textContent = "100 レコード";
            $pageArea.appendChild($recDesc);
            let $gridPaging = _prtDiv.cloneNode();
            $gridPaging.classList.add("mgrid-paging-nav");
            $pageArea.appendChild($gridPaging);
            let $firstPage = _prtDiv.cloneNode();
            $firstPage.classList.add("mgrid-firstpage");
            $firstPage.classList.add("mgrid-paging-item");
            $firstPage.classList.add("ui-state-default");
            $gridPaging.appendChild($firstPage);
            let $arrowStopImg = document.createElement("span");
            $arrowStopImg.classList.add("mgrid-pageimg");
            $arrowStopImg.classList.add("ui-icon");
            $arrowStopImg.classList.add("ui-icon-arrowstop-1-w");
            $firstPage.appendChild($arrowStopImg);
            
            let $buttons = document.createElement("ul");
            $buttons.classList.add("mgrid-page-buttonlist");
            $gridPaging.appendChild($buttons);
            
            let pageList = _.filter(Object.keys(_mafollicle), p => p !== SheetDef).sort((p1, p2) => parseFloat(p1) - parseFloat(p2));
            $firstPage.addXEventListener(ssk.CLICK_EVT, evt => {
                if (parseInt(pageList[0]) === _currentPage) return;
                let btns = $buttons.querySelectorAll("li");
                _.forEach(btns, li => {
                    if (li.classList.contains("ui-state-active")) {
                        li.classList.remove("ui-state-active");
                    }
                });
                
                lungeto(0);
                btns[0].classList.add("ui-state-active");
            });
            
            _.forEach(pageList, p => {
                if (p !== SheetDef) {
                    let $pageBtn = document.createElement("li");
                    $pageBtn.classList.add("mgrid-page-button");
                    $pageBtn.classList.add("ui-state-default");
                    $pageBtn.textContent = parseInt(p) + 1;
                    if (parseInt(p) === _currentPage) $pageBtn.classList.add("ui-state-active");
                    $pageBtn.addXEventListener(ssk.CLICK_EVT, evt => {
                        if ($pageBtn.classList.contains("ui-state-active")) return;
                        lungeto(parseInt(p));
                        _.forEach($buttons.querySelectorAll("li"), li => {
                            if (li.classList.contains("ui-state-active")) {
                                li.classList.remove("ui-state-active");
                            }
                        });
                        
                        $pageBtn.classList.add("ui-state-active");
                    });
                    
                    $buttons.appendChild($pageBtn);
                }
            });
            
            let $lastPage = _prtDiv.cloneNode();
            $lastPage.classList.add("mgrid-lastpage");
            $lastPage.classList.add("mgrid-paging-item");
            $lastPage.classList.add("ui-state-default");
            $gridPaging.appendChild($lastPage);
            $lastPage.addXEventListener(ssk.CLICK_EVT, evt => {
                if (pageList.length - 1 === _currentPage) return;
                let btns = $buttons.querySelectorAll("li");
                _.forEach(btns, li => {
                    if (li.classList.contains("ui-state-active")) {
                        li.classList.remove("ui-state-active");
                    }
                });
                
                lungeto(pageList.length - 1);
                btns[btns.length - 1].classList.add("ui-state-active");
            });
            
            let $arrowStopEImg = document.createElement("span");
            $arrowStopEImg.classList.add("mgrid-pageimg");
            $arrowStopEImg.classList.add("ui-icon");
            $arrowStopEImg.classList.add("ui-icon-arrowstop-1-e");
            $lastPage.appendChild($arrowStopEImg);
        }
        
        export function imiSheets($container: HTMLElement, top: any, width: any) {
            if (!_sheeting) return;
            let $sheetArea = v.createWrapper(top + ti.getScrollWidth() + SUM_HEIGHT + "px", 0, 
                { width: parseFloat(width) + ti.getScrollWidth() + "px", height: SHEET_HEIGHT + "px", containerClass: SHEET_CLS });
            $container.appendChild($sheetArea);
            let $gridSheet = _prtDiv.cloneNode();
            $gridSheet.classList.add("mgrid-sheet-nav");
            $sheetArea.appendChild($gridSheet);
            let $buttons = document.createElement("ul");
            $buttons.classList.add("mgrid-sheet-buttonlist");
            $gridSheet.appendChild($buttons);
            
            _.forEach(Object.keys(_mafollicle[SheetDef]), s => {
                let $btn = document.createElement("li");
                $btn.classList.add("mgrid-sheet-button");
                $btn.classList.add("ui-state-default");
                $btn.textContent = _mafollicle[SheetDef][s].text;
                if (s === _currentSheet) $btn.classList.add("ui-state-active");
                $btn.addXEventListener(ssk.CLICK_EVT, evt => {
                    if ($btn.classList.contains("ui-state-active")) return;
                    hopto(s);
                    _.forEach($buttons.querySelectorAll("li"), li => {
                        if (li.classList.contains("ui-state-active")) {
                            li.classList.remove("ui-state-active");
                        }
                    });
                    
                    $btn.classList.add("ui-state-active");
                });
                
                $buttons.appendChild($btn);
            });
        }
        
        export function lungeto(index: any) {
            let sheetDef = _mafollicle[SheetDef][_currentSheet];
//            _mafollicle[_currentPage].dataSource = _.cloneDeep(_dataSource);
            _currentPage = index;
            _dataSource = _mafollicle[_currentPage].dataSource;
            _hr = null;
            lch.clearAll(_$grid[0]);
            
            _.filter(_bodyWrappers, w => w.classList.contains(FREE))[0].scrollTop = 0;
            
            if (!_vessel()) {
                _mafollicle[_currentPage][_currentSheet] = { errors: [], desc: {}, dirties: {}, zeroHidden: _zeroHidden };
            }
            
            _mDesc = _vessel().desc;
            _errors = _vessel().errors;
            _dirties = _vessel().dirties;
            let res = _cloud.renderRows(true);
            if (!res) {
                if (lo.changeZero(_vessel().zeroHidden)) _vessel().zeroHidden = _zeroHidden;
                return;
            }
            
            let start = res.start, end = res.end, cursor;
            if (_.isNil(_mDesc) || Object.keys(_mDesc).length === 0) {
                $.data(_$grid[0], lo.DESC, _mDesc);
                if (!_.isNil(res.fixedColIdxes) || !_.isNil(res.colIdxes)) {
                    _mDesc.fixedColIdxes = res.fixedColIdxes;
                    _mDesc.colIdxes = res.colIdxes;
                } 
                
                _mDesc.fixedRows = [];
                _mDesc.rows = [];
                _mDesc.fixedRowElements = [];
                _mDesc.rowElements = [];
            }
            
            for (let i = start; i <= end; i++) {
                cursor = i - start;
                _mDesc.fixedRows[i] = res.fixedRows[cursor];
                _mDesc.rows[i] = res.rows[cursor];
                _mDesc.fixedRowElements[i] = res.fixedRowElements[cursor];
                _mDesc.rowElements[i] = res.rowElements[cursor];
            }
            
            if (lo.changeZero(_vessel().zeroHidden)) _vessel().zeroHidden = _zeroHidden;
        }
        
        export function hopto(place: any) {
            let bfPainter;
            if (_currentSheet === place) return;
            if (_hasFixed) bfPainter = _.cloneDeep(_mafollicle[SheetDef][_currentSheet].painters[0]);
            _currentSheet = place;
            
            if (!_vessel()) {
                let desc = { 
                        fixedColIdxes: _.cloneDeep(_mDesc.fixedColIdxes), 
                        fixedRows: _.cloneDeep(_mDesc.fixedRows),
                        fixedRowElements: _.cloneDeep(_mDesc.fixedRowElements),
                        colIdxes: [],
                        rows: [],
                        rowElements: []
                };
                _mafollicle[_currentPage][_currentSheet] = { desc: desc, errors: [], dirties: {}, zeroHidden: _zeroHidden };
            }
            
            _mDesc = _vessel().desc;
            _errors = _vessel().errors;
            _dirties = _vessel().dirties;
            let $header = _$grid[0].querySelector("." + FREE + "." + HEADER);
            let $headerTbl = $header.querySelector("table");
            let bhGroup = $header.querySelector("colgroup");
            let bhBody = $header.querySelector("tbody");
            let dTable = _bodyWrappers[1].querySelector("table");
            let bbGroup = dTable.querySelector("colgroup");
            let sumWrap = _$grid[0].querySelector("." + FREE + "-summaries");
            let sumTbl = sumWrap.querySelector("table");
            let bSumGroup = sumWrap.querySelector("colgroup");
            let bSumBody = sumWrap.querySelector("tbody");
            
            if (!_vessel().$hGroup) {
                _header.columns = _cstifle();
                let $wrapper = v.createWrapper("0px", _leftAlign, _header);
                $wrapper.classList.add(HEADER);
                let table = v.process($wrapper, _header);
                table.$table.style.height = _header.height;
                let hGroup = $wrapper.querySelector("colgroup");
                let hBody = $wrapper.querySelector("tbody"); 
                $headerTbl.replaceChild(hGroup, bhGroup);
                $headerTbl.replaceChild(hBody, bhBody);
                _vessel().$hGroup = hGroup;
                _vessel().$hBody = hBody;
                _mafollicle[SheetDef][_currentSheet].hColArr = table.cols; 
                let artifactOptions = { primaryKey: _pk, controlMap: table.controlMap,  
                    columns: _header.columns, features: _features, hasSum: _hasSum };
                _mafollicle[SheetDef][_currentSheet].controlMap = _.assignIn(_fixedControlMap, table.controlMap);
                let painters = _hasFixed ? [ bfPainter, table.painter ] : [ table.painter ];
                _mafollicle[SheetDef][_currentSheet].painters = painters;
                let colGroup = document.createElement("colgroup");
                let bodyGroupArr = [];
                _.forEach(table.cols, c => {
                    let col = c.cloneNode();
                    colGroup.appendChild(col);
                    bodyGroupArr.push(col);
                });
                _vessel().$bGroup = colGroup;
                _mafollicle[SheetDef][_currentSheet].bColArr = bodyGroupArr;
                dTable.replaceChild(colGroup, bbGroup);
                _mafollicle[SheetDef][_currentSheet].levelStruct = _.cloneDeep(_header.levelStruct); 
                _cloud.painter.revive();
                _cloud.sidePainter.revive();
                v.construe(_$grid[0], _bodyWrappers, artifactOptions, true);
                _vessel().$bBody = dTable.querySelector("tbody");
                
                let $sumBody = document.createElement("tbody");
                let sumGroupArr = [], $sumGroup = document.createElement("colgroup");
                let $tr = document.createElement("tr");
                $tr.style.height = "27px";
                $sumBody.appendChild($tr);
                
                _.forEach(table.cols, c => {
                    let col = c.cloneNode(true);
                    $sumGroup.appendChild(col);
                    sumGroupArr.push(col);
                });
                
                _.forEach(table.painter.visibleColumns, c => {
                    if (c.hidden) return;
                    let sum = _summaries[c.key]; 
                    let $td = _prtCell.cloneNode();
                    $tr.appendChild($td);
                    
                    if (!sum) return;
                    if (sum.calculator === "Time") {
                        let time = sum.total.asHours();
                        let hour = Math.floor(time);
                        let minute = (time - hour) * 60;
                        let roundMin = Math.round(minute);
                        let minuteStr = roundMin < 10 ? ("0" + roundMin) : String(roundMin);
                        $td.textContent = hour + ":" + minuteStr;
                    } else if (sum.calculator === "Number") {
                        $td.textContent = sum.total;
                    } else {
                        $td.textContent = sum.calculator;
                    }
                });
                
                sumTbl.replaceChild($sumGroup, bSumGroup);
                sumTbl.replaceChild($sumBody, bSumBody);
                _vessel().$sumGroup = $sumGroup;
                _vessel().$sumBody = $sumBody;
                _mafollicle[SheetDef][_currentSheet].sumColArr = sumGroupArr;
                
                kt._adjuster.nostal(table.cols, bodyGroupArr, sumGroupArr);
                kt._adjuster.handle(); 
                if (lo.changeZero(_vessel().zeroHidden)) _vessel().zeroHidden = _zeroHidden;         
                return;
            }
            
            _cloud.painter.revive();
            _cloud.sidePainter.revive();
            $headerTbl.replaceChild(_vessel().$hGroup, bhGroup);
            $headerTbl.replaceChild(_vessel().$hBody, bhBody);
            dTable.replaceChild(_vessel().$bGroup, bbGroup);
//            dTable.replaceChild(_vessel().$bBody, dTable.querySelector("tbody"));
            _cloud.renderSideRows(true);
            sumTbl.replaceChild(_vessel().$sumGroup, bSumGroup);
            sumTbl.replaceChild(_vessel().$sumBody, bSumBody);
            kt._adjuster.nostal(_mafollicle[SheetDef][_currentSheet].hColArr, 
                _mafollicle[SheetDef][_currentSheet].bColArr, _mafollicle[SheetDef][_currentSheet].sumColArr);
            kt._adjuster.handle();
            if (lo.changeZero(_vessel().zeroHidden)) _vessel().zeroHidden = _zeroHidden;
        }
    }
    
    export module dkn {
        export let LABEL: string = 'Label';
        export let LINK_LABEL: string = 'LinkLabel';
        export let CHECKBOX: string = 'CheckBox';
        export let SWITCH_BUTTONS: string = 'SwitchButtons';
        export let COMBOBOX: string = 'ComboBox'; 
        export let BUTTON: string = 'Button';
        export let DELETE_BUTTON = 'DeleteButton';
        export let TEXTBOX = 'TextBox';
        export let TEXT_EDITOR = 'TextEditor';
        export let FLEX_IMAGE = 'FlexImage';
        export let IMAGE = 'Image';
        export let HEIGHT_CONTROL = "27px";
        export let controlType = {};
        
        export const CONTROL_CLS = "nts-control";
        export const LABEL_CLS = "mlabel";
        export const CBX_CLS = "mcombo";
        export const CBX_ACTIVE_CLS = "mcombo-state-active";

        /**
         * Get control
         */
        export function getControl(name: string): NtsControlBase {
            switch (name) {
                case TEXTBOX:
                    return textBox();
                case CHECKBOX:
                    return checkBox;
//                case SWITCH_BUTTONS:
//                    return new SwitchButtons;
                case COMBOBOX:
                    return comboBox;
                case BUTTON:
                    return button;
                case DELETE_BUTTON:
                    return deleteButton;
//                case TEXT_EDITOR:
//                    return new TextEditor;
                case LINK_LABEL:
                    return linkLabel;
                case FLEX_IMAGE:
                    return flexImage;
                case IMAGE:
                    return image;
            }
        }
        
        export function textBox(key: string) {
            let control = controlType[TEXTBOX];
            controlType[key] = TEXTBOX;
            if (control) {
                return;
            }
            let $editContainer = document.createElement("div"); 
            $editContainer.classList.add("medit-container");
            $editContainer.style.height = (BODY_ROW_HEIGHT - 4) + "px";
            let $editor = document.createElement("input");
            $editor.classList.add("medit");
            $editContainer.appendChild($editor);
            controlType[TEXTBOX] = { my: $editContainer, type: TEXTBOX };
            $editor.addXEventListener(ssk.KEY_DOWN, evt => {
                if (ti.isEnterKey(evt) || ti.isTabKey(evt)) {
                    let grid = ti.closest($editor, "." + MGRID);
                    su.endEdit(grid);
                }
            });
            
            $editor.addXEventListener(ssk.KEY_UP, evt => {
                let $td = ti.closest($editor, "td." + v.CELL_CLS);
                if ($td) {
                    let coord = ti.getCellCoord($td);
                    let validator = _validators[coord.columnKey];
                    if (!validator) return;
                    let result = validator.probe($editor.value); 
                    let cell = { id: _dataSource[coord.rowIdx][_pk], index: coord.rowIdx, columnKey: coord.columnKey, element: $td };
                    khl.clear(cell);
                    
                    if (!result.isValid) {
                        khl.set(cell, result.errorMessage);
                    }
                }
            });
        }

        function checkBox(data: any): HTMLElement {
            let checkBoxText: string;
            let setChecked = data.update;
            let initValue = data.initValue;
            let $wrapper = document.createDocumentFragment();
            let text = data.controlDef.options[data.controlDef.optionsText];
            checkBoxText = text;
            
            let $checkBoxLabel = document.createElement("label");
            $checkBoxLabel.classList.add("ntsCheckBox");
            let $checkBox = document.createElement("input");
            $checkBox.setAttribute("type", "checkbox");
            $checkBox.addXEventListener("change", function() {
                setChecked($checkBox.checked);
            });
            $checkBoxLabel.appendChild($checkBox);
            
            let $box = document.createElement("span");
            $box.classList.add("box");
            $checkBoxLabel.appendChild($box);
            
            if (checkBoxText && checkBoxText.length > 0) {
                let span = document.createElement("span");
                span.classList.add("label");
                span.innerHTML = checkBoxText;
                $checkBoxLabel.appendChild(span);
            }
            
            $wrapper.appendChild($checkBoxLabel);
            let checked = initValue !== undefined ? initValue : true;

            if (checked === true) $checkBox.setAttribute("checked", "checked");
            else $checkBox.removeAttribute("checked");
            if (data.enable === true) $checkBox.removeAttribute("disabled");
            else $checkBox.setAttribute("disabled", "disabled");
            if (!controlType[data.columnKey]) {
                controlType[data.columnKey] = CHECKBOX;
            }
            return $wrapper;
        }
        
        export function comboBox(data: any): HTMLElement {
            let result = "", control = controlType[data.columnKey];
            if (control) {
                _.forEach(data.controlDef.options, i => {
                    let val = i[data.controlDef.optionsValue];
                    if (val === data.initValue) {
                        result = { code: val, name: i[data.controlDef.optionsText] };
//                        result = createItem(_prtDiv, val, i[data.controlDef.optionsText]);
                        return false;
                    }
                });
                
                return result;
            }
            
            let comboDiv = document.createElement("div");
            let $comboWrapper = comboDiv.cloneNode();
            $comboWrapper.classList.add("mcombo-wrapper");
            if (!data.controlDef.width) {
                $comboWrapper.style.width = "100%";
//                $comboWrapper.style.top = "-1px";
            } else {
                $comboWrapper.style.width = data.controlDef.width;
            }
            let $combo = comboDiv.cloneNode();
            $combo.classList.add(CBX_CLS);
            $combo.classList.add("ui-state-default");
            $comboWrapper.appendChild($combo);
            
            let $comboBtn = comboDiv.cloneNode();
            $comboBtn.classList.add("mcombo-button");
            $comboBtn.classList.add("ui-state-default");
            let $comboBtnIcon = comboDiv.cloneNode();
            $comboBtnIcon.classList.add("mcombo-buttonicon");
            $comboBtnIcon.classList.add("ui-icon-triangle-1-s");
            $comboBtnIcon.classList.add("ui-icon");
            $comboBtn.appendChild($comboBtnIcon);
            
            let $comboValue = comboDiv.cloneNode();
            $comboValue.classList.add("mcombo-value");
            $combo.appendChild($comboBtn);
            $combo.appendChild($comboValue);
            
            let $comboDropdown = comboDiv.cloneNode();
            $comboDropdown.classList.add("mcombo-dropdown");
            document.body.appendChild($comboDropdown);
            
            $comboBtn.addXEventListener(ssk.CLICK_EVT, function(evt) {
                if ($comboDropdown.style.height === "" || $comboDropdown.style.height === "0px") {
                    openDD($comboDropdown, $comboWrapper);
                    $combo.classList.add(CBX_ACTIVE_CLS);
                } else {
                    closeDD($comboDropdown);
                    $combo.classList.remove(CBX_ACTIVE_CLS);
                }
            });
            
            let $comboList = comboDiv.cloneNode();
            $comboList.classList.add("mcombo-list");
            $comboDropdown.appendChild($comboList);
            let $itemHolder = document.createElement("ul");
            $itemHolder.classList.add("mcombo-listitemholder");
            $comboList.appendChild($itemHolder);
            
            let val, textContent, maxHeight = 0, itemList = [], controlDef = data.controlDef;
            _.forEach(data.controlDef.options, i => {
                let $item = document.createElement("li");
                $item.classList.add("mcombo-listitem");
                $item.classList.add("ui-state-default");
                val = i[controlDef.optionsValue];
                $.data($item, "value", val);
//                textContent = text.padRight(val, ' ', 6) + i[controlDef.optionsText];
//                $item.textContent = textContent;
                // Create columns
                let $comboItem = createItem(comboDiv, val, i[controlDef.optionsText], $item);
                
                if (data.initValue === val) {
                    let initItem = $comboItem.cloneNode(true);
                    $comboValue.appendChild(initItem);
                    $item.classList.add("selecteditem");
                    result = { code: val, name: i[controlDef.optionsText] };
                }
                
                $item.addXEventListener(ssk.CLICK_EVT, evt => {
                    $comboValue.innerHTML = "";
                    $comboValue.appendChild($comboItem.cloneNode(true));
                    _.forEach(itemList, i => {
                        if (i.classList.contains("selecteditem")) {
                            i.classList.remove("selecteditem");
                        } 
                    });
                    
                    let value = $.data($item, lo.CBX_ITEM_VALUE);
                    $.data($comboWrapper, lo.CBX_SELECTED, value); 
                    $item.classList.add("selecteditem");
                    let $cbxCell = ti.closest($comboWrapper, "." + v.CELL_CLS);
                    if ($cbxCell) {
                        $.data($cbxCell, lo.CBX_SELECTED_TD, value);
                    }
                    closeDD($comboDropdown);
                    $combo.classList.remove(CBX_ACTIVE_CLS);
                    let coord = ti.getCellCoord($cbxCell);
                    data.update(value, coord.rowIdx);
                    let sCol = _specialColumn[data.columnKey];
                    if (sCol) {
                        let $cCell = lch.cellAt(_$grid[0], coord.rowIdx, sCol);
                        if ($cCell) { 
                            $cCell.textContent = value;
                            su.wedgeCell(_$grid[0], { rowIdx: coord.rowIdx, columnKey: sCol },  value);
                        }
                    }
                });
                $itemHolder.appendChild($item);
                itemList.add($item);
                maxHeight += 26;
            });
            
            $comboDropdown.style.maxHeight = (maxHeight + 2) + "px";
            controlType[data.columnKey] = { my: $comboWrapper, dropdown: $comboDropdown, options: data.controlDef.options, type: COMBOBOX };
            return result;
        }
        
        export function openDD($dd, $w) {
            let offset = selector.offset($w);
            $dd.style.top = (offset.top + BODY_ROW_HEIGHT - 2) + "px";
            $dd.style.left = offset.left + "px";
            $dd.style.width = $w.offsetWidth + "px";
            $dd.style.height = $dd.style.maxHeight;
        };
        
        export function closeDD($dd) {
            $dd.style.height = "0px";
//            setTimeout(() => {
                $dd.style.top = "-99999px";
                $dd.style.left = "-99999px";
//            }, 120);
        }
        
        function createItem(comboDiv: HTMLElement, code: any, name: any, $item?: HTMLElement) {
            let $comboItem = comboDiv.cloneNode();
            $comboItem.classList.add("mcombo-item");
            if ($item) $item.appendChild($comboItem);
            let $itemCode = comboDiv.cloneNode();
            $itemCode.classList.add("mcombo-item-column");
            $itemCode.style.width = "25%";
            $itemCode.style.float = "left";
            $itemCode.textContent = code;
            let $itemName = comboDiv.cloneNode();
            $itemName.classList.add("mcombo-item-column");
            $itemName.style.width = "75%";
            $itemName.textContent = name;
            $comboItem.appendChild($itemCode);
            $comboItem.appendChild($itemName);
            return $comboItem;
        }

        function button(data: any): HTMLElement {
            let $container = document.createDocumentFragment();
            let $button = document.createElement("button");
            $button.classList.add("mbutton");
            $container.appendChild($button);
            $button.innerHTML = data.controlDef.text || data.initValue;
            $button.setAttribute("tabindex", -1)
            $.data($button, "enable", data.enable);
            let clickHandle = data.controlDef.click.bind(null, data.rowObj);
            $.data($button, ssk.CLICK_EVT, clickHandle);
            if (data.enable) $button.addXEventListener("click", clickHandle);
            $button.disabled = !data.enable;
            if (!controlType[data.columnKey]) {
                controlType[data.columnKey] = BUTTON;
            }
            return $container;
        }
        
        function deleteButton(data: any): HTMLElement {
            let btnContainer = button(data);
            let btn = btnContainer.querySelector("button");
            btn.removeXEventListener("click", data.controlDef.click);
            btn.addXEventListener("click", data.deleteRow);
            return btnContainer;
        }
            
        function linkLabel(data: any): HTMLElement {
            let $container = document.createDocumentFragment();
            let $link = document.createElement("a");
            $link.classList.add("mlink-button");
            $link.textContent = data.initValue;
            let clickHandle = data.controlDef.click.bind(null, data.rowId, data.columnKey);
            if (data.enable) {
                $link.addXEventListener("click", clickHandle);
            } else $link.style.color = "#333";
            $.data($link, "click", clickHandle);
            $container.appendChild($link);
            if (!controlType[data.columnKey]) {
                controlType[data.columnKey] = LINK_LABEL;
            }
            return $container;
        }
            
        function flexImage(data: any): HTMLElement {
            let $container = document.createDocumentFragment();
            if (_.isNil(data.initValue) || _.isEmpty(data.initValue)) return $container;
            let $image = document.createElement("span");
            $image.className = data.controlDef.source;
            if (data.controlDef.click && _.isFunction(data.controlDef.click)) {
                let clickHandle = data.controlDef.click.bind(null, data.columnKey, data.rowId);
                if (data.enable) {
                    $image.addXEventListener(ssk.CLICK_EVT, clickHandle);
                    $image.style.cursor = "pointer";
                }
                $.data($image, ssk.CLICK_EVT, clickHandle);
            }
            
            $container.appendChild($image);
            if (!controlType[data.columnKey]) {
                controlType[data.columnKey] = FLEX_IMAGE;
            }
            return $container;
        }
            
        function image(data: any): HTMLElement {
            let $container = document.createDocumentFragment();
            let $span = document.createElement("span");
            $span.classList.add(data.controlDef.source);
            $container.appendChild($span);
            if (!controlType[data.columnKey]) {
                controlType[data.columnKey] = IMAGE;
            }
            
            return $container;
        }
    }
    
    module lch {
        
        export const CELL_SELECTED_CLS = "cell-selected";
        
        export class Cell {
            rowIndex: any;
            columnKey: any;
            value: any;
            
            constructor(rowIdx: any, columnKey: any, value: any) {
                this.rowIndex = rowIdx;
                this.columnKey = columnKey;
                this.value = value;
            }
        }
        
        export function checkUp($grid: HTMLElement) {
            let isSelecting;
            
            $grid.addXEventListener(ssk.MOUSE_DOWN, function(evt: any) {
                let $target = evt.target;
                isSelecting = true;
                if (!selector.is($target, ".mcell")) return;
                
                if (evt.shiftKey) {
                    selectRange($grid, $target);
                    $grid.onselectstart = function() {
                        return false;
                    };
                    return;
                }
                
                if (!evt.ctrlKey) {
                    clearAll($grid);
                }
                selectCell($grid, $target);
            });
            
            $grid.addXEventListener(ssk.MOUSE_UP, function(evt: any) {
                isSelecting = false;
                $grid.onselectstart = null;
            });
            
            $grid.addXEventListener(ssk.MOUSE_MOVE, function(evt: any) {
                if (isSelecting) {
                    selectRange($grid, evt.target);
                }
            });
        }
        
        /**
         * Select range.
         */
        function selectRange($grid: HTMLElement, $cell: HTMLElement) {
            if (_.isNil($cell) || !selector.is($cell, "td.mcell")) return;
            let lastSelected = $.data($grid, lo.LAST_SELECT);
            if (!lastSelected) { 
                selectCell($grid, $cell);
                return;
            }
            
            clearAll($grid);
            let toCoord = ti.getCellCoord($cell); 
            let minRowIdx = Math.min(lastSelected.rowIdx, toCoord.rowIdx);
            let maxRowIdx = Math.max(lastSelected.rowIdx, toCoord.rowIdx);
            for (let i = minRowIdx; i < maxRowIdx + 1; i++) { 
                cellInRange($grid, i, lastSelected.columnKey, toCoord.columnKey);
            }
        }
        
        /**
         * Mark cell.
         */
        export function markCell($cell: HTMLElement) {
            if (selector.is($cell, "td.mcell")) {
                $cell.classList.add(CELL_SELECTED_CLS);
                return true;
            }
            return false;
        }
        
        /**
         * Select cell.
         */
        export function selectCell($grid: HTMLElement, $cell: HTMLElement, notLast?: boolean) {
            if (!markCell($cell)) return;
            let coord = ti.getCellCoord($cell);
            addSelect($grid, coord.rowIdx, coord.columnKey, notLast);
            $cell.focus();
        }
        
        /**
         * Add select.
         */
        export function addSelect($grid: HTMLElement, rowIdx: any, columnKey: any, notLast?: boolean) {
            let selectedCells = $.data($grid, lo.SELECTED_CELLS);
            if (!notLast) $.data($grid, lo.LAST_SELECT, { rowIdx: rowIdx, columnKey: columnKey });
            if (!selectedCells) {
                selectedCells = {};
                selectedCells[rowIdx] = [ columnKey ];
                $.data($grid, lo.SELECTED_CELLS, selectedCells);
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
        export function clear($grid: HTMLElement, rowIdx: any, columnKey: any) {
            let selectedCells = $.data($grid, lo.SELECTED_CELLS);
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
            
            if (_.isNil(colIdx)) return;
            row.splice(colIdx, 1);
            let selectedCell = cellAt($grid, rowIdx, columnKey);
            if (selectedCell === null) return;
            if (selectedCell) {
                ti.removeClass(selectedCell, CELL_SELECTED_CLS);
            }
        }
        
        /**
         * Clear all.
         */
        export function clearAll($grid: HTMLElement) {
            let selectedCells = $.data($grid, lo.SELECTED_CELLS);
            if (!selectedCells) return;
            _.forEach(Object.keys(selectedCells), function(rowIdx: any, index: number) {
                _.forEach(selectedCells[rowIdx], function(col: any, i: number) {
                    let $cell = cellAt($grid, rowIdx, col);
                    if ($cell) {
                        ti.removeClass($cell, CELL_SELECTED_CLS);
                    }
                });
            });
            
            $.data($grid, lo.SELECTED_CELLS, null);
        }
        
        /**
         * Cell at.
         */
        export function cellAt($grid: HTMLElement, rowIdx: any, columnKey: any) {
            let rowArr = rowAt($grid, rowIdx);
            return getCellInRow(rowArr, columnKey);
        }
        
        export function rowAt($grid: HTMLElement, rowIdx: any) : Array<HTMLElement> {
            let desc = _mDesc;
            let fixed, row;
            if (!desc || !(row = desc.rows[rowIdx])) {
                return null;
            }
            
            if (desc.fixedRows && (fixed = desc.fixedRows[rowIdx])) {
                row = _.concat(fixed, row);
            } 
            return row;
        }
        
        /**
         * Cell in range.
         */
        export function cellInRange($grid: HTMLElement, rowIdx: any, startKey: any, endKey: any) {
            let range = [];
            let rowArr = rowAt($grid, rowIdx);
            let desc = _mDesc;
            if (_.isNil(rowArr) || _.isNil(desc)) return;
            let start, end, fixedCount = 0;
            if (desc.fixedColIdxes) {
                start = desc.fixedColIdxes[startKey];
                end = desc.fixedColIdxes[endKey];
                fixedCount = Object.keys(desc.fixedColIdxes).length;
            }
            
            if (_.isNil(start)) {
                start = desc.colIdxes[startKey] + fixedCount;
            }
            
            if (_.isNil(end)) {
                end = desc.colIdxes[endKey] + fixedCount;
            }
            
            if (_.isNil(start) || _.isNil(end)) return;
            let min = Math.min(start, end);
            let max = Math.max(start, end);
            _.forEach(rowArr, (c, index) => {
                if (c.style.display !== "none" && index >= min && index <= max) {
                    ti.addClass(c, CELL_SELECTED_CLS);
                    let coord = ti.getCellCoord(c);
                    
                    if (coord) {
                        addSelect($grid, rowIdx, coord.columnKey, true);
                        range.push(c);
                    }
                } else if (index > max) return;
            });
            
            return range;
        }
        
        /**
         * Get cell in row.
         */
        export function getCellInRow(rowArr: any, columnKey: any) {
            if (_.isNil(rowArr)) return null;
            
            return _.find(rowArr, c => {
                if (c.style.display === "none") return false;
                let coord = ti.getCellCoord(c);    
                if (coord.columnKey === columnKey) return true;
            });
        }
        
        /**
         * Get selected cells.
         */
        export function getSelectedCells($grid: HTMLElement) {   
            let selectedCells = $.data($grid, lo.SELECTED_CELLS);
            let desc = _mDesc;
            let dataSource = _dataSource;
            let cells = [];
            _.forEach(Object.keys(selectedCells), function(rowIdx: any) {
                _.forEach(selectedCells[rowIdx], function(colKey: any) {
                    cells.push(new Cell(rowIdx, colKey, dataSource[rowIdx][colKey]));
                });
            });
            return cells;
        }
        
        export function selectNext($grid: HTMLElement, direct?: boolean) {
            let selectedCells = $.data($grid, lo.SELECTED_CELLS);
            if (!selectedCells) return;
            let sortedKeys = Object.keys(selectedCells).sort((o, t) => o - t);
            let cell, nCell, colElms, key, fixedCount = 0, dCount = 0, colIdx, desc = _mDesc, ds = _dataSource, tRowIdx = _.min(sortedKeys);
            if (_.isNil(tRowIdx)) return;
            
            if (desc.fixedColIdxes) {
                fixedCount = Object.keys(desc.fixedColIdxes).length;
            }
            
            colIdx = _.min(_.map(selectedCells[tRowIdx], c => {
                let idx, isFixed = true;
                if (desc.fixedColIdxes) {
                    idx = desc.fixedColIdxes[c];
                }
                
                if (_.isNil(idx)) {
                    idx = desc.colIdxes[c];
                    isFixed = false;
                }
                
                return isFixed ? idx : idx + fixedCount;
            }));
            
            dCount = Object.keys(desc.colIdxes).length;
            
            if (direct === "below") {
                if (ds && parseInt(tRowIdx) === ds.length - 1) {
                    tRowIdx = 0;
                    if (fixedCount + dCount === colIdx + 1) {
                        colIdx = 0;
                    } else colIdx++;
                } else {
                    tRowIdx++;
                }
            } else if (fixedCount + dCount === colIdx + 1) {
                colIdx = 0;
                if (ds && parseInt(tRowIdx) === ds.length - 1) {
                    tRowIdx = 0;
                } else {
                    tRowIdx++;
                }
            } else {
                colIdx++;
            }
                
            colElms = rowAt($grid, tRowIdx);
            nCell = colElms[colIdx];
            clearAll($grid);
            
            if (tc.visualJumpTo($grid, tRowIdx)) {
                setTimeout(() => selectCell($grid, nCell), 1);
                return;
            }
            
            selectCell($grid, nCell);
        }
        
        export function selectPrev($grid: HTMLElement, direct?: boolean) {
            let selectedCells = $.data($grid, lo.SELECTED_CELLS);
            if (!selectedCells) return;
            let sortedKeys = Object.keys(selectedCells).sort((o, t) => o - t);
            let cell, nCell, colElms, key, fixedCount = 0, dCount = 0, colIdx, desc = _mDesc, ds = _dataSource, tRowIdx = _.min(sortedKeys);
            if (_.isNil(tRowIdx)) return;
            
            if (desc.fixedColIdxes) {
                fixedCount = Object.keys(desc.fixedColIdxes).length;
            }
            
            colIdx = _.min(_.map(selectedCells[tRowIdx], c => {
                let idx, isFixed = true;
                if (desc.fixedColIdxes) {
                    idx = desc.fixedColIdxes[c];
                }
                
                if (_.isNil(idx)) {
                    idx = desc.colIdxes[c];
                    isFixed = false;
                }
                
                return isFixed ? idx : idx + fixedCount;
            }));
            
            dCount = Object.keys(desc.colIdxes).length;
            
            if (direct === "below") {
                if (tRowIdx === 0) {
                    tRowIdx = ds.length - 1;
                    if (colIdx === 0) {
                        colIdx = fixedCount + dCount - 1;
                    }
                } else {
                    tRowIdx--;
                }
            } else if (colIdx === 0) {
                colIdx = fixedCount + dCount - 1;
                if (tRowIdx === 0) {
                    tRowIdx = ds.length - 1;
                } else {
                    tRowIdx--;
                }
            } else {
                colIdx--;
            }
                
            colElms = rowAt($grid, tRowIdx);
            nCell = colElms[colIdx];
            clearAll($grid);
            
            if (tc.visualJumpTo($grid, tRowIdx)) {
                setTimeout(() => selectCell($grid, nCell), 1);
                return;
            }
            
            selectCell($grid, nCell);
        }
    }
    
    module hpl {
        export const VALIDATORS = "mValidators"; 
        export const CURRENCY_CLS = "currency-symbol";
        let H_M_MAX: number = 60;
        
        export class ColumnFieldValidator {
            name: string;
            primitiveValue: string;
            options: any;
            constructor(name: string, primitiveValue: string, options: any) {
                this.name = name;
                this.primitiveValue = primitiveValue;
                this.options = options;
            }
            
            probe(value: string) {
                let valueType = this.primitiveValue ? ui.validation.getConstraint(this.primitiveValue).valueType
                                : this.options.cDisplayType;
                switch (valueType) {
                    case "String":
                        return new nts.uk.ui.validation.StringValidator(this.name, this.primitiveValue, this.options)
                                .validate(value, this.options);
                    case "Integer":
                    case "Decimal":
                    case "HalfInt":
                        return new NumberValidator(this.name, valueType, this.primitiveValue, this.options) 
                               .validate(value);
                    case "Currency":
                        let opts: any = new ui.option.CurrencyEditorOption();
                        opts.grouplength = this.options.groupLength | 3;
                        opts.decimallength = this.options.decimalLength | 2;
                        opts.currencyformat = this.options.currencyFormat ? this.options.currencyFormat : "JPY";
                        return new NumberValidator(this.name, valueType, this.primitiveValue, opts)
                                .validate(value);
                    case "Time":
                        this.options.mode = "time";
                        return new nts.uk.ui.validation.TimeValidator(this.name, this.primitiveValue, this.options)
                                .validate(value);
                    case "Clock":
                        // Don't merge with time type.
                        this.options.mode = "time";
                        return new nts.uk.ui.validation.TimeValidator(this.name, this.primitiveValue, this.options)
                                .validate(value);
                    case "TimeWithDay":
                        this.options.timeWithDay = true;
                        let result = new ui.validation.TimeWithDayValidator(this.name, this.primitiveValue, this.options)
                                        .validate(value);
                        if (result.isValid) {
                            let formatter = new text.TimeWithDayFormatter(this.options);
                            result.parsedValue = formatter.format(result.parsedValue);
                        }
                        return result;
                }
            }
        }
        
        class NumberValidator {
            name: string;
            displayType: string;
            primitiveValue: string;
            options: any;
            constructor(name: string, displayType: string, primitiveValue?: string, options?: any) {
                this.name = name;
                this.displayType = displayType;
                this.primitiveValue = primitiveValue;
                this.options = options;
            }
            
            validate(text: string) {
                let self = this;
                if (self.primitiveValue) {
                    return new nts.uk.ui.validation.NumberValidator(self.name, self.primitiveValue, self.options).validate(text);
                }
                
                if (self.displayType === "Currency") {
                    text = uk.text.replaceAll(text, self.options.groupseperator, "");
                }
                
                let result = new ui.validation.ValidationResult();
                if ((util.isNullOrUndefined(text) || text.length === 0)) {
                    if (self.options && self.options.required) {
                        result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [ self.name ]), 'FND_E_REQ_INPUT');
                        return result;
                    }
                    if (!self.options || (self.options && !self.options.required)) {
                        result.success(text);
                        return result;
                    }
                }
                let message: any = {};
                let isValid;
                if (self.displayType === "HalfInt") {
                     isValid = ntsNumber.isHalfInt(text, message);
                } else if (self.displayType === "Integer") {
                    isValid = ntsNumber.isNumber(text, false, self.options, message);
                } else if (self.displayType === "Decimal" || self.displayType === "Currency") {
                    isValid = ntsNumber.isNumber(text, true, self.options, message);
                }
                
                let min = 0, max = 999999999;
                let value = parseFloat(text);
                if (!util.isNullOrUndefined(self.options.min)) {
                    min = self.options.min;
                    if (value < min) isValid = false;
                }
                if (!util.isNullOrUndefined(self.options.max)) {
                    max = self.options.max;
                    if (value > max) isValid = false;
                }
                
                if (!isValid) {
                    result.fail(resource.getMessage(message.id, [ self.name, min, max ]), message.id);
                    return result;
                }
                
                let formatter = new uk.text.NumberFormatter({ option: self.options });
                let formatted = formatter.format(text);
                result.success(self.displayType === "Currency" ? formatted : value + "");
                return result;
            }
        }
        
        export class Result {
            isValid: boolean;
            formatted: any;
            errorMessageId: string;
            onSuccess: any = $.noop;
            onFail: any = $.noop;
            constructor(isValid: boolean, formatted?: any, messageId?: string) {
                this.isValid = isValid;
                this.formatted = formatted;
                this.errorMessageId = messageId;
            }
            
            static OK(formatted: any) {
                return new Result(true, formatted);
            }
            
            static invalid(msgId: string) {
                return new Result(false, null, msgId);
            }
            
            success(cnt: any) {
                this.onSuccess = cnt;
                return this;
            }
            
            fail(cnt: any) {
                this.onFail = cnt;
                return this;
            }
            
            terminate() {
                let self = this;
                if (self.isValid && self.onSuccess && _.isFunction(self.onSuccess)) {
                     self.onSuccess(self.formatted);
                } else if (!self.isValid && self.onFail && _.isFunction(self.onFail)) {
                    self.onFail(self.errorMessageId);
                }
            }
        }
        
        export function parseTime(value: any, format?: any): Result {
            if (uk.ntsNumber.isNumber(value, false)) {
                if (value <= H_M_MAX) return Result.OK(value);
                let hh = Math.floor(value / 100);
                let mm = value % 100;
                if (mm >= H_M_MAX) return Result.invalid("NEED_MSG_INVALID_TIME_FORMAT");
                return Result.OK(hh + ":" + mm.toLocaleString("en-US", { minimumIntegerDigits: 2, useGrouping: false }));
            }
            let formatRes = uk.time.applyFormat(format, value, undefined);
            if (!formatRes) return Result.invalid("NEED_MSG_INVALID_TIME_FORMAT");
            return Result.OK(formatRes);
        }
        
        export function getValueType(columnKey: any) {
            if (!_validators || !_validators[columnKey]) return;
            let column = _validators[columnKey];
            return column.primitiveValue ? ui.validation.getConstraint(column.primitiveValue).valueType
                                : column.options.cDisplayType
        }
        
        export function getGroupSeparator(columnKey: any) {
            if (!_validators || !_validators[columnKey]) return;
            return _validators[columnKey].options.groupseperator;
        }
    }
    
    module khl {
        export let ERROR_CLS = "merror";
        
        export class GridCellError {
            grid: JQuery = _$grid;
            index: any;
            rowId: any;
            columnKey: string;
            columnName: string;
            message: string;
            
            constructor(index: any, rowId: any, columnKey: any, message: string) {
                this.index = index;
                this.rowId = rowId;
                this.columnKey = columnKey;
                this.message = message;
                let col = _columnsMap[this.columnKey];
                if (col) this.columnName = col[0].headerText; 
            }
            
            equals(err: GridCellError) {
                if (this.index !== err.index) return false;
                if (this.rowId !== err.rowId) return false;
                if (this.columnKey !== err.columnKey) return false;
                return true;
            }
        }
        
        function addCellError(error: any) {
            if (_errors.some(function(e: GridCellError) {
                return e.equals(error);
            })) return;
            
            _errors.push(error);
        }
    
        function removeCellError(rowId: any, key: any) {
            _.remove(_errors, function(e) {
                return rowId === e.rowId && key === e.columnKey;
            });
        }
        
        export function set(cell: any, message: string) {
            if (!cell || !cell.element || any(cell)) return;
            let $cell = cell.element;
            $cell.classList.add(ERROR_CLS);
            let errorDetails = createErrorInfos(cell, message);
            if (_errorsOnPage) {
                ui.errors.addCell(errorDetails);
            }
            addCellError(errorDetails);
        }
        
        function createErrorInfos(cell: any, message: string): any {
            let record: any = _dataSource[cell.index];
            let error: any = new GridCellError(cell.index, cell.id, cell.columnKey, message);
            // Error column headers
            let headers;
            if (_errorsOnPage) {
                let columns = ko.toJS(ui.errors.errorsViewModel().option().headers());
                if (columns) {
                    headers = columns.filter(c => c.visible).map(c => c.name); 
                }
            } else { 
                headers = _errorColumns;
            }
            _.forEach(headers, function(header: any) {
                if (_.isNil(record[header]) 
                    || !_.isNil(error[header])) return;
                error[header] = record[header];
            });
            return error;
        } 
        
        export function clear(cell: any) {
            if (!cell || !cell.element || !any(cell)) return;
            let $cell = cell.element;
            $cell.classList.remove(ERROR_CLS);
            if (_errorsOnPage) {
                ui.errors.removeCell(_$grid, cell.id, cell.columnKey);
            }
            removeCellError(cell.id, cell.columnKey);
        }
        
        export function any(cell: any) {
            return cell.element && cell.element.classList.contains(ERROR_CLS);
        }
        
        function cellEquals(one: any, other: any) {
            if (one.columnKey !== other.columnKey) return false;
            if (one.id !== other.id) return false;
            return true;
        }
    }
    
    module selector {
        
        export function find(p: HTMLElement, sel: any) {
            return new Manipulator().addNodes(p.querySelectorAll(sel));
        }
        
        export function create(str: any) {
            return new Manipulator().addElement(document.createElement(str));
        }
        
        export function is(el, sel) {
            let matches = el.matches || el.matchesSelector || el.msMatchesSelector || el.mozMatchesSelector || el.webkitMatchesSelector || el.oMatchesSelector;
            if (matches) return matches.call(el, sel);
            return $(el).is(sel);
        }
        
        export function index(el) {
            return Array.prototype.slice.call(el.parentNode.children).indexOf(el);
        }
        
        export function queryAll(el, sel): Array<HTMLElement> {
            return Array.prototype.slice.call(el.querySelectorAll(sel));
        }
        
        export function offset(el) {
            let rect = el.getBoundingClientRect();
            return {
              top: rect.top + document.body.scrollTop,
              left: rect.left + document.body.scrollLeft
            }
        }
        
        export function classSiblings(node: any, clazz: any) {
            let parent = node.parentElement;
            if (!parent) return;
            let children = parent.children;
            let results = [];
            for (let i = 0; i < children.length; i++) {
                if (children[i] === node) continue;
                let classList = children[i].classList;
                for (let j = 0; j < classList.length; j++) {
                    if (classList.item(j) === clazz) {
                        results.push(children[i]);
                        break;
                    }
                }
            }
            return results;
        }
        
        export function siblingsLt(el, index) {
            let parent = el.parentNode;
            if (!parent) return;
            let children = parent.children;
            let results = [];
            for (let i = 0; i < children.length; i++) {
                if (i < index) {
                    if (children[i] !== el) results.push(children[i]);
                } else return results;
            }
        }
        
        export class Manipulator {
            elements: Array<HTMLElement>;
                
            addNodes(nodes: NodeList) {
                if (!nodes || nodes.length === 0) return;
                this.elements = Array.prototype.slice.call(self.elements);
                return this;
            }
            
            addElements(elements: Array<HTMLElement>) {
                this.elements = elements;
                return this;
            }
            
            addElement(element: HTMLElement) {
                if (!this.elements) this.elements = [];
                this.elements.push(element);
                return this;
            }
            
            html(str) {
                this.elements.forEach(function(e) {
                    e.innerHTML = str;
                });
                return this;
            }
            
            width(w) {
                let self = this;
                this.elements.forEach(function(e) {
                    e.style.width = parseInt(w) + "px";
                });
                return this;
            }
            
            height(h) {
                let self = this;
                this.elements.forEach(function(e) {
                    e.style.height = parseInt(h) + "px";
                });
                return this;
            }
            
            data(name: any, value: any) {
                this.elements.forEach(function(e) {
                    $.data(e, name, value);
                });
                return this;
            }
            
            addClass(clazz: any) {
                this.elements.forEach(function(e) {
                    e.classList.add(clazz);
                });
                return this;
            }
            
            css(style: any) {
                let text = "; ";
                Object.keys(style).forEach(function(k) {
                    if (k === "maxWidth") {
                        text += ("max-width: " + style[k] + "; ");
                        return;
                    }
                    text += k + ": " + style[k] + "; ";
                });
                
                this.elements.forEach(function(e) {
                    e.style.cssText = text;
                });
                
                return this;
            }
            
            getSingle() {
                return this.elements[0];
            }
            
            get() {
                return this.elements;
            }
        }
    }
    
    export module color {
        export const Error = "mgrid-error";
        export const Alarm = "mgrid-alarm";
        export const ManualEditTarget = "mgrid-manual-edit-target";
        export const ManualEditOther = "mgrid-manual-edit-other";
        export const Reflect = "mgrid-reflect";
        export const Calculation = "mgrid-calc";
        export const Disable = "mgrid-disable";
        export const HOVER = "ui-state-hover"; 
        
        export function pushState(id: any, key: any, state: any) {
            if (!_cellStates[id]) {
                _cellStates[id] = { key: [{ rowId: id, columnKey: key, state: _.concat([], state) }]};
                return;
            }
            
            if (!_cellStates[id][key]) {
                _cellStates[id][key] = [{ rowId: id, columnKey: key, state: _.concat([], state) }];
                return;
            }
            
            if (_.isArray(state)) {
                _.forEach(state, s => {
                    _cellStates[id][key][0].state.push(s);
                });
            } else _cellStates[id][key][0].state.push(state);
        }
        
        export function popState(id: any, key: any, state: any) {
            if (!_cellStates[id] || !_cellStates[id][key]) return;
            _.remove(_cellStates[id][key][0].state, s => s === state);
        }
    }
    
     module ti {
        
        /**
         * Is IE.
         */
        export function isIE() {
            return window.navigator.userAgent.indexOf("MSIE") > -1 || window.navigator.userAgent.match(/trident/i);
        }
        
        /**
         * Is Chrome.
         */
        export function isChrome() {
            return window.chrome;
        }
        
        /**
         * Is Edge.
         */
        export function isEdge() {
            return window.navigator.userAgent.indexOf("Edge") > -1;
        }
         
        export function isArrowKey(evt: any) {
            return evt.keyCode >= 37 && evt.keyCode <= 40;
        }
         
        export function isArrowLeft(evt: any) {
            return evt.keyCode === 37;
        }
        
        export function isArrowRight(evt: any) {
            return evt.keyCode === 39;
        }
         
        export function isArrowUp(evt: any) {
            return evt.keyCode === 38;
        }
         
        export function isArrowDown(evt: any) {
            return evt.keyCode === 40;
        } 
         
        export function isAlphaNumeric(evt: any) {
            return (evt.keyCode >= 48 && evt.keyCode <= 90) 
                    || (evt.keyCode >= 96 && evt.keyCode <= 105);
        }
         
        export function isMinusSymbol(evt: any) {
            return evt.keyCode === 189 || evt.keyCode === 109;
        }
         
        export function isTabKey(evt: any) {
            return evt.keyCode === 9;
        }
         
        export function isEnterKey(evt: any) {
            return evt.keyCode === 13;
        }
         
        export function isSpaceKey(evt: any) {
            return evt.keyCode === 32;
        }
         
        export function isDeleteKey(evt: any) {
            return evt.keyCode === 46;
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
         
        export function isZero(value: any) {
            return parseFloat(value) === 0 || value === "0" || value === "0:00";
        }
         
        export function isTableCell(obj: any) {
            return obj.constructor === HTMLTableCellElement 
                || obj.constructor === HTMLTableDataCellElement;
        }
         
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
         * Get scroll width.
         */
        export function getScrollWidth() {
            if (_scrollWidth) return _scrollWidth;
            let $outer = document.body.appendChild(selector.create("div").css({ visibility: 'hidden', width: "100px", overflow: 'scroll' }).getSingle());
            let $inner = selector.create("div").css({ width: '100%' }).getSingle();
            $outer.appendChild($inner);
            let widthWithScroll = $inner.offsetWidth;
            $outer.parentNode.removeChild($outer);
            _scrollWidth = 100 - widthWithScroll;
            return _scrollWidth;
        }
         
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
         
        export function addClass1n(node, clazz) {
            if (node && node.constructor !== HTMLCollection) {
                let children = node.querySelectorAll("." + v.CHILD_CELL_CLS); 
                if (children.length > 0) addClass(children, clazz);
                else addClass(node, clazz);
                return;
            }
            for (let i = 0; i < node.length; i++) {
                let children = node[i].querySelectorAll("." + v.CHILD_CELL_CLS);
                if (children.length > 0) addClass(children, clazz);
                else addClass(node[i], clazz);
            }
        }
        
        /**
         * Remove class.
         */
        export function removeClass1n(node, clazz) {
            if (node && node.constructor !== HTMLCollection) {
                let children = node.querySelectorAll("." + v.CHILD_CELL_CLS);
                if (children.length > 0) removeClass(children, clazz);
                else removeClass(node, clazz);
                return;
            }
            for (let i = 0; i < node.length; i++) {
                let children = node[i].querySelectorAll("." + v.CHILD_CELL_CLS);
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
         
        export function classifyColumns(options: any) {
            let visibleColumns = [];
            let hiddenColumns = [];
            filterColumns(options.columns, visibleColumns, hiddenColumns);
            return {
                        visibleColumns: visibleColumns,
                        hiddenColumns: hiddenColumns
                   };
        }
         
        export function getColumnsMap(columns: any) {
            return _.groupBy(columns, "key");
        }
         
        function filterColumns(columns: Array<any>, visibleColumns: Array<any>, hiddenColumns: Array<any>) {
            _.forEach(columns, function(col: any) {
                if (!_.isNil(col.hidden) && col.hidden === true) {
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
         
         export function getCellCoord($cell: HTMLElement) {
            if (!$cell) return;
            let $td = $cell;
            if (selector.is($cell, "div")) {
                $td = closest($cell, "td");
            }
            let view = $.data($td, lo.VIEW);
            if (!view) return;
            let coord = view.split("-");
            if (util.isNullOrUndefined(coord[0]) || util.isNullOrUndefined(coord[1])) return;
            return {
                rowIdx: parseFloat(coord[0]),
                columnKey: coord[1]
            };
        }
     }
}