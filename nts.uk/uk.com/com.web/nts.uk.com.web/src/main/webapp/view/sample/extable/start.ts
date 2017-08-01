__viewContext.ready(function () {
    class ScreenModel {
        constructor() {
            var self = this;
        }
    }
    
    this.bind(new ScreenModel());
    
    class ExItem {
        empId: string;
        empName: string;
        __25: string;
        __26: string;
        __27: string;
        __28: string;
        __29: string;
        __30: string;
        __31: string;
        _1: any;
        _2: string;
        _3: string;
        _4: string;
        _5: string;
        _6: string;
        _7: string;
        _8: string;
        _9: string;
        _10: string;
        _11: string;
        _12: string;
        _13: string;
        _14: string;
        _15: string;
        _16: string;
        _17: string;
        _18: string;
        _19: string;
        _20: string;
        _21: string;
        _22: string;
        _23: string;
        _24: string;
        _25: string;
        _26: string;
        _27: string;
        _28: string;
        _29: string;
        _30: string;
        _31: string;
        
        constructor(empId: string, manual?: boolean) {
            this.empId = empId;
            this.empName = empId;
            if (manual) {
                for (let i = -6; i <= 31; i++) {
                    if (i <= 0) {
                        let d = 31 + i;
                        this["__" + d] = d;
                    } else {
                        this["_" + i] = "4/" + i;
                    }
                }
                return;
            }
            for (let i = -6; i <= 31; i++) {
                if (i <= 0) {
                    let d = 31 + i;
                    this["__" + d] = "前";
                } else if (i === 1) this["_" + i] = ["出勤", "8:30"];
                else if (i === 2) this["_" + i] = ["出勤B", "16:00"];
                else if (i === 3) this["_" + i] = ["出勤C", "20:00"];
                else if (i === 4) this["_" + i] = ["出勤D", "19:00"];
                else this["_" + i] = "通" + i;
            }
        }
    }
    
    let days = [ "日", "月", "火", "水", "木", "金", "土" ];
    let detailHeaderDs = [];
    detailHeaderDs.push(new ExItem(undefined, true));
    detailHeaderDs.push({ empId: "", __25: "over", __26: "", __27: "", __28: "", __29: "", __30: "", __31: "",
        _1: "セール", _2: "", _3: "", _4: "", _5: "", _6: "", _7: "", _8: "", _9: "特別", _10: "",
         _11: "", _12: "", _13: "", _14: "", _15: "", _16: "Oouch", _17: "", _18: "", _19: "", _20: "", _21: "", _22: "", _23: "",
          _24: "", _25: "", _26: "設定", _27: "", _28: "", _29: "", _30: "", _31: "", });
    
    
    class CellColor {
        columnKey: any;
        rowId: any;
        innerIdx: any;
        clazz: any;
        constructor(columnKey: any, rowId: any, clazz: any, innerIdx?: any) {
            this.columnKey = columnKey;
            this.rowId = rowId;
            this.innerIdx = innerIdx;
            this.clazz = clazz;
        }
    }
    class TimeRange {
        columnKey: any;
        rowId: any;
        innerIdx: any;
        max: string;
        min: string;
        constructor(columnKey: any, rowId: any, max: string, min: string, innerIdx?: any) {
            this.columnKey = columnKey;
            this.rowId = rowId;
            this.innerIdx = innerIdx;
            this.max = max;
            this.min = min;
        }
    }
    let middleHeaderDeco = [ new CellColor("over1", undefined, "small-font-size"), new CellColor("over2", undefined, "small-font-size")];
    let middleContentDeco = [];
    let detailHeaderDeco = [ new CellColor("empId", 1, "ultra-small-font-size")];
    for (let i = -6; i < 32; i++) {
        if (i <= 0) {
            let d = 31 + i;
            detailHeaderDeco.push(new CellColor("__" + d, 1, "ultra-small-font-size")) 
        } else {
            detailHeaderDeco.push(new CellColor("_" + i, 1, "ultra-small-font-size"));
        }
    }
    let detailContentDeco = []; 
    let timeRanges = [];
    let detailContentDs = [];
    let leftmostDs = [];
    let middleDs = [], updateMiddleDs = [];
    let horzSumContentDs = [], leftHorzContentDs = [], vertSumContentDs = [];
    for (let i = 0; i < 300; i++) {
        detailContentDs.push(new ExItem(i.toString()));
        leftmostDs.push({　empId: i.toString(), empName: "社員名" + i });
        middleDs.push({ empId: i.toString(), cert: "★", over1: "207:00", over2: "23.0" });
        updateMiddleDs.push({ empId: i.toString(), time: "100:00", days: "38", can: "", get: "" });
        if (i % 2 === 0) middleContentDeco.push(new CellColor("over1", i.toString(), "cell-red"));
        else middleContentDeco.push(new CellColor("over2", i.toString(), "cell-green"));
        if (i % 7 === 0) detailContentDeco.push(new CellColor("_3", i.toString(), "cell-light-green", 0));
        if (i < 1000) timeRanges.push(new TimeRange("_2", i.toString(), "17:00", "7:00", 1));
        vertSumContentDs.push({ empId: i.toString(), noCan: 6, noGet: 6 });
    }
    for (let i = 0; i < 10; i++) {
        horzSumContentDs.push({ itemId: i.toString(), empId: "", __25: "1.0", __26: "1.4", __27: "0.3", __28: "0.9", __29: "1.0", __30: "1.0", __31: "3.3", 
        _1: "1.0", _2: "1.0", _3: "0.5", _4: "1.0", _5: "1.0", _6: "1.0", _7: "0.5", _8: "0.5", _9: "1.0", _10: "0.5",
         _11: "0.5", _12: "1.0", _13: "0.5", _14: "1.0", _15: "1.0", _16: "0.5", _17: "1.0", _18: "1.0", _19: "1.0", _20: "1.0", _21: "1.0", _22: "1.0", _23: "1.0",
          _24: "0.5", _25: "0.5", _26: "1.0", _27: "1.0", _28: "1.0", _29: "0.5", _30: "1.0", _31: "1.0" });
        leftHorzContentDs.push({ itemId: i.toString(), itemName: "8:00 ~ 9:00", sum: "23.5" });
    }
    
    let detailColumns = [{
           key: "empId", width: "50px", headerText: "ABC", visible: false
        }, {
//            key: "empName", width: "120px"
//        }, {
            key: "_1", width: "100px", handlerType: "Input", dataType: "text/duration", min: "9:00", max: "19:00"
        }, {
            key: "_2", width: "100px", handlerType: "Input", dataType: "text/duration", rightClick: function(rData, rowIdx, columnKey) { alert(rowIdx); }
        }, {
            key: "_3", width: "100px", handlerType: "Input", dataType: "text/time"
        }, {
            key: "_4", width: "100px", handlerType: "input", dataType: "text/time"
        }, {
            key: "_5", width: "100px", handlerType: "input", dataType: "text"
        }, {
            key: "_6", width: "100px", handlerType: "input", dataType: "text", rightClick: function(rData, rowIdx, columnKey) { alert(rowIdx); }
        }, {
            key: "_7", width: "100px", dataType: "text"
        }, {
            key: "_8", width: "100px"
        }, {
            key: "_9", width: "100px"
        }, {
            key: "_10", width: "100px"
        }, {
            key: "_11", width: "100px"
        }, {
            key: "_12", width: "100px"
        }, {
            key: "_13", width: "100px"
        }, {
            key: "_14", width: "100px"
        }, {
            key: "_15", width: "100px"
        }, {
            key: "_16", width: "100px"
        }, {
            key: "_17", width: "100px"
        }, {
            key: "_18", width: "100px"
        }, {
            key: "_19", width: "100px"
        }, {
            key: "_20", width: "100px"
        }, {
            key: "_21", width: "100px"
        }, {
            key: "_22", width: "100px"
        }, {
            key: "_23", width: "100px"
        }, {
            key: "_24", width: "100px"
        }, {
            key: "_25", width: "100px"
        }, {
            key: "_26", width: "100px"
        }, {
            key: "_27", width: "100px"
        }, {
            key: "_28", width: "100px"
        }, {
            key: "_29", width: "100px"
        }, {
            key: "_30", width: "100px"
        }, {
            key: "_31", width: "100px"
        }];
    
    let leftmostColumns = [{ key: "empName", headerText: "社員名", width: "160px", icon: "ui-icon ui-icon-contact", 
            iconWidth: "35px", control: "link", handler: function(rData, rowIdx, key) { alert(rowIdx); } }];
    let leftmostHeader = {
        columns: leftmostColumns,
        rowHeight: "60px",
        width: "160px"
    };
    let leftmostContent = {
        columns: leftmostColumns,
        dataSource: leftmostDs,
        primaryKey: "empId"
    };
    
    let tts = function(rData, rowIdx, colKey) {
        if (rowIdx % 2 === 0) {
            return $("<div/>").css({ width: "60px", height: "50px").html(rData[colKey] + rowIdx);
        }
    };
    let middleColumns = [
        { headerText: "有資格者", key: "cert", width: "50px", handlerType: "tooltip", supplier: tts },
        { 
            headerText: "回数集計１",
            group: [
                { headerText: "上１", key: "over1", width: "100px" },
                { headerText: "上２", key: "over2", width: "100px" }
            ]
        }
    ];
    
    let middleHeader = {
        columns: middleColumns,
        width: "200px",
        features: [{
            name: "HeaderRowHeight",
            rows: { 0: "35px", 1: "25px" }   
        }, {
            name: "HeaderCellStyle",
            decorator: middleHeaderDeco
//            decorate: function($cell, cellData, rowData, rowIdx, columnKey) { 
//                
//            }
        }, {
            name: "ColumnResizes"
        }]
    };
    let middleContent = {
        columns: middleColumns,
        dataSource: middleDs,
        primaryKey: "empId",
        features: [{
            name: "BodyCellStyle",
            decorator: middleContentDeco   
        }]
    };
    let detailHeader = {
        columns: detailColumns,
        dataSource: detailHeaderDs,
        rowHeight: "30px",
        width: "700px",
        features: [{
            name: "HeaderRowHeight",
            rows: { 0: "35px", 1: "25px" }   
        }, {
            name: "HeaderCellStyle",
            decorator: detailHeaderDeco
        }, {
            name: "ColumnResizes"
        }, {
            name: "HeaderPopups",
            menu: {
                rows: [0],
                items: [
                    { id: "日付別", text: "日付別", selectHandler: function(id) { alert(id); }, icon: "ui-icon ui-icon-calendar" },
                    { id: "partition" },
                    { id: "シフト別", text: "シフト別", selectHandler: function(id) { alert(id); }, icon: "ui-icon ui-icon-star" }
                ]
            },
            popup: {
                rows: [1],
                provider: function() { return $("#popup"); }
            }
        }]
    };
    let detailContent = {
        columns: detailColumns,
        dataSource: detailContentDs,
        primaryKey: "empId",
        features: [{
            name: "BodyCellStyle",
            decorator: detailContentDeco
        }, {
            name: "TimeRange",
            ranges: timeRanges
        }]
    };
    
    let leftHorzColumns = [
        { headerText: "項目名", key: "itemName", width: "200px" },
        { headerText: "合計", key: "sum", width: "100px" }
    ];
    let leftHorzSumHeader = {
        columns: leftHorzColumns,
//        dataSource: leftHorzHeaderDs,
        rowHeight: "60px"
    };
    let leftHorzSumContent = {
        columns: leftHorzColumns,
        dataSource: leftHorzContentDs,
        primaryKey: "itemId"
    }; 
    
    let horizontalSumHeader = {
        columns: detailColumns,
        dataSource: detailHeaderDs,
        rowHeight: "30px",
//        features: [{
//            name: "HeaderRowHeight",
//            rows: { 0: "35px", 1: "25px" }   
//        }, {
//            name: "HeaderCellStyle",
//            decorator: detailHeaderDeco
//        }, {
//            name: "ColumnResize"
//        }]
    };
    let horizontalSumContent = {
        columns: detailColumns,
        dataSource: horzSumContentDs,
        primaryKey: "itemId"
    };
    
    let vertSumColumns = [
        { 
            headerText: "公休日数",
            group: [
                { headerText: "可能数", key: "noCan", width: "100px" },
                { headerText: "取得数", key: "noGet", width: "100px" }
            ]
        }
    ];
    let vertSumHeader = {
        columns: vertSumColumns,
        width: "200px",
        features: [{
            name: "HeaderRowHeight",
            rows: { 0: "20px", 1: "40px" }   
        }
    };
    let vertSumContent = {
        columns: vertSumColumns,
        dataSource: vertSumContentDs,
        primaryKey: "empId"
    };
    new nts.uk.ui.exTable.ExTable($("#extable"), { 
            headerHeight: "60px", bodyRowHeight: "50px", bodyHeight: "400px", 
            horizontalSumHeaderHeight: "60px", horizontalSumBodyHeight: "200px",
            horizontalSumBodyRowHeight: "20px"
            areaResize: true, 
            bodyHeightMode: "dynamic",
            windowOccupation: 800,
            updateMode: "edit",
            pasteOverWrite: true,
            stickOverWrite: true,
            determination: {
                rows: [0],
                columns: ["empName"]
            },
            heightSetter: {
                showBodyHeightButton: true,
                click: function() {
                    alert("Show dialog");
                } 
            }})
        .LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
        .MiddleHeader(middleHeader).MiddleContent(middleContent)
        .DetailHeader(detailHeader).DetailContent(detailContent)
        .VerticalSumHeader(vertSumHeader).VerticalSumContent(vertSumContent)
        .LeftHorzSumHeader(leftHorzSumHeader).LeftHorzSumContent(leftHorzSumContent)
        .HorizontalSumHeader(horizontalSumHeader).HorizontalSumContent(horizontalSumContent).create();
    
        $("#extable").on("extablecellupdated", function() {
        });
        $("#extable").on("extablerowupdated", function() {
        });
    
        let updateMiddleH = [
            { headerText: "時間", key: "time", width: "100px" },
            { headerText: "日数", key: "days", width: "50px" },
            { headerText: "可能", key: "can", width: "50px" },
            { headerText: "取得", key: "get", width: "50px" }
        ];
        let updateMiddleHeader = {
            columns: updateMiddleH,
            rowHeight: "60px",
            features: []
        };
        let updateMiddleContent = {
            columns: updateMiddleH,
            dataSource: updateMiddleDs
        };
        $("#update-middle").click(function() {
            $("#extable").exTable("updateTable", "middle", updateMiddleHeader, updateMiddleContent);
        });
    
        let newDetailColumns = [{
           key: "empId", width: "50px", headerText: "ABC", visible: false
        }, {
            key: "__25", width: "100px"
        }, {
            key: "__26", width: "100px"
        }, {
            key: "__27", width: "100px"
        }, {
            key: "__28", width: "100px"
        }, {
            key: "__29", width: "100px"
        }, {
            key: "__30", width: "100px"
        }, {
            key: "__31", width: "100px"
        }, {
            key: "_1", width: "100px"
        }, {
            key: "_2", width: "100px", handlerType: "Input"
        }, {
            key: "_3", width: "100px"
        }, {
            key: "_4", width: "100px"
        }, {
            key: "_5", width: "100px"
        }, {
            key: "_6", width: "100px"
        }, {
            key: "_7", width: "100px"
        }, {
            key: "_8", width: "100px"
        }, {
            key: "_9", width: "100px"
        }, {
            key: "_10", width: "100px"
        }, {
            key: "_11", width: "100px"
        }, {
            key: "_12", width: "100px"
        }, {
            key: "_13", width: "100px"
        }, {
            key: "_14", width: "100px"
        }, {
            key: "_15", width: "100px"
        }, {
            key: "_16", width: "100px"
        }, {
            key: "_17", width: "100px"
        }, {
            key: "_18", width: "100px"
        }, {
            key: "_19", width: "100px"
        }, {
            key: "_20", width: "100px"
        }, {
            key: "_21", width: "100px"
        }, {
            key: "_22", width: "100px"
        }, {
            key: "_23", width: "100px"
        }, {
            key: "_24", width: "100px"
        }, {
            key: "_25", width: "100px"
        }, {
            key: "_26", width: "100px"
        }, {
            key: "_27", width: "100px"
        }, {
            key: "_28", width: "100px"
        }, {
            key: "_29", width: "100px"
        }, {
            key: "_30", width: "100px"
        }, {
            key: "_31", width: "100px"
        }];
        let updateDetailHeader = {
            columns: newDetailColumns
        };
        let updateDetailContent = {
            columns: newDetailColumns
        };
        $("#show-last-week").click(function() {
            $("#extable").exTable("updateTable", "detail", updateDetailHeader, updateDetailContent);
            $("#extable").exTable("updateTable", "horizontalSummaries", { columns: newDetailColumns }, { columns: newDetailColumns });
        });
        $("#hide-last-week").click(function() {
            $("#extable").exTable("updateTable", "detail", { columns: detailColumns }, { columns: detailColumns });
            $("#extable").exTable("updateTable", "horizontalSummaries", { columns: detailColumns }, { columns: detailColumns });
        });
        $("#set-sticker-multi").click(function() {
            $("#extable").exTable("stickData", [ "MM1", "MM2", "MM3", "MM4", "MM5", "MM6", "MM7", "MM8", "MM9", "MM10" ]);
        });
        $("#set-sticker-multi2").click(function() {
            $("#extable").exTable("stickData", [ ["出勤MM", "1:00"], ["出勤DD", "2:00"], ["出勤CC", "3:00"] ]);
        });
        $("#set-sticker-single").click(function() {
            $("#extable").exTable("stickData", [ "出勤MM", "1:00" ]);
        });
        $("#stick-undo").click(function() {
            $("#extable").exTable("stickUndo");
        });
        $("#set-sticker-valid").click(function() {
            $("#extable").exTable("stickValidate", function(rowIdx, key, data) { 
                if (rowIdx > 6) {
                    return function() {
                        alert("error");
                    };
                }
                return true;
            });
        });
        $("#popup-set").click(function() {
            $("#extable").exTable("popupValue", $("#popup-val").val());
        });
});