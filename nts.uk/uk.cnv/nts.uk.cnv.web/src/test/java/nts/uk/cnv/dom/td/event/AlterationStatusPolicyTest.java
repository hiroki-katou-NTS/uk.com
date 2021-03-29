package nts.uk.cnv.dom.td.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.summary.AlterationStatusPolicy;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

public class AlterationStatusPolicyTest {

	static class Dummy {
		private static String featureId = "featurefeature";
		private static EventType type = EventType.ORDER;
		private static DevelopmentProgress Progress = DevelopmentProgress.not(type.necessary());
		private static String targetTableId 	= "tttttttttt";
		private static String unrelatedTableId 	= "xxxxxxxxxx";
		private static List<AlterationSummary> targetAlters = Arrays.asList(targetTableAlter(1), targetTableAlter(2), targetTableAlter(3));
	}
	
	AlterationStatusPolicy ep = new AlterationStatusPolicy(Dummy.type);

	@Test
	public void 既存未達orutaなし(
			@Injectable AlterationStatusPolicy.Require require) {
		
		new Expectations() {{
			require.getByTable(Dummy.targetTableId, Dummy.Progress);
			result = Collections.emptyList();
		}};
		
		List<AlterationSummary> result = NtsAssert.Invoke.privateMethod(ep, "getNecessaryAlters", 
				require, Dummy.targetAlters, Dummy.targetTableId);
		
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test
	public void 古い未達orutaなし(
			@Injectable AlterationStatusPolicy.Require require) {
		val targetAlters 	= Arrays.asList(targetTableAlter(1), 
											targetTableAlter(2), 
											targetTableAlter(3), 
											unrelatedTableAlter(1), 
											unrelatedTableAlter(2));
		
		val existingAlters 	= Arrays.asList(targetTableAlter(1), 
											targetTableAlter(2), 
											targetTableAlter(3));

		new Expectations() {{
			require.getByTable(Dummy.targetTableId, Dummy.Progress);
			result = existingAlters;
		}};
		
		List<AlterationSummary> result = NtsAssert.Invoke.privateMethod(ep, "getNecessaryAlters", 
				require, targetAlters, Dummy.targetTableId);
		
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test
	public void 古い未達orutaあり(
			@Injectable AlterationStatusPolicy.Require require) {
		val targetAlters 	= Arrays.asList(targetTableAlter(2), 
											targetTableAlter(3), 
											unrelatedTableAlter(1), 
											unrelatedTableAlter(2));
		
		val existingAlters 	= Arrays.asList(targetTableAlter(1), 
											targetTableAlter(2), 
											targetTableAlter(3));

		new Expectations() {{
			require.getByTable(Dummy.targetTableId, Dummy.Progress);
			result = existingAlters;
		}};
		
		List<AlterationSummary> result = NtsAssert.Invoke.privateMethod(ep, "getNecessaryAlters", 
				require, targetAlters, Dummy.targetTableId);
		
		assertThat(result.size()).isNotEqualTo(0);
	}
	
	private static AlterationSummary targetTableAlter(Integer addMinute) {
		return new AlterationSummary("tgtAlterId_" + addMinute.toString(), GeneralDateTime.now().addMinutes(addMinute), Dummy.targetTableId, DevelopmentStatus.NOT_ORDER, new AlterationMetaData("", ""), Dummy.featureId);
	}
	
	private static AlterationSummary unrelatedTableAlter(Integer addMinute) {
		return new AlterationSummary("utdAlterId_" + addMinute.toString(), GeneralDateTime.now().addMinutes(addMinute), Dummy.unrelatedTableId, DevelopmentStatus.NOT_ORDER, new AlterationMetaData("", ""), Dummy.featureId);
	}


}
