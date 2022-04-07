package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.util.Either;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;

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

	@Test
	public void NOTHING_COLON_error() {
		Either<ErrorMessage, Integer> result = NtsAssert.Invoke.privateMethod(
				TimeBase60Delimiter.COLON,
				"convertDelimiter",
				"08");

		assertThat(result.getLeft()).isEqualTo(new ErrorMessage("時間の区切り文字が正しく含まれていません。"));
	}

	@Test
	public void TWO_COLON_OK() {
		Either<ErrorMessage, Integer> result = NtsAssert.Invoke.privateMethod(
				TimeBase60Delimiter.COLON,
				"convertDelimiter",
				"08:30:35");

		assertThat(result.getRight()).isEqualTo(510);
	}

	@Test
	public void OVER_TWO_COLON_OK() {
		Either<ErrorMessage, Integer> result = NtsAssert.Invoke.privateMethod(
				TimeBase60Delimiter.COLON,
				"convertDelimiter",
				"08:30:35:44");

		assertThat(result.getRight()).isEqualTo(510);
	}

}
