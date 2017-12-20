package nts.uk.ctx.at.record.app.find.workrecord.daily;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.workrecord.daily.dto.DailyWorkRecordDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 勤務実績（日次）Finder */
@Stateless
public class DailyWorkRecordFinder extends FinderFacade {

	/**
	 * Find 勤務実績（日次）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DailyWorkRecordDto find() {
		return new DailyWorkRecordDto();
	}
}
