package nts.uk.ctx.office.dom.equipment.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.ItemClassification;
import nts.uk.ctx.office.dom.equipment.achievement.domain.EquipmentUsageRecordItemSettingTestHelper;
import nts.uk.ctx.office.dom.equipment.data.ItemData.Require;

@RunWith(JMockit.class)
public class ItemDataTest {

	@Injectable
	private Require require;

	@Test
	public void getters() {
		// when
		ItemData domain = EquipmentDataTestHelper.createItemData("1", ItemClassification.TEXT, "test");

		// then
		NtsAssert.invokeGetters(domain);
	}

	/**
	 * [C-1] 新規追加 
	 * $エラー.isPresent() == false
	 */
	@Test
	public void testCreateTempItemDataNoErr() {
		// given
		ItemData domain = EquipmentDataTestHelper.createItemData("1", ItemClassification.TEXT, "test");

		new Expectations() {
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "1");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("1", ItemClassification.TEXT, 5));
			};
		};

		// when
		ItemCreationResultTemp temp = ItemData.createTempData(require, EquipmentDataTestHelper.CID,
				new EquipmentItemNo("1"), new ActualItemUsageValue("test"));
		
		// then
		assertThat(temp.getItemData().isPresent()).isTrue();
		assertThat(temp.getItemData().get()).isEqualTo(domain);
	}
	
	/**
	 * [C-1] 新規追加 
	 * $エラー.isPresent() == true
	 */
	@Test
	public void testCreateTempItemDataHasErr() {
		// given
		new Expectations() {
			{
				require.getItemSetting(EquipmentDataTestHelper.CID, "1");
				result = Optional
						.of(EquipmentUsageRecordItemSettingTestHelper.mockDomain("1", ItemClassification.TEXT, 5));
			};
		};

		// when
		ItemCreationResultTemp temp = ItemData.createTempData(require, EquipmentDataTestHelper.CID,
				new EquipmentItemNo("1"), new ActualItemUsageValue("test1234"));
		
		// then
		assertThat(temp.getItemData().isPresent()).isFalse();
		assertThat(temp.getErrorMsg().isPresent()).isTrue();
	}
	
	/**
	 * [C-1] 新規追加 
	 * $項目設定.isPresent() == false
	 */
	@Test
	public void testCreateTempItemDataNoItemSetting() {
		// when
		ItemCreationResultTemp temp = ItemData.createTempData(require, EquipmentDataTestHelper.CID,
				new EquipmentItemNo("15"), new ActualItemUsageValue("test"));
		
		// then
		assertThat(temp).isNull();
	}
}
