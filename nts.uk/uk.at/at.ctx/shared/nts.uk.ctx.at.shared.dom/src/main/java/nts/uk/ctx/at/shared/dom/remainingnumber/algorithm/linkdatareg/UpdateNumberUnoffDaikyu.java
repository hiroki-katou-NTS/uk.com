package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.AfterChangeHolidayDaikyuInfoResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;

/**
 * @author thanh_nx
 *
 *         変更後の代休休出情報と暫定データ
 */
@AllArgsConstructor
@Getter
public class UpdateNumberUnoffDaikyu {

	/**
	 * 変更後の代休休出情報
	 */
	private AfterChangeHolidayDaikyuInfoResult afterResult;

	/**
	 * 暫定休出
	 */
	private List<InterimBreakMng> kyusyutsu;

	/**
	 * 暫定代休
	 */
	private List<InterimDayOffMng> daikyu;

}
