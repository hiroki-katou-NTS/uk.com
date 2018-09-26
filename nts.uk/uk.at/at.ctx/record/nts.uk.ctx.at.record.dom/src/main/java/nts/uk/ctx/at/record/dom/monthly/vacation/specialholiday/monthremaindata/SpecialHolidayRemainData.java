package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveRemainNoMinus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 特別休暇月別残数データ
 * 
 * @author do_dt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SpecialHolidayRemainData extends AggregateRoot {
	/**
	 * 社員ID
	 */
	private String sid;
	/**
	 * 年月
	 */
	private YearMonth ym;
	/**
	 * 締めID
	 */
	private int closureId;

	/**
	 * 締め期間
	 */
	private DatePeriod closurePeriod;
	/**
	 * 締め処理状態
	 */
	private ClosureStatus closureStatus;
	/**
	 * 締め日付
	 */
	private ClosureDate closureDate;

	/**
	 * 特別休暇コード
	 */
	private int specialHolidayCd;
	/**
	 * 実特別休暇
	 */
	private ActualSpecialLeave actualSpecial;
	/**
	 * 特別休暇
	 */
	private SpecialLeave specialLeave;
	/**
	 * 付与区分
	 */
	private boolean grantAtr;
	/**
	 * 特別休暇付与情報: 付与日数
	 */
	private Optional<SpecialLeaveGrantUseDay> grantDays;

	/*
	 * 特別休暇月別残数データを更新
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param period 期間
	 * @param specialLeaveCode 特別休暇コード
	 * @param inPeriod 特別休暇の集計結果
	 * @param remainNoMinus 特別休暇の残数マイナスなし
	 * @return 特別休暇月別残数データ
	 */
	public static SpecialHolidayRemainData of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod period,
			int specialLeaveCode,
			InPeriodOfSpecialLeave inPeriod,
			SpecialLeaveRemainNoMinus remainNoMinus){
	
		SpecialHolidayRemainData domain = new SpecialHolidayRemainData();
		domain.sid = employeeId;
		domain.closureId = closureId.value;
		domain.closurePeriod = period;
		domain.closureStatus = ClosureStatus.UNTREATED;		// 未締め
		domain.closureDate = closureDate;
		domain.ym = yearMonth;
		domain.specialHolidayCd = specialLeaveCode;
		
		val remainDays = inPeriod.getRemainDays();

		// 実特別休暇：残数
		SpecialLeaveRemain actualRemainBefore = new SpecialLeaveRemain(
				new SpecialLeaveRemainDay(remainDays.getGrantDetailBefore().getRemainDays()),
				Optional.empty());
		SpecialLeaveRemain actualRemainAfter = null;
		if (remainDays.getGrantDetailAfter().isPresent()){
			actualRemainAfter = new SpecialLeaveRemain(
					new SpecialLeaveRemainDay(remainDays.getGrantDetailAfter().get().getRemainDays()),
					Optional.empty());
		}
		
		// 実特別休暇：使用数
		double actualUseDays = remainDays.getGrantDetailBefore().getUseDays();
		SpecialLeaveRemainDay actualUseDaysBefore = new SpecialLeaveRemainDay(
				remainDays.getGrantDetailBefore().getUseDays());
		SpecialLeaveRemainDay actualUseDaysAfter = null;
		if (remainDays.getGrantDetailAfter().isPresent()){
			actualUseDaysAfter = new SpecialLeaveRemainDay(remainDays.getGrantDetailAfter().get().getUseDays());
			actualUseDays += actualUseDaysAfter.v();
		}
		SpecialLeaveUseDays actualUseNumberDays = new SpecialLeaveUseDays(
				new SpecialLeaveRemainDay(actualUseDays),
				actualUseDaysBefore,
				Optional.ofNullable(actualUseDaysAfter));
		
		// 実特別休暇
		domain.actualSpecial = new ActualSpecialLeave(
				(actualRemainAfter != null ? actualRemainAfter : actualRemainBefore),
				actualRemainBefore,
				new SpecialLeaveUseNumber(
						actualUseNumberDays,
						Optional.empty()),
				Optional.ofNullable(actualRemainAfter));
		
		// 特別休暇：残数
		SpecialLeaveRemain specialRemainBefore = new SpecialLeaveRemain(
				new SpecialLeaveRemainDay(remainNoMinus.getRemainDaysBeforeGrant()),
				Optional.empty());
		SpecialLeaveRemain specialRemainAfter = null;
		if (remainNoMinus.getRemainDaysAfterGrant().isPresent()){
			specialRemainAfter = new SpecialLeaveRemain(
					new SpecialLeaveRemainDay(remainNoMinus.getRemainDaysAfterGrant().get()),
					Optional.empty());
		}
		
		// 特別休暇：使用数
		double specialUseDays = remainNoMinus.getUseDaysBeforeGrant();
		SpecialLeaveRemainDay specialUseDaysBefore = new SpecialLeaveRemainDay(
				remainNoMinus.getUseDaysBeforeGrant());
		SpecialLeaveRemainDay specialUseDaysAfter = null;
		if (remainNoMinus.getUseDaysAfterGrant().isPresent()){
			specialUseDaysAfter = new SpecialLeaveRemainDay(remainNoMinus.getUseDaysAfterGrant().get());
			specialUseDays += specialUseDaysAfter.v();
		}
		SpecialLeaveUseDays specialUseNumberDays = new SpecialLeaveUseDays(
				new SpecialLeaveRemainDay(specialUseDays),
				specialUseDaysBefore,
				Optional.ofNullable(specialUseDaysAfter));
		
		// 特別休暇：未消化数
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion(
				new SpecialLeaveRemainDay(remainDays.getUnDisgesteDays()),
				Optional.empty());
		
		// 特別休暇
		domain.specialLeave = new SpecialLeave(
				(specialRemainAfter != null ? specialRemainAfter : specialRemainBefore),
				specialRemainBefore,
				new SpecialLeaveUseNumber(
						specialUseNumberDays,
						Optional.empty()),
				unDegestionNumber,
				Optional.ofNullable(specialRemainAfter));
		
		// 付与区分
		domain.grantAtr = inPeriod.getRemainDays().getGrantDetailAfter().isPresent();
		
		// 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if (inPeriod.getRemainDays().getGrantDetailAfter().isPresent()){
			grantDays = new SpecialLeaveGrantUseDay(
					inPeriod.getRemainDays().getGrantDetailAfter().get().getGrantDays());
		}
		domain.grantDays = Optional.ofNullable(grantDays);
		
		return domain;
	}

	public SpecialHolidayRemainData(
			String sid,
			YearMonth ym,
			int closureId,
			ClosureDate closureDate,
			DatePeriod closurePeriod,
			ClosureStatus closureStatus,
			int specialHolidayCd,
			ActualSpecialLeave actualSpecial,
			SpecialLeave specialLeave,
			Optional<SpecialLeaveGrantUseDay> grantDays,
			boolean grantAtr) {
		super();
		this.sid = sid;
		this.ym = ym;
		this.closureId = closureId;
		this.closurePeriod = closurePeriod;
		this.closureStatus = closureStatus;
		this.closureDate = closureDate;
		this.specialHolidayCd = specialHolidayCd;
		this.actualSpecial = actualSpecial;
		this.specialLeave = specialLeave;
		this.grantAtr = grantAtr;
		this.grantDays = grantDays;
	}
}
