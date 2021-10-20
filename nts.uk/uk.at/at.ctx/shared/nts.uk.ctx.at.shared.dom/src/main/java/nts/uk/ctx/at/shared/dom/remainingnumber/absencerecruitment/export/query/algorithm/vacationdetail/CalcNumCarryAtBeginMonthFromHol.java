package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.CalcCarryForwardNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.CompenSuspensionOffsetProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.AccumulationAbsenceDetailComparator;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * @author thanh_nx
 *
 *         振休振出から月初の繰越数を計算
 */
public class CalcNumCarryAtBeginMonthFromHol {

	public static ReserveLeaveRemainingDayNumber calculate(Require require, String cid, String sid, DatePeriod period,
			VacationDetails details, boolean isMode) {

		// 集計開始日以前の逐次発生の休暇明細を取得する
		List<AccumulationAbsenceDetail> lstAcctAbsenDetail = details.getLstAcctAbsenDetail().stream().filter(
				x -> x.getDateOccur().isUnknownDate() || x.getDateOccur().getDayoffDate().get().before(period.start()))
				.collect(Collectors.toList());

		// 逐次発生の休暇明細をソートする
		lstAcctAbsenDetail.sort(new AccumulationAbsenceDetailComparator());

		// 月初時点での相殺処理
		CompenSuspensionOffsetProcess.process(require, cid, sid, period.end(), lstAcctAbsenDetail);

		// 繰越数を計算する
		return CalcCarryForwardNumber.calc(lstAcctAbsenDetail, period.start(), isMode);

	}

	public static interface Require extends CompenSuspensionOffsetProcess.Require {

	}
}
