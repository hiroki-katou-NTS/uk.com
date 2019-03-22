__viewContext.ready(function() {
    
    class ScreenModel {
        constructor() {
            let ruler = new nts.uk.ui.chart.Ruler($("#gc")[0]);
            
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
                middleDs.push({ empId: i.toString(), cert: "★", over1: 100 + i + "", over2: 1 + i + "" });
            }
            
            let leftmostContent = {
                columns: leftmostColumns,
                dataSource: leftmostDs,
                primaryKey: "empId"
            };
            
            let middleColumns = [
                { headerText: "列０", key: "cert", width: "50px" },
                { headerText: "列１", key: "over1", width: "100px" },
                { headerText: "列２", key: "over2", width: "100px" }
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
            
            let width = "40px";
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
                bodyHeightMode: "dynamic",
                windowXOccupation: 40,
                windowYOccupation: 200   
            }).LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
              .MiddleHeader(middleHeader).MiddleContent(middleContent)
              .DetailHeader(detailHeader).DetailContent(detailContent);
            
            extable.create();
            let ruler = extable.getChartRuler();
            ruler.addType({
                name: "Child",
                followParent: true,
                color: "orange",
                lineWidth: 30,
                limitEnd: 30,
                canSlide: true
            });
            
            for (let i = 0; i < 300; i++) {
                let start = Math.round(((i % 60) + i / 60) / 2);
                let gc = ruler.addChart({
                    id: `rgc${i}`,
                    start: start,
                    end: start + 10,
                    lineNo: i,
                    limitEnd: 96,
                    lineWidth: 30,
                    canSlide: true,
                    fixed: i == 6 ? "Start" : "None"
                });
                
                $(gc).on("gcResize", (e) => {
                    let param = e.detail;
                });
                
                ruler.addChartWithType("Child", {
                    id: `rgc${i}_0`,
                    parent: `rgc${i}`,
                    lineNo: i,
                    start: start + 3,
                    end: start + 6,
                    followParent: i == 9
                });
            }
            
            ruler.setLock([0, 1, 2, 3], true);
        }
    }
    
    let screenModel = new ScreenModel();
    this.bind(screenModel);
});