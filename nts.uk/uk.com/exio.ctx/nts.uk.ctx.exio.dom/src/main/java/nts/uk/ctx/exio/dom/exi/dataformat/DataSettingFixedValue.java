package nts.uk.ctx.exio.dom.exi.dataformat;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author DatLH
 * データ設定固定値
 *
 */
@StringMaxLength(30)
public class DataSettingFixedValue extends StringPrimitiveValue<DataSettingFixedValue>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataSettingFixedValue(String rawValue) {
		super(rawValue);
	}
	
}
