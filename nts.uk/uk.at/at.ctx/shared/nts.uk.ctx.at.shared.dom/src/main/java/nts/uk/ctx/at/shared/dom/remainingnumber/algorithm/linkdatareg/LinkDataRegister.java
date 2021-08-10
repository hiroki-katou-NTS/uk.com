package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

/**
 * @author thanh_nx
 *
 *         紐付けデータ登録
 */
public class LinkDataRegister {

	public static AtomTask process(Require require, String sid, List<GeneralDate> lstDate,
			DatePeriod period, 
			List<InterimRemain> interimRemains) {

		AtomTask atomTask = AtomTask.none();
		// 代休の発生消化を変更を加えて取得する
		atomTask = UpdateSubstituteHolidayLinkData.updateProcess(require, sid, period, lstDate, 
				interimRemains.stream().filter(x -> x.getRemainType() == RemainType.SUBHOLIDAY).map(x -> (InterimDayOffMng)x).collect(Collectors.toList()), 
				interimRemains.stream().filter(x -> x.getRemainType() == RemainType.BREAK).map(x -> (InterimBreakMng)x).collect(Collectors.toList()));

		// 振休の発生消化を変更を加えて取得する
		atomTask = atomTask.then(() -> UpdateHolidayLinkData.updateProcess(require, sid, period, lstDate,
				interimRemains.stream().filter(x -> x.getRemainType() == RemainType.PAUSE).map(x -> (InterimAbsMng) x)
						.collect(Collectors.toList()),
				interimRemains.stream().filter(x -> x.getRemainType() == RemainType.PICKINGUP)
						.map(x -> (InterimRecMng) x).collect(Collectors.toList())));
		return atomTask;
	}
	public static interface Require extends UpdateSubstituteHolidayLinkData.Require, UpdateHolidayLinkData.Require {

	}
}
