module nts.uk.at.view.ksu001.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import openDialog = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;


    /**
     * load screen O->Q->A
     * reference file a.start.ts
     */
    export class ScreenModel {
        
        employeeIdLogin: string = null;

        empItems: KnockoutObservableArray<PersonModel> = ko.observableArray([]);
        dataSource: KnockoutObservableArray<BasicSchedule> = ko.observableArray([]);
        visibleShiftPalette : KnockoutObservable<boolean> = ko.observable(true);
        
        modeAction : KnockoutObservable<string> = ko.observable('symbol'); //symbol | shortName | time
        
        // A4 popup-area6 
        // A4_4
        selectedModeDisplayInBody: KnockoutObservable<number> = ko.observable('Ac'); //シフト表示 //Hiển thị shift  1 cell  // 略名表示 //Hiển thị detail 2 cell  // 勤務表示 //Hiển thị working 4 cell                                     
        
        // A4_7
        achievementDisplaySelected: KnockoutObservable<number> = ko.observable(2);

        // A4_12
        backgroundColorSelected: KnockoutObservable<string> = ko.observable(0);

        isEnableCompareMonth: KnockoutObservable<boolean> = ko.observable(true);

        popupVal: KnockoutObservable<string> = ko.observable('');
        selectedDate: KnockoutObservable<string> = ko.observable('1993/23/12');

        //Date time A3_1

        currentDate: Date = new Date();
        dtPrev: KnockoutObservable<Date> = ko.observable(new Date()); // A3_1_2
        dtAft: KnockoutObservable<Date> = ko.observable(new Date());  // A3_1_4
        dateTimePrev: KnockoutObservable<string>;
        dateTimeAfter: KnockoutObservable<string>;

        //Switch  A3_2
        selectedModeDisplay: KnockoutObservable<number> = ko.observable(1);
        
        // A2_2
        targetOrganizationName: KnockoutObservable<string> = ko.observable('開発本部　第一開発部　　　UK就業　チーム１0');
        
        // popup Setting Grid
        selectedTypeHeightExTable : KnockoutObservable<number> = ko.observable(1);
        heightGridSetting: KnockoutObservable<string> = ko.observable('');
        isEnableInputHeight :KnockoutObservable<boolean> = ko.observable(false);
        
        // dùng cho xử lý của botton toLeft, toRight, toDown
        indexBtnToLeft: number  = 0;
        indexBtnToRight: number = 0;
        indexBtnToDown: number  = 0;
        
        //
        arrDay: Time[] = [];
        listSid: KnockoutObservableArray<string> = ko.observableArray([]);
        
        isClickChangeDisplayMode: boolean = false;
        listColorOfHeader: KnockoutObservableArray<ksu001.common.modelgrid.CellColor> = ko.observableArray([]);
        
        affiliationId: any = null;
        affiliationName: KnockoutObservable<string> = ko.observable('');
        dataWScheduleState: KnockoutObservableArray<WorkScheduleState> = ko.observableArray([]);
        listStateWorkTypeCode: KnockoutObservableArray<any> = ko.observableArray([]);
        listCheckNeededOfWorkTime: KnockoutObservableArray<any> = ko.observableArray([]);
        dataWkpSpecificDate: KnockoutObservableArray<any> = ko.observableArray([]);
        dataComSpecificDate: KnockoutObservableArray<any> = ko.observableArray([]);
        dataPublicHoliday: KnockoutObservableArray<any> = ko.observableArray([]);
        dataWorkEmpCombine: KnockoutObservableArray<any> = ko.observableArray([]);
        dataScheduleDisplayControl: KnockoutObservable<any> = ko.observableArray([]);
        isInsuranceStatus: boolean = false;
        
        flag: boolean = true;
        
        stopRequest: KnockoutObservable<boolean> = ko.observable(true);
        arrLockCellInit: KnockoutObservableArray<Cell> = ko.observableArray([]);
        // 表示形式 ＝ 日付別(固定) = 0
        displayFormat: KnockoutObservable<number> = ko.observable(0);
        hasEmployee: KnockoutObservable<boolean> = ko.observable(false);
        KEY : string = 'USER_INFOR';
        
        constructor() {
            let self = this;
            
            //Date time
            self.dateTimeAfter = ko.observable(moment(self.dtAft()).format('YYYY/MM/DD'));
            self.dateTimePrev = ko.observable(moment(self.dtPrev()).format('YYYY/MM/DD'));

            self.dtPrev.subscribe((newValue) => {
                self.dateTimePrev(moment(self.dtPrev()).format('YYYY/MM/DD'));
            });
            self.dtAft.subscribe((newValue) => {
                self.dateTimeAfter(moment(self.dtAft()).format('YYYY/MM/DD'));
            });
            
            self.selectedTypeHeightExTable.subscribe((newValue) => {
                if(newValue == TypeHeightExTable.DEFAULT){ // 
                    self.isEnableInputHeight(false);
                    self.heightGridSetting('');
                    $('#input-heightExtable').ntsError('clear');
                }else if(newValue == TypeHeightExTable.SETTING){
                    self.isEnableInputHeight(true);
                    setTimeout(() =>{
                        $('#input-heightExtable').focus();                        
                    }, 1);
                }
            });
            
            self.achievementDisplaySelected.subscribe(function(newValue) {
                if (newValue == 1) {
                    self.isEnableCompareMonth(true);
                } else {
                    self.isEnableCompareMonth(false);
                }
            });

            self.selectedModeDisplayInBody.subscribe(function(newValue) {
                if (newValue == null)
                    return;
                
                console.log('mode:  ' +newValue);
                nts.uk.ui.errors.clearAll();
                __viewContext.viewModel.viewAB.time1('');
                __viewContext.viewModel.viewAB.time2('');
                
                self.stopRequest(false);
                // close screen O1 when change mode
                let currentScreen = __viewContext.viewModel.viewAB.currentScreen;
                if (currentScreen) {
                    currentScreen.close();
                }
                
                let detailContentDeco: any[] = [];  
                
//                if (self.empItems().length == 0) {
//                    self.stopRequest(true);
//                    return;
//                }

                if (newValue == 'Aa') { // mode 勤務表示 //Hiển thị working       
                    self.saveModeGridToLocalStorege('Aa');
                    self.visibleShiftPalette(true);
                    self.startPage('symbol');
                    self.stopRequest(true);
                } else if (newValue == 'Ab') { // mode 略名表示 //Hiển thị detail 
                    self.saveModeGridToLocalStorege('Ab');
                    self.visibleShiftPalette(false);
                    self.startPage('shortName');
                    self.stopRequest(true);
                } else if (newValue == 'Ac') {  // mode シフト表示 //Hiển thị shift    
                    self.saveModeGridToLocalStorege('Ac');
                    self.visibleShiftPalette(false);
                    self.startPage('time');
                    self.stopRequest(true);
                }
            });
            
            
            self.backgroundColorSelected.subscribe((value) => {
                let self = this;
                uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                    let userInfor = JSON.parse(data);
                    userInfor.backgroundColor = value;
                    uk.localStorage.setItemAsJson(self.KEY, userInfor);
                });
            });

            
            self.stopRequest.subscribe(function(value) {
                if (!value) {
                    nts.uk.ui.block.grayout();
                } else {
                    nts.uk.ui.block.clear();
                }
            });

            /**
             * close popup
             */
            $(".close-popup").click(function() {
                $('#popup-area8').css('display', 'none');
            });
            
        }
        // end constructor
        
        startPage(viewMode : string): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                param = {
                    workplaceId: 'workplaceId',
                    workplaceGroupId: 'workplaceGroupId'

                };
            service.getDataStartScreen(param).done((data : IDataStartScreen) => {
                
                // 1
                self.bindingToHeader(data);
                
                let dataBindGrid = self.convertDataToGrid(data,viewMode);
                
                self.initExTable(dataBindGrid, viewMode);
                
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }
        
        // binding ket qua cua <<ScreenQuery>> 初期起動の情報取得 
        private bindingToHeader(data : IDataStartScreen){
             let self = this;
                let dataBasic : IDataBasicDto = data.dataBasicDto;
            self.dtPrev(dataBasic.startDate);
            self.dtAft(dataBasic.endDate);
            self.targetOrganizationName(dataBasic.targetOrganizationName);
            __viewContext.viewModel.viewAC.workplaceModeName(dataBasic.designation);
        }
        
        // convert data lấy từ server để đẩy vào Grid
        private convertDataToGrid(data : IDataStartScreen, viewMode : string){
            let self = this;
            let result = {};
            let leftmostDs = [];
            let middleDs = [];
            let detailColumns = [];
            let objDetailHeaderDs = {};
            let detailHeaderDeco = [];
            let htmlToolTip = [];
            let detailContentDs = [];
            let objDetailContentDs = {};
            
            _.each(data.listEmpInfo, (emp: IEmpInfo, i) => {
                // set data to detailLeftmost
                let businessName = emp.businessName == null || emp.businessName == undefined ? '' : emp.businessName.trim();
                leftmostDs.push({ sid: emp.employeeId, codeNameOfEmp: emp.employeeCode + ' ' + businessName });
                
                // set data to detailContent
                objDetailContentDs['sid'] = i;
                _.each(data.listDateInfo, (dateInfo : IDateInfo) => {
                    let time = new Time(new Date(dateInfo.ymd));
                    let ymd = time.yearMonthDay;
                    objDetailContentDs['_' + ymd] = new ExCell("001", "出勤A" , "1", "通常８ｈ");
                });
                detailContentDs.push(objDetailContentDs);
            });
            
            if (data.displayControlPersonalCond == null) {
                // ẩn A9    
                $("#extable").exTable("hideMiddle");

            } else {
                $("#extable").exTable("showMiddle");
                _.each(data.listPersonalConditions, (personalCond : IPersonalConditions) => {
                    middleDs.push({ sid: personalCond.sid, team: personalCond.teamName, rank: personalCond.rankName, qualification: personalCond.licenseClassification });
                });
            }
            
            // set width cho column cho tung mode
            let widthColumn = viewMode === 'time' ? 150 : 75;
            
            detailColumns.push({ key: "sid", width: "5px", headerText: "ABC", visible: false });
            objDetailHeaderDs['sid'] = "";
            _.each(data.listDateInfo, (dateInfo : IDateInfo) => {
                self.arrDay.push(new Time(new Date(dateInfo.ymd)));
                let time = new Time(new Date(dateInfo.ymd));
                detailColumns.push({
                    key: "_" + time.yearMonthDay, width: widthColumn +"px", handlerType: "input", dataType: "label/label/time/time", headerControl: "link"
                });
                let ymd = time.yearMonthDay;
                let field = '_' + ymd;
                if (dateInfo.isToday) {
                    detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-that-day"));
                    detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-that-day"));
                } else if (dateInfo.isSpecificDay) {
                    detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-specific-date"));
                    detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-specific-date"));
                } else if (dateInfo.isHoliday) {
                    detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-sunday"));
                    detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-sunday"));
                } else if (dateInfo.dayOfWeek == 7) {
                    detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-sunday"));
                    detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-sunday"));
                } else if (dateInfo.dayOfWeek == 6) {
                    detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-saturday"));
                    detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-saturday"));
                } else if (dateInfo.dayOfWeek > 0 || dateInfo.dayOfWeek < 6) {
                    detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-weekdays"));
                    detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-weekdays"));
                }
                
                if(dateInfo.isSpecificDay || dateInfo.optCompanyEventName != null || dateInfo.optWorkplaceEventName != null){
                    objDetailHeaderDs['_' + ymd] = "<div class='header-image-event'></div>";
                    let heightToolTip = 22 + 22 + 22*dateInfo.listSpecDayNameCompany.length + 22*dateInfo.listSpecDayNameWorkplace.length + 5 ; //22 là chiều cao 1 row của table trong tooltip
                    htmlToolTip.push(new HtmlToolTip('_' + ymd , dateInfo.htmlTooltip, heightToolTip ));
                }else{
                    objDetailHeaderDs['_' + ymd] = "<div class='header-image-no-event'></div>";
                }
            });
            self.listColorOfHeader(detailHeaderDeco);

            result = {
               leftmostDs : leftmostDs,
               middleDs   : middleDs,
               detailColumns: detailColumns,
               objDetailHeaderDs: objDetailHeaderDs,
               detailHeaderDeco: detailHeaderDeco,
               htmlToolTip: htmlToolTip,
               detailContentDs: detailContentDs
               
            };
            return result;
        }
        
        
        reloadScreen(){
           let self = this;
           self.startKSU001(); 
        }
        
        saveModeGridToLocalStorege(mode) {
            let self = this;
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                userInfor.disPlayFormat = mode;
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
        }
        
        /**
         * get setting ban dau
         */
        getSettingDisplayWhenStart() {
            let self = this;
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                
                // A4_4 表示形式の初期選択と画面モード (Chọn default của các hình thức hiển thị và mode màn hình)
                if (userInfor.disPlayFormat == '') {
                    self.selectedModeDisplayInBody('Ac');
                    self.visibleShiftPalette(false);
                } else {
                    if (userInfor.disPlayFormat == 'Aa') {
                        self.selectedModeDisplayInBody('Aa');
                        self.visibleShiftPalette(true);
                    } else if (userInfor.disPlayFormat == 'Ab') {
                        self.selectedModeDisplayInBody('Ab');
                        self.visibleShiftPalette(false);
                    } else if (userInfor.disPlayFormat == 'Ac') {
                        self.selectedModeDisplayInBody('Ac');
                        self.visibleShiftPalette(false);
                    }
                }
                
                // A4_12 背景色の初期選択   (Chọn default màu nền)
                if (userInfor.backgroundColor == '') {
                    self.backgroundColorSelected(0);
                } else {
                    self.backgroundColorSelected(1);
                }
                
                // get setting height grid
                if (userInfor.gridHeightSelection == 1) {
                    self.selectedTypeHeightExTable(1);
                    self.isEnableInputHeight(false);
                } else {
                    self.heightGridSetting(userInfor.heightGridSetting);
                    self.selectedTypeHeightExTable(2);
                    self.isEnableInputHeight(true);
                }
                
                // get setting shiftPalletUnit selected
                __viewContext.viewModel.viewAC.selectedpalletUnit(userInfor.shiftPalletUnit);
                
            });
        }

        /**
        * Create exTable
        */
        initExTable(dataBindGrid: any, viewMode : string): void {
            let self = this,
                //Get dates in time period
                currentDay = new Date(),
                bodyHeightMode = "dynamic",
                windowXOccupation = 65,
                windowYOccupation = 328;

            // phần leftMost

            let leftmostColumns = [];
            let leftmostHeader = {};
            let leftmostContent = {};
            let leftmostDs = dataBindGrid.leftmostDs;

            leftmostColumns = [{
                key: "codeNameOfEmp", headerText: getText("KSU001_56"), width: "160px", icon: { for: "body", class: "icon-leftmost", width: "25px" },
                css: { whiteSpace: "pre" }, control: "link", handler: function(rData, rowIdx, key) { console.log(rowIdx); },
                headerControl: "link", headerHandler: function() { alert("Link!"); }
            }];
            
            leftmostHeader = {
                columns: leftmostColumns,
                rowHeight: "60px",
                width: "160px"
            };

            leftmostContent = {
                columns: leftmostColumns,
                dataSource: leftmostDs,
                primaryKey: "sid"
            };

            // Phần middle
            let middleDs = dataBindGrid.middleDs;
            let updateMiddleDs = [];
            let middleColumns = [];
            let middleContentDeco = [];
            let middleHeader = {};
            let middleContent = {};

            middleColumns = [
                { headerText: getText("KSU001_4023"), key: "team", width: "40px", css: { whiteSpace: "none" } },
                { headerText: getText("KSU001_4024"), key: "rank", width: "40px", css: { whiteSpace: "none" } },
                { headerText: getText("KSU001_4025"), key: "qualification", width: "40px", css: { whiteSpace: "none" } }
            ];

            middleHeader = {
                columns: middleColumns,
                width: "120px",
                features: [{
                    name: "HeaderRowHeight",
                    rows: { 0: "60px" }
                }]
            };

            middleContent = {
                columns: middleColumns,
                dataSource: middleDs,
                primaryKey: "sid",
                features: [{
                    name: "BodyCellStyle",
                    decorator: middleContentDeco
                }]
            };

            // Phần detail
            let detailHeaderDeco = dataBindGrid.detailHeaderDeco;
            let detailHeaderDs = [];
            let detailContentDeco = [];
            let detailContentDs = dataBindGrid.detailContentDs;
            let detailColumns = dataBindGrid.detailColumns;
            let objDetailHeaderDs = dataBindGrid.objDetailHeaderDs;
            let htmlToolTip = dataBindGrid.htmlToolTip;

            let timeRanges = [];

            //create dataSource for detailHeader
            detailHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));
            detailHeaderDs.push(objDetailHeaderDs);
            

            let detailHeader = {
                columns: detailColumns,
                dataSource: detailHeaderDs,
                width: "700px",
                features: [{
                    name: "HeaderRowHeight",
                    rows: { 0: "40px", 1: "20px" }
                }, {
                        name: "HeaderCellStyle",
                        decorator: detailHeaderDeco
                    }, {
                        name: "ColumnResizes"
                    }, {
                    }, {
                        name: "Hover",
                        selector: ".header-image-event",
                        enter: function(ui) {
                            if (ui.rowIdx === 1 && $(ui.target).is(".header-image-event")) {
                                let objTooltip    = _.filter(htmlToolTip, function(o) { return o.key == ui.columnKey; });
                                if(objTooltip.length > 0){
                                    let heightToolTip = objTooltip[0].heightToolTip;
                                    ui.tooltip("show", $("<div/>").css({ width: "315px", height: heightToolTip +"px" }).html(objTooltip[0].value));
                                }else{
                                    ui.tooltip("show", $("<div/>").css({ width: "60px", height: 60 +"px" }).html(''));
                                }
                            }
                        },
                        exit: function(ui) {
                            ui.tooltip("hide");
                        }
                    }, {
                        name: "Click",
                        handler: function(ui) {
                            console.log(`${ui.rowIdx}-${ui.columnKey}`);
                        }
                    }]
            };
            
            let detailContent = {
                columns: detailColumns,
                dataSource: detailContentDs,
                primaryKey: "sid",
                //        highlight: false,
                features: [{
                    name: "BodyCellStyle",
                    decorator: detailContentDeco
                }, {
                        name: "TimeRange",
                        ranges: timeRanges
                    }, {
                        name: "RightClick",
                        handler: function(ui) {
                            let items = [
                                { id: "日付別", text: "日付別", selectHandler: function(id) { alert(id); }, icon: "ui-icon ui-icon-calendar" },
                                { id: "partition" },
                                { id: "シフト別", text: "シフト別", selectHandler: function(id) { alert(id); }, icon: "ui-icon ui-icon-star" }
                            ];

                            ui.contextMenu(items);
                            ui.contextMenu("show");
                        }
                    }],
                view: function(mode) {
                    switch (mode) {
                        case "symbol":
                            return ["symbol"];
                        case "shortName":
                            return ["workTypeName", "workTimeName"];
                        case "time":
                            return ["workTypeName", "workTimeName", "startTime", "endTime"];
                    }
                },
                fields: ["workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "symbol", "startTime", "endTime"],
                //        banEmptyInput: [ "time" ]
            };

            let start = performance.now();

            new nts.uk.ui.exTable.ExTable($("#extable"), {
                headerHeight: "60px",
                bodyRowHeight: "50px",
                bodyHeight: "500px",
                horizontalSumHeaderHeight: "0px",
                horizontalSumBodyHeight: "0px",
                horizontalSumBodyRowHeight: "0px",
                areaResize: true,
                bodyHeightMode: bodyHeightMode,
                windowXOccupation: windowXOccupation,
                windowYOccupation: windowYOccupation,
                manipulatorId: "6",
                manipulatorKey: "sid",
                updateMode: "edit",
                pasteOverWrite: true,
                stickOverWrite: true,
                viewMode: viewMode,
                showTooltipIfOverflow: true,
                errorMessagePopup: true,
                determination: {
                    rows: [0],
                    columns: ["codeNameOfEmp"]
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
                .create();

            $("#extable").exTable("scrollBack", 0, { h: 1050 });

            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                if (userInfor.gridHeightSelection == 2) {
                    $("#extable").exTable("setHeight", userInfor.heightGridSetting);
                    let heightBodySetting: number = + userInfor.heightGridSetting;
                    let heightBody = heightBodySetting + 60 - 25; // 60 chieu cao header, 25 chieu cao button
                    $(".toDown").css({ "margin-top": heightBody + 'px' });
                } else {
                    self.setPositionButonDownAndHeightGrid();
                }
            });

            console.log(performance.now() - start);
        }
        
        updateExTable(dataBindGrid: any, viewMode : string): void {
            let self = this;
            // save scroll's position
            $("#extable").exTable("saveScroll");
            self.stopRequest(false);

            let newLeftMostDs = [], newMiddleDs = [], newDetailContentDs = [], 
                newDetailHeaderDs = [], newObjDetailHeaderDs = [], newVertSumContentDs = [], newLeftHorzContentDs = [];
            
        
        }
        
        /**
         * Set color for cell header: 日付セル背景色文字色制御
         * 
         */
        setColorForCellHeaderDetailAndHoz(detailHeaderDeco: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();

            if (self.isClickChangeDisplayMode) {
                self.isClickChangeDisplayMode = false;
                _.map(self.listColorOfHeader(), (item) => {
                    detailHeaderDeco.push(item);
                });
                dfd.resolve();
                return;
            }
            // getDataSpecDateAndHoliday always query to server
            // because date is changed when click nextMonth or backMonth
            $.when(self.getDataSpecDateAndHoliday()).done(() => {
                _.each(self.arrDay, (date) => {
                    let ymd = date.yearMonthDay;
                    let dateFormat = moment(date.yearMonthDay).format('YYYY/MM/DD');
                    if (_.includes(self.dataWkpSpecificDate(), dateFormat) || _.includes(self.dataComSpecificDate(), dateFormat)) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-specific-date"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-specific-date"));
                    } else if (_.includes(self.dataPublicHoliday(), dateFormat)) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-sunday color-schedule-sunday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-sunday color-schedule-sunday"));
                    } else if (date.weekDay === '土') {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-saturday color-schedule-saturday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-saturday color-schedule-saturday"));
                    } else if (date.weekDay === '日') {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-sunday color-schedule-sunday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-sunday color-schedule-sunday"));
                    } else {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-weekdays color-weekdays"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-weekdays color-weekdays"));
                    }
                });

                // set class bg-schedule-that-day for currentDay
                if (self.dateTimePrev() <= moment().format('YYYY/MM/DD') && moment().format('YYYY/MM/DD') <= self.dateTimeAfter()) {
                    let arrCellColorFilter = _.filter(detailHeaderDeco, ['columnKey', "_" + moment().format('YYYYMMDD')]);
                    _.map(arrCellColorFilter, (cellColor: any) => {
                        cellColor.clazz = cellColor.clazz.replace(/bg-\w*/g, 'bg-schedule-that-day');
                    });
                }

                self.listColorOfHeader(detailHeaderDeco);
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /**
         * Get data WkpSpecificDate, ComSpecificDate, PublicHoliday
         */
        getDataSpecDateAndHoliday(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                obj = {
                    workplaceId: 'dea95de1-a462-4028-ad3a-d68b8f180412', //self.empItems()[0] ? self.empItems()[0].affiliationId : null,
                    startDate: self.dtPrev(),
                    endDate: self.dtAft()
                };
            service.getDataSpecDateAndHoliday(obj).done(function(data) {
                self.dataWkpSpecificDate(data.listWkpSpecificDate);
                self.dataComSpecificDate(data.listComSpecificDate);
                self.dataPublicHoliday(data.listPublicHoliday);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }
        

        // save setting hight cua grid vao localStorage
        saveHeightGridToLocal() {
            let self = this;
            
            $('#input-heightExtable').trigger("validate");
            if(nts.uk.ui.errors.hasError())
            return;
            
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                userInfor.gridHeightSelection = self.selectedTypeHeightExTable();
                if (self.selectedTypeHeightExTable() == TypeHeightExTable.DEFAULT) {
                    userInfor.heightGridSetting = '';
                } else if (self.selectedTypeHeightExTable() == TypeHeightExTable.SETTING) {
                    userInfor.heightGridSetting = self.heightGridSetting();
                }
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
                        
            $('#A16').ntsPopup('hide');
        }

        // xử lý cho button A13
        toLeft() {
            let self = this;
            if(self.indexBtnToLeft % 2 == 0){
                $("#extable").exTable("hideMiddle");
                $(".toLeft").css("background", "url(../image/toright.png) no-repeat center");
                $(".toLeft").css("margin-left", "185px");
                
            }else{
                $("#extable").exTable("showMiddle");
                $(".toLeft").css("background", "url(../image/toleft.png) no-repeat center");
                $(".toLeft").css("margin-left", "310px");
                
            }
            self.indexBtnToLeft = self.indexBtnToLeft + 1;
             
        }
        
        toRight() {
            let self = this;
            if(self.indexBtnToRight % 2 == 0){
                $(".toRight").css("background", "url(../image/toleft.png) no-repeat center");
                
            }else{
                $(".toRight").css("background", "url(../image/toright.png) no-repeat center");
            }
            self.indexBtnToRight = self.indexBtnToRight + 1; 
        }
        
        toDown() {
            let self = this;
            if(self.indexBtnToDown % 2 == 0){
                $(".toDown").css("background", "url(../image/toup.png) no-repeat center");
                
            }else{
                $(".toDown").css("background", "url(../image/todown.png) no-repeat center");
            }
            self.indexBtnToDown = self.indexBtnToDown + 1; 
        }
        
        
        setPositionButonDownAndHeightGrid(){
            let self = this;

            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                if (userInfor.gridHeightSelection == 2) {
                    $("#extable").exTable("setHeight", userInfor.heightGridSetting);
                    let heightBodySetting: number = + userInfor.heightGridSetting;
                    let heightBody = heightBodySetting + 60 - 25; // 60 chieu cao header, 25 chieu cao button
                    $(".toDown").css({ "margin-top": heightBody + 'px' });
                } else {
                    let heightExtable = $("#extable").height();
                    let margintop = heightExtable - 52;
                    $(".toDown").css({ "margin-top": margintop + 'px' });
                }
            });
        }

        setWidth(): any {
            $(".ex-header-detail").width(window.innerWidth - 572);
            $(".ex-body-detail").width(window.innerWidth - 554);
            $("#extable").width(window.innerWidth - 554);
        }

        /**
         *  update extable
         *  create new dataSource for some part of exTable
         *  set color for extable
         */
        updateExTable(): void {
            let self = this;
        }

        /**
         * update new data of header and content of detail and horizSum
         */
        updateWhenChangeDatePeriod(): void {
            let self = this;
            // save scroll's position
            $("#extable").exTable("saveScroll");
            //Get dates in time period
            let currentDay = new Date(self.dtPrev().toString());
            self.arrDay = [];
            let newDetailColumns = [], newObjDetailHeaderDs = [], newDetailHeaderDs = [], newDetailContentDs = [];
            
            newDetailColumns.push({ key: "sid", width: "50px", headerText: "sid", visible: false });
            while (moment(currentDay).format('YYYY-MM-DD') <= moment(self.dtAft()).format('YYYY-MM-DD')) {
                self.arrDay.push(new Time(currentDay));
                let time = new Time(currentDay);
                //define the new detailColumns
                newDetailColumns.push({
                    key: "_" + time.yearMonthDay, width: "50px", handlerType: "input", dataType: "label/label/time/time", visible: true
                });
                //create new detailHeaderDs
                newObjDetailHeaderDs['_' + time.yearMonthDay] = '';

                currentDay.setDate(currentDay.getDate() + 1);
            }
            
            //create new detailHeaderDs
            newDetailHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));
            newDetailHeaderDs.push(newObjDetailHeaderDs);
            
            let detailHeaderDeco = [], detailContentDeco = [];
            
             //if haven't data in extable, only update header detail
            if (self.empItems().length == 0) {
                $.when(self.setColorForCellHeaderDetailAndHoz(detailHeaderDeco)).done(() => {
                    let updateDetailHeader = {
                        columns: newDetailColumns,
                        dataSource: newDetailHeaderDs,
                        features: [{
                            name: "HeaderRowHeight",
                            rows: { 0: "40px", 1: "20px" }
                        }, {
                                name: "HeaderCellStyle",
                                decorator: detailHeaderDeco
                            }, {
                                name: "HeaderPopups",
                                menu: {
                                    rows: [0],
                                    items: [
                                        { id: "日付別", text: getText("KSU001_325"), selectHandler: function(id) { alert('Open KSU003'); } },
                                        { id: "シフト別", text: getText("KSU001_326"), selectHandler: function(id) { alert('Open KSC003'); } }
                                    ]
                                },
                            }]
                    };

                    $("#extable").exTable("updateTable", "detail", updateDetailHeader, {});

                    setTimeout(function() { $("#extable").exTable("scrollBack", 2); }, 1000);

                    //set lock cell
                    _.forEach(self.dataSource(), (x) => {
                        if (x.confirmedAtr == 1) {
                            $("#extable").exTable("lockCell", x.employeeId, "_" + moment(x.date, 'YYYY/MM/DD').format('YYYYMMDD'));
                        }
                    });
                    // get cell is locked
                    self.arrLockCellInit($("#extable").exTable("lockCells"));
                    self.stopRequest(true);
                });
            } else if (self.selectedModeDisplayInBody () == 'Ac') {
                

            } else if (self.selectedModeDisplay() == 'Ab') {


            } else if (self.selectedModeDisplay() == 'Aa') {


            }
        }
        


        /**
         * Get data of Basic Schedule = listDataShortName + listDataTimeZone + dataWScheState
         */
        getDataBasicSchedule(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                obj = {
                    employeeId: self.listSid(),
                    startDate: self.dtPrev(),
                    endDate: self.dtAft()
                };
        }

        /**
         * Get data to display symbol for dataSource(exTable)
         */
        setDataToDisplaySymbol(dataS): void {
            let self = this;
        }

        handleSetSymbolForCell(item: any): void {
            let self = this;
        }

        /**
        * datasource = dataBasicSchedule + dataToDisplaySymbol
        */
        setDatasource(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $.when(self.getDataBasicSchedule()).done(function() {
                // set data hien thi o mode symbol
                self.setDataToDisplaySymbol(self.dataSource())
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * Get data WorkScheduleState
         * with itemId = 1~4 (set on server)
         */
        getDataWorkScheduleState(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                obj = {
                    employeeId: self.listSid(),
                    startDate: self.dtPrev(),
                    endDate: self.dtAft(),
                };
            service.getDataWorkScheduleState(obj).done(function(data) {
                self.dataWScheduleState(data);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * Get data WorkEmpCombine
         */
        getDataWorkEmpCombine(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(), lstWorkTypeCode: any[] = [], lstWorkTimeCode: any[] = [], obj: any = null;
            _.each(__viewContext.viewModel.viewAB.listWorkType(), (item) => {
                lstWorkTypeCode.push(item.workTypeCode);
            });
            _.each(__viewContext.viewModel.viewAB.listWorkTime(), (item) => {
                lstWorkTimeCode.push(item.workTimeCode);
            });

            obj = {
                lstWorkTypeCode: lstWorkTypeCode,
                lstWorkTimeCode: lstWorkTimeCode
            }
            service.getDataWorkEmpCombine(obj).done(function(data) {
                self.dataWorkEmpCombine(data);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * Get data of Schedule Display Control
         */
        getDataScheduleDisplayControl(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.getDataScheduleDisplayControl().done(function(data) {
                self.dataScheduleDisplayControl(data);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
        * next a month
        */
        nextMonth(): void {
            let self = this;
            self.stopRequest(false);
            if (self.selectedModeDisplay() != 2) {
                let dtMoment = moment(self.dtAft());
                dtMoment.add(1, 'days');
                self.dtPrev(dtMoment.toDate());
                dtMoment = dtMoment.add(1, 'months');
                dtMoment.subtract(1, 'days');
                self.dtAft(dtMoment.toDate());
                self.dataSource([]);
                self.updateWhenChangeDatePeriod();
            } else {
                // hoi lai da
            }
            self.stopRequest(true);
        }

        /**
        * come back a month
        */
        backMonth(): void {
            let self = this;
            self.stopRequest(false);
            //Recalculate the time period
            if (self.selectedModeDisplay() != 2) {
                let dtMoment = moment(self.dtPrev());
                dtMoment.subtract(1, 'days');
                self.dtAft(dtMoment.toDate());
                if (dtMoment.date() === dtMoment.daysInMonth()) {
                    dtMoment = dtMoment.subtract(1, 'months');
                    dtMoment.endOf('months');
                } else {
                    dtMoment = dtMoment.subtract(1, 'months');
                }
                dtMoment.add(1, 'days');
                self.dtPrev(new Date(dtMoment.format('YYYY/MM/DD')));
                self.dataSource([]);
                self.updateWhenChangeDatePeriod();
            }else{
                   // hoi lai da
            }
            
            self.stopRequest(true);
        }

        /**
         * call <<ScreenQuery>> 表示期間を変更する（シフト）
         */
        changeDisplayPeriodShift(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                obj = {
                    startDate: self.dtPrev(),
                    endDate: self.dtAft(),
                    days28: self.selectedModeDisplay() == 2 ? true : false,
                    isNextMonth: isNextMonth

                };
            service.getSendingPeriod().done((data) => {
                self.dtAft(data.endDate);
                self.dtPrev(data.startDate);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
        * Save data
        */
        saveData(): void {
            let self = this;
        }

        /**
         * Set color for cell = set text color + set background color
         */
        setColorForCell(detailContentDeco: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
        }


        /**
         * Set color for cell of leftmost : 個人名セルの背景色の判断処理
         */
        setColorForLeftmostContent(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            //            $.when(self.getDataScheduleDisplayControl()).done(() => {
            if (self.isInsuranceStatus) {
                //TO-DO    
            }
            dfd.resolve();
            //            });
            return dfd.promise();
        }

        /**
         * Set color for cell : 明細セル背景色の判断処理
         */
        setColor(detailHeaderDeco: any, detailContentDeco: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $.when(self.setColorForCellHeaderDetailAndHoz(detailHeaderDeco),
                self.setColorForCell(detailContentDeco), self.setColorForLeftmostContent()).done(() => {
                    dfd.resolve();
                });
            return dfd.promise();
        }

        /**
         * Set error
         */
        addListError(errorsRequest: Array<string>) {
            let errors = [];
        }
        
        editMode() {
            let self = this;
            nts.uk.ui.block.grayout();
            // set color button
            $(".editMode").css({"background-color" : "#007fff" , "color" : "#ffffff"});
            $(".confirmMode").css({"background-color" : "#ffffff" , "color" : "#000000"});
            nts.uk.ui.block.clear();
        }

        confirmMode() {
            let self = this;
            nts.uk.ui.block.grayout();
            // set color button
            $(".confirmMode").css({"background-color" : "#007fff", "color" : "#ffffff"});
            $(".editMode").css({"background-color" : "#ffffff" , "color" : "#000000"});
            nts.uk.ui.block.clear();
        }

        /**
         * paste data on cell
         */
        pasteData(): void {
            let self = this;
            // set color button
            nts.uk.ui.block.grayout();
            $("#paste").css({"background-color" : "#007fff", "color" : "#ffffff"});
            $("#copy").css({"background-color" : "#ffffff" , "color" : "#000000" });
            $("#input").css({"background-color" : "#ffffff" , "color" : "#000000" });
            nts.uk.ui.block.clear();
        }

        /**
         * copy data on cell
         */
        copyData(): void {
            // set color button
            nts.uk.ui.block.grayout();
            $("#copy").css({"background-color" : "#007fff", "color" : "#ffffff"});
            $("#paste").css({"background-color" : "#ffffff" , "color" : "#000000" });
            $("#input").css({"background-color" : "#ffffff" , "color" : "#000000" });
            nts.uk.ui.block.clear();
            
        }
        
        /**
         * copy data on cell
         */
        inputData(): void {
            // set color button
            nts.uk.ui.block.grayout();
            $("#input").css({"background-color" : "#007fff", "color" : "#ffffff"});
            $("#copy").css({"background-color" : "#ffffff" , "color" : "#000000" });
            $("#paste").css({"background-color" : "#ffffff" , "color" : "#000000" });
            nts.uk.ui.block.clear();
        }

        /**
         * undo data on cell
         */
        undoData(): void {
            $("#extable").exTable("stickUndo");
        }
        
        /**
         * redo data on cell
         */
        redoData(): void {
            $("#extable").exTable("stickRedo");
        }

        /**
         * get data form COM_PATTERN (for screen Q)
         */
        getDataComPattern(selectedLinkButton): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getDataComPattern().done((data) => {
                __viewContext.viewModel.viewAC.listComPattern(data);
                __viewContext.viewModel.viewAC.handleInitCom(
                        data,
                        __viewContext.viewModel.viewAC.textButtonArrComPattern, 
                        __viewContext.viewModel.viewAC.dataSourceCompany, 
                        selectedLinkButton == null ||  selectedLinkButton == undefined ? ko.observable(0) : selectedLinkButton );
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });

            return dfd.promise();
        }

        /**
         * get data form WKP_PATTERN (for screen Q)
         */
        getDataWkpPattern(selectedLinkButton): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let obj: string = 'dea95de1-a462-4028-ad3a-d68b8f180412';  //self.empItems()[0] ? self.empItems()[0].affiliationId : '';
            
           // let workplaceId : 'dea95de1-a462-4028-ad3a-d68b8f180412'; 
            service.getDataWkpPattern(obj).done((data) => {
                __viewContext.viewModel.viewAC.listWkpPattern(data);
                __viewContext.viewModel.viewAC.handleInitWkp(
                        data, 
                        __viewContext.viewModel.viewAC.textButtonArrComPattern, 
                        __viewContext.viewModel.viewAC.dataSourceCompany,  
                        selectedLinkButton == null ||  selectedLinkButton == undefined ? ko.observable(0) : selectedLinkButton );
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });

            return dfd.promise();
        }


        /**
         * open dialog D
         */
        openDialogD(): void {
            let self = this;
            setShared('dataForScreenD', {
                dataSource: self.dataSource(),
                empItems: self.empItems(),
                startDate: moment(self.dtPrev()).format('YYYY/MM/DD'),
                endDate: moment(self.dtAft()).format('YYYY/MM/DD'),
                // in phare 2, permissionHandCorrection allow false
                permissionHandCorrection: false,
                listColorOfHeader: self.listColorOfHeader()
            });
            
            nts.uk.ui.windows.sub.modal("/view/ksu/001/d/index.xhtml").onClosed(() => {
                if (getShared("dataFromScreenD") && !getShared("dataFromScreenD").clickCloseDialog) {
                    self.dataSource([]);
                    self.updateWhenChangeDatePeriod();
//                    $.when(self.setDatasource()).done(() => {
//                        self.updateExTable();
//                    });
                }
            });
        }

        /**
         * open dialog L
         */
        openDialogL(): void {
            let self = this;
            $('#popup-area5').ntsPopup('hide');
            //hiện giờ truyền sang workplaceId va tất cả emmployee . Sau này sửa truyền list employee theo workplace id
            setShared("dataForScreenL", {
                workplaceId: self.empItems()[0] ? self.empItems()[0].affiliationId : null,
                empItems: self.empItems()
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/l/index.xhtml");
        }

        /**
         * open dialog N
         */
        openDialogN(): void {
            let self = this;
            $('#popup-area5').ntsPopup('hide');
            nts.uk.ui.windows.setShared("listEmployee", self.empItems());
            nts.uk.ui.windows.sub.modal("/view/ksu/001/n/index.xhtml");
        }

        /**
         * go to screen KML004
         */
        gotoKml004(): void {
            nts.uk.ui.windows.sub.modal("/view/kml/004/a/index.xhtml");
        }

        /**
        * go to screen KML002
        */
        gotoKml002(): void {
            nts.uk.request.jump("/view/kml/002/h/index.xhtml");
        }
        
         /**
         * Open dialog CDL027
         */
        openCDL027(): void {
            let self = this,
                period = {
                    startDate : self.dateTimePrev(),
                    endDate : self.dateTimeAfter()  
                },
                param = {
                    pgid: __viewContext.program.programId,
                    functionId: 1,
                    listEmployeeId: self.listSid(),
                    period : period,
                    displayFormat: self.displayFormat() 
                };
            setShared("CDL027Params", param);
            openDialog('com',"/view/cdl/027/a/index.xhtml");
        }
        
        compareArrByRowIndexAndColumnKey(a: any, b: any): any {
            return a.rowIndex == b.rowIndex && a.comlumnKey == b.comlumnKey;
        }
    }

    export enum viewMode {
        SYMBOL    = 1,
        SHORTNAME   = 2,
        TIME  = 3
    } 
    
    export enum TypeHeightExTable {
        DEFAULT    = 1,
        SETTING   = 2
    }
    
    class ExItem {
        sid: string;
        empName: string;

        constructor(sid: string, dsOfSid: BasicSchedule[], listWorkType: ksu001.common.modelgrid.WorkType[], listWorkTime: ksu001.common.modelgrid.WorkTime[], manual: boolean, arrDay: Time[]) {
            this.sid = sid;
            this.empName = sid;
            // create detailHeader (ex: 4/1 | 4/2)
            if (manual) {
                for (let i = 0; i < arrDay.length; i++) {
                    if (+arrDay[i].day == 1) {
                        this['_' + arrDay[i].yearMonthDay] = arrDay[i].month + '/' + arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                    } else {
                        this['_' + arrDay[i].yearMonthDay] = arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                    }
                }
                return;
            }
        }
    }
    
    interface IBasicSchedule {
        date: string,
        employeeId: string,
        workTimeCode?: string,
        workTypeCode?: string,
        confirmedAtr?: number,
        isIntendedData?: boolean,
        scheduleCnt?: number,
        scheduleStartClock?: number,
        scheduleEndClock?: number,
        bounceAtr?: number,
        symbolName?: string
    }

    class BasicSchedule {
        date: string;
        employeeId: string;
        workTimeCode: string;
        workTypeCode: string;
        confirmedAtr: number;
        isIntendedData: boolean;
        scheduleCnt: number;
        scheduleStartClock: number;
        scheduleEndClock: number;
        bounceAtr: number;
        symbolName: string;

        constructor(params: IBasicSchedule) {
            this.date = params.date;
            this.employeeId = params.employeeId;
            this.workTimeCode = params.workTimeCode;
            this.workTypeCode = params.workTypeCode;
            this.confirmedAtr = params.confirmedAtr;
            this.isIntendedData = params.isIntendedData;
            this.scheduleCnt = params.scheduleCnt;
            this.scheduleStartClock = params.scheduleStartClock;
            this.scheduleEndClock = params.scheduleEndClock;
            this.bounceAtr = params.bounceAtr;
            this.symbolName = params.symbolName;
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
    
    interface IPersonModel {
        employeeId: string,
        employeeCode: string,
        employeeName: string,
        workplaceId: string,
        wokplaceCode: string,
        workplaceName: string,
        baseDate?: number
    }

    class PersonModel {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        wokplaceCode: string;
        workplaceId: string;
        workplaceName: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.employeeId = param.employeeId;
            this.employeeCode = param.employeeCode;
            this.employeeName = param.employeeName;
            this.wokplaceCode = param.wokplaceCode;
            this.workplaceId = param.workplaceId;
            this.workplaceName = param.workplaceName;
            this.baseDate = param.baseDate;
        }
    }
    
       class Time {
        year: string;
        month: string;
        day: string;
        weekDay: string;
        yearMonthDay: string;

        constructor(ymd: Date) {
            this.year = moment(ymd).format('YYYY');
            this.month = moment(ymd).format('M');
            this.day = moment(ymd).format('D');
            this.weekDay = moment(ymd).format('dd');
            this.yearMonthDay = this.year + moment(ymd).format('MM') + moment(ymd).format('DD');
        }
    }

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


    interface IDataStartScreen { 
        dataBasicDto : IDataBasicDto,
        displayControlPersonalCond : IDisplayControlPersonalCond,
        listDateInfo : Array<IDateInfo>,
        listEmpInfo  : Array<IEmpInfo>,
        listPersonalConditions : Array<IPersonalConditions>
    }

    interface IDataBasicDto {
        startDate: string,
        endDate: string,
        designation: string,
        targetOrganizationName: string,
        unit: number,
        workplaceId: string,
        workplaceGroupId: string
    }

    interface IDisplayControlPersonalCond {
        companyID: string,
        listQualificationCD: string,
        qualificationMark: string,
        listConditionDisplayControl: Array<IConditionDisplayControl>
    }

    interface IConditionDisplayControl {
        conditionATR: number, 
        displayCategory: number       
    }

    interface IDateInfo {
        ymd: string,
        dayOfWeek: number,
        isHoliday: boolean,
        isSpecificDay: boolean,
        optCompanyEventName : string,
        optWorkplaceEventName : string,
        listSpecDayNameCompany: Array<string>,
        listSpecDayNameWorkplace : Array<string>,
        isToday : boolean,
        htmlTooltip : string
    }
    
    interface IEmpInfo {
        employeeId: string,
        employeeCode: string,
        businessName: string
    }
    
    interface IPersonalConditions {
        sid: string,
        teamName: string,
        rankName: string,
        licenseClassification : string
    }
    
    class HtmlToolTip {
        key: string;
        value: string;
        heightToolTip : number;
        constructor(key : string, value: string, heightToolTip : number ) {
            this.key   = key;
            this.value = value;
            this.heightToolTip = heightToolTip;
        }
    }
    
    
}