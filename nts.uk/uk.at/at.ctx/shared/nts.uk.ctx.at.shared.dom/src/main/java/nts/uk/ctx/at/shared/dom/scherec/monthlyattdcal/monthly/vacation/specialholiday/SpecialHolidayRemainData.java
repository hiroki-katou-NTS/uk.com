package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 特別休暇月別残数データ
 *
 * @author do_dt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
	private SpecialLeave actualSpecial;
	/**
	 * 特別休暇
	 */
	private SpecialLeave specialLeave;
	/**
	 * 付与区分
	 */
	private boolean grantAtr;
	/**
	 * 未消化数
	 */
	private SpecialLeaveUnDigestion unDegestionNumber;
	/**
	 * 特別休暇付与情報: 付与日数
	 */
	private Optional<LeaveGrantDayNumber> grantDays;

	public SpecialHolidayRemainData(String sid, YearMonth ym, int closureId, ClosureDate closureDate,
			DatePeriod closurePeriod, ClosureStatus closureStatus, int specialHolidayCd, SpecialLeave actualSpecial,
			SpecialLeave specialLeave, Optional<LeaveGrantDayNumber> grantDays, boolean grantAtr,
			SpecialLeaveUnDigestion unDegestionNumber) {
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
		this.unDegestionNumber = unDegestionNumber;
	}
}
