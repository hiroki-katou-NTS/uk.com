package nts.uk.ctx.exio.dom.input.revise.type.string;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FetchingPosition;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.Padding;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.PaddingLength;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.PaddingMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.StringRevise;

public class StringReviseTest {
	
	@Mocked
	FetchingPosition rangeOfValue;
	
	@Mocked
	Padding fixedLength;
	
	private static class Dummy{
		private static String TARGET = "value";
		private static String RESULT = "value";
		private static Padding PADDING = new Padding(new PaddingLength(10), PaddingMethod.ZERO_BEFORE);
	}
	
	
	@Test
	public void noRevise() {
		val reviser = new StringRevise(false, Optional.empty(), Optional.empty());
		
		reviser.revise(Dummy.TARGET);
		
		new Verifications() {{
			rangeOfValue.extract((String)any);
			times = 0;
		}};
		
		new Verifications() {{
			fixedLength.fix((String)any);
			times = 0;
		}};
	}
		
	@Test
	public void fixedLength() {
		val reviser = new StringRevise(true, Optional.of(Dummy.PADDING), Optional.empty());
		
		new Expectations() {{
			fixedLength.fix(Dummy.TARGET);
			result = Dummy.RESULT;
		}};
		
		reviser.revise(Dummy.TARGET);
		
		new Verifications() {{
			rangeOfValue.extract((String)any);
			times = 0;
		}};
		
		new Verifications() {{
			fixedLength.fix((String)any);
			times = 1;
		}};
	}
}
