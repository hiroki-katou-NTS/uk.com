package nts.uk.ctx.pereg.pub.person.info.item;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;

@Data
@NoArgsConstructor
public class PerInfoItemDefExport {

	private String id;

	private String perInfoCtgId;
	
	private String itemCode;
	
	private String itemParentCode;

	private String itemName;

	private int isAbolition;

	private int isFixed;

	private int isRequired;

	private int systemRequired;

	private int requireChangable;
	
	private ItemTypeStateExport itemTypeState;
	
	private String resourceId;

	// is not ItemDefinition of attribute
	private int dispOrder;

	/**
	 * will remove: lanlt
	 */
	private BigDecimal selectionItemRefType;

	// is not ItemDefinition of attribute
	private List<EnumConstant> selectionItemRefTypes;
	
	private boolean canAbolition;
	
	public boolean isSingleItem() {
		return itemTypeState.itemType == ItemTypeExport.SINGLE_ITEM.value;
	}
}
