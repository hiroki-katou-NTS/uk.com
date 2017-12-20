package nts.uk.ctx.pereg.dom.person.setting.init.item;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SaveDataType
 * 
 * @author lanlt
 *
 */
@AllArgsConstructor
@StringMaxLength(1)
public enum SaveDataType {
	
	/** (文字列): */
	STRING(1),
	/** (数値): */
	NUMBERIC(2),
	/** (日付): */
	DATE(3);

	public final Integer value;
}
