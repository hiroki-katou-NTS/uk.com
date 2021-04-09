package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.algorithm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.Assert;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetCombinationrAndWorkHolidayAtrService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;

@RunWith(JMockit.class)
public class ScheYearCheckServiceTest {
	@Injectable ScheYearCheckServiceImpl scheYearCheckService;
	
	@Test
	public void getCompareOperatorTextTest() {
		CompareRange compareRange = new CompareRange<>(RangeCompareType.BETWEEN_RANGE_OPEN);
		compareRange.setStartValue(Double.valueOf(12.0));
		compareRange.setEndValue(Double.valueOf(20.0));
		
		new Expectations() {{
			result = "12 < A";
		}};
		
		val instance = new ScheYearCheckServiceImpl();
		@SuppressWarnings("unchecked")
		val result = (String)NtsAssert.Invoke.privateMethod(
				instance, 
				"getCompareOperatorText", 
				compareRange, 
				"A"
			);
	}
}
