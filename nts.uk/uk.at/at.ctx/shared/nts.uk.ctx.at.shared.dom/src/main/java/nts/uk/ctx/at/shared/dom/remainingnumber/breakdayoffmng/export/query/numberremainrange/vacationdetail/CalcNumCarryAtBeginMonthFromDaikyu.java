package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.CarryForwardDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.AccumulationAbsenceDetailComparator;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.CalculateCarryForwardNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.OffsetProcessing;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;

/**
 * @author thanh_nx
 *
 *         代休休出から月初の繰越数を計算
 */
public class CalcNumCarryAtBeginMonthFromDaikyu {

	public static CarryForwardDayTimes calculate(Require require, String cid, String sid, DatePeriod period,
			VacationDetails details, boolean isMode) {

		// 集計開始日以前の逐次発生の休暇明細を取得する
		List<AccumulationAbsenceDetail> lstAcctAbsenDetail = details.getLstAcctAbsenDetail().stream().filter(
				x -> x.getDateOccur().isUnknownDate() || x.getDateOccur().getDayoffDate().get().before(period.start()))
				.collect(Collectors.toList());

		// 逐次発生の休暇明細をソートする
		lstAcctAbsenDetail.sort(new AccumulationAbsenceDetailComparator());

		// 月初時点での相殺処理
		OffsetProcessing.process(require, cid, sid, period.end(), lstAcctAbsenDetail);

		// 代休休出から月初の繰越数を計算
		return CalculateCarryForwardNumber.process(require, cid, sid, period.start(), lstAcctAbsenDetail, isMode);
	}

	public static interface Require extends OffsetProcessing.Require, CalculateCarryForwardNumber.Require {

	}
}
