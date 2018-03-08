package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype;

import static mockit.Deencapsulation.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public class PlanActualWorkTypeTest {

    @Mocked
    WorkInfoOfDailyPerformance info;
    
    @Test
    public void checkWorkTypeTest() {
        val target = PlanActualWorkType.init(true, FilterByCompare.DO_NOT_COMPARE.value);
        target.setworkTypeActual(true, Arrays.asList("1", "2"));
        target.setWorkTypePlan(true, Arrays.asList("1", "2"));
        target.chooseOperator(LogicalOperator.AND.value);
        new Expectations() {
            {
                info.getScheduleWorkInformation();
                result = new WorkInformation(new WorkTypeCode("1"), new WorkTimeCode("1"));
                times = 1;

                info.getRecordWorkInformation();
                result = new WorkInformation(new WorkTypeCode("3"), new WorkTimeCode("1"));
                times = 1;
            }
        };

        boolean actual = target.checkWorkType(info);
        assertThat(actual, is(true));

    }

}
