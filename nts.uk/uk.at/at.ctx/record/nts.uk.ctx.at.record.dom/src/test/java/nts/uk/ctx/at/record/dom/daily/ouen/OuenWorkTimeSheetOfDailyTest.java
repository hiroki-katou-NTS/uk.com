package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

public class OuenWorkTimeSheetOfDailyTest {

	private String empId = "empId";
	private GeneralDate ymd = GeneralDate.today();

	@Test
	public void testOuenWorkTimeSheetOfDaily_contructor() {
		
		new MockUp<AttendanceItemIdContainer>() {
			@Mock
			public  List<ItemValue> getIds(AttendanceItemType type) {
				return new ArrayList<>();

			}
		};

		OuenWorkTimeSheetOfDaily rs = new OuenWorkTimeSheetOfDaily(empId, ymd, new ArrayList<>());
		NtsAssert.invokeGetters(rs);
	}

	// @Test
	// public void testMethodChange() {
	// OuenWorkTimeSheetOfDaily rs =
	// OuenWorkTimeSheetOfDailyHelper.getOuenWorkTimeSheetOfDailyDefault();
	//
	// AttendanceItemToChange attendanceItemToChange =
	// rs.change(OuenWorkTimeSheetOfDailyHelper.getListOuenWorkTime());
	// }

}
