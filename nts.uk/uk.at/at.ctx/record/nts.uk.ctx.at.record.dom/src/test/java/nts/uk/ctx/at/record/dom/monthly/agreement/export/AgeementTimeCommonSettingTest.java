package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import lombok.val;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSettingsGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JMockit.class)
public class AgeementTimeCommonSettingTest {

    @Test
    public void getters() {
        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.USE,UseClassificationAtr.USE);
        BasicAgreementSettingsGetter agreementSettingsGetter =
                new BasicAgreementSettingsGetter(agreementUnitSetting, Arrays.asList(),Arrays.asList(), new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashMap<>(),Arrays.asList());
        AgeementTimeCommonSetting ageementTimeCommonSetting = new AgeementTimeCommonSetting(agreementSettingsGetter,new HashMap<>());
        NtsAssert.invokeGetters(ageementTimeCommonSetting);
    }

    @Test
    public void getAggrPeriodTest() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.USE,UseClassificationAtr.USE);
        BasicAgreementSettingsGetter agreementSettingsGetter =
                new BasicAgreementSettingsGetter(agreementUnitSetting, Arrays.asList(),Arrays.asList(), new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashMap<>(),Arrays.asList());
        AgeementTimeCommonSetting target = new AgeementTimeCommonSetting(agreementSettingsGetter,new HashMap<>());
        // Mock up
        val setting = new BasicAgreementSetting(new AgreementOneMonth(), null, null, null);
        new MockUp<BasicAgreementSettingsGetter>() {
            @Mock
            public BasicAgreementSetting getBasicSet(
                    String companyId,
                    String employeeId,
                    GeneralDate criteriaDate,
                    WorkingSystem workingSystem) {

                return setting;
            }
        };

        BasicAgreementSetting result = target.getBasicSet("cid","sid", GeneralDate.today(), WorkingSystem.REGULAR_WORK);
        assertThat(result).isEqualToComparingFieldByField(setting);
    }

    @Test
    public void getWorkConditionTest_1() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.USE,UseClassificationAtr.USE);
        BasicAgreementSettingsGetter agreementSettingsGetter =
                new BasicAgreementSettingsGetter(agreementUnitSetting, Arrays.asList(),Arrays.asList(), new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashMap<>(),Arrays.asList());

        Map<String, Map<GeneralDate, WorkingConditionItem>> mapResult = new HashMap<>();
        Map<GeneralDate, WorkingConditionItem> map = new HashMap<>();
        map.put(GeneralDate.today(),new WorkingConditionItem("historyId", ManageAtr.USE,"employee"));
        mapResult.put("employee",map);
        AgeementTimeCommonSetting target = new AgeementTimeCommonSetting(agreementSettingsGetter,mapResult);

        Optional<WorkingConditionItem> result = target.getWorkCondition("sid", GeneralDate.today());
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void getWorkConditionTest_2() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.USE,UseClassificationAtr.USE);
        BasicAgreementSettingsGetter agreementSettingsGetter =
                new BasicAgreementSettingsGetter(agreementUnitSetting, Arrays.asList(),Arrays.asList(), new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashMap<>(),Arrays.asList());
        Map<String, Map<GeneralDate, WorkingConditionItem>> mapResult = new HashMap<>();
        Map<GeneralDate, WorkingConditionItem> map = new HashMap<>();
        map.put(GeneralDate.today(),new WorkingConditionItem("historyId", ManageAtr.USE,"sid"));
        mapResult.put("sid",map);

        AgeementTimeCommonSetting target = new AgeementTimeCommonSetting(agreementSettingsGetter,mapResult);

        Optional<WorkingConditionItem> result = target.getWorkCondition("sid", GeneralDate.today());
        assertThat(result).isEqualTo(Optional.of(new WorkingConditionItem("historyId", ManageAtr.USE,"sid")));
    }

}
