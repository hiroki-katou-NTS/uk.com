/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;

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
	private CompanyId companyId;

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
	
	/**
	 * Instantiates a new overtime setting.
	 *
	 * @param memento the memento
	 */
	public OutsideOTSetting(OutsideOTSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.note = memento.getNote();
		this.breakdownItems = memento.getBreakdownItems();
		this.calculationMethod = memento.getCalculationMethod();
		this.overtimes = memento.getOvertimes();
		
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
	

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutsideOTSettingSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setNote(this.note);
		memento.setBreakdownItems(this.breakdownItems);
		memento.setCalculationMethod(this.calculationMethod);
		memento.setOvertimes(this.overtimes);
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
		
		/** ○法定内休出の勤怠項目IDを全て取得 */
		breakdownItems.addAll(getLegalHolidayWorkItems(require, cid));
		
		/** 取得した件数分ループ */
		val converter = require.createMonthlyConverter();
		val attendanceTime = new AttendanceTimeOfMonthly(monthlyCalculation.getEmployeeId(), 
														monthlyCalculation.getYearMonth(), 
														monthlyCalculation.getClosureId(),
														monthlyCalculation.getClosureDate(), 
														monthlyCalculation.getProcPeriod());
		attendanceTime.setMonthlyCalculation(monthlyCalculation);
		converter.withAttendanceTime(attendanceTime);
		val attendanceItemValues = converter.convert(breakdownItems);
		
		/** ○丸め処理 */
		attendanceItemValues.stream().forEach(v -> {
			val value = new AttendanceTimeMonth(v.value());
			val rounded = roundSet.map(r -> r.itemRound(v.getItemId(), value)).orElse(value);
			breakdown.addTimeByAttendanceItemId(v.getItemId(), rounded);
		});
		
		return breakdown;
	}

	/** 内訳項目に設定されている勤怠項目IDを全て取得 */
	public List<Integer> getBreakDownItemIds() {
		return this.breakdownItems.stream().map(b -> b.getAttendanceItemIds())
											.flatMap(List::stream)
											.collect(Collectors.toList());
	}
	
	/** 法定内休出の勤怠項目IDを全て取得 */
	public List<Integer> getLegalHolidayWorkItems(RequireM2 require, String cid) {
		
		/** ドメインモデル「休出枠の役割」を取得 */
		val legalHolWork = require.roleOfOpenPeriod(cid).stream()
				.filter(r -> r.getRoleOfOpenPeriodEnum() == RoleOfOpenPeriodEnum.STATUTORY_HOLIDAYS)
				.collect(Collectors.toList());
		
		/** 取得したNOに該当する勤怠項目IDを取得 */
		val legalHolWorkItemIds = legalHolWork.stream()
				.map(hw -> mapLegalHolidayWorkItemNoToId(hw.getBreakoutFrNo().v()))
				.flatMap(List::stream).collect(Collectors.toList());
		
		/**内訳項目一覧に設定されていない法定内休出の勤怠項目IDを判断 */
		legalHolWorkItemIds.removeAll(getBreakDownItemIds());
		
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
	
	public static interface RequireM2 {
		
		List<RoleOfOpenPeriod> roleOfOpenPeriod(String cid);
	}
	
	public static interface RequireM1 extends RequireM2 {
		
		Optional<RoundingSetOfMonthly> monthRoundingSet(String cid);
		
		MonthlyRecordToAttendanceItemConverter createMonthlyConverter();
	}
}
