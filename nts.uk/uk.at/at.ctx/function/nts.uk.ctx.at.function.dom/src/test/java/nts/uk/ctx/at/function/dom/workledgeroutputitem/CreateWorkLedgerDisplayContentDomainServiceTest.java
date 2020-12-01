package nts.uk.ctx.at.function.dom.workledgeroutputitem;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DumDataTest;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.CreateDisplayContentWorkStatusDService;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DumData;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(JMockit.class)
public class CreateWorkLedgerDisplayContentDomainServiceTest {
    @Injectable
    CreateWorkLedgerDisplayContentDomainService.Require require;

    private final DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1));
    private static final List<EmployeeInfor> employeeInfors = DumData.employeeInfors;
    private static final List<EmployeeInfor> employeeInforFail = DumData.employeeInforFail;
    private static final List<WorkPlaceInfo> workPlaceInfo = DumData.workPlaceInfo;
    private static final List<WorkPlaceInfo> workPlaceInfoFail = DumData.workPlaceInfoFail;
    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");
    private final String iD = "iD";
    private final String empId = "employeeId";
    private final SettingClassificationCommon settingCategoryStandard = SettingClassificationCommon.STANDARD_SELECTION;
    private final WorkLedgerOutputItem domainsStandard = WorkLedgerOutputItem.create( iD,empId,code, name,settingCategoryStandard);


    /**
     * TEST CASE :Throw exception
     * - Fail  employee info
     */
    @Test
    public void test_01() {
        val listSid = employeeInforFail.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId05",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};
        val baseDate = datePeriod.end();
        val closure = DumDataTest.createClosure();
        val listCl = Arrays.asList(
                new ClosureDateEmployment("eplId01", "employeeCode1", "employeeName1", closure),
                new ClosureDateEmployment("sid2", "employeeCode2", "employeeName2", closure));

        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod,listSid);
                result = listEmployeeStatus;

                GetClosureDateEmploymentDomainService.get(require,baseDate,listSid);
                result = Collections.emptyList();


            }
        };

        NtsAssert.businessException("Msg_1926", () -> {
            CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfors,
                    domainsStandard, workPlaceInfo);
        });
    }
}
