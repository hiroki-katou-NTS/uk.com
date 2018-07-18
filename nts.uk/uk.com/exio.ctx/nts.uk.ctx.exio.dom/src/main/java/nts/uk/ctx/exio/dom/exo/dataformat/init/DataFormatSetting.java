package nts.uk.ctx.exio.dom.exo.dataformat.init;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author TamNX - データ形式設定
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
