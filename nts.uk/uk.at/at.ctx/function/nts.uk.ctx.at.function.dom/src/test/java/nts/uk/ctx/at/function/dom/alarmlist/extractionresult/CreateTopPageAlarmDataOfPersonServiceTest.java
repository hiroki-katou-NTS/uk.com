package nts.uk.ctx.at.function.dom.alarmlist.extractionresult;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.CreateTopPageAlarmDataOfPersonService;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class CreateTopPageAlarmDataOfPersonServiceTest {
    @Injectable
    private CreateTopPageAlarmDataOfPersonService.Require require;

    @Test
    public void testCreateTopPageAlarmData(){
        String cID = "CID";
        List<TopPageAlarmImport> alarmListInfos = DumData.alarmListInfos;
        Optional<DeleteInfoAlarmImport> deleteInfo = Optional.of(DumData.deleteInfoAlarmImport);

        NtsAssert.atomTask(() ->
                        CreateTopPageAlarmDataOfPersonService.create(require, cID, alarmListInfos, deleteInfo ),
                any -> require.create(cID, alarmListInfos, deleteInfo)
        );
    }
}
