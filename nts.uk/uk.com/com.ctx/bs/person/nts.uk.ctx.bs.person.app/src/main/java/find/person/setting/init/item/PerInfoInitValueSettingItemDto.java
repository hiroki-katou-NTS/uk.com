package find.person.setting.init.item;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
@Value
public class PerInfoInitValueSettingItemDto {
	
	// 個人情報項目定義ID
	private String perInfoItemDefId;

	private String settingId;

	// 個人情報カテゴリID
	private String perInfoCtgId;

	private String itemName;

	private int isRequired;

	// 参照方法
	private int refMethodType;

	// 保存データ型
	private int saveDataType;

	// 文字列
	private String stringValue;

	// 数値
	private int intValue;

	// 日付
	private GeneralDate dateValue;
	
	public static PerInfoInitValueSettingItemDto fromDomain(PerInfoInitValueSetItem domain) {
		return new PerInfoInitValueSettingItemDto(domain.getPerInfoItemDefId(), domain.getSettingId(), domain.getPerInfoCtgId(),
				domain.getItemName(), domain.getIsRequired().value, domain.getRefMethodType().value, domain.getSaveDataType().value,
				domain.getStringValue().v(), Integer.valueOf(domain.getIntValue().toString()), domain.getDateValue());
	}
}
