package nts.uk.ctx.at.record.dom.stamp.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class StampPromptApplicationTest {

	@Test
	public void getters() {

		StampPromptApplication promptApplication = StampPromptApplicationHelper.getStampPromptApplicationDefault();
		NtsAssert.invokeGetters(promptApplication);

	}

	@Test
	public void testUSER() {

		List<StampRecordDis> results = StampPromptApplicationHelper.getListStampRecordDisUSE();

		StampPromptApplication application = new StampPromptApplication("000-0000000001", results);

		List<ErrorInformationApplication> list = results.stream().map(x -> x.getErrornformation())
				.collect(Collectors.toList());
		
		List<ErrorInformationApplication> lstErrorIF = application.getErrorListApply();
		
		assertThat(lstErrorIF.size() == list.size()).isTrue();
		
		for (int i = 0; i < lstErrorIF.size(); i++) {
			assertThat(lstErrorIF.get(i).getCheckErrorType()).isEqualTo(list.get(i).getCheckErrorType());
			assertThat(lstErrorIF.get(i).getErrorAlarmCode()).isEqualTo(list.get(i).getErrorAlarmCode());
			assertThat(lstErrorIF.get(i).getPromptingMessage()).isEqualTo(list.get(i).getPromptingMessage());
		}
	}
}
