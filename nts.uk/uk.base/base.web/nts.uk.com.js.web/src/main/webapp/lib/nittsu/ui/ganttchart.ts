module nts.uk.ui.chart {
    
    export let warning = [];
    export class Ruler {
        
        chartArea: HTMLElement;
        definedType: any = {};
        gcChart: any = {};
        lineLock: any = {};
        slideTrigger: any;
        
        constructor(chartArea: HTMLElement) {
            if (_.isNil(chartArea)) {
                warning.push(new Error("chartArea is undefined."));
            }
            this.chartArea = chartArea;
        }
        
        addType(options: any) {
            let self = this;
            if (_.isNil(options.name)) {
                warning.push(new Warn("Set type name"));
                return;
            }
            
            self.definedType[options.name] = new DefinedType(options);
            if (options.locked) {
                self.lineLock[options.lineNo] = true;
            }
        }
        
        addChart(options?: any) {
            let self = this, chart = new GanttChart(options);
            if (chart.locked) {
                self.lineLock[chart.lineNo] = true;
            }
            
            if ((self.gcChart[options.lineNo] || {})[options.id]) {
                warning.push(new Warn("Chart id existed"));
                return;
            }
            
            if (chart.newChart()) return;
            if (_.isNil(self.gcChart[options.lineNo])) {
                self.gcChart[options.lineNo] = {};
            }
            
            self.gcChart[options.lineNo][options.id] = chart;
            
            if (!_.isNil(options.parent)) {
                let parent = self.gcChart[options.lineNo][options.parent];
                if (parent) {
                    parent.children.push(chart);
                }
            }
            
            self.chartArea.appendChild(chart.html);
            let docMove = () => {
                if (_.keys(self.slideTrigger).length === 0) return;
                let diff = event.pageX - self.slideTrigger.pageX, nearestLine, parentChart;
                if (!_.isNil(chart.parent)) {
                    parentChart = self.gcChart[chart.lineNo][chart.parent];
                }
                
                if (self.slideTrigger.holdPos === HOLD_POS.BODY) {
                    if (!chart.canSlide) return;
                    nearestLine = Math.round((self.slideTrigger.start + diff / chart.unitToPx) / chart.snatchInterval);
                    let step = nearestLine - self.slideTrigger.start, pDec = { left: nearestLine * chart.unitToPx, start: nearestLine, end: self.slideTrigger.end + step };
                    if (chart.limitStart > pDec.start || chart.limitEnd < pDec.end) return;
                    if (parentChart && ((diff > 0 && pDec.end > parentChart.end) || (diff < 0 && pDec.start < parentChart.start))) return;
                    
                    _.forEach(chart.children, (child: GanttChart) => {
                        let childSlide;
                        if (child.followParent) {
                            childSlide = _.find(self.slideTrigger.children, c => c.id === child.id);
                            if (!childSlide) return;
                            child.reposition({ start: childSlide.start + step, end: childSlide.end + step, left: (childSlide.start + step) * child.unitToPx });
                        }
                        
                        if (diff > 0 && child.start < pDec.start) {
                            childSlide = _.find(self.slideTrigger.children, c => c.id === child.id);
                            if (!childSlide) return;
                            child.reposition({ width: childSlide.length + (childSlide.start - pDec.start) * child.unitToPx, left: pDec.start * child.unitToPx, start: pDec.start });
                        } else if (diff < 0 && child.end > pDec.end) {
                            childSlide = _.find(self.slideTrigger.children, c => c.id === child.id);
                            if (!childSlide) return;
                            child.reposition({ width: childSlide.length + (pDec.end - childSlide.end) * child.unitToPx, end: pDec.end });
                        }
                    });
                    
                    chart.reposition(pDec);
                } else if (self.slideTrigger.holdPos === HOLD_POS.START) {
                    if (chart.fixed === CHART_FIXED.START || chart.fixed === CHART_FIXED.BOTH) return;
                    nearestLine = Math.round((self.slideTrigger.start + diff / chart.unitToPx) / chart.snatchInterval);
                    let pDec = { width: self.slideTrigger.length + (self.slideTrigger.start - nearestLine) * chart.unitToPx, left: nearestLine * chart.unitToPx, start: nearestLine };
                    if (chart.limitStart > pDec.start) return;
                    if (pDec.start + chart.snatchInterval > chart.end
                        || (parentChart && !self.slideTrigger.overlap && pDec.start < parentChart.start)) return;
                    
                    _.forEach(chart.children, (child: GanttChart) => {
                        if (child.start < pDec.start) {
                            let childSlide = _.find(self.slideTrigger.children, c => c.id === child.id);
                            if (!childSlide) return;
                            child.reposition({ width: childSlide.length + (childSlide.start - pDec.start) * child.unitToPx, left: pDec.start * child.unitToPx, start: pDec.start }); 
                        }
                    });
                    
                    if (self.slideTrigger.overlap) {
                        parentChart.reposition({ width: self.slideTrigger.overlap.parentLength + (self.slideTrigger.start - nearestLine) * parentChart.unitToPx, left: pDec.left, start: pDec.start });
                    }
                    
                    chart.reposition(pDec);
                } else {
                    if (chart.fixed === CHART_FIXED.END || chart.fixed === CHART_FIXED.BOTH) return;
                    nearestLine = Math.round((self.slideTrigger.end + diff / chart.unitToPx) / chart.snatchInterval);
                    let pDec = { width: self.slideTrigger.length + (nearestLine - self.slideTrigger.end) * chart.unitToPx, end: nearestLine };
                    if (chart.limitEnd < pDec.end) return;
                    if (chart.start + chart.snatchInterval > pDec.end
                        || (parentChart && !self.slideTrigger.overlap && pDec.end > parentChart.end)) return;
                    
                    _.forEach(chart.children, (child: GanttChart) => {
                        if (child.end > pDec.end) {
                            let childSlide = _.find(self.slideTrigger.children, c => c.id === child.id);
                            if (!childSlide) return;
                            child.reposition({ width: childSlide.length + (pDec.end - childSlide.end) * child.unitToPx, end: pDec.end });
                        }
                    });
                    
                    if (self.slideTrigger.overlap) {
                        parentChart.reposition({ width: self.slideTrigger.overlap.parentLength + (nearestLine - self.slideTrigger.end) * parentChart.unitToPx, end: pDec.end });
                    }
                    
                    chart.reposition(pDec);
                }
            };
            
            let docUp = () => {
                document.removeEventListener("mousemove", docMove);
                document.removeEventListener("mouseup", docUp);
                
                let e = document.createEvent('CustomEvent');
                if (self.slideTrigger.holdPos === HOLD_POS.BODY) {
                    e.initCustomEvent("gcDrag", true, true, [ chart.start, chart.end ]);
                } else {
                    e.initCustomEvent("gcResize", true, true, [ chart.start, chart.end, self.slideTrigger.holdPos === HOLD_POS.START ]);
                }
                
                self.slideTrigger = {};
                chart.html.dispatchEvent(e);
            };
            
            chart.html.addEventListener("mousedown", () => {
                let holdPos = self.getHoldPos(chart);
                if (holdPos === HOLD_POS.OUT) return;
                self.slideTrigger = { 
                    pageX: event.pageX,
                    holdPos: holdPos,
                    length: parseFloat(chart.html.style.width),
                    start: chart.start,
                    end: chart.end,
                    children: _.map(chart.children, c => { return { id: c.id, start: c.start, end: c.end, length: parseFloat(c.html.style.width) }; })
                }
                
                if (!_.isNil(chart.parent)) {
                    let parentChart = self.gcChart[chart.lineNo][chart.parent];
                    if ((holdPos === HOLD_POS.START && parentChart.start === chart.start)
                        || (holdPos === HOLD_POS.END && parentChart.end === chart.end)) {
                        self.slideTrigger.overlap = { parentLength: parseFloat(parentChart.html.style.width) };
                    }
                }
                
                document.addEventListener("mousemove", docMove);
                document.addEventListener("mouseup", docUp);
            });
            
            chart.html.addEventListener("mousemove", () => {
                let holdPos = self.getHoldPos(chart);
                if (holdPos === HOLD_POS.START || holdPos === HOLD_POS.END) {
                    chart.cursor = "col-resize";
                } else if (holdPos === HOLD_POS.BODY) {
                    chart.cursor = "e-resize";
                }
                
                chart.html.style.cursor = chart.cursor;
            });
            
            return chart.html;
        }
        
        getHoldPos(chart: GanttChart) {
            let self = this;
            if (self.lineLock[chart.lineNo] || chart.fixed === CHART_FIXED.BOTH) return HOLD_POS.OUT;
            if (chart.fixed !== CHART_FIXED.START && event.offsetX < chart.drawerSize) {
                return HOLD_POS.START;
            } else if (chart.fixed !== CHART_FIXED.END
                && (chart.end - chart.start) * chart.unitToPx - chart.drawerSize < event.offsetX) {
                return HOLD_POS.END;
            } else {
                return HOLD_POS.BODY;
            }
        }
        
        addChartWithType(typeName: string, options?: any) {
            let self = this,
                chartType = self.definedType[typeName];
            if (_.isNil(options)) {
                options = {};
            }
            
            if (chartType) {
                _.forEach(_.keys(chartType), key => {
                    if (chartType[key] !== undefined) {
                        options[key === "name" ? "definedType" : key] = chartType[key];
                    }
                });
            }
            
            return self.addChart(options);
        }
        
        setLock(lines: Array<any>, lock: any) {
            let self = this;
            _.forEach(lines, line => self.lineLock[line] = lock); 
        }
    }
    
    class DefinedType {
        
        name: string;
        parent: string;
        lineNo: number;
        color: string;
        followParent: boolean;
        canSlide: boolean;
        cursor: string;
        limitStart: number;
        limitEnd: number;
        fixed: CHART_FIXED;
        unitToPx: number;
        locked: boolean;
        chartWidth: number;
        lineWidth: number;
        snatchInterval: number;
        drawerSize: number;
        
        constructor(options) {
            this.name = options.name;
            this.parent = options.parent;
            this.lineNo = options.lineNo;
            this.color = options.color;
            this.followParent = options.followParent;
            this.canSlide = options.canSlide;
            this.cursor = options.cursor;
            this.limitStart = options.limitStart;
            this.limitEnd = options.limitEnd;
            this.unitToPx = options.unitToPx;
            this.fixed = options.fixed;
            this.locked = options.locked;
            this.chartWidth = options.chartWidth;
            this.lineWidth = options.lineWidth;
            this.snatchInterval = options.snatchInterval;
            this.drawerSize = options.drawerSize;
        }
    }
    
    class GanttChart {
        
        lineNo: number;
        id: string;
        parent: string;
        children: Array<GanttChart> = [];
        definedType: string;
        maxArea: number = 50;
        start: number;
        end: number;
        zIndex: number = 1000;
        color: string = "#b8f441";
        origin: Array<any> = [0, 0];
        chartWidth: number = 15;
        lineWidth: number = 20;
        unitToPx: number = 10;
        snatchInterval: number = 1;
        canSlide: boolean = false;
        limitStart: number = 0;
        limitEnd: number;
        followParent: boolean = false;
        fixed: CHART_FIXED = CHART_FIXED.NONE;
        drawerSize: number = 3;
        cursor: string;
        locked: boolean = false;
        html: HTMLElement;
        
        constructor(options: any) {
            let self = this;
            if (!_.keys(options).length) return;
            self.limitEnd = options.limitEnd || options.maxArea || self.maxArea;
            $.extend(self, options);
        }
        
        newChart() {
            
            if (_.isNil(this.id)) {
                warning.push(new Warn("Not set id"));
                return 1;
            }
            
            if (_.isNil(this.lineNo)) {
                warning.push(new Warn("Not set lineNo"));
                return 1;
            }
            
            if (this.limitStart > this.start || this.limitEnd < this.end) {
                warning.push(new Warn("Start/end is not valid"));
                return 1;
            }
            
            let self = this,
                posTop = self.origin[1] + self.lineNo * self.lineWidth + Math.floor((self.lineWidth - self.chartWidth) / 2),
                posLeft = self.origin[0] + self.start * self.unitToPx,
                chart = document.createElement("div");
            chart.setAttribute("id", `${self.lineNo}-${self.id}`);
            chart.className = "nts-ganttchart";
            chart.style.cssText = `; position: absolute; top: ${posTop}px; left: ${posLeft}px; z-index: ${self.zIndex}; 
                overflow: hidden; white-space: nowrap; width: ${(self.end - self.start) * self.unitToPx}px; height: ${self.chartWidth}px;
                background-color: ${self.color}; cursor: ${self.cursor}; border: 1px solid #AAB7B8; `;
            
            self.html = chart;
            self.html.addEventListener("selectstart", () => { return false; });
        }
        
        reposition(style: any) {
            let self = this;
            
//            if ((_.has(style, "start") && style.start < self.limitStart)
//                || (_.has(style, "end") && style.end > self.limitEnd)) return;
            
            if (_.has(style, "start")) {
                self.start = style.start;    
            }
            
            if (_.has(style, "end")) {
                self.end = style.end;
            }
            
            if (_.has(style, "top")) {
                self.html.style.top = style.top + "px";
            }
            
            if (_.has(style, "left")) {
                self.html.style.left = style.left + "px";
            }
            
            if (_.has(style, "width")) {
                if (style.width <= 0) {
                    self.html.parentNode.removeChild(self.html);
                } else {
                    self.html.style.width = style.width + "px";
                }
            }
            
        }
    }
    
    class Warn {
        message: string;
        constructor(msg: string) {
            this.message = msg;
        }
    }
    
    enum CHART_FIXED {
        NONE = "None",
        START = "Start",
        END = "End",
        BOTH = "Both"
    } 
    
    enum HOLD_POS {
        START = "Start",
        END = "End",
        BODY = "Body",
        OUT = "Out"
    }
}