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
        visibleShiftPalette: KnockoutObservable<boolean> = ko.observable(true);

        mode: KnockoutObservable<string> = ko.observable('edit'); // edit || confirm 
        showA9: boolean;

        // A4 popup-area6 
        // A4_4
        selectedModeDisplayInBody: KnockoutObservable<number> = ko.observable('time');

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
        targetOrganizationName: KnockoutObservable<string> = ko.observable('');

        // popup Setting Grid
        selectedTypeHeightExTable: KnockoutObservable<number> = ko.observable(1);
        heightGridSetting: KnockoutObservable<string> = ko.observable('');
        isEnableInputHeight: KnockoutObservable<boolean> = ko.observable(false);

        // dùng cho xử lý của botton toLeft, toRight, toDown
        indexBtnToLeft: number = 0;
        indexBtnToRight: number = 0;
        indexBtnToDown: number = 0;

        //
        enableBtnPaste: KnockoutObservable<boolean> = ko.observable(true);
        enableBtnCoppy: KnockoutObservable<boolean> = ko.observable(true);
        enableBtnInput: KnockoutObservable<boolean> = ko.observable(true);
        visibleBtnInput: KnockoutObservable<boolean> = ko.observable(true);
        enableBtnUndo: KnockoutObservable<boolean> = ko.observable(true);
        enableBtnRedo: KnockoutObservable<boolean> = ko.observable(true);
        visibleBtnUndo: KnockoutObservable<boolean> = ko.observable(true);
        visibleBtnRedo: KnockoutObservable<boolean> = ko.observable(true);
        enableHelpBtn: KnockoutObservable<boolean> = ko.observable(true);

        arrDay: Time[] = [];
        listSid: KnockoutObservableArray<string> = ko.observableArray([]);
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
        KEY: string = 'USER_INFOR';

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
                if (newValue == TypeHeightExTable.DEFAULT) { // 
                    self.isEnableInputHeight(false);
                    self.heightGridSetting('');
                    $('#input-heightExtable').ntsError('clear');
                } else if (newValue == TypeHeightExTable.SETTING) {
                    self.isEnableInputHeight(true);
                    setTimeout(() => {
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

            self.selectedModeDisplayInBody.subscribe(function(viewMode) {
                if (viewMode == null)
                    return;
                console.log('mode:  ' + viewMode);
                nts.uk.ui.errors.clearAll();
                self.removeClass();
                self.stopRequest(false);
                // close screen O1 when change mode
                if (viewMode == 'shift') { // mode シフト表示  
                    self.shiftModeStart().done(() => {
                        self.stopRequest(true);
                    });
                } else if (viewMode == 'shortName') { // mode 略名表示
                    self.shortNameModeStart().done(() => {
                        self.stopRequest(true);
                    });
                } else if (viewMode == 'time') {  // mode 勤務表示 
                    self.timeModeStart().done(() => {
                        self.stopRequest(true);
                    });
                }
            });

            self.backgroundColorSelected.subscribe((value) => {
                let self = this;
                uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                    let userInfor: IUserInfor = JSON.parse(data);
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
            //close popup
            $(".close-popup").click(function() {
                $('#popup-area8').css('display', 'none');
            });
            
        }
        // end constructor

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let viewMode = 'time'; // default
            let item = uk.localStorage.getItem(self.KEY);
            if (item.isPresent()) {
                let userInfor: IUserInfor = JSON.parse(item.get());
                viewMode = userInfor.disPlayFormat;
            }

            let param = {
                viewMode: viewMode
            };
            service.getDataStartScreen(param).done((data: IDataStartScreen) => {

                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart();

                // set data Header
                self.bindingToHeader(data);

                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, viewMode);
                self.initExTable(dataBindGrid, viewMode);

                // 
                self.creatDataLocalStorege(data);
                $(".editMode").addClass("btnControlSelected").removeClass("btnControlUnSelected");
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        creatDataLocalStorege(data: IDataStartScreen) {
            let self = this;
            let dataBasic: IDataBasicDto = data.dataBasicDto;
            let item = uk.localStorage.getItem(self.KEY);
            if (!item.isPresent()) {
                let userInfor: IUserInfor = {};
                userInfor.disPlayFormat = self.selectedModeDisplayInBody();
                userInfor.backgroundColor = 0; // 0 : 通常; 1: シフト
                userInfor.shiftPalletUnit = 1;
                userInfor.shiftPalettePageNumber = 0;
                userInfor.shiftPalletPositionNumber = 0;
                userInfor.gridHeightSelection = 1;
                userInfor.heightGridSetting = '';
                userInfor.workplaceId= dataBasic.workplaceId;
                userInfor.workPlaceName= dataBasic.targetOrganizationName;
                userInfor.workType = {}; 
                userInfor.workTime = {}; 
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            }
        }

        shiftModeStart(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                param = {
                     viewMode : 'shift'
                };
            self.saveModeGridToLocalStorege('shift');
            self.visibleShiftPalette(true);
            self.visibleBtnInput(false);
            service.getDataOfShiftMode(param).done((data: IDataStartScreen) => {
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart();
                // set data Header
                self.bindingToHeader(data);
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, 'shift');
                
                self.updateExTable(dataBindGrid, 'shift', true, true, true);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        shortNameModeStart(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                param = {
                    viewMode : 'shortName'
                };
            self.saveModeGridToLocalStorege('shortName');
            self.visibleShiftPalette(false);
            self.visibleBtnInput(false);
            service.getDataOfShortNameMode(param).done((data: IDataStartScreen) => {
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart();
                // set data Header
                self.bindingToHeader(data);
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, 'shortName');

                self.updateExTable(dataBindGrid , 'shortName', true, true, true);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        timeModeStart(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                param = {
                     viewMode : 'time'
                };
            self.saveModeGridToLocalStorege('time');
            self.visibleShiftPalette(false);
            self.visibleBtnInput(true);
            service.getDataOfTimeMode(param).done((data: IDataStartScreen) => {
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart();
                // set data Header
                self.bindingToHeader(data);
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, 'time');

                self.updateExTable(dataBindGrid, 'time', true, true, true);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        // binding ket qua cua <<ScreenQuery>> 初期起動の情報取得 
        private bindingToHeader(data: IDataStartScreen) {
            let self = this;
            let dataBasic: IDataBasicDto = data.dataBasicDto;
            self.dtPrev(dataBasic.startDate);
            self.dtAft(dataBasic.endDate);
            self.targetOrganizationName(dataBasic.targetOrganizationName);
            __viewContext.viewModel.viewAC.workplaceModeName(dataBasic.designation);
            // save data to ocal Storage
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor: IUserInfor = JSON.parse(data);
                userInfor.workplaceId = dataBasic.workplaceId;
                userInfor.workplaceId = dataBasic.workplaceGroupId;
                userInfor.workPlaceName = dataBasic.targetOrganizationName;
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
        }

        // convert data lấy từ server để đẩy vào Grid
        private convertDataToGrid(data: IDataStartScreen, viewMode: string) {
            let self = this;
            let result = {};
            let leftmostDs = [];
            let middleDs = [];
            let detailColumns = [];
            let objDetailHeaderDs = {};
            let detailHeaderDeco = [];
            let detailContentDs = [];
            let detailContentDeco = [];
            let htmlToolTip = [];
            for (let i = 0; i < data.listEmpInfo.length; i++) {
                let emp: IEmpInfo = data.listEmpInfo[i];
                let objDetailContentDs = new Object();
                console.log('key  ' + i);
                // set data to detailLeftmost
                let businessName = emp.businessName == null || emp.businessName == undefined ? '' : emp.businessName.trim();
                leftmostDs.push({ sid: emp.employeeId, codeNameOfEmp: emp.employeeCode + ' ' + businessName });

                let listWorkScheduleInforByEmp: Array<IWorkScheduleWorkInforDto> = _.filter(data.listWorkScheduleWorkInfor, function(workSchedul: IWorkScheduleWorkInforDto) { return workSchedul.employeeId === emp.employeeId });
                let listWorkScheduleShiftByEmp: Array<IWorkScheduleShiftforDto> = _.filter(data.listWorkScheduleShift, function(workSchedul: IWorkScheduleShiftforDto) { return workSchedul.employeeId === emp.employeeId });

                // set data to detailContent : datasource va deco
                
                if (viewMode == 'shift') {
                    objDetailContentDs['sid'] = i;
                    _.each(listWorkScheduleShiftByEmp, (cell: IWorkScheduleShiftforDto) => {
                        let time = new Time(new Date(cell.date));
                        let ymd = time.yearMonthDay;
                        let shiftName = '';
                        shiftName = (cell.haveData == true && cell.shiftName == null) ? getText("KSU001_94") : cell.shiftName;
                        if (cell.needToWork == false)
                            shiftName = '';
                        objDetailContentDs['_' + ymd] = new ExCell('', '', '', '', '', '', shiftName);

                        // điều kiện ※Aa1
                        if (!cell.isActive) {
                            detailContentDeco.push(new CellColor('_' + ymd, i, "xseal", 0));
                        }

                        // set Deco background
                        if (self.backgroundColorSelected() == 1) {
                            //シフト表示：シフトの背景色  Shift display: Shift background color                                                                             
                            if (cell.shiftCode != null) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "", 0)); // cho anh Phong update KSM015B
                            }
                        } else if (self.backgroundColorSelected() == 0) {
                            // シフト表示：通常の背景色  Shift display: normal background color 
                            if (cell.shiftCode != null) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "", 0)); // cho anh Phong update KSM015B
                            }
                        }

                        // set Deco text color
                        if (cell.achievements == true) {
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "color-schedule-performance", 0));
                        } else {
                            if (cell.workHolidayCls == AttendanceHolidayAttr.FULL_TIME) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-attendance", 0));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.MORNING) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 0));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.AFTERNOON) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 0));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.HOLIDAY) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-holiday", 0));
                            }
                        }
                    });
                    detailContentDs.push(objDetailContentDs);
                    
                } else if (viewMode == 'shortName') {
                    objDetailContentDs['sid'] = i;
                    _.each(listWorkScheduleInforByEmp, (dateOfWorkSchedule: IWorkScheduleWorkInforDto) => {
                        let time = new Time(new Date(dateOfWorkSchedule.date));
                        let ymd = time.yearMonthDay;
                        let workTypeName = (dateOfWorkSchedule.workTypeCode != null && dateOfWorkSchedule.workTypeName == null) ? dateOfWorkSchedule.workTypeCode + getText("KSU001_22") : dateOfWorkSchedule.workTypeName;
                        let workTimeName = (dateOfWorkSchedule.workTimeCode != null && dateOfWorkSchedule.workTimeName == null) ? dateOfWorkSchedule.workTimeCode + getText("KSU001_22") : dateOfWorkSchedule.workTimeName;
                        objDetailContentDs['_' + ymd] = new ExCell(dateOfWorkSchedule.workTypeCode, workTypeName, dateOfWorkSchedule.workTimeCode, workTimeName);

                        // set Deco background
                        if (dateOfWorkSchedule.achievements == true || dateOfWorkSchedule.needToWork == false) {
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-schedule-uncorrectable", 0));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-schedule-uncorrectable", 1));
                        } else {
                            if (dateOfWorkSchedule.workTypeEditStatus.editStateSetting == 0) {
                                // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-daily-alter-self", 0));
                            }
                            if (dateOfWorkSchedule.workTimeEditStatus.editStateSetting == 1) {
                                //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-daily-alter-other", 1));
                            }
                        }

                        // set Deco text color
                        if (dateOfWorkSchedule.achievements == true) {
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "color-schedule-performance", 0));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "color-schedule-performance", 1));
                        } else {
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.FULL_TIME) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-attendance", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-attendance", 1));
                            }
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.MORNING) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 1));
                            }
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.AFTERNOON) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 1));
                            }
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.HOLIDAY) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-holiday", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-holiday", 1));
                            }
                        }
                    });
                    detailContentDs.push(objDetailContentDs);

                } else if (viewMode == 'time') {
                    
                    objDetailContentDs['sid'] = i;
                    _.each(listWorkScheduleInforByEmp, (dateOfWorkSchedule: IWorkScheduleWorkInforDto) => {
                        // set dataSource
                        let time = new Time(new Date(dateOfWorkSchedule.date));
                        let ymd = time.yearMonthDay;
                        let workTypeName = (dateOfWorkSchedule.workTypeCode != null && dateOfWorkSchedule.workTypeName == null) ? dateOfWorkSchedule.workTypeCode + getText("KSU001_22") : dateOfWorkSchedule.workTypeName;
                        let workTimeName = (dateOfWorkSchedule.workTimeCode != null && dateOfWorkSchedule.workTimeName == null) ? dateOfWorkSchedule.workTimeCode + getText("KSU001_22") : dateOfWorkSchedule.workTimeName;
                        let startTime = formatById("Clock_Short_HM", dateOfWorkSchedule.startTime);
                        let endTime = formatById("Clock_Short_HM", dateOfWorkSchedule.endTime);
                        let workTypeCode = dateOfWorkSchedule.workTypeCode;
                        let workTimeCode = dateOfWorkSchedule.workTimeCode;
                        objDetailContentDs['_' + ymd] = new ExCell(workTypeCode, workTypeName, workTimeCode, workTimeName, startTime, endTime);

                        // set Deco background
                        if (dateOfWorkSchedule.achievements == true || dateOfWorkSchedule.needToWork == false) {
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-schedule-uncorrectable", 0));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-schedule-uncorrectable", 1));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-schedule-uncorrectable", 2));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-schedule-uncorrectable", 3));
                        } else {
                            if (dateOfWorkSchedule.workTypeEditStatus.editStateSetting == 0) {
                                // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-daily-alter-self", 0));
                            }
                            if (dateOfWorkSchedule.workTimeEditStatus.editStateSetting == 1) {
                                //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-daily-alter-other", 1));
                            }
                            if (dateOfWorkSchedule.startTimeEditState.editStateSetting == 2) {
                                //REFLECT_APPLICATION(2), 申請反映
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-daily-reflect-application", 2));
                            }
                            if (dateOfWorkSchedule.endTimeEditState.editStateSetting == 2) {
                                //REFLECT_APPLICATION(2), 申請反映
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "bg-daily-reflect-application", 3));
                            }
                        }

                        // set Deco text color
                        if (dateOfWorkSchedule.achievements == true) {
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "color-schedule-performance", 0));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "color-schedule-performance", 1));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "color-schedule-performance", 2));
                            //detailContentDeco.push(new CellColor('_' + ymd, i, "color-schedule-performance", 3));
                        } else {
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.FULL_TIME) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-attendance", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-attendance", 1));
                            }
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.MORNING) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 1));
                            }
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.AFTERNOON) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-half-day-work", 1));
                            }
                            if (dateOfWorkSchedule.workHolidayCls == AttendanceHolidayAttr.HOLIDAY) {
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-holiday", 0));
                                //detailContentDeco.push(new CellColor('_' + ymd, i, "color-holiday", 1));
                            }
                        }
                    });
                    detailContentDs.push(objDetailContentDs);
                }
            }

            if (data.displayControlPersonalCond == null) {
                // ẩn A9    
                $("#extable").exTable("hideMiddle");
                self.showA9 = false;

            } else {
//                $("#extable").exTable("showMiddle");
                self.showA9 = true;
                _.each(data.listPersonalConditions, (personalCond: IPersonalConditions) => {
                    middleDs.push({ sid: personalCond.sid, team: personalCond.teamName, rank: personalCond.rankName, qualification: personalCond.licenseClassification });
                });
            }

            // set data cho combobox WorkType
            let listWorkType = [];
            _.each(data.listWorkTypeInfo, (emp: IWorkTypeInfomation, i) => {
                listWorkType.push(emp.workTypeDto);
            });
            __viewContext.viewModel.viewAB.listWorkType(listWorkType);


            // set width cho column cho tung mode
            let widthColumn = 0;
            if (viewMode == 'time') {
                widthColumn = 150;
            } else if (viewMode == 'shortName') {
                widthColumn = 75;
            } else if (viewMode == 'shift') {
                widthColumn = 50;
            }

            detailColumns.push({ key: "sid", width: "5px", headerText: "ABC", visible: false });
            objDetailHeaderDs['sid'] = "";
            _.each(data.listDateInfo, (dateInfo: IDateInfo) => {
                self.arrDay.push(new Time(new Date(dateInfo.ymd)));
                let time = new Time(new Date(dateInfo.ymd));
                detailColumns.push({
                    key: "_" + time.yearMonthDay, width: widthColumn + "px", handlerType: "input", dataType: "label/label/time/time", headerControl: "link"
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

                if (dateInfo.isSpecificDay || dateInfo.optCompanyEventName != null || dateInfo.optWorkplaceEventName != null) {
                    objDetailHeaderDs['_' + ymd] = "<div class='header-image-event'></div>";
                    let heightToolTip = 22 + 22 + 22 * dateInfo.listSpecDayNameCompany.length + 22 * dateInfo.listSpecDayNameWorkplace.length + 5; //22 là chiều cao 1 row của table trong tooltip
                    htmlToolTip.push(new HtmlToolTip('_' + ymd, dateInfo.htmlTooltip, heightToolTip));
                } else {
                    objDetailHeaderDs['_' + ymd] = "<div class='header-image-no-event'></div>";
                }
            });
            self.listColorOfHeader(detailHeaderDeco);

            result = {
                leftmostDs: leftmostDs,
                middleDs: middleDs,
                detailColumns: detailColumns,
                objDetailHeaderDs: objDetailHeaderDs,
                detailHeaderDeco: detailHeaderDeco,
                htmlToolTip: htmlToolTip,
                detailContentDs: detailContentDs,
                detailContentDeco: detailContentDeco

            };
            return result;
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
                let userInfor: IUserInfor = JSON.parse(data);

                // A4_4 表示形式の初期選択と画面モード (Chọn default của các hình thức hiển thị và mode màn hình)
                if (userInfor.disPlayFormat == '') {
                    self.selectedModeDisplayInBody('time');
                    self.visibleShiftPalette(false);
                } else {
                    if (userInfor.disPlayFormat == 'shift') {
                        self.selectedModeDisplayInBody('shift');
                        self.visibleShiftPalette(true);
                    } else if (userInfor.disPlayFormat == 'shortName') {
                        self.selectedModeDisplayInBody('shortName');
                        self.visibleShiftPalette(false);
                    } else if (userInfor.disPlayFormat == 'time') {
                        self.selectedModeDisplayInBody('time');
                        self.visibleShiftPalette(false);
                    }
                }

                // A4_12 背景色の初期選択   (Chọn default màu nền)
                self.backgroundColorSelected(userInfor.backgroundColor);

                // get setting height grid
                if (userInfor.gridHeightSelection == 1) {
                    self.selectedTypeHeightExTable(1);
                    self.isEnableInputHeight(false);
                } else {
                    self.heightGridSetting(userInfor.heightGridSetting);
                    self.selectedTypeHeightExTable(2);
                    self.isEnableInputHeight(true);
                }

                // 
                __viewContext.viewModel.viewAC.selectedpalletUnit(userInfor.shiftPalletUnit);
            });
        }

        /**
        * Create exTable
        */
        initExTable(dataBindGrid: any, viewMode: string): void {
            let self = this,
                //Get dates in time period
                currentDay = new Date(),
                bodyHeightMode = "dynamic",
                windowXOccupation = 65,
                windowYOccupation = 328;
            let updateMode = viewMode === 'time' ? 'edit' :  'stick'

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
            let detailContentDeco = dataBindGrid.detailContentDeco;
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
                                let objTooltip = _.filter(htmlToolTip, function(o) { return o.key == ui.columnKey; });
                                if (objTooltip.length > 0) {
                                    let heightToolTip = objTooltip[0].heightToolTip;
                                    ui.tooltip("show", $("<div/>").css({ width: "315px", height: heightToolTip + "px" }).html(objTooltip[0].value));
                                } else {
                                    ui.tooltip("show", $("<div/>").css({ width: "60px", height: 60 + "px" }).html(''));
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
                    }],
                view: function(mode) {
                    switch (mode) {
                        case "shift":
                            return ["shiftName"];
                        case "shortName":
                            return ["workTypeName", "workTimeName"];
                        case "time":
                            return ["workTypeName", "workTimeName", "startTime", "endTime"];
                    }
                },
                fields: ["workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "shiftName", "startTime", "endTime"],
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
                updateMode: updateMode,
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

            $("#extable").exTable("scrollBack", 0, { h: 0 });
            $("#extable").exTable("saveScroll");

            // set height grid theo localStorage đã lưu
            self.setPositionButonDownAndHeightGrid();

            console.log(performance.now() - start);
        }

        updateExTable(dataBindGrid: any, viewMode : string , updateLeftMost : boolean, updateMiddle : boolean, updateDetail : boolean): void {
            let self = this;
            // save scroll's position
            $("#extable").exTable("saveScroll");
            self.stopRequest(false);
            
            // update phan leftMost
            let leftmostDs = dataBindGrid.leftmostDs;
            let leftmostColumns = [{
                key: "codeNameOfEmp", headerText: getText("KSU001_56"), width: "160px", icon: { for: "body", class: "icon-leftmost", width: "25px" },
                css: { whiteSpace: "pre" }, control: "link", handler: function(rData, rowIdx, key) { console.log(rowIdx); },
                headerControl: "link", headerHandler: function() { alert("Link!"); }
            }];

            let leftmostContentUpdate = {
                columns: leftmostColumns,
                dataSource: leftmostDs,
                primaryKey: "sid"
            };
            
            // update Phần Middle
            let middleDs = dataBindGrid.middleDs;
            let middleColumns = [];
            let middleContentDeco = [];
            let middleHeader = {};
            let middleContentUpdate = {};

            middleColumns = [
                { headerText: getText("KSU001_4023"), key: "team", width: "40px", css: { whiteSpace: "none" } },
                { headerText: getText("KSU001_4024"), key: "rank", width: "40px", css: { whiteSpace: "none" } },
                { headerText: getText("KSU001_4025"), key: "qualification", width: "40px", css: { whiteSpace: "none" } }
            ];       

            middleContentUpdate = {
                columns: middleColumns,
                dataSource: middleDs,
                primaryKey: "sid",
                features: [{
                    name: "BodyCellStyle",
                    decorator: middleContentDeco
                }]
            };
             
            // update Phần Detail
            let detailHeaderDeco = dataBindGrid.detailHeaderDeco;
            let detailHeaderDs = [];
            let detailContentDeco = dataBindGrid.detailContentDeco;
            let detailContentDs = dataBindGrid.detailContentDs;
            let detailColumns = dataBindGrid.detailColumns;
            let objDetailHeaderDs = dataBindGrid.objDetailHeaderDs;
            let htmlToolTip = dataBindGrid.htmlToolTip;
            let timeRanges = [];

            //create dataSource for detailHeader
            detailHeaderDs.push(new ExItem(undefined, null, null, null, true, self.arrDay));
            detailHeaderDs.push(objDetailHeaderDs);
            let detailHeaderUpdate = {
                columns: detailColumns,
                dataSource: detailHeaderDs,
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
                                let objTooltip = _.filter(htmlToolTip, function(o) { return o.key == ui.columnKey; });
                                if (objTooltip.length > 0) {
                                    let heightToolTip = objTooltip[0].heightToolTip;
                                    ui.tooltip("show", $("<div/>").css({ width: "315px", height: heightToolTip + "px" }).html(objTooltip[0].value));
                                } else {
                                    ui.tooltip("show", $("<div/>").css({ width: "60px", height: 60 + "px" }).html(''));
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
            
            let detailContentUpdate = {
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
                    }],
                view: function(mode) {
                    switch (mode) {
                        case "shift":
                            return ["shiftName"];
                        case "shortName":
                            return ["workTypeName", "workTimeName"];
                        case "time":
                            return ["workTypeName", "workTimeName", "startTime", "endTime"];
                    }
                },
                fields: ["workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "shiftName", "startTime", "endTime"],
            };
            
            $("#extable").exTable("viewMode", viewMode); 
            $("#extable").exTable("updateMode", 'edit'); 
            if (updateLeftMost) {
                $("#extable").exTable("updateTable", "leftmost", {}, leftmostContentUpdate);
            }
            if (updateMiddle) {
                $("#extable").exTable("updateTable", "middle", {} , middleContentUpdate);
            }
            if (updateDetail) {
                $("#extable").exTable("updateTable", "detail", detailHeaderUpdate, detailContentUpdate);
            }
            $("#extable").exTable("scrollBack", 0, { h: 1050 });
            
        }

        // save setting hight cua grid vao localStorage
        saveHeightGridToLocal() {
            let self = this;

            $('#input-heightExtable').trigger("validate");
            if (nts.uk.ui.errors.hasError())
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
            if (self.indexBtnToLeft % 2 == 0) {
                if (self.showA9) {
                    $("#extable").exTable("hideMiddle");
                }
                $(".toLeft").css("background", "url(../image/toright.png) no-repeat center");
                $(".toLeft").css("margin-left", "185px");
            } else {
                if (self.showA9) {
                    $("#extable").exTable("showMiddle");
                }
                $(".toLeft").css("background", "url(../image/toleft.png) no-repeat center");
                $(".toLeft").css("margin-left", "310px");
            }
            self.indexBtnToLeft = self.indexBtnToLeft + 1;

        }

        toRight() {
            let self = this;
            if (self.indexBtnToRight % 2 == 0) {
                $(".toRight").css("background", "url(../image/toleft.png) no-repeat center");

            } else {
                $(".toRight").css("background", "url(../image/toright.png) no-repeat center");
            }
            self.indexBtnToRight = self.indexBtnToRight + 1;
        }

        toDown() {
            let self = this;
            if (self.indexBtnToDown % 2 == 0) {
                $(".toDown").css("background", "url(../image/toup.png) no-repeat center");

            } else {
                $(".toDown").css("background", "url(../image/todown.png) no-repeat center");
            }
            self.indexBtnToDown = self.indexBtnToDown + 1;
        }


        setPositionButonDownAndHeightGrid() {
            let self = this;
            if (uk.localStorage.getItem(self.KEY).isPresent()) {
                let userInfor = JSON.parse(uk.localStorage.getItem(self.KEY).get());
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
            } else {
                let heightExtable = $("#extable").height();
                let margintop = heightExtable - 52;
                $(".toDown").css({ "margin-top": margintop + 'px' });
            }
        }

        setWidth(): any {
            $(".ex-header-detail").width(window.innerWidth - 572);
            $(".ex-body-detail").width(window.innerWidth - 554);
            $("#extable").width(window.innerWidth - 554);
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
            } else if (self.selectedModeDisplayInBody() == 'time') {


            } else if (self.selectedModeDisplay() == 'shortName') {


            } else if (self.selectedModeDisplay() == 'shift') {


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
            if (self.selectedModeDisplay() == 1 || self.selectedModeDisplay() == 2)
                return;

            let dtMoment = moment(self.dtAft());
            dtMoment.add(1, 'days');
            self.dtPrev(dtMoment.toDate());
            dtMoment = dtMoment.add(1, 'months');
            dtMoment.subtract(1, 'days');
            self.dtAft(dtMoment.toDate());
            self.stopRequest(false);
            
            let self = this,
                dfd = $.Deferred(),
                viewMode = self.selectedModeDisplayInBody();

            let param = {
                viewMode: viewMode
            };
            service.getDataNextMonth(param).done((data: IDataStartScreen) => {
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart();
                // set data Header
                self.bindingToHeader(data);
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, viewMode);

                self.updateExTable(dataBindGrid, viewMode, false, true, true);
                dfd.resolve();
                self.stopRequest(true);
            }).fail(function() {
                dfd.reject();
                self.stopRequest(true);
            });
            return dfd.promise();
        }

        /**
        * come back a month
        */
        backMonth(): void {
            let self = this;
            if (self.selectedModeDisplay() == 1 || self.selectedModeDisplay() == 2)
                return;
            
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
                    
            self.stopRequest(false);
            let self = this,
                dfd = $.Deferred(),
                viewMode = self.selectedModeDisplayInBody();

            let param = {
                viewMode: viewMode
            };
            service.getDataPreMonth(param).done((data: IDataStartScreen) => {
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart();
                // set data Header
                self.bindingToHeader(data);
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, viewMode);

                self.updateExTable(dataBindGrid, viewMode, false, true, true);
                dfd.resolve();
                self.stopRequest(true);
            }).fail(function() {
                dfd.reject();
                self.stopRequest(true);
            });
            return dfd.promise();
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
            self.mode('edit');
            // set color button
            $(".editMode").addClass("btnControlSelected").removeClass("btnControlUnSelected");
            $(".confirmMode").addClass("btnControlUnSelected").removeClass("btnControlSelected");

            self.removeClass();

            // set enable btn A7_1, A7_2, A7_3, A7_4, A7_5
            self.enableBtnPaste(true);
            self.enableBtnCoppy(true);
            if (self.selectedModeDisplayInBody() == 'time') {
                self.visibleBtnInput(true);
                self.enableBtnInput(true);
            } else {
                self.visibleBtnInput(false);
                self.enableBtnInput(false);
            }

            self.visibleBtnUndo(true);
            self.visibleBtnRedo(true);
            self.enableBtnUndo(false);
            self.enableBtnRedo(false);
            self.enableHelpBtn(true);

            nts.uk.ui.block.clear();
        }

        confirmMode() {
            let self = this;
            nts.uk.ui.block.grayout();
            self.mode('confirm');
            // set color button
            $(".confirmMode").addClass("btnControlSelected").removeClass("btnControlUnSelected");
            $(".editMode").addClass("btnControlUnSelected").removeClass("btnControlSelected");

            self.removeClass();

            // set enable btn A7_1, A7_2,, A7_3, A7_4, A7_5
            self.enableBtnPaste(false);
            self.enableBtnCoppy(false);

            if (self.selectedModeDisplayInBody() == 'time') {
                self.visibleBtnInput(true);
                self.enableBtnInput(false);
            } else {
                self.visibleBtnInput(false);
                self.enableBtnInput(false);
            }

            self.visibleBtnUndo(true);
            self.visibleBtnRedo(true);
            self.enableBtnUndo(false);
            self.enableBtnRedo(false);
            self.enableHelpBtn(false);

            nts.uk.ui.block.clear();
        }

        removeClass() {
            let self = this;
            $("#paste").removeClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#coppy").removeClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#input").removeClass("btnControlUnSelected").removeClass("btnControlSelected");
        }

        /**
         * paste data on cell
         * stick
         */
        pasteData(): void {
            let self = this;
            if (self.mode() == 'confirm')
                return;
            nts.uk.ui.block.grayout();
            $("#paste").addClass("btnControlSelected").removeClass("btnControlUnSelected");
            $("#coppy").addClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#input").addClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#extable").exTable("updateMode", "stick");
            if (self.selectedModeDisplayInBody() == 'time') {
                $("#extable").exTable("stickData", [new ExCell("111", "出勤1", "112", "通常８ｈ0", "9:00", "20:00")]);
            } else if (self.selectedModeDisplayInBody() == 'shortName') {
                $("#extable").exTable("stickData", [new ExCell("888", "出勤8", "999", "通常８ｈ9")]);
            } else if (self.selectedModeDisplayInBody() == 'shift') {
                $("#extable").exTable("stickData", [
                new ExCell('', '', '', '', '', '', '1'),
                new ExCell('', '', '', '', '', '', '2'),
                new ExCell('', '', '', '', '', '', '3'),
                new ExCell('', '', '', '', '', '', '4'),
                new ExCell('', '', '', '', '', '', '5'),
                new ExCell('', '', '', '', '', '', '6'),
                new ExCell('', '', '', '', '', '', '7'),
                new ExCell('', '', '', '', '', '', '8'),
                new ExCell('', '', '', '', '', '', '9'),
                new ExCell('', '', '', '', '', '', '10'),
                new ExCell('', '', '', '', '', '', '11'),
                new ExCell('', '', '', '', '', '', '12'),
                new ExCell('', '', '', '', '', '', '13'),
                new ExCell('', '', '', '', '', '', '14'),
                new ExCell('', '', '', '', '', '', '15'),
                new ExCell('', '', '', '', '', '', '16'),
                new ExCell('', '', '', '', '', '', '17'),
                new ExCell('', '', '', '', '', '', '18'),
                new ExCell('', '', '', '', '', '', '19'),
                new ExCell('', '', '', '', '', '', '20'),
                new ExCell('', '', '', '', '', '', '21'),
                new ExCell('', '', '', '', '', '', '22'),
                new ExCell('', '', '', '', '', '', '23'),
                new ExCell('', '', '', '', '', '', '24'),
                new ExCell('', '', '', '', '', '', '25'),
                new ExCell('', '', '', '', '', '', '26'),
                new ExCell('', '', '', '', '', '', '27'),
                new ExCell('', '', '', '', '', '', '28'),
                new ExCell('', '', '', '', '', '', '29'),
                new ExCell('', '', '', '', '', '', '30'),
                new ExCell('', '', '', '', '', '', '31')]);
            }
            
            nts.uk.ui.block.clear();
        }

        /**
         * copy data on cell
         */
        coppyData(): void {
            let self = this;
            if (self.mode() == 'confirm')
                return;
            nts.uk.ui.block.grayout();
            $("#paste").addClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#coppy").addClass("btnControlSelected").removeClass("btnControlUnSelected");
            $("#input").addClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#extable").exTable("updateMode", "copyPaste");
            nts.uk.ui.block.clear();
        }

        /**
         * copy data on cell
         */
        inputData(): void {
            let self = this;
            if (self.mode() == 'confirm')
                return;
            nts.uk.ui.block.grayout();
            $("#paste").addClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#coppy").addClass("btnControlUnSelected").removeClass("btnControlSelected");
            $("#input").addClass("btnControlSelected").removeClass("btnControlUnSelected");

            $("#extable").exTable("updateMode", "edit");
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
                    selectedLinkButton == null || selectedLinkButton == undefined ? ko.observable(0) : selectedLinkButton);
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
                    selectedLinkButton == null || selectedLinkButton == undefined ? ko.observable(0) : selectedLinkButton);
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
                    startDate: self.dateTimePrev(),
                    endDate: self.dateTimeAfter()
                },
                param = {
                    pgid: __viewContext.program.programId,
                    functionId: 1,
                    listEmployeeId: self.listSid(),
                    period: period,
                    displayFormat: self.displayFormat()
                };
            setShared("CDL027Params", param);
            openDialog('com', "/view/cdl/027/a/index.xhtml");
        }

        compareArrByRowIndexAndColumnKey(a: any, b: any): any {
            return a.rowIndex == b.rowIndex && a.comlumnKey == b.comlumnKey;
        }
    }

    export enum viewMode {
        SYMBOL = 1,
        SHORTNAME = 2,
        TIME = 3
    }

    export enum TypeHeightExTable {
        DEFAULT = 1,
        SETTING = 2
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
        shiftName: string;
        startTime: any;
        endTime: any;
        constructor(workTypeCode: string, workTypeName: string, workTimeCode: string, workTimeName: string, startTime?: string, endTime?: string, shiftName?: any) {
            this.workTypeCode = workTypeCode;
            this.workTypeName = workTypeName;
            this.workTimeCode = workTimeCode;
            this.workTimeName = workTimeName;
            this.shiftName = shiftName !== null ? shiftName : '';
            this.startTime = ( startTime == undefined || startTime == null ) ? '' : startTime;
            this.endTime = ( endTime == undefined || endTime == null ) ? '' : endTime;
        }
    }


    interface IDataStartScreen {
        dataBasicDto: IDataBasicDto,
        displayControlPersonalCond: IDisplayControlPersonalCond,
        listDateInfo: Array<IDateInfo>,
        listEmpInfo: Array<IEmpInfo>,
        listPersonalConditions: Array<IPersonalConditions>,
        listWorkTypeInfo: Array<IWorkTypeInfomation>,
        listWorkScheduleWorkInfor: Array<IWorkScheduleWorkInforDto>,
        listWorkScheduleShift: Array<IWorkScheduleShiftforDto>,
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
        optCompanyEventName: string,
        optWorkplaceEventName: string,
        listSpecDayNameCompany: Array<string>,
        listSpecDayNameWorkplace: Array<string>,
        isToday: boolean,
        htmlTooltip: string
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
        licenseClassification: string
    }

    class HtmlToolTip {
        key: string;
        value: string;
        heightToolTip: number;
        constructor(key: string, value: string, heightToolTip: number) {
            this.key = key;
            this.value = value;
            this.heightToolTip = heightToolTip;
        }
    }

    interface IWorkTypeInfomation {
        workTypeDto: IWorkTypeDto,    // 勤務種類選択 - 勤務種類 
        workTimeSetting: number,          // 必須任意不要区分    
        attHdAtr: number,          // 出勤休日区分
    }

    interface IWorkTypeDto {
        workTypeCode: string, // 勤務種類コード - コード
        name: string,         // 勤務種類名称  - 表示名
        memo: string          // 勤務種類備考  - 備考
    }

    interface IEditStateOfDailyAttdDto {
        attendanceItemId: number;
        editStateSetting: number; // enum AttendanceHolidayAttr
        date: Date;
    }

    interface IWorkScheduleWorkInforDto {
        employeeId: string; // 社員ID
        date: Date; // 年月日
        haveData: boolean; // データがあるか
        achievements: boolean; // 実績か
        confirmed: boolean; // 確定済みか
        needToWork: boolean; // 勤務予定が必要か
        supportCategory: number; // 応援か tu 1-> 5
        //Khu vực Optional
        workTypeCode: string; // 勤務種類コード 
        workTypeName: string; // 勤務種類名
        workTypeEditStatus: IEditStateOfDailyAttdDto; // 勤務種類編集状態
        workTimeCode: string; // 就業時間帯コード
        workTimeName: string; // 就業時間帯名
        workTimeEditStatus: IEditStateOfDailyAttdDto; // 就業時間帯編集状態
        startTime: number; // 開始時刻
        startTimeEditState: IEditStateOfDailyAttdDto; // 開始時刻編集状態
        endTime: number; // 終了時刻
        endTimeEditState: IEditStateOfDailyAttdDto; // 終了時刻編集状態
        workHolidayCls: number; // 出勤休日区分
    }

    interface IWorkScheduleShiftforDto {
        employeeId: string; // 社員ID
        date: Date; // 年月日
        haveData: boolean; // データがあるか
        achievements: boolean; // 実績か
        confirmed: boolean; // 確定済みか
        needToWork: boolean; // 勤務予定が必要か
        supportCategory: number; // 応援か tu 1-> 5
        //Khu vực Optional
        shiftCode: string; // シフトコード
        shiftName: string; // シフト名称
        shiftEditState: IEditStateOfDailyAttdDto; // シフトの編集状態
        workHolidayCls: number; // 出勤休日区分
        isActive: boolean;
    }

    enum AttendanceHolidayAttr {
        FULL_TIME = 3, //(3, "１日出勤系"),
        MORNING = 1, //(1, "午前出勤系"),
        AFTERNOON = 2, //(2, "午後出勤系"),
        HOLIDAY = 0, //(0, "１日休日系");
    }

    interface IUserInfor {
        disPlayFormat: string;
        backgroundColor: number; // 背景色
        shiftPalletUnit: number;
        shiftPalettePageNumber: number;
        shiftPalletPositionNumber: number;
        gridHeightSelection: number;
        heightGridSetting: number;
        workplaceId: string;
        workPlaceName: string;
        workType: {};
        workTime: {}
    }

}