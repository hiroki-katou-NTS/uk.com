package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;


import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(JMockit.class)
public class CheckExistenceMasterDomainServiceTest {

    @Injectable
    CheckExistenceMasterDomainService.Require require;

    /**
     * case 作業は削除されている: Msg_2065
     */
    @Test
    public void testWorkHasBeenDeletedIsTrue() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getTask("cid", HelperCheckExist.taskFrameNo);
                result = HelperCheckExist.createDomain(HelperCheckExist.childWorkList);


            }
        };
        NtsAssert.businessException("Msg_2065",
                () -> CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, HelperCheckExist.taskFrameNo,
                        HelperCheckExist.codeList));
    }

    /**
     * case: No exit
     */

    @Test
    public void testWorkHasBeenDeletedIsFalse() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getTask("cid", HelperCheckExist.taskFrameNo);
                result = HelperCheckExist.createDomain(HelperCheckExist.codeList);


            }
        };

        CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, HelperCheckExist.taskFrameNo,
                HelperCheckExist.codeList);
    }
    
}
