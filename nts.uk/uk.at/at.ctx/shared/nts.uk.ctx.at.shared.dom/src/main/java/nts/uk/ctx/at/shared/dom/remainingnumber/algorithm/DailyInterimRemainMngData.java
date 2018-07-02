package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;
/**
 * 暫定残数データ
 * @author do_dt
 *
 */

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DailyInterimRemainMngData {
	/**暫定振休管理データ */
	private Optional<InterimAbsMng> interimAbsData;
	/**	暫定残数管理データ */
	private List<InterimRemain> recAbsData;
	/**
	 * 暫定振出管理データ
	 */
	private Optional<InterimRecMng> recData;
	/**
	 * 暫定代休管理データ
	 */
	private Optional<InterimDayOffMng> dayOffData;
	/**
	 * 暫定年休管理データ
	 */
	private Optional<TmpAnnualHolidayMng> annualHolidayData;
	/**
	 * 暫定積立年休管理データ
	 */
	private Optional<TmpResereLeaveMng> resereData;
	/**
	 * 暫定休出管理データ
	 */
	private Optional<InterimBreakMng> breakData;
	
}
