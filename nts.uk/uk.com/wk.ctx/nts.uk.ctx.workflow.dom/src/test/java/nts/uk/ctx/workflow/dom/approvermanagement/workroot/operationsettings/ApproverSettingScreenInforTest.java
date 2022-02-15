package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class ApproverSettingScreenInforTest {
	
	/**
	 * Test getter
	 */
	@Test
	public void testGetter() {
		ApproverSettingScreenInfor domain = new ApproverSettingScreenInfor(
				new ApproverItemName("name 1"),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty());
		
		NtsAssert.invokeGetters(domain);
	}

	/**
	 * Test [C-1] 項目の名称情報で作成する
	 */
	@Test
	public void testFirstConstruct() {
		ItemNameInformation expItemNameInformation = new ItemNameInformation(
				new ApproverItemName("firstItemName"),
				new ApproverItemName("secondItemName"),
				new ApproverItemName("thirdItemName"),
				new ApproverItemName("fourthItemName"),
				new ApproverItemName("fifthItemName"));
		
		ApproverSettingScreenInfor domain = new ApproverSettingScreenInfor(expItemNameInformation);
		
		assertThat(domain.getFirstItemName()).isEqualTo(expItemNameInformation.getFirstItemName());
		assertThat(domain.getSecondItemName().get()).isEqualTo(expItemNameInformation.getSecondItemName());
		assertThat(domain.getThirdItemName().get()).isEqualTo(expItemNameInformation.getThirdItemName());
		assertThat(domain.getFourthItemName().get()).isEqualTo(expItemNameInformation.getFourthItemName());
		assertThat(domain.getFifthItemName().get()).isEqualTo(expItemNameInformation.getFifthItemName());
		assertThat(domain.getProcessMemo()).isEmpty();
		assertThat(domain.getAttentionMemo()).isEmpty();
	}
	
}
