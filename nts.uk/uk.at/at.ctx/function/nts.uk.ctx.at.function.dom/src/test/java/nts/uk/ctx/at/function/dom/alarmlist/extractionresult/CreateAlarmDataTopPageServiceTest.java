package nts.uk.ctx.at.function.dom.alarmlist.extractionresult;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.alarm.AffAtWorkplaceExport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.CreateAlarmDataTopPageService;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class CreateAlarmDataTopPageServiceTest {
    @Injectable
    private CreateAlarmDataTopPageService.Require require;

    /**
     * WkplIdListNotError Empty
     * wkplIdListErrors NotEmpty
     */
    @Test
    public void testCreateAlarmTopPage_WorkplaceIdListNotError_Empty() {
        DeleteInfoAlarmImport deleteInfoAlarmImport = DumData.deleteInfoAlarmImport;
        List<TopPageAlarmImport> alarmListInfo = DumData.alarmListInfos;
        List<AffAtWorkplaceExport> affAtWorkplaceExports = DumData.affAtWorkplaceExports;
        List<String> employeeIds = DumData.employeeIds;

        String companyId = "companyId";

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = companyId;

                require.getWorkplaceId(employeeIds, GeneralDate.today());
                result = affAtWorkplaceExports;

                require.getListEmployeeId("S001", GeneralDate.today());
                result = Arrays.asList("sya001", "sya002", "sya003");

                require.getListEmployeeId("S002", GeneralDate.today());
                result = Arrays.asList("sya004", "sya005");
            }
        };

        NtsAssert.atomTask(() ->
                        CreateAlarmDataTopPageService.create(require, Optional.of(deleteInfoAlarmImport), alarmListInfo),
                any -> require.create(companyId, any.get(), any.get())
        );
    }

    /**
     * WkplIdListNotError NotEmpty
     * wkplIdListErrors NotEmpty
     */
    @Test
    public void testCreateAlarmTopPage_WorkplaceIdListNotError_NotEmpty() {
        DeleteInfoAlarmImport deleteInfoAlarmImport = DumData.deleteInfoAlarmImport2;
        List<TopPageAlarmImport> alarmListInfo = DumData.alarmListInfos2;
        List<AffAtWorkplaceExport> affAtWorkplaceExports = DumData.affAtWorkplaceExports;
        List<String> employeeIds = DumData.employeeIds;

        String companyId = "companyId";

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = companyId;

                require.getWorkplaceId(employeeIds, GeneralDate.today());
                result = affAtWorkplaceExports;

                require.getListEmployeeId("S001", GeneralDate.today());
                result = Arrays.asList("sya001", "sya002", "sya003");
            }
        };

        NtsAssert.atomTask(() -> CreateAlarmDataTopPageService.create(require, Optional.of(deleteInfoAlarmImport), alarmListInfo),
                any -> require.create(companyId, any.get(), any.get())
        );
    }

    /**
     * wkplIdListErrors Empty
     */
    @Test
    public void testCreateAlarmTopPage_wkplIdListErrors_Empty() {
        DeleteInfoAlarmImport deleteInfoAlarmImport = DumData.deleteInfoAlarmImport;
        List<TopPageAlarmImport> alarmListInfo = DumData.alarmListInfos3;
        List<AffAtWorkplaceExport> affAtWorkplaceExports = DumData.affAtWorkplaceExports;
        List<String> employeeIds = Arrays.asList("sya006", "sya001", "sya002", "sya003", "sya004", "sya005");

        String companyId = "companyId";

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = companyId;

                require.getWorkplaceId(employeeIds, GeneralDate.today());
                result = affAtWorkplaceExports;
            }
        };

        NtsAssert.atomTask(() -> CreateAlarmDataTopPageService.create(require, Optional.of(deleteInfoAlarmImport), alarmListInfo),
                any -> require.create(companyId, any.get(), any.get())
        );
    }

    /**
     * wkplIdListErrors NotEmpty
     */
    @Test
    public void testCreateAlarmTopPage_wkplIdListErrors_NotEmpty() {
        DeleteInfoAlarmImport deleteInfoAlarmImport = DumData.deleteInfoAlarmImport2;
        List<TopPageAlarmImport> alarmListInfo = DumData.alarmListInfos2;
        List<AffAtWorkplaceExport> affAtWorkplaceExports = DumData.affAtWorkplaceExports;
        List<String> employeeIds = DumData.employeeIds;

        String companyId = "companyId";

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = companyId;

                require.getWorkplaceId(employeeIds, GeneralDate.today());
                result = affAtWorkplaceExports;

                require.getListEmployeeId("S001", GeneralDate.today());
                result = Arrays.asList("sya001", "sya002", "sya003");
            }
        };

        NtsAssert.atomTask(() -> CreateAlarmDataTopPageService.create(require, Optional.of(deleteInfoAlarmImport), alarmListInfo),
                any -> require.create(companyId, any.get(), any.get())
        );
    }
}
