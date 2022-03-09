package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;

public class TimeBase60DelimiterTest {

	@Test
	public void NONE_0_01() {
		val either = TimeBase60Delimiter.NONE.toMinutes("001");
		assertThat(either.getRight()).isEqualTo(1);
	}
	
	@Test
	public void NONE_minus_0_01() {
		val either = TimeBase60Delimiter.NONE.toMinutes("-001");
		assertThat(either.getRight()).isEqualTo(-1);
	}
	
	@Test
	public void NONE_1_01() {
		val either = TimeBase60Delimiter.NONE.toMinutes("101");
		assertThat(either.getRight()).isEqualTo(60 + 1);
	}
	
	@Test
	public void NONE_100_01() {
		val either = TimeBase60Delimiter.NONE.toMinutes("10001");
		assertThat(either.getRight()).isEqualTo(100 * 60 + 1);
	}

	@Test
	public void COLON_0_01() {
		val either = TimeBase60Delimiter.COLON.toMinutes("0:01");
		assertThat(either.getRight()).isEqualTo(1);
	}
	
	@Test
	public void COLON_minus_0_01() {
		val either = TimeBase60Delimiter.COLON.toMinutes("-0:01");
		assertThat(either.getRight()).isEqualTo(-1);
	}

}
