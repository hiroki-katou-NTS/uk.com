package find.person.info.item;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import nts.arc.enums.EnumConstant;

@Data
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
	
	public PerInfoItemDefDto() {
		
	}
	
}
