package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternName;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class PersistenceAlarmListExtractResultTest {
    @Test
    public void getters() {
        PersistenceAlarmListExtractResult domain = DumData.dumDomain;
        NtsAssert.invokeGetters(domain);
    }

    @Test
    public void testCreate_sucessfully() {
        PersistenceAlarmListExtractResult result =
                new PersistenceAlarmListExtractResult(
                        new AlarmPatternCode("01"),
                        new AlarmPatternName("日々の勤怠チェック"),
                        DumData.alarmListExtractResults,
                        "000000000001",
                        "Z"
                );

        assertThat(result.getAlarmPatternCode().v()).isEqualTo("01");
        assertThat(result.getAlarmPatternName().v()).isEqualTo("日々の勤怠チェック");
        assertThat(result.getAlarmListExtractResults()).isEqualTo(DumData.alarmListExtractResults);
        assertThat(result.getCompanyID()).isEqualTo("000000000001");
        assertThat(result.getAutoRunCode()).isEqualTo("Z");
    }
}