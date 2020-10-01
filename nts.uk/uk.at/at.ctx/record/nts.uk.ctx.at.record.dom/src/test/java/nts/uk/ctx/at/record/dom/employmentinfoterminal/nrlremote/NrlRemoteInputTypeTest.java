package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NrlRemoteInputTypeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testValueOfNull() {

		NrlRemoteInputType actualResult = NrlRemoteInputType.valueOf((Integer) null);
		assertThat(actualResult).isEqualTo(null);

		NrlRemoteInputType actualResult2 = NrlRemoteInputType.valueOf(10);
		assertThat(actualResult2).isEqualTo(null);

	}

	@Test
	public void testValueOf() {

		NrlRemoteInputType actualResult = NrlRemoteInputType.valueOf((Integer) 0);
		assertThat(actualResult).isEqualTo(NrlRemoteInputType.NUM);

	}

	@Test
	public void testValueInputTypeOfNull() {

		NrlRemoteInputType actualResult = NrlRemoteInputType.valueInputTypeOf(null);
		assertThat(actualResult).isEqualTo(null);

		NrlRemoteInputType actualResult2 = NrlRemoteInputType.valueInputTypeOf("text");
		assertThat(actualResult2).isEqualTo(null);

	}

	@Test
	public void testValueInputTypeOf() {

		NrlRemoteInputType actualResult = NrlRemoteInputType.valueInputTypeOf("num_str");
		assertThat(actualResult).isEqualTo(NrlRemoteInputType.NUM_STR);

	}

}
