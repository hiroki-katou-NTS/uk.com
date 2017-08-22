module ksu001.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {

        empItems: KnockoutObservableArray<PersonModel> = ko.observableArray([]);
        dataSource: KnockoutObservableArray<BasicSchedule> = ko.observableArray([]);
        ccgcomponent: GroupOption = ko.observable();
        selectedCode: KnockoutObservableArray<any> = ko.observableArray([]);
        showinfoSelectedEmployee: KnockoutObservable<boolean> = ko.observable(true);

        //Grid list A2_4 (pop-up)
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_19"), key: 'code', width: 50 },
            { headerText: nts.uk.resource.getText("KSU001_20"), key: 'name', width: 150 },
            { headerText: 'コード', key: 'id', width: 50, hidden: true },
        ]);
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);

        //Date time
        dtPrev: KnockoutObservable<Date> = ko.observable(new Date('2017/01/01'));
        dtAft: KnockoutObservable<Date> = ko.observable(new Date('2017/01/31'));
        dateTimePrev: KnockoutObservable<string>;
        dateTimeAfter: KnockoutObservable<string>;


        //Switch
        timePeriod: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: '抽出' },
            { code: 2, name: '２８日' },
            { code: 3, name: '末日' }]);
        selectedTimePeriod: KnockoutObservable<number> = ko.observable(1);

        modeDisplay: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: '略名' },
            { code: 2, name: '時刻' },
            { code: 3, name: '記号' }]);
        selectedModeDisplay: KnockoutObservable<number> = ko.observable(undefined);

        modeDisplayObject: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: '予定' },
            { code: 2, name: '実績' }]);
        selectedModeDisplayObject: KnockoutObservable<number> = ko.observable(1);

        arrDay: Time[] = [];
        listSid: string[] = [];
        isClickNextBackMonth: boolean = false;

        constructor() {
            let self = this;

            //Date time
            self.dateTimeAfter = ko.observable(moment(self.dtAft()).format('YYYY/MM/DD'));
            self.dateTimePrev = ko.observable(moment(self.dtPrev()).format('YYYY/MM/DD'));

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

            // Fire event.
            $("#multi-list").on('itemDeleted', (function(e: Event) {
                alert("Item is deleted in multi grid is " + e["detail"]["target"]);
            }));

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
                if (newValue == 1) {
                    $('#oViewModel').addClass('oViewModelDisplay');
                } else {
                    $('#oViewModel').removeClass('oViewModelDisplay');
                }
            });

            //start
            self.selectedModeDisplay(1);
            self.initCCG001();
            self.initExTable();

            //            _.delay(() => {
            //                $('#hor-scroll-button-hide').click();
            //            }, 300);
        }

        /**
         * Get data Basic_Schedule
         */
        getDataBasicSchedule(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                obj = {
                    sId: self.listSid,
                    startDate: self.dtPrev(),
                    endDate: self.dtAft()
                };
            service.getDataBasicSchedule(obj).done(function(data: BasicSchedule[]) {
                if (data) {
                    self.dataSource(data);
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        searchEmployee(dataEmployee: EmployeeSearchDto[]) {
            let self = this;
            self.empItems.removeAll();
            _.forEach(dataEmployee, function(item: EmployeeSearchDto) {
                self.empItems.push(new PersonModel({
                    empId: item.employeeId,
                    empCd: item.employeeCode,
                    empName: item.employeeName,
                    workplaceId: item.workplaceId,
                    wokplaceCd: item.workplaceCode,
                    workplaceName: item.workplaceName,
                }));
            });
            //
            self.listSid = [];
            _.each(self.empItems(), (x) => {
                self.listSid.push(x.empId);
            });
            //get data basicSchedule
            self.getDataBasicSchedule().done(function() {
                self.updateExTable();
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

        /**
         * Create exTable
         */
        initExTable(): void {
            let self = this,
                timeRanges = [],
                //Get dates in time period
                currentDay = new Date(self.dtPrev().toString());

            while (currentDay <= self.dtAft()) {
                self.arrDay.push(new Time(currentDay.toString()));
                currentDay.setDate(currentDay.getDate() + 1);
            }

            // create data for columns
            let leftmostDs = [],
                middleDs = [],
                middleContentDeco = [],
                detailHeaderDeco = [],
                detailContentDeco = [],
                detailHeaderDs = [],
                detailContentDs = [],
                objDetailHeaderDs = {},
                detailColumns = [],
                horzSumHeaderDs = [],
                horzSumContentDs = [],
                leftHorzContentDs = [],
                vertSumContentDs = [];

            self.setColorForDetail(detailHeaderDeco, detailContentDeco);

            //create dataSource for detailHeader
            detailHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));
            for (let i = 0; i < self.arrDay.length; i++) {
                objDetailHeaderDs['_' + self.arrDay[i].yearMonthDay] = '';
            }
            detailHeaderDs.push(objDetailHeaderDs);

            //define the detailColumns
            _.each(self.arrDay, (x: Time) => {
                detailColumns.push({
                    key: "_" + x.yearMonthDay, width: "100px", headerText: "", visible: true
                });
            });

            horzSumHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));

            //create leftMost Header and Content
            let leftmostColumns = [{
                headerText: nts.uk.resource.getText("KSU001_56"), key: "empName", width: "160px", icon: "ui-icon ui-icon-contact",
                iconWidth: "35px", control: "link", handler: function(rData, rowIdx, key) { }
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

            /**
             * next a month
             */
            $("#nextMonth").click(function() {
                //Recalculate the time period
                let dtMoment = moment(self.dtAft());
                dtMoment.add(1, 'days');
                self.dtPrev(dtMoment.toDate());
                dtMoment = dtMoment.add(1, 'months');
                dtMoment.subtract(1, 'days');
                self.dtAft(dtMoment.toDate());
                self.isClickNextBackMonth = true;
                self.updateDetailHozrSum(self.isClickNextBackMonth);
            });

            /**
             * come back a month
             */
            $("#prevMonth").click(function() {
                //Recalculate the time period
                let dtMoment = moment(self.dtPrev());
                dtMoment.subtract(1, 'days');
                self.dtAft(dtMoment.toDate());
                dtMoment = dtMoment.subtract(1, 'months');
                dtMoment.add(1, 'days');
                self.dtPrev(dtMoment.toDate());
                self.isClickNextBackMonth = true;
                self.updateDetailHozrSum(self.isClickNextBackMonth);
            });

            /**
             * Save data
             */
            $("#saveData").click(function() {
                let dfd = $.Deferred(),
                    arrObj: BasicSchedule[] = [],
                    arrCell: Cell[] = $("#extable").exTable("updatedCells"),
                    lengthArrCell = arrCell.length;

                for (let i = 0; i < lengthArrCell; i += 2) {
                    arrObj.push(new BasicSchedule({
                        // slice string '_YYYYMMDD' to 'YYYYMMDD'
                        date: moment.utc(arrCell[i].columnKey.slice(1, arrCell[i].columnKey.length), 'YYYYMMDD').toISOString(),
                        employeeId: self.listSid[arrCell[i].rowIndex],
                        //                        workTimeCd: arrCell[i + 1].value,
                        //                        workTypeCd: arrCell[i].value
                        workTimeCd: 'ABC',
                        workTypeCd: '001'
                    }));
                }
                service.registerData(arrObj).done(function() {
                    //get data and update extable
                    self.getDataBasicSchedule().done(function() {
                        self.updateExTable();
                    });
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
                return dfd.promise();
            });
        }

        /**
         *  update extable 
         */
        updateExTable(): void {
            let self = this;
            //Paste data into cell (set-sticker-single)
            $("#extable").exTable("stickData", __viewContext.viewModel.viewO.nameWorkTimeType());

            let newLeftMostDs = [], newMiddleDs = [], newDetailContentDs = [], newVertSumContentDs = [], newLeftHorzContentDs = [];

            _.each(self.listSid, (x) => {
                //newLeftMost dataSource
                let empItem: PersonModel = _.find(self.empItems(), ['empId', x]);
                newLeftMostDs.push({ empId: x, empName: empItem.empCd + ' ' + empItem.empName });
                //newMiddle dataSource
                newMiddleDs.push({ empId: x, team: "1", rank: "A", qualification: "★", employmentName: "アルバイト", workplaceName: "東京本社", classificationName: "分類", positionName: "一般" });
                //newDetail dataSource
                let dsOfSid: any = _.filter(self.dataSource(), ['sid', x]);
                newDetailContentDs.push(new ExItem(x, dsOfSid, __viewContext.viewModel.viewO.listWorkType(), __viewContext.viewModel.viewO.listWorkTime(), false, self.arrDay));
                //vertSumContent dataSource
                newVertSumContentDs.push({ empId: x, noCan: 6, noGet: 6 });
            });

            for (let i = 0; i < 10; i++) {
                newLeftHorzContentDs.push({ itemId: i.toString(), itemName: "8:00 ~ 9:00", sum: "23.5" });
            }

            let updateLeftmostContent = {
                dataSource: newLeftMostDs,
            };

            let updateMiddleContent = {
                dataSource: newMiddleDs,
            };

            let updateDetailContent = {
                dataSource: newDetailContentDs,
            };

            let updateVertSumContent = {
                dataSource: newVertSumContentDs,
            };

            let updateLeftHorzSumContent = {
                dataSource: newLeftHorzContentDs
            };

            $("#extable").exTable("updateTable", "leftmost", {}, updateLeftmostContent);
            $("#extable").exTable("updateTable", "middle", {}, updateMiddleContent);
            $("#extable").exTable("updateTable", "verticalSummaries", {}, updateVertSumContent);
            $("#extable").exTable("updateTable", "leftHorizontalSummaries", {}, updateLeftHorzSumContent);

            self.isClickNextBackMonth = false;
            self.updateDetailHozrSum(self.isClickNextBackMonth);
        }

        /**
         * update new data of header and content of detail and horizSum
         */
        updateDetailHozrSum(isClickNextBackMonth: boolean): void {
            let self = this;

            //Get dates in time period
            let currentDay = new Date(self.dtPrev().toString());
            self.arrDay = [];
            let newDetailColumns = [], newObjDetailHeaderDs = [], newDetailHeaderDs = [], newDetailContentDs = [];
            while (currentDay <= self.dtAft()) {
                self.arrDay.push(new Time(currentDay.toString()));
                currentDay.setDate(currentDay.getDate() + 1);
            }

            //define the new detailColumns
            _.each(self.arrDay, (x: Time) => {
                newDetailColumns.push({
                    key: "_" + x.yearMonthDay, width: "100px", headerText: "", visible: true
                });
            });

            //create new detailHeaderDs
            newDetailHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));
            for (let i = 0; i < self.arrDay.length; i++) {
                newObjDetailHeaderDs['_' + self.arrDay[i].yearMonthDay] = '';
            }
            newDetailHeaderDs.push(newObjDetailHeaderDs);

            //get new horzSumContentDs
            let horzSumContentDs = [];
            for (let i = 0; i < 10; i++) {
                let obj = {};
                obj["itemId"] = i.toString();
                obj["empId"] = "";
                for (let j = 0; j < self.arrDay.length; j++) {
                    obj['_' + self.arrDay[j].yearMonthDay] = "10";
                }
                horzSumContentDs.push(obj);
            }

            let detailHeaderDeco = [], detailContentDeco = [];
            //Set color for detail
            self.setColorForDetail(detailHeaderDeco, detailContentDeco);
            let updateDetailHeader = {
                columns: newDetailColumns,
                dataSource: newDetailHeaderDs,
                features: [{
                    name: "HeaderCellStyle",
                    decorator: detailHeaderDeco
                }]
            };

            //if haven't data in extable, only update header detail and header horizontal
            if (isClickNextBackMonth && self.empItems().length == 0) {
                $("#extable").exTable("updateTable", "detail", updateDetailHeader, {});
                $("#extable").exTable("updateTable", "horizontalSummaries", updateDetailHeader, {});
            } else {
                self.getDataBasicSchedule().done(() => {
                    //dataSource
                    _.each(self.listSid, (x) => {
                        let dsOfSid: any = _.filter(self.dataSource(), ['sid', x]);
                        newDetailContentDs.push(new ExItem(x, dsOfSid, __viewContext.viewModel.viewO.listWorkType(), __viewContext.viewModel.viewO.listWorkTime(), false, self.arrDay));
                    });

                    let updateDetailContent = {
                        columns: newDetailColumns,
                        dataSource: newDetailContentDs,
                        features: [{
                            name: "BodyCellStyle",
                            decorator: detailContentDeco
                        }]
                    };

                    let updateHorzSumContent = {
                        columns: newDetailColumns,
                        dataSource: horzSumContentDs
                    };

                    $("#extable").exTable("updateTable", "detail", updateDetailHeader, updateDetailContent);
                    $("#extable").exTable("updateTable", "horizontalSummaries", updateDetailHeader, updateHorzSumContent);
                });
            }
        }

        /**
         * Set color for detailHeader
         */
        setColorForDetail(detailHeaderDeco: any, detailContentDeco: any): void {
            let self = this;

            for (let i = 0; i < self.arrDay.length; i++) {
                if (self.arrDay[i].weekDay == '土') {
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].yearMonthDay, 0, "color-blue text-color-blue text-align-center"));
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].yearMonthDay, 1, "color-blue"));
                } else if (self.arrDay[i].weekDay == '日') {
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].yearMonthDay, 0, "color-pink text-color-red text-align-center"));
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].yearMonthDay, 1, "color-pink"));
                } else {
                    detailHeaderDeco.push(new CellColor("_" + self.arrDay[i].yearMonthDay, 0, "text-align-center"));
                }
                //Set color for detailContent
                _.each(self.listSid, (empId) => {
                    if (self.arrDay[i].weekDay == '土' || self.arrDay[i].weekDay == '日') {
                        detailContentDeco.push(new CellColor("_" + self.arrDay[i].yearMonthDay, empId, "text-color-red"));
                    } else {
                        detailContentDeco.push(new CellColor("_" + self.arrDay[i].yearMonthDay, empId, "text-color-blue"));
                    }
                });
            }
        }
    }

    interface ICell {
        rowIndex: string,
        columnKey: string,
        value: string,
        innerIdx: number
    }

    class Cell {
        rowIndex: string;
        columnKey: string;
        value: string;
        innerIdx: number;

        constructor(params: ICell) {
            this.rowIndex = params.rowIndex;
            this.columnKey = params.columnKey;
            this.value = params.value;
            this.innerIdx = params.innerIdx;
        }
    }

    interface IPersonModel {
        empId: string,
        empCd: string,
        empName: string,
        workplaceId: string,
        wokplaceCd: string,
        workplaceName: string,
        baseDate?: number
    }

    class PersonModel {
        empId: string;
        empCd: string;
        empName: string;
        workplaceId: string;
        wokplaceCd: string;
        workplaceName: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.empId = param.empId;
            this.empCd = param.empCd;
            this.empName = param.empName;
            this.workplaceId = param.workplaceId;
            this.wokplaceCd = param.wokplaceCd;
            this.workplaceName = param.workplaceName;
            this.baseDate = param.baseDate;
        }
    }

    interface IBasicSchedule {
        date: string,
        employeeId: string,
        workTimeCd: string,
        workTypeCd: string
    }

    class BasicSchedule {
        date: string;
        employeeId: string;
        workTimeCd: string;
        workTypeCd: string;

        constructor(params: IBasicSchedule) {
            this.date = params.date;
            this.employeeId = params.employeeId;
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
        year: string;
        month: string;
        day: string;
        weekDay: string;
        yearMonthDay: string;

        constructor(ymd: string) {
            this.ymd = ymd;
            this.year = moment(this.ymd).format('YYYY');
            this.month = moment(this.ymd).format('M');
            this.day = moment(this.ymd).format('D');
            this.weekDay = moment(this.ymd).format('dd');
            this.yearMonthDay = this.year + moment(this.ymd).format('MM') + moment(this.ymd).format('DD');
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

        constructor(empId: string, dsOfSid: BasicSchedule[], listWorkType: WorkType[], listWorkTime: WorkTime[], manual: boolean, arrDay: Time[]) {
            this.empId = empId;
            this.empName = empId;
            // create detailHeader (ex: 4/1 | 4/2)
            if (manual) {
                for (let i = 0; i < arrDay.length; i++) {
                    this['_' + arrDay[i].yearMonthDay] = arrDay[i].month + '/' + arrDay[i].day + "<br/>" + arrDay[i].weekDay;
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
                    this['_' + arrDay[i].yearMonthDay] = ['休日', ''];
                } else if (obj) {
                    //get name of workType and workTime
                    let workTypeName = _.find(listWorkType, ['workTypeCode', obj.workTypeCd]).abbreviationName;
                    let workTimeName = _.find(listWorkTime, ['siftCd', obj.workTimeCd]).abName;
                    this['_' + arrDay[i].yearMonthDay] = [workTypeName, workTimeName];
                } else {
                    this['_' + arrDay[i].yearMonthDay] = ['', ''];
                }
            }
        }
    }
}