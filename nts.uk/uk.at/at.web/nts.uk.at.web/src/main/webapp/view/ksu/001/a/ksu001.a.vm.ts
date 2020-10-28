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
    import util = nts.uk.util;

    /**
     * load screen O->Q->A
     * reference file a.start.ts
     */
    export class ScreenModel {

        employeeIdLogin: string = __viewContext.user.employeeId;
        key: string;

        empItems: KnockoutObservableArray<PersonModel> = ko.observableArray([]);
        
        visibleShiftPalette: KnockoutObservable<boolean> = ko.observable(true);
        mode: KnockoutObservable<string> = ko.observable('edit'); // edit || confirm 
        showA9: boolean;

        // A4 popup-area6 
        // A4_4
        selectedModeDisplayInBody: KnockoutObservable<number> = ko.observable(undefined);

        // A4_7
        achievementDisplaySelected: KnockoutObservable<number> = ko.observable(undefined); // 1 || 2

        // A4_12
        backgroundColorSelected: KnockoutObservable<string> = ko.observable(undefined);  // 0 || 1
        showComboboxA4_12: KnockoutObservable<boolean> = ko.observable(true);

        isEnableCompareMonth: KnockoutObservable<boolean> = ko.observable(true);

        popupVal: KnockoutObservable<string> = ko.observable('');
        selectedDate: KnockoutObservable<string> = ko.observable('');

        //Date time A3_1

        currentDate: Date = new Date();
        dtPrev: KnockoutObservable<Date> = ko.observable(new Date()); // A3_1_2
        dtAft: KnockoutObservable<Date> = ko.observable(new Date());  // A3_1_4
        dateTimePrev: KnockoutObservable<string>;
        dateTimeAfter: KnockoutObservable<string>;

        //Switch  A3_2
        selectedDisplayPeriod: KnockoutObservable<number> = ko.observable(1);

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

        enableBtnPaste: KnockoutObservable<boolean>  = ko.observable(true);
        enableBtnCoppy: KnockoutObservable<boolean>  = ko.observable(true);
        enableBtnInput: KnockoutObservable<boolean>  = ko.observable(true);
        visibleBtnInput: KnockoutObservable<boolean> = ko.observable(true);
        enableBtnUndo: KnockoutObservable<boolean>   = ko.observable(true);
        enableBtnRedo: KnockoutObservable<boolean>   = ko.observable(true);
        visibleBtnUndo: KnockoutObservable<boolean>  = ko.observable(true);
        visibleBtnRedo: KnockoutObservable<boolean>  = ko.observable(true);
        enableHelpBtn: KnockoutObservable<boolean>   = ko.observable(true);

        arrDay: Time[] = [];
        listSid: KnockoutObservableArray<string> = ko.observableArray([]);
        listEmpData = [];
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
        dataCell: any; // data để paste vào grid
        listPageInfo : any;
        targetShiftPalette : any;
        workPlaceId : any;
        arrListCellLock = [];
        listCellNotEditBg = [];
        listCellNotEditColor = [];
        detailContentDeco = [];
        detailColumns = [];
        detailContentDs = [];
        dataSource = {};
        
        // data grid
        listEmpInfo = [];
        listWorkScheduleWorkInfor  = [];
        listWorkScheduleShift      = [];
        listPersonalConditions     = [];
        displayControlPersonalCond = {};
        listDateInfo     = [];
        
        
        showTeamCol = false;
        showRankCol = false;
        showQualificCol = false;
        widthMid : number = 0;
        numberCellsUpdatedAfterUndo = 0;
        pathToLeft = '';
        pathToRight = '';
        pathToDown = '';
        pathToUp = '';
        
        // param kcp015
        hasParams: KnockoutObservable<boolean> = ko.observable(true);
        visibleA31: KnockoutObservable<boolean> = ko.observable(true);
        visibleA32: KnockoutObservable<boolean> = ko.observable(true);
        visibleA33: KnockoutObservable<boolean> = ko.observable(true);
        visibleA34: KnockoutObservable<boolean> = ko.observable(true);
        visibleA35: KnockoutObservable<boolean> = ko.observable(true);
        visibleA36: KnockoutObservable<boolean> = ko.observable(true);
        baseDate: KnockoutObservable<string> = ko.observable('');
        sids: KnockoutObservableArray<any> = ko.observableArray([]);
        
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
            
            self.selectedDisplayPeriod.subscribe(function(value) { // value = 1 || 2 || 3
                if (value == null || value == undefined || value == 2)
                    return;
                if (value == 3) {
                    let date = new Date(self.dateTimeAfter());
                    let firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
                    let lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
                    
                    let startDate = moment(firstDay).format('YYYY/MM/DD');
                    let endDate   = moment(lastDay).format('YYYY/MM/DD');
                    
                    if ((self.dateTimeAfter() != endDate) || (self.dateTimePrev() != startDate)) {
                        self.getDataWhenChangeModePeriod(startDate, endDate);
                    }
                }
            });

            self.achievementDisplaySelected.subscribe(function(newValue) {
                if(newValue == null || newValue == undefined)
                    return;
                
                if(self.flag == true)
                    return;
                
                if (newValue == 1) {
                    self.isEnableCompareMonth(true);
                    
                } else {
                    self.isEnableCompareMonth(false);
                }
                uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                    let userInfor = JSON.parse(data);
                    userInfor.achievementDisplaySelected = (newValue == 1) ? true : false;
                    uk.localStorage.setItemAsJson(self.KEY, userInfor);
                });
                self.stopRequest(false);
                let viewMode = self.selectedModeDisplayInBody();
                self.getNewData(viewMode).done(() => {
                    if (self.mode() == 'confirm') {
                        $("#extable").exTable("updateMode", "determine");
                        $(".editMode").addClass("A6_not_hover").removeClass("A6_hover");
                        $(".confirmMode").addClass("A6_hover").removeClass("A6_not_hover");
                        if (self.selectedModeDisplayInBody() == 'time' || self.selectedModeDisplayInBody() == 'shortName') {
                            // disable combobox workType, workTime
                            __viewContext.viewModel.viewAB.enableListWorkType(false);
                            __viewContext.viewModel.viewAB.disabled(true);
                        } else {
                            self.shiftPalletControlDisable();
                        }
                    }
                    
                    self.stopRequest(true);
                }).fail(function() {
                    nts.uk.ui.block.clear();
                });
            });

            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor: IUserInfor = JSON.parse(data);
                // A4_4 表示形式の初期選択と画面モード (Chọn default của các hình thức hiển thị và mode màn hình)
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
            }).ifEmpty((data) => {
                self.selectedModeDisplayInBody('time');
                self.visibleShiftPalette(false);
            });

            self.selectedModeDisplayInBody.subscribe(function(viewMode) {
                if (viewMode == null)
                    return;
                if(self.flag == true)
                    return;
                
                nts.uk.ui.errors.clearAll();
                self.removeClass();
                self.stopRequest(false);
                // close screen O1 when change mode
                if (viewMode == 'shift') { // mode シフト表示   
                    self.shiftModeStart().done(() => {
                        self.editMode();
                        self.stopRequest(true);
                    });
                } else if (viewMode == 'shortName') { // mode 略名表示
                    self.shortNameModeStart().done(() => {
                        self.editMode();
                        self.stopRequest(true);
                    });
                } else if (viewMode == 'time') {  // mode 勤務表示 
                    self.timeModeStart().done(() => {
                        self.editMode();
                        self.stopRequest(true);
                    });
                }
            });

            self.backgroundColorSelected.subscribe((value) => {
                if (util.isNullOrUndefined(value) || util.isNullOrEmpty(value))
                    return;
                // update lại màu background phần detail
                let self = this;
                if (self.flag == true)
                    return;
                let shiftMasterWithWorkStyleLst;
                let detailContentDeco = [];

                uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                    let userInfor: IUserInfor = JSON.parse(data);
                    userInfor.backgroundColor = value;
                    shiftMasterWithWorkStyleLst = userInfor.shiftMasterWithWorkStyleLst;
                    uk.localStorage.setItemAsJson(self.KEY, userInfor);
                });

                // lay data tai thời điểm chuyển combobox
                let dataSource = $("#extable").exTable('dataSource', 'detail').body;

                for (let i = 0; i < self.listEmpInfo.length; i++) {
                    let rowId = i + '';
                    let emp: IEmpInfo = self.listEmpInfo[i];
                    let objDetailContentDs = new Object();

                    let listWorkScheduleShiftByEmp: Array<IWorkScheduleShiftInforDto> = _.filter(self.listWorkScheduleShift, function(workSchedul: IWorkScheduleShiftInforDto) { return workSchedul.employeeId === emp.employeeId });
                    let dataOnGrid: any = _.filter(dataSource, function(workSchedul: any) { return workSchedul.employeeId === emp.employeeId })[0];

                    // set data to detailContent : datasource v        
                    objDetailContentDs['sid'] = i.toString();
                    objDetailContentDs['employeeId'] = emp.employeeId;
                    let listWorkScheduleShiftByEmpSort = _.orderBy(listWorkScheduleShiftByEmp, ['date'], ['asc']);

                    let item = uk.localStorage.getItem(self.KEY);
                    let userInfor: IUserInfor = JSON.parse(item.get());
                    let shiftMasterWithWorkStyleLst = userInfor.shiftMasterWithWorkStyleLst;

                    for (let j = 0; j < listWorkScheduleShiftByEmpSort.length; j++) {
                        let cell: IWorkScheduleShiftInforDto = listWorkScheduleShiftByEmpSort[j];
                        let rowOfSelf = cell.employeeId == self.employeeIdLogin ? true : false;
                        let time = new Time(new Date(cell.date));
                        let ymd = time.yearMonthDay;

                        let dataCellOnGrid = dataOnGrid['_' + ymd];

                        // set Deco background
                        if (value == 1) {
                            let shiftMasterWithWorkStyleLst = userInfor.shiftMasterWithWorkStyleLst;
                            if (dataCellOnGrid.shiftCode != null) {
                                let objShiftMasterWithWorkStyle = _.filter(shiftMasterWithWorkStyleLst, function(o) { return o.shiftMasterCode == dataCellOnGrid.shiftCode; });
                                if (objShiftMasterWithWorkStyle.length > 0) {
                                    let color = '#' + objShiftMasterWithWorkStyle[0].color;
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, color, 0));
                                } else {
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "background-white", 0));
                                }
                            }
                        } else if (value == 0) {
                            // set Deco BackGround
                            if (rowOfSelf) {
                                //detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 0));
                            } else {
                                //detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 0));
                            }
                        }

                        // set Deco text color
                        // A10_color⑥ スケジュール明細の文字色  (Màu chữ của "Schedule detail")  
                        if (util.isNullOrUndefined(dataCellOnGrid.shiftCode) || util.isNullOrEmpty(dataCellOnGrid.shiftCode)) {
                            // デフォルト（黒）  Default (black)
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-default", 0));
                        } else {
                            let cellCanNotEdit = _.filter(self.listCellNotEditColor, function(o) { return o.columnKey == '_' + ymd && o.rowId == rowId; });
                            if (cellCanNotEdit.length > 0 && value == 0) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 0));
                            } else {
                                let workStyle = self.getWorkStyle(shiftMasterWithWorkStyleLst, dataCellOnGrid.shiftCode);
                                let cellColor = self.setColorCell(workStyle, ymd, rowId);
                                detailContentDeco.push(cellColor);
                            }
                        }
                    };
                }

                self.updateExTableWhenChangeModeBg($.merge(detailContentDeco, self.listCellNotEditBg));

                self.setIconEventHeader();

                let item = uk.localStorage.getItem(self.KEY);
                let userInfor: IUserInfor = JSON.parse(item.get());
                if (userInfor.updateMode == 'copyPaste') {
                    self.coppyData();
                }
                self.stopRequest(true);
            });

            self.stopRequest.subscribe(function(value) {
                if (!value) {
                    nts.uk.ui.block.grayout();
                } else {
                    nts.uk.ui.block.clear();
                }
            });
         
            self.showComboboxA4_12 = ko.computed(function() {
                return self.selectedModeDisplayInBody() == 'shift' && self.mode() == 'edit' ;
            }, this);
            
            self.dataCell = {};

            $("#extable").on("extablecellupdated", (dataCell) => {
                let itemLocal = uk.localStorage.getItem(self.KEY);
                let userInfor: IUserInfor = JSON.parse(itemLocal.get());
                if (userInfor.disPlayFormat == 'time' && userInfor.updateMode == 'edit') {
                    let strTime = dataCell.originalEvent.detail.value.startTime;
                    let endTime = dataCell.originalEvent.detail.value.endTime;
                    let startTimeCal = nts.uk.time.minutesBased.duration.parseString(strTime).toValue();
                    let endTimeCal = nts.uk.time.minutesBased.duration.parseString(endTime).toValue();

                    if (startTimeCal < 0 && endTimeCal < 0) {
                        startTimeCal = startTimeCal * -1;
                        endTimeCal = endTimeCal * -1;
                    }

                    if (startTimeCal >= endTimeCal) {
                        nts.uk.ui.dialog.alertError({ messageId: 'Msg_54' });
                    }

                    let param = {
                        workTypeCode: '',
                        workTimeCode: '',
                        startTime: nts.uk.time.minutesBased.duration.parseString(strTime).toValue(),
                        endTime:   nts.uk.time.minutesBased.duration.parseString(endTime).toValue(),
                    }
                    self.inputDataValidate(param).done(() => {
                        self.checkExitCellUpdated();
                    });
                } else {
                    self.checkExitCellUpdated();
                }
            });
            
            $("#extable").on("extablerowupdated", (dataCell) => {
                self.checkExitCellUpdated();
            });
            
            self.pathToLeft  = nts.uk.request.location.siteRoot.mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/").mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/").mergeRelativePath("152.png").serialize();
            self.pathToRight = nts.uk.request.location.siteRoot.mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/").mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/").mergeRelativePath("153.png").serialize();
            self.pathToDown  = nts.uk.request.location.siteRoot.mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/").mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/").mergeRelativePath("150.png").serialize();
            self.pathToUp    = nts.uk.request.location.siteRoot.mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/").mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/").mergeRelativePath("151.png").serialize();
        }
        // end constructor
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = {};
            if (item.isPresent()) {
                userInfor = JSON.parse(item.get());
            }
            let viewMode = item.isPresent() ? userInfor.disPlayFormat : 'time';
            let updateMode = item.isPresent() ? userInfor.updateMode : 'stick';
            
            let param = {
                viewMode: viewMode,
                startDate: null,
                endDate  : null,
                shiftPalletUnit: item.isPresent() ? userInfor.shiftPalletUnit : 1, // 1: company , 2 : workPlace 
                pageNumberCom: item.isPresent() ? userInfor.shiftPalettePageNumberCom : 1,
                pageNumberOrg: item.isPresent() ? userInfor.shiftPalettePageNumberOrg : 1,
                getActualData: false,
                listShiftMasterNotNeedGetNew: item.isPresent() ? userInfor.shiftMasterWithWorkStyleLst : [], // List of shifts không cần lấy mới
                listSid: self.listSid(),
                unit: item.isPresent() ? userInfor.unit : 0,
                workplaceId     : null,
                workplaceGroupId: null,
            }

            service.getDataStartScreen(param).done((data: IDataStartScreen) => {
                console.log('userInfo');
                console.log(data.dataBasicDto);

                self.saveDataGrid(data);

                // khởi tạo data localStorage khi khởi động lần đầu.
                self.creatDataLocalStorege(data.dataBasicDto);
                
                self.setHeightScreen();
                
                __viewContext.viewModel.viewAB.workplaceIdKCP013(data.dataBasicDto.unit == 0 ? data.dataBasicDto.workplaceId : data.dataBasicDto.workplaceGroupId);
                
                self.getSettingDisplayWhenStart(viewMode, true);
                
                 if (viewMode == 'shift') {
                    self.saveShiftMasterToLocalStorage(data.shiftMasterWithWorkStyleLst);
                    self.bingdingToShiftPallet(data);
                }
                
                // set data Header
                self.bindingToHeader(data);
                
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, viewMode);
                self.initExTable(dataBindGrid, viewMode, updateMode);
                
                $(".editMode").addClass("btnControlSelected").removeClass("btnControlUnSelected");
                $(".confirmMode").addClass("btnControlUnSelected").removeClass("btnControlSelected");
                self.setUpdateMode();
                self.setDataWorkType(data.listWorkTypeInfo);
                self.checkEnableCombWTime();
                self.setPositionButonToRightToLeft();
                self.flag = false;
                dfd.resolve();
            }).fail(function(error) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        getDataWhenChangeModePeriod(startDate, endDate) {
            let self = this;
            let viewMode = self.selectedModeDisplayInBody();
            self.stopRequest(false);
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());

            let param = {
                viewMode: self.selectedModeDisplayInBody(), // time | shortName | shift
                startDate: startDate,
                endDate: endDate,
                workplaceId: userInfor.workplaceId,
                workplaceGroupId: userInfor.workplaceGroupId,
                unit: userInfor.unit,
                getActualData: item.isPresent() ? userInfor.achievementDisplaySelected : false,
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst,
                listSid: self.listSid()
            };

            service.getDataWhenChangeModePeriod(param).done((data: any) => {
                if (userInfor.disPlayFormat == 'shift') {
                    self.saveShiftMasterToLocalStorage(data.shiftMasterWithWorkStyleLst);
                }
                
                self.saveDataGrid(data);

                self.dtPrev(data.dataBasicDto.startDate);
                self.dtAft(data.dataBasicDto.endDate);
                
                let dataGrid: any = {
                    listDateInfo: data.listDateInfo,
                    listEmpInfo: data.listEmpInfo,
                    displayControlPersonalCond: data.displayControlPersonalCond,
                    listPersonalConditions: data.listPersonalConditions,
                    listWorkScheduleWorkInfor: data.listWorkScheduleWorkInfor,
                    listWorkScheduleShift: data.listWorkScheduleShift
                }
                let dataBindGrid = self.convertDataToGrid(dataGrid, self.selectedModeDisplayInBody());

                // remove va tao lai grid
                self.destroyAndCreateGrid(dataBindGrid, self.selectedModeDisplayInBody());

                self.setUpdateMode();
                
                if (self.mode() == 'confirm') {
                    $("#extable").exTable("updateMode", "determine");
                }

                self.setPositionButonToRightToLeft();
                
                // fix bug khong coppyPaste dc 
                if (userInfor.updateMode == 'copyPaste') {
                    $("#extable").exTable("updateMode", "stick");
                    $("#extable").exTable("updateMode", "copyPaste");
                }

                self.stopRequest(true);
            }).fail(function(error) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alertError(error);
            });
        }

        getNewData(viewMode): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            if (viewMode == 'shift') { // mode シフト表示   
                self.shiftModeStart().done(() => {
                    dfd.resolve();
                    self.stopRequest(true);
                }).fail(function() {
                    dfd.reject();
                });
            } else if (viewMode == 'shortName') { // mode 略名表示
                self.shortNameModeStart().done(() => {
                    dfd.resolve();
                    self.stopRequest(true);
                }).fail(function() {
                    dfd.reject();
                });
            } else if (viewMode == 'time') {  // mode 勤務表示 
                self.timeModeStart().done(() => {
                    dfd.resolve();
                    self.stopRequest(true);
                }).fail(function() {
                    dfd.reject();
                });
            }
            return dfd.promise();
        }

        creatDataLocalStorege(dataBasic: IDataBasicDto) {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            if (!item.isPresent()) {
                let userInfor: IUserInfor = {};
                userInfor.disPlayFormat = self.selectedModeDisplayInBody();
                userInfor.backgroundColor = 0; // 0 : 通常; 1: シフト   // mau nền default của shiftMode
                userInfor.achievementDisplaySelected = false;
                userInfor.shiftPalletUnit = 1;
                userInfor.shiftPalettePageNumberCom = 1;
                userInfor.shiftPalletPositionNumberCom = { column : 0 , row : 0 };
                userInfor.shiftPalettePageNumberOrg = 1;
                userInfor.shiftPalletPositionNumberOrg = { column : 0 , row : 0 };
                userInfor.gridHeightSelection = 1;
                userInfor.heightGridSetting = '';
                userInfor.unit = dataBasic.unit;
                userInfor.workplaceId= dataBasic.workplaceId;
                userInfor.workplaceGroupId = dataBasic.workplaceGroupId;
                userInfor.workPlaceName= dataBasic.targetOrganizationName;
                userInfor.code = dataBasic.code;
                userInfor.workType = {}; 
                userInfor.workTime = {}; 
                userInfor.shiftMasterWithWorkStyleLst = [];
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            }
        }
        
        setUpdateMode() {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = {};
            if (item.isPresent()) {
                userInfor = JSON.parse(item.get());
            }
            let updateMode = item.isPresent() ? (userInfor.updateMode == undefined ? 'stick' : userInfor.updateMode) : 'stick';

            if (updateMode == 'stick') {
                self.pasteData();
            } else if (updateMode == 'copyPaste') {
                self.coppyData();
            } else if (updateMode == 'edit') {
                self.inputData();
            }
        }

        shiftModeStart(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());
            let param = {
                viewMode : 'shift',
                startDate: self.dateTimePrev() ,
                endDate  : self.dateTimeAfter(),
                shiftPalletUnit : userInfor.shiftPalletUnit, // 1: company , 2 : workPlace 
                pageNumberCom   : userInfor.shiftPalettePageNumberCom,
                pageNumberOrg   : userInfor.shiftPalettePageNumberOrg,
                getActualData   : item.isPresent() ? userInfor.achievementDisplaySelected : false, 
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst, // List of shifts không cần lấy mới
                listSid: self.listSid(),
                unit: item.isPresent() ? userInfor.unit : 0,
                workplaceId     : userInfor.workplaceId,
                workplaceGroupId: userInfor.workplaceGroupId
            };
            self.saveModeGridToLocalStorege('shift');
            self.visibleShiftPalette(true);
            self.visibleBtnInput(false);
            service.getDataOfShiftMode(param).done((data: IDataStartScreen) => {
                
                self.saveDataGrid(data);
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart('shift', false);
                //WORKPLACE(0), //WORKPLACE_GROUP(1);
                __viewContext.viewModel.viewAC.workplaceModeName(data.dataBasicDto.designation);
                $($("#Aa1_2 > button")[1]).html(data.dataBasicDto.designation);

                self.saveShiftMasterToLocalStorage(data.shiftMasterWithWorkStyleLst);
                // set data shiftPallet
                __viewContext.viewModel.viewAC.flag = false;
                
                __viewContext.viewModel.viewAC.selectedpalletUnit(userInfor.shiftPalletUnit);
                if(userInfor.shiftPalletUnit == 1){
                    __viewContext.viewModel.viewAC.handleInitCom(
                        data.listPageInfo,
                        data.targetShiftPalette.shiftPalletCom,
                        userInfor.shiftPalettePageNumberCom);
                }else{
                    __viewContext.viewModel.viewAC.handleInitWkp(
                        data.listPageInfo,
                        data.targetShiftPalette.shiftPalletWorkPlace,
                        userInfor.shiftPalettePageNumberOrg);
                }
                __viewContext.viewModel.viewAC.flag = true;
                
                // check enable or disable tbaleButton
                self.checkEnabDisableTblBtn();
                
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, 'shift');
                
                // remove va tao lai grid
                self.destroyAndCreateGrid(dataBindGrid, 'shift');
                
                self.pasteData();
                
                self.setPositionButonToRightToLeft();
                
                dfd.resolve();
            }).fail(function(error) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }

        shortNameModeStart(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());
            let setWorkTypeTime = false;
            let param = {
                viewMode: 'shortName',
                startDate: self.dateTimePrev(),
                endDate:   self.dateTimeAfter() ,
                getActualData: item.isPresent() ? userInfor.achievementDisplaySelected : false,
                unit: item.isPresent() ? userInfor.unit : 0,
                workplaceId     : userInfor.workplaceId,
                workplaceGroupId: userInfor.workplaceGroupId
            };
            
            if (userInfor.disPlayFormat == 'shift') {
                setWorkTypeTime = true;
            } else {
                setWorkTypeTime = false;
            }
            
            self.visibleShiftPalette(false);
            self.visibleBtnInput(false);
            self.saveModeGridToLocalStorege('shortName');
            
            service.getDataOfShortNameMode(param).done((data: IDataStartScreen) => {
                if (setWorkTypeTime) {
                    self.setWorkTypeTime(data.listWorkTypeInfo, userInfor);
                }
                
                self.saveDataGrid(data);
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart('shortName',false);
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, 'shortName');
                // remove va tao lai grid
                self.destroyAndCreateGrid(dataBindGrid, 'shortName');
                
                self.pasteData();
                
                self.setPositionButonToRightToLeft();
                
                dfd.resolve();
            }).fail(function(error) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }

        timeModeStart(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());
            let setWorkTypeTime = false;
            let param = {
                viewMode: 'time',
                startDate: self.dateTimePrev(),
                endDate: self.dateTimeAfter(),
                getActualData: item.isPresent() ? userInfor.achievementDisplaySelected : false,
                unit: item.isPresent() ? userInfor.unit : 0,
                workplaceId     : userInfor.workplaceId,
                workplaceGroupId: userInfor.workplaceGroupId
            };

            if (userInfor.disPlayFormat == 'shift') {
                setWorkTypeTime = true;
            } else {
                setWorkTypeTime = false;
            }

            self.visibleShiftPalette(false);
            self.visibleBtnInput(true);
            
            self.saveModeGridToLocalStorege('time');
            service.getDataOfTimeMode(param).done((data: IDataStartScreen) => {
                if (setWorkTypeTime) {
                    self.setWorkTypeTime(data.listWorkTypeInfo, userInfor);
                }
                
                self.saveDataGrid(data);
                // set hiển thị ban đầu theo data đã lưu trong localStorege
                self.getSettingDisplayWhenStart('time', false);
                // set data Grid
                let dataBindGrid = self.convertDataToGrid(data, 'time');
                // remove va tao lai grid
                self.destroyAndCreateGrid(dataBindGrid, 'time');
                
                self.pasteData();
                
                self.setPositionButonToRightToLeft();
                
                dfd.resolve();
            }).fail(function(error) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }

        setWorkTypeTime(listWorkTypeInfo, userInfor) {
            let self = this;
            let workTypeCodeSave = uk.localStorage.getItem('workTypeCodeSelected');
            let workTimeCodeSave = uk.localStorage.getItem('workTimeCodeSelected');
            let workTimeCode = '';
            if (workTimeCodeSave.isPresent()) {
                if (workTimeCodeSave.get() === 'none') {
                    workTimeCode = '';
                } else if (workTimeCodeSave.get() === 'deferred') {
                    workTimeCode = ' ';
                } else {
                    workTimeCode = workTimeCodeSave.get();
                }
            }
            self.setDataWorkType(listWorkTypeInfo);
            __viewContext.viewModel.viewAB.selectedWorkTypeCode(workTypeCodeSave.get());
            __viewContext.viewModel.viewAB.selected(workTimeCode);
            __viewContext.viewModel.viewAB.workplaceIdKCP013(userInfor.unit == 0 ? userInfor.workplaceId : userInfor.workplaceGroupId);
        }
        
        checkEnableCombWTime() {
            let self = this;
            if (self.selectedModeDisplayInBody() == 'shift')
                return;
            let workTypeCodeSave = uk.localStorage.getItem('workTypeCodeSelected');
            if (!workTypeCodeSave.isPresent()) {
                if (__viewContext.viewModel.viewAB.listWorkType()[0].workTimeSetting == 2) {
                    __viewContext.viewModel.viewAB.disabled(true);
                }
            } else {
                let objWtime = _.filter(__viewContext.viewModel.viewAB.listWorkType(), function(o) { return o.workTypeCode == workTypeCodeSave.get(); });
                if (objWtime.length > 0 && objWtime[0].workTimeSetting == 2) {
                    __viewContext.viewModel.viewAB.disabled(true);
                }
            }
        }
        
        destroyAndCreateGrid(dataBindGrid,viewMode){
            let self = this;
            $("#extable").children().remove();
            $("#extable").removeData();
            self.initExTable(dataBindGrid, viewMode, 'stick');
            if (!self.showA9) {
                $(".toLeft").css("display", "none");
            }
        }
        
        saveDataGrid(data: any) {
            let self = this;
            self.listEmpInfo = data.listEmpInfo;
            self.listWorkScheduleWorkInfor = data.listWorkScheduleWorkInfor;
            self.listWorkScheduleShift = data.listWorkScheduleShift;
            self.listPersonalConditions = data.listPersonalConditions;
            self.displayControlPersonalCond = data.displayControlPersonalCond;
            self.listDateInfo = data.listDateInfo;
        }

        // binding ket qua cua <<ScreenQuery>> 初期起動の情報取得 
        bindingToHeader(data: IDataStartScreen) {
            let self = this;
            let dataBasic: IDataBasicDto = data.dataBasicDto;
            self.dtPrev(dataBasic.startDate);
            self.dtAft(dataBasic.endDate);
            self.targetOrganizationName(dataBasic.targetOrganizationName);
            
            // save data to local Storage
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor: IUserInfor  = JSON.parse(data);
                userInfor.unit             = dataBasic.unit;
                userInfor.workplaceId      = dataBasic.workplaceId;
                userInfor.workplaceGroupId = dataBasic.workplaceGroupId;
                userInfor.workPlaceName    = dataBasic.targetOrganizationName;
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
        }
        
        saveShiftMasterToLocalStorage(shiftMasterWithWorkStyleLst: Array<IShiftMasterMapWithWorkStyle>) {
            let self = this;
            // save data to local Storage
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor: IUserInfor = JSON.parse(data);
                userInfor.shiftMasterWithWorkStyleLst = shiftMasterWithWorkStyleLst;
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
        }
        
        bingdingToShiftPallet(data: any) {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());

            // set data shiftPallet
            __viewContext.viewModel.viewAC.flag = false;
            __viewContext.viewModel.viewAC.workplaceModeName(data.dataBasicDto.designation);
            __viewContext.viewModel.viewAC.palletUnit([
                { code: 1, name: getText("Com_Company") },
                { code: 2, name: data.dataBasicDto.designation }
            ]);
            
            __viewContext.viewModel.viewAC.selectedpalletUnit(userInfor.shiftPalletUnit);
            if (userInfor.shiftPalletUnit == 1) {
                __viewContext.viewModel.viewAC.handleInitCom(
                    data.listPageInfo,
                    data.targetShiftPalette.shiftPalletCom,
                    userInfor.shiftPalettePageNumberCom);
            } else {
                __viewContext.viewModel.viewAC.handleInitWkp(
                    data.listPageInfo,
                    data.targetShiftPalette.shiftPalletWorkPlace,
                    userInfor.shiftPalettePageNumberOrg);
            }
            __viewContext.viewModel.viewAC.flag = true;
        }
        
        indexOfPageSelected(listPageInfo : any, shiftPalettePageNumber : any) {
            let index = _.findIndex(listPageInfo, function(o) { return o.pageNumber == shiftPalettePageNumber; });
            return index != -1 ? index : 0;
        }
        
        private setDataWorkType(listWorkTypeInfo: any) {
            let self = this;
            // set data cho combobox WorkType
            let listWorkType = [];
            _.each(listWorkTypeInfo, (emp: IWorkTypeInfomation, i) => {
                let workTypeDto: IWorkTypeDto = {
                    workTypeCode: emp.workTypeDto.workTypeCode, // 勤務種類コード - コード
                    name: emp.workTypeDto.name,         // 勤務種類名称  - 表示名
                    memo: emp.workTypeDto.memo,
                    workTimeSetting: emp.workTimeSetting, // 必須任意不要区分 :  必須である REQUIRED(0), 任意であるOPTIONAL(1), 不要であるNOT_REQUIRED(2)
                    workStyle: emp.workStyle,
                }
                listWorkType.push(workTypeDto);
            });
            __viewContext.viewModel.viewAB.listWorkType(listWorkType);
        }
        
        // convert data lấy từ server để đẩy vào Grid
        private convertDataToGrid(data: IDataStartScreen, viewMode: string) {
            let self = this;
            let result = {};
            let leftmostDs        = [];
            let middleDs          = [];
            let detailColumns     = [];
            let objDetailHeaderDs = {};
            let detailHeaderDeco  = [];
            let detailContentDs   = [];
            let detailContentDeco = [];
            let htmlToolTip       = [];
            let listCellNotEditBg = [];
            let listCellNotEditColor = [];
            let arrListCellLock = [];
            
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());
            self.listEmpData = [];
            self.listSid([]);
            self.arrListCellLock = [];
            self.listCellNotEditBg = [];
            self.listCellNotEditColor = [];
            self.dataSource = data;
            
            for (let i = 0; i < data.listEmpInfo.length; i++) {
                let rowId = i+'';
                let emp: IEmpInfo = data.listEmpInfo[i];
                let objDetailContentDs = new Object();
                // set data to detailLeftmost
                let businessName = emp.businessName == null || emp.businessName == undefined ? '' : emp.businessName.trim();
                leftmostDs.push({ sid: i.toString() ,employeeId: emp.employeeId, codeNameOfEmp: emp.employeeCode + ' ' + businessName });
                
                self.listSid.push(emp.employeeId);
                self.listEmpData.push({ id: emp.employeeId, code: emp.employeeCode, name : businessName });
                let listWorkScheduleInforByEmp: Array<IWorkScheduleWorkInforDto> = _.filter(data.listWorkScheduleWorkInfor, function(workSchedul: IWorkScheduleWorkInforDto) { return workSchedul.employeeId === emp.employeeId });
                let listWorkScheduleShiftByEmp: Array<IWorkScheduleShiftInforDto> = _.filter(data.listWorkScheduleShift, function(workSchedul: IWorkScheduleShiftInforDto) { return workSchedul.employeeId === emp.employeeId });
                // set data middle
                let personalCond: IPersonalConditions = _.filter(data.listPersonalConditions, function(o) { return o.sid == emp.employeeId; });
                if(personalCond.length > 0){
                   middleDs.push({ 
                    sid: i.toString(), 
                    employeeId: emp.employeeId, 
                    team: _.isNil(personalCond[0].teamName) ? '' : personalCond[0].teamName , 
                    rank: _.isNil(personalCond[0].rankName) ? '' : personalCond[0].rankName, 
                    qualification: _.isNil(personalCond[0].licenseClassification) ? '' : personalCond[0].licenseClassification });
                }
                
                // set data to detailContent : datasource va deco
                if (viewMode == 'shift') {
                    objDetailContentDs['sid'] = i.toString();
                    objDetailContentDs['employeeId'] = emp.employeeId;
                    let listWorkScheduleShiftByEmpSort = _.orderBy(listWorkScheduleShiftByEmp, ['date'],['asc']);
                    let item = uk.localStorage.getItem(self.KEY);
                    let userInfor: IUserInfor = JSON.parse(item.get());
                    let shiftMasterWithWorkStyleLst = userInfor.shiftMasterWithWorkStyleLst;
                    
                    for (let j = 0; j < listWorkScheduleShiftByEmpSort.length; j++) {
                        let cell: IWorkScheduleShiftInforDto = listWorkScheduleShiftByEmpSort[j];
                        let time = new Time(new Date(cell.date));
                        let ymd = time.yearMonthDay;
                        let shiftName = '';
                        shiftName = (cell.haveData == true && (cell.shiftName == null || cell.shiftName == '')) ? getText("KSU001_94") : cell.shiftName;
                        if (cell.needToWork == false)
                            shiftName = '';
                        objDetailContentDs['_' + ymd] = new ExCell('', '', '', '', '', '', shiftName, cell.shiftCode);

                        // set Deco background
                        if (userInfor.backgroundColor == 1) {
                            // A10_color② シフト表示：シフトの背景色  (Hiển thị Shift: màu nền của shift) 
                            if (cell.shiftCode != null) {
                                let objShiftMasterWithWorkStyle = _.filter(shiftMasterWithWorkStyleLst, function(o) { return o.shiftMasterCode == cell.shiftCode; });
                                if (objShiftMasterWithWorkStyle.length > 0) {
                                    let color = '#'+ objShiftMasterWithWorkStyle[0].color;
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, color, 0)); 
                                }
                            }
                        } else if (userInfor.backgroundColor == 0) {
                            // A10_color③ シフト表示：通常の背景色  (hiển thị shift: màu nền normal)                                                     
                            if (cell.achievements == true || cell.needToWork == false) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-uncorrectable-ksu001", 0));
                            } else if (cell.supportCategory != SupportCategory.NotCheering) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-support", 0)); 
                            } else {
                                if (cell.shiftEditState != null && cell.shiftEditState.editStateSetting == 1) {
                                    // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 0));
                                }
                                if (cell.shiftEditState != null && cell.shiftEditState.editStateSetting == 2) {
                                    //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 0));
                                }
                                if (cell.shiftEditState != null && cell.shiftEditState.editStateSetting == 3) {
                                    //REFLECT_APPLICATION(2), 申請反映
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-reflect-application", 0));
                                }
                            }
                        }
                        // set Deco text color
                        // A10_color⑥ スケジュール明細の文字色  (Màu chữ của "Schedule detail")                                                         
                        if (cell.achievements == true) {
                            if (userInfor.backgroundColor == 0) {
                                if (cell.shiftCode == '' || cell.shiftCode == null) {
                                    // デフォルト（黒）  Default (black) 
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-default", 0));
                                } else {
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 0));
                                }
                            } else {
                                if (cell.shiftCode == '' || cell.shiftCode == null) {
                                    // デフォルト（黒）  Default (black) 
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-default", 0));
                                } else {
                                    let workHolidayCls = cell.workHolidayCls == null ? self.getWorkStyle(shiftMasterWithWorkStyleLst, cell.shiftCode) : cell.workHolidayCls;
                                    let cellColor = self.setColorCell(workHolidayCls, ymd, rowId);
                                    detailContentDeco.push(cellColor);
                                }
                            }
                        } else {
                            if (cell.shiftCode == '' || cell.shiftCode == null) {
                                // デフォルト（黒）  Default (black) 
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-default", 0));
                            } else {
                                let workHolidayCls = cell.workHolidayCls == null ? self.getWorkStyle(shiftMasterWithWorkStyleLst , cell.shiftCode) : cell.workHolidayCls;
                                let cellColor      = self.setColorCell(workHolidayCls,ymd, rowId);
                                detailContentDeco.push(cellColor);
                            }
                        }
                        
                        // điều kiện ※Aa1
                        if (cell.isEdit == false) {
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "xseal", 0));
                            listCellNotEditBg.push(new CellColor('_' + ymd, rowId, "xseal", 0));
                            listCellNotEditColor.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 0));
                        }
                        // điều kiện ※Aa2
                        if (cell.isActive == false) {
                            arrListCellLock.push({ rowId: rowId, columnId: "_" + ymd });
                        }
                        
                    };
                    detailContentDs.push(objDetailContentDs);
                    self.arrListCellLock = arrListCellLock;
                    
                } else if (viewMode == 'shortName') {
                    objDetailContentDs['sid'] = i.toString();
                    objDetailContentDs['employeeId'] = emp.employeeId;
                    let listWorkScheduleInforByEmpSort = _.orderBy(listWorkScheduleInforByEmp, ['date'],['asc']);
                    _.each(listWorkScheduleInforByEmpSort, (cell: IWorkScheduleWorkInforDto) => {
                        let time = new Time(new Date(cell.date));
                        let ymd = time.yearMonthDay;
                        let workTypeName = ((cell.workTypeCode != null && (cell.workTypeName == '' || _.isNil(cell.workTypeName))) || cell.workTypeIsNotExit == true ) ? (cell.workTypeCode == null ? '' : cell.workTypeCode) + getText("KSU001_22") : cell.workTypeName;
                        let workTimeName = ((cell.workTimeCode != null && (cell.workTimeName == '' || _.isNil(cell.workTimeName))) || cell.workTimeIsNotExit == true ) ? (cell.workTimeCode == null ? '' : cell.workTimeCode) + getText("KSU001_22") : cell.workTimeName;
                        if (cell.needToWork == false) {
                            workTypeName = '';
                            workTimeName = '';
                        }
                        objDetailContentDs['_' + ymd] = new ExCell(cell.workTypeCode, workTypeName, cell.workTimeCode, workTimeName);

                        // set Deco background
                        // A10_color⑤ 勤務略名表示の背景色 (Màu nền hiển thị "chuyên cần, tên viết tắt")                                                   
                        if (cell.achievements == true || cell.needToWork == false) {
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-uncorrectable-ksu001", 0));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-uncorrectable-ksu001", 1));
                        } else {
                            if (cell.workTypeEditStatus != null) {
                                if (cell.workTypeEditStatus.editStateSetting == 1) {
                                    // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 0));
                                } else if (cell.workTypeEditStatus.editStateSetting == 2) {
                                    //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 0));
                                } else if (cell.workTypeEditStatus.editStateSetting == 3) {
                                    //REFLECT_APPLICATION(2), 申請反映
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-reflect-application", 0));
                                }
                            }

                            if (cell.workTimeEditStatus != null) {
                                if (cell.workTimeEditStatus.editStateSetting == 1) {
                                    // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 1));
                                } else if (cell.workTimeEditStatus.editStateSetting == 2) {
                                    //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 1));
                                } else if (cell.workTimeEditStatus.editStateSetting == 3) {
                                    //REFLECT_APPLICATION(2), 申請反映
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-reflect-application", 1));
                                }
                            }
                        }

                        // set Deco text color
                        // A10_color⑥ スケジュール明細の文字色  (Màu chữ của "Schedule detail")
                        if (cell.achievements == true) {
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 0));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 1));
                        } else {
                            if (cell.workHolidayCls == AttendanceHolidayAttr.FULL_TIME) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-attendance", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-attendance", 1));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.MORNING) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 1));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.AFTERNOON) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 1));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.HOLIDAY) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-holiday", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-holiday", 1));
                            }
                        }
                        
                        // điều kiện ※Abc1 dieu kien edit
                        if (cell.isEdit == false) {
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "xseal", 0));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "xseal", 1));
                        }
                        // điều kiện ※Abc2
                        if (cell.isActive == false) {
                            arrListCellLock.push({ rowId: rowId, columnId: "_" + ymd });
                        }
                    });
                    detailContentDs.push(objDetailContentDs);
                    self.arrListCellLock = arrListCellLock;

                } else if (viewMode == 'time') {
                    objDetailContentDs['sid'] = i.toString();
                    objDetailContentDs['employeeId'] = emp.employeeId;
                    let listWorkScheduleInforByEmpSort = _.orderBy(listWorkScheduleInforByEmp, ['date'],['asc']);
                    _.each(listWorkScheduleInforByEmpSort, (cell: IWorkScheduleWorkInforDto) => {
                        // set dataSource
                        let time = new Time(new Date(cell.date));
                        let ymd = time.yearMonthDay;
                        let workTypeName = ((cell.workTypeCode != null && (cell.workTypeName == '' || _.isNil(cell.workTypeName))) || cell.workTypeIsNotExit == true ) ? (cell.workTypeCode == null ? '' : cell.workTypeCode) + getText("KSU001_22") : cell.workTypeName;
                        let workTimeName = ((cell.workTimeCode != null && (cell.workTimeName == '' || _.isNil(cell.workTimeName))) || cell.workTimeIsNotExit == true ) ? (cell.workTimeCode == null ? '' : cell.workTimeCode) + getText("KSU001_22") : cell.workTimeName;
                        let startTime    = cell.startTime == null ? '' : formatById("Clock_Short_HM", cell.startTime);
                        let endTime      = cell.endTime   == null ? '' : formatById("Clock_Short_HM", cell.endTime);
                        let workTypeCode = cell.workTypeCode;
                        let workTimeCode = cell.workTimeCode;
                        if (cell.needToWork == false) {
                            workTypeName = '';
                            workTimeName = '';
                            startTime    = '';
                            endTime      = '';
                        }
                        objDetailContentDs['_' + ymd] = new ExCell(workTypeCode, workTypeName, workTimeCode, workTimeName, startTime, endTime);

                        // set Deco background
                        // A10_color⑤ 勤務略名表示の背景色 (Màu nền hiển thị "chuyên cần, tên viết tắt")
                        if (cell.achievements == true || cell.needToWork == false) {
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-uncorrectable-ksu001", 0));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-uncorrectable-ksu001", 1));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-uncorrectable-ksu001", 2));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-schedule-uncorrectable-ksu001", 3));
                        } else {
                            if (cell.workTypeEditStatus != null) {
                                if (cell.workTypeEditStatus.editStateSetting == 1) {
                                    // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 0));
                                } else if (cell.workTypeEditStatus.editStateSetting == 2) {
                                    //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 0));
                                } else if (cell.workTypeEditStatus.editStateSetting == 3) {
                                    //REFLECT_APPLICATION(2), 申請反映
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-reflect-application", 0));
                                }
                            }
                            
                            if (cell.workTimeEditStatus != null) {
                                if (cell.workTimeEditStatus.editStateSetting == 1) {
                                    // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 1));
                                } else if (cell.workTimeEditStatus.editStateSetting == 2) {
                                    //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 1));
                                } else if (cell.workTimeEditStatus.editStateSetting == 3) {
                                    //REFLECT_APPLICATION(2), 申請反映
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-reflect-application", 1));
                                }
                            }
                            
                            if (cell.startTimeEditState != null) {
                                if (cell.startTimeEditState.editStateSetting == 1) {
                                    // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 2));
                                } else if (cell.startTimeEditState.editStateSetting == 2) {
                                    //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 2));
                                } else if (cell.startTimeEditState.editStateSetting == 3) {
                                    //REFLECT_APPLICATION(2), 申請反映
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-reflect-application", 2));
                                }
                            }
                            
                            if (cell.endTimeEditState != null) {
                                if (cell.endTimeEditState.editStateSetting == 1) {
                                    // HAND_CORRECTION_MYSELF(0), 手修正（本人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-self", 3));
                                } else if (cell.endTimeEditState.editStateSetting == 2) {
                                    //HAND_CORRECTION_OTHER(1), 手修正（他人）
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-alter-other", 3));
                                } else if (cell.endTimeEditState.editStateSetting == 3) {
                                    //REFLECT_APPLICATION(2), 申請反映
                                    detailContentDeco.push(new CellColor('_' + ymd, rowId, "bg-daily-reflect-application", 3));
                                }
                            }
                        }

                        // set Deco text color
                        // A10_color⑥ スケジュール明細の文字色  (Màu chữ của "Schedule detail")
                        if (cell.achievements == true) {
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 0));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 1));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 2));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-schedule-performance", 3));
                        } else {
                            if (cell.workHolidayCls == AttendanceHolidayAttr.FULL_TIME) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-attendance", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-attendance", 1));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.MORNING) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 1));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.AFTERNOON) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-half-day-work", 1));
                            }
                            if (cell.workHolidayCls == AttendanceHolidayAttr.HOLIDAY) {
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-holiday", 0));
                                detailContentDeco.push(new CellColor('_' + ymd, rowId, "color-holiday", 1));
                            }
                        }
                        
                         // điều kiện ※Abc1
                         // dieu kien ※Ac
                        if (cell.isEdit == false) {
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "xseal", 0));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "xseal", 1));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "xseal", 2));
                            detailContentDeco.push(new CellColor('_' + ymd, rowId, "xseal", 3));
                        }
                            
                        // điều kiện ※Abc2
                        if (cell.isActive == false) {
                            arrListCellLock.push({ rowId: rowId, columnId: "_" + ymd });
                        }
                    });
                    detailContentDs.push(objDetailContentDs);
                    self.arrListCellLock = arrListCellLock;
                }
            }
            
            // truyen sids vao kcp015
            self.sids(self.listSid());
            
            self.listCellNotEditBg    = listCellNotEditBg;
            self.listCellNotEditColor = listCellNotEditColor;

            // set width cho column cho tung mode
            let widthColumn = 0;
            if (viewMode == 'time') {
                widthColumn = 100;
            } else if (viewMode == 'shortName') {
                widthColumn = 50;
            } else if (viewMode == 'shift') {
                widthColumn = 35;
            }

            // イベント情報と個人条件のmapping (mapping "thông tin event" và "person condition")
            if (data.displayControlPersonalCond == null) {
                self.showA9 = false;
            } else {
                self.showA9 = true;
                let listConditionDisplayControl = data.displayControlPersonalCond.listConditionDisplayControl;
                let team = _.filter(listConditionDisplayControl, function(o) { return o.conditionATR == 1; });
                let rank = _.filter(listConditionDisplayControl, function(o) { return o.conditionATR == 2; });
                let qual = _.filter(listConditionDisplayControl, function(o) { return o.conditionATR == 4; });
                if (team.length > 0) {
                    if (team[0].displayCategory == 1) {
                        self.showTeamCol = true;
                    }
                }
                if (rank.length > 0) {
                    if (rank[0].displayCategory == 1) {
                        self.showRankCol = true;
                    }
                }
                if (qual.length > 0) {
                    if (qual[0].displayCategory == 1) {
                        self.showQualificCol = true;
                    }
                }
                if (self.showTeamCol == false && self.showRankCol == false && self.showQualificCol == false) {
                    self.showA9 = false;
                }
            }
            
            detailColumns.push({ key: "sid", width: "5px", headerText: "ABC", visible: false });
            objDetailHeaderDs['sid'] = "";
            self.arrDay = [];
            _.each(data.listDateInfo, (dateInfo: IDateInfo) => {
                self.arrDay.push(new Time(new Date(dateInfo.ymd)));
                let time = new Time(new Date(dateInfo.ymd));
                detailColumns.push({
                    key: "_" + time.yearMonthDay, width: widthColumn + "px", handlerType: "input", dataType: "label/label/duration/duration", primitiveValue: "TimeWithDayAttr", headerControl: "link"
                });
                let ymd = time.yearMonthDay;
                let field = '_' + ymd;
                if (dateInfo.isToday) {
                    
                    if (dateInfo.isHoliday) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-that-day-color-schedule-sunday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-that-day-color-schedule-sunday"));
                    } else if (dateInfo.dayOfWeek == 7) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-that-day-color-schedule-sunday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-that-day-color-schedule-sunday"));
                    } else if (dateInfo.dayOfWeek == 6) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-that-day-color-schedule-saturday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-that-day-color-schedule-saturday"));
                    } else if (dateInfo.dayOfWeek > 0 || dateInfo.dayOfWeek < 6) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-that-day-color-schedule-weekdays"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-that-day-color-schedule-weekdays"));
                    } else {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-that-day"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-that-day"));
                    }
                    
                } else if (dateInfo.isSpecificDay) {
                    
                    if (dateInfo.isHoliday) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-specific-date-color-schedule-sunday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-specific-date-color-schedule-sunday"));
                    } else if (dateInfo.dayOfWeek == 7) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-specific-date-color-schedule-sunday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-specific-date-color-schedule-sunday"));
                    } else if (dateInfo.dayOfWeek == 6) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-specific-date-color-schedule-saturday"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-specific-date-color-schedule-saturday"));
                    } else if (dateInfo.dayOfWeek > 0 || dateInfo.dayOfWeek < 6) {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-specific-date-color-schedule-weekdays"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-specific-date-color-schedule-weekdays"));
                    } else {
                        detailHeaderDeco.push(new CellColor("_" + ymd, 0, "bg-schedule-specific-date"));
                        detailHeaderDeco.push(new CellColor("_" + ymd, 1, "bg-schedule-specific-date"));
                    }
                    
                    
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
                
                if (dateInfo.htmlTooltip != null) {
                    objDetailHeaderDs['_' + ymd] = "<div class='header-image-event'></div>";
                    htmlToolTip.push(new HtmlToolTip('_' + ymd, dateInfo.htmlTooltip));
                } else {
                    objDetailHeaderDs['_' + ymd] = "<div class='header-image-no-event'></div>";
                }
            });
            
            self.setIconEventHeader();
            
            self.listColorOfHeader(detailHeaderDeco);
            result = {
                leftmostDs: leftmostDs,
                middleDs: middleDs,
                detailColumns: detailColumns,
                objDetailHeaderDs: objDetailHeaderDs,
                detailHeaderDeco: detailHeaderDeco,
                htmlToolTip: htmlToolTip,
                detailContentDs: detailContentDs,
                detailContentDeco: detailContentDeco,
                arrListCellLock: arrListCellLock

            };
            self.detailContentDs = detailContentDs;
            self.detailColumns = detailColumns;
            self.detailContentDeco = detailContentDeco;
            
            let empLogin = _.filter(detailContentDs, function(o) { return o.employeeId == self.employeeIdLogin; });
            if (empLogin.length > 0) {
                self.key = empLogin[0].sid;
            } else {
                self.key = 0;
            }
            return result;
        }
        
        // getWorkStyler cell ở mode 1 cell
        getWorkStyle(shiftMasterWithWorkStyleLst,shiftCode){
            let self = this;
            let objShiftMasterWithWorkStyle = _.filter(shiftMasterWithWorkStyleLst, function(o) { return o.shiftMasterCode == shiftCode; });
            if (objShiftMasterWithWorkStyle.length > 0) {
                return objShiftMasterWithWorkStyle[0].workStyle;
            }
        }
        
        // set cell color ở mode 1 cell
        setColorCell(workHolidayCls, ymd, rowId){
            if (workHolidayCls == AttendanceHolidayAttr.FULL_TIME) {
                return new CellColor('_' + ymd, rowId, "color-attendance", 0);
            }
            if (workHolidayCls == AttendanceHolidayAttr.MORNING) {
                return new CellColor('_' + ymd, rowId, "color-half-day-work", 0);
            }
            if (workHolidayCls == AttendanceHolidayAttr.AFTERNOON) {
                return new CellColor('_' + ymd, rowId, "color-half-day-work", 0);
            }
            if (workHolidayCls == AttendanceHolidayAttr.HOLIDAY) {
                return new CellColor('_' + ymd, rowId, "color-holiday", 0);
            }
            if (util.isNullOrUndefined(workHolidayCls) || util.isNullOrEmpty(workHolidayCls)) {
                // デフォルト（黒）  Default (black)
                return new CellColor('_' + ymd, rowId, "color-default", 0);
            }
        }

        setIconEventHeader() {
            setTimeout(() => {
                // set icon Employee
                let iconEmpPath = nts.uk.request.location.siteRoot.mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/").mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/").mergeRelativePath("7.png").serialize();
                $('.icon-leftmost').css('background-image', 'url(' + iconEmpPath + ')');
                
                // set backgound image icon header
                let iconEventPath = nts.uk.request.location.siteRoot.mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/").mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/").mergeRelativePath("120.png").serialize();
                $('.header-image-event').css('background-image', 'url(' + iconEventPath + ')');

                let iconNoEventPath = nts.uk.request.location.siteRoot.mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/").mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/").mergeRelativePath("121.png").serialize();
                $('.header-image-no-event').css('background-image', 'url(' + iconNoEventPath + ')');
            }, 1);
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
        getSettingDisplayWhenStart(viewMode, isStart) {
            let self = this;
            
            $(".editMode").addClass("A6_hover").removeClass("A6_not_hover");
            $(".confirmMode").addClass("A6_not_hover").removeClass("A6_hover");

            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor: IUserInfor = JSON.parse(data);

                // A4_7
                if(isStart){
                    self.achievementDisplaySelected(2);
                }else{
                    self.achievementDisplaySelected(userInfor.achievementDisplaySelected == false ? 2 : 1);
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
                
                if (viewMode == 'time') {
                    self.visibleBtnInput(true);
                } else {
                    self.visibleBtnInput(false);
                }

                self.enableBtnRedo(false);
                self.enableBtnUndo(false);
                
                // enable| disable combobox workTime
                let workType = _.filter(__viewContext.viewModel.viewAB.listWorkType(), function(o) { return o.workTypeCode == __viewContext.viewModel.viewAB.selectedWorkTypeCode(); });
                if (workType.length > 0) {
                    // check workTimeSetting 
                    if (workType[0].workTimeSetting == 2) {
                        __viewContext.viewModel.viewAB.disabled(true);
                    } else {
                        __viewContext.viewModel.viewAB.disabled(false);
                    }
                }
            });
        }

        /**
        * Create exTable
        */
        initExTable(dataBindGrid: any, viewMode: string, updateMode : string): void {
            let self = this,
                //Get dates in time period
                currentDay = new Date(),
                windowXOccupation = 65,
                windowYOccupation = 328;
            let itemLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(itemLocal.get());
            let bodyHeightMode = userInfor.gridHeightSelection == 1 ? "dynamic" : "fixed";
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
            
            if (self.showA9) {
                let widthMid : number = 0;
                middleColumns = [];
                if(self.showTeamCol){
                    widthMid = widthMid + 40;
                    middleColumns.push({ headerText: getText("KSU001_4023"), key: "team", width: "40px", css: { whiteSpace: "none" } });    
                }
                if(self.showRankCol){
                    widthMid = widthMid + 40;
                    middleColumns.push({ headerText: getText("KSU001_4024"), key: "rank", width: "40px", css: { whiteSpace: "none" } });
                }
                if(self.showQualificCol){
                    widthMid = widthMid + 40;
                    middleColumns.push({ headerText: getText("KSU001_4025"), key: "qualification", width: "40px", css: { whiteSpace: "none" } });
                }
                
                self.widthMid = widthMid;
                
                let key = request.location.current.rawUrl + "/extable/areawidths";
                uk.localStorage.getItem(key).ifPresent((data) => {
                    let areawidths = JSON.parse(data);
                    areawidths["ex-header-middle"] = widthMid;
                    uk.localStorage.setItemAsJson(key, areawidths);
                });
                
                middleHeader = {
                    columns: middleColumns,
                    width:   widthMid+"px",
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
            }

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
                                    ui.tooltip("show", $("<div/>").css({ width: "max-content", height: "max-content" }).html(objTooltip[0].value));
                                } else {
                                    ui.tooltip("show", $("<div/>").css({ width: "60px", height: "60px" }).html(''));
                                }
                            }
                        },
                        exit: function(ui) {
                            ui.tooltip("hide");
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
                fields: ["workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "shiftName", "startTime", "endTime", "shiftCode"],
            };
            
            function customValidate(idx, key, innerIdx, value, obj) {
                let startTime, endTime;
                if (innerIdx === 2) {
                    startTime = nts.uk.time.minutesBased.duration.parseString(value).toValue();
                    endTime = nts.uk.time.minutesBased.duration.parseString(obj.endTime).toValue();
                } else if (innerIdx === 3) {
                    startTime = nts.uk.time.minutesBased.duration.parseString(obj.startTime).toValue();
                    endTime = nts.uk.time.minutesBased.duration.parseString(value).toValue();
                }
                
                if (startTime < 0 && endTime < 0) {
                    startTime = startTime * -1;
                    endTime = endTime * -1;
                }

                if (startTime >= endTime) {
                    let messInfo = nts.uk.resource.getMessage('Msg_54');
                    return { isValid: false, message: messInfo };
                }

                if (innerIdx === 2) {
                    return { isValid: true, innerErrorClear: [3] };
                } else if (innerIdx === 3) {
                    return { isValid: true, innerErrorClear: [2] };
                }

                return { isValid: true };
            };

            let start = performance.now();
            
            if (self.showA9) {
                new nts.uk.ui.exTable.ExTable($("#extable"), {
                    headerHeight: "60px",
                    bodyRowHeight: viewMode == 'shift' ? "35px" : "50px",
                    bodyHeight: "500px",
                    horizontalSumHeaderHeight: "0px",
                    horizontalSumBodyHeight: "0px",
                    horizontalSumBodyRowHeight: "0px",
                    areaResize: true,
                    bodyHeightMode: bodyHeightMode,
                    windowXOccupation: windowXOccupation,
                    windowYOccupation: windowYOccupation,
                    manipulatorId: self.key,
                    manipulatorKey: "sid",
                    updateMode: updateMode,
                    pasteOverWrite: true,
                    stickOverWrite: true,
                    viewMode: viewMode,
                    showTooltipIfOverflow: true,
                    errorMessagePopup: true,
                    customValidate: customValidate,
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
            } else {
                new nts.uk.ui.exTable.ExTable($("#extable"), {
                    headerHeight: "60px",
                    bodyRowHeight: viewMode == 'shift' ? "35px" : "50px",
                    bodyHeight: "500px",
                    horizontalSumHeaderHeight: "0px",
                    horizontalSumBodyHeight: "0px",
                    horizontalSumBodyRowHeight: "0px",
                    areaResize: true,
                    bodyHeightMode: bodyHeightMode,
                    windowXOccupation: windowXOccupation,
                    windowYOccupation: windowYOccupation,
                    manipulatorId: self.key,
                    manipulatorKey: "sid",
                    updateMode: updateMode,
                    pasteOverWrite: true,
                    stickOverWrite: true,
                    viewMode: viewMode,
                    showTooltipIfOverflow: true,
                    errorMessagePopup: true,
                    customValidate: customValidate,
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
                    .DetailHeader(detailHeader).DetailContent(detailContent)
                    .create();
            }
            
            // set height grid theo localStorage đã lưu
            self.setPositionButonDownAndHeightGrid();
            
            console.log(performance.now() - start);
        }
        
        //set lock cell
        setLockCell(arrListCellLock: any) {
            _.forEach(arrListCellLock, (x) => {
                $("#extable").exTable("lockCell", x.rowId + "", x.columnId);
            });
        }

        updateExTable(dataBindGrid: any, viewMode : string ,updateMode: string, updateLeftMost : boolean, updateMiddle : boolean, updateDetail : boolean): void {
            let self = this;
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

            if (self.showA9) {
                middleColumns = [];
                if (self.showTeamCol) {
                    middleColumns.push({ headerText: getText("KSU001_4023"), key: "team", width: "40px", css: { whiteSpace: "none" } });
                }
                if (self.showRankCol) {
                    middleColumns.push({ headerText: getText("KSU001_4024"), key: "rank", width: "40px", css: { whiteSpace: "none" } });
                }
                if (self.showQualificCol) {
                    middleColumns.push({ headerText: getText("KSU001_4025"), key: "qualification", width: "40px", css: { whiteSpace: "none" } });
                }
            }

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
                fields: ["workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "shiftName", "startTime", "endTime", "shiftCode"],
            };

            if (updateLeftMost) {
                $("#extable").exTable("updateTable", "leftmost", {}, leftmostContentUpdate);
            }
            if (updateMiddle) {
                if (self.showA9) {
                    $("#extable").exTable("updateTable", "middle", {}, middleContentUpdate);
                }
            }
            
            $("#extable").exTable("mode", viewMode, updateMode, null, [{
                    name: "BodyCellStyle",
                    decorator: detailContentDeco
            }]);
            
            if (updateDetail) {
                $("#extable").exTable("updateTable", "detail", detailHeaderUpdate, detailContentUpdate);
            }
        }
        
        // khi thay đổi combobox backgrounndMode
        updateExTableWhenChangeModeBg(detailContentDecoUpdate, updateMode ): void {
            let self = this;
            // save scroll's position
            self.stopRequest(false);
             
            // update Phần Detail
            let detailContentDeco = detailContentDecoUpdate;
            let detailContentDs = self.detailContentDs;
            let detailColumns = self.detailColumns;

            let detailContentUpdate = {
                columns: detailColumns,
                dataSource: detailContentDs,
                primaryKey: "sid",
                //highlight: false,
                features: [{
                    name: "BodyCellStyle",
                    decorator: detailContentDeco
                }, {
                        name: "TimeRange",
                        ranges: []
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
                fields: ["workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "shiftName", "startTime", "endTime", "shiftCode"],
            };

//            $("#extable").exTable("mode", 'shift', updateMode, null, [{
//                name: "BodyCellStyle",
//                decorator: detailContentDeco
//            }]);

           $("#extable").exTable("updateTable", "detail", {}, detailContentUpdate);
            
            self.setCoppyStyler();
        }
        
        setCoppyStyler() {
            let self = this;
            // get listShiftMaster luu trong localStorage
            let itemLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(itemLocal.get());
            let listShiftMasterSaveLocal = userInfor.shiftMasterWithWorkStyleLst;
            
            // set color for cell
            $("#extable").exTable("stickStyler", function(rowIdx, key, innerIdx, data, stickOrigData) {
                let modeBackGround = self.backgroundColorSelected(); // 0||1
                let shiftCode;
                if (_.isNil(stickOrigData)) {
                    shiftCode = data.shiftCode;
                } else {
                    shiftCode = stickOrigData.shiftCode == "" ? data.shiftCode : stickOrigData.shiftCode;
                }
                let workInfo = _.filter(listShiftMasterSaveLocal, function(o) { return o.shiftMasterCode === shiftCode; });
                if (workInfo.length > 0) {
                    let workStyle = workInfo[0].workStyle;
                    if (workStyle == AttendanceHolidayAttr.FULL_TIME + '') {
                        if (modeBackGround == 1) {
                            return { textColor: "#0000ff", background: "#" + workInfo[0].color }; // color-attendance
                        } else {
                            return { textColor: "#0000ff" }; // color-attendance
                        }
                    }
                    if (workStyle == AttendanceHolidayAttr.MORNING + '') {
                        if (modeBackGround == 1) {
                            return { textColor: "#FF7F27", background: "#" + workInfo[0].color };// color-half-day-work
                        } else {
                            return { textColor: "#FF7F27" };// color-half-day-work
                        }
                    }
                    if (workStyle == AttendanceHolidayAttr.AFTERNOON + '') {
                        if (modeBackGround == 1) {
                            return { textColor: "#FF7F27", background: "#" + workInfo[0].color };// color-half-day-work
                        } else {
                            return { textColor: "#FF7F27" };// color-half-day-work
                        }
                    }
                    if (workStyle == AttendanceHolidayAttr.HOLIDAY + '') {
                        if (modeBackGround == 1) {
                            return { textColor: "#ff0000", background: "#" + workInfo[0].color };// color-holiday
                        } else {
                            return { textColor: "#ff0000" };// color-holiday
                        }

                    }
                    if (nts.uk.util.isNullOrUndefined(workStyle) || nts.uk.util.isNullOrEmpty(workStyle)) {
                        if (modeBackGround == 1) {
                            return { textColor: "#000000", background: "#ffffff" } // デフォルト（黒）  Default (black)
                        } else {
                            return { textColor: "#000000" }
                        }
                    }
                    /**
                     *  1日休日系  ONE_DAY_REST(0)
                     *  午前出勤系 MORNING_WORK(1)
                     *  午後出勤系 AFTERNOON_WORK(2)
                     *  1日出勤系 ONE_DAY_WORK(3)
                     */
                }
            });
        }
        
        // khi thay đổi mode edit va confirm
        updateExTableWhenChangeMode(viewMode, updateMode): void {
            let self = this;
            // save scroll's position
            self.stopRequest(false);
             
            // update Phần Detail
            let detailContentDeco = self.detailContentDeco;
            let detailContentDs = self.detailContentDs;
            let detailColumns = self.detailColumns;

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
                        ranges: []
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
                fields: ["workTypeCode", "workTypeName", "workTimeCode", "workTimeName", "shiftName", "startTime", "endTime", "shiftCode"],
            };

            $("#extable").exTable("mode", viewMode, updateMode, null, [{
                name: "BodyCellStyle",
                decorator: detailContentDeco
            }]);

           $("#extable").exTable("updateTable", "detail", {}, detailContentUpdate);
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
                $('.iconToLeft').css('background-image', 'url(' + self.pathToRight + ')');
                
                $(".toLeft").css("margin-left", "188px");
                
                let marginleft = $("#extable").width() - 160 - 27 - 27 - 30 - 10;
                $(".toRight").css('margin-left', marginleft + 'px');
            } else {
                if (self.showA9) {
                    $("#extable").exTable("showMiddle");
                }
                $('.iconToLeft').css('background-image', 'url(' + self.pathToLeft + ')');
                let marginleftOfbtnToLeft: number = 190 + self.widthMid;
                $(".toLeft").css("margin-left", marginleftOfbtnToLeft + 'px');
                
                let marginleftOfbtnToRight = $("#extable").width() - 160 - self.widthMid - 27 - 27 - 30 - 10;
                $(".toRight").css('margin-left', marginleftOfbtnToRight + 'px');
            }
            self.indexBtnToLeft = self.indexBtnToLeft + 1;

        }

        toRight() {
            let self = this;
            if (self.indexBtnToRight % 2 == 0) {
                $('.iconToRight').css('background-image', 'url(' + self.pathToLeft + ')');
                //$(".toRight").css("background", "url(../image/toleft.png) no-repeat center");

            } else {
                $('.iconToRight').css('background-image', 'url(' + self.pathToRight + ')');
            }
            self.indexBtnToRight = self.indexBtnToRight + 1;
        }

        toDown() {
            let self = this;
            if (self.indexBtnToDown % 2 == 0) {
                //$(".toDown").css("background", "url(../image/toup.png) no-repeat center");
                $('.iconToDown').css('background-image', 'url(' + self.pathToUp + ')');

            } else {
                //$(".toDown").css("background", "url(../image/todown.png) no-repeat center");
                $('.iconToDown').css('background-image', 'url(' + self.pathToDown + ')');
            }
            self.indexBtnToDown = self.indexBtnToDown + 1;
        }
        
        setPositionButonToRightToLeft() {
            let self = this;
            self.indexBtnToLeft = 0;

            let marginleftOfbtnToRight: number = 0;
            let marginleftOfbtnToLeft: number = 190 + self.widthMid;
            if (self.showA9) {
                //$(".toLeft").css("background", "url(../image/toleft.png) no-repeat center");
                //$('.iconToLeft').css('background-image', 'url(' + self.pathToLeft + ')');

                $(".toLeft").css("margin-left", marginleftOfbtnToLeft + 'px');

                marginleftOfbtnToRight = $("#extable").width() - 160 - self.widthMid - 27 - 27 - 32 - 10;
            } else {
                $(".toLeft").css("display", "none");
                marginleftOfbtnToRight = $("#extable").width() - 32 - 3;
            }
            $(".toRight").css('margin-left', marginleftOfbtnToRight + 'px');
        }
        
        setHeightScreen() {
            let self = this;
            let itemLocal = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(itemLocal.get());
            if (userInfor.gridHeightSelection == 1) {
                $("#content-main").css('overflow-y', 'hidden');
                $("#content-main").css('height', 'auto');
            } else {
                var height = window.innerHeight - 205;
                $("#content-main").css('overflow-y', 'scroll');
                $("#content-main").css('height', height + 'px');
            }
        }
        
        setPositionButonToRight() {
            let self = this;
            let marginleftOfbtnToRight: number = 0;
            if (self.showA9) {
                marginleftOfbtnToRight = $("#extable").width() - 160 - self.widthMid - 27 - 27 - 40;
            } else {
                marginleftOfbtnToRight = $("#extable").width() - 32 - 3;
            }
            $(".toRight").css('margin-left', marginleftOfbtnToRight + 'px');
        }
        
        setPositionButonDownAndHeightGrid() {
            let self = this;
            if (uk.localStorage.getItem(self.KEY).isPresent()) {
                let userInfor = JSON.parse(uk.localStorage.getItem(self.KEY).get());
                if (userInfor.gridHeightSelection == 2) {
                    $("#extable").exTable("setHeight", userInfor.heightGridSetting);
                    let heightBodySetting: number = + userInfor.heightGridSetting;
                    let heightBody = heightBodySetting + 60 - 25 - 16; // 60 chieu cao header, 25 chieu cao button
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


        /**
        * next a month
        */
        nextMonth(): void {
            let self = this;
            if(self. selectedDisplayPeriod() == 2)
                return;
            
            self.stopRequest(false);
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());
            
            let param = {
                viewMode : self.selectedModeDisplayInBody(), // time | shortName | shift
                startDate: self.dateTimePrev(),
                endDate  : self.dateTimeAfter(),
                isNextMonth : true,
                cycle28Day : self. selectedDisplayPeriod() == 2 ? true : false,
                workplaceId     : userInfor.workplaceId,
                workplaceGroupId: userInfor.workplaceGroupId,
                unit:             userInfor.unit,
                getActualData   : item.isPresent() ? userInfor.achievementDisplaySelected : false, 
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst, 
                listSid: self.listSid(),
                modePeriod : self. selectedDisplayPeriod()
            };
            
            service.getDataChangeMonth(param).done((data: any) => {
                if (userInfor.disPlayFormat == 'shift') {
                    self.saveShiftMasterToLocalStorage(data.shiftMasterWithWorkStyleLst);
                }
                self.saveDataGrid(data);
                self.dtPrev(data.dataBasicDto.startDate);
                self.dtAft(data.dataBasicDto.endDate);
                
                let dataGrid: any = {
                    listDateInfo: data.listDateInfo,
                    listEmpInfo: data.listEmpInfo,
                    displayControlPersonalCond: data.displayControlPersonalCond,
                    listPersonalConditions: data.listPersonalConditions,
                    listWorkScheduleWorkInfor: data.listWorkScheduleWorkInfor,
                    listWorkScheduleShift: data.listWorkScheduleShift
                }
                let dataBindGrid = self.convertDataToGrid(dataGrid, self.selectedModeDisplayInBody());
                
                // remove va tao lai grid
                self.destroyAndCreateGrid(dataBindGrid, self.selectedModeDisplayInBody());

                self.setUpdateMode();
                
                if (self.mode() == 'confirm') {
                    $("#extable").exTable("updateMode", "determine");
                }

                self.setPositionButonToRightToLeft();
                
                self.stopRequest(true);
            }).fail(function(error) {
                self.stopRequest(true);
                nts.uk.ui.dialog.alertError(error);
            });
        }

        /**
        * come back a month
        */
        backMonth(): void {
            let self = this;
            if (self. selectedDisplayPeriod() == 2)
                return;

            self.stopRequest(false);
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());

            let param = {
                viewMode: self.selectedModeDisplayInBody(), // time | shortName | shift
                startDate: self.dateTimePrev(),
                endDate: self.dateTimeAfter(),
                isNextMonth: false,
                cycle28Day: self. selectedDisplayPeriod() == 2 ? true : false,
                workplaceId: userInfor.workplaceId,
                workplaceGroupId: userInfor.workplaceGroupId,
                unit: userInfor.unit,
                getActualData: item.isPresent() ? userInfor.achievementDisplaySelected : false,
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst,
                listSid: self.listSid(),
                modePeriod : self. selectedDisplayPeriod()
            };

            service.getDataChangeMonth(param).done((data: any) => {
                if (userInfor.disPlayFormat == 'shift') {
                    self.saveShiftMasterToLocalStorage(data.shiftMasterWithWorkStyleLst);
                }
                
                self.saveDataGrid(data);
                self.dtPrev(data.dataBasicDto.startDate);
                self.dtAft(data.dataBasicDto.endDate);

                let dataGrid: any = {
                    listDateInfo: data.listDateInfo,
                    listEmpInfo: data.listEmpInfo,
                    displayControlPersonalCond: data.displayControlPersonalCond,
                    listPersonalConditions: data.listPersonalConditions,
                    listWorkScheduleWorkInfor: data.listWorkScheduleWorkInfor,
                    listWorkScheduleShift: data.listWorkScheduleShift
                }
                let dataBindGrid = self.convertDataToGrid(dataGrid, self.selectedModeDisplayInBody());
                
                // remove va tao lai grid
                self.destroyAndCreateGrid(dataBindGrid, self.selectedModeDisplayInBody());

                self.setUpdateMode();

                if (self.mode() == 'confirm') {
                    $("#extable").exTable("updateMode", "determine");
                }

                self.setPositionButonToRightToLeft();
                
                self.stopRequest(true);
            }).fail(function(error) {
                self.stopRequest(true);
                nts.uk.ui.dialog.alertError(error);
            });
        }
        
        //let item = uk.localStorage.getItem(self.KEY);
        //let userInfor: IUserInfor = JSON.parse(item.get());
        editMode() {
            let self = this;
            if (self.mode() == 'edit')
                return;

            let arrCellUpdated = $("#extable").exTable("updatedCells");
            let arrTmp = _.clone(arrCellUpdated);
            let lockCells = $("#extable").exTable("lockCells");
            
            $(".editMode").addClass("A6_hover").removeClass("A6_not_hover");
            $(".confirmMode").addClass("A6_not_hover").removeClass("A6_hover");
            
            $('div.ex-body-leftmost a').css("pointer-events", "");
            $('div.ex-header-detail.xheader a').css("pointer-events", "");
            
            if (lockCells.length > 0 || arrCellUpdated.length > 0) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_1732" }).ifYes(() => {
                    self.editModeAct();
                    
                    let item = uk.localStorage.getItem(self.KEY);
                    let userInfor: IUserInfor = JSON.parse(item.get());
                    let updateMode = userInfor.updateMode;
                    self.convertDataToGrid(self.dataSource, self.selectedModeDisplayInBody());
                    self.updateExTableWhenChangeMode(self.selectedModeDisplayInBody(), updateMode);
                    self.setUpdateMode();
                    nts.uk.ui.block.clear();
                    
                }).ifNo(() => {
                    self.editModeAct();
                    self.setUpdateMode();
                    // check xem co undo redo dc ko
                });
            } else {
                self.editModeAct();
                self.setUpdateMode();
            }
        }
        
        editModeAct() {
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
            self.enableHelpBtn(true);

            if (self.selectedModeDisplayInBody() == 'time' || self.selectedModeDisplayInBody() == 'shortName') {
                // enable combobox workType, workTime
                //__viewContext.viewModel.viewAB.enableListWorkType(true);
                __viewContext.viewModel.viewAB.enableListWorkType(true);
                
                let wTypeCdSelected = __viewContext.viewModel.viewAB.selectedWorkTypeCode();
                let objWtime = _.filter(__viewContext.viewModel.viewAB.listWorkType(), function(o) { return o.workTypeCode == wTypeCdSelected; });
                if (objWtime[0].workTimeSetting != 2) {
                    __viewContext.viewModel.viewAB.disabled(false);
                }
                if (self.selectedModeDisplayInBody() == 'time') {
                    self.visibleBtnInput(true);
                    self.enableBtnInput(true);
                }
            } else {
                self.visibleBtnInput(false);
                self.enableBtnInput(false);
                self.shiftPalletControlEnable();
            }
            nts.uk.ui.block.clear();
        }
        
        confirmMode() {
            let self = this;
            if (self.mode() == 'confirm')
                return;

            $(".editMode").addClass("A6_not_hover").removeClass("A6_hover");
            $(".confirmMode").addClass("A6_hover").removeClass("A6_not_hover");

            $('div.ex-body-leftmost a').css("pointer-events", "none");
            $('div.ex-header-detail.xheader a').css("pointer-events", "none");

            let arrCellUpdated = $("#extable").exTable("updatedCells");

            if (arrCellUpdated.length > 0) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_1732" }).ifYes(() => {
                    self.confirmModeAct();
                    self.convertDataToGrid(self.dataSource, self.selectedModeDisplayInBody());
                    self.updateExTableWhenChangeMode(self.selectedModeDisplayInBody() , "determine");
                    //$("#extable").exTable("updateMode", "determine");
                    nts.uk.ui.block.clear();

                }).ifNo(() => {
                    self.confirmModeAct();
                    $("#extable").exTable("updateMode", "determine");
                });
            } else {
                self.confirmModeAct();
                $("#extable").exTable("updateMode", "determine");
            }
        }
        
        confirmModeAct() {
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
            self.enableHelpBtn(false);

            self.enableBtnRedo(false);
            self.enableBtnUndo(false);

            if (self.selectedModeDisplayInBody() == 'time' || self.selectedModeDisplayInBody() == 'shortName') {
                // disable combobox workType, workTime
                __viewContext.viewModel.viewAB.disabled(true);
                __viewContext.viewModel.viewAB.enableListWorkType(false);
                
                if (self.selectedModeDisplayInBody() == 'time') {
                    self.visibleBtnInput(true);
                    self.enableBtnInput(false);
                }
                
            } else {
                self.visibleBtnInput(false);
                self.enableBtnInput(false);
                self.shiftPalletControlDisable();
            }
            nts.uk.ui.block.clear();
        }
        
        shiftPalletControlEnable() {
            let self = this;
            self.checkEnabDisableTblBtn();
            $('.listLink a').css("pointer-events", "");
            __viewContext.viewModel.viewAC.enableSwitchBtn(true);
            __viewContext.viewModel.viewAC.enableCheckBoxOverwrite(true);
            __viewContext.viewModel.viewAC.enableBtnOpenDialogJB1(true);
        }
        
        shiftPalletControlDisable() {
            let self = this;
            if (__viewContext.viewModel.viewAC.selectedpalletUnit() == 1) { // 1 : mode company , 2: mode workPlace
                $('#tableButton1 button').addClass('disabledShiftControl');
            } else {
                $('#tableButton2 button').addClass('disabledShiftControl');
            }
            $('.listLink a').css("pointer-events", "none");
            __viewContext.viewModel.viewAC.enableSwitchBtn(false);
            __viewContext.viewModel.viewAC.enableCheckBoxOverwrite(false);
            __viewContext.viewModel.viewAC.enableBtnOpenDialogJB1(false);
        }
        
        checkEnabDisableTblBtn() {
            if (__viewContext.viewModel.viewAC.selectedpalletUnit() == 1) { // 1 : mode company , 2: mode workPlace
                if (__viewContext.viewModel.viewAC.listPageComIsEmpty == true) {
                    $('#tableButton1 button').addClass('disabledShiftControl');
                } else {
                    $('#tableButton1 button').removeClass('disabledShiftControl');
                }
            } else {
                if (__viewContext.viewModel.viewAC.listPageWkpIsEmpty == true) {
                    $('#tableButton2 button').addClass('disabledShiftControl');
                } else {
                    $('#tableButton2 button').removeClass('disabledShiftControl');
                }
            }
        }
        
        removeClass() {
            let self = this;
            $("#paste").addClass("A6_not_hover").removeClass("A6_hover btnControlUnSelected btnControlSelected");
            $("#coppy").addClass("A6_not_hover").removeClass("A6_hover btnControlUnSelected btnControlSelected");
            $("#input").addClass("A6_not_hover").removeClass("A6_not_hover btnControlUnSelected btnControlSelected");
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
            $("#paste").addClass("btnControlSelected A6_hover").removeClass("btnControlUnSelected A6_not_hover");
            $("#coppy").addClass("btnControlUnSelected A6_not_hover").removeClass("btnControlSelected A6_hover");
            $("#input").addClass("btnControlUnSelected A6_not_hover").removeClass("btnControlSelected A6_hover");
            
            $("#extable").exTable("updateMode", "stick");
            self.enableBtnUndo(false);
            self.enableBtnRedo(false);
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor : IUserInfor = JSON.parse(data);
                userInfor.updateMode = 'stick';
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
            
            if (self.selectedModeDisplayInBody() == 'time' || self.selectedModeDisplayInBody() == 'shortName') {
                $("#extable").exTable("stickMode", "single");
                if (self.selectedModeDisplayInBody() == 'time'){
                    $("#extable").exTable("stickFields", ["workTypeName","workTimeName", "startTime", "endTime"]);
                } else {
                    $("#extable").exTable("stickFields", ["workTypeName","workTimeName"]);
                }
                
                // set lai data stick
                let objWorkTime = __viewContext.viewModel.viewAB.objWorkTime;
                if (objWorkTime != undefined) {
                    __viewContext.viewModel.viewAB.workTimeCode(objWorkTime.code);
                    __viewContext.viewModel.viewAB.updateDataCell(objWorkTime);
                }
                
            } else if (self.selectedModeDisplayInBody() == 'shift') {
                $("#extable").exTable("stickMode", "multi");
                $("#extable").exTable("stickFields", ["shiftName"]);
                // set lai data stick
                if (__viewContext.viewModel.viewAC.selectedpalletUnit() == 1) {
                    let selectedBtnTblCom = __viewContext.viewModel.viewAC.selectedButtonTableCompany();
                    if (selectedBtnTblCom.hasOwnProperty('data')) {
                        __viewContext.viewModel.viewAC.selectedButtonTableCompany(selectedBtnTblCom);
                    }
                } else {
                    let selectedBtnTblWkp = __viewContext.viewModel.viewAC.selectedButtonTableWorkplace();
                    if (selectedBtnTblWkp.hasOwnProperty('data')) {
                        __viewContext.viewModel.viewAC.selectedButtonTableWorkplace(selectedBtnTblWkp);
                    }
                }
            }
            
            $("#extable").exTable("stickValidate", function(rowIdx, key, data) {
                let dfd = $.Deferred();
                let item = uk.localStorage.getItem(self.KEY);
                let userInfor: IUserInfor = JSON.parse(item.get());
                if (userInfor.disPlayFormat == 'time' || userInfor.disPlayFormat == 'shortName') {
                    let workType = self.dataCell.objWorkType;
                    let workTime = self.dataCell.objWorkTime;
                    // truong hop chon workType = required va workTime = 選択なし 
                    if ((workType.workTimeSetting == 0 && workTime.code == '')) {
                        nts.uk.ui.dialog.alertError({ messageId: 'Msg_435' });
                        dfd.resolve(false);
                    } else if ((workType.workTimeSetting == 0 && workTime.code == ' ')) {
                        // truong hop chon workType = required va workTime = 据え置き 
                        // neu cell dc stick ma khong co workTime se alertError 435
                        let dataSource = $("#extable").exTable('dataSource', 'detail').body;
                        let cellData = dataSource[rowIdx][key];
                        if (_.isNil(cellData.workTimeName)) {
                            nts.uk.ui.dialog.alertError({ messageId: 'Msg_435' });
                            dfd.resolve(false);
                        } else {
                            dfd.resolve(true);
                        }
                    } else {
                        dfd.resolve(true);
                    }
                } else if (userInfor.disPlayFormat == 'shift') {
                    // truong hop listPage empty thi khong can validate
                    if ((__viewContext.viewModel.viewAC.selectedpalletUnit()) == 1 && (__viewContext.viewModel.viewAC.listPageComIsEmpty == true)) {
                            dfd.reject();
                            return;
                    }

                    if ((__viewContext.viewModel.viewAC.selectedpalletUnit()) == 2 && (__viewContext.viewModel.viewAC.listPageWkpIsEmpty == true)) {
                        dfd.reject();
                        return;
                    } 
                    
                    // neu data = {} thi la co  shiftmaster đa bị xóa
                    // => stick show mess 1728
                    let isRemove = Object.keys(data).length === 0 && data.constructor === Object;
                    if(isRemove){
                         nts.uk.ui.dialog.alertError({ messageId: 'Msg_1727' });   
                    }
                    
                    self.stopRequest(false);
                    // khoi tao param ddeer truyen leen server check xem shiftmaster đã bị xóa đi hay chưa
                    let param = [];
                    for (item in data) {
                        let x = {
                            shiftmastercd: data[item].shiftCode,
                            workTypeCode: data[item].workTypeCode,
                            workTimeCode: data[item].workTimeCode
                        };
                        param.push(x);
                    }
                    service.validWhenPaste(param).done((data) => {
                        if (data == true) {
                            dfd.resolve(true);
                        } 
                        self.stopRequest(true);
                    }).fail(function() {
                        self.stopRequest(true);
                        dfd.reject();
                    }).always((data) => {
                        if (data.messageId == "Msg_1728") {
                            nts.uk.ui.dialog.alertError({ messageId: 'Msg_1728' });
                        }
                        self.stopRequest(true);
                    });
                }
                return dfd.promise();
            });
            
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
            $("#paste").addClass("btnControlUnSelected A6_not_hover").removeClass("btnControlSelected A6_hover");
            $("#coppy").addClass("btnControlSelected A6_hover").removeClass("btnControlUnSelected A6_not_hover");
            $("#input").addClass("btnControlUnSelected A6_not_hover").removeClass("btnControlSelected A6_hover");
            $("#extable").exTable("updateMode", "copyPaste");
            self.enableBtnUndo(false);
            self.enableBtnRedo(false);
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor : IUserInfor = JSON.parse(data);
                userInfor.updateMode =  'copyPaste';
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
            
            self.setCoppyStyler();
            
            $("#extable").exTable("pasteValidate", function(data) {
                let dfd = $.Deferred();
                let item = uk.localStorage.getItem(self.KEY);
                let userInfor: IUserInfor = JSON.parse(item.get());
                let param = [];
                self.stopRequest(false);
                _.forEach(data, d => {
                    let shiftCode = d.shiftCode;
                    let shiftMasterWithWorkStyleLst = userInfor.shiftMasterWithWorkStyleLst;
                    let objShiftMaster = _.filter(shiftMasterWithWorkStyleLst, function(o) { return o.shiftMasterCode == shiftCode; });
                    if (objShiftMaster.length > 0) {
                        let x = {
                            shiftmastercd: shiftCode,
                            workTypeCode: objShiftMaster[0].workTypeCode,
                            workTimeCode: objShiftMaster[0].workTimeCode
                        };
                        param.push(x);
                    }
                });
                service.validWhenPaste(param).done((data) => {
                    if (data == true) {
                        dfd.resolve(true);
                    }
                    self.stopRequest(true);
                }).fail(function() {
                    self.stopRequest(true);
                    dfd.reject();
                }).always((data) => {
                    if (data.messageId == "Msg_1728") {
                        nts.uk.ui.dialog.alertError({ messageId: 'Msg_1728' });
                    }
                    self.stopRequest(true);
                });
                return dfd.promise();
            });
            nts.uk.ui.block.clear();
        }

        inputData(): void {
            let self = this;
            if (self.mode() == 'confirm')
                return;
            nts.uk.ui.block.grayout();
            $("#paste").addClass("btnControlUnSelected A6_not_hover").removeClass("btnControlSelected A6_hover");
            $("#coppy").addClass("btnControlUnSelected A6_not_hover").removeClass("btnControlSelected A6_hover");
            $("#input").addClass("btnControlSelected A6_hover").removeClass("btnControlUnSelected A6_not_hover");
            
            $("#extable").exTable("updateMode", "edit");
            self.enableBtnUndo(false);
            self.enableBtnRedo(false);
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor: IUserInfor = JSON.parse(data);
                userInfor.updateMode = 'edit';
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });
            
            nts.uk.ui.block.clear();
        }
        
        inputDataValidate(param): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            self.stopRequest(false);
            service.validWhenEditTime(param).done((data: any) => {
                if (data == true) {
                    dfd.resolve(true);
                }
                self.stopRequest(true);
            }).fail(function(error) {
                self.stopRequest(true);
                nts.uk.ui.dialog.alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        undoData(): void {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(item.get());
            if (userInfor.updateMode == 'stick') {
                $("#extable").exTable("stickUndo");
            } else if (userInfor.updateMode = 'copyPaste') {
                $("#extable").exTable("copyUndo");
            } else if (userInfor.updateMode = 'edit') {
                console.log('grid chua support undo o mode nay');
            }
            self.checkExitCellUpdated();
            self.enableBtnRedo(true);
        }

        redoData(): void {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(item.get());
            if (userInfor.updateMode == 'stick') {
                $("#extable").exTable("stickRedo");
            } else if (userInfor.updateMode == 'copyPaste') {
                $("#extable").exTable("copyRedo");
            } else if (userInfor.updateMode = 'edit') {
                console.log('grid chua support redo o mode nay');
            }
            self.checkExitCellUpdated();
        }
        
        checkExitCellUpdated() {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor = JSON.parse(item.get());
            setTimeout(() => {
                if (userInfor.updateMode == 'stick') {
                    
                    // check undo
                    let $grid1   = $("#extable").find("." + "ex-body-detail");
                    let histories = $grid1.data("stick-history");
                    if (!histories || histories.length === 0){
                        self.enableBtnUndo(false);
                    } else {
                        self.enableBtnUndo(true);
                    }
                    
                    // check redo
                    let $grid2 = $("#extable").find(`.${"ex-body-detail"}`);
                    let redoStack = $grid2.data("stick-redo-stack");
                    if (!redoStack || redoStack.length === 0){
                        self.enableBtnRedo(false);
                    } else {
                        self.enableBtnRedo(true);
                    }
                } else if (userInfor.updateMode == 'copyPaste') {
                    // check undo
                    let $grid1 = $("#extable").find("." + "ex-body-detail");
                    let histories = $grid1.data("copy-history");
                    if (!histories || histories.length === 0){
                        self.enableBtnUndo(false);
                    } else {
                        self.enableBtnUndo(true);
                    }
                    
                    // check redo
                    let $grid2 = $("#extable").find("." + "ex-body-detail");
                    let redoStack = $grid2.data("redo-stack");
                    if (!redoStack || redoStack.length === 0) {
                        self.enableBtnRedo(false);
                    } else {
                        self.enableBtnRedo(true);
                    }
                }
            }, 1);
        }

        /**
          * open dialog D
         */
        openDialogU(): void {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor : IUserInfor = JSON.parse(item.get());

             setShared('dataShareDialogU', {                
                startDate: moment(self.dtPrev()).format('YYYY/MM/DD'),
                endDate: moment(self.dtAft()).format('YYYY/MM/DD'),
                unit: userInfor.unit,
                workplaceId: userInfor.workplaceId,
                workplaceGroupId: userInfor.workplaceGroupId,
            });

            nts.uk.ui.windows.sub.modal("/view/ksu/001/u/index.xhtml").onClosed(() => {
                if (getShared("dataFromScreenD") && !getShared("dataFromScreenD").clickCloseDialog) {
                    // to do
                }
            });
        }
        
        /**
          * open dialog G
         */
        openDialogG(): void {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());

            // listEmpData : {id : '' , code : '', name : ''}
            setShared('dataShareDialogG', {
                startDate   : moment(self.dtPrev()).format('YYYY/MM/DD'),
                endDate     : moment(self.dtAft()).format('YYYY/MM/DD'),
                employeeIDs : self.sids(),
            });
            nts.uk.ui.windows.sub.modeless("/view/ksu/001/g/index.xhtml").onClosed(() => {
                console.log('closed g dialog');
            });
        }

        // A2_1
        openKDL046() {
            let self = this;
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());

            let param = {
                unit: userInfor.unit == 0 ? '0' : '1',
                date: moment(self.dateTimeAfter()),
                workplaceId: userInfor.unit == 0 ? userInfor.workplaceId : userInfor.workplaceGroupId,
                workplaceGroupId: userInfor.unit == 0 ? userInfor.workplaceId : userInfor.workplaceGroupId,
                showBaseDate : false
            }
            setShared('dataShareDialog046', param);
            $('#A1_10_1').ntsPopup('hide');
            nts.uk.ui.windows.sub.modal('/view/kdl/046/a/index.xhtml').onClosed(function(): any {
                let dataFrom046 = getShared('dataShareKDL046');
                if (dataFrom046 === undefined || dataFrom046 === null)
                    return;
                self.updateScreen(dataFrom046);
                console.log('closed');
            });
        }
        
        updateScreen(input: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.stopRequest(false);
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());
            let param = {
                viewMode: userInfor.disPlayFormat,
                startDate: self.dateTimePrev(),
                endDate: self.dateTimeAfter(),
                shiftPalletUnit: userInfor.shiftPalletUnit, // 1: company , 2 : workPlace 
                pageNumberCom: userInfor.shiftPalettePageNumberCom,
                pageNumberOrg: userInfor.shiftPalettePageNumberOrg,
                getActualData: false,
                listShiftMasterNotNeedGetNew: userInfor.shiftMasterWithWorkStyleLst, // List of shifts không cần lấy mới
                unit: input.unit,
                wkpId: input.unit == 0 ? input.workplaceId : input.workplaceGroupID
            };
            
            service.changeWokPlace(param).done((data: IDataStartScreen) => {
                
                self.targetOrganizationName(input.unit == 0 ? input.workplaceName : input.workplaceGroupName);
                
                let item = uk.localStorage.getItem(self.KEY);
                let userInfor: IUserInfor  = JSON.parse(item.get());
                userInfor.unit             = input.unit;
                userInfor.workplaceId      = input.unit == 0 ? input.workplaceId : '';
                userInfor.workplaceGroupId = input.unit == 0 ? '' : input.workplaceGroupID;
                userInfor.workPlaceName    = input.unit == 0 ? input.workplaceName : input.workplaceGroupName;
                userInfor.code             = input.workplaceGroupCode;
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
                
                __viewContext.viewModel.viewAB.workplaceIdKCP013(input.unit == 0 ? input.workplaceId : input.workplaceGroupID);

                self.saveDataGrid(data);
                
                let dataGrid: any = {
                    listDateInfo: data.listDateInfo,
                    listEmpInfo: data.listEmpInfo,
                    displayControlPersonalCond: data.displayControlPersonalCond,
                    listPersonalConditions: data.listPersonalConditions,
                    listWorkScheduleWorkInfor: data.listWorkScheduleWorkInfor,
                    listWorkScheduleShift: data.listWorkScheduleShift
                }
                let dataBindGrid = self.convertDataToGrid(dataGrid, self.selectedModeDisplayInBody());
                
                // remove va tao lai grid
                self.destroyAndCreateGrid(dataBindGrid, self.selectedModeDisplayInBody());

                self.setUpdateMode();
                
                if (self.mode() == 'confirm') {
                    $("#extable").exTable("updateMode", "determine");
                }

                self.setPositionButonToRightToLeft();

                self.stopRequest(true);
                
            }).fail(function(error) {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        // A1_12_8
        openQDialog() {
            let self = this;
            let period = {
                    startDate: self.dateTimePrev(),
                    endDate: self.dateTimeAfter()
                };
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor : IUserInfor = JSON.parse(item.get());
            
            setShared('target', {
                unit: userInfor.unit,
                id: userInfor.unit == 0 ? userInfor.workplaceId : userInfor.workplaceGroupId
            });
            
            setShared('period', {
                startDate: self.dateTimePrev(),
                endDate: self.dateTimeAfter()
            });
            
            $('#A1_12_1').ntsPopup('hide');
            nts.uk.ui.windows.sub.modal("/view/ksu/001/q/index.xhtml").onClosed(() => {
            });
        }

        // A1_12_16
        openSDialog(): void {
            let self = this;
            //hiện giờ truyền sang workplaceId va tất cả emmployee . Sau này sửa truyền list employee theo workplace id
            setShared("baseDate", ko.observable(self.dateTimeAfter()));

            // listEmpData : {id : '' , code : '', name : ''}
            setShared('dataShareDialogG', {
                endDate: moment(self.dtAft()).format('YYYY/MM/DD'),
                listEmp: self.listEmpData
            });

            $('#A1_12_1').ntsPopup('hide');
            nts.uk.ui.windows.sub.modal("/view/ksu/001/s/index.xhtml").onClosed(() => {
                let dataShare = getShared("ksu001s-result");
                if (dataShare !== 'Cancel') {
                    self.stopRequest(false);
                    self.getListEmpIdSorted().done(() => {
                        self.stopRequest(true);
                    });
                }
            });
        }
        
        // A1_12_18
        openLDialog(): void {
            let self = this;
            //hiện giờ truyền sang workplaceId va tất cả emmployee . Sau này sửa truyền list employee theo workplace id
            setShared("baseDate", ko.observable(self.dateTimeAfter()));
            $('#A1_12_1').ntsPopup('hide');
            nts.uk.ui.windows.sub.modal("/view/ksu/001/la/index.xhtml").onClosed(() => {
                let dataShare = getShared("ksu001la-result");
                if (dataShare !== 'Cancel') {
                    self.stopRequest(false);
                    self.getListEmpIdSorted().done(() => {
                        self.stopRequest(true);
                    });
                }
            });
        }
        
        // A1_12_20
        openMDialog(): void {
            let self = this;
            setShared("KSU001M", self.listEmpData);
            $('#A1_12_1').ntsPopup('hide');
            nts.uk.ui.windows.sub.modal("/view/ksu/001/m/index.xhtml").onClosed(() => {
                let dataShare = getShared("ksu001m-result");
                if (dataShare !== 'Cancel') {
                    self.stopRequest(false);
                    self.getListEmpIdSorted().done(() => {
                        self.stopRequest(true);
                    });
                }
            });
        }

        getListEmpIdSorted(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let item = uk.localStorage.getItem(self.KEY);
            let userInfor: IUserInfor = JSON.parse(item.get());
            
            let param = {
                endDate: self.dateTimeAfter(),
                listEmpInfo: self.listEmpInfo
            }
            service.getListEmpIdSorted(param).done((data) => {
                // update lai grid
                if (data.lstEmp.length > 0) {
                    self.listEmpInfo = data.lstEmp;
                    self.listPersonalConditions = data.listPersonalCond;
                    let dataGrid: any = {
                        listEmpInfo: data.lstEmp,
                        listWorkScheduleWorkInfor: self.listWorkScheduleWorkInfor,
                        listWorkScheduleShift: self.listWorkScheduleShift,
                        listPersonalConditions: data.listPersonalCond,
                        displayControlPersonalCond: self.displayControlPersonalCond,
                        listDateInfo: self.listDateInfo
                    }

                    let dataBindGrid = self.convertDataToGrid(dataGrid, self.selectedModeDisplayInBody());

                    self.updateExTable(dataBindGrid, self.selectedModeDisplayInBody(), userInfor.updateMode, true, true, true);

                    self.stopRequest(true);
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
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
                    if ((i == 0 && +arrDay[0].day != 1) || (+arrDay[i].day == 1)) {
                        this['_' + arrDay[i].yearMonthDay] = arrDay[i].month + '/' + arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                    } else {
                        this['_' + arrDay[i].yearMonthDay] = arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                    }
                }
                return;
            }
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
        shiftCode: string;
        
        
        constructor(workTypeCode: string, workTypeName: string, workTimeCode: string, workTimeName: string, startTime?: string, endTime?: string, shiftName?: any, shiftCode? : any) {
            this.workTypeCode = workTypeCode;
            this.workTypeName = workTypeName;
            this.workTimeCode = workTimeCode;
            this.workTimeName = workTimeName;
            this.shiftName = shiftName !== null ? shiftName : '';
            this.startTime = ( startTime == undefined || startTime == null ) ? '' : startTime;
            this.endTime = ( endTime == undefined || endTime == null ) ? '' : endTime;
            this.shiftCode = shiftCode !== null ? shiftCode : '';
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
        
        listPageInfo: Array<IPageInfo>,
        shiftMasterWithWorkStyleLst : Array<IShiftMasterMapWithWorkStyle>,
        listWorkScheduleShift: Array<IWorkScheduleShiftInforDto>,
    }
    
    interface IPageInfo {
        pageName: string,
        pageNumber: number,
    }

    interface IDataBasicDto {
        startDate: string,
        endDate: string,
        designation: string,
        targetOrganizationName: string,
        unit: number,
        workplaceId: string,
        workplaceGroupId: string,
        code: string
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
        constructor(key: string, value: string) {
            this.key = key;
            this.value = value;
        }
    }

    interface IWorkTypeInfomation {
        workTypeDto: IWorkTypeDto,    // 勤務種類選択 - 勤務種類 
        workTimeSetting: number,          // 必須任意不要区分    
        workStyle: number,          // 出勤休日区分
    }

    interface IWorkTypeDto {
        workTypeCode: string, // 勤務種類コード - コード
        name: string,         // 勤務種類名称  - 表示名
        memo: string,
        workTimeSetting: number         // 必須任意不要区分 
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
        isEdit: boolean;
        isActive: boolean;
        workTypeNameIsNull: boolean; 
        workTimeNameIsNull: boolean;
        workTypeIsNotExit : boolean;
        workTimeIsNotExit : boolean;
    }

    interface IWorkScheduleShiftInforDto {
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
        isEdit: boolean;
        isActive: boolean;
    }

    enum AttendanceHolidayAttr {
        FULL_TIME = 3, //(3, "１日出勤系"),
        MORNING = 1, //(1, "午前出勤系"),
        AFTERNOON = 2, //(2, "午後出勤系"),
        HOLIDAY = 0, //(0, "１日休日系");
    }

    enum SupportCategory {
        NotCheering = 1, // 応援ではない
        TimeSupporter = 2, // 時間帯応援元
        TimeSupport = 3, // 時間帯応援先
        SupportFrom = 4, // 終日応援元
        SupportTo = 5, // 終日応援先
    }

    interface IUserInfor {
        disPlayFormat: string;
        backgroundColor: number; // 背景色
        achievementDisplaySelected: boolean;
        shiftPalletUnit: number;
        shiftPalettePageNumberCom: number;
        shiftPalletPositionNumberCom: {};
        shiftPalettePageNumberOrg: number;
        shiftPalletPositionNumberOrg: {};
        gridHeightSelection: number;
        heightGridSetting: number;
        unit: number;
        workplaceId: string;
        workplaceGroupId: string;
        workPlaceName: string;
        workType: {};
        workTime: {};
        updateMode : string; // updatemode cua grid
        startDate : string;
        endDate : string;
        code: string;
        shiftMasterWithWorkStyleLst : Array<IShiftMasterMapWithWorkStyle>;
    }
    
    interface IShiftMasterMapWithWorkStyle {
        companyId: string;
        shiftMasterName: string;
        shiftMasterCode: string;
        color: string;
        remark: string;
        workTypeCode: string;
        workTimeCode: string;
        workStyle: string;
    }
}