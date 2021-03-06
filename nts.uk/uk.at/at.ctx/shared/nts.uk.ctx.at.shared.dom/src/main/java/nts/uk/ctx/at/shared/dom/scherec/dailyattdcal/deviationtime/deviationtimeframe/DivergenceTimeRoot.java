package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceReasonContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DivergenceTime.
 */
// δΉι’ζι
@Getter
@AllArgsConstructor
public class DivergenceTimeRoot extends AggregateRoot {

	/** The divergence time no. */
	// δΉι’ζιNO
	private int divergenceTimeNo;

	/** The c id. */
	// δΌη€ΎID
	private String companyId;

	/** The Use classification. */
	// δ½Ώη¨εΊε
	private DivergenceTimeUseSet divTimeUseSet;

	/** The divergence time name. */
	// δΉι’ζιεη§°
	private DivergenceTimeName divTimeName;

	/** The divergence type. */
	// δΉι’γ?η¨?ι‘
	private DivergenceType divType;

	/** The divergence time error cancel method. */
	// δΉι’ζιγ?γ¨γ©γΌγ?θ§£ι€ζΉζ³
	private DivergenceTimeErrorCancelMethod errorCancelMedthod;

	/** The target item list. */
	// ε―Ύθ±‘ι η?δΈθ¦§
	private List<Integer> targetItems;

	/**
	 * Instantiates a new divergence time.
	 *
	 * @param memento
	 *            the memento
	 */
	public DivergenceTimeRoot(DivergenceTimeGetMemento memento) {
		this.divergenceTimeNo = memento.getDivergenceTimeNo();
		this.companyId = AppContexts.user().companyId();
		this.divTimeUseSet = memento.getDivTimeUseSet();
		this.divTimeName = memento.getDivTimeName();
		this.divType = memento.getDivType();
		this.errorCancelMedthod = memento.getErrorCancelMedthod();
		this.targetItems = memento.getTargetItems();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DivergenceTimeSetMemento memento) {

		memento.setDivergenceTimeNo(this.divergenceTimeNo);
		memento.setCompanyId(AppContexts.user().companyId());
		memento.setDivTimeName(this.divTimeName);
		memento.setDivTimeUseSet(this.divTimeUseSet);
		memento.setDivType(this.divType);
		memento.setErrorCancelMedthod(this.errorCancelMedthod);
		memento.setTarsetItems(this.targetItems);

	}

	/**
	 * Checks if is divergence time use.
	 *
	 * @return true, if is divergence time use
	 */
	public boolean isDivergenceTimeUse() {
		return this.divTimeUseSet == DivergenceTimeUseSet.USE; 
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + divergenceTimeNo;
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DivergenceTimeRoot other = (DivergenceTimeRoot) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (divergenceTimeNo != other.divergenceTimeNo)
			return false;
		return true;
	}

	/**
	 * ε€ζ ι η?IDγ«η΄γ₯γι η?(ε€)γεθ¨
	 * @param idConverter
	 * @return
	 */
	public int totalDivergenceTimeWithAttendanceItemId(DailyRecordToAttendanceItemConverter idConverter) {
		if(this.targetItems == null) {
			return 0;
		}
		val getValueList = idConverter.convert(this.targetItems.stream().map(c -> c.intValue()).collect(Collectors.toList()));
				
		return  getValueList.stream()
									 .filter(tc ->  tc !=null
									  			 && tc.getValue() != null
									  			 && tc.getValueType().isInteger())
									 .map(tc -> Integer.valueOf(tc.getValue()))
									 .collect(Collectors.summingInt(tc -> tc));
									 
		
	}

	/**
	 * δΉι’ζιγθ¨η?γγ 
	 * @param forCalcDivergenceDto ζ₯ε₯ε€ζ γ³γ³γγΌγΏ
	 * @param divergenceTimeList δΉι’ζιθ¨­ε?List
	 * @param calcAtrOfDaily ζ₯ε₯ε€ζ γ?θ¨η?εΊε
	 * @param breakTimeSheets εΊε?δΌζ©ζιγ?ζιεΈ―θ¨­ε?
	 * @param calcResultOotsuka ζ₯ε₯ε€ζ γ?η·ε΄εζι
	 * @param workTimeSetting ε°±ζ₯­ζιεΈ―γ?θ¨­ε?
	 * @param workType ε€εη¨?ι‘
	 * @return δΉι’ζιList
	 */
	public static List<DivergenceTime> calcDivergenceTime(
			DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
			List<DivergenceTimeRoot> divergenceTimeList,
			CalAttrOfDailyAttd calcAtrOfDaily,
			Optional<TimezoneOfFixedRestTimeSet> breakTimeSheets,
			TotalWorkingTime calcResultOotsuka,
			Optional<WorkTimeSetting> workTimeSetting,
			Optional<WorkType> workType) {
		
		val integrationOfDailyInDto = forCalcDivergenceDto.toDomain();
		if(integrationOfDailyInDto == null
			|| integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance() == null
			|| !integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().isPresent()
			|| integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() == null)
			return Collections.emptyList();
		
		List<DivergenceTime> divergenceTime = new ArrayList<>();
		
		// ζ₯ε₯ε€ζ γγδΉι’ηη±γεεΎγγ
		DivergenceTimeOfDaily div_time = integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime();
		for(int i=0 ; i<10 ;i++) {
			String reasonContent = null;
			String reasonCode = null;
			
			int div_index = i+1;
			if(div_time != null) {
				List<DivergenceTime> obj
					= div_time.getDivergenceTime().stream().filter(c->c.getDivTimeId()==div_index).collect(Collectors.toList());
				
				if(!obj.isEmpty()) {
					if(obj.get(0).getDivReason().isPresent()) reasonContent = obj.get(0).getDivReason().get().v();
					if(obj.get(0).getDivResonCode().isPresent()) reasonCode = obj.get(0).getDivResonCode().get().v();
				}
					
			}
			
			DivergenceTime obj = new DivergenceTime(
					new AttendanceTimeOfExistMinus(0),
					div_index,
					reasonContent == null ? null : new DivergenceReasonContent(reasonContent),
					reasonCode == null ? null : new DiverdenceReasonCode(reasonCode));
			
			divergenceTime.add(obj);
		}
		
		// θͺεθ¨η?θ¨­ε?γ§δ½Ώη¨γγͺγγ§γγγ°η©Ίγζ»γ
		if(!calcAtrOfDaily.getDivergenceTime().getDivergenceTime().isUse()) return divergenceTime;
		
		val divergenceTimeInIntegrationOfDaily = new DivergenceTimeOfDaily(divergenceTime);
		val returnList = new ArrayList<DivergenceTime>(); 
		
		// δΉι’ζιθ¨­ε?ListγεεΎ
		for(DivergenceTimeRoot divergenceTimeClass : divergenceTimeList) {
			if(divergenceTimeClass.getDivTimeUseSet().isUse()) {
				divergenceTimeInIntegrationOfDaily.getDivergenceTime().stream()
										.filter(tc -> tc.getDivTimeId() == divergenceTimeClass.getDivergenceTimeNo())
										.findFirst().ifPresent(tdi -> {
											int totalTime = 0;
											boolean isCustom = false;
											// ε€§ε‘γ’γΌγγ?η’Ίθͺ
											if (AppContexts.optionLicense().customize().ootsuka()){
												if (1 < tdi.getDivTimeId() && tdi.getDivTimeId() <= 7){
												}
												else{
													isCustom = true;
												}
											}
											if(!isCustom) {
												// ζε?γγε€ζ ι η?IDγ«ε―ΎεΏγγι η?γεεΎγο½γεθ¨
												totalTime = divergenceTimeClass.totalDivergenceTimeWithAttendanceItemId(forCalcDivergenceDto);
											}
											else {
												// δΌζ©δΉι’ζιγθ¨η?γγοΌε€§ε‘γ’γΌγοΌ
												totalTime = calcDivergenceNo8910(tdi,integrationOfDailyInDto,breakTimeSheets,calcResultOotsuka,workTimeSetting,workType);
											}
											// δΉι’ζιγζ Όη΄
											returnList.add(new DivergenceTime(
													new AttendanceTimeOfExistMinus(totalTime), 
													tdi.getDivTimeId(), 
											 		tdi.getDivReason(), 
											 		tdi.getDivResonCode()));
										});
			}
		}
		// δΉι’ζιγθΏγ
		return (returnList.size()>0)?returnList:divergenceTimeInIntegrationOfDaily.getDivergenceTime();
	}

	/**
	 * ε€§ε‘γ«γΉγΏγγ€γΊγδΉι’No 8γ9γοΌοΌγ?γΏε₯γ?γγ§γγ―γγγγ
	 * @param integrationOfDailyInDto ζ₯ε₯ε?ηΈΎ(WORK)ε?ηΈΎθ¨η?ζΈγΏ 
	 * @param tdi  
	 * @param calcResultOotsuka 
	 * @param workTimeSetting 
	 * @param workType 
	 * @param breakList ε°±ζ₯­ζιεΈ―ε΄γ?δΌζ©γͺγΉγ
	 * @param breakOfDaily 
	 */
	public static int calcDivergenceNo8910(DivergenceTime tdi, IntegrationOfDaily integrationOfDailyInDto,Optional<TimezoneOfFixedRestTimeSet> masterBreakList, 
										   TotalWorkingTime calcResultOotsuka, Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType) {
		//ε?ηΈΎγγγγγε­ε¨γγͺγ(δΈζ­£)γ?ε ΄ε
		if(!integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().isPresent()
		 ||!masterBreakList.isPresent()) 
			return 0;
		val breakOfDaily = calcResultOotsuka.getBreakTimeOfDaily();
		//ε°±ζ₯­ζιεΈ―ε΄γγε?ηΈΎγ?δΌζ©ζιεΈ―γΈγ?ε€ζ
		val breakList = BreakTimeSheet.covertFromFixRestTimezoneSet(masterBreakList.get().getTimezones());
		
		switch(tdi.getDivTimeId()) {
		case 1:
			if(!workTimeSetting.isPresent()) return 0;
		    return processNumberOne(integrationOfDailyInDto,workTimeSetting.get(), workType,calcResultOotsuka, breakList);
		case 8:
			if(!workTimeSetting.isPresent()) return 0;
			return processNumberEight(integrationOfDailyInDto, breakList, breakOfDaily,workTimeSetting.get(),workType,calcResultOotsuka);
		case 9:
			if(!workTimeSetting.isPresent()) return 0;
			return processNumberNight(integrationOfDailyInDto, breakList, breakOfDaily,calcResultOotsuka,workTimeSetting.get(),workType);
		case 10:
			return processNumberTen(integrationOfDailyInDto, breakList, breakOfDaily,workType);
		default:
			throw new RuntimeException("exception divergence No:"+tdi.getDivTimeId());
		}
	}
	private static int processNumberOne(IntegrationOfDaily integrationOfDailyInDto, WorkTimeSetting workTimeSetting,Optional<WorkType> workType,TotalWorkingTime calcResultOotsuka,List<BreakTimeSheet> breakList) {
		Optional<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 1).findFirst();
		int breakTime = breakTimeSheet.isPresent() ? breakTimeSheet.get().getEndTime().valueAsMinutes() - breakTimeSheet.get().getStartTime().valueAsMinutes() : 0 ;
		//εΊε?
		if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isRegular() && workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().isFixedWork()) {
			if(workType.isPresent()) {
				if(workType.get().getDailyWork().isHolidayWork()) {
					if(calcResultOotsuka.getActualTime().greaterThan(60*8)) {
						val divergenceTime = calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes()
												- breakTime;
						
						return divergenceTime > 0 ? divergenceTime : 0 ;
					}
				}
				else {
					return calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes();
				}
					
			}
		}
		else if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {
			if((calcResultOotsuka.getActualTime().greaterThan(60*8))) {
				if(workType.isPresent()) {
					int divergenceTime = 0;
//					//εΊιε€εεΎ
//					TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
//					if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
//						val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
//						if(attendanceTimeByWorkNo.isPresent()) {
//							attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
//						}
//					}
//					if(breakTimeSheet.isPresent()) {
//						List<BreakTimeSheet> bt = containsBreakTime(attendanceLeave, Arrays.asList(breakTimeSheet.get()));
//						breakTime = bt.stream().collect(Collectors.summingInt(tc -> tc.getEndTime().valueAsMinutes() - tc.getStartTime().valueAsMinutes()));
//					}
					if(workType.get().getDailyWork().isHolidayWork()) {
						divergenceTime = calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes()
													- breakTime;
					}
					else {
						divergenceTime = calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getWithinStatutoryTotalTime().getCalcTime().valueAsMinutes()
												- breakTime;
					}
					return divergenceTime > 0 ? divergenceTime : 0 ;
				}				
			}

		}
		return 0;
	}

	/**
	 * δΉι’NoοΌγ«ε―Ύγγε¦η
	 * @param workTimeSetting 
	 * @param workType 
	 * @param calcResultOotsuka 
	 */
	public static int processNumberEight(IntegrationOfDaily integrationOfDailyInDto,List<BreakTimeSheet> breakList,BreakTimeOfDaily breakOfDaily, WorkTimeSetting workTimeSetting,
										Optional<WorkType> workType, TotalWorkingTime calcResultOotsuka) {
		Optional<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 1).findFirst();
		int breakTime = breakTimeSheet.isPresent() ? breakTimeSheet.get().getEndTime().valueAsMinutes() - breakTimeSheet.get().getStartTime().valueAsMinutes() : 0 ;
		if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isRegular() && workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().isFixedWork()) {
			
			//δΌεΊ
			if(workType.get().getDailyWork().isHolidayWork()) {
				if(calcResultOotsuka.getActualTime().lessThanOrEqualTo(60*8)) {
					val divergenceTime = calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes() - breakTime;
					return divergenceTime > 0 ? divergenceTime : 0 ;
				}				
			}
			//
			else {
				//δΌζ©ζ No1εεΎ
				if(!breakTimeSheet.isPresent()) return 0;
				//εΊιε€εεΎ
				TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
				if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
					val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
					if(attendanceTimeByWorkNo.isPresent()) {
						attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
					}
				}
				//εΊιε€γδΌζ©No1γε«γγ§γγγ
				if(attendanceLeave.contains(new TimeSpanForCalc(breakTimeSheet.get().getStartTime(),breakTimeSheet.get().getEndTime()))) {
					val calcValue = breakOfDaily.getToRecordTotalTime().getWithinStatutoryTotalTime().getCalcTime().minusMinutes(new TimeSpanForCalc(breakTimeSheet.get().getStartTime(),breakTimeSheet.get().getEndTime()).lengthAsMinutes());
					return calcValue.greaterThan(0)?calcValue.valueAsMinutes():0;
				}
				//ε«γγ§γγͺγ
				else {
					//δΉι’ζι0
					return 0;
				}
			}
		}
		else if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {

			if(calcResultOotsuka.getActualTime().lessThanOrEqualTo(8*60)) {
				AttendanceTime calcDivTime = new AttendanceTime(0);
				//δΌεΊ
				if(workType.get().getDailyWork().isHolidayWork()) {
					calcDivTime = breakOfDaily.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().minusMinutes(breakTime);
				}
				//δΌεΊδ»₯ε€
				else {
					calcDivTime = breakOfDaily.getToRecordTotalTime().getWithinStatutoryTotalTime().getCalcTime().minusMinutes(breakTime);
				}
				return calcDivTime.greaterThan(0) ? calcDivTime.valueAsMinutes() : 0 ;	
			}
		}
		return 0;

	}
	public static int processNumberNight(IntegrationOfDaily integrationOfDailyInDto,List<BreakTimeSheet> breakList,BreakTimeOfDaily breakOfDaily, TotalWorkingTime calcResultOotsuka, WorkTimeSetting workTimeSetting,Optional<WorkType> workType) {
		if(!workType.isPresent()) return 0;
		//εΊε?
		if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isRegular() && workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().isFixedWork()) {
			//δΌεΊ
			if(workType.get().getDailyWork().isHolidayWork()) {
				//ε?εζι > 8:00 && ζ?ζ₯­εθ¨(ζ―ζΏζ?ζ₯­ε«γ)>0
				if(calcResultOotsuka.getActualTime().greaterThan(480)) {
					//δΌζ©ζ No2εεΎ
					List<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 1 || tc.getBreakFrameNo().v() == 2).collect(Collectors.toList());
					int allBreakTime = breakTimeSheet.stream().collect(Collectors.summingInt(tc -> tc.getEndTime().valueAsMinutes() - tc.getStartTime().valueAsMinutes()));
					val divergenceTime = allBreakTime - calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes();
					return divergenceTime > 0 ? divergenceTime : 0;
				}
			}
			else {
				//δΌζ©ζ No2εεΎ
				Optional<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 2).findFirst();
				if(!breakTimeSheet.isPresent()) return 0;
				//εΊιε€εεΎ
				//TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
				if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
					val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
					if(attendanceTimeByWorkNo.isPresent()) {
					//	attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
					}
				}
				//ε?εζι > 8:00 && ζ?ζ₯­εθ¨(ζ―ζΏζ?ζ₯­ε«γ)>0
				if(calcResultOotsuka.getActualTime().greaterThan(480)) {
					//εΊιε€γδΌζ©No2γε«γγ§γγγ
					val calcValue = new AttendanceTime(breakTimeSheet.get().getEndTime().valueAsMinutes() - breakTimeSheet.get().getStartTime().valueAsMinutes())
												.minusMinutes(breakOfDaily.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes());
					return calcValue.greaterThan(0)?calcValue.valueAsMinutes():0;
				}
				//ε«γγ§γγͺγ
				else {
				//δΉι’ζι0
				return 0;
				}				
			}
		}
		//γγ¬
		else if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {
			//ε?εζι
			AttendanceTime actualTime = calcResultOotsuka.getActualTime();
			//γγ¬γγ―γΉζι
//			AttendanceTimeOfExistMinus flexTime = calcResultOotsuka.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getCalcTime();
			if(actualTime.greaterThan(8*60)) {
				//δΌεΊ
				if(workType.get().getDailyWork().isHolidayWork()) {
					List<BreakTimeSheet> oneOrTwoBreakTimeSheet = breakList.stream().filter(bt -> bt.getBreakFrameNo().v().equals(1) || bt.getBreakFrameNo().v().equals(2)).collect(Collectors.toList()); 
					int allBreakTime = oneOrTwoBreakTimeSheet.stream().collect(Collectors.summingInt(tc -> tc.getEndTime().valueAsMinutes() - tc.getStartTime().valueAsMinutes()));
					AttendanceTime calcDivTime = new AttendanceTime(allBreakTime).minusMinutes(breakOfDaily.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes()); 
					return calcDivTime.greaterThan(0) ? calcDivTime.valueAsMinutes() : 0 ;									
				}
				//γγδ»₯ε€
				else {
					List<BreakTimeSheet> oneOrTwoBreakTimeSheet = breakList.stream().filter(bt -> bt.getBreakFrameNo().v().equals(1) || bt.getBreakFrameNo().v().equals(2)).collect(Collectors.toList());
					//εΊιε€εεΎ
					TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
					if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
//						val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
//						if(attendanceTimeByWorkNo.isPresent()) {
//							attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
//						}
						val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getTimeLeavingWorks();
						if(!attendanceTimeByWorkNo.isEmpty()) {
							attendanceLeave = attendanceTimeByWorkNo.get(0).getTimespan();
						}
					}
					
					oneOrTwoBreakTimeSheet = containsBreakTime(attendanceLeave,oneOrTwoBreakTimeSheet);
					
					int allBreakTime = oneOrTwoBreakTimeSheet.stream().collect(Collectors.summingInt(tc -> tc.getEndTime().valueAsMinutes() - tc.getStartTime().valueAsMinutes()));
					AttendanceTime calcDivTime = new AttendanceTime(allBreakTime).minusMinutes(breakOfDaily.getToRecordTotalTime().getWithinStatutoryTotalTime().getCalcTime().valueAsMinutes()); 
					return calcDivTime.greaterThan(0) ? calcDivTime.valueAsMinutes() : 0 ;				
				}
			}
		}
		return 0;
	}
	
	private static List<BreakTimeSheet> containsBreakTime(TimeSpanForCalc attendanceLeave ,List<BreakTimeSheet> breakTimeSheet){
		return breakTimeSheet.stream().filter(bt -> attendanceLeave.contains(new TimeSpanForCalc(bt.getStartTime(), bt.getEndTime()))).collect(Collectors.toList());
	}
	
	public static int processNumberTen(IntegrationOfDaily integrationOfDailyInDto,List<BreakTimeSheet> breakList,BreakTimeOfDaily breakOfDaily,
									   Optional<WorkType> workType) {
		//δΉι’No10γ―δΌεΊγ§γ―ζ±γγͺγ
		if(workType.isPresent() && workType.get().getDailyWork().isHolidayWork()) return 0;
		//δΌζ©ζ No2εεΎ
		Optional<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 2).findFirst();
		if(!breakTimeSheet.isPresent()) return 0;
		//εΊιε€εεΎ
		TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
		if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
			val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
			if(attendanceTimeByWorkNo.isPresent()) {
				attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
			}
		}
		//δΌζ©η΅δΊ <= ιε€
		if(attendanceLeave.getEnd().greaterThan(breakTimeSheet.get().getEndTime())) {
			Optional<BreakTimeSheet> equalTimeSheet = integrationOfDailyInDto.getBreakTime().getBreakTimeSheets().stream()
						  .filter(ts -> ts.getStartTime() != null && ts.getEndTime() != null)
						  .filter(tc -> new TimeSpanForCalc(tc.getStartTime(),tc.getEndTime())
								  			.contains(new TimeSpanForCalc(breakTimeSheet.get().getStartTime(),
				  															breakTimeSheet.get().getEndTime())))
						  .findFirst();
			if(equalTimeSheet.isPresent()) {
				//εεΎγγδΌζ©γ¨δΊε?δΌζ©γ?ζιεΈ―γεγ
				return 0;
			}
			//θ©²ε½γγη©γδΈγ€γγͺγ
			else {
				val difference = breakTimeSheet.get().getEndTime().valueAsMinutes() - breakTimeSheet.get().getStartTime().valueAsMinutes();
				return difference>0?difference:0;
			}
		}
		//ε«γγ§γγͺγ
		else {
			//δΉι’ζι0
			return 0;
		}
	}
	
	/**
	 * 	[1] δΉι’ζιγ«ε―ΎεΏγγζ₯ζ¬‘γ?ε€ζ ι η?γεεΎγγ
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdByNo() {
		switch(this.divergenceTimeNo) {
		case 1:
			return Arrays.asList(436,437,440);
		case 2: 
			return Arrays.asList(441,442,445);
		case 3: 
			return Arrays.asList(446,447,450);
		case 4: 
			return Arrays.asList(451,452,455);
		case 5: 
			return Arrays.asList(456,457,460);
		case 6: 
			return Arrays.asList(799,800,803);
		case 7: 
			return Arrays.asList(804,805,808);
		case 8: 
			return Arrays.asList(809,810,813);
		case 9: 
			return Arrays.asList(814,815,818);
		default : //10
			return Arrays.asList(819,820,823);
		}
	}
	
	/**
	 * [2] δΉι’ζιγ«ε―ΎεΏγγζζ¬‘γ?ε€ζ ι η?γεεΎγγ
	 * 
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdByNo() {
		return Arrays.asList(388 + this.divergenceTimeNo);
	}
	
	/**
	 * 	[3] ε©η¨γ§γγͺγζ₯ζ¬‘γ?ε€ζ ι η?γεεΎγγ
	 * @return
	 */
	public List<Integer> getDailyAttendanceIdNotAvailable() {
		if(this.divTimeUseSet == DivergenceTimeUseSet.NOT_USE) {
			return this.getDaiLyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}
	
	/**
	 *  [4] ε©η¨γ§γγͺγζζ¬‘γ?ε€ζ ι η?γεεΎγγ
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.divTimeUseSet == DivergenceTimeUseSet.NOT_USE) {
			return this.getMonthlyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}
	
	
}
