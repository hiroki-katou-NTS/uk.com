package nts.uk.cnv.dom.td.event.order;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.DevelopmentState;

public class OrderServiceTest {
	@Mocked OrderService orderService;

	static class Dummy {
		private static String featureId = "featurefeature";
		private static String tableId = "tabletable";
		private static String eventName = "eventevent";
		private static String userName = "useruser";
		private static List<String> tgtAlters = new ArrayList<String>(Arrays.asList("AlterId_1", "AlterId_2", "AlterId_3"));
		private static AlterationSummary alterSmmary = new AlterationSummary("AlterId_1", GeneralDateTime.now(), "AlterId_1", DevelopmentState.NOT_ORDERING, new AlterationMetaData("", ""), "AlterId_2");
		private static List<AlterationSummary> alterSmmarys = new ArrayList<AlterationSummary>(Arrays.asList(alterSmmary, alterSmmary, alterSmmary));
	}

	@Test
	public void 発注制約逸脱(
			@Injectable OrderService.Require require) {
		new Expectations() {{
			require.getByAlter(Dummy.tgtAlters);
			result = Dummy.alterSmmarys;

			orderService.getNecessaryAlters(require, Dummy.tgtAlters, Dummy.alterSmmarys, Dummy.tableId);
			result = Dummy.alterSmmarys;
		}};

		val result = orderService.order(require, Dummy.featureId, Dummy.eventName, Dummy.userName, Dummy.tgtAlters);
		assertThat(result.getErrorList().size()).isNotEqualTo(0);
		assertThat(result.getAtomTask()).isEqualTo(Optional.empty());
	}

}
