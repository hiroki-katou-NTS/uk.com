package nts.uk.ctx.at.shared.dom.employmentrules;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.*;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class ConditionEmployeeTest {

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
    public void Check_isConditionChanger_isTrue(@Mocked final AppContexts tr) {
        ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);
        WorkingConditionItem item1 = null;
        WorkingConditionItem item2 = null;

        new Expectations() {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.GetHistoryItemByPeriod("cid",Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
                result = new ArrayList<WorkingConditionItem>(){{
                    add(item1);
                    add(item2);
                }};
            }
        };
        assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
    }

    @Test
    public void Check_isLeave_isTrue(@Mocked final AppContexts tr) {
        ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);

        LeavePeriod leavePeriod = null;

        new Expectations() {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.GetLeavePeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
                result = new ArrayList<LeavePeriod>(){{
                    add(leavePeriod);
                }};
            }
        };
        assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
    }

    @Test
    public void Check_isLeave_isTrue_V1(@Mocked final AppContexts tr) {
        ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);

        LeaveHolidayPeriod leaveHolidayPeriod = null;

        new Expectations() {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.GetLeaveHolidayPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
                result = new ArrayList<LeaveHolidayPeriod>(){{
                    add(leaveHolidayPeriod);
                }};
            }
        };
        assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
    }

    @Test
    public void Check_isTransferPerson_isTrue(@Mocked final AppContexts tr) {
        ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);
        AffiliationPeriodAndWorkplace item1 = null;
        AffiliationPeriodAndWorkplace item2 = null;
        WorkPlaceHist workPlaceHist = new WorkPlaceHist("eid",new ArrayList<AffiliationPeriodAndWorkplace>(){{
            add(item1);
            add(item2);
        }});

        new Expectations() {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.GetWorkHistory(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
                result = new ArrayList<WorkPlaceHist>(){{
                    add(workPlaceHist);
                }};

            }
        };
        assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
    }

    @Test
    public void Check_employeesIsEligible_isFalse() {
        ConditionEmployee conditionEmployee = new ConditionEmployee(false,false,false,false);
        assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
    }

}
