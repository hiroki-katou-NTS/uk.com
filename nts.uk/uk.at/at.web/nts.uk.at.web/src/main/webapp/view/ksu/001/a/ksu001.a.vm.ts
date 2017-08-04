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
        dataSource: KnockoutObservableArray<BasicSchedule>;
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

        oViewModel: any;
        selectedWorkTimeName: any;
        arrDay: Time[] = [];
        listSid: any = ["00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000002", "00000000-0000-0000-0000-000000000003", "00000000-0000-0000-0000-000000000004",
            "00000000-0000-0000-0000-000000000005", "00000000-0000-0000-0000-000000000006", "00000000-0000-0000-0000-000000000007", "00000000-0000-0000-0000-000000000008",
            "00000000-0000-0000-0000-000000000009", "00000000-0000-0000-0000-000000000010"];

        constructor() {
            let self = this;
            self.ccgcomponent = ko.observable();
            self.selectedCode = ko.observableArray([]);
            self.dataSource = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(true);
            self.selectedEmployee = ko.observableArray([]);
            //            self.isShow = ko.observable(false);
            //Employee 
            self.empItems = ko.observableArray([]);
            self.empSelectedItem = ko.observable();
            self.items = ko.observableArray([]);

            //Date time
            self.dtPrev = ko.observable(new Date('2016/12/29'));
            self.dtAft = ko.observable(new Date('2017/01/15'));
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

            //Diplay screen O
            self.selectedModeDisplay.subscribe(function(newValue) {
                let area = $("#oViewModel");
                area.html("");
                if (newValue == 1) {
                    $('#oViewModel').addClass('oViewModelDisplay');
                    area.load("../o/index.xhtml", function() {
                        self.oViewModel = new o.viewmodel.ScreenModel();
                        self.oViewModel.nameWorkTimeType.subscribe(function (value) {
                            //Paste data into cell (set-sticker-single)
                            $("#extable").exTable("stickData", value);
                        });
                        ko.applyBindings(self.oViewModel, area.children().get(0));
                    });
                } else {
                    $('#oViewModel').removeClass('oViewModelDisplay');
                }
            });
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.initCCG001();
            //create screen O
            self.selectedModeDisplay(1);
            $.when(self.getDataBasicSchedule()).done(function() {
                self.initExTable();
            });
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * Get data Basic_Schedule
         */
        getDataBasicSchedule(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            //obj is fixed
            let obj = {
                sId: self.listSid,
                startDate: self.dtPrev(),
                endDate: self.dtAft()
            };
            service.getDataBasicSchedule(obj).done(function(data: any) {
                if (data) {
                    self.dataSource(data);
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * next one month
         */
        //        nextMonth(): void {
        //            let self = this;
        //            let dtMoment = moment(self.dtAft());
        //            dtMoment.add(1, 'days');
        //            self.dtPrev(dtMoment.toDate());
        //            dtMoment = dtMoment.add(1, 'months');
        //            dtMoment.subtract(1, 'days');
        //            self.dtAft(dtMoment.toDate());
        //        }

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

            //Get dates in time period
            var currentDay = new Date(self.dtPrev().toString());
            while (currentDay <= self.dtAft()) {
                self.arrDay.push(new Time(currentDay.toString()));
                currentDay.setDate(currentDay.getDate() + 1);
            }

            // create data for columns
            let leftmostDs = [], middleDs = [], middleContentDeco = [], detailHeaderDeco = [], detailContentDeco = [], detailHeaderDs = [], detailContentDs = [], objDetailHeaderDs = {};
            //Set color for detailHeader
            for (let i = 0; i < self.arrDay.length; i++) {
                if (self.arrDay[i].weekDay == '土') {
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].day, 0, "color-blue text-color-blue text-align-center"));
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].day, 1, "color-blue"));
                }
                else if (self.arrDay[i].weekDay == '日') {
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].day, 0, "color-pink text-color-red text-align-center"));
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].day, 1, "color-pink"));
                } else {
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].day, 0, "text-align-center"));
                }
                //Set color for detailContent
                _.each(self.listSid, (empId) => {
                    if (self.arrDay[i].weekDay == '土' || self.arrDay[i].weekDay == '日') {
                        detailContentDeco.push(new CellColor("_" + self.arrDay[i].day, empId, "text-color-red"));
                    } else {
                        detailContentDeco.push(new CellColor("_" + self.arrDay[i].day, empId, "text-color-blue"));
                    }
                });
            }

            //create dataSource for detailHeader
            detailHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));
            for (let i = 0; i < self.arrDay.length; i++) {
                objDetailHeaderDs['_' + self.arrDay[i].day] = '';
            }
            detailHeaderDs.push(objDetailHeaderDs);

            //define the detailColumns
            let detailColumns = [];
            _.each(self.arrDay, (x: Time) => {
                detailColumns.push({
                    key: "_" + x.day, width: "100px", headerText: "", visible: true
                });
            });
            let horzSumHeaderDs = [], horzSumContentDs = [], leftHorzContentDs = [], vertSumContentDs = [];
            horzSumHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));

            //dataSource
            _.each(self.listSid, (x) => {
                //leftMost dataSource
                leftmostDs.push({ empId: x, empName: "社員名" });
                //middle dataSource
                middleDs.push({ empId: x, team: "1", rank: "A", qualification: "★", employmentName: "アルバイト", workplaceName: "東京本社", classificationName: "分類", positionName: "一般" });
                //detail dataSource
                let dsOfSid: any = _.filter(self.dataSource(), ['sid', x]);
                detailContentDs.push(new ExItem(x, dsOfSid, self.oViewModel.listWorkType(), self.oViewModel.listWorkTime(), false, self.arrDay));
                //vertSumContent dataSource
                vertSumContentDs.push({ empId: x, noCan: 6, noGet: 6 });
            });

            for (let i = 0; i < 10; i++) {
                horzSumContentDs.push({
                    itemId: i.toString(), empId: "", _1: "1.0", _2: "1.0", _3: "0.5", _4: "1.0", _5: "1.0", _6: "1.0", _7: "0.5", _8: "0.5", _9: "1.0", _10: "0.5",
                    _11: "0.5", _12: "1.0", _13: "0.5", _14: "1.0", _15: "1.0", _16: "0.5", _17: "1.0", _18: "1.0", _19: "1.0", _20: "1.0", _21: "1.0", _22: "1.0", _23: "1.0",
                    _24: "0.5", _25: "0.5", _26: "1.0", _27: "1.0", _28: "1.0", _29: "0.5", _30: "1.0", _31: "1.0"
                });
                leftHorzContentDs.push({ itemId: i.toString(), itemName: "8:00 ~ 9:00", sum: "23.5" });
            }

            //create leftMost Header and Content
            let leftmostColumns = [{
                headerText: nts.uk.resource.getText("KSU001_56"), key: "empName", width: "160px", icon: "ui-icon ui-icon-contact",
                iconWidth: "35px", control: "link", handler: function(rData, rowIdx, key) { alert('社員名'); }
            }];
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

            //create Middle Header and Content
            let middleColumns = [
                { headerText: nts.uk.resource.getText("KSU001_57"), key: "team", width: "50px" },
                { headerText: nts.uk.resource.getText("KSU001_58"), key: "rank", width: "50px" },
                { headerText: nts.uk.resource.getText("KSU001_59"), key: "qualification", width: "50px" },
                { headerText: nts.uk.resource.getText("KSU001_60"), key: "employmentName", width: "100px" },
                { headerText: nts.uk.resource.getText("KSU001_61"), key: "workplaceName", width: "150px" },
                { headerText: nts.uk.resource.getText("KSU001_62"), key: "classificationName", width: "100px" },
                { headerText: nts.uk.resource.getText("KSU001_63"), key: "positionName", width: "100px" },
            ];

            let middleHeader = {
                columns: middleColumns,
                width: "100px",
                features: [{
                    name: "HeaderRowHeight",
                    rows: { 0: "75px" }
                },
                    {
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
                    rows: { 0: "50px", 1: "25px" }
                }, {
                        name: "HeaderCellStyle",
                        decorator: detailHeaderDeco
                    }, {
                        name: "ColumnResizes"
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
                    rows: { 0: "30px", 1: "45px" }
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
                rowHeight: "75px"
            };

            let leftHorzSumContent = {
                columns: leftHorzColumns,
                dataSource: leftHorzContentDs,
                primaryKey: "itemId"
            };

            //create HorizontalSum Header and Content

            let horizontalSumHeader = {
                columns: detailColumns,
                dataSource: horzSumHeaderDs,
                rowHeight: "75px",
                features: [{
                    name: "HeaderCellStyle",
                    decorator: detailHeaderDeco
                }]
            };

            let horizontalSumContent = {
                columns: detailColumns,
                dataSource: horzSumContentDs,
                primaryKey: "itemId"
            };

            new nts.uk.ui.exTable.ExTable($("#extable"), {
                headerHeight: "75px", bodyRowHeight: "50px", bodyHeight: "200px",
                horizontalSumHeaderHeight: "75px", horizontalSumBodyHeight: "200px",
                horizontalSumBodyRowHeight: "20px",
                areaResize: true,
                bodyHeightMode: "dynamic",
                windowOccupation: 50,
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

            //set mode of exTable is stickMode single
            $("#extable").exTable("stickMode", "single");
            //Paste data into cell (set-sticker-single)
            $("#extable").exTable("stickData", self.oViewModel.nameWorkTimeType());

            //when next/back month
            let updateDetailHeader = {
                columns: detailColumns
            };
            let updateDetailContent = {
                columns: detailColumns
            };

            $("#nextMonth").click(function() {
                //Recalculate the time period
                let dtMoment = moment(self.dtAft());
                dtMoment.add(1, 'days');
                self.dtPrev(dtMoment.toDate());
                dtMoment = dtMoment.add(1, 'months');
                dtMoment.subtract(1, 'days');
                self.dtAft(dtMoment.toDate());

                //Get dates in time period
                let currentDay = new Date(self.dtPrev().toString());
                self.arrDay = [];
                while (currentDay <= self.dtAft()) {
                    self.arrDay.push(new Time(currentDay.toString()));
                    currentDay.setDate(currentDay.getDate() + 1);
                }

                //define the new detailColumns
                let newDetailColumns = [{
                    key: "empId", width: "50px", headerText: "ABC", visible: false
                }];
                _.each(self.arrDay, (x: Time) => {
                    newDetailColumns.push({
                        key: "_" + x.day, width: "100px", headerText: "", visible: true
                    });
                });

                let updateDetailHeader = {
                    columns: newDetailColumns
                };
                let updateDetailContent = {
                    columns: newDetailColumns
                };

                $("#extable").exTable("updateTable", "detail", updateDetailHeader, updateDetailContent);
                $("#extable").exTable("updateTable", "horizontalSummaries", updateDetailHeader, updateDetailContent);
                //updateCell return arr[rowIndex, columnKey, innerIdx (là index của cell con ở trong cell lớn)]
                $("#exTable").exTable("updatedCells");
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
            this.baseDate = param.baseDate;
        }
    }

    interface IBasicSchedule {
        date: string,
        sid: string,
        workTimeCd: string,
        workTypeCd: string
    }

    class BasicSchedule {
        date: string;
        sid: string;
        workTimeCd: string;
        workTypeCd: string;

        constructor(params: IBasicSchedule) {
            this.date = params.date;
            this.sid = params.sid;
            this.workTimeCd = params.workTimeCd;
            this.workTypeCd = params.workTypeCd;
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

    class Time {
        ymd: string;
        month: string;
        day: string;
        weekDay: string;

        constructor(ymd: string) {
            this.ymd = ymd;
            this.month = moment(this.ymd).format('M');
            this.day = moment(this.ymd).format('D');
            this.weekDay = moment(this.ymd).format('dd');
        }
    }

    interface IWorkType {
        workTypeCode: string,
        sortOrder: number,
        symbolicName: string,
        name: string,
        abbreviationName: string,
        memo: string,
        displayAtr: number
    }

    class WorkType {
        workTypeCode: string;
        sortOrder: number;
        symbolicName: string;
        name: string;
        abbreviationName: string;
        memo: string;
        displayAtr: number;

        constructor(params: IWorkType) {
            this.workTypeCode = params.workTypeCode;
            this.sortOrder = params.sortOrder;
            this.symbolicName = params.symbolicName;
            this.name = params.name;
            this.abbreviationName = params.abbreviationName;
            this.memo = params.memo;
            this.displayAtr = params.displayAtr;
        }
    }

    interface IWorkTime {
        siftCd: string,
        name: string,
        abName: string,
        dailyWorkAtr: number,
        methodAtr: number,
        displayAtr: number,
        note: string
    }

    class WorkTime {
        siftCd: string;
        name: string;
        abName: string;
        dailyWorkAtr: number;
        methodAtr: number;
        displayAtr: number;
        note: string;

        constructor(params: IWorkTime) {
            this.siftCd = params.siftCd;
            this.name = params.name;
            this.abName = params.abName;
            this.dailyWorkAtr = params.dailyWorkAtr;
            this.methodAtr = params.methodAtr;
            this.displayAtr = params.displayAtr;
            this.note = params.note;
        }
    }

    class ExItem {
        empId: string;
        empName: string;
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

        constructor(empId: string, dsOfSid: BasicSchedule[], listWorkType: WorkType[], listWorkTime: WorkTime[], manual: boolean, arrDay: Time[]) {
            this.empId = empId;
            this.empName = empId;
            // create detailHeader (ex: 4/1 | 4/2)
            if (manual) {
                for (let i = 0; i < arrDay.length; i++) {
                    this['_' + arrDay[i].day] = arrDay[i].month + '/' + arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                }
                return;
            }
            //create detailContent (ex: [workType, workTime] : ["出勤", "通常４ｈ "])
            for (let i = 0; i < arrDay.length; i++) {
                let obj: BasicSchedule = _.find(dsOfSid, (x) => {
                    return moment(x.date).format('D') == arrDay[i].day;
                });
                //holiday
                if (arrDay[i].weekDay == '日' || arrDay[i].weekDay == '土') {
                    this['_' + arrDay[i].day] = ['休日', ''];
                } else if (obj) {
                    //get name of workType and workTime
                    let workTypeName = _.find(listWorkType, ['workTypeCode', obj.workTypeCd]).abbreviationName;
                    let workTimeName = _.find(listWorkTime, ['siftCd', obj.workTimeCd]).abName;
                    this['_' + arrDay[i].day] = [workTypeName, workTimeName];
                } else {
                    this['_' + arrDay[i].day] = ['', ''];
                }
            }
        }
    }
}