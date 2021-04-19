package nts.uk.ctx.at.function.dom.alarmlist.extractionresult;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternName;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.AlarmEmployeeList;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.AlarmExtractInfoResult;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.PersistenceAlarmListExtractResult;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
