package nts.uk.ctx.at.shared.dom.employmentrules;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.ConditionEmployee;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.WorkPlaceHist;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;

@RunWith(JMockit.class)
public class OrganizationmanagementTest {
    @Injectable
    private ConditionEmployee.Require require;

    @Test
    public void getters() {
        ConditionEmployee workInformation = new ConditionEmployee(true,true,true,true);
        NtsAssert.invokeGetters(workInformation);
    }

    @Test
    public void Check_isShortTimeWork_isTrue() {
        ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);
        new Expectations() {
            {
                require.GetShortWorkHistory("eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()));
                result = Optional.of(new ShortWorkTimeHistory("cid","sid",new ArrayList<>()));
            }
        };
        assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
    }

    @Test
    public void Check_isConditionChanger_isTrue() {
        ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);
        new Expectations() {
            {
                require.GetShortWorkHistory("eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()));
                result = Optional.empty();

                require.GetHistoryItemByPeriod("0001",Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
                result = Arrays.asList();
            }
        };
        assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
    }

}
