package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import nts.arc.testing.assertion.NtsAssert;

public class NRHelperCommonTest {

	public static void testEqualHash(Object obj1, Object obj2, Object obj3) {

		assertEquals(obj1, obj2);
		assertNotEquals(obj1, null);

		assertEquals(obj1.hashCode(), obj2.hashCode());
		assertNotEquals(obj1.hashCode(), obj3.hashCode());

		assertEquals(obj1.toString(), obj2.toString());

		assertNotEquals(obj1.toString(), obj3.toString());

		NtsAssert.invokeGetters(obj1);
	}
}
