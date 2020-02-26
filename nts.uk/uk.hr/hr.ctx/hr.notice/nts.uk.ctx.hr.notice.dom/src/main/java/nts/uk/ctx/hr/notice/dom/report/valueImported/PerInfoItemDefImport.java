package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumConstant;

@Setter
@Getter
@NoArgsConstructor
public class PerInfoItemDefImport {

	public PerInfoItemDefImport(String id, String perInfoCtgId, String itemCode, String itemParentCode, String itemName,
			int isAbolition, int isFixed, int isRequired, int systemRequired, int requireChangable,
			ItemTypeStateImport itemTypeState, String resourceId, int dispOrder, BigDecimal selectionItemRefType,
			List<EnumConstant> selectionItemRefTypes, boolean canAbolition, String categoryCode, String categoryName) {
		super();
		this.id = id;
		this.perInfoCtgId = perInfoCtgId;
		this.itemCode = itemCode;
		this.itemParentCode = itemParentCode;
		this.itemName = itemName;
		this.isAbolition = isAbolition;
		this.isFixed = isFixed;
		this.isRequired = isRequired;
		this.systemRequired = systemRequired;
		this.requireChangable = requireChangable;
		this.itemTypeState = itemTypeState;
		this.resourceId = resourceId;
		this.dispOrder = dispOrder;
		this.selectionItemRefType = selectionItemRefType;
		this.selectionItemRefTypes = selectionItemRefTypes;
		this.canAbolition = canAbolition;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
	}

	public PerInfoItemDefImport(String id, String perInfoCtgId, String itemCode, String itemParentCode, String itemName,
			int isAbolition, int isFixed, int isRequired, int systemRequired, int requireChangable,
			ItemTypeStateImport itemTypeState, String resourceId, int dispOrder, BigDecimal selectionItemRefType,
			List<EnumConstant> selectionItemRefTypes, boolean canAbolition) {
		super();
		this.id = id;
		this.perInfoCtgId = perInfoCtgId;
		this.itemCode = itemCode;
		this.itemParentCode = itemParentCode;
		this.itemName = itemName;
		this.isAbolition = isAbolition;
		this.isFixed = isFixed;
		this.isRequired = isRequired;
		this.systemRequired = systemRequired;
		this.requireChangable = requireChangable;
		this.itemTypeState = itemTypeState;
		this.resourceId = resourceId;
		this.dispOrder = dispOrder;
		this.selectionItemRefType = selectionItemRefType;
		this.selectionItemRefTypes = selectionItemRefTypes;
		this.canAbolition = canAbolition;
	}

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
	
	private ItemTypeStateImport itemTypeState;
	
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
	
	private String categoryCode;
	
	private String categoryName;
	
	private String initValue;
	
	public boolean isSingleItem() {
		return itemTypeState.itemType == ItemTypeImport.SINGLE_ITEM.value;
	}
}
