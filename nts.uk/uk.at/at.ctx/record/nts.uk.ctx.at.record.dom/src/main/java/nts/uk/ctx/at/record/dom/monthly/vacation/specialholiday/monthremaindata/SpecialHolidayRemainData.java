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
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SpecialHolidayRemainData extends AggregateRoot{
	/**
	 * 社員ID
	 */
	private String sid;
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
	 * 年月
	 */
	private YearMonth ym;
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
	
}
