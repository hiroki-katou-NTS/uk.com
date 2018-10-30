__viewContext.noHeader = true;
__viewContext.ready(function () {
    class ScreenModel {
        constructor() {
            var self = this;
        }
    }
    
    this.bind(new ScreenModel());
    
    class ExCell {
        workTypeCode: string;
        workTypeName: string;
        workTimeCode: string;
        workTimeName: string;
        symbol: string;
        startTime: any;
        endTime: any;
        constructor(workTypeCode: string, workTypeName: string, workTimeCode: string, workTimeName: string, startTime?: string, endTime?: string, symbol?: any) {
            this.workTypeCode = workTypeCode;
            this.workTypeName = workTypeName;
            this.workTimeCode = workTimeCode;
            this.workTimeName = workTimeName;
            this.symbol = symbol !== null ? (parseInt(workTypeCode) % 3 === 0 ? "通" : "◯") : null;
            this.startTime = startTime !== undefined ? startTime : "8:30";
            this.endTime = endTime !== undefined ? endTime : "17:30";
        }
    }
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
                let days = [ "日", "月", "火", "水", "木", "金", "土" ];
                for (let i = -6; i <= 31; i++) {
                    if (i <= 0) {
                        let d = 31 + i;
                        this["__" + d] = d + "<br/>" + days[7 + i === 7 ? 0 : 7 + i];
                    } else {
                        this["_" + i] = "4/" + i + "<br/>" + days[i % 7]; 
                    }
                }
                return;
            }
            for (let i = -6; i <= 31; i++) {
                if (i <= 0) {
                    let d = 31 + i;
                    this["__" + d] = new ExCell("001", "出勤A" + this.empId, "1", "通常８ｈ");
                } else if (i === 1) this["_" + i] = new ExCell("001", "出勤A" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 2) this["_" + i] = new ExCell("002", "出勤B" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 3) this["_" + i] = new ExCell("003", "出勤C" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 4) this["_" + i] = new ExCell("004", "出勤D" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 6) this["_" + i] = new ExCell(null, null, null, null, null, null);
                else this["_" + i] = new ExCell("00" + i, "出勤" + i + this.empId, "1", "通常８ｈ" + this.empId);
            }
            
            if (empId === "1") {
                this["_" + 2] = new ExCell(null, null, null, null, null, null, null);
            }
        }
    }
    
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
    let detailHeaderDeco = [ new CellColor("empId", 1, "small-font-size")];
    for (let i = -6; i < 32; i++) {
        if (i <= 0) {
            let d = 31 + i;
            detailHeaderDeco.push(new CellColor("__" + d, 1, "medium-font-size")) 
        } else {
            detailHeaderDeco.push(new CellColor("_" + i, 1, "medium-font-size"));
        }
    }
    let detailContentDeco = []; 
    let timeRanges = [];
    let detailContentDs = [];
    let leftmostDs = [];
    let middleDs = [], updateMiddleDs = [];
    let horzSumContentDs = [], leftHorzContentDs = [], vertSumContentDs = [], newVertSumContentDs = [];
    for (let i = 0; i < 300; i++) {
        detailContentDs.push(new ExItem(i.toString()));
        let eName = nts.uk.text.padRight("社員名" + i, " ", 10) + "AAAAAAAAAAAAAAAAAA";
        leftmostDs.push({　empId: i.toString(), empName: eName });//"社員名" + i + "    AAA" });
        middleDs.push({ empId: i.toString(), cert: "★", over1: 100 + i + "", over2: 1 + i + "" });
        updateMiddleDs.push({ empId: i.toString(), time: "100:00", days: "38", can: "", get: "" });
        if (i % 2 === 0) middleContentDeco.push(new CellColor("over1", i.toString(), "cell-red"));
        else middleContentDeco.push(new CellColor("over2", i.toString(), "cell-green cell-red"));
        if (i % 7 === 0) {
            detailContentDeco.push(new CellColor("_2", i.toString(), "xseal", 0));
            detailContentDeco.push(new CellColor("_2", i.toString(), "xcustom", 0));
            detailContentDeco.push(new CellColor("_2", i.toString(), "xseal", 1));
            detailContentDeco.push(new CellColor("_2", i.toString(), "xcustom", 1));
            detailContentDeco.push(new CellColor("_3", i.toString(), "xhidden", 0));
            detailContentDeco.push(new CellColor("_3", i.toString(), "xhidden", 1));
        }
        // Add both child cells to mark them respectively
        detailContentDeco.push(new CellColor("_2", "2", "blue-text", 0));
        detailContentDeco.push(new CellColor("_2", "2", "blue-text", 1));
        if (i < 1000) timeRanges.push(new TimeRange("_2", i.toString(), "17:00", "7:00", 1));
        vertSumContentDs.push({ empId: i.toString(), noCan: 6, noGet: 6 });
        newVertSumContentDs.push({ empId: i.toString(), time: "0:00", plan: "30:00"});
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
            key: "_1", width: "100px", handlerType: "Input", dataType: "duration/duration", min: "4:00", max: "19:00", primitiveValue: "HolidayAppPrimitiveTime"
        }, {
            key: "_2", width: "100px", handlerType: "Input", dataType: "duration/duration", rightClick: function(rData, rowIdx, columnKey) { alert(rowIdx); }
        }, {
            key: "_3", width: "100px", handlerType: "Input", dataType: "duration/duration", required: true, min: "-12:00", max: "71:59"
        }, {
            key: "_4", width: "100px", handlerType: "input", dataType: "duration/duration", primitiveValue: "HolidayAppPrimitiveTime"
        }, {
            key: "_5", width: "100px", handlerType: "input", dataType: "duration/duration", primitiveValue: "TimeWithDayAttr"
        }, {
            key: "_6", width: "100px", handlerType: "input", dataType: "time/time", rightClick: function(rData, rowIdx, columnKey) { alert(rowIdx); }
        }, {
            key: "_7", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_8", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_9", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_10", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_11", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_12", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_13", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_14", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_15", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_16", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_17", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_18", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_19", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_20", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_21", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_22", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_23", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_24", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_25", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_26", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_27", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_28", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_29", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_30", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_31", width: "100px", handlerType: "input", dataType: "time/time"
        }];
    
    let leftmostColumns = [{ key: "empName", headerText: "社員名", width: "160px", icon: { for: "body", class: "ui-icon ui-icon-contact per-icon", width: "35px" }, 
        css: { whiteSpace: "pre" }, control: "link", handler: function(rData, rowIdx, key) { alert(rowIdx); }, 
        headerControl: "link", headerHandler: function() { alert("Link!"); } }];
    let leftmostHeader = {
        columns: leftmostColumns,
        rowHeight: "75px",
        width: "160px"
    };
    let leftmostContent = {
        columns: leftmostColumns,
        dataSource: leftmostDs,
        primaryKey: "empId"
    };
    
    let tts = function(rData, rowIdx, colKey) {
        if (rowIdx % 2 === 0) {
            return $("<div/>").css({ width: "60px", height: "50px" }).html(rData[colKey] + rowIdx);
        }
    };
    let middleColumns = [
        { headerText: "有資<br/>格者", key: "cert", width: "50px", handlerType: "tooltip", supplier: tts, 
            headerControl: "link", headerHandler: function() { alert("有資格者"); } },
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
            rows: { 0: "50px", 1: "25px" }   
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
            rows: { 0: "50px", 1: "25px" }   
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
//        highlight: false,
        features: [{
            name: "BodyCellStyle",
            decorator: detailContentDeco
        }, {
            name: "TimeRange",
            ranges: timeRanges
        }],
        view: function(mode) {
            switch (mode) {
                case "shortName":
                    return [ "workTypeName", "workTimeName" ];
                case "symbol": 
                    return [ "symbol" ];
                case "time":
                    return [ "startTime", "endTime" ]; 
            }
        },
        fields: [ "workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "symbol", "startTime", "endTime" ],
        upperInput: "startTime",
        lowerInput: "endTime",
        banEmptyInput: [ "time" ]
    };
    
    let leftHorzColumns = [
        { headerText: "項目名", key: "itemName", width: "200px", 
            icon: { for: "header", class: "ui-icon ui-icon-calculator per-icon", width: "35px", popup: function() { return $("#popup-items2"); }}},
        { headerText: "合計", key: "sum", width: "100px" }
    ];
    let leftHorzSumHeader = {
        columns: leftHorzColumns,
//        dataSource: leftHorzHeaderDs,
        rowHeight: "75px"
    };
    let leftHorzSumContent = {
        columns: leftHorzColumns,
        dataSource: leftHorzContentDs,
        primaryKey: "itemId"
    }; 
    
    let horizontalSumHeader = {
        columns: detailColumns,
        dataSource: detailHeaderDs,
        features: [{
            name: "HeaderRowHeight",
            rows: { 0: "45px", 1: "30px" }   
        }]
//        , {
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
            headerText: "公休日数", icon: { for: "header", class: "ui-icon ui-icon-calculator", width: "35px", 
                popup: function() { return $("#popup-items"); }},
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
            rows: { 0: "30px", 1: "45px" }   
        }]
    };
    let vertSumContent = {
        columns: vertSumColumns,
        dataSource: vertSumContentDs,
        primaryKey: "empId"
    };
    new nts.uk.ui.exTable.ExTable($("#extable"), { 
            headerHeight: "75px", bodyRowHeight: "50px", bodyHeight: "400px", 
            horizontalSumHeaderHeight: "75px", horizontalSumBodyHeight: "140px",
            horizontalSumBodyRowHeight: "20px",
            areaResize: true, 
            bodyHeightMode: "dynamic",
            windowXOccupation: 170,
            windowYOccupation: 300,
            manipulatorId: "6",
            manipulatorKey: "empId",
            updateMode: "stick",
            pasteOverWrite: true,
            stickOverWrite: true,
            viewMode: "shortName",
            showTooltipIfOverflow: true,
            secondaryTable: $("#subtable"),
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
        .HorizontalSumHeader(horizontalSumHeader).HorizontalSumContent(horizontalSumContent)
        .create();
    
    let leftHorzColumns2 = [
        { headerText: "項目名", key: "itemName", width: "200px" },
        { headerText: "合計", key: "sum", width: "100px" }
    ];
//    let leftHorzColumns2 = [
//        { headerText: "1", group: [
//            { headerText: "2", group: [ 
//                { headerText: "4", key: "itemName", width: "100px" },
//                { headerText: "5", key: "5", width: "100" }]},
//            { headerText: "3", group: [
//                { headerText: "6", key: "6", width: "50px" },
//                { headerText: "7", key: "7", width: "50px" }
//            ]}
//        ]}
//    ];
    
    let leftHorzSumHeader2 = {
        columns: leftHorzColumns2,
        rowHeight: "75px",
        width: "362px",
//        features: [{
//            name: "HeaderRowHeight",
//            rows: { 0: "30px", 1: "30px", 2: "15px" }   
//        }]
    };
    let leftHorzSumContent2 = {
        columns: leftHorzColumns2,
        dataSource: leftHorzContentDs,
        primaryKey: "itemId"
    }; 
    
    let detailColumns2 = _.forEach(_.cloneDeep(detailColumns), function(c) {
        delete c["handlerType"];
    });
    let horizontalSumHeader2 = {
        columns: detailColumns2,
        dataSource: detailHeaderDs,
        width: "700px",
        features: [{
            name: "HeaderRowHeight",
            rows: { 0: "45px", 1: "30px" }   
        }]
    };
    let horizontalSumContent2 = {
        columns: detailColumns2,
        dataSource: horzSumContentDs,
        highlight: false,
        primaryKey: "itemId"
    };
    new nts.uk.ui.exTable.ExTable($("#subtable"), { 
        headerHeight: "75px", bodyRowHeight: "20px", bodyHeight: "140px",
        horizontalSumBodyRowHeight: "20px",
        areaResize: false, 
        bodyHeightMode: "fixed",
        updateMode: "edit",
        windowXOccupation: 120,
        windowYOccupation: 600,
        primaryTable: $("#extable") 
        })
    .LeftmostHeader(leftHorzSumHeader2).LeftmostContent(leftHorzSumContent2)
    .DetailHeader(horizontalSumHeader2).DetailContent(horizontalSumContent2).create();
    
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
            rowHeight: "75px",
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
            key: "__25", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "__26", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "__27", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "__28", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "__29", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "__30", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "__31", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_1", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_2", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_3", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_4", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_5", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_6", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_7", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_8", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_9", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_10", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_11", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_12", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_13", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_14", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_15", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_16", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_17", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_18", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_19", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_20", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_21", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_22", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_23", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_24", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_25", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_26", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_27", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_28", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_29", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_30", width: "100px", handlerType: "input", dataType: "time/time"
        }, {
            key: "_31", width: "100px", handlerType: "input", dataType: "time/time"
        }];
        let updateDetailHeader = {
            columns: newDetailColumns
        };
        let updateDetailContent = {
            columns: newDetailColumns
        };
        $("#show-last-week").click(function() {
            $("#extable").exTable("updateTable", "detail", updateDetailHeader, updateDetailContent, true);
            $("#extable").exTable("updateTable", "horizontalSummaries", { columns: newDetailColumns }, { columns: newDetailColumns });
        });
        $("#hide-last-week").click(function() {
            $("#extable").exTable("updateTable", "detail", { columns: detailColumns }, { columns: detailColumns }, true);
            $("#extable").exTable("updateTable", "horizontalSummaries", { columns: detailColumns }, { columns: detailColumns });
        });
        $("#set-sticker-multi").click(function() {
            return;
        });
        $("#set-sticker-multi2").click(function() {
            $("#extable").exTable("stickData", [ new ExCell("001", "出勤A0", "1", "通常８ｈ0"), new ExCell("MM", "出勤MM", "M0", "通常１０ｈ"), new ExCell("DD", "出勤DD", "M1", "通常１０ｈ"), new ExCell("CC", "出勤CC", "M2", "通常１０ｈ") ]);
        });
        $("#set-sticker-single").click(function() {
            $("#extable").exTable("stickData", new ExCell("MM", "出勤MM", null, null));
//            $("#extable").exTable("stickData", new ExCell("MM", "出勤MM", "M0", "通常１０ｈ"));
//            $("#extable").exTable("stickData", new ExCell("001", "出勤A0", "1", "通常８ｈ0"));
//            $("#extable").exTable("stickData", 
//            { workTypeCode: "001",
//                workTypeName: "出勤A0",
//                workTimeCode: "1",
//                workTimeName: "通常８ｈ0",
//                symbol: "◯",
//                startTime: "8:30",
//                endTime: "17:30",
//                state: 1, register: 0 });
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
    
        $("#stick-styler").click(function() {
            $("#extable").exTable("stickStyler", function(rowIdx, key, data) {
                return { class: "red-text" };
            });
        });    
    
        $("#popup-set").click(function() {
            $("#extable").exTable("popupValue", $("#popup-val").val());
        });
    
        $("#open-dialog").click(function() {
            nts.uk.ui.windows.sub.modal("/view/sample/component/extable/dialog/sample.xhtml");
        });
    
    let newVertSumColumns = [
    { 
        headerText: "残業管理", icon: { for: "header", class: "ui-icon ui-icon-calculator", width: "35px", 
            popup: function() { return $("#popup-items"); }},
        group: [
            { headerText: "残業時間", key: "time", width: "100px" },
            { headerText: "計画", key: "plan", width: "100px" }
        ]
    }
    ];
    let newVertSumHeader = {
        columns: newVertSumColumns,
        width: "200px",
        features: [{
            name: "HeaderRowHeight",
            rows: { 0: "30px", 1: "45px" }   
        }]
    };
    let newVertSumContent = {
        columns: newVertSumColumns,
        dataSource: newVertSumContentDs,
        primaryKey: "empId"
    };
    $("#item-select").click(function() {
        $("#extable").exTable("updateTable", "verticalSummaries", newVertSumHeader, newVertSumContent);
    });
});