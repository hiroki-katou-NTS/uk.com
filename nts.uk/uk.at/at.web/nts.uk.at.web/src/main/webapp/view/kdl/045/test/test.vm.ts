module nts.uk.at.view.test.vm {

    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import shareModelData = nts.uk.at.view.kdl045.share.model;   
    const DATE_FORMAT_YYYY_MM_DD = "YYYY/MM/DD";

    export class ScreenModel {

        

        shiftItems: KnockoutObservableArray<ShiftMaster>;
        selectedShiftMaster: KnockoutObservableArray<any>;
        options: Option;
        
        // CCG001
        ccg001ComponentOption: GroupOption;
        
        datepickerValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;            
        
        // KCP005 start
        listComponentOption: any;
        selectedIdEmployee: KnockoutObservableArray<string>;
        selectedCodeEmployee: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel>;
        showOptionalColumn: KnockoutObservable<boolean>;
        // KCP005 end
        
        lstOutputItemCode: KnockoutObservableArray<ItemModel>;
        selectedOutputItemCode: KnockoutObservable<string>;
        
        checkedCardNOUnregisteStamp: KnockoutObservable<boolean>;
        enableCardNOUnregisteStamp: KnockoutObservable<boolean>;
        listEmpSetShare: any =ko.observableArray([]);
        
        //
        workType: KnockoutObservable<string> = ko.observable("");
        workTypeName: KnockoutObservable<string> = ko.observable("");
        workTime: KnockoutObservable<string> = ko.observable("");
        workTimeName: KnockoutObservable<string> = ko.observable("");
        
        directAtr: KnockoutObservable<boolean> = ko.observable( false);
        bounceAtr: KnockoutObservable<boolean> = ko.observable( false);
    
        timeRange1Value: KnockoutObservable<any>;
        timeRange2Value: KnockoutObservable<any>;
        
        isNursing : KnockoutObservable<boolean> = ko.observable( true);
        timeChildcareNursing1: KnockoutObservable<any>;
        timeChildcareNursing2: KnockoutObservable<any>;
        
        timeRange: KnockoutObservable<any>;
        dataSourceTime: KnockoutObservableArray<any>;
        
        canModified : KnockoutObservable<boolean> = ko.observable( true);
        targetInfor : KnockoutObservable<boolean> = ko.observable( true);
        
        isFixed : KnockoutObservable<boolean> = ko.observable( true);
        isFlex : KnockoutObservable<boolean> = ko.observable( true);
        isFlow : KnockoutObservable<boolean> = ko.observable( true);
        isTimeDifference: KnockoutObservable<boolean> = ko.observable( true);
        
        fixBreakTime : KnockoutObservable<boolean> = ko.observable( true);
        
        date: KnockoutObservable<string> = ko.observable("2020/01/01");
        
        atWork1Range: KnockoutObservable<any>;
        atWork2Range: KnockoutObservable<any>;
        offWork1Range: KnockoutObservable<any>;
        offWork2Range: KnockoutObservable<any>;
        privateTimeRange1: KnockoutObservable<any>;
        unionTimeRange1: KnockoutObservable<any>;
        privateTimeRange2: KnockoutObservable<any>;
        unionTimeRange2: KnockoutObservable<any>;

        timeAbbyakLeave :KnockoutObservable<number> = ko.observable(10);
        timeOff :KnockoutObservable<number> = ko.observable(10);
        excessPaidHoliday :KnockoutObservable<number> = ko.observable(10);
        childNursingLeave :KnockoutObservable<number> = ko.observable(10);
        nursingCareLeave :KnockoutObservable<number> = ko.observable(10);
        specialHoliday1 :KnockoutObservable<number> = ko.observable(10);
        specialHoliday2 :KnockoutObservable<number> = ko.observable(10);
        
        constructor() {
            
            let self = this;
            self.declareCCG001();
            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");
            self.datepickerValue = ko.observable({});

            self.selectedIdEmployee = ko.observableArray([]);
            self.selectedCodeEmployee = ko.observableArray([]);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([]);
            self.isDialog = ko.observable(true);
            self.isShowNoSelectRow = ko.observable(true);
            self.isMultiSelect = ko.observable(true);
            self.isShowWorkPlaceName = ko.observable(true);
            self.isShowSelectAllButton = ko.observable(false);
            self.showOptionalColumn = ko.observable(false);
            self.employeeList = ko.observableArray<UnitModel>([]);
            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.NO_SELECT,
                selectedCode: self.selectedCodeEmployee,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                showOptionalColumn: self.showOptionalColumn(),
                maxRows: 15
            };

            self.lstOutputItemCode = ko.observableArray([]);

            self.selectedOutputItemCode = ko.observable('');
            self.checkedCardNOUnregisteStamp = ko.observable(false);
            self.enableCardNOUnregisteStamp = ko.observable(true);

            self.conditionBinding();
            self.selectedCodeEmployee.subscribe(function(value) {
                let emps = _.filter(self.employeeList(), function(emp) {
                    return _.indexOf(self.selectedCodeEmployee(), emp.code) != -1;
                });
                self.listEmpSetShare(emps);
            })

            //
            self.timeRange1Value = ko.observable({
                startTime: 0,
                endTime: 100 
            });
            self.timeRange2Value = ko.observable({
                startTime: 101,
                endTime: 102
            });
            
             self.timeChildcareNursing1 = ko.observable({
                startTime: 60,
                endTime: 120 
            });
            self.timeChildcareNursing2 = ko.observable({
                startTime: 121,
                endTime: 180
            });
            
            self.dataSourceTime = ko.observableArray([]);
            
            self.timeRange = ko.observable({
                    maxRow: 10,
                    minRow: 0,
                    maxRowDisplay: 10,
                    isShowButton: true,
                    dataSource: self.dataSourceTime,
                    isMultipleSelect: true,
                    columns: self.settingColumns(),
                    tabindex :9
                });
            
            self.atWork1Range = ko.observable({
                startTime: 0,
                endTime: 30
            });
            self.atWork2Range = ko.observable({
                startTime: 0,
                endTime: 31
            });
            self.offWork1Range = ko.observable({
                startTime: 0,
                endTime: 32
            });
            self.offWork2Range = ko.observable({
                startTime: 0,
                endTime: 33
            });
            self.privateTimeRange1 = ko.observable({
                startTime: 0,
                endTime: 34
            });
            self.unionTimeRange1 = ko.observable({
                startTime: 0,
                endTime: 35
            });
            self.privateTimeRange2 = ko.observable({
                startTime: 35,
                endTime: 100
            });
            self.unionTimeRange2 = ko.observable({
                startTime: 36,
                endTime: 110
            });
            
        }
        
        private settingColumns(): Array<any> {
                let self = this;
                return [
                    {
                        headerText: nts.uk.resource.getText("KDL045_30"), key: "range1", defaultValue: ko.observable({ startTime: 0, endTime: 0,breakFrameNo: 0 }),
                        width: 243, template:
                        `<div data-bind="ntsTimeRangeEditor: {required: true,
                            enable: true,
                            inputFormat: 'time',
                            startTimeNameId: '#[KDL045_32]',
                            endTimeNameId: '#[KDL045_33]',
                            startConstraint: 'TimeWithDayAttr',
                            endConstraint: 'TimeWithDayAttr',
                            paramId: 'KDL045_31'
                                }
                            "/>`
                    }
                ];
            }
        
        
         public openDialogKDL003(): void {
                let self = this;
                // set update data input open dialog kdl003
                nts.uk.ui.windows.setShared('parentCodes', {
                    workTypeCodes: [],
                    selectedWorkTypeCode: self.workType(),
                    workTimeCodes: [],
                    selectedWorkTimeCode: self.workTime()
                }, true);

                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                    //view all code of selected item 
                    let childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.workType(childData.selectedWorkTypeCode);
                        self.workTypeName(childData.selectedWorkTypeName);
                        self.workTime(childData.selectedWorkTimeCode);
                        self.workTimeName(childData.selectedWorkTimeName);

//                        let timeRange1ScreenModel = $("#a5-5").data("screenModel");
//                        if (timeRange1ScreenModel) {
//                            timeRange1ScreenModel.startTime(childData.first.start != null ? childData.first.start : 0);
//                            timeRange1ScreenModel.endTime(childData.first.end != null ? childData.first.end : 0);
//                        }
//
//                        let timeRange2ScreenModel = $("#a5-9").data("screenModel");
//                        if (timeRange2ScreenModel) {
//                            timeRange2ScreenModel.startTime(childData.second.start != null ? childData.second.start : 0);
//                            timeRange2ScreenModel.endTime(childData.second.end != null ? childData.second.end : 0);
//                        }
                    }
                });
            }
        
         private conditionBinding(): void {
                let self = this;
                
                self.startDateString.subscribe(function(value){
                    self.datepickerValue().startDate = value;
                    self.datepickerValue.valueHasMutated();        
                });
                
                self.endDateString.subscribe(function(value){
                    self.datepickerValue().endDate = value;   
                    self.datepickerValue.valueHasMutated();      
                });
                
                self.checkedCardNOUnregisteStamp.subscribe((newValue) => {
//                    if (newValue) {
//                        $('#ccg001-btn-search-drawer').addClass("disable-cursor");
//                    } else {
//                        $('#ccg001-btn-search-drawer').removeClass("disable-cursor");
//                    }
                })
            }
        
        private declareCCG001(): void {
            let self = this;
            // Set component option
            self.ccg001ComponentOption = {
                /** Common properties */
                systemType: 2,
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: false,
                showClosure: false,
                showAllClosure: false,
                showPeriod: true,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: false,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: false,
                isMutipleCheck: true,

                /**
                * Self-defined function: Return data from CCG001
                * @param: data: the data return from CCG001
                */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    let arrEmployeelst: UnitModel[] = [];
                    _.forEach(data.listEmployee, function(value) {
                        arrEmployeelst.push({ code: value.employeeCode, name: value.employeeName, affiliationName: value.affiliationName, id: value.employeeId });
                    });
                    self.employeeList(arrEmployeelst);
                }
            }
        }

        startPage(): JQueryPromise<any> {
            var dfd = $.Deferred<void>();
                let self = this,
                    companyId: string = __viewContext.user.companyId,
                    userId: string = __viewContext.user.employeeId;
                
                $.when(service.getDataStartPage(), service.restoreCharacteristic(companyId, userId))
                                    .done((dataStartPage, dataCharacteristic) => {
                    // get data from server
                    self.startDateString(dataStartPage.startDate);
                    self.endDateString(dataStartPage.endDate);
                    self.ccg001ComponentOption.periodStartDate = moment.utc(dataStartPage.startDate, DATE_FORMAT_YYYY_MM_DD).toISOString();
                    self.ccg001ComponentOption.periodEndDate = moment.utc(dataStartPage.endDate, DATE_FORMAT_YYYY_MM_DD).toISOString();
                      
                    let arrOutputItemCodeTmp: ItemModel[] = [];
                    _.forEach(dataStartPage.lstStampingOutputItemSetDto, function(value) {
                        arrOutputItemCodeTmp.push(new ItemModel(value.stampOutputSetCode, value.stampOutputSetName));  
                    });
                    self.lstOutputItemCode(arrOutputItemCodeTmp);                    
                                        
                    // get data from characteris
                    if (!_.isUndefined(dataCharacteristic)) {
                        self.checkedCardNOUnregisteStamp(dataCharacteristic.cardNumNotRegister);
                        self.selectedOutputItemCode(dataCharacteristic.outputSetCode);    
                    }
                    
                    // enable button when exist Authority of employment form                                        
                    self.enableCardNOUnregisteStamp(dataStartPage.existAuthEmpl);
                                        
                    dfd.resolve();
                })
                return dfd.promise();

        }
         /**
            * binding component CCG001 and KCP005
            */
        public executeComponent(): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            let self = this;
            $.when($('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption),
                $('#employee-list').ntsListComponent(self.listComponentOption)).done(() => {
                    self.changeHeightKCP005();
                    dfd.resolve();
                });
            return dfd.promise();
        }
        private changeHeightKCP005(): void {
            let _document: any = document,
                isIE = /*@cc_on!@*/false || !!_document.documentMode;
            if (isIE) {
                let heightKCP = $('div[id$=displayContainer]').height();
                $('div[id$=displayContainer]').height(heightKCP + 3);
                $('div[id$=scrollContainer]').height(heightKCP + 3);
            }
        }
        openDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            if(self.employeeList().length !=1 ){
                nts.uk.ui.dialog.alertError({ messageId: 'Have select one employee !!!'});
                nts.uk.ui.block.clear();
                return;
            }
            let supportTimeZone :shareModelData.TimeZoneDto ={
                startTime : new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY,101),
                endTime : new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY,301)
            }
            
            let listShortTime = [];
            if(self.isNursing()){
                listShortTime.push(new shareModelData.ShortTimeDto(1,0,self.timeChildcareNursing1().startTime,self.timeChildcareNursing1().endTime));
                listShortTime.push(new shareModelData.ShortTimeDto(2,0,self.timeChildcareNursing2().startTime,self.timeChildcareNursing2().endTime));
            }else{
                listShortTime.push(new shareModelData.ShortTimeDto(1,1,self.timeChildcareNursing1().startTime,self.timeChildcareNursing1().endTime));
                listShortTime.push(new shareModelData.ShortTimeDto(2,1,self.timeChildcareNursing2().startTime,self.timeChildcareNursing2().endTime));
            }
            
            let dataUsageTime: Array<shareModelData.DailyAttdTimeVacationDto> = [];
                 dataUsageTime.push( new shareModelData.DailyAttdTimeVacationDto(self.timeAbbyakLeave(),self.timeOff(),self.excessPaidHoliday(),self.specialHoliday1() ,1,self.childNursingLeave(),self.nursingCareLeave()));
                 dataUsageTime.push( new shareModelData.DailyAttdTimeVacationDto(self.timeAbbyakLeave(),self.timeOff(),self.excessPaidHoliday(),self.specialHoliday2() ,2,self.childNursingLeave(),self.nursingCareLeave()));
            
                 let listTimeVacationAndType = [];
                 //atwork1
                 let dataTimeZoneAtwork1 = [];
                 dataTimeZoneAtwork1.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.atWork1Range().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.atWork1Range().endTime)
                 });

                 listTimeVacationAndType.push(new shareModelData.TimeVacationAndType(0, new shareModelData.TimeVacationDto(dataTimeZoneAtwork1, dataUsageTime)));
                 //offwork1
                 let dataTimeZoneOffWork1 = [];
                 dataTimeZoneOffWork1.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.offWork1Range().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.offWork1Range().endTime)
                 });

                 listTimeVacationAndType.push(new shareModelData.TimeVacationAndType(1, new shareModelData.TimeVacationDto(dataTimeZoneOffWork1, dataUsageTime)));
                 //atwork2
                 let dataTimeZoneAtwork2 = [];
                 dataTimeZoneAtwork2.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.atWork2Range().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.atWork2Range().endTime)
                 });

                 listTimeVacationAndType.push(new shareModelData.TimeVacationAndType(2, new shareModelData.TimeVacationDto(dataTimeZoneAtwork2, dataUsageTime)));

                 //offwork2
                 let dataTimeZoneOffWork2 = [];
                 dataTimeZoneOffWork2.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.offWork2Range().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.offWork2Range().endTime)
                 });

                 listTimeVacationAndType.push(new shareModelData.TimeVacationAndType(3, new shareModelData.TimeVacationDto(dataTimeZoneOffWork2, dataUsageTime)));

                 //privateTimeRange
                 let dataTimeZonePrivateTime = [];
                 dataTimeZonePrivateTime.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.privateTimeRange1().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.privateTimeRange1().endTime)
                 });
                 dataTimeZonePrivateTime.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.privateTimeRange2().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.privateTimeRange2().endTime)
                 });

                 listTimeVacationAndType.push(new shareModelData.TimeVacationAndType(4, new shareModelData.TimeVacationDto(dataTimeZonePrivateTime, dataUsageTime)));
                 //privateTimeRange
                 let dataTimeZoneUnionTime = [];
                 dataTimeZoneUnionTime.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.unionTimeRange1().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.unionTimeRange1().endTime)
                 });
                 dataTimeZoneUnionTime.push({
                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.unionTimeRange2().startTime),
                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, self.unionTimeRange2().endTime)
                 });

                 listTimeVacationAndType.push(new shareModelData.TimeVacationAndType(5, new shareModelData.TimeVacationDto(dataTimeZoneUnionTime, dataUsageTime)));

            
//             for (let i = 0; i < 6; i++) {
//                 let dataTimeZoneDto = [];
//                 dataTimeZoneDto.push({
//                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, i+100),
//                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, i+200)
//                 });
//                 dataTimeZoneDto.push({
//                     startTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY,i+201),
//                     endTime: new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY, i+300)
//                 });
//                 
//                 let dataUsageTime: Array<shareModelData.DailyAttdTimeVacationDto> = [];
//                 dataUsageTime.push( new shareModelData.DailyAttdTimeVacationDto(i + 30,i + 30,i + 30,i + 30 ,1,i + 30,i + 30));
//                 dataUsageTime.push( new shareModelData.DailyAttdTimeVacationDto(i + 45,i + 45,i + 45,i + 45 ,2,i + 45,i + 45));
//
//                 let dataTimeVacation: shareModelData.TimeVacationDto = {
//                     timeZone: dataTimeZoneDto, 
//                     usageTime : dataUsageTime
//
//                 }
//                 let timeVacationAndType: shareModelData.TimeVacationAndType = {
//                     typeVacation: i,
//                     timeVacation : dataTimeVacation
//                 }
//                 listTimeVacationAndType.push(timeVacationAndType);
//             }
            
            
            let employeeWorkInfoDto: shareModelData.IEmployeeWorkInfoDto = {
                isCheering: shareModelData.SupportAtr.NOT_CHEERING,
                isConfirmed: 0,
                bounceAtr: self.bounceAtr() == true?1:0, //ok
                directAtr: self.directAtr() == true?1:0, //ok
                isNeedWorkSchedule: 1,
                employeeId: self.employeeList()[0].id,//ok
                date: moment(self.date()).format("YYYY/MM/DD"), //ok
                supportTimeZone: supportTimeZone,
                wkpNameSupport: "wkpNameSupport",
                listTimeVacationAndType: listTimeVacationAndType,
                shiftCode: "shiftCode",
                shiftName: "shiftName",
                shortTime: listShortTime//ok
            }
            
            let breakTimeSheets = [];
            for (let i = 0; i < self.dataSourceTime().length; i++) {
                let breakTimeZoneDto = new shareModelData.BreakTimeZoneDto(self.dataSourceTime()[i].range1().breakFrameNo,self.dataSourceTime()[i].range1().startTime,self.dataSourceTime()[i].range1().endTime,0);
                breakTimeSheets.push(breakTimeZoneDto);
            }
            let breakTimeOfDailyAttdDto : shareModelData.BreakTimeOfDailyAttdDto = {
                breakType : 1,
                breakTimeSheets :  breakTimeSheets //ok
            }
            
            let listBreakTimeZoneDto = [breakTimeOfDailyAttdDto];
            
            let employeeWorkScheduleDto: shareModelData.IEmployeeWorkScheduleDto = {
                startTime1: self.timeRange1Value().startTime,//ok
                startTime1Status: shareModelData.EditStateSetting.HAND_CORRECTION_MYSELF,
                endTime1: self.timeRange1Value().endTime,//ok
                endTime1Status: shareModelData.EditStateSetting.HAND_CORRECTION_OTHER,
                startTime2: self.timeRange2Value().startTime,//ok
                startTime2Status: shareModelData.EditStateSetting.REFLECT_APPLICATION,
                endTime2: self.timeRange2Value().endTime,//ok
                endTime2Status: shareModelData.EditStateSetting.IMPRINT,
                listBreakTimeZoneDto: listBreakTimeZoneDto,//ok
                workTypeCode:self.workType(), //ok
                breakTimeStatus: shareModelData.EditStateSetting.HAND_CORRECTION_MYSELF,
                workTypeStatus: shareModelData.EditStateSetting.HAND_CORRECTION_MYSELF,
                workTimeCode: self.workTime(), //ok
                workTimeStatus: shareModelData.EditStateSetting.HAND_CORRECTION_MYSELF
            }
            
            let startTimeRange1 :shareModelData.TimeZoneDto ={
                startTime : new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY,1),
                endTime : new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY,201)
            }
            let startTimeRange2 :shareModelData.TimeZoneDto ={
                startTime : new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY,2),
                endTime : new shareModelData.TimeOfDayDto(shareModelData.DayDivision.THIS_DAY,202)
            }
            let changeableWorkTime1 = new shareModelData.ChangeableWorkTime(1,startTimeRange1);
            let changeableWorkTime2 = new shareModelData.ChangeableWorkTime(2,startTimeRange2);
            let overtimeHours = [changeableWorkTime1,changeableWorkTime2];
            
            
            let fixedWorkInforDto = shareModelData.FixedWorkInforDto = {
                workTimeName: self.workTimeName(),
                coreStartTime: 30,
                coreEndTime: 40,
                overtimeHours : overtimeHours,
                startTimeRange1: startTimeRange1,
                etartTimeRange1: startTimeRange1,
                workTypeName: self.workTypeName(),
                startTimeRange2: startTimeRange2,
                etartTimeRange2: startTimeRange2,
                fixBreakTime : self.fixBreakTime()?1:0,//ok
                workType :shareModelData.WorkTimeForm.FIXED
            }
            let employeeInformation : shareModelData.EmployeeInformation = {
                employeeCode : self.employeeList()[0].code,//ok
                employeeName : self.employeeList()[0].name,//ok
                employeeWorkInfoDto : employeeWorkInfoDto,
                employeeWorkScheduleDto : employeeWorkScheduleDto,
                fixedWorkInforDto :fixedWorkInforDto
            };
            
            let listScheCorrection = [];
            if(self.isFixed()){
                listScheCorrection.push(shareModelData.WorkTimeForm.FIXED);
            }
            if(self.isFlex()){
                listScheCorrection.push(shareModelData.WorkTimeForm.FLEX);
            }
            if(self.isFlow()){
                listScheCorrection.push(shareModelData.WorkTimeForm.FLOW);
            }
            if(self.isTimeDifference()){
                listScheCorrection.push(shareModelData.WorkTimeForm.TIMEDIFFERENCE);
            }
            let paramKsu003 : shareModelData.ParamKsu003 ={
                employeeInfo :employeeInformation,
                targetInfor : self.targetInfor()?1:0,//ok
                canModified : self.canModified()?shareModelData.CanModified.FIX:shareModelData.CanModified.REFERENCE,//ok
                scheCorrection : listScheCorrection //ok
            };
            
            
            setShared('kdl045Data', paramKsu003);
            nts.uk.ui.windows.sub.modal("/view/kdl/045/a/index.xhtml", { dialogClass: "no-close" })
                .onClosed(() => {
                    nts.uk.ui.block.clear();
                });
        }
    }
    
    export interface GroupOption {
            /** Common properties */
            showEmployeeSelection?: boolean; // 検索タイプ
            systemType: number; // システム区分
            showQuickSearchTab?: boolean; // クイック検索
            showAdvancedSearchTab?: boolean; // 詳細検索
            showBaseDate?: boolean; // 基準日利用
            showClosure?: boolean; // 就業締め日利用
            showAllClosure?: boolean; // 全締め表示
            showPeriod?: boolean; // 対象期間利用
            periodFormatYM?: boolean; // 対象期間精度
            isInDialog?: boolean;
        
            /** Required parameter */
            baseDate?: string; // 基準日
            periodStartDate?: string; // 対象期間開始日
            periodEndDate?: string; // 対象期間終了日
            inService: boolean; // 在職区分
            leaveOfAbsence: boolean; // 休職区分
            closed: boolean; // 休業区分
            retirement: boolean; // 退職区分
        
            /** Quick search tab options */
            showAllReferableEmployee?: boolean; // 参照可能な社員すべて
            showOnlyMe?: boolean; // 自分だけ
            showSameWorkplace?: boolean; // 同じ職場の社員
            showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員
        
            /** Advanced search properties */
            showEmployment?: boolean; // 雇用条件
            showWorkplace?: boolean; // 職場条件
            showClassification?: boolean; // 分類条件
            showJobTitle?: boolean; // 職位条件
            showWorktype?: boolean; // 勤種条件
            isMutipleCheck?: boolean; // 選択モード
            isTab2Lazy?: boolean;
        
            /** Data returned */
            returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
        }

         export interface UnitModel {
             code: string;
             name?: string;
             affiliationName?: string;
             id?: string;
             isAlreadySetting?: boolean;
         }
         export interface Ccg001ReturnedData {
             baseDate: string; // 基準日
             closureId?: number; // 締めID
             periodStart: string; // 対象期間（開始)
             periodEnd: string; // 対象期間（終了）
             listEmployee: Array<EmployeeSearchDto>; // 検索結果
         }
        export interface EmployeeSearchDto {
             employeeId: string;
             employeeCode: string;
             employeeName: string;
             workplaceId: string;
             workplaceName: string;
         }
         export class ListType {
             static EMPLOYMENT = 1;
             static Classification = 2;
             static JOB_TITLE = 3;
             static EMPLOYEE = 4;
         }

         export class SelectType {
             static SELECT_BY_SELECTED_CODE = 1;
             static SELECT_ALL = 2;
             static SELECT_FIRST_ITEM = 3;
             static NO_SELECT = 4;
         }
        export class ItemModel {
            code: string;
            name: string;
        
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

}

