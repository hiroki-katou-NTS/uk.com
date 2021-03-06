package nts.uk.ctx.at.record.pubimp.dailyprocess.scheduletime;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ProvisionalCalculationService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist.PrevisionalForImp;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubImport;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.CommonCompanySettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScheduleTimePubImpl implements ScheduleTimePub{

	@Inject
	private ProvisionalCalculationService provisionalCalculationService;
	
	@Inject
	private CommonCompanySettingForCalc commonCompanySetting;

	@Override
	public Object getCompanySettingForCalclationScheduleTimeForMultiPeople() {
		ManagePerCompanySet companySet = this.commonCompanySetting.getCompanySetting(new CalculateOption(true, true));
		MasterShareContainer<String> shareContainer = MasterShareBus.open();
		companySet.setShareContainer(shareContainer);
		return companySet;
	}

	@Override
	public void clearCompanySettingShareConainter(Object companySetObj) {
		ManagePerCompanySet companySet = (ManagePerCompanySet)companySetObj;
		companySet.getShareContainer().clearAll();
		companySet.setShareContainer(null);
	}

	
	@Override
	/**
	 * RequestList(1???????????????)
	 */
	public ScheduleTimePubExport calculationScheduleTime(Object companySetting, ScheduleTimePubImport impTime) {
		if(impTime != null) {
			val calcValue = calclationScheduleTimeForMultiPeople(companySetting, Arrays.asList(impTime));
			if(!calcValue.isEmpty()) {
				return calcValue.stream().findFirst().get();
			}
		}
		return ScheduleTimePubExport.empty();
	}
	
	/**
	 * RequestList No 91(??????????????????)
	 */
	@Override
	public List<ScheduleTimePubExport> calclationScheduleTimeForMultiPeople(Object companySetting, List<ScheduleTimePubImport> impList) {
		return calclationScheduleTimePassCompanyCommonSetting(impList, Optional.ofNullable((ManagePerCompanySet)companySetting));
	}
	
	@Override
	public List<ScheduleTimePubExport> calclationScheduleTimePassCompanyCommonSetting(List<ScheduleTimePubImport> impList,Optional<ManagePerCompanySet> companySetting){
		List<PrevisionalForImp> paramList = new ArrayList<>();
		for(ScheduleTimePubImport imp : impList){
			//??????????????????
			Map<Integer, TimeZone> timeSheets = getTimeZone(imp.getStartClock(),imp.getEndClock());
			List<BreakTimeSheet> breakTimeSheets = getBreakTimeSheets(imp.getBreakStartTime(),imp.getBreakEndTime());
			List<OutingTimeSheet> outingTimeSheets = new ArrayList<>();
			List<ShortWorkingTimeSheet> shortWorkingTimeSheets = getShortTimeSheets(imp.getChildCareStartTime(),imp.getChildCareEndTime());
			
			paramList.add(new PrevisionalForImp(imp.getEmployeeId(), imp.getTargetDate(), timeSheets, imp.getWorkTypeCode(), imp.getWorkTimeCode(), breakTimeSheets, outingTimeSheets, shortWorkingTimeSheets));
		}
		//????????????
		List<IntegrationOfDaily> integrationOfDaily = provisionalCalculationService.calculationPassCompanyCommonSetting(paramList,companySetting);	
		
		if(!integrationOfDaily.isEmpty()) {
			return getreturnValye(integrationOfDaily);
		}
		else {
			return new ArrayList<>();				
		}
	}

	
	/**
	 * ????????????Output??????????????????????????????
	 * @param integrationOfDaily ??????
	 * @return Output?????????
	 */
	private List<ScheduleTimePubExport> getreturnValye(List<IntegrationOfDaily> integrationOfDailyList) {
		
		List<ScheduleTimePubExport> returnList = new ArrayList<>();
		
		for(IntegrationOfDaily integrationOfDaily:integrationOfDailyList) {
		
			String empId = integrationOfDaily.getEmployeeId();
			GeneralDate ymd = integrationOfDaily.getYmd();
			
			//???????????????
			AttendanceTime totalWorkTime = new AttendanceTime(0);
			//??????????????????
			AttendanceTime preTime = new AttendanceTime(0);
			//????????????
			AttendanceTime actualWorkTime = new AttendanceTime(0);
			//????????????
			AttendanceTime weekDayTime = new AttendanceTime(0);
			//????????????
			AttendanceTime breakTime = new AttendanceTime(0);
			//????????????
			AttendanceTime childTime = new AttendanceTime(0);
			//????????????
			AttendanceTime careTime = new AttendanceTime(0);
			//?????????????????????
			AttendanceTimeOfExistMinus flexTime = new AttendanceTimeOfExistMinus(0);
			
			//???????????????
			List<AttendanceTime> personalExpenceTime = new ArrayList<>();
		
			if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()
					&&integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null) {
				//??????
				personalExpenceTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()
													.getPremiumTimes().stream().map(tc -> tc.getPremitumTime()).collect(Collectors.toList());
				//????????????
				preTime = integrationOfDaily.getSnapshot().map(c -> c.getPredetermineTime()).orElseGet(() -> new AttendanceTime(0));//integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getWorkScheduleTimeOfDaily().getSchedulePrescribedLaborTime();
			
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
					//???????????????
					totalWorkTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime();
					//????????????
					actualWorkTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getActualTime();
				
					//????????????
					if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily() != null) {
						breakTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily()
											   .getToRecordTotalTime().getTotalTime().getTime();
					}
					//??????????????????
					if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily() != null) {
						if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily().getChildCareAttribute().isCare()) {
							childTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily().getTotalTime().getTotalTime().getTime();
						}
						else {
							careTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily().getTotalTime().getTotalTime().getTime();
						}
					}
					if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() != null
					&& integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
						flexTime = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getCalcTime();
					}
				
					//????????????
					weekDayTime =  new AttendanceTime(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getWorkTime().valueAsMinutes()
						       					  + integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().calcTotalHolTime().valueAsMinutes()
						       					  - integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getVacationAddTime().valueAsMinutes());
				}
			}
			returnList.add(ScheduleTimePubExport.of(empId, ymd, totalWorkTime, preTime, actualWorkTime, weekDayTime, breakTime, childTime, careTime, flexTime, personalExpenceTime));
		}
		return returnList;
	}

	/**
	 * ???????????????????????????????????????
	 * @param childCareStartTime ???????????????????????????
	 * @param childCareEndTime??????????????????????????????
	 * @return???????????????????????????
	 */
	private List<ShortWorkingTimeSheet> getShortTimeSheets(List<Integer> childCareStartTime,List<Integer> childCareEndTime) {
		List<ShortWorkingTimeSheet> returnList = new ArrayList<>();
		for(int shortTimeStamp = 1 ; shortTimeStamp< childCareStartTime.size();shortTimeStamp++) {
			if(shortTimeStamp <= childCareStartTime.size()
			 &&shortTimeStamp <= childCareEndTime.size()) {
				returnList.add(new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(shortTimeStamp), 
													 ChildCareAtr.CHILD_CARE,
													 new TimeWithDayAttr(childCareStartTime.get(shortTimeStamp - 1).intValue()),
													 new TimeWithDayAttr(childCareEndTime.get(shortTimeStamp - 1).intValue())));
			}
		}
		return returnList;
	}

	/**
	 * ?????????????????????
	 * @param breakStartTime ??????????????????
	 * @param breakEndTime?????????????????????
	 * @return??????????????????
	 */
	private List<BreakTimeSheet> getBreakTimeSheets(List<Integer> breakStartTime, List<Integer> breakEndTime) {
		List<BreakTimeSheet> returnList = new ArrayList<>();
		for(int breatStampNo = 1;breatStampNo <= breakStartTime.size() ; breatStampNo++) {
			if( breatStampNo <= breakStartTime.size() 
			  &&breatStampNo <= breakEndTime.size()) {
				returnList.add(new BreakTimeSheet(new BreakFrameNo(breatStampNo),
											  new TimeWithDayAttr(breakStartTime.get(breatStampNo - 1).intValue()),
											  new TimeWithDayAttr(breakEndTime.get(breatStampNo - 1).intValue()),
											  new AttendanceTime(0)));
			}
		}
		return returnList;
	}

	/**
	 * ?????????????????????
	 * @param startClock ????????????
	 * @param endClock ????????????
	 * @return??????????????????
	 */
	private Map<Integer, TimeZone> getTimeZone(List<Integer> startClock, List<Integer> endClock) {
		Map<Integer, TimeZone> timeList = new HashMap<>();
		
		for(int workNo = 1 ; workNo <= startClock.size() ; workNo++) {
			if(startClock.size() >= workNo
			&& endClock.size() >= workNo) {
				timeList.put(workNo,new TimeZone(new TimeWithDayAttr(startClock.get(workNo - 1).intValue()),
							   		new TimeWithDayAttr(endClock.get(workNo - 1).intValue())));
			}
		}
		return timeList;
	}


}