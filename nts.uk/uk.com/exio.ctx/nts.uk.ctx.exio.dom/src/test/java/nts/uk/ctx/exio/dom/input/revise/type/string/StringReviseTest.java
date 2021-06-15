package nts.uk.ctx.exio.dom.input.revise.type.string;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;

public class StringReviseTest {
	
	@Test
	public void noRevise() {
		String target = "value";
		val reviser = new StringRevise(false, Optional.empty(), false, Optional.empty());
		
		val result = reviser.revise(target);
		assertThat(result.isSuccess()).isEqualTo(true);
		assertThat(result.getRevisedvalue().toString()).isEqualTo(target);
	}

}
