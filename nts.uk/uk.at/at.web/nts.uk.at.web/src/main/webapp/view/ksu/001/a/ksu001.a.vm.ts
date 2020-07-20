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
        dtPrev: KnockoutObservable<Date> = ko.observable(null);
        dtAft: KnockoutObservable<Date> = ko.observable(null);
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
                __viewContext.viewModel.viewO.time1('');
                __viewContext.viewModel.viewO.time2('');
                
                self.stopRequest(false);
                // close screen O1 when change mode
                let currentScreen = __viewContext.viewModel.viewO.currentScreen;
                if (currentScreen) {
                    currentScreen.close();
                }
                
                let detailContentDeco: any[] = [];


                if (newValue == 'Aa') { // mode 勤務表示 //Hiển thị working       
                    self.saveModeGridToLocalStorege('Aa');
                    self.visibleShiftPalette(true);
                    $("#extable").exTable("viewMode", "symbol");

                    self.stopRequest(true);
                } else if (newValue == 'Ab') { // mode 略名表示 //Hiển thị detail 
                    self.saveModeGridToLocalStorege('Ab');
                    self.visibleShiftPalette(false);
                    $("#extable").exTable("viewMode", "shortName");

                    self.stopRequest(true);
                } else if (newValue == 'Ac') {  // mode シフト表示 //Hiển thị shift    
                    self.saveModeGridToLocalStorege('Ac');
                    self.visibleShiftPalette(false);
                    $("#extable").exTable("viewMode", "time");

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
        
        saveModeGridToLocalStorege(mode) {
            let self = this;
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                userInfor.disPlayFormat = mode;
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
        }
        
        /**
         * get data for screen O, Q , A before bind
         */
        startKSU001(): JQueryPromise<any> {
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

                self.initExTable();
                
                dfd.resolve();
            });
            return dfd.promise();
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
                __viewContext.viewModel.viewQ.selectedpalletUnit(userInfor.shiftPalletUnit);
                
            });
        }
        

        /**
        * Create exTable
        */
        initExTable(): void {
            let self = this,
                timeRanges = [],
                //Get dates in time period
                currentDay = new Date(self.dtPrev().toString()),
                bodyHeightMode = "dynamic",
                windowXOccupation = 65,
                windowYOccupation = 328;

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
                let codeNameOfEmp = nts.uk.text.padRight("社員名" + i, " ", 10) + "EmpAAA";
                leftmostDs.push({ empId: i.toString(), codeNameOfEmp: codeNameOfEmp });
                middleDs.push({ empId: i.toString(), team: 'A', rank: 100 + i + "", qualification: 1 + i + "" });
                updateMiddleDs.push({ empId: i.toString(), time: "100:00", days: "38", can: "", get: "" });
                if (i % 2 === 0) middleContentDeco.push(new ksu001.common.modelgrid.CellColor("over1", i.toString(), "cell-red"));
                else middleContentDeco.push(new ksu001.common.modelgrid.CellColor("over2", i.toString(), "cell-green cell-red"));
                if (i % 7 === 0) {
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xseal", 0));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xcustom", 0));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xseal", 1));
                    detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", i.toString(), "xcustom", 1));
                }
                // Add both child cells to mark them respectively
                detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", "2", "blue-text", 0));
                detailContentDeco.push(new ksu001.common.modelgrid.CellColor("_2", "2", "blue-text", 1));
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
                key: "codeNameOfEmp", headerText: getText("KSU001_56"), width: "160px", icon: { for: "body", class: "icon-leftmost", width: "25px" },
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
                { headerText: getText("KSU001_4024"), key: "rank", width: "40px", css: { whiteSpace: "none" } },
                { headerText: getText("KSU001_4025"), key: "qualification", width: "40px", css: { whiteSpace: "none" } }
            ];


            let middleHeader = {
                columns: middleColumns,
                width: "120px",
                features: [{
                    name: "HeaderRowHeight",
                    rows: { 0: "60px" }
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
                manipulatorKey: "empId",
                updateMode: "edit",
                pasteOverWrite: true,
                stickOverWrite: true,
                viewMode: "time",
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
                        
            $('#popup-setting-grid').ntsPopup('hide');
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
        }

        /**
         * Set color for cell = set text color + set background color
         */
        setColorForCell(detailContentDeco: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
        }

        /**
         * Set color for cell header: 日付セル背景色文字色制御
         * 
         */
        setColorForCellHeaderDetailAndHoz(detailHeaderDeco: any): JQueryPromise<any> {
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

    export enum viewMode {
        SYMBOL    = 1,
        SHORTNAME   = 2,
        TIME  = 3
    } 
    
    export enum TypeHeightExTable {
        DEFAULT    = 1,
        SETTING   = 2
    }










    



    
}