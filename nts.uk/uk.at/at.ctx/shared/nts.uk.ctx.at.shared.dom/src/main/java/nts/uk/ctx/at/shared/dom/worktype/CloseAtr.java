package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;
/**
 * The Enum Colse Attribute
 * 
 * @author sonnh
 *
 */
@AllArgsConstructor
public enum CloseAtr {
	
	/**
	 * 産前休業
	 */
	PRENATAL(0, "Enum_CloseAtr_PRENATAL"),
	
	/**
	 * 産後休業
	 */
	POSTPARTUM(1, "Enum_CloseAtr_POSTPARTUM"),
	
	/**
	 * 育児休業
	 */
	CHILD_CARE(2, "Enum_CloseAtr_CHILD_CARE"),
	
	/**
	 * 介護休業
	 */
	CARE(3, "Enum_CloseAtr_CARE"),
	
	/**
	 * 傷病休業
	 */
	INJURY_OR_ILLNESS(4, "Enum_CloseAtr_INJURY_OR_ILLNESS");
	
    public final int value;
    public final String nameId;
}
