package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionGridColumnWidthTest {
    @Test
    public void testGetters() {
        AnyPeriodCorrectionGridColumnWidth setting = new AnyPeriodCorrectionGridColumnWidth(
                "employeeId",
                new ArrayList<>()
        );

        NtsAssert.invokeGetters(setting);
    }
}
