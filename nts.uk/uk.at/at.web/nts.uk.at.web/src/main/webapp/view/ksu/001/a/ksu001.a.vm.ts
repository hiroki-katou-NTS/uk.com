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
        
        // popup-area6
        formatSelection: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, getText("KSU001_43")),
            new BoxModel(2, getText("KSU001_44")),
            new BoxModel(3, getText("KSU001_45")),
        ]);
        selectedFormat: any = ko.observable(1);
        
        displaySelection: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, getText("KSU001_135")),
            new BoxModel(2, getText("KSU001_136")),
        ]);
        selectedDisplay: any = ko.observable(1);

        backgroundColorSelList: KnockoutObservableArray<ItemModel> = ko.observableArray([
            new ItemModel('1', getText("KSU001_143")),
            new ItemModel('2', getText("KSU001_144"))
        ]);
        backgroundColorSelected: KnockoutObservable<string> = ko.observable('1');

        isEnableCompareMonth: KnockoutObservable<boolean> = ko.observable(true);

        popupVal: KnockoutObservable<string> = ko.observable('');
        selectedDate: KnockoutObservable<string> = ko.observable('1993/23/12');

        //Date time A3_1

        currentDate: Date = new Date();
        dtPrev: KnockoutObservable<Date> = ko.observable(null);
        dtAft: KnockoutObservable<Date> = ko.observable(null);
        dateTimePrev: KnockoutObservable<string>;
        dateTimeAfter: KnockoutObservable<string>;

        //Switch  A3_2
        modeDisplay: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText("KSU001_39") },
            { code: 2, name: getText("KSU001_40") },
            { code: 3, name: getText("KSU001_41") }    
        ]);
        selectedModeDisplay: KnockoutObservable<number> = ko.observable(1);
        
        // A2_2
        targetOrganizationName: KnockoutObservable<string> = ko.observable('開発本部　第一開発部　　　UK就業　チーム１0');
        
        // popup Setting Grid
        selectedTypeHeightExTable : KnockoutObservable<number> = ko.observable(1);
        heightGridSetting: KnockoutObservable<string> = ko.observable('');
        isEnableInputHeight :KnockoutObservable<boolean> = ko.observable(false);
        
        arrDay: Time[] = [];
        listSid: KnockoutObservableArray<string> = ko.observableArray([]);
        
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
        listColorOfHeader: KnockoutObservableArray<ksu001.common.modelgrid.CellColor> = ko.observableArray([]);
        flag: boolean = true;
        isClickChangeDisplayMode: boolean = false;
        stopRequest: KnockoutObservable<boolean> = ko.observable(true);
        arrLockCellInit: KnockoutObservableArray<Cell> = ko.observableArray([]);
        // 表示形式 ＝ 日付別(固定) = 0
        displayFormat: KnockoutObservable<number> = ko.observable(0);
        hasEmployee: KnockoutObservable<boolean> = ko.observable(false);
        
        indexBtnToLeft : number = 0;
        indexBtnToRight : number = 0;
           
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
                if(newValue == 1){
                    self.isEnableInputHeight(false);
                    self.heightGridSetting('');
                    $('#input-heightExtable').ntsError('clear');
                }else if(newValue == 2){
                    self.isEnableInputHeight(true);
                    setTimeout(() =>{
                        $('#input-heightExtable').focus();                        
                    }, 1);
                }
            });
            
            self.selectedDisplay.subscribe(function(newValue) {
                if (newValue == 1) {
                    self.isEnableCompareMonth(true);
                } else {
                    self.isEnableCompareMonth(false);
                }
            });

            self.selectedFormat.subscribe(function(newValue) {
                nts.uk.ui.errors.clearAll();
                __viewContext.viewModel.viewO.time1('');
                __viewContext.viewModel.viewO.time2('');
                
                self.stopRequest(false);
                // close screen O1 when change mode
                let currentScreen = __viewContext.viewModel.viewO.currentScreen;
                if (currentScreen) {
                    currentScreen.close();
                }
                
                let detailContentDeco: any[] = [];

                if (newValue == 1) {
                    $('#contain-view').show();
                    $('#contain-view').removeClass('h-90');
                    $('#group-bt').show();
                    $('#oViewModel').show();
                    $('#qViewModel').hide();
                    self.setColorForCell(detailContentDeco).done(() => {
                        $("#extable").exTable("mode", "shortName", "stick", { y: 210 }, [{
                            name: "BodyCellStyle",
                            decorator: detailContentDeco
                        }]);
                        self.stopRequest(true);
                    });
                    $("#extable").exTable("stickMode", "single");
                    $("#combo-box1").focus();
                    // get data to stickData
                    $("#extable").exTable("stickData", __viewContext.viewModel.viewO.nameWorkTimeType());
                } else if (newValue == 2) {
                    $('#contain-view').hide();
                    self.setColorForCell(detailContentDeco).done(() => {
                        $("#extable").exTable("mode", "time", "edit", { y: 150 }, [{
                            name: "BodyCellStyle",
                            decorator: detailContentDeco
                        }]);
                        self.stopRequest(true);
                    });
                } else {
                    $('#contain-view').show();
                    $('#contain-view').addClass('h-90');
                    $('#oViewModel').hide();
                    $('#qViewModel').show();
                    $('#group-bt').show();
                    self.setColorForCell(detailContentDeco).done(() => {
                        $("#extable").exTable("mode", "symbol", "stick", { y: 245 }, [{
                            name: "BodyCellStyle",
                            decorator: detailContentDeco
                        }]);
                        self.stopRequest(true);
                    });
                    $("#extable").exTable("stickMode", "multi");
                    $("#tab-panel").focus();
                    // get data to stickData
                    // if buttonTable not selected, set stickData is null
                    if ((__viewContext.viewModel.viewQ.selectedTab() == 'company' && $("#test1").ntsButtonTable("getSelectedCells")[0] == undefined)
                        || (__viewContext.viewModel.viewQ.selectedTab() == 'workplace' && $("#test2").ntsButtonTable("getSelectedCells")[0] == undefined)) {
                        $("#extable").exTable("stickData", null);
                    } else if (__viewContext.viewModel.viewQ.selectedTab() == 'company') {
                        let dataToStick = _.map($("#test1").ntsButtonTable("getSelectedCells")[0].data.data, 'data');
                        $("#extable").exTable("stickData", dataToStick);
                    } else if (__viewContext.viewModel.viewQ.selectedTab() == 'workplace') {
                        let dataToStick = _.map($("#test2").ntsButtonTable("getSelectedCells")[0].data.data, 'data');
                        $("#extable").exTable("stickData", dataToStick);
                    }
                    // jump initScreenQ only once
                    if (self.flag) {
                        //select first link button
                        __viewContext.viewModel.viewQ.initScreenQ();
                        self.flag = false;
                    }
                }
            });
            // TODO - comment do chua lam phan du lieu thuc te
//            self.selectedModeDisplayObject.subscribe((newValue) => {
//                if (self.listSid().length > 0) {
//                    if (newValue == 2) {
//                        // actual data display mode 
//                        // (in phase 2 not done, so the actual data = intended data)
//                        // if actual data is null, display intended data
//                        self.setDatasource().done(function() {
//                            self.updateExTable();
//                        });
//                    } else {
//                        // intended data display mode 
//                        self.setDatasource().done(function() {
//                            self.updateExTable();
//                        });
//                    }
//                }
//            });

            self.backgroundColorSelected.subscribe((newValue) => {
                self.updateExTable();
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
            
            self.getSettingGrid();
        }
        
        getSettingGrid() {
            let self = this;
            // setting height grid
            var itemOpt = nts.uk.localStorage.getItem('HEIGHT_OF_GIRD');
            if (itemOpt.isPresent()) {
                let item = itemOpt.get();
                if (item == "null") {
                    self.selectedTypeHeightExTable(1);
                } else {
                    self.heightGridSetting(item);
                    self.selectedTypeHeightExTable(2);
                    self.isEnableInputHeight(true);
                }
            }
        }

        /**
         * get data for screen O, Q , A before bind
         */
        startKSU001(settingHeightGrid): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            // get data for screen O 
            // initScreen: get data workType-workTime for 2 combo-box of screen O
            // and startDate-endDate of screen A
            __viewContext.viewModel.viewO.initScreen().done(() => {
                self.getDataScheduleDisplayControl(); 
                self.getDataComPattern();
                self.getDataWkpPattern();
                self.dtPrev(moment.utc(__viewContext.viewModel.viewO.startDateScreenA, 'YYYY/MM/DD'));
                self.dtAft(moment.utc(__viewContext.viewModel.viewO.endDateScreenA, 'YYYY/MM/DD'));
                self.employeeIdLogin = __viewContext.viewModel.viewO.employeeIdLogin;
                // get state of list workTypeCode
                // get data for screen A
                let lstWorkTypeCode = [];
                _.map(__viewContext.viewModel.viewO.listWorkType(), (workType: nts.uk.at.view.ksu001.common.viewmodel.WorkType) => {
                    lstWorkTypeCode.push(workType.workTypeCode);
                });
                // get data for dialog C
                // self.initShiftCondition();
                // init and get data for screen A
                // checkNeededOfWorkTimeSetting(): get list state of workTypeCode relate to need of workTime
                self.listStateWorkTypeCode(__viewContext.viewModel.viewO.checkStateWorkTypeCode);
                self.listCheckNeededOfWorkTime(__viewContext.viewModel.viewO.checkNeededOfWorkTimeSetting);
                self.dataWorkEmpCombine(__viewContext.viewModel.viewO.workEmpCombines);

                self.initExTable(settingHeightGrid);
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * CCG001 return listEmployee
         * When listEmployee changed, call function updateExtable to refresh data of exTable
         */
        searchEmployee(dataEmployee: EmployeeSearchDto[]) {
            let self = this;
            self.stopRequest(false);

            self.empItems.removeAll();
            _.forEach(dataEmployee, function(item: EmployeeSearchDto) {
                self.empItems.push(new PersonModel({
                    empId: item.employeeId,
                    empCd: item.employeeCode,
                    empName: item.employeeName,
                    affiliationId: item.affiliationId,
                    affiliationCode: item.affiliationCode,
                    affiliationName: item.affiliationName,
                }));
            });
            self.affiliationId = self.empItems()[0].affiliationId;
            self.affiliationName(self.empItems()[0].affiliationName);
            // get data for listSid
            self.listSid([]);
            let arrSid: string[] = [];
            _.each(self.empItems(), (x) => {
                arrSid.push(x.empId);
            });
            self.listSid(arrSid);
            //getDataOfWorkPlace(): get workPlaceName to display A3-1
            //getDataWkpPattern() : get data WorkPattern for screen Q
            $.when(self.getDataWkpPattern()).done(() => {
                if (__viewContext.viewModel.viewQ.selectedTab() === 'workplace') {
                    __viewContext.viewModel.viewQ.initScreenQ();
                }

                if (self.selectedModeDisplayObject() == 1) {
                    // intended data display mode 
                    self.setDatasource().done(function() {
                        self.updateExTable();
                    });
                } else {
                    // actual data display mode 
                    // in phare 2, set actual data = intended data
                    self.setDatasource().done(function() {
                        self.updateExTable();
                    });
                }
            }).fail(() => { self.stopRequest(true); });
        }
        
        // save setting hight cua grid vao localStorage
        saveHeightGridToLocal() {
            let self = this;
            
            $('#input-heightExtable').trigger("validate");
            if(nts.uk.ui.errors.hasError())
            return;
            
            var item = nts.uk.localStorage.getItem('HEIGHT_OF_GIRD');
            if (item.isPresent()) {
                nts.uk.localStorage.removeItem('HEIGHT_OF_GIRD');   
            }
                        
            if(self.selectedTypeHeightExTable() == 1){
                nts.uk.localStorage.setItem('HEIGHT_OF_GIRD', null); //sử dủng size của trình duyệt để setting chiều cao cho grid
            }else if(self.selectedTypeHeightExTable() == 2){
                nts.uk.localStorage.setItem('HEIGHT_OF_GIRD', self.heightGridSetting());
            }
            $('#popup-setting-grid').ntsPopup('hide');
        }

        // xử lý cho button A13
        toLeft() {
            let self = this;
            if(self.indexBtnToLeft % 2 == 0){
                //$("#extable").exTable("hideMiddle");
                $(".toLeft").css("background", "url(../image/toright.png) no-repeat center");
                $(".toLeft").css("margin-left", "190px");
                
            }else{
                //$("#extable").exTable("showMiddle");
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
        
        /**
         * Create exTable
         */
        initExTable(settingHeightGrid): void {
            let self = this,
                timeRanges = [],
                //Get dates in time period
                currentDay = new Date(self.dtPrev().toString()),
                bodyHeightMode = "dynamic",
                windowXOccupation = 50,
                windowYOccupation = 328;

            
            if(settingHeightGrid.isPresent()){
                
            }else{
                
            }
            
            let detailHeaderDs = [];
            let detailContentDeco = [];
            let timeRanges = [];
            
            let leftmostDs = [];
            let middleDs = [];
            let updateMiddleDs = [];
            let detailContentDs = [];
            let horzSumContentDs = [];
            let leftHorzContentDs = [];
            let vertSumContentDs = [];
            let newVertSumContentDs = [];
            
            detailHeaderDs.push(new ksu001.common.modelgrid.ExItem(undefined, true));
            detailHeaderDs.push({
                empId: "", __25: "over", __26: "", __27: "", __28: "", __29: "", __30: "", __31: "",
                _1: "セール", _2: "<div class='header-image'></div>", _3: "", _4: "", _5: "", _6: "", _7: "", _8: "", _9: "特別", _10: "",
                _11: "", _12: "", _13: "", _14: "", _15: "", _16: "Oouch", _17: "", _18: "", _19: "", _20: "", _21: "", _22: "", _23: "",
                _24: "", _25: "", _26: "設定", _27: "", _28: "", _29: "", _30: "", _31: "",
            });

            let middleHeaderDeco = [new ksu001.common.modelgrid.CellColor("over1", undefined, "small-font-size"), 
                                    new ksu001.common.modelgrid.CellColor("over2", undefined, "small-font-size")];
            let middleContentDeco = [];
            let detailHeaderDeco = [new ksu001.common.modelgrid.CellColor("empId", 1, "small-font-size")];
            for (let i = -6; i < 32; i++) {
                if (i <= 0) {
                    let d = 31 + i;
                    detailHeaderDeco.push(new ksu001.common.modelgrid.CellColor("__" + d, 1, "medium-font-size"))
                } else {
                    detailHeaderDeco.push(new ksu001.common.modelgrid.CellColor("_" + i, 1, "medium-font-size"));
                }
            }
            
            for (let i = 0; i < 30; i++) {
                detailContentDs.push(new ksu001.common.modelgrid.ExItem(i.toString()));
                let eName = nts.uk.text.padRight("社員名" + i, " ", 10) + "EmpAAA";
                leftmostDs.push({ 　empId: i.toString(), empName: eName });
                middleDs.push({ empId: i.toString(), team: 'A', rank: 100 + i + "", qualification: 1 + i + "" });
                updateMiddleDs.push({ empId: i.toString(), time: "100:00", days: "38", can: "", get: "" });
                if (i % 2 === 0) middleContentDeco.push(new ksu001.common.modelgrid.CellColor("over1", i.toString(), "cell-red"));
                else middleContentDeco.push(new ksu001.common.modelgrid.CellColor("over2", i.toString(), "cell-green cell-red"));
                if (i % 7 === 0) {
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xseal", 0));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xcustom", 0));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xseal", 1));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xcustom", 1));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_3", i.toString(), "xhidden", 0));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_3", i.toString(), "xhidden", 1));
                }
                // Add both child cells to mark them respectively
                detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", "2", "blue-text", 0));
                detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", "2", "blue-text", 1));
                detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_3", "3", "black-corner-mark", 2));
                detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_3", "4", "red-corner-mark", 3));
                if (i < 1000) timeRanges.push(new ksu001.common.modelgrid.TimeRange("_2", i.toString(), "17:00", "7:00", 1));
                vertSumContentDs.push({ empId: i.toString(), noCan: 6, noGet: 6 });
                newVertSumContentDs.push({ empId: i.toString(), time: "0:00", plan: "30:00" });
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
            
            let detailColumns = [{
                key: "empId", width: "50px", headerText: "ABC", visible: false
            }, {
                    key: "__25", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "__26", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "__27", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "__28", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "__29", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "__30", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "__31", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_1", width: "150px", handlerType: "Input", dataType: "label/label/duration/duration", required: true, min: "4:00", max: "19:00", primitiveValue: "HolidayAppPrimitiveTime"
                }, {
                    key: "_2", width: "150px", handlerType: "Input", dataType: "label/label/duration/duration", rightClick: function(rData, rowIdx, columnKey) { alert(rowIdx); }
                }, {
                    key: "_3", width: "150px", handlerType: "Input", dataType: "label/label/duration/duration", required: true, min: "-12:00", max: "71:59"
                }, {
                    key: "_4", width: "150px", handlerType: "input", dataType: "label/label/duration/duration", primitiveValue: "HolidayAppPrimitiveTime"
                }, {
                    key: "_5", width: "150px", handlerType: "input", dataType: "label/label/duration/duration", primitiveValue: "TimeWithDayAttr"
                }, {
                    key: "_6", width: "150px", handlerType: "input", dataType: "label/label/time/time", rightClick: function(rData, rowIdx, columnKey) { alert(rowIdx); }
                }, {
                    key: "_7", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_8", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_9", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_10", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_11", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_12", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_13", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_14", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_15", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_16", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_17", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_18", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_19", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_20", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_21", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_22", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_23", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_24", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_25", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_26", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_27", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_28", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_29", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_30", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }, {
                    key: "_31", width: "150px", handlerType: "input", dataType: "label/label/time/time"
                }];
            
            detailColumns.forEach(col => {
                if (col.visible === false) return;
                col.headerControl = "link";
                col.headerHandler = (ui) => {
                    alert(ui.columnKey);
                    return false;
                };
            });
            
            // phần leftMost
            let leftmostColumns = [{
                key: "empName", headerText: getText("KSU001_56"), width: "160px", icon: { for: "body", class: "icon-leftmost", width: "25px" },
                css: { whiteSpace: "pre" }, control: "link", handler: function(rData, rowIdx, key) { }
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
    
            // Phần middle
            let middleColumns = [
                { headerText: getText("KSU001_4023"), key: "team", width: "40px", css: { whiteSpace: "none" } },
                { headerText: getText("KSU001_4024"), key: "rank", width: "40px" , css: { whiteSpace: "none" }},
                { headerText: getText("KSU001_4025"), key: "qualification", width: "40px" , css: { whiteSpace: "none" }}
            ];
            

            let middleHeader = {
                columns: middleColumns,
                width: "120px",
                features: [{
                    name: "HeaderRowHeight",
                    rows: { 0: "60px"}
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
            
            // phần Detail
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
                        selector: ".header-image",
                        enter: function(ui) {
                            if (ui.rowIdx === 1 && $(ui.target).is(".header-image")) {
                                ui.tooltip("show", $("<div/>").css({ width: "60px", height: "50px" }).html(`${ui.rowIdx}-${ui.columnKey}`));
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
                primaryKey: "empId",
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
                        case "shortName":
                            return ["workTypeName", "workTimeName"];
                        case "symbol":
                            return ["symbol"];
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
                manipulatorKey: "empId",
                updateMode: "edit",
                pasteOverWrite: true,
                stickOverWrite: true,
                viewMode: "time",
                showTooltipIfOverflow: true,
                errorMessagePopup: true,
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
                .create();

            $("#extable").exTable("scrollBack", 0, { h: 1050 });
            console.log(performance.now() - start);

            //self.setWidth();
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
        updateDetailAndHorzSum(): void {
            let self = this;
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

            service.getDataBasicSchedule(obj).done(function(datas) {
                let arrTmp: any = [];
                self.dataSource([]);
                _.each(datas, data =>{
                    arrTmp.push(new BasicSchedule({
                            date: data.date,
                            employeeId: data.employeeId,
                            workTimeCode: data.workTimeCode,
                            workTypeCode: data.workTypeCode,
                            confirmedAtr: data.confirmedAtr,
                            isIntendedData: true, // = true la du lieu du dinh
                            scheduleCnt: data.scheduleCnt,
                            scheduleStartClock: data.scheduleStartClock,
                            scheduleEndClock: data.scheduleEndClock,
                            bounceAtr: data.bounceAtr
                        }));
                });
                
                self.dataSource(arrTmp);
                
//                //set dataSource for mode shortName
//                self.dataSource([]);
//                _.each(data.listDataShortName, (itemData: BasicSchedule) => {
//                    let itemDataSource: BasicSchedule = _.find(self.dataSource(), { 'employeeId': itemData.employeeId, 'date': itemData.date });
//                    if (itemDataSource) {
//                        itemDataSource.workTimeCode = itemData.workTimeCode;
//                        itemDataSource.workTypeCode = itemData.workTypeCode;
//                        itemDataSource.confirmedAtr = itemData.confirmedAtr;
//                        itemDataSource.isIntendedData = true
//                    } else {
//                        self.dataSource.push(new BasicSchedule({
//                            date: itemData.date,
//                            employeeId: itemData.employeeId,
//                            workTimeCode: itemData.workTimeCode,
//                            workTypeCode: itemData.workTypeCode,
//                            confirmedAtr: itemData.confirmedAtr,
//                            isIntendedData: true
//                        }));
//                    }
//                });
//
//                //set dataSource for mode timeZone
//                _.each(self.dataSource(), (itemDataSource: BasicSchedule) => {
//                    let itemDataTimeZone: any = _.find(data.listDataTimeZone, { 'employeeId': itemDataSource.employeeId, 'date': itemDataSource.date });
//                    if (itemDataTimeZone) {
//                        itemDataSource.scheduleStartClock = itemDataTimeZone.scheduleStartClock;
//                        itemDataSource.scheduleEndClock = itemDataTimeZone.scheduleEndClock;
//                    } else {
//                        itemDataSource.scheduleStartClock = null;
//                        itemDataSource.scheduleEndClock = null;
//                    }
//                });

                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * Get data to display symbol for dataSource(exTable)
         */
        setDataToDisplaySymbol(dataS): void {
            let self = this;
            if (self.dataScheduleDisplayControl() && +self.dataScheduleDisplayControl().symbolAtr == 1) {
                _.each(dataS, (x) => {
                    let workEmpCombine = _.find(self.dataWorkEmpCombine(), { 'workTypeCode': x.workTypeCode, 'workTimeCode': x.workTimeCode });
                    if (workEmpCombine) {
                        x.symbolName = workEmpCombine.symbolName;
                    } else {
                        self.handleSetSymbolForCell(x);
                    }
                });
            } else {
                _.each(dataS, (item) => {
                    self.handleSetSymbolForCell(item);
                });
            }
        }

        handleSetSymbolForCell(item: any): void {
            let self = this;
            let symbolName: string = '';
            if (_.isEmpty(item.workTimeCode)) {
                let workTypeItem: any = _.find(__viewContext.viewModel.viewO.listWorkType(), { 'workTypeCode': item.workTypeCode });
                symbolName = workTypeItem ? workTypeItem.symbolicName : '';
            } else {
                let workTimeItem: any = _.find(__viewContext.viewModel.viewO.listWorkTime(), { 'workTimeCode': item.workTimeCode });
                symbolName = workTimeItem ? workTimeItem.symbolName : '';
            }
            //state = 0 || 3 : rest || work all day
            //state = 1 || 2 : work in morning || work in afternoon
            let stateWorkTypeCode = _.find(self.listStateWorkTypeCode(), { 'workTypeCode': item.workTypeCode });
            if (stateWorkTypeCode) {
                let state = stateWorkTypeCode.state;
                if (state == 1 && self.dataScheduleDisplayControl() && +self.dataScheduleDisplayControl().symbolHalfDayAtr == 1) {
                    item.symbolName = symbolName + self.dataScheduleDisplayControl().symbolHalfDayName;
                } else if (state == 2 && self.dataScheduleDisplayControl() && +self.dataScheduleDisplayControl().symbolHalfDayAtr == 1) {
                    item.symbolName = self.dataScheduleDisplayControl().symbolHalfDayName + symbolName;
                } else {
                    //                    if (state == 0 || state == 3) {
                    item.symbolName = symbolName;
                }
            }
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
         * Get data WkpSpecificDate, ComSpecificDate, PublicHoliday
         */
        getDataSpecDateAndHoliday(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                obj = {
                    workplaceId: self.empItems()[0] ? self.empItems()[0].affiliationId : null,
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

        /**
         * Get data WorkEmpCombine
         */
        getDataWorkEmpCombine(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(), lstWorkTypeCode: any[] = [], lstWorkTimeCode: any[] = [], obj: any = null;
            _.each(__viewContext.viewModel.viewO.listWorkType(), (item) => {
                lstWorkTypeCode.push(item.workTypeCode);
            });
            _.each(__viewContext.viewModel.viewO.listWorkTime(), (item) => {
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
                //TO-DO
                //                if (!!data && data.personInforAtr == 7) {
                //                    self.isInsuranceStatus = true;
                //                }
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
            if (self.selectedTimePeriod() == 1) {
                // Recalculate the time period
                let dtMoment = moment(self.dtAft());
                dtMoment.add(1, 'days');
                self.dtPrev(dtMoment.toDate());
                dtMoment = dtMoment.add(1, 'months');
                dtMoment.subtract(1, 'days');
                self.dtAft(dtMoment.toDate());
                self.dataSource([]);
                self.updateDetailAndHorzSum();
            } else {
                self.stopRequest(true);
            }
        }

        /**
        * come back a month
        */
        backMonth(): void {
            let self = this;
            self.stopRequest(false);
            if (self.selectedTimePeriod() == 1) {
                //Recalculate the time period
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
                self.updateDetailAndHorzSum();
            } else {
                self.stopRequest(true);
            }
        }

        /**
        * Save data
        */
        saveData(): void {
            let self = this;
            setTimeout(function() {
                let arrObj: any[] = [],
                    arrCell: Cell[] = $("#extable").exTable("updatedCells"),
                    arrTmp: Cell[] = _.clone(arrCell),
                    arrLockCellAfterSave: Cell[] = $("#extable").exTable("lockCells"),
                    newArrCell = [];
                
                // compare 2 array lockCell init and after
                if (arrCell.length == 0 && _.isEqual(self.arrLockCellInit(), arrLockCellAfterSave)) {
                    return;
                }
                
                self.stopRequest(false);
                                    
                let arrNewCellIsLocked: any[] = _.differenceWith(arrLockCellAfterSave, self.arrLockCellInit(), _.isEqual),
                    arrNewCellIsUnlocked: any[] = _.differenceWith(self.arrLockCellInit(), arrLockCellAfterSave, _.isEqual);

                // neu o mode time thi can merge cac object giong nhau vao thanh 1
                if (self.selectedModeDisplay() == 2) {
                    _.each(arrTmp, (item) => {
                        let arrFilter = _.filter(arrTmp, { 'rowIndex': item.rowIndex, 'columnKey': item.columnKey });
                        if (arrFilter.length > 1) {
                            let sTime: any = '', eTime: any = '';
                            _.each(arrFilter, (data) => {
                                if (data.innerIdx == 0) {
                                    sTime = data.value.startTime;
                                } 
                                if (data.innerIdx == 1) {
                                    eTime = data.value.endTime;
                                }
                                _.remove(arrCell, data);
                            });
                            // set innerIdx = -1: do sua startTime va endTime (=> cell) trong mode Time
                            arrCell.push(new Cell({
                                rowIndex: item.rowIndex,
                                columnKey: item.columnKey,
                                value: new ksu001.common.viewmodel.ExCell ({
                                    workTypeCode: item.value.workTypeCode,
                                    workTypeName: item.value.workTypeName,
                                    workTimeCode: item.value.workTimeCode,
                                    workTimeName: item.value.workTimeName,
                                    symbolName: item.value.symbolName,
                                    startTime: sTime,
                                    endTime: eTime,
                                }),
                                innerIdx: -1,    
                            }));
                        }
                    });
                }
                
                // distinct arrCell- do khi thay doi 1 cell co 2 row thi a Manh ban ra 2 cell vs innerIdx khac nhau
                _.each(arrCell, cell => {
                    if (!_.find(newArrCell, { 'rowIndex': cell.rowIndex, 'columnKey': cell.columnKey })) {
                        newArrCell.push(cell);
                    };
                });
                arrCell = newArrCell;
                
                arrNewCellIsUnlocked = _.differenceBy(arrNewCellIsUnlocked, arrCell, ['rowIndex', 'columnKey']);
                arrCell.push.apply(arrCell, arrNewCellIsUnlocked);
                arrCell = _.differenceBy(arrCell, arrNewCellIsLocked, ['rowIndex', 'columnKey']);

                for (let i = 0; i < arrCell.length; i += 1) {
                    let cell: any = arrCell[i], valueCell = cell.value, sid = self.listSid()[Number(cell.rowIndex)];
                    
                     // slice string '_YYYYMMDD' to 'YYYYMMDD'
                    let date: string = moment.utc(cell.columnKey.slice(1, cell.columnKey.length), 'YYYYMMDD').toISOString(),
                        basicSchedule: any = _.find(self.dataSource(), {'employeeId': sid, 'date': moment(date).format('YYYY/MM/DD') }),
                        bounceAtr: any = 1, //set static bounceAtr =  1
                        confirmedAtr: any =  0,
                        workScheduleTimeZone: any = (self.selectedModeDisplay() != 1 && valueCell.workTimeCode != null) ? [{
                            scheduleCnt: 1,
                            scheduleStartClock: (typeof valueCell.startTime === 'number') ? valueCell.startTime
                                : (valueCell.startTime ? nts.uk.time.minutesBased.clock.dayattr.parseString(valueCell.startTime).asMinutes : null),
                            scheduleEndClock: (typeof valueCell.endTime === 'number') ? valueCell.endTime
                                : (valueCell.endTime ? nts.uk.time.minutesBased.clock.dayattr.parseString(valueCell.endTime).asMinutes : null),
                            bounceAtr: bounceAtr
                        }] : null;
                        
                    arrObj.push({
                        date: date,
                        employeeId: sid,
                        workTimeCode: valueCell.workTimeCode,
                        workTypeCode: valueCell.workTypeCode,
                        confirmedAtr: confirmedAtr,
                        workScheduleTimeZoneSaveCommands: workScheduleTimeZone
                    });
                }
                
                for (let i = 0; i < arrNewCellIsLocked.length; i += 1) {
                    let newCellIsLocked: any = arrNewCellIsLocked[i], 
                        valueNewCellIsLocked = newCellIsLocked.value,
                        sid = self.listSid()[newCellIsLocked.rowIndex];
                    if(!valueNewCellIsLocked.workTypeCode) { 
                        $("#extable").exTable("unlockCell", sid, newCellIsLocked.columnKey);
                        continue;
                    }
                    // slice string '_YYYYMMDD' to 'YYYYMMDD'
                    let date: string = moment.utc(newCellIsLocked.columnKey.slice(1, newCellIsLocked.columnKey.length), 'YYYYMMDD').toISOString(),
                        basicSchedule: any = _.find(self.dataSource(), {'employeeId': sid, 'date': moment(date).format('YYYY/MM/DD') }),
                        bounceAtr: any = 1, //set static bounceAtr =  1
                        confirmedAtr: any =  1,
                        workScheduleTimeZone: any = (valueNewCellIsLocked.workTimeCode != null && self.selectedModeDisplay() != 1) ? [{
                            scheduleCnt: 1,
                            scheduleStartClock: (typeof valueNewCellIsLocked.startTime === 'number') ? valueNewCellIsLocked.startTime
                                : (valueNewCellIsLocked.startTime ? nts.uk.time.minutesBased.clock.dayattr.parseString(valueNewCellIsLocked.startTime).asMinutes : null),
                            scheduleEndClock: (typeof valueNewCellIsLocked.endTime === 'number') ? valueNewCellIsLocked.endTime
                                : (valueNewCellIsLocked.endTime ? nts.uk.time.minutesBased.clock.dayattr.parseString(valueNewCellIsLocked.endTime).asMinutes : null),
                            bounceAtr: bounceAtr
                        }] : null;
                        
                    arrObj.push({
                        date: date,
                        employeeId: self.listSid()[Number(newCellIsLocked.rowIndex)],
                        workTimeCode: valueNewCellIsLocked.workTimeCode,
                        workTypeCode: valueNewCellIsLocked.workTypeCode,
                        confirmedAtr: confirmedAtr,
                        workScheduleTimeZoneSaveCommands: workScheduleTimeZone
                    });
                }
                
                if(arrObj.length <= 0){
                    self.stopRequest(true);
                    return;
                }
                
                let dataRegisterBasicSchedule: any = {
                    modeDisplay: self.selectedModeDisplay(),
                    listRegisterBasicSchedule: arrObj
                };
                
                service.registerData(dataRegisterBasicSchedule).done(function(error: any) {
                    //get data and update extable
                    self.setDatasource().done(function() {
                        self.updateExTable();
                        if (error.length != 0) {
                            self.addListError(error);
                        }
                    });
                }).fail(function(error: any) {
                    alertError({ messageId: error.messageId });
                    self.setDatasource().done(function() {
                        self.updateExTable();
                    });
                }).always(() => {
                    self.stopRequest(true);
                });
            }, 1);
        }

        /**
         * Set color for cell = set text color + set background color
         */
        setColorForCell(detailContentDeco: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            
            /* set color for text in cell - 明細セル文字色の判断処理 */
            if (self.selectedModeDisplayObject() == 2) {
                // isIntendedData': false => this is actual data
                let arrActualData: any[] = _.filter(self.dataSource(), { 'isIntendedData': false });
                if (arrActualData.length > 0) {
                    _.each(arrActualData, (item: BasicSchedule) => {
                        detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(new Date(item.date)).format('YYYYMMDD'), item.employeeId, "color-schedule-performance", 0));
                        detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(new Date(item.date)).format('YYYYMMDD'), item.employeeId, "color-schedule-performance", 1));
                    });
                }
            }
            
            // lstData: list object in dataSource. It has workTypeCode, which exist in master data WORKTYPE
            let lstData: BasicSchedule[] = [];
            _.each(__viewContext.viewModel.viewO.listWorkType(), (item) => {
                let obj = _.filter(self.dataSource(), { 'workTypeCode': item.workTypeCode });
                if (obj) {
                    lstData.push.apply(lstData, obj);
                }
            });
            
            if (self.selectedModeDisplay() == 2) {
                // when mode is time(時刻) dont set color for text in cell
                _.each(lstData, (item) => {
                    detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, '', 0));
                    detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, '', 1));
                });
            } else {
                // when mode is shortName(略名) and mode symbol(記号)
                let lstWorkTypeCode = [],
                    lstIntendedData = _.filter(self.dataSource(), { 'isIntendedData': true });
                if (lstIntendedData.length > 0) {
                    _.map(lstIntendedData, (x) => {
                        if (!_.includes(lstWorkTypeCode, x.workTypeCode)) {
                            lstWorkTypeCode.push(x.workTypeCode);
                        }
                    });
                }

                _.each(lstData, (item) => {
                    let stateWorkTypeCode = _.find(self.listStateWorkTypeCode(), { 'workTypeCode': item.workTypeCode });
                    if (stateWorkTypeCode) {
                        let state = stateWorkTypeCode.state;
                        if (state == 3) {
                            //state == 3 is work-day
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, "color-attendance", 0));
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, "color-attendance", 1));
                        } else if (state == 0) {
                            //state == 0 is holiday-day
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, "color-holiday", 0));
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, "color-holiday", 1));
                        } else {
                            //state == 1 || 2 is work-half-day
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, "color-half-day-work", 0));
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor("_" + moment(item.date, 'YYYY/MM/DD', true).format('YYYYMMDD'), item.employeeId, "color-half-day-work", 1));
                        }
                    }
                });
            }
            
            /* set background color for cell*/
            
            //            if (self.selectedBackgroundColor() === '001') {
            // Return value：就業時間帯 -> query table WorkTime to get color code
            // TO-DO
            //                dfd.resolve();
            //            } else {
            // TO-DO
            // 日単位でチェック handler will return state 　非表示　or 確定　or 応援者　or 修正不可
            
            // get data from WorkScheduleState
            self.getDataWorkScheduleState().done(() => {
                let data = [],
                    innerIdx: number = 1;
                if (self.selectedModeDisplay() === 3) {
                    // refer スケジュール明細セル　背景色制御 in document
                    //scheduleItemId = 1: it is id of WorkType
                    //scheduleItemId = 2: it is id of WorkTime
                    data = _.reduce(self.dataWScheduleState(), (result, item) => {
                        if (item.scheduleItemId == 1 || item.scheduleItemId == 2) {
                            // ham find ben duoi luon chi tim duoc toi da 1 gia tri
                            let scheState: any = _.find(result, { 'employeeId': item.employeeId, 'date': item.date });
                            if (scheState == undefined) {
                                // neu chua co data cho employeeId va date tuong ung thi insert
                                result.push(item);
                            } else if (scheState.scheduleEditState > item.scheduleEditState) {
                                // neu co data cho employeeId va date tuong ung nhung state khong uu tien thi remove roi insert
                                _.remove(result, { 'employeeId': item.employeeId, 'date': item.date });
                                result.push(item);
                            }
                        }
                        return result;
                    }, []);
                } else {
                    // Return value of ScheduleEditState of WorkScheduleState
                    if (self.selectedModeDisplay() === 2) {
                        //scheduleItemId = 3: it is id of StartTime
                        //scheduleItemId = 4: it is id of EndTime
                        data = _.reduce(self.dataWScheduleState(), (result, item) => {
                            if (item.scheduleItemId == 3 || item.scheduleItemId == 4) {
                                result.push(item);
                            }
                            return result;
                        }, []);
                    } else {
                        //scheduleItemId = 1: it is id of WorkType
                        //scheduleItemId = 2: it is id of WorkTime
                        data = _.reduce(self.dataWScheduleState(), (result, item) => {
                            if (item.scheduleItemId == 1 || item.scheduleItemId == 2) {
                                result.push(item);
                            }
                            return result;
                        }, []);
                    }

                }
                // set background color    
                _.each(data, (item) => {
                    let columnKey: string = "_" + moment(new Date(item.date)).format('YYYYMMDD');
                    // neu la mode symbol thi luon set innerIdx = 0 de mau background luon hien thi dung
                    // do mau background luon hien thi mau theo innerIdx = 0
                    if (self.selectedModeDisplay() === 3) {
                        innerIdx = 0;
                    } else {
                        if (item.scheduleItemId == 1 || item.scheduleItemId == 3) {
                            innerIdx = 0;
                        } else {
                            innerIdx = 1;
                        }
                    }
                    
                    if (item.scheduleEditState == 1) {
                        //手修正(本人) = bg-daily-alter-self
                        let cell = _.find(detailContentDeco, { 'columnKey': columnKey, 'rowId': item.employeeId, 'innerIdx': innerIdx });
                        if (_.isNil(cell)){
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor(columnKey, item.employeeId, "bg-daily-alter-self", innerIdx));
                        } else{
                            cell.clazz = 'bg-daily-alter-self' + (cell.clazz == '' ? '' : ' ') + cell.clazz;
                        } 
                    } else if (item.scheduleEditState == 2) {
                        //手修正(他人) = bg-daily-alter-other
                        let cell = _.find(detailContentDeco, { 'columnKey': columnKey, 'rowId': item.employeeId, 'innerIdx': innerIdx });
                        if (_.isNil(cell)){
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor(columnKey, item.employeeId, "bg-daily-alter-other", innerIdx));
                        } else {
                            cell.clazz = 'bg-daily-alter-other' + (cell.clazz == '' ? '' : ' ') + cell.clazz;
                        } 
                    } else if (item.scheduleEditState == 3) {
                        //申請反映 = bg-daily-reflect-application
                        let cell = _.find(detailContentDeco, { 'columnKey': columnKey, 'rowId': item.employeeId, 'innerIdx': innerIdx });
                        if (_.isNil(cell)){
                            detailContentDeco.push(new ksu001.common.viewmodel.CellColor(columnKey, item.employeeId, "bg-daily-reflect-application", innerIdx));    
                        } else {
                            cell.clazz = 'bg-daily-reflect-application' + (cell.clazz == '' ? '' : ' ') + cell.clazz;
                        } 
                    }
                });
                
                dfd.resolve();
            });
            return dfd.promise();
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
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 0, "bg-schedule-specific-date"));
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 1, "bg-schedule-specific-date"));
                    } else if (_.includes(self.dataPublicHoliday(), dateFormat)) {
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 0, "bg-schedule-sunday color-schedule-sunday"));
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 1, "bg-schedule-sunday color-schedule-sunday"));
                    } else if (date.weekDay === '土') {
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 0, "bg-schedule-saturday color-schedule-saturday"));
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 1, "bg-schedule-saturday color-schedule-saturday"));
                    } else if (date.weekDay === '日') {
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 0, "bg-schedule-sunday color-schedule-sunday"));
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 1, "bg-schedule-sunday color-schedule-sunday"));
                    } else {
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 0, "bg-weekdays color-weekdays"));
                        detailHeaderDeco.push(new ksu001.common.viewmodel.CellColor("_" + ymd, 1, "bg-weekdays color-weekdays"));
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
            _.forEach(errorsRequest, function(err) {
                let errSplits = err.split(",");
                if (errSplits.length == 1) {
                    errors.push({ message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
                } else {
                    errors.push({
                        message: nts.uk.resource.getMessage(errSplits[0], [getText(errSplits[1]), getText(errSplits[2])]),
                        messageId: errSplits[0],
                        supplements: {}
                    });
                }
            });

            nts.uk.ui.dialog.bundledErrors({ errors: errors });
        }
        
        editMode() {
            let self = this;
            // set color button
            $(".editMode").css("background-color", "#007fff");
            $(".editMode").css("color", "#ffffff");
            
            $(".confirmMode").css("background-color", "#ffffff");
            $(".confirmMode").css("color", "#000000");
        }

        confirmMode() {
            let self = this;
            // set color button
            $(".confirmMode").css("background-color", "#007fff");
            $(".confirmMode").css("color", "#ffffff");
            
            $(".editMode").css("background-color", "#ffffff");
            $(".editMode").css("color", "#000000");
        }

        /**
         * paste data on cell
         */
        pasteData(): void {
            let self = this;
            // set color button
            $("#paste").css("background-color", "#007fff");
            $("#paste").css("color", "#ffffff");
            
            $("#copy").css("background-color", "#ffffff");
            $("#copy").css("color", "#000000");
            
            $("#input").css("background-color", "#ffffff");
            $("#input").css("color", "#000000");

            
            $("#extable").exTable("updateMode", "stick");
             
            if (self.selectedModeDisplay() == 1) {
                // set sticker single
                $("#extable").exTable("stickData", __viewContext.viewModel.viewO.nameWorkTimeType());
                $("#extable").exTable("stickMode", "single");
            } else if (self.selectedModeDisplay() == 3) {
                $("#extable").exTable("stickMode", "multi");
            }
        }

        /**
         * copy data on cell
         */
        copyData(): void {
            // set color button
            $("#copy").css("background-color", "#007fff");
            $("#copy").css("color", "#ffffff");
            
            $("#paste").css("background-color", "#ffffff");
            $("#paste").css("color", "#000000");
            
            $("#input").css("background-color", "#ffffff");
            $("#input").css("color", "#000000");
            
            $("#extable").exTable("updateMode", "copyPaste");
        }
        
        /**
         * copy data on cell
         */
        inputData(): void {
            // set color button
            $("#input").css("background-color", "#007fff");
            $("#input").css("color", "#ffffff");
            
            $("#copy").css("background-color", "#ffffff");
            $("#copy").css("color", "#000000");
            
            $("#paste").css("background-color", "#ffffff");
            $("#paste").css("color", "#000000");
            
        }
        
        

        /**
         * undo data on cell
         */
        undoData(): void {
            $("#extable").exTable("stickUndo");
        }

        /**
         * get data form COM_PATTERN (for screen Q)
         */
        getDataComPattern(selectedLinkButton): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getDataComPattern().done((data) => {
                __viewContext.viewModel.viewQ.listComPattern(data);
                __viewContext.viewModel.viewQ.handleInitCom(
                        data,
                        __viewContext.viewModel.viewQ.textButtonArrComPattern, 
                        __viewContext.viewModel.viewQ.dataSourceCompany, 
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
                __viewContext.viewModel.viewQ.listWkpPattern(data);
                __viewContext.viewModel.viewQ.handleInitWkp(
                        data, 
                        __viewContext.viewModel.viewQ.textButtonArrComPattern, 
                        __viewContext.viewModel.viewQ.dataSourceCompany,  
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
                    self.updateDetailAndHorzSum();
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

    class Node {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
        childs: Array<Node>;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
            self.custom = 'Random' + new Date().getTime();
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    interface ICell {
        rowIndex: string,
        columnKey: string,
        value: ksu001.common.viewmodel.ExCell,
        innerIdx: number
    }

    class Cell {
        rowIndex: string;
        columnKey: string;
        value: ksu001.common.viewmodel.ExCell;
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
    	affiliationCode: string;
    	affiliationId: string;
    	affiliationName: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.empId = param.empId;
            this.empCd = param.empCd;
            this.empName = param.empName;
            this.affiliationCode = param.affiliationCode;
            this.affiliationId = param.affiliationId;
            this.affiliationName = param.affiliationName;
            this.baseDate = param.baseDate;
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

    class WorkScheduleState {
        date: Date;
        employeeId: string;
        scheduleItemId: number;
        scheduleEditState: number;

        constructor(date: Date, employeeId: string, scheduleItemId: number, scheduleEditState: number) {
            this.date = date;
            this.employeeId = employeeId;
            this.scheduleItemId = scheduleItemId;
            this.scheduleEditState = scheduleEditState;
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
        constructor(code: string, name: string, description?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
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

    class ExItem {
        empId: string;
        empName: string;

        constructor(empId: string, dsOfSid: BasicSchedule[], listWorkType: ksu001.common.viewmodel.WorkType[], listWorkTime: ksu001.common.viewmodel.WorkTime[], manual: boolean, arrDay: Time[]) {
            this.empId = empId;
            this.empName = empId;}
        
            // create detailHeader (ex: 4/1 | 4/2)
            /*if (manual) {
                for (let i = 0; i < arrDay.length; i++) {
                    if (+arrDay[i].day == 1) {
                        this['_' + arrDay[i].yearMonthDay] = arrDay[i].month + '/' + arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                    } else {
                        this['_' + arrDay[i].yearMonthDay] = arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                    }
                }
                return;
            }
            //create detailContent 
            for (let i = 0; i < arrDay.length; i++) {
                let obj: BasicSchedule = _.find(dsOfSid, (x) => {
                    return moment(new Date(x.date)).format('D') == arrDay[i].day;
                });
                if (obj) {
                    //get code and name of workType and workTime
                    let workTypeCode = null, workTypeName = null, workTimeCode = null, workTimeName = null;
                    let workType = _.find(listWorkType, ['workTypeCode', obj.workTypeCode]);
                    if (workType) {
                        workTypeCode = obj.workTypeCode;
                        workTypeName = workType.abbreviationName;
                    } else {
                        workTypeCode = null;
                        workTypeName = obj.workTypeCode != null ? getText('KSU001_103', [obj.workTypeCode]) : null;
                    }

                    let workTime = _.find(listWorkTime, ['workTimeCode', obj.workTimeCode]);
                    if (workTime) {
                        workTimeCode = obj.workTimeCode;
                        workTimeName = workTime.abName;
                    } else {
                        workTimeCode = null;
                        workTimeName = obj.workTimeCode != null ? getText('KSU001_103', [obj.workTimeCode]) : null;
                    }

                    this['_' + arrDay[i].yearMonthDay] = new ksu001.common.viewmodel.ExCell({
                        workTypeCode: workTypeCode,
                        workTypeName: workTypeName,
                        workTimeCode: workTimeCode,
                        workTimeName: workTimeName,
                        symbolName: obj.symbolName,
                        startTime: obj.scheduleStartClock ? formatById("Clock_Short_HM", obj.scheduleStartClock) : '',
                        endTime: obj.scheduleStartClock ? formatById("Clock_Short_HM", obj.scheduleEndClock) : ''
                    });
                } else {
                    this['_' + arrDay[i].yearMonthDay] = new ksu001.common.viewmodel.ExCell({
                        workTypeCode: null,
                        workTypeName: null,
                        workTimeCode: null,
                        workTimeName: null,
                        symbolName: '',
                        //startTime/endTime để là '' chứ không phải null
                        //do màn hình ở mode Time, click cell mà k thay đổi rồi click vào cell khác
                        //khi đó cell bị click trả ra giá trị là ''
                        //nếu để startTime/endTime null thì cell đó sẽ nhận thấy là đã thay đổi giá trị
                        //điều đó là không đúng
                        startTime: '',
                        endTime: ''
                    });
                }
            }
        } */
    }
}