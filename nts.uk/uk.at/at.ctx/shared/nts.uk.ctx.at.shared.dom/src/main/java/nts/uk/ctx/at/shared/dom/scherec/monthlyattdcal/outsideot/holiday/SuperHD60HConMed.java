/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.SuperHD60HConTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SuperHD60HConMed.
 */
// 60H超休への換算方法
@Getter
@AllArgsConstructor
public class SuperHD60HConMed extends AggregateRoot{

	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The time rounding setting. */
	// 時間丸め設定
	private TimeRoundingSetting timeRoundingSetting;
	
	
	/** The super holiday occurrence unit. */
	// 超休発生単位
	private SuperHDOccUnit superHolidayOccurrenceUnit;
	
	/** 超有休の付与時間を計算 */
	public void calcTimeSuperHD(RequireM1 require, ExcessOutsideWorkOfMonthly excessOutsideWorkOfMonthly) {
		
		val outsideOTSetting = require.outsideOTSetting(AppContexts.user().companyId()).orElse(null);
		if (outsideOTSetting == null) {
			return;
		}
		
		/** 換算時間 */
		val transferTime = MutableValue.of(new BigDecimal(0));
		
		/** 60H超休が発生する超過時間NOでループ */
		outsideOTSetting.getOvertimes().stream().filter(ot -> ot.isSuperHoliday60HOccurs()).forEach(ot -> {
			
			/** 内訳項目NOでループ */
			outsideOTSetting.getBreakdownItems().stream()
					.filter(bd -> bd.getUseClassification() == UseClassification.UseClass_Use)
					.forEach(bd -> {
				
				/** 60Hを超過している分の時間を取得 */
				val monthlyExcess = getOverTime(excessOutsideWorkOfMonthly, ot, bd);
				
				val outOTRate = bd.getPremiumExtra60HRates().stream()
						.filter(c -> c.getOvertimeNo() == ot.getOvertimeNo())
						.findFirst().map(c -> c.getPremiumRate().v()).orElse(0);
				
				transferTime.set(time -> {
					val rate = outOTRate == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(monthlyExcess.valueAsMinutes() / outOTRate);
					return time.add(rate);
				});
			});
		});
		
		/** 換算時間から付与時間と精算時間を計算 */
		val superHD60Time = calcTime(transferTime.get());
		excessOutsideWorkOfMonthly.setSuperHD60Time(superHD60Time);
	}

	private AttendanceTimeMonth getOverTime(ExcessOutsideWorkOfMonthly excessOutsideMonthly, Overtime ot,
			OutsideOTBRDItem bd) {
		
		val otTime = excessOutsideMonthly.getTime().get(ot.getOvertimeNo().value);
		if (otTime == null) {
			return new AttendanceTimeMonth(0);
		}
		
		val brdTime = otTime.getBreakdown().get(bd.getBreakdownItemNo().value);
		if (brdTime == null) {
			return new AttendanceTimeMonth(0);
		}
		
		return brdTime.getExcessTime();
	}
	
	/** 換算時間から付与時間と精算時間を計算 */
	private SuperHD60HConTime calcTime(BigDecimal time) {
		
		/** 小数点以下の丸めを行う */
		val roundedTime = time.setScale(0, this.timeRoundingSetting.getRoundingMode());
		
		/** 再度、丸めを行う */
		val reRoundedTime = this.timeRoundingSetting.roundBigDecimal(roundedTime);
		
		/** 発生単位から付与時間と精算時間を計算する */
		val grantTime = calcGrantTime(reRoundedTime);
		val calcTime = reRoundedTime.intValue() - grantTime;
		val transferedTime = reRoundedTime.intValue();
		
		return SuperHD60HConTime.of(new AttendanceTimeMonth(grantTime), 
									new AttendanceTimeMonth(calcTime),
									new AttendanceTimeMonth(transferedTime));
	}

	private int calcGrantTime(BigDecimal reRoundedTime) {
		if (this.superHolidayOccurrenceUnit.valueAsMinutes() == 0) {
			return 0;
		}
		
		return (reRoundedTime.intValue() / this.superHolidayOccurrenceUnit.valueAsMinutes()) 
							* this.superHolidayOccurrenceUnit.valueAsMinutes();
	}
	
	public static interface RequireM1 {
		
		Optional<OutsideOTSetting> outsideOTSetting(String cid); 
	}
}
