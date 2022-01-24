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
// 乖離時間
@Getter
@AllArgsConstructor
public class DivergenceTimeRoot extends AggregateRoot {

	/** The divergence time no. */
	// 乖離時間NO
	private int divergenceTimeNo;

	/** The c id. */
	// 会社ID
	private String companyId;

	/** The Use classification. */
	// 使用区分
	private DivergenceTimeUseSet divTimeUseSet;

	/** The divergence time name. */
	// 乖離時間名称
	private DivergenceTimeName divTimeName;

	/** The divergence type. */
	// 乖離の種類
	private DivergenceType divType;

	/** The divergence time error cancel method. */
	// 乖離時間のエラーの解除方法
	private DivergenceTimeErrorCancelMethod errorCancelMedthod;

	/** The target item list. */
	// 対象項目一覧
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
	 * 勤怠項目IDに紐づく項目(値)を合計
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
	 * 乖離時間を計算する 
	 * @param forCalcDivergenceDto 日別勤怠コンバータ
	 * @param divergenceTimeList 乖離時間設定List
	 * @param calcAtrOfDaily 日別勤怠の計算区分
	 * @param breakTimeSheets 固定休憩時間の時間帯設定
	 * @param calcResultOotsuka 日別勤怠の総労働時間
	 * @param workTimeSetting 就業時間帯の設定
	 * @param workType 勤務種類
	 * @return 乖離時間List
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
		
		// 日別勤怠から乖離理由を取得する
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
		
		// 自動計算設定で使用しないであれば空を戻す
		if(!calcAtrOfDaily.getDivergenceTime().getDivergenceTime().isUse()) return divergenceTime;
		
		val divergenceTimeInIntegrationOfDaily = new DivergenceTimeOfDaily(divergenceTime);
		val returnList = new ArrayList<DivergenceTime>(); 
		
		// 乖離時間設定Listを取得
		for(DivergenceTimeRoot divergenceTimeClass : divergenceTimeList) {
			if(divergenceTimeClass.getDivTimeUseSet().isUse()) {
				divergenceTimeInIntegrationOfDaily.getDivergenceTime().stream()
										.filter(tc -> tc.getDivTimeId() == divergenceTimeClass.getDivergenceTimeNo())
										.findFirst().ifPresent(tdi -> {
											int totalTime = 0;
											boolean isCustom = false;
											// 大塚モードの確認
											if (AppContexts.optionLicense().customize().ootsuka()){
												if (1 < tdi.getDivTimeId() && tdi.getDivTimeId() <= 7){
												}
												else{
													isCustom = true;
												}
											}
											if(!isCustom) {
												// 指定した勤怠項目IDに対応する項目を取得　～　合計
												totalTime = divergenceTimeClass.totalDivergenceTimeWithAttendanceItemId(forCalcDivergenceDto);
											}
											else {
												// 休憩乖離時間を計算する（大塚モード）
												totalTime = calcDivergenceNo8910(tdi,integrationOfDailyInDto,breakTimeSheets,calcResultOotsuka,workTimeSetting,workType);
											}
											// 乖離時間を格納
											returnList.add(new DivergenceTime(
													new AttendanceTimeOfExistMinus(totalTime), 
													tdi.getDivTimeId(), 
											 		tdi.getDivReason(), 
											 		tdi.getDivResonCode()));
										});
			}
		}
		// 乖離時間を返す
		return (returnList.size()>0)?returnList:divergenceTimeInIntegrationOfDaily.getDivergenceTime();
	}

	/**
	 * 大塚カスタマイズ　乖離No 8、9、１０のみ別のチェックをさせる
	 * @param integrationOfDailyInDto 日別実績(WORK)実績計算済み 
	 * @param tdi  
	 * @param calcResultOotsuka 
	 * @param workTimeSetting 
	 * @param workType 
	 * @param breakList 就業時間帯側の休憩リスト
	 * @param breakOfDaily 
	 */
	public static int calcDivergenceNo8910(DivergenceTime tdi, IntegrationOfDaily integrationOfDailyInDto,Optional<TimezoneOfFixedRestTimeSet> masterBreakList, 
										   TotalWorkingTime calcResultOotsuka, Optional<WorkTimeSetting> workTimeSetting, Optional<WorkType> workType) {
		//実績がそもそも存在しない(不正)の場合
		if(!integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().isPresent()
		 ||!masterBreakList.isPresent()) 
			return 0;
		val breakOfDaily = calcResultOotsuka.getBreakTimeOfDaily();
		//就業時間帯側から実績の休憩時間帯への変換
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
		//固定
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
//					//出退勤取得
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
	 * 乖離No８に対する処理
	 * @param workTimeSetting 
	 * @param workType 
	 * @param calcResultOotsuka 
	 */
	public static int processNumberEight(IntegrationOfDaily integrationOfDailyInDto,List<BreakTimeSheet> breakList,BreakTimeOfDaily breakOfDaily, WorkTimeSetting workTimeSetting,
										Optional<WorkType> workType, TotalWorkingTime calcResultOotsuka) {
		Optional<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 1).findFirst();
		int breakTime = breakTimeSheet.isPresent() ? breakTimeSheet.get().getEndTime().valueAsMinutes() - breakTimeSheet.get().getStartTime().valueAsMinutes() : 0 ;
		if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isRegular() && workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().isFixedWork()) {
			
			//休出
			if(workType.get().getDailyWork().isHolidayWork()) {
				if(calcResultOotsuka.getActualTime().lessThanOrEqualTo(60*8)) {
					val divergenceTime = calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes() - breakTime;
					return divergenceTime > 0 ? divergenceTime : 0 ;
				}				
			}
			//
			else {
				//休憩枠No1取得
				if(!breakTimeSheet.isPresent()) return 0;
				//出退勤取得
				TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
				if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
					val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
					if(attendanceTimeByWorkNo.isPresent()) {
						attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
					}
				}
				//出退勤が休憩No1を含んでいるか
				if(attendanceLeave.contains(new TimeSpanForCalc(breakTimeSheet.get().getStartTime(),breakTimeSheet.get().getEndTime()))) {
					val calcValue = breakOfDaily.getToRecordTotalTime().getWithinStatutoryTotalTime().getCalcTime().minusMinutes(new TimeSpanForCalc(breakTimeSheet.get().getStartTime(),breakTimeSheet.get().getEndTime()).lengthAsMinutes());
					return calcValue.greaterThan(0)?calcValue.valueAsMinutes():0;
				}
				//含んでいない
				else {
					//乖離時間0
					return 0;
				}
			}
		}
		else if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {

			if(calcResultOotsuka.getActualTime().lessThanOrEqualTo(8*60)) {
				AttendanceTime calcDivTime = new AttendanceTime(0);
				//休出
				if(workType.get().getDailyWork().isHolidayWork()) {
					calcDivTime = breakOfDaily.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().minusMinutes(breakTime);
				}
				//休出以外
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
		//固定
		if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isRegular() && workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().isFixedWork()) {
			//休出
			if(workType.get().getDailyWork().isHolidayWork()) {
				//実働時間 > 8:00 && 残業合計(振替残業含む)>0
				if(calcResultOotsuka.getActualTime().greaterThan(480)) {
					//休憩枠No2取得
					List<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 1 || tc.getBreakFrameNo().v() == 2).collect(Collectors.toList());
					int allBreakTime = breakTimeSheet.stream().collect(Collectors.summingInt(tc -> tc.getEndTime().valueAsMinutes() - tc.getStartTime().valueAsMinutes()));
					val divergenceTime = allBreakTime - calcResultOotsuka.getBreakTimeOfDaily().getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes();
					return divergenceTime > 0 ? divergenceTime : 0;
				}
			}
			else {
				//休憩枠No2取得
				Optional<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 2).findFirst();
				if(!breakTimeSheet.isPresent()) return 0;
				//出退勤取得
				//TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
				if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
					val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
					if(attendanceTimeByWorkNo.isPresent()) {
					//	attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
					}
				}
				//実働時間 > 8:00 && 残業合計(振替残業含む)>0
				if(calcResultOotsuka.getActualTime().greaterThan(480)) {
					//出退勤が休憩No2を含んでいるか
					val calcValue = new AttendanceTime(breakTimeSheet.get().getEndTime().valueAsMinutes() - breakTimeSheet.get().getStartTime().valueAsMinutes())
												.minusMinutes(breakOfDaily.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes());
					return calcValue.greaterThan(0)?calcValue.valueAsMinutes():0;
				}
				//含んでいない
				else {
				//乖離時間0
				return 0;
				}				
			}
		}
		//フレ
		else if(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {
			//実働時間
			AttendanceTime actualTime = calcResultOotsuka.getActualTime();
			//フレックス時間
//			AttendanceTimeOfExistMinus flexTime = calcResultOotsuka.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getCalcTime();
			if(actualTime.greaterThan(8*60)) {
				//休出
				if(workType.get().getDailyWork().isHolidayWork()) {
					List<BreakTimeSheet> oneOrTwoBreakTimeSheet = breakList.stream().filter(bt -> bt.getBreakFrameNo().v().equals(1) || bt.getBreakFrameNo().v().equals(2)).collect(Collectors.toList()); 
					int allBreakTime = oneOrTwoBreakTimeSheet.stream().collect(Collectors.summingInt(tc -> tc.getEndTime().valueAsMinutes() - tc.getStartTime().valueAsMinutes()));
					AttendanceTime calcDivTime = new AttendanceTime(allBreakTime).minusMinutes(breakOfDaily.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes()); 
					return calcDivTime.greaterThan(0) ? calcDivTime.valueAsMinutes() : 0 ;									
				}
				//それ以外
				else {
					List<BreakTimeSheet> oneOrTwoBreakTimeSheet = breakList.stream().filter(bt -> bt.getBreakFrameNo().v().equals(1) || bt.getBreakFrameNo().v().equals(2)).collect(Collectors.toList());
					//出退勤取得
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
		//乖離No10は休出では求めない
		if(workType.isPresent() && workType.get().getDailyWork().isHolidayWork()) return 0;
		//休憩枠No2取得
		Optional<BreakTimeSheet> breakTimeSheet = breakList.stream().filter(tc -> tc.getBreakFrameNo().v() == 2).findFirst();
		if(!breakTimeSheet.isPresent()) return 0;
		//出退勤取得
		TimeSpanForCalc attendanceLeave = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0));
		if(integrationOfDailyInDto.getAttendanceLeave().isPresent()) {
			val attendanceTimeByWorkNo = integrationOfDailyInDto.getAttendanceLeave().get().getAttendanceLeavingWork(1);
			if(attendanceTimeByWorkNo.isPresent()) {
				attendanceLeave = attendanceTimeByWorkNo.get().getTimespan();
			}
		}
		//休憩終了 <= 退勤
		if(attendanceLeave.getEnd().greaterThan(breakTimeSheet.get().getEndTime())) {
			Optional<BreakTimeSheet> equalTimeSheet = integrationOfDailyInDto.getBreakTime().getBreakTimeSheets().stream()
						  .filter(ts -> ts.getStartTime() != null && ts.getEndTime() != null)
						  .filter(tc -> new TimeSpanForCalc(tc.getStartTime(),tc.getEndTime())
								  			.contains(new TimeSpanForCalc(breakTimeSheet.get().getStartTime(),
				  															breakTimeSheet.get().getEndTime())))
						  .findFirst();
			if(equalTimeSheet.isPresent()) {
				//取得した休憩と予定休憩の時間帯が同じ
				return 0;
			}
			//該当した物が一つもない
			else {
				val difference = breakTimeSheet.get().getEndTime().valueAsMinutes() - breakTimeSheet.get().getStartTime().valueAsMinutes();
				return difference>0?difference:0;
			}
		}
		//含んでいない
		else {
			//乖離時間0
			return 0;
		}
	}
	
	/**
	 * 	[1] 乖離時間に対応する日次の勤怠項目を取得する
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
	 * [2] 乖離時間に対応する月次の勤怠項目を取得する
	 * 
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdByNo() {
		return Arrays.asList(388 + this.divergenceTimeNo);
	}
	
	/**
	 * 	[3] 利用できない日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDailyAttendanceIdNotAvailable() {
		if(this.divTimeUseSet == DivergenceTimeUseSet.NOT_USE) {
			return this.getDaiLyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}
	
	/**
	 *  [4] 利用できない月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.divTimeUseSet == DivergenceTimeUseSet.NOT_USE) {
			return this.getMonthlyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}
	
	
}
