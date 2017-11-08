package find.person.info.item;

import java.math.BigDecimal;
import java.util.List;

import find.layout.classification.ActionRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumConstant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerInfoItemDefDto {

	private String id;

	private String perInfoCtgId;

	private String itemCode;

	private String itemName;

	private int isAbolition;

	private int isFixed;

	private int isRequired;

	private int systemRequired;

	private int requireChangable;

	private int dispOrder;

	private BigDecimal selectionItemRefType;

	private ItemTypeStateDto itemTypeState;

	private List<EnumConstant> selectionItemRefTypes;

	private ActionRole actionRole;

	public PerInfoItemDefDto(String id, String perInfoCtgId, String itemCode, String itemName, int isAbolition,
			int isFixed, int isRequired, int systemRequired, int requireChangable, int dispOrder,
			BigDecimal selectionItemRefType, ItemTypeStateDto itemTypeState, List<EnumConstant> selectionItemRefTypes) {
		this.id = id;
		this.perInfoCtgId = perInfoCtgId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.isAbolition = isAbolition;
		this.isFixed = isFixed;
		this.isRequired = isRequired;
		this.systemRequired = systemRequired;
		this.requireChangable = requireChangable;
		this.dispOrder = dispOrder;
		this.selectionItemRefType = selectionItemRefType;
		this.itemTypeState = itemTypeState;
		this.selectionItemRefTypes = selectionItemRefTypes;
	}
}
