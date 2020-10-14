module test.viewmodel {

    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import sharemodelData = nts.uk.at.view.kdl045.share.model; 

    export class ScreenModel {



        shiftItems: KnockoutObservableArray<ShiftMaster>;
        selectedShiftMaster: KnockoutObservableArray<any>;
        options: Option;
        constructor() {
            let self = this;


            //			self.shiftItems = ko.observableArray([]);
            //			self.selectedShiftMaster = ko.observableArray([]);
            //			self.options = {
            //				// neu muon lay code ra tu trong list thi bind gia tri nay vao
            //				currentCodes: self.currentCodes,
            //				currentNames: self.currentNames,
            //				// tuong tu voi id
            //				currentIds: self.currentIds,
            //				//
            //				multiple: false,
            //				tabindex: 2,
            //				isAlreadySetting: true,
            //				alreadySettingList: self.alreadySettingList,
            //				// show o tim kiem
            //				showPanel: true,
            //				// show empty item
            //		howEmptyItem: false,
            //				// trigger reload lai data cua component
            //				reloadData: ko.observable(''),
            //				height: 400,
            //				// NONE = 0, FIRST = 1, ALL = 2
            //				selectedMode: 1
            //			};

			
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let param = { targetUnit: null, workplaceIds: null, workplaceGroupID: null };


            dfd.resolve();

            return dfd.promise();
        }
        openDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            
            let supportTimeZone :sharemodelData.TimeZoneDto ={
                startTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,101),
                endTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,301)
            }
            
             let shortTime :sharemodelData.TimeZoneDto ={
                startTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,102),
                endTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,302)
            }
            let listTimeVacationAndType = [];
             for (let i = 0; i < 6; i++) {

                 let dataTimeZoneDto: sharemodelData.TimeZoneDto = {
                     startTime: new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY, i+100),
                     endTime: new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY, i+200)
                 }
                 
                 let dataUsageTime : sharemodelData.DailyAttdTimeVacationDto = {
                     timeAbbyakLeave : i+500,
                     timeOff : i+500,
                     excessPaidHoliday : i+500,
                     specialHoliday:   i+500,
                     frameNO : i,
                     childNursingLeave : i+500,
                     nursingCareLeave : i+500,
                 }

                 let dataTimeVacation: sharemodelData.TimeVacationDto = {
                     timeZone: dataTimeZoneDto,
                     usageTime : dataUsageTime

                 }
                 let timeVacationAndType: sharemodelData.TimeVacationAndType = {
                     typeVacation: i,
                     timeVacation : dataTimeVacation
                 }
                 listTimeVacationAndType.push(timeVacationAndType);
             }
            
            
            let employeeWorkInfoDto: sharemodelData.IEmployeeWorkInfoDto = {
                isCheering: sharemodelData.SupportAtr.NOT_CHEERING,
                isConfirmed: 0,
                bounceAtr: 0,
                directAtr: 1,
                isNeedWorkSchedule: 1,
                employeeId: "SID0001",
                date: "2020/01/01",
                supportTimeZone: supportTimeZone,
                wkpNameSupport: "wkpNameSupport",
                listTimeVacationAndType: listTimeVacationAndType,
                shiftCode: "shiftCode",
                shiftName: "shiftName",
                shortTime: shortTime
            }
            
            let breakTimeSheets = [];
            for (let i = 0; i < 10; i++) {
                let breakTimeZoneDto = new sharemodelData.BreakTimeZoneDto(i,500+i,550+i,600+i);
                breakTimeSheets.push(breakTimeZoneDto);
            }
            let breakTimeOfDailyAttdDto1 : sharemodelData.BreakTimeOfDailyAttdDto = {
                breakType : 1,
                breakTimeSheets :  breakTimeSheets
            }
            let breakTimeOfDailyAttdDto2 : sharemodelData.BreakTimeOfDailyAttdDto = {
                breakType : 2,
                breakTimeSheets :  breakTimeSheets
            }
            let listBreakTimeZoneDto = [breakTimeOfDailyAttdDto1,breakTimeOfDailyAttdDto2];
            
            let employeeWorkScheduleDto: sharemodelData.IEmployeeWorkScheduleDto = {
                startTime1: 600,
                startTime1Status: sharemodelData.EditStateSetting.HAND_CORRECTION_MYSELF,
                endTime1: 650,
                endTime1Status: sharemodelData.EditStateSetting.HAND_CORRECTION_OTHER,
                startTime2: 651,
                startTime2Status: sharemodelData.EditStateSetting.REFLECT_APPLICATION,
                endTime2: 700,
                endTime2Status: sharemodelData.EditStateSetting.IMPRINT,
                listBreakTimeZoneDto: listBreakTimeZoneDto,
                workTypeCode: "WorkType",
                breakTimeStatus: sharemodelData.EditStateSetting.HAND_CORRECTION_MYSELF,
                workTypeStatus: sharemodelData.EditStateSetting.HAND_CORRECTION_MYSELF,
                workTimeCode: "workTime",
                workTimeStatus: sharemodelData.EditStateSetting.HAND_CORRECTION_MYSELF
            }
            
            let startTimeRange1 :sharemodelData.TimeZoneDto ={
                startTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,1),
                endTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,201)
            }
            let startTimeRange2 :sharemodelData.TimeZoneDto ={
                startTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,2),
                endTime : new sharemodelData.TimeOfDayDto(sharemodelData.DayDivision.THIS_DAY,202)
            }
            let changeableWorkTime1 = new sharemodelData.ChangeableWorkTime(1,startTimeRange1);
            let changeableWorkTime2 = new sharemodelData.ChangeableWorkTime(2,startTimeRange2);
            let overtimeHours = [changeableWorkTime1,changeableWorkTime2];
            
            
            let fixedWorkInforDto = sharemodelData.FixedWorkInforDto = {
                workTimeName: "workTimeName",
                coreStartTime: 30,
                coreEndTime: 40,
                overtimeHours : overtimeHours,
                startTimeRange1: startTimeRange1,
                etartTimeRange1: startTimeRange1,
                workTypeName: "workTypeName",
                startTimeRange2: startTimeRange2,
                etartTimeRange2: startTimeRange2,
                fixBreakTime : 1,
                workType :sharemodelData.WorkTimeForm.FIXED
            }
            let employeeInformation : sharemodelData.EmployeeInformation = {
                employeeCode : "0000001",
                employeeName : "Triệu Khắc Tú",
                employeeWorkInfoDto : employeeWorkInfoDto,
                employeeWorkScheduleDto : employeeWorkScheduleDto,
                fixedWorkInforDto :fixedWorkInforDto
            };
            
            let paramKsu003 : sharemodelData.ParamKsu003 ={
                employeeInfo :employeeWorkInfoDto,
                targetInfor : 1,
                canModified : sharemodelData.CanModified.REFERENCE,
                scheCorrection : [sharemodelData.WorkTimeForm.FIXED,sharemodelData.WorkTimeForm.FLEX,sharemodelData.WorkTimeForm.FLOW,sharemodelData.WorkTimeForm.TIMEDIFFERENCE]
            };
            
            
            setShared('kdl045Data', paramKsu003);
            nts.uk.ui.windows.sub.modal("/view/kdl/045/a/index.xhtml", { dialogClass: "no-close" })
                .onClosed(() => {
                    nts.uk.ui.block.clear();
                });
        }
    }



}

