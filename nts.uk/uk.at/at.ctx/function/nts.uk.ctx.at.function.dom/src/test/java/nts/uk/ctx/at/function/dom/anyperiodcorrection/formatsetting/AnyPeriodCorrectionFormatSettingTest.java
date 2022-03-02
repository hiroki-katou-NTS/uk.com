package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionFormatSettingTest {
    @Test
    public void testGetter() {
        AnyPeriodCorrectionFormatSetting setting = new AnyPeriodCorrectionFormatSetting(
                "code",
                "name",
                new ArrayList<>()
        );

        NtsAssert.invokeGetters(setting);
    }
}
