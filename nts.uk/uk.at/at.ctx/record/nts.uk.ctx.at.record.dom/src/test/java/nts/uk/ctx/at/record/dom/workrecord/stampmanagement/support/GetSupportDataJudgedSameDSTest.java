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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime1 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(5))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(60)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime2 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(10))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(60)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime3 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(1))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(60)))));
		
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime1);
		supportDataList.add(ouenWorkTime2);
		supportDataList.add(ouenWorkTime3);
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(20))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(70)))));
		
		boolean isStart = true;
		
		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(10), new MaximumNumberOfSupport(20));
		new Expectations() {
			{
				required.getCriteriaSameStampOfSupport();
				result = support;
			}
		};
		Optional<OuenWorkTimeSheetOfDailyAttendance> rs = GetSupportDataJudgedSameDS.getSupportDataJudgedSame(required, supportDataList, targetSupportData, isStart);
		assertThat(rs).isEqualTo(Optional.of(supportDataList.get(1)));
	}
	
	// required.getCriteriaSameStampOfSupport != null
	// isStart = true
	// boolean check = jcSameStampOfSupport.checkStampRecognizedAsSame(standardStampStart, targetStampStart) ==> false
	@Test
	public void case3() {
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime1 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(5))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(60)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime2 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(10))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(60)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime3 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(1))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(60)))));
		
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime1);
		supportDataList.add(ouenWorkTime2);
		supportDataList.add(ouenWorkTime3);
		
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendance.create(
				1, 
				null, 
				TimeSheetOfAttendanceEachOuenSheet.create(
						new WorkNo(1), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(120))), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(170)))));
		
		boolean isStart = true;
		
		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(10), new MaximumNumberOfSupport(20));
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
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime1 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(5))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(30)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime2 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(10))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(30)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime3 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(1))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(20)))));
		
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime1);
		supportDataList.add(ouenWorkTime2);
		supportDataList.add(ouenWorkTime3);
		
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(5))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(10))))); 
		
		boolean isStart = false;

		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(10), new MaximumNumberOfSupport(20));
		new Expectations() {
			{
				required.getCriteriaSameStampOfSupport();
				result = support;
			}
		};
		Optional<OuenWorkTimeSheetOfDailyAttendance> rs = GetSupportDataJudgedSameDS.getSupportDataJudgedSame(required, supportDataList, targetSupportData, isStart);
		assertThat(rs).isEqualTo(Optional.of(supportDataList.get(2)));
	}
	
	// required.getCriteriaSameStampOfSupport != null
	// isStart = false
	// boolean check = jcSameStampOfSupport.checkStampRecognizedAsSame(standardStampStart, targetStampStart) ==> false
	@Test
	public void case5() {
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime1 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(5))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(30)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime2 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(10))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(40)))));
		
		OuenWorkTimeSheetOfDailyAttendance ouenWorkTime3 = OuenWorkTimeSheetOfDailyAttendance.create(
				1, null, TimeSheetOfAttendanceEachOuenSheet.create(
							new WorkNo(1), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(1))), 
							Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(50)))));
		
		List<OuenWorkTimeSheetOfDailyAttendance> supportDataList = new ArrayList<OuenWorkTimeSheetOfDailyAttendance>();
		supportDataList.add(ouenWorkTime1);
		supportDataList.add(ouenWorkTime2);
		supportDataList.add(ouenWorkTime3);
		
		OuenWorkTimeSheetOfDailyAttendance targetSupportData = OuenWorkTimeSheetOfDailyAttendance.create(
				1, 
				null, 
				TimeSheetOfAttendanceEachOuenSheet.create(
						new WorkNo(1), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(5))), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(10)))));
		
		boolean isStart = false;

		JudgmentCriteriaSameStampOfSupport support = new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(10), new MaximumNumberOfSupport(20));
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
