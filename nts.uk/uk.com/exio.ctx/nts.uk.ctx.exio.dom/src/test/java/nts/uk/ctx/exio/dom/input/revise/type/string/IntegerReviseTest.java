package nts.uk.ctx.exio.dom.input.revise.type.string;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.RangeOfValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.integer.IntegerRevise;

public class IntegerReviseTest {
	
	@Mocked
	RangeOfValue rangeOfValue;
	
	private static class Dummy{
		private static String TARGET = "12345";
		private static Long RESULT = (long) 12345;
		private static RangeOfValue RANGE_OF_VALUE = new RangeOfValue(new ExternalImportRowNumber(2), new ExternalImportRowNumber(4));
	}
	
	@Test
	public void noRevise() {
		val reviser = new IntegerRevise(false, Optional.empty());
		
		val result = reviser.revise(Dummy.TARGET);
		
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.getRevisedvalue().get()).isEqualTo(Dummy.RESULT);
		
		new Verifications() {{
			rangeOfValue.extract((String)any);
			times = 0;
		}};
	}
	
	@Test
	public void specifyRange() {
		val reviser = new IntegerRevise(true, Optional.of(Dummy.RANGE_OF_VALUE));
		
		new Expectations() {{
			rangeOfValue.extract(Dummy.TARGET);
			result = Dummy.RESULT;
		}};
		
		val result = reviser.revise(Dummy.TARGET);
		
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.getRevisedvalue().get()).isEqualTo(Dummy.RESULT);
		
		new Verifications() {{
			rangeOfValue.extract((String)any);
			times = 1;
		}};
	}
}
