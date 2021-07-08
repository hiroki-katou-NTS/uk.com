package nts.uk.ctx.exio.dom.input.setting.assembly.revise.dataformat;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;

/**
 * 
 * @author HungTT - データ形式設定
 *
 */

@Getter
public class DataFormatSetting extends DomainObject{
	
	private ItemType itemType;

	public DataFormatSetting(int itemType) {
		super();
		this.itemType = EnumAdaptor.valueOf(itemType, ItemType.class);
	}
	
}
