package nts.uk.ctx.at.schedule.dom.schedule.share;

import java.util.Optional;

import mockit.Injectable;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;

public class OuenWorkTimeSheetOfDailyAttendanceHelperInSchedule {

	@Injectable
	private static TimeSheetOfAttendanceEachOuenSheet timeSheet;
	
	/**
	 * 時間帯で日別勤怠の応援作業時間帯を作る
	 * @param workplaceId 職場ID
	 * @return
	 */
	public static OuenWorkTimeSheetOfDailyAttendance createOuenWorkTimeSheetOfDailyAttendance(
			String workplaceId
			) {
		return OuenWorkTimeSheetOfDailyAttendance.create( new SupportFrameNo( 1 )
				,	WorkContent.create( 
							new WorkplaceOfWorkEachOuen( new WorkplaceId( workplaceId ), Optional.empty(), Optional.empty() )
						,	Optional.empty()
						,	Optional.empty() )
				,	timeSheet
				,	Optional.empty()
					);
	}
}
