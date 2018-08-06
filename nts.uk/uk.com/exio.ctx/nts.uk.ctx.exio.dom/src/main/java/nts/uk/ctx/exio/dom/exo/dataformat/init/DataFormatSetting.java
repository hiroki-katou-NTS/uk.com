package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author TamNX - データ形式設定
 *
 */

@Getter
@Setter
public class DataFormatSetting extends DomainObject{
	
	private ItemType itemType;
	
	/**
    * 固定値
    */
    private NotUseAtr fixedValue;
    
    /**
    * 固定値の値
    */
    private Optional<DataTypeFixedValue> valueOfFixedValue;
    
    /**
	* NULL値置換
	*/
	private NotUseAtr nullValueReplace;
	 
	/**
	* NULL値置換の値
	*/
	private Optional<DataFormatNullReplacement> valueOfNullValueReplace;

	public DataFormatSetting(int itemType, int fixedValue, String valueOfFixedValue, int nullValueReplace , String valueOfNullValueReplace) {
		super();
		this.itemType = EnumAdaptor.valueOf(itemType, ItemType.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.valueOfFixedValue = Optional.ofNullable(valueOfFixedValue == "" || valueOfFixedValue == null ? null : new DataTypeFixedValue(valueOfFixedValue));
		this.nullValueReplace = EnumAdaptor.valueOf(nullValueReplace, NotUseAtr.class);
		this.valueOfNullValueReplace = Optional.ofNullable(valueOfNullValueReplace == "" ||  valueOfNullValueReplace == null ? null : new DataFormatNullReplacement(valueOfNullValueReplace));
	}

	public DataFormatSetting(ItemType itemType, NotUseAtr fixedValue, Optional<DataTypeFixedValue> valueOfFixedValue,
			NotUseAtr nullValueReplace, Optional<DataFormatNullReplacement> valueOfNullValueReplace) {
		super();
		this.itemType = itemType;
		this.fixedValue = fixedValue;
		this.valueOfFixedValue = valueOfFixedValue;
		this.nullValueReplace = nullValueReplace;
		this.valueOfNullValueReplace = valueOfNullValueReplace;
	}
	
}
