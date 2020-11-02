__viewContext.noHeader = true;
__viewContext.ready(function() {
    
    class ScreenModel {
        constructor() { 
            this.itemList = ko.observableArray([ 5, 10, 15, 30 ].map(c => ({ code: c, name: c })));
            this.selectedCode = ko.observable(5);
            this.selectedCode.subscribe(c => {
                ruler.setSnatchInterval(c / 5);
            });
//            let ruler = new nts.uk.ui.chart.Ruler($("#gc")[0]);
            
//            ruler.addType({
//                name: "Type1",
//                lineNo: 2,
//                chartWidth: 30,
//                lineWidth: 40,
//                canSlide: true
//            });
//            
//            ruler.addChart({
//                id: "rgc1",
//                start: 5,
//                end: 60,
//                limitEnd: 100,
//                lineNo: 0,
//                fixed: "Start",
//                canSlide: true,
//                color: "#F00"
//            });
//            
//            ruler.addChart({
//                id: "rgc1_1",
//                start: 10,
//                end: 20,
//                lineNo: 0,
//                followParent: true,
//                color: "#0F0",
//                canSlide: true,
//                parent: "rgc1"
//            });
//            
//            ruler.addChart({
//                id: "rgc2",
//                start: 8,
//                end: 40,
//                lineNo: 1,
//                locked: true,
//                color: "#00F"
//            });
//            
//            ruler.addChartWithType("Type1", {
//                id: "rgc3",
//                start: 3,
//                end: 30,
//                color: "#ff0",
//            });
//            
//            ruler.addChartWithType("Type1", {
//                id: "rgc3_1",
//                parent: "rgc3",
//                start: 5,
//                end: 19,
//                followParent: true,
//                color: "#0ff"
//            });
            let leftmostColumns = [{ key: "empName", headerText: "社員名", width: "160px", css: { whiteSpace: "pre" } }];
            let leftmostHeader = {
                columns: leftmostColumns,
                rowHeight: "33px",
                width: "160px"
            };
            
            let leftmostDs = [], middleDs = [];
            for (let i = 0; i < 300; i++) {
                let eName = nts.uk.text.padRight("名前" + i, " ", 10) + "AAAAAAAAAAAAAAAAAA";
                leftmostDs.push({　empId: i.toString(), empName: eName });
                middleDs.push({ empId: i.toString(), code: i + "", startTime: Math.round(i / 6) + ":30", endTime: Math.round(i / 6 + 8) + ":30" });
            }
            
            let leftmostContent = {
                columns: leftmostColumns,
                dataSource: leftmostDs,
                primaryKey: "empId"
            };
            
            let middleColumns = [
                { headerText: "コード", key: "code", width: "50px", handlerType: "input", dataType: "text" },
                { headerText: "開始", key: "startTime", width: "100px", handlerType: "input", dataType: "time" },
                { headerText: "終了", key: "endTime", width: "100px", handlerType: "input", dataType: "time" }
            ];
            
            let middleHeader = {
                columns: middleColumns,
                width: "200px",
                rowHeight: "33px"
            };
            let middleContent = {
                columns: middleColumns,
                dataSource: middleDs,
                primaryKey: "empId"
            };
            
            let width = "48px";
            let detailColumns = [{
               key: "empId", width: "0px", headerText: "ABC", visible: false
            }, {
                key: "_0", width: width
            },{
                key: "_1", width: width
            }, {
                key: "_2", width: width
            }, {
                key: "_3", width: width
            }, {
                key: "_4", width: width
            }, {
                key: "_5", width: width
            }, {
                key: "_6", width: width
            }, {
                key: "_7", width: width
            }, {
                key: "_8", width: width
            }, {
                key: "_9", width: width
            }, {
                key: "_10", width: width
            }, {
                key: "_11", width: width
            }, {
                key: "_12", width: width
            }, {
                key: "_13", width: width
            }, {
                key: "_14", width: width
            }, {
                key: "_15", width: width
            }, {
                key: "_16", width: width
            }, {
                key: "_17", width: width
            }, {
                key: "_18", width: width
            }, {
                key: "_19", width: width
            }, {
                key: "_20", width: width
            }, {
                key: "_21", width: width
            }, {
                key: "_22", width: width
            }, {
                key: "_23", width: width
            }];
            
            let detailHeaderDs = [{ empId: "", _0: "0", _1: "1", _2: "2", _3: "3", _4: "4", _5: "5", _6: "6", _7: "7", _8: "8", _9: "9", _10: "10", _11: "11", _12: "12", _13: "13", _14: "14", _15: "15", _16: "16", _17: "17", _18: "18", _19: "19", _20: "20", _21: "21", _22: "22", _23: "23" }];
            let detailHeader = {
                columns: detailColumns,
                dataSource: detailHeaderDs,
                rowHeight: "33px",
                width: "700px"
            };
            
            let detailContentDs = [];
            for (let i = 0; i < 300; i++) {
                detailContentDs.push({ empId: i.toString(), _0: "", _1: "", _2: "", _3: "", _4: "", _5: "", _6: "", _7: "", _8: "", _9: "", _10: "", _11: "", _12: "", _13: "", _14: "", _15: "", _16: "", _17: "", _18: "", _19: "", _20: "", _21: "", _22: "", _23: "" });
            }
            
            let detailContent = {
                columns: detailColumns,
                dataSource: detailContentDs,
                primaryKey: "empId"
            };
            
            let extable = new nts.uk.ui.exTable.ExTable($("#extable"), {
                headerHeight: "33px",
                bodyRowHeight: "30px",
                bodyHeight: "400px",
                horizontalSumHeaderHeight: "75px", horizontalSumBodyHeight: "140px",
                horizontalSumBodyRowHeight: "20px",
                areaResize: true,
                manipulatorId: "6",
                manipulatorKey: "empId",
                bodyHeightMode: "dynamic",
                windowXOccupation: 40,
                windowYOccupation: 200   
            }).LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
              .MiddleHeader(middleHeader).MiddleContent(middleContent)
              .DetailHeader(detailHeader).DetailContent(detailContent);
            
            extable.create();
            this.ruler = extable.getChartRuler();
            
            this.ruler.addType({
                name: "Fixed",
                color: "#ccccff",
                lineWidth: 30,
                canSlide: false,
                unitToPx: 4
            });
            
            this.ruler.addType({
                name: "Changeable",
                color: "#ffc000",
                lineWidth: 30,
                canSlide: true,
                unitToPx: 4
            });
            
            this.ruler.addType({
                name: "Flex",
                color: "#ccccff",
                lineWidth: 30,
                canSlide: true,
                unitToPx: 4
            });
            
            this.ruler.addType({
                name: "BreakTime",
                followParent: true,
                color: "#ff9999",
                lineWidth: 30,
                canSlide: true,
                unitToPx: 4,
                pin: true,
                rollup: true,
                roundEdge: true,
                fixed: "Both"
            });
            
            this.ruler.addType({
                name: "OT",
                followParent: true,
                color: "#ffff00",
                lineWidth: 30,
                canSlide: false,
                unitToPx: 4,
                pin: true,
                rollup: true,
                fixed: "Both"
            });
            
            this.ruler.addType({
                name: "CoreTime",
                color: "#00ffcc",
                lineWidth: 30,
                unitToPx: 4,
                fixed: "Both"
            });
            
            for (let i = 0; i < 300; i++) {
                let start = Math.round(((i % 60) + i / 60) / 2);
                
                if (i % 5 === 1) {
                    // 固定勤務時間
                    let lgc = this.ruler.addChartWithType("Fixed", {
                        id: `lgc${i}`,
                        start: 12,
                        end: 60,
                        lineNo: i,
                        limitStartMax: 60,
                        limitEndMax: 72
                    });
                    
                    this.ruler.addChartWithType("BreakTime", {
                        id: `lgc${i}_0`,
                        parent: `lgc${i}`,
                        lineNo: i,
                        start: 24,
                        end: 36,
                        limitStartMin: 12,
                        limitStartMax: 60,
                        limitEndMax: 60
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `lgc${i}_1`,
                        parent: `lgc${i}`,
                        lineNo: i,
                        start: 0,
                        end: 12
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `lgc${i}_2`,
                        parent: `lgc${i}`,
                        lineNo: i,
                        start: 60,
                        end: 72
                    });
                    
                    let gc = this.ruler.addChartWithType("Fixed", {
                        id: `rgc${i}`,
                        start: 102,
                        end: 210,
                        lineNo: i,
                        limitStartMin: 84,
                        limitStartMax: 264,
                        limitEndMax: 264,
                        title: "固定勤務"
                    });
                    
                    $(gc).on("gcResize", (e) => {
                        let param = e.detail;
                    });
                    
                    this.ruler.addChartWithType("BreakTime", {
                        id: `rgc${i}_0`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 144,
                        end: 156,
                        limitStartMin: 102,
                        limitStartMax: 210,
                        limitEndMax: 210
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `rgc${i}_1`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 84,
                        end: 102
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `rgc${i}_2`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 210,
                        end: 264
                    });
                }
                
                if (i % 5 === 2) {
                    //　流動勤務時間
                    this.ruler.addChartWithType("Changeable", {
                        id: `lgc${i}`,
                        start: 12,
                        end: 60,
                        lineNo: i,
                        limitStartMax: 60,
                        limitEndMax: 72
                    });
                    
                    this.ruler.addChartWithType("BreakTime", {
                        id: `lgc${i}_0`,
                        parent: `lgc${i}`,
                        lineNo: i,
                        start: 24,
                        end: 36,
                        limitStartMin: 12,
                        limitStartMax: 60,
                        limitEndMax: 60
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `lgc${i}_1`,
                        parent: `lgc${i}`,
                        lineNo: i,
                        start: 0,
                        end: 12
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `lgc${i}_2`,
                        parent: `lgc${i}`,
                        lineNo: i,
                        start: 60,
                        end: 72
                    });
                    
                    this.ruler.addChartWithType("Changeable", {
                        id: `rgc${i}`,
                        start: 102,
                        end: 210,
                        lineNo: i,
                        limitStartMin: 84,
                        limitStartMax: 264,
                        limitEndMax: 264,
                        title: "流動勤務"
                    });
                    
                    this.ruler.addChartWithType("BreakTime", {
                        id: `rgc${i}_0`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 144,
                        end: 156,
                        limitStartMin: 102,
                        limitStartMax: 210,
                        limitEndMax: 210
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `rgc${i}_1`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 84,
                        end: 102
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `rgc${i}_2`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 210,
                        end: 264
                    });
                }
                
                // フレックス
                if (i % 5 === 3) {
                    this.ruler.addChartWithType("Flex", {
                        id: `rgc${i}`,
                        start: 102,
                        end: 210,
                        lineNo: i,
                        limitStartMin: 84,
                        limitStartMax: 144,
                        limitEndMin: 168,
                        limitEndMax: 264,
                        title: "フレックス勤務"
                    });
                    
                    this.ruler.addChartWithType("CoreTime", {
                        id: `rgc${i}_3`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 144,
                        end: 168,
                        pin: true
                    });
                    
                    this.ruler.addChartWithType("BreakTime", {
                        id: `rgc${i}_0`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 144,
                        end: 156,
                        limitStartMin: 102,
                        limitStartMax: 210,
                        limitEndMax: 210
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `rgc${i}_1`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 84,
                        end: 102
                    });
                    
                    this.ruler.addChartWithType("OT", {
                        id: `rgc${i}_2`,
                        parent: `rgc${i}`,
                        lineNo: i,
                        start: 210,
                        end: 264
                    });
                    
                }
            }
            
            this.ruler.setLock([0, 1, 2, 3], true);
        }
        
        replace() {
            this.ruler.replaceAt(7, [
            { 
                type: "Flex", 
                options: {
                    id: `lgc7`,
                    start: 12,
                    end: 60,
                    lineNo: 7,
                    limitStartMax: 60,
                    limitEndMax: 72
                }
            }, {
                type: "BreakTime",
                options: {
                    id: `lgc7_0`,
                    parent: `lgc7`,
                    lineNo: 7,
                    start: 24,
                    end: 36,
                    limitStartMin: 12,
                    limitStartMax: 60,
                    limitEndMax: 60
                }
            }, {
                type: "OT",
                options: {
                    id: `lgc7_1`,
                    parent: `lgc7`,
                    lineNo: 7,
                    start: 0,
                    end: 12
                }
            }, {
                type: "OT",
                options: {
                    id: `lgc7_2`,
                    parent: `lgc7`,
                    lineNo: 7,
                    start: 60,
                    end: 72
                }
            }]);
        }
    }
    
    let screenModel = new ScreenModel();
    this.bind(screenModel);
});