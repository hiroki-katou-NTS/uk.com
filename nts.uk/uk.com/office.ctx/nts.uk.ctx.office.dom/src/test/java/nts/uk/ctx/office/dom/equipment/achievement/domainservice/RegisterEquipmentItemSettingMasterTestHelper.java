package nts.uk.ctx.office.dom.equipment.achievement.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.office.dom.equipment.achievement.DisplayWidth;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentFormSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentFormTitle;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentItemNo;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentPerformInputFormatSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;
import nts.uk.ctx.office.dom.equipment.achievement.ItemDisplay;
import nts.uk.ctx.office.dom.equipment.achievement.domain.EquipmentUsageRecordItemSettingTestHelper;

public class RegisterEquipmentItemSettingMasterTestHelper {
	
	/**
	 * Dummy data
	 * [R-1]全て設備利用実績の項目設定を取得する
	 * @param isEmpty result is empty or not
	 */
	public static List<EquipmentUsageRecordItemSetting> getEURItemSettings() {
		List<EquipmentUsageRecordItemSetting> result = new ArrayList<EquipmentUsageRecordItemSetting>(); 
		EquipmentUsageRecordItemSetting item = EquipmentUsageRecordItemSettingTestHelper.createDoamin();
		result.add(item);
		
		return result;
	}
	
	/**
	 * Dummy data
	 * [R-4] 設備の実績入力フォーマット設定を取得する
	 * @param isEmpty result is empty or not
	 */
	public static Optional<EquipmentPerformInputFormatSetting> getEURInputFormatSetting() {
		String cid = "dummy-cid";
		List<ItemDisplay> itemDisplaySettings = new ArrayList<ItemDisplay>();
		ItemDisplay itemDisplay = new ItemDisplay(new DisplayWidth(100), 1, new EquipmentItemNo("10"));
		itemDisplaySettings.add(itemDisplay);
		
		EquipmentPerformInputFormatSetting item = new EquipmentPerformInputFormatSetting(cid, itemDisplaySettings);
		return Optional.of(item);
	}
	
	/**
	 * Dummy data
	 * [R-7]設備帳票設定を取得する
	 * @param isEmpty result is empty or not
	 */
	public static Optional<EquipmentFormSetting> getEquipmentFormSettings() {
		String cid = "dummy-cid";
		EquipmentFormTitle title = new EquipmentFormTitle("10");
		EquipmentFormSetting item = new EquipmentFormSetting(cid, title);
		
		return Optional.of(item);
	}
	
	public static List<EquipmentUsageRecordItemSetting> recordItemSettings() {
		List<EquipmentUsageRecordItemSetting> result = new ArrayList<EquipmentUsageRecordItemSetting>(); 
		EquipmentUsageRecordItemSetting item = EquipmentUsageRecordItemSettingTestHelper.createDoamin();
		result.add(item);
		
		return result;
	}
	
	public static Optional<EquipmentPerformInputFormatSetting> inputFormatSetting() {
		String cid = "dummy-cid";
		List<ItemDisplay> itemDisplaySettings = new ArrayList<ItemDisplay>();
		itemDisplaySettings.add(new ItemDisplay(new DisplayWidth(10), 1, new EquipmentItemNo("ein")));
		itemDisplaySettings.add(new ItemDisplay(new DisplayWidth(100), 2, new EquipmentItemNo("ein2")));
		
		EquipmentPerformInputFormatSetting item = new EquipmentPerformInputFormatSetting(cid, itemDisplaySettings);
		return Optional.of(item);
	}
	
	public static Optional<EquipmentFormSetting> formSettings() {
		String cid = "dummy-cid";
		EquipmentFormTitle title = new EquipmentFormTitle("EquipmentFormTitle");
		EquipmentFormSetting item = new EquipmentFormSetting(cid, title);
		
		return Optional.of(item);
	}
}
