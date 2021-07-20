package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class RegisterOuenWorkTimeSheetOfDailyServiceTest {

	@Injectable
	private RegisterOuenWorkTimeSheetOfDailyService.Require require;
	private String empId = "empId";
	private GeneralDate ymd = GeneralDate.today();
	private List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailys = new ArrayList<>();
	private List<EditStateOfDailyPerformance> editStateOfDailyPerformance = new ArrayList<>();
	private EditStateOfDailyPerformance editStateOfDaily = new EditStateOfDailyPerformance(empId, ymd,
			new EditStateOfDailyAttd(1, EditStateSetting.HAND_CORRECTION_MYSELF));

	// $実績の作業時間帯 notPresent
	// and attendanceIds == empty

	// $実績の作業時間帯 notPresent
	// and attendanceIds not empty
	// createEditState => Insert
	@Test
	public void test2() {

		List<Integer> atendentceIds = new ArrayList<>();
		atendentceIds.add(1);

		new Expectations() {
			{
				require.findOuenWorkTimeSheetOfDaily(empId, ymd);

				require.getEditStateOfDailyPerformance(empId, ymd);
			}
		};

		new MockUp<OuenWorkTimeSheetOfDaily>() {
			@Mock
			public List<Integer> getAttendanceId() {
				return atendentceIds;

			}
		};

		OuenWorkTimeSheetOfDaily domain = new OuenWorkTimeSheetOfDaily(empId, ymd, ouenWorkTimeSheetOfDailys);

		EditStateOfDailyPerformance domain1 = new EditStateOfDailyPerformance(empId, 1, ymd,
				EnumAdaptor.valueOf(1, EditStateSetting.class));

		AtomTask atomtask = RegisterOuenWorkTimeSheetOfDailyService.register(require, empId, ymd,
				ouenWorkTimeSheetOfDailys, EnumAdaptor.valueOf(1, EditStateSetting.class));

		NtsAssert.atomTask(() -> atomtask, any -> require.insert(domain));
		NtsAssert.atomTask(() -> atomtask, any -> require.insert(domain1));
	}

	// $実績の作業時間帯 notPresent
	// and attendanceIds not empty
	// createEditState => Update
	@Test
	public void test3() {
		this.editStateOfDailyPerformance.add(editStateOfDaily);
		this.editStateOfDailyPerformance.add(editStateOfDaily);
		this.editStateOfDailyPerformance.add(editStateOfDaily);

		List<Integer> atendentceIds = new ArrayList<>();
		atendentceIds.add(1);

		new Expectations() {
			{
				require.findOuenWorkTimeSheetOfDaily(empId, ymd);

				require.getEditStateOfDailyPerformance(empId, ymd);
				result = editStateOfDailyPerformance;
			}
		};

		new MockUp<OuenWorkTimeSheetOfDaily>() {
			@Mock
			public List<Integer> getAttendanceId() {
				return atendentceIds;

			}
		};

		OuenWorkTimeSheetOfDaily domain = new OuenWorkTimeSheetOfDaily(empId, ymd, ouenWorkTimeSheetOfDailys);

		EditStateOfDailyPerformance domain1 = new EditStateOfDailyPerformance(empId, 1, ymd,
				EnumAdaptor.valueOf(1, EditStateSetting.class));

		AtomTask atomtask = RegisterOuenWorkTimeSheetOfDailyService.register(require, empId, ymd,
				ouenWorkTimeSheetOfDailys, EnumAdaptor.valueOf(1, EditStateSetting.class));

		NtsAssert.atomTask(() -> atomtask, any -> require.insert(domain));
		NtsAssert.atomTask(() -> atomtask, any -> require.update(domain1));
	}

	// $登録対象.add(require.作業時間帯を削除する($実績の作業時間帯))
	// return require.編集状態を追加する($日別実績の編集状態) [prv-1]
	@Test
	public void test4() {
		this.editStateOfDailyPerformance.add(editStateOfDaily);
		this.editStateOfDailyPerformance.add(editStateOfDaily);

		List<Integer> atendentceIds = new ArrayList<>();
		atendentceIds.add(1);

		OuenWorkTimeSheetOfDaily ouenWorkTime = OuenWorkTimeSheetOfDaily.create(empId, ymd, ouenWorkTimeSheetOfDailys);

		AttendanceItemToChange attendanceItemToChange = new AttendanceItemToChange(atendentceIds, ouenWorkTime);

		new Expectations() {
			{
				require.findOuenWorkTimeSheetOfDaily(empId, ymd);
				result = Optional.of(ouenWorkTime);

				require.getEditStateOfDailyPerformance(empId, ymd);
				result = editStateOfDailyPerformance;
			}
		};

		new MockUp<OuenWorkTimeSheetOfDaily>() {
			@Mock
			public AttendanceItemToChange change(List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet) {
				return attendanceItemToChange;

			}
		};

		OuenWorkTimeSheetOfDaily domain = new OuenWorkTimeSheetOfDaily(empId, ymd, ouenWorkTimeSheetOfDailys);

		EditStateOfDailyPerformance domain1 = new EditStateOfDailyPerformance(empId, 1, ymd,
				EnumAdaptor.valueOf(1, EditStateSetting.class));

		AtomTask atomtask = RegisterOuenWorkTimeSheetOfDailyService.register(require, empId, ymd,
				ouenWorkTimeSheetOfDailys, EnumAdaptor.valueOf(1, EditStateSetting.class));

		NtsAssert.atomTask(() -> atomtask, any -> require.delete(domain));
		NtsAssert.atomTask(() -> atomtask, any -> require.insert(domain1));

	}

	// $登録対象.add(require.作業時間帯を更新する($更新時間帯))
	// return require.編集状態を追加する($日別実績の編集状態) [prv-1]
	@Test
	public void test5() {

		OuenWorkTimeSheetOfDailyAttendance attendance = OuenWorkTimeSheetOfDailyAttendance.create(SupportFrameNo.of(1),
				WorkContent.create(WorkplaceOfWorkEachOuen.create(new WorkplaceId("DUMMY"), new WorkLocationCD("1")),
						Optional.empty(), Optional.empty()),
				null);

		this.editStateOfDailyPerformance.add(editStateOfDaily);
		this.editStateOfDailyPerformance.add(editStateOfDaily);
		this.ouenWorkTimeSheetOfDailys.add(attendance);

		List<Integer> atendentceIds = new ArrayList<>();
		atendentceIds.add(1);

		OuenWorkTimeSheetOfDaily ouenWorkTime = OuenWorkTimeSheetOfDaily.create(empId, ymd, ouenWorkTimeSheetOfDailys);

		EditStateOfDailyPerformance domain1 = new EditStateOfDailyPerformance(empId, 1, ymd,
				EnumAdaptor.valueOf(1, EditStateSetting.class));

		AttendanceItemToChange attendanceItemToChange = new AttendanceItemToChange(atendentceIds, ouenWorkTime);

		new Expectations() {
			{
				require.findOuenWorkTimeSheetOfDaily(empId, ymd);
				result = Optional.of(ouenWorkTime);

				require.getEditStateOfDailyPerformance(empId, ymd);
				result = editStateOfDailyPerformance;
			}
		};

		new MockUp<OuenWorkTimeSheetOfDaily>() {
			@Mock
			public AttendanceItemToChange change(List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet) {
				return attendanceItemToChange;

			}
		};

		OuenWorkTimeSheetOfDaily domain = new OuenWorkTimeSheetOfDaily(empId, ymd, ouenWorkTimeSheetOfDailys);

		AtomTask atomtask = RegisterOuenWorkTimeSheetOfDailyService.register(require, empId, ymd,
				ouenWorkTimeSheetOfDailys, EnumAdaptor.valueOf(1, EditStateSetting.class));

		NtsAssert.atomTask(() -> atomtask, any -> require.update(domain));
		NtsAssert.atomTask(() -> atomtask, any -> require.insert(domain1));
	}
}
