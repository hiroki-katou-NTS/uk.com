package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author ThanhNX
 *
 *         外出打刻の変換Test
 */
public class OutPlaceConvertTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getters() {

		OutPlaceConvert target = new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty());
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void hashCodeEqual() {
		OutPlaceConvert target1 = new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty());

		OutPlaceConvert target2 = new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty());

		OutPlaceConvert target3 = new OutPlaceConvert(NotUseAtr.USE, Optional.empty());

		NRHelperCommonTest.testEqualHash(target1, target2, target3);

	}

}
