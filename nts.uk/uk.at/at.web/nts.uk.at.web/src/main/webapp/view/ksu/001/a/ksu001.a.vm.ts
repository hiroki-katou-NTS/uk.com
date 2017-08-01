module ksu001.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import DirtyChecker = nts.uk.ui.DirtyChecker;
    import modal = nts.uk.ui.windows.sub.modal;
    import formatym = nts.uk.time.parseYearMonthDate;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {

        empItems: KnockoutObservableArray<PersonModel>;
        empSelectedItem: KnockoutObservable<any>;
        ccgcomponent: GroupOption;
        selectedCode: KnockoutObservableArray<any>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        selectedEmployee: KnockoutObservableArray<any>;
//        isShow: KnockoutObservable<boolean>;

        //Grid list A2_4 (pop-up)
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
//        count: number = 100;
//        switchOptions: KnockoutObservableArray<any>;

        //Date time
        dateTimePrev: KnockoutObservable<string>;
        dateTimeAfter: KnockoutObservable<string>;
        dtPrev: KnockoutObservable<Date>;
        dtAft: KnockoutObservable<Date>;

        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<number>;

        modeDisplay: KnockoutObservableArray<any>;
        selectedModeDisplay: KnockoutObservable<number>;

        roundingRules2: KnockoutObservableArray<any>;
        selectedRuleCode2: KnockoutObservable<number>;

        constructor() {
            let self = this;
            self.ccgcomponent = ko.observable();
            self.selectedCode = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(true);
            self.selectedEmployee = ko.observableArray([]);
//            self.isShow = ko.observable(false);
            //Employee 
            self.empItems = ko.observableArray([]);
            self.empSelectedItem = ko.observable();
            self.items = ko.observableArray([]);

            //Date time
            self.dtPrev = ko.observable(new Date('2017/01/01'));
            self.dtAft = ko.observable(new Date('2017/01/31'));
            self.dateTimePrev = ko.observable(moment(self.dtPrev()).format('YYYY/MM/DD'));
            self.dateTimeAfter = ko.observable(moment(self.dtAft()).format('YYYY/MM/DD'));

            self.dtPrev.subscribe(() => {
                self.dateTimePrev(moment(self.dtPrev()).format('YYYY/MM/DD'));
            });
            self.dtAft.subscribe(() => {
                self.dateTimeAfter(moment(self.dtAft()).format('YYYY/MM/DD'));
            });

            //Grid list for pop-up
            for (let i = 1; i <= 12; i++) {
                self.items.push(new ItemModel('00' + i, '基本給' + i, '00' + i));
            }
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSU001_19"), key: 'code', width: 50 },
                { headerText: nts.uk.resource.getText("KSU001_20"), key: 'name', width: 150 },
                { headerText: 'コード', key: 'id', width: 50, hidden: true },
            ]);
            self.currentCodeList = ko.observableArray([]);
            // Fire event.
            $("#multi-list").on('itemDeleted', (function(e: Event) {
                alert("Item is deleted in multi grid is " + e["detail"]["target"]);
            }));
            //Switch button
            self.roundingRules = ko.observableArray([
                { code: 1, name: '抽出' },
                { code: 2, name: '２８日' },
                { code: 3, name: '末日' }]);
            self.selectedRuleCode = ko.observable(1);

            self.modeDisplay = ko.observableArray([
                { code: 1, name: '略名' },
                { code: 2, name: '時刻' },
                { code: 3, name: '記号' }]);
            self.selectedModeDisplay = ko.observable(null);

            self.roundingRules2 = ko.observableArray([
                { code: 1, name: '予定' },
                { code: 2, name: '実績' }]);
            self.selectedRuleCode2 = ko.observable(1);

            //popup 1
            $('#popup-area2').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.create')
                }
            });

            $('.create').click(function() {
                $('#popup-area2').toggle();
            });

            //popup 2
            $('#popup-area3').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.check')
                }
            });

            $('.check').click(function() {
                $('#popup-area3').toggle();
            });

            //popup 3
            $('#popup-area4').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.vacation')
                }
            });

            $('.vacation').click(function() {
                $('#popup-area4').toggle();
            });

            //popup 4
            $('#popup-area5').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('.setting')
                }
            });

            $('.setting').click(function() {
                $('#popup-area5').toggle();
            });

            self.selectedModeDisplay.subscribe(function(newValue) {
                var area = $("#oViewModel");
                area.html("");
                if (newValue == 1) {
                    $('#oViewModel').addClass('oViewModelDisplay');
                    area.load("../o/index.xhtml", function() {
                        var oViewModel = new o.viewmodel.ScreenModel();
                        ko.applyBindings(oViewModel, area.children().get(0));
                    });
                } else {
                    $('#oViewModel').removeClass('oViewModelDisplay');
                }
            });
        }

        start() {
            let self = this;
            var dfd = $.Deferred();
            self.initCCG001();
            self.selectedModeDisplay(1);
            self.initExTable();
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * next one month
         */
        nextMonth(): void {
            let self = this;
            let dtMoment = moment(self.dtAft());
            dtMoment.add(1, 'days');
            self.dtPrev(dtMoment.toDate());
            dtMoment = dtMoment.add(1, 'months');
            dtMoment.subtract(1, 'days');
            self.dtAft(dtMoment.toDate());
        }

        /**
         * come back a month
         */
        prevMonth(): void {
            let self = this;
            let dtMoment = moment(self.dtPrev());
            dtMoment.subtract(1, 'days');
            self.dtAft(dtMoment.toDate());
            dtMoment = dtMoment.subtract(1, 'months');
            dtMoment.add(1, 'days');
            self.dtPrev(dtMoment.toDate());
        }

        initCCG001() {
            let self = this;
            self.ccgcomponent = {
                baseDate: ko.observable(new Date()),
                // Show/hide options 
                isQuickSearchTab: true,
                isAdvancedSearchTab: true,
                isAllReferableEmployee: true,
                isOnlyMe: true,
                isEmployeeOfWorkplace: true,
                isEmployeeWorkplaceFollow: true,
                isMutipleCheck: true,
                isSelectAllEmployee: true,

                //Event options
                /**
                * @param dataList: list employee returned from component.
                * Define how to use this list employee by yourself in the function's body.
                */
                onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                    self.searchEmployee(dataList);

                },
                onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                    self.showinfoSelectedEmployee(true);
                    var dataEmployee: EmployeeSearchDto[] = [];
                    dataEmployee.push(data);
                    self.searchEmployee(dataEmployee);
                },
                onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                    self.searchEmployee(dataList);
                },
                onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                    self.searchEmployee(dataList);
                },
                onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                    self.searchEmployee(dataEmployee);
                }
            }

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

        }

        initExTable(): void {
            let self = this;
            let timeRanges = [];

            // creat data of some columns
            let leftmostDs = [];

            let middleDs = [];
            let middleHeaderDeco = [new CellColor("over1", undefined, "small-font-size"), new CellColor("over2", undefined, "small-font-size")];
            let middleContentDeco = [];

            let detailHeaderDeco = [new CellColor("empId", 1, "ultra-small-font-size")];
            for (let i = -6; i < 32; i++) {
                if (i <= 0) {
                    let d = 31 + i;
                    detailHeaderDeco.push(new CellColor("__" + d, 1, "ultra-small-font-size"))
                } else {
                    detailHeaderDeco.push(new CellColor("_" + i, 1, "ultra-small-font-size"));
                }
            }
            let detailContentDeco = [];
            let detailHeaderDs = [];
            let detailContentDs = [];
            detailHeaderDs.push(new ExItem(undefined, true));
            detailHeaderDs.push({
                empId: "", __25: "over", __26: "", __27: "", __28: "", __29: "", __30: "", __31: "",
                _1: "セール", _2: "", _3: "", _4: "", _5: "", _6: "", _7: "", _8: "", _9: "特別", _10: "",
                _11: "", _12: "", _13: "", _14: "", _15: "", _16: "Oouch", _17: "", _18: "", _19: "", _20: "", _21: "", _22: "", _23: "",
                _24: "", _25: "", _26: "設定", _27: "", _28: "", _29: "", _30: "", _31: "",
            });
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

            let horzSumContentDs = [], leftHorzContentDs = [], vertSumContentDs = [];

            //dataSource
            for (let i = 0; i < 300; i++) {
                //leftMost dataSource
                leftmostDs.push({ empId: i.toString(), empName: "社員名" + i });
                //middle dataSource
                middleDs.push({ empId: i.toString(), cert: "★", over1: "207:00", over2: "23.0" });
                if (i % 2 === 0) middleContentDeco.push(new CellColor("over1", i.toString(), "cell-red"));
                else middleContentDeco.push(new CellColor("over2", i.toString(), "cell-green"));
                //detail dataSource
                if (i % 7 === 0) detailContentDeco.push(new CellColor("_3", i.toString(), "cell-light-green", 0));
                detailContentDs.push(new ExItem(i.toString()));
                if (i < 1000) timeRanges.push(new TimeRange("_2", i.toString(), "17:00", "7:00", 1));
                //vertSumContent dataSource
                vertSumContentDs.push({ empId: i.toString(), noCan: 6, noGet: 6 });
            }
            
            
            for (let i = 0; i < 10; i++) {
                horzSumContentDs.push({
                    itemId: i.toString(), empId: "", __25: "1.0", __26: "1.4", __27: "0.3", __28: "0.9", __29: "1.0", __30: "1.0", __31: "3.3",
                    _1: "1.0", _2: "1.0", _3: "0.5", _4: "1.0", _5: "1.0", _6: "1.0", _7: "0.5", _8: "0.5", _9: "1.0", _10: "0.5",
                    _11: "0.5", _12: "1.0", _13: "0.5", _14: "1.0", _15: "1.0", _16: "0.5", _17: "1.0", _18: "1.0", _19: "1.0", _20: "1.0", _21: "1.0", _22: "1.0", _23: "1.0",
                    _24: "0.5", _25: "0.5", _26: "1.0", _27: "1.0", _28: "1.0", _29: "0.5", _30: "1.0", _31: "1.0"
                });
                leftHorzContentDs.push({ itemId: i.toString(), itemName: "8:00 ~ 9:00", sum: "23.5" });
            }

            //create leftMost Header and Content
            let leftmostColumns = [{
                key: "empName", headerText: "社員名", width: "160px", icon: "ui-icon ui-icon-contact",
                iconWidth: "35px", control: "link", handler: function(rData, rowIdx, key) { alert(rowIdx); }
            }];
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

            //create Middle Header and Content
            let tts = function(rData, rowIdx, colKey) {
                if (rowIdx % 2 === 0) {
                    return $("<div/>").css({ width: "60px", height: "50px" }).html(rData[colKey] + rowIdx);
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

            //create Detail Header and Content
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

            //create VerticalSum Header and Content
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
                }]
            };
            let vertSumContent = {
                columns: vertSumColumns,
                dataSource: vertSumContentDs,
                primaryKey: "empId"
            };
            //create LeftHorzSum Header and Content

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

            //create HorizontalSum Header and Content

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



            new nts.uk.ui.exTable.ExTable($("#extable"), {
                headerHeight: "60px", bodyRowHeight: "50px", bodyHeight: "200px",
                horizontalSumHeaderHeight: "60px", horizontalSumBodyHeight: "200px",
                horizontalSumBodyRowHeight: "20px",
                areaResize: true,
                bodyHeightMode: "dynamic",
                windowOccupation: 800,
                updateMode: "stick",
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
                }
            })
                .LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
                .MiddleHeader(middleHeader).MiddleContent(middleContent)
                .DetailHeader(detailHeader).DetailContent(detailContent)
                .VerticalSumHeader(vertSumHeader).VerticalSumContent(vertSumContent)
                .LeftHorzSumHeader(leftHorzSumHeader).LeftHorzSumContent(leftHorzSumContent)
                .HorizontalSumHeader(horizontalSumHeader).HorizontalSumContent(horizontalSumContent).create();



        }

        searchEmployee(dataEmployee: EmployeeSearchDto[]) {
            var self = this;
            self.empItems.removeAll();
            _.forEach(dataEmployee, function(item: EmployeeSearchDto) {
                self.empItems.push(new PersonModel({
                    personId: item.employeeId,
                    code: item.employeeCode,
                    name: item.employeeName,
                }));
            });
        }
    }

    interface IPersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate?: number;
    }

    class PersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.personId = param.personId;
            this.code = param.code;
            this.name = param.name;
            this.baseDate = param.baseDate || 20170104;
        }
    }

    class TimeModel {
        dateTimePrev: string;
        dateTimeAfter: string;
        text: string;
        constructor(dateTimePrev: string, dateTimeAfter: string, text: string) {
            this.dateTimePrev = dateTimePrev;
            this.dateTimePrev = dateTimeAfter;
            this.text = dateTimePrev + dateTimeAfter;
        }
    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }

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
}