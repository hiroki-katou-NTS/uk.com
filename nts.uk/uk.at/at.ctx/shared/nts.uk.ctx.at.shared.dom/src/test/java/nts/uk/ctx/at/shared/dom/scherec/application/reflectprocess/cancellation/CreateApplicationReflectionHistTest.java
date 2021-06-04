package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.cancellation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.cancellation.AttendanceBeforeApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.cancellation.CreateApplicationReflectionHist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

@RunWith(JMockit.class)
public class CreateApplicationReflectionHistTest {

	@Injectable
	private CreateApplicationReflectionHist.Require require;

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

		List<AttendanceBeforeApplicationReflect> before = new ArrayList<>();
		before.add(new AttendanceBeforeApplicationReflect(31, Optional.of("200"), Optional.empty()));
		before.add(new AttendanceBeforeApplicationReflect(34, Optional.of("201"),
				Optional.of(new EditStateOfDailyAttd(34, EditStateSetting.HAND_CORRECTION_MYSELF))));
		NtsAssert.Invoke.privateMethod(new CreateApplicationReflectionHist(), "addDataBeforeAppReflect", require,
				before, domainBefore);

		assertThat(before).extracting(x -> x.getAttendanceId(), x -> x.getEditState(), x -> x.getValue().orElse(null))
				.containsExactly(Tuple.tuple(31, Optional.empty(), "600"), Tuple.tuple(34, Optional.empty(), "600"));

	}

}
