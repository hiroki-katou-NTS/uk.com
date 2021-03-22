package nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@RunWith(JMockit.class)
public class AddDataBeforeApplicationReflectTest {

	@Injectable
	private AddDataBeforeApplicationReflect.Require require;

	@SuppressWarnings("unchecked")
	@Test
	public void test(@Mocked DailyRecordToAttendanceItemConverter converter) {

		IntegrationOfDaily domainBefore = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				1);

		List<ItemValue> itemValue = new ArrayList<>();
		itemValue.add(new ItemValue(ValueType.TIME_WITH_DAY, "", 31, "600"));
		itemValue.add(new ItemValue(ValueType.TIME_WITH_DAY, "", 34, "600"));
		new Expectations() {
			{
				converter.convert((Collection<Integer>) any);
				result = itemValue;
			}
		};
		AddDataBeforeApplicationReflect.process(require, new ArrayList<>(), domainBefore);

	}

}
