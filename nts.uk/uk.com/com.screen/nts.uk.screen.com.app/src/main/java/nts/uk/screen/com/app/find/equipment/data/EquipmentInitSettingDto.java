package nts.uk.screen.com.app.find.equipment.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentPerformInputFormatSetting;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;

@Data
@AllArgsConstructor
public class EquipmentInitSettingDto {

	/**
	 * 設備利用実績の項目設定<List>
	 */
	private List<EquipmentUsageRecordItemSetting> itemSettings;
	
	/**
	 * 設備の実績入力フォーマット設定<List>
	 */
	private EquipmentPerformInputFormatSetting formatSetting; 
}
