package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record;

import java.util.Optional;

import mockit.Injectable;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;

public class OuenWorkTimeSheetOfDailyAttendanceHelper {
	
	@Injectable
	private static TimeSheetOfAttendanceEachOuenSheet timeSheet;
	
	/**
	 * 応援別勤務の勤務先を作る
	 * @param workplaceId　職場ID
	 * @param workplaceGroupId 職場グループID
	 * @param supportType 応援形式
	 * @return
	 */
	public static OuenWorkTimeSheetOfDailyAttendance createOuenWorkTimeSheet(
			String workplaceId,
			Optional<String> workplaceGroupId,
			SupportType supportType ) {
			return new OuenWorkTimeSheetOfDailyAttendance( new SupportFrameNo( 1 )
					,	supportType
					,	WorkContent.create( 
								new WorkplaceOfWorkEachOuen( new WorkplaceId( workplaceId ), Optional.empty(), workplaceGroupId )
							,	Optional.empty()
							,	Optional.empty() )
					,	timeSheet
					,	Optional.empty()
						);
	}
	
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
