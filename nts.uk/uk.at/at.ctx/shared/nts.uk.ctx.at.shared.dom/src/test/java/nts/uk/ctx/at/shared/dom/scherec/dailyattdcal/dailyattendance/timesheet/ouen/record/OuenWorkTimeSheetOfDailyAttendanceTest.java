package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;

/**
 * 日別勤怠の応援作業時間帯のUTコード
 * @author lan_lt
 *
 */
public class OuenWorkTimeSheetOfDailyAttendanceTest {

	@Test
	public void testGetter(
				@Injectable WorkContent workContent
			,	@Injectable TimeSheetOfAttendanceEachOuenSheet timeSheet ) {
		
		val domain = new OuenWorkTimeSheetOfDailyAttendance(
					new SupportFrameNo( 1 )
				,	SupportType.TIMEZONE
				,	workContent, timeSheet, Optional.empty() );
		
		NtsAssert.invokeGetters( domain );
		
	}
	
	/**
	 * target: create
	 */
	@Test
	public void testCreate(
				@Injectable WorkContent workContent
			,	@Injectable TimeSheetOfAttendanceEachOuenSheet timeSheet
			) {
		
		//act
		val result = OuenWorkTimeSheetOfDailyAttendance.create(
					new SupportFrameNo( 1 )
				,	workContent
				,	timeSheet
				,	Optional.empty() );
		
		//assert
		assertThat( result.getWorkNo().v() ).isEqualTo( 1 );
		assertThat( result.getSupportType() ).isEqualTo( SupportType.TIMEZONE );
		assertThat( result.getWorkContent() ).isEqualTo( workContent );
		assertThat( result.getTimeSheet() ).isEqualTo( timeSheet );
		assertThat( result.getInputFlag() ).isEmpty();
		
	}
	
}
