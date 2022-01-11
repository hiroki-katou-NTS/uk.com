package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.AffiliationInforOfDailyPerforImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.OuenWorkTimeOfDailyImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.OuenWorkTimeSheetOfDailyImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataImport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class SupportWorkAggregationSettingTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        SupportWorkAggregationSetting setting = new SupportWorkAggregationSetting(
                SupportAggregationUnit.WORK_LOCATION,
                Optional.of(WorkplaceSupportJudgmentMethod.SUPPORT_ALL_BUT_WORKPLACE),
                Optional.empty()
        );

        NtsAssert.invokeGetters(setting);
    }

    /**
     * unit = WORKPLACE
     * judgment method = SUPPORT_ALL_BUT_WORKPLACE
     * SupportWorkDetails.supportWork = false
     */
    @Test
    public void testCreateSupportWorkDetails1() {
        String companyId = "000000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";

        SupportWorkAggregationSetting setting = new SupportWorkAggregationSetting(
                SupportAggregationUnit.WORKPLACE,
                Optional.of(WorkplaceSupportJudgmentMethod.SUPPORT_ALL_BUT_WORKPLACE),
                Optional.empty()
        );

        List<SupportWorkDetails> details = setting.createSupportWorkDetails(
                require,
                companyId,
                Optional.empty(),
                new SupportWorkDataImport(
                        Arrays.asList(new OuenWorkTimeOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                new ArrayList<>()
                        )),
                        Arrays.asList(new OuenWorkTimeSheetOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                Arrays.asList(new OuenWorkTimeSheetOfDailyAttendance(
                                        new SupportFrameNo(1),
                                        WorkContent.create(
                                                WorkplaceOfWorkEachOuen.create(
                                                        new WorkplaceId(workplaceId),
                                                        null
                                                ),
                                                Optional.empty(),
                                                Optional.empty()
                                        ),
                                        null,
                                        Optional.empty()
                                ))
                        )),
                        Arrays.asList(new AffiliationInforOfDailyPerforImport(
                                employeeId,
                                GeneralDate.today(),
                                new AffiliationInforOfDailyAttd(
                                        null, // employmentCode
                                        null, // jobTitleId
                                        workplaceId, // workplaceId
                                        null, // classificationCode
                                        Optional.empty(), // businessTypeCode
                                        Optional.empty() //bonusPaySettingCode
                                )
                        ))
                ),
                Arrays.asList(1, 2, 3)
        );

        assertThat(details.size()).isEqualTo(1);
        assertThat(details.get(0).isSupportWork()).isEqualTo(false);
    }

    /**
     * unit = WORKPLACE
     * judgment method = SUPPORT_ALL_BUT_WORKPLACE
     * SupportWorkDetails.supportWork = true
     */
    @Test
    public void testCreateSupportWorkDetails2() {
        String companyId = "000000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";
        String workplaceId2 = "workplace-id-0002";

        SupportWorkAggregationSetting setting = new SupportWorkAggregationSetting(
                SupportAggregationUnit.WORKPLACE,
                Optional.of(WorkplaceSupportJudgmentMethod.SUPPORT_ALL_BUT_WORKPLACE),
                Optional.empty()
        );

        List<SupportWorkDetails> details = setting.createSupportWorkDetails(
                require,
                companyId,
                Optional.empty(),
                new SupportWorkDataImport(
                        Arrays.asList(new OuenWorkTimeOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                new ArrayList<>()
                        )),
                        Arrays.asList(new OuenWorkTimeSheetOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                Arrays.asList(new OuenWorkTimeSheetOfDailyAttendance(
                                        new SupportFrameNo(1),
                                        WorkContent.create(
                                                WorkplaceOfWorkEachOuen.create(
                                                        new WorkplaceId(workplaceId2),
                                                        null
                                                ),
                                                Optional.empty(),
                                                Optional.empty()
                                        ),
                                        null,
                                        Optional.empty()
                                ))
                        )),
                        Arrays.asList(new AffiliationInforOfDailyPerforImport(
                                employeeId,
                                GeneralDate.today(),
                                new AffiliationInforOfDailyAttd(
                                        null, // employmentCode
                                        null, // jobTitleId
                                        workplaceId, // workplaceId
                                        null, // classificationCode
                                        Optional.empty(), // businessTypeCode
                                        Optional.empty() //bonusPaySettingCode
                                )
                        ))
                ),
                Arrays.asList(1, 2, 3)
        );

        assertThat(details.size()).isEqualTo(1);
        assertThat(details.get(0).isSupportWork()).isEqualTo(true);
    }

    /**
     * unit = WORKPLACE
     * judgment method = SUPPORT_JUDGMENT_AT_WORKPLACE_LEVEL
     * SupportWorkDetails.supportWork = false
     */
    @Test
    public void testCreateSupportWorkDetails3() {
        String companyId = "000000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";

        SupportWorkAggregationSetting setting = new SupportWorkAggregationSetting(
                SupportAggregationUnit.WORKPLACE,
                Optional.of(WorkplaceSupportJudgmentMethod.SUPPORT_JUDGMENT_AT_WORKPLACE_LEVEL),
                Optional.of(new StandardWorkplaceHierarchy(2))
        );

        new Expectations() {{
            require.getWorkplaceInfos(companyId, Arrays.asList(workplaceId, workplaceId), GeneralDate.today());
            result = Arrays.asList(new WorkPlaceInforExport(
                    workplaceId,
                    "001001001",
                    "",
                    "",
                    "",
                    "",
                    ""
            ));
        }};

        List<SupportWorkDetails> details = setting.createSupportWorkDetails(
                require,
                companyId,
                Optional.empty(),
                new SupportWorkDataImport(
                        Arrays.asList(new OuenWorkTimeOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                new ArrayList<>()
                        )),
                        Arrays.asList(new OuenWorkTimeSheetOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                Arrays.asList(new OuenWorkTimeSheetOfDailyAttendance(
                                        new SupportFrameNo(1),
                                        WorkContent.create(
                                                WorkplaceOfWorkEachOuen.create(
                                                        new WorkplaceId(workplaceId),
                                                        null
                                                ),
                                                Optional.empty(),
                                                Optional.empty()
                                        ),
                                        null,
                                        Optional.empty()
                                ))
                        )),
                        Arrays.asList(new AffiliationInforOfDailyPerforImport(
                                employeeId,
                                GeneralDate.today(),
                                new AffiliationInforOfDailyAttd(
                                        null, // employmentCode
                                        null, // jobTitleId
                                        workplaceId, // workplaceId
                                        null, // classificationCode
                                        Optional.empty(), // businessTypeCode
                                        Optional.empty() //bonusPaySettingCode
                                )
                        ))
                ),
                Arrays.asList(1, 2, 3)
        );

        assertThat(details.size()).isEqualTo(1);
        assertThat(details.get(0).isSupportWork()).isEqualTo(false);
    }

    /**
     * unit = WORKPLACE
     * judgment method = SUPPORT_JUDGMENT_AT_WORKPLACE_LEVEL
     * SupportWorkDetails.supportWork = true
     */
    @Test
    public void testCreateSupportWorkDetails4() {
        String companyId = "000000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";
        String workplaceId2 = "workplace-id-0002";

        SupportWorkAggregationSetting setting = new SupportWorkAggregationSetting(
                SupportAggregationUnit.WORKPLACE,
                Optional.of(WorkplaceSupportJudgmentMethod.SUPPORT_JUDGMENT_AT_WORKPLACE_LEVEL),
                Optional.of(new StandardWorkplaceHierarchy(2))
        );

        new Expectations() {{
            require.getWorkplaceInfos(companyId, Arrays.asList(workplaceId, workplaceId2), GeneralDate.today());
            result = Arrays.asList(new WorkPlaceInforExport(
                    workplaceId,
                    "001001001",
                    "",
                    "",
                    "",
                    "",
                    ""
            ), new WorkPlaceInforExport(
                    workplaceId2,
                    "002001001",
                    "",
                    "",
                    "",
                    "",
                    ""
            ));
        }};

        List<SupportWorkDetails> details = setting.createSupportWorkDetails(
                require,
                companyId,
                Optional.empty(),
                new SupportWorkDataImport(
                        Arrays.asList(new OuenWorkTimeOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                new ArrayList<>()
                        )),
                        Arrays.asList(new OuenWorkTimeSheetOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                Arrays.asList(new OuenWorkTimeSheetOfDailyAttendance(
                                        new SupportFrameNo(1),
                                        WorkContent.create(
                                                WorkplaceOfWorkEachOuen.create(
                                                        new WorkplaceId(workplaceId2),
                                                        null
                                                ),
                                                Optional.empty(),
                                                Optional.empty()
                                        ),
                                        null,
                                        Optional.empty()
                                ))
                        )),
                        Arrays.asList(new AffiliationInforOfDailyPerforImport(
                                employeeId,
                                GeneralDate.today(),
                                new AffiliationInforOfDailyAttd(
                                        null, // employmentCode
                                        null, // jobTitleId
                                        workplaceId, // workplaceId
                                        null, // classificationCode
                                        Optional.empty(), // businessTypeCode
                                        Optional.empty() //bonusPaySettingCode
                                )
                        ))
                ),
                Arrays.asList(1, 2, 3)
        );

        assertThat(details.size()).isEqualTo(1);
        assertThat(details.get(0).isSupportWork()).isEqualTo(true);
    }

    /**
     * unit = WORK_LOCATION
     * SupportWorkDetails.supportWork = false
     */
    @Test
    public void testCreateSupportWorkDetails5() {
        String companyId = "000000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";

        SupportWorkAggregationSetting setting = new SupportWorkAggregationSetting(
                SupportAggregationUnit.WORK_LOCATION,
                Optional.empty(),
                Optional.empty()
        );

        List<SupportWorkDetails> details = setting.createSupportWorkDetails(
                require,
                companyId,
                Optional.empty(),
                new SupportWorkDataImport(
                        Arrays.asList(new OuenWorkTimeOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                new ArrayList<>()
                        )),
                        Arrays.asList(new OuenWorkTimeSheetOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                Arrays.asList(new OuenWorkTimeSheetOfDailyAttendance(
                                        new SupportFrameNo(1),
                                        WorkContent.create(
                                                WorkplaceOfWorkEachOuen.create(
                                                        new WorkplaceId(workplaceId),
                                                        new WorkLocationCD(workplaceId)
                                                ),
                                                Optional.empty(),
                                                Optional.empty()
                                        ),
                                        null,
                                        Optional.empty()
                                ))
                        )),
                        Arrays.asList(new AffiliationInforOfDailyPerforImport(
                                employeeId,
                                GeneralDate.today(),
                                new AffiliationInforOfDailyAttd(
                                        null, // employmentCode
                                        null, // jobTitleId
                                        workplaceId, // workplaceId
                                        null, // classificationCode
                                        Optional.empty(), // businessTypeCode
                                        Optional.empty() //bonusPaySettingCode
                                )
                        ))
                ),
                Arrays.asList(1, 2, 3)
        );

        assertThat(details.size()).isEqualTo(1);
        assertThat(details.get(0).isSupportWork()).isEqualTo(false);
    }

    /**
     * unit = WORK_LOCATION
     * SupportWorkDetails.supportWork = true
     */
    @Test
    public void testCreateSupportWorkDetails6() {
        String companyId = "000000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";

        SupportWorkAggregationSetting setting = new SupportWorkAggregationSetting(
                SupportAggregationUnit.WORK_LOCATION,
                Optional.empty(),
                Optional.empty()
        );

        List<SupportWorkDetails> details = setting.createSupportWorkDetails(
                require,
                companyId,
                Optional.empty(),
                new SupportWorkDataImport(
                        Arrays.asList(new OuenWorkTimeOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                new ArrayList<>()
                        )),
                        Arrays.asList(new OuenWorkTimeSheetOfDailyImport(
                                employeeId,
                                GeneralDate.today(),
                                Arrays.asList(new OuenWorkTimeSheetOfDailyAttendance(
                                        new SupportFrameNo(1),
                                        WorkContent.create(
                                                WorkplaceOfWorkEachOuen.create(
                                                        new WorkplaceId(workplaceId),
                                                        new WorkLocationCD("0001")
                                                ),
                                                Optional.empty(),
                                                Optional.empty()
                                        ),
                                        null,
                                        Optional.empty()
                                ))
                        )),
                        Arrays.asList(new AffiliationInforOfDailyPerforImport(
                                employeeId,
                                GeneralDate.today(),
                                new AffiliationInforOfDailyAttd(
                                        null, // employmentCode
                                        null, // jobTitleId
                                        workplaceId, // workplaceId
                                        null, // classificationCode
                                        Optional.empty(), // businessTypeCode
                                        Optional.empty() //bonusPaySettingCode
                                )
                        ))
                ),
                Arrays.asList(1, 2, 3)
        );

        assertThat(details.size()).isEqualTo(1);
        assertThat(details.get(0).isSupportWork()).isEqualTo(true);
    }
}
