package nts.uk.ctx.at.function.dom.alarmlist.webmenu;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.*;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.System;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AlarmListWebMenuTest {
    @Test
    public void getters() {
        AlarmListWebMenu domain = new AlarmListWebMenu(
                "00000000001",
                "01",
                new AlarmCheckConditionCode("001"),
                AlarmCategory.DAILY,
                AlarmListCheckType.FixCheck,
                Arrays.asList(
                        new WebMenuInfo(
                                System.TIME_SHEET,
                                new WebMenuCode("001"),
                                MenuClassification.STANDARD
                        )
                )
        );

        NtsAssert.invokeGetters(domain);
    }

    @Test
    public void testCreate_sucessfully(){
        List<WebMenuInfo> webMenuInfos = Collections.singletonList(
                new WebMenuInfo(
                        System.TIME_SHEET,
                        new WebMenuCode("001"),
                        MenuClassification.STANDARD
                )
        );

        AlarmListWebMenu domain = new AlarmListWebMenu(
                "00000000001",
                "01",
                new AlarmCheckConditionCode("001"),
                AlarmCategory.DAILY,
                AlarmListCheckType.FixCheck,
                webMenuInfos
        );

        assertThat(domain.getCompanyID()).isEqualTo("00000000001");
        assertThat(domain.getAlarmCode()).isEqualTo("01");
        assertThat(domain.getAlarmCheckConditionCode().v()).isEqualTo("001");
        assertThat(domain.getAlarmCategory()).isEqualTo(AlarmCategory.DAILY);
        assertThat(domain.getCheckType()).isEqualTo(AlarmListCheckType.FixCheck);
        assertThat(domain.getWebMenuInfoList()).isEqualTo(webMenuInfos);
    }
}
