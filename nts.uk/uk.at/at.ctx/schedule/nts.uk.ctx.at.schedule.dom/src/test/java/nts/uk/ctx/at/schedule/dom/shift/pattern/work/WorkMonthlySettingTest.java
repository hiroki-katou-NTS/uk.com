package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import lombok.AllArgsConstructor;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(JMockit.class)
public class WorkMonthlySettingTest {

    @Injectable
    private WorkInformation.Require require;

    private final String CID = "000-001";

    @Test
    public void test_WORKTYPE_WAS_DELETE() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        new Expectations() {{
            require.getWorkType(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.empty();

        }};
        NtsAssert.businessException("Msg_1608", () -> {
                workMonthlySetting.checkForErrors(require);
            }
        );
    }

    @Test
    public void test_WORKTIME_ARE_REQUIRE_NOT_SET() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        workMonthlySetting.setWorkInformation(new WorkInformation(workMonthlySetting.getWorkInformation().getWorkTypeCode().v(),null));
        WorkType workType = new WorkType(CID, workMonthlySetting.getWorkInformation().getWorkTypeCode(), new WorkTypeSymbolicName(""),
                new WorkTypeName(""), new WorkTypeAbbreviationName(""), new WorkTypeMemo(""),
                new DailyWork(), DeprecateClassification.NotDeprecated, CalculateMethod.DO_NOT_GO_TO_WORK
                );
        new Expectations() {{
            require.getWorkType(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);

            require.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.REQUIRED;

        }};
        NtsAssert.businessException("Msg_435", () -> {
                    workMonthlySetting.checkForErrors(require);
                }
        );
    }

    @Test
    public void test_WORKTIME_WAS_DELETE() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        WorkType workType = new WorkType(CID, workMonthlySetting.getWorkInformation().getWorkTypeCode(), new WorkTypeSymbolicName(""),
                new WorkTypeName(""), new WorkTypeAbbreviationName(""), new WorkTypeMemo(""),
                new DailyWork(), DeprecateClassification.NotDeprecated, CalculateMethod.DO_NOT_GO_TO_WORK
        );
        new Expectations() {{
            require.getWorkType(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);

            require.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.REQUIRED;

            require.getWorkTime(workMonthlySetting.getWorkInformation().getWorkTimeCode().v());
            result = Optional.empty();
        }};
        NtsAssert.businessException("Msg_1609", () -> {
                    workMonthlySetting.checkForErrors(require);
                }
        );
    }

    @Test
    public void test_WORKTIME_ARE_SET_WHEN_UNNECESSARY() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        WorkType workType = new WorkType(CID, workMonthlySetting.getWorkInformation().getWorkTypeCode(), new WorkTypeSymbolicName(""),
                new WorkTypeName(""), new WorkTypeAbbreviationName(""), new WorkTypeMemo(""),
                new DailyWork(), DeprecateClassification.NotDeprecated, CalculateMethod.DO_NOT_GO_TO_WORK
        );
        new Expectations() {{
            require.getWorkType(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);

            require.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.NOT_REQUIRED;

        }};
        NtsAssert.businessException("Msg_434", () -> {
                    workMonthlySetting.checkForErrors(require);
                }
        );
    }

    @Test
    public void test_PASSED() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        WorkType workType = new WorkType(CID, workMonthlySetting.getWorkInformation().getWorkTypeCode(), new WorkTypeSymbolicName(""),
                new WorkTypeName(""), new WorkTypeAbbreviationName(""), new WorkTypeMemo(""),
                new DailyWork(), DeprecateClassification.NotDeprecated, CalculateMethod.DO_NOT_GO_TO_WORK
        );
        new Expectations() {{
            require.getWorkType(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);

            require.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.REQUIRED;

            require.getWorkTime(workMonthlySetting.getWorkInformation().getWorkTimeCode().v());
            result = Optional.of(new WorkTimeSetting());
        }};
        workMonthlySetting.checkForErrors(require);
    }

    @Test
    public void getters() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        NtsAssert.invokeGetters(workMonthlySetting);
    }

    @AllArgsConstructor
    class WorkMonthlySettingGetMementoImpl implements WorkMonthlySettingGetMemento{

        private String cid;

        @Override
        public CompanyId getCompanyId() {
            return new CompanyId(cid);
        }

        @Override
        public WorkTypeCode getWorkTypeCode() {
            return new WorkTypeCode("123");
        }

        @Override
        public WorkingCode getWorkingCode() {
            return new WorkingCode("code-1");
        }

        @Override
        public GeneralDate getYmdK() {
            return GeneralDate.today();
        }

        @Override
        public MonthlyPatternCode getMonthlyPatternCode() {
            return new MonthlyPatternCode("COD");
        }
    }
}
