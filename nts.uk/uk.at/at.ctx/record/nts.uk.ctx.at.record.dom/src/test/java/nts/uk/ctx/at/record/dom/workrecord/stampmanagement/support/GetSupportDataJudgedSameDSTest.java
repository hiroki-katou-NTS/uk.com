/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

/**
 * @author laitv
 */

@RunWith(JMockit.class)
public class GetSupportDataJudgedSameDSTest {

	@Injectable
	private GetSupportDataJudgedSameDS.Required required;
	
	// required.getCriteriaSameStampOfSupport = null
	@Test
	public void case1() {
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = null;
		boolean isStart = true;
		new Expectations() {
			{
				required.getCriteriaSameStampOfSupport();
				result = null;
			}
		};
		Optional<OuenWorkTimeSheetOfDailyAttendance> rs = GetSupportDataJudgedSameDS.getSupportDataJudgedSame(required, supportDataList, targetSupportData, isStart);
		assertThat(rs).isEqualTo(Optional.empty());
	}
	
	// required.getCriteriaSameStampOfSupport != null
	// isStart = true
	// boolean check = jcSameStampOfSupport.checkStampRecognizedAsSame(standardStampStart, targetStampStart) ==> true
	@Test
	public void case2() {
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest1();
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime);
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest2();
		boolean isStart = true;
		
		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(60), new MaximumNumberOfSupport(20));
		new Expectations() {
			{
				required.getCriteriaSameStampOfSupport();
				result = support;
			}
		};
		Optional<OuenWorkTimeSheetOfDailyAttendance> rs = GetSupportDataJudgedSameDS.getSupportDataJudgedSame(required, supportDataList, targetSupportData, isStart);
		assertThat(rs).isEqualTo(Optional.of(supportDataList.get(0)));
	}
	
	// required.getCriteriaSameStampOfSupport != null
	// isStart = true
	// boolean check = jcSameStampOfSupport.checkStampRecognizedAsSame(standardStampStart, targetStampStart) ==> false
	@Test
	public void case3() {
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest1();
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime);
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest3();
		boolean isStart = true;
		
		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(60), new MaximumNumberOfSupport(20));
		new Expectations() {
			{
				required.getCriteriaSameStampOfSupport();
				result = support;
			}
		};
		Optional<OuenWorkTimeSheetOfDailyAttendance> rs = GetSupportDataJudgedSameDS.getSupportDataJudgedSame(required, supportDataList, targetSupportData, isStart);
		assertThat(rs).isEqualTo(Optional.empty());
	}

	// required.getCriteriaSameStampOfSupport != null
	// isStart = false
	// boolean check = jcSameStampOfSupport.checkStampRecognizedAsSame(standardStampStart, targetStampStart) ==> true
	@Test
	public void case4() {
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest1();
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime);
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest2();
		boolean isStart = false;

		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(60), new MaximumNumberOfSupport(20));
		new Expectations() {
			{
				required.getCriteriaSameStampOfSupport();
				result = support;
			}
		};
		Optional<OuenWorkTimeSheetOfDailyAttendance> rs = GetSupportDataJudgedSameDS.getSupportDataJudgedSame(required,
				supportDataList, targetSupportData, isStart);
		assertThat(rs).isEqualTo(Optional.of(supportDataList.get(0)));
	}
	
	// required.getCriteriaSameStampOfSupport != null
	// isStart = false
	// boolean check = jcSameStampOfSupport.checkStampRecognizedAsSame(standardStampStart, targetStampStart) ==> false
	@Test
	public void case5() {
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest1();
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime);
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendanceHelper.getDataTest3();
		boolean isStart = false;

		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(60), new MaximumNumberOfSupport(20));
		new Expectations() {
			{
				required.getCriteriaSameStampOfSupport();
				result = support;
			}
		};
		Optional<OuenWorkTimeSheetOfDailyAttendance> rs = GetSupportDataJudgedSameDS.getSupportDataJudgedSame(required,
				supportDataList, targetSupportData, isStart);
		assertThat(rs).isEqualTo(Optional.empty());
	}

}
