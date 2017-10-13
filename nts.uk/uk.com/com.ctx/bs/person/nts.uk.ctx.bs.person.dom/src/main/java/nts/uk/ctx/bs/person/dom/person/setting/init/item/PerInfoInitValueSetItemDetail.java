package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The class PerInfoInitValueSetItemDetail
 * 
 * @author lanlt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PerInfoInitValueSetItemDetail {

	private String settingId;

	private String perInfoCtgId;

	private String perInfoItemDefId;

	private String itemName;

	// 参照方法
	private ReferenceMethodType refMethodType;
	
	// 保存データ型
	private SaveDataType saveDataType;

	// 文字列
	private StringValue stringValue;

	// 数値
	private IntValue intValue;

	// 日付
	private GeneralDate dateValue;
	

}
