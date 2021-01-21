package nts.uk.ctx.at.schedule.dom.shift.pattern.service;

import lombok.AllArgsConstructor;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkMonthlySettingServiceTest {

    @Injectable
    private WorkMonthlySettingService.Require require;

    @Injectable
    private final String CID = "000-001";
    private final String WMID = "abc";
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void test_error() {
        WorkMonthlySetting workMonthlySetting = new WorkMonthlySetting(CID, WMID, GeneralDate.today(), new WorkInformation("123", "1234"));
        new Expectations(workMonthlySetting){
            {workMonthlySetting.checkForErrors(require);
             result = new BusinessException("Msg_1608");
            }
        };
        NtsAssert.businessException("Msg_1608", () -> {
                    WorkMonthlySettingService
                            .register(require, workMonthlySetting, true);
                }
        );
    }

    @Test
    public void test_non_error_condition_non_update() {
        WorkMonthlySetting workMonthlySetting = new WorkMonthlySetting(CID, WMID, GeneralDate.today(), new WorkInformation("123", "1234"));
        new Expectations(WorkMonthlySetting.class) {{
            workMonthlySetting.checkForErrors(require);
            require.checkRegister(workMonthlySetting.getCompanyId().v(), workMonthlySetting.getMonthlyPatternCode().v(), workMonthlySetting.getYmdk());
            result = true;

        }};
        assertThat(WorkMonthlySettingService.register(require, workMonthlySetting, false)).isEqualTo(Optional.empty());
    }

    @Test
    public void test_non_error_condition_update() {
        WorkMonthlySetting workMonthlySetting = new WorkMonthlySetting(CID, WMID, GeneralDate.today(), new WorkInformation("123", "1234"));
        new Expectations(WorkMonthlySetting.class) {{
            workMonthlySetting.checkForErrors(require);
            require.checkRegister(workMonthlySetting.getCompanyId().v(), workMonthlySetting.getMonthlyPatternCode().v(), workMonthlySetting.getYmdk());
            result = true;
        }};
        NtsAssert.atomTask(() -> WorkMonthlySettingService
                        .register(require, workMonthlySetting, true).get(),
                any -> require.update(any.get())
        );
    }

    @Test
    public void test_non_error_condition_add() {
        WorkMonthlySetting workMonthlySetting = new WorkMonthlySetting(CID, WMID, GeneralDate.today(), new WorkInformation("123", "1234"));

        new Expectations(WorkMonthlySetting.class) {{
            workMonthlySetting.checkForErrors(require);
            require.checkRegister(workMonthlySetting.getCompanyId().v(), workMonthlySetting.getMonthlyPatternCode().v(), workMonthlySetting.getYmdk());
            result = false;
        }};

        NtsAssert.atomTask(() -> WorkMonthlySettingService
                        .register(require, workMonthlySetting, true).get(),
                any -> require.add(any.get())
        );
    }

}

