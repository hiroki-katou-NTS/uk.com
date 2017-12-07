package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.shr.pereg.app.ComboBoxObject;

/**
 * item def dto which declare fields for displaying layout
 * @author xuan vinh
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerInfoItemDefForLayoutDto {
	
	private String itemDefId;
	
	private String perInfoCtgId;
	
	private String perInfoCtgCd;

	private String itemCode;

	private String itemName;
	
	private int itemDefType;
	
	private PerInfoItemDefDto itemDefDto;
	
	/**
	 * combo box value list when item type selection
	 */
	private List<ComboBoxObject> lstComboxBoxValue;
	
	private List<PerInfoItemDefForLayoutDto> lstChildItemDef;

	private int isRequired;

	private int dispOrder;
	
	private int row;
	
	private ActionRole actionRole;

	private BigDecimal selectionItemRefType;

	private ItemTypeStateDto itemTypeState;

	private List<EnumConstant> selectionItemRefTypes;
	
}
