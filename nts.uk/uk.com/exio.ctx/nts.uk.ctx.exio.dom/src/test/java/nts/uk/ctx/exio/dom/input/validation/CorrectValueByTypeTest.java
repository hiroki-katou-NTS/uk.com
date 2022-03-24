package nts.uk.ctx.exio.dom.input.validation;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.bs.company.dom.company.CompanyCode;
import nts.uk.ctx.exio.dom.input.DataItem;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CorrectValueByTypeTest {

    @Test
    public void correctPvClass() {

        DataItem actual = NtsAssert.Invoke.staticMethod(
                CorrectValueByType.class,
                "correct",
                new Class[] { Class.class, DataItem.class },
                CompanyCode.class,
                DataItem.of(123, "1"));

        Assertions.assertThat(actual.getString()).isEqualTo("0001");
    }
}