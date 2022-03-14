/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * OT = Overtime
 * The Class OutsideOTSetting.
 */
//時間外超過設定
@Getter
public class OutsideOTSetting extends AggregateRoot implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The company id. */
	// 会社ID
	private String companyId;

	/** The note. */
	// 備考
	private OvertimeNote note;
	
	/** The breakdown items. */
	// 内訳項目一覧
	private List<OutsideOTBRDItem> breakdownItems;
	
	/** The calculation method. */
	// 計算方法
	private OutsideOTCalMed calculationMethod;
	
	/** The over times. */
	// 超過時間一覧
	private List<Overtime> overtimes;

	//
	// TODO QA 39234
	/** 丸め */
	private Optional<TimeRoundingOfExcessOutsideTime> timeRoundingOfExcessOutsideTime;
	
	/**
	 * Instantiates a new overtime setting.
	 */
	public OutsideOTSetting(String companyId, OvertimeNote note, List<OutsideOTBRDItem> breakdownItems, 
			OutsideOTCalMed calculationMethod, List<Overtime> overtimes) {
		
		this.companyId = companyId;
		this.note = note;
		this.breakdownItems = breakdownItems;
		this.calculationMethod = calculationMethod;
		this.overtimes = overtimes;
		this.timeRoundingOfExcessOutsideTime = Optional.empty();
		
		// validate domain
		if(CollectionUtil.isEmpty(this.breakdownItems)){
			throw new BusinessException("Msg_485");
		}
		if (!checkUseBreakdownItem()) {
			throw new BusinessException("Msg_485");
		}
		if (this.checkOverlapOvertime()) {
			throw new BusinessException("Msg_486");
		}
		if(this.checkOverlapProductNumber()){
			throw new BusinessException("Msg_490");
		}
	}

	//TODO QA 39234
	public OutsideOTSetting(String companyId, OvertimeNote note, List<OutsideOTBRDItem> breakdownItems,
			OutsideOTCalMed calculationMethod, List<Overtime> overtimes,Optional<TimeRoundingOfExcessOutsideTime> timeRoundingOfExcessOutsideTime) {

		this.companyId = companyId;
		this.note = note;
		this.breakdownItems = breakdownItems;
		this.calculationMethod = calculationMethod;
		this.overtimes = overtimes;
		this.timeRoundingOfExcessOutsideTime = timeRoundingOfExcessOutsideTime;

		// validate domain
		if(CollectionUtil.isEmpty(this.breakdownItems)){
			throw new BusinessException("Msg_485");
		}
		if (!checkUseBreakdownItem()) {
			throw new BusinessException("Msg_485");
		}
		if (this.checkOverlapOvertime()) {
			throw new BusinessException("Msg_486");
		}
		if(this.checkOverlapProductNumber()){
			throw new BusinessException("Msg_490");
		}
	}
	
	/**
	 * Check use breakdown item.
	 *
	 * @return true, if successful
	 */
	private boolean checkUseBreakdownItem() {
		for (OutsideOTBRDItem breakdownItem : this.breakdownItems) {
			if (breakdownItem
					.getUseClassification().value == UseClassification.UseClass_Use.value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check use overtime.
	 *
	 * @return true, if successful
	 */
	private boolean checkOverlapOvertime() {
		for (Overtime overtime1 : this.overtimes) {
			for(Overtime overtime2: this.overtimes){
				if(overtime1.getUseClassification().value == UseClassification.UseClass_Use.value 
						&& overtime2.getUseClassification().value ==UseClassification.UseClass_Use.value 
						&& overtime1.getOvertimeNo().value != overtime2.getOvertimeNo().value
						&& overtime1.getOvertime().equals(overtime2.getOvertime())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check overlap product number.
	 *
	 * @return true, if successful
	 */
	private boolean checkOverlapProductNumber() {
		for (OutsideOTBRDItem breakdownItem1 : this.breakdownItems) {
			for (OutsideOTBRDItem breakdownItem2 : this.breakdownItems) {
				if (breakdownItem1.getBreakdownItemNo().value != breakdownItem2
						.getBreakdownItemNo().value
						&& breakdownItem1.getProductNumber().value == breakdownItem2
								.getProductNumber().value) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		OutsideOTSetting other = (OutsideOTSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	/**
	 * 内訳項目に設定されている勤怠項目IDをすべて取得
	 * @return 勤怠項目IDリスト
	 */
	// 2018.3.19 add shuichi_ishida
	public List<Integer> getAllAttendanceItemIds(){
		
		List<Integer> allIds = new ArrayList<>();
		for (val breakdownItem : this.breakdownItems) allIds.addAll(breakdownItem.getAttendanceItemIds());
		return allIds;
	}
	
	/** 36協定対象時間を取得 */
	public AgreementTimeBreakdown getTargetTime(RequireM1 require, String cid, 
			MonthlyCalculation monthlyCalculation) {
		
		val breakdown = new AgreementTimeBreakdown();
		/** 月別実績の丸め設定を取得 */
		val roundSet = require.monthRoundingSet(cid);
		
		/** 内訳項目に設定されている勤怠項目IDを全て取得 */
		val breakdownItems = getBreakDownItemIds();
		
		/** 休出枠一覧を取得する */
		val workDayOffFrames = getWorkDayOffFrame(monthlyCalculation);
		
		/** ○法定内休出の勤怠項目IDを全て取得 */
		val holiWorkItems = getLegalHolidayWorkItems(workDayOffFrames);
		
		if(breakdownItems.isEmpty() && holiWorkItems.isEmpty()) {
			return breakdown;
		}
		
		/** 対象項目の時間を求める */
		getBreakDownTimes(require, monthlyCalculation, breakdown, roundSet, breakdownItems);
		
		/** 法定内休出時間を取得する */
		getHolidayWorkTime(require, roundSet, getDailyRecords(monthlyCalculation), 
				breakdown, holiWorkItems, workDayOffFrames);
		
		/** ○36協定上限時間内訳を返す */
		return breakdown;
	}

	private List<IntegrationOfDaily> getDailyRecords(MonthlyCalculation monthlyCalculation) {
		return monthlyCalculation.getMonthlyCalculatingDailys().getDailyWorks(monthlyCalculation.getEmployeeId())
				.stream().filter(c -> monthlyCalculation.getProcPeriod().contains(c.getYmd()))
				.collect(Collectors.toList());
	}
	
	/** 休出枠一覧を取得する */
	private Map<Integer, WorkdayoffFrame> getWorkDayOffFrame(MonthlyCalculation monthlyCalculation) {
		
		switch (monthlyCalculation.getWorkingSystem()) {
		case FLEX_TIME_WORK:
			return monthlyCalculation.getSettingsByFlex().getRoleHolidayWorkFrameMap();
		case REGULAR_WORK:
			return monthlyCalculation.getSettingsByReg().getRoleHolidayWorkFrameMap();
		case VARIABLE_WORKING_TIME_WORK:
			return monthlyCalculation.getSettingsByDefo().getRoleHolidayWorkFrameMap();
		default:
			return new HashMap<>();
		}
	}
	
	/** 法定内休出時間を取得する */
	private void getHolidayWorkTime(Require require, Optional<RoundingSetOfMonthly> roundSet, 
			List<IntegrationOfDaily> dailyRecords,
			AgreementTimeBreakdown breakdown, List<Integer> holidayWorkItems,
			Map<Integer, WorkdayoffFrame> roleHolidayWorkFrameMap) {
		
		/** 休出対象項目一覧のIDから休出枠Noでグループする */
		val noGroup = mapHolidayWorkItemsByNo(holidayWorkItems);
		
		noGroup.entrySet().stream().forEach(no -> {
			/** 該当の休出枠を取得する */
			val workdayoffFrame = roleHolidayWorkFrameMap.get(no.getKey());
			val holWorkTime = new AtomicInteger(0);
			val holTransTime = new AtomicInteger(0);
			dailyRecords.stream().forEach(d -> {
				
				/** 対象日の実績から休出時間を求める区分を確認する */
				val holAtr = workdayoffFrame.getHolWorkAtrforDailyRecord(require, d.getWorkInformation().getRecordInfo());
				if (holAtr == HolidayAtr.STATUTORY_HOLIDAYS) {
					
					/** 該当の法内休出時間を取得する */
					getLegalHolTime(d.getAttendanceTimeOfDailyPerformance(), no.getValue(), no.getKey(), holWorkTime, holTransTime);
				}
			});
			
			/** 休出対象項目の丸め処理 */
			roundAndSetHolWorkItem(roundSet, breakdown, no.getValue(), holWorkTime, holTransTime);
		});
	}
	
	/** 休出対象項目の丸め処理 */
	private void roundAndSetHolWorkItem(Optional<RoundingSetOfMonthly> roundSet, AgreementTimeBreakdown breakdown, 
			List<Integer> items, AtomicInteger holWorkTime, AtomicInteger holTransTime) {
		
		/** 休出枠Noごとに休出時間と休出振替時間は同じ丸め設定を使うためここで一回だけ取得する */
		/** 該当の月別実績の項目丸め設定を取得する */
		val hwRoundSet = roundSet.flatMap(r -> {
			return items.stream().map(i -> r.getItemRoundingSet().get(i)).filter(i -> i != null).findFirst();
		}).map(r -> r.getRoundingSet());
		
		items.stream().forEach(i -> {
			
			val targetItemTime = i >= 175 ? holTransTime.get() : holWorkTime.get();
			
			/** 丸め処理 */
			val roundedTime = hwRoundSet.map(c -> c.round(targetItemTime)).orElse(targetItemTime);
			
			/** 該当の項目にセットする */
			breakdown.addTimeByAttendanceItemId(i, new AttendanceTimeMonth(roundedTime));
		});
	}
	
	/** 該当の法内休出時間を取得する */
	private void getLegalHolTime(Optional<AttendanceTimeOfDailyAttendance> attendanceTime, List<Integer> items,
			int workDayOffNo, AtomicInteger holWorkTime, AtomicInteger holTransTime) {
		
		items.stream().forEach(i -> {
			
			attendanceTime.flatMap(at -> at.getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime())
				.flatMap(hw -> hw.getHolidayWorkFrameTime().stream().filter(h -> h.getHolidayFrameNo().v() == workDayOffNo).findFirst())
				.ifPresent(hw -> {

					if (i >= 175) {
						
						/** 振替時間を取得する */
						/** 取得できた法定内休出時間を時間外に加算する */
						holTransTime.addAndGet(hw.getTransferTime().get().getTime().valueAsMinutes());
					} else {

						/** 休出時間を取得する */
						/** 取得できた法定内休出時間を時間外に加算する */
						holWorkTime.addAndGet(hw.getHolidayWorkTime().get().getTime().valueAsMinutes());
					}
				});
		});
	}

	/** 休出対象項目一覧のIDから休出枠Noでグループする */
	private Map<Integer, List<Integer>> mapHolidayWorkItemsByNo(List<Integer> holidayWorkItems) {
		Map<Integer, List<Integer>> noGroup = new HashMap<>();
		
		holidayWorkItems.stream().forEach(id -> {
			int no = 0;
			if (id >= 175 && id <= 184) { /** 175 就内振替休出時間1*/
				no = id - 175 + 1;
			} else if (id >= 165 && id <= 174) { /** 165 就内休出時間1*/
				no = id - 165 + 1;
			}
			
			if (no > 0) {
				noGroup.putIfAbsent(no, new ArrayList<>());
				noGroup.get(no).add(id);
			}
		});
		
		return noGroup;
	}

	/** 対象項目の時間を求める*/
	private void getBreakDownTimes(RequireM1 require, MonthlyCalculation monthlyCalculation,
			AgreementTimeBreakdown breakdown, Optional<RoundingSetOfMonthly> roundSet, List<Integer> breakdownItems) {
		
		/**　対象項目ID一覧　*/
		val targetItems = new ArrayList<>(breakdownItems);
		if (targetItems.contains(AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value)) {
			/**　フレックス法定外時間を含む場合、時間外のフレックス週平均超過時間も取得する　*/
			targetItems.add(AttendanceItemOfMonthly.CUR_MONTH_EXC_WA_TIME_OT.value);
		}
		
		/** 取得した件数分ループ */
		val converter = require.createMonthlyConverter();
		val attendanceTime = new AttendanceTimeOfMonthly(monthlyCalculation.getEmployeeId(), 
														monthlyCalculation.getYearMonth(), 
														monthlyCalculation.getClosureId(),
														monthlyCalculation.getClosureDate(), 
														monthlyCalculation.getProcPeriod());
		attendanceTime.setMonthlyCalculation(monthlyCalculation);
		converter.withAttendanceTime(attendanceTime);
		val attendanceItemValues = converter.convert(targetItems);
		
		/** ⁂複数月対応で、複数の場合（フレックスと変形）以下の項目を値を補正する */
		if (isMultiMonthMode(monthlyCalculation)) {
			correctItems(attendanceItemValues, converter);
		}
		
		/** ○丸め処理 */
		attendanceItemValues.stream().forEach(v -> {
			val value = new AttendanceTimeMonth(v.valueOrDefault());
			val roundItemId = getRoundItemId(v.getItemId());
			val rounded = roundSet.map(r -> r.itemRound(roundItemId, value)).orElse(value);
			breakdown.addTimeByAttendanceItemId(v.getItemId(), rounded);
		});
	}
	
	private int getRoundItemId(int itemId) {
		
		/** ⁂項目IDがフレックス法定内時間、フレックス法定外時間、時間外の週平均超過時間の場合、フレックスの丸め設定を見る */
		if (itemId == AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value
				|| itemId == AttendanceItemOfMonthly.FLEX_LEGAL_TIME.value
				|| itemId == AttendanceItemOfMonthly.CUR_MONTH_EXC_WA_TIME_OT.value) {
			
			return AttendanceItemOfMonthly.FLEX_TIME.value;
		}
		
		return itemId;
	}
	
	private void correctItems(List<ItemValue> attendanceItemValues, MonthlyRecordToAttendanceItemConverter converter) {
		
		val alterItemMap = attendanceItemValues.stream().collect(Collectors.toMap(c -> c.getItemId(), c -> {
			if (c.getItemId() == AttendanceItemOfMonthly.FLEX_TIME.value) 
				return AttendanceItemOfMonthly.CUR_MONTH_FLEX_TIME_OT.value;
			if (c.getItemId() == AttendanceItemOfMonthly.FLEX_LEGAL_TIME.value) 
				return AttendanceItemOfMonthly.CUR_MONTH_FLEX_LEGAL_TIME_OT.value;
			if (c.getItemId() == AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value) 
				return AttendanceItemOfMonthly.CUR_MONTH_FLEX_ILLEGAL_TIME_OT.value;
			if (c.getItemId() == AttendanceItemOfMonthly.MONTHLY_TOTAL_PREMIUM_TIME.value) 
				return AttendanceItemOfMonthly.DEFOR_PERIOD_CARRY_TIME.value;
			return 0;
		}));
		
		val alterItemIds = alterItemMap.values().stream().filter(c -> c > 0).collect(Collectors.toList());
		
		if (!alterItemIds.isEmpty()) {
			
			val alterItems = converter.convert(alterItemIds);
			
			alterItems.stream().forEach(c -> {
				attendanceItemValues.stream().filter(i -> alterItemMap.get(i.getItemId()) == c.getItemId())
									.findFirst().ifPresent(ai -> {
					ai.value(c.value());
				});
			});
		}
	}
	
	private boolean isMultiMonthMode(MonthlyCalculation monthlyCalculation) {
		
		if (monthlyCalculation.getWorkingSystem() == WorkingSystem.FLEX_TIME_WORK) {
			
			return monthlyCalculation.getSettingsByFlex().getFlexAggrSet().isMultiMonthSettlePeriod();
			
		} else if (monthlyCalculation.getWorkingSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
			
			return monthlyCalculation.getSettingsByDefo().getDeforAggrSet().isMultiMonthSettlePeriod(monthlyCalculation.getYearMonth());
		}
		
		return false;
	}
	
	/** clones from 36協定対象時間を取得 */
	public Map<String, AgreementTimeBreakdown> getTargetTimeClones(RequireM1 require, String cid, 
			List<MonthlyCalculation> monthlyCalculations) {
		
		val breakdownMap = new HashMap<String, AgreementTimeBreakdown>();
		/** 月別実績の丸め設定を取得 */
		val roundSet = require.monthRoundingSet(cid);
		
		/** 内訳項目に設定されている勤怠項目IDを全て取得 */
		val breakdownItems = getBreakDownItemIds();
		
		for (MonthlyCalculation monthlyCalculation : monthlyCalculations) {
			val breakdown = new AgreementTimeBreakdown();
			
			/** 休出枠一覧を取得する */
			val workDayOffFrames = getWorkDayOffFrame(monthlyCalculation);
			
			/** ○法定内休出の勤怠項目IDを全て取得 */
			val holiWorkItems = getLegalHolidayWorkItems(workDayOffFrames);
			
			if(breakdownItems.isEmpty() && holiWorkItems.isEmpty()) {
				breakdownMap.put(monthlyCalculation.getEmployeeId(), breakdown);
				continue;
			}
			
			/** 対象項目の時間を求める */
			getBreakDownTimes(require, monthlyCalculation, breakdown, roundSet, breakdownItems);
			
			/** 法定内休出時間を取得する */
			getHolidayWorkTime(require, roundSet, getDailyRecords(monthlyCalculation), 
					breakdown, holiWorkItems, workDayOffFrames);
			
			/** ○36協定上限時間内訳を返す */
			breakdownMap.put(monthlyCalculation.getEmployeeId(), breakdown);
		}
		
		return breakdownMap;
		
	}

	/** 内訳項目に設定されている勤怠項目IDを全て取得 */
	public List<Integer> getBreakDownItemIds() {
		return this.breakdownItems.stream().map(b -> b.getAttendanceItemIds())
											.flatMap(List::stream)
											.collect(Collectors.toList());
	}
	
	/** 法定内休出の勤怠項目IDを全て取得 */
	public List<Integer> getLegalHolidayWorkItems(Map<Integer, WorkdayoffFrame> workDayOffFrames) {
		
		/** ドメインモデル「休出枠の役割」を取得 */
		val legalHolWork = workDayOffFrames.entrySet().stream()
				.filter(r -> r.getValue().getRole() == WorkdayoffFrameRole.STATUTORY_HOLIDAYS)
				.map(c -> c.getValue())
				.collect(Collectors.toList());
		
		/** 取得したNOに該当する勤怠項目IDを取得 */
		val legalHolWorkItemIds = legalHolWork.stream()
				.map(hw -> mapLegalHolidayWorkItemNoToId(hw.getWorkdayoffFrNo().v().intValue()))
				.flatMap(List::stream).collect(Collectors.toList());
		
		/**内訳項目一覧に設定されていない法定内休出の勤怠項目IDを判断 */
		legalHolWorkItemIds.removeAll(getBreakDownItemIds());
		
		/** ドメインモデル「休出枠の役割」を取得 */
		val mixHolWork = workDayOffFrames.entrySet().stream()
				.filter(r -> r.getValue().getRole() == WorkdayoffFrameRole.MIX_WITHIN_OUTSIDE_STATUTORY)
				.map(c -> c.getValue())
				.collect(Collectors.toList());
		
		/** 法定内・外混在を取得したNOに該当する勤怠項目IDを取得 */
		legalHolWorkItemIds.addAll(mixHolWork.stream()
				.map(hw -> mapLegalHolidayWorkItemNoToId(hw.getWorkdayoffFrNo().v().intValue()))
				.flatMap(List::stream).collect(Collectors.toList()));
		
		/** 取得した勤怠項目ID（List）を返す */
		return legalHolWorkItemIds;
	}
	
	private List<Integer> mapLegalHolidayWorkItemNoToId(int no) {
		switch (no) {
		case 1:
			return Arrays.asList(165, 175);
		case 2:
			return Arrays.asList(166, 176);
		case 3:
			return Arrays.asList(167, 177);
		case 4:
			return Arrays.asList(168, 178);
		case 5:
			return Arrays.asList(169, 179);
		case 6:
			return Arrays.asList(170, 180);
		case 7:
			return Arrays.asList(171, 181);
		case 8:
			return Arrays.asList(172, 182);
		case 9:
			return Arrays.asList(173, 183);
		case 10:
			return Arrays.asList(174, 184);
		default:
			return new ArrayList<>();
		}
	}

	/**
	 * 時間外超過丸め
	 * @param attendanceItemId 勤怠項目ID
	 * @param attendanceTimeMonth 勤怠月間時間　（丸め前）
	 * @return 勤怠月間時間　（丸め後）
	 */
	public AttendanceTimeMonth excessOutsideRound(int attendanceItemId, AttendanceTimeMonth attendanceTimeMonth){

		int minutes = attendanceTimeMonth.v();

		val excessOutsideRoundSet = this.timeRoundingOfExcessOutsideTime.get();
		switch (excessOutsideRoundSet.getRoundingProcess()) {
			case ROUNDING_DOWN:
				minutes = excessOutsideRoundSet.getRoundingUnit().round(minutes, Unit.Direction.TO_BACK);
				break;
			case ROUNDING_UP:
				minutes = excessOutsideRoundSet.getRoundingUnit().round(minutes, Unit.Direction.TO_FORWARD);
				break;
			case FOLLOW_ELEMENTS:
				return new AttendanceTimeMonth(minutes);
		}
		return new AttendanceTimeMonth(minutes);
	}
	
	public static interface RequireM1 extends Require {
		
		Optional<RoundingSetOfMonthly> monthRoundingSet(String cid);
		
		MonthlyRecordToAttendanceItemConverter createMonthlyConverter();
	}
	
	public static interface Require extends WorkdayoffFrame.Require {}
}
