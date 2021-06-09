package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.dailyresult.service.JudgingStatusDomainService;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.shr.com.i18n.TextResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class MasterNameInformationTest {
    @Injectable
    private TextResource textResource;

    private final List<WorkplaceInfor> workplaceInfos = Helper.MasterNameInfo.workplaceInfos(false);
    private final List<WorkplaceInfor> workplaceInfos_NameNull = Helper.MasterNameInfo.workplaceInfos(true);
    private final List<EmployeeInfoImport> empInfos = Helper.MasterNameInfo.empInfos;
    private final List<TaskImport> tasks = Helper.MasterNameInfo.tasks;

    @Test
    public void getters(){
        MasterNameInformation target = new MasterNameInformation(workplaceInfos, workplaceInfos, empInfos, tasks, tasks, tasks, tasks, tasks);
        NtsAssert.invokeGetters(target);
    }

    /**
     * code = 001 => equals
     * SummaryItemType = 0 (AFFILIATION_WORKPLACE)
     * workplaceName not null
     */
    @Test
    public void getDisplayInfo_equalsCode_typeIsAffWkp(){
        MasterNameInformation obj = new MasterNameInformation(workplaceInfos, null, null, null, null, null, null, null);
        val target = new DisplayInformation("wkpCode01", "wkpName1");
        val actual = obj.getDisplayInfo("001", SummaryItemType.AFFILIATION_WORKPLACE);
        assertThat(target.getCode()).isEqualTo(actual.getCode());
        assertThat(target.getName()).isEqualTo(actual.getName());
    }

    /**
     * code = 001 => equals
     * SummaryItemType = 0 (AFFILIATION_WORKPLACE)
     * workplaceName null
     */
    @Test
    public void getDisplayInfo_equalsCode_typeIsAffWkp_nameIsNull() {
        MasterNameInformation obj = new MasterNameInformation(workplaceInfos_NameNull, null, null, null, null, null, null, null);
        val target = new DisplayInformation("wkpCode01", "wkpName1");
        val actual = obj.getDisplayInfo("001", SummaryItemType.AFFILIATION_WORKPLACE);
        assertThat(target.getCode()).isEqualTo(actual.getCode());
        assertThat(target.getName()).isEqualTo(actual.getName());
    }

    /**
     * code = 002 => not equals
     * SummaryItemType = 0 (AFFILIATION_WORKPLACE)
     */
    @Test
    public void getDisplayInfo_notEqualsCode_typeIsAffWkp(){
        MasterNameInformation obj = new MasterNameInformation(workplaceInfos, null, null, null, null, null, null, null);
        val target = new DisplayInformation("マスタ未登録", "マスタ未登録");

        new Expectations() {
            {
                textResource.localize("KHA003_101");
                result = "マスタ未登録";
            }
        };
        val actual = obj.getDisplayInfo("002", SummaryItemType.AFFILIATION_WORKPLACE);

        assertThat(target.getCode()).isEqualTo(actual.getCode());
        assertThat(target.getName()).isEqualTo(actual.getName());
    }
}
