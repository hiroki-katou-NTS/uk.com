package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author ThanhNX
 *
 *         打刻区分を変換Test
 */
public class ConvertEmbossCategoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getters() {

		ConvertEmbossCategory target = new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void hashCodeEqual() {
		ConvertEmbossCategory target1 = new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);

		ConvertEmbossCategory target2 = new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);

		ConvertEmbossCategory target3 = new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.USE);

		NRHelperCommonTest.testEqualHash(target1, target2, target3);

	}

}
