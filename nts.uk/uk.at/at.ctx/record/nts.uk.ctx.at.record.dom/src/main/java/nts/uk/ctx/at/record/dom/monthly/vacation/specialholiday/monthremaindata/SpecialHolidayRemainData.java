package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 特別休暇月別残数データ
 * 
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
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
	 * 締め日
	 */
	private int closureDay;

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
	/**
	 * for using table merge KrcdtMonRemainMerge
	 * @author lanlt
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @param closureDay
	 * @param closurePeriod
	 * @param closureStatus
	 * @param actualSpecial
	 * @param specialLeave
	 * @param grantDays
	 * @param grantAtr
	 */
	public SpecialHolidayRemainData(
			String employeeId, 
			YearMonth yearMonth, 
			int closureId, 
			int closureDay,
			DatePeriod closurePeriod, 
			ClosureStatus closureStatus,
			ActualSpecialLeave actualSpecial, 
			SpecialLeave specialLeave, 
			Optional<SpecialLeaveGrantUseDay> grantDays,
			boolean grantAtr) {
		this.sid = employeeId;
		this.ym = yearMonth;
		this.closureId = closureId;
		this.closureDay = closureDay;
		this.closurePeriod = closurePeriod;
		this.closureStatus  = closureStatus;
		this.specialHolidayCd = 1;
		this.actualSpecial = actualSpecial;
		this.specialLeave = specialLeave;
		this.grantDays = grantDays;
		this.grantAtr = grantAtr;
	}
}
