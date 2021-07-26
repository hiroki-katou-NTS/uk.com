package nts.uk.ctx.exio.dom.input.revise.type.string;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.FetchingPosition;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.Padding;
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
		private static FetchingPosition RANGE_OF_VALUE = new FetchingPosition(new ExternalImportRowNumber(2), new ExternalImportRowNumber(4));
		private static Padding PADDING = new Padding(new ExternalImportRowNumber(10), PaddingMethod.ZERO_BEFORE);
	}
	
	
	@Test
	public void noRevise() {
		val reviser = new StringRevise(false, Optional.empty(), false, Optional.empty());
		
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
	public void specifyRange() {
		val reviser = new StringRevise(true, Optional.of(Dummy.RANGE_OF_VALUE), false, Optional.empty());
		
		new Expectations() {{
			rangeOfValue.extract(Dummy.TARGET);
			result = Dummy.RESULT;
		}};
		
		reviser.revise(Dummy.TARGET);
		
		new Verifications() {{
			rangeOfValue.extract((String)any);
			times = 1;
		}};
		
		new Verifications() {{
			fixedLength.fix((String)any);
			times = 0;
		}};
	}
	
	@Test
	public void fixedLength() {
		val reviser = new StringRevise(false, Optional.empty(), true, Optional.of(Dummy.PADDING));
		
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
