package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionRegulationFormatTest {
    @Test
    public void testGetter() {
        AnyPeriodCorrectionDefaultFormat setting = new AnyPeriodCorrectionDefaultFormat("code");
        NtsAssert.invokeGetters(setting);
    }
}
