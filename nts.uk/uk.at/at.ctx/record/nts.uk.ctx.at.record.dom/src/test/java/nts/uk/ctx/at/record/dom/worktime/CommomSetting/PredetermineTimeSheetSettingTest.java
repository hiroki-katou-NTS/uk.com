package nts.uk.ctx.at.record.dom.worktime.CommomSetting;
//package nts.uk.ctx.at.shared.dom.worktime.CommomSetting;
//
//import static mockit.Deencapsulation.*;
//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;
//
//import java.util.Arrays;
//
//import org.junit.Test;
//
//import lombok.val;
//import nts.uk.ctx.at.record.dom.bonuspay.enums.UseAtr;
//import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
//import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
//import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
//import nts.uk.shr.com.time.TimeWithDayAttr;
//
//public class PredetermineTimeSheetSettingTest {
//
//	@Test
//	public void onedayWork() {
//		val work1 = new TimeSheetWithUseAtr(UseAtr.USE, new TimeWithDayAttr(9 * 60), new TimeWithDayAttr(12 * 60));
//		val work2 = new TimeSheetWithUseAtr(UseAtr.USE, new TimeWithDayAttr(13 * 60), new TimeWithDayAttr(18 * 60));
//		val target = new PredetermineTimeSheetSetting(
//				Arrays.asList(work1, work2),
//				new TimeWithDayAttr(13 * 60),
//				new TimeWithDayAttr(13 * 60));
//		
//		val dailyWork = new DailyWork(WorkTypeUnit.OneDay, WorkTypeClassification.Absence, null, null);
//		target.correctPredetermineTimeSheet(dailyWork);
//
//		assertThat(target.getTimeSheets().size(), is(2));
//		assertThat(target.getTimeSheets().get(0).getStartTime(), is(new TimeWithDayAttr(9 * 60)));
//		assertThat(target.getTimeSheets().get(0).getEndTime(), is(new TimeWithDayAttr(12 * 60)));
//		assertThat(target.getTimeSheets().get(1).getStartTime(), is(new TimeWithDayAttr(13 * 60)));
//		assertThat(target.getTimeSheets().get(1).getEndTime(), is(new TimeWithDayAttr(18 * 60)));
//	}
//	
//	@Test
//	public void morningWork() {
//		val work1 = new TimeSheetWithUseAtr(UseAtr.USE, new TimeWithDayAttr(9 * 60), new TimeWithDayAttr(12 * 60));
//		val work2 = new TimeSheetWithUseAtr(UseAtr.USE, new TimeWithDayAttr(13 * 60), new TimeWithDayAttr(18 * 60));
//		val target = new PredetermineTimeSheetSetting(
//				Arrays.asList(work1, work2),
//				new TimeWithDayAttr(13 * 60),
//				new TimeWithDayAttr(13 * 60));
//		
//		val dailyWork = new DailyWork(WorkTypeUnit.MonringAndAfternoon, null, WorkTypeClassification.Attendance, WorkTypeClassification.Holiday);
//		target.correctPredetermineTimeSheet(dailyWork);
//
//		assertThat(target.getTimeSheets().size(), is(1));
//		assertThat(target.getTimeSheets().get(0).getStartTime(), is(new TimeWithDayAttr(9 * 60)));
//		assertThat(target.getTimeSheets().get(0).getEndTime(), is(new TimeWithDayAttr(12 * 60)));
//	}
//
//}
