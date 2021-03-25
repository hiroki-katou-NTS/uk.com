package nts.uk.cnv.dom.td.feature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import nts.gul.text.IdentifierUtil;

public class FeatureTest {
	@Mocked IdentifierUtil identifierUtil;
	
	class Dummy {
		private static final String UUID = "UUIDuuidUUIDuuidUUIDuuidUUID";
		private static final String name = "HogeName";
		private static final String description = "HogeDesc";
	}

	@Test
	public void 新規() {
		Feature feature = Feature.newFeature(Dummy.name, Dummy.description);
		

		new Expectations() {{
			IdentifierUtil.randomUniqueId();
			result = Dummy.UUID;
		}};

		assertThat(feature.getFeatureId()).isEqualTo(Dummy.UUID);
		assertThat(feature.getName()).isEqualTo(Dummy.name);
		assertThat(feature.getDescription()).isEqualTo(Dummy.description);
	}

}
