package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class IndependentCanonicalizationTest {

	@Test
	public void test() {
		
		List<String> a = Arrays.asList("A", "B");
		List<String> b = Arrays.asList("A", "B");

		assertThat(a.equals(b)).isTrue();
		assertThat(a.hashCode() == b.hashCode()).isTrue();
		
	}

}
