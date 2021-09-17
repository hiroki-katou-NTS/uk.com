package nts.uk.ctx.exio.dom.input.revise.type.string;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FetchingPosition;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.CodeConvertDetail;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.integer.IntegerRevise;

public class IntegerReviseTest {
	
	@Mocked
	FetchingPosition rangeOfValue;
	
	private static class Dummy{
		private static String TARGET = "12345";
		private static Long RESULT = (long) 12345;
		private static ExternalImportCodeConvert CODE_CONVERT =
				new ExternalImportCodeConvert(false, Arrays.asList(
						new CodeConvertDetail("0", "1"),
						new CodeConvertDetail("1", "2")
				));
	}
	
	@Test
	public void noRevise() {
		val reviser = new IntegerRevise(Optional.empty());
		
		val result = reviser.revise(Dummy.TARGET);
		
		result.ifLeft(err -> assertThat(err).isNull());
		result.ifRight(v -> assertThat(v).isEqualTo(Dummy.RESULT));
		
		new Verifications() {{
			rangeOfValue.extract((String)any);
			times = 0;
		}};
	}
	
	@Test
	public void specifyRange() {
		val reviser = new IntegerRevise(Optional.of(Dummy.CODE_CONVERT));
		
		new Expectations() {{
			rangeOfValue.extract(Dummy.TARGET);
			result = Dummy.RESULT;
		}};
		
		val result = reviser.revise(Dummy.TARGET);
		
		result.ifLeft(err -> assertThat(err).isNull());
		result.ifRight(v -> assertThat(v).isEqualTo(Dummy.RESULT));
		
		new Verifications() {{
			rangeOfValue.extract((String)any);
			times = 1;
		}};
	}
}
